package com.comvee.cdms.statistics.cfg;

/**
 * @Author linr
 * @Date 2022/2/18
 */
public enum StatisticsNavigationItemEnum {

    /**
     *desc为空则不生成文案！ unit为空则不带上value
     */

    BLOOD_SUGAR_EXCEPTION("sugar","血糖:","mmol/L"),
    BLOOD_HBA1C_EXCEPTION("hba1c","糖化血红蛋白:","%"),
    BLOOD_PRESSURE_EXCEPTION("pressure","血压:","mmHg"),
    BLOOD_SUGAR_LEVER_EXCEPTION("sugar_level","糖尿病分标改变【","，改变原因：点击查看】"),
    BLOOD_PRESSURE_LEVER_EXCEPTION("pressure_level","高血压分层分级改变【","，改变原因：点击查看】"),
    EYE_SCREEN_EXCEPTION("eye_screen","眼底筛查结果异常",""),
    VPT_SCREEN_EXCEPTION("vpt_screen","VPT筛查结果异常",""),
    ABI_SCREEN_EXCEPTION("abi_screen","ABI筛查结果异常",""),
    ALL_SCREEN_EXCEPTION("all_screen","并发症筛查",""),
    TODAY_SCREEN_EXCEPTION("all_screen_day","今日并发症筛查",""),
    TODAY_IN_HOSPITAL_NUM("in_hospital_num","医院建档人数",""),
    TODAY_MANAGE_PRESC_NUM("prescription","今日管理处方",""),
    TODAY_FOLLOW_NUM("follow","今日随访",""),
    MANAGE_MEMBER_NUM("manage_member","管理人数",""),
    DIABETES_MEMBER_NUM("diab_member","糖尿病人数",""),
    HYP_MEMBER_NUM("hyp_member","高血压人数",""),
    DIABETES_MEMBER_DAY_NUM("diab_member_day","日糖尿病人数",""),
    HYP_MEMBER_DAY_NUM("hyp_member_day","日高血压人数",""),
    DIABETES_RATE("diab_rate","糖尿病占比",""),
    HYP_RATE("hyp_rate","高血压占比",""),
    TODAY_VISIT("today_visit","",""),//今日门诊患者人数
    YEAR_CHECK("year_check","",""),//本年度体检患者人数
    ABNORMAL_NUM("abnormal_num","",""),//今日异常患者人数



    sugar_level_id("sugar_level_id","",""),
    pressure_level_id("pressure_level_id","",""),


    ;

    private String code;
    private String desc;
    private String unit;

    StatisticsNavigationItemEnum(String code, String desc, String unit) {
        this.code = code;
        this.desc = desc;
        this.unit = unit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public static String getDesc(String code){
        StatisticsNavigationItemEnum[] values = StatisticsNavigationItemEnum.values();
        for (StatisticsNavigationItemEnum val: values) {
            if(val.code.equals(code) ){
                return val.desc;
            }
        }
        return null;
    }
    public static String getUnit(String code){
        StatisticsNavigationItemEnum[] values = StatisticsNavigationItemEnum.values();
        for (StatisticsNavigationItemEnum val: values) {
            if(val.code.equals(code) ){
                return val.unit;
            }
        }
        return null;
    }
}
