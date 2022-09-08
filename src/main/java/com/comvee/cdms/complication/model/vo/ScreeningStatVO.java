package com.comvee.cdms.complication.model.vo;

/**
 * @author: suyz
 * @date: 2019/2/27
 */
public class ScreeningStatVO {

    private long todayPreNumber;
    private long orderNumber;
    private long finishScreeningNumber;
    private long overdueNumber;
    private Long allNumber;

    public Long getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(Long allNumber) {
        this.allNumber = allNumber;
    }

    public long getTodayPreNumber() {
        return todayPreNumber;
    }

    public void setTodayPreNumber(long todayPreNumber) {
        this.todayPreNumber = todayPreNumber;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getFinishScreeningNumber() {
        return finishScreeningNumber;
    }

    public void setFinishScreeningNumber(long finishScreeningNumber) {
        this.finishScreeningNumber = finishScreeningNumber;
    }

    public long getOverdueNumber() {
        return overdueNumber;
    }

    public void setOverdueNumber(long overdueNumber) {
        this.overdueNumber = overdueNumber;
    }
}
