package com.comvee.cdms.foot.contorller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.foot.dto.ListFootDTO;
import com.comvee.cdms.foot.po.FootPO;
import com.comvee.cdms.foot.service.FootService;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/4/24
 */
@RestController
@RequestMapping("/wechat/foot")
public class WechatFootController {

    @Autowired
    private FootService footService;

    /**
     * 加载患者足部处方列表
     * @param pr
     * @return
     */
    @RequestMapping("/listMemberFootPrescription")
    public Result listMemberFootPrescription(PageRequest pr){
        MemberPO memberPO = SessionTool.getWechatSession();
        ListFootDTO listFootDTO = new ListFootDTO();
        listFootDTO.setMemberId(memberPO.getMemberId());
        listFootDTO.setSaveType("3");
//        listFootDTO.setFootType(String.valueOf(FootConstant.FOOT_TYPE_SCREENING));
        PageResult pageResult = this.footService.listFootPage(listFootDTO ,pr);
        return Result.ok(pageResult);
    }

    /**
     * 根据id获取足部处方
     * @param prescriptId
     * @return
     */
    @RequestMapping("/getFootPrescriptionById")
    public Result getFootPrescriptionById(String prescriptId){
        ValidateTool.checkParamIsNull(prescriptId , "prescriptId");
        FootPO footPO = this.footService.getFoot(prescriptId);
        return Result.ok(footPO);
    }

    /**
     * 加载足部处方关联的筛查报告
     * @param prescriptId
     * @return
     */
    @RequestMapping("/listPrescriptionScreeningReport")
    public Result listPrescriptionScreeningReport(String prescriptId){
        ValidateTool.checkParamIsNull(prescriptId , "prescriptId");
        List list = this.footService.listPrescriptionScreeningReport(prescriptId);
        return Result.ok(list);
    }

    /** v4.2.1
     * 获取患者最新的足部管理处方
     * @param
     * @return
     */
    @RequestMapping("getFootNew")
    public Result getFootNew(ListFootDTO listFootDTO){
        ValidateTool.checkParamIsNull(listFootDTO.getMemberId(), "患者id");
        ValidateTool.checkParamIsNull(listFootDTO.getDoctorId(), "医生id");
        FootPO footNew = this.footService.getFootNew(listFootDTO);
        return new Result(footNew);
    }
}
