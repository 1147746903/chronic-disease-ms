package com.comvee.cdms.dybloodsugar.po;

public class DyRememberDietPO {
    private String sid;
    private String memberId; //患者id
    private String operationId; //患者id
    private String paramCode; //餐时(早,中,晚,其他)
    private String paramDt; //用餐时间
    private String paramIngredientJson; //食材(食材库的食材,自定义其他食材)
    private String modifyDt; //更新时间
    private String insertDt; //插入时间
    private Integer isValid; //是否有效 1有效 0无效
    private String recordDt; //记录时间
    private Integer operationType; //数据来源(1:医生端 0:患者端)
    private String picUrl;
    private String content;
    private String isLife;

    public String getIsLife() {
        return isLife;
    }

    public void setIsLife(String isLife) {
        this.isLife = isLife;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    @Override
    public String toString() {
        return "DyRememberDietPO{" +
                "sid='" + sid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", paramCode='" + paramCode + '\'' +
                ", paramDt='" + paramDt + '\'' +
                ", paramIngredientJson='" + paramIngredientJson + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", isValid=" + isValid +
                ", recordDt='" + recordDt + '\'' +
                ", operationType=" + operationType +
                '}';
    }
}
