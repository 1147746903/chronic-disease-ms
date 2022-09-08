package com.comvee.cdms.visit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author linr
 * @Date 2022/3/8
 */
public class AddVistiEventDTO {

    /**
     * 患者id
     */
    @NotBlank(message = "memberId不允许为空")
    private String memberId;
    /**
     * 事件类型
     */
    @NotNull(message = "eventType不允许为空")
    private Integer eventType;

    /**
     * 操作人id
     */
    private String operatorId;
    private String operatorName;
    @NotBlank(message = "hospitalName不允许为空")
    private String hospitalName;
    @NotBlank(message = "hospitalId不允许为空")
    private String hospitalId;
    private String referralHospitalName;//转诊医院
    private String departmentName;
    private String reCheckDt;//复查时间 年月日
    private String foreignId;//外键
    private String paramCode; //eventType为2时需要
    private String paramName;//eventType为2,4时需要



    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }


    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getReCheckDt() {
        return reCheckDt;
    }

    public void setReCheckDt(String reCheckDt) {
        this.reCheckDt = reCheckDt;
    }

    public String getReferralHospitalName() {
        return referralHospitalName;
    }

    public void setReferralHospitalName(String referralHospitalName) {
        this.referralHospitalName = referralHospitalName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
