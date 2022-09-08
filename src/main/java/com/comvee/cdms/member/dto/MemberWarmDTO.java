package com.comvee.cdms.member.dto;

/**
 * @author 李左河
 * @date 2018/3/27 15:20.
 */
public class MemberWarmDTO {

    private String memberIds;

    private String sid;

    /**
     * 关怀类型：0 无特别意义 1复诊提醒、2监测提醒、3检查提醒、4用药提2醒、5出院关怀、6节假日问候，0 无特别意义
     */
    private Integer warmType=0;

    /**
     * 下发时间类型：1立即下发、2定时下发
     */
    private Integer pushDtType;

    /**
     * 下发时间
     */
    private String pushDt;

    /**
     * 推送状态: 1 未推送 2 已推送
     */
    private Integer pushStatus;

    /**
     * 关怀内容
     */
    private String warmContent;
    private String doctorId;

    public String getMemberIds() {
        return memberIds;
    }

    public Integer getWarmType() {
        return warmType;
    }

    public void setWarmType(Integer warmType) {
        this.warmType = warmType;
    }

    public Integer getPushDtType() {
        return pushDtType;
    }

    public void setPushDtType(Integer pushDtType) {
        this.pushDtType = pushDtType;
    }

    public String getPushDt() {
        return pushDt;
    }

    public void setPushDt(String pushDt) {
        this.pushDt = pushDt;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getWarmContent() {
        return warmContent;
    }

    public void setWarmContent(String warmContent) {
        this.warmContent = warmContent;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setMemberIds(String memberIds) {
        this.memberIds = memberIds;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }
}
