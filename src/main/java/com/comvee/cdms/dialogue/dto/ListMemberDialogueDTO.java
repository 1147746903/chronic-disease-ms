package com.comvee.cdms.dialogue.dto;

/**
 * @author ZhiGe
 * @description
 * @date 2018/1/30 17:40 create
 */
public class ListMemberDialogueDTO {

    private String memberId;
    private String doctorId;
    private String upTimeStamp;
    private String downTimeStamp;
    private Integer beDelete;

    public Integer getBeDelete() {
        return beDelete;
    }

    public void setBeDelete(Integer beDelete) {
        this.beDelete = beDelete;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

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

    @Override
    public String toString() {
        return "ListDialogueServiceDTO{" +
                "memberId='" + memberId + '\'' +
                ", upTimeStamp='" + upTimeStamp + '\'' +
                ", downTimeStamp='" + downTimeStamp + '\'' +
                '}';
    }
}
