package com.comvee.cdms.sign.vo;

/**
 * @author wyc
 * @date 2020/3/4 17:12
 */
public class TodayBloodNumVO {

    /**
     * 今日全院血糖数量
     */
    private Long allBloodSugarNumber;
    /**
     * 今日异常血糖高危数量
     */
    private Long highRiskNumber;

    /**
     * 今日已经监测次数
     */
    private Integer todayTrueMonitorNumber;
    /**
     * 今日未监测次数
     */
    private Integer todayfalseMonitorNumber;

    public Long getAllBloodSugarNumber() {
        return allBloodSugarNumber;
    }

    public void setAllBloodSugarNumber(Long allBloodSugarNumber) {
        this.allBloodSugarNumber = allBloodSugarNumber;
    }

    public Long getHighRiskNumber() {
        return highRiskNumber;
    }

    public void setHighRiskNumber(Long highRiskNumber) {
        this.highRiskNumber = highRiskNumber;
    }

    public Integer getTodayTrueMonitorNumber() {
        return todayTrueMonitorNumber;
    }

    public void setTodayTrueMonitorNumber(Integer todayTrueMonitorNumber) {
        this.todayTrueMonitorNumber = todayTrueMonitorNumber;
    }

    public Integer getTodayfalseMonitorNumber() {
        return todayfalseMonitorNumber;
    }

    public void setTodayfalseMonitorNumber(Integer todayfalseMonitorNumber) {
        this.todayfalseMonitorNumber = todayfalseMonitorNumber;
    }
}
