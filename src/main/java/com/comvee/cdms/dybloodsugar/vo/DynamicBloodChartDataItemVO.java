package com.comvee.cdms.dybloodsugar.vo;

public class DynamicBloodChartDataItemVO {

    private String id;
    private String time;
    private Double value;
    private String doctorRemark;
    private String memberRemark;
    private String doctorRemarkId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorRemarkId() {
        return doctorRemarkId;
    }

    public void setDoctorRemarkId(String doctorRemarkId) {
        this.doctorRemarkId = doctorRemarkId;
    }

    public String getDoctorRemark() {
        return doctorRemark;
    }

    public void setDoctorRemark(String doctorRemark) {
        this.doctorRemark = doctorRemark;
    }

    public String getMemberRemark() {
        return memberRemark;
    }

    public void setMemberRemark(String memberRemark) {
        this.memberRemark = memberRemark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
