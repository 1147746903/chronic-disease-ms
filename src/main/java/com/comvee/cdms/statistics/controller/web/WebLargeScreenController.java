package com.comvee.cdms.statistics.controller.web;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.statistics.service.MemberExceptionScreenService;
import com.comvee.cdms.statistics.service.StatisticsService;
import com.comvee.cdms.statistics.vo.CommitteeRankVO;
import com.comvee.cdms.statistics.vo.ListScreenStaticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2022/8/3
 */
@RestController
@RequestMapping("/web/largescreen")
public class WebLargeScreenController {


    @Autowired
    private StatisticsService statisticsService;


    @Autowired
    private MemberExceptionScreenService memberExceptionScreenService;

    /**
     * 导航栏数据  医院id可用逗号隔开
     * @param items
     * @return
     */
    @RequestMapping("loadNavigationByHospitalId")
    public Result loadNavigationByHospitalId(String items, String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        ValidateTool.checkParameterIsNull("items",items);
        String[] split = items.split(",");
        List<String> list = Arrays.asList(split);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        Map<String, Object> map = this.statisticsService.loadNavigationItemData(list, hospitalIdList, DateHelper.getToday());
        return Result.ok(map);
    }


    /**
     * 加载大屏患者异常信息---一体机
     * @param hospitalId
     * @return
     */
    @RequestMapping("loadMemberExceptionList")
    public Result getNumberStatistics(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> map = memberExceptionScreenService.loadMemberExceptionDayList(hospitalId);
        return new Result(map);
    }



    /*-------------------------大屏统计start--------------------------*/


