package com.comvee.cdms.dybloodpressure.po;

import java.io.Serializable;

/**
 * 动态血压报告(DyBloodPressureReportPO)实体类
 *
 * @author makejava
 * @since 2021-11-08 16:44:47
 */
public class DyBloodPressureReportPO implements Serializable {
    private static final Long serialVersionUID = -89866447172095213L;
    /**
     * 主键id
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 操作者id
     */
    private String operationId;
    /**
     * 详细报告
     */
    private String details;
    /**
     * 送检医生
     */
    private String sender;
    /**
     * 送检签名日期
     */
    private String senderSignDt;
    /**
     * 报告医生
     */
    private String reporter;
    /**
     * 送检签名日期
     */
    private String reportSignDt;
    /**
     * 起始时间
     */
    private String startDt;
    /**
     * 结束时间
     */
    private String endDt;
    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 是否有效 1有效,0无效
     */
    private Integer isValid;



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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderSignDt() {
        return senderSignDt;
    }

    public void setSenderSignDt(String senderSignDt) {
        this.senderSignDt = senderSignDt;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReportSignDt() {
        return reportSignDt;
    }

    public void setReportSignDt(String reportSignDt) {
        this.reportSignDt = reportSignDt;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
