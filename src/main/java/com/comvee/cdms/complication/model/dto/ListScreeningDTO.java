package com.comvee.cdms.complication.model.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/2/22
 */
public class ListScreeningDTO {

    private String idCard;
    private String patientName;
    private String mobilePhone;
    private String applyDt;
    private Integer orderOrigin;
    private String screeningStatus;
    private Integer orderStatus;

    private String orderStartDt;
    private String orderEndDt;
    private String finishStartDt;
    private String finishEndDt;

    private String orderStartOrDt;
    private String orderEndOrDt;
    private String finishStartOrDt;
    private String finishEndOrDt;

    private String memberId;



    private List<String> doctorList;

    private List<Integer> screeningStatusList;
    private List<String> memberIdList;
    private String hospitalId;
    private Integer isIgnore; //1已忽略0未忽略
    private String orderDate; //预约日期

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

    public List<Integer> getScreeningStatusList() {
        return screeningStatusList;
    }

    public void setScreeningStatusList(List<Integer> screeningStatusList) {
        this.screeningStatusList = screeningStatusList;
    }

    public List<String> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<String> doctorList) {
        this.doctorList = doctorList;
    }

    public String getOrderStartOrDt() {
        return orderStartOrDt;
    }

    public void setOrderStartOrDt(String orderStartOrDt) {
        this.orderStartOrDt = orderStartOrDt;
    }

    public String getOrderEndOrDt() {
        return orderEndOrDt;
    }

    public void setOrderEndOrDt(String orderEndOrDt) {
        this.orderEndOrDt = orderEndOrDt;
    }

    public String getFinishStartOrDt() {
        return finishStartOrDt;
    }

    public void setFinishStartOrDt(String finishStartOrDt) {
        this.finishStartOrDt = finishStartOrDt;
    }

    public String getFinishEndOrDt() {
        return finishEndOrDt;
    }

    public void setFinishEndOrDt(String finishEndOrDt) {
        this.finishEndOrDt = finishEndOrDt;
    }

    public String getScreeningStatus() {
        return screeningStatus;
    }

    public void setScreeningStatus(String screeningStatus) {
        this.screeningStatus = screeningStatus;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(String applyDt) {
        this.applyDt = applyDt;
    }

    public Integer getOrderOrigin() {
        return orderOrigin;
    }

    public void setOrderOrigin(Integer orderOrigin) {
        this.orderOrigin = orderOrigin;
    }

    public String getOrderStartDt() {
        return orderStartDt;
    }

    public void setOrderStartDt(String orderStartDt) {
        this.orderStartDt = orderStartDt;
    }

    public String getOrderEndDt() {
        return orderEndDt;
    }

    public void setOrderEndDt(String orderEndDt) {
        this.orderEndDt = orderEndDt;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFinishStartDt() {
        return finishStartDt;
    }

    public void setFinishStartDt(String finishStartDt) {
        this.finishStartDt = finishStartDt;
    }

    public String getFinishEndDt() {
        return finishEndDt;
    }

    public void setFinishEndDt(String finishEndDt) {
        this.finishEndDt = finishEndDt;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public Integer getIsIgnore() {
        return isIgnore;
    }

    public void setIsIgnore(Integer isIgnore) {
        this.isIgnore = isIgnore;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
