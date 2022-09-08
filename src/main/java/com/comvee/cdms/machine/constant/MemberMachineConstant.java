package com.comvee.cdms.machine.constant;

/**
 * 
 * @author landd
 *
 */
public class MemberMachineConstant {

	/** 绑定、解绑设备接口 **/
    public final static String EOH_BIND_EQUIPMENT_URL = "common/deviceOperation.do";

	/** 查询SN设备接口【查询设备是否已被绑定或出库】 **/
	public final static String EOH_EQUIPMENT_URL = "comveecrm/member/loadMachineByMachineId.do";
	public final static String EOH_EQUIPMENT_URL2 = "medicaldevice/machine/bind";// "medicaldevice/machine/machineinfo";
	public final static String EOH_EQUIPMENT_UNBIND_URL = "medicaldevice/machine/unbind";// "medicaldevice/machine/machineinfo";
}
