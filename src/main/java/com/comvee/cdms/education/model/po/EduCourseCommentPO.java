package com.comvee.cdms.education.model.po;

import java.io.Serializable;

/**
 * 远程教育课程评论(EduCourseCommentPO)实体类
 *
 * @author makejava
 * @since 2022-01-28 09:58:09
 */
public class EduCourseCommentPO implements Serializable {
    private static final long serialVersionUID = -14525665520942822L;
    /**
     * 主键
     */
    private String sid;
    /**
     * 医生id
     */
    private String doctorId;
    /**
     * 课程id
     */
    private String courseId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 是否匿名1匿名0不匿名
     */
    private Integer isAnoy;

    private Integer isShow;//是否显示 1显示0隐藏

    /**
     * 星星
     */
    private Integer star;
    /**
     * 得分
     */
    private String score;
    /**
     * 等级 1好评2中评3差评
     */
    private Integer lever;
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
    private Integer origin;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsAnoy() {
        return isAnoy;
    }

    public void setIsAnoy(Integer isAnoy) {
        this.isAnoy = isAnoy;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getLever() {
        return lever;
    }

    public void setLever(Integer lever) {
        this.lever = lever;
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

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
}
