package com.comvee.cdms.dybloodsugar.po;

import javax.validation.constraints.NotBlank;

/**
 * @author hbj
 * @description
 * @date 2021/5/10 15:21
 */
public class MemberLifeTypePO {
    private String sid;
    @NotBlank(message = "患者id(memberId)，不可为空")
    private String memberId;
    @NotBlank(message = "记录时间不能为空")
    private String recordDt;
    private String insertDt;
    private String modifyDt;
    private String isValid;
    @NotBlank(message = "记录内容不能为空")
    private String content;
    private String paramDt;
    private String operationId;
    private String operationType;

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getParamDt() {
        return paramDt;
    }

    public void setParamDt(String paramDt) {
        this.paramDt = paramDt;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

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


    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }


    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
