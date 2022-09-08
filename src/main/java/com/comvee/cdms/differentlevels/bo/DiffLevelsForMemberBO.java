package com.comvee.cdms.differentlevels.bo;

import java.io.Serializable;

public class DiffLevelsForMemberBO implements Serializable {
    /**
     * 日期
     */
    private String dt;
    /**
     * 分层评级
     */
    private Integer layer;
    /**
     * DSME/S等级
     */
    private Integer level;
    /**
     * 对比分析
     */
    private String analysis;
    /**
     * 近一周平均空腹血糖
     */
    private String nwGlu0;
    /**
     * 近一周平均餐后血糖
     */
    private String nwGlu;
    /**
     * 糖化血红蛋白
     */
    private String hlb;
    /**
     * 近一周低血糖次数
     */
    private String nwLGlu;
    /**
     * 急性并发症情况
     */
    private Object jxbfzqk;
    /**
     * 慢性并发症情况
     */
    private Object mxbfzqk;
    /**
     * 评估情况
     */
    private Object pgqk;

    /**
     * 是否人为调整 1是 0否
     */
    private Integer adjustment;
    /**
     * 原发性高血压
     */
    private Object anamnesis;

    public Integer getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Integer adjustment) {
        this.adjustment = adjustment;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getNwGlu0() {
        return nwGlu0;
    }

    public void setNwGlu0(String nwGlu0) {
        this.nwGlu0 = nwGlu0;
    }

    public String getNwGlu() {
        return nwGlu;
    }

    public void setNwGlu(String nwGlu) {
        this.nwGlu = nwGlu;
    }

    public String getHlb() {
        return hlb;
    }

    public void setHlb(String hlb) {
        this.hlb = hlb;
    }

    public String getNwLGlu() {
        return nwLGlu;
    }

    public void setNwLGlu(String nwLGlu) {
        this.nwLGlu = nwLGlu;
    }

    public Object getJxbfzqk() {
        return jxbfzqk;
    }

    public void setJxbfzqk(Object jxbfzqk) {
        this.jxbfzqk = jxbfzqk;
    }

    public Object getMxbfzqk() {
        return mxbfzqk;
    }

    public void setMxbfzqk(Object mxbfzqk) {
        this.mxbfzqk = mxbfzqk;
    }

    public Object getPgqk() {
        return pgqk;
    }

    public void setPgqk(Object pgqk) {
        this.pgqk = pgqk;
    }

    public void setAnamnesis(Object anamnesis) {
        this.anamnesis = anamnesis;
    }

    public Object getAnamnesis() {
        return anamnesis;
    }
}
