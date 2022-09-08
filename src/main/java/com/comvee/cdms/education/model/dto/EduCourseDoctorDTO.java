package com.comvee.cdms.education.model.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Author linr
 * @Date 2022/1/29
 */
public class EduCourseDoctorDTO {
    @NotBlank(message = "doctorId不允许为空")
    private String doctorId;
    @NotBlank(message = "courseId不允许为空")
    private String courseId;
    private Integer origin;//1web2小程序

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

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }
}
