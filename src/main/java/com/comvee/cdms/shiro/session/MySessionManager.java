package com.comvee.cdms.shiro.session;

import com.comvee.cdms.common.utils.SerializableUtils;
import com.comvee.cdms.shiro.mapper.ShiroSessionMapper;
import com.comvee.cdms.shiro.model.ShiroSessionPO;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

public class MySessionManager extends DefaultWebSessionManager {

    private final static Logger log = LoggerFactory.getLogger(MySessionManager.class);

    public final static String CHANGE_SESSION_IMMEDIATELY = MySessionManager.class.getName() + "." + "changeSessionImmediately";

    @Autowired
    private ShiroSessionMapper shiroSessionMapper;

    @Override
    public void touch(SessionKey key) throws InvalidSessionException {
        Session s = lookupRequiredSession(key);

        //如果存在马上修改session的属性，则调用onChange
        if(s.getAttribute(CHANGE_SESSION_IMMEDIATELY) != null){
            s.removeAttribute(CHANGE_SESSION_IMMEDIATELY);
            onChange(s);
            return;
        }

        //1/10过期时间更新最后访问时间
        long t = System.currentTimeMillis() - getGlobalSessionTimeout() / 10;
        if(s.getLastAccessTime().getTime() > t){
            return;
        }

        s.touch();
        onChange(s);
    }

    @Override
    protected void onStart(Session session, SessionContext context) {
//        super.onStart(session, context);

        if (!WebUtils.isHttp(context)) {
            log.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response " +
                    "pair. No session ID cookie will be set.");
            return;

        }
        HttpServletRequest request = WebUtils.getHttpRequest(context);
        HttpServletResponse response = WebUtils.getHttpResponse(context);

        if (isSessionIdCookieEnabled()) {
            Serializable sessionId = session.getId();
            storeSessionId(sessionId, request, response);
        } else {
            log.debug("Session ID cookie is disabled.  No cookie has been set for new session with id {}", session.getId());
        }

        request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
    }


    @Override
    public void validateSessions() {
        List<ShiroSessionPO> shiroSessionPOList = null;
        while(true){
            shiroSessionPOList = this.shiroSessionMapper.listTimeoutSession();
            if(shiroSessionPOList == null || shiroSessionPOList.size() == 0){
                return;
            }
            shiroSessionPOList.forEach(x ->{
                try{
                    doValidate(x);
                }catch (InvalidSessionException e){
                    log.warn("shiro session过期/无效,sessionId:{},最后访问时间为：{}" , x.getSessionId() ,x.getLastAccessTime());
                }catch (Exception e){
                    log.warn("shiro session验证失败,sessionId:{}" , x.getSessionId() ,e);
                    SimpleSession session = new SimpleSession();
                    session.setId(x.getSessionId());
                    delete(session);
                }
            });
        }
    }


    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = getUriPathSegmentParamValue(request, ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        if (id == null) {
            //not a URI path segment parameter, try the query parameters:
            String name = getSessionIdName();
            id = request.getParameter(name);
            if (id == null) {
                //try lowercase:
                id = request.getParameter(name.toLowerCase());
            }
        }
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
        } else {
            //not in a cookie, or cookie is disabled - try the request URI as a fallback (i.e. due to URL rewriting):

            //try the URI path segment parameters first:
            id = getSessionIdCookieValue(request, response);

            if (id != null) {
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                        ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
            }
        }
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            //automatically mark it valid here.  If it is invalid, the
            //onUnknownSession method below will be invoked and we'll remove the attribute at that time.
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        }

        // always set rewrite flag - SHIRO-361
        request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, isSessionIdUrlRewritingEnabled());

        return id;
    }

    private void storeSessionId(Serializable currentId, HttpServletRequest request, HttpServletResponse response) {
        if (currentId == null) {
            String msg = "sessionId cannot be null when persisting for subsequent requests.";
            throw new IllegalArgumentException(msg);
        }
        Cookie template = getSessionIdCookie();
        Cookie cookie = new SimpleCookie(template);
        String idString = currentId.toString();
        cookie.setValue(idString);
        //防止跨域问题
        cookie.setSameSite(null);
/*        cookie.setSameSite(Cookie.SameSiteOptions.NONE);
        cookie.setSecure(true);*/
        cookie.saveTo(request, response);
        log.trace("Set session ID cookie for session with id {}", idString);
    }

    private Session lookupRequiredSession(SessionKey key) throws SessionException {
        Session session = lookupSession(key);
        if (session == null) {
            String msg = "Unable to locate required Session instance based on SessionKey [" + key + "].";
            throw new UnknownSessionException(msg);
        }
        return session;
    }

    private Session lookupSession(SessionKey key) throws SessionException {
        if (key == null) {
            throw new NullPointerException("SessionKey argument cannot be null.");
        }
        return doGetSession(key);
    }




    /**
     * 验证session是否过期
     * @param shiroSessionPO
     */
    private void doValidate(ShiroSessionPO shiroSessionPO){
        Session session = (Session) SerializableUtils.deserialize(shiroSessionPO.getSessionObject());
        SessionKey key = new DefaultSessionKey(session.getId());
        validate(session, key);
    }



    private String getSessionIdCookieValue(ServletRequest request, ServletResponse response) {
        if (!isSessionIdCookieEnabled()) {
            log.debug("Session ID cookie is disabled - session id will not be acquired from a request cookie.");
            return null;
        }
        if (!(request instanceof HttpServletRequest)) {
            log.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
            return null;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return getSessionIdCookie().readValue(httpRequest, WebUtils.toHttp(response));
    }

    private String getUriPathSegmentParamValue(ServletRequest servletRequest, String paramName) {

        if (!(servletRequest instanceof HttpServletRequest)) {
            return null;
        }
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uri = request.getRequestURI();
        if (uri == null) {
            return null;
        }

        int queryStartIndex = uri.indexOf('?');
        if (queryStartIndex >= 0) { //get rid of the query string
            uri = uri.substring(0, queryStartIndex);
        }

        int index = uri.indexOf(';'); //now check for path segment parameters:
        if (index < 0) {
            //no path segment params - return:
            return null;
        }

        //there are path segment params, let's get the last one that may exist:

        final String TOKEN = paramName + "=";

        uri = uri.substring(index+1); //uri now contains only the path segment params

        //we only care about the last JSESSIONID param:
        index = uri.lastIndexOf(TOKEN);
        if (index < 0) {
            //no segment param:
            return null;
        }

        uri = uri.substring(index + TOKEN.length());

        index = uri.indexOf(';'); //strip off any remaining segment params:
        if(index >= 0) {
            uri = uri.substring(0, index);
        }

        return uri; //what remains is the value
    }

    private String getSessionIdName() {
        String name = super.getSessionIdCookie() != null ? super.getSessionIdCookie().getName() : null;
        if (name == null) {
            name = ShiroHttpSession.DEFAULT_SESSION_ID_NAME;
        }
        return name;
    }
}
