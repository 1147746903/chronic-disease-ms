package com.comvee.cdms.complication.mapper;

import com.comvee.cdms.complication.model.po.ScreeningItemDictPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScreeningItemDictMapper {

    /**
     * 加载医院的筛查项目
     * @param hospitalId
     * @return
     */
    List<ScreeningItemDictPO> listHospitalScreeningItemDict(@Param("hospitalId") String hospitalId);
}
