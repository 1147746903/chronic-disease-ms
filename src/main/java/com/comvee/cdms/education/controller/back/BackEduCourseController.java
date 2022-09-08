package com.comvee.cdms.education.controller.back;

import com.comvee.cdms.admin.model.po.AdminPO;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.education.constant.EducationCourseConstant;
import com.comvee.cdms.education.model.dto.AddEduCourseDTO;
import com.comvee.cdms.education.model.dto.ListEduCourseDTO;
import com.comvee.cdms.education.model.po.EduCourseClassPO;
import com.comvee.cdms.education.model.po.EduCourseCommentPO;
import com.comvee.cdms.education.model.vo.DetailEduCourseVO;
import com.comvee.cdms.education.model.vo.ListEduCommentVO;
import com.comvee.cdms.education.model.vo.ListEduCourseVO;
import com.comvee.cdms.education.service.EducationCourseServiceI;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author linr
 * @Date 2022/1/27
 */
@RestController
@RequestMapping("/back/edu")
public class BackEduCourseController {

    @Autowired
    private EducationCourseServiceI educationCourseService;

    /**
     * 分类列表
     * @param className
     * @param page
     * @return
     */
    @RequestMapping("loadClassList")
    public Result loadClassList(String className, PageRequest page) {
        PageResult<EduCourseClassPO> classPage = educationCourseService.loadEducationCourseClass(className, page);
        return Result.ok(classPage);
    }


    /**
     * 添加分类
     * @param eduCourseClassPO
     * @return
     */
    @RequestMapping("addClass")
    public Result addEducationCourseClass(EduCourseClassPO eduCourseClassPO) {
        ValidateTool.checkParameterIsNull("className",eduCourseClassPO.getClassName());
        AdminPO adminSession = SessionTool.getAdminSession();
        eduCourseClassPO.setOperatorId(adminSession.getAdminId());
        educationCourseService.addEducationCourseClass(eduCourseClassPO);
        return Result.ok();
    }

    /**
     * 修改分类
     * @param eduCourseClassPO
     * @return
     */
    @RequestMapping("modifyClass")
    public Result modifyEducationCourseClass(EduCourseClassPO eduCourseClassPO) {
        ValidateTool.checkParameterIsNull("className",eduCourseClassPO.getClassName());
        ValidateTool.checkParameterIsNull("sid",eduCourseClassPO.getSid());
        AdminPO adminSession = SessionTool.getAdminSession();
        eduCourseClassPO.setOperatorId(adminSession.getAdminId());
        educationCourseService.modifyEducationCourseClass(eduCourseClassPO);
        return Result.ok();
    }

    /**
     * 删除分类
     * @param sid
     * @return
     */
    @RequestMapping("delClass")
    public Result delEducationCourseClass(String sid) {
        ValidateTool.checkParameterIsNull("sid",sid);
        AdminPO adminSession = SessionTool.getAdminSession();
        educationCourseService.delEducationCourseClass(sid,adminSession.getAdminId());
        return Result.ok();
    }

    /**
     * 修改评论
     * @return
     */
    @RequestMapping("modifyComment")
    public Result hideComment(EduCourseCommentPO commentPO) {
        ValidateTool.checkParameterIsNull("sid",commentPO.getSid());
        educationCourseService.modifyComment(commentPO);
        return Result.ok();
    }

    /**
     * 添加课程
     * @param addEduCourseDTO
     * @return
     */
    @RequestMapping("addCourse")
    public Result addCourse(@Validated AddEduCourseDTO addEduCourseDTO) {
        courseInfoCheck(addEduCourseDTO);
        educationCourseService.addCourse(addEduCourseDTO);
        return Result.ok();
    }


    /**
     * 修改课程
     * @param addEduCourseDTO
     * @return
     */
    @RequestMapping("modifyCourse")
    public Result modifyCourse(@Validated AddEduCourseDTO addEduCourseDTO) {
        courseInfoCheck(addEduCourseDTO);
        ValidateTool.checkParameterIsNull("sid",addEduCourseDTO.getSid());
        educationCourseService.modifyCourse(addEduCourseDTO);
        return Result.ok();
    }


    /**
     * 列表外层禁用/启用
     * @param courseId
     * @param status 状态 1启用0禁用
     * @return
     */
    @RequestMapping("modifyCourseStatus")
    public Result banCourse(String courseId,Integer status) {
        ValidateTool.checkParameterIsNull("courseId",courseId);
        ValidateTool.checkParameterIsNull("status",status);
        educationCourseService.modifyCourseStatus(courseId,status);
        return Result.ok();
    }


    /**
     * 删除课程
     * @param courseId
     * @return
     */
    @RequestMapping("delCourse")
    public Result delCourse(String courseId) {
        ValidateTool.checkParameterIsNull("courseId",courseId);
        educationCourseService.delCourse(courseId);
        return Result.ok();
    }


    /**
     * 获取课程详细
     * @param courseId
     * @return
     */
    @RequestMapping("loadCourseDetail")
    public Result loadCourseDetail(String courseId) {
        ValidateTool.checkParameterIsNull("courseId",courseId);
        DetailEduCourseVO detailEduCourseVO = educationCourseService.loadCourseDetailBack(courseId);
        return Result.ok(detailEduCourseVO);
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
     * 评论列表
     * @param lever 1好评2中评3差评
     * @param courseId
     * @param page
     * @return
     */
    @RequestMapping("loadCommentList")
    public Result loadEducationCourseList(Integer lever,String courseId, PageRequest page) {
        ValidateTool.checkParameterIsNull("courseId",courseId);
        PageResult<ListEduCommentVO> commentList = educationCourseService.loadCourseCommentList(page, courseId, lever, true);
        return Result.ok(commentList);
    }



    private void courseInfoCheck(AddEduCourseDTO addEduCourseDTO){
        if (addEduCourseDTO.getCourseType() == EducationCourseConstant.EDUCATION_COURSE_TYPE_VEDIO){
            ValidateTool.checkParameterIsNull("vedioUrl",addEduCourseDTO.getVedioUrl());
            ValidateTool.checkParameterIsNull("duration",addEduCourseDTO.getDuration());
        }else {
            ValidateTool.checkParameterIsNull("articleContent",addEduCourseDTO.getArticleContent());
        }
        AdminPO adminSession = SessionTool.getAdminSession();
        addEduCourseDTO.setOperatorId(adminSession.getAdminId());
    }

}
