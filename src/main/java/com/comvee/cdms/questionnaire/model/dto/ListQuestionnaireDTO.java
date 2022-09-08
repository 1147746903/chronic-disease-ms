package com.comvee.cdms.questionnaire.model.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: suyz
 * @date: 2019/3/18
 */
public class ListQuestionnaireDTO {

    @NotEmpty
    private String memberId;
//    @NotEmpty
    private String doctorId;

    //问卷状态 1 未填写 2 草稿 3 已完成
    private String questionStatus;

    //问卷类型 1 糖尿病知识测试 2 患者自我管理行为评估 3 糖尿病授权评分测试  4 糖尿病足风险评估 5 足部护理评估量表
    private String questionType;

    private String hospitalId;



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

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
