package com.comvee.cdms.app.doctorapp.model;

public class UserModel {
	private static final long serialVersionUID = 1L;
	private String userId;//用户唯一ID，主键
	private String userNo;//用户账号，唯一索引
	private String userPwd;//用户密码
	private String saltPwd;//
	private Integer userType;//用户类型 1：管理员 2：用户 3医生
	private Integer checkStatus;//用户审核状态 0未审核 1审核通过
	private String  loginUserIp;//最近登录的IP
	private String loginDt;//最近登录时间
	private String modifyDt;//用户唯一ID，主键
	private String insertDt;//入库时间
	private String isValid;//有效标志
	private String loginNum;//登录次数
	private String nickname;//昵称
	private Integer failNum;//连续失败次数
	private String doctor_invite_id;//邀请唯一id ，如果用户只是注册还未完善成员资料（即还没建立成员）该字段值保留，等建完成员，并建立医患关系后，把该字段清空。
	private Integer maxMemberNum;  //可以允许添加最大的成员 
	private String origin;           //来源
	private String itvsn;            //ITV账号  
	private String userPhone;        //联系电话
	private String maxSelfAssessment;//自助评估次数
	private Integer guideFlag;//引导标识
	private Integer groupType;  //机构标识
	private Integer  ownerFlag;    //机构拥有者，userid
	private Integer isGuest;//是否游客
	private Integer platForm;//平台
	private String doctorId;
	private String remark;
	private String doctorNum;
	private String studioId;//工作室id
	private Integer isLead;//是否首席医生 //1是0不是
	
	
	public String getStudioId() {
		return studioId;
	}
	public void setStudioId(String studioId) {
		this.studioId = studioId;
	}
	
	public Integer getIsLead() {
		return isLead;
	}
	public void setIsLead(Integer isLead) {
		this.isLead = isLead;
	}
	public String getSaltPwd() {
		return saltPwd;
	}
	public void setSaltPwd(String saltPwd) {
		this.saltPwd = saltPwd;
	}
	public String getDoctorNum() {
		return doctorNum;
	}
	public void setDoctorNum(String doctorNum) {
		this.doctorNum = doctorNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getLoginUserIp() {
		return loginUserIp;
	}
	public void setLoginUserIp(String loginUserIp) {
		this.loginUserIp = loginUserIp;
	}
	public String getLoginDt() {
		return loginDt;
	}
	public void setLoginDt(String loginDt) {
		this.loginDt = loginDt;
	}
	public String getModifyDt() {
		return modifyDt;
	}
	public void setModifyDt(String modifyDt) {
		this.modifyDt = modifyDt;
	}
	public String getInsertDt() {
		return insertDt;
	}
	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(String loginNum) {
		this.loginNum = loginNum;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getFailNum() {
		return failNum;
	}
	public void setFailNum(Integer failNum) {
		this.failNum = failNum;
	}
	public String getDoctor_invite_id() {
		return doctor_invite_id;
	}
	public void setDoctor_invite_id(String doctor_invite_id) {
		this.doctor_invite_id = doctor_invite_id;
	}
	public Integer getMaxMemberNum() {
		return maxMemberNum;
	}
	public void setMaxMemberNum(Integer maxMemberNum) {
		this.maxMemberNum = maxMemberNum;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getItvsn() {
		return itvsn;
	}
	public void setItvsn(String itvsn) {
		this.itvsn = itvsn;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getMaxSelfAssessment() {
		return maxSelfAssessment;
	}
	public void setMaxSelfAssessment(String maxSelfAssessment) {
		this.maxSelfAssessment = maxSelfAssessment;
	}
	public Integer getGuideFlag() {
		return guideFlag;
	}
	public void setGuideFlag(Integer guideFlag) {
		this.guideFlag = guideFlag;
	}
	public Integer getGroupType() {
		return groupType;
	}
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
	public Integer getOwnerFlag() {
		return ownerFlag;
	}
	public void setOwnerFlag(Integer ownerFlag) {
		this.ownerFlag = ownerFlag;
	}
	public Integer getIsGuest() {
		return isGuest;
	}
	public void setIsGuest(Integer isGuest) {
		this.isGuest = isGuest;
	}
	public Integer getPlatForm() {
		return platForm;
	}
	public void setPlatForm(Integer platForm) {
		this.platForm = platForm;
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
}
