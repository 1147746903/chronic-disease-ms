package com.comvee.cdms.bloodmonitor.po;

import java.util.List;

public class MemberMonitorPlanRecordPO {

    private String planId; //关联监测方案的主键id
    private String monitorPlanType; //监测方案类型 (1:血糖监测类型,2:血压监测类型)
    private String startMonitorDt; //开始监测时间
    private String endMonitorDt; //结束监测时间
    private String doctorId; //医生id
    private String hospitalId; //医院id
    private String memberId; //患者id
    private Integer operationType; //来源 1:管理处方 2:医生Web 3:医生H5
    private Integer state; //记录方案状态  1:待执行 2:执行中 3:已停止
    private String insertDt; //插入时间
    private String modifyDt; //更新时间
    private String isValid; //是否有效 1:有效 0:无效

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getMonitorPlanType() {
        return monitorPlanType;
    }

    public void setMonitorPlanType(String monitorPlanType) {
        this.monitorPlanType = monitorPlanType;
    }

    public String getStartMonitorDt() {
        return startMonitorDt;
    }

    public void setStartMonitorDt(String startMonitorDt) {
        this.startMonitorDt = startMonitorDt;
    }

    public String getEndMonitorDt() {
        return endMonitorDt;
    }

    public void setEndMonitorDt(String endMonitorDt) {
        this.endMonitorDt = endMonitorDt;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

}
