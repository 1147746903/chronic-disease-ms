package com.comvee.cdms.checkresult.constant;

import com.comvee.cdms.common.cfg.Config;

import java.util.*;

public class CheckConstant {
    public final static List<String> IMPORTANT_CODES = new ArrayList<>(10);
    static {
        // 血糖
        IMPORTANT_CODES.add(Config.getValueByKey("GLU"));
        // 糖化血红蛋白
        IMPORTANT_CODES.add(Config.getValueByKey("GHB"));
        // C肽
        IMPORTANT_CODES.add(Config.getValueByKey("C-PEPTIDE"));
        // 总胆固醇TC
        IMPORTANT_CODES.add(Config.getValueByKey("TC"));
        // 甘油三酯TG
        IMPORTANT_CODES.add(Config.getValueByKey("TG"));
        // 高密度脂蛋白HDL
        IMPORTANT_CODES.add(Config.getValueByKey("HDL"));
        // 低密度脂蛋白LDL
        IMPORTANT_CODES.add(Config.getValueByKey("LDL"));
        // 尿白蛋白/尿肌酐比值ACR
        IMPORTANT_CODES.add(Config.getValueByKey("ACR"));
        // 肌酐CR
        IMPORTANT_CODES.add(Config.getValueByKey("CR"));
        // 尿酸UA
        IMPORTANT_CODES.add(Config.getValueByKey("UA"));
    }
    //重要指标类型
    public final static Integer TYPE_IMPORTANT = 1;
    //异常指标类型
    public final static Integer TYPE_ABNORMAL = 2;

    public final static Map<String,String> TYPE_MAP = new HashMap<>();
    static {
        //血糖
        TYPE_MAP.put(Config.getValueByKey("GLU"),"GLU");
        //糖化血红蛋白
        TYPE_MAP.put(Config.getValueByKey("GHB"),"GHB");
        //C肽
        TYPE_MAP.put(Config.getValueByKey("C-PEPTIDE"),"C-PEPTIDE");
        //总胆固醇
        TYPE_MAP.put(Config.getValueByKey("TC"),"TC");
        //甘油三酯
        TYPE_MAP.put(Config.getValueByKey("TG"),"TG");
        //高密度脂蛋白
        TYPE_MAP.put(Config.getValueByKey("HDL"),"HDL");
        //低密度脂蛋白
        TYPE_MAP.put(Config.getValueByKey("LDL"),"LDL");
        //尿白蛋白/尿肌酐比值
        TYPE_MAP.put(Config.getValueByKey("ACR"),"ACR");
        //肌酐
        TYPE_MAP.put(Config.getValueByKey("CR"),"CR");
        //尿酸
        TYPE_MAP.put(Config.getValueByKey("UA"),"UA");
        //肾小球滤过率
        TYPE_MAP.put(Config.getValueByKey("eGFR"),"eGFR");
        //CKD分期A
        TYPE_MAP.put(Config.getValueByKey("CKD"),"CKD");
        //G
        TYPE_MAP.put(Config.getValueByKey("G"),"G");
    }

    /**
     * 默认检验展示项目
     */
    public static List<String> DEFAULT_CHECKOUT_CODE_LIST = new ArrayList<>(17);
    static {
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_ogtt_0m");//OGTT 0
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_ogtt_120m");//OGTT 120
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_ct_0m");//C肽 0
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_ct_120m");//C肽 120
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_hba");//糖化血红蛋白
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_tc");//总胆固醇
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_triglyceride");//甘油三酯
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_ldl");//低密度脂蛋白胆固醇
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_hdl");//高密度脂蛋白胆固醇
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_egfr");//肾小球滤过率
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_ckd_g");//CKD分期 G
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_neph_acr");//尿微量白蛋白/肌酐
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_ckd_a");//CKD分期 A
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_gast");//谷氨酸脱羧酶抗体
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_gast");//谷氨酸脱羧酶抗体
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_blood_uric");//尿酸
        DEFAULT_CHECKOUT_CODE_LIST.add("add_check_serum");//肌酐
    }
//    public static final String INSPECT_CODE_

    public final static Map<Integer,String> INSPECTION_TYPE_CODE = new HashMap<>();
    static {
        INSPECTION_TYPE_CODE.put(1, "ABI");
        INSPECTION_TYPE_CODE.put(2, "VPT");
        INSPECTION_TYPE_CODE.put(3, "EYES");
        INSPECTION_TYPE_CODE.put(4, "ACR");

    }

    public final static Map<Integer,String> INSPECTION_TYPE_NAME = new HashMap<>();
    static {
        INSPECTION_TYPE_NAME.put(1, "外周动脉检查");
        INSPECTION_TYPE_NAME.put(2, "震动感觉阈值检查");
        INSPECTION_TYPE_NAME.put(3, "眼底检查");
        INSPECTION_TYPE_NAME.put(4, "ACR筛查");
    }

    public final static Integer INSPECTION_ORIGIN_SYS = 1;
    public final static Integer INSPECTION_ORIGIN_HIS = 2;

    public final static Integer INSPECTION_IN_HOS = 1;
    public final static Integer INSPECTION_NOT_IN_HOS = 0;

}
