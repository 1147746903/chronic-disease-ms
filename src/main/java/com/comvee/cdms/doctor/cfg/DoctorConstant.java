package com.comvee.cdms.doctor.cfg;

/**
 * @author wyc
 * @date 2019/11/21 9:55
 */
public class DoctorConstant {
    /**
     * 管理病种 1:糖尿病 2:高血压
     */
    public static final String ENTITY_TYPE_TNB = "1";
    public static final String ENTITY_TYPE_GXY = "2";
    public static final String ENTITY_TYPE_FAT = "3";

    /**
     * 医生服务信息开启状态 0 关闭 1 开启
     */
    public static final Integer OPEN_STATUS_YES = 1;
    public static final Integer OPEN_STATUS_NO = 0;

    /**
     * 是否可以切换医院 0 否 1 是
     */
    public static final Integer SWITCH_HOS_YES = 1;
    public static final Integer SWITCH_HOS_NO = 0;


    public static final Integer DOCTOR_LEVEL_COUNTY = 1;
    public static final Integer DOCTOR_LEVEL_TOWN = 2;
    public static final Integer DOCTOR_LEVEL_VILLAGE = 3;
}
