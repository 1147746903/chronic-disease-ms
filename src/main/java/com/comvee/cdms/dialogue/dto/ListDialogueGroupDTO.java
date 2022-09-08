package com.comvee.cdms.dialogue.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/2
 */
public class ListDialogueGroupDTO {

    private Integer sendStatus;
    private List<String> doctorList;

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public List<String> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<String> doctorList) {
        this.doctorList = doctorList;
    }
}
