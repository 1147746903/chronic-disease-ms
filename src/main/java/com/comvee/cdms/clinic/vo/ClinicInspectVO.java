/*
*
* @author wyc
* @date 2019-08-12
*/
package com.comvee.cdms.clinic.vo;

public class ClinicInspectVO {
    /**
     * 临床检查主键id
     */
    private String sid;

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 责任医生
     */
    private String doctorName;

    /**
     * 创建人id
     */
    private String creatorId;

    /**
     * 床号
     */
    private String bedNumber;

    /**
     * 基本信息
     */
    private String baseJson;

    /**
     * 院内相关检查信息
     */
    private String checkJson;

    /**
     * 保存状态 0 未保存 1 保存草稿 2 已提交
     */
    private Integer saveStatus;

    /**
     * 检查日期
     */
    private String checkDt;

    /**
     * 插入时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String updateDt;

    /**
     * 是否有效0 否 1 是
     */
    private Integer isValid;

    /**
     * 患者姓名
     */
    private String memberName;

    /**
     * 患者年龄
     */
    private Integer memberAge;

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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getBaseJson() {
        return baseJson;
    }

    public void setBaseJson(String baseJson) {
        this.baseJson = baseJson;
    }

    public String getCheckJson() {
        return checkJson;
    }

    public void setCheckJson(String checkJson) {
        this.checkJson = checkJson;
    }

    public Integer getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Integer saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getCheckDt() {
        return checkDt;
    }

    public void setCheckDt(String checkDt) {
        this.checkDt = checkDt;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(Integer memberAge) {
        this.memberAge = memberAge;
    }
}