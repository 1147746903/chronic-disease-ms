package com.comvee.cdms.records.model.vo;

import com.comvee.cdms.records.model.po.DrugsRecordPO;

import java.util.List;

public class DrugsRecordVO {
    private String memberId;
    private String recordMainId;
    private String recordDt;
    private String recordTime;
    private Integer operationType;
    private String operationId;
    private Integer origin;
    private String originId;
    private String paramTime;
    private List<DrugsRecordPO> dataList;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRecordMainId() {
        return recordMainId;
    }

    public void setRecordMainId(String recordMainId) {
        this.recordMainId = recordMainId;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public List<DrugsRecordPO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DrugsRecordPO> dataList) {
        this.dataList = dataList;
    }

    public String getParamTime() {
        return paramTime;
    }

    public void setParamTime(String paramTime) {
        this.paramTime = paramTime;
    }
}
