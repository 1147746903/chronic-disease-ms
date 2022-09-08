package com.comvee.cdms.sign.dto;

import java.util.List;

/**
 * @author wyc
 * @date 2019/4/17 20:18
 */
public class ListMemberBloodNumDTO {
    private String startDt;
    private String endDt;
    private List<String> memberList;
    private String hospitalName;
    private String departmentName;
    private String doctorName;
    private String memberId;
    private List<String> doctorList;

    public List<String> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<String> doctorList) {
        this.doctorList = doctorList;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public List<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }
}
