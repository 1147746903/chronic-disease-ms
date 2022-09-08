package com.comvee.cdms.follow.po;

/**
 * @author wyc
 * @date 2019/5/17 13:59
 */
public class FollowCustomContentPO {
    private String sid;

    private String archivesJson;  //患者档案信息Json

    private String drugListJson;  //用药情况Json

    private String footJson;  //足有关Json

    private String addJson;  //添加问题json

    private String insertDt;  //插入时间

    private String updateDt;  //更新时间

    private Integer isValid;   //是否有效

    private String memberId;  //患者id

    private String followDt; //本次随访时间

    private String nextDt; //下次随访时间

    private String doctorName; //医生姓名

    private String doctorId; //医生id

    private Integer dealStatus;  //处理状态 0 未完成 1已完成 2患者已填写(未完成）

    /**
     * 填表人：1 医生 2 患者
     */
    private Integer fillFormBy;


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

    /**
     * 问卷JSON
     */
    private String quesJSon;

    public Integer getFillFormBy() {
        return fillFormBy;
    }

    public void setFillFormBy(Integer fillFormBy) {
        this.fillFormBy = fillFormBy;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
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

    public String getFollowDt() {
        return followDt;
    }

    public void setFollowDt(String followDt) {
        this.followDt = followDt;
    }

    public String getNextDt() {
        return nextDt;
    }

    public void setNextDt(String nextDt) {
        this.nextDt = nextDt;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getArchivesJson() {
        return archivesJson;
    }

    public void setArchivesJson(String archivesJson) {
        this.archivesJson = archivesJson;
    }

    public String getDrugListJson() {
        return drugListJson;
    }

    public void setDrugListJson(String drugListJson) {
        this.drugListJson = drugListJson;
    }

    public String getFootJson() {
        return footJson;
    }

    public void setFootJson(String footJson) {
        this.footJson = footJson;
    }

    public String getAddJson() {
        return addJson;
    }

    public void setAddJson(String addJson) {
        this.addJson = addJson;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getQuesJSon() {
        return quesJSon;
    }

    public void setQuesJSon(String quesJSon) {
        this.quesJSon = quesJSon;
    }
}
