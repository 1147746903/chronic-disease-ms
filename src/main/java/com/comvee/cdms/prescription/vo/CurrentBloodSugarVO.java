package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

/**
 * 患者今日血糖情况
 */
public class CurrentBloodSugarVO implements Serializable {

    /**
     * 凌晨血糖
     */
    private Double beforedawnSugar;
    /**
     * 空腹血糖
     */
    private Double beforeBreakfastSugar;
    /**
     * 早餐后血糖
     */
    private Double afterBreakfastSugar;
    /**
     * 午餐前血糖
     */
    private Double beforeLunchSugar;
    /**
     * 午餐后血糖
     */
    private Double afterLunchSugar;
    /**
     * 晚餐前血糖
     */
    private Double beforeDinnerSugar;
    /**
     * 晚餐后血糖
     */
    private Double afterDinnerSugar;
    /**
     * 睡前血糖
     */
    private Double beforeSleepSugar;

    public Double getBeforedawnSugar() {
        return beforedawnSugar;
    }

    public void setBeforedawnSugar(Double beforedawnSugar) {
        this.beforedawnSugar = beforedawnSugar;
    }

    public Double getBeforeBreakfastSugar() {
        return beforeBreakfastSugar;
    }

    public void setBeforeBreakfastSugar(Double beforeBreakfastSugar) {
        this.beforeBreakfastSugar = beforeBreakfastSugar;
    }

    public Double getAfterBreakfastSugar() {
        return afterBreakfastSugar;
    }

    public void setAfterBreakfastSugar(Double afterBreakfastSugar) {
        this.afterBreakfastSugar = afterBreakfastSugar;
    }

    public Double getBeforeLunchSugar() {
        return beforeLunchSugar;
    }

    public void setBeforeLunchSugar(Double beforeLunchSugar) {
        this.beforeLunchSugar = beforeLunchSugar;
    }

    public Double getAfterLunchSugar() {
        return afterLunchSugar;
    }

    public void setAfterLunchSugar(Double afterLunchSugar) {
        this.afterLunchSugar = afterLunchSugar;
    }

    public Double getBeforeDinnerSugar() {
        return beforeDinnerSugar;
    }

    public void setBeforeDinnerSugar(Double beforeDinnerSugar) {
        this.beforeDinnerSugar = beforeDinnerSugar;
    }

    public Double getAfterDinnerSugar() {
        return afterDinnerSugar;
    }

    public void setAfterDinnerSugar(Double afterDinnerSugar) {
        this.afterDinnerSugar = afterDinnerSugar;
    }

    public Double getBeforeSleepSugar() {
        return beforeSleepSugar;
    }

    public void setBeforeSleepSugar(Double beforeSleepSugar) {
        this.beforeSleepSugar = beforeSleepSugar;
    }

    @Override
    public String toString() {
        return "CurrentBloodSugarVO{" +
                "beforedawnSugar=" + beforedawnSugar +
                ", beforeBreakfastSugar=" + beforeBreakfastSugar +
                ", afterBreakfastSugar=" + afterBreakfastSugar +
                ", beforeLunchSugar=" + beforeLunchSugar +
                ", afterLunchSugar=" + afterLunchSugar +
                ", beforeDinnerSugar=" + beforeDinnerSugar +
                ", afterDinnerSugar=" + afterDinnerSugar +
                ", beforeSleepSugar=" + beforeSleepSugar +
                '}';
    }
}
