package com.comvee.cdms.member.vo;

/**
 * @Author linr
 * @Date 2021/8/17
 */
public class ListInHospitalLogVO {
    private String hospitalName;
    private String departName;
    private String doctorName;
    private String inTime;//住院日期
    private String outTime;//出院日期
    private String inHospitalDay;//住院天数

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getInHospitalDay() {
        return inHospitalDay;
    }

    public void setInHospitalDay(String inHospitalDay) {
        this.inHospitalDay = inHospitalDay;
    }
}
