package com.comvee.cdms.dybloodsugar.vo;

/**
 * 动态血糖基础指标对象
 */
public class DynamicBloodSugarIndexBaseVO {

    /**
     * 低血糖事件数量
     */
    private Integer eventCountOfLow;

    /**
     * 高血糖事件数量
     */
    private Integer eventCountOfHigh;
    /**
     * 正常血糖事件数量
     */
    private Integer eventCountOfNormal;
    /**
     * 低血糖时间占比
     */
    private Double awiTimeRateOfLow;
    /**
     * 高血糖时间占比
     */
    private Double awiTimeRateOfHigh;
    /**
     * 正常血糖时间占比
     */
    private Double awiTimeRateOfNormal;
    /**
     * 平均血糖
     */
    private Double avgNum;
    /**
     * 血糖水平标准差
     */
    private Double standardVal;
    /**
     * 血糖变异系数
     */
    private Double coefficientOfVariation;
    /**
     * 平均血糖波动
     */
    private Double meanAmplitudeOfGlycemicExcursion;
    /**
     * 预估糖化血红蛋白
     */
    private Double ghb;

    /**
     * 自定义低于指定血糖值占比
     */
    private Double customLessThanRatio;
    /**
     * 自定义高于指定血糖值占比
     */
    private Double customGreaterThanRatio;

    /**
     * 自定义低于指定血糖值次数
     */
    private Integer customLessThan;
    /**
     * 自定义高于指定血糖值次数
     */
    private Integer customGreaterThan;

    /**
     * 总数
     */
    private Integer totalEvent;

    public Integer getCustomLessThan() {
        return customLessThan;
    }

    public void setCustomLessThan(Integer customLessThan) {
        this.customLessThan = customLessThan;
    }

    public Integer getCustomGreaterThan() {
        return customGreaterThan;
    }

    public void setCustomGreaterThan(Integer customGreaterThan) {
        this.customGreaterThan = customGreaterThan;
    }

    public Integer getTotalEvent() {
        return totalEvent;
    }

    public void setTotalEvent(Integer totalEvent) {
        this.totalEvent = totalEvent;
    }

    public Double getCustomLessThanRatio() {
        return customLessThanRatio;
    }

    public void setCustomLessThanRatio(Double customLessThanRatio) {
        this.customLessThanRatio = customLessThanRatio;
    }

    public Double getCustomGreaterThanRatio() {
        return customGreaterThanRatio;
    }

    public void setCustomGreaterThanRatio(Double customGreaterThanRatio) {
        this.customGreaterThanRatio = customGreaterThanRatio;
    }

    public Integer getEventCountOfLow() {
        return eventCountOfLow;
    }

    public void setEventCountOfLow(Integer eventCountOfLow) {
        this.eventCountOfLow = eventCountOfLow;
    }

    public Integer getEventCountOfHigh() {
        return eventCountOfHigh;
    }

    public void setEventCountOfHigh(Integer eventCountOfHigh) {
        this.eventCountOfHigh = eventCountOfHigh;
    }

    public Integer getEventCountOfNormal() {
        return eventCountOfNormal;
    }

    public void setEventCountOfNormal(Integer eventCountOfNormal) {
        this.eventCountOfNormal = eventCountOfNormal;
    }

    public Double getAwiTimeRateOfLow() {
        return awiTimeRateOfLow;
    }

    public void setAwiTimeRateOfLow(Double awiTimeRateOfLow) {
        this.awiTimeRateOfLow = awiTimeRateOfLow;
    }

    public Double getAwiTimeRateOfHigh() {
        return awiTimeRateOfHigh;
    }

    public void setAwiTimeRateOfHigh(Double awiTimeRateOfHigh) {
        this.awiTimeRateOfHigh = awiTimeRateOfHigh;
    }

    public Double getAwiTimeRateOfNormal() {
        return awiTimeRateOfNormal;
    }

    public void setAwiTimeRateOfNormal(Double awiTimeRateOfNormal) {
        this.awiTimeRateOfNormal = awiTimeRateOfNormal;
    }

    public Double getAvgNum() {
        return avgNum;
    }

    public void setAvgNum(Double avgNum) {
        this.avgNum = avgNum;
    }

    public Double getStandardVal() {
        return standardVal;
    }

    public void setStandardVal(Double standardVal) {
        this.standardVal = standardVal;
    }

    public Double getCoefficientOfVariation() {
        return coefficientOfVariation;
    }

    public void setCoefficientOfVariation(Double coefficientOfVariation) {
        this.coefficientOfVariation = coefficientOfVariation;
    }

    public Double getMeanAmplitudeOfGlycemicExcursion() {
        return meanAmplitudeOfGlycemicExcursion;
    }

    public void setMeanAmplitudeOfGlycemicExcursion(Double meanAmplitudeOfGlycemicExcursion) {
        this.meanAmplitudeOfGlycemicExcursion = meanAmplitudeOfGlycemicExcursion;
    }

    public Double getGhb() {
        return ghb;
    }

    public void setGhb(Double ghb) {
        this.ghb = ghb;
    }

    @Override
    public String toString() {
        return "DynamicBloodSugarIndexBaseVO{" +
                "eventCountOfLow=" + eventCountOfLow +
                ", eventCountOfHigh=" + eventCountOfHigh +
                ", eventCountOfNormal=" + eventCountOfNormal +
                ", awiTimeRateOfLow=" + awiTimeRateOfLow +
                ", awiTimeRateOfHigh=" + awiTimeRateOfHigh +
                ", awiTimeRateOfNormal=" + awiTimeRateOfNormal +
                ", avgNum=" + avgNum +
                ", standardVal=" + standardVal +
                ", coefficientOfVariation=" + coefficientOfVariation +
                ", meanAmplitudeOfGlycemicExcursion=" + meanAmplitudeOfGlycemicExcursion +
                ", ghb=" + ghb +
                '}';
    }
}
