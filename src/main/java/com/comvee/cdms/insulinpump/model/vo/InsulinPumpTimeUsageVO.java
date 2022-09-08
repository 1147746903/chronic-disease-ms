package com.comvee.cdms.insulinpump.model.vo;

public class InsulinPumpTimeUsageVO {

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 基础量
     */
    private Double basal;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getBasal() {
        return basal;
    }

    public void setBasal(Double basal) {
        this.basal = basal;
    }

}
