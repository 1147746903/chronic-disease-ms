package com.comvee.cdms.complication.model.vo;

/**
 * @author: suyz
 * @date: 2019/2/27
 */
public class ScreeningStatFinishVO {

    private long todayFinishNumber;
    private long weekFinishNumber;
    private long otherFinishNumber;

    public long getTodayFinishNumber() {
        return todayFinishNumber;
    }

    public void setTodayFinishNumber(long todayFinishNumber) {
        this.todayFinishNumber = todayFinishNumber;
    }

    public long getWeekFinishNumber() {
        return weekFinishNumber;
    }

    public void setWeekFinishNumber(long weekFinishNumber) {
        this.weekFinishNumber = weekFinishNumber;
    }

    public long getOtherFinishNumber() {
        return otherFinishNumber;
    }

    public void setOtherFinishNumber(long otherFinishNumber) {
        this.otherFinishNumber = otherFinishNumber;
    }
}
