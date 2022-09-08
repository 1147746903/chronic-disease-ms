package com.comvee.cdms.user.dto;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
public class UpdateWechatJoinMapperDTO {

    /**
     * sid
     */
    private String sid;


    /**
     * 用户nickname
     * nick_name
     */
    private String nickName;

    /**
     * 用户头像url
     * photo_url
     */
    private String photoUrl;

    /**
     * user表的外键id (患者id,医生id)
     * foreign_id
     */
    private String foreignId;

    /**
     * 用户openid
     * open_id
     */
    private String openId;

    /**
     * 类型 1：小程序 2：微信公众号
     * type
     */
    private String type;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
     * union_id
     */
    private String unionId;

    /**
     * app_id
     */
    private String appId;

    private Integer joinStatus;

    private String whereUnionId;

    private Integer authorizedSource;

    public Integer getAuthorizedSource() {
        return authorizedSource;
    }

    public void setAuthorizedSource(Integer authorizedSource) {
        this.authorizedSource = authorizedSource;
    }

    public String getWhereUnionId() {
        return whereUnionId;
    }

    public void setWhereUnionId(String whereUnionId) {
        this.whereUnionId = whereUnionId;
    }

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
