package com.comvee.cdms.defender.wechat.vo;

import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2021/12/6
 */
public class ListBarrierQuesVO {

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
    private List<Map<String,Object>> answerOptions;
    /**
     * 正确答案
     */
    private String answerKey;
    /**
     * 解析
     */
    private String analyse;
    /**
     * 分类（1并发症2监测3生活方式4药物5饮食6运动7其他-小知识）
     */
    private Integer type;
    /**
     * 标签
     */
    private String tag;

    private Integer quesType;//题目类型 1单选题2多选题

    public ListBarrierQuesVO() {
    }

    public ListBarrierQuesVO(String sid, String title, List<Map<String, Object>> answerOptions, String answerKey, String analyse, Integer type, String tag, Integer quesType) {
        this.sid = sid;
        this.title = title;
        this.answerOptions = answerOptions;
        this.answerKey = answerKey;
        this.analyse = analyse;
        this.type = type;
        this.tag = tag;
        this.quesType = quesType;
    }

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

    public List<Map<String, Object>> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<Map<String, Object>> answerOptions) {
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

    public Integer getQuesType() {
        return quesType;
    }

    public void setQuesType(Integer quesType) {
        this.quesType = quesType;
    }
}
