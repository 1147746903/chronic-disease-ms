package com.comvee.cdms.education.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.education.model.dto.AddEduCourseCommentDTO;
import com.comvee.cdms.education.model.dto.AddEduCourseDTO;
import com.comvee.cdms.education.model.dto.EduCourseDoctorDTO;
import com.comvee.cdms.education.model.dto.ListEduCourseDTO;
import com.comvee.cdms.education.model.po.EduCourseClassPO;
import com.comvee.cdms.education.model.po.EduCourseCommentPO;
import com.comvee.cdms.education.model.po.EduCoursePO;
import com.comvee.cdms.education.model.vo.DetailEduCourseVO;
import com.comvee.cdms.education.model.vo.ListEduCommentVO;
import com.comvee.cdms.education.model.vo.ListEduCourseVO;

import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2022/1/27
 */
public interface EducationCourseServiceI {


    /**
     *获取课程分类列表
     * @return
     */
    PageResult<EduCourseClassPO> loadEducationCourseClass(String className, PageRequest page);

    /**
     * 添加课程分类
     * @param eduCourseClassPO
     */
    void addEducationCourseClass(EduCourseClassPO eduCourseClassPO);

    /**
     * 修改课程分类
     * @param eduCourseClassPO
     */
    void modifyEducationCourseClass(EduCourseClassPO eduCourseClassPO);

    /**
     * 删除课程分类
     * @param classId
     */
    void delEducationCourseClass(String classId,String operatorId);

    /**
     * 远程教育课程列表
     * @param listEduCourseDTO
     * @return
     */
    PageResult<ListEduCourseVO> loadEducationCourseList(ListEduCourseDTO listEduCourseDTO, PageRequest page);

    /**
     * 我的历史记录
     * @return
     */
    List<ListEduCourseVO> loadDoctorViewHistory(String doctorId);

    /**
     * 我的收藏
     * @return
     */
    List<ListEduCourseVO> loadDoctorCollect(String doctorId);

    /**
     * 添加收藏
     * @param
     */
    void addDoctorCollect(EduCourseDoctorDTO eduCourseDoctorDTO);

    /**
     * 取消收藏
     * @param
     */
    void delDoctorCollect(EduCourseDoctorDTO eduCourseDoctorDTO);

    /**
     * 获取课程详情
     * @param
     * @return
     */
    DetailEduCourseVO loadCourseDetail(EduCourseDoctorDTO eduCourseDoctorDTO);

    DetailEduCourseVO loadCourseDetailBack(String courseId);

    /**
     * 立即学习
     */
    DetailEduCourseVO learnCourse(EduCourseDoctorDTO eduCourseDoctorDTO);

    /**
     * 发表评论
     * @param
     */
    void publishComment(AddEduCourseCommentDTO addEduCourseCommentDTO);

    /**
     * 修改评论（隐藏/显示评论）
     * @param
     */
    void modifyComment(EduCourseCommentPO eduCourseCommentPO);

    /**
     * 添加课程
     * @param
     */
    void addCourse(AddEduCourseDTO addEduCourseDTO);

    /**
     * 修改课程
     * @param
     */
    void modifyCourse(AddEduCourseDTO addEduCourseDTO);

    /**
     * 删除课程
     * @param
     */
    void delCourse(String courseId);

    /**
     * 禁用/修改课程
     * @param courseId
     */
    void modifyCourseStatus(String courseId,Integer status);


    /**
     * 评论列表
     * @param page
     * @param lever
     * @return
     */
    PageResult<ListEduCommentVO> loadCourseCommentList(PageRequest page,String courseId,Integer lever,Boolean showAnoy);


    /**
     * 推荐课程
     * @param courseId
     * @return
     */
    List<ListEduCourseVO> recommendCourseList(String courseId);


    /**
     * 播放课程视频
     */
    void playVedioCourse(EduCourseDoctorDTO eduCourseDoctorDTO);




}
