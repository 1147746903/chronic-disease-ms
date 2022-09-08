package com.comvee.cdms.doctor.service.impl;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.doctor.dto.AddSettingDTO;
import com.comvee.cdms.doctor.mapper.DoctorServiceSettingMapper;
import com.comvee.cdms.doctor.po.DoctorServiceSettingPO;
import com.comvee.cdms.doctor.service.DoctorServiceSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DoctorServiceSettingService")
public class DoctorServiceSettingImpl implements DoctorServiceSettingService {

    @Autowired
    private DoctorServiceSettingMapper doctorServiceSettingMapper;

    @Override
    public void addServiceSetting(AddSettingDTO addSettingDTO) {
        DoctorServiceSettingPO po = this.doctorServiceSettingMapper.getServiceSetting(addSettingDTO.getDoctorId());
        DoctorServiceSettingPO settingPO = new DoctorServiceSettingPO();
        if (po ==null || "".equals(po)){
            BeanUtils.copyProperties(settingPO,addSettingDTO);
            settingPO.setSid(DaoHelper.getSeq());
            this.doctorServiceSettingMapper.addServiceSetting(settingPO);
        }else{
            settingPO = new DoctorServiceSettingPO();
            BeanUtils.copyProperties(settingPO,addSettingDTO);
            settingPO.setDoctorId(po.getDoctorId());
            this.doctorServiceSettingMapper.updateServiceSetting(settingPO);
        }
    }

    @Override
    public DoctorServiceSettingPO getServiceSetting(AddSettingDTO addSettingDTO) {
        DoctorServiceSettingPO po = this.doctorServiceSettingMapper.getServiceSetting(addSettingDTO.getDoctorId());
        if (po == null || "".equals(po)){
            po = new DoctorServiceSettingPO();
            po.setIsRemind(1);
            po.setBloodSugarMax( 16.7);
            po.setBloodSugarMin( 3.9);
        }
        return po;
    }
}
