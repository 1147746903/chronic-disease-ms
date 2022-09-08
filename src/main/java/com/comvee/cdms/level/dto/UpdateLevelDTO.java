package com.comvee.cdms.level.dto;

/**
 * @author wyc
 * @date 2019/11/22 17:26
 */
public class UpdateLevelDTO {

    /**
     * 主键
     */
    private String sid;

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 医院id
     */
    private String hospitalId;

    /**
     * 是否确认 0 未确认 1已确认
     */
    private Integer confirm;

    /**
     * 分级类型 1 高血压
     */
    private Integer levelType;

    /**
     * 改变日期
     */
    private String changeDate;

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }
}
