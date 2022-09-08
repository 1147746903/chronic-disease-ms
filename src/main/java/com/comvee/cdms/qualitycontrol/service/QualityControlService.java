package com.comvee.cdms.qualitycontrol.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.qualitycontrol.model.bo.MaterialConfigBO;
import com.comvee.cdms.qualitycontrol.model.param.AddQualityControlParam;
import com.comvee.cdms.qualitycontrol.model.param.ListQualityControlParam;
import com.comvee.cdms.qualitycontrol.model.po.QualityControlLiquidPO;
import com.comvee.cdms.qualitycontrol.model.po.QualityControlTestPaperPO;
import com.comvee.cdms.qualitycontrol.model.vo.QualityControlVO;

import java.util.List;

public interface QualityControlService {

    List<MaterialConfigBO> getMaterialConfig(String hospitalId);

    void updateMaterialConfig(String doctorId ,String hospitalId ,List<MaterialConfigBO> config);

    PageResult<QualityControlTestPaperPO> listTestPaper(PageRequest pr , String hospitalId);

    PageResult<QualityControlTestPaperPO> listTestPaperValid(PageRequest pr , String hospitalId);

    QualityControlTestPaperPO getTestPaper(String id);

    void updateTestPaper(QualityControlTestPaperPO update);

    void deleteTestPaper(String id);

    String addTestPaper(QualityControlTestPaperPO add);

    PageResult<QualityControlLiquidPO> listLiquid(PageRequest pr , String hospitalId);

    PageResult<QualityControlLiquidPO> listLiquidValid(PageRequest pr , String hospitalId);

    String addLiquid(QualityControlLiquidPO add);

    void updateLiquid(QualityControlLiquidPO update);

    void deleteLiquid(String id);

    QualityControlLiquidPO getLiquid(String id);

    PageResult<QualityControlVO> listQualityControl(PageRequest pr , ListQualityControlParam param);

    void unlockMachine(String machineId);

    int addQualityControl(AddQualityControlParam param);
}
