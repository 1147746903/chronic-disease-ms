package com.comvee.cdms.clinicaldiagnosis.constant;

public class YzConstant {

    /**
     * 医嘱状态 1 新开 2 已下发 3 执行中 4 已执行完 5 已停止 6 已作废
     */
    public final static int YZ_STATUS_NEW = 1;
    public final static int YZ_STATUS_SEND = 2;
    public final static int YZ_STATUS_EXECUTING = 3;
    public final static int YZ_STATUS_EXECUTED = 4;
    public final static int YZ_STATUS_STOP = 5;
    public final static int YZ_STATUS_ABOLISH = 6;

    /**
     * 来源 1 his (院内) 2 其他 （手动） 3 虚拟病区
     */
    public final static int RECORD_ORIGIN_HIS = 1;
    public final static int RECORD_ORIGIN_OTHER = 2;
    public final static int RECORD_ORIGIN_VIRTUAL_WARD = 3;

    /**
     * 胰岛素医嘱code
     */
    public static final String YZ_ITEM_CODE_INSULIN_PUMP = "insulinPump";
    /**
     * 血酮医嘱code
     */
    public static final String YZ_ITEM_CODE_BLOOD_KETONE = "bloodKetone";

    /**
     * 废除 20220421 改成文本
     * 使用方式 1 其他 2 皮下注射 3 静脉注射
     */
    public static final int USE_DRUG_WAY_OTHER = 1;
    public static final int USE_DRUG_WAY_SKIN_POP = 2;
    public static final int USE_DRUG_WAY_INTRAVENOUS_INJECTION = 3;
    /**
     * 医嘱类型: 1:长期医嘱 2:临时医嘱
     */
    public static final int LONG_ADVICE = 1;
    public static final int TEMPORARY_ADVICE = 2;

    /**
     * 医嘱类型 1 长期医嘱 2 临时医嘱
     */
    public static final int YZ_TYPE_LONG_TERM = 1;
    public static final int YZ_TYPE_TEMPORARY = 2;
}
