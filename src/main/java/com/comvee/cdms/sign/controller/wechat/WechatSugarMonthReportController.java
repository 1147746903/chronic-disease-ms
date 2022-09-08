package com.comvee.cdms.sign.controller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.sign.po.SugarMonthReportPO;
import com.comvee.cdms.sign.service.SugarMonthReportService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat/sugarMonthReport")
public class WechatSugarMonthReportController {

    @Autowired
    private SugarMonthReportService sugarMonthReportService;

    /**
     * 加载月度控糖报告列表
     * @param pr
     * @return
     *
     * http://192.168.7.120:9080/intelligent-prescription/wechat/sugarMonthReport/listSugarMonthReportByMid.do?rows=10&page=1&session
     */
    @RequestMapping("listSugarMonthReport")
    public Result listSugarMonthReport(PageRequest pr){
        MemberPO memberPO = SessionTool.getWechatSession();
        PageResult pageResult = this.sugarMonthReportService.listSugarMonthReport(memberPO.getMemberId() ,pr);
        return Result.ok(pageResult);
    }

    /**
     * 加载月度控糖报告列表-不用session
     * @param pr
     * @return
     *
     * http://192.168.7.120:9080/intelligent-prescription/wechat/sugarMonthReport/listSugarMonthReportByMid.do?memberId=190629144040100001&rows=10&page=1
     */
    @RequestMapping("listSugarMonthReportByMid")
    public Result listSugarMonthReportByMid(PageRequest pr,String memberId){
        PageResult pageResult = this.sugarMonthReportService.listSugarMonthReport(memberId ,pr);
        return Result.ok(pageResult);
    }

    /**
     * 根据报告id获取月度控糖报告
     * @param reportId
     * @return
     * http://192.168.7.120:9080/intelligent-prescription/wechat/sugarMonthReport/getSugarMonthReportById.do?reportId=191101010006101895
     */
    @RequestMapping("getSugarMonthReportById")
    public Result getSugarMonthReportById(String reportId){
        ValidateTool.checkParameterIsNull(reportId ,"reportId");
        SugarMonthReportPO sugarMonthReportPO = this.sugarMonthReportService.getSugarMonthReportByIdAndCompleteReport(reportId);
        return Result.ok(sugarMonthReportPO);
    }
}
