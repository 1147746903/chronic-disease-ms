package com.comvee.cdms.clinicaldiagnosis.dto;

import javax.validation.constraints.NotEmpty;

import java.util.List;

public class ListMemberYzDTO {

    @NotEmpty(message = "memberId不能为空")
    private String memberId;

    private Integer yzType;

    private String yzItemTypeString;

    private String startDt;
    private String endDt;
    private String yzStatusString;
    private String visitNo;

    private List<String> yzItemTypeList;
    private List<String> yzStatusList;
    private List<String> yzItemCodeList;
    private List<String> visitNoList;

    private String yzNameKeyword;


    private List<Integer> recordOriginList;

    private List<String> foreignIdList;

    private Integer useDrugWay;

    private String hospitalId;

    private String yzId;

    private String intoDateHospital; //入院时间


    public List<Integer> getRecordOriginList() {
        return recordOriginList;
    }

    public void setRecordOriginList(List<Integer> recordOriginList) {
        this.recordOriginList = recordOriginList;
    }

    public List<String> getForeignIdList() {
        return foreignIdList;
    }

    public void setForeignIdList(List<String> foreignIdList) {
        this.foreignIdList = foreignIdList;
    }

    public List<String> getYzItemCodeList() {
        return yzItemCodeList;
    }

    public void setYzItemCodeList(List<String> yzItemCodeList) {
        this.yzItemCodeList = yzItemCodeList;
    }

    public String getYzNameKeyword() {
        return yzNameKeyword;
    }

    public void setYzNameKeyword(String yzNameKeyword) {
        this.yzNameKeyword = yzNameKeyword;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getYzType() {
        return yzType;
    }

    public void setYzType(Integer yzType) {
        this.yzType = yzType;
    }

    public String getYzItemTypeString() {
        return yzItemTypeString;
    }

    public void setYzItemTypeString(String yzItemTypeString) {
        this.yzItemTypeString = yzItemTypeString;
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

    public String getYzStatusString() {
        return yzStatusString;
    }

    public void setYzStatusString(String yzStatusString) {
        this.yzStatusString = yzStatusString;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public List<String> getYzItemTypeList() {
        return yzItemTypeList;
    }

    public void setYzItemTypeList(List<String> yzItemTypeList) {
        this.yzItemTypeList = yzItemTypeList;
    }

    public List<String> getYzStatusList() {
        return yzStatusList;
    }

    public void setYzStatusList(List<String> yzStatusList) {
        this.yzStatusList = yzStatusList;
    }

    public List<String> getVisitNoList() {
        return visitNoList;
    }

    public void setVisitNoList(List<String> visitNoList) {
        this.visitNoList = visitNoList;
    }

    public Integer getUseDrugWay() {
        return useDrugWay;
    }

    public void setUseDrugWay(Integer useDrugWay) {
        this.useDrugWay = useDrugWay;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getYzId() {
        return yzId;
    }

    public void setYzId(String yzId) {
        this.yzId = yzId;
    }


    public String getIntoDateHospital() {
        return intoDateHospital;
    }

    public void setIntoDateHospital(String intoDateHospital) {
        this.intoDateHospital = intoDateHospital;
    }

}
