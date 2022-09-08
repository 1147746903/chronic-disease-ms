package com.comvee.cdms.complication.mapper;

import com.comvee.cdms.complication.model.dto.GetLastScreeningReportDTO;
import com.comvee.cdms.complication.model.dto.ListMemberScreeningReportDTO;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.statistics.dto.GetScreeningReportDTO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
public interface ScreeningReportMapper {
    /**
     * 新增筛查报告
     * @param screeningReportPO
     */
    void addScreeningReport(ScreeningReportPO screeningReportPO);

    /**
     * 修改筛查报告
     * @param screeningReportPO
     */
    void updateScreeningReport(ScreeningReportPO screeningReportPO);

    /**
     * 根据id获取筛查报告
     * @param reportId
     */
    ScreeningReportPO getScreeningReportById(@Param("reportId") String reportId ,@Param("screeningId") String screeningId
            ,@Param("screeningType") Integer screeningType ,@Param("valid") Integer valid);

    /**
     * 加载筛查单下的所有报告
     * @param screeningId
     * @return
     */
    List<ScreeningReportPO> listScreeningReport(@Param("screeningId") String screeningId);

    /**
     * 加载患者的筛查报告列表
     * @param memberId
     * @return
     */
    List<ScreeningReportPO> listMemberScreeningReport(ListMemberScreeningReportDTO dto);

    List<ScreeningReportPO> listMemberScreeningReportByHospitalId(ListMemberScreeningReportDTO dto);

    /**
     * 获取最后一次评估报告
     * @param idCard
     * @param screeningType
     * @return
     */
    ScreeningReportPO getLastScreeningReport(GetLastScreeningReportDTO param);

    /**
     * 患者近六个月各种类型最新的并发症筛查报告
     * @param memberId
     * @return
     */
    List<ScreeningReportPO> listScreeningReportOfTypeNearlySixMonth(@Param("memberId") String memberId, @Param("hospitalId") String hospitalId, @Param("startDt") String startDt);

    /**
     * 糖化血红蛋白分布数据源-医院
     * @param dto
     * @return
     */
    List<ScreeningReportPO> listIntelligentResultOfThStatistics(GetStatisticsDTO dto);

    /**
     * 并发症患者数统计
     * @param dto
     * @return
     */
    Long getScreeningOfMemberCount(GetStatisticsDTO dto);

    Long getScreeningOfMemberCountByHospitalId(GetScreeningReportDTO getScreeningReportDTO);

}
