package com.comvee.cdms.dialogue.dto;

import java.io.Serializable;

/**
 * 随访消息模型类
 * @author 林雨堆
 * @date 2018-03-27 15:30
 */
public class FollowDialogueDTO implements Serializable {
    /**
     * {"id":"随访主表主键",“title”:”随访下发通知”,”date”:”下发日期”,”time”:”下发时间”,”name”:”随访类型名称”,”content”:”文本文案”,
     * ”url”:”html5详情页面Url”,”logId”:”记录唯一编号”,”textType”,1}
     */

    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FollowDialogueDTO{" +
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
