package com.comvee.cdms.member.dto;

import java.util.List;

/**
 * @Author linr
 * @Date 2021/8/6
 */
public class ListMemberBloodStaticsDTO {
    private String departmentId;
    private Integer type;//1住院患者2出院患者3所有患者4科室统计
    private String doctorId;
    private List<String> memberIdList;
    private String hospitalId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
