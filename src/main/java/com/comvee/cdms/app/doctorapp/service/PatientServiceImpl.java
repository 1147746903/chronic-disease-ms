package com.comvee.cdms.app.doctorapp.service;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.app.doctorapp.model.app.BloodSugarCountModel;
import com.comvee.cdms.app.doctorapp.model.app.MemberListAndGroupRespModel;
import com.comvee.cdms.app.doctorapp.model.app.MemberListByGroupIdRespModel;
import com.comvee.cdms.app.doctorapp.model.app.MemberModel;
import com.comvee.cdms.app.doctorapp.vo.CountFollowReqVO;
import com.comvee.cdms.bloodmonitor.dto.GetMemberMonitorDTO;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorPlanMapper;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.checkresult.service.CheckoutServiceI;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.differentlevels.service.DifferentLevelsService;
import com.comvee.cdms.differentlevels.vo.MemberCurrentDiffLevelVO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.cfg.GroupConstant;
import com.comvee.cdms.doctor.dto.AddGroupMapperDTO;
import com.comvee.cdms.doctor.dto.UpdateGroupDTO;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.po.DoctorGroupPO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.follow.mapper.FollowMapper;
import com.comvee.cdms.hospital.model.bo.CheckinInfoBO;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.bo.RangeDialogueBO;
import com.comvee.cdms.member.bo.RangeDialogueDataBO;
import com.comvee.cdms.member.constant.ControlTargetConstant;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.DoctorMemberPO;
import com.comvee.cdms.member.po.MemberDrugItemPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.packages.dto.ListValidMemberPackageDTO;
import com.comvee.cdms.packages.mapper.PackageMapper;
import com.comvee.cdms.packages.po.MemberPackagePO;
import com.comvee.cdms.packages.po.PackagePO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.mapper.BloodSugarMapper;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("patientAppService")
public class PatientServiceImpl implements PatientServiceI{
 
	private final static Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private PackageMapper packageMapper;	
	
	@Autowired
	private DoctorMapper doctorMapper;	
	
	@Autowired
	private BloodSugarMapper bloodSugarMapper;		
	
    @Autowired
    private MemberService memberService;	
    
    @Autowired
    private PrescriptionMapper prescriptionMapper;
    
    @Autowired
    private FollowMapper followMapper;
    
    @Autowired
    private MemberMonitorPlanMapper memberMonitorPlanMapper;
    
    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private PackageService packageService;

	@Autowired
	private DrugsMemberServiceI drugsMemberService;

	@Autowired
	private CheckoutServiceI checkoutService;

    @Autowired
	private com.comvee.cdms.doctor.service.DoctorServiceI doctorService;

    @Autowired
	private DifferentLevelsService differentLevelsService;

    @Autowired
	private HospitalService hospitalService;

	@Override
	public Map<String,Object> loadMemberListAndGroup(String doctorId, PageRequest page)  {
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//加载医生列表
		List<DoctorPO> doctorList = this.doctorService.listMyDoctor(doctorId);
		List<String> doctorIdList = doctorList.stream().map(DoctorPO::getDoctorId).collect(Collectors.toList());

		//是否付费 - 获取所有患者 含packageid
		PageHelper.startPage(page.getPage(), page.getRows());
		List<MemberListByGroupIdRespModel> list = this.memberMapper.loadMemberListByGroupId(doctorIdList, null , null , null);
		PageResult<MemberListByGroupIdRespModel> pageResult = new PageResult(list);
		for (MemberListByGroupIdRespModel memberListByGroupIdRespModel : list) {
			memberPackageHandler(memberListByGroupIdRespModel);
			String birthday = memberListByGroupIdRespModel.getBirthday()==null?"":memberListByGroupIdRespModel.getBirthday();
			if(!StringUtils.isBlank(birthday)){
				int age = DateHelper.getAge(birthday);
				memberListByGroupIdRespModel.setAge(age > 150 ? "":String.valueOf(age));
			}
		}
		
		resultMap.put("memberList", pageResult);
		if(page.getPage() == 1){
			//逻辑 - 获取所有分组信息  -  分组每组获取第一页信息s
			List<MemberListAndGroupRespModel> groupList = listDoctorGroupInfo(doctorIdList);
			resultMap.put("groupList", groupList);//获取到啊分组

			resultMap.put("doctorList", doctorList);

			//获取套餐t_package
			List<PackagePO> packageList = this.packageMapper.getAllPackage(doctorIdList);
			PackagePO  ppo = new PackagePO();
			ppo.setPackageId("");
			ppo.setPackageName("免费");
			packageList.add(ppo);

			resultMap.put("packageList", packageList);
		}

		resultMap.put("returnDate", String.valueOf(System.currentTimeMillis())); //返回时间
		return resultMap;
	}

