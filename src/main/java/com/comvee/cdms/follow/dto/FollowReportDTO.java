/*
*
* @author wyc
* @date 2019-08-16
*/
package com.comvee.cdms.follow.dto;


public class FollowReportDTO {
    /**
     * 主键id
     */
    private String sid;

    /**
     * 随访详情表id
     */
    private String followId;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 报告类型 1 首诊报告 2 2型糖尿病随访报告
     */
    private Integer reportType;

    /**
     * 插入时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String updateDt;

    /**
     * 是否有效 0 否 1 是
     */
    private Integer isValid;

    /**
     * 报告内容
     */
    private String reportJson;

    /**
     *患者近一周血糖监测(华西)
     */
    private String weekBloodData;

    /**
     * 第几次随访总结报告(华西)
     */
    private Integer reportNum;

    private String doctorName;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getReportJson() {
        return reportJson;
    }

    public void setReportJson(String reportJson) {
        this.reportJson = reportJson;
    }

    public String getWeekBloodData() {
        return weekBloodData;
    }

    public void setWeekBloodData(String weekBloodData) {
        this.weekBloodData = weekBloodData;
    }

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}