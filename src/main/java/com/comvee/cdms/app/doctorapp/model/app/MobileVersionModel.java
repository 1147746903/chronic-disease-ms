package com.comvee.cdms.app.doctorapp.model.app;

/**
 * 手机客户端
 * @author f011509070
 *
 */
public class MobileVersionModel {
	   /**
     * sid
     */
    private String sid;

    /**
     * 系统类型 1  安卓  2 ios
     * os_type
     */
    private Integer osType;

    /**
     * 版本号
     * version_num
     */
    private String versionNum;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * upgrade_msg
     */
    private String upgradeMsg;

    /**
     * 是否是最新版本 1  是 0 不是
     * be_newest
     */
    private Integer beNewest;

    /**
     * 升级时间戳
     * upgrade_num
     */
    private Integer upgradeNum;
    /**
     * 升级url
     */
    private String upgradeUrl;
    /**
     * 是否强制 1 强制 0 不强制
     */
    private Integer isForce;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getOsType() {
        return osType;
    }

    public void setOsType(Integer osType) {
        this.osType = osType;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpgradeMsg() {
        return upgradeMsg;
    }

    public void setUpgradeMsg(String upgradeMsg) {
        this.upgradeMsg = upgradeMsg;
    }

    public Integer getBeNewest() {
        return beNewest;
    }

    public void setBeNewest(Integer beNewest) {
        this.beNewest = beNewest;
    }

    public Integer getUpgradeNum() {
        return upgradeNum;
    }

    public void setUpgradeNum(Integer upgradeNum) {
        this.upgradeNum = upgradeNum;
    }

    public String getUpgradeUrl() {
        return upgradeUrl;
    }

    public void setUpgradeUrl(String upgradeUrl) {
        this.upgradeUrl = upgradeUrl;
    }

    public Integer getIsForce() {
        return isForce;
    }

    public void setIsForce(Integer isForce) {
        this.isForce = isForce;
    }
}
