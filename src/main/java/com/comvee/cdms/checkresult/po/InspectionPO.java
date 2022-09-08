package com.comvee.cdms.checkresult.po;

import java.util.Date;

/**
 * 
 * @author 李左河
 *
 */
public class InspectionPO {

    /**
     * sid
     */
    private String sid;

    /**
     * 检查项目
     * inspect_title
     */
    private String inspectTitle;

    /**
     * 检查报告id
     * inspect_id
     */
    private String inspectId;

    /**
     * 检查医生id
     * inspect_doctor_id
     */
    private String inspectDoctorId;

    /**
     * 检查医生姓名
     * inspect_doctor_name
     */
    private String inspectDoctorName;

    /**
     * 检查时间
     * inspect_dt
     */
    private String inspectDt;

    /**
     * 报告日期
     * report_dt
     */
    private String reportDt;

    /**
     * 审查医生id
     * review_doctor_id
     */
    private String reviewDoctorId;

    /**
     * 审查医生姓名
     * review_doctor_name
     */
    private String reviewDoctorName;

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 就诊号
     * visit_no
     */
    private String visitNo;

    /**
     * 检查项目code
     * inspect_code
     */
    private String inspectCode;

    /**
     * 检查方法描述
     * inspect_method
     */
    private String inspectMethod;

    /**
     * department_id
     */
    private String departmentId;

    /**
     * department_name
     */
    private String departmentName;

    /**
     * 医院编号
     * hospital_id
     */
    private String hospitalId;

    /**
     * 医院名称
     * hospital_name
     */
    private String hospitalName;

    /**
     * 是否住院 1 住院 0非住院
     * in_hos
     */
    private Integer inHos;

    /**
     * 来源 1 系统 2 his
     * origin
     */
    private Integer origin;

    /**
     * 插入时间
     * insert_dt
     */
    private String insertDt;

    /**
     * 更新时间
     * modify_dt
     */
    private String modifyDt;

    /**
     * 是否有效 1是 0否
     * is_valid
     */
    private Integer isValid;

    /**
     * 检查所见
     * inspect_finding
     */
    private String inspectFinding;

    /**
     * 诊断结果
     * diagnose_result
     */
    private String diagnoseResult;

    /**
     * 检查数据json
     * inspect_data_json
     */
    private String inspectDataJson;

    /**
     * 报告地址
     *
     */
    private String reportUrl;

    private Integer reviewStatus;

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

	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
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

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
}