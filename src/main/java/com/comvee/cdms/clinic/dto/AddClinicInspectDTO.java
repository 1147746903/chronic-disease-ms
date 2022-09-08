package com.comvee.cdms.clinic.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author wyc
 * @date 2019/8/12 11:41
 */
public class AddClinicInspectDTO {
    /**
     * 患者id
     */
    @NotEmpty()
    private String memberId;

    /**
     * 医生id
     */
    @NotEmpty()
    private String doctorId;

    /**
     * 责任医生
     */
    private String doctorName;

    /**
     * 创建人id
     */
    private String creatorId;

    /**
     * 床号
     */
    private String bedNumber;

    /**
     * 基本信息
     */
    private String baseJson;

    /**
     * 院内相关检查信息
     */
    private String checkJson;

    /**
     * 保存状态 0 未保存 1 保存草稿 2 已提交
     */
    private Integer saveStatus;

    /**
     * 检查日期
     */
    private String checkDt;

    /**
     * 是否有效0 否 1 是
     */
    private Integer isValid;

    /**
     * 医院id
     */
    private String hospitalId;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getBaseJson() {
        return baseJson;
    }

    public void setBaseJson(String baseJson) {
        this.baseJson = baseJson;
    }

    public String getCheckJson() {
        return checkJson;
    }

    public void setCheckJson(String checkJson) {
        this.checkJson = checkJson;
    }

    public Integer getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Integer saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getCheckDt() {
        return checkDt;
    }

    public void setCheckDt(String checkDt) {
        this.checkDt = checkDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
