package com.comvee.cdms.sign.dto;

import javax.validation.constraints.NotEmpty;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_blood_pressure
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class AddBloodPressureDTO {

    @NotEmpty(message = "memberId 不能为空")
    private String memberId;


    /**
     * 舒张压
     * dbp
     */
    @NotEmpty
    private String dbp;

    /**
     * 收缩压
     * sbp
     */
    @NotEmpty
    private String sbp;

    /**
     * 记录时间
     * record_time
     */
    @NotEmpty
    private String recordTime;

//    @NotNull(message = "来源不可为空，origin")
    private Integer origin;


    private String dbpMin;
    private String dbpMax;

    private String sbpMin;
    private String sbpMax;

    private String heartRate;//心率

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDbpMin() {
        return dbpMin;
    }

    public void setDbpMin(String dbpMin) {
        this.dbpMin = dbpMin;
    }

    public String getDbpMax() {
        return dbpMax;
    }

    public void setDbpMax(String dbpMax) {
        this.dbpMax = dbpMax;
    }

    public String getSbpMin() {
        return sbpMin;
    }

    public void setSbpMin(String sbpMin) {
        this.sbpMin = sbpMin;
    }

    public String getSbpMax() {
        return sbpMax;
    }

    public void setSbpMax(String sbpMax) {
        this.sbpMax = sbpMax;
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

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }
}