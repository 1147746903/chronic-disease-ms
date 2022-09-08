package com.comvee.cdms.dybloodsugar.po;

public class DyRememberSleepPO {
    private String sid;
    private String memberId; //患者id
    private String operationId; //患者id
    private String sleepDt; // 睡眠时间
    private String sleepRemark; //睡眠备注信息
    private String modifyDt; //更新时间
    private String insertDt; //插入时间
    private String recordDt; //记录时间
    private Integer isValid; //是否有效 1有效 0无效
    private Integer operationType; //数据来源(1:医生端 0:患者端)

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "DyRememberSleepPO{" +
                "sid='" + sid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", sleepDt='" + sleepDt + '\'' +
                ", sleepRemark='" + sleepRemark + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", recordDt='" + recordDt + '\'' +
                ", isValid=" + isValid +
                ", operationType=" + operationType +
                '}';
    }
}
