package com.comvee.cdms.consultation.model.param;

import java.util.List;

public class UpdateRemoteConsultationWhereParam {

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
     * 发起科室id
     * from_depart_id
     */
    private String fromDepartId;

    /**
     * 接收科室id
     * to_depart_id
     */
    private String toDepartId;

    /**
     * 会诊状态 1  未开始 2 会诊中  3 已结束
     * consultation_status
     */
    private List<Integer> consultationStatusList;


    /**
     * 发起方 提醒状态 1 需要提醒  2 不需要提醒
     * from_remind_status
     */
    private Integer fromRemindStatus;

    /**
     * 接收方 提醒状态 1 需要提醒  2 不需要提醒
     * to_remind_status
     */
    private Integer toRemindStatus;

    private Boolean overdueDtValidate;

    public Boolean getOverdueDtValidate() {
        return overdueDtValidate;
    }

    public void setOverdueDtValidate(Boolean overdueDtValidate) {
        this.overdueDtValidate = overdueDtValidate;
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

    public String getFromDepartId() {
        return fromDepartId;
    }

    public void setFromDepartId(String fromDepartId) {
        this.fromDepartId = fromDepartId;
    }

    public String getToDepartId() {
        return toDepartId;
    }

    public void setToDepartId(String toDepartId) {
        this.toDepartId = toDepartId;
    }

    public List<Integer> getConsultationStatusList() {
        return consultationStatusList;
    }

    public void setConsultationStatusList(List<Integer> consultationStatusList) {
        this.consultationStatusList = consultationStatusList;
    }

    public Integer getFromRemindStatus() {
        return fromRemindStatus;
    }

    public void setFromRemindStatus(Integer fromRemindStatus) {
        this.fromRemindStatus = fromRemindStatus;
    }

    public Integer getToRemindStatus() {
        return toRemindStatus;
    }

    public void setToRemindStatus(Integer toRemindStatus) {
        this.toRemindStatus = toRemindStatus;
    }
}
