package com.comvee.cdms.qualitycontrol.mapper;

import com.comvee.cdms.qualitycontrol.model.po.QualityControlMaterialConfigPO;
import org.apache.ibatis.annotations.Param;

public interface QualityControlMaterialConfigMapper {

    QualityControlMaterialConfigPO getQualityControlMaterialConfig(@Param("hospitalId") String hospitalId);

    void addOrUpdateQualityControlMaterialConfig(QualityControlMaterialConfigPO obj);
}
