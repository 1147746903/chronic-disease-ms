package com.comvee.cdms.consultation.model.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_remote_consultation
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class RemoteConsultationPO {

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
     * 会诊方式  1 向上 2 向下
     * consultation_way
     */
    private Integer consultationWay;

    /**
     * 发起医院id
     * from_hospital_id
     */
    private String fromHospitalId;

    /**
     * 发起科室id
     * from_depart_id
     */
    private String fromDepartId;

    /**
     * 发起医生id
     * from_doctor_id
     */
    private String fromDoctorId;

    /**
     * 发起人联系方式
     * from_contact
     */
    private String fromContact;

    /**
     * 接收医院id
     * to_hospital_id
     */
    private String toHospitalId;

    /**
     * 接收科室id
     * to_depart_id
     */
    private String toDepartId;

    /**
     * 接收医生id
     * to_doctor_id
     */
    private String toDoctorId;

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

    /**
     * 会诊状态 1  未开始 2 会诊中  3 已结束
     * consultation_status
     */
    private Integer consultationStatus;

    /**
     * 发起方未读消息数
     * from_unread_number
     */
    private Integer fromUnreadNumber;

    /**
     * 接收方未读消息数
     * to_unread_number
     */
    private Integer toUnreadNumber;

    /**
     * 会诊原因
     * consultation_reason
     */
    private String consultationReason;

    /**
     * 会诊结果
     * consultation_result
     */
    private String consultationResult;

    /**
     * 确认接收时间
     * confirm_dt
     */
    private String confirmDt;

    /**
     * 会诊完成时间
     * finish_dt
     */
    private String finishDt;

    /**
     * 会诊完成操作者id
     * finish_operator_id
     */
    private String finishOperatorId;

    /**
     * 发起方 提醒状态 1 需要提醒  2 不需要提醒
     * from_remind_status
     */
    private Integer fromRemindStatus;

    /**
     * 发起方 提醒时间
     * from_remind_dt
     */
    private String fromRemindDt;

    /**
     * 接收方 提醒状态 1 需要提醒  2 不需要提醒
     * to_remind_status
     */
    private Integer toRemindStatus;

    /**
     * 接收方 提醒时间
     * to_remind_dt
     */
    private String toRemindDt;

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

    public Integer getConsultationWay() {
        return consultationWay;
    }

    public void setConsultationWay(Integer consultationWay) {
        this.consultationWay = consultationWay;
    }

    public String getFromHospitalId() {
        return fromHospitalId;
    }

    public void setFromHospitalId(String fromHospitalId) {
        this.fromHospitalId = fromHospitalId;
    }

    public String getFromDepartId() {
        return fromDepartId;
    }

    public void setFromDepartId(String fromDepartId) {
        this.fromDepartId = fromDepartId;
    }

    public String getFromDoctorId() {
        return fromDoctorId;
    }

    public void setFromDoctorId(String fromDoctorId) {
        this.fromDoctorId = fromDoctorId;
    }

    public String getFromContact() {
        return fromContact;
    }

    public void setFromContact(String fromContact) {
        this.fromContact = fromContact;
    }

    public String getToHospitalId() {
        return toHospitalId;
    }

    public void setToHospitalId(String toHospitalId) {
        this.toHospitalId = toHospitalId;
    }

    public String getToDepartId() {
        return toDepartId;
    }

    public void setToDepartId(String toDepartId) {
        this.toDepartId = toDepartId;
    }

    public String getToDoctorId() {
        return toDoctorId;
    }

    public void setToDoctorId(String toDoctorId) {
        this.toDoctorId = toDoctorId;
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

    public Integer getConsultationStatus() {
        return consultationStatus;
    }

    public void setConsultationStatus(Integer consultationStatus) {
        this.consultationStatus = consultationStatus;
    }

    public Integer getFromUnreadNumber() {
        return fromUnreadNumber;
    }

    public void setFromUnreadNumber(Integer fromUnreadNumber) {
        this.fromUnreadNumber = fromUnreadNumber;
    }

    public Integer getToUnreadNumber() {
        return toUnreadNumber;
    }

    public void setToUnreadNumber(Integer toUnreadNumber) {
        this.toUnreadNumber = toUnreadNumber;
    }

    public String getConsultationReason() {
        return consultationReason;
    }

    public void setConsultationReason(String consultationReason) {
        this.consultationReason = consultationReason;
    }

    public String getConsultationResult() {
        return consultationResult;
    }

    public void setConsultationResult(String consultationResult) {
        this.consultationResult = consultationResult;
    }

    public String getConfirmDt() {
        return confirmDt;
    }

    public void setConfirmDt(String confirmDt) {
        this.confirmDt = confirmDt;
    }

    public String getFinishDt() {
        return finishDt;
    }

    public void setFinishDt(String finishDt) {
        this.finishDt = finishDt;
    }

    public String getFinishOperatorId() {
        return finishOperatorId;
    }

    public void setFinishOperatorId(String finishOperatorId) {
        this.finishOperatorId = finishOperatorId;
    }

    public Integer getFromRemindStatus() {
        return fromRemindStatus;
    }

    public void setFromRemindStatus(Integer fromRemindStatus) {
        this.fromRemindStatus = fromRemindStatus;
    }

    public String getFromRemindDt() {
        return fromRemindDt;
    }

    public void setFromRemindDt(String fromRemindDt) {
        this.fromRemindDt = fromRemindDt;
    }

    public Integer getToRemindStatus() {
        return toRemindStatus;
    }

    public void setToRemindStatus(Integer toRemindStatus) {
        this.toRemindStatus = toRemindStatus;
    }

    public String getToRemindDt() {
        return toRemindDt;
    }

    public void setToRemindDt(String toRemindDt) {
        this.toRemindDt = toRemindDt;
    }
}