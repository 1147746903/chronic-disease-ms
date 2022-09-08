package com.comvee.cdms.app.doctorapp.model.app;

public class HomePageRespModel {
	
	private String patientNum;//拥有患者人数
	
	private String hasPayNum;// 付费患者人数
	
	private String noPayNum;// 免费患者人数
	
	private String abnormalNum;// 血糖异常患者人数
	
	private String normalNum;// 血糖正常患者人数
	
	private String unMeasuredNum;// 未测量血糖患者人数
	/**
	 * 患者总数
	 */
	private Long totalMemberNum;
	/**
	 * 佩戴探头患者数量
	 */
	private Long bindSensorMemberNum;

	public String getPatientNum() {
		return patientNum;
	}

	public void setPatientNum(String patientNum) {
		this.patientNum = patientNum;
	}

	public String getHasPayNum() {
		return hasPayNum;
	}

	public void setHasPayNum(String hasPayNum) {
		this.hasPayNum = hasPayNum;
	}

	public String getNoPayNum() {
		return noPayNum;
	}

	public void setNoPayNum(String noPayNum) {
		this.noPayNum = noPayNum;
	}

	public String getAbnormalNum() {
		return abnormalNum;
	}

	public void setAbnormalNum(String abnormalNum) {
		this.abnormalNum = abnormalNum;
	}

	public String getNormalNum() {
		return normalNum;
	}

	public void setNormalNum(String normalNum) {
		this.normalNum = normalNum;
	}

	public String getUnMeasuredNum() {
		return unMeasuredNum;
	}

	public void setUnMeasuredNum(String unMeasuredNum) {
		this.unMeasuredNum = unMeasuredNum;
	}


	public void setTotalMemberNum(Long totalMemberNum) {
		this.totalMemberNum = totalMemberNum;
	}

	public Long getTotalMemberNum() {
		return totalMemberNum;
	}

	public void setBindSensorMemberNum(Long bindSensorMemberNum) {
		this.bindSensorMemberNum = bindSensorMemberNum;
	}

	public Long getBindSensorMemberNum() {
		return bindSensorMemberNum;
	}
}
