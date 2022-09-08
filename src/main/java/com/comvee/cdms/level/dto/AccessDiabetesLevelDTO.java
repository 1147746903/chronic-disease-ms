package com.comvee.cdms.level.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccessDiabetesLevelDTO {

    @NotEmpty(message = "memberId 不能为空")
    private String memberId;
    @NotNull(message = "lowBloodSugarTimes 不能为空")
    private Integer lowBloodSugarTimes;
    @NotNull(message = "hba1c 不能为空")
    private Double hba1c;

    private String diabeticNephropathyCheckDt;
    /**
     * 是否确诊糖尿病肾病
     */
    private String diabeticNephropathy;
    /**
     * 糖尿病肾病类型
     */
    private String diabeticNephropathyType;
    private String diabeticNephropathyTypeOther;

    private String diabeticFootCheckDt;
    /**、
     * 是否确诊糖尿病足
     */
    private String diabeticFoot;
    /**
     * 糖尿病足症状
     */
    private String diabeticFootSymptom;
    private String diabeticFootSymptomOther;

    /**
     * 左眼病变情况
     */
    private Integer leftEyePathology;
    /**
     * 右眼病变情况
     */
    private Integer rightEyePathology;

    private Double leftAbi;
    private Double rightAbi;

    private Double leftVpt;
    private Double rightVpt;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getLowBloodSugarTimes() {
        return lowBloodSugarTimes;
    }

    public void setLowBloodSugarTimes(Integer lowBloodSugarTimes) {
        this.lowBloodSugarTimes = lowBloodSugarTimes;
    }

    public Double getHba1c() {
        return hba1c;
    }

    public void setHba1c(Double hba1c) {
        this.hba1c = hba1c;
    }

    public String getDiabeticNephropathyCheckDt() {
        return diabeticNephropathyCheckDt;
    }

    public void setDiabeticNephropathyCheckDt(String diabeticNephropathyCheckDt) {
        this.diabeticNephropathyCheckDt = diabeticNephropathyCheckDt;
    }

    public String getDiabeticNephropathy() {
        return diabeticNephropathy;
    }

    public void setDiabeticNephropathy(String diabeticNephropathy) {
        this.diabeticNephropathy = diabeticNephropathy;
    }

    public String getDiabeticNephropathyType() {
        return diabeticNephropathyType;
    }

    public void setDiabeticNephropathyType(String diabeticNephropathyType) {
        this.diabeticNephropathyType = diabeticNephropathyType;
    }

    public String getDiabeticNephropathyTypeOther() {
        return diabeticNephropathyTypeOther;
    }

    public void setDiabeticNephropathyTypeOther(String diabeticNephropathyTypeOther) {
        this.diabeticNephropathyTypeOther = diabeticNephropathyTypeOther;
    }

    public String getDiabeticFootCheckDt() {
        return diabeticFootCheckDt;
    }

    public void setDiabeticFootCheckDt(String diabeticFootCheckDt) {
        this.diabeticFootCheckDt = diabeticFootCheckDt;
    }

    public String getDiabeticFoot() {
        return diabeticFoot;
    }

    public void setDiabeticFoot(String diabeticFoot) {
        this.diabeticFoot = diabeticFoot;
    }

    public String getDiabeticFootSymptom() {
        return diabeticFootSymptom;
    }

    public void setDiabeticFootSymptom(String diabeticFootSymptom) {
        this.diabeticFootSymptom = diabeticFootSymptom;
    }

    public String getDiabeticFootSymptomOther() {
        return diabeticFootSymptomOther;
    }

    public void setDiabeticFootSymptomOther(String diabeticFootSymptomOther) {
        this.diabeticFootSymptomOther = diabeticFootSymptomOther;
    }

    public Integer getLeftEyePathology() {
        return leftEyePathology;
    }

    public void setLeftEyePathology(Integer leftEyePathology) {
        this.leftEyePathology = leftEyePathology;
    }

    public Integer getRightEyePathology() {
        return rightEyePathology;
    }

    public void setRightEyePathology(Integer rightEyePathology) {
        this.rightEyePathology = rightEyePathology;
    }

    public Double getLeftAbi() {
        return leftAbi;
    }

    public void setLeftAbi(Double leftAbi) {
        this.leftAbi = leftAbi;
    }

    public Double getRightAbi() {
        return rightAbi;
    }

    public void setRightAbi(Double rightAbi) {
        this.rightAbi = rightAbi;
    }

    public Double getLeftVpt() {
        return leftVpt;
    }

    public void setLeftVpt(Double leftVpt) {
        this.leftVpt = leftVpt;
    }

    public Double getRightVpt() {
        return rightVpt;
    }

    public void setRightVpt(Double rightVpt) {
        this.rightVpt = rightVpt;
    }
}
