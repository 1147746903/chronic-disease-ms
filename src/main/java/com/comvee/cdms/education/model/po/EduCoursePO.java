package com.comvee.cdms.education.model.po;

import java.io.Serializable;

/**
 * 远程教育课程(EduCoursePO)实体类
 *
 * @author makejava
 * @since 2022-01-27 09:58:10
 */
public class EduCoursePO implements Serializable {
    private static final long serialVersionUID = -15464309068083550L;
    /**
     * 主键
     */
    private String sid;
    private String courseNo;
    /**
     * 课程标题
     */
    private String title;
    /**
     * 类型 1视频2文章
     */
    private Integer courseType;
    /**
     * 类别表id
     */
    private String classId;
    /**
     * 状态 1启用0禁用
     */
    private Integer courseStatus;
    /**
     * 主图
     */
    private String imgUrl;
    /**
     * 简介
     */
    private String intr;
    /**
     * 讲师名称
     */
    private String teacherName;
    /**
     * 视频地址
     */
    private String vedioUrl;
    /**
     * 文章内容
     */
    private String articleContent;
    /**
     * 时长
     */
    private String duration;
    /**
     * 评分
     */
    private String score;
    /**
     * 好评度
     */
    private String praise;
    /**
     * 观看次数
     */
    private String viewNum;
    /**
     * 观看人数
     */
    private String peopleNum;
    /**
     * 操作人员
     */
    private String operatorId;
    /**
     * 是否有效
     */
    private Integer isValid;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 添加时间
     */
    private String insertDt;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(Integer courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIntr() {
        return intr;
    }

    public void setIntr(String intr) {
        this.intr = intr;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }
}
