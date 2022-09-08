package com.comvee.cdms.common.interceptors;

import com.comvee.cdms.glu.service.GluServiceI;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 单点血糖仪接口权限判断
 */
public class GlucometerAuthInterceptor implements HandlerInterceptor {

    private final static Logger log = LoggerFactory.getLogger(GlucometerAuthInterceptor.class);

    @Autowired
    private GluServiceI gluService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String appId = request.getParameter("appId");
        checkAppId(appId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 判断appId是否合法
     * @param appId
     */
    private void checkAppId(String appId){
        if(StringUtils.isBlank(appId)){
            throw new BusinessException("5001" ,"版本过低，请升级后使用");
        }
        Map<String ,String> map = this.gluService.listGlucometerApplication();
        if(map.get(appId) == null){
            log.warn("非法的单点血糖仪接入appId:{}" ,appId);
            throw new BusinessException("403" ,"非法appId，请确认");
        }
    }
}
