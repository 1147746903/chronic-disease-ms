package com.comvee.cdms.complication.model.bo;

/**
 * @author: suyz
 * @date: 2019/2/22
 */
public class ScreeningReportSyncBO {

    private String reportId;
    private String screeningId;
    private Integer screeningType;
    private String reportJson;
    private Integer editStatus;
    private String dataJson;
    private String nursingAdvice;
    private Integer printTime;
    private String reportDt;
    private Integer reportSync;
    private String lastSyncDt;
    private String abnormalSign;
    private String abnormalLevel;
    private String abnormalMsg;
    private String idCard;
    private Integer resultStatus;
    private Integer valid;

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
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

    public String getReportJson() {
        return reportJson;
    }

    public void setReportJson(String reportJson) {
        this.reportJson = reportJson;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public String getNursingAdvice() {
        return nursingAdvice;
    }

    public void setNursingAdvice(String nursingAdvice) {
        this.nursingAdvice = nursingAdvice;
    }

    public Integer getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Integer printTime) {
        this.printTime = printTime;
    }

    public String getReportDt() {
        return reportDt;
    }

    public void setReportDt(String reportDt) {
        this.reportDt = reportDt;
    }

    public Integer getReportSync() {
        return reportSync;
    }

    public void setReportSync(Integer reportSync) {
        this.reportSync = reportSync;
    }

    public String getLastSyncDt() {
        return lastSyncDt;
    }

    public void setLastSyncDt(String lastSyncDt) {
        this.lastSyncDt = lastSyncDt;
    }

    public String getAbnormalSign() {
        return abnormalSign;
    }

    public void setAbnormalSign(String abnormalSign) {
        this.abnormalSign = abnormalSign;
    }

    public String getAbnormalLevel() {
        return abnormalLevel;
    }

    public void setAbnormalLevel(String abnormalLevel) {
        this.abnormalLevel = abnormalLevel;
    }

    public String getAbnormalMsg() {
        return abnormalMsg;
    }

    public void setAbnormalMsg(String abnormalMsg) {
        this.abnormalMsg = abnormalMsg;
    }
}
