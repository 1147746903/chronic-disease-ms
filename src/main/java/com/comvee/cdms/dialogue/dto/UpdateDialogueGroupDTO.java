package com.comvee.cdms.dialogue.dto;

/**
 * @author: suyz
 * @date: 2019/1/2
 */
public class UpdateDialogueGroupDTO {

    private String sid;
    private Integer sendStatus;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }
}
