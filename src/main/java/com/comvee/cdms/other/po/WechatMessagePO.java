package com.comvee.cdms.other.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_wechat_message
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class WechatMessagePO {
    /**
     * sid
     */
    private String sid;

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * 数据类型
     * data_type
     */
    private Integer dataType;

    /**
     * 处理状态 1 未处理  2 处理成功 3 失败
     * deal_status
     */
    private Integer dealStatus;

    /**
     * 响应数据
     * response_data
     */
    private String responseData;

    /**
     * 外键id
     * foreign_id
     */
    private String foreignId;

    /**
     * 数据json
     * data_json
     */
    private String dataJson;

    private Integer userType;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wechat_message.sid
     *
     * @return the value of t_wechat_message.sid
     *
     * @mbggenerated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wechat_message.sid
     *
     * @param sid the value for t_wechat_message.sid
     *
     * @mbggenerated
     */
    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wechat_message.member_id
     *
     * @return the value of t_wechat_message.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wechat_message.member_id
     *
     * @param memberId the value for t_wechat_message.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wechat_message.insert_dt
     *
     * @return the value of t_wechat_message.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wechat_message.insert_dt
     *
     * @param insertDt the value for t_wechat_message.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wechat_message.update_dt
     *
     * @return the value of t_wechat_message.update_dt
     *
     * @mbggenerated
     */
    public String getUpdateDt() {
        return updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wechat_message.update_dt
     *
     * @param updateDt the value for t_wechat_message.update_dt
     *
     * @mbggenerated
     */
    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt == null ? null : updateDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wechat_message.data_type
     *
     * @return the value of t_wechat_message.data_type
     *
     * @mbggenerated
     */
    public Integer getDataType() {
        return dataType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wechat_message.data_type
     *
     * @param dataType the value for t_wechat_message.data_type
     *
     * @mbggenerated
     */
    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wechat_message.deal_status
     *
     * @return the value of t_wechat_message.deal_status
     *
     * @mbggenerated
     */
    public Integer getDealStatus() {
        return dealStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wechat_message.deal_status
     *
     * @param dealStatus the value for t_wechat_message.deal_status
     *
     * @mbggenerated
     */
    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wechat_message.response_data
     *
     * @return the value of t_wechat_message.response_data
     *
     * @mbggenerated
     */
    public String getResponseData() {
        return responseData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wechat_message.response_data
     *
     * @param responseData the value for t_wechat_message.response_data
     *
     * @mbggenerated
     */
    public void setResponseData(String responseData) {
        this.responseData = responseData == null ? null : responseData.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wechat_message.foreign_id
     *
     * @return the value of t_wechat_message.foreign_id
     *
     * @mbggenerated
     */
    public String getForeignId() {
        return foreignId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wechat_message.foreign_id
     *
     * @param foreignId the value for t_wechat_message.foreign_id
     *
     * @mbggenerated
     */
    public void setForeignId(String foreignId) {
        this.foreignId = foreignId == null ? null : foreignId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wechat_message.data_json
     *
     * @return the value of t_wechat_message.data_json
     *
     * @mbggenerated
     */
    public String getDataJson() {
        return dataJson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wechat_message.data_json
     *
     * @param dataJson the value for t_wechat_message.data_json
     *
     * @mbggenerated
     */
    public void setDataJson(String dataJson) {
        this.dataJson = dataJson == null ? null : dataJson.trim();
    }
}