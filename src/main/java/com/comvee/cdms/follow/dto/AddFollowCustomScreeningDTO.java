package com.comvee.cdms.follow.dto;

/**
 * @author: suyz
 * @date: 2019/4/15
 */
public class AddFollowCustomScreeningDTO {

    private String idCard;

    private String secretKey;

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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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
