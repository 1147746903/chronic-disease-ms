package com.comvee.cdms.complication.model.dto;

public class ACRDataInputDto {
    private Double acr;
    private Double alb;
    private Double creat;
    private String screeningId;


    public Double getAcr() {
        return acr;
    }

    public void setAcr(Double acr) {
        this.acr = acr;
    }

    public Double getAlb() {
        return alb;
    }

    public void setAlb(Double alb) {
        this.alb = alb;
    }

    public Double getCreat() {
        return creat;
    }

    public void setCreat(Double creat) {
        this.creat = creat;
    }

    public String getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }

}
