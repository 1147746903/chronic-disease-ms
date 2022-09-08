package com.comvee.cdms.bloodmonitor.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.bloodmonitor.bo.DialogueMonitorPlanBO;
import com.comvee.cdms.bloodmonitor.constant.MonitorPlanConstant;
import com.comvee.cdms.bloodmonitor.dto.DeleteMonitorTemplateDTO;
import com.comvee.cdms.bloodmonitor.dto.GetMemberMonitorDTO;
import com.comvee.cdms.bloodmonitor.dto.ListMonitorPlanTemplateMapperDTO;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorPlanMapper;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorTaskCardMapper;
import com.comvee.cdms.bloodmonitor.model.SugarMonitorTemplatePO;
import com.comvee.cdms.bloodmonitor.model.TodaySchemaModel;
import com.comvee.cdms.bloodmonitor.po.*;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanDetailServiceI;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.bloodmonitor.vo.TodayTaskDetailListVO;
import com.comvee.cdms.bloodmonitor.vo.TodayTaskListVO;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.bo.MemberCodeBO;
import com.comvee.cdms.member.dto.CountDoctorMemberDTO;
import com.comvee.cdms.member.dto.MemberDataDTO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import com.comvee.cdms.prescription.vo.MemberMonitorVO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * 
 * @author 李左河
 *
 */
@Service("memberMonitorPlanService")
public class MemberMonitorPlanServiceImpl implements MemberMonitorPlanServiceI {

	@Autowired
	private MemberMonitorPlanMapper memberMonitorPlanMapper;
	
	@Autowired
	@Qualifier("memberMonitorPlanDetailService")
	private MemberMonitorPlanDetailServiceI memberMonitorPlanDetailService;

	@Autowired
	private DialogueService dialogueService;

	@Autowired
	private WechatMessageService wechatMessageService;

	@Autowired
	private DoctorServiceI doctorService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberMonitorTaskCardMapper memberMonitorTaskCardMapper;

	@Autowired
	private MemberMonitorPlanRecordService memberMonitorPlanRecordService;

	@Override
	public MemberMonitorPlanPO getMemberMonitorPlan(GetMemberMonitorDTO getMemberMonitorDTO) {
		return this.memberMonitorPlanMapper.getMemberMonitorPlan(getMemberMonitorDTO);
	}

	@Override
	public void stopMonitor(String memberId) {
		this.memberMonitorPlanMapper.stopMonitor(memberId , null);
	}

	@Override
	public MemberMonitorVO saveMonitor(MemberMonitorPlanPO memberMonitorPlan) {
		MemberMonitorVO planVO =  new MemberMonitorVO();
		//查询正在执行的监测方案
		List<MemberMonitorPlanPO> planPO = this.memberMonitorPlanMapper.getBloodMonitorPlanAndRecord(memberMonitorPlan.getIllnessType(),memberMonitorPlan.getMemberId());
		if (planPO != null && planPO.size()>0){
			planVO.setPlanId(planPO.get(0).getPlanId());
			planVO.setType(1);
		}else{
			//3、保存监测方案
			String sid = DaoHelper.getSeq();
			memberMonitorPlan.setPlanId(sid);
			memberMonitorPlan.setPlanType(1);//默认长期 1
			this.memberMonitorPlanMapper.saveMonitor(memberMonitorPlan);
			//4、保存监测方案详情
			//获取监测方案字符串
			String planDetail = memberMonitorPlan.getPlanDetail();
			String[] longArray = planDetail.split(";");
			for (String longA : longArray) {
				//解析字符串后，保存每条记录
				MemberMonitorPlanDetailPO memberMonitorPlanDetail = new MemberMonitorPlanDetailPO();
				memberMonitorPlanDetail.setSid(DaoHelper.getSeq());
				memberMonitorPlanDetail.setMemberId(memberMonitorPlan.getMemberId());
				memberMonitorPlanDetail.setPlanType(memberMonitorPlan.getPlanType());
				String[] tempArr = longA.split("-");
				int index = longA.indexOf("-");
				memberMonitorPlanDetail.setDateCode(longA.substring(0 ,index));
				memberMonitorPlanDetail.setMonitorTime(longA.substring(index + 1, longA.length()));
				memberMonitorPlanDetail.setEohType(memberMonitorPlan.getEohType());
				memberMonitorPlanDetail.setIllnessType(memberMonitorPlan.getIllnessType());
				this.memberMonitorPlanDetailService.saveMonitorPlanDetail(memberMonitorPlanDetail);
			}
			//保存监测方案任务卡
			List<MemberMonitorPlanPO> poList = new ArrayList<>();
			poList.add(memberMonitorPlan);
			this.memberMonitorPlanRecordService.addMemberMonitorTaskCard(poList);
			//停止监测方案
			stopBeforeTaskCard(sid);
			//下发设置通知
			sendMonitorDialogue(memberMonitorPlan);
			addMonitorWechatMessage(memberMonitorPlan);
		}
		return planVO;
	}


