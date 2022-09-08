package com.comvee.cdms.insulinpump.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.insulinpump.mapper.InsulinPumpNurseRecordMapper;
import com.comvee.cdms.insulinpump.mapper.InsulinPumpObserveRecordMapper;
import com.comvee.cdms.insulinpump.model.po.InsulinPumpNurseRecordPO;
import com.comvee.cdms.insulinpump.model.po.InsulinPumpObserveRecordPO;
import com.comvee.cdms.insulinpump.service.InsulinPumpNurseService;
import com.comvee.cdms.virtualward.mapper.VirtualWardMapper;
import com.comvee.cdms.virtualward.model.param.GetVirtualWardParam;
import com.comvee.cdms.virtualward.model.po.VirtualWardPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("insulinPumpNurseService")
public class InsulinPumpNurseServiceImpl implements InsulinPumpNurseService {

    @Autowired
    private InsulinPumpNurseRecordMapper insulinPumpNurseRecordMapper;

    @Autowired
    private InsulinPumpObserveRecordMapper insulinPumpObserveRecordMapper;

    @Autowired
    private VirtualWardMapper virtualWardMapper;

    @Override
    public String addInsulinPumpNurseRecord(InsulinPumpNurseRecordPO insulinPumpNurseRecordPO) {
        String sid = DaoHelper.getSeq();
        insulinPumpNurseRecordPO.setSid(sid);
        if(StringUtils.isBlank(insulinPumpNurseRecordPO.getPumpDownDt())){
            insulinPumpNurseRecordPO.setPumpDownDt(null);
        }
        this.insulinPumpNurseRecordMapper.addInsulinPumpNurseRecord(insulinPumpNurseRecordPO);
        useInsulinPumpDateHandler(insulinPumpNurseRecordPO.getVirtualWardId() ,insulinPumpNurseRecordPO.getPumpUpDt());
        return sid;
    }

    @Override
    public void updateInsulinPumpNurseRecord(InsulinPumpNurseRecordPO insulinPumpNurseRecordPO) {
        InsulinPumpNurseRecordPO old = getInsulinPumpNurseRecordById(insulinPumpNurseRecordPO.getSid());
        if(old == null){
            throw new BusinessException("护理记录不存在，请确认");
        }
        this.insulinPumpNurseRecordMapper.updateInsulinPumpNurseRecord(insulinPumpNurseRecordPO);
        useInsulinPumpDateHandler(old.getVirtualWardId() ,insulinPumpNurseRecordPO.getPumpUpDt());
    }

    @Override
    public List<InsulinPumpNurseRecordPO> listInsulinPumpNurseRecord(String memberId, String virtualWardId) {
        return this.insulinPumpNurseRecordMapper.listInsulinPumpNurseRecord(memberId ,virtualWardId);
    }

    @Override
    public InsulinPumpNurseRecordPO getInsulinPumpNurseRecordById(String sid) {
        return this.insulinPumpNurseRecordMapper.getInsulinPumpNurseRecordById(sid);
    }

    @Override
    public String addInsulinPumpObserveRecord(InsulinPumpObserveRecordPO add) {
        String sid = DaoHelper.getSeq();
        add.setSid(sid);
        this.insulinPumpObserveRecordMapper.addInsulinPumpObserveRecord(add);
        return sid;
    }

    @Override
    public void updateInsulinPumpObserveRecord(InsulinPumpObserveRecordPO update) {
        this.insulinPumpObserveRecordMapper.updateInsulinPumpObserveRecord(update);
    }

    @Override
    public InsulinPumpObserveRecordPO getInsulinPumpObserveRecordById(String sid) {
        return this.insulinPumpObserveRecordMapper.getInsulinPumpObserveRecordById(sid);
    }

    @Override
    public List<InsulinPumpObserveRecordPO> listInsulinPumpObserveRecord(String memberId, String virtualWardId) {
        return this.insulinPumpObserveRecordMapper.listInsulinPumpObserveRecord(memberId ,virtualWardId);
    }

    /**
     * 第一次上泵时间处理
     * @param virtualWardId
     * @param pumpUpDt
     */
    private void useInsulinPumpDateHandler(String virtualWardId ,String pumpUpDt){
        if(StringUtils.isBlank(pumpUpDt)){
            return;
        }
        GetVirtualWardParam get = new GetVirtualWardParam();
        get.setId(virtualWardId);
        VirtualWardPO virtualWardPO = this.virtualWardMapper.getVirtualWard(get);
        if(virtualWardPO == null){
            return;
        }
        String useInsulinPumpDate = virtualWardPO.getUseInsulinPumpDate();
        if(StringUtils.isBlank(useInsulinPumpDate) || pumpUpDt.compareTo(useInsulinPumpDate) < 0){
            VirtualWardPO update = new VirtualWardPO();
            update.setSid(get.getId());
            update.setUseInsulinPumpDate(pumpUpDt);
            this.virtualWardMapper.updateVirtualWard(update);
        }

    }
}
