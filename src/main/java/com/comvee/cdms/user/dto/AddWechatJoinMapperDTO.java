package com.comvee.cdms.user.dto;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_user_wechat_join_info
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class AddWechatJoinMapperDTO {
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
    private String joinType;

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

    /**
     * 1患者端，2渠道端
     */
    private Integer authorizedSource;

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
    }

    public Integer getAuthorizedSource() {
        return authorizedSource;
    }

    public void setAuthorizedSource(Integer authorizedSource) {
        this.authorizedSource = authorizedSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_wechat_join_info.sid
     *
     * @return the value of t_user_wechat_join_info.sid
     *
     * @mbggenerated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_wechat_join_info.sid
     *
     * @param sid the value for t_user_wechat_join_info.sid
     *
     * @mbggenerated
     */
    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_wechat_join_info.nick_name
     *
     * @return the value of t_user_wechat_join_info.nick_name
     *
     * @mbggenerated
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_wechat_join_info.nick_name
     *
     * @param nickName the value for t_user_wechat_join_info.nick_name
     *
     * @mbggenerated
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_wechat_join_info.photo_url
     *
     * @return the value of t_user_wechat_join_info.photo_url
     *
     * @mbggenerated
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_wechat_join_info.photo_url
     *
     * @param photoUrl the value for t_user_wechat_join_info.photo_url
     *
     * @mbggenerated
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_wechat_join_info.foreign_id
     *
     * @return the value of t_user_wechat_join_info.foreign_id
     *
     * @mbggenerated
     */
    public String getForeignId() {
        return foreignId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_wechat_join_info.foreign_id
     *
     * @param foreignId the value for t_user_wechat_join_info.foreign_id
     *
     * @mbggenerated
     */
    public void setForeignId(String foreignId) {
        this.foreignId = foreignId == null ? null : foreignId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_wechat_join_info.open_id
     *
     * @return the value of t_user_wechat_join_info.open_id
     *
     * @mbggenerated
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_wechat_join_info.open_id
     *
     * @param openId the value for t_user_wechat_join_info.open_id
     *
     * @mbggenerated
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_wechat_join_info.union_id
     *
     * @return the value of t_user_wechat_join_info.union_id
     *
     * @mbggenerated
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_wechat_join_info.union_id
     *
     * @param unionId the value for t_user_wechat_join_info.union_id
     *
     * @mbggenerated
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_wechat_join_info.app_id
     *
     * @return the value of t_user_wechat_join_info.app_id
     *
     * @mbggenerated
     */
    public String getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_wechat_join_info.app_id
     *
     * @param appId the value for t_user_wechat_join_info.app_id
     *
     * @mbggenerated
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }
}