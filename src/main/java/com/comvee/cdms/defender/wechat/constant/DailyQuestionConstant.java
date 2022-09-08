package com.comvee.cdms.defender.wechat.constant;

/**
 * @Author linr
 * @Date 2021/12/14
 */
public class DailyQuestionConstant {

    //每日一答题库type
    public final static int DAILY_QUES_TYPE_COMPLICATION = 1;//并发症
    public final static int DAILY_QUES_TYPE_MONITOR = 2;//监测
    public final static int DAILY_QUES_TYPE_OTHER = 3;//其他
    public final static int DAILY_QUES_TYPE_MEDICINE = 4;//药物
    public final static int DAILY_QUES_TYPE_DIET = 5;//饮食
    public final static int DAILY_QUES_TYPE_SPORT = 6;//运动
    public final static int DAILY_QUES_TYPE_INSULIN = 7;//胰岛素


    //每日一答题库two_type
    public final static int DAILY_QUES_TWO_TYPE_COMMON = 1;//通用版
    public final static int DAILY_QUES_TWO_TYPE_TAG = 2;//标签版

    //每日一答题库group_code
    public final static String DAILY_QUES_GROUP_CODE_TWO_WEEK = "two_week";
    public final static String DAILY_QUES_GROUP_CODE_THREE_MONTH = "three_month";
    public final static String DAILY_QUES_GROUP_CODE_TWELVE_MONTH = "twelve_month";

    //每日一答推送阶段stage
    public final static int DAILY_QUES_PUSH_STAGE_1 = 1;//题库规律推送2
    public final static int DAILY_QUES_PUSH_STAGE_2 = 2;//错题集
    public final static int DAILY_QUES_PUSH_STAGE_3 = 3;//做过的题目循环
}
