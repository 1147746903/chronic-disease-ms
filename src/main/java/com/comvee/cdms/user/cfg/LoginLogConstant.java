package com.comvee.cdms.user.cfg;

public class LoginLogConstant {

    /**
     * 登录结果 1 成功  2 账号不存在 3 密码错误 4 账号被禁 5 过期 99 其他
     */
    public static final int LOGIN_RESULT_SUCCESS = 1;
    public static final int LOGIN_RESULT_USER_NOT_EXISTS = 2;
    public static final int LOGIN_RESULT_PASSWORD_ERROR = 3;
    public static final int LOGIN_RESULT_FORBIDDEN = 4;
    public static final int LOGIN_RESULT_OVERDUE = 5;
    public static final int LOGIN_RESULT_OTHER = 99;

    /**
     * 账号类型 1 医生web 2 后台 3 医生手机 4 微信 5 血糖仪 6随访系统
     */
    public static final int USER_TYPE_WEB = 1;
    public static final int USER_TYPE_BACK = 2;
    public static final int USER_TYPE_MOBILE = 3;
    public static final int USER_TYPE_WX = 4;
    public static final int USER_TYPE_GLU = 5;
    public static final int USER_TYPE_FOLLOW = 6;
}
