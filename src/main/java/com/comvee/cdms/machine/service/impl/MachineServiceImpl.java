package com.comvee.cdms.machine.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.machine.mapper.MachineMapper;
import com.comvee.cdms.machine.po.MachinePO;
import com.comvee.cdms.machine.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("machineService")
public class MachineServiceImpl implements MachineService {

    @Autowired
    private MachineMapper machineMapper;

    @Override
    public void updateMachine(MachinePO update) {
        MachinePO get = new MachinePO();
        get.setMachineId(update.getMachineId());
        MachinePO getResult = this.machineMapper.getMachine(get);
        if(getResult == null){
            throw new BusinessException("无法通过id查找到匹配的设备，请确认");
        }
        this.machineMapper.updateMachineModel(update);
    }

    @Override
    public MachinePO getMachine(String machineId, String machineSn, Integer machineType) {
        MachinePO get = new MachinePO();
        get.setMachineId(machineId);
        get.setMachineSn(machineSn);
        get.setMachineType(machineType);
        return this.machineMapper.getMachine(get);
    }

    @Override
    public String addMachine(String machineSn, Integer machineType) {
        MachinePO get = new MachinePO();
        get.setMachineSn(machineSn);
        get.setMachineType(machineType);
        MachinePO getResult = this.machineMapper.getMachine(get);
        if(getResult != null){
            return getResult.getMachineId();
        }
        String machineId = DaoHelper.getSeq();
        MachinePO add = new MachinePO();
        add.setMachineType(machineType);
        add.setMachineSn(machineSn);
        add.setMachineId(machineId);
        this.machineMapper.addMachineModel(add);
        return machineId;
    }
}
