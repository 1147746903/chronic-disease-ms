package com.comvee.cdms.dybloodsugar.po;

public class DyApplicationPO {
    private String appId; //应用id
    private String secretKey; //密钥
    private String description; //描述
    private String insertDt; //插入时间
    private String updateDt; // 更新时间
    private Integer isValid; //是否有效 1:有效
    private String whileList; //ip白名单
    private String pushUrl; //推送接口地址

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getWhileList() {
        return whileList;
    }

    public void setWhileList(String whileList) {
        this.whileList = whileList;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    @Override
    public String toString() {
        return "DyApplicationPO{" +
                "appId='" + appId + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", description='" + description + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", updateDt='" + updateDt + '\'' +
                ", isValid=" + isValid +
                ", whileList='" + whileList + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                '}';
    }
}