	@Override
	public void saveMonitorPlan(MemberMonitorPlanPO memberMonitorPlan) {

		//查询这患者有没有执行的方案
		List<MemberMonitorPlanPO> poList = this.memberMonitorPlanMapper.getBloodMonitorPlanAndRecord(memberMonitorPlan.getIllnessType(),memberMonitorPlan.getMemberId());
		if (poList != null && poList.size()>0){
			for (MemberMonitorPlanPO planPO : poList){
				//1:先停止旧的方案
				this.memberMonitorPlanMapper.modifyMonitorPlanByPlanId(planPO.getPlanId());
				//血糖记录->监测方案
				MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanMapper.getMemberMonitorPlanBySid(planPO.getPlanId());
				//2、删除监测方案详细（物理删除）
				this.memberMonitorPlanDetailService.deleteMonitorPlanDetail(memberMonitorPlanPO.getMemberId() ,memberMonitorPlanPO.getIllnessType());

				//停止任务卡
				stopAfterTaskCard(planPO);
			}
		}
		//3、保存监测方案
		String sid = DaoHelper.getSeq();
		memberMonitorPlan.setPlanId(sid);
		memberMonitorPlan.setPlanType(1);//默认长期 1
		this.memberMonitorPlanMapper.saveMonitor(memberMonitorPlan);
		//4、保存监测方案详情
		//获取监测方案字符串
		String planDetail = memberMonitorPlan.getPlanDetail();
		String[] longArray = planDetail.split(";");
		for (String longA : longArray) {
			//解析字符串后，保存每条记录
			MemberMonitorPlanDetailPO memberMonitorPlanDetail = new MemberMonitorPlanDetailPO();
			memberMonitorPlanDetail.setSid(DaoHelper.getSeq());
			memberMonitorPlanDetail.setMemberId(memberMonitorPlan.getMemberId());
			memberMonitorPlanDetail.setPlanType(memberMonitorPlan.getPlanType());
			String[] tempArr = longA.split("-");
			int index = longA.indexOf("-");
			memberMonitorPlanDetail.setDateCode(longA.substring(0 ,index));
			memberMonitorPlanDetail.setMonitorTime(longA.substring(index + 1, longA.length()));
			memberMonitorPlanDetail.setEohType(memberMonitorPlan.getEohType());
			memberMonitorPlanDetail.setIllnessType(memberMonitorPlan.getIllnessType());
			this.memberMonitorPlanDetailService.saveMonitorPlanDetail(memberMonitorPlanDetail);
		}
		//保存监测方案任务卡
		List<MemberMonitorPlanPO> monitorPlanPOS = new ArrayList<>();
		monitorPlanPOS.add(memberMonitorPlan);
		this.memberMonitorPlanRecordService.addMemberMonitorTaskCard(monitorPlanPOS);
		//停止监测方案
		stopBeforeTaskCard(sid);
		//下发设置通知
		sendMonitorDialogue(memberMonitorPlan);
		addMonitorWechatMessage(memberMonitorPlan);

	}

