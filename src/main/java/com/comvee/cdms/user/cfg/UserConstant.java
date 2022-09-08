package com.comvee.cdms.user.cfg;

/**
 * @author: suyz
 * @date: 2018/9/29
 */
public class UserConstant {

    /**
     * 用户类型 1 医生  2  患者  3 管理员
     */
    public static final int USER_TYPE_DOCTOR = 1;
    public static final int USER_TYPE_ADMIN = 3;

    /**
     * 用户账号状态
     */
    public static final int USER_STATUS_OK = 1;
    public static final int USER_STATUS_FORBIDDEN = 2;
    public static final int USER_STATUS_LOCK = 3;

    /**
     * 渠道小程序登陆类型：1代理登陆，2销售登陆
     */
    public static final int AGENT_LOGIN = 1;
    public static final int SALES_LOGIN = 2;
}
