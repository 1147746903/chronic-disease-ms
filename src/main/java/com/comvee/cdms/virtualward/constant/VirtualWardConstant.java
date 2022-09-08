package com.comvee.cdms.virtualward.constant;

public class VirtualWardConstant {

    /**
     * 虚拟病区转移状态 1 已转入  2 已转出 3:直接出院
     */
    public static final int TRANSFER_STATUS_IN = 1;
    public static final int TRANSFER_STATUS_OUT = 2;
    public static final int TRANSFER_STATUS_OUT_HOSPITAL = 3;

    /**
     * t_virtual_ward_transfer_apply 申请类型  1 转入 2 转出 3:直接出院
     */
    public static final int APPLY_TYPE_IN = 1;
    public static final int APPLY_TYPE_OUT = 2;

    /**
     * t_virtual_ward_transfer_apply 申请状态 1 待处理 2 同意 3 直接出院
     */
    public static final int APPLY_STATUS_NOT_DEAL = 1;
    public static final int APPLY_STATUS_AGREE = 2;
    public static final int APPLY_STATUS_DIRECT_OUT_HOSPITAL = 3;

    /**
     * 申请列表来源 1 弹窗 2 列表
     */
    public static final int APPLY_LIST_ORIGIN_POP = 1;
    public static final int APPLY_LIST_ORIGIN_LIST = 2;

    /**
     * t_virtual_ward 申请转出状态 1 未申请 2 已申请
     */
    public static final int APPLY_OUT_STATUS_NO = 1;
    public static final int APPLY_OUT_STATUS_YES = 2;

    /**
     * t_virtual_ward 是否使用泵  1 有 0 没有
     */
    public static final int USE_INSULIN_PUMP_STATUS_YES = 1;
    public static final int USE_INSULIN_PUMP_STATUS_NO = 0;

}
