package com.comvee.cdms.doctor.service.impl;

import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.doctor.mapper.DoctorPopupRemindIgnoreTimeMapper;
import com.comvee.cdms.doctor.po.DoctorPopupRemindIgnoreTimePO;
import com.comvee.cdms.doctor.service.DoctorPopupRemindIgnoreTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctorPopupRemindIgnoreTimeServiceImpl implements DoctorPopupRemindIgnoreTimeService {

    @Autowired
    private DoctorPopupRemindIgnoreTimeMapper doctorPopupRemindIgnoreTimeMapper;

    @Override
    public DoctorPopupRemindIgnoreTimePO getDoctorPopupRemindIgnoreTime(String doctorId, Integer remindType) {
        return this.doctorPopupRemindIgnoreTimeMapper.getDoctorPopupRemindIgnoreTime(doctorId ,remindType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class ,isolation = Isolation.READ_UNCOMMITTED)
    public void addOrUpdateDoctorPopupRemindIgnoreTime(String doctorId, Integer remindType, String ignoreDt) {
        boolean addFlag = false;
        DoctorPopupRemindIgnoreTimePO getResult = getDoctorPopupRemindIgnoreTime(doctorId ,remindType);
        if(getResult == null){
            synchronized (this){
                getResult = getDoctorPopupRemindIgnoreTime(doctorId ,remindType);
                if(getResult == null){
                    addFlag = true;
                    getResult = new DoctorPopupRemindIgnoreTimePO();
                    getResult.setSid(DaoHelper.getSeq());
                    getResult.setDoctorId(doctorId);
                    getResult.setRemindType(remindType);
                    getResult.setIgnoreDt(ignoreDt);
                    this.doctorPopupRemindIgnoreTimeMapper.addDoctorPopupRemindIgnoreTime(getResult);
                }
            }
        }
        if(!addFlag){
            DoctorPopupRemindIgnoreTimePO update = new DoctorPopupRemindIgnoreTimePO();
            update.setSid(getResult.getSid());
            update.setIgnoreDt(ignoreDt);
            this.doctorPopupRemindIgnoreTimeMapper.updateDoctorPopupRemindIgnoreTime(update);
        }
    }
}
