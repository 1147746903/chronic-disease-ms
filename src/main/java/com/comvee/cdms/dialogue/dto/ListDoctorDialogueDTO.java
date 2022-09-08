package com.comvee.cdms.dialogue.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author ZhiGe
 * @description
 * @date 2018/1/30 17:40 create
 */
public class ListDoctorDialogueDTO {

    @NotEmpty
    private String memberId;
    private Long upTimeStamp;
    private Long downTimeStamp;
    @NotEmpty
    private String doctorId;

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

    public Long getUpTimeStamp() {
        return upTimeStamp;
    }

    public void setUpTimeStamp(Long upTimeStamp) {
        this.upTimeStamp = upTimeStamp;
    }

    public Long getDownTimeStamp() {
        return downTimeStamp;
    }

    public void setDownTimeStamp(Long downTimeStamp) {
        this.downTimeStamp = downTimeStamp;
    }

    @Override
    public String toString() {
        return "ListDialogueServiceDTO{" +
                "memberId='" + memberId + '\'' +
                ", upTimeStamp='" + upTimeStamp + '\'' +
                ", downTimeStamp='" + downTimeStamp + '\'' +
                '}';
    }
}
