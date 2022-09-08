package com.comvee.cdms.qualitycontrol.mapper;

import com.comvee.cdms.qualitycontrol.model.po.QualityControlTestPaperPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QualityControlTestPaperMapper {

    void addTestPaper(QualityControlTestPaperPO add);

    int updateTestPaper(QualityControlTestPaperPO update);

    QualityControlTestPaperPO getTestPaperById(@Param("sid") String sid ,@Param("hospitalId")String hospitalId ,@Param("batchNo")String batchNo);

    List<QualityControlTestPaperPO> listTestPaper(@Param("hospitalId") String hospitalId);

    List<QualityControlTestPaperPO> listTestPaperValid(@Param("hospitalId") String hospitalId);


}
