package com.comvee.cdms.qualitycontrol.mapper;

import com.comvee.cdms.qualitycontrol.model.param.ListQualityControlParam;
import com.comvee.cdms.qualitycontrol.model.po.QualityControlPO;
import com.comvee.cdms.qualitycontrol.model.vo.QualityControlVO;

import java.util.List;

public interface QualityControlMapper {

    void addQualityControl(QualityControlPO add);

    List<QualityControlVO> listQualityControl(ListQualityControlParam param);
}
