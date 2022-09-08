package com.comvee.cdms.user.po;

import java.io.Serializable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_user_wechat_join_info
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class UserWechatJoinPO implements Serializable {

    private static final long serialVersionUID = 1574324422159475657L;

    /**
     * sid
     */
    private String sid;

    /**
     * 添加时间
     * insert_dt
     */
    private String insertDt;

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
     * 是否有效
     * is_valid
     */
    private Integer isValid;

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

    private Integer authorizedSource;

    public Integer getAuthorizedSource() {
        return authorizedSource;
    }

    public void setAuthorizedSource(Integer authorizedSource) {
        this.authorizedSource = authorizedSource;
    }

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
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
     * This method returns the value of the database column t_user_wechat_join_info.insert_dt
     *
     * @return the value of t_user_wechat_join_info.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_wechat_join_info.insert_dt
     *
     * @param insertDt the value for t_user_wechat_join_info.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
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
     * This method returns the value of the database column t_user_wechat_join_info.is_valid
     *
     * @return the value of t_user_wechat_join_info.is_valid
     *
     * @mbggenerated
     */
    public Integer getIsValid() {
        return isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_wechat_join_info.is_valid
     *
     * @param isValid the value for t_user_wechat_join_info.is_valid
     *
     * @mbggenerated
     */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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