	/**
	 * 处理时间点之后的任务卡为失效
	 */
	public void stopAfterTaskCard(MemberMonitorPlanPO memberMonitorPlanPO){
		if (memberMonitorPlanPO != null){
			List<String> taskIds = new ArrayList<>();
			if (memberMonitorPlanPO.getIllnessType() == 1){ //血糖
				//1:取该方案停止的时间点
				String time = memberMonitorPlanPO.getModifyDt().substring(11,16);
				//获取这停止的时间点之后的数据,将后面时间点及以后的修改为无效
				List<MemberMonitorTaskCardPO> taskCardPOS = this.memberMonitorTaskCardMapper.listAfterMemberMonitorTaskCard(memberMonitorPlanPO.getPlanId(),monitorDt(time),DateHelper.getToday());
				for (MemberMonitorTaskCardPO monitorTaskCardPO : taskCardPOS){
					taskIds.add(monitorTaskCardPO.getSid());
				}
			}else{ //血压
				//1:取该方案的停止时间
				String time = memberMonitorPlanPO.getModifyDt().substring(11,16);
				List<MemberMonitorTaskCardPO> taskCardPOS = this.memberMonitorTaskCardMapper.listAfterMemberMonitorTaskCard(memberMonitorPlanPO.getPlanId(),bloodPressure(time),DateHelper.getToday());
				for (MemberMonitorTaskCardPO monitorTaskCardPO : taskCardPOS){
					taskIds.add(monitorTaskCardPO.getSid());
				}
			}
			if (taskIds == null || "".equals(taskIds)){
				return;
			}
			//将对应时间点之后的监测点改为失效
			this.memberMonitorTaskCardMapper.updateMemberMonitorTaskCard(taskIds);
		}
	}

