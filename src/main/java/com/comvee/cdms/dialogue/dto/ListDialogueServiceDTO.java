package com.comvee.cdms.dialogue.dto;

import javax.validation.constraints.NotEmpty;

import java.util.List;

/**
 * @author ZhiGe
 * @description
 * @date 2018/1/30 17:40 create
 */
public class ListDialogueServiceDTO {

    @NotEmpty
    private String memberId;
    private String doctorId;
    private Long upTimeStamp;
    private Long downTimeStamp;
    private Integer beDelete;
    private List<Integer> showClientList;

    public List<Integer> getShowClientList() {
        return showClientList;
    }

    public void setShowClientList(List<Integer> showClientList) {
        this.showClientList = showClientList;
    }

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
