package com.comvee.cdms.bloodmonitor.constant;

/**
 * @author ZhiGe
 * @description
 * @date 2018/2/1 16:28 create
 */
public class MonitorPlanConstant {

    /**
     * 监测方案模板类型 1 web 2 app
     */
    public static final int MONITOR_TYPE_WEB = 1;
    public static final int MONITOR_TYPE_APP = 2;

    /**
     * 监测方案类型 1  长期 2 临时
     */
    public static final int PLAN_TYPE_LONG = 1;
    public static final int PLAN_TYPE_TEMPORARY = 2;

    /**
     * 方案是否有效 1 有效 0 无效
     */
    public static final int PLAN_IN_PROGRESS_YES = 1;
    public static final int PLAN_IN_PROGRESS_NO = 0;

    /**
     * 模板类型 1 系统模板  2自定义模板
     */
    public static final int TEMPLATE_PLAN_TYPE_SYSTEM = 1;
    public static final int TEMPLATE_PlAN_TYPE_CUSTOM = 2;

    /**
     * 模板权限 1 全院 2 个人
     */
    public static final int EMPLATE_PLAN_PERMISSION_ALL = 1;
    public static final int EMPLATE_PLAN_PERMISSION_PERSION = 2;

    /**
     * 监测方案详细类型 0 2型 1 妊娠 2 高血压
     */
    public static final int EOH_TYPE_DIABETES_2 = 0;
    public static final int EOH_TYPE_DIABETES_GESTATION = 1;
    public static final int EOH_TYPE_HYPERTENSION = 2;

    /**
     * 监测方案病种  1 糖尿病 2 高血压
     */
    public static final int ILLNESS_TYPE_DIABETES = 1;
    public static final int ILLNESS_TYPE_HYPERTENSION = 2;

}
