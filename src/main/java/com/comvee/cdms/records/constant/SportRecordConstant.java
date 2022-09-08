package com.comvee.cdms.records.constant;

public class SportRecordConstant {
    //操作人员类型 1:医护人员  2:患者
    public static int OPERATION_TYPE_MEDIC = 1;//医护人员
    public static int OPERATION_TYPE_MEMBER = 2;//患者

    //录入来源 1:患者小程序、2:动态血糖记一记
    public static int ORIGIN_MINI_PROGRAM = 1;
    public static int ORIGIN_DY_BLOOD = 2;

    //运动类型 1： 运动库类型 2：自定义类型
    public static int SPORT_TYPE_LIB_ITEM = 1;
    public static int SPORT_TYPE_USER_DEFINED = 2;

    //默认代谢当量 低 3 中 4 高 6
    public static double DEFEAT_LOW_MET = 3;
    public static double DEFEAT_MIDDLE_MET = 4;
    public static double DEFEAT_HIGH_MET = 6;

    //梅脱 静息坐位时的代谢水平
    public static double MET = 0.0167;
}
