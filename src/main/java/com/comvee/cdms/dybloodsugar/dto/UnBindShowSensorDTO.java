package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

public class UnBindShowSensorDTO implements Serializable {

    @NotEmpty(message = "memberSensorSid不可为空")
    private String memberSensorSid;

    private String memberId;

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