	/**
	 * 付费状态处理
	 * @param memberId
	 * @param doctorId
	 * @return
	 */
	private void memberPackageHandler(MemberListByGroupIdRespModel memberListByGroupIdRespModel ){
		ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
		listValidMemberPackageDTO.setMemberId(memberListByGroupIdRespModel.getMemberId());
		listValidMemberPackageDTO.setDoctorId(memberListByGroupIdRespModel.getDoctorId());
		List<MemberPackagePO> list = this.packageService.listValidMemberPackage(listValidMemberPackageDTO);
		if(list != null || list.size() > 0){
			String packageIdList = Joiner.on(",")
					.join(
							list.stream()
									.map(MemberPackagePO::getPackageId)
									.collect(Collectors.toList())
					);
			memberListByGroupIdRespModel.setPackageIds(packageIdList);
		}

	}


	/**
	 * 加载医生分组信息（统计患者数量）
	 * @param doctorIdList
	 * @return
	 */
	private List<MemberListAndGroupRespModel> listDoctorGroupInfo(List<String> doctorIdList){
		List<MemberListAndGroupRespModel> groupList = this.memberMapper.loadMemberListAndGroup(doctorIdList , null , null);
		doctorIdList.forEach( x -> {
			CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
			countDoctorMemberDTO.setDoctorId(x);
			countDoctorMemberDTO.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
			long count = this.memberMapper.countMemberDoctor(countDoctorMemberDTO);
			MemberListAndGroupRespModel memberListAndGroupRespModel = new MemberListAndGroupRespModel();
			memberListAndGroupRespModel.setNum(String.valueOf(count));
			memberListAndGroupRespModel.setGroupName(GroupConstant.DEFAULT_GROUP_NAME);
			memberListAndGroupRespModel.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
			memberListAndGroupRespModel.setDoctorId(x);
			groupList.add(0, memberListAndGroupRespModel);
		});
		return groupList;
	}

	@Override
	public Map<String, Object> refreshMemberListAndGroup(String doctorId, PageRequest page, String lastDate,
			String groupType)  {
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(!StringUtils.isBlank(lastDate)){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = sdf.parse(lastDate);
				Long times = date.getTime()-100000;
				lastDate = sdf.format(new Date(times));
			} catch (ParseException e) {
				logger.error("格式化日期异常" ,e);
			}
		}		
		


		//加载医生列表
		List<DoctorPO> doctorList = this.doctorService.listMyDoctor(doctorId);
		List<String> doctorIdList = doctorList.stream().map(DoctorPO::getDoctorId).collect(Collectors.toList());

		List<MemberListAndGroupRespModel> groupList = listDoctorGroupInfo(doctorIdList);
		resultMap.put("groupList", groupList);
		//是否付费 - 获取所有患者 含packageid
		List<MemberListByGroupIdRespModel> list = this.memberMapper.loadMemberListByGroupId(doctorIdList, null , lastDate , null);
		for (MemberListByGroupIdRespModel memberListByGroupIdRespModel : list) {
			memberPackageHandler(memberListByGroupIdRespModel);
			String birthday = memberListByGroupIdRespModel.getBirthday()==null?"":memberListByGroupIdRespModel.getBirthday();
			if(!StringUtils.isBlank(birthday)){
				int age = DateHelper.getAge(birthday);
				memberListByGroupIdRespModel.setAge(age > 150 ? "":String.valueOf(age));
			}
		}
		PageResult<MemberListByGroupIdRespModel> pageResult = new PageResult(list);
		resultMap.put("memberList", pageResult);
		
