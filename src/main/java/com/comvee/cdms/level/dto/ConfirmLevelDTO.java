package com.comvee.cdms.level.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author wyc
 * @date 2019/11/20 18:46
 */
public class ConfirmLevelDTO {

    /**
     * 分层分级id
     */
    @NotEmpty
    private String sid;

    /**
     * 调整原因
     */
    private String contrastAnalyze;

    /**
     * 分级 1 一级支持 2 二级支持 3 三级支持 0其他
     */
    private Integer memberLevel;

    /**
     * 分层 1 高危 2 中危 3 低危
     */
    private Integer memberLayer;

    /**
     * 是否调整 0:否 1:是
     */
    @NotNull
    private Integer adjustment;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 来源 1系统 2医生手动调整
     */
    @NotNull
    private Integer origin;
    private String confirmDt;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getContrastAnalyze() {
        return contrastAnalyze;
    }

    public void setContrastAnalyze(String contrastAnalyze) {
        this.contrastAnalyze = contrastAnalyze;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public Integer getMemberLayer() {
        return memberLayer;
    }

    public void setMemberLayer(Integer memberLayer) {
        this.memberLayer = memberLayer;
    }

    public Integer getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Integer adjustment) {
        this.adjustment = adjustment;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getConfirmDt() {
        return confirmDt;
    }

    public void setConfirmDt(String confirmDt) {
        this.confirmDt = confirmDt;
    }
}
