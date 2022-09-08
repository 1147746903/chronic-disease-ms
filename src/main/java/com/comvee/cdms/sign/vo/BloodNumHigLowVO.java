package com.comvee.cdms.sign.vo;

public class BloodNumHigLowVO {

    //血糖测量总次数、偏高、正常、偏低
    private String totalNums;
    private String high;
    private String nomal;
    private String low;

    public String getTotalNums() {
        return totalNums;
    }

    public void setTotalNums(String totalNums) {
        this.totalNums = totalNums;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getNomal() {
        return nomal;
    }

    public void setNomal(String nomal) {
        this.nomal = nomal;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }
}