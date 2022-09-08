package com.comvee.cdms.clinicaldiagnosis.vo;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class MobileUseDrugVO implements Serializable{
    /**
     * 记录唯一标识
     */
    private String logId; 
    /**
     * 记录类型 1长期 2临时
     */
    private Integer dateType;
    /**
     * 药品名称
     */
    private String name;
    /**
     * 用药日期
     */
    private String useDt;
    /**
     * html5页面
     */
    private String url;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseDt() {
        return useDt;
    }

    public void setUseDt(String useDt) {
        this.useDt = useDt;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
