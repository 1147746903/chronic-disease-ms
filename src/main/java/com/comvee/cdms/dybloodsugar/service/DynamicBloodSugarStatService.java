package com.comvee.cdms.dybloodsugar.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodsugar.dto.DyReportDTO;
import com.comvee.cdms.dybloodsugar.dto.DyStaticsDTO;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.po.DyBloodSugarReportPO;
import com.comvee.cdms.dybloodsugar.vo.*;

import java.util.List;
import java.util.Map;

public interface DynamicBloodSugarStatService {

    /**
     * 获取日趋势图结果
     * @param dto
     * @return
     */
    DynamicBloodSugarDailyTrendVO getDynamicBloodSugarDailyTrend(DyStaticsDTO dto);

    /**
     * 每日血糖总结结果
     * @param dto
     * @return
     */
    DynamicBloodSugarDailySummaryListVO listDynamicBloodSugarDailySummary(DyStaticsDTO dto);

    /**
     * 动态结果
     * @param dto
     * @return
     */
    DynamicBloodSugarDynamicVO getDynamicBloodSugarDynamic(DyStaticsDTO dto);

    /**
     * 日均血糖平均绝对差
     * @param dto
     * @return
     */
    DynamicBloodSugarMeanAbsoluteDeviationVO getDynamicBloodSugarMeanAbsoluteDeviation(DyStaticsDTO dto);

    /**
     * 删除统计建议
     * @param sid
     */
    void deleteStatAdvice(String sid);

    /**
     * 获取日趋势图结果 v2
     * @param dto
     * @return
     * @since v6.3.0-->7.2.0
     */
    DynamicBloodSugarDailyTrendV2VO getDynamicBloodSugarDailyTrendV2(DyStaticsDTO dto);

    /**
     * 每日血糖总结结果 v2
     * @param dto
     * @return
     * @since v6.3.0
     */
    DynamicBloodSugarDailySummaryListV2VO listDynamicBloodSugarDailySummaryV2(DyStaticsDTO dto);

    /**
     * 获取动态血糖基础指标结果
     * @param list
     * @return
     */
    DynamicBloodSugarIndexBaseVO getDynamicBloodSugarIndexBase(List<DYYPBloodSugarPO> list , DynamicBloodSugarSettingsVO settings);

    /**
     * 转换插值数据
     * @param dto
     */
    void getExportDynamicBloodSugarDailyData(DyStaticsDTO dto);

    Map<String, Map<String, Double>> getDynamicBloodSugarValues(DyStaticsDTO dto);

    /**
     * 血糖分析
     *
     * @param dto
     */
    String analysisDyBloodSugar(DyReportDTO dto);

    /**
     * 获取血糖报告
     *
     * @param dto
     * @return 报告列表
     */
    PageResult<DyBloodSugarReportPO> getBloodSugarReportList(DyReportDTO dto, PageRequest pager);

    /**
     * 获取血糖报告
     *
     * @param dto
     * @return 报告列表
     */
    PageResult<DyBloodSugarReportPO> getBloodSugarReportListByMember(DyReportDTO dto, PageRequest pager);


    /**
     * 获取血糖报告
     *
     * @param id
     * @return 报告列表
     */
    String getBloodSugarReportById(String id);


    /**
     * 设置报告内容
     * @param id
     * @param details
     */
    void setBloodSugarReportById(String id, String details);



    /**
     * 获取微信端封面
     *
     * @param reportId
     * @param memberId
     * @return
     */
    JSONObject getBloodSugarReportCover(String reportId, String memberId);

    List<JSONObject>  analysisDailyDyBloodSugar(int dateType, DyReportDTO dto, Map<Integer, String> eatTime);

    Double getMaxEatFlu(String sensorNo, String date);
}
