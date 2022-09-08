package com.comvee.cdms.differentlevels.bo;

public class DiffLevelsChartPointInfoBO {
    /**
     * 日期时间
     */
    private String dt;
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
     * 自我管理行为评分
     */
    private String zwglxwpf;
    /**
     * 糖尿病足部风险评估
     */
    private String tnbzfxpgdj;
    /**
     * 层级
     */
    private Integer layer;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
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

    public String getZwglxwpf() {
        return zwglxwpf;
    }

    public void setZwglxwpf(String zwglxwpf) {
        this.zwglxwpf = zwglxwpf;
    }

    public String getTnbzfxpgdj() {
        return tnbzfxpgdj;
    }

    public void setTnbzfxpgdj(String tnbzfxpgdj) {
        this.tnbzfxpgdj = tnbzfxpgdj;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }
}
