package com.comvee.cdms.dialogue.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author ZhiGe
 * @description
 * @date 2018/1/30 17:40 create
 */
public class ListDialogueMemberDTO {


    @NotEmpty
    private String doctorId;
    private String upTimeStamp;
    private String downTimeStamp;


    public String getUpTimeStamp() {
        return upTimeStamp;
    }

    public void setUpTimeStamp(String upTimeStamp) {
        this.upTimeStamp = upTimeStamp;
    }

    public String getDownTimeStamp() {
        return downTimeStamp;
    }

    public void setDownTimeStamp(String downTimeStamp) {
        this.downTimeStamp = downTimeStamp;
    }


    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }


}
