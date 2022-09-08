package com.comvee.cdms.dialogue.dto;

/**
 * @author: suyz
 * @date: 2018/10/25
 */
public class MemberDialogueLatestDTO {

    private Long refreshTimeStamp;
    private Integer beDelete;
    private String memberId;

    public Long getRefreshTimeStamp() {
        return refreshTimeStamp;
    }

    public void setRefreshTimeStamp(Long refreshTimeStamp) {
        this.refreshTimeStamp = refreshTimeStamp;
    }

    public Integer getBeDelete() {
        return beDelete;
    }

    public void setBeDelete(Integer beDelete) {
        this.beDelete = beDelete;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
