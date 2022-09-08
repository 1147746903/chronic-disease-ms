package com.comvee.cdms.complication.service;


import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.complication.model.po.ScreeningStatsPO;
import com.comvee.cdms.complication.model.vo.AssessResultStatsVO;
import com.comvee.cdms.complication.model.vo.PatientInfoStatsVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatsVO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;

/**
 * @author: suyz
 * @date: 2019/4/16
 */
public interface ScreeningStatsService {

    /**
     * abi统计
     * @return
     */
    long abiStats(String doctorId);

    /**
     * vpt统计
     * @return
     */
    long vptStats(String doctorId);

    /**
     * 筛查患者信息统计
     * @return
     */
    PatientInfoStatsVO screeningPatientInfoStats(String doctorId);

    /**
     *计算全部患者数量
     * @return
     */
    long countAllPatient(String doctorId);

    /**
     * 筛查情况统计
     * @return
     */
    ScreeningStatsVO screeningStats(String doctorId);

    /**
     * 统计足部Wagner分级
     * @return
     */
    AssessResultStatsVO wagnerLevelStats(String doctorId);

    /**
     * 统计CKD的EFR分期
     * @return
     */
    AssessResultStatsVO gfrStagesStats(String doctorId);

    /**
     * 添加筛查统计结果
     * @param idCard
     * @param itemCode
     * @param itemValue
     */
    void addScreeningStats(ScreeningStatsPO screeningStatsPO);

    /**
     * 糖尿病各慢性病发病率 统计
     * @return
     */
    JSONObject chronicDiseaseStats(String doctorId);

    /**
     * 筛查饼状图数据
     * @return
     */
    JSONObject screeningPieChartData(String doctorId);

    /**
     * 删除筛查统计
     * @param memberId
     * @param doctorId
     */
    void deleteScreeningStats(String memberId, String doctorId);

    /**
     * 筛查患者信息统计-医院
     * @return
     */
    PatientInfoStatsVO screeningPatientInfoStatsForHos(GetStatisticsDTO dto);

    /**
     * 筛查饼状图数据-医院
     * @return
     */
    JSONObject screeningPieChartDataForHos(GetStatisticsDTO dtos);

    /**
     * 糖尿病各慢性病发病率 统计
     * @return
     */
    JSONObject chronicDiseaseStatsForHos(GetStatisticsDTO dto);
}
