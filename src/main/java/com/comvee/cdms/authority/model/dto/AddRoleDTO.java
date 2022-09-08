package com.comvee.cdms.authority.model.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
public class AddRoleDTO {

    @NotEmpty
    private String roleName;
    private String roleDescribe;
    @NotNull
    private Integer roleType;
    @NotEmpty
    private String authorityIdList;

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

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getAuthorityIdList() {
        return authorityIdList;
    }

    public void setAuthorityIdList(String authorityIdList) {
        this.authorityIdList = authorityIdList;
    }
}
