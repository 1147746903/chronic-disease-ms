package com.comvee.cdms.complication.model.po;

/**
 * @author Su
 */
public class ScreeningReportPO {
    private String reportId;

    private String screeningId;

    private Integer screeningType;

    private String pdfUrl;

    private String imageUrl;

    private String cutImageUrl;

    private String reportJson;

    private Integer editStatus;

    private String nursingAdvice;

    private String dataJson;

    private Integer printTime;

    private String insertDt;

    private String updateDt;

    private String reportDt;

    private String abnormalSign;

    private String abnormalLevel;

    private String abnormalMsg;

    private String memberId;

    private String doctorId;

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

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId == null ? null : reportId.trim();
    }

    public String getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId == null ? null : screeningId.trim();
    }

    public Integer getScreeningType() {
        return screeningType;
    }

    public void setScreeningType(Integer screeningType) {
        this.screeningType = screeningType;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl == null ? null : pdfUrl.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getCutImageUrl() {
        return cutImageUrl;
    }

    public void setCutImageUrl(String cutImageUrl) {
        this.cutImageUrl = cutImageUrl == null ? null : cutImageUrl.trim();
    }

    public String getReportJson() {
        return reportJson;
    }

    public void setReportJson(String reportJson) {
        this.reportJson = reportJson == null ? null : reportJson.trim();
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public String getNursingAdvice() {
        return nursingAdvice;
    }

    public void setNursingAdvice(String nursingAdvice) {
        this.nursingAdvice = nursingAdvice == null ? null : nursingAdvice.trim();
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson == null ? null : dataJson.trim();
    }

    public Integer getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Integer printTime) {
        this.printTime = printTime;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt == null ? null : updateDt.trim();
    }

    public String getReportDt() {
        return reportDt;
    }

    public void setReportDt(String reportDt) {
        this.reportDt = reportDt == null ? null : reportDt.trim();
    }

    public String getAbnormalSign() {
        return abnormalSign;
    }

    public void setAbnormalSign(String abnormalSign) {
        this.abnormalSign = abnormalSign == null ? null : abnormalSign.trim();
    }

    public String getAbnormalLevel() {
        return abnormalLevel;
    }

    public void setAbnormalLevel(String abnormalLevel) {
        this.abnormalLevel = abnormalLevel == null ? null : abnormalLevel.trim();
    }

    public String getAbnormalMsg() {
        return abnormalMsg;
    }

    public void setAbnormalMsg(String abnormalMsg) {
        this.abnormalMsg = abnormalMsg == null ? null : abnormalMsg.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId == null ? null : doctorId.trim();
    }
}