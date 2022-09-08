package com.comvee.cdms.complication.service.impl;

import com.comvee.cdms.complication.mapper.ScreeningItemDictMapper;
import com.comvee.cdms.complication.model.po.ScreeningItemDictPO;
import com.comvee.cdms.complication.service.ScreeningItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("screeningItemService")
public class ScreeningItemServiceImpl implements ScreeningItemService {

    @Autowired
    private ScreeningItemDictMapper screeningItemDictMapper;

    @Override
    public List<ScreeningItemDictPO> listHospitalScreeningItemDict(String hospitalId) {
        return this.screeningItemDictMapper.listHospitalScreeningItemDict(hospitalId);
    }
}
