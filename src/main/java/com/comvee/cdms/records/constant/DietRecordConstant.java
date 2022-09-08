package com.comvee.cdms.records.constant;

public class DietRecordConstant {
    //操作人员类型 1:医护人员  2:患者
    public static int OPERATION_TYPE_MEDIC = 1;//医护人员
    public static int OPERATION_TYPE_MEMBER = 2;//患者

    //餐时  1:早餐、2：午餐、3：晚餐、4：其他餐时
    public static int DIET_TIME_BREAKFAST = 1;
    public static int DIET_TIME_LUNCH = 2;
    public static int DIET_TIME_DINNER = 3;
    public static int DIET_TIME_OTHER = 4;

    //录入来源 1:患者小程序、2:动态血糖记一记、3:管理处方记录
    public static int ORIGIN_MINI_PROGRAM = 1;
    public static int ORIGIN_DY_REMEMBER = 2;
    public static int ORIGIN_PRESCRIPTION = 3;

    //食物类型 1： 食材库类型 2：自定义类型
    public static int FOOD_TYPE_LIB_ITEM = 1;
    public static int FOOD_TYPE_USER_DEFINED = 2;
}
