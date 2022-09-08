package com.comvee.cdms.checkresult.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Author linr
 * @Date 2022/3/28
 */
public class GetMemberCheckoutDTO {

    @NotBlank(message = "memberId不允许为空")
    private String memberId;
    @NotBlank(message = "hospitalIdId不允许为空")
    private String hospitalId;
    private String startDt;
    private String endDt;
    private String doctorId;
    private String recordOrigin;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getRecordOrigin() {
        return recordOrigin;
    }

    public void setRecordOrigin(String recordOrigin) {
        this.recordOrigin = recordOrigin;
    }
}
