package com.comvee.cdms.checkresult.constant;

public class CheckoutConstant {

    /**
     * 检验数据来源 1 his同步 2 系统记录  4 处方 5 随访 6医生端 7患者端
     */
    public static final int RECORD_ORIGIN_HIS = 1;
    public static final int RECORD_ORIGIN_SYS = 2;
    public static final int RECORD_ORIGIN_PRESCRIPTION = 4;
    public static final int RECORD_ORIGIN_FOLLOW = 5;
    public static final int RECORD_ORIGIN_DOCTOR_WEHCAT = 6;
    public static final int RECORD_ORIGIN_PATIENT_WECHAT = 7;


    /**
     *  随访、处方中 检验数据类型dataType 1 血糖 2 血压 3 bmi 4 腰臀比 5 hba1c 6其他
     */
    public static final int DATATYPE_BLOOD_SUGAR = 1;
    public static final int DATATYPE_BLOOD_PRESSURE = 2;
    public static final int DATATYPE_BMI = 3;
    public static final int DATATYPE_WHR = 4;
    public static final int DATATYPE_HBA1C = 5;
    public static final int DATATYPE_OTHER = 6;


    public static final int VISIT_TYPE_OUT_PATIENT = 1;
    public static final int VISIT_TYPE_IN_HOSPITAL = 2;
    public static final int VISIT_TYPE_OTHER = 3;



    /**
     * 数据同步任务类型  1检验 2检查 3医嘱
     */
    public static final int DATA_SYNC_TASK_CHECK = 1;
    public static final int DATA_SYNC_TASK_INSPECTION = 2;
    public static final int DATA_SYNC_TASK_YZ = 3;


    /**
     * 数据同步任务状态 0未执行 1成功 2失败
     */
    public static final int DATA_SYNC_TASK_IN = 0;
    public static final int DATA_SYNC_TASK_SUCCESS = 1;
    public static final int DATA_SYNC_TASK_FAILED = 2;
}
