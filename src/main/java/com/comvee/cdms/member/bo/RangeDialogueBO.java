package com.comvee.cdms.member.bo;

/**
 * @author: suyz
 * @date: 2018/11/8
 */
public class RangeDialogueBO {

    private String content;
    private String date;
    private String logId;
    private String name;
    private Integer textType;
    private String time;
    private String title;
    private RangeDialogueDataBO data;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RangeDialogueDataBO getData() {
        return data;
    }

    public void setData(RangeDialogueDataBO data) {
        this.data = data;
    }
}
