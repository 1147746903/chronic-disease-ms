package com.comvee.cdms.authority.model.dto;

import javax.validation.constraints.NotNull;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
public class ListRoleDTO {

    private String keyword;

    private Integer roleStatus;

    @NotNull
    private Integer roleType;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}
