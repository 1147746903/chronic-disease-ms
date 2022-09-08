package com.comvee.cdms.complication.model.dto;

public class GetLastScreeningReportDTO {

    private String memberId;
    private String doctorId;
    private Integer screeningType;
    private String unScreeningId;
    private Integer editStatus;
    private Integer dealStatus;

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

    public Integer getScreeningType() {
        return screeningType;
    }

    public void setScreeningType(Integer screeningType) {
        this.screeningType = screeningType;
    }

    public String getUnScreeningId() {
        return unScreeningId;
    }

    public void setUnScreeningId(String unScreeningId) {
        this.unScreeningId = unScreeningId;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }
}
