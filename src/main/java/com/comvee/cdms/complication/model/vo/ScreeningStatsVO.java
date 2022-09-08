package com.comvee.cdms.complication.model.vo;

/**
 * @author: suyz
 * @date: 2019/4/16
 */
public class ScreeningStatsVO {

    private Long allPeople;
    private Long abiAndVptGood;
    private Long abiBad;
    private Long vptBad;
    private Long abiAndVptBad;
    private Long hasBadPeople;

    public Long getHasBadPeople() {
        return hasBadPeople;
    }

    public void setHasBadPeople(Long hasBadPeople) {
        this.hasBadPeople = hasBadPeople;
    }

    public Long getAllPeople() {
        return allPeople;
    }

    public void setAllPeople(Long allPeople) {
        this.allPeople = allPeople;
    }

    public Long getAbiAndVptGood() {
        return abiAndVptGood;
    }

    public void setAbiAndVptGood(Long abiAndVptGood) {
        this.abiAndVptGood = abiAndVptGood;
    }

    public Long getAbiBad() {
        return abiBad;
    }

    public void setAbiBad(Long abiBad) {
        this.abiBad = abiBad;
    }

    public Long getVptBad() {
        return vptBad;
    }

    public void setVptBad(Long vptBad) {
        this.vptBad = vptBad;
    }

    public Long getAbiAndVptBad() {
        return abiAndVptBad;
    }

    public void setAbiAndVptBad(Long abiAndVptBad) {
        this.abiAndVptBad = abiAndVptBad;
    }
}
