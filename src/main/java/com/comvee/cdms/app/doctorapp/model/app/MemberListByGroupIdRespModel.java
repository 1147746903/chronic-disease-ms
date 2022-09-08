package com.comvee.cdms.app.doctorapp.model.app;

public class MemberListByGroupIdRespModel {

	/***/
	private String memberId;
	
	/**患者姓名*/
	private String memberName;
	
	/***/
	private String sex;
	
	/**糖尿病类型*/
	private String diabetesType;
	
	/***/
	private String diabetesName;
	
	private String picUrl;
	
	private String priceFlag;
	
	private String packageIds;
	
	private String packageName;
	
	private String birthday;
	
	private String age;
	
	private String groupId;
	
	private String isValid;

	private String doctorId;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public String getDiabetesName() {
		return diabetesName;
	}

	public void setDiabetesName(String diabetesName) {
		this.diabetesName = diabetesName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPriceFlag() {
		return priceFlag;
	}

	public void setPriceFlag(String priceFlag) {
		this.priceFlag = priceFlag;
	}

	public String getPackageIds() {
		return packageIds;
	}

	public void setPackageIds(String packageIds) {
		this.packageIds = packageIds;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
}
