package com.comvee.cdms.follow.dto;

/**
 * @author: suyz
 * @date: 2019/4/15
 */
public class AddFollowCustomDTO {

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 医生id
     * doctor_id
     */
    private String doctorId;

    /**
     * 创建者id
     * creator_id
     */
    private String creatorId;

    /**
     * 题目选项字符串，逗号隔开
     * question_str
     */
    private String questionStr;

    /**
     * 随访名称
     * follow_name
     */
    private String followName;

    /**
     * 创建处方时要求的填写人  1 患者 2 医生
     * fill_person
     */
    private Integer fillPerson;

    private String customQuestionData;

    public String getCustomQuestionData() {
        return customQuestionData;
    }

    public void setCustomQuestionData(String customQuestionData) {
        this.customQuestionData = customQuestionData;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getQuestionStr() {
        return questionStr;
    }

    public void setQuestionStr(String questionStr) {
        this.questionStr = questionStr;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public Integer getFillPerson() {
        return fillPerson;
    }

    public void setFillPerson(Integer fillPerson) {
        this.fillPerson = fillPerson;
    }
}
