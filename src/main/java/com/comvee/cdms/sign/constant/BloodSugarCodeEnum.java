package com.comvee.cdms.sign.constant;

/**
 * @Author linr
 * @Date 2021/8/10
 */
public enum BloodSugarCodeEnum {

    BEFOREDAWN("beforedawn","凌晨"),
    BEFOREBREAKFAST("beforeBreakfast","空腹"),
    AFTERBREAKFAST("afterBreakfast","早餐后"),
    BEFORELUNCH("beforeLunch","午餐前"),
    AFTERLUNCH("afterLunch","午餐后"),
    BEFOREDINNER("beforeDinner","晚餐前"),
    AFTERDINNER("afterDinner","晚餐后"),
    BEFORESLEEP("beforeSleep","睡前"),
    RANDOMTIME("randomtime","随机");


    private String code;
    private String name;


    public static String getName(String code){
        BloodSugarCodeEnum[] values = BloodSugarCodeEnum.values();
        for (BloodSugarCodeEnum val: values) {
            if(val.code.equals(code) ){
                return val.name;
            }
        }
        return null;
    }


     BloodSugarCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
