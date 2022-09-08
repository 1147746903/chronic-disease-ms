package com.comvee.cdms.dybloodsugar.vo;

/**
 * 日均血糖平均绝对差 列表细项
 */
public class DynamicBloodSugarMeanAbsoluteDeviationItemVO {

    private String today;
    private String yesterday;
    private Double value;

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
