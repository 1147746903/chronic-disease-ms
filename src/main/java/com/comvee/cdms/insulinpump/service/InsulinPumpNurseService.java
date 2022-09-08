package com.comvee.cdms.insulinpump.service;

import com.comvee.cdms.insulinpump.model.po.InsulinPumpNurseRecordPO;
import com.comvee.cdms.insulinpump.model.po.InsulinPumpObserveRecordPO;

import java.util.List;

public interface InsulinPumpNurseService {

    /**
     * 添加护理记录
     * @param insulinPumpNurseRecordPO
     * @return
     */
    String addInsulinPumpNurseRecord(InsulinPumpNurseRecordPO insulinPumpNurseRecordPO);

    /**
     * 修改护理记录
     * @param insulinPumpNurseRecordPO
     */
    void updateInsulinPumpNurseRecord(InsulinPumpNurseRecordPO insulinPumpNurseRecordPO);

    /**
     * 加载护理记录列表
     * @param memberId
     * @param hospitalNo
     * @return
     */
    List<InsulinPumpNurseRecordPO> listInsulinPumpNurseRecord(String memberId ,String virtualWardId);

    /**
     * 根据主键获取护理记录
     * @param sid
     * @return
     */
    InsulinPumpNurseRecordPO getInsulinPumpNurseRecordById(String sid);


    /**
     * 新增观察记录
     * @param add
     */
    String addInsulinPumpObserveRecord(InsulinPumpObserveRecordPO add);

    /**
     * 修改观察记录
     * @param update
     * @return
     */
    void updateInsulinPumpObserveRecord(InsulinPumpObserveRecordPO update);

    /**
     * 根据主键获取观察记录
     * @param sid
     * @return
     */
    InsulinPumpObserveRecordPO getInsulinPumpObserveRecordById(String sid);

    /**
     * 加载观察记录列表
     * @param memberId
     * @param hospitalNo
     * @return
     */
    List<InsulinPumpObserveRecordPO> listInsulinPumpObserveRecord(String memberId ,String virtualWardId);
}
