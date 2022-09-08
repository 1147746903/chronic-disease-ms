package com.comvee.cdms.clinicaldiagnosis.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.comvee.cdms.clinicaldiagnosis.constant.YzConstant;
import com.comvee.cdms.clinicaldiagnosis.constant.YzOperation;
import com.comvee.cdms.clinicaldiagnosis.dto.AddDrugYzDTO;
import com.comvee.cdms.clinicaldiagnosis.dto.AddYzDTO;
import com.comvee.cdms.clinicaldiagnosis.dto.ListMemberYzDTO;
import com.comvee.cdms.clinicaldiagnosis.dto.ListYzRecordDTO;
import com.comvee.cdms.clinicaldiagnosis.mapper.YzLogMapper;
import com.comvee.cdms.clinicaldiagnosis.mapper.YzMapper;
import com.comvee.cdms.clinicaldiagnosis.po.YzLogPO;
import com.comvee.cdms.clinicaldiagnosis.po.YzPO;
import com.comvee.cdms.clinicaldiagnosis.service.YzServiceI;
import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzAndDrugVO;
import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzListVO;
import com.comvee.cdms.clinicaldiagnosis.vo.YzExecuteLogVO;
import com.comvee.cdms.clinicaldiagnosis.vo.YzVO;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.virtualward.constant.VirtualWardConstant;
import com.comvee.cdms.virtualward.model.param.GetVirtualWardParam;
import com.comvee.cdms.virtualward.model.po.VirtualWardPO;
import com.comvee.cdms.virtualward.service.VirtualWardService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * @author 李左河
 *
 */
@Service("yzService")
public class YzServiceImpl implements YzServiceI {

	@Autowired
	private YzMapper yzMapper;

	@Autowired
	private DoctorServiceI doctorService;

	@Autowired
	private YzLogMapper yzLogMapper;

	@Autowired
	private VirtualWardService virtualWardService;

	@Override
	public Long getYzByPageCount(Map<String, Object> map) {
		return this.yzMapper.getYzByPageCount(map);
	}

	@Override
	public List<YzVO> listYzByPage(Map<String, Object> map) {
		List<YzPO> pos = this.yzMapper.listYzByPage(map);
		List<YzVO> vos = new ArrayList<>();
		pos.forEach(item->{
			YzVO vo = new YzVO();
			BeanUtils.copyProperties(vo,item);
			vos.add(vo);
		});
		return vos;
	}

	@Override
	public void saveYz(AddYzDTO dto) {
		YzPO yzPO = new YzPO();
		BeanUtils.copyProperties(yzPO,dto);
		yzPO.setSid(DaoHelper.getSeq());
		this.yzMapper.saveYz(yzPO);
	}

	@Override
	public YzVO getYzByYzId(String yzId) {
		YzPO po = this.yzMapper.getYzByYzId(yzId);
		YzVO vo = new YzVO();
		BeanUtils.copyProperties(vo,po);
		return vo;
	}

	@Override
	public YzVO getYzById(String sid) {
		YzPO po = this.yzMapper.getYzById(sid);
		YzVO vo = new YzVO();
		BeanUtils.copyProperties(vo,po);
		return vo;
	}

	@Override
	public List<YzVO> getYzByMemberId(String memberId) {
		List<YzPO> pos = this.yzMapper.getYzByMemberId(memberId);;
		List<YzVO> vos = new ArrayList<>();
		pos.forEach(item->{
			YzVO vo = new YzVO();
			BeanUtils.copyProperties(vo,item);
			vos.add(vo);
		});
		return vos;
	}

	@Override
	public List<YzVO> listYzByParams(Map<String, Object> paramMap) {
		List<YzPO> pos = this.yzMapper.listYzByParams(paramMap);
		List<YzVO> vos = new ArrayList<>();
		pos.forEach(item->{
			YzVO vo = new YzVO();
			BeanUtils.copyProperties(vo,item);
			vos.add(vo);
		});
		return vos;
	}

	@Override
	public PageResult<MemberYzAndDrugVO> listDrugRecordByMemberId(String page, String row, String memberId) {

		Integer pageNum = ValidateTool.checkIsNull(page) ? Integer.parseInt(page) : 1;
		Integer pageSize = ValidateTool.checkIsNull(row) ? Integer.parseInt(row) : 10;
		PageHelper.startPage(pageNum, pageSize);
		List<MemberYzAndDrugVO> memberYzAndDrugList = this.yzMapper.listDrugRecordByMemberId(memberId);
		PageResult<MemberYzAndDrugVO> pageObj = new PageResult<MemberYzAndDrugVO>(memberYzAndDrugList);
		return pageObj;
	}

