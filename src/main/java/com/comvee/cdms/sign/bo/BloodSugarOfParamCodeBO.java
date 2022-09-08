package com.comvee.cdms.sign.bo;

import java.io.Serializable;

public class BloodSugarOfParamCodeBO implements Serializable {
    private String paramCode;
    private Integer num;
    private String level;

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamCode() {
        return paramCode;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
