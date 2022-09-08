package com.comvee.cdms.complication.model.dto;

/**
 * @author: suyz
 * @date: 2019/6/5
 */
public class EyesReadingFilmParam {

    private String reportId;
    private Integer leftEyesResult;
    private Integer rightEyesResult;
    private String advices;
    private String describe;
    private String doctorId;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Integer getLeftEyesResult() {
        return leftEyesResult;
    }

    public void setLeftEyesResult(Integer leftEyesResult) {
        this.leftEyesResult = leftEyesResult;
    }

    public Integer getRightEyesResult() {
        return rightEyesResult;
    }

    public void setRightEyesResult(Integer rightEyesResult) {
        this.rightEyesResult = rightEyesResult;
    }

    public String getAdvices() {
        return advices;
    }

    public void setAdvices(String advices) {
        this.advices = advices;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
