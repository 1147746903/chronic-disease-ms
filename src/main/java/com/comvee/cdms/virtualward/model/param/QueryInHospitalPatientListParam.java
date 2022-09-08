package com.comvee.cdms.virtualward.model.param;

import javax.validation.constraints.NotEmpty;

public class QueryInHospitalPatientListParam {

    private String keyword;
    @NotEmpty(message = "科室id不能为空")
    private String departmentId;
    private Integer applyStatus;
    private String hospitalId;
    private String doctorName; //登陆医生的名称
    private String doctorId; //登陆医生的id
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toString() {
        return "QueryInHospitalPatientListParam{" +
                "keyword='" + keyword + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", applyStatus=" + applyStatus +
                ", hospitalId='" + hospitalId + '\'' +
                '}';
    }
}
