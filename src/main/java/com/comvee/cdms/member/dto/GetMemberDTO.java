package com.comvee.cdms.member.dto;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/8
 */
public class GetMemberDTO implements Serializable{

    private String memberId;
    private String idCard;
    private String mobilePhone;
    private String hospitalId;
    private String keyword;
    private String memberName;
    private String birthday;
    private String sex;
    private String socialCard;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSocialCard() {
        return socialCard;
    }

    public void setSocialCard(String socialCard) {
        this.socialCard = socialCard;
    }
}
