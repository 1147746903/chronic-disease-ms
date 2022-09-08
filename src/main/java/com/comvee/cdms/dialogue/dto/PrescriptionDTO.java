package com.comvee.cdms.dialogue.dto;

import java.io.Serializable;
/**
 * 
 * @author 李左河
 *
 */
public class PrescriptionDTO implements Serializable {
    /**{“title”:”管理处方”,”date”:”下发日期”,”time”:”下发时间”,”name”:”管理处方名称”,”content”:”文本文案”,
    ”url”:”html5详情页面Url”,”logId”:”记录唯一编号”,”textType”,0}**/

    private String title;
    private String date;
    private String time;
    private String name;
    private String content;
    private String url;
    private String logId;
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

    @Override
    public String toString() {
        return "PrescriptionDTO{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", logId='" + logId + '\'' +
                ", textType=" + textType +
                '}';
    }
}
