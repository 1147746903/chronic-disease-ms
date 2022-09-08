package com.comvee.cdms.follow.model;

import java.io.Serializable;
/**
 * 
 * @author 李左河
 *
 */
public class MobileFollowModel implements Serializable {
    /**
     * 记录唯一标识
     */
     private String logId; 
    /**
     * 随访类别
     */
     private Integer type; 
    /**
     * 随访人
     */
     private String doctorName; 
    /**
     * 随访日期
     */
     private String followUpDt; 
    /**
     * 随访详情Html5 Url
     */
     private String url;
    /**
     * 1已完成 0未完成
     */
    private Integer dealStatus;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getFollowUpDt() {
        return followUpDt;
    }

    public void setFollowUpDt(String followUpDt) {
        this.followUpDt = followUpDt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }
}
