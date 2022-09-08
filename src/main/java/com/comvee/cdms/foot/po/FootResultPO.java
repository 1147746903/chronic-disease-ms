package com.comvee.cdms.foot.po;

public class FootResultPO {
    /**
     * sid
     */
    private String sid;

    /**
     * 身份证
     * id_card
     */
    private String idCard;

    /**
     * 评估code
     * assess_code
     */
    private String assessCode;

    /**
     * 评估结果
     * assess_value
     */
    private String assessValue;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAssessCode() {
        return assessCode;
    }

    public void setAssessCode(String assessCode) {
        this.assessCode = assessCode;
    }

    public String getAssessValue() {
        return assessValue;
    }

    public void setAssessValue(String assessValue) {
        this.assessValue = assessValue;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
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
}