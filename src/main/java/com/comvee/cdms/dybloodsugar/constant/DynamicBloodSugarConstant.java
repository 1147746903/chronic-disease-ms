package com.comvee.cdms.dybloodsugar.constant;

public class DynamicBloodSugarConstant {

    /**
     * 血糖等级 5 偏高  3 正常  1 偏低
     */
    public static final int LEVEL_HIGH = 5;
    public static final int LEVEL_NORMAL = 3;
    public static final int LEVEL_LOW = 1;

    /**
     * 血糖偏低偏高标准
     */
    public static final double LOW_VALUE = 3.9D;
    public static final double HIGH_VALUE = 10.0D;
    /**
     * 一级低血糖
     */
    public static final double FIRST_LOW_VALUE = 3.9D;

    /**
     * 二级低血糖
     */
    public static final double SECOND_LOW_VALUE = 3.0D;

    /**
     * 用餐波动值
     */
    public static final double EATING_FLUCTUATION_VALUE = 2.0D;

    /**
     * 中位线波动提示值
     */
    public static final double PER_50_FLUCTUATION_VALUE = 3.9D;

    /**
     * IQR 提醒值
     */
    public static final double IQR_VALUE = 5D;

    /**
     * IDR 提醒值
     */
    public static final double IDR_VALUE = 8D;
    /**
     * TIR 提醒值
     */
    public static final double TIR_VALUE = 70;
    /**
     * TBR 提醒值
     */
    public static final double TBR_VALUE = 25;

    /**
     * 高血糖提醒值
     */
    public static final double HYPERGLYCEMIA_VALUE = 13.9;

    /**
     * TDR 提醒值
     */
    public static final double TDR_VALUE = 5;

    /**
     * 血糖报告类型 1：达标时间占比 2：血糖波动 3：低血糖风险 4:高血糖
     */
    public static final int REPORT_TYPE_TIR = 1;
    public static final int REPORT_TYPE_FLU = 2;
    public static final int REPORT_TYPE_HYPO = 3;
    public static final int REPORT_TYPE_HYPE = 4;


    /**
     * 每日最大记录值
     */
    public static final int DAY_MAX_RECORD = 96;

    /**
     * 统计类型 1 日趋势 2 动态 3 绝对差 4 每日血糖汇总
     */
    public final static Integer STATISTICS_REPORT_TYPE_1 = 1;
    public final static Integer STATISTICS_REPORT_TYPE_2 = 2;
    public final static Integer STATISTICS_REPORT_TYPE_3 = 3;
    public final static Integer STATISTICS_REPORT_TYPE_4 = 4;

    /**
     * 动态的图表必须拥有的最少的数据天数
     */
    public final static int DYNAMIC_CHART_MINIMUM_DAY = 5;

    /**
     * 请求来源1 web  2  微信
     */
    public final static int ORIGIN_WEB = 1;
    public final static int ORIGIN_WECHAT = 2;

    /**
     * 血糖记录间隔分钟
     */
    public final static int RECORD_TIME_INTERVAL_MINUTER = 15;

    /**
     * 备注来源 1 患者 2医生
     */
    public final static int ORIGIN_MEMBER = 1;
    public final static int ORIGIN_DOCTOR = 2;


    public final static String BREAKFAST = "1"; //早餐
    public final static String LUNCH = "2"; //午餐
    public final static String DINNER = "3"; //晚餐
    public final static String OTHER_MEAL = "4"; //其他餐时

    public final static String OTHER_SPORT = "其他运动";

    public final static int DAY = 14;

    /**
     * 绑定类型 1 居家 2 在院
     */
    public final static int BIND_TYPE_IN_HOME = 1;
    public final static int BIND_TYPE_IN_HOSPITAL = 2;

    /**
     * 百度智云token过期
     */
    public static final int ACCESS_TOKEN_EXPIRED = 111;

    public final static String TIME_INTERVAL1 = "00:00:00-01:00:00";
    public final static String TIME_INTERVAL2 = "01:00:00-02:00:00";
    public final static String TIME_INTERVAL3 = "02:00:00-03:00:00";
    public final static String TIME_INTERVAL4 = "03:00:00-04:00:00";
    public final static String TIME_INTERVAL5 = "04:00:00-05:00:00";
    public final static String TIME_INTERVAL6 = "05:00:00-06:00:00";
    public final static String TIME_INTERVAL7 = "06:00:00-07:00:00";
    public final static String TIME_INTERVAL8 = "07:00:00-08:00:00";
    public final static String TIME_INTERVAL9 = "08:00:00-09:00:00";
    public final static String TIME_INTERVAL10 = "09:00:00-10:00:00";
    public final static String TIME_INTERVAL11 = "10:00:00-11:00:00";
    public final static String TIME_INTERVAL12 = "11:00:00-12:00:00";
    public final static String TIME_INTERVAL13 = "12:00:00-13:00:00";
    public final static String TIME_INTERVAL14 = "13:00:00-14:00:00";
    public final static String TIME_INTERVAL15 = "14:00:00-15:00:00";
    public final static String TIME_INTERVAL16 = "15:00:00-16:00:00";
    public final static String TIME_INTERVAL17 = "16:00:00-17:00:00";
    public final static String TIME_INTERVAL18 = "17:00:00-18:00:00";
    public final static String TIME_INTERVAL19 = "18:00:00-19:00:00";
    public final static String TIME_INTERVAL20 = "19:00:00-20:00:00";
    public final static String TIME_INTERVAL21 = "20:00:00-21:00:00";
    public final static String TIME_INTERVAL22 = "21:00:00-22:00:00";
    public final static String TIME_INTERVAL23 = "22:00:00-23:00:00";
    public final static String TIME_INTERVAL24 = "23:00:00-24:00:00";

}
