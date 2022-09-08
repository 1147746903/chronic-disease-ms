package com.comvee.cdms.machine.service;

import com.comvee.cdms.machine.mapper.ThpBedMachineMapper;
import com.comvee.cdms.machine.mapper.ThpMemberMachineMapper;
import com.comvee.cdms.machine.model.ThpMemberMachine;
import com.comvee.cdms.machine.model.ThpMemberMachineExample;
import com.comvee.cdms.machine.po.BedMachinePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberMachineService {

	
	@Autowired
    private ThpMemberMachineMapper memberMachineMapper;
	@Autowired
	private ThpBedMachineMapper thpBedMachineMapper;
	
	public ThpMemberMachine queryByMachineCode(String machineId) {
		ThpMemberMachineExample example = new ThpMemberMachineExample();
		example.createCriteria().andMachineIdEqualTo(machineId).andMachineTypeEqualTo("02").andIsValidEqualTo((byte)1);
		List<ThpMemberMachine> memberMachines = memberMachineMapper.selectByExample(example);
		if(memberMachines != null && memberMachines.size() > 0) {
			return memberMachines.get(0);
		}
		return null;
	}

	public BedMachinePO queryBedByMachineCode(String machineId) {
		BedMachinePO bedMachinePOS = this.thpBedMachineMapper.queryBedByMachineCode(machineId);
		return bedMachinePOS;
	}
}
