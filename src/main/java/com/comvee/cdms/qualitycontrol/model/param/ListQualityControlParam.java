package com.comvee.cdms.qualitycontrol.model.param;

public class ListQualityControlParam {

    private String hospitalId;
    private Integer levelCode;
    private String startDt;
    private String endDt;
    private Integer measureResult;
    private String paperBatchNo;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(Integer levelCode) {
        this.levelCode = levelCode;
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

    public Integer getMeasureResult() {
        return measureResult;
    }

    public void setMeasureResult(Integer measureResult) {
        this.measureResult = measureResult;
    }

    public String getPaperBatchNo() {
        return paperBatchNo;
    }

    public void setPaperBatchNo(String paperBatchNo) {
        this.paperBatchNo = paperBatchNo;
    }
}
