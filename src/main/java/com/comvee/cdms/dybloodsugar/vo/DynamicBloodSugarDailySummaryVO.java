package com.comvee.cdms.dybloodsugar.vo;

import java.util.List;

/**
 * 动态血糖每日总结返回对象
 */
public class DynamicBloodSugarDailySummaryVO extends DynamicBloodSugarIndexBaseVO{

    /**
     * 控制目标低值
     */
    private Double lowLineVal;
    /**
     * 控制目标高值
     */
    private Double highLineVal;

    /**
     * 图表值
     */
    private List<String> lineData;

    /**
     * 记录时间
     */
    private String recordDt;

    /**
     * 微信端图表数据
     */
    private List<List<Object>> wechatChartData;

    public List<List<Object>> getWechatChartData() {
        return wechatChartData;
    }

    public void setWechatChartData(List<List<Object>> wechatChartData) {
        this.wechatChartData = wechatChartData;
    }

    public Double getLowLineVal() {
        return lowLineVal;
    }

    public void setLowLineVal(Double lowLineVal) {
        this.lowLineVal = lowLineVal;
    }

    public Double getHighLineVal() {
        return highLineVal;
    }

    public void setHighLineVal(Double highLineVal) {
        this.highLineVal = highLineVal;
    }

    public List<String> getLineData() {
        return lineData;
    }

    public void setLineData(List<String> lineData) {
        this.lineData = lineData;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }
}
