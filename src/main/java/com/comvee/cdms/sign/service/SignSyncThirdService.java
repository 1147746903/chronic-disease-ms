package com.comvee.cdms.sign.service;

import com.comvee.cdms.sign.dto.AddBloodPressureMapperDTO;
import com.comvee.cdms.sign.dto.AddBloodSugarMapperDTO;

/**
 * @author: suyz
 * @date: 2019/4/12
 */
public interface SignSyncThirdService {

    /**
     * 同步血糖
     * @param addBloodSugarMapperDTO
     */
    void syncBloodSugar(AddBloodSugarMapperDTO addBloodSugarMapperDTO);

    /**
     * 同步血压
     */
    void syncBloodPressure(AddBloodPressureMapperDTO addBloodPressureMapperDTO);
}
