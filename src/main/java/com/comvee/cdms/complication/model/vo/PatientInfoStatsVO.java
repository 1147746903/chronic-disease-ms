package com.comvee.cdms.complication.model.vo;

/**
 * @author: suyz
 * @date: 2019/4/16
 */
public class PatientInfoStatsVO {

    private Long manNumber;
    private Long womanNumber;
    private Long low20;
    private Long age20To40;
    private Long age40To60;
    private Long age60To70;
    private Long high70;

    public Long getManNumber() {
        return manNumber;
    }

    public void setManNumber(Long manNumber) {
        this.manNumber = manNumber;
    }

    public Long getWomanNumber() {
        return womanNumber;
    }

    public void setWomanNumber(Long womanNumber) {
        this.womanNumber = womanNumber;
    }

    public Long getLow20() {
        return low20;
    }

    public void setLow20(Long low20) {
        this.low20 = low20;
    }

    public Long getAge20To40() {
        return age20To40;
    }

    public void setAge20To40(Long age20To40) {
        this.age20To40 = age20To40;
    }

    public Long getAge40To60() {
        return age40To60;
    }

    public void setAge40To60(Long age40To60) {
        this.age40To60 = age40To60;
    }

    public Long getAge60To70() {
        return age60To70;
    }

    public void setAge60To70(Long age60To70) {
        this.age60To70 = age60To70;
    }

    public Long getHigh70() {
        return high70;
    }

    public void setHigh70(Long high70) {
        this.high70 = high70;
    }
}
