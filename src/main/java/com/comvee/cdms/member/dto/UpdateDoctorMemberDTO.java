package com.comvee.cdms.member.dto;

/**
 * @author: suyz
 * @date: 2018/10/16
 */
public class UpdateDoctorMemberDTO {

    /**
     * 主键
     * sid
     */
    private String sid;

    /**
     * 患者编号
     * member_id
     */
    private String memberId;

    /**
     * 医生编号
     * doctor_id
     */
    private String doctorId;

    /**
     * 更新时间
     * modify_time_stamp
     */
    private String modifyTimeStamp;

    /**
     * 是否是患者的主治医生 1是 0否
     * is_attend
     */
    private Integer isAttend;

    /**
     * 标签
     * label
     */
    private String label;

    /**
     * 0：默认分组，非0为真实分组
     * group_id
     */
    private String groupId;

    /**
     * 操作者id
     * operator_id
     */
    private String operatorId;

    private Integer concernStatus;

    private String groupIdWhere;

    private Integer payStatus;

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
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

    public String getModifyTimeStamp() {
        return modifyTimeStamp;
    }

    public void setModifyTimeStamp(String modifyTimeStamp) {
        this.modifyTimeStamp = modifyTimeStamp;
    }

    public Integer getIsAttend() {
        return isAttend;
    }

    public void setIsAttend(Integer isAttend) {
        this.isAttend = isAttend;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(Integer concernStatus) {
        this.concernStatus = concernStatus;
    }

    public String getGroupIdWhere() {
        return groupIdWhere;
    }

    public void setGroupIdWhere(String groupIdWhere) {
        this.groupIdWhere = groupIdWhere;
    }
}
