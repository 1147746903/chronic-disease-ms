package com.comvee.cdms.other.dto;

import java.io.Serializable;

public class CreateMiniStrQrCodeDTO implements Serializable {
    private Integer businessType;
    private String foreignId;
    private String description;
    private long expireSeconds;
    /**
     * 小程序跳转页面
     */
    private String pagePath;
    /**
     * 小程序二维码宽度
     */
    private Integer width=430;
    /**
     * scene
     */
    private String params;

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
