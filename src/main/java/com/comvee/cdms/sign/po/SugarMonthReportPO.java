package com.comvee.cdms.sign.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_sugar_month_report
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class SugarMonthReportPO {
    /**
     * report_id
     */
    private String reportId;

    /**
     * member_id
     */
    private String memberId;

    /**
     * 糖尿病类型
     * diabetes_type
     */
    private String diabetesType;

    /**
     * 控制目标数据
     * range_data
     */
    private String rangeData;

    /**
     * 糖化数据
     * hba1c_data
     */
    private String hba1cData;

    /**
     * 血糖数据
     * blood_sugar_data
     */
    private String bloodSugarData;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * valid
     */
    private Integer valid;

    /**
     * 报告状态 1 未生成完整报告 2 已生成
     * report_status
     */
    private Integer reportStatus;

    /**
     * 推送状态 1 已推送 0 未推送
     * push_status
     */
    private Integer pushStatus;

    private String reportDate;

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.report_id
     *
     * @return the value of t_sugar_month_report.report_id
     *
     * @mbggenerated
     */
    public String getReportId() {
        return reportId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.report_id
     *
     * @param reportId the value for t_sugar_month_report.report_id
     *
     * @mbggenerated
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.member_id
     *
     * @return the value of t_sugar_month_report.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.member_id
     *
     * @param memberId the value for t_sugar_month_report.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.diabetes_type
     *
     * @return the value of t_sugar_month_report.diabetes_type
     *
     * @mbggenerated
     */
    public String getDiabetesType() {
        return diabetesType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.diabetes_type
     *
     * @param diabetesType the value for t_sugar_month_report.diabetes_type
     *
     * @mbggenerated
     */
    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.range_data
     *
     * @return the value of t_sugar_month_report.range_data
     *
     * @mbggenerated
     */
    public String getRangeData() {
        return rangeData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.range_data
     *
     * @param rangeData the value for t_sugar_month_report.range_data
     *
     * @mbggenerated
     */
    public void setRangeData(String rangeData) {
        this.rangeData = rangeData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.hba1c_data
     *
     * @return the value of t_sugar_month_report.hba1c_data
     *
     * @mbggenerated
     */
    public String getHba1cData() {
        return hba1cData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.hba1c_data
     *
     * @param hba1cData the value for t_sugar_month_report.hba1c_data
     *
     * @mbggenerated
     */
    public void setHba1cData(String hba1cData) {
        this.hba1cData = hba1cData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.blood_sugar_data
     *
     * @return the value of t_sugar_month_report.blood_sugar_data
     *
     * @mbggenerated
     */
    public String getBloodSugarData() {
        return bloodSugarData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.blood_sugar_data
     *
     * @param bloodSugarData the value for t_sugar_month_report.blood_sugar_data
     *
     * @mbggenerated
     */
    public void setBloodSugarData(String bloodSugarData) {
        this.bloodSugarData = bloodSugarData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.insert_dt
     *
     * @return the value of t_sugar_month_report.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.insert_dt
     *
     * @param insertDt the value for t_sugar_month_report.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.update_dt
     *
     * @return the value of t_sugar_month_report.update_dt
     *
     * @mbggenerated
     */
    public String getUpdateDt() {
        return updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.update_dt
     *
     * @param updateDt the value for t_sugar_month_report.update_dt
     *
     * @mbggenerated
     */
    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.valid
     *
     * @return the value of t_sugar_month_report.valid
     *
     * @mbggenerated
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.valid
     *
     * @param valid the value for t_sugar_month_report.valid
     *
     * @mbggenerated
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.report_status
     *
     * @return the value of t_sugar_month_report.report_status
     *
     * @mbggenerated
     */
    public Integer getReportStatus() {
        return reportStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.report_status
     *
     * @param reportStatus the value for t_sugar_month_report.report_status
     *
     * @mbggenerated
     */
    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sugar_month_report.push_status
     *
     * @return the value of t_sugar_month_report.push_status
     *
     * @mbggenerated
     */
    public Integer getPushStatus() {
        return pushStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sugar_month_report.push_status
     *
     * @param pushStatus the value for t_sugar_month_report.push_status
     *
     * @mbggenerated
     */
    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }
}