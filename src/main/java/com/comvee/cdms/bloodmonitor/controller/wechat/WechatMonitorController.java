package com.comvee.cdms.bloodmonitor.controller.wechat;

import com.comvee.cdms.bloodmonitor.constant.MonitorPlanConstant;
import com.comvee.cdms.bloodmonitor.dto.GetMemberMonitorDTO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author: suyz
 * @date: 2018/11/6
 */
@RestController
@RequestMapping("/wechat/monitor")
public class WechatMonitorController {

    @Autowired
    private MemberMonitorPlanServiceI memberMonitorPlanServiceI;

    /**
     * 获取患者血糖监测方案
     * @return
     */
    @RequestMapping("getMemberMonitor")
    public Result getMemberMonitor(String planId ,Integer illnessType){
        MemberPO memberPO = SessionTool.getWechatSession();
        GetMemberMonitorDTO getMemberMonitorDTO = handlerParam(planId, memberPO.getMemberId() ,illnessType);
        MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanServiceI.getMemberMonitorPlan(getMemberMonitorDTO);
        return new Result(memberMonitorPlanPO);
    }

    /**
     * 参数处理
     * @param planId
     * @param memberId
     * @return
     */
    private GetMemberMonitorDTO handlerParam(String planId, String memberId  ,Integer illnessType){
        GetMemberMonitorDTO getMemberMonitorDTO = new GetMemberMonitorDTO();
        if(StringUtils.isBlank(planId)){
            getMemberMonitorDTO.setMemberId(memberId);
            getMemberMonitorDTO.setInProgress(MonitorPlanConstant.PLAN_IN_PROGRESS_YES);
            getMemberMonitorDTO.setIllnessType(Optional.ofNullable(illnessType).orElse(MonitorPlanConstant.ILLNESS_TYPE_DIABETES));
        }else{
            getMemberMonitorDTO.setPlanId(planId);
        }
        return getMemberMonitorDTO;
    }

}
