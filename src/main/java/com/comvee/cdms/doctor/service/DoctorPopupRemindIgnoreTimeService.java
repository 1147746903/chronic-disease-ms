package com.comvee.cdms.doctor.service;

import com.comvee.cdms.doctor.po.DoctorPopupRemindIgnoreTimePO;

public interface DoctorPopupRemindIgnoreTimeService {

    DoctorPopupRemindIgnoreTimePO getDoctorPopupRemindIgnoreTime(String doctorId ,Integer remindType);

    void addOrUpdateDoctorPopupRemindIgnoreTime(String doctorId ,Integer remindType ,String ignoreDt);
}
