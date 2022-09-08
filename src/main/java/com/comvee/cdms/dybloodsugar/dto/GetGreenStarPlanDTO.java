package com.comvee.cdms.dybloodsugar.dto;

public class GetGreenStarPlanDTO {
    private String mainPlanId;
    private int dateIndex;
    private String sid;

    public int getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(int dateIndex) {
        this.dateIndex = dateIndex;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMainPlanId() {
        return mainPlanId;
    }

    public void setMainPlanId(String mainPlanId) {
        this.mainPlanId = mainPlanId;
    }
}
