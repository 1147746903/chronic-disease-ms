package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.app.doctorapp.model.app.MemberMonitorPlanModel;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.prescription.vo.MemberMonitorVO;

public interface BloodMonitorServiceI {

	public MemberMonitorPlanModel loadSugarMonitorTemplates(String memberId);

	public MemberMonitorPlanModel loadMemberSugarmonitorById(String memberId);

	public MemberMonitorVO addSugarMonitor(MemberMonitorPlanPO memberMonitorPlan);
}
