package com.comvee.cdms.doctor.vo;

import java.util.List;

public class DoctorTeamWithGroupsVO {

    private String doctorId;
    private String doctorName;
    private List<DoctorGroupVO> groupList;

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

    public List<DoctorGroupVO> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<DoctorGroupVO> groupList) {
        this.groupList = groupList;
    }
}
