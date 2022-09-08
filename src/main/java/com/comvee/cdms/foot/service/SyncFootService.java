package com.comvee.cdms.foot.service;

import com.comvee.cdms.foot.bo.FootPrescriptionQrCodeBO;

/**
 * @author: suyz
 * @date: 2019/4/23
 */
public interface SyncFootService {

    /**
     * 同步足部处方
     * @param doctorId
     * @param prescriptionFootJson
     * @return
     */
    String syncFootPrescription(String doctorId ,String prescriptionFootJson);

    /**
     * 生成足部处方报告二维码
     * @param prescriptId
     * @return
     */
    FootPrescriptionQrCodeBO createFootPrescriptionQrCode(String prescriptId);

    /**
     * 同步足部处方报告关联
     * @param footRelateJson
     * @return
     */
    String syncFootReportRelate(String footRelateJson);
}
