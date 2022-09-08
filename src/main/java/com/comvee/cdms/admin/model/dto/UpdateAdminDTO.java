package com.comvee.cdms.admin.model.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
public class UpdateAdminDTO {

    @NotEmpty
    private String adminId;
    private Integer accountStatus;
    private String adminName;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
