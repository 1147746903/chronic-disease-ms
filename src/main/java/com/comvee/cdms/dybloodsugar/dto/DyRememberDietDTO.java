package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DyRememberDietDTO implements Serializable {

    @NotBlank(message = "患者id(memberId)，不可为空")
    private String memberId;

    private String operationId;

    private String paramCode;

    private String paramDt;

    private String paramIngredientJson;
    private String RecordDt;

    @NotBlank(message = "开始时间（recordDtStart:yyyy-MM-dd），不可为空")
    private String recordDtStart;
    @NotBlank(message = "结束时间（recordDtEnd:yyyy-MM-dd），不可为空")
    private String recordDtEnd;

    private Integer operationType; //来源 1:医生端  0:患者端

    private String modifyDt;

    private String picUrl;

    private String sid;

    private String isLife;

    public String getIsLife() {
        return isLife;
    }

    public void setIsLife(String isLife) {
        this.isLife = isLife;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public String getRecordDt() {
        return RecordDt;
    }

    public void setRecordDt(String recordDt) {
        RecordDt = recordDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamDt() {
        return paramDt;
    }

    public void setParamDt(String paramDt) {
        this.paramDt = paramDt;
    }

    public String getParamIngredientJson() {
        return paramIngredientJson;
    }

    public void setParamIngredientJson(String paramIngredientJson) {
        this.paramIngredientJson = paramIngredientJson;
    }

    public String getRecordDtStart() {
        return recordDtStart;
    }

    public void setRecordDtStart(String recordDtStart) {
        this.recordDtStart = recordDtStart;
    }

    public String getRecordDtEnd() {
        return recordDtEnd;
    }

    public void setRecordDtEnd(String recordDtEnd) {
        this.recordDtEnd = recordDtEnd;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "DyRememberDietDTO{" +
                "memberId='" + memberId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", paramCode='" + paramCode + '\'' +
                ", paramDt='" + paramDt + '\'' +
                ", paramIngredientJson='" + paramIngredientJson + '\'' +
                ", RecordDt='" + RecordDt + '\'' +
                ", recordDtStart='" + recordDtStart + '\'' +
                ", recordDtEnd='" + recordDtEnd + '\'' +
                ", operationType=" + operationType +
                ", modifyDt='" + modifyDt + '\'' +
                '}';
    }
}
