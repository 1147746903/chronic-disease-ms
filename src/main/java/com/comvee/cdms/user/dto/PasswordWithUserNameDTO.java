package com.comvee.cdms.user.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/9/30
 */
public class PasswordWithUserNameDTO implements Serializable{

    @NotEmpty(message = "userName 不能为空")
    private String userName;
    @NotEmpty(message = "oldPassword 不能为空")
    private String oldPassword;
    @NotEmpty(message = "newPassword 不能为空")
    private String newPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
