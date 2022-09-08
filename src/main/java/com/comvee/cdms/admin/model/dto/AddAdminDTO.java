package com.comvee.cdms.admin.model.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: suyz
 * @date: 2019/1/16
 */
public class AddAdminDTO {

    @NotEmpty
    private String accountNo;
    @NotEmpty
    private String adminName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String roleIdString;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleIdString() {
        return roleIdString;
    }

    public void setRoleIdString(String roleIdString) {
        this.roleIdString = roleIdString;
    }
}
