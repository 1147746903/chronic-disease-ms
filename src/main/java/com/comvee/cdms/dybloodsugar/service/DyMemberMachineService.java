package com.comvee.cdms.dybloodsugar.service;

import com.comvee.cdms.dybloodsugar.dto.MachineInfoDTO;

public interface DyMemberMachineService {

    int insertOrUpdateMachineInfo(MachineInfoDTO machineInfoDTO);
}
