package com.comvee.cdms.app.doctorapp.model.app;

public class NormalSugerListResqModel {
	
	private String memberId;
	private String sex;
	private String diabetesType;
	private String totalNum;	
	private String paramValue;//用逗号分隔
	private String normalNum;
	private String priceFlag;//是否付费
	private String picUrl;
	private String lastTime;//

	private String memberName;


	private String departmentName;
	private String hospitalNo;
	private String bedNo;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getHospitalNo() {
		return hospitalNo;
	}

	public void setHospitalNo(String hospitalNo) {
		this.hospitalNo = hospitalNo;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDiabetesType() {
		return diabetesType;
	}
	public void setDiabetesType(String diabetesType) {
		this.diabetesType = diabetesType;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getNormalNum() {
		return normalNum;
	}
	public void setNormalNum(String normalNum) {
		this.normalNum = normalNum;
	}
	public String getPriceFlag() {
		return priceFlag;
	}
	public void setPriceFlag(String priceFlag) {
		this.priceFlag = priceFlag;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	
}
