package com.comvee.cdms.dybloodsugar.contorller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.service.DyStaticsService;
import com.comvee.cdms.dybloodsugar.service.DynamicBloodSugarStatService;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarDailySummaryListVO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarDailyTrendVO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarDynamicVO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarMeanAbsoluteDeviationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wechat/dy/statistics/")
public class WechatDyStatisticsContorller {

    @Autowired
    private DyStaticsService dyStaticsService;

    @Autowired
    private DynamicBloodSugarStatService dynamicBloodSugarStatService;

    /**
     * @api {post}/wechatapp/dy/statistics/loadSensorBloodSugarChart.do 获取探头的血糖统计图表
     * @author 林雨堆
     * @time 2020/06/11 17:00
     * @apName loadSensorBloodSugarChart 获取探头的血糖统计图表
     * @apiGroup wechat-dy-statistics
     * @apiVersion 0.0.1
     * @apiParam {String} sensorNo  传感器编号（必填）
     * @apiParam {String} startDt  开始时间 格式"1970-01-01"（必填）
     * @apiParam {String} endDt  结束时间 格式"1970-01-01"（必填）
     * @apiParam {String} type 图表数据类型（必填） 1 血糖日趋势图 2 动态血糖图 3 日间血糖平均绝对差 4 每日血糖总结
     * @apiSampleRequest  {post}/web/dy/statistics/loadSensorBloodSugarChart.do
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 OK
     * type=1:血糖日趋势图
     * {
     *     "obj":{
     *         chartShow:1,// 是否显示页面，1显示 0不显示
     *         avgNum:"0.0", //平均血糖值
     *         standardVal:"0.0", //标准差
     *         eventCountOfLow:0, //低血糖事件次数
     *         avgAwiTimeOfLow:"0.0", //平均低血糖持续时间
     *         eventCountOfHigh:0, //高血糖事件次数
     *         avgAwiTimeOfHigh:"0.0", //平均高血糖持续时间
     *         eventCountOfNormal:0, //正常血糖事件次数
     *         avgAwiTimeOfNormal:"0.0", //平均正常血糖持续时间
     *         awiTimeRateOfLow:"0.0", //低血糖(<3.9)持续的时间占总时间（查询范围）比例
     *         awiTimeRateOfHigh:"0.0", //高血糖(>10.0)持续的时间占总时间（查询范围）比例
     *         awiTimeRateOfNormal:"0.0", //正常血糖(3.9-10)持续的时间占总时间（查询范围）比例
     *         coefficientOfVariation:"0.0" //血糖变异系数
     *         meanAmplitudeOfGlycemicExcursion:"--" //平均血糖波动幅度
     *         awiTimeRateOf3_9:"0.0", //血糖小于3.9持续的时间占总时间（查询范围）比例
     *         awiTimeRateOf4_0:"0.0", //血糖小于4.0持续的时间占总时间（查询范围）比例
     *         awiTimeRateOf13_9:"0.0", //血糖小于13.9持续的时间占总时间（查询范围）比例
     *         chartData:{
     *             xArea:[x1,x2,x3,...,x7,xN], //x轴坐标
     *             dataSource:[[x,y,s,k],[x,y,s,k],...] //x值，y值，s状态：1偏低 3正常 5偏高 k：血糖值的编号
     *         },
     *         dateOfLast14Day:[d1,d2,d3,...,d14], //近14天日期
     *         lowLineVal:3.9, //控制目标下限
     *         highLineVal:10.0, //控制目标上限
     *         ghb:7 //糖化血红蛋白
     *     },
     *     "code":"0",
     *     "msg":"",
     *     "success":true
     * }
     * or
     * type=2:动态血糖图
     * {
     *     "obj":{
     *         chartShow:1,// 是否显示页面，1显示 0不显示
     *         avgNum:"0.0", //平均血糖值
     *         standardVal:"0.0", //标准差
     *         eventCountOfLow:0, //低血糖事件次数
     *         avgAwiTimeOfLow:"0.0", //平均低血糖持续时间
     *         eventCountOfHigh:0, //高血糖事件次数
     *         avgAwiTimeOfHigh:"0.0", //平均高血糖持续时间
     *         eventCountOfNormal:0, //正常血糖事件次数
     *         avgAwiTimeOfNormal:"0.0", //平均正常血糖持续时间
     *         awiTimeRateOfLow:"0.0", //低血糖(<3.9)持续的时间占总时间（查询范围）比例
     *         awiTimeRateOfHigh:"0.0", //高血糖(>10.0)持续的时间占总时间（查询范围）比例
     *         awiTimeRateOfNormal:"0.0", //正常血糖(3.9-10)持续的时间占总时间（查询范围）比例
     *         coefficientOfVariation:"0.0" //血糖变异系数
     *         meanAmplitudeOfGlycemicExcursion:"--" //平均血糖波动幅度
     *         awiTimeRateOf3_9:"0.0", //血糖小于3.9持续的时间占总时间（查询范围）比例
     *         awiTimeRateOf4_0:"0.0", //血糖小于4.0持续的时间占总时间（查询范围）比例
     *         awiTimeRateOf13_9:"0.0", //血糖小于13.9持续的时间占总时间（查询范围）比例
     *         chartData:{
     *             xArea:[x1,x2,x3,...,x7,xN], //x轴坐标
     *             dataSource:{
     *                 curveOfTenPercent:[[x,y],[x,y],...] //10%曲线 x值，y值
     *                 curveOfTwentyFivePercent:[[x,y],[x,y],...] //25%曲线 x值，y值
     *                 curveOfFiftyPercent:[[x,y],[x,y],...] //50%曲线 x值，y值
     *                 curveOfSeventyFivePercent:[[x,y],[x,y],...] //75%曲线 x值，y值
     *                 curveOfNinetyPercent:[[x,y],[x,y],...] //90%曲线 x值，y值
     *             }
     *         },
     *         lowLineVal:3.9, //控制目标下限
     *         highLineVal:10.0, //控制目标上限
     *         ghb:7 //糖化血红蛋白
     *     },
     *     "code":"0",
     *     "msg":"",
     *     "success":true
     * }
     * or
     * type=3:日间血糖平均绝对差
     * {
     *     "obj":{
     *         chartShow:1,// 是否显示页面，1显示 0不显示
     *         "daySugarAvgDiffValMap":{"04/22~04/23":0.0,"04/23~04/23":0.0,..."MM/dd~MM/dd":0.0}, //日间血糖平均绝对差
     *         "highLineVal":0.9, //控制目标上限
     *         "lowLineVal":0 //控制目标下限
     *     },
     *     "code":"0",
     *     "msg":"",
     *     "success":true
     * }
     * or
     * type=4:每日血糖总结
     * {
     *     "obj":{
     *         "chartShow":1,
     *         "highLineVal":10,
     *         "lowLineVal":3.9
     *         "listChartData":[{
     *              "avgAwiTimeOfHigh":90,
     *              "avgAwiTimeOfLow":"0.00",
     *              "avgAwiTimeOfNormal":480,
     *              "avgNum":"12.11",
     *              "awiTimeRateOf13_9":"57.89",
     *              "awiTimeRateOf3_9":"5.26",
     *              "awiTimeRateOf4_0":"10.53",
     *              "awiTimeRateOfHigh":"15.79",
     *              "awiTimeRateOfLow":"0.00",
     *              "awiTimeRateOfNormal":"84.21",
     *              "chartData":{
     *                      xArea:[x1,x2,x3,...,x7,xN], //x轴坐标
     *                      dataSource:[[x,y,s,k],[x,y,s,k],...] //x值，y值，s状态：1偏低 3正常 5偏高 k：血糖值的编号
     *              },
     *              "chartShow":1,
     *              "coefficientOfVariation":"47.88",
     *              "eventCountOfHigh":1,
     *              "eventCountOfLow":0,
     *              "eventCountOfNormal":1,
     *              "highLineVal":10,
     *              "lowLineVal":3.9,
     *              "meanAmplitudeOfGlycemicExcursion":"21.00",
     *              "recordDt":"2019-06-23",
     *              "standardVal":"5.80",
     *              "ghb":7 // 糖化血红蛋白
     *          },...]
     *     },
     *     "code":"0",
     *     "msg":"",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"登录超时，请重新登录",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"系统错误",
     *     "success":false
     * }
     */
    @RequestMapping("loadSensorBloodSugarChart")
    public Result loadSensorBloodSugarChart(@Validated DyStaticsDTO dto){
        JSONObject model = this.dyStaticsService.loadSensorBloodSugarChart(dto);
        return new Result(model);
    }

