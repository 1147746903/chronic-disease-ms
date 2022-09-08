package com.comvee.cdms.dybloodsugar.contorller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.dto.DyReportDTO;
import com.comvee.cdms.dybloodsugar.dto.GetGreenStarMainPlanDto;
import com.comvee.cdms.dybloodsugar.dto.GreenStarRememberDTO;
import com.comvee.cdms.dybloodsugar.po.*;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DynamicBloodSugarStatService;
import com.comvee.cdms.dybloodsugar.service.GreenStarPlanService;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.records.model.dto.DietRecordDTO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wechat/greenStar/")
public class GreenStarPlanController {
    @Autowired
    private GreenStarPlanService greenStarPlanService;
    @Autowired
    private DynamicBloodSugarStatService dynamicBloodSugarStatService;
    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    /**
     * 开启计划
     * @param dto
     * @return
     */
    @RequestMapping("openPlan")
    public Result openPlan(GetGreenStarMainPlanDto dto) {
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        ValidateTool.checkParameterIsNull("sensorNo", dto.getSensorNo());
        ValidateTool.checkParameterIsNull("bindType", dto.getBindType());
        greenStarPlanService.openPlan(dto);
        return Result.ok("开启成功");
    }

    /**
     * 计划是否开启
     * @param dto
     * @return
     */
    @RequestMapping("hasOpenPlan")
    public Result hasOpenPlan(GetGreenStarMainPlanDto dto) {
        ValidateTool.checkParameterIsNull("sensorNo", dto.getSensorNo());
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        return Result.ok(greenStarPlanService.hasOpenPlan(dto));
    }
    /**
     * 计划是否过期开启
     * @return
     */
    @RequestMapping("isMainPlanOverdue")
    public Result isMainPlanOverdue(GetGreenStarMainPlanDto dto){
        ValidateTool.checkParameterIsNull("sensorNo", dto.getSensorNo());
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        return Result.ok(greenStarPlanService.isMainPlanOverdue(dto));
    }


    @RequestMapping("getMainPlan")
    public Result getMainPlan(GetGreenStarMainPlanDto dto) {
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        ValidateTool.checkParameterIsNull("sensorNo", dto.getSensorNo());
        return Result.ok(greenStarPlanService.getMainPlan(dto));
    }

    @RequestMapping("getPlan")
    public Result getPlan(String planId) {
        MemberPO member = SessionTool.getWechatSession();
        ValidateTool.checkParameterIsNull("planId", planId);
        GreenStarMainPlanPO mainPlan = greenStarPlanService.getMainPlanByDailyPlanId(planId);
        GreenStarPlanPO plan = greenStarPlanService.getPlanById(planId);
        if(plan.getDateIndex() != 1) {
            DyReportDTO dto = new DyReportDTO();
            dto.setMemberId(member.getMemberId());
            dto.setSensorNo(mainPlan.getSensorNo());
            String date = DateHelper.plusDate(plan.getPlanDate(), -1);
            dto.setStartDt(date);
            Map<Integer, String> eatTime = greenStarPlanService.getMemberEatTime(member.getMemberId(), date);
            List<JSONObject> result = dynamicBloodSugarStatService.analysisDailyDyBloodSugar(1, dto, eatTime);
            if(result != null)
                greenStarPlanService.createExceptionPlan(planId, result);
        }
        return Result.ok(greenStarPlanService.getPlanById(planId));
    }


    @RequestMapping("hasUpdateDy")
    public Result hasUpdateDy(String planId) {
        MemberPO member = SessionTool.getWechatSession();
        ValidateTool.checkParameterIsNull("planId", planId);
        return Result.ok(greenStarPlanService.checkUpdate(planId));
    }

    @RequestMapping("hasUpdateDyThreeHour")
    public Result hasUpdateDyThreeHour(String sensorNo) {
        ValidateTool.checkParameterIsNull("sensorNo", sensorNo);
        return Result.ok(greenStarPlanService.checkUpdateThreeHour(sensorNo));
    }

    @RequestMapping("checkMemberInfo")
    public Result checkMemberInfo(){
        MemberPO member = SessionTool.getWechatSession();
        return Result.ok(greenStarPlanService.checkMemberInfo(member.getMemberId()));
    }

    /**
     * @param dto
     * @return
     */
    @RequestMapping("/analysisDailyDyBloodSugar")
    public Result analysisDailyDyBloodSugar(DyReportDTO dto) {
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        ValidateTool.checkParameterIsNull("sensorNo", dto.getSensorNo());
        ValidateTool.checkParameterIsNull("startDt", dto.getStartDt());
        String date = dto.getStartDt();
        Map<Integer, String> eatTime = greenStarPlanService.getMemberEatTime(dto.getMemberId(), date);
        List<JSONObject> result = dynamicBloodSugarStatService.analysisDailyDyBloodSugar(0, dto, eatTime);
        return Result.ok(result);
    }

