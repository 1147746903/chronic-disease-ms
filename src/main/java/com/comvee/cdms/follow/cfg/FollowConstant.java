package com.comvee.cdms.follow.cfg;

/**
 * @author: suyz
 * @date: 2018/12/5
 */
public class FollowConstant {

    /**
     * 随访类型 1 首诊 2 日常 3行为问卷 4糖尿病足随访表 5糖尿病随访表 6 自定义随访表 7 2型糖尿病随访表 8健康评估 9南京鼓楼医院糖尿病随访
     * 10 高血压首诊 11 高血压（基位随访） 12 糖尿病风险评估
     */
    public static final int FOLLOW_TYPE_FIRST = 1;
    public static final int FOLLOW_TYPE_DAY = 2;
    public static final int FOLLOW_TYPE_QUES = 3;
    public static final int FOLLOW_TYPE_QUES_FOLLOW = 4;
    public static final int FOLLOW_TYPE_FOLLOW = 5;
    public static final int FOLLOW_TYPE_CUSTOM = 6;
    public static final int FOLLOW_TYPE_TWO_DIABETES = 7;
    public static final int FOLLOW_TYPE_HEALTH_ACCESS = 8;
    public static final int FOLLOW_TYPE_NJ_DIABETES = 9;
    public static final int FOLLOW_TYPE_FIRST_GXY= 10;
    public static final int FOLLOW_TYPE_HYP_JW = 11;
    public static final int FOLLOW_TYPE_TNB_ASSESS= 12;

    /**
     * 随访填写人 1医生 2 患者
     */
    public static final int FOLLOW_FILL_BY_DOCTOR = 1;
    public static final int FOLLOW_FILL_BY_MEMBER = 2;

    /**
     * 随访状态 0 未完成 1 已完成 2 患者已完成
     */
    public static final int FOLLOW_STATUS_NO = 0;
    public static final int FOLLOW_STATUS_YES = 1;
    public static final int FOLLOW_STATUS_MEMBER_YES = 2;

}