	@Override
	public PageResult<MemberYzAndDrugVO> listYzRecordByMemberId(String page, String row, ListYzRecordDTO dto) {

		Integer pageNum = ValidateTool.checkIsNull(page) ? Integer.parseInt(page) : 1;
		Integer pageSize = ValidateTool.checkIsNull(row) ? Integer.parseInt(row) : 10;
		PageHelper.startPage(pageNum, pageSize);
		List<MemberYzAndDrugVO> memberYzAndDrugList = this.yzMapper.listYzRecordByMemberId(dto);
		PageResult<MemberYzAndDrugVO> pageObj = new PageResult<MemberYzAndDrugVO>(memberYzAndDrugList);
		return pageObj;
	}

	@Override
	public List<YzVO> listYzDrugNewByMemberId(Map<String, Object> paramMap) {
		String memberId = (String)paramMap.get("memberId");
		String startDate = this.yzMapper.getYzStartDateNewByMemberId(memberId);
		if (StringUtils.isBlank(startDate)) {
			return null;
		}
		paramMap.put("startDate", startDate);
		List<YzPO> pos = this.yzMapper.listYzDrugNewByMemberId(paramMap);;
		List<YzVO> vos = new ArrayList<>();
		pos.forEach(item->{
			YzVO vo = new YzVO();
			BeanUtils.copyProperties(vo,item);
			vos.add(vo);
		});
		return vos;
	}

	@Override
	public List<YzVO> listDrugYzOfOutHospitalMember(String memberId) {
		List<YzPO> pos = this.yzMapper.listDrugYzOfOutHospitalMember(memberId);
		List<YzVO> vos = new ArrayList<>();
		pos.forEach(item->{
			YzVO vo = new YzVO();
			BeanUtils.copyProperties(vo,item);
			vos.add(vo);
		});
		return vos;
	}

	@Override
	public List<YzVO> listYzDrugNearlyTwoMonths(Map<String, Object> paramMap) {
		List<YzPO> pos = this.yzMapper.listYzDrugNearlyTwoMonths(paramMap);
		List<YzVO> vos = new ArrayList<>();
		pos.forEach(item->{
			YzVO vo = new YzVO();
			BeanUtils.copyProperties(vo,item);
			vos.add(vo);
		});
		return vos;
	}

	@Override
	public void addMemberDrugYz(DoctorSessionBO doctor, String memberId, String visitNo, String priorityCode, String drugJson) {
		List<AddDrugYzDTO> addDrugYzDTOS = JSONArray.parseArray(drugJson, AddDrugYzDTO.class);
		if(addDrugYzDTOS!=null && addDrugYzDTOS.size()>0){
			String groupId = DaoHelper.getSeq();
			for(AddDrugYzDTO drugYzDTO : addDrugYzDTOS){
				AddYzDTO dto = new AddYzDTO();
				dto.setDoctorName(doctor.getDoctorName());
				dto.setDoctorCode(doctor.getDoctorId());
				dto.setDrugDose(drugYzDTO.getDrugDose());
				dto.setDrugDoseUnit(drugYzDTO.getDrugDoseUnit());
				dto.setDrugDoseUnitDesc(drugYzDTO.getDrugDoseUnitDesc());
				dto.setDrugDurationCode(drugYzDTO.getDrugDurationCode());
				dto.setDrugDurationDesc(drugYzDTO.getDrugDurationDesc());
				dto.setDrugFreqCode(drugYzDTO.getDrugFreqCode());
				dto.setDrugSpecification(drugYzDTO.getDrugSpecification());
				dto.setDrugFreqDesc(drugYzDTO.getDrugFreqDesc());
				dto.setEmergency(drugYzDTO.getEmergency());
				dto.setPrice(drugYzDTO.getPrice());
				dto.setSkinTest(drugYzDTO.getSkinTest());
				dto.setYzItemCode(drugYzDTO.getDrugCode());
				dto.setYzType(drugYzDTO.getType());
				String startDt = drugYzDTO.getStartDt();
				if(!StringUtils.isBlank(startDt)){
					dto.setStartDate(DateHelper.getDate(DateHelper.getDate(startDt
							,DateHelper.DATETIME_FORMAT),DateHelper.DAY_FORMAT));
					dto.setStartTime(DateHelper.getDate(DateHelper.getDate(startDt
							,DateHelper.DATETIME_FORMAT),"HH:mm:ss"));
				} else {
					Date date = new Date();
					dto.setStartDate(DateHelper.getToday());
					dto.setStartTime(DateHelper.getDate(date,"HH:mm:ss"));
				}
				String stopDt = drugYzDTO.getStopDt();
				if(!StringUtils.isBlank(stopDt)){
					dto.setStopDate(DateHelper.getDate(DateHelper.getDate(stopDt
							,DateHelper.DATETIME_FORMAT),DateHelper.DAY_FORMAT));
					dto.setStopTime(DateHelper.getDate(DateHelper.getDate(stopDt
							,DateHelper.DATETIME_FORMAT),"HH:mm:ss"));
				}
				dto.setYzItemName(drugYzDTO.getDrugName());
				dto.setUsePlanJson(drugYzDTO.getUsePlanJson());

				dto.setMemberId(memberId);
				if(StringUtils.isBlank(visitNo)){
					visitNo="-1";
				}
				dto.setVisitNo(visitNo);
				dto.setYzItemType(1);
				dto.setYzId(DaoHelper.getSeq());
				dto.setYzGroupId(groupId);
				dto.setPriorityCode(priorityCode);
				if(StringUtils.isBlank(priorityCode)){
					dto.setPriorityCode("1");
				}
				dto.setRecordOrigin(2);
				dto.setDepartmentId(doctor.getDepartId());
				dto.setHospitalId(doctor.getHospitalId());
				this.saveYz(dto);
			}
		}
	}

