package com.comvee.cdms.app.doctorapp.contorller;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.referral.dto.ListReferralApplyDTO;
import com.comvee.cdms.referral.service.ReferralApplyServiceI;
import com.comvee.cdms.referral.vo.H5ListReferralApplyVO;
import com.comvee.cdms.statistics.service.StatisticsService;
import com.comvee.cdms.statistics.vo.CommitteeRankVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2022/7/28
 */
@RestController
@RequestMapping("/docapp/statistics")
@RequiresUser
public class DoctorAppStatisticsController {


    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private ReferralApplyServiceI referralApplyService;

    /**
     * 导航栏
     * today_visit  manage_member  diab_member  hyp_member
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
     * 年度医院管理数据
     */
    @RequestMapping("loadManagementDataByHospitalId")
    public Result loadManagementDataByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> map = this.statisticsService.loadManagementDataByHospitalId(hospitalId);
        return Result.ok(map);
    }


    /**
     * 慢性病分层分级整体情况
     */
    @RequestMapping("loadDiseaseDataTodayByHospitalId")
    public Result loadDiseaseDataByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        String today = DateHelper.getToday();
        Map<String, Object> map = this.statisticsService.loadHospitalDiseaseDataForScreen(Arrays.asList(hospitalId),today);
        map.remove("disease");
        return Result.ok(map);
    }


    /**
     * 年度区域患者检查与体检情况
     */
    @RequestMapping("loadCommitteeRank")
    public Result loadHospitalRankByHospitalId(){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        List<CommitteeRankVO> committeeRankVOS = this.statisticsService.loadCommitteeRankForH5(webSession);
        return Result.ok(committeeRankVOS);
    }

    /**
     * 转诊列表
     */
    @RequestMapping("listReferral")
    public Result listReferral(ListReferralApplyDTO dto, PageRequest pager){
        PageResult<H5ListReferralApplyVO> pageResult = this.referralApplyService.listReferralPageForH5(dto, pager);
        Result resultModel = new Result(pageResult);
        return resultModel;
    }



}
