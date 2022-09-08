package com.comvee.cdms.sign.mapper;

import com.comvee.cdms.sign.po.SugarMonthReportPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SugarMonthReportMapper {

    /**
     * 新增月度控糖报告
     * @param sugarMonthReportPO
     */
    void addSugarMonthReport(SugarMonthReportPO sugarMonthReportPO);

    /**
     * 修改月度控糖报告
     * @param sugarMonthReportPO
     */
    void updateSugarMonthReport(SugarMonthReportPO sugarMonthReportPO);

    /**
     * 根据id获取月度控糖报告
     * @param reportId
     * @return
     */
    SugarMonthReportPO getSugarMonthReportById(@Param("reportId") String reportId);

    /**
     * 加载控糖报告列表
     * @param memberId
     * @return
     */
    List<SugarMonthReportPO> listSugarMonthReport(@Param("memberId") String memberId);

    /**
     * 获取患者某个月份的报告
     * @param memberId
     * @param month
     * @return
     */
    SugarMonthReportPO getSugarMonthReportByMemberId(@Param("memberId") String memberId ,@Param("month") String month);


    /**
     * 加载需要推送的月度控糖报告
     * @param page
     * @param rows
     * @return
     */
    List<SugarMonthReportPO> listPushSugarMonthReport(@Param("limit") int limit ,@Param("date") String date);

    /**
     * 批量更新月度控糖报告推送状态
     * @param idList
     */
    void batchUpdateSugarMonthReportPushStatus(@Param("idList") List<String> idList);

}
