package com.comvee.cdms.packages.po;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/24
 */
public class PackageMonthPricePO implements Serializable{

    private static final long serialVersionUID = 424267129062405870L;

    private String month;
    private long priceTotal;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(long priceTotal) {
        this.priceTotal = priceTotal;
    }
}
