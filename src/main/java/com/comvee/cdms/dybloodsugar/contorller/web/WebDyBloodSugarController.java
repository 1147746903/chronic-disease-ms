package com.comvee.cdms.dybloodsugar.contorller.web;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodsugar.dto.DyReportDTO;
import com.comvee.cdms.dybloodsugar.po.*;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSettingService;
import com.comvee.cdms.dybloodsugar.service.DynamicBloodSugarStatService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/web/dy/bloodSugar")
public class WebDyBloodSugarController {

    @Autowired
    private DyBloodSugarService dyBloodSugarService;
    @Autowired
    private DyMemberSettingService dyMemberSettingService;

    @Autowired
    private DynamicBloodSugarStatService dynamicBloodSugarStatService;

    /**
     * 获取最新动态血糖记录
     * @param sensorNo
     * @return
     */
    @RequestMapping("getLatestDyBloodSugar")
    public Result getLatestDyBloodSugar(String sensorNo){
        ValidateTool.checkParameterIsNull("sensorNo" ,sensorNo);
        DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getLatestDyBloodSugar(sensorNo);
        return Result.ok(dyypBloodSugarPO);
    }


    /**
     * @api {post}/web/dy/bloodSugar/restoreDefault.do 恢复默认设置
     * @author 林雨堆
     * @time 2020/06/16
     * @apiName restoreDefault 恢复默认设置
     * @apiGroup web-setting
     * @apiVersion 6.3.0
     * @apiParam {String} id 恢复默认(1:作息时间设置,2:目标设置,3:其他设置)
     * @apiSampleRequest  {post}/web/dy/bloodSugar/restoreDefault.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/restoreDefault")
    public Result restoreDefault(String id){
        DyMemberSettingPO dyMemberSettingPO = this.dyMemberSettingService.restoreDefault(id);
        return Result.ok(dyMemberSettingPO);
    }

    /**
     * @api {post}/web/dy/bloodSugar/showSystemSetting.do 动态血糖指标数据回填
     * @author 林雨堆
     * @time 2020/06/16
     * @apiName showSystemSetting 动态血糖指标数据回填
     * @apiGroup web-setting
     * @apiVersion 6.3.0
     * @apiParam {String} memberId 患者id
     * @apiSampleRequest  {post}/web/dy/bloodSugar/showSystemSetting.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/showSystemSetting")
    public Result showSystemSetting(String memberId){
        DyMemberSettingPO dyMemberSettingPo = this.dyMemberSettingService.showSystemSetting(memberId);
        return Result.ok(dyMemberSettingPo);
    }


    /**
     * @api {post}/web/dy/bloodSugar/insertBloodSugarSystemSetting.do 保存动态血糖指标数据
     * @author 林雨堆
     * @time 2020/06/16
     * @apiName insertBloodSugarSystemSetting 保存动态血糖指标数据
     * @apiGroup web-setting
     * @apiVersion 6.3.0
     * @apiParam {VARCHAR} memberId 患者id
     * @apiParam {VARCHAR} breakfastDt 早餐时间
     * @apiParam {VARCHAR} lunchDt 午餐时间
     * @apiParam {VARCHAR} dinnerDt 晚餐时间
     * @apiParam {VARCHAR} sleepDt 入睡时间
     * @apiParam {DOUBLE} bloodSugarMin 血糖目标范围最小值
     * @apiParam {DOUBLE} bloodSugarMax 血糖目标范围最大值
     * @apiParam {INTEGER} bloodSugarNorm 血糖目标范围时间占比标准
     * @apiParam {INTEGER} bloodSugarNormThan 高于目标范围时间占比标准
     * @apiParam {INTEGER} bloodSugarNormLess 低于目标范围时间占比标准
     * @apiParam {DOUBLE} bloodTimeRatioMax 血糖时间占比最大值
     * @apiParam {DOUBLE} bloodTimeRatioMin 血糖时间占比最小值
     * @apiSampleRequest  {post}/web/dy/bloodSugar/insertBloodSugarSystemSetting.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/insertBloodSugarSystemSetting")
    public Result insertBloodSugarSystemSetting(DyMemberSettingPO dyMemberSettingPo){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dyMemberSettingPo.setOperationId(doctorSessionBO.getDoctorId());
        this.dyMemberSettingService.insertSystemSetting(dyMemberSettingPo);
        return Result.ok("");
    }

    /**
     * 获取血糖报告列表
     * path /web/dy/bloodSugar/getBloodSugarReportList.do
     * @param dto
     * @return
     */
    @RequestMapping("/getBloodSugarReportList")
    public Result getBloodSugarReport(DyReportDTO dto, PageRequest pager){
        if(dto.getDoctorId() == null){
            DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
            dto.setDoctorId(doctorSessionBO.getDoctorId());
        }
        return Result.ok(dynamicBloodSugarStatService.getBloodSugarReportList(dto, pager));
    }

    /**
     * 获取血糖报告
     * path /web/dy/bloodSugar/getBloodSugarReport.do
     * @param dto
     * @return
     */
    @RequestMapping("/getBloodSugarReport")
    public Result getBloodSugarReport(DyReportDTO dto){
        ValidateTool.checkParameterIsNull("reportId" , dto.getReportId());
        return Result.ok(dynamicBloodSugarStatService.getBloodSugarReportById(dto.getReportId()));
    }

    /**
     * 修改报告
     * path /web/dy/bloodSugar/setBloodSugarReport.do
     * @param dto
     * @return
     */
    @RequestMapping("/setBloodSugarReport")
    public Result setBloodSugarReport(DyReportDTO dto){
        ValidateTool.checkParameterIsNull("reportId" , dto.getReportId());
        ValidateTool.checkParameterIsNull("details" , dto.getReportId());
        dynamicBloodSugarStatService.setBloodSugarReportById(dto.getReportId(), dto.getDetails());
        return Result.ok();
    }

    /**
     * 血糖分析
     * path /web/dy/bloodSugar/analysisDyBloodSugar.do
     * @param dto
     * @return
     */
    @RequestMapping("/analysisDyBloodSugar")
    public Result analysisDyBloodSugar(DyReportDTO dto){
        ValidateTool.checkParameterIsNull("sensorNo" ,dto.getSensorNo());
        if(dto.getDoctorId() == null){
            DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
            dto.setDoctorId(doctorSessionBO.getDoctorId());
        }
        String sid = dynamicBloodSugarStatService.analysisDyBloodSugar(dto);
        return Result.ok(sid);
    }



}
