package com.comvee.cdms.complication.model.dto;

/**
 * @author: suyz
 * @date: 2019/4/16
 */
public class ScreeningStatsParam {

    private Boolean allGood;
    private Boolean vptBad;
    private Boolean abiBad;
    private Boolean allBad;
    private Boolean hasBad;
    private String doctorId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Boolean getHasBad() {
        return hasBad;
    }

    public void setHasBad(Boolean hasBad) {
        this.hasBad = hasBad;
    }

    public Boolean getAllGood() {
        return allGood;
    }

    public void setAllGood(Boolean allGood) {
        this.allGood = allGood;
    }

    public Boolean getVptBad() {
        return vptBad;
    }

    public void setVptBad(Boolean vptBad) {
        this.vptBad = vptBad;
    }

    public Boolean getAbiBad() {
        return abiBad;
    }

    public void setAbiBad(Boolean abiBad) {
        this.abiBad = abiBad;
    }

    public Boolean getAllBad() {
        return allBad;
    }

    public void setAllBad(Boolean allBad) {
        this.allBad = allBad;
    }
}
