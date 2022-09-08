package com.comvee.cdms.differentlevels.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class MemberDiffLevelSureDTO implements Serializable {
    /**
     * 记录编号
     */
    @NotEmpty
    private String sid;
    @NotEmpty
    private String doctorId;
    /**
     * 是否调整 1是 0否
     */
    @NotNull
    private Integer adjustment;
    private String caReason;
    private Integer adLayer;
    private Integer adLevel;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Integer adjustment) {
        this.adjustment = adjustment;
    }

    public String getCaReason() {
        return caReason;
    }

    public void setCaReason(String caReason) {
        this.caReason = caReason;
    }

    public Integer getAdLayer() {
        return adLayer;
    }

    public void setAdLayer(Integer adLayer) {
        this.adLayer = adLayer;
    }

    public Integer getAdLevel() {
        return adLevel;
    }

    public void setAdLevel(Integer adLevel) {
        this.adLevel = adLevel;
    }
}
