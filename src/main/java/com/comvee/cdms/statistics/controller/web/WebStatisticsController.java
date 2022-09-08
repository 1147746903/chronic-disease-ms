package com.comvee.cdms.statistics.controller.web;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.hospital.constant.HospitalConstant;
import com.comvee.cdms.member.dto.CertificateGetMemberDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.QueryChartDTO;
import com.comvee.cdms.statistics.service.StatisticsService;
import com.comvee.cdms.statistics.vo.*;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author: suyz
 * @date: 2018/10/19
 */
@RestController
@RequestMapping("/web/statistics")
@RequiresUser
public class WebStatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private MemberService memberService;

    /**
     * 获取数值统计
     * @return
     */
    @RequestMapping("getNumberStatistics")
    public Result getNumberStatistics(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        NumberStatisticsVO vo = this.statisticsService.getNumberStatistics(doctorSessionBO.getDoctorId());
        return new Result(vo);
    }

    /**
     * 加载订单金额统计图表数据
     * @param queryChartDTO
     * @return
     */
    @RequestMapping("listOrderChartData")
    public Result listOrderChartData(@Validated QueryChartDTO queryChartDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        queryChartDTO.setDoctorId(doctorSessionBO.getDoctorId());
        List<OrderChartVO> list = this.statisticsService.listOrderChartData(queryChartDTO);
        return new Result(list);
    }

    /**
     * 加载各项数据统计图表数据
     * @param queryChartDTO
     * @return
     */
    @RequestMapping("listStatisticsChartData")
    public Result listStatisticsChartData(@Validated QueryChartDTO queryChartDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        queryChartDTO.setDoctorId(doctorSessionBO.getDoctorId());
        List<StatisticsChartVO> list = this.statisticsService.listStatisticsChartData(queryChartDTO);
        return new Result(list);
    }

    /**
     * 加载血糖统计图表数据
     * @param startDt
     * @param endDt
     * @return
     */
    @RequestMapping("listBloodSugarChartData")
    public Result listBloodSugarChartData(String startDt, String endDt){
        ValidateTool.checkParamIsNull(startDt, "startDt");
        ValidateTool.checkParamIsNull(endDt, "endDt");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        BloodSugarChartDataVO bloodSugarChartDataVO = this.statisticsService.listBloodSugarChartData(startDt, endDt, doctorSessionBO.getDoctorId());
        return new Result(bloodSugarChartDataVO);
    }

    /**
     * @api {post}/web/statistics/getMemberBloodSugarChartData.do 血糖标准天图，血糖漂移度分析，血檀趋势
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName 血糖标准天图，血糖漂移度分析，血檀趋势
     * @apiGroup web-statistics
     * @apiVersion 4.0.0
     * @apiParam {String} startDt 开始时间
     * @apiParam {String} endDt 结束时间
     * @apiParam {String} memberId 患者编号(必填)
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/statistics/getMemberBloodSugarChartData.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("getMemberBloodSugarChartData")
    public Result getMemberBloodSugarChartData(String memberId,String startDt,
                                               String endDt){
        ValidateTool.checkParameterIsNull("memberId不能为空" ,memberId);
        Result result = this.statisticsService.getMemberParamStatistics(memberId,startDt,endDt);
        return result;
    }

    /**
     * @api {post}/web/statistics/getStatistics.do 获取统计
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName getStatistics 获取统计
     * @apiGroup WEB-V6.0.0-C
     * @apiVersion 6.0.0
     * @apiParam {String} pageType 统计页面类型 1订单统计 2患者统计 3BMI分布 4血压达标率
     *                             5血脂达标率 6HbA1c分布 7血糖分析 8并发症筛查 9药品使用
     *                             10 BMI分布&HbA1c分布 11 血压达标率&出院血压达标率 (默认 1)
     * @apiParam {String} dtCode 1：当日 2：本周 3：三个月 4：半年 5：一年(可选)
     * @apiParam {String} startDt 开始时间 (必填 yyyy-MM-dd 默认当日)
     * @apiParam {String} endDt 结束时间 (必填 yyyy-MM-dd 默认当日)
     * @apiParam {String} visitType 类型 1住院 2门诊&居家 3 科研统计 (默认 1)
     * @apiParam {String} hospitalId 医院编号 （医院编号没有时，登录医护人员有切换医院权限时，默认当前登录医护人员的医院编号）
     * @apiParam {String} doctorId 医护人员编号 （登录医护人员没有切换医院权限时，医护人员编号没有时，医院编号没有时，默认医护人员编号）
     * @apiSampleRequest  http://192.168.7.203:8080/web/statistics/getStatistics.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("getStatistics")
    public Result getStatistics(@Validated GetStatisticsDTO dto){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if(StringUtils.isBlank(dto.getHospitalId())){
            dto.setHospitalId(doctorSessionBO.getHospitalId());
        }
        return new Result(this.statisticsService.getStatistics(dto));
    }

    /**
     * @api {post}/web/statistics/getMemberParamCount.do 获取用户血糖统计
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName 获取用户血糖统计
     * @apiGroup web-statistics
     * @apiVersion 4.0.0
     * @apiParam {String} memberId 患者编号(必填)
     * @apiParam {String} startDt 开始时间
     * @apiParam {String} endDt 结束时间
     * @apiParam {String} paramCode 时段code
     * @apiParam {String} inHos 1住院 0非住院
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/statistics/getMemberParamCount.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("getMemberParamCount")
    public Result getMemberParamCount(String memberId,String startDt,
                                      String endDt,String paramCode,Integer inHos){
        ValidateTool.checkParameterIsNull("memberId",memberId);
        Result result = this.statisticsService.getMemberParamCount(memberId,startDt,endDt,paramCode, inHos);
        return result;
    }



    /**
     * his嵌入页信息展示
     * paramType 1身份证 2就诊号 3住院号
     * paramNo 对应type的值
     * hospitalCode HospitalConstant.HOSPITAL_MAP配置的key
     */
    @RequestMapping("loadMemberHisEmbedded")
    public Result loadMemberHisEmbedded(Integer paramType,String paramNo,String hospitalCode) {
        ValidateTool.checkParameterIsNull("paramType",paramType);
        ValidateTool.checkParameterIsNull("paramNo",paramNo);
        CertificateGetMemberDTO getMemberDTO = new CertificateGetMemberDTO();
        if (paramType == 1){
            getMemberDTO.setIdCard(paramNo);
        }else if (paramType == 2){
            getMemberDTO.setVisitNo(paramNo);
        }else if (paramType == 3){
            getMemberDTO.setHospitalNo(paramNo);
        }
        MemberPO memberPO = memberService.getMemberByCertificateNo(getMemberDTO);
        String hospitalId = HospitalConstant.HOSPITAL_MAP.get(hospitalCode);
        Map<String, Object> map = memberService.loadMemberHisEmbedded(memberPO.getMemberId(),hospitalId);
        return new Result(map);
    }

    /**
     * 测试用--his嵌入页
     * @param memberId
     * @param hospitalId
     * @return
     */
    @RequestMapping("loadMemberHisEmbeddedTest")
    public Result loadMemberHisEmbedded(String memberId,String hospitalId) {
        ValidateTool.checkParameterIsNull("memberId",memberId);
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        Map<String, Object> map = memberService.loadMemberHisEmbedded(memberId,hospitalId);
        return new Result(map);
    }


    /**
     * 导航栏数据  医院id可用逗号隔开
     * @param items
     * @return
     */
    @RequestMapping("loadNavigationByHospitalId")
    public Result loadNavigationByHospitalId(String items,String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        ValidateTool.checkParameterIsNull("items",items);
        String[] split = items.split(",");
        List<String> list = Arrays.asList(split);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        Map<String, Object> map = this.statisticsService.loadNavigationItemData(list, hospitalIdList, DateHelper.getToday());
        return Result.ok(map);
    }


    /**
     * 获取工作台 分标/分层分级/慢病效果 医院
     */
    @RequestMapping("loadDiseaseDataByHospitalId")
    public Result loadHospitalDiseaseData(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        String date = null;
        //院长多医院分层分级统计当日，分中心统计所有
        if (!hospitalIdList.isEmpty() && hospitalIdList.size()>1){
            date = DateHelper.getToday();
        }
        Map<String, Object> map = this.statisticsService.loadHospitalDiseaseDataForWork(hospitalIdList,date);
        return Result.ok(map);
    }

    /**
     * 获取工作台 健康管理情况
     */
    @RequestMapping("loadHealthManageByHospitalId")
    public Result loadHospitalHealthManageData(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        Map<String, Object> map = this.statisticsService.loadHospitalHealthManageData(hospitalIdList,null);
        return Result.ok(map);
    }

    /**
     * 获取工作台 并发症情况
     */
    @RequestMapping("loadComplicationByHospitalId")
    public Result loadComplicationByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        Map<String, Object> map = this.statisticsService.loadHospitalComplicationData(hospitalIdList,null,null);
        return Result.ok(map);
    }


    /**
     * 工作台乡镇卫生院情况
     */
    @RequestMapping("loadTownHospitalData")
    public Result loadHospitalLevel3Data(String areaId){
        ValidateTool.checkParameterIsNull("areaId",areaId);
        List<Map<String, Object>> maps = this.statisticsService.loadHospitalLevel3Data(areaId);
        return Result.ok(maps);
    }


    /**
     * 年度慢病效果列表
     */
    @RequestMapping("listDiseaseEffectByHospitalId")
    public Result listDiseaseEffectByHospitalId(String areaId){
        ValidateTool.checkParameterIsNull("areaId",areaId);
        String startDt = DateHelper.getDate(new Date(),"yyyy")+ "-01-01 00:00:00";
        String endDt = DateHelper.getTime();
        List<Map<String, Object>> maps = this.statisticsService.listDiseaseEffectByAreaId(areaId, startDt, endDt);
        return Result.ok(maps);
    }

}
