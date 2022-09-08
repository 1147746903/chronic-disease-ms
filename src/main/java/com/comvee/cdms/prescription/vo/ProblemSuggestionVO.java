package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

public class ProblemSuggestionVO implements Serializable {

    /**
     * 问题
     */
    private String problem;
    /**
     * 目前情况
     */
    private String description;
    /**
     * 建议
     */
    private String suggestion;
    /**
     * 编号
     */
    private String code;
    /**
     * 优选
     */
    private String hasChecked;

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHasChecked() {
        return hasChecked;
    }

    public void setHasChecked(String hasChecked) {
        this.hasChecked = hasChecked;
    }

    @Override
    public String toString() {
        return "ProblemSuggestionVO{" +
                "problem='" + problem + '\'' +
                ", description='" + description + '\'' +
                ", suggestion='" + suggestion + '\'' +
                ", code='" + code + '\'' +
                ", hasChecked='" + hasChecked + '\'' +
                '}';
    }
}
