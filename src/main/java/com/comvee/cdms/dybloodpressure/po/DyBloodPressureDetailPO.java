package com.comvee.cdms.dybloodpressure.po;

import java.io.Serializable;

/**
 * 动态血压拓展表(TDynamicBpDetail)实体类
 *
 * @author makejava
 * @since 2021-11-04 10:36:13
 */
public class DyBloodPressureDetailPO implements Serializable {
    private static final Long serialVersionUID = -74122184101696580L;
    /**
     * 主键
     */
    private String sid;
    /**
     * 外键--动态血压表主键
     */
    private String foreignId;
    private String memberId;
    private Integer timeType;//时段类型  1起床后清晨时段 2白天清醒时段 3白天睡眠时段 4夜间睡眠时段
    /**
     * 舒张压白天清醒时段等级
     */
    private String dbpDayLevel;
    /**
     * 收缩压白天清醒时段等级
     */
    private String sbpDayLevel;
    /**
     * 舒张压夜间睡眠时段等级
     */
    private String dbpSleepLevel;
    /**
     * 收缩压夜间睡眠时段等级
     */
    private String sbpSleepLevel;
    /**
     * 舒张压起床后清晨时段等级
     */
    private String dbpAfterBedLevel;
    /**
     * 收缩压起床后清晨时段等级
     */
    private String sbpAfterBedLevel;
    /**
     * 舒张压24h等级
     */
    private String dbp24hLevel;
    /**
     * 收缩压24h等级
     */
    private String sbp24hLevel;

    private String startDt;

    private String endDt;

    private Integer isValid;

    private String insertDt;

    private String modifyDt;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getDbpDayLevel() {
        return dbpDayLevel;
    }

    public void setDbpDayLevel(String dbpDayLevel) {
        this.dbpDayLevel = dbpDayLevel;
    }

    public String getSbpDayLevel() {
        return sbpDayLevel;
    }

    public void setSbpDayLevel(String sbpDayLevel) {
        this.sbpDayLevel = sbpDayLevel;
    }

    public String getDbpSleepLevel() {
        return dbpSleepLevel;
    }

    public void setDbpSleepLevel(String dbpSleepLevel) {
        this.dbpSleepLevel = dbpSleepLevel;
    }

    public String getSbpSleepLevel() {
        return sbpSleepLevel;
    }

    public void setSbpSleepLevel(String sbpSleepLevel) {
        this.sbpSleepLevel = sbpSleepLevel;
    }

    public String getDbpAfterBedLevel() {
        return dbpAfterBedLevel;
    }

    public void setDbpAfterBedLevel(String dbpAfterBedLevel) {
        this.dbpAfterBedLevel = dbpAfterBedLevel;
    }

    public String getSbpAfterBedLevel() {
        return sbpAfterBedLevel;
    }

    public void setSbpAfterBedLevel(String sbpAfterBedLevel) {
        this.sbpAfterBedLevel = sbpAfterBedLevel;
    }

    public String getDbp24hLevel() {
        return dbp24hLevel;
    }

    public void setDbp24hLevel(String dbp24hLevel) {
        this.dbp24hLevel = dbp24hLevel;
    }

    public String getSbp24hLevel() {
        return sbp24hLevel;
    }

    public void setSbp24hLevel(String sbp24hLevel) {
        this.sbp24hLevel = sbp24hLevel;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
