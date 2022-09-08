package com.comvee.cdms.clinic.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author wyc
 * @date 2019/8/13 10:01
 */
public class ListClinicInspectDTO {

    /**
     * 患者id
     */
    @NotEmpty
    private String memberId;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 医院id
     */
    private String hospitalId;

    /**
     * 住院日期
     */
    private String checkDt;

    public String getCheckDt() {
        return checkDt;
    }

    public void setCheckDt(String checkDt) {
        this.checkDt = checkDt;
    }

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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
