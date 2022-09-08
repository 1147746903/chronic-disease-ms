package com.comvee.cdms.app.doctorapp.model.app;

public class DialogueLatestForDoctorModel {

    /**
     * sid
     */
    private String sid;

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 医生id
     * doctor_id
     */
    private String doctorId;

    /**
     * 插入时间
     * insert_dt
     */
    private String insertDt;

    /**
     * 患者消息
     * patient_msg
     */
    private String patientMsg;

    /**
     * 医生消息
     * doctor_msg
     */
    private String doctorMsg;

    /**
     * 是否被删除 1 是 0 不是
     * be_delete
     */
    private String beDelete;

    private Long doctorTimestamp;

    private Long patientTimestamp;

    /**
     * 最后发表时间
     * latest_dt
     */
    private String latestDt;

    /**
     * 患者未读数
     */
    private Long doctorUnread;

    /**
     * 患者性别
     */
    private int sex;
    
    private String picUrl;
    
    private String memberName;

	/**
	 * 付费状态
	 */
	private String priceFlag;

    private Integer layer;


	public String getPriceFlag() {
		return priceFlag;
	}

	public void setPriceFlag(String priceFlag) {
		this.priceFlag = priceFlag;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getInsertDt() {
		return insertDt;
	}

	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}

	public String getPatientMsg() {
		return patientMsg;
	}

	public void setPatientMsg(String patientMsg) {
		this.patientMsg = patientMsg;
	}

	public String getDoctorMsg() {
		return doctorMsg;
	}

	public void setDoctorMsg(String doctorMsg) {
		this.doctorMsg = doctorMsg;
	}

	public String getBeDelete() {
		return beDelete;
	}

	public void setBeDelete(String beDelete) {
		this.beDelete = beDelete;
	}

	public Long getDoctorTimestamp() {
		return doctorTimestamp;
	}

	public void setDoctorTimestamp(Long doctorTimestamp) {
		this.doctorTimestamp = doctorTimestamp;
	}

	public Long getPatientTimestamp() {
		return patientTimestamp;
	}

	public void setPatientTimestamp(Long patientTimestamp) {
		this.patientTimestamp = patientTimestamp;
	}

	public String getLatestDt() {
		return latestDt;
	}

	public void setLatestDt(String latestDt) {
		this.latestDt = latestDt;
	}

	public Long getDoctorUnread() {
		return doctorUnread;
	}

	public void setDoctorUnread(Long doctorUnread) {
		this.doctorUnread = doctorUnread;
	}



	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getLayer() {
        return layer;
    }
}
