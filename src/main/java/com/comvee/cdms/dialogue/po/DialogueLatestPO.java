package com.comvee.cdms.dialogue.po;

/**
 * @author 李左河
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_dialogue_latest
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class DialogueLatestPO {
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
     * 医生未读数
     */
    private Long doctorUnread;

    /**
     * 患者未读数
     */
    private Long patientUnread;



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

    public Long getPatientUnread() {
        return patientUnread;
    }

    public void setPatientUnread(Long patientUnread) {
        this.patientUnread = patientUnread;
    }

}