package com.comvee.cdms.dialogue.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/25
 */
public class DoctorDialogueLatestSearchDTO {

    private Long refreshTimeStamp;
    private Integer beDelete;
    private String keyword;
    private String doctorId;
    private List<String> doctorList;

    public List<String> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<String> doctorList) {
        this.doctorList = doctorList;
    }

    public Long getRefreshTimeStamp() {
        return refreshTimeStamp;
    }

    public void setRefreshTimeStamp(Long refreshTimeStamp) {
        this.refreshTimeStamp = refreshTimeStamp;
    }

    public Integer getBeDelete() {
        return beDelete;
    }

    public void setBeDelete(Integer beDelete) {
        this.beDelete = beDelete;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toString() {
        return "DoctorDialogueLatestSearchDTO{" +
                "refreshTimeStamp=" + refreshTimeStamp +
                ", beDelete=" + beDelete +
                ", keyword='" + keyword + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", doctorList=" + doctorList +
                '}';
    }
}
