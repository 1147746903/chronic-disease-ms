package com.comvee.cdms.complication.service;

import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
public interface ScreeningSyncService {

    /**
     * 同步分组
     * @param doctorId
     * @param groupListJson
     */
    void syncGroup(String doctorId ,String groupListJson);

    /**
     * 同步患者
     * @param doctorId
     * @param patientListJson
     */
    void syncPatient(String doctorId ,String patientListJson);

    /**
     * 同步筛查记录
     * @param doctorId
     * @param screeningListJson
     */
    void syncScreeningList(String doctorId ,String screeningListJson);

    /**
     * 同步筛查报告内容
     * @param reportJson
     * @param doctorId
     */
    void syncScreeningReport(String reportJson ,String doctorId);

    /**
     * 同步筛查报告内容及文件
     * @param reportJson
     * @param doctorId
     * @return
     */
    String syncScreeningReportWithFile(String reportJson ,String doctorId , Map<String ,String> uploadMap);

    /**
     * 同步筛查数据
     * @param screeningDataJson
     * @param doctorId
     */
    void syncScreeningData(String screeningDataJson ,String doctorId);

    /**
     * 同步足部处方评估结果
     * @param resultList
     * @param doctorId
     */
    void syncFootPrescriptionResult(String resultList ,String doctorId);

    /**
     * 同步筛查系统统计数据
     * @param jsonList
     * @param doctorId
     */
    void syncScreeningStats(String jsonList ,String doctorId);
}
