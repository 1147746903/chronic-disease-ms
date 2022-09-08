package com.comvee.cdms.dybloodpressure.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author linr
 * @Date 2021/10/26
 */
public class AddDyBpBaseDataDTO {

    /**
     * 舒张压
     */
    @NotBlank(message = "dbp不允许为空")
    private String dbp;
    /**
     * 收缩压
     */
    @NotBlank(message = "sbp不允许为空")
    private String sbp;

    /**
     * 心率
     */
    @NotBlank(message = "heartRate不允许为空")
    private String heartRate;
    /**
     * 记录时间
     */
    @NotBlank(message = "recordTime不允许为空")
    private String recordTime;

    /**
     * 启动方式
     */
    @NotNull(message = "startType不允许为空")
    private Integer startType;

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

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getStartType() {
        return startType;
    }

    public void setStartType(Integer startType) {
        this.startType = startType;
    }

    @Override
    public String toString() {
        return "AddDyBpBaseDataDTO{" +
                "dbp='" + dbp + '\'' +
                ", sbp='" + sbp + '\'' +
                ", heartRate='" + heartRate + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", startType=" + startType +
                '}';
    }
}
