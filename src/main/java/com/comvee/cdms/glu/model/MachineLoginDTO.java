package com.comvee.cdms.glu.model;

import javax.validation.constraints.NotEmpty;

public class MachineLoginDTO {

    @NotEmpty(message = "账号不能为空")
    private String userName;
    @NotEmpty(message = "密码不能为空")
    private String password;
    private String lastLoginIPAddress;
    private String machineSn;
    private Integer machineType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLoginIPAddress() {
        return lastLoginIPAddress;
    }

    public void setLastLoginIPAddress(String lastLoginIPAddress) {
        this.lastLoginIPAddress = lastLoginIPAddress;
    }

    public String getMachineSn() {
        return machineSn;
    }

    public void setMachineSn(String machineSn) {
        this.machineSn = machineSn;
    }

    public Integer getMachineType() {
        return machineType;
    }

    public void setMachineType(Integer machineType) {
        this.machineType = machineType;
    }
}
