package com.comvee.cdms.checkresult.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ListCheckoutDetailNearlySixMonthsDTO implements Serializable {
    @NotEmpty(message = "患者编号不可为空")
    private String memberId;

    @NotNull(message = "类型 ：1 重要检验指标 2 异常检验指标，不可为空")
    private Integer type;

    private String hospitalId;
    private String doctorId;
    private Integer inHos;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }
}
