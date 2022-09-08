package com.comvee.cdms.other.dto;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
public class CreateStrQrCodeDTO {

    private String foreignId;
    private Integer businessType;
    private long expireSeconds;
    private String description;
    private String dataJson;

    /**
     * true 二维码上传至oss
     */
    private Boolean uploadOSS;

    private Integer origin;

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Boolean getUploadOSS() {
        return uploadOSS;
    }

    public void setUploadOSS(Boolean uploadOSS) {
        this.uploadOSS = uploadOSS;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
