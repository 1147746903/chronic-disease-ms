package com.comvee.cdms.complication.model.vo;

/**
 * @author: suyz
 * @date: 2019/2/27
 */
public class ScreeningStatOrderVO {

    private long todayOrderNumber;
    private long weekOrderNumber;
    private long otherOrderNumber;

    public long getTodayOrderNumber() {
        return todayOrderNumber;
    }

    public void setTodayOrderNumber(long todayOrderNumber) {
        this.todayOrderNumber = todayOrderNumber;
    }

    public long getWeekOrderNumber() {
        return weekOrderNumber;
    }

    public void setWeekOrderNumber(long weekOrderNumber) {
        this.weekOrderNumber = weekOrderNumber;
    }

    public long getOtherOrderNumber() {
        return otherOrderNumber;
    }

    public void setOtherOrderNumber(long otherOrderNumber) {
        this.otherOrderNumber = otherOrderNumber;
    }
}
