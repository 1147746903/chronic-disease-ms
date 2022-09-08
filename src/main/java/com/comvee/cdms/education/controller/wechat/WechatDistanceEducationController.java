package com.comvee.cdms.education.controller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.education.constant.EducationCourseConstant;
import com.comvee.cdms.education.model.dto.AddEduCourseCommentDTO;
import com.comvee.cdms.education.model.dto.EduCourseDoctorDTO;
import com.comvee.cdms.education.model.dto.ListEduCourseDTO;
import com.comvee.cdms.education.model.po.EduCourseClassPO;
import com.comvee.cdms.education.model.vo.DetailEduCourseVO;
import com.comvee.cdms.education.model.vo.ListEduCommentVO;
import com.comvee.cdms.education.model.vo.ListEduCourseVO;
import com.comvee.cdms.education.service.EducationCourseServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2022/1/29
 */
@RestController
@RequestMapping("/wechat/edu")
public class WechatDistanceEducationController {

    @Autowired
    private EducationCourseServiceI educationCourseService;

    /**
     * 分类列表
     * @param page
     * @return
     */
    @RequestMapping("loadClassList")
    public Result loadClassList(PageRequest page) {
        PageResult<EduCourseClassPO> classPage = educationCourseService.loadEducationCourseClass(null, page);
        return Result.ok(classPage);
    }

    /**
     * 课程列表
     * @param listEduCourseDTO
     * @param page
     * @return
     */
    @RequestMapping("loadCourseList")
    public Result loadEducationCourseList(ListEduCourseDTO listEduCourseDTO, PageRequest page) {
        PageResult<ListEduCourseVO> result = educationCourseService.loadEducationCourseList(listEduCourseDTO, page);
        return Result.ok(result);
    }

    /**
     * 观看历史
     * @param doctorId
     * @return
     */
    @RequestMapping("loadViewHistory")
    public Result loadDoctorViewHistory(String doctorId) {
        ValidateTool.checkParameterIsNull("doctorId",doctorId);
        List<ListEduCourseVO> result = educationCourseService.loadDoctorViewHistory(doctorId);
        return Result.ok(result);
    }

    /**
     * 获取收藏夹
     * @param doctorId
     * @return
     */
    @RequestMapping("loadCollect")
    public Result loadDoctorCollect(String doctorId) {
        ValidateTool.checkParameterIsNull("doctorId",doctorId);
        List<ListEduCourseVO> result = educationCourseService.loadDoctorCollect(doctorId);
        return Result.ok(result);
    }

    /**
     * 添加收藏
     * @param eduCourseDoctorDTO
     * @return
     */
    @RequestMapping("addCollect")
    public Result addDoctorCollect(@Validated EduCourseDoctorDTO eduCourseDoctorDTO) {
        eduCourseDoctorDTO.setOrigin(EducationCourseConstant.EDUCATION_ORIGIN_H5);
        educationCourseService.addDoctorCollect(eduCourseDoctorDTO);
        return Result.ok();
    }

    /**
     * 取消收藏
     * @param eduCourseDoctorDTO
     * @return
     */
    @RequestMapping("delCollect")
    public Result delDoctorCollect(@Validated EduCourseDoctorDTO eduCourseDoctorDTO) {
        educationCourseService.delDoctorCollect(eduCourseDoctorDTO);
        return Result.ok();
    }

    /**
     * 获取课程详情
     * @param eduCourseDoctorDTO
     * @return
     */
    @RequestMapping("loadCourseDetail")
    public Result loadCourseDetail(@Validated EduCourseDoctorDTO eduCourseDoctorDTO) {
        DetailEduCourseVO detailEduCourseVO = educationCourseService.loadCourseDetail(eduCourseDoctorDTO);
        return Result.ok(detailEduCourseVO);
    }

    /**
     * 立即学习
     * @param eduCourseDoctorDTO
     * @return
     */
    @RequestMapping("learnCourse")
    public Result learnCourse(@Validated EduCourseDoctorDTO eduCourseDoctorDTO) {
        eduCourseDoctorDTO.setOrigin(EducationCourseConstant.EDUCATION_ORIGIN_H5);
        DetailEduCourseVO detailEduCourseVO = educationCourseService.learnCourse(eduCourseDoctorDTO);
        return Result.ok(detailEduCourseVO);
    }

    /**
     * 发表评论
     * @param addEduCourseCommentDTO
     * @return
     */
    @RequestMapping("publishComment")
    public Result publishComment(@Validated AddEduCourseCommentDTO addEduCourseCommentDTO) {
        addEduCourseCommentDTO.setOrigin(EducationCourseConstant.EDUCATION_ORIGIN_H5);
        educationCourseService.publishComment(addEduCourseCommentDTO);
        return Result.ok();
    }

    /**
     * 评论列表
     * @param lever 1好评2中评3差评
     * @param courseId
     * @param page
     * @return
     */
    @RequestMapping("loadCommentList")
    public Result loadEducationCourseList(Integer lever,String courseId, PageRequest page) {
        ValidateTool.checkParameterIsNull("courseId",courseId);
        PageResult<ListEduCommentVO> commentList = educationCourseService.loadCourseCommentList(page, courseId, lever, null);
        return Result.ok(commentList);
    }

    /**
     * 播放视频
     * @param eduCourseDoctorDTO
     * @return
     */
    @RequestMapping("playVideo")
    public Result playVedioCourse(@Validated EduCourseDoctorDTO eduCourseDoctorDTO) {
        eduCourseDoctorDTO.setOrigin(EducationCourseConstant.EDUCATION_ORIGIN_H5);
        educationCourseService.playVedioCourse(eduCourseDoctorDTO);
        return Result.ok();
    }
}
