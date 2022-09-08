package com.comvee.cdms.prescription.service;

import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.vo.PrescriptionForSportVO;

public interface PrescriptionOfSportServiceI {

    /**
     * 根据基础信息-智能推荐运动
     * @param baseJson
     * @return
     */
	PrescriptionForSportVO intelligentRecommendationOfSport(String baseJson,Integer eohType);

    /**
     * 根据管理处方编号获取饮食方案
     * @param prescriptionId
     * @return
     */
	PrescriptionForSportVO getPrescriptionSport(String prescriptionId,Integer eohType);
    
    /**
     * 修改管理处方 饮食方案
     * @param prescriptionId
     * @return
     */
    void modifySport(PrescriptionDTO prescriptionDTO);
    
}
