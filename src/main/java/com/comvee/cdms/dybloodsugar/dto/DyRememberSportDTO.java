package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DyRememberSportDTO implements Serializable {
    @NotBlank(message = "患者id(memberId)，不可为空")
    private String memberId;

    private String operationId;
    @NotBlank(message = "开始时间（recordDtStart:yyyy-MM-dd），不可为空")
    private String recordDtStart;
    @NotBlank(message = "结束时间（recordDtEnd:yyyy-MM-dd），不可为空")
    private String recordDtEnd;
    private String recordDt;
    private String sportStartDt; //开始运动时间
    private String sportEndDt; //结束运动时间
    private String sportMethodJson; //运动方式
    private Integer operationType; //来源 1:医生端  0:患者端

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
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

    public String getSportStartDt() {
        return sportStartDt;
    }

    public void setSportStartDt(String sportStartDt) {
        this.sportStartDt = sportStartDt;
    }

    public String getSportEndDt() {
        return sportEndDt;
    }

    public void setSportEndDt(String sportEndDt) {
        this.sportEndDt = sportEndDt;
    }

    public String getSportMethodJson() {
        return sportMethodJson;
    }

    public void setSportMethodJson(String sportMethodJson) {
        this.sportMethodJson = sportMethodJson;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "DyRememberSportDTO{" +
                "memberId='" + memberId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", recordDtStart='" + recordDtStart + '\'' +
                ", recordDtEnd='" + recordDtEnd + '\'' +
                ", sportStartDt='" + sportStartDt + '\'' +
                ", sportEndDt='" + sportEndDt + '\'' +
                ", sportMethodJson='" + sportMethodJson + '\'' +
                ", operationType=" + operationType +
                '}';
    }
}
