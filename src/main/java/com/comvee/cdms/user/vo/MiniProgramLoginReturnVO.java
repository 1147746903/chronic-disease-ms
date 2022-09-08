package com.comvee.cdms.user.vo;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
public class MiniProgramLoginReturnVO {

    private String sessionId;
    private Integer joinStatus;
    private String memberId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
    }

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
    
}
