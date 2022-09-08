package com.comvee.cdms.prescription.cfg;

public class PrescriptionConstant {
    /**
     * 管理处方最新版本编号
     */
    public final static Integer PRESCRIPTION_VERSION = 2;

    /**
     * 控制目标类型
     */
    public final static int MODULE_TYPE_TARGET = 1;
    /**
     * 监测方案类型
     */
    public final static int MODULE_TYPE_MONITOR = 2;
    /**
     * 饮食方案类型
     */
    public final static int MODULE_TYPE_DIET = 3;
    /**
     * 运动方案类型
     */
    public final static int MODULE_TYPE_SPORT = 4;
    /**
     * 知识教育类型
     */
    public final static int MODULE_TYPE_EDU = 5;
    /**
     * 患者档案
     */
    public final static int MODULE_TYPE_ARCHIVES = 6;

    public final static String MODULE_NAME_TARGET = "控制目标";
    public final static String MODULE_NAME_MONITOR = "监测方案";
    public final static String MODULE_NAME_DIET = "饮食方案";
    public final static String MODULE_NAME_SPORT = "运动方案";
    public final static String MODULE_NAME_EDU = "知识教育";
    public final static String MODULE_NAME_ARCHIVES = "患者档案";

    /**
     * 1待制定
     */
    public final static Integer SCHEDULE_FORMULATED = 1;
    /**
     * 2保存草稿
     */
    public final static Integer SCHEDULE_DRAFT = 2;
    /**
     * 3完成
     */
    public final static Integer SCHEDULE_COMPLETE = 3;

    /**
     * 未下发
     */
    public final static Integer UN_HAND_DOWN = 0;
    /**
     * 已下发
     */
    public final static Integer HAND_DOWN = 1;
    /**
     * 添加BMI
     */
    public static final String ADD_MEMBER_DATA_BMI_TYPE = "1";
    /**
     * 添加血压
     */
    public static final String ADD_MEMBER_DATA_BP_TYPE = "2";
    /**
     * 添加血糖
     */
    public static final String ADD_MEMBER_DATA_BS_TYPE = "3";
    /**
     * 添加血脂
     */
    public static final String ADD_MEMBER_DATA_BT_TYPE = "4";

    /**
     * 管理处方类型 0 二型 1 妊娠 2 高血压
     */
    public static final int PRESCRIPTION_EOH_TYPE_DIABETES_2 = 0;
    public static final int PRESCRIPTION_EOH_TYPE_DIABETES_GESTATION = 1;
    public static final int PRESCRIPTION_EOH_TYPE_HYP = 2;
    public static final int PRESCRIPTION_EOH_TYPE_FAT = 3;


    /**
     * 计划类型 1 处方学习计划 2 首诊学习计划
     */
    public static final int KNOWLEDGE_TYPE_PRE = 1;
    public static final int KNOWLEDGE_TYPE_FIRST = 2;

    /**
     * 疾病类型 1 糖尿病 2 高血压
     */
    public static final String TYPE_TNB = "1";
    public static final String TYPE_GXY = "2";
}
