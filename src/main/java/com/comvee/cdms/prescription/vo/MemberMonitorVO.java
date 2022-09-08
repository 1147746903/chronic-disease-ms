package com.comvee.cdms.prescription.vo;

public class MemberMonitorVO {
    private String planId; //监测方案id
    private Integer type; //1:血糖管理->监测方案 2:管理处方->监测方案

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MemberMonitorVO{" +
                "planId=" + planId +
                ", type=" + type +
                '}';
    }
}
