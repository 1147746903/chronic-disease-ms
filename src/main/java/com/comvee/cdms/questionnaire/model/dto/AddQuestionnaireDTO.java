package com.comvee.cdms.questionnaire.model.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_questionnaire
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class AddQuestionnaireDTO {

    /**
     * 问卷类型
     * question_type
     */
    @NotNull
    private Integer questionType;


    /**
     * 患者id
     * member_id
     */
    @NotEmpty
    private String memberId;

    /**
     * 医生id
     * doctor_id
     */
    @NotEmpty
    private String doctorId;

    /**
     * 操作者id
     * operator_id
     */
    private String operatorId;
    /**
     * 创建类型1:问卷管理创建2:自定义随访里面的问卷
     */
    private Integer createType;
    /**
     * 随访内容id
     */
    private String followContentId;

    public String getFollowContentId() {
        return followContentId;
    }

    public void setFollowContentId(String followContentId) {
        this.followContentId = followContentId;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}