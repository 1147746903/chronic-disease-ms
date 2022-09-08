package com.comvee.cdms.bloodmonitor.model;

import java.io.Serializable;
/**
 * 
 * @author 李左河
 *
 */
public class TodaySchemaModel implements Serializable {
    private String memberId;
    /**
     * 类型1长期2临时
     */
    private String planType;
    /**
     * 监测方案详情
     */
    private String planDetail;
    /**
     * 今日计划
     */
    private String todayPlan;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanDetail() {
        return planDetail;
    }

    public void setPlanDetail(String planDetail) {
        this.planDetail = planDetail;
    }

    public String getTodayPlan() {
        return todayPlan;
    }

    public void setTodayPlan(String todayPlan) {
        this.todayPlan = todayPlan;
    }

    @Override
    public String toString() {
        return "TodaySchemaModel{" +
                "memberId=" + memberId +
                ", planType='" + planType + '\'' +
                ", planDetail='" + planDetail + '\'' +
                ", todayPlan='" + todayPlan + '\'' +
                '}';
    }
}
