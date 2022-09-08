package com.comvee.cdms.bloodmonitor.po;

import java.util.List;

/**
 * 
 * @author 李左河
 *
 */
public class MemberMonitorPlanPO {
    private String planId;
    private String memberId;
    private Integer planType;
    private String planName;
    private String applyExplain;
    private String planDetail;
    private String insertDt;
    private String modifyDt;
    private Integer isValid;
    private String doctorId;
	private String senderId;
	private String startDt;
	private String endDt;
	/**
	 * 是否进行中 1 是 0 不是 2:待执行
	 */
	private Integer inProgress;

	/**
	 * 病种 1 糖尿病 2 高血压
	 */
	private Integer illnessType;
	/**
	 * 详细类型  0 2型糖尿病  1 妊娠糖尿病 2 高血压
	 */
	private Integer eohType;

	private String startMonitorDt; //开始监测时间
	private String endMonitorDt; //结束监测时间
	private List<String> week; //固定每周
	private Integer operationType; //来源 1:医生Web 2:医生H5
	private String monitorPlanType; //监测方案类型
	private String hospitalId; //医院id
	private Integer type; //来源 1:医生Web 2:医生H5 3:管理处方
	public Integer getIllnessType() {
		return illnessType;
	}

	public void setIllnessType(Integer illnessType) {
		this.illnessType = illnessType;
	}

	public Integer getEohType() {
		return eohType;
	}

	public void setEohType(Integer eohType) {
		this.eohType = eohType;
	}

	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getApplyExplain() {
		return applyExplain;
	}
	public void setApplyExplain(String applyExplain) {
		this.applyExplain = applyExplain;
	}
	public String getPlanDetail() {
		return planDetail;
	}
	public void setPlanDetail(String planDetail) {
		this.planDetail = planDetail;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getStartDt() {
		return startDt;
	}

	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public Integer getInProgress() {
		return inProgress;
	}

	public void setInProgress(Integer inProgress) {
		this.inProgress = inProgress;
	}


	public String getStartMonitorDt() {
		return startMonitorDt;
	}

	public void setStartMonitorDt(String startMonitorDt) {
		this.startMonitorDt = startMonitorDt;
	}

	public String getEndMonitorDt() {
		return endMonitorDt;
	}

	public void setEndMonitorDt(String endMonitorDt) {
		this.endMonitorDt = endMonitorDt;
	}


	public List<String> getWeek() {
		return week;
	}

	public void setWeek(List<String> week) {
		this.week = week;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	public String getMonitorPlanType() {
		return monitorPlanType;
	}

	public void setMonitorPlanType(String monitorPlanType) {
		this.monitorPlanType = monitorPlanType;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MemberMonitorPlanPO{" +
				"planId='" + planId + '\'' +
				", memberId='" + memberId + '\'' +
				", planType=" + planType +
				", planName='" + planName + '\'' +
				", applyExplain='" + applyExplain + '\'' +
				", planDetail='" + planDetail + '\'' +
				", insertDt='" + insertDt + '\'' +
				", modifyDt='" + modifyDt + '\'' +
				", isValid=" + isValid +
				", doctorId='" + doctorId + '\'' +
				", senderId='" + senderId + '\'' +
				", startDt='" + startDt + '\'' +
				", endDt='" + endDt + '\'' +
				", inProgress=" + inProgress +
				", illnessType=" + illnessType +
				", eohType=" + eohType +
				", startMonitorDt='" + startMonitorDt + '\'' +
				", endMonitorDt='" + endMonitorDt + '\'' +
				", operationType=" + operationType +
				", type=" + type +
				'}';
	}
}