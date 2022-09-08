package com.comvee.cdms.foot.vo;

/**
 * @author: suyz
 * @date: 2019/6/12
 */
public class ReportRelateStatusVO {

    private Integer abiRelate;
    private Integer vptRelate;
    private Integer eyesRelate;
    /**
     * 肌电图
     */
    private Integer emgRelate;

    public Integer getEmgRelate() {
        return emgRelate;
    }

    public void setEmgRelate(Integer emgRelate) {
        this.emgRelate = emgRelate;
    }

    public Integer getAbiRelate() {
        return abiRelate;
    }

    public void setAbiRelate(Integer abiRelate) {
        this.abiRelate = abiRelate;
    }

    public Integer getVptRelate() {
        return vptRelate;
    }

    public void setVptRelate(Integer vptRelate) {
        this.vptRelate = vptRelate;
    }

    public Integer getEyesRelate() {
        return eyesRelate;
    }

    public void setEyesRelate(Integer eyesRelate) {
        this.eyesRelate = eyesRelate;
    }
}
