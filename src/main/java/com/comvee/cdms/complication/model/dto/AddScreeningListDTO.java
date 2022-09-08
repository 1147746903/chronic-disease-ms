package com.comvee.cdms.complication.model.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author Su
 */
public class AddScreeningListDTO {

    @NotEmpty
    private String screeningId;

    @NotEmpty
    private String patientName;

    @NotEmpty
    private String idCard;

    private Integer sex;

    private String birthday;

    private String mobilePhone;

    private String diabetesType;

    private String applyDt;

    private String patientExtJson;

    private Integer screeningStatus;

    private Integer orderStatus;

    private String orderDate;

    private String orderTime;

    private Integer orderTimeCode;

    private String hisOrderCode;

    private String hisExtJson;

    private Integer orderOrigin;

    private String machineSn;

    @NotEmpty
    private String modules;

    @NotEmpty
    private String modulesStatus;

    private String finishDt;

    private String memberId;

    private String doctorId;

    private String hospitalId;

    private Integer abiStatus;
    private Integer vptStatus;
    private Integer valid;

    private String stopItemJson;

    public String getStopItemJson() {
        return stopItemJson;
    }

    public void setStopItemJson(String stopItemJson) {
        this.stopItemJson = stopItemJson;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getAbiStatus() {
        return abiStatus;
    }

    public void setAbiStatus(Integer abiStatus) {
        this.abiStatus = abiStatus;
    }

    public Integer getVptStatus() {
        return vptStatus;
    }

    public void setVptStatus(Integer vptStatus) {
        this.vptStatus = vptStatus;
    }

    public String getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(String applyDt) {
        this.applyDt = applyDt;
    }

    public String getPatientExtJson() {
        return patientExtJson;
    }

    public void setPatientExtJson(String patientExtJson) {
        this.patientExtJson = patientExtJson;
    }

    public Integer getScreeningStatus() {
        return screeningStatus;
    }

    public void setScreeningStatus(Integer screeningStatus) {
        this.screeningStatus = screeningStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderTimeCode() {
        return orderTimeCode;
    }

    public void setOrderTimeCode(Integer orderTimeCode) {
        this.orderTimeCode = orderTimeCode;
    }

    public String getHisOrderCode() {
        return hisOrderCode;
    }

    public void setHisOrderCode(String hisOrderCode) {
        this.hisOrderCode = hisOrderCode;
    }

    public String getHisExtJson() {
        return hisExtJson;
    }

    public void setHisExtJson(String hisExtJson) {
        this.hisExtJson = hisExtJson;
    }

    public Integer getOrderOrigin() {
        return orderOrigin;
    }

    public void setOrderOrigin(Integer orderOrigin) {
        this.orderOrigin = orderOrigin;
    }

    public String getMachineSn() {
        return machineSn;
    }

    public void setMachineSn(String machineSn) {
        this.machineSn = machineSn;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    public String getModulesStatus() {
        return modulesStatus;
    }

    public void setModulesStatus(String modulesStatus) {
        this.modulesStatus = modulesStatus;
    }

    public String getFinishDt() {
        return finishDt;
    }

    public void setFinishDt(String finishDt) {
        this.finishDt = finishDt;
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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}