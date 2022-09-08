package com.comvee.cdms.sign.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
public class SignConstant {

    /**
     * 体征录入操作者类型1  患者 2 医护
     */
    public static final int SIGN_OPERATOR_TYPE_DOCTOR = 2;
    public static final int SIGN_OPERATOR_TYPE_MEMBER = 1;

    /**
     * 体征类型 1  血糖 2 血压 3 BMI 4 糖化
     */
    public static final int SIGN_TYPE_BLOOD_SUGAR = 1;
    public static final int SIGN_TYPE_BLOOD_PRESSURE = 2;
    public static final int SIGN_TYPE_BMI = 3;
    public static final int SIGN_TYPE_HBA1C = 4;

    /**
     * 体征等级 1 偏低 3 正常 5 偏高
     */
    public static final int SIGN_LEVEL_HIGH = 5;
    public static final int SIGN_LEVEL_NORMAL = 3;
    public static final int SIGN_LEVEL_LOW = 1;

    /**
     * 是否住院记录 0否 1是
     */
    public static final int OUT_HOSPITAL = 0;
    public static final int IN_HOSPITAL = 1;

    /**
     * 获取血糖类型 0异常 1偏低 2偏高 3正常 4全部;
     */
    public static final int BLOOD_TYPE_ABNORMAL = 0;
    public static final int BLOOD_TYPE_LOW = 1;
    public static final int BLOOD_TYPE_HIGH = 2;
    public static final int BLOOD_TYPE_NORMAL = 3;
    public static final int BLOOD_TYPE_ALL = 4;


    /**
     * 凌晨3点
     */
    public final static String PARAM_CODE_3AM = "3am";
    /**
     * 早餐后(2小时)
     */
    public final static String PARAM_CODE_AFTER_BREAKFAST = "afterBreakfast";
    /**
     * 早餐后(1小时)
     */
    public final static String PARAM_CODE_AFTER_BREAKFAST_1H = "afterBreakfast1h";
    /**
     * 晚餐后(2小时)
     */
    public final static String PARAM_CODE_AFTER_DINNER = "afterDinner";
    /**
     * 晚餐后(1小时)
     */
    public final static String PARAM_CODE_AFTER_DINNER_1H = "afterDinner1h";
    /**
     * 午餐后(2小时)
     */
    public final static String PARAM_CODE_AFTER_LUNCH = "afterLunch";
    /**
     * 午餐后(1小时)
     */
    public final static String PARAM_CODE_AFTER_LUNCH_1H = "afterLunch1h";
    /**
     * 早餐前
     */
    public final static String PARAM_CODE_BEFORE_BREAKFAST = "beforeBreakfast";
    /**
     * 凌晨
     */
    public final static String PARAM_CODE_BEFORE_DAWN = "beforedawn";
    /**
     * 晚餐前
     */
    public final static String PARAM_CODE_BEFORE_DINNER = "beforeDinner";
    /**
     * 午餐前
     */
    public final static String PARAM_CODE_BEFORE_LUNCH = "beforeLunch";
    /**
     * 睡前
     */
    public final static String PARAM_CODE_BEFORE_SLEEP = "beforeSleep";
    /**
     * 随机血糖
     */
    public final static String PARAM_CODE_RANDOM_TIME = "randomtime";

    /**
     * 血糖来源 1 小程序  2 医生web 3 医生端 4 HIS 5 随访 6 血糖仪  7 健康一体机
     */
    public final static int ORIGIN_MINI_PROGRAM = 1;
    public final static int ORIGIN_WEB_PROGRAM = 2;
    public final static int ORIGIN_APP_PROGRAM = 3;
    public final static int ORIGIN_HIS = 4;
    public final static int ORIGIN_FOLLOW = 5;
    public final static int ORIGIN_G_DEVICE = 6;
    public final static int ORIGIN_HEALTH_INTEGRATED_MACHINE = 7;

    /**
     * 血糖测量时段文案
     */
    public final static Map<String,String> BLOOD_SUGAR_TIME_TEXT = new HashMap<>();
    static {
        BLOOD_SUGAR_TIME_TEXT.put(PARAM_CODE_3AM, "凌晨3am");
        BLOOD_SUGAR_TIME_TEXT.put(PARAM_CODE_AFTER_BREAKFAST, "早餐后");
        BLOOD_SUGAR_TIME_TEXT.put(PARAM_CODE_AFTER_DINNER, "晚餐后");
        BLOOD_SUGAR_TIME_TEXT.put(PARAM_CODE_AFTER_LUNCH, "午餐后");
        BLOOD_SUGAR_TIME_TEXT.put(PARAM_CODE_BEFORE_BREAKFAST, "空腹");
        BLOOD_SUGAR_TIME_TEXT.put(PARAM_CODE_BEFORE_DAWN, "凌晨");
        BLOOD_SUGAR_TIME_TEXT.put(PARAM_CODE_BEFORE_DINNER, "晚餐前");
        BLOOD_SUGAR_TIME_TEXT.put(PARAM_CODE_BEFORE_LUNCH, "午餐前");
        BLOOD_SUGAR_TIME_TEXT.put(PARAM_CODE_BEFORE_SLEEP, "睡前");
        BLOOD_SUGAR_TIME_TEXT.put(PARAM_CODE_RANDOM_TIME, "随机");
    }

    /**
     * 高危血糖提醒默认值
     */
    public static final Double SUGAR_HIGH = 16.7;
    public static final Double SUGAR_LOW = 3.9;

    /**
     * 血酮 是否是最新数据 1是 2否
     */
    public final static int LATEST_DATA_YES = 1;
    public final static int LATEST_DATA_NO = 2;

    /**
     * 是否需要同步到 checkout检验表  1是 2否
     */
    public final static int SYN_TO_CHECKOUT_YES = 1;
    public final static int SYN_TO_CHECKOUT_NO = 2;
}
