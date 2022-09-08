package com.comvee.cdms.glu.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author ZhiGe
 * @description
 * @date 2018/3/15 13:45 create
 */
public class UserDTO implements Serializable{

    @NotEmpty(message = "{user.login.account.not.empty}")
    private String userNo;
    @NotEmpty(message = "{user.login.password.not.empty}")
    private String password;
    private String userId;
    private String lastLoginIp;
    private Integer sex;
    private String mobileNo;
    private String userName;
    private Integer isValid;
    private String foreignId;
    private String roleIdStr;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getUserId() {
        return userId;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getRoleIdStr() {
        return roleIdStr;
    }

    public void setRoleIdStr(String roleIdStr) {
        this.roleIdStr = roleIdStr;
    }
}
