package com.comvee.cdms.statistics.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: suyz
 * @date: 2018/10/19
 */
public class QueryChartDTO {

    private String doctorId;
    @NotEmpty
    private String year;

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
