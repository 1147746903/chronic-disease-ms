package com.comvee.cdms.clinicaldiagnosis.vo;
/**
 * 
 * @author 李左河
 *
 */
public class MemberYzAndDrugVO {

	private String sid;

    /**
     * his中的患者id
     */
    private String patientId;

    /**
     * 登记号
     */
    private String visitNo;

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 医嘱id
     */
    private String yzId;

    /**
     * 医嘱组id
     */
    private String yzGroupId;

    /**
     * 医嘱项目类型 1 用药 2 护理 3 检验检查 4 其他
     */
    private Integer yzItemType;

    /**
     * 医嘱类型 1 长期医嘱 2 临时医嘱
     */
    private Integer yzType;

    /**
     * 医嘱项目名称
     */
    private String yzItemName;

    /**
     * 医嘱项目代码
     */
    private String yzItemCode;

    /**
     * 剂量
     */
    private String drugDose;

    /**
     * 剂量单位
     */
    private String drugDoseUnit;

    /**
     * 剂量单位描述
     */
    private String drugDoseUnitDesc;

    /**
     * 频次code
     */
    private String drugFreqCode;

    /**
     * 频次描述
     */
    private String drugFreqDesc;

    /**
     * 疗程代码
     */
    private String drugDurationCode;

    /**
     * 疗程描述
     */
    private String drugDurationDesc;

    /**
     * 规格
     */
    private String drugSpecification;

    /**
     * 医生代码
     */
    private String doctorCode;

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束日期
     */
    private String stopDate;

    /**
     * 结束时间
     */
    private String stopTime;

    /**
     * 价格
     */
    private String price;

    /**
     * 是否皮试 Y 是 N不是
     */
    private String skinTest;

    /**
     * 是否紧急 Y 是 N不是
     */
    private String emergency;

    /**
     * 主医嘱类型code
     */
    private String priorityCode;
    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 就诊类型  门诊/住院/其他
     */
	private String visitType;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getVisitNo() {
		return visitNo;
	}
	public void setVisitNo(String visitNo) {
		this.visitNo = visitNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getYzId() {
		return yzId;
	}
	public void setYzId(String yzId) {
		this.yzId = yzId;
	}
	public String getYzGroupId() {
		return yzGroupId;
	}
	public void setYzGroupId(String yzGroupId) {
		this.yzGroupId = yzGroupId;
	}
	public Integer getYzItemType() {
		return yzItemType;
	}
	public void setYzItemType(Integer yzItemType) {
		this.yzItemType = yzItemType;
	}
	public Integer getYzType() {
		return yzType;
	}
	public void setYzType(Integer yzType) {
		this.yzType = yzType;
	}
	public String getYzItemName() {
		return yzItemName;
	}
	public void setYzItemName(String yzItemName) {
		this.yzItemName = yzItemName;
	}
	public String getYzItemCode() {
		return yzItemCode;
	}
	public void setYzItemCode(String yzItemCode) {
		this.yzItemCode = yzItemCode;
	}
	public String getDrugDose() {
		return drugDose;
	}
	public void setDrugDose(String drugDose) {
		this.drugDose = drugDose;
	}
	public String getDrugDoseUnit() {
		return drugDoseUnit;
	}
	public void setDrugDoseUnit(String drugDoseUnit) {
		this.drugDoseUnit = drugDoseUnit;
	}
	public String getDrugDoseUnitDesc() {
		return drugDoseUnitDesc;
	}
	public void setDrugDoseUnitDesc(String drugDoseUnitDesc) {
		this.drugDoseUnitDesc = drugDoseUnitDesc;
	}
	public String getDrugFreqCode() {
		return drugFreqCode;
	}
	public void setDrugFreqCode(String drugFreqCode) {
		this.drugFreqCode = drugFreqCode;
	}
	public String getDrugFreqDesc() {
		return drugFreqDesc;
	}
	public void setDrugFreqDesc(String drugFreqDesc) {
		this.drugFreqDesc = drugFreqDesc;
	}
	public String getDrugDurationCode() {
		return drugDurationCode;
	}
	public void setDrugDurationCode(String drugDurationCode) {
		this.drugDurationCode = drugDurationCode;
	}
	public String getDrugDurationDesc() {
		return drugDurationDesc;
	}
	public void setDrugDurationDesc(String drugDurationDesc) {
		this.drugDurationDesc = drugDurationDesc;
	}
	public String getDrugSpecification() {
		return drugSpecification;
	}
	public void setDrugSpecification(String drugSpecification) {
		this.drugSpecification = drugSpecification;
	}
	public String getDoctorCode() {
		return doctorCode;
	}
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStopDate() {
		return stopDate;
	}
	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSkinTest() {
		return skinTest;
	}
	public void setSkinTest(String skinTest) {
		this.skinTest = skinTest;
	}
	public String getEmergency() {
		return emergency;
	}
	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}
	public String getInsertDt() {
		return insertDt;
	}
	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}
	public String getPriorityCode() {
		return priorityCode;
	}
	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
	}
	public String getVisitType() {
		return visitType;
	}
	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	
}
