package com.comvee.cdms.prescription.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/24
 */
public class CountPrescriptionDTO implements Serializable {

    private static final long serialVersionUID = 8854519670380875349L;

    private String doctorId;
    private Integer schedule;
    private List<String> doctorIdList;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }
}
