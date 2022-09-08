package com.comvee.cdms.dybloodsugar.dto;

import java.util.List;

public class DyBloodSugarDTO {
    private String sensorNo;
    private String recordTime;
    private String startTime;
    private String endTime;
    private Integer valid;
    private List<String> recordDtList;

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public List<String> getRecordDtList() {
        return recordDtList;
    }

    public void setRecordDtList(List<String> recordDtList) {
        this.recordDtList = recordDtList;
    }
}