		//获取套餐t_package
		List<PackagePO> packageList = this.packageMapper.getAllPackage(doctorIdList);
		PackagePO  ppo = new PackagePO();
		ppo.setPackageId("");
		ppo.setPackageName("免费");
		packageList.add(ppo);
		resultMap.put("doctorList" , doctorList);
		resultMap.put("packageList", packageList);
		resultMap.put("returnDate", DateHelper.getDate(new Date(), "yyyy-MM-dd HH:mm:ss")); //返回时间
		return resultMap;
	}

	@Override
	public void getGroupInfoByType(String groupType , String doctorId) {
		
		
	}
	
	@Override
	public Map<String, Object> loadDoctorGrouping(String doctorId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//加载医生列表
		List<DoctorPO> doctorList = this.doctorService.listMyDoctor(doctorId);
		List<String> doctorIdList = doctorList.stream().map(DoctorPO::getDoctorId).collect(Collectors.toList());
		//分组
		List<MemberListAndGroupRespModel> groupList = listDoctorGroupInfo(doctorIdList);
		resultMap.put("groupList", groupList);		
		
		return resultMap;
	}	

	@Override
	public AddGroupMapperDTO addDoctorGrouping(String doctorId , String groupName) {
		String groupId = DaoHelper.getSeq();
		AddGroupMapperDTO addGroupDTO= new AddGroupMapperDTO();
		addGroupDTO.setGroupId(groupId);
		addGroupDTO.setDoctorId(doctorId);;
		addGroupDTO.setGroupName(groupName);
		this.doctorMapper.addGroup(addGroupDTO);
		return addGroupDTO;
	}

	@Override
	public void deleteDoctorGrouping(String groupId) {
        DoctorGroupPO doctorGroupPO = this.doctorMapper.getGroupById(groupId);
        if(doctorGroupPO == null){
            throw new BusinessException("分组不存在，请确认");
        }		
        UpdateDoctorMemberDTO updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
        updateDoctorMemberDTO.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
        updateDoctorMemberDTO.setGroupIdWhere(groupId);
        this.memberService.updateMemberDoctor(updateDoctorMemberDTO);		
		
        //删除分组
        this.doctorMapper.deleteGroup(groupId);
	}

	@Override
	public void editDoctorGrouping(String groupName, String groupId) {
		UpdateGroupDTO updateGroupDTO = new UpdateGroupDTO();
		updateGroupDTO.setGroupId(groupId);
		updateGroupDTO.setGroupName(groupName);
		this.doctorMapper.updateGroup(updateGroupDTO);		
	}

	@Override
	public MemberModel memberDetailNew(String memberId, String doctorId) {
		GetMemberDTO getMemberDTO = new GetMemberDTO();
		getMemberDTO.setMemberId(memberId);
		MemberPO memberPO = this.memberMapper.getMember(getMemberDTO);//获取患者信息 
		
		MemberModel memberModel = new MemberModel();
		BeanUtils.copyProperties(memberModel, memberPO);
		String bmi = memberModel.getBmi();
		int bmiLevel = assertBmiAbnormal(bmi);
		memberModel.setBmiLevel(String.valueOf(bmiLevel));
		
		if(!StringUtils.isBlank(memberModel.getBirthday())) {
			int age = DateHelper.getAge((String) memberModel.getBirthday());
			memberModel.setAge(String.valueOf(age> 150 ? "--":age));
		}

		//计算患糖尿病年限
		String diabetesDate = memberModel.getDiabetesDate();
		if(!StringUtils.isBlank(diabetesDate)) {
			memberModel.setHasSuggerTime(DateHelper.getAge(diabetesDate) + "年");
		}
		//用药方案
		MemberDrugItemPO drugsMemberNew = drugsMemberService.getDrugsMemberNew(memberId, doctorId);
		if(null!=drugsMemberNew){
			memberModel.setMemberDrug(drugsMemberNew);
		}


		//统计一周血糖的数据
		String startDt = null;
		try {
			startDt = DateHelper.getNeedStartDateTime(1);//统计一周的数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		String endDt = DateHelper.getSystemDate();
		List<BloodSugarCountModel> bloodNumStaticsList = this.bloodSugarMapper.getBloodNumForType(null, startDt, endDt, memberId);
		Map<Integer,Long> bloodNumStaticsMap = bloodNumStaticsList.stream().collect(Collectors.toMap(BloodSugarCountModel::getParamLevel, BloodSugarCountModel::getNumber));
		Map<String, Object> bloodNumStaticsResult = new HashMap<>();
		bloodNumStaticsResult.put("highTotalNum", bloodNumStaticsMap.getOrDefault(SignConstant.SIGN_LEVEL_HIGH, 0L));
		bloodNumStaticsResult.put("normalTotalNum", bloodNumStaticsMap.getOrDefault(SignConstant.SIGN_LEVEL_NORMAL, 0L));
		bloodNumStaticsResult.put("lowTotalNum", bloodNumStaticsMap.getOrDefault(SignConstant.SIGN_LEVEL_LOW, 0L));
		memberModel.setBloodNumStaticsMap(bloodNumStaticsResult);
		
		//获取糖化值
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("memberId" ,memberId);
		paramMap.put("checkoutName" , "糖化血红蛋白");
		String hemoglobin = this.checkoutService.getNewestCheckoutResult(paramMap);
		memberModel.setHemoglobin(hemoglobin);

		DoctorPO doctor = this.doctorService.getDoctorById(doctorId);

		//血糖监测schemeName
		GetMemberMonitorDTO getMemberMonitorDTO = new GetMemberMonitorDTO();
		getMemberMonitorDTO.setMemberId(memberId);
		getMemberMonitorDTO.setInProgress(1);
		getMemberMonitorDTO.setIllnessType(1);
		MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanMapper.getMemberMonitorPlan(getMemberMonitorDTO);
		if(memberMonitorPlanPO!=null) {
			memberModel.setSchemeName(memberMonitorPlanPO.getPlanName());
		}
		
		//控制目标的获取
		RangeBO rangeBO = this.memberService.getMemberRange(memberId);
		if(rangeBO != null) {
			memberModel.setLowEmpty(rangeBO.getLowBeforeBreakfast());
			memberModel.setHighEmpty(rangeBO.getHighBeforeBreakfast());
			memberModel.setLowFull(rangeBO.getLowAfterMeal());
			memberModel.setHighFull(rangeBO.getHighAfterMeal());
			memberModel.setHighHemoglobin(rangeBO.getHighGlycosylatedVal());
			memberModel.setLowHemoglobin(rangeBO.getLowGlycosylatedVal());
		}
		//没有用药  化验单数
        // 管理处方数量t_prescription
        Long eohNum = this.prescriptionMapper.countPrescriptionForMember(memberId);
        memberModel.setEohNum(String.valueOf(eohNum));

        //新加随访数
        
        CountFollowReqVO countFollowReq = new CountFollowReqVO();
        countFollowReq.setMemberId(memberId);
        Long followNum = this.followMapper.countFollowForMember(countFollowReq);
        memberModel.setFollowNum(String.valueOf(followNum));

		ListDoctorMemberDTO listDoctorMemberDTO = new ListDoctorMemberDTO();
		listDoctorMemberDTO.setDoctorId(doctorId);
		listDoctorMemberDTO.setMemberId(memberId);
		DoctorMemberPO doctorMember = this.memberService.getDoctorMember(listDoctorMemberDTO);
		if(doctorMember != null){
			memberModel.setPriceFlag(doctorMember.getPriceFlag());
		}else{
			memberModel.setPriceFlag(String.valueOf(MemberDoctorConstant.PAY_STATUS_NO));
		}

		String hospitalId = doctor.getHospitalId();
		MemberCurrentDiffLevelVO vo = this.differentLevelsService.getMemberCurrentDiffLevelResult(memberId,hospitalId);
		if(vo==null || vo.getLayer()==null){
			memberModel.setLayer(0);
		} else {
			memberModel.setLayer(vo.getLayer());
		}
		//是否住院
		CheckinInfoBO checkinInfoBO = this.hospitalService.getCheckinInfoBOByMid(memberId ,hospitalId);
		Integer inHos = 0;
		if(checkinInfoBO!=null && "1".equals(checkinInfoBO.getCheckinStatus())){
			inHos = 1;
		}
		memberModel.setInHos(inHos);
		memberModel.setDepartmentId(checkinInfoBO.getDepartmentId());
		memberModel.setDepartmentName(checkinInfoBO.getDepartmentName());
		memberModel.setHospitalNo(checkinInfoBO.getHospitalNo());
		memberModel.setBedNo(checkinInfoBO.getBedNo());
		return memberModel;
	}
	
	 /**
     * 申请添加医患关系的患者信息
     * @param idCard String
     * @return
     */
    @Override
    public MemberPO getDocPatientInfo(GetMemberDTO requestMember,String doctorId, String hospitalId){
    	//患者类型（1、未注册 2、已注册但与登录医生没有医患关系 3、已注册且与登录医生有医患关系）
    	String memberType = "1";
    	MemberPO returnMember = this.memberMapper.getMember(requestMember);
    	if(returnMember!=null && returnMember.getMemberId()!=null && !returnMember.getMemberId().trim().equals("")){
    		CountDoctorMemberDTO doctorMember = new CountDoctorMemberDTO();
    		doctorMember.setMemberId(returnMember.getMemberId().trim());
    		doctorMember.setDoctorId(doctorId.trim());
    		long haveDoctorMember = this.memberMapper.countMemberDoctor(doctorMember);
    		if(haveDoctorMember > 0){
    			memberType = "3";
    		} else {
    			memberType = "2";
    		}
    		returnMember.setMemberType(memberType);
    	} else {
    		returnMember = new MemberPO();
    		returnMember.setMemberType(memberType);
    	}
		//是否住院
		CheckinInfoBO checkinInfoBO = this.hospitalService.getCheckinInfoBOByMid(returnMember.getMemberId() , hospitalId);
		Integer inHos = 0;
		if(checkinInfoBO!=null && "1".equals(checkinInfoBO.getCheckinStatus())){
			inHos = 1;
		}
		returnMember.setInHos(inHos);
    	return returnMember;
    }

    /**
     * 添加医患关系
     * @param idCard String
     * @return
     */
    @Override
	public void addDoctorMember(String memberId,String doctorId,Integer relationWay){
		AddMemberDoctorRelateDTO dto = new AddMemberDoctorRelateDTO();
		dto.setDoctorId(doctorId);
		dto.setMemberId(memberId);
		dto.setGroupId("0");
		dto.setOperatorId(dto.getDoctorId());
		dto.setRelationWay(relationWay);
		memberService.addMemberDoctorRelate(dto);
	}
    
    /**
     * 患者注册并添加医患关系
     * @param doctorId String
     * @param member MemberPO
     * @return
     */
    @Override
    public void addMemberAndRelation(AddMemberDTO addMemberDTO,DoctorSessionBO doctorSessionBO){
    	String memberId = memberService.addMember(addMemberDTO, doctorSessionBO);
		AddMemberDoctorRelateDTO dto = new AddMemberDoctorRelateDTO();
		dto.setDoctorId(doctorSessionBO.getDoctorId());
		dto.setMemberId(memberId);
		dto.setGroupId("0");
		dto.setOperatorId(doctorSessionBO.getDoctorId());
		dto.setRelationWay(addMemberDTO.getRelationWay());
		memberService.addMemberDoctorRelate(dto);
	}
    
    /**
     * 判断BMI是否异常
     * @param addBmiMapperDTO
     */
    private int assertBmiAbnormal(String bmi){
    	if(StringUtils.isBlank(bmi)) {
    		bmi = "0.0" ;
    	}
        Float bmi_value = null;
    	try{ bmi_value = Float.parseFloat(bmi);}catch (Exception e){}
    	if(bmi_value == null){
			bmi_value = 0F;
		}
        if(ControlTargetConstant.BMI_HIGH < bmi_value){
            return SignConstant.SIGN_LEVEL_HIGH;
        }else if(ControlTargetConstant.BMI_LOW > bmi_value){
        	return SignConstant.SIGN_LEVEL_LOW;
        }else{
        	return SignConstant.SIGN_LEVEL_NORMAL;
        }
    }

	@Override
	public void editGroupMember(UpdateDoctorMemberDTO updateDoctorMemberDTO) {
        updateDoctorMemberDTO.setModifyTimeStamp(String.valueOf(System.currentTimeMillis()));
        this.memberMapper.updateDoctorMember(updateDoctorMemberDTO);
	}

	@Override
	public void addMemberRange(RangeBO rangeBO) {
        RangeBO memberRange = this.memberMapper.getMemberRange(rangeBO.getMemberId());
        
        if (null == memberRange) {
			rangeBO.setRangeId(DaoHelper.getSeq());
            this.memberMapper.addMemberRange(rangeBO);
        }else{
            this.memberMapper.modifyMemberRange(rangeBO);
        }
        
        memberRange = this.memberMapper.getMemberRange(rangeBO.getMemberId());
        memberRange.setDoctorId(rangeBO.getDoctorId());
		memberRange.setSenderId(rangeBO.getSenderId());
        sendRangeDialogue(memberRange);
	}    
	
    private void sendRangeDialogue(RangeBO rangeBO){
        if(StringUtils.isBlank(rangeBO.getDoctorId())){
            return;
        }
        boolean result = this.memberService.checkDoctorMemberRelationExists(rangeBO.getMemberId() ,rangeBO.getDoctorId());
        if(!result){
        	return;
		}
        DialoguePO dialoguePO = new DialoguePO();
        dialoguePO.setMemberId(rangeBO.getMemberId());
        dialoguePO.setDoctorId(rangeBO.getDoctorId());
        dialoguePO.setForeignId(rangeBO.getRangeId());
        dialoguePO.setSenderId(rangeBO.getSenderId());
        dialoguePO.setMsgType(DialogueConstant.DIALOGUE_TARGET_MSG_TYPE);
        dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        dialoguePO.setDoctorMsg(DIALOGUE_RANGE_MESSAGE);
        dialoguePO.setPatientMsg(DIALOGUE_RANGE_MESSAGE);
        RangeDialogueBO rangeDialogueBO = new RangeDialogueBO();
        rangeDialogueBO.setContent(DIALOGUE_RANGE_CONTENT);
        rangeDialogueBO.setDate(DateHelper.getToday());
        rangeDialogueBO.setLogId(rangeBO.getRangeId());
        rangeDialogueBO.setName(DIALOGUE_RANGE_TITLE);
        rangeDialogueBO.setTime(DateHelper.getDate(new Date(),"hh:mm:ss"));
        rangeDialogueBO.setTitle(DIALOGUE_RANGE_MESSAGE);
        rangeDialogueBO.setTextType(DialogueConstant.DIALOGUE_TEXT_TYPE_NO);
        RangeDialogueDataBO rangeDialogueDataBO = new RangeDialogueDataBO();
        rangeDialogueDataBO.setHighAfterMeal(rangeBO.getHighAfterMeal());
        rangeDialogueDataBO.setHighBeforeBreakfast(rangeBO.getHighBeforeBreakfast());
        rangeDialogueDataBO.setLowAfterMeal(rangeBO.getLowAfterMeal());
        rangeDialogueDataBO.setLowBeforeBreakfast(rangeBO.getLowBeforeBreakfast());
        rangeDialogueBO.setData(rangeDialogueDataBO);
        dialoguePO.setDataStr(JSON.toJSONString(rangeDialogueBO));
        this.dialogueService.addDialogue(dialoguePO);
    }	
    
    //默认主治状态 0 非主治
    private final static int DEFAULT_NOT_ATTEND = 0;
    private final static int DEFAULT_ATTEND = 1;
    //对话默认外键
    private final static String DEFAULT_FOREIGN_ID = "-1";

    private final static String DIALOGUE_RANGE_MESSAGE = "控制目标设置通知";
    private final static String DIALOGUE_RANGE_TITLE = "控制目标";
    private final static String DIALOGUE_RANGE_CONTENT = "为患者制定控制目标";

	public static boolean strResult(String str){
		Boolean strResult = str.matches("-?[0-9]+.？[0-9]*");
		return strResult;
	}
}
