package com.comvee.cdms.qualitycontrol.mapper;

import com.comvee.cdms.qualitycontrol.model.po.QualityControlLiquidPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QualityControlLiquidMapper {

    void addQualityControlLiquid(QualityControlLiquidPO add);

    int updateQualityControlLiquid(QualityControlLiquidPO update);

    QualityControlLiquidPO getQualityControlLiquidById(@Param("sid") String sid ,@Param("hospitalId") String hospitalId ,@Param("batchNo") String batchNo);

    List<QualityControlLiquidPO> listQualityControlLiquid(@Param("hospitalId") String hospitalId);

    List<QualityControlLiquidPO> listQualityControlLiquidValid(@Param("hospitalId") String hospitalId);
}
