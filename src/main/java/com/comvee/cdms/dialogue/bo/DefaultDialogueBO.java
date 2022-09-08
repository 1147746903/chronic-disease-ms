package com.comvee.cdms.dialogue.bo;

/**
 * @author: suyz
 * @date: 2018/10/29
 */
public class DefaultDialogueBO {

    private Integer textType;
    private String content;
    private String sid;

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
