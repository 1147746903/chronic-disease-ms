package com.comvee.cdms.dialogue.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/3
 */
public class ListDialogueLatestMapperDTO {

    private String memberId;
    private String doctorId;
    private Integer beDelete;
    private Long doctorRefreshTime;
    private Long memberRefreshTime;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getBeDelete() {
        return beDelete;
    }

    public void setBeDelete(Integer beDelete) {
        this.beDelete = beDelete;
    }

    public Long getDoctorRefreshTime() {
        return doctorRefreshTime;
    }

    public void setDoctorRefreshTime(Long doctorRefreshTime) {
        this.doctorRefreshTime = doctorRefreshTime;
    }

    public Long getMemberRefreshTime() {
        return memberRefreshTime;
    }

    public void setMemberRefreshTime(Long memberRefreshTime) {
        this.memberRefreshTime = memberRefreshTime;
    }
}
