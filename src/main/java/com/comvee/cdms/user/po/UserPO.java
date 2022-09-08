package com.comvee.cdms.user.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_user
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class UserPO {
    /**
     * 用户id
     * uid
     */
    private String uid;

    /**
     * 账号
     * user_name
     */
    private String userName;

    /**
     * 密码
     * password
     */
    private String password;

    /**
     * 加密盐
     * salt
     */
    private String salt;

    /**
     * 最后登录时间
     * last_login_dt
     */
    private String lastLoginDt;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * 用户类型 1 医生  2  患者
     * user_type
     */
    private Integer userType;

    private String validDt;

    /**
     * 用户状态 1 正常 2 禁用
     */
    private Integer userStatus;
    private Integer loginFailTimes;
    private String lastUpdatePasswordDt;
    private String lockDt;

    private Boolean needDoubleFactor;
    public String getValidDt() {
        return validDt;
    }

    public void setValidDt(String validDt) {
        this.validDt = validDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.uid
     *
     * @return the value of t_user.uid
     *
     * @mbggenerated
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.uid
     *
     * @param uid the value for t_user.uid
     *
     * @mbggenerated
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.user_name
     *
     * @return the value of t_user.user_name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.user_name
     *
     * @param userName the value for t_user.user_name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.password
     *
     * @return the value of t_user.password
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.password
     *
     * @param password the value for t_user.password
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.salt
     *
     * @return the value of t_user.salt
     *
     * @mbggenerated
     */
    public String getSalt() {
        return salt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.salt
     *
     * @param salt the value for t_user.salt
     *
     * @mbggenerated
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.last_login_dt
     *
     * @return the value of t_user.last_login_dt
     *
     * @mbggenerated
     */
    public String getLastLoginDt() {
        return lastLoginDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.last_login_dt
     *
     * @param lastLoginDt the value for t_user.last_login_dt
     *
     * @mbggenerated
     */
    public void setLastLoginDt(String lastLoginDt) {
        this.lastLoginDt = lastLoginDt == null ? null : lastLoginDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.insert_dt
     *
     * @return the value of t_user.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.insert_dt
     *
     * @param insertDt the value for t_user.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.update_dt
     *
     * @return the value of t_user.update_dt
     *
     * @mbggenerated
     */
    public String getUpdateDt() {
        return updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.update_dt
     *
     * @param updateDt the value for t_user.update_dt
     *
     * @mbggenerated
     */
    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt == null ? null : updateDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.user_type
     *
     * @return the value of t_user.user_type
     *
     * @mbggenerated
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.user_type
     *
     * @param userType the value for t_user.user_type
     *
     * @mbggenerated
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getLoginFailTimes() {
        return loginFailTimes;
    }

    public void setLoginFailTimes(Integer loginFailTimes) {
        this.loginFailTimes = loginFailTimes;
    }

    public String getLastUpdatePasswordDt() {
        return lastUpdatePasswordDt;
    }

    public void setLastUpdatePasswordDt(String lastUpdatePasswordDt) {
        this.lastUpdatePasswordDt = lastUpdatePasswordDt;
    }

    public String getLockDt() {
        return lockDt;
    }

    public void setLockDt(String lockDt) {
        this.lockDt = lockDt;
    }

    public Boolean getNeedDoubleFactor() {
        return needDoubleFactor;
    }

    public void setNeedDoubleFactor(Boolean needDoubleFactor) {
        this.needDoubleFactor = needDoubleFactor;
    }
}