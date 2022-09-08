package com.comvee.cdms.prescription.bo;

/**
 * @author 李左河
 * @date 2018/8/15 14:48.
 */
public class PrescriptionHandDownBO {
    @Override
    public String toString() {
        return "PrescriptionHandDownBO{}";
    }

    /**
     * 管理处方
     */
    private String title;
    /**
     * 下发日期
     */
    private String date;
    /**
     * 下发时间
     */
    private String time;
    /**
     * 管理处方名称
     */
    private String name;
    /**
     * 文本文案
     */
    private String content;
    /**
     * html5详情页面Url
     */
    private String url;
    /**
     * 记录唯一编号
     */
    private String logId;
    /**
     * 0
     */
    private Integer textType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
    }
}
