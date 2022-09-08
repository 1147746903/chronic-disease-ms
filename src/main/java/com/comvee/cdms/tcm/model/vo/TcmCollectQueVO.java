package com.comvee.cdms.tcm.model.vo;

public class TcmCollectQueVO {
    private String queId;
    private Integer queIndex;//题目序号
    private String queTopic;//题目
    private Integer queType;//显示条件 1必有 2 男性 3 女性
    private Integer ansType;//选项类型 1 单选 2 多选
    private String ansId;
    private Integer ansIndex;
    private String ansDesc;

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public Integer getQueIndex() {
        return queIndex;
    }

    public void setQueIndex(Integer queIndex) {
        this.queIndex = queIndex;
    }

    public String getQueTopic() {
        return queTopic;
    }

    public void setQueTopic(String queTopic) {
        this.queTopic = queTopic;
    }

    public Integer getQueType() {
        return queType;
    }

    public void setQueType(Integer queType) {
        this.queType = queType;
    }

    public Integer getAnsType() {
        return ansType;
    }

    public void setAnsType(Integer ansType) {
        this.ansType = ansType;
    }

    public String getAnsId() {
        return ansId;
    }

    public void setAnsId(String ansId) {
        this.ansId = ansId;
    }

    public Integer getAnsIndex() {
        return ansIndex;
    }

    public void setAnsIndex(Integer ansIndex) {
        this.ansIndex = ansIndex;
    }

    public String getAnsDesc() {
        return ansDesc;
    }

    public void setAnsDesc(String ansDesc) {
        this.ansDesc = ansDesc;
    }
}
