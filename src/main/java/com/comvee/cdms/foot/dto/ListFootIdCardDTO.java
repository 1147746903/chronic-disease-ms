package com.comvee.cdms.foot.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public class ListFootIdCardDTO {

    @NotEmpty
    private String IdCard;

    private String followId;

    private String doctorId;

    private String saveType;

    private String footType;

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public String getFootType() {
        return footType;
    }

    public void setFootType(String footType) {
        this.footType = footType;
    }
}
