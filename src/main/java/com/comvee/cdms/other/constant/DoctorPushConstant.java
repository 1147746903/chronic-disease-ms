package com.comvee.cdms.other.constant;

/**
 * @author: suyz
 * @date: 2019/1/4
 */
public class DoctorPushConstant {

    /**
     * 设备类型 1 安卓 2 ios
     */
    public static final int DEVICE_TYPE_ANDROID = 1;
    public static final int DEVICE_TYPE_IOS = 2;

    /**
     * 推送状态 1 未推送 2 推送成功 3 推送失败
     */
    public static final int SEND_STATUS_NO = 1;
    public static final int SEND_STATUS_SUCCESS = 2;
    public static final int SEND_STATUS_FAIL = 3;


    /**
     * 推送类型
     */
    public static final int PUSH_TYPE_DIALOGUE = 1;
    public static final int PUSH_TYPE_SIGN = 2;


    /**
     * app推送 业务类型
     * 1 对话 2 血糖异常 3 血压异常 4 BMI异常 5糖化血红蛋白异常
     */
    public static final int PUSH_BUSINESS_TYPE_DIALOGUE = 1;
    public static final int PUSH_BUSINESS_TYPE_SIGN_BLOOD_SUGAR = 2;
    public static final int PUSH_BUSINESS_TYPE_SIGN_BLOOD_PRESSURE = 3;
    public static final int PUSH_BUSINESS_TYPE_SIGN_BMI = 4;
    public static final int PUSH_BUSINESS_TYPE_SIGN_HBA = 5;

    /**
     * 推送状态 1 开 0 关闭
     */
    public static final int PUSH_STATUS_OPEN = 1;
    public static final int PUSH_STATUS_CLOSE = 0;
}
