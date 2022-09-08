package com.comvee.cdms.dybloodsugar.vo;

/**
 * @author: chenhb
 * @description: 描述
 * @data: 2021/4/9 9:39
 **/
public class DynamicBloodSugarDailyTrendV2AvgTimeVO {

    /**
     * 时间区间
     */
    private String timeInterval;

    /**
     * 时段血糖平均值
     */
    private Double value;

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
