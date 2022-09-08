package com.comvee.cdms.follow.bo;

/**
 * @author: suyz
 * @date: 2018/11/12
 */
public class DialogueFollowReportBO {

    private String name;
    private Integer followType;
    private String doctorName;
    private String date;
    private String time;
    private Integer textType;
    private String mainProblem;
    private Integer fType;  //随访类型 1 通用 2 华西

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
    }

    public String getMainProblem() {
        return mainProblem;
    }

    public void setMainProblem(String mainProblem) {
        this.mainProblem = mainProblem;
    }

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }
}

