package com.comvee.cdms.sign.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.sign.po.SugarMonthReportPO;

import java.util.List;

public interface SugarMonthReportService {


    /**
     * 新增月度控糖报告
     * @param sugarMonthReportPO
     */
    String addSugarMonthReport(SugarMonthReportPO sugarMonthReportPO);

    /**
     * 根据id获取月度控糖报告
     * @param reportId
     * @return
     */
    SugarMonthReportPO getSugarMonthReportById(String reportId);

    /**
     * 加载控糖报告列表
     * @param memberId
     * @return
     */
    PageResult<SugarMonthReportPO> listSugarMonthReport(String memberId , PageRequest pr);

    /**
     * 根据id获取月度控糖报告 (如果是未完善的报告则完善)
     * @param reportId
     * @return
     */
    SugarMonthReportPO getSugarMonthReportByIdAndCompleteReport(String reportId);

    /**
     * 获取患者某个月份的报告
     * @param memberId
     * @param month
     * @return
     */
    SugarMonthReportPO getSugarMonthReportByMemberId(String memberId ,String month);

    /**
     * 加载需要推送的月度控糖报告
     * @param page
     * @param rows
     * @return
     */
    List<SugarMonthReportPO> listPushSugarMonthReport(int limit);

    /**
     * 批量更新月度控糖报告推送状态
     * @param idList
     */
    void batchUpdateSugarMonthReportPushStatus(List<String> idList);
}
