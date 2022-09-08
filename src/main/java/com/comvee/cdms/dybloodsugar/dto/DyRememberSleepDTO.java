package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DyRememberSleepDTO implements Serializable {

    @NotBlank(message = "患者id(memberId)，不可为空")
    private String memberId;
    private String operationId; //患者id
    private String sleepDt; // 睡眠时间
    private String sleepRemark; //睡眠备注信息
    @NotBlank(message = "开始时间（recordDtStart:yyyy-MM-dd），不可为空")
    private String recordDtStart;
    @NotBlank(message = "结束时间（recordDtEnd:yyyy-MM-dd），不可为空")
    private String recordDtEnd;

    private Integer operationType; //来源 1:医生端  0:患者端
    private String recordDt;
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

    public String getSleepDt() {
        return sleepDt;
    }

    public void setSleepDt(String sleepDt) {
        this.sleepDt = sleepDt;
    }

    public String getSleepRemark() {
        return sleepRemark;
    }

    public void setSleepRemark(String sleepRemark) {
        this.sleepRemark = sleepRemark;
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

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    @Override
    public String toString() {
        return "DyRememberSleepDTO{" +
                "memberId='" + memberId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", sleepDt='" + sleepDt + '\'' +
                ", sleepRemark='" + sleepRemark + '\'' +
                ", recordDtStart='" + recordDtStart + '\'' +
                ", recordDtEnd='" + recordDtEnd + '\'' +
                ", operationType=" + operationType +
                ", recordDt='" + recordDt + '\'' +
                '}';
    }
}
