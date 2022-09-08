package com.comvee.cdms.complication.model.vo;

import java.io.Serializable;

public class ScreeningListVO implements Serializable {
    private String screeningId;

    private String patientName;

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

    private String insertDt;

    private String updateDt;

    private String modules;

    private String modulesStatus;

    private String finishDt;

    private String memberId;

    private String doctorId;

    private Integer abiStatus;
    private Integer vptStatus;
    private Integer valid;

    private String stopItemJson;

    private String bedNo;
    private String visitNo;
    private String height;
    private String weight;
    private Integer inHos;
    private String departmentId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

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
        this.screeningId = screeningId == null ? null : screeningId.trim();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
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
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType == null ? null : diabetesType.trim();
    }

    public String getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(String applyDt) {
        this.applyDt = applyDt == null ? null : applyDt.trim();
    }

    public String getPatientExtJson() {
        return patientExtJson;
    }

    public void setPatientExtJson(String patientExtJson) {
        this.patientExtJson = patientExtJson == null ? null : patientExtJson.trim();
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
        this.orderDate = orderDate == null ? null : orderDate.trim();
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime == null ? null : orderTime.trim();
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
        this.hisOrderCode = hisOrderCode == null ? null : hisOrderCode.trim();
    }

    public String getHisExtJson() {
        return hisExtJson;
    }

    public void setHisExtJson(String hisExtJson) {
        this.hisExtJson = hisExtJson == null ? null : hisExtJson.trim();
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
        this.machineSn = machineSn == null ? null : machineSn.trim();
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt == null ? null : updateDt.trim();
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules == null ? null : modules.trim();
    }

    public String getModulesStatus() {
        return modulesStatus;
    }

    public void setModulesStatus(String modulesStatus) {
        this.modulesStatus = modulesStatus == null ? null : modulesStatus.trim();
    }

    public String getFinishDt() {
        return finishDt;
    }

    public void setFinishDt(String finishDt) {
        this.finishDt = finishDt == null ? null : finishDt.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId == null ? null : doctorId.trim();
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
