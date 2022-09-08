package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.app.doctorapp.model.app.MemberMonitorPlanModel;
import com.comvee.cdms.bloodmonitor.constant.MonitorPlanConstant;
import com.comvee.cdms.bloodmonitor.dto.GetMemberMonitorDTO;
import com.comvee.cdms.bloodmonitor.dto.ListMonitorPlanTemplateMapperDTO;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorPlanMapper;
import com.comvee.cdms.bloodmonitor.model.SugarMonitorTemplatePO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.prescription.vo.MemberMonitorVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("bloodMonitorAppService")
public class BloodMonitorServiceImpl implements BloodMonitorServiceI{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MemberMonitorPlanMapper memberMonitorPlanMapper;

	@Autowired
	private MemberMonitorPlanServiceI memberMonitorPlanService;
	
	@Override
	public MemberMonitorPlanModel loadSugarMonitorTemplates(String memberId) {
		//1获取公共模板 - 加载app模板
		ListMonitorPlanTemplateMapperDTO listMonitorPlanTemplateMapperDTO = new ListMonitorPlanTemplateMapperDTO();
		listMonitorPlanTemplateMapperDTO.setMonitorType(MonitorPlanConstant.MONITOR_TYPE_APP);
		List<SugarMonitorTemplatePO> templateList = this.memberMonitorPlanMapper.listMonitorPlanTemplate(listMonitorPlanTemplateMapperDTO);
		if(templateList!=null && templateList.size()>0){
		    for (Iterator iterator = templateList.iterator(); iterator.hasNext();) {
		    	SugarMonitorTemplatePO templatePO = (SugarMonitorTemplatePO) iterator.next();
                if(templatePO.getSchemeName()!=null && StringUtils.isBlank(templatePO.getSchemeName().toString())){
                    iterator.remove();
                }
            }
		}	
		
		//2获取自己的模板
		Map<String,Object> map = new HashMap();
		
		MemberMonitorPlanModel memberMonitorPlanModel = new MemberMonitorPlanModel();
		
		GetMemberMonitorDTO getMemberMonitorDTO = new GetMemberMonitorDTO();
		getMemberMonitorDTO.setInProgress(1);
		getMemberMonitorDTO.setMemberId(memberId);
		MemberMonitorPlanPO memberonitor = this.memberMonitorPlanMapper.getMemberMonitorPlan(getMemberMonitorDTO);
		if(memberonitor == null) {
			map.put("scheme_name","");
		}else {
			BeanUtils.copyProperties(memberonitor, memberMonitorPlanModel);
			memberMonitorPlanModel.setTemplates(templateList);
		}
		return memberMonitorPlanModel;
	}

	@Override
	public MemberMonitorPlanModel loadMemberSugarmonitorById(String memberId) {
		GetMemberMonitorDTO getMemberMonitorDTO = new GetMemberMonitorDTO();
		getMemberMonitorDTO.setInProgress(1);
		getMemberMonitorDTO.setMemberId(memberId);
		getMemberMonitorDTO.setIllnessType(1);
		MemberMonitorPlanPO memberonitor = this.memberMonitorPlanMapper.getExecutionMemberMonitorPlan(getMemberMonitorDTO);
		MemberMonitorPlanModel memberMonitorPlanModel = new MemberMonitorPlanModel();
		if(memberonitor!=null) {
			BeanUtils.copyProperties(memberonitor, memberMonitorPlanModel);
		}
		
		return memberMonitorPlanModel;
	}

	@Override
	public MemberMonitorVO addSugarMonitor(MemberMonitorPlanPO memberMonitorPlan) {
		MemberMonitorVO memberMonitorVO = this.memberMonitorPlanService.saveMonitor(memberMonitorPlan);
		return memberMonitorVO;
	}

}
