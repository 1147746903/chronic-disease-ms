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
public class AssessQuestionnaireDTO {

    /**
     * 问卷类型
     * question_type
     */
    @NotNull
    private Integer questionType;

    /**
     * 问卷内容json
     * content_json
     */
    @NotEmpty
    private String contentJson;

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }
}