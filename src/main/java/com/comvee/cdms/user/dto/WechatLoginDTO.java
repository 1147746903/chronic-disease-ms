package com.comvee.cdms.user.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
public class WechatLoginDTO {

    @NotEmpty(message = "appId不能为空")
    private String appId;
    @NotEmpty(message = "code不能为空")
    private String code;
    @NotEmpty(message = "rawData不能为空")
    private String rawData;
    @NotEmpty(message = "signature不能为空")
    private String signature;
    @NotEmpty(message = "encryptedData不能为空")
    private String encryptedData;
    @NotEmpty(message = "iv不能为空")
    private String iv;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
