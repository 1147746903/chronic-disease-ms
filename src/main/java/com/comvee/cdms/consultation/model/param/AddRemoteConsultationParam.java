package com.comvee.cdms.consultation.model.param;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class AddRemoteConsultationParam {

    /**
     * 患者id
     * member_id
     */
    @NotEmpty(message = "memberId 不能为空")
    private String memberId;

    /**
     * 会诊方式  1 向上 2 向下
     * consultation_way
     */
    @NotNull(message = "consultationWay 不能为空")
    private Integer consultationWay;

    /**
     * 发起人联系方式
     * from_contact
     */
    private String fromContact;

    /**
     * 接收医院id
     * to_hospital_id
     */
    @NotEmpty(message = "toHospitalId 不能为空")
    private String toHospitalId;

    /**
     * 接收科室id
     * to_depart_id
     */
    @NotEmpty(message = "toDepartId 不能为空")
    private String toDepartId;

    /**
     * 会诊原因
     * consultation_reason
     */
    @NotEmpty(message = "consultationReason 不能为空")
    private String consultationReason;

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

    public String getConsultationReason() {
        return consultationReason;
    }

    public void setConsultationReason(String consultationReason) {
        this.consultationReason = consultationReason;
    }
}
