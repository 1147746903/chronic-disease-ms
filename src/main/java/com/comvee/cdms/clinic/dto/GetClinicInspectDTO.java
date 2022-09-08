package com.comvee.cdms.clinic.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author wyc
 * @date 2019/8/12 14:33
 */
public class GetClinicInspectDTO {
    /**
     * 患者id
     */
    @NotEmpty
    private String memberId;

    /**
     * 医生id
     */
    @NotEmpty
    private String doctorId;

    /**
     * 医院id
     */
    private String hospitalId;

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
