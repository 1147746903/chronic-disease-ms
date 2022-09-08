package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.sign.dto.AddBloodPressureServiceDTO;
import com.comvee.cdms.sign.dto.ListBmiDTO;
import com.comvee.cdms.sign.po.BloodPressurePO;
import com.comvee.cdms.sign.po.BmiPO;

public interface BloodPressureServiceI {

	public PageResult<BloodPressurePO> getPatientBloodPressure(PageRequest page , String memberId);
	
	public PageResult<BmiPO> getPagientBmiList(PageRequest page , ListBmiDTO listBmiDTO);
}
