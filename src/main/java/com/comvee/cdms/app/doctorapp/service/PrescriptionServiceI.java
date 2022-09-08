package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.app.doctorapp.model.app.PrescriptionModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

public interface PrescriptionServiceI {

	public PageResult<PrescriptionModel> loadDoctorMemberPrescriptionList(PageRequest page,String memberId , String doctorId);
}
