package com.comvee.cdms.defender.wechat.po;

import java.io.Serializable;

/**
 * 知识挑战-题库表(BarrierQuesCfgPO)实体类
 *
 * @author makejava
 * @since 2021-12-03 15:53:56
 */
public class BarrierQuesCfgPO implements Serializable {
    private static final long serialVersionUID = 603215290713015746L;
    /**
     * 主键id
     */
    private String sid;
    /**
     * 题目
     */
    private String title;
    /**
     * 答案选项
     */
    private String answerOptions;
    /**
     * 正确答案
     */
    private String answerKey;
    /**
     * 解析
     */
    private String analyse;
    private Integer quesType;//题目类型 1单选题2多选题
    /**
     * 分类（1并发症2监测3生活方式4药物5饮食6运动7其他-小知识）
     */
    private Integer type;

    private Integer barrierType;//关卡类型 1试炼场2普通
    /**
     * 标签
     */
    private String tag;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public Integer getQuesType() {
        return quesType;
    }

    public void setQuesType(Integer quesType) {
        this.quesType = quesType;
    }

    public Integer getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(Integer barrierType) {
        this.barrierType = barrierType;
    }
}
