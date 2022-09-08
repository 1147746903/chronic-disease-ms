package com.comvee.cdms.dybloodsugar.vo;

import com.comvee.cdms.dybloodsugar.dto.DyBloodSugarValueDTO;
import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarStatBase;

import java.util.List;
import java.util.Map;

public class DynamicBloodSugarDynamicVO extends DynamicBloodSugarIndexBaseVO implements DynamicBloodSugarStatBase {

    private String sid;
    private String AdContent;

    /**
     * 每日体征列表
     */
    private List<DynamicBloodSugarDynamicItemVO> dataList;

    /**
     * 读数列表
     */
    private List<DyBloodSugarValueDTO> valueList;

    /**
     * 低葡萄糖的可能性,中位数葡萄糖,低于中位数的波动性
     */
    private Map<String, List> resultMap;

    /**
     * 下限的点
     */
    private List<List<String>> lowerLimitPointList;
    /**
     * 下限点的时间
     */
    private List<String> lowerLimitPointListDt;
    /**
     * 下限的点列表
     */
    private List<List<String>> upperLimitPointList;
    /**
     * 中位的点列表
     */
    private List<String> medianPointList;

    /**
     * 是否显示图表  true 显示  false 不显示
     */
    private Boolean showChart;

    /**
     * 控制目标低值
     */
    private Double lowLineVal;
    /**
     * 控制目标高值
     */
    private Double highLineVal;

    private List<List<Object>> per5Point;
    private List<List<Object>> per25Point;
    private List<List<Object>> per50Point;
    private List<List<Object>> per75Point;
    private List<List<Object>> per90Point;

    /**
     * 设置
     */
    private DynamicBloodSugarSettingsVO settings;

    /**
     * 时段血糖平均值数据
     */
    private List<DynamicBloodSugarDailyTrendV2AvgTimeVO> timeIntervalData;

    public String getSid() {
        return sid;
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAdContent() {
        return AdContent;
    }

    @Override
    public void setAdContent(String adContent) {
        AdContent = adContent;
    }

    public List<DynamicBloodSugarDynamicItemVO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DynamicBloodSugarDynamicItemVO> dataList) {
        this.dataList = dataList;
    }

    public List<List<String>> getLowerLimitPointList() {
        return lowerLimitPointList;
    }

    public void setLowerLimitPointList(List<List<String>> lowerLimitPointList) {
        this.lowerLimitPointList = lowerLimitPointList;
    }

    public List<List<String>> getUpperLimitPointList() {
        return upperLimitPointList;
    }

    public void setUpperLimitPointList(List<List<String>> upperLimitPointList) {
        this.upperLimitPointList = upperLimitPointList;
    }

    public List<String> getMedianPointList() {
        return medianPointList;
    }

    public void setMedianPointList(List<String> medianPointList) {
        this.medianPointList = medianPointList;
    }

    public Boolean getShowChart() {
        return showChart;
    }

    public void setShowChart(Boolean showChart) {
        this.showChart = showChart;
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

    public List<List<Object>> getPer5Point() {
        return per5Point;
    }

    public void setPer5Point(List<List<Object>> per5Point) {
        this.per5Point = per5Point;
    }

    public List<List<Object>> getPer25Point() {
        return per25Point;
    }

    public void setPer25Point(List<List<Object>> per25Point) {
        this.per25Point = per25Point;
    }

    public List<List<Object>> getPer50Point() {
        return per50Point;
    }

    public void setPer50Point(List<List<Object>> per50Point) {
        this.per50Point = per50Point;
    }

    public List<List<Object>> getPer75Point() {
        return per75Point;
    }

    public void setPer75Point(List<List<Object>> per75Point) {
        this.per75Point = per75Point;
    }

    public List<List<Object>> getPer90Point() {
        return per90Point;
    }

    public void setPer90Point(List<List<Object>> per90Point) {
        this.per90Point = per90Point;
    }

    public DynamicBloodSugarSettingsVO getSettings() {
        return settings;
    }

    public void setSettings(DynamicBloodSugarSettingsVO settings) {
        this.settings = settings;
    }

    public List<String> getLowerLimitPointListDt() {
        return lowerLimitPointListDt;
    }

    public void setLowerLimitPointListDt(List<String> lowerLimitPointListDt) {
        this.lowerLimitPointListDt = lowerLimitPointListDt;
    }

    public List<DyBloodSugarValueDTO> getValueList() {
        return valueList;
    }

    public void setValueList(List<DyBloodSugarValueDTO> valueList) {
        this.valueList = valueList;
    }

    public Map<String, List> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, List> resultMap) {
        this.resultMap = resultMap;
    }

    public List<DynamicBloodSugarDailyTrendV2AvgTimeVO> getTimeIntervalData() {
        return timeIntervalData;
    }

    public void setTimeIntervalData(List<DynamicBloodSugarDailyTrendV2AvgTimeVO> timeIntervalData) {
        this.timeIntervalData = timeIntervalData;
    }
}


