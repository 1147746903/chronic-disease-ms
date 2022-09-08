package com.comvee.cdms.user.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

public class HxLoginDTO implements Serializable {
    @NotEmpty(message = "系统标志(flag)不可为空，请传“android”或“ios”")
     private String flag;

    @NotEmpty(message = "登录令牌(token)不可为空")
     private String token;

    @NotEmpty(message = "时间戳(timestamp)不可为空")
     private String timestamp;

    @NotEmpty(message = "来源秘钥(digest)不可为空")
     private String digest;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
