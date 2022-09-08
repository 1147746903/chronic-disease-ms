package com.comvee.cdms.dybloodsugar.vo;

public class DynamicBloodChartDataSportVO {

    private String startTime;
    private String endTime;
    private String sportContent;
    private Integer origin;

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

    public String getSportContent() {
        return sportContent;
    }

    public void setSportContent(String sportContent) {
        this.sportContent = sportContent;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }
}
