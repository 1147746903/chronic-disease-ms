package com.comvee.cdms.doctor.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/6
 */
public class ListDoctorDTO {

    private String doctorName;
    private Integer showInPatient;
    private String keyword;
    private String departmentId;
    private String hospitalId;
    private String hospitalName;
    private String departmentName;
    private String doctorId;
    private String roleName;
    private String eType;  //管理病种 1糖尿病 2高血压

    private List<String> hospitalIdList;

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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

    public Integer getShowInPatient() {
        return showInPatient;
    }

    public void setShowInPatient(Integer showInPatient) {
        this.showInPatient = showInPatient;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getHospitalIdList() {
        return hospitalIdList;
    }

    public void setHospitalIdList(List<String> hospitalIdList) {
        this.hospitalIdList = hospitalIdList;
    }
}
