package com.comvee.cdms.dialogue.dto;

public class ArticleDialogueDTO {
    /**
     * 文章编号
     */
    private String sid;
    /**
     * 标题图片地址
     */
    private String src;
    /**
     * 患教知识标题
     */
    private String title;
    /**
     * 文章简介
     */
    private String content;
    /**
     * 保留属性
     */
    private Integer textType;
    /**
     * html5链接地址
     */
    private String url;
    private String diaUrl;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setDiaUrl(String diaUrl) {
        this.diaUrl = diaUrl;
    }

    public String getDiaUrl() {
        return diaUrl;
    }

    @Override
    public String toString() {
        return "ArticleDialogueDTO{" +
                "sid='" + sid + '\'' +
                ", src='" + src + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", textType=" + textType +
                ", url='" + url + '\'' +
                ", diaUrl='" + diaUrl + '\'' +
                '}';
    }
}
