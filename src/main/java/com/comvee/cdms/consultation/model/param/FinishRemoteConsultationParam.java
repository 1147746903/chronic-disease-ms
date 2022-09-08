package com.comvee.cdms.consultation.model.param;

import javax.validation.constraints.NotEmpty;

public class FinishRemoteConsultationParam {

    @NotEmpty(message = "consultationId 不能为空")
    private String consultationId;
    @NotEmpty(message = "consultationResult 不能为空")
    private String consultationResult;
    private String doctorId;

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public String getConsultationResult() {
        return consultationResult;
    }

    public void setConsultationResult(String consultationResult) {
        this.consultationResult = consultationResult;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
