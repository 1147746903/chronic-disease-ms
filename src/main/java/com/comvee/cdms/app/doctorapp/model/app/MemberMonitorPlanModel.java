package com.comvee.cdms.app.doctorapp.model.app;

import com.comvee.cdms.bloodmonitor.model.SugarMonitorTemplatePO;

import java.util.List;

public class MemberMonitorPlanModel {
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
	 * 是否进行中 1 是 0 不是
	 */
	private Integer inProgress;
	
	//**app模板list
	private List<SugarMonitorTemplatePO> templates;

	private String startMonitorDt; //开始监测时间
	private String endMonitorDt; //结束监测时间

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
	public List<SugarMonitorTemplatePO> getTemplates() {
		return templates;
	}
	public void setTemplates(List<SugarMonitorTemplatePO> templates) {
		this.templates = templates;
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

	@Override
	public String toString() {
		return "MemberMonitorPlanModel{" +
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
				", templates=" + templates +
				", startMonitorDt='" + startMonitorDt + '\'' +
				", endMonitorDt='" + endMonitorDt + '\'' +
				'}';
	}
}
