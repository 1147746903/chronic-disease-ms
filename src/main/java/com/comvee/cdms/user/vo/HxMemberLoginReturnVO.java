package com.comvee.cdms.user.vo;

import com.comvee.cdms.member.po.MemberPO;

/**
 * @author: suyz
 * @date: 2019/7/23
 * @author-update 林雨堆
 */
public class HxMemberLoginReturnVO {

    private MemberPO member;
    private String sessionId;
    /**
     * 华西用户编号
     */
    private String hxUserId;

    public MemberPO getMember() {
        return member;
    }

    public void setMember(MemberPO member) {
        this.member = member;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setHxUserId(String hxUserId) {
        this.hxUserId = hxUserId;
    }

    public String getHxUserId() {
        return hxUserId;
    }
}
