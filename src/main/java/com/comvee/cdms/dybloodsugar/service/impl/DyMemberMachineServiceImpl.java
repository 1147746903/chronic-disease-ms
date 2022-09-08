package com.comvee.cdms.dybloodsugar.service.impl;

import com.comvee.cdms.dybloodsugar.dto.MachineInfoDTO;
import com.comvee.cdms.dybloodsugar.service.DyMemberMachineService;
import org.springframework.stereotype.Service;

@Service("dyMemberMachineService")
public class DyMemberMachineServiceImpl implements DyMemberMachineService {
    @Override
    public int insertOrUpdateMachineInfo(MachineInfoDTO machineInfoDTO) {
        return 0;
    }
}
