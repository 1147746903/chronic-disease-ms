package com.comvee.cdms.dialogue.vo;

/**
 * @author: suyz
 * @date: 2018/10/25
 */
public class DialogueLatestMemberVO {

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
    private Long patientUnread;

    private String sex;

    private String doctorName;
    private String doctorImageHead;

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

    public Long getPatientUnread() {
        return patientUnread;
    }

    public void setPatientUnread(Long patientUnread) {
        this.patientUnread = patientUnread;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorImageHead() {
        return doctorImageHead;
    }

    public void setDoctorImageHead(String doctorImageHead) {
        this.doctorImageHead = doctorImageHead;
    }
}
