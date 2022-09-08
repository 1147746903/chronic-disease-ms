package com.comvee.cdms.records.constant;

public class DrugsRecordConstant {

    //操作人员类型 1:医护人员  2:患者
    public static int OPERATION_TYPE_MEDIC = 1;//医护人员
    public static int OPERATION_TYPE_MEMBER = 2;//患者

    //录入来源 1:患者小程序、2:动态血糖记一记
    public static int ORIGIN_MINI_PROGRAM = 1;
    public static int ORIGIN_DY_BLOOD = 2;

    //记录类型 1： 药库类型 2：自定义类型
    public static int DRUG_TYPE_LIB_ITEM = 1;
    public static int DRUG_TYPE_USER_DEFINED = 2;
}
