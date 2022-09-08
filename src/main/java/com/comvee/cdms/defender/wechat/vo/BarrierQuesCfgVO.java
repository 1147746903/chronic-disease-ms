package com.comvee.cdms.defender.wechat.vo;

import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2021/12/8
 */
public class BarrierQuesCfgVO {

    private String sid;
    private String title;
    private String answerOptions;
    private List<Map<String,Object>> answerList;
    private String answerKey;
    private String analyse;
    private Integer quesType;//题目类型 1单选题2多选题',
    private Integer type;//分类（1并发症2监测3生活方式4药物5饮食6运动7其他-小知识）',
    private Integer barrierType;//关卡类型 1试炼场2普通
    private String tag;
    private String courseId;//关联课程id
    private String courseTitle;//关联课程标题
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

    public List<Map<String, Object>> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Map<String, Object>> answerList) {
        this.answerList = answerList;
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

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
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

}
