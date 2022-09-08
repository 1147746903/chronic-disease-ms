package com.comvee.cdms.dialogue.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author ZhiGe
 * @description
 * @date 2018/1/30 14:50 create
 */
public class AddDialogueMemberDTO {

    /**
     * 医生id
     */
    @NotEmpty
    private String doctorId;

    /**
     * 消息内容
     */
    private String msg;
    /**
     * 消息类型 1 文本 2 图片 3 语音
     */
    private Integer textType;
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



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
