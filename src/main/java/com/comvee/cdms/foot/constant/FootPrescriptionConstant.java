package com.comvee.cdms.foot.constant;

/**
 * @author: suyz
 * @date: 2019/4/8
 */
public class FootPrescriptionConstant {

    /**
     * 是否关联报告 1 是 0 否
     */
    public static final int HAS_RELATE_REPORT_YES = 1;
    public static final int HAS_RELATE_REPORT_NO = 0;

    /**
     * 足部处方保存桩体  1 新建 2 草稿 3 已完成
     */
    public static final int SAVE_TYPE_NEW = 1;
    public static final int SAVE_TYPE_DRAFT = 2;
    public static final int SAVE_TYPE_FINISH = 3;

    /**
     * 评估结果code
     */
    public static final String ASSESS_CODE_EGFR = "eGFR";
    public static final String ASSESS_CODE_WAGNER = "Wagner";
}
