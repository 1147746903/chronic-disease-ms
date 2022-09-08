package com.comvee.cdms.other.constant;

/**
 * @author: suyz
 * @date: 2018/11/30
 */
public class WechatMessageConstant {

    /**
     * 1 问答  2 血糖测量 3 血压测量 4 bmi测量结果 5 处方下发 6 处方知识学习 7 控制目标 8 监测方案
     *  9 随访问卷 10 随访报告 11 复诊 12 血糖监测提醒 13 血糖未测提示
     *  14 套餐过期提醒 15  套餐即将到期警告 16 新增套餐提醒 17 筛查复诊提醒 18 筛查知识学习推送
     *  19 筛查预约提醒 20 新建自定义随访通知(并发症筛查) 21 足部筛查处方下发提醒 22 转诊申请通知
     *  23 转诊成功通知
     *  19 筛查预约提醒 20 新建自定义随访通知(并发症筛查) 21 足部筛查处方下发提醒 22 随访关怀提醒23 糖化测量
     *  24 月度控糖报告 25 随访总结报告(华西) 26 血压监测提醒 \
     *  27: 动态血糖绑定通知  28:动态血糖解绑通知  29:血糖分享绑定通知 30设备电量信息通知 31 动态血糖报告生成通知(血糖上传)
     *  32 血糖仪绑定通知 33 动态血糖医生建议通知 34 动态血糖上传近期概况通知（通知医生） 35 检查提醒(复查)通知
     *  36 课程提醒 37 筛查报告 38 课程报名成功提醒 39健康警告提醒 40转诊申请 41转诊成功
     */
    public final static int DATA_TYPE_CONSULT = 1;
    public final static int DATA_TYPE_BLOOD_SUGAR = 2;
    public final static int DATA_TYPE_BLOOD_PRESSURE = 3;
    public final static int DATA_TYPE_BMI = 4;
    public final static int DATA_TYPE_PRESCRIPTION = 5;
    public final static int DATA_TYPE_PRESCRIPTION_KNOWLEDGE = 6;
    public final static int DATA_TYPE_CONTROL_TARGET = 7;
    public final static int DATA_TYPE_MONITOR_PLAN = 8;
    public final static int DATA_TYPE_FOLLOW_QUESTION = 9;
    public final static int DATA_TYPE_FOLLOW_REPORT = 10;
    public final static int DATA_TYPE_REVISIT = 11;
    public final static int DATA_TYPE_BLOOD_SUGAR_MONITOR = 12;
    public final static int DATA_TYPE_BLOOD_SUGAR_UN_MONITOR = 13;
    public final static int DATA_TYPE_PACKAGE_EXPIRE_REMIND = 14;
    public final static int DATA_TYPE_PACKAGE_EXPIRE_WARN = 15;
    public final static int DATA_TYPE_ADD_PACKAGE = 16;
    public final static int DATA_TYPE_SCREENING_RETURN_VISIT_REMIND = 17;
    public final static int DATA_TYPE_SCREENING_KNOWLEDGE_PUSH = 18;
    public final static int DATA_TYPE_SCREENING_ORDER_REMIND = 19;
    public final static int DATA_TYPE_NEW_CUSTOM_FOLLOW = 20;
    public final static int DATA_TYPE_SCREENING_FOOT_PRESCRIPTION = 21;
    public final static int DATA_TYPE_FOLLOWUP_CARE = 22;
    public final static int DATA_TYPE_HBA1C = 23;
    public final static int DATA_TYPE_SUGAR_MONTH_REPORT = 24;
    public final static int DATA_TYPE_FOLLOW_ZJ_REPORT = 25;

    public final static int DATA_TYPE_BLOOD_PRESSURE_MONITOR = 26;

    public final static int DATA_TYPE_BLOODSUGAR_BIND = 27;
    public final static int DATA_TYPE_BLOODSUGAR_UNBIND = 28;
    public final static int DATA_TYPE_BLOODSUGAR_SHARE_BIND = 29;
    public final static int DATA_TYPE_BLOOD_SUGAR_REPORT = 31;
    public final static int DATA_TYPE_BLOOD_SUGAR_MACHINE_BIND = 32;
    public final static int DATA_TYPE_DYNAMIC_BLOOD_SUGAR_DOCTOR_ADVICE = 33;
    public final static int DATA_TYPE_DYNAMIC_BLOOD_SUGAR_SURVEY_REMIND = 34;
    public final static int DATA_TYPE_CHECK_REMIND = 35;
    public final static int DATA_TYPE_COURSE_REMIND = 36;
    public final static int DATA_TYPE_SCREENING_REPORT = 37;
    public final static int DATA_TYPE_ADD_COURSE_SUCCESS = 38;
    public final static int DATA_TYPE_HEALTH_WARN = 39;

    public final static int DATA_TYPE_REFERRAL_APPLY = 40;
    public final static int DATA_TYPE_REFERRAL_SUCCESS = 41;

    /**
     * 处理状态  2  成功 3  失败 4  未关联公众号 5 推送关闭 6 医生不存在
     */
    public final static int DEAL_STATUS_SUCCESS = 2;
    public final static int DEAL_STATUS_FAIL = 3;
    public final static int DEAL_STATUS_NOT_RELATE = 4;
    public final static int DEAL_STATUS_PUSH_CLOSED = 5;
    public final static int DEAL_STATUS_DOCTOR_NOT_FOUND = 6;

    /**
     * 用户身份 1 患者 2 医生
     */
    public final static int USER_TYPE_PATIENT = 1;
    public final static int USER_TYPE_DOCTOR = 2;
}