	@Override
	public String addYz(AddYzDTO addYzDTO) {
		//新增长期泵剂量医嘱,先判断有没有未停止状态的长期泵剂量,有就提示先停止.
		if (addYzDTO.getYzType()== 1){
			List<YzPO> pos = this.yzMapper.listYzByMemberIdAndTypeAndItemType(addYzDTO.getMemberId(),Arrays.asList(YzConstant.YZ_STATUS_NEW,YzConstant.YZ_STATUS_SEND,YzConstant.YZ_STATUS_EXECUTING,YzConstant.YZ_STATUS_EXECUTED), 1,YzConstant.LONG_ADVICE,YzConstant.YZ_ITEM_CODE_INSULIN_PUMP,addYzDTO.getHospitalId());
			if (pos != null && pos.size() >0){
				throw new BusinessException("请先停止目前的泵剂量医嘱");
			}
		}
		String yzId = DaoHelper.getSeq();
		addYzDTO.setYzGroupId(yzId);
		addYzDTO.setYzId(yzId);
		addYzDTO.setYzStatus(YzConstant.YZ_STATUS_NEW);
		yzDateHandler(addYzDTO);
		saveYz(addYzDTO);
		return yzId;
	}

	@Override
	public PageResult<MemberYzListVO> listMemberYz(PageRequest pr, ListMemberYzDTO listMemberYzDTO) {
		listMemberYzParamHandler(listMemberYzDTO);
		PageHelper.startPage(pr.getPage() ,pr.getRows());
		List<MemberYzListVO> list = this.yzMapper.listMemberYz(listMemberYzDTO);
		PageResult<MemberYzListVO> result = new PageResult<>(list);
		return result;
	}

	@Override
	public YzExecuteLogVO getYzExecuteLogByYzId(String yzId) {
		return this.yzMapper.getYzExecuteLogByYzId(yzId);
	}

	@Override
	public List<MemberYzListVO> listMemberYz(ListMemberYzDTO listMemberYzDTO) {
		return this.yzMapper.listMemberYz(listMemberYzDTO);
	}

	@Override
	public void operateYz(List<String> yzIdList, YzOperation yzOperation, String operatorId) {
		DoctorPO doctorPO = this.doctorService.getDoctorById(operatorId);
		YzPO yz = null;
		for(String id : yzIdList){
			yz = this.yzMapper.getYzByYzId(id);
			if(yz == null){
				throw new BusinessException("医嘱记录不存在，医嘱id:" + id);
			}
			yzOperationHandler(yz ,yzOperation ,doctorPO);
		}
	}

	@Override
	public void updateYz(YzPO yzPO) {
		YzVO vo = getYzByYzId(yzPO.getYzId());
		if(vo == null || StringUtils.isBlank(vo.getYzId())){
			throw new BusinessException("医嘱不存在，请确认");
		}
		if(YzConstant.YZ_STATUS_NEW != vo.getYzStatus()){
			throw new BusinessException("非未下发状态不可修改");
		}
		if(!StringUtils.isBlank(yzPO.getStartDt())){
			String[] arr = yzPO.getStartDt().split(" ");
			if(arr.length > 1){
				yzPO.setStartDate(arr[0]);
				yzPO.setStartTime(arr[1]);
			}
		}
		if(!StringUtils.isBlank(yzPO.getStopDt())) {
			String[] arr = yzPO.getStopDt().split(" ");
			if (arr.length > 1) {
				yzPO.setStopDate(arr[0]);
				yzPO.setStopTime(arr[1]);
			}
		}
		this.yzMapper.updateYz(yzPO);
	}

