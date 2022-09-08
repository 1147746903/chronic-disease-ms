package com.comvee.cdms.member.constant;

/**
 * @author wyc
 * @date 2020/3/6 16:28
 */
public class MemberConstant {

    /**
     * 是否是住院患者 0 否 1 是
     */
    public static final Integer IN_HOS_YES = 1;
    public static final Integer IN_HOS_NO = 0;
    /**
     * 付费院患者 付费状态 1 未付费 2 已付费 3 已过期
     */
    public static final String PACKAGE_STATUS_PAY = "2";
    public static final String PACKAGE_STATUS_UN_PAY = "1";
    public static final String PACKAGE_STATUS_GQ_PAY = "3";

    /**
     * 患者设备 1 动态血糖 2 胰岛素泵
     */
    public static final int MEMBER_MACHINE_DYNAMIC_BLOOD_SUGAR = 1;
    public static final int MEMBER_MACHINE_INSULIN_PUMP = 2;

    /**
     * 卡类型1  门诊卡号 2 住院卡号
     */
    public static final int CARD_TYPE_OUT_PATIENT  = 1;
    public static final int CARD_TYPE_IN_HOSPITAL = 2;


    /**
     * 患者标签 1 COMMON 常规用户 2 AGENT 合伙人入口用户
     */
    public static final String MEMBER_TAG_COMMON = "COMMON";
    public static final String MEMBER_TAG_AGENT = "AGENT";

    /**
     * 患者类型 1 门诊 2 住院  0其他
     */
    public static final Integer MEMBER_TYPE_OUT_DEPART = 1;
    public static final Integer MEMBER_TYPE_IN_HOSP = 2;
    public static final Integer MEMBER_TYPE_OTHER = 0;

}
