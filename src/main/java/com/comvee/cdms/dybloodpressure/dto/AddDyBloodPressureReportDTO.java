package com.comvee.cdms.dybloodpressure.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Author linr
 * @Date 2021/11/8
 */
public class AddDyBloodPressureReportDTO {
    private String sid;
    @NotBlank(message = "患者编号不允许为空")
    private String memberId;
    private String operationId;
    private String details;//报告详细
    private String sender;//送检医生
    private String senderSignDt;//送检签名日期
    private String reporter;//报告医生
    private String reportSignDt;//报告签名日期
    @NotBlank(message = "startDt不允许为空")
    private String startDt;
    @NotBlank(message = "endDt不允许为空")
    private String endDt;


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
}
