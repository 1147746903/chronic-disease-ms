package com.comvee.cdms.dybloodsugar.vo;

public class DynamicBloodChartDataDietVO {

    private String time;
    private String dietContent;
    private Integer origin;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDietContent() {
        return dietContent;
    }

    public void setDietContent(String dietContent) {
        this.dietContent = dietContent;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }
}
