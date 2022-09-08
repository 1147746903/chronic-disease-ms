package com.comvee.cdms.machine.service;


import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.machine.model.BedMachineModel;
import com.comvee.cdms.machine.model.ThpMemberMachine;

import java.util.List;

/**
 * 
 * @author 李左河
 *
 */
public interface MemberMachineServiceI {


	/**
	 * 根据memberId获取已绑定的设备
	 * @param model
	 * @return
	 */
	List<ThpMemberMachine> loadBindEquipmentByMemberId(ThpMemberMachine model);

	/**
	 * 绑定设备
	 * @param checkoutPO
	 */
	Result bindEquipment(ThpMemberMachine model);

	/**
	 * 解绑设备
	 * @param checkoutPO
	 */
	void unBindEquipment(ThpMemberMachine model);
	
	/**
	 * 根据machineId查询是否绑定
	 * @param machineId
	 * @return
	 */
	Long countMachineByMachineId(String machineId);

	/**
	 * 根据machineId查询是否绑定
	 * @param machineId
	 * @return
	 */
	Long countMachineToBedByMachineId(String machineId);

	/**
	 * 设备绑定、解绑
	 * @param model
	 * @param bindFlag
	 * @return
	 */
	Result bindOrUnbindEquipment(ThpMemberMachine model, String bindFlag);


	/**
	 * 床号:设备绑定,解绑
	 * @param model
	 * @param bindFlag
	 * @return
	 */
	Result bedToBindOrUnbindEquipment(BedMachineModel model, String bindFlag);
}
