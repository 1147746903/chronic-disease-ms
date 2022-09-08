package com.comvee.cdms.education.model.vo;

/**
 * @Author linr
 * @Date 2022/1/27
 */
public class DetailEduCourseVO {
    /**
     * 课程id
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
     * 状态 1启动0禁用
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
     * 评论数
     */
    private String commentNum;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 添加时间
     */
    private String insertDt;

    private Integer isCollect;//是否收藏1是0不是
    private String praiseCommentNum;//好评数
    private String midCommentNum;//中评数
    private String negCommentNum;//差评数
    private String vedioUrl;//视频地址
    private String articleContent;//文章块

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

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
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

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
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

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public String getPraiseCommentNum() {
        return praiseCommentNum;
    }

    public void setPraiseCommentNum(String praiseCommentNum) {
        this.praiseCommentNum = praiseCommentNum;
    }

    public String getMidCommentNum() {
        return midCommentNum;
    }

    public void setMidCommentNum(String midCommentNum) {
        this.midCommentNum = midCommentNum;
    }

    public String getNegCommentNum() {
        return negCommentNum;
    }

    public void setNegCommentNum(String negCommentNum) {
        this.negCommentNum = negCommentNum;
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

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }
}
