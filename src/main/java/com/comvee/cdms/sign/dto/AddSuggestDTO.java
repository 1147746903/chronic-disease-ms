package com.comvee.cdms.sign.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public class AddSuggestDTO {


    /**
     * 体征记录id
     * sign_id
     */
    @NotEmpty
    private String signId;

    /**
     * 建议内容
     * suggest_text
     */
    @NotEmpty
    private String suggestText;


    /**
     * doctor_id
     */
    @NotEmpty
    private String doctorId;

    private String senderId;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getSuggestText() {
        return suggestText;
    }

    public void setSuggestText(String suggestText) {
        this.suggestText = suggestText;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
