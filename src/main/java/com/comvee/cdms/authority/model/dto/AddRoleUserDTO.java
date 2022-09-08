package com.comvee.cdms.authority.model.dto;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
public class AddRoleUserDTO {

    private String sid;
    private String roleId;
    private Integer roleType;
    private String foreignId;

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

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }
}
