package com.comvee.cdms.prescription.vo;

import com.comvee.cdms.prescription.bo.*;

public class MemberLatestSignVO {

    private BmiBO bmi;
    private WhrBO whr;
    private BloodPressureBO bloodPressure;
    private Hba1cBO hba1c;
    private CheckoutBloodFatBO bloodFat;

    public BmiBO getBmi() {
        return bmi;
    }

    public void setBmi(BmiBO bmi) {
        this.bmi = bmi;
    }

    public WhrBO getWhr() {
        return whr;
    }

    public void setWhr(WhrBO whr) {
        this.whr = whr;
    }

    public BloodPressureBO getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(BloodPressureBO bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Hba1cBO getHba1c() {
        return hba1c;
    }

    public void setHba1c(Hba1cBO hba1c) {
        this.hba1c = hba1c;
    }

    public CheckoutBloodFatBO getBloodFat() {
        return bloodFat;
    }

    public void setBloodFat(CheckoutBloodFatBO bloodFat) {
        this.bloodFat = bloodFat;
    }
}
