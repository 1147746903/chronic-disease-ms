package com.comvee.cdms.bloodmonitor.vo;

public class PrescriptionMonitorPlanVO {
    private Integer packageType; //方案类型 1:血糖监测方案 2:血压监测方案
    private String planName; //方案名称
    private String memberName; //患者名称
    private String applyExplain; //适用说明
    private String startMonitorDt; //开始监测时间
    private String endMonitorDt; //结束监测时间
    private String detailMonitor; //监测细项

    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
        this.packageType = packageType;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getApplyExplain() {
        return applyExplain;
    }

    public void setApplyExplain(String applyExplain) {
        this.applyExplain = applyExplain;
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

    public String getDetailMonitor() {
        return detailMonitor;
    }

    public void setDetailMonitor(String detailMonitor) {
        this.detailMonitor = detailMonitor;
    }

    @Override
    public String toString() {
        return "PrescriptionMonitorPlanVO{" +
                "packageType=" + packageType +
                ", planName='" + planName + '\'' +
                ", memberName='" + memberName + '\'' +
                ", applyExplain='" + applyExplain + '\'' +
                ", startMonitorDt='" + startMonitorDt + '\'' +
                ", endMonitorDt='" + endMonitorDt + '\'' +
                ", detailMonitor='" + detailMonitor + '\'' +
                '}';
    }
}
