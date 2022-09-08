package com.comvee.cdms.knowledge.bo;

/**
 * @author: suyz
 * @date: 2018/11/14
 */
public class KnowledgeDialogueDataBO {

    private String prescriptionId;
    private Integer week;
    private String doctorName;
    private String pushDt;
    private Integer textType;

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPushDt() {
        return pushDt;
    }

    public void setPushDt(String pushDt) {
        this.pushDt = pushDt;
    }
}
