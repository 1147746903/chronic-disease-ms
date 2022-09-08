/*
*
* @author wyc
* @date 2019-08-15
*/
package com.comvee.cdms.follow.po;


public class FollowDiabetesPO {
    /**
     * 主键id
     */
    private String sid;

    /**
     * 随访方式 1 门诊 2 家庭 3 电话
     */
    private Integer followType;

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 是否有效 0 否 1 是
     */
    private Integer isValid;

    /**
     * 随访时间
     */
    private String followDt;

    /**
     * 插入时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String updateDt;

    /**
     * 随访信息json
     */
    private String followInfo;

    /**
     * 目前主要问题
     */
    private String mqzywt;

    /**
     * 主要改进措施
     */
    private String zygjcs;

    /**
     * 预期达到目标
     */
    private String yqddmb;

    private String leaderId;
    private Integer dealStatus;
    private String doctorName;
    private String nextDt;
    private String xltz;//心理调整
    private String zyxw;//遵医行为

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getNextDt() {
        return nextDt;
    }

    public void setNextDt(String nextDt) {
        this.nextDt = nextDt;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getFollowDt() {
        return followDt;
    }

    public void setFollowDt(String followDt) {
        this.followDt = followDt;
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

    public String getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(String followInfo) {
        this.followInfo = followInfo;
    }

    public String getMqzywt() {
        return mqzywt;
    }

    public void setMqzywt(String mqzywt) {
        this.mqzywt = mqzywt;
    }

    public String getZygjcs() {
        return zygjcs;
    }

    public void setZygjcs(String zygjcs) {
        this.zygjcs = zygjcs;
    }

    public String getYqddmb() {
        return yqddmb;
    }

    public void setYqddmb(String yqddmb) {
        this.yqddmb = yqddmb;
    }

    public String getXltz() {
        return xltz;
    }

    public void setXltz(String xltz) {
        this.xltz = xltz;
    }

    public String getZyxw() {
        return zyxw;
    }

    public void setZyxw(String zyxw) {
        this.zyxw = zyxw;
    }
}