	@Override
	public List listMemberYzByMemberCheckInfo(ListMemberYzDTO dto) {
		List<MemberYzListVO> memberYzListVOS = this.yzMapper.listMemberYzByMemberCheckInfo(dto);
		return memberYzListVOS;
	}

	@Override
	public List listYzByMemberIdAndTypeAndItemType(String memberId,List<Integer> yzStatus,Integer yzItemType,Integer yzType,String yzItemCode,String hospitalId) {
		List<YzPO> pos = this.yzMapper.listYzByMemberIdAndTypeAndItemType(memberId,yzStatus,yzItemType,yzType,yzItemCode,hospitalId);
		return pos;
	}

	@Override
	public List<YzPO> getYzByMemberIdByYzItemType(String memberId,String date) {
		List<YzPO> yzVOS = new ArrayList<>();
		return yzVOS;
	}

	@Override
	public List<YzPO> listYzByMemberIdAndHospital(String memberId, String hospitalId,String inHospitalDt,String outHospitalDt,Integer yzItemType,List<Integer> yzStatus) {
		List<YzPO> poList = this.yzMapper.listYzByMemberIdAndHospital(memberId,hospitalId,inHospitalDt,outHospitalDt,yzItemType,yzStatus);
		return poList;
	}

	@Override
	public List<YzPO> listInsulinPumpDoctorAdvice(String memberId, String hospitalId,Integer yzItemType) {
		List<YzPO> pos = this.yzMapper.listInsulinPumpDoctorAdvice(memberId,hospitalId,yzItemType);
		return pos;
	}

	/**
	 * 医嘱操作处理
	 * @param yzPO
	 * @param yzOperation
	 * @param doctorPO
	 */
	private void yzOperationHandler(YzPO yzPO , YzOperation yzOperation , DoctorPO doctorPO){
		switch (yzOperation){
			case HAND_DOWN:
				handDownYz(yzPO);
				break;
			case CANCEL_HAND_DOWN:
				cancelHandDownYz(yzPO);
				break;
			case DELETE:
				deleteYz(yzPO);
				break;
			case ABOLISH:
				abolishYz(yzPO);
				break;
			case CHECK:
				checkYz(yzPO ,doctorPO);
				break;
			case STOP:
				stopYz(yzPO);
				break;
			case EXECUTE:
				executeYz(yzPO);
				break;
			default:
				break;
		}
	}

	/**
	 * 医嘱日期处理
	 * @param addYzDTO
	 */
	private void yzDateHandler(AddYzDTO addYzDTO){
		if(!StringUtils.isBlank(addYzDTO.getStartDt())){
			String[] arr = addYzDTO.getStartDt().split(" ");
			if(arr.length > 1){
				addYzDTO.setStartDate(arr[0]);
				addYzDTO.setStartTime(arr[1]);
			}
		}
		if(!StringUtils.isBlank(addYzDTO.getStopDt())){
			String[] arr = addYzDTO.getStopDt().split(" ");
			if(arr.length > 1){
				addYzDTO.setStopDate(arr[0]);
				addYzDTO.setStopTime(arr[1]);
			}
		}else{
			addYzDTO.setStopDt(null);
		}
	}

	/**
	 * 患者医嘱列表参数处理
	 * @param listMemberYzDTO
	 */
	private void listMemberYzParamHandler(ListMemberYzDTO listMemberYzDTO){
		String splitRegex = ",";
		if(!StringUtils.isBlank(listMemberYzDTO.getYzItemTypeString())){
			listMemberYzDTO.setYzItemTypeList(new ArrayList<>(Arrays.asList(listMemberYzDTO.getYzItemTypeString().split(splitRegex))));
		}
		if(!StringUtils.isBlank(listMemberYzDTO.getYzStatusString())){
			listMemberYzDTO.setYzStatusList(new ArrayList<>(Arrays.asList(listMemberYzDTO.getYzStatusString().split(splitRegex))));
		}
	}

	/**
	 * 下发医嘱
	 * @param yzPO
	 */
	private void handDownYz(YzPO yzPO){
		if(YzConstant.YZ_STATUS_NEW != yzPO.getYzStatus()){
			throw new BusinessException("非未下发状态不可下发");
		}
		yzPO.setYzStatus(YzConstant.YZ_STATUS_SEND);
		this.yzMapper.updateYz(yzPO);
	}

