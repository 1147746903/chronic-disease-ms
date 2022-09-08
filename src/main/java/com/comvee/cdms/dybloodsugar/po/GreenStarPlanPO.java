package com.comvee.cdms.dybloodsugar.po;

import java.util.List;

public class GreenStarPlanPO {
    private String sid;
    private String mainPlanId;
    private String planDate;
    private Integer dateIndex;
    private Integer hasDone;
    private Integer isLock;
    private Integer planCount;
    private Integer hasDoneCount;
    private String insertDt;
    private String modifyDt;
    private List<GreenStarPlanItemPO> planItems;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public Integer getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(int dateIndex) {
        this.dateIndex = dateIndex;
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

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getPlanCount() {
        return planCount;
    }

    public void setPlanCount(Integer planCount) {
        this.planCount = planCount;
    }

    public Integer getHasDoneCount() {
        return hasDoneCount;
    }

    public void setHasDoneCount(Integer hasDoneCount) {
        this.hasDoneCount = hasDoneCount;
    }

    public String getMainPlanId() {
        return mainPlanId;
    }

    public void setMainPlanId(String mainPlanId) {
        this.mainPlanId = mainPlanId;
    }

    public List<GreenStarPlanItemPO> getPlanItems() {
        return planItems;
    }

    public void setPlanItems(List<GreenStarPlanItemPO> planItems) {
        this.planItems = planItems;
    }
}
