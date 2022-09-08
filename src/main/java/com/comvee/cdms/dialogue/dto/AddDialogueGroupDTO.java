package com.comvee.cdms.dialogue.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: suyz
 * @date: 2019/1/2
 */
public class AddDialogueGroupDTO {

    @NotNull
    private Integer msgType;
    private String textContent;
    /**
     * 附件链接
     */
    private String attachUrl;
    /**
     * 语音长度 单位:秒
     */
    private Long voiceLength;
    /**
     * 图片宽度
     */
    private String imgW;
    /**
     * 图片高度
     */
    private String imgH;

    @NotEmpty
    private String sendGroupJson;

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public Long getVoiceLength() {
        return voiceLength;
    }

    public void setVoiceLength(Long voiceLength) {
        this.voiceLength = voiceLength;
    }

    public String getImgW() {
        return imgW;
    }

    public void setImgW(String imgW) {
        this.imgW = imgW;
    }

    public String getImgH() {
        return imgH;
    }

    public void setImgH(String imgH) {
        this.imgH = imgH;
    }

    public String getSendGroupJson() {
        return sendGroupJson;
    }

    public void setSendGroupJson(String sendGroupJson) {
        this.sendGroupJson = sendGroupJson;
    }
}
