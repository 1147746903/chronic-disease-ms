package com.comvee.cdms.statistics.vo;

import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public class BloodSugarChartDataVO {

    private Long memberNum;
    private Long sugarNum;
    private Double highRate;
    private Double normalRate;
    private Double lowRate;
    private Double max;
    private Double min;
    private Double emptyAvgNum;
    private Double fullAvgNum;
    private Map<String,Object> dataMap;

    public Long getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Long memberNum) {
        this.memberNum = memberNum;
    }

    public Long getSugarNum() {
        return sugarNum;
    }

    public void setSugarNum(Long sugarNum) {
        this.sugarNum = sugarNum;
    }

    public Double getHighRate() {
        return highRate;
    }

    public void setHighRate(Double highRate) {
        this.highRate = highRate;
    }

    public Double getNormalRate() {
        return normalRate;
    }

    public void setNormalRate(Double normalRate) {
        this.normalRate = normalRate;
    }

    public Double getLowRate() {
        return lowRate;
    }

    public void setLowRate(Double lowRate) {
        this.lowRate = lowRate;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getEmptyAvgNum() {
        return emptyAvgNum;
    }

    public void setEmptyAvgNum(Double emptyAvgNum) {
        this.emptyAvgNum = emptyAvgNum;
    }

    public Double getFullAvgNum() {
        return fullAvgNum;
    }

    public void setFullAvgNum(Double fullAvgNum) {
        this.fullAvgNum = fullAvgNum;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
