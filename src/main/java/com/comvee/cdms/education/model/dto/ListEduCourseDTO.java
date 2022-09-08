package com.comvee.cdms.education.model.dto;

/**
 * @Author linr
 * @Date 2022/1/27
 */
public class ListEduCourseDTO {

    private String keyword;
    private String classId;
    private Integer courseType;
    private String timeOrder;
    private String scoreOrder;
    private String viewOrder;
    private String teacherName;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getScoreOrder() {
        return scoreOrder;
    }

    public void setScoreOrder(String scoreOrder) {
        this.scoreOrder = scoreOrder;
    }

    public String getViewOrder() {
        return viewOrder;
    }

    public void setViewOrder(String viewOrder) {
        this.viewOrder = viewOrder;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
