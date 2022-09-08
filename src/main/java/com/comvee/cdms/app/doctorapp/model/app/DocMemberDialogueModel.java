package com.comvee.cdms.app.doctorapp.model.app;

public class DocMemberDialogueModel {

	   /**
     * sid
     */
    private String sid;

    /**
     * 医生id
     * doctor_id
     */
    private String doctorId;

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 插入时间
     * insert_dt
     */
    private String insertDt;

    /**
     * 消息所有者 1 患者 2 医生
     * owner_type
     */
    private Integer ownerType;

    /**
     * 消息类型 
     * msg_type
     */
    private Integer msgType;

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
     * 客户端展示： 1 只在医生端展示 2 只在患者端展示 3 医生端&患者端都展示
     * show_client
     */
    private Integer showClient;

    /**
     * 展示形式： 1 只在首页 2 只在对话 3 在首页&对话
     * show_type
     */
    private Integer showType;

    /**
     * 发送者id
     * sender_id
     */
    private String senderId;

    /**
     * 消息结构体
     * data_str
     */
    private String dataStr;


    private Integer beDelete;

    private Long sendTimestamp;

    private Long updateTimestamp;
    
    private String textType;//如果msgTpye = 1的时候 从 dataStr取出来给前端


    private String foreignId;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public Integer getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
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

    public Integer getShowClient() {
        return showClient;
    }

    public void setShowClient(Integer showClient) {
        this.showClient = showClient;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public Long getSendTimestamp() {
        return sendTimestamp;
    }

    public void setSendTimestamp(Long sendTimestamp) {
        this.sendTimestamp = sendTimestamp;
    }

    public Long getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Long updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public Integer getBeDelete() {
        return beDelete;
    }

    public void setBeDelete(Integer beDelete) {
        this.beDelete = beDelete;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

	public String getTextType() {
		return textType;
	}

	public void setTextType(String textType) {
		this.textType = textType;
	}
    
}
