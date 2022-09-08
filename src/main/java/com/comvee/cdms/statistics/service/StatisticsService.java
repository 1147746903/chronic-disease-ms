package com.comvee.cdms.statistics.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.app.doctorapp.model.app.PrescriptionModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.QueryChartDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import com.comvee.cdms.statistics.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/19
 */
public interface StatisticsService {

    /**
     * 获取数值统计
     * @param doctorId
     * @return
     */
    NumberStatisticsVO getNumberStatistics(String doctorId);

    /**
     * 加载订单图表数据
     * @param queryChartDTO
     * @return
     */
    List<OrderChartVO> listOrderChartData(QueryChartDTO queryChartDTO);

    /**
     * 加载统计图表数据
     * @param queryChartDTO
     * @return
     */
    List<StatisticsChartVO> listStatisticsChartData(QueryChartDTO queryChartDTO);

    /**
     * 获取血糖图表数据
     * @param startDt
     * @param endDt
     * @param doctorId
     * @return
     */
    BloodSugarChartDataVO listBloodSugarChartData(String startDt, String endDt, String doctorId);

    /**
     * 查询数据
     * @param synthesizeDataDTO
     * @return
     */
    Map<String,Long> queryDat(SynthesizeDataDTO synthesizeDataDTO);

    /**
     * 获取患者血糖统计
     * @param memberId
     * @param startDt
     * @param endDt
     * @return
     */
    Result getMemberParamStatistics(String memberId, String startDt, String endDt);

    /**
     * 获取患者血糖统计
     * @param memberId
     * @param startDt
     * @param endDt
     * @return
     */
    Map<String , Object> getGraphsForParametersNewBloodSugar(String memberId, String startDt, String endDt);

    /**
     * 获取统计
     * @param dto
     * @return
     */
    JSONObject getStatistics(GetStatisticsDTO dto);

    /**
     *
     * @param memberId
     * @param startDt
     * @param endDt
     * @param paramCode
     * @return
     */
    Result getMemberParamCount(String memberId, String startDt, String endDt, String paramCode,Integer inHos);

    /**
     * 导航栏信息统计
     * @param items
     * @param hospitalIdList
     * @param date
     * @return
     */
    Map<String, Object> loadNavigationItemData(List<String> items, List<String> hospitalIdList, String date);

    /**
     * 工作台慢性病
     * @param
     * @return
     */
    Map<String, Object> loadHospitalDiseaseDataForScreen(List<String> hospitalIdList,String date);

    Map<String, Object> loadHospitalDiseaseDataForWork(List<String> hospitalIdList,String date);
    /**
     * 工作台健康管理
     * @param
     * @return
     */
    Map<String, Object> loadHospitalHealthManageData(List<String> hospitalIdList,String date);

    /**
     * 工作台并发症
     * @param hospitalIdList
     * @return
     */
    Map<String, Object> loadHospitalComplicationData(List<String> hospitalIdList, String startDt, String endDt);

    /**
     * 工作台重要指标控制情况
     * @param hospitalIdList
     * @param startDt
     * @param endDt
     * @return
     */
    Map<String, Object> loadImportantIndicByHospitalId(List<String> hospitalIdList,String startDt, String endDt);

    List<Map<String, Object> > listDiseaseEffectByAreaId(String areaId, String startDt, String endDt);

    List<CommitteeRankVO> loadCommitteeRankByHospitalId(String hospitalId);

    List<CommitteeRankVO> loadCommitteeRankForH5(DoctorSessionBO doctorSessionBO);

    //乡镇卫生院情况
    List<Map<String,Object>> loadHospitalLevel3Data(String areaId);

    Map<String, Object> loadFollowDataByHospitalId(String hospitalId);

    /**
     * 单体医院管理数据
     * @param hospitalId
     * @return
     */
    Map<String, Object> loadManagementDataByHospitalId(String hospitalId);

    /**
     * 年龄分布图
     * @param hospitalId
     * @return
     */
    Map<String, Object> loadMemberAgeDataByHospitalId(String hospitalId);
    /**
     * bmi分布图
     * @param hospitalId
     * @return
     */
    Map<String, Object> loadMemberBmiDataByHospitalId(String hospitalId);

    /**
     * 糖尿病/高血压 类型占比 饼状图
     */
    Map<String, Object> loadMemberDiseaseTypeDataByHospitalId(String hospitalId);

    /**
     * 血糖血压测量统计
     */
    Map<String, Object> loadMemberRecordSugarPressureDta(String hospitalId);


    PageResult<ListScreenStaticsVO> pageMemberJoinHospital(List<String> hospitalIdList, PageRequest pageRequest);

    PageResult<ListScreenStaticsVO> pageMemberBloodSugar(List<String> hospitalIdList, PageRequest pageRequest);

    PageResult<ListScreenStaticsVO> pageMemberBloodPressure(List<String> hospitalIdList, PageRequest pageRequest);

    PageResult<ListScreenStaticsVO> pageMemberDiabetesLevel(List<String> hospitalIdList, PageRequest pageRequest);

    PageResult<ListScreenStaticsVO> pageMemberHypLevel(List<String> hospitalIdList, PageRequest pageRequest);

    Map<String, Object> getTodayVisitDetail(String hospitalId);

    Map<String, Object> detailFollowDataByHospitalId(String hospitalId);

    String getMemberLevelReasonById(String sid);


}
