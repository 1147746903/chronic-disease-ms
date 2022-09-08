package com.comvee.cdms.complication.mapper;


import com.comvee.cdms.complication.model.dto.CountPatientParam;
import com.comvee.cdms.complication.model.dto.ScreeningStatsParam;
import com.comvee.cdms.complication.model.po.AssessDetailStatsPO;
import com.comvee.cdms.complication.model.po.ScreeningStatsDetailPO;
import com.comvee.cdms.complication.model.po.ScreeningStatsPO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/4/16
 */
public interface ScreeningStatsMapper {

    /**
     * 统计已筛查的人数（根据类型）
     * @param screeningType
     * @return
     */
    long countScreeningPeople(@Param("screeningType") Integer screeningType ,@Param("doctorId") String doctorId);

    /**
     * 根据条件统计患者情况
     * @param countPatientParam
     * @return
     */
    long countPatient(CountPatientParam countPatientParam);

    /**
     * 筛查统计
     * @param screeningStatsParam
     * @return
     */
    long screeningStats(ScreeningStatsParam screeningStatsParam);

    /**
     * 统计评估结果数量
     * @param assessCode
     * @return
     */
    long countAssessResult(@Param("assessCode") String assessCode,@Param("doctorId") String doctorId);

    /**
     * 分类统计评估结果详情
     * @param assessCode
     * @return
     */
    List<AssessDetailStatsPO> countAssessResultDetail(@Param("assessCode") String assessCode,@Param("doctorId") String doctorId);

    /**
     * 添加筛查统计数据
     * @param screeningStatsPO
     */
    void addScreeningStats(ScreeningStatsPO screeningStatsPO);

    /**
     * 获取筛查统计结果
     * @param idCard
     * @param itemCode
     * @return
     */
    ScreeningStatsPO getScreeningStats(@Param("memberId") String memberId , @Param("doctorId") String doctorId  ,@Param("itemCode") String itemCode);

    /**
     * 分类统计筛查结果详情
     * @param itemCode
     * @return
     */
    List<ScreeningStatsDetailPO> countScreeningStatsDetail(@Param("itemCode") String itemCode , @Param("doctorId") String doctorId );

    /**
     * 统计筛查
     * @param itemCode
     * @param itemValue
     * @return
     */
    long countScreeningStats(@Param("itemCode") String itemCode ,@Param("itemValue") String itemValue , @Param("doctorId") String doctorId );

    /**
     * 修改筛查统计数据
     * @param screeningStatsPO
     */
    void updateScreeningStats(ScreeningStatsPO screeningStatsPO);

    /**
     * 删除患者的筛查统计数据
     * @param doctorId
     * @param memberId
     */
    void deleteScreeningStats(@Param("doctorId") String doctorId ,@Param("memberId") String memberId);

    /**
     * 根据条件统计患者情况-医院
     * @param countPatientParam
     * @return
     */
    long countPatientForHos(GetStatisticsDTO dto);

    /**
     * 统计筛查-医院
     * @param itemCode
     * @param itemValue
     * @return
     */
    long countScreeningStatsForHos(@Param("itemCode") String itemCode ,@Param("itemValue") String itemValue , @Param("dto") GetStatisticsDTO dto );

    /**
     * 分类统计筛查结果详情-医院
     * @param itemCode
     * @return
     */
    List<ScreeningStatsDetailPO> countScreeningStatsDetailForHos(@Param("itemCode") String itemCode , @Param("dto") GetStatisticsDTO dto );

}
