package com.comvee.cdms.user.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/9/30
 */
public class UpdateUserDTO implements Serializable{

    @NotEmpty
    private String uid;
    private String password;
    private String lastLoginDt;
    private String validDt;
    private String remark;
    private String lastLoginIPAddress;
    private Integer userStatus;
    private Integer loginFailTimes;
    private String lastUpdatePasswordDt;
    private String lockDt;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getValidDt() {
        return validDt;
    }

    public void setValidDt(String validDt) {
        this.validDt = validDt;
    }

    public String getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(String lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastLoginIPAddress(String lastLoginIPAddress) {
        this.lastLoginIPAddress = lastLoginIPAddress;
    }

    public String getLastLoginIPAddress() {
        return lastLoginIPAddress;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getLoginFailTimes() {
        return loginFailTimes;
    }

    public void setLoginFailTimes(Integer loginFailTimes) {
        this.loginFailTimes = loginFailTimes;
    }

    public String getLastUpdatePasswordDt() {
        return lastUpdatePasswordDt;
    }

    public void setLastUpdatePasswordDt(String lastUpdatePasswordDt) {
        this.lastUpdatePasswordDt = lastUpdatePasswordDt;
    }

    public String getLockDt() {
        return lockDt;
    }

    public void setLockDt(String lockDt) {
        this.lockDt = lockDt;
    }
}
