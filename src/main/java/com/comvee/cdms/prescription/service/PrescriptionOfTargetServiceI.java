package com.comvee.cdms.prescription.service;

import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.vo.PrescriptionDetailVO;
import com.comvee.cdms.prescription.vo.PrescriptionTargetVO;

/**
 * @author 李左河
 * @date 2018/8/2 14:59.
 */
public interface PrescriptionOfTargetServiceI {
    /**
     * 获取控制目标
     * @author 李左河
     * @date 2018/8/2 15:02
     * @param prescriptionDTO
     * @return com.comvee.cdms.prescription.model.PrescriptionDetailModel<com.comvee.cdms.prescription.vo.PrescriptionTargetVO>
     *
     */
    PrescriptionDetailVO<PrescriptionTargetVO> loadPrescriptionTarget(PrescriptionDTO prescriptionDTO);

    /**
     * 修改控制目标
     * @author 李左河
     * @date 2018/8/7 9:32
     * @param prescriptionDTO
     * @return void
     *
     */
    void modifyPrescriptionTarget(PrescriptionDTO prescriptionDTO);

    /**
     * 控制目标推荐
     * @param baseJson
     * @return
     */
    PrescriptionTargetVO RecommendationOfTarget(String baseJson,String memberId,String doctorId);
}
