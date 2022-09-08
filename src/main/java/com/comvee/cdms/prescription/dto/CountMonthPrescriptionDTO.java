package com.comvee.cdms.prescription.dto;

/**
 * @author: suyz
 * @date: 2018/10/24
 */
public class CountMonthPrescriptionDTO {

    private String year;
    private String doctorId;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