    /**
     * 慢性病分层分级整体情况
     */
    @RequestMapping("loadDiseaseDataTodayByHospitalId")
    public Result loadDiseaseDataByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        String today = DateHelper.getToday();
        Map<String, Object> map = this.statisticsService.loadHospitalDiseaseDataForScreen(hospitalIdList,today);
        return Result.ok(map);
    }


    /**
     * 并发症
     */
    @RequestMapping("loadComplicationYearByHospitalId")
    public Result loadComplicationYearByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        String startDt = DateHelper.getDate(new Date(),"yyyy")+ "-01-01 00:00:00";
        String endDt = DateHelper.getTime();
        Map<String, Object> map = this.statisticsService.loadHospitalComplicationData(hospitalIdList,startDt,endDt);
        return Result.ok(map);
    }


    /**
     * 年度重要指标控制情况
     */
    @RequestMapping("loadImportantIndicByHospitalId")
    public Result loadImportantIndicByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        String startDt = DateHelper.getDate(new Date(),"yyyy")+ "-01-01 00:00:00";
        String endDt = DateHelper.getTime();
        Map<String, Object> map = this.statisticsService.loadImportantIndicByHospitalId(hospitalIdList,startDt,endDt);
        return Result.ok(map);
    }


    /**
     * 年度医院管理数据
     */
    @RequestMapping("loadManagementDataByHospitalId")
    public Result loadManagementDataByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> map = this.statisticsService.loadManagementDataByHospitalId(hospitalId);
        return Result.ok(map);
    }

    /**
     * 年龄分布图
     */
    @RequestMapping("loadMemberAgeDataByHospitalId")
    public Result loadMemberAgeDataByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> map = this.statisticsService.loadMemberAgeDataByHospitalId(hospitalId);
        return Result.ok(map);
    }

    /**
     * bmi分布图
     */
    @RequestMapping("loadMemberBmiDataByHospitalId")
    public Result loadMemberBmiDataByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> map = this.statisticsService.loadMemberBmiDataByHospitalId(hospitalId);
        return Result.ok(map);
    }


    /**
     * 糖尿病/高血压 类型占比 饼状图（弃用）
     */
    @RequestMapping("loadMemberDiseaseTypeData")
    public Result loadMemberDiseaseTypeDataByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> map = this.statisticsService.loadMemberDiseaseTypeDataByHospitalId(hospitalId);
        return Result.ok(map);
    }

    /**
     * 血糖血压测量统计
     */
    @RequestMapping("loadMemberRecordSugarPressureDta")
    public Result loadMemberRecordSugarPressureDta(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> map = this.statisticsService.loadMemberRecordSugarPressureDta(hospitalId);
        return Result.ok(map);
    }

    /**
     * 年度区域患者检查与体检情况
     */
    @RequestMapping("loadCommitteeRankByHospitalId")
    public Result loadHospitalRankByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<CommitteeRankVO> committeeRankVOS = this.statisticsService.loadCommitteeRankByHospitalId(hospitalId);
        return Result.ok(committeeRankVOS);
    }

    /**
     * 年度随访分布情况
     */
    @RequestMapping("loadFollowDataByHospitalId")
    public Result loadFollowDataByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> map = this.statisticsService.loadFollowDataByHospitalId(hospitalId);
        return Result.ok(map);
    }

    /*-------------------------大屏统计end--------------------------*/

    /*---------------------异常提醒弹窗start------------------*/
    /**
     * 今日建档人数列表
     */
    @RequestMapping("pageMemberJoinHospital")
    public Result pageMemberJoinHospital(String hospitalId, PageRequest pageRequest){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        PageResult<ListScreenStaticsVO> result = this.statisticsService.pageMemberJoinHospital(hospitalIdList, pageRequest);
        return Result.ok(result);
    }

    /**
     * 今日血糖测量人数列表
     */
    @RequestMapping("pageMemberBloodSugar")
    public Result pageMemberBloodSugar(String hospitalId, PageRequest pageRequest){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        PageResult<ListScreenStaticsVO> result = this.statisticsService.pageMemberBloodSugar(hospitalIdList, pageRequest);
        return Result.ok(result);
    }

    /**
     * 今日血压测量人数列表
     */
    @RequestMapping("pageMemberBloodPressure")
    public Result pageMemberBloodPressure(String hospitalId, PageRequest pageRequest){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        PageResult<ListScreenStaticsVO> result = this.statisticsService.pageMemberBloodPressure(hospitalIdList, pageRequest);
        return Result.ok(result);
    }

    /**
     * 今日糖尿病分标异常人数列表
     */
    @RequestMapping("pageMemberDiabetesLevel")
    public Result pageMemberDiabetesLevel(String hospitalId, PageRequest pageRequest){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        PageResult<ListScreenStaticsVO> result = this.statisticsService.pageMemberDiabetesLevel(hospitalIdList, pageRequest);
        return Result.ok(result);
    }

    /**
     * 今日高血压分层分级异常人数列表
     */
    @RequestMapping("pageMemberHypLevel")
    public Result pageMemberHypLevel(String hospitalId, PageRequest pageRequest){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        PageResult<ListScreenStaticsVO> result = this.statisticsService.pageMemberHypLevel(hospitalIdList, pageRequest);
        return Result.ok(result);
    }


    /**
     * 获取分层分级
     */
    @RequestMapping("/getMemberLevelById")
    public Result getMemberLevelById(String sid){
        ValidateTool.checkParameterIsNull("sid",sid);
        String reasonById = this.statisticsService.getMemberLevelReasonById(sid);
        return Result.ok(reasonById);
    }

    /*---------------------异常提醒弹窗end------------------*/

    /*---------------------数据大屏弹窗start-------------------*/


    /**
     * 今日门诊详情
     * @param hospitalId
     * @return
     */
    @RequestMapping("detailTodayVisitDetail")
    public Result getTodayVisitDetail(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> todayVisitDetail = this.statisticsService.getTodayVisitDetail(hospitalId);
        return Result.ok(todayVisitDetail);
    }


    /**
     * 年度随访分布情况
     * @param hospitalId
     * @return
     */
    @RequestMapping("detailFollowDataByHospitalId")
    public Result detailFollowDataByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> map = this.statisticsService.detailFollowDataByHospitalId(hospitalId);
        return Result.ok(map);
    }


    /*---------------------数据大屏弹窗end-------------------*/

}
