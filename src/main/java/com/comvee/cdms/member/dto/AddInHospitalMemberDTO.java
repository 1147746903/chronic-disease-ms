package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

public class AddInHospitalMemberDTO {

    @NotEmpty
    private String memberId;
    @NotEmpty
    private String sid;
    @NotEmpty
    private String inHospitalDate;
    @NotEmpty
    private String hospitalNo;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }
}
