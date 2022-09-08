package com.comvee.cdms.admin.model.dto;

/**
 * @author: suyz
 * @date: 2019/1/16
 */
public class ListAdminDTO {

    private String keyword;

    private Integer accountStatus;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }
}
