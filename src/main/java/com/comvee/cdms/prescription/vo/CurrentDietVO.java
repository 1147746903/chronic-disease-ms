package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

/**
 * 目前饮食内容
 */
public class CurrentDietVO implements Serializable {
    private String breakfastIngredients;
    private String lunchIngredients;
    private String dinnerIngredients;
    private String breakfastLunchIngredients;
    private String lunchDinnerIngredients;
    private String beforeSleepIngredients;

    public String getBreakfastIngredients() {
        return breakfastIngredients;
    }

    public void setBreakfastIngredients(String breakfastIngredients) {
        this.breakfastIngredients = breakfastIngredients;
    }

    public String getLunchIngredients() {
        return lunchIngredients;
    }

    public void setLunchIngredients(String lunchIngredients) {
        this.lunchIngredients = lunchIngredients;
    }

    public String getDinnerIngredients() {
        return dinnerIngredients;
    }

    public void setDinnerIngredients(String dinnerIngredients) {
        this.dinnerIngredients = dinnerIngredients;
    }

    public String getBreakfastLunchIngredients() {
        return breakfastLunchIngredients;
    }

    public void setBreakfastLunchIngredients(String breakfastLunchIngredients) {
        this.breakfastLunchIngredients = breakfastLunchIngredients;
    }

    public String getLunchDinnerIngredients() {
        return lunchDinnerIngredients;
    }

    public void setLunchDinnerIngredients(String lunchDinnerIngredients) {
        this.lunchDinnerIngredients = lunchDinnerIngredients;
    }

    public String getBeforeSleepIngredients() {
        return beforeSleepIngredients;
    }

    public void setBeforeSleepIngredients(String beforeSleepIngredients) {
        this.beforeSleepIngredients = beforeSleepIngredients;
    }

    @Override
    public String toString() {
        return "CurrentDietVO{" +
                "breakfastIngredients='" + breakfastIngredients + '\'' +
                ", lunchIngredients='" + lunchIngredients + '\'' +
                ", dinnerIngredients='" + dinnerIngredients + '\'' +
                ", breakfastLunchIngredients='" + breakfastLunchIngredients + '\'' +
                ", lunchDinnerIngredients='" + lunchDinnerIngredients + '\'' +
                ", beforeSleepIngredients='" + beforeSleepIngredients + '\'' +
                '}';
    }
}
