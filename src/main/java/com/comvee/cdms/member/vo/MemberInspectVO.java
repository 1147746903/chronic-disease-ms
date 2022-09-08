package com.comvee.cdms.member.vo;

import java.io.Serializable;

public class MemberInspectVO implements Serializable {

    /**
     * 种类 1 并发症筛查 2 检查
     * （影响前段处理检查项目名称，获取详情等）
     */
    private Integer type;
    /**
     * 记录编号
     */
    private String sid;
    /**
     * 检查项目(type=1时需根据类型转化名称，type=2直接使用)
     */
    private String inspectTitle;
    /**
     * 检查报告编号
     */
    private String inspectId;
    /**
     * 检查日期
     */
    private String inspectDate;
    /**
     * 检查时间
     */
    private String inspectTime;
    /**
     * 检查科室名称
     */
    private String inspectDepartName;
    /**
     * 检查医生姓名
     */
    private String inspectDoctorName;
    /**
     * 审查医生姓名
     */
    private String reviewDoctorName;
    /**
     * 患者编号
     */
    private String memberId;
    /**
     * 就诊号
     */
    private String visitNo;
    /**
     * 检查类型
     */
    private String inspectType;
    private String remark;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getInspectTitle() {
        return inspectTitle;
    }

    public void setInspectTitle(String inspectTitle) {
        this.inspectTitle = inspectTitle;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getInspectDate() {
        return inspectDate;
    }

    public void setInspectDate(String inspectDate) {
        this.inspectDate = inspectDate;
    }

    public String getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(String inspectTime) {
        this.inspectTime = inspectTime;
    }

    public String getInspectDepartName() {
        return inspectDepartName;
    }

    public void setInspectDepartName(String inspectDepartName) {
        this.inspectDepartName = inspectDepartName;
    }

    public String getInspectDoctorName() {
        return inspectDoctorName;
    }

    public void setInspectDoctorName(String inspectDoctorName) {
        this.inspectDoctorName = inspectDoctorName;
    }

    public String getReviewDoctorName() {
        return reviewDoctorName;
    }

    public void setReviewDoctorName(String reviewDoctorName) {
        this.reviewDoctorName = reviewDoctorName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getInspectType() {
        return inspectType;
    }

    public void setInspectType(String inspectType) {
        this.inspectType = inspectType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