    /**
     * @api {post}/wechatapp/dy/statistics/addDYYPStatisticsAdvice.do 添加医生建议
     * @author 林雨堆
     * @time 2020/06/11 17:00
     * @apName addDYYPStatisticsAdvice 添加医生建议-针对报告
     * @apiGroup wechat-dy-statistics
     * @apiVersion 0.0.1
     * @apiParam {String} content  建议内容（必填）
     * @apiParam {String} doctorId  医生编号（必填）
     * @apiParam {String} statisticsId  报告唯一主键编号（必填）
     * @apiSampleRequest  {post}/wechatapp/dy/statistics/addDYYPStatisticsAdvice.do
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
     * 获取获取日间血糖平均绝对差
     * @param dto
     * @return
     */
    @RequestMapping("getDynamicBloodSugarMeanAbsoluteDeviation")
    public Result getDynamicBloodSugarMeanAbsoluteDeviation(@Validated DyStaticsDTO dto){
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WECHAT);
        DynamicBloodSugarMeanAbsoluteDeviationVO result = this.dynamicBloodSugarStatService.getDynamicBloodSugarMeanAbsoluteDeviation(dto);
        return Result.ok(result);
    }

    /**
     * 获取动态血糖每日趋势图统计结果
     * @param dto
     * @return
     */
    @RequestMapping("getDynamicBloodSugarDailyTrend")
    public Result getDynamicBloodSugarDailyTrend(@Validated DyStaticsDTO dto){
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WECHAT);
        DynamicBloodSugarDailyTrendVO result = this.dynamicBloodSugarStatService.getDynamicBloodSugarDailyTrend(dto);
        return Result.ok(result);
    }

    /**
     * 加载每日血糖总结列表统计结果
     * @param dto
     * @return
     */
    @RequestMapping("listDynamicBloodSugarDailySummary")
    public Result listDynamicBloodSugarDailySummary(@Validated DyStaticsDTO dto){
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WECHAT);
        DynamicBloodSugarDailySummaryListVO result = this.dynamicBloodSugarStatService.listDynamicBloodSugarDailySummary(dto);
        return Result.ok(result);
    }

    /**
     * 获取动态统计结果
     * @param dto
     * @return
     */
    @RequestMapping("getDynamicBloodSugarDynamic")
    public Result getDynamicBloodSugarDynamic(@Validated DyStaticsDTO dto){
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WECHAT);
        DynamicBloodSugarDynamicVO result = this.dynamicBloodSugarStatService.getDynamicBloodSugarDynamic(dto);
        return Result.ok(result);
    }





}