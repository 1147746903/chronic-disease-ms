package com.comvee.cdms.dybloodpressure.constant;


/**
 * @Author linr
 * @Date 2021/10/27
 */
public class DyBloodPressureConstant {


    public static final Integer OPERATION_TYPE_MEMBER = 1; //操作者类型--患者用户
    public static final Integer OPERATION_TYPE_DOCTOR = 2; //操作者类型--医护用户


    public static final Integer RECORD_TYPE_OUT_DEPART = 1; //录入类型-门诊
    public static final Integer RECORD_TYPE_IN_HOME = 2; //录入类型-居家

    /**
     * 血压时段类型
     */

    public static final Integer RECORD_TIME_TYPE_AFTER_BED = 1; //起床后清晨时段
    public static final Integer RECORD_TIME_TYPE_DAY_WAKE = 2; //白天清醒时段
    public static final Integer RECORD_TIME_TYPE_DAY_SLEEP = 3; //白天睡眠时段
    public static final Integer RECORD_TIME_TYPE_NIGHT_SLEEP = 4; //夜间睡眠时段



    /**
     * 血压等级 1 偏低 3 正常 5 偏高
     */
    public static final String BP_LEVEL_HIGH = "5";
    public static final String BP_LEVEL_NORMAL = "3";
    public static final String BP_LEVEL_LOW = "1";


    /**
     * 动态血压指标标准范围
     */
    //白天清醒时段
    public static final Double SBP_DAY_MIN = 90d;
    public static final Double SBP_DAY_MAX = 135d;
    public static final Double DBP_DAY_MIN = 60d;
    public static final Double DBP_DAY_MAX = 85d;
    //夜间睡眠时段
    public static final Double SBP_SLEEP_MIN = 90d;
    public static final Double SBP_SLEEP_MAX = 120d;
    public static final Double DBP_SLEEP_MIN = 60d;
    public static final Double DBP_SLEEP_MAX = 70d;
    //起床后清晨时段
    public static final Double SBP_AFTER_BED_MIN = 90d;
    public static final Double SBP_AFTER_BED_MAX = 135d;
    public static final Double DBP_AFTER_BED_MIN = 60d;
    public static final Double DBP_AFTER_BED_MAX = 85d;
    //24h
    public static final Double SBP_24H_MIN = 90d;
    public static final Double SBP_24H_MAX = 130d;
    public static final Double DBP_24H_MIN = 60d;
    public static final Double DBP_24H_MAX = 80d;

    /**
     * 日记默认值
     */
    public static final String DEFAULT_BED_TIME = "06:00:00"; //默认起床时间
    public static final String DEFAULT_SLEEP_TIME = "21:30:00"; //默认睡觉时间
    public static final String DEFAULT_DAY_START_TIME = "10:00:00"; //默认一天起始时间
    public static final String DEFAULT_DAY_END_TIME = "09:59:59"; //默认一天结束时间
    public static final String DEFAULT_AFTER_BED_TIME = "08:00:00"; //默认起床后两小时时间


}
