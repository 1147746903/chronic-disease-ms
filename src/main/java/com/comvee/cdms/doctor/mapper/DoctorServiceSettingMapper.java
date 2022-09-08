package com.comvee.cdms.doctor.mapper;

import com.comvee.cdms.doctor.po.DoctorServiceSettingPO;
import org.apache.ibatis.annotations.Param;

public interface DoctorServiceSettingMapper {

    void addServiceSetting(DoctorServiceSettingPO settingPO);

    void updateServiceSetting(DoctorServiceSettingPO settingPO);

    DoctorServiceSettingPO getServiceSetting(@Param("doctorId") String doctorId);
}