	/**
	 * 添加微信监测方案消息
	 * @param memberMonitorPlan
	 */
	private void addMonitorWechatMessage(MemberMonitorPlanPO memberMonitorPlan){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("doctorId", memberMonitorPlan.getDoctorId());
		jsonObject.put("date", DateHelper.getNowDate());
		jsonObject.put("planName", memberMonitorPlan.getPlanName());
		jsonObject.put("applyExplain", memberMonitorPlan.getApplyExplain());
		jsonObject.put("illnessType" ,memberMonitorPlan.getIllnessType());
		AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
		addWechatMessageDTO.setForeignId(memberMonitorPlan.getPlanId());
		addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_MONITOR_PLAN);
		addWechatMessageDTO.setMemberId(memberMonitorPlan.getMemberId());
		addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
		this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
	}

	/**
	 * 下发监测方案对话
	 * @param memberMonitorPlan
	 */
	private void sendMonitorDialogue(MemberMonitorPlanPO memberMonitorPlan){

/*		CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
		countDoctorMemberDTO.setDoctorId(memberMonitorPlan.getDoctorId());
		countDoctorMemberDTO.setMemberId(memberMonitorPlan.getMemberId());
		long count = this.memberService.countMemberDoctor(countDoctorMemberDTO);
		if(count == 0){
			return;
		}*/
		DialoguePO dialoguePO = new DialoguePO();
		dialoguePO.setMemberId(memberMonitorPlan.getMemberId());
		dialoguePO.setDoctorId(memberMonitorPlan.getDoctorId());
		dialoguePO.setForeignId(memberMonitorPlan.getPlanId());
		dialoguePO.setPatientMsg(DIALOGUE_MONITOR_TITLE);
		dialoguePO.setDoctorMsg(DIALOGUE_MONITOR_TITLE);
		dialoguePO.setMsgType(DialogueConstant.DIALOGUE_MONITORPLAN_MSG_TYPE);
		dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
		dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
		dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
		dialoguePO.setSenderId(memberMonitorPlan.getSenderId());
		DialogueMonitorPlanBO dialogueMonitorPlanBO = new DialogueMonitorPlanBO();
		dialogueMonitorPlanBO.setContent(DIALOGUE_MONITOR_CONTENT);
		dialogueMonitorPlanBO.setDate(DateHelper.getToday());
		dialogueMonitorPlanBO.setLogId(memberMonitorPlan.getPlanId());
		dialogueMonitorPlanBO.setName(memberMonitorPlan.getPlanName());
		//监测方案 类型
		dialogueMonitorPlanBO.setTextType(memberMonitorPlan.getEohType());
		dialogueMonitorPlanBO.setTime(DateHelper.getDate(new Date(),"HH:mm:ss"));
		dialogueMonitorPlanBO.setTitle(DIALOGUE_MONITOR_TITLE);
		dialoguePO.setDataStr(JSON.toJSONString(dialogueMonitorPlanBO));
		this.dialogueService.addDialogue(dialoguePO);
	}

	private final static String DIALOGUE_MONITOR_TITLE = "监测方案设置通知";
	private final static String DIALOGUE_MONITOR_CONTENT = "为患者制定了监测方案";

    @Override
    public List<MemberSugarCodeNumberPO> listHasMonitorPeopleNumber(Map<String, Object> map) {
        return this.memberMonitorPlanMapper.listHasMonitorPeopleNumber(map);
    }

    @Override
    public List<MemberSugarCodeNumberPO> listHasMonitorAndRecordPeopleNumber(Map<String, Object> map) {
        return this.memberMonitorPlanMapper.listHasMonitorAndRecordPeopleNumber(map);
    }


	@Override
	public List<SchemaPO> listSchemaPoOfMember(SchemaPO po) {
		return this.memberMonitorPlanMapper.listSchemaPoOfMember(po);
	}

	@Override
	public List<TodaySchemaModel> listTodaySchemaOfMember(List<String> midList,Integer illnessType) {
		SchemaPO paramPo = new SchemaPO();
		paramPo.setIllnessType(1);  //糖尿病监测方案
		paramPo.setMidList(midList);
		List<SchemaPO> schemaPOS = this.listSchemaPoOfMember(paramPo);
		List<TodaySchemaModel> todaySchemaModels = new ArrayList<TodaySchemaModel>(schemaPOS.size());
		for(SchemaPO schemaPO : schemaPOS){
			TodaySchemaModel todaySchemaModel = new TodaySchemaModel();
			todaySchemaModel.setMemberId(schemaPO.getMemberId());
			todaySchemaModel.setPlanType(schemaPO.getPlanType());
			todaySchemaModel.setPlanDetail(schemaPO.getPlanDetail());
			todaySchemaModel.setTodayPlan(this.getTodySchemaDetailOfPlan(schemaPO));
			todaySchemaModels.add(todaySchemaModel);
		}
		return todaySchemaModels;
	}

	@Override
	public Long getTestSugarNumberOfSchema(SchemaPO po){
		return this.memberMonitorPlanMapper.getTestSugarNumberOfSchema(po);
	}

	@Override
	public Long getTestSugarNumberOfMember(SchemaPO po) {
		return this.memberMonitorPlanMapper.getTestSugarNumberOfMember(po);
	}

	@Override
	public List<String> getHasTestSchemaOfMemberId(SchemaPO po) {
		return this.memberMonitorPlanMapper.getHasTestSchemaOfMemberId(po);
	}

	@Override
	public Map<String,Object> listMonitorPlanTemplate(Integer monitorType,Integer eohType,String memberId ,String doctorId,String hospitalId,boolean switchFlag) {
		Map<String,Object> map = new HashMap<>(2);
		List<SugarMonitorTemplatePO> list = new ArrayList<>();
		if (!switchFlag){
			//加载权限为个人的模板
			ListMonitorPlanTemplateMapperDTO listMonitorPlanTemplateMapperDTO = new ListMonitorPlanTemplateMapperDTO();
			listMonitorPlanTemplateMapperDTO.setMonitorType(monitorType);
			List<String> doctorList = new ArrayList<>();
			doctorList.add(doctorId);
			listMonitorPlanTemplateMapperDTO.setDoctorIdList(doctorList);
			listMonitorPlanTemplateMapperDTO.setPermission(MonitorPlanConstant.EMPLATE_PLAN_PERMISSION_PERSION);
			listMonitorPlanTemplateMapperDTO.setEohType(eohType);  //适用类型0非妊娠,1妊娠
			List<SugarMonitorTemplatePO> list1 = this.memberMonitorPlanMapper.listMonitorPlanTemplate(listMonitorPlanTemplateMapperDTO);
			list.addAll(list1);

			//加载权限为全院的模板
			ListMonitorPlanTemplateMapperDTO dto = new ListMonitorPlanTemplateMapperDTO();
			dto.setMonitorType(monitorType);
			dto.setPermission(MonitorPlanConstant.EMPLATE_PLAN_PERMISSION_ALL);
			dto.setHospitalId(hospitalId);
			dto.setEohType(eohType);//适用类型0非妊娠,1妊娠
			List<SugarMonitorTemplatePO> list2 = this.memberMonitorPlanMapper.listMonitorPlanTemplate(dto);
			list.addAll(list2);
		}

		//加载系统模板
		ListMonitorPlanTemplateMapperDTO dto1 = new ListMonitorPlanTemplateMapperDTO();
		dto1.setMonitorType(monitorType);
		dto1.setPlanType(MonitorPlanConstant.TEMPLATE_PLAN_TYPE_SYSTEM);
		dto1.setEohType(eohType);//适用类型0非妊娠,1妊娠
		List<SugarMonitorTemplatePO> list3 = this.memberMonitorPlanMapper.listSystemMonitorPlanTemplate(dto1);
		list.addAll(list3);

		String currentPlan = "";
		GetMemberMonitorDTO getMemberMonitorDTO = new GetMemberMonitorDTO();
		getMemberMonitorDTO.setMemberId(memberId);
		getMemberMonitorDTO.setInProgress(MonitorPlanConstant.PLAN_IN_PROGRESS_YES);
		getMemberMonitorDTO.setEohType(eohType);
		MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanMapper.getMemberMonitorPlan(getMemberMonitorDTO);
//		if(memberMonitorPlanPO != null &&  MonitorPlanConstant.PLAN_TYPE_LONG == memberMonitorPlanPO.getPlanType()){
//			currentPlan = memberMonitorPlanPO.getPlanName();
//		}
		map.put("planList", list);
		map.put("currentPlan", memberMonitorPlanPO);
		return map;
	}

	@Override
	public PageResult<MemberMonitorPlanPO> listMonitorPlanHistory(String memberId, PageRequest pageRequestModel) {
		PageHelper.startPage(pageRequestModel.getPage(), pageRequestModel.getRows());
		Map<String,Object> queryMap = new HashMap<>(2);
		queryMap.put("planType", MonitorPlanConstant.PLAN_TYPE_LONG);
		queryMap.put("memberId", memberId);
		List<MemberMonitorPlanPO> list = this.memberMonitorPlanMapper.listMonitorPlanHistory(queryMap);
		return new PageResult<>(list);
	}

    @Override
    public List<MemberCodeBO> getMemberCodeModelBySchema(SchemaPO schemaPO) {
        return this.memberMonitorPlanMapper.getMemberCodeModelBySchema(schemaPO);
    }

	@Override
	public String addMonitorPlanTemplate(SugarMonitorTemplatePO sugarMonitorTemplatePO) {
		sugarMonitorTemplatePO.setSid(DaoHelper.getSeq());
		sugarMonitorTemplatePO.setMonitorType(MonitorPlanConstant.MONITOR_TYPE_WEB);
		sugarMonitorTemplatePO.setPlanType(MonitorPlanConstant.TEMPLATE_PlAN_TYPE_CUSTOM);
		sugarMonitorTemplatePO.setTreatment(Integer.parseInt(Constant.DEFAULT_FOREIGN_ID));
		this.memberMonitorPlanMapper.addMonitorPlanTemplate(sugarMonitorTemplatePO);
		return sugarMonitorTemplatePO.getSid();
	}

	@Override
	public List<SugarMonitorTemplatePO> listCustomMonitorPlanTemplate(String doctorId,String hospitalId,Integer eohType) {
    	//加载权限为个人的模板
		List<String> doctorList = new ArrayList<>();
		doctorList.add(doctorId);
		ListMonitorPlanTemplateMapperDTO listMonitorPlanTemplateMapperDTO = new ListMonitorPlanTemplateMapperDTO();
		listMonitorPlanTemplateMapperDTO.setDoctorIdList(doctorList);
		listMonitorPlanTemplateMapperDTO.setPermission(MonitorPlanConstant.EMPLATE_PLAN_PERMISSION_PERSION);
		listMonitorPlanTemplateMapperDTO.setEohType(eohType);//适用类型0非妊娠,1妊娠
		List<SugarMonitorTemplatePO> list1 = this.memberMonitorPlanMapper.listMonitorPlanTemplate(listMonitorPlanTemplateMapperDTO);
		//加载权限为全院的模板
		ListMonitorPlanTemplateMapperDTO dto = new ListMonitorPlanTemplateMapperDTO();
		dto.setPermission(MonitorPlanConstant.EMPLATE_PLAN_PERMISSION_ALL);
		dto.setHospitalId(hospitalId);
		dto.setEohType(eohType);//适用类型0非妊娠,1妊娠
		List<SugarMonitorTemplatePO> list2 = this.memberMonitorPlanMapper.listMonitorPlanTemplate(dto);

		List<SugarMonitorTemplatePO> result = new ArrayList<>();
		result.addAll(list1);
		result.addAll(list2);
		return result;
	}

	@Override
	public void updateCustomMonitorPlanTemplate(SugarMonitorTemplatePO sugarMonitorTemplatePO) {
		this.memberMonitorPlanMapper.updateCustomMonitorPlanTemplate(sugarMonitorTemplatePO);
	}

	/*****private-utils*****/
	/**
	 * 返回今日计划字符串","分割
	 * @param po
	 * @return
	 */
	private String getTodySchemaDetailOfPlan(SchemaPO po){
		String planType = po.getPlanType();
		String todaySchemaDetail = null;
		String detail = po.getPlanDetail();
		String longPlanType = "1";
		//长期
		if(longPlanType.equals(planType)){
			String weekStr = DateHelper.getWeekCode()+"-";
			String[] arrPlan = detail.split(";");
			StringBuilder sb = new StringBuilder();
			for(String iplan : arrPlan){
				if(iplan.indexOf(weekStr)>-1){
					sb.append(iplan.replace(weekStr,"")+",");
				}
			}
			return sb.toString();
		} else {
			String tempPlanType = "2";
			//临时
			if(tempPlanType.equals(planType)){
				String today = DateHelper.getToday().replace("-","")+"-";
				String[] arrPlan = detail.split(";");
				StringBuilder sb = new StringBuilder();
				for(String iplan : arrPlan){
					if(iplan.indexOf(today)>-1){
						sb.append(iplan.replace(today,"")+",");
					}
				}
				return sb.toString();
			}
		}

		return todaySchemaDetail;
	}

	@Override
	public MemberMonitorPlanPO getNewBloodMonitorPlan(SynthesizeDataDTO synthesizeDataDTO) {
		List<MemberMonitorPlanPO> list = this.memberMonitorPlanMapper.getBloodMonitorPlan(synthesizeDataDTO);
		MemberMonitorPlanPO planPO = new MemberMonitorPlanPO();
		if (list != null && list.size() > 0){
			planPO = list.get(0);
		}
		return planPO;
	}

	@Override
	public PageResult<SugarMonitorTemplatePO> listOperateMonitorTemplate(PageRequest page, String doctorId,String keyword) {
		List<String> doctorList = new ArrayList<>();
		doctorList.add(doctorId);
		PageHelper.startPage(page.getPage(),page.getRows());
		List<SugarMonitorTemplatePO> pos = this.memberMonitorPlanMapper.listMonitorTemplateByPerson(doctorList,keyword);
		PageResult<SugarMonitorTemplatePO> rePage = new PageResult<SugarMonitorTemplatePO>(pos);
		List<SugarMonitorTemplatePO> rows = rePage.getRows();
		if (null != rows && rows.size() > 0) {
			for (int i = 0; i < rows.size(); i++) {
				SugarMonitorTemplatePO po = rows.get(i);
				//获取模板所属医生姓名
				DoctorPO doctorPO = this.doctorService.getDoctorById(po.getDoctorId());
				if (null != doctorPO){
					po.setDoctorName(doctorPO.getDoctorName());
				}
			}
		}
		return rePage;
	}

	@Override
	public void deleteMonitorTemplate(String ids) {
		String[] split = ids.split(",");
		List<String> list = new ArrayList<>();
		for (String s : split) {
			list.add(s);
		}
		DeleteMonitorTemplateDTO dto = new DeleteMonitorTemplateDTO();
		dto.setIds(list);
		this.memberMonitorPlanMapper.deleteMonitorTemplate(dto);
	}

	@Override
	public SugarMonitorTemplatePO getMonitorTemplateById(String sid) {
		return memberMonitorPlanMapper.getMonitorTemplateById(sid);
	}

	@Override
	public Map<String, Object> listTodayTask(String doctorId ,String paramCode ,String departmentId) {
		DoctorPO doctor = this.doctorService.getDoctorById(doctorId);
		if(departmentId == null){
			departmentId = doctor.getDepartId();
		}
		List<TodayTaskDetailListVO> list = this.memberMonitorPlanMapper.listInHospitalTodayTask(departmentId
				,DateHelper.getToday() ,MonitorPlanConstant.ILLNESS_TYPE_DIABETES ,todayListTaskParamCodeHandler(paramCode));
		MultiValueMap<String ,TodayTaskDetailListVO> dateMap = new LinkedMultiValueMap<>();
		for(TodayTaskDetailListVO item : list){
			dateMap.add(item.getParamCode() ,item);
		}
		List<TodayTaskListVO> bloodSugarTasksList = new ArrayList<>();
		TodayTaskListVO bloodSugarTasks;
		for(Map.Entry<String ,List<TodayTaskDetailListVO>> entry : dateMap.entrySet()){
			if(TASK_PARAM_CODE_MAP.containsKey(entry.getKey())){
				bloodSugarTasks = new TodayTaskListVO();
//				entry.getValue().sort(Comparator.comparing(TodayTaskDetailListVO::getBedNo));
				bloodSugarTasks.setParamCode(TASK_PARAM_CODE_MAP.get(entry.getKey()));
				bloodSugarTasks.setMemberList(entry.getValue());
				bloodSugarTasksList.add(bloodSugarTasks);
			}
		}
		Map<String ,Object> result = new HashMap<>();
		result.put("bloodSugarTasks" ,bloodSugarTasksList);
		//加载血糖检测任务
		return result;
	}

	/**
	 * 处理血糖时间点
	 * @param time
	 * @return
	 */
	public Integer monitorDt(String time){
		Integer dt = null;
		Date date = DateHelper.date(time);
		Date timeStart = DateHelper.date("00:01");
		Date timeEnd = DateHelper.date("04:00");
		if (DateHelper.isEffectiveDate(date,timeStart,timeEnd)){
			dt = 1;
		}
		Date timeStart1 = DateHelper.date("04:01");
		Date timeEnd1 = DateHelper.date("08:00");
		if (DateHelper.isEffectiveDate(date,timeStart1,timeEnd1)){
			dt = 2;
		}
		Date timeStart2 = DateHelper.date("08:01");
		Date timeEnd2 = DateHelper.date("10:00");
		if (DateHelper.isEffectiveDate(date,timeStart2,timeEnd2)){
			dt = 3;
		}
		Date timeStart3 = DateHelper.date("10:01");
		Date timeEnd3 = DateHelper.date("12:00");
		if (DateHelper.isEffectiveDate(date,timeStart3,timeEnd3)){
			dt = 4;
		}
		Date timeStart4 = DateHelper.date("12:01");
		Date timeEnd4 = DateHelper.date("15:00");
		if (DateHelper.isEffectiveDate(date,timeStart4,timeEnd4)){
			dt = 5;
		}
		Date timeStart5 = DateHelper.date("15:01");
		Date timeEnd5 = DateHelper.date("18:00");
		if (DateHelper.isEffectiveDate(date,timeStart5,timeEnd5)){
			dt = 6;
		}
		Date timeStart6 = DateHelper.date("18:01");
		Date timeEnd6 = DateHelper.date("20:00");
		if (DateHelper.isEffectiveDate(date,timeStart6,timeEnd6)){
			dt = 7;
		}
		Date timeStart7 = DateHelper.date("20:01");
		Date timeEnd7 = DateHelper.date("24:00");
		if (DateHelper.isEffectiveDate(date,timeStart7,timeEnd7)){
			dt = 8;
		}
		return dt;
	}

	/**
	 * 处理血压的时间点
	 * @param time
	 * @return
	 */
	public Integer bloodPressure(String time){
		Integer dt = null;
		String nowTime = DateHelper.getToday()+" "+time;
		Date nowDt = DateHelper.dateTime(nowTime);
		String startTime = DateHelper.getToday()+" 04:01";
		Date startDt = DateHelper.dateTime(startTime);
		String endTime = DateHelper.getToday()+" 09:00";
		Date endDt = DateHelper.dateTime(endTime);
		if (DateHelper.isEffectiveDate(nowDt,startDt,endDt)){
			dt = 9;
		}
		String startTime2 = DateHelper.getToday()+" 09:01";
		String endTime2 = DateHelper.getToday()+" 12:00";
		Date nowDt1 = DateHelper.dateTime(nowTime);
		Date startDt1 = DateHelper.dateTime(startTime2);
		Date endDt1 = DateHelper.dateTime(endTime2);
		if (DateHelper.isEffectiveDate(nowDt1,startDt1,endDt1)){
			dt = 10;
		}

		String startTime3 = DateHelper.getToday()+" 12:01";
		String endTime3 = DateHelper.getToday()+" 18:00";
		Date nowDt2 = DateHelper.dateTime(nowTime);
		Date startDt2 = DateHelper.dateTime(startTime3);
		Date endDt2 = DateHelper.dateTime(endTime3);
		if (DateHelper.isEffectiveDate(nowDt2,startDt2,endDt2)){
			dt = 11;
		}
		String startTime4 = DateHelper.getToday()+" 18:01";
		String today = DateHelper.getToday();
		String endTime4 = DateHelper.plusDate(today,1)+" 04:00";
		Date nowDt3 = DateHelper.dateTime(nowTime);
		Date startDt3 = DateHelper.dateTime(startTime4);
		Date endDt3 = DateHelper.dateTime(endTime4);
		if (DateHelper.isEffectiveDate(nowDt3,startDt3,endDt3)){
			dt = 12;
		}
		return dt;
	}

	/**
	 * 处理时间点之前的任务卡为失效
	 * @param planId
	 *
	 */
	public void stopBeforeTaskCard(String planId){
		//血糖记录->监测方案
		MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanMapper.getMemberMonitorPlanBySid(planId);
		if (memberMonitorPlanPO != null){
			List<String> taskIds = new ArrayList<>();
			String time = memberMonitorPlanPO.getModifyDt().substring(11,16);
			//1:取该方案停止的时间点
			if (memberMonitorPlanPO.getIllnessType() == 1){ //血糖
				//获取这停止的时间点之后的数据,将后面时间点及以后的修改为无效
				int ad = monitorDt(time);
				List<MemberMonitorTaskCardPO> taskCardPOS = this.memberMonitorTaskCardMapper.listBeforeMemberMonitorTaskCard(planId,monitorDt(time));
				for (MemberMonitorTaskCardPO monitorTaskCardPO : taskCardPOS){
					taskIds.add(monitorTaskCardPO.getSid());
				}
			}else{ //血压
				List<MemberMonitorTaskCardPO> taskCardPOS = this.memberMonitorTaskCardMapper.listBeforeMemberMonitorTaskCard(planId,bloodPressure(time));
				for (MemberMonitorTaskCardPO monitorTaskCardPO : taskCardPOS){
					taskIds.add(monitorTaskCardPO.getSid());
				}
			}
			if (taskIds == null || "".equals(taskIds)){
				return;
			}
			//将对应时间点之后的监测点改为失效
			this.memberMonitorTaskCardMapper.updateMemberMonitorTaskCard(taskIds);
		}
	}

	private String todayListTaskParamCodeHandler(String paramCode){
		if(StringUtils.isBlank(paramCode)){
			return null;
		}
		return PARAM_CODE_TASK_MAP.get(paramCode);
	}

	/**
	 * 血糖监测方案的paramCode 和 血糖的paramCode映射
	 */
	final static Map<String ,String> TASK_PARAM_CODE_MAP = new HashMap<>();
	static {
		TASK_PARAM_CODE_MAP.put("1" , SignConstant.PARAM_CODE_BEFORE_DAWN);
		TASK_PARAM_CODE_MAP.put("2" , SignConstant.PARAM_CODE_BEFORE_BREAKFAST);
		TASK_PARAM_CODE_MAP.put("3" , SignConstant.PARAM_CODE_AFTER_BREAKFAST);
		TASK_PARAM_CODE_MAP.put("4" , SignConstant.PARAM_CODE_BEFORE_LUNCH);
		TASK_PARAM_CODE_MAP.put("5" , SignConstant.PARAM_CODE_AFTER_LUNCH);
		TASK_PARAM_CODE_MAP.put("6" , SignConstant.PARAM_CODE_BEFORE_DINNER);
		TASK_PARAM_CODE_MAP.put("7" , SignConstant.PARAM_CODE_AFTER_DINNER);
		TASK_PARAM_CODE_MAP.put("8" , SignConstant.PARAM_CODE_BEFORE_SLEEP);
	}

	final static Map<String ,String> PARAM_CODE_TASK_MAP = new HashMap<>();
	static {
		TASK_PARAM_CODE_MAP.forEach((x ,y) ->{
			PARAM_CODE_TASK_MAP.put(y ,x);
		});
	}
}
