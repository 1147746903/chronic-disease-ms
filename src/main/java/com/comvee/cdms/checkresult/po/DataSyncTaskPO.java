package com.comvee.cdms.checkresult.po;

import java.io.Serializable;

/**
 * 检验检查刷新任务请求表(CheckSyncTask)实体类
 *
 * @author lr
 * @since 2022-06-30
 */
public class DataSyncTaskPO implements Serializable {
    private static final long serialVersionUID = -11638887625858491L;
    /**
     * 主键id
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
     * 申请医生id
     */
    private String doctorId;
    /**
     * 状态 0未执行 1已执行 2失败
     */
    private Integer status;
    private Integer taskType;//1检验 2检查 3医嘱
    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 更新时间
     */
    private String updateDt;


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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }
}
