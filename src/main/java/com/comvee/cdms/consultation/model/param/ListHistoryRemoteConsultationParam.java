package com.comvee.cdms.consultation.model.param;

public class ListHistoryRemoteConsultationParam {

    private String departId;
    private Integer consultationStatus;
    private String searchFromHospitalName;
    private String searchToHospitalName;
    private String searchMemberName;
    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public Integer getConsultationStatus() {
        return consultationStatus;
    }

    public void setConsultationStatus(Integer consultationStatus) {
        this.consultationStatus = consultationStatus;
    }

    public String getSearchFromHospitalName() {
        return searchFromHospitalName;
    }

    public void setSearchFromHospitalName(String searchFromHospitalName) {
        this.searchFromHospitalName = searchFromHospitalName;
    }

    public String getSearchToHospitalName() {
        return searchToHospitalName;
    }

    public void setSearchToHospitalName(String searchToHospitalName) {
        this.searchToHospitalName = searchToHospitalName;
    }

    public String getSearchMemberName() {
        return searchMemberName;
    }

    public void setSearchMemberName(String searchMemberName) {
        this.searchMemberName = searchMemberName;
    }
}
