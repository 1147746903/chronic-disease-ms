package com.comvee.cdms.doctor.service;

import com.comvee.cdms.doctor.dto.AddSettingDTO;
import com.comvee.cdms.doctor.po.DoctorServiceSettingPO;

public interface DoctorServiceSettingService {

    /**
     * 保存高危血糖提醒设置
     * @param addSettingDTO
     * @return
     */
    void addServiceSetting(AddSettingDTO addSettingDTO);

    /**
     * 获取高危血糖提醒设置
     * @param addSettingDTO
     * @return
     */
    DoctorServiceSettingPO getServiceSetting(AddSettingDTO addSettingDTO);
}
