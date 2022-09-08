package com.comvee.cdms.complication.model.bo;

/**
 * @author: suyz
 * @date: 2019/3/1
 */
public class GroupSyncBO {

    private String groupId;
    private String groupName;
    private Integer dataStatus;

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

    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }
}
