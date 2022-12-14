package com.comvee.cdms.remind.dto;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_member_remind_latest
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class UpdateMemberRemindLatestDTO {


    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 提醒消息
     * remind_message
     */
    private String remindMessage;

    /**
     * 未读数
     * unreada_num
     */
    private Integer unReadNum;

    /**
     * timestamp
     */
    private String timeStamp;

    private Integer resetUnReadNum;

    public Integer getResetUnReadNum() {
        return resetUnReadNum;
    }

    public void setResetUnReadNum(Integer resetUnReadNum) {
        this.resetUnReadNum = resetUnReadNum;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRemindMessage() {
        return remindMessage;
    }

    public void setRemindMessage(String remindMessage) {
        this.remindMessage = remindMessage;
    }

    public Integer getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(Integer unReadNum) {
        this.unReadNum = unReadNum;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}