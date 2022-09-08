package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotNull;

/**
 * @author wyc
 * @date 2019/4/15 15:52
 */
public class ListMemberByDoctorDTO {
    @NotNull
    private String doctorId; //医生id
    private String memberName; //患者姓名
    private String idCard;  //患者身份号
    private String phoneNumber; //患者手机号
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
