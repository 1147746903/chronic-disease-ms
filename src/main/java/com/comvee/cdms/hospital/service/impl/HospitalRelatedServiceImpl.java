package com.comvee.cdms.hospital.service.impl;

import com.comvee.cdms.hospital.mapper.HospitalRelatedMapper;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.service.HospitalRelatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wyc
 * @date 2019/12/23 17:18
 */
@Service("hospitalRelatedService")
public class HospitalRelatedServiceImpl implements HospitalRelatedService {

    @Autowired
    private HospitalRelatedMapper hospitalRelatedMapper;

    @Override
    public List<HospitalPO> listRelatedHospital(Integer queryType, String hospitalId) {
        return this.hospitalRelatedMapper.listRelatedHospital(queryType,hospitalId);
    }
}
