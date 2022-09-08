package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

public class ListResearchGroupMemberDTO {

    @NotEmpty(message = "groupId 不能为空")
    private String groupId;
    private String keyword;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
