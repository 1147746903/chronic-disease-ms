package com.comvee.cdms.checkresult.vo;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class MobileInspectionVO implements Serializable {
    /**
     * 检查名称
     */
    private String name;
    /**
     * 检查时间
     */
    private String inspectDt;
    /**
     * 检查记录唯一标识
     */
    private String logId;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInspectDt() {
        return inspectDt;
    }

    public void setInspectDt(String inspectDt) {
        this.inspectDt = inspectDt;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
