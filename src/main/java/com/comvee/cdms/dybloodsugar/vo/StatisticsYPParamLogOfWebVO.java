package com.comvee.cdms.dybloodsugar.vo;

import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;

import java.io.Serializable;
import java.util.List;

/**
 * @author linyudui
 */
public class StatisticsYPParamLogOfWebVO implements Serializable {
    private String avgNum;
    private Integer eventCountOfLow;
    private String avgAwiTimeOfLow;
    private String standardVal;
    private Integer eventCountOfHigh;
    private Double avgAwiTimeOfHigh;
    private Integer eventCountOfNormal;
    private Double avgAwiTimeOfNormal;
    private String awiTimeRateOfLow;
    private String awiTimeRateOfHigh;
    private String awiTimeRateOfNormal;
    private String coefficientOfVariation;
    private String awiTimeRateOf3_9;
    private String awiTimeRateOf13_9;
    private String meanAmplitudeOfGlycemicExcursion;
    private String awiTimeRateOf4_0;
    private Double lowLineVal;
    private Double highLineVal;
    private Integer chartShow;
    private List<String> lineData;
    private String recordDt;
    private List<List<Object>> lineDataOfDay;
    private String ghb;
    private List<DYYPBloodSugarPO> dataSource;

    public String getAvgNum() {
        return avgNum;
    }

    public void setAvgNum(String avgNum) {
        this.avgNum = avgNum;
    }

    public Integer getEventCountOfLow() {
        return eventCountOfLow;
    }

    public void setEventCountOfLow(Integer eventCountOfLow) {
        this.eventCountOfLow = eventCountOfLow;
    }

    public String getAvgAwiTimeOfLow() {
        return avgAwiTimeOfLow;
    }

    public void setAvgAwiTimeOfLow(String avgAwiTimeOfLow) {
        this.avgAwiTimeOfLow = avgAwiTimeOfLow;
    }

    public void setStandardVal(String standardVal) {
        this.standardVal = standardVal;
    }

    public String getStandardVal() {
        return standardVal;
    }

    public void setEventCountOfHigh(Integer eventCountOfHigh) {
        this.eventCountOfHigh = eventCountOfHigh;
    }

    public Integer getEventCountOfHigh() {
        return eventCountOfHigh;
    }

    public void setAvgAwiTimeOfHigh(Double avgAwiTimeOfHigh) {
        this.avgAwiTimeOfHigh = avgAwiTimeOfHigh;
    }

    public Double getAvgAwiTimeOfHigh() {
        return avgAwiTimeOfHigh;
    }

    public void setEventCountOfNormal(Integer eventCountOfNormal) {
        this.eventCountOfNormal = eventCountOfNormal;
    }

    public Integer getEventCountOfNormal() {
        return eventCountOfNormal;
    }

    public void setAvgAwiTimeOfNormal(Double avgAwiTimeOfNormal) {
        this.avgAwiTimeOfNormal = avgAwiTimeOfNormal;
    }

    public Double getAvgAwiTimeOfNormal() {
        return avgAwiTimeOfNormal;
    }

    public void setAwiTimeRateOfLow(String awiTimeRateOfLow) {
        this.awiTimeRateOfLow = awiTimeRateOfLow;
    }

    public String getAwiTimeRateOfLow() {
        return awiTimeRateOfLow;
    }

    public void setAwiTimeRateOfHigh(String awiTimeRateOfHigh) {
        this.awiTimeRateOfHigh = awiTimeRateOfHigh;
    }

    public String getAwiTimeRateOfHigh() {
        return awiTimeRateOfHigh;
    }

    public void setAwiTimeRateOfNormal(String awiTimeRateOfNormal) {
        this.awiTimeRateOfNormal = awiTimeRateOfNormal;
    }

    public String getAwiTimeRateOfNormal() {
        return awiTimeRateOfNormal;
    }

    public void setCoefficientOfVariation(String coefficientOfVariation) {
        this.coefficientOfVariation = coefficientOfVariation;
    }

    public String getCoefficientOfVariation() {
        return coefficientOfVariation;
    }

    public void setAwiTimeRateOf3_9(String awiTimeRateOf3_9) {
        this.awiTimeRateOf3_9 = awiTimeRateOf3_9;
    }

    public String getAwiTimeRateOf3_9() {
        return awiTimeRateOf3_9;
    }

    public void setAwiTimeRateOf13_9(String awiTimeRateOf13_9) {
        this.awiTimeRateOf13_9 = awiTimeRateOf13_9;
    }

    public String getAwiTimeRateOf13_9() {
        return awiTimeRateOf13_9;
    }

    public void setMeanAmplitudeOfGlycemicExcursion(String meanAmplitudeOfGlycemicExcursion) {
        this.meanAmplitudeOfGlycemicExcursion = meanAmplitudeOfGlycemicExcursion;
    }

    public String getMeanAmplitudeOfGlycemicExcursion() {
        return meanAmplitudeOfGlycemicExcursion;
    }

    public void setAwiTimeRateOf4_0(String awiTimeRateOf4_0) {
        this.awiTimeRateOf4_0 = awiTimeRateOf4_0;
    }

    public String getAwiTimeRateOf4_0() {
        return awiTimeRateOf4_0;
    }

    public void setLowLineVal(Double lowLineVal) {
        this.lowLineVal = lowLineVal;
    }

    public double getLowLineVal() {
        return lowLineVal;
    }

    public void setHighLineVal(Double highLineVal) {
        this.highLineVal = highLineVal;
    }

    public double getHighLineVal() {
        return highLineVal;
    }

    public void setChartShow(Integer chartShow) {
        this.chartShow = chartShow;
    }

    public Integer getChartShow() {
        return chartShow;
    }

    public List<String> getLineData() {
        return lineData;
    }

    public void setLineData(List<String> lineData) {
        this.lineData = lineData;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setLineDataOfDay(List<List<Object>> lineDataOfDay) {
        this.lineDataOfDay = lineDataOfDay;
    }

    public List<List<Object>> getLineDataOfDay() {
        return lineDataOfDay;
    }

    public void setGhb(String ghb) {
        this.ghb = ghb;
    }

    public String getGhb() {
        return ghb;
    }

    public void setDataSource(List<DYYPBloodSugarPO> dataSource) {
        this.dataSource = dataSource;
    }

    public List<DYYPBloodSugarPO> getDataSource() {
        return dataSource;
    }
}
