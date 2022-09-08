package com.comvee.cdms.glu.model;

import java.util.List;

public class GluGroupVO {

    private String groupId;
    private String groupName;
    private Long peopleNumber;
    private List<ListMemberVO> members;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Long peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public List<ListMemberVO> getMembers() {
        return members;
    }

    public void setMembers(List<ListMemberVO> members) {
        this.members = members;
    }
}
