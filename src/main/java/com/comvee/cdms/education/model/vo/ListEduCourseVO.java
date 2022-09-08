package com.comvee.cdms.education.model.vo;

/**
 * @Author linr
 * @Date 2022/1/27
 */
public class ListEduCourseVO {

    /**
     * 主键
     */
    private String courseId;
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
    //类别名
    private String className;
    /**
     * 状态 1启用0禁用
     */
    private Integer courseStatus;
    /**
     * 主图
     */
    private String imgUrl;
    /**
     * 时长
     */
    private String duration;
    /**
     * 观看次数
     */
    private String viewNum;

    private String insertDt;//创建时间

    private String teacherName;//讲师姓名

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
