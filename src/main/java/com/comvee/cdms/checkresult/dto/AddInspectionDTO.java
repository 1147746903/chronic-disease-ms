package com.comvee.cdms.checkresult.dto;

public class AddInspectionDTO {

    /**
     * 检查项目
     */
    private String inspectTitle;
    /**
     * 检查报告id
     */
    private String inspectId;
    /**
     * 检查医生id
     */
    private String inspectDoctorId;
    /**
     * 检查医生姓名
     */
    private String inspectDoctorName;
    /**
     * 检查时间
     */
    private String inspectDt;
    /**
     * 报告时间
     */
    private String reportDt;

    /**
     * 审查医生id
     */
    private String reviewDoctorId;
    /**
     * 审查医生姓名
     */
    private String reviewDoctorName;
    /**
     * 诊断所见
     */
    private String inspectFinding;
    /**
     * 诊断结果
     */
    private String diagnoseResult;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 就诊号
     */
    private String visitNo;

    /**
     * 检查项目
     */
    private String inspectCode;
    /**
     * 检查方法描述
     */
    private String inspectMethod;


    /**
     * 检查数值
     */
    private String inspectDataJson;

    /**
     * 部门id
     */
    private String departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 医院编号
     */
    private String hospitalId;
    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 是否住院
     */
    private Integer inHos;

    /**
     * 来源
     */
    private Integer origin;

    private String reportUrl;

    /**
     * 审核状态 1 已审核 0 未审核
     */
    private Integer reviewStatus;

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
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

    public String getInspectDoctorId() {
        return inspectDoctorId;
    }

    public void setInspectDoctorId(String inspectDoctorId) {
        this.inspectDoctorId = inspectDoctorId;
    }

    public String getInspectDoctorName() {
        return inspectDoctorName;
    }

    public void setInspectDoctorName(String inspectDoctorName) {
        this.inspectDoctorName = inspectDoctorName;
    }

    public String getInspectDt() {
        return inspectDt;
    }

    public void setInspectDt(String inspectDt) {
        this.inspectDt = inspectDt;
    }

    public String getReportDt() {
        return reportDt;
    }

    public void setReportDt(String reportDt) {
        this.reportDt = reportDt;
    }

    public String getReviewDoctorId() {
        return reviewDoctorId;
    }

    public void setReviewDoctorId(String reviewDoctorId) {
        this.reviewDoctorId = reviewDoctorId;
    }

    public String getReviewDoctorName() {
        return reviewDoctorName;
    }

    public void setReviewDoctorName(String reviewDoctorName) {
        this.reviewDoctorName = reviewDoctorName;
    }

    public String getInspectFinding() {
        return inspectFinding;
    }

    public void setInspectFinding(String inspectFinding) {
        this.inspectFinding = inspectFinding;
    }

    public String getDiagnoseResult() {
        return diagnoseResult;
    }

    public void setDiagnoseResult(String diagnoseResult) {
        this.diagnoseResult = diagnoseResult;
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

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    public String getInspectMethod() {
        return inspectMethod;
    }

    public void setInspectMethod(String inspectMethod) {
        this.inspectMethod = inspectMethod;
    }



    public String getInspectDataJson() {
        return inspectDataJson;
    }

    public void setInspectDataJson(String inspectDataJson) {
        this.inspectDataJson = inspectDataJson;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }


}
