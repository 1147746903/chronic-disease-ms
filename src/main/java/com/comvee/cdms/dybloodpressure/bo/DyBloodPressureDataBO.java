package com.comvee.cdms.dybloodpressure.bo;


/**
 * @Author linr
 * @Date 2021/10/26
 */
public class DyBloodPressureDataBO {

    /**
     * 主键
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 舒张压
     */
    private Integer dbp;
    /**
     * 收缩压
     */
    private Integer sbp;
    /**
     * 平均动脉压
     */
    private Integer map;
    /**
     * 心率
     */
    private Integer heartRate;

    /**
     * 记录时间
     */
    private String recordTime;
    private String machineType;//设备类型


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

    public DyBloodPressureDataBO() {
    }

    public DyBloodPressureDataBO(Integer dbp, Integer sbp) {
        this.dbp = dbp;
        this.sbp = sbp;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getDbp() {
        return dbp;
    }

    public void setDbp(Integer dbp) {
        this.dbp = dbp;
    }

    public Integer getSbp() {
        return sbp;
    }

    public void setSbp(Integer sbp) {
        this.sbp = sbp;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
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

    public Integer getMap() {
        return map;
    }

    public void setMap(Integer map) {
        this.map = map;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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
}
