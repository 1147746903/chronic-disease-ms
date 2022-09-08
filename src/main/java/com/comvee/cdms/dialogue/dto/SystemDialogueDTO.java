package com.comvee.cdms.dialogue.dto;

import java.io.Serializable;

/**
 * @author: 苏友智
 */
public class SystemDialogueDTO implements Serializable {
    private String title;
    private String content;
    private Integer textType;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
    }

    @Override
    public String toString() {
        return "SystemDialogueDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", textType='" + textType + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
