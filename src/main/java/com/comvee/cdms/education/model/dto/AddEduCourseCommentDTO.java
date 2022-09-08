package com.comvee.cdms.education.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author linr
 * @Date 2022/1/29
 */
public class AddEduCourseCommentDTO {

    /**
     * 医生id
     */
    @NotBlank(message = "doctorId不允许为空")
    private String doctorId;
    /**
     * 课程id
     */
    @NotBlank(message = "courseId不允许为空")
    private String courseId;
    /**
     * 评论内容
     */
    @NotBlank(message = "content不允许为空")
    @Length(max = 500,message = "评论最多500字")
    private String content;
    /**
     * 是否匿名1匿名0不匿名
     */
    @NotNull(message = "isAnoy不允许为空")
    private Integer isAnoy;
    /**
     * 星星
     */
    @NotNull(message = "star不允许为空")
    private Integer star;

    private Integer origin;

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

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }
}
