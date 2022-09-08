package com.comvee.cdms.dialogue.dto;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class MonitorPlanDTO implements Serializable {
    /**{“title”:”监测方案”,”date”:”设置日期”,”time”:”设置时间”,”name”:”方案名称”,
     * ”content”:”文本文案”,”logId”:”记录唯一编号”,”textType”,0}*/
    /**
     * 监测方案
     */
    private String title;
    /**
     * 设置日期
     */
    private String date;
    /**
     * 设置时间
     */
    private String time;
    /**
     * 方案名称
     */
    private String name;
    /**
     * 文本文案
     */
    private String content;
    /**
     * 记录唯一编号
     */
    private String logId;
    /**
     * 保留类型
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
        return "DialogueMonitorPlanBO{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", logId='" + logId + '\'' +
                ", textType=" + textType +
                '}';
    }
}
