package com.comvee.cdms.checkresult.service.impl;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.checkresult.constant.InspectionConstant;
import com.comvee.cdms.checkresult.constant.InspectionDict;
import com.comvee.cdms.checkresult.dto.AddInspectionDTO;
import com.comvee.cdms.checkresult.dto.GetInspectionDTO;
import com.comvee.cdms.checkresult.dto.ResolveInspectionRemindDTO;
import com.comvee.cdms.checkresult.mapper.InspectionMapper;
import com.comvee.cdms.checkresult.po.InspectionPO;
import com.comvee.cdms.checkresult.service.CheckRemindService;
import com.comvee.cdms.checkresult.service.InspectionServiceI;
import com.comvee.cdms.checkresult.support.InspectionSyncHandler;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
@Service("inspectionService")
public class InspectionServiceImpl implements InspectionServiceI {

	@Autowired
	private InspectionMapper inspectionMapper;

	@Autowired
	private CheckRemindService checkRemindService;

	@Autowired
	private InspectionSyncHandler inspectionSyncHandler;

	@Override
	public List<InspectionPO> listInspection(String memberId) {
		
		return this.inspectionMapper.listInspection(memberId);
	}

	@Override
	public InspectionPO getInspectionByInspectId(String inspectId,String hospitalId) {

		return this.inspectionMapper.getInspectionByInspectId(inspectId);
	}

	@Override
	public List<InspectionPO> listInspectionByHospital(String memberId, String hospitalId) {
		Map<String,Object> questMap = new HashMap<>(3);
		questMap.put("memberId",memberId);
		questMap.put("hospitalId",hospitalId);
		questMap.put("groupbyTitle",true);
		return this.inspectionMapper.listInspectionByParams(questMap);
	}

	@Override
	@Transactional(rollbackFor = Exception.class ,isolation = Isolation.READ_UNCOMMITTED)
	public String addInspection(AddInspectionDTO add){
		InspectionPO inspection = new InspectionPO();
		BeanUtils.copyProperties(inspection ,add);
		GetInspectionDTO getDTO = new GetInspectionDTO();
		getDTO.setInspectionCode(add.getInspectCode());
		getDTO.setMemberId(add.getMemberId());
		getDTO.setReportDt(add.getReportDt());
		InspectionPO localRecord = this.inspectionMapper.getInspection(getDTO);
		if(localRecord == null){
			inspection.setSid(DaoHelper.getSeq());
			//默认都是已审核的状态
			if(inspection.getReviewStatus() == null){
				inspection.setReviewStatus(InspectionConstant.REVIEW_STATUS_YES);
			}
			this.inspectionMapper.addInspection(inspection);
			checkRemindResolve(inspection);
			this.inspectionSyncHandler.resolveInspectionSync(inspection);
		}else{
			inspection.setSid(localRecord.getSid());
			modifyInspection(inspection);
		}
		return inspection.getSid();
	}

	@Override
	public void modifyInspection(InspectionPO po){
		inspectionMapper.modifyInspection(po);
		GetInspectionDTO getInspectionDTO = new GetInspectionDTO();
		getInspectionDTO.setSid(po.getSid());
		InspectionPO selectResult = this.inspectionMapper.getInspection(getInspectionDTO);
		checkRemindResolve(selectResult);
		this.inspectionSyncHandler.resolveInspectionSync(selectResult);
	}


	/**
	 * 检查提醒处理
	 * @param inspection
	 */
	private void checkRemindResolve(InspectionPO inspection){
		ResolveInspectionRemindDTO dto = new ResolveInspectionRemindDTO();
		dto.setMemberId(inspection.getMemberId());
		dto.setInspectionCode(inspection.getInspectCode());
		dto.setReportDt(inspection.getReportDt());
		dto.setDataJson(inspection.getInspectDataJson());
		this.checkRemindService.resolveInspectionRemind(dto);

		//如果是abi,还要处理pwv
		if(InspectionDict.ABI.getCode().equals(inspection.getInspectCode())){
			dto = new ResolveInspectionRemindDTO();
			dto.setMemberId(inspection.getMemberId());
			dto.setInspectionCode(InspectionDict.PWV.getCode());
			dto.setReportDt(inspection.getReportDt());
			dto.setDataJson(inspection.getInspectDataJson());
			this.checkRemindService.resolveInspectionRemind(dto);
		}
	}

}