	/**
	 * 取消下发医嘱
	 * @param yzPO
	 */
	private void cancelHandDownYz(YzPO yzPO){
		if(YzConstant.YZ_STATUS_SEND != yzPO.getYzStatus()){
			throw new BusinessException("非已发送状态不可取消发送");
		}
		yzPO.setYzStatus(YzConstant.YZ_STATUS_NEW);
		this.yzMapper.updateYz(yzPO);
	}

	/**
	 * 删除医嘱
	 * @param yzPO
	 */
	private void deleteYz(YzPO yzPO){
		if(YzConstant.YZ_STATUS_NEW != yzPO.getYzStatus()){
			throw new BusinessException("非未下发状态不可删除");
		}
		yzPO.setIsValid(0);
		this.yzMapper.updateYz(yzPO);
	}

	/**
	 * 作废医嘱
	 * @param yzPO
	 */
	private void abolishYz(YzPO yzPO){
		if(YzConstant.YZ_STATUS_EXECUTING != yzPO.getYzStatus() && YzConstant.YZ_STATUS_EXECUTED != yzPO.getYzStatus()){
			throw new BusinessException("非执行状态不可作废");
		}
		yzPO.setYzStatus(YzConstant.YZ_STATUS_ABOLISH);
		this.yzMapper.updateYz(yzPO);
	}

	/**
	 * 停止医嘱
	 * @param yzPO
	 */
	private void stopYz(YzPO yzPO){
		if(YzConstant.YZ_STATUS_EXECUTING != yzPO.getYzStatus() && YzConstant.YZ_STATUS_EXECUTED != yzPO.getYzStatus()){
			throw new BusinessException("非执行状态不可停止");
		}
		String stopDate = DateHelper.getNowDate();
		yzPO.setStopDt(stopDate);
		yzPO.setStopDate(stopDate.substring(0 ,10));
		yzPO.setStopTime(stopDate.substring(11 ,19));
		yzPO.setYzStatus(YzConstant.YZ_STATUS_STOP);
		this.yzMapper.updateYz(yzPO);
	}

	/**
	 * 校对医嘱
	 * @param yzPO
	 * @param doctorPO
	 */
	@Transactional
	public void checkYz(YzPO yzPO ,DoctorPO doctorPO){
		YzLogPO yzLogPO = this.yzLogMapper.getYzLogByYzId(yzPO.getYzId());
		if(yzLogPO != null){
			throw new BusinessException("该医嘱已校对，医嘱id:" + yzPO.getYzId());
		}
		yzLogPO = new YzLogPO();
		yzLogPO.setSid(DaoHelper.getSeq());
		yzLogPO.setYzId(yzPO.getYzId());
		yzLogPO.setCheckerId(doctorPO.getDoctorId());
		yzLogPO.setCheckerName(doctorPO.getDoctorName());
		yzLogPO.setCheckDt(DateHelper.getNowDate());
		this.yzLogMapper.addYzLog(yzLogPO);

		//修改医嘱表的医嘱状态为已校验
		YzPO po = new YzPO();
		po.setYzStatus(7);
		po.setYzId(yzPO.getYzId());
		this.yzMapper.updateYz(po);
	}

	/**
	 * 执行医嘱
	 * @param yzPO
	 */
	private void executeYz(YzPO yzPO){
		YzLogPO yzLogPO = this.yzLogMapper.getYzLogByYzId(yzPO.getYzId());
		if(yzLogPO == null){
			throw new BusinessException("该医嘱暂无法执行");
		}
		yzLogPO.setLastExecuteTime(DateHelper.getNowDate());
		this.yzLogMapper.updateYzLog(yzLogPO);

		yzPO.setYzStatus(YzConstant.YZ_STATUS_EXECUTED);
		this.yzMapper.updateYz(yzPO);

		executeYzPostHandler(yzPO);

	}

	/**
	 * 执行医嘱后置处理
	 * @param addYzDTO
	 */
	private void executeYzPostHandler(YzPO yzPO){
		//用泵逻辑处理
		if(!YzConstant.YZ_ITEM_CODE_INSULIN_PUMP.equals(yzPO.getYzItemCode())){
			return;
		}
		GetVirtualWardParam get = new GetVirtualWardParam();
		get.setId(yzPO.getForeignId());
		VirtualWardPO virtualWardPO = this.virtualWardService.getVirtualWard(get);
		if(virtualWardPO != null){
			virtualWardPO.setUseInsulinPumpStatus(VirtualWardConstant.USE_INSULIN_PUMP_STATUS_YES);
			this.virtualWardService.updateVirtualWard(virtualWardPO);
		}
	}
}
