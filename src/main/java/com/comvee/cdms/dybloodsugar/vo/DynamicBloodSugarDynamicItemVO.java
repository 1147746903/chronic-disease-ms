package com.comvee.cdms.dybloodsugar.vo;

public class DynamicBloodSugarDynamicItemVO extends DynamicBloodSugarIndexBaseVO {

    private String recordDt;

    /**
     * 血糖平均绝对差
     */
    private Double bloodSugarMeanAbsoluteDeviation;

    //当天开始时间
    private String recordStartTime;
    //当天结束时间
    private String recordEndTime;

    public String getRecordStartTime() {
        return recordStartTime;
    }

    public void setRecordStartTime(String recordStartTime) {
        this.recordStartTime = recordStartTime;
    }

    public String getRecordEndTime() {
        return recordEndTime;
    }

    public void setRecordEndTime(String recordEndTime) {
        this.recordEndTime = recordEndTime;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public Double getBloodSugarMeanAbsoluteDeviation() {
        return bloodSugarMeanAbsoluteDeviation;
    }

    public void setBloodSugarMeanAbsoluteDeviation(Double bloodSugarMeanAbsoluteDeviation) {
        this.bloodSugarMeanAbsoluteDeviation = bloodSugarMeanAbsoluteDeviation;
    }
}
