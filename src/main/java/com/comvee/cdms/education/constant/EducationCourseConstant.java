package com.comvee.cdms.education.constant;

/**
 * @Author linr
 * @Date 2022/1/27
 */
public class EducationCourseConstant {

    public final static Integer EDUCATION_ORIGIN_WEB = 1;//来源端-WEB
    public final static Integer EDUCATION_ORIGIN_H5 = 2;//来源端-H5


    public final static Integer EDUCATION_COURSE_STATUS_ACTIVE = 1;//课程状态-启用
    public final static Integer EDUCATION_COURSE_STATUS_BANNED = 2;//课程状态-禁用

    public final static Integer EDUCATION_COURSE_COLLECT_YES = 1;//课程已被收藏
    public final static Integer EDUCATION_COURSE_COLLECT_NO = 0;//课程未被收藏

    public final static Integer EDUCATION_COURSE_TYPE_VEDIO = 1;//课程类型-视频
    public final static Integer EDUCATION_COURSE_TYPE_ARTICLE = 2;//课程类型-文章


    public final static Integer EDUCATION_COMMENT_LEVER_PRAISE = 1;//评论-好评
    public final static Integer EDUCATION_COMMENT_LEVER_MID = 2;//评论-中评
    public final static Integer EDUCATION_COMMENT_LEVER_NEGATIVE = 3;//评论-差评


    public final static Integer EDUCATION_COURSE_COMMENT_SHOW = 1;//评论-展示
    public final static Integer EDUCATION_COURSE_COMMENT_HIDE = 0;//评论-隐藏
}
