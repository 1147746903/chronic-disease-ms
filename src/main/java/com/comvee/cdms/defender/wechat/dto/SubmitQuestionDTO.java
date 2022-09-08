package com.comvee.cdms.defender.wechat.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Author linr
 * @Date 2021/11/29
 */
public class SubmitQuestionDTO {
    private String memberId;
    @NotBlank(message = "qid不允许为空")
    private String qid;
    @NotBlank(message = "answer不允许为空")
    private String answer;
    @NotBlank(message = "isCorrect不允许为空")
    private String isCorrect;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }
}
