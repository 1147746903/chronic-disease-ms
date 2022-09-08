package com.comvee.cdms.bloodmonitor.vo;


public class MemberMonitorPlanVO implements Comparable<MemberMonitorPlanVO>{
    private String planId; //监测方案id
    private Integer packageType; //方案类型 1:血糖监测方案 2:血压监测方案
    private String startMonitorDt; //开始监测时间
    private String endMonitorDt; //结束监测时间
    private String week; //固定每周
    private String doctorName; //医生名称
    private String departmentName; //科室
    private Integer operationType; //来源类型 1:医生web 2:医生H5 3:管理处方
    private Integer state; //监测方案状态 1: 已停止 2:执行中
    private String insertDt; //插入时间
    private String modifyDt; //修改时间
    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
        this.packageType = packageType;
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

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    @Override
    public String toString() {
        return "MemberMonitorPlanVO{" +
                "planId='" + planId + '\'' +
                ", packageType=" + packageType +
                ", startMonitorDt='" + startMonitorDt + '\'' +
                ", endMonitorDt='" + endMonitorDt + '\'' +
                ", week='" + week + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", operationType=" + operationType +
                ", state=" + state +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                '}';
    }

    @Override
    public int compareTo(MemberMonitorPlanVO o) {
        return this.state.compareTo(o.getState());
    }
}