    /**
     * 更新绿星计划使用的患者档案
     *
     * @param po
     * @return
     */
    @RequestMapping("/updateMemberArchives")
    public Result updateMemberArchives(GsMemberArchivesPO po) {
        MemberPO member = SessionTool.getWechatSession();
        po.setMemberId(member.getMemberId());
        greenStarPlanService.updateMemberArchives(po);
        return Result.ok();
    }

    /**
     * 获取绿星计划使用的患者档案
     *
     * @return
     */
    @RequestMapping("/getMemberArchivesById")
    public Result getMemberArchivesById(String sensorNo) {
        MemberPO member = SessionTool.getWechatSession();
        String memberId = member.getMemberId();
        ValidateTool.checkParameterIsNull("sensorNo", sensorNo);
        GsMemberArchivesPO memberArchivesPO = greenStarPlanService.getMemberArchivesById(memberId);
        if (memberArchivesPO != null) {
            GetGreenStarMainPlanDto dto = new GetGreenStarMainPlanDto();
            dto.setMemberId(memberId);
            dto.setSensorNo(sensorNo);
            if (!greenStarPlanService.hasOpenPlan(dto))
                memberArchivesPO.setAgreement(0);
            else
                memberArchivesPO.setAgreement(1);
        }
        return Result.ok(memberArchivesPO);
    }

    /**
     * 获取记一记
     *
     * @param dto
     * @return
     */
    @RequestMapping("/listRemember")
    public Result listRemember(GreenStarRememberDTO dto) {
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        ValidateTool.checkParamIsNull(dto.getRememberDate(), "rememberDate");
        List<GreenStarRememberPO> list = new ArrayList<>();
        return Result.ok(list);
    }

    /**
     * 获取探头有数据
     * @param sensorNo
     * @return
     */
    @RequestMapping("/listBloodSugarRecordDt")
    public Result listBloodSugarRecordDt(String sensorNo){
        ValidateTool.checkParamIsNull(sensorNo, "sensorNo");
        MemberPO member = SessionTool.getWechatSession();
        return Result.ok(dyBloodSugarService.listBloodSugarRecordDt(sensorNo));
    }

    /**
     * 获取课程链接
     * @param sid
     * @return
     */
    @RequestMapping("/getGreenStarCourseJumpUrl")
    public Result getGreenStarCourseJumpUrl(String sid){
        MemberPO member = SessionTool.getWechatSession();
        ValidateTool.checkParamIsNull(sid, "sid");
        GreenStarPlanCoursePO po = greenStarPlanService.getGreenStarCourseById(sid);
        if(po == null){
            throw new BusinessException("课程不存在");
        }
        return Result.ok(po.getJumpUrl());
    }

    /**
     * 完成学习计划
     * @param planId
     * @param itemId
     * @return
     */
    @RequestMapping("/completeCoursePlan")
    public Result completeCoursePlan(String planId, String itemId){
        MemberPO member = SessionTool.getWechatSession();
        greenStarPlanService.completeCoursePlan(planId, itemId);
        return Result.ok();
    }

    /**
     * @param sensorNo
     * @param date
     * @return
     */
    @RequestMapping("/getMaxEatFlu")
    public Result getMaxEatFlu(String sensorNo, String date){
        MemberPO member = SessionTool.getWechatSession();
        ValidateTool.checkParamIsNull(sensorNo, "sensorNo");
        ValidateTool.checkParamIsNull(date, "date");
        return Result.ok(String.format("%.1f", dynamicBloodSugarStatService.getMaxEatFlu(sensorNo, date)));
    }


    /**
     * 获取记一记日期列表
     * @param dto
     * @return
     */
    @RequestMapping("/listGetRememberDt")
    public Result listGetRememberDt(DietRecordDTO dto){
        MemberPO member = SessionTool.getWechatSession();
        ValidateTool.checkParamIsNull(dto.getBegin(), "begin");
        ValidateTool.checkParamIsNull(dto.getEnd(), "end");
        dto.setMemberId(member.getMemberId());
        return Result.ok(greenStarPlanService.listRecordDt(dto));
    }


    @RequestMapping("/getGreenStarPlanMenu")
    public Result getGreenStarPlanMenu(GetGreenStarMainPlanDto dto){
        MemberPO member = SessionTool.getWechatSession();
        ValidateTool.checkParamIsNull(dto.getSensorNo(), "sensorNo");
        dto.setMemberId(member.getMemberId());
       return Result.ok(greenStarPlanService.getGreenStarPlanMenu(dto));
    }
}
