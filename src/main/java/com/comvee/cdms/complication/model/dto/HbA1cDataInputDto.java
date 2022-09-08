package com.comvee.cdms.complication.model.dto;

public class HbA1cDataInputDto {
    private Double HbA1c;
    private String screeningId;

    public Double getHbA1c() {
        return HbA1c;
    }

    public void setHbA1c(Double hbA1c) {
        HbA1c = hbA1c;
    }

    public String getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }
}
