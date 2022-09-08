package com.comvee.cdms.complication.model.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/2/27
 */
public class CountScreeningDTO {

    private Integer screeningStatus;
    private Integer orderStatus;
    private String orderStartDt;
    private String orderEndDt;
    private String finishStartDt;
    private String finishEndDt;

    private String orderStartOrDt;
    private String orderEndOrDt;
    private String finishStartOrDt;
    private String finishEndOrDt;

    private List<Integer> screeningStatusList;

    private List<String> doctorList;
    private List<String> memberIdList;
    private String hospitalId;


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

    public List<Integer> getScreeningStatusList() {
        return screeningStatusList;
    }

    public void setScreeningStatusList(List<Integer> screeningStatusList) {
        this.screeningStatusList = screeningStatusList;
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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
