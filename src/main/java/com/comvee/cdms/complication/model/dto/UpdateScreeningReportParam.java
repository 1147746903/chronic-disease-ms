package com.comvee.cdms.complication.model.dto;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
public class UpdateScreeningReportParam {

    private String doctorMsg;
    private String nursingAdvice;
    private String screeningId;
    private Integer screeningType;
    private Integer incrPrintTime;
    private Integer editStatus;
    private String lastSyncDt;
    private Integer reportSync;
    private String reportId;
    private String reportJson;
    private String compareAnalysis;
    private String checkTips;

    public String getCompareAnalysis() {
        return compareAnalysis;
    }

    public void setCompareAnalysis(String compareAnalysis) {
        this.compareAnalysis = compareAnalysis;
    }

    public String getCheckTips() {
        return checkTips;
    }

    public void setCheckTips(String checkTips) {
        this.checkTips = checkTips;
    }

    public String getReportJson() {
        return reportJson;
    }

    public void setReportJson(String reportJson) {
        this.reportJson = reportJson;
    }

    public String getDoctorMsg() {
        return doctorMsg;
    }

    public void setDoctorMsg(String doctorMsg) {
        this.doctorMsg = doctorMsg;
    }

    public String getNursingAdvice() {
        return nursingAdvice;
    }

    public void setNursingAdvice(String nursingAdvice) {
        this.nursingAdvice = nursingAdvice;
    }

    public String getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }

    public Integer getScreeningType() {
        return screeningType;
    }

    public void setScreeningType(Integer screeningType) {
        this.screeningType = screeningType;
    }

    public Integer getIncrPrintTime() {
        return incrPrintTime;
    }

    public void setIncrPrintTime(Integer incrPrintTime) {
        this.incrPrintTime = incrPrintTime;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public String getLastSyncDt() {
        return lastSyncDt;
    }

    public void setLastSyncDt(String lastSyncDt) {
        this.lastSyncDt = lastSyncDt;
    }

    public Integer getReportSync() {
        return reportSync;
    }

    public void setReportSync(Integer reportSync) {
        this.reportSync = reportSync;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}
