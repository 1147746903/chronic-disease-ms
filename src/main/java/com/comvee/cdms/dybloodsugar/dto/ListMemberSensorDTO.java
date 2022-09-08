package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotNull;

/**
 * @author wyc
 * @date 2020/6/17 16:52
 */
public class ListMemberSensorDTO {

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 医院id
     */
    private String hospitalId;

    @NotNull(message = "住院类型(inHos 1:住院  0:门诊居家)不能为空")
    private Integer inHos;


    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
