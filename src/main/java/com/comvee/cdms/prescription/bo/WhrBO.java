package com.comvee.cdms.prescription.bo;

public class WhrBO {

    /**
     * 腰围
     */
    private String waist;

    /**
     * 臀围
     */
    private String hip;

    /**
     * 腰臀比
     */
    private String whr;

    /**
     * 记录时间
     */
    private String recordDt;

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

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }
}