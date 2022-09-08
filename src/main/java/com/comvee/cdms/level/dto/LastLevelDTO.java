package com.comvee.cdms.level.dto;

/**
 * @author wyc
 * @date 2019/11/20 18:26
 */
public class LastLevelDTO {

    /**
     * 患者id
     */
    private String memberId;
    /**
     * 医院id
     */
    private String hospitalId;
    /**
     * 检查日期
     */
    private String changeDate;
    /**
     * 是否确认 0:否 1:是
     */
    private Integer confirm;

    /**
     * true 查询当天数据 false 查询上次数据
     */
    private boolean nowLast;

    /**
     * 疾病类型 1高血压
     */
    private Integer levelType;

    private String doctorId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public boolean isNowLast() {
        return nowLast;
    }

    public void setNowLast(boolean nowLast) {
        this.nowLast = nowLast;
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

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }
}
