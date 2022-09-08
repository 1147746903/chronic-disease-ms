package com.comvee.cdms.complication.service;

import com.comvee.cdms.complication.model.po.ScreeningItemDictPO;

import java.util.List;

public interface ScreeningItemService {

    /**
     * 加载医院的筛查项目
     * @param hospitalId
     * @return
     */
    List<ScreeningItemDictPO> listHospitalScreeningItemDict(String hospitalId);
}
