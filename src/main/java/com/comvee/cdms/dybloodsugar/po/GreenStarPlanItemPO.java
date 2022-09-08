package com.comvee.cdms.dybloodsugar.po;

import com.comvee.cdms.dybloodsugar.vo.GreenStarPlanSubItemVO;

import java.util.ArrayList;
import java.util.List;

public class GreenStarPlanItemPO {
    private String sid;
    private String dailyPlanId;
    private Integer planType;
    private String planName;
    private String dataJson;
    private Integer hasDone;
    private Boolean isLock;
    private String insertDt;
    private String modifyDt;
    private List<GreenStarPlanSubItemVO> subPlans = new ArrayList<>();

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDailyPlanId() {
        return dailyPlanId;
    }

    public void setDailyPlanId(String dailyPlanId) {
        this.dailyPlanId = dailyPlanId;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public Integer getHasDone() {
        return hasDone;
    }

    public void setHasDone(Integer hasDone) {
        this.hasDone = hasDone;
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

    public List<GreenStarPlanSubItemVO> getSubPlans() {
        return subPlans;
    }

    public void setSubPlans(List<GreenStarPlanSubItemVO> subPlans) {
        this.subPlans = subPlans;
    }

    public void addSubPlan(GreenStarPlanSubItemVO subItem) {
        subPlans.add(subItem);
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Boolean getLock() {
        return isLock;
    }

    public void setLock(Boolean lock) {
        isLock = lock;
    }
}
