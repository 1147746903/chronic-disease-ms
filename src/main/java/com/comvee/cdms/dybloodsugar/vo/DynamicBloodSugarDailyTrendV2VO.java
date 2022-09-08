package com.comvee.cdms.dybloodsugar.vo;

import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarStatBase;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * 动态血糖日趋势图 v2版本
 * @since v6.3.0
 */
public class DynamicBloodSugarDailyTrendV2VO extends DynamicBloodSugarIndexBaseVO implements DynamicBloodSugarStatBase {

    /**
     * 统计结果主键
     */
    private String sid;
    /**
     * 医生建议
     */
    private String adContent;

    /**
     * 日间血糖平均绝对差
     */
    private Double meanAbsoluteDifference;

    /***
     * 图表数据
     */
    private List<DynamicBloodChartDayItemVO> chartData;

    /**
     * 设置
     */
    private DynamicBloodSugarSettingsVO settings;

    /**
     * 时段血糖平均值数据
     */
    private List<DynamicBloodSugarDailyTrendV2AvgTimeVO> timeIntervalData;

    //供插值数据模拟
    private MultiValueMap<String,DYYPBloodSugarPO> dataSimulationList;

    public MultiValueMap<String, DYYPBloodSugarPO> getDataSimulationList() {
        return dataSimulationList;
    }

    public void setDataSimulationList(MultiValueMap<String, DYYPBloodSugarPO> dataSimulationList) {
        this.dataSimulationList = dataSimulationList;
    }

    public String getSid() {
        return sid;
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAdContent() {
        return adContent;
    }

    @Override
    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public Double getMeanAbsoluteDifference() {
        return meanAbsoluteDifference;
    }

    public void setMeanAbsoluteDifference(Double meanAbsoluteDifference) {
        this.meanAbsoluteDifference = meanAbsoluteDifference;
    }

    public List<DynamicBloodChartDayItemVO> getChartData() {
        return chartData;
    }

    public void setChartData(List<DynamicBloodChartDayItemVO> chartData) {
        this.chartData = chartData;
    }

    public DynamicBloodSugarSettingsVO getSettings() {
        return settings;
    }

    public void setSettings(DynamicBloodSugarSettingsVO settings) {
        this.settings = settings;
    }

    public List<DynamicBloodSugarDailyTrendV2AvgTimeVO> getTimeIntervalData() {
        return timeIntervalData;
    }

    public void setTimeIntervalData(List<DynamicBloodSugarDailyTrendV2AvgTimeVO> timeIntervalData) {
        this.timeIntervalData = timeIntervalData;
    }
}
