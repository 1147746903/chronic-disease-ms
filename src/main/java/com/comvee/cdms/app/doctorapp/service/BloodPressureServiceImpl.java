package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.sign.dto.ListBloodPressureDTO;
import com.comvee.cdms.sign.dto.ListBmiDTO;
import com.comvee.cdms.sign.mapper.BloodPressureMapper;
import com.comvee.cdms.sign.po.BloodPressurePO;
import com.comvee.cdms.sign.po.BmiPO;
import com.comvee.cdms.sign.service.BmiService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bloodPressureAppService")
public class BloodPressureServiceImpl implements BloodPressureServiceI{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BloodPressureMapper bloodPressureMapper;
	
	@Autowired
	private BmiService bmiService;
	
	@Override
	public PageResult<BloodPressurePO> getPatientBloodPressure(PageRequest page ,String memberId) {
		
		
		ListBloodPressureDTO listBloodPressureDTO = new ListBloodPressureDTO();
		listBloodPressureDTO.setMemberId(memberId);
		PageHelper.startPage(page.getPage(), page.getRows());
		List<BloodPressurePO> listBloodPressur = this.bloodPressureMapper.listBloodPressure(listBloodPressureDTO);
		
		PageResult<BloodPressurePO> pageResult = new PageResult(listBloodPressur);
		
		return pageResult;
	}

	@Override
	public PageResult<BmiPO> getPagientBmiList(PageRequest page, ListBmiDTO listBmiDTO) {
		PageHelper.startPage(page.getPage(), page.getRows());
		List<BmiPO> list = this.bmiService.listBmi(listBmiDTO);
		PageResult<BmiPO> pageResult = new PageResult(list);
		return pageResult;
	}
	
	
}
