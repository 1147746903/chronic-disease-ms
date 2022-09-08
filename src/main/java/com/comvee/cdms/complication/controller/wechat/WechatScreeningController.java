package com.comvee.cdms.complication.controller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.complication.model.po.ScreeningKnowledgePO;
import com.comvee.cdms.complication.model.po.ScreeningListPO;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.complication.service.ScreeningKnowledgeService;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.user.service.UserJoinInfoService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/3/8
 */
@RestController
@RequestMapping("/wechat/screening")
@RequiresAuthentication
public class WechatScreeningController {

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private UserJoinInfoService userJoinInfoService;

    @Autowired
    private ScreeningKnowledgeService screeningKnowledgeService;

    /**
     * 根据id获取筛查报告
     * @param reportId
     * @return
     */
    @RequestMapping("/getReportByReportId")
    public Result getReportByReportId(String reportId){
        ScreeningReportPO screeningReportPO = this.screeningService.getScreeningReportById(reportId);
        return Result.ok(screeningReportPO);
    }

    /**
     * 获取本次筛查的所有报告
     * @param screeningId
     * @return
     */
    @RequestMapping("/listScreeningReport")
    public Result listScreeningReport(String screeningId){
        ValidateTool.checkParamIsNull(screeningId, "screeningId");
        List<ScreeningReportPO> list =  this.screeningService.listScreeningReport(screeningId);
        return Result.ok(list);
    }

    /**
     * 根据id获取筛查单内容
     * @param screeningId
     * @return
     */
    @RequestMapping("/getScreeningById")
    public Result getScreeningById(String screeningId){
        ValidateTool.checkParamIsNull(screeningId, "screeningId");
        ScreeningListPO screeningListPO = this.screeningService.getScreeningById(screeningId);
        return Result.ok(screeningListPO);
    }

    /**
     * 根据id获取知识学习内容
     * @param sid
     * @return
     */
    @RequestMapping("/getScreeningKnowledgeById")
    public Result getScreeningKnowledgeById(String sid){
        ValidateTool.checkParamIsNull(sid , "sid");
        ScreeningKnowledgePO po = this.screeningKnowledgeService.getScreeningKnowledge(sid ,null);
        return Result.ok(po);
    }

    /**
     * 加载患者的筛查报告列表
     * @param pr
     * @return
     */
    @RequestMapping("/listMemberScreeningReport")
    public Result listMemberScreeningReport(PageRequest pr){
        MemberPO memberPO = SessionTool.getWechatSession();
        PageResult result = this.screeningService.listMemberScreeningReport(memberPO.getMemberId() ,pr);
        return Result.ok(result);
    }
}
