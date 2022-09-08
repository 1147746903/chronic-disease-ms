package com.comvee.cdms.dialogue.dto;

/**
 * @author ZhiGe
 * @description
 * @date 2018/1/30 14:50 create
 */
public class AddDialogueServiceDTO {

    /**
     * 患者id
     */
    private String memberId;
    /**\
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

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 发送者id
     */
    private String senderId;

    /**
     * 用户类型
     */
    private Integer ownerType;

    public Integer getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "AddDialogueServiceDTO{" +
                "memberId='" + memberId + '\'' +
                ", msg='" + msg + '\'' +
                ", textType=" + textType +
                ", attachUrl='" + attachUrl + '\'' +
                ", voiceLength=" + voiceLength +
                ", imgW='" + imgW + '\'' +
                ", imgH='" + imgH + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", senderId='" + senderId + '\'' +
                '}';
    }
}
