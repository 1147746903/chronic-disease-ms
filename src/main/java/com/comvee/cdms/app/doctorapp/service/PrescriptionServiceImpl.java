package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.app.doctorapp.model.app.PrescriptionModel;
import com.comvee.cdms.common.utils.Base64Util;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.prescription.dto.GetPrescriptionDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("prescriptionAppService")
public class PrescriptionServiceImpl implements PrescriptionServiceI{
	
	private final static Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Autowired
	private PrescriptionMapper prescriptionMapper;
	
	@Override
	public PageResult<PrescriptionModel> loadDoctorMemberPrescriptionList(PageRequest page, String memberId , String doctorId) {
		GetPrescriptionDTO dto = new GetPrescriptionDTO();
		dto.setMemberId(memberId);
		dto.setComplete(true);
		List<PrescriptionModel> presModelList = new ArrayList();
		PageHelper.startPage(page.getPage(), page.getRows());
		List<PrescriptionPO> list = this.prescriptionMapper.listPrescriptionByParam(dto);
		PageResult<PrescriptionPO> listResult = new PageResult<PrescriptionPO>(list);
		
		for (PrescriptionPO prescriptionPO : list) {
			PrescriptionModel presModel = new PrescriptionModel();
			BeanUtils.copyProperties(presModel, prescriptionPO);
//			presModel.setTypeName("糖尿病自我管理处方");
			String url = "/prescription/mobile/care_prescript.html?doctorId="+doctorId+
                    "&memberId="+memberId+"&prescriptionId="+ Base64Util.encodeBase64(presModel.getSid())+"&isApp=1";						
			presModel.setUrl(url);
			String moduleName = "";
			String module = presModel.getModule();
			if(module.indexOf(",")>0) {
				String[] a = module.split(",");
				for(int i=0;i<a.length;i++) {
					if(i ==a.length-1) {
						moduleName += getModuleName(a[i]);
					}else {
						moduleName += getModuleName(a[i]) + ',';
					}
				}
				presModel.setTypeName(moduleName);
			}else {
				presModel.setTypeName(getModuleName(module));
			}
			// 1控制目标，2监测方案，3饮食，4运动，5知识学习
			presModelList.add(presModel);
		}
		PageResult<PrescriptionModel> pageResult = new PageResult<PrescriptionModel>(presModelList);
		pageResult.setTotalPages(listResult.getTotalPages());
		pageResult.setTotalRows(listResult.getTotalRows());
		pageResult.setPageNum(listResult.getPageNum());
		pageResult.setPageSize(listResult.getPageSize());
		
		return pageResult;
	}
	
	private String getModuleName(String code) {
		if(code.equals("1")) {
			return "控制目标";
		}else if(code.equals("2")) {
			return "监测方案";
		}else if(code.equals("3")) {
			return "饮食";
		}else if(code.equals("4")) {
			return "运动";
		}else if(code.equals("5")) {
			return "知识学习";
		}else if(code.equals("6")){
			return "患者档案";
		}
		return "";
			
	}

}
