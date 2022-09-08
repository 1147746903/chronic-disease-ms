package com.comvee.cdms.doctor.vo;

public class PersonalModuleInfoVO<T> {
    /**
     * 模块名称
     */
    private String name;
    /**
     * 模块
     */
    private T module;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getModule() {
        return module;
    }

    public void setModule(T module) {
        this.module = module;
    }
}
