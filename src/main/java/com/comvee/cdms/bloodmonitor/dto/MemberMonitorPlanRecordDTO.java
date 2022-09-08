package com.comvee.cdms.bloodmonitor.dto;

public class MemberMonitorPlanRecordDTO {

    private String memberId; //患者id
    private String doctorName; //医生id
    private String doctorId; //医院id
    private String departName; //科室名称
    private String hospitalId; //医院id

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Override
    public String toString() {
        return "MemberMonitorPlanRecordDTO{" +
                "memberId='" + memberId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", departName='" + departName + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                '}';
    }
}
