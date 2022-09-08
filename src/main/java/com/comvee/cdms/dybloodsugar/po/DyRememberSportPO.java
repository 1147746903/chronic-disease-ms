package com.comvee.cdms.dybloodsugar.po;

public class DyRememberSportPO {
    private String sid;
    private String memberId; //患者id
    private String operationId; //患者id
    private String sportStartDt; //开始运动时间
    private String sportEndDt; //结束运动时间
    private String sportMethodJson; //运动方式
    private String modifyDt; //更新时间
    private String recordDt; //记录时间
    private String insertDt; //插入时间
    private Integer isValid; //是否有效 1有效 0无效
    private Integer operationType; //数据来源(1:医生端 0:患者端)

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
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

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "DyRememberSportPO{" +
                "sid='" + sid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", sportStartDt='" + sportStartDt + '\'' +
                ", sportEndDt='" + sportEndDt + '\'' +
                ", sportMethodJson='" + sportMethodJson + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", recordDt='" + recordDt + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", isValid=" + isValid +
                ", operationType=" + operationType +
                '}';
    }
}
