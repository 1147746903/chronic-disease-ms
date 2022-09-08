package com.comvee.cdms.member.constant;

/**
 * @author: suyz
 * @date: 2018/11/7
 */
public class MemberDoctorConstant {

    /**
     * 医患关系主要照顾状态 1  是 0 不是
     */
    public static final int MEMBER_DOCTOR_ATTEND_YES = 1;
    public static final int MEMBER_DOCTOR_ATTEND_NO = 0;

    /**
     * 付费状态 1 未付费 2 已付费 3 过期
     */
    public static final int PAY_STATUS_NO = 1;
    public static final int PAY_STATUS_YES = 2;
    public static final int PAY_STATUS_EXPIRE = 3;

    /**
     * 关注状态 1 已关注 0 未关注
     */
    public static final int CONCERN_STATUS_YES = 1;
    public static final int CONCERN_STATUS_NO = 0;

    /**
     * 医患关系创建方式 1 web端添加患者  2 APP添加患者 3 扫描二维码 4 小程序患者申请添加 5 筛查系统同步 6转诊添加医患关系 7 其他 8修改分组 9 通过订单
     * 10 通过扫描探头二维码添加 11 血糖仪添加医患关系
     */
    public static final int MEMBER_DOCTOR_RELATION_WEB = 1;
    public static final int MEMBER_DOCTOR_RELATION_APP = 2;
    public static final int MEMBER_DOCTOR_RELATION_QRCODE = 3;
    public static final int MEMBER_DOCTOR_RELATION_WECHAT = 4;
    public static final int MEMBER_DOCTOR_RELATION_SCREEING_SYSTEM = 5;
    public static final int MEMBER_DOCTOR_RELATION_REFERRAL = 6;
    public static final int MEMBER_DOCTOR_RELATION_OTHER = 7;
    public static final int MEMBER_DOCTOR_RELATION_WEB_UPDATEGROUP = 8;
    public static final int MEMBER_DOCTOR_RELATION_ORDER = 9;
    public static final int MEMBER_DOCTOR_RELATION_SENSOR =10;
    public static final int MEMBER_DOCTOR_RELATION_GLU = 11;


}
