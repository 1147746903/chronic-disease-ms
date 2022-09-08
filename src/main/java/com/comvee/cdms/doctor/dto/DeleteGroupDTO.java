package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李左河
 * @date 2018/7/26 17:24.
 */
public class DeleteGroupDTO {
    @Override
    public String toString() {
        return "DeleteGroupDTO{}";
    }

    /**
     * 分组id
     */
    @NotEmpty(message = "分组id，不能为空")
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
