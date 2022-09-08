package com.comvee.cdms.follow.dto;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: suyz
 * @date: 2019/7/24
 */
public class ScreeningAddFollowupCareDTO {


    /**
     * 关怀内容
     * care_content
     */
    @NotEmpty(message = "关怀内容不能为空")
    @Length(max = 1024)
    private String careContent;

    /**
     * 发送日期
     * send_date
     */
    @NotEmpty(message = "发送日期不能为空")
    private String sendDate;

    /**
     * 发送者信息
     * sender_info
     */
    @NotEmpty(message = "发送者信息不能为空")
    private String senderInfo;

    /**
     * 接收者类型 1 指定患者 2 全部患者
     * receive_type
     */
    @NotNull(message = "接受者类型不能为空")
    private Integer receiveType;

    @NotEmpty(message = "身份证不能为空")
    private String idCard;

    public String getCareContent() {
        return careContent;
    }

    public void setCareContent(String careContent) {
        this.careContent = careContent;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(String senderInfo) {
        this.senderInfo = senderInfo;
    }

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
