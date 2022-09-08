package com.comvee.cdms.bloodmonitor.service;

import com.comvee.cdms.bloodmonitor.dto.MemberMonitorPlanDetailDTO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanDetailServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorPlanDetailMapper;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanDetailPO;

import java.util.List;

/**
 * 
 * @author 李左河
 *
 */
@Service("memberMonitorPlanDetailService")
public class MemberMonitorPlanDetailServiceImpl implements MemberMonitorPlanDetailServiceI {

	@Autowired
	private MemberMonitorPlanDetailMapper memberMonitorPlanDetailMapper;

	@Override
	public void deleteMonitorPlanDetail(String memberId ,Integer illnessType) {
		
		this.memberMonitorPlanDetailMapper.deleteMonitorPlanDetail(memberId ,illnessType);
	}

	@Override
	public void saveMonitorPlanDetail(MemberMonitorPlanDetailPO memberMonitorPlanDetail) {
		
		this.memberMonitorPlanDetailMapper.saveMonitorPlanDetail(memberMonitorPlanDetail);
	}

	@Override
	public List<MemberMonitorPlanDetailPO> listMemberMonitorPlanDetail(MemberMonitorPlanDetailDTO memberMonitorPlanDetailDTO) {
		return this.memberMonitorPlanDetailMapper.listMemberMonitorPlanDetail(memberMonitorPlanDetailDTO);
	}
}
