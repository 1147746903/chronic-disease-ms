package com.comvee.cdms.dybloodsugar.po;

public class DyBloodSugarReportPO {

    private String sid;

    /**
     * 探头号
     */
    private String sensorNo;


    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 医生名字
     */
    private String doctorName;

    /**
     * 报告正文
     */
    private String details;


    //开始时间
    private String startDt;

    //结束时间
    private String endDt;

    /**
     * 修改时间
     */
    private String modifyDt;

    /**
     * 创建时间
     */
    private String insertDt;

    private int isValid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    @Override
    public String toString() {
        return "DyBloodSugarReportPO{" +
                "sid='" + sid + '\'' +
                ", sensorNo='" + sensorNo + '\'' +
                ", details='" + details + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", insertDt='" + insertDt + '\'' +
                '}';
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
}
