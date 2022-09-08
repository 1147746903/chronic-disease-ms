package com.comvee.cdms.prescription.bo;

public class CheckoutBloodFatBO {

    /**
     * 低密度脂蛋白
     */
    private String ldl;
    /**
     * 高密度脂蛋白
     */
    private String hdl;
    /**
     * 甘油三酯
     */
    private String triglyceride;
    /**
     * 总胆固醇
     */
    private String tc;

    /**
     * 尿酸
     */
    private String uric;

    public String getLdl() {
        return ldl;
    }

    public void setLdl(String ldl) {
        this.ldl = ldl;
    }

    public String getHdl() {
        return hdl;
    }

    public void setHdl(String hdl) {
        this.hdl = hdl;
    }

    public String getTriglyceride() {
        return triglyceride;
    }

    public void setTriglyceride(String triglyceride) {
        this.triglyceride = triglyceride;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getUric() {
        return uric;
    }

    public void setUric(String uric) {
        this.uric = uric;
    }
}
