package com.comvee.cdms.authority.model.dto;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
public class AddRoleAuthorityDTO {

    private String sid;
    private String roleId;
    private String authorityId;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }
}
