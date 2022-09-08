package com.comvee.cdms.drugs.service;

import com.comvee.cdms.statistics.dto.GetStatisticsDTO;

import java.util.Map;

public interface DrugStatisticsServer {
    /**
     * 患者用药情况统计数据源
     * @param dto
     * @return
     */
    Map<String,Object> getPatientStatisticsForDrugClass(GetStatisticsDTO dto);
}
