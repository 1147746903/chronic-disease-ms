package com.comvee.cdms.follow.po;

/**
 * @author: suyz
 * @date: 2018/10/24
 */
public class CountMonthFollowPO {

    private Integer month;
    private Long count;
    private String yearMonth;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
