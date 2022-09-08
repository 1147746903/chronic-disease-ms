package com.comvee.cdms.prescription.service;

import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.vo.PrescriptionArchivesVO;
import com.comvee.cdms.prescription.vo.PrescriptionDetailVO;

/**
 * @author 李左河
 * @date 2018/8/20 14:49.
 */
public interface PrescriptionOfArchivesI {
    /**
     * 获取管理处方 患者档案
     * @author 李左河
     * @date 2018/8/20 14:52
     * @param prescriptionDTO
     * @return com.comvee.cdms.prescription.vo.PrescriptionDetailVO<com.comvee.cdms.prescription.vo.PrescriptionArchivesVO>
     *
     */
    PrescriptionDetailVO<PrescriptionArchivesVO> getPrescriptionArchives(PrescriptionDTO prescriptionDTO);

    /**
     * 修改管理处方 患者档案
     * @author 李左河
     * @date 2018/8/21 9:37
     * @param prescriptionDTO
     * @return void
     *
     */
    void modifyPrescriptionArchives(PrescriptionDTO prescriptionDTO);
}
