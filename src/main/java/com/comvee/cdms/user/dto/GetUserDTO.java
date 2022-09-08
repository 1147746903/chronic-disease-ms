package com.comvee.cdms.user.dto;

/**
 * @author: suyz
 * @date: 2018/9/29
 */
public class GetUserDTO {

    private String uid;
    private String userName;
    private Integer userType;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
