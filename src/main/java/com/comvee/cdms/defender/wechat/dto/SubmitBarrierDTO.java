package com.comvee.cdms.defender.wechat.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author linr
 * @Date 2021/12/6
 */
public class SubmitBarrierDTO {
    @NotBlank(message = "sid不允许为空")
    private String sid;
    @NotBlank(message = "score不允许为空")
    private String score;
    @NotBlank(message = "answer不允许为空")
    private String answer;
    @NotNull(message = "overTime不允许为空")
    private Integer overTime;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getOverTime() {
        return overTime;
    }

    public void setOverTime(Integer overTime) {
        this.overTime = overTime;
    }
}
