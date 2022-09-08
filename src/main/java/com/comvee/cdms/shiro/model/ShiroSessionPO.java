package com.comvee.cdms.shiro.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_session
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class ShiroSessionPO {
    /**
     * session_id
     */
    private String sessionId;

    /**
     * start_time_stamp
     */
    private String startTimeStamp;

    /**
     * last_access_time
     */
    private String lastAccessTime;

    /**
     * timeout
     */
    private Long timeout;

    /**
     * host
     */
    private String host;

    /**
     * attributes
     */
    private String sessionObject;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(String lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSessionObject() {
        return sessionObject;
    }

    public void setSessionObject(String sessionObject) {
        this.sessionObject = sessionObject;
    }
}