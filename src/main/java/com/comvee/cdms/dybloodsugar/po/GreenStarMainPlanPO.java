package com.comvee.cdms.dybloodsugar.po;

import java.util.List;

public class GreenStarMainPlanPO {
    private String sid;
    private String memberId;
    private String sensorNo;
    private String startDt;
    private String stopDt;
    private String insertDt;
    private String modifyDt;
    private List<GreenStarPlanPO> dailyPlans;


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

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getStopDt() {
        return stopDt;
    }

    public void setStopDt(String stopDt) {
        this.stopDt = stopDt;
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

    public List<GreenStarPlanPO> getDailyPlans() {
        return dailyPlans;
    }

    public void setDailyPlans(List<GreenStarPlanPO> dailyPlans) {
        this.dailyPlans = dailyPlans;
    }
}
