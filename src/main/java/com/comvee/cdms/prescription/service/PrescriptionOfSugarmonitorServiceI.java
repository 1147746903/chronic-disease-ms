package com.comvee.cdms.prescription.service;

import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.vo.PrescriptionForSugarmonitorVO;

public interface PrescriptionOfSugarmonitorServiceI {

    /**
     * 根据管理处方编号获取饮食方案
     * @param prescriptionId
     * @return
     */
    PrescriptionForSugarmonitorVO getPrescriptionSugarmonitor(String prescriptionId);

    /**
     * 根据基础信息-智能推荐饮食
     * @param baseJson
     * @return
     */
    PrescriptionForSugarmonitorVO intelligentRecommendationOfSugarmonitor(String baseJson);

    /**
     * 修改管理处方 饮食方案
     * @param prescriptionDTO
     * @return
     */
    void modifySugarmonitor(PrescriptionDTO prescriptionDTO);

}
