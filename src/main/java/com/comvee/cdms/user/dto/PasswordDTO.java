package com.comvee.cdms.user.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/9/30
 */
public class PasswordDTO implements Serializable{

    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;

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
