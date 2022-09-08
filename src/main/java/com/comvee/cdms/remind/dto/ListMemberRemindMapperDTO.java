package com.comvee.cdms.remind.dto;

import java.util.List;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_member_remind
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class ListMemberRemindMapperDTO {

    /**
     * member_id
     */
    private String memberId;

    /**
     * 提醒类型
     * remind_type
     */
    private List<Integer> remindTypeList;

    /**
     * 时间戳
     * timestamp
     */
    private String refreshTimestamp;


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<Integer> getRemindTypeList() {
        return remindTypeList;
    }

    public void setRemindTypeList(List<Integer> remindTypeList) {
        this.remindTypeList = remindTypeList;
    }

    public String getRefreshTimestamp() {
        return refreshTimestamp;
    }

    public void setRefreshTimestamp(String refreshTimestamp) {
        this.refreshTimestamp = refreshTimestamp;
    }

}