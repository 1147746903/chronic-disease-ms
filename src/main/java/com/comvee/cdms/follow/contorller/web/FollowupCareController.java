package com.comvee.cdms.follow.contorller.web;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.follow.dto.AddFollowupCareDTO;
import com.comvee.cdms.follow.po.FollowupCarePO;
import com.comvee.cdms.follow.service.FollowupCareService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2019/7/24
 */
@RestController
@RequestMapping("/web/followupCare")
public class FollowupCareController {

    @Autowired
    private FollowupCareService followupCareService;

    /**
     * 添加随访关怀
     * @param screeningAddFollowupCareDTO
     * @param secretKey
     * @return
     */
    @RequestMapping("addFollowupCare")
    public Result addFollowupCare(@Validated AddFollowupCareDTO addFollowupCareDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        FollowupCarePO followupCarePO = new FollowupCarePO();
        BeanUtils.copyProperties(followupCarePO ,addFollowupCareDTO);
        followupCarePO.setDoctorId(doctorSessionBO.getDoctorId());
        String sid = this.followupCareService.addFollowupCare(followupCarePO);
        return Result.ok(sid);
    }

    /**
     * 修改随访关怀
     * @param followupCarePO
     * @param secretKey
     * @return
     */
    @RequestMapping("updateFollowupCare")
    public Result updateFollowupCare(FollowupCarePO followupCarePO) {
        ValidateTool.checkParamIsNull(followupCarePO.getSid() , "主键");
        this.followupCareService.updateFollowupCare(followupCarePO);
        return Result.ok();
    }

    /**
     * 删除随访关怀
     * @param sid
     * @param secretKey
     * @return
     */
    @RequestMapping("deleteFollowupCare")
    public Result deleteFollowupCare(String sid){
        ValidateTool.checkParamIsNull(sid, "主键");
        this.followupCareService.deleteFollowupCare(sid);
        return Result.ok();
    }

    /**
     * 加载患者的随访关怀
     * @param pr
     * @param idCard
     * @param secretKey
     * @return
     */
    @RequestMapping("listMemberFollowupCare")
    public Result listMemberFollowupCare(PageRequest pr , String memberId){
        ValidateTool.checkParamIsNull(memberId , "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        PageResult pageResult = this.followupCareService.listMemberFollowupCare(pr ,memberId ,doctorSessionBO.getDoctorId());
        return Result.ok(pageResult);
    }

    /**
     * 根据id获取随访关怀
     * @param sid
     * @return
     */
    @RequestMapping("getFollowupCareById")
    public Result getFollowupCareById(String sid){
        ValidateTool.checkParamIsNull(sid, "主键");
        FollowupCarePO followupCarePO = this.followupCareService.getFollowupCareById(sid);
        return Result.ok(followupCarePO);
    }

}
