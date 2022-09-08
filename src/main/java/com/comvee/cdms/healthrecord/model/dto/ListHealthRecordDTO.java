package com.comvee.cdms.healthrecord.model.dto;

import java.util.List;

public class ListHealthRecordDTO {

    private String hospitalId;
    private String keyword;
    private String checkupDateStart;
    private String checkupDateEnd;
    private Integer visitType;
    private Integer jwSyncStatus;
    private List<Integer> jwSyncStatusList;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCheckupDateStart() {
        return checkupDateStart;
    }

    public void setCheckupDateStart(String checkupDateStart) {
        this.checkupDateStart = checkupDateStart;
    }

    public String getCheckupDateEnd() {
        return checkupDateEnd;
    }

    public void setCheckupDateEnd(String checkupDateEnd) {
        this.checkupDateEnd = checkupDateEnd;
    }

    public Integer getVisitType() {
        return visitType;
    }

    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    public Integer getJwSyncStatus() {
        return jwSyncStatus;
    }

    public void setJwSyncStatus(Integer jwSyncStatus) {
        this.jwSyncStatus = jwSyncStatus;
    }

    public List<Integer> getJwSyncStatusList() {
        return jwSyncStatusList;
    }

    public void setJwSyncStatusList(List<Integer> jwSyncStatusList) {
        this.jwSyncStatusList = jwSyncStatusList;
    }
}
