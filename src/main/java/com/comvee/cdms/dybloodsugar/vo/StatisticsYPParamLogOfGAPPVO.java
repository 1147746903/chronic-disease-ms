package com.comvee.cdms.dybloodsugar.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class StatisticsYPParamLogOfGAPPVO implements Serializable {
    private String avgNum;

    private Integer eventCountOfLow;

    private String avgAwiTimeOfLow;

    private YPCharDataOfXYVO chartData;

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
    private List<String> dateOfLast14Day;
    private Double lowLineVal;
    private Double highLineVal;
    private List<Map<String,Object>> daySugarAvgDiffValMap;
    private Integer chartShow;
    private String recordDt;
    private List<StatisticsYPParamLogOfGAPPVO> listChartData;
    private String ghb;
    private String sid;
    private String adContent;
    private String doctorId;
    private Integer reportType;

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

    public YPCharDataOfXYVO getChartData() {
        return chartData;
    }

    public void setChartData(YPCharDataOfXYVO chartData) {
        this.chartData = chartData;
    }

    public String getStandardVal() {
        return standardVal;
    }

    public void setStandardVal(String standardVal) {
        this.standardVal = standardVal;
    }

    public Integer getEventCountOfHigh() {
        return eventCountOfHigh;
    }

    public void setEventCountOfHigh(Integer eventCountOfHigh) {
        this.eventCountOfHigh = eventCountOfHigh;
    }

    public Double getAvgAwiTimeOfHigh() {
        return avgAwiTimeOfHigh;
    }

    public void setAvgAwiTimeOfHigh(Double avgAwiTimeOfHigh) {
        this.avgAwiTimeOfHigh = avgAwiTimeOfHigh;
    }

    public Integer getEventCountOfNormal() {
        return eventCountOfNormal;
    }

    public void setEventCountOfNormal(Integer eventCountOfNormal) {
        this.eventCountOfNormal = eventCountOfNormal;
    }

    public Double getAvgAwiTimeOfNormal() {
        return avgAwiTimeOfNormal;
    }

    public void setAvgAwiTimeOfNormal(Double avgAwiTimeOfNormal) {
        this.avgAwiTimeOfNormal = avgAwiTimeOfNormal;
    }

    public String getAwiTimeRateOfLow() {
        return awiTimeRateOfLow;
    }

    public void setAwiTimeRateOfLow(String awiTimeRateOfLow) {
        this.awiTimeRateOfLow = awiTimeRateOfLow;
    }

    public String getAwiTimeRateOfHigh() {
        return awiTimeRateOfHigh;
    }

    public void setAwiTimeRateOfHigh(String awiTimeRateOfHigh) {
        this.awiTimeRateOfHigh = awiTimeRateOfHigh;
    }

    public String getAwiTimeRateOfNormal() {
        return awiTimeRateOfNormal;
    }

    public void setAwiTimeRateOfNormal(String awiTimeRateOfNormal) {
        this.awiTimeRateOfNormal = awiTimeRateOfNormal;
    }

    public String getCoefficientOfVariation() {
        return coefficientOfVariation;
    }

    public void setCoefficientOfVariation(String coefficientOfVariation) {
        this.coefficientOfVariation = coefficientOfVariation;
    }

    public String getAwiTimeRateOf3_9() {
        return awiTimeRateOf3_9;
    }

    public void setAwiTimeRateOf3_9(String awiTimeRateOf3_9) {
        this.awiTimeRateOf3_9 = awiTimeRateOf3_9;
    }

    public String getAwiTimeRateOf13_9() {
        return awiTimeRateOf13_9;
    }

    public void setAwiTimeRateOf13_9(String awiTimeRateOf13_9) {
        this.awiTimeRateOf13_9 = awiTimeRateOf13_9;
    }

    public String getMeanAmplitudeOfGlycemicExcursion() {
        return meanAmplitudeOfGlycemicExcursion;
    }

    public void setMeanAmplitudeOfGlycemicExcursion(String meanAmplitudeOfGlycemicExcursion) {
        this.meanAmplitudeOfGlycemicExcursion = meanAmplitudeOfGlycemicExcursion;
    }

    public String getAwiTimeRateOf4_0() {
        return awiTimeRateOf4_0;
    }

    public void setAwiTimeRateOf4_0(String awiTimeRateOf4_0) {
        this.awiTimeRateOf4_0 = awiTimeRateOf4_0;
    }

    public List<String> getDateOfLast14Day() {
        return dateOfLast14Day;
    }

    public void setDateOfLast14Day(List<String> dateOfLast14Day) {
        this.dateOfLast14Day = dateOfLast14Day;
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

    public List<Map<String, Object>> getDaySugarAvgDiffValMap() {
        return daySugarAvgDiffValMap;
    }

    public void setDaySugarAvgDiffValMap(List<Map<String, Object>> daySugarAvgDiffValMap) {
        this.daySugarAvgDiffValMap = daySugarAvgDiffValMap;
    }

    public Integer getChartShow() {
        return chartShow;
    }

    public void setChartShow(Integer chartShow) {
        this.chartShow = chartShow;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public List<StatisticsYPParamLogOfGAPPVO> getListChartData() {
        return listChartData;
    }

    public void setListChartData(List<StatisticsYPParamLogOfGAPPVO> listChartData) {
        this.listChartData = listChartData;
    }

    public String getGhb() {
        return ghb;
    }

    public void setGhb(String ghb) {
        this.ghb = ghb;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }
}
