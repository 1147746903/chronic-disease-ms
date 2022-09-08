package com.comvee.cdms.app.doctorapp.contorller;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.AddDYYPStatisticsAdviceDTO;
import com.comvee.cdms.dybloodsugar.dto.DyStaticsDTO;
import com.comvee.cdms.dybloodsugar.service.DyRememberService;
import com.comvee.cdms.dybloodsugar.service.DyStaticsService;
import com.comvee.cdms.dybloodsugar.service.DynamicBloodSugarStatService;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarDailySummaryListVO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarDailyTrendVO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarDynamicVO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarMeanAbsoluteDeviationVO;
import com.comvee.cdms.sign.dto.MemberBloodSugarDTO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.sign.vo.DoctorMemberVo;
import com.comvee.cdms.sign.vo.MemberBloodSugarVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docapp/dy/statistics/")
public class DoctorAppDyStatisticsController {

    @Autowired
    private DyStaticsService dyStaticsService;

    @Autowired
    private DynamicBloodSugarStatService dynamicBloodSugarStatService;

    @Autowired
    private DyRememberService dyRememberService;

    @Autowired
    private BloodSugarService bloodSugarService;
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
     * @api {post}/docapp/dy/statistics/listAllBloodSugarOfMember.do 获取所选时间的血糖记录
     * @apiName listTodayBloodSugarOfMember 获取住院患者每日血糖
     * @apiGroup blood-sugar-app
     * @apiParam {String} startDt 开始时间 格式: yyyy-MM-dd 00:00:00
     * @apiParam {String} endDt 结束时间 格式: yyyy-MM-dd 23:59:59
     *
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.2.44:8080/docapp/dy/statistics/listAllBloodSugarOfMember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listAllBloodSugarOfMember")
    public Result listAllBloodSugarOfMember(MemberBloodSugarDTO memberBloodSugarDTO, PageRequest page){
        if(com.comvee.cdms.common.utils.StringUtils.isBlank(memberBloodSugarDTO.getDoctorId())){
            DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
            memberBloodSugarDTO.setDoctorId(doctorSessionBO.getDoctorId());
            memberBloodSugarDTO.setHospitalId(doctorSessionBO.getHospitalId());
            memberBloodSugarDTO.setDepartmentId(doctorSessionBO.getDepartId());
        }
        PageResult<MemberBloodSugarVO> result = this.bloodSugarService.listAllBloodSugarOfMember(memberBloodSugarDTO, page);
        return Result.ok(result);
    }

    /**
     * 取医生所在科室名称
     * @return
     */
    @RequestMapping("getBloodSugarOfMemberDepartName")
    public Result getBloodSugarOfMemberDepartName(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        MemberBloodSugarDTO memberBloodSugarDTO = new MemberBloodSugarDTO();
        memberBloodSugarDTO.setStartDt(DateHelper.getNowDate().substring(0,10)+" 00:00:00");
        memberBloodSugarDTO.setEndDt(DateHelper.getNowDate().substring(0,10)+" 23:59:59");
        memberBloodSugarDTO.setDoctorId(doctorSessionBO.getDoctorId());
        memberBloodSugarDTO.setHospitalId(doctorSessionBO.getHospitalId());
        memberBloodSugarDTO.setDepartmentId(doctorSessionBO.getDepartId());
        DoctorMemberVo vo = this.bloodSugarService.getDoctorDepartName(memberBloodSugarDTO);
        return Result.ok(vo);
    }

}
