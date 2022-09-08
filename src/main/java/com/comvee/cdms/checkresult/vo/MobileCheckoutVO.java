package com.comvee.cdms.checkresult.vo;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class MobileCheckoutVO implements Serializable {
    private String logId;
    private String name;
    private String checkoutDt;
    private String url;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckoutDt() {
        return checkoutDt;
    }

    public void setCheckoutDt(String checkoutDt) {
        this.checkoutDt = checkoutDt;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
