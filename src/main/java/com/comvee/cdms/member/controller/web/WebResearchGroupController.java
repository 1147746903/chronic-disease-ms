package com.comvee.cdms.member.controller.web;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.member.dto.AddResearchGroupDTO;
import com.comvee.cdms.member.dto.ListResearchGroupDTO;
import com.comvee.cdms.member.dto.ListResearchGroupMemberDTO;
import com.comvee.cdms.member.dto.UpdateResearchGroupDTO;
import com.comvee.cdms.member.service.ResearchGroupService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/researchGroup")
public class WebResearchGroupController {

    @Autowired
    private ResearchGroupService researchGroupService;

    /**
     * @api {post}/web/researchGroup/listResearchGroup.do 加载课题组列表(分页)
     * @author suyz
     * @time 2020/11/30
     * @apiName listResearchGroup 加载课题组列表(分页)
     * @apiGroup web:researchGroup
     * @apiParam {String} keyword 搜索关键字
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/listResearchGroup")
    public Result listResearchGroup(ListResearchGroupDTO dto ,PageRequest pr){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setHospitalId(doctorSessionBO.getHospitalId());
        PageResult result = this.researchGroupService.listResearchGroup(dto ,pr);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/researchGroup/addResearchGroup.do 新增课题组
     * @author suyz
     * @time 2020/11/30
     * @apiName addResearchGroup 新增课题组
     * @apiGroup web:researchGroup
     * @apiParam {String} researchGroupName 课题组名称
     * @apiParam {String} memberIdStr 成员id,多个用,隔开
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/addResearchGroup")
    public Result addResearchGroup(@Validated AddResearchGroupDTO add){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        add.setDoctorId(doctorSessionBO.getDoctorId());
        add.setHospitalId(doctorSessionBO.getHospitalId());
        String groupId = this.researchGroupService.addResearchGroup(add);
        return Result.ok(groupId);
    }

    /**
     * @api {post}/web/researchGroup/updateResearchGroup.do 修改课题组
     * @author suyz
     * @time 2020/11/30
     * @apiName updateResearchGroup 修改课题组
     * @apiGroup web:researchGroup
     * @apiParam {String} groupId 课题组id
     * @apiParam {String} researchGroupName 课题组名称
     * @apiParam {String} memberIdStr 成员id,多个用,隔开
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/updateResearchGroup")
    public Result updateResearchGroup(@Validated UpdateResearchGroupDTO update){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        update.setDoctorId(doctorSessionBO.getDoctorId());
        this.researchGroupService.updateResearchGroup(update);
        return Result.ok();
    }

    /**
     * @api {post}/web/researchGroup/deleteResearchGroup.do 删除课题组
     * @author suyz
     * @time 2020/11/30
     * @apiName deleteResearchGroup 删除课题组
     * @apiGroup web:researchGroup
     * @apiParam {String} groupId id
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/deleteResearchGroup")
    public Result deleteResearchGroup(String groupId){
        ValidateTool.checkParameterIsNull("groupId" ,groupId);
        this.researchGroupService.deleteResearchGroup(groupId);
        return Result.ok();
    }

    /**
     * @api {post}/web/researchGroup/listResearchGroupMember.do 加载课题组成员列表
     * @author suyz
     * @time 2020/11/30
     * @apiName listResearchGroupMember 加载课题组成员列表
     * @apiGroup web:researchGroup
     * @apiParam {String} keyword 搜索关键字
     * @apiParam {String} groupId 组id
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/listResearchGroupMember")
    public Result listResearchGroupMember(@Validated ListResearchGroupMemberDTO dto){
        List result = this.researchGroupService.listResearchGroupMember(dto);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/researchGroup/addResearchGroupMember.do 新增课题组成员
     * @author suyz
     * @time 2020/11/30
     * @apiName addResearchGroupMember 新增课题组成员
     * @apiGroup web:researchGroup
     * @apiParam {String} groupId 组id
     * @apiParam {String} memberId 患者id
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/addResearchGroupMember")
    public Result addResearchGroupMember(String groupId ,String memberId){
        ValidateTool.checkParameterIsNull("groupId" ,groupId);
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        String result = this.researchGroupService.addResearchGroupMember(groupId ,memberId ,doctorSessionBO.getDoctorId());
        return Result.ok(result);
    }

    /**
     * @api {post}/web/researchGroup/deleteResearchGroupMember.do 删除课题组成员
     * @author suyz
     * @time 2020/11/30
     * @apiName deleteResearchGroupMember 删除课题组成员
     * @apiGroup web:researchGroup
     * @apiParam {String} sid 记录id
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/deleteResearchGroupMember")
    public Result deleteResearchGroupMember(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        this.researchGroupService.deleteResearchGroupMember(sid);
        return Result.ok();
    }
}
