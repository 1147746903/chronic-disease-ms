package com.comvee.cdms.defender.wechat.vo;

import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2022/1/20
 */
public class DailyQuestionVO {

    /**
     * 主键id
     */
    private String sid;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 入组时间code
     */
    private String groupCode;
    /**
     * 推荐课程
     */
    private String recommendCourse;
    /**
     * 题目
     */
    private String title;
    /**
     * 答案选项
     */
    private String answerOptions;
    private List<Map<String,Object>> answerList;
    /**
     * 正确答案
     */
    private String answerKey;
    /**
     * 解析
     */
    private String analyse;
    /**
     * 分类（1并发症2监测3药物4饮食5运动6胰岛素7其他）
     */
    private Integer type;
    /**
     * 分类(1通用版2标签版)
     */
    private Integer twoType;

    //题目类型 1单选题2多选题3主观题',
    private Integer quesType;
    /**
     * 标记
     */
    private String quesSign;
    /**
     * 标签
     */
    private String quesTag;
    /**
     * 难度
     */
    private String lever;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否有效1有效0无效
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getRecommendCourse() {
        return recommendCourse;
    }

    public void setRecommendCourse(String recommendCourse) {
        this.recommendCourse = recommendCourse;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTwoType() {
        return twoType;
    }

    public void setTwoType(Integer twoType) {
        this.twoType = twoType;
    }

    public Integer getQuesType() {
        return quesType;
    }

    public void setQuesType(Integer quesType) {
        this.quesType = quesType;
    }

    public String getQuesSign() {
        return quesSign;
    }

    public void setQuesSign(String quesSign) {
        this.quesSign = quesSign;
    }

    public String getQuesTag() {
        return quesTag;
    }

    public void setQuesTag(String quesTag) {
        this.quesTag = quesTag;
    }

    public String getLever() {
        return lever;
    }

    public void setLever(String lever) {
        this.lever = lever;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
