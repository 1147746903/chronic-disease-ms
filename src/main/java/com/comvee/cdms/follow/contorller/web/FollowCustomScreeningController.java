package com.comvee.cdms.follow.contorller.web;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.follow.cfg.FollowCustomConstant;
import com.comvee.cdms.follow.dto.AddFollowCustomDTO;
import com.comvee.cdms.follow.dto.AddFollowCustomScreeningDTO;
import com.comvee.cdms.follow.dto.ListFollowCustomDTO;
import com.comvee.cdms.follow.po.FollowCustomFormworkPO;
import com.comvee.cdms.follow.po.FollowCustomPO;
import com.comvee.cdms.follow.service.FollowCustomService;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author: suyz
 * @date: 2019/4/15
 */
@RestController
@RequestMapping("/web/followCustom")
public class FollowCustomScreeningController {

    @Autowired
    private FollowCustomService followCustomService;

    @Autowired
    private MemberService memberService;


    /**
     * 添加自定义问卷
     * @param addFollowCustomScreeningDTO
     * @return
     */
    @RequestMapping("/addFollowCustom")
    public Result addFollowCustom(AddFollowCustomScreeningDTO addFollowCustomScreeningDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        MemberPO memberPO = this.memberService.getMemberById(addFollowCustomScreeningDTO.getIdCard());
        AddFollowCustomDTO addFollowCustomDTO = new AddFollowCustomDTO();
        BeanUtils.copyProperties(addFollowCustomDTO ,addFollowCustomScreeningDTO);
        addFollowCustomDTO.setCreatorId(doctorSessionBO.getDoctorId());
        addFollowCustomDTO.setDoctorId(doctorSessionBO.getDoctorId());
        addFollowCustomDTO.setMemberId(memberPO.getMemberId());
        String followId = this.followCustomService.addFollowCustom(addFollowCustomDTO);
        return Result.ok(followId);
    }


    /**
     * 根据随访id获取自定义随访
     * @param followId
     * @return
     */
    @RequestMapping("/getFollowCustomById")
    public Result getFollowCustomById(String  followId){
        ValidateTool.checkParamIsNull(followId ,"followId");
        FollowCustomPO followCustomPO = this.followCustomService.getFollowCustomById(followId);
        return Result.ok(followCustomPO);
    }

    /**
     * 删除自定义随访
     * @param followId
     */
    @RequestMapping("/deleteFollowCustom")
    public Result deleteFollowCustom(String followId){
        this.followCustomService.deleteFollowCustom(followId);
        return Result.ok();
    }

    /**
     * 加载自定义随访列表
     * @param pr
     * @param idCard
     * @param secretKey
     * @return
     */
    @RequestMapping("/listFollowCustom")
    public Result listFollowCustom(PageRequest pr ,String memberId){
        ValidateTool.checkParamIsNull(memberId ,"memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        ListFollowCustomDTO listFollowCustomDTO = new ListFollowCustomDTO();
        listFollowCustomDTO.setDoctorId(doctorSessionBO.getDoctorId());
        listFollowCustomDTO.setMemberId(memberId);
        PageResult<FollowCustomPO> poPageResult = this.followCustomService.listFollowCustom(listFollowCustomDTO ,pr);
        return Result.ok(poPageResult);
    }

    /**
     * 保存草稿
     * @param followId
     * @param dataJson
     * @return
     */
    @RequestMapping("/saveDraft")
    public Result saveDraft(String followId ,String dataJson){
        ValidateTool.checkParamIsNull(followId ,"followId");
        ValidateTool.checkParamIsNull(dataJson ,"dataJson");
        this.followCustomService.saveDraft(followId ,dataJson);
        return Result.ok();
    }

    /**
     * 提交随访
     * @param followId
     * @param dataJson
     * @return
     */
    @RequestMapping("/commitFollow")
    public Result commitFollow(String followId ,String dataJson){
        ValidateTool.checkParamIsNull(followId ,"followId");
        ValidateTool.checkParamIsNull(dataJson ,"dataJson");
        this.followCustomService.commitFollow(followId ,dataJson , FollowCustomConstant.FILL_PERSON_DOCTOR);
        return Result.ok();
    }

    /**
     * 加载自定义随访模版
     * @param secretKey
     * @return
     */
    @RequestMapping("/listFollowCustomFormwork")
    public Result listFollowCustomFormwork(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List list = this.followCustomService.listFollowCustomFormwork(doctorSessionBO.getDoctorId());
        return Result.ok(list);
    }

    /**
     * 添加自定义随访模板
     * @param secretKey
     * @param followCustomFormworkPO
     * @return
     */
    @RequestMapping("/addFollowCustomFormwork")
    public Result addFollowCustomFormwork(FollowCustomFormworkPO followCustomFormworkPO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        followCustomFormworkPO.setCreatorId(doctorSessionBO.getDoctorId());
        followCustomFormworkPO.setDoctorId(doctorSessionBO.getDoctorId());
        String sid = this.followCustomService.addFollowCustomFormwork(followCustomFormworkPO);
        return Result.ok(sid);
    }

    /**
     * 修改自定义随访模版
     * @param followCustomFormworkPO
     * @return
     */
    @RequestMapping("/updateFollowCustomFormwork")
    public Result updateFollowCustomFormwork(FollowCustomFormworkPO followCustomFormworkPO){
        this.followCustomService.updateFollowCustomFormwork(followCustomFormworkPO);
        return Result.ok();
    }

    /**
     * 加载自定义随访模版(分页)
     * @param secretKey
     * @return
     */
    @RequestMapping("/listFollowCustomFormworkPage")
    public Result listFollowCustomFormworkPage(PageRequest pr ,String keyword){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        PageResult list = this.followCustomService.listFollowCustomFormworkPage(doctorSessionBO.getDoctorId() ,pr ,keyword);
        return Result.ok(list);
    }

    /**
     * 批量删除随访模板
     * @param idStr
     * @param secretKey
     * @return
     */
    @RequestMapping("batchDeleteFollowCustomFormwork")
    public Result batchDeleteFollowCustomFormwork(String idStr){
        ValidateTool.checkParamIsNull(idStr ,"idStr");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<String> idList = Arrays.asList(idStr.split(","));
        this.followCustomService.batchDeleteFollowCustomFormwork(idList ,doctorSessionBO.getDoctorId());
        return Result.ok();
    }
}
