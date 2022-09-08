package com.comvee.cdms.sign.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_blood_sugar
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class AddBloodSugarServiceDTO {

    private String sid;

    /**
     * 血糖code
     * param_code
     */
    @NotEmpty(message = "paramCode is not null")
    private String paramCode;

    /**
     * 血糖值
     * param_value
     */
    @NotEmpty(message = "paramValue is not null")
    private String paramValue;

    /**
     * 记录时间
     * record_dt
     */
    @NotEmpty(message = "recordDt is not null")
    private String recordDt;

    /**
     * 备注
     * remark
     */
    private String remark;

    /**
     * 来源
     * origin
     */
    @NotNull(message = "origin is not null")
    private Integer origin;

    /**
     * member_id
     */
    @NotEmpty(message = "memberId is not null")
    private String memberId;

    /**
     * 操作者类型 1 用户 2 医护
     * operator_type
     */
    @NotNull(message = "operatorType is not null")
    private Integer operatorType;

    /**
     * operator_id
     */
    private String operatorId;

    @NotNull(message = "inHos is not null")
    private Integer inHos;

    /**
     * 控制目标低值
     */
    private String rangeMin;
    /**
     * 控制目标高值
     */
    private String rangeMax;

    private String HospitalId;

    /**
     * 应用id
     */
    private String appId;
    private String hospitalNo;
    private String bedNo;
    private String departmentId;
    /**
     * 血糖记录类型 1监测 2LOW 3HIGH 4拒测 5外出 6请假 7已进餐 8未进餐
     */
    private Integer recordType;

    private String strongCode; //强化时段code



    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRangeMin() {
        return rangeMin;
    }

    public void setRangeMin(String rangeMin) {
        this.rangeMin = rangeMin;
    }

    public String getRangeMax() {
        return rangeMax;
    }

    public void setRangeMax(String rangeMax) {
        this.rangeMax = rangeMax;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public String getHospitalId() {
        return HospitalId;
    }

    public void setHospitalId(String hospitalId) {
        HospitalId = hospitalId;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public String getStrongCode() {
        return strongCode;
    }

    public void setStrongCode(String strongCode) {
        this.strongCode = strongCode;
    }
}