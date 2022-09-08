package com.comvee.cdms.drugs.dto;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public class ListDrugsMemberDTO {

    private String memberId;
    private String doctorId;
    private String teamId;

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

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
