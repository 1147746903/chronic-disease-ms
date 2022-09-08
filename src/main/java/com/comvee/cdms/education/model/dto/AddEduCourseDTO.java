package com.comvee.cdms.education.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author linr
 * @Date 2022/1/29
 */
public class AddEduCourseDTO {

    /**
     * 主键
     */
    private String sid;
    private String courseNo;
    /**
     * 课程标题
     */
    @NotBlank(message = "title不允许为空")
    @Length(max = 30,message = "标题最多30字")
    private String title;
    /**
     * 类型 1视频2文章
     */
    @NotNull(message = "courseType不允许为空")
    private Integer courseType;
    /**
     * 类别表id
     */
    @NotBlank(message = "classId不允许为空")
    private String classId;
    /**
     * 状态 1启用0禁用
     */
    @NotNull(message = "courseStatus不允许为空")
    private Integer courseStatus;
    /**
     * 主图
     */
    @NotBlank(message = "imgUrl不允许为空")
    private String imgUrl;
    /**
     * 简介
     */
    @Length(max = 500,message = "课程简介最多500字")
    private String intr;
    /**
     * 讲师名称
     */
    @Length(max = 6,message = "讲师姓名最多6字")
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
     * 操作人员
     */
    private String operatorId;
    /**
     * 是否有效
     */
    private Integer isValid;

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
}
