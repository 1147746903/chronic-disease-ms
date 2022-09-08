package com.comvee.cdms.consultation.model.param;

import java.util.List;

public class ListRemoteConsultationParam {

    private String toDepartId;
    private String fromDepartId;
    private String orDepartId;
    private List<Integer> consultationStatusList;
    private String searchMemberName;
    private String searchFromHospitalName;
    private String searchToHospitalName;
    private List<String> memberIdList;

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public String getToDepartId() {
        return toDepartId;
    }

    public void setToDepartId(String toDepartId) {
        this.toDepartId = toDepartId;
    }

    public String getFromDepartId() {
        return fromDepartId;
    }

    public void setFromDepartId(String fromDepartId) {
        this.fromDepartId = fromDepartId;
    }

    public String getOrDepartId() {
        return orDepartId;
    }

    public void setOrDepartId(String orDepartId) {
        this.orDepartId = orDepartId;
    }

    public List<Integer> getConsultationStatusList() {
        return consultationStatusList;
    }

    public void setConsultationStatusList(List<Integer> consultationStatusList) {
        this.consultationStatusList = consultationStatusList;
    }

    public String getSearchMemberName() {
        return searchMemberName;
    }

    public void setSearchMemberName(String searchMemberName) {
        this.searchMemberName = searchMemberName;
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
}
