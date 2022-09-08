package com.comvee.cdms.records.model.dto;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class DrugsRecordDTO {
    private String memberId;
    private String recordMainId;
    private String recordDt;
    private String recordTime;
    private String begin;
    private String end;
    private List<JSONObject> drugsList;
    private List<String> idList;

    public String getRecordMainId() {
        return recordMainId;
    }

    public void setRecordMainId(String recordMainId) {
        this.recordMainId = recordMainId;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public List<JSONObject> getDrugsList() {
        return drugsList;
    }

    public void setDrugsList(List<JSONObject> drugsList) {
        this.drugsList = drugsList;
    }
}
