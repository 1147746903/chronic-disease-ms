package com.comvee.cdms.dybloodsugar.contorller.web;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.AddDYYPStatisticsAdviceDTO;
import com.comvee.cdms.dybloodsugar.dto.DyStaticsDTO;
import com.comvee.cdms.dybloodsugar.service.DyStaticsService;
import com.comvee.cdms.dybloodsugar.service.DynamicBloodSugarStatService;
import com.comvee.cdms.dybloodsugar.vo.*;
import com.comvee.cdms.exportexcle.tool.ExcelUtil;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("web/dy/statistics/")
public class WebDyStatisticsContorller {

    @Autowired
    private DyStaticsService dyStaticsService;

    @Autowired
    private DynamicBloodSugarStatService dynamicBloodSugarStatService;

    @Autowired
    private MemberService memberService;

    /**
     * @api {post}/web/dy/statistics/loadSensorBloodSugarChart.do 获取探头的血糖统计图表
     * @author 林雨堆
     * @time 2020/06/11 17:00
     * @apName loadSensorBloodSugarChart 获取探头的血糖统计图表
     * @apiGroup web-dy-statistics
     * @apiVersion 0.0.1
     * @apiParam {String} sensorNo  传感器编号（必填）
     * @apiParam {String} startDt  开始时间 格式"1970-01-01"（必填）
     * @apiParam {String} endDt  结束时间 格式"1970-01-01"（必填）
     * @apiParam {String} type 图表数据类型（必填） 1 血糖日趋势图 2 动态血糖图 3 日间血糖平均绝对差 4 每日血糖总结
     * @apiSampleRequest  {post}/web/dy/statistics/loadSensorBloodSugarChart.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("loadSensorBloodSugarChart")
    public Result loadSensorBloodSugarChart(@Validated DyStaticsDTO dto){
        JSONObject map = this.dyStaticsService.loadSensorBloodSugarChartOfWeb(dto);
        return new Result(map);
    }

    /**
     * @api {post}/web/dy/statistics/addDYYPStatisticsAdvice.do 添加医生建议
     * @author 林雨堆
     * @time 2020/06/11 17:00
     * @apName addDYYPStatisticsAdvice 添加医生建议-针对报告
     * @apiGroup web-dy-statistics
     * @apiVersion 0.0.1
     * @apiParam {String} content  建议内容（必填）
     * @apiParam {String} doctorId  医生编号（必填）
     * @apiParam {String} statisticsId  报告唯一主键编号（必填）
     * @apiSampleRequest  {post}/web/dy/statistics/addDYYPStatisticsAdvice.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("addDYYPStatisticsAdvice")
    public Result addDYYPStatisticsAdvice(@Validated AddDYYPStatisticsAdviceDTO dto){
        this.dyStaticsService.addDYYPStatisticsAdvice(dto);
        return Result.ok();
    }

    /**
     * 获取动态血糖每日趋势图统计结果
     * @param dto
     * @return
     */
    @RequestMapping("getDynamicBloodSugarDailyTrend")
    public Result getDynamicBloodSugarDailyTrend(@Validated DyStaticsDTO dto){
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WEB);
        DynamicBloodSugarDailyTrendVO result = this.dynamicBloodSugarStatService.getDynamicBloodSugarDailyTrend(dto);
        return Result.ok(result);
    }

//    /**
//     * 端口废弃
//     * 加载每日血糖总结列表统计结果
//     * @param dto
//     * @return
//     */
//    @RequestMapping("listDynamicBloodSugarDailySummary")
//    public Result listDynamicBloodSugarDailySummary(@Validated DyStaticsDTO dto){
//        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WEB);
//        DynamicBloodSugarDailySummaryListVO result = this.dynamicBloodSugarStatService.listDynamicBloodSugarDailySummary(dto);
//        return Result.ok(result);
//    }

    /**
     * 获取动态统计结果(动态葡萄糖图谱)
     * @param dto
     * @return
     */
    @RequestMapping("getDynamicBloodSugarDynamic")
    public Result getDynamicBloodSugarDynamic(@Validated DyStaticsDTO dto){
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WEB);
        DynamicBloodSugarDynamicVO result = this.dynamicBloodSugarStatService.getDynamicBloodSugarDynamic(dto);
        return Result.ok(result);
    }

    /**
     * 删除统计建议
     * @param sid
     * @return
     */
    @RequestMapping("deleteStatAdvice")
    public Result deleteStatAdvice(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        this.dynamicBloodSugarStatService.deleteStatAdvice(sid);
        return Result.ok();
    }

    /**
     * 获取动态血糖每日趋势图统计结果
     * @since v6.3.0 -->7.2.0
     * @param dto
     * @return
     */
    @RequestMapping("/v2/getDynamicBloodSugarDailyTrend")
    public Result getDynamicBloodSugarDailyTrendV2(@Validated DyStaticsDTO dto){
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WEB);
        DynamicBloodSugarDailyTrendV2VO result = this.dynamicBloodSugarStatService.getDynamicBloodSugarDailyTrendV2(dto);
        return Result.ok(result);
    }

    /**
     * 加载每日血糖总结列表统计结果
     * @param dto
     * @return
     */
    @RequestMapping("/v2/listDynamicBloodSugarDailySummary")
    public Result listDynamicBloodSugarDailySummaryV2(@Validated DyStaticsDTO dto){
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WEB);
        DynamicBloodSugarDailySummaryListV2VO result = this.dynamicBloodSugarStatService.listDynamicBloodSugarDailySummaryV2(dto);
        return Result.ok(result);
    }

    @RequestMapping("/exportDailyDyBloodSugar")
    public void exportDailyDyBloodSugar(DyStaticsDTO dto,String memberId, String hospitalId, String doctorId, HttpServletResponse response){
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WEB);
        if(doctorId == null) {
            DoctorSessionBO doctorPO = SessionTool.getWebSession();
            doctorId = doctorPO.getDoctorId();
        }
        MemberPO memberPO = memberService.getMemberById(memberId);
        Map<String, Object> memberArchives = memberService.getMemberArchivesByMemberId(memberId,doctorId ,hospitalId, false);
        memberArchives.put("member", memberPO);
        DynamicBloodSugarDynamicVO dynamicVO = dynamicBloodSugarStatService.getDynamicBloodSugarDynamic(dto);
        Map<String, Map<String, Double>>  dtDataMap = dynamicBloodSugarStatService.getDynamicBloodSugarValues(dto);
        ExcelUtil excelUtil = new ExcelUtil();
        excelUtil.exportDailyDyBloodSugar(dynamicVO, memberArchives, dtDataMap, response);
    }

}
