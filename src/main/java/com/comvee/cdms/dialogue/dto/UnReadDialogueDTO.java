package com.comvee.cdms.dialogue.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/5
 */
public class UnReadDialogueDTO {

    private String doctorId;
    private String memberId;
    private List<String> doctorList;

    public List<String> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<String> doctorList) {
        this.doctorList = doctorList;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
