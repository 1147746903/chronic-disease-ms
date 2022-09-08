package com.comvee.cdms.member.dto;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/16
 */
public class CountDoctorMemberDTO implements Serializable{

    private static final long serialVersionUID = 3004330737239435286L;

    private String memberId;
    private String doctorId;
    private Integer concernStatus;
    private Integer isAttend;
    private String groupId;
    private Integer payStatus;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(Integer concernStatus) {
        this.concernStatus = concernStatus;
    }

    public Integer getIsAttend() {
        return isAttend;
    }

    public void setIsAttend(Integer isAttend) {
        this.isAttend = isAttend;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
