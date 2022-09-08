package com.comvee.cdms.dybloodsugar.vo;

import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarChartDataWorkRest;
import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarStatBase;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * 动态血糖日趋势图
 */
public class DynamicBloodSugarDailyTrendVO extends DynamicBloodSugarIndexBaseVO implements DynamicBloodSugarStatBase, DynamicBloodSugarChartDataWorkRest {

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
     * 数据集合
     */
    private List<DYYPBloodSugarPO> dataList;

    /**
     * 统计结果主键
     */
    private String sid;
    /**
     * 医生建议
     */
    private String adContent;

    /**
     * 微信端图表数据
     */
    private List<List<Object>> wechatChartData;

    /**
     * 设置
     */
    private DynamicBloodSugarSettingsVO settings;

    private DynamicBloodChartDataDietVO breakfastTime;
    private DynamicBloodChartDataDietVO lunchTime;
    private DynamicBloodChartDataDietVO dinnerTime;
    private DynamicBloodChartDataDietVO sleepTime;
    private String date;

    //供插值数据模拟
    private MultiValueMap<String,DYYPBloodSugarPO> dataSimulationList;

    public MultiValueMap<String, DYYPBloodSugarPO> getDataSimulationList() {
        return dataSimulationList;
    }

    public void setDataSimulationList(MultiValueMap<String, DYYPBloodSugarPO> dataSimulationList) {
        this.dataSimulationList = dataSimulationList;
    }
    @Override
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public List<DYYPBloodSugarPO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DYYPBloodSugarPO> dataList) {
        this.dataList = dataList;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public List<List<Object>> getWechatChartData() {
        return wechatChartData;
    }

    public void setWechatChartData(List<List<Object>> wechatChartData) {
        this.wechatChartData = wechatChartData;
    }

    public DynamicBloodSugarSettingsVO getSettings() {
        return settings;
    }

    public void setSettings(DynamicBloodSugarSettingsVO settings) {
        this.settings = settings;
    }

    public DynamicBloodChartDataDietVO getBreakfastTime() {
        return breakfastTime;
    }

    @Override
    public void setBreakfastTime(DynamicBloodChartDataDietVO breakfastTime) {
        this.breakfastTime = breakfastTime;
    }

    public DynamicBloodChartDataDietVO getLunchTime() {
        return lunchTime;
    }

    @Override
    public void setLunchTime(DynamicBloodChartDataDietVO lunchTime) {
        this.lunchTime = lunchTime;
    }

    public DynamicBloodChartDataDietVO getDinnerTime() {
        return dinnerTime;
    }

    @Override
    public void setDinnerTime(DynamicBloodChartDataDietVO dinnerTime) {
        this.dinnerTime = dinnerTime;
    }

    public DynamicBloodChartDataDietVO getSleepTime() {
        return sleepTime;
    }

    @Override
    public void setSleepTime(DynamicBloodChartDataDietVO sleepTime) {
        this.sleepTime = sleepTime;
    }
}
