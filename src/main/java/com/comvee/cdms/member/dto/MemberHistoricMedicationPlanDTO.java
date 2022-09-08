package com.comvee.cdms.member.dto;

import java.io.Serializable;

public class MemberHistoricMedicationPlanDTO implements Serializable {
    private String drugJson;
    private String id;
    private String memberId;
    private String doctorId;
    private String doctorName;

    public String getDrugJson() {
        return drugJson;
    }

    public void setDrugJson(String drugJson) {
        this.drugJson = drugJson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
