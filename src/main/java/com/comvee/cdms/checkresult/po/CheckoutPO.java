package com.comvee.cdms.checkresult.po;

import java.util.Date;

/**
 * 
 * @author 李左河
 *
 */
public class CheckoutPO {
    private String sid;
    /** 检验项目 **/
    private String checkoutTitle;
    /** 检查报告id **/
    private String checkoutId;
    /** 检验医生id **/
    private String checkoutDoctorId;
    /** 检验医生名称 **/
    private String checkoutDoctorName;
    /** 样本code **/
    private String sampleCode;
    /** 样本名称 **/
    private String sampleName;
    /** 检验日期 **/
    private String checkoutDate;
    /** 检验时间 **/
    private String checkoutTime;
    /** 检验报告备注 **/
    private String checkoutRemark;
    /** 检验目的 **/
    private String checkoutGoal;
    /** 医嘱id **/
    private String yzId;
    /** 诊断结果 **/
    private String diagnoseResult;
    /** 诊断名称 **/
    private String diagnoseText;
    /** 科室code(病区code) **/
    private String departCode;
    /** 患者id **/
    private String memberId;
    /** 就诊号 **/
    private String visitNo;
    /** 报告日期 **/
    private String reportDate;
    /** 科室id **/
    private String departmentId;
    /** 科室名称 **/
    private String departmentName;
    /** 送检日期 **/
    private String submitCheckoutDate;
    /** 诊断意见 **/
    private String diagnoseOpinion;
	private Integer isValid;
	/**
	 * 来源 1his 2web手动添加
	 */
	private Integer recordOrigin;
	/**
	 * 医院编号
	 */
	private String hospitalId;
	/**
	 * 医院名称
	 */
	private String hospitalName;

	private Integer visitType;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getCheckoutTitle() {
		return checkoutTitle;
	}

	public void setCheckoutTitle(String checkoutTitle) {
		this.checkoutTitle = checkoutTitle;
	}

	public String getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(String checkoutId) {
		this.checkoutId = checkoutId;
	}

	public String getCheckoutDoctorId() {
		return checkoutDoctorId;
	}

	public void setCheckoutDoctorId(String checkoutDoctorId) {
		this.checkoutDoctorId = checkoutDoctorId;
	}

	public String getCheckoutDoctorName() {
		return checkoutDoctorName;
	}

	public void setCheckoutDoctorName(String checkoutDoctorName) {
		this.checkoutDoctorName = checkoutDoctorName;
	}

	public String getSampleCode() {
		return sampleCode;
	}

	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public String getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public String getCheckoutRemark() {
		return checkoutRemark;
	}

	public void setCheckoutRemark(String checkoutRemark) {
		this.checkoutRemark = checkoutRemark;
	}

	public String getCheckoutGoal() {
		return checkoutGoal;
	}

	public void setCheckoutGoal(String checkoutGoal) {
		this.checkoutGoal = checkoutGoal;
	}

	public String getYzId() {
		return yzId;
	}

	public void setYzId(String yzId) {
		this.yzId = yzId;
	}

	public String getDiagnoseResult() {
		return diagnoseResult;
	}

	public void setDiagnoseResult(String diagnoseResult) {
		this.diagnoseResult = diagnoseResult;
	}

	public String getDiagnoseText() {
		return diagnoseText;
	}

	public void setDiagnoseText(String diagnoseText) {
		this.diagnoseText = diagnoseText;
	}

	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
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

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
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

	public String getSubmitCheckoutDate() {
		return submitCheckoutDate;
	}

	public void setSubmitCheckoutDate(String submitCheckoutDate) {
		this.submitCheckoutDate = submitCheckoutDate;
	}

	public String getDiagnoseOpinion() {
		return diagnoseOpinion;
	}

	public void setDiagnoseOpinion(String diagnoseOpinion) {
		this.diagnoseOpinion = diagnoseOpinion;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getRecordOrigin() {
		return recordOrigin;
	}

	public void setRecordOrigin(Integer recordOrigin) {
		this.recordOrigin = recordOrigin;
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

	public Integer getVisitType() {
		return visitType;
	}

	public void setVisitType(Integer visitType) {
		this.visitType = visitType;
	}
}