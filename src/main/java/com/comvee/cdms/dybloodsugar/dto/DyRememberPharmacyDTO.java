package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DyRememberPharmacyDTO implements Serializable {
    @NotBlank(message = "患者id(memberId)，不可为空")
    private String memberId;
    private String operationId; //患者id
    private String pharmacyDt; //开始运动时间
    private String pharmacyDetailsJson; //结束运动时间
    @NotBlank(message = "开始时间（recordDtStart:yyyy-MM-dd），不可为空")
    private String recordDtStart;
    @NotBlank(message = "结束时间（recordDtEnd:yyyy-MM-dd），不可为空")
    private String recordDtEnd;
    private String recordDt;
    private Integer operationType; //来源 1:医生端  0:患者端

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

    public String getPharmacyDt() {
        return pharmacyDt;
    }

    public void setPharmacyDt(String pharmacyDt) {
        this.pharmacyDt = pharmacyDt;
    }

    public String getPharmacyDetailsJson() {
        return pharmacyDetailsJson;
    }

    public void setPharmacyDetailsJson(String pharmacyDetailsJson) {
        this.pharmacyDetailsJson = pharmacyDetailsJson;
    }

    public String getRecordDtStart() {
        return recordDtStart;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
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
        return "v{" +
                "memberId='" + memberId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", pharmacyDt='" + pharmacyDt + '\'' +
                ", pharmacyDetailsJson='" + pharmacyDetailsJson + '\'' +
                ", recordDtStart='" + recordDtStart + '\'' +
                ", recordDtEnd='" + recordDtEnd + '\'' +
                ", operationType=" + operationType +
                '}';
    }
}
