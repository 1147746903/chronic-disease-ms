package com.comvee.cdms.clinicaldiagnosis.vo;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class MobileDocOrderVO implements Serializable {
    /**
     * 记录标识
     */
    private String logId; 
    /**
     * 医嘱名称
     */
    private String name; 
    /**
     * 医嘱类别 1长期 2短期
     */
    private Integer dateType;
    /**
     * 开始时间
     */
    private String orderStartDt;
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

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public String getOrderStartDt() {
        return orderStartDt;
    }

    public void setOrderStartDt(String orderStartDt) {
        this.orderStartDt = orderStartDt;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
