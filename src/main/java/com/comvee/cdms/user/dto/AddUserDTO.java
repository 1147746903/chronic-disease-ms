package com.comvee.cdms.user.dto;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_user
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class AddUserDTO {

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
     * 用户类型 1 医生  2  患者 3代理 4销售
     * user_type
     */
    private Integer userType;

    private String validDt;

    private String userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getValidDt() {
        return validDt;
    }

    public void setValidDt(String validDt) {
        this.validDt = validDt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}