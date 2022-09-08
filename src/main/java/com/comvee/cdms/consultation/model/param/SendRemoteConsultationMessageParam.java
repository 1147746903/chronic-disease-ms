package com.comvee.cdms.consultation.model.param;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class SendRemoteConsultationMessageParam {

    @NotEmpty(message = "consultationId 不能为空")
    private String consultationId;
    private String doctorId;
    private String departId;
    private String hospitalId;
    @NotNull(message = "contentType 不能为空")
    private Integer contentType;
    private String text;

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
