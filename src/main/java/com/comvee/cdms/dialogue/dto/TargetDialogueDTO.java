package com.comvee.cdms.dialogue.dto;

import java.io.Serializable;
import java.util.Map;

/**
 *  @author 李左河
 * 控制目标消息model-dataStrut
 */
public class TargetDialogueDTO implements Serializable {
    /**{“title”:”控制目标”,”date”:”设置日期”,”time”:”设置时间”,”name”:”方案名称”,”content”:”文本文案”,
     ”data”:”控制目标数据结构”,”logId”:”记录唯一编号”,”textType”,0}**/

    private String title;
    private String date;
    private String time;
    private String name;
    private String content;
    private Map<String,Object> data;
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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
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
        return "TargetDialogueDTO{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", data=" + data +
                ", logId='" + logId + '\'' +
                ", textType=" + textType +
                '}';
    }
}
