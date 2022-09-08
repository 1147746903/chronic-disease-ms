package com.comvee.cdms.consultation.model.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_remote_consultation_dialogue
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class RemoteConsultationMessagePO {
    /**
     * sid
     */
    private String sid;

    /**
     * 会诊id  t_remote_consultation的sid
     * consultation_id
     */
    private String consultationId;

    /**
     * 医生id
     * doctor_id
     */
    private String doctorId;

    /**
     * 医院id
     * hospital_id
     */
    private String hospitalId;

    /**
     * 科室id
     * depart_id
     */
    private String departId;

    /**
     * 发送时间
     * send_dt
     */
    private String sendDt;

    /**
     * 发送时间戳
     * send_timestamp
     */
    private Long sendTimestamp;

    /**
     * 内容类型 1 文本
     * content_type
     */
    private Integer contentType;

    /**
     * 发送内容
     * content_data
     */
    private String contentData;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * valid
     */
    private Integer valid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getSendDt() {
        return sendDt;
    }

    public void setSendDt(String sendDt) {
        this.sendDt = sendDt;
    }

    public Long getSendTimestamp() {
        return sendTimestamp;
    }

    public void setSendTimestamp(Long sendTimestamp) {
        this.sendTimestamp = sendTimestamp;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getContentData() {
        return contentData;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
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

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }
}