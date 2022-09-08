package com.comvee.cdms.member.dto;

import java.util.List;

public class ListInHospitalOrVirtualWardHasBloodSugarDataDTO {

    private String hospitalId;
    private String startDt;
    private String endDt;
    private List<String> inHospitalDepartIdList;
    private List<String> virtualWardDepartIdList;
    private Boolean loadInHospitalData;
    private Boolean loadVirtualWardData;
    private Integer paramLevel;

    public Integer getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(Integer paramLevel) {
        this.paramLevel = paramLevel;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public List<String> getInHospitalDepartIdList() {
        return inHospitalDepartIdList;
    }

    public void setInHospitalDepartIdList(List<String> inHospitalDepartIdList) {
        this.inHospitalDepartIdList = inHospitalDepartIdList;
    }

    public List<String> getVirtualWardDepartIdList() {
        return virtualWardDepartIdList;
    }

    public void setVirtualWardDepartIdList(List<String> virtualWardDepartIdList) {
        this.virtualWardDepartIdList = virtualWardDepartIdList;
    }

    public Boolean getLoadInHospitalData() {
        return loadInHospitalData;
    }

    public void setLoadInHospitalData(Boolean loadInHospitalData) {
        this.loadInHospitalData = loadInHospitalData;
    }

    public Boolean getLoadVirtualWardData() {
        return loadVirtualWardData;
    }

    public void setLoadVirtualWardData(Boolean loadVirtualWardData) {
        this.loadVirtualWardData = loadVirtualWardData;
    }
}
