package com.comvee.cdms.sign.controller.web;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.sign.po.SugarMonthReportPO;
import com.comvee.cdms.sign.service.SugarMonthReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/sugarMonthReport")
public class WebSugarMonthReportController {
    @Autowired
    private SugarMonthReportService sugarMonthReportService;

    /**
     * @api {post}/web/sugarMonthReport/listSugarMonthReport.do 加载月度控糖报告列表
     * @author 林雨堆
     * @time 2019/10/12
     * @apiName 加载月度控糖报告列表
     * @apiGroup web-sugarMonthReport
     * @apiVersion 4.2.2
     * @apiParam {String} memberId 患者编号
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.7.168:9080/intelligent-prescription/web/sugarMonthReport/listSugarMonthReport.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listSugarMonthReport")
    public Result listSugarMonthReport(PageRequest pr,String memberId){
        PageResult pageResult = this.sugarMonthReportService.listSugarMonthReport(memberId ,pr);
        return Result.ok(pageResult);
    }

    /**
     * @api {post}/web/sugarMonthReport/getSugarMonthReportById.do 根据报告id获取月度控糖报告
     * @author 林雨堆
     * @time 2019/10/12
     * @apiName 根据报告id获取月度控糖报
     * @apiGroup web-sugarMonthReport
     * @apiVersion 4.2.2
     * @apiParam {String} reportId 报告编号
     * @apiSampleRequest  http://192.168.7.168:9080/intelligent-prescription/web/sugarMonthReport/getSugarMonthReportById.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("getSugarMonthReportById")
    public Result getSugarMonthReportById(String reportId){
        ValidateTool.checkParameterIsNull(reportId ,"reportId");
        SugarMonthReportPO sugarMonthReportPO = this.sugarMonthReportService.getSugarMonthReportByIdAndCompleteReport(reportId);
        return Result.ok(sugarMonthReportPO);
    }
}
