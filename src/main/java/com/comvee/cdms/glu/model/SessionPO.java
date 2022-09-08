/**    
 * 文件名：SessionPO.java    
 *    
 * 版本信息：    
 * 日期：2018-1-12    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.comvee.cdms.glu.model;

import java.io.Serializable;

/**  
 * 会话对象
 * @author: 苏友智
 * @time：2018-1-12 上午10:29:15     
 * @version:       
 */
public class SessionPO implements Serializable{
    
    /**    
     *
     */    
    private static final long serialVersionUID = -2333640917092314601L;
    
    private String sid;
    private String userId;
    private String sessionToken;
    private String insertDt;
    private Integer sessionStatus;
    private Integer type;
    private String invalidDt;
    private Long lastAccessTime;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public Integer getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(Integer sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInvalidDt() {
        return invalidDt;
    }

    public void setInvalidDt(String invalidDt) {
        this.invalidDt = invalidDt;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Override
    public String toString() {
        return "SessionPO{" +
                "sid='" + sid + '\'' +
                ", userId='" + userId + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", sessionStatus=" + sessionStatus +
                ", type=" + type +
                ", invalidDt='" + invalidDt + '\'' +
                '}';
    }
}
