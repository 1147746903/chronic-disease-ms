package com.comvee.cdms.dybloodsugar.dto;

public class MemberSensorShowDTO {
    private String sid;
    private String memberSensorSid;
    private String memberId;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberSensorSid() {
        return memberSensorSid;
    }

    public void setMemberSensorSid(String memberSensorSid) {
        this.memberSensorSid = memberSensorSid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
