package com.comvee.cdms.healthrecord.model.vo;

public class HealthRecordStatsVO {

    private Long todayCheckup;
    private Long totalCheckup;
    private Long totalPeople;
    private Long diabetesPeople;
    private Long hypertensionPeople;

    public Long getTodayCheckup() {
        return todayCheckup;
    }

    public void setTodayCheckup(Long todayCheckup) {
        this.todayCheckup = todayCheckup;
    }

    public Long getTotalCheckup() {
        return totalCheckup;
    }

    public void setTotalCheckup(Long totalCheckup) {
        this.totalCheckup = totalCheckup;
    }

    public Long getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(Long totalPeople) {
        this.totalPeople = totalPeople;
    }

    public Long getDiabetesPeople() {
        return diabetesPeople;
    }

    public void setDiabetesPeople(Long diabetesPeople) {
        this.diabetesPeople = diabetesPeople;
    }

    public Long getHypertensionPeople() {
        return hypertensionPeople;
    }

    public void setHypertensionPeople(Long hypertensionPeople) {
        this.hypertensionPeople = hypertensionPeople;
    }
}
