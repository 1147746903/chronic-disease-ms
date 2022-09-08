package com.comvee.cdms.statistics.vo;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/19
 */
public class OrderChartVO implements Serializable{

    private static final long serialVersionUID = 1028700577720753494L;

    private String month;
    private Long orderTotal;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Long orderTotal) {
        this.orderTotal = orderTotal;
    }
}
