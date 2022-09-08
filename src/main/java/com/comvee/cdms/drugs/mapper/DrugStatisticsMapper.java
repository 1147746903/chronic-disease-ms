package com.comvee.cdms.drugs.mapper;

import com.comvee.cdms.statistics.dto.GetStatisticsDTO;

import java.util.Map;

public interface DrugStatisticsMapper {
    /**
     * 药品统计数据源
     * @param dto
     * @return
     */
    Map<String,Object> getPatientStatisticsForDrugClass(GetStatisticsDTO dto);
}
