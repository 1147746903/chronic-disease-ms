package com.comvee.cdms.tcm.model.po;

import java.util.List;

public class TcmCollectQuePO {
    private String sid;
    private Integer queIndex;//题目序号
    private String queTopic;//题目
    private Integer queType;//显示条件 1必有 2 男性 3 女性
    private Integer ansType;//选项类型 1 单选 2 多选
    private String insertDt;
    private String updateDt;
    private Integer valid;
    private List<TcmCollectQueAnsPO> ans;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public List<TcmCollectQueAnsPO> getAns() {
        return ans;
    }

    public void setAns(List<TcmCollectQueAnsPO> ans) {
        this.ans = ans;
    }
}
