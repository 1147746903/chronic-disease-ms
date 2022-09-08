package com.comvee.cdms.dybloodsugar.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.dybloodsugar.dto.AddDYYPStatisticsAdviceDTO;
import com.comvee.cdms.dybloodsugar.dto.DyStaticsDTO;
import com.comvee.cdms.dybloodsugar.po.DYYPStatisticsPOWithBLOBs;
import com.comvee.cdms.dybloodsugar.vo.StatisticsYPParamLogOfGAPPVO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;

import java.util.List;

public interface DyStaticsService {

    /**
     * 统计报告处理
     * @param type
     * @param paramOfRecordDts
     * @param sensorNo
     */
    void statisticsOfYPGAPPHandle(Integer type, List<String> paramOfRecordDts, String sensorNo);

    /**
     * 计算患者雅培瞬感统计数据并保存
     * @param diffDay
     * @param type
     * @param endDt
     * @param sensorNo
     */
    StatisticsYPParamLogOfGAPPVO handleOfStatisticsYPParamLogWithLock(Integer diffDay, Integer type, String endDt, String sensorNo);

    /**
     * 获取统计报告
     * @param endDt
     * @param diffDay
     * @param type
     * @param sensorNo
     * @return
     */
    DYYPStatisticsPOWithBLOBs getStatisticsYPParamLogOfGAPP(String endDt, Integer diffDay, Integer type, String sensorNo);

    /**
     * 修改统计报告
     * @param statisticsYPParamLog
     */
    void modifyDayStatistics(DYYPStatisticsPOWithBLOBs statisticsYPParamLog);

    /**
     * 添加统计报告
     * @param statisticsYPParamLog
     */
    void addDayStatistics(DYYPStatisticsPOWithBLOBs statisticsYPParamLog);

    /**
     * 获取探头的血糖统计图表-app&wechat
     * @param dto
     * @return
     */
    JSONObject loadSensorBloodSugarChart(DyStaticsDTO dto);

    /**
     * 获取探头的血糖统计图表-web
     * @param dto
     * @return
     */
    JSONObject loadSensorBloodSugarChartOfWeb(DyStaticsDTO dto);

    /**
     * （针对血糖报告）添加医生建议
     * @param dto
     */
    void addDYYPStatisticsAdvice(AddDYYPStatisticsAdviceDTO dto);

    /**
     * 同步更新探头开始时间
     * @param sensorNo
     */
    void updateSensorMonitorTimes(String sensorNo);

    /**
     * 动态血糖佩戴人数统计
     * @param gto
     * @return
     */
    long hasDynamicBloodSugarSensorStatistics(GetStatisticsDTO gto);
}
