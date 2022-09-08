package com.comvee.cdms.defender.wechat.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author linr
 * @Date 2021/12/8
 */
public class UpdateBarrierQuesCfgDTO {
    @NotBlank(message = "sid不允许为空")
    private String sid;
    @NotBlank(message = "title不允许为空")
    private String title;
    @NotBlank(message = "answerOptions不允许为空")
    private String answerOptions;
    @NotBlank(message = "answerKey不允许为空")
    private String answerKey;
    private String analyse;
    @NotNull(message = "quesType不允许为空")
    private Integer quesType;
    private Integer type;
    @NotNull(message = "barrierType不允许为空")
    private Integer barrierType;//关卡类型 1试炼场2普通
    private String tag;
    private String courseId;//关联课程id

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(String answerOptions) {
        this.answerOptions = answerOptions;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    public String getAnalyse() {
        return analyse;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

    public Integer getQuesType() {
        return quesType;
    }

    public void setQuesType(Integer quesType) {
        this.quesType = quesType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(Integer barrierType) {
        this.barrierType = barrierType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
