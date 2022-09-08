package com.comvee.cdms.checkresult.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 检验列表参数
 */
public class ListCheckResultDTO implements Serializable {

    @NotEmpty(message = "患者编号不可为空")
    private String memberId;
    @NotEmpty(message = "检验代码不可为空")
    private String checkoutCode;
    @NotEmpty(message = "检验名称不可为空")
    private String checkoutName;

    private String startDt;
    private String endDt;
    @NotEmpty(message = "医院编号不可为空")
    private String hospitalId;
    private String doctorId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCheckoutCode() {
        return checkoutCode;
    }

    public void setCheckoutCode(String checkoutCode) {
        this.checkoutCode = checkoutCode;
    }

    public String getCheckoutName() {
        return checkoutName;
    }

    public void setCheckoutName(String checkoutName) {
        this.checkoutName = checkoutName;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
