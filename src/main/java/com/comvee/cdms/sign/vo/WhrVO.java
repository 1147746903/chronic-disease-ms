package com.comvee.cdms.sign.vo;

import java.io.Serializable;

public class WhrVO implements Serializable {
    private String recordDt;
    private String waist;
    private String hip;
    private String whr;
    private String lessWaist = "90";
    private String lessWhr = "0.9";
    private Integer origin;
    private String sid;

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getHip() {
        return hip;
    }

    public void setHip(String hip) {
        this.hip = hip;
    }

    public String getWhr() {
        return whr;
    }

    public void setWhr(String whr) {
        this.whr = whr;
    }

    public String getLessWaist() {
        return lessWaist;
    }

    public void setLessWaist(String lessWaist) {
        this.lessWaist = lessWaist;
    }

    public String getLessWhr() {
        return lessWhr;
    }

    public void setLessWhr(String lessWhr) {
        this.lessWhr = lessWhr;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
