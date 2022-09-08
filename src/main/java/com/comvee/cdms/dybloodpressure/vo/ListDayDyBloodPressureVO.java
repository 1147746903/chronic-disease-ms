package com.comvee.cdms.dybloodpressure.vo;

/**
 * @Author linr
 * @Date 2021/10/20
 */
public class ListDayDyBloodPressureVO {

    private String sid;
    private String recordTime;
    private String dbp;
    private String sbp;
    private String heartRate;
    private String map;//平均动脉压
    private Integer isValid;//是否有效 1是0不是
    private Integer isSleep;//是否是睡眠时段 1是0不是
    private Integer machineNo;
    private Integer timeType;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getDbp() {
        return dbp;
    }

    public void setDbp(String dbp) {
        this.dbp = dbp;
    }

    public String getSbp() {
        return sbp;
    }

    public void setSbp(String sbp) {
        this.sbp = sbp;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getIsSleep() {
        return isSleep;
    }

    public void setIsSleep(Integer isSleep) {
        this.isSleep = isSleep;
    }

    public Integer getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(Integer machineNo) {
        this.machineNo = machineNo;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }
}
