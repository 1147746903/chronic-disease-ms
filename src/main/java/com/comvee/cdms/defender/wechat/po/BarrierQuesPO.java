package com.comvee.cdms.defender.wechat.po;

import java.io.Serializable;

/**
 * 知识挑战-关卡问题表(BarrierQuesPO)实体类
 *
 * @author makejava
 * @since 2021-12-03 15:52:43
 */
public class BarrierQuesPO implements Serializable {
    private static final long serialVersionUID = 316961640082938393L;
    /**
     * 主键id
     */
    private String sid;
    private String batchId;//重复闯关批次id
    /**
     * 关卡id
     */
    private String barrierId;
    /**
     * 题目id
     */
    private String quesId;

    private Integer sort;
    /**
     * 答案json
     */
    private String answer;
    private Integer isCorrect;//是否正确1正确0不正确
    /**
     * 是否有效(1有效0无效)
     */
    private Integer isValid;
    /**
     * 添加时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;

    private Integer overTime;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getBarrierId() {
        return barrierId;
    }

    public void setBarrierId(String barrierId) {
        this.barrierId = barrierId;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Integer isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Integer getOverTime() {
        return overTime;
    }

    public void setOverTime(Integer overTime) {
        this.overTime = overTime;
    }
}
