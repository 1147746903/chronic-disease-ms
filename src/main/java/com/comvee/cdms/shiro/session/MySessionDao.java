package com.comvee.cdms.shiro.session;

import com.comvee.cdms.common.utils.SerializableUtils;
import com.comvee.cdms.shiro.mapper.ShiroSessionMapper;
import com.comvee.cdms.shiro.model.ShiroSessionPO;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class MySessionDao extends EnterpriseCacheSessionDAO {

    private final static Logger log = LoggerFactory.getLogger(MySessionDao.class);

    @Autowired
    private ShiroSessionMapper shiroSessionMapper;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        ShiroSessionPO shiroSessionPO = castSessionToShiroSession(session);
        this.shiroSessionMapper.addShiroSession(shiroSessionPO);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        ShiroSessionPO shiroSessionPO = this.shiroSessionMapper.getShiroSession(sessionId.toString());
        if(shiroSessionPO == null){
            return null;
        }
        Session result = null;
        try{
            result = (Session) SerializableUtils.deserialize(shiroSessionPO.getSessionObject());
        }catch (Exception ex){
            log.error("从数据库读取的Shiro Session对象反序列化失败" ,ex);
        }
        return result;
    }

    @Override
    protected void doUpdate(Session session) {
        if(session instanceof ValidatingSession && !((ValidatingSession) session).isValid()){
            return;
        }
        ShiroSessionPO shiroSessionPO = castSessionToShiroSession(session);
        this.shiroSessionMapper.updateShiroSession(shiroSessionPO);

    }

    @Override
    protected void doDelete(Session session) {
        this.shiroSessionMapper.deleteShiroSession(session.getId().toString());
    }

    /**
     * 格式化日期
     * @param date
     * @return
     */
    private String formatDate(Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 对象转换
     * @param session
     * @return
     */
    private ShiroSessionPO castSessionToShiroSession(Session session){
        ShiroSessionPO shiroSessionPO = new ShiroSessionPO();
        shiroSessionPO.setSessionId(session.getId().toString());
        shiroSessionPO.setStartTimeStamp(formatDate(session.getStartTimestamp()));
        //为空则使用当前时间
        Date lastAccessTime = Optional.ofNullable(session.getLastAccessTime()).orElse(new Date());
        shiroSessionPO.setLastAccessTime(formatDate(lastAccessTime));
        shiroSessionPO.setTimeout(session.getTimeout());
        shiroSessionPO.setHost(session.getHost());
        shiroSessionPO.setSessionObject(SerializableUtils.serialize(session));
        return shiroSessionPO;
    }
}
