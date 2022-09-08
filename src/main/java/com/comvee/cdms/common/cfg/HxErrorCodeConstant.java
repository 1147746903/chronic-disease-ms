package com.comvee.cdms.common.cfg;

public class HxErrorCodeConstant {
    /**
     * 来源验证失败,参数为空无法验证
     *
     * Q：找第三方提供者参数为什么为空
     */
    public final static String ORIGIN_FAILL_CODE1 = "10001";
    /**
     * 来源验证失败,验证结果不匹配
     *
     * Q：先验证系统来源计算是否没有错误，再找第三方验证
     */
    public final static String ORIGIN_FAILL_CODE2 = "10002";
    /**
     * 登录令牌验证失败,参数为空无法验证
     *
     * Q：找第三方提供者参数为什么为空
     */
    public final static String TOKEN_FAILL_CODE1 = "10003";
    /**
     * 登录失败,无法找到该用户信息
     *
     * Q：判断第三方提供者参数为什么匹配不到用户
     */
    public final static String LOGIN_FAILL_CODE1 = "10004";
    /**
     * 用户关联编号为空，无法找到患者信息
     *
     * Q：找第三方提供者用户关联编号为什么为空
     */
    public final static String LOGIN_FAILL_CODE2 = "10005";
    /**
     * 调用华西公用验证登录令牌失败,返回结果空
     *
     * Q：找第三方提供者,返回结果空为什么为空
     */
    public final static String TOKEN_FAILL_CODE2 = "10006";
    /**
     * 调用华西公用验证登录令牌失败,返回失败消息
     *
     * Q：判断第三方提供者为什么返回失败消息
     */
    public final static String TOKEN_FAILL_CODE3 = "10007";
    /**
     * 调用华西公用验证登录令牌失败
     *
     * Q：找第三方提供者为什么验证登录令牌失败
     */
    public final static String TOKEN_FAILL_CODE4 = "10008";
    /**
     * 调用华西推送接口,返回结果空
     *
     * Q：找第三方提供者为什么返回结果空
     */
    public final static String PUSH_FAILL_CODE1 = "10009";
    /**
     * 调用华西推送接口失败,返回失败消息
     *
     * Q：判断第三方提供者为什么返回失败消息
     */
    public final static String PUSH_FAILL_CODE2 = "10010";
    /**
     * 调用华西推送接口失败,系统错误
     *
     * Q：查看错误日记为什么系统错误
     */
    public final static String PUSH_FAILL_CODE3 = "10011";
    /**
     * 调用服务包同步接口,返回结果空
     *
     * Q：找第三方提供者为什么返回结果空
     */
    public final static String SYNC_PACKAGE_FAILL_CODE1 = "10012";
    /**
     * 调用服务包同步接口,返回失败消息
     *
     * Q：判断第三方提供者为什么返回失败消息
     */
    public final static String SYNC_PACKAGE_FAILL_CODE2 = "10013";
    /**
     * 调用服务包同步接口,系统错误
     *
     * Q：查看错误日记为什么系统错误
     */
    public final static String SYNC_PACKAGE_FAILL_CODE3 = "10014";

    /**
     * 调用服务包同步接口,系统错误
     */
    public final static String BIZ_NOT_BUY_PACKAGE = "20001";

    /**
     * 调用服务包同步接口,系统错误
     */
    public final static String BIZ_PACKAGE_NOT_VALID = "20002";
}
