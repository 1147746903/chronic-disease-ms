package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

/**
 * 日常生活习惯
 */
public class CurrentLifeDailyVO implements Serializable {
    /**
     * 是否吸烟 1:抽烟 2:不抽
     */
    private Integer hasSmoke;
    /**
     * 抽烟数量 X根/天
     */
    private Integer smokeNum;
    /**
     * 烟龄
     */
    private Double somkeYear;
    /**
     * 喝酒频率
     */
    private String drinkTime;
    /**
     * YJQK02
     */
    private String drinkRate;
    /**
     * 红酒
     */
    private Double redWineE;
    /**
     * 啤酒
     */
    private Double beerE;
    /**
     * 黄酒
     */
    private Double yellowWineE;
    /**
     * 高度白酒
     */
    private Double whiteSpiritHighE;
    /**
     * 白酒
     */
    private Double whiteSpiritE;

    public Integer getHasSmoke() {
        return hasSmoke;
    }

    public void setHasSmoke(Integer hasSmoke) {
        this.hasSmoke = hasSmoke;
    }

    public Integer getSmokeNum() {
        return smokeNum;
    }

    public void setSmokeNum(Integer smokeNum) {
        this.smokeNum = smokeNum;
    }

    public Double getSomkeYear() {
        return somkeYear;
    }

    public void setSomkeYear(Double somkeYear) {
        this.somkeYear = somkeYear;
    }

    public String getDrinkTime() {
        return drinkTime;
    }

    public void setDrinkTime(String drinkTime) {
        this.drinkTime = drinkTime;
    }

    public String getDrinkRate() {
        return drinkRate;
    }

    public void setDrinkRate(String drinkRate) {
        this.drinkRate = drinkRate;
    }

    public Double getRedWineE() {
        return redWineE;
    }

    public void setRedWineE(Double redWineE) {
        this.redWineE = redWineE;
    }

    public Double getBeerE() {
        return beerE;
    }

    public void setBeerE(Double beerE) {
        this.beerE = beerE;
    }

    public Double getYellowWineE() {
        return yellowWineE;
    }

    public void setYellowWineE(Double yellowWineE) {
        this.yellowWineE = yellowWineE;
    }

    public Double getWhiteSpiritHighE() {
        return whiteSpiritHighE;
    }

    public void setWhiteSpiritHighE(Double whiteSpiritHighE) {
        this.whiteSpiritHighE = whiteSpiritHighE;
    }

    public Double getWhiteSpiritE() {
        return whiteSpiritE;
    }

    public void setWhiteSpiritE(Double whiteSpiritE) {
        this.whiteSpiritE = whiteSpiritE;
    }

    @Override
    public String toString() {
        return "LifeDailyVO{" +
                "hasSmoke=" + hasSmoke +
                ", smokeNum=" + smokeNum +
                ", somkeYear=" + somkeYear +
                ", drinkTime='" + drinkTime + '\'' +
                ", drinkRate='" + drinkRate + '\'' +
                ", redWineE=" + redWineE +
                ", beerE=" + beerE +
                ", yellowWineE=" + yellowWineE +
                ", whiteSpiritHighE=" + whiteSpiritHighE +
                ", whiteSpiritE=" + whiteSpiritE +
                '}';
    }
}
