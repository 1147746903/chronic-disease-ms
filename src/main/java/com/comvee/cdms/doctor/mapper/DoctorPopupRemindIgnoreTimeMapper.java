package com.comvee.cdms.doctor.mapper;

import com.comvee.cdms.doctor.po.DoctorPopupRemindIgnoreTimePO;
import org.apache.ibatis.annotations.Param;

public interface DoctorPopupRemindIgnoreTimeMapper {

    void addDoctorPopupRemindIgnoreTime(DoctorPopupRemindIgnoreTimePO add);

    void updateDoctorPopupRemindIgnoreTime(DoctorPopupRemindIgnoreTimePO update);

    DoctorPopupRemindIgnoreTimePO getDoctorPopupRemindIgnoreTime(@Param("doctorId") String doctorId ,@Param("remindType") Integer remindType);

}
