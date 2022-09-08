package com.comvee.cdms.remind.constant;

/**
 * @author: suyz
 * @date: 2018/11/7
 */
public class MemberRemindTypeConstant {

    /**
     * 患者提醒类型 1  血糖异常 2 血压异常 3 BMI异常 4 血糖监测提醒 5 复诊血糖监测提醒 6 复诊提醒 7 血糖未测量提醒 9 糖化异常
     *  10 血压监测提醒
     */
    public static final int REMIND_TYPE_ABNORMAL_BLOOD_SUGAR = 1;
    public static final int REMIND_TYPE_ABNORMAL_BLOOD_PRESSURE = 2;
    public static final int REMIND_TYPE_ABNORMAL_BMI = 3;
    public static final int REMIND_TYPE_MONITOR_BLOOD_SUGAR = 4;
    public static final int REMIND_TYPE_REVISIT_MONITOR = 5;
    public static final int REMIND_TYPE_REVISIT = 6;
    public static final int REMIND_TYPE_BLOOD_SUGAR_UN_MEASURE = 7;
    public static final int REMIND_TYPE_HBA1C_UN_MEASURE = 9;
    public static final int REMIND_TYPE_MONITOR_BLOOD_PRESSURE = 10;
}
