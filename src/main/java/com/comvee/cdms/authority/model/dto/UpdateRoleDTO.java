package com.comvee.cdms.authority.model.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
public class UpdateRoleDTO {

    @NotEmpty
    private String roleId;
    private String roleName;
    private String roleDescribe;
    private Integer roleStatus;
    private String authorityIdList;

    public String getAuthorityIdList() {
        return authorityIdList;
    }

    public void setAuthorityIdList(String authorityIdList) {
        this.authorityIdList = authorityIdList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }
}
