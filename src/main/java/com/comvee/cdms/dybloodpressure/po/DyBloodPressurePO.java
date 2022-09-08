package com.comvee.cdms.dybloodpressure.po;

import java.io.Serializable;

/**
 * 动态血压表(TDynamicBloodPressure)实体类
 *
 * @author makejava
 * @since 2021-10-26 09:56:12
 */
public class DyBloodPressurePO implements Serializable {
    private static final long serialVersionUID = -85106359235296664L;
    /**
     * 主键
     */
    private String sid;
    /**
     * 舒张压
     */
    private String dbp;
    /**
     * 收缩压
     */
    private String sbp;
    /**
     * 平均动脉压
     */
    private String map;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 设备编号
     */
    private String machineNo;
    /**
     * 设备型号
     */
    private String machineModel;
    /**
     * 启动方式
     */
    private Integer startType;
    /**
     * 记录时间
     */
    private String recordTime;
    /**
     * 心率
     */
    private String heartRate;

    /**
     * 备注
     */
    private String remark;

    private Integer isValid;

    private String insertDt;

    private String modifyDt;

    private Integer machineType;//设备类型 1门诊2居家


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getMachineModel() {
        return machineModel;
    }

    public void setMachineModel(String machineModel) {
        this.machineModel = machineModel;
    }

    public Integer getStartType() {
        return startType;
    }

    public void setStartType(Integer startType) {
        this.startType = startType;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getMachineType() {
        return machineType;
    }

    public void setMachineType(Integer machineType) {
        this.machineType = machineType;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }
}
