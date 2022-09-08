package com.comvee.cdms.member.po;

/**
 *
 * @author: 李左河
 * @date: 2018/3/27 23:55
 *
 */
public class MemberWarmPO {
    private String sid;

    /**
     * 患者id串
     */
    private String memberIds;

    /**
     * 插入时间
     */
    private String insertDt;

    /**
     * 修改时间
     */
    private String modifyDt;

    /**
     * 是否有效
     */
    private Integer isValid;

    /**
     * 关怀类型：1复诊提醒、2监测提醒、3检查提醒、4用药提2醒、5出院关怀、6节假日问候
     */
    private Integer warmType;

    /**
     * 下发时间类型：1出院后1个月、2出院后3个月、3出院后6个月、4出院后1年、5自定义
     */
    private Integer pushDtType;
    
    /**
     * 下发时间
     */
    private String pushDt;

    /**
     * 推送状态: 1 未推送 2 已推送
     */
    private Integer pushStatus;

    /**
     * 关怀内容
     */
    private String warmContent;
	/**
	 * 医生id
	 */
    private String doctorId;

	/**
	 * 医院编号
	 */
	private String hospitalId;

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public void setMemberIds(String memberIds) {
		this.memberIds = memberIds;
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

	public Integer getWarmType() {
		return warmType;
	}

	public void setWarmType(Integer warmType) {
		this.warmType = warmType;
	}

	public Integer getPushDtType() {
		return pushDtType;
	}

	public void setPushDtType(Integer pushDtType) {
		this.pushDtType = pushDtType;
	}

	public String getPushDt() {
		return pushDt;
	}

	public void setPushDt(String pushDt) {
		this.pushDt = pushDt;
	}

	public Integer getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(Integer pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getWarmContent() {
		return warmContent;
	}

	public void setWarmContent(String warmContent) {
		this.warmContent = warmContent;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getMemberIds() {
		return memberIds;
	}
}