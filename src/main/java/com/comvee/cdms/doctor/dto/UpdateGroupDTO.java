package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李左河
 * @date 2018/7/26 11:04.
 */
public class UpdateGroupDTO {
    @Override
    public String toString() {
        return "UpdateGroupDTO{}";
    }

    /**
     * 分组id
     */
    @NotEmpty(message = "分组id，不能为空")
    private String groupId;
    /**
     * 分组名称
     */
    @NotEmpty(message = "请完善分组名称信息")
    private String groupName;

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

}
