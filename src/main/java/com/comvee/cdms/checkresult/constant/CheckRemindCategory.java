package com.comvee.cdms.checkresult.constant;

import com.comvee.cdms.common.utils.EnumUtils;

import java.util.Optional;

/**
 * 检查提醒 项目类别
 */
public enum CheckRemindCategory {

    hba1c("hba1c" ,"糖化检查"),
    urinalysis("urinalysis" ,"尿常规"),
    bloodFat("bloodFat" ,"血脂检查"),
    renal("renal" ,"肾功能相关检查"),
    liver("liver" ,"肝功能检查"),
    eyes("eyes" ,"眼相关检查"),
    footVessels("footVessels" ,"足血管检查"),
    neuropathy("neuropathy" ,"神经病变相关检查"),
    arteriosclerosis("arteriosclerosis" ,"动脉硬化检查"),
    ;

    private String code;
    private String name;

    CheckRemindCategory(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(String code){
        CheckRemindCategory checkRemindCategory = EnumUtils.getEnum(CheckRemindCategory.class , x -> x.getCode().equals(code));
        return Optional.ofNullable(checkRemindCategory).map(x -> x.getName()).orElse("其他");
    }

}
