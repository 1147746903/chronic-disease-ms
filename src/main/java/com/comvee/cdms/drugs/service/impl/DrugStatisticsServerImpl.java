package com.comvee.cdms.drugs.service.impl;

import com.comvee.cdms.drugs.mapper.DrugStatisticsMapper;
import com.comvee.cdms.drugs.service.DrugStatisticsServer;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("drugStatisticsServer")
public class DrugStatisticsServerImpl implements DrugStatisticsServer {
    @Autowired
    private DrugStatisticsMapper drugStatisticsMapper;

    @Override
    public Map<String, Object> getPatientStatisticsForDrugClass(GetStatisticsDTO dto) {
        return this.drugStatisticsMapper.getPatientStatisticsForDrugClass(dto);
    }
}
