package com.comvee.cdms.clinic.dto;

/**
 * @author wyc
 * @date 2019/8/12 13:51
 */
public class UpdateClinicInspectDTO {
    /**
     * 临床检查主键id
     */
    private String sid;

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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
}
