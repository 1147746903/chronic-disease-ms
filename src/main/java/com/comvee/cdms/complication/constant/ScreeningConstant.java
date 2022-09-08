package com.comvee.cdms.complication.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/2/22
 */
public class ScreeningConstant {

    /**
     * 筛查单类型 1 abi 2 vpt 3 眼底
     * 7 肌电图
     */
    public static final int SCREENING_TYPE_ABI = 1;
    public static final int SCREENING_TYPE_VPT = 2;
    public static final int SCREENING_TYPE_EYES = 3;
    public static final int SCREENING_TYPE_ACR = 4;
    public static final int SCREENING_TYPE_HBA1C = 5;
    public static final int SCREENING_TYPE_SIGN = 6;
    public static final int SCREENING_TYPE_EMG = 7;

    /**
     * 筛查单状态 1 未开始 2 已开始 3 待确认 4已完成 5 已确认
     */
    public static final int SCREENING_STATUS_NOT_START = 1;
    public static final int SCREENING_STATUS_STARTING = 2;
    public static final int SCREENING_STATUS_CONFIRM = 3;
    public static final int SCREENING_STATUS_CANCEL = 4;
    public static final int SCREENING_STATUS_FINISH = 5;

    /**
     * 预约状态 1 已预约 0 未预约
     */
    public static final int ORDER_STATUS_NO = 0;
    public static final int ORDER_STATUS_YES = 1;

    /**
     * 待筛查处理状态 1 未处理 2 筛查完成 3 报告已生成 4 手动取消 5 上传成功待确认 6 已确认
     */
    public static final int PRE_DEAL_STATUS_NO = 1;
    public static final int PRE_DEAL_STATUS_SCREENING_OK = 2;
    public static final int PRE_DEAL_STATUS_REPORT_OK = 3;
    public static final int PRE_DEAL_STATUS_REPORT_CANCEL = 4;
    public static final int PRE_DEAL_STATUS_UPLOAD_OK = 5;
    public static final int PRE_DEAL_STATUS_REPORT_CONFIRM = 6;

    /**
     * 筛查来源 1 门诊 2 住院 3 其他
     */
    public static final int ORDER_ORIGIN_OUT = 1;
    public static final int ORDER_ORIGIN_HOSPITAL = 2;
    public static final int ORDER_ORIGIN_OTHER = 3;

    /**
     * 报告编辑状态 1 已编辑 0 未编辑
     */
    public static final int REPORT_EDIT_YES = 1;
    public static final int REPORT_EDIT_NO = 0;

    /**
     * 报告状态 1 正常 2 异常 0 没有结果
     */
    public static final int REPORT_STATUS_GOOD = 1;
    public static final int REPORT_STATUS_BAD = 2;
    public static final int REPORT_STATUS_NO= 0;

    public final static Map<Integer,String> SCREENING_TYPE_NAME = new HashMap<>();
    static {
        SCREENING_TYPE_NAME.put(1, "外周动脉检查");
        SCREENING_TYPE_NAME.put(2, "震动感觉阈值检查");
        SCREENING_TYPE_NAME.put(3, "眼底");
        SCREENING_TYPE_NAME.put(4, "ACR");
        SCREENING_TYPE_NAME.put(5, "HBA1C");
        SCREENING_TYPE_NAME.put(6, "SIGN");
        SCREENING_TYPE_NAME.put(7, "肌电图");
    }

    public static final String ABI_REPORT_URL = "/complication/print/abi.html";
    public static final String VPT_REPORT_URL = "/complication/print/vpt.html";
    public static final String EYES_REPORT_URL = "/complication/print/eyes.html";
}
