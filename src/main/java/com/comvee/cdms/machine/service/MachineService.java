package com.comvee.cdms.machine.service;

import com.comvee.cdms.machine.po.MachinePO;

public interface MachineService {

    void updateMachine(MachinePO update);

    MachinePO getMachine(String machineId ,String machineSn ,Integer machineType);

    String addMachine(String machineSn ,Integer machineType);
}
