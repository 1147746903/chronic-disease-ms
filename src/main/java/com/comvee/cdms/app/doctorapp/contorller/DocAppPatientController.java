package com.comvee.cdms.app.doctorapp.contorller;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.app.doctorapp.model.app.MemberModel;
import com.comvee.cdms.app.doctorapp.service.PatientServiceI;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.cfg.DoctorConstant;
import com.comvee.cdms.doctor.cfg.GroupConstant;
import com.comvee.cdms.doctor.dto.AddGroupMapperDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.service.CommitteeService;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.constant.MemberLock;
import com.comvee.cdms.member.constant.MemberSourceConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.po.MemberListPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.service.impl.MemberManageServiceImpl;
import com.comvee.cdms.member.vo.InHospitalMemberVO;
import com.comvee.cdms.member.vo.ListFollowPlatformMemberVO;
import com.comvee.cdms.packages.dto.ListMemberPackageDTO;
import com.comvee.cdms.packages.po.MemberPackagePO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.packages.vo.MemberPackageInfoVO;
import com.comvee.cdms.packages.vo.PackageGroupVO;
import com.comvee.cdms.sign.dto.AddSignSuggestDTO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.SignSuggestService;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;


@Controller
@RequestMapping("/docapp/patient")
public class DocAppPatientController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PatientServiceI patientService;
	
	@Autowired
    private PackageService packageService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SignSuggestService signSuggestService;

	@Autowired
	private MemberManageServiceImpl manageService;

	@Autowired
	private CommitteeService committeeService;

	/**
	 * ->???????????? - ?????? - ??????????????????&????????????
	 * @param mobileDefualtVO
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/loadMemberListAndGroup")
	@ResponseBody
	public Result loadMemberListAndGroup(PageRequest page){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		Map<String,Object> map = this.patientService.loadMemberListAndGroup(doctorModel.getDoctorId(), page);
		return Result.ok(map);
	}	
	
	
	/**
	 * 
	 * @param mobileDefualtVO
	 * @param request
     * @param {Integer} page ??????
     * @param {Integer} rows ??????
     * @param {String} lastDate ??????????????????
     * @param {String} groupType ???????????? 1???????????????2????????????????????????????????????3???????????????????????? ??????1???
	 * @return
	 */
	@RequestMapping("/refreshMemberListAndGroup")
	@ResponseBody
	public Result refreshMemberListAndGroup(
			PageRequest page,String lastDate, String groupType){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		Map<String, Object> map = this.patientService.refreshMemberListAndGroup(doctorModel.getDoctorId(), page, lastDate, groupType);
		return Result.ok(map);
	}	
	
	/**
	 *  ??????????????????????????????
	 * @param mobileDefualtVO
	 * @param groupName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadDoctorGrouping")
	@ResponseBody
	public Result loadDoctorGrouping(){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		Map<String, Object> map = this.patientService.loadDoctorGrouping(doctorModel.getDoctorId());
		return Result.ok(map);
	}	
	
	
	/**
	 *  ????????????????????????
	 * @param mobileDefualtVO
	 * @param groupName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addDoctorGrouping")
	@ResponseBody
	public Result addDoctorGrouping(String groupName ,String doctorId){
		ValidateTool.checkParamIsNull(groupName, "groupName");
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		if(StringUtils.isBlank(doctorId)){
			doctorId = doctorModel.getDoctorId();
		}
		AddGroupMapperDTO addGroupDTO = this.patientService.addDoctorGrouping(doctorId, groupName);
		return Result.ok(addGroupDTO);
	}		
	
	/**
	 *  ??????????????????
	 * @param mobileDefualtVO
	 * @param groupName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/deleteDoctorGrouping")
	@ResponseBody
	public Result deleteDoctorGrouping(String groupId){
		ValidateTool.checkParamIsNull(groupId, "groupId");
		this.patientService.deleteDoctorGrouping(groupId);
		return Result.ok();
	}	
	
	/**
	 *  ??????????????????
	 * @param mobileDefualtVO
	 * @param groupName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/editDoctorGrouping")
	@ResponseBody
	public Result editDoctorGrouping(String groupName,String groupId){
		ValidateTool.checkParamIsNull(groupName, "groupName");
		ValidateTool.checkParamIsNull(groupId, "groupId");
		this.patientService.editDoctorGrouping(groupName,groupId);
		return Result.ok();
	}	
	
	/**
	 * ??????????????????
	 * @param mobileDefualtVO
	 * @param memberId
	 * @param request
	 * @param response
	 * @param memberIds
	 * @param groupId
	 * @return
	 */
	@RequestMapping("/editGroupMember")
	@ResponseBody
	public Result editGroupMember(String memberIds,String groupId ,String doctorId){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(memberIds, "memberIds");
		ValidateTool.checkParamIsNull(groupId, "groupId");
		if(StringUtils.isBlank(doctorId)){
			doctorId = doctorModel.getDoctorId();
		}
		if(memberIds.indexOf(",")>0) {
			String[] memberArray = memberIds.split(",");
			for (String memid : memberArray) {
				UpdateDoctorMemberDTO updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
				updateDoctorMemberDTO.setDoctorId(doctorId);
				updateDoctorMemberDTO.setMemberId(memid);
				updateDoctorMemberDTO.setGroupId(groupId);
				this.patientService.editGroupMember(updateDoctorMemberDTO);
			}
		}else {
			UpdateDoctorMemberDTO updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
			updateDoctorMemberDTO.setDoctorId(doctorId);
			updateDoctorMemberDTO.setMemberId(memberIds);
			updateDoctorMemberDTO.setGroupId(groupId);
			this.patientService.editGroupMember(updateDoctorMemberDTO);
		}
			

		return Result.ok();
	}


	/**
	 * ??????????????????
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @return
     * @apiSuccess {Map} body ???????????????
     * @apiSuccess {Map} body.drugDetail ????????????
     * @apiSuccess {String} body.drugDetail.schemeName ????????????
     * @apiSuccess {String} body.drugDetail.schemeId ??????id
     * @apiSuccess {String} body.drugDetail.startDt ????????????
     * @apiSuccess {String} body.drugDetail.endDt ????????????
     * @apiSuccess {List} body.drugDetail.drugList ????????????
     * @apiSuccess {String} body.drugDetail.drugList.drugName ???????????? - ????????????????????????
     * @apiSuccess {String} body.drugDetail.drugList.dayTime ??????
     * @apiSuccess {String} body.memberName ??????????????????(??????????????????????????????????????????????????????????????????????????????????????????)
     * @apiSuccess {String} body.lsNum ???????????????- ????????????????????????
     * @apiSuccess {String} body.eohNum ??????????????????
     * @apiSuccess {String} body.ffNum ??????????????????	 - ????????????????????????
	 */
	@RequestMapping("/memberDetailNew")
	@ResponseBody
	public Result memberDetailNew(String memberId,String doctorId){
		ValidateTool.checkParamIsNull(memberId, "memberId");
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		if(StringUtils.isBlank(doctorId)){
			doctorId= doctorModel.getDoctorId();
		}
		MemberModel memberModel = this.patientService.memberDetailNew(memberId, doctorId);
		return Result.ok(memberModel);
	}	
	
	/**
	 * ??????????????????
	 * @param mobileDefualtVO
	 * @param memberId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/rangeSelect")
	@ResponseBody
	public Result rangeSelect(String memberId){
		ValidateTool.checkParamIsNull(memberId, "memberId");
		RangeBO rangeBO = this.memberService.getMemberRange(memberId);
		return Result.ok(rangeBO);
	}	
	
	@RequestMapping("/rangeSet")
	@ResponseBody
	public Result rangeSet(RangeBO rangeBO){
		ValidateTool.checkParamIsNull(rangeBO.getDoctorId(), "doctorId");
		ValidateTool.checkParamIsNull(rangeBO.getMemberId(), "memberId");
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		rangeBO.setSenderId(doctorSessionBO.getDoctorId());
		this.patientService.addMemberRange(rangeBO);
		return Result.ok();
		
	}		

	/**
     * ????????????????????????
     * @param pr
     * @param memberId
     * @return
     */
    @RequestMapping("memberPackageList")
    @ResponseBody
    public Result memberPackageList(String memberId, String doctorId, PageRequest pr){
      	ValidateTool.checkParamIsNull(memberId, "memberId");
      	ValidateTool.checkParamIsNull(doctorId, "doctorId");
		ListMemberPackageDTO listMemberPackageDTO = new ListMemberPackageDTO();
		listMemberPackageDTO.setMemberId(memberId);
		listMemberPackageDTO.setDoctorId(doctorId);
		PageResult<MemberPackagePO> result = this.packageService.listMemberPackageById(listMemberPackageDTO, pr);
        return Result.ok(result);
    }
	
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param memberPackageId ????????????id
	 * @return
	 */
	@RequestMapping("/getMemberPackageInfo")
	@ResponseBody
	public Result getMemberPackageInfo(String memberPackageId){
		ValidateTool.checkParamIsNull(memberPackageId, "memberPackageId");
		MemberPackageInfoVO memberPackagePO = this.packageService.getMemberPackageWithServiceInfo(memberPackageId);
        return Result.ok(memberPackagePO);
	}		
	
	/**
	 * ???????????????????????????????????????
	 * @param idCard String
	 * @return
	 */
	@RequestMapping("/getDocPatientInfo")
	@ResponseBody
	public Result getDocPatientInfo(GetMemberDTO requestMember){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(requestMember.getKeyword(), "keyword");
		MemberPO member = this.patientService.getDocPatientInfo(requestMember,doctorModel.getDoctorId(), doctorModel.getHospitalId());
        return Result.ok(member);
	}
	
	/**
	 * ??????????????????
	 * @param memberId String
	 * @return
	 */
	@RequestMapping("/addDoctorMember")
	@ResponseBody
	public Result addDoctorMember(String memberId){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(memberId, "memberId");

		AddMemberDoctorRelateDTO dto = new AddMemberDoctorRelateDTO();
		dto.setDoctorId(doctorModel.getDoctorId());
		dto.setMemberId(memberId);
		dto.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
		dto.setOperatorId(dto.getDoctorId());
		dto.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_APP);  //???????????????????????? ?????????APP
		memberService.addMemberDoctorRelate(dto);
        return Result.ok();
	}
	
	/**
	 * ?????????????????????????????????
	 * @param memberId String
	 * @return
	 */
	@RequestMapping("/addMemberAndRelation")
	@ResponseBody
	public Result addMemberAndRelation(@Valid AddMemberDTO addMemberDTO, String groupId,
									   String memberId){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		memberId = memberService.h5AddMemberOrUpdate(addMemberDTO, groupId, memberId, doctorModel);
		return Result.ok(memberId);
	}
	/**
	 * ??????????????????
	 * @param addInHospitalMemberDTO
	 * @return
	 */
	@RequestMapping("/h5AddInHospitalMemberOrUpdate")
	@ResponseBody
	public Result h5AddInHospitalMemberOrUpdate(@Validated AddMemberDTO addMemberDTO, AddInHospitalMemberDTO addInHospitalMemberDTO){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(addInHospitalMemberDTO.getHospitalNo(), "hospitalNo");
		ValidateTool.checkParamIsNull(addInHospitalMemberDTO.getSid(), "sid");
		ValidateTool.checkParamIsNull(addInHospitalMemberDTO.getInHospitalDate(), "inHospitalDate");
		this.memberService.h5AddInHospitalMemberOrUpdate(addMemberDTO ,doctorSessionBO, addInHospitalMemberDTO);
		return Result.ok();
	}




	/**
	 * ????????????????????????
	 * @param signId String ???????????????????????????
	 * @return
	 */
	@RequestMapping("/getSignSuggetBySignId")
	@ResponseBody
	public Result getSignSuggetBySignId(String signId){
		ValidateTool.checkParamIsNull(signId, "signId");
		SignSuggestPO returnModel = signSuggestService.getSignSuggetBySignId(signId);
        return Result.ok(returnModel);
	}
	
	/**
	 * ??????????????????????????????
	 * @param signId String 
	 * @return
	 */
	@RequestMapping("/addSignSuggest")
	@ResponseBody
	public Result addSignSuggest(AddSignSuggestDTO addSignSuggestDTO){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(addSignSuggestDTO.getSignId(), "signId");
		ValidateTool.checkParamIsNull(addSignSuggestDTO.getMemberId(), "memberId");
		ValidateTool.checkParamIsNull(addSignSuggestDTO.getSuggestText(), "suggestText");
		ValidateTool.checkParamIsNull(addSignSuggestDTO.getSignType(), "signType");

		addSignSuggestDTO.setSenderId(doctorModel.getDoctorId());
		String sid = signSuggestService.addSignSuggest(addSignSuggestDTO);
        return Result.ok(sid);
	}
	
	/**
	 * ??????????????????
	 * @param mobileDefualtVO
	 * @param request
	 * @param response
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/getMemberArchivesById")
	@ResponseBody
	public Result getMemberArchivesById(String memberId){
		ValidateTool.checkParamIsNull(memberId, "memberId");

		GetMemberDTO getMemberDTO = new GetMemberDTO();
		getMemberDTO.setMemberId(memberId);
		MemberPO memberInfo = this.memberService.getMember(getMemberDTO);
		logger.info("[getMemberArchivesById]??????????????????"+JSON.toJSON(memberInfo));

		List<List<Map<String,Object>>> listMap = new ArrayList<List<Map<String,Object>>>();
		LinkedHashMap<String,Object> resultBasicMap = new LinkedHashMap<String,Object>();
		resultBasicMap.put("????????????","");
		resultBasicMap.put("??????", memberInfo.getMemberName());
		resultBasicMap.put("????????????", memberInfo.getIdCard());
		resultBasicMap.put("?????????", memberInfo.getMobilePhone()==null?"":memberInfo.getMobilePhone());
		resultBasicMap.put("??????", StringUtils.isBlank(memberInfo.getSex().toString())?"":sexMap.get(String.valueOf(memberInfo.getSex())));
		resultBasicMap.put("????????????", memberInfo.getBirthday());
		resultBasicMap.put("??????", memberInfo.getHeight());
		resultBasicMap.put("??????", memberInfo.getWeight());
		List<Map<String,Object>> resultBasicList = new ArrayList<Map<String,Object>>();
		for(Map.Entry<String, Object> entry:resultBasicMap.entrySet()){
			HashMap<String,Object> map2 = new HashMap<String,Object>();
			map2.put("code",entry.getKey());
			map2.put("value",entry.getValue());
			resultBasicList.add(map2);
		}
		listMap.add(resultBasicList);

		LinkedHashMap<String,Object> resultAnamnesisMap = new LinkedHashMap<String,Object>();
		resultAnamnesisMap.put("????????????","");
		resultAnamnesisMap.put("?????????????????????",memberInfo.getDiabetesDate());
		resultAnamnesisMap.put("???????????????",StringUtils.isBlank(memberInfo.getDiabetesType())?"":diabetesTypeMap.get(memberInfo.getDiabetesType()));
		List<Map<String,Object>> resultAnamnesisList = new ArrayList<Map<String,Object>>();
		for(Map.Entry<String, Object> entry:resultAnamnesisMap.entrySet()){
			HashMap<String,Object> map2 = new HashMap<String,Object>();
			map2.put("code",entry.getKey());
			map2.put("value",entry.getValue());
			resultAnamnesisList.add(map2);
		}
		listMap.add(resultAnamnesisList);


		return Result.ok(listMap);
	}

	/**
	 * ??????????????????
	 * @param listMemberDTO
	 * @param pr
	 * @return
	 */
	@RequestMapping("listMember")
	@ResponseBody
	public Result listMember(@Validated ListMemberDTO listMemberDTO, PageRequest pr){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		listMemberDTO.setHospitalId(doctorSessionBO.getHospitalId());
		PageResult<MemberListPO> poPageResult = this.memberService.listMember(listMemberDTO, pr);
		return new Result(poPageResult);
	}

	/**
	 * ????????????
	 * @param pr
	 * @param keyword
	 * @return
	 */
	@RequestMapping("listSearchMember")
	@ResponseBody
	public Result listSearchMember(PageRequest pr ,String keyword){
		ValidateTool.checkParameterIsNull(keyword ,"keyword");
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		PageResult<MemberListPO> poPageResult = this.memberService.listSearchMember(doctorSessionBO.getDoctorId() ,keyword ,pr);
		return Result.ok(poPageResult);
	}

	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping("listPackageGroup")
	@ResponseBody
	public Result listPackageGroup(){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		List<PackageGroupVO> list = this.packageService.listPackageGroup(doctorSessionBO.getDoctorId());
		return Result.ok(list);
	}

	/**
	 * ????????????????????????????????????
	 * @param packageId
	 * @param pr
	 * @return
	 */
	@RequestMapping("listMemberByPackageGroup")
	@ResponseBody
	public Result listMemberByPackageGroup(String packageId ,PageRequest pr){
		ValidateTool.checkParameterIsNull(packageId ,"packageId");
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		PageResult result = this.memberService.listMemberByPackageGroup(pr ,packageId ,doctorSessionBO.getDoctorId());
		return Result.ok(result);
	}

	/**
	 * @api {post}/docapp/patient/cancelRelation.do ??????????????????
	 * @author ?????????
	 * @time 2018/08/21
	 * @apiName cancelRelation ??????????????????
	 * @apiGroup docapp-patient
	 * @apiVersion 4.0.0
	 * @apiParam {String} memberId ????????????
	 * @apiParam {String} doctorId ????????????
	 *
	 * @apiSuccess {String} data.obj ????????????
	 * @apiSuccess {Object} data.msg ????????????
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code ???????????? 0??????
	 */
	@RequestMapping("cancelRelation")
	@ResponseBody
	public Result cancelRelation(String memberId,String doctorId){
		this.memberService.cancelRelation(memberId,doctorId);
		Result result =  new Result();
		result.setMessage("????????????");
		return result;
	}

	/**
	 * @api {post}/docapp/patient/listInHospitalMember.do ??????????????????&??????
	 * @author ?????????
	 * @time 2020/03/09
	 * @apiName listInHospitalMember ??????????????????&??????
	 * @apiGroup WEB-V6.0.0
	 * @apiVersion 6.0.0
	 * @apiParam {String} hospitalId ????????????
	 * @apiParam {String} doctorId ??????????????????
	 * @apiParam {String} groupId ????????????
	 * @apiParam {String} keyWord ??????????????????????????????????????????
	 * @apiParam {String} concernStatus ????????????
	 * @apiParam {String} type ???????????? 1:????????? 2:?????????
	 * @apiParam {String} monitor ???????????? 1??? 0???
	 * @apiParam {String} paramLevel ???????????? 1????????? 3?????? 5?????????
	 * @apiParam {Int} page ??????
	 * @apiParam {Int} rows ??????
	 * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/member/listInHospitalMember.do
	 *
	 * @apiSuccess {String} data.obj ????????????
	 * @apiSuccess {Object} data.msg ????????????
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code ???????????? 0??????
	 */
	@RequestMapping("listInHospitalMember")
	@ResponseBody
	public Result listInHospitalMember(@Validated ListMemberDTO listMemberDTO,PageRequest pager){
		PageResult<InHospitalMemberVO> pageResult = this.memberService.pagerInHospitalMember(listMemberDTO,pager);
		return new Result(pageResult);
	}

	public static Map<String ,String> sexMap =  new HashMap<String,String>();
	static {
		sexMap.put("1","???");
		sexMap.put("2","???");
	}
	public static Map<String ,String> diabetesTypeMap =  new HashMap<String,String>();
	static {
		diabetesTypeMap.put("SUGAR_TYPE_001","1???");
		diabetesTypeMap.put("SUGAR_TYPE_002","2???");
		diabetesTypeMap.put("SUGAR_TYPE_003","??????");
		diabetesTypeMap.put("SUGAR_TYPE_004","??????");
	}
	/**
	 * ????????????????????????
	 * @return memberRange
	 */
	@RequestMapping("getMemberRange")
	@ResponseBody
	public Result getMemberRange(String memberId){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(memberId, "memberId");
		RangeBO memberRange = this.memberService.getMemberRange(memberId);
		if (memberRange == null) {
			return new Result(this.memberService.getMemberDefaultControlTarget(memberId));
		}
		return new Result(memberRange);
	}
	/**
	 * ??????????????????
	 * @param dto
	 * @param pr
	 * @return
	 */
	@RequestMapping("listMemberJoinHospital")
	@ResponseBody
	public Result listMemberJoinHospital(ListMemberJoinHospitalDTO dto, PageRequest pr){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		dto.setDoctorId(doctorSessionBO.getDoctorId());
		dto.setHospitalId(doctorSessionBO.getHospitalId());
		dto.setDoctorLevel(doctorSessionBO.getDoctorLevel());
		dto.setMobilePhone(dto.getKeyword());
		return Result.ok(manageService.listMemberJoinHospitalH5(dto, pr));
	}


	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping("listHospitalCommiteeList")
	@ResponseBody
	public Result listHospitalCommiteeList(){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		HospitalCommitteePO param = new HospitalCommitteePO();
		param.setHospitalId(doctorSessionBO.getHospitalId());
		List<HospitalCommitteePO> hospitalCommiteeList  = committeeService.listHospitalCommittee(param);
		return Result.ok(hospitalCommiteeList);
	}

	/**
	 * ???????????????????????????
	 *
	 * @param addMemberDTO
	 * @return
	 */
	@RequestMapping("/createMemberHospitalRelation")
	@ResponseBody
	public Result createMemberHospitalRelation(AddMemberDTO addMemberDTO) {
		ValidateTool.checkParamIsNull(addMemberDTO.getMemberName(), "memberName");
		ValidateTool.checkParamIsNull(addMemberDTO.getBirthday(), "birthday");
		ValidateTool.checkParamIsNull(addMemberDTO.getSex(), "sex");
		ValidateTool.checkParamIsNull(addMemberDTO.getIdCard(), "idCard");
		ValidateTool.checkParamIsNull(addMemberDTO.getMobilePhone(), "mobilePhone");
		ValidateTool.checkParamIsNull(addMemberDTO.getDiabetesType(), "diabetesType");
		MemberPO memberPO = memberService.getMemberByIdCard(addMemberDTO.getIdCard());
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		String memberId;
		if (memberPO == null) {
			MemberLock.ADD_MEMBER_LOCK.lock();
			try {
				addMemberDTO.setMemberSource(MemberSourceConstant.MEMBER_SOURCE_DOC_APP);
				memberId = this.memberService.addMember(addMemberDTO, doctorSessionBO);
			} finally {
				MemberLock.ADD_MEMBER_LOCK.unlock();
			}
		} else {
			memberId = memberPO.getMemberId();
			UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
			updateMemberDTO.setMemberId(memberId);
			updateMemberDTO.setBirthday(addMemberDTO.getBirthday());
			updateMemberDTO.setSex(addMemberDTO.getSex());
			updateMemberDTO.setMemberName(addMemberDTO.getMemberName());
			updateMemberDTO.setMobilePhone(addMemberDTO.getMobilePhone());
			updateMemberDTO.setVisitNo(addMemberDTO.getVisitNo());
			updateMemberDTO.setDiabetesType(addMemberDTO.getDiabetesType());
			memberService.updateMember(updateMemberDTO);
			memberService.memberJoinHospitalHandler(doctorSessionBO, memberId);
		}
		return Result.ok(memberId);
	}


	@RequestMapping("/addMemberToCache")
	@ResponseBody
	public Result addMemberToCache(String memberId){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(memberId, "memberId");
		manageService.addMemberToCache(doctorSessionBO.getDoctorId(), memberId);
		return Result.ok();
	}

	@RequestMapping("/listMemberRecentSearchH5")
	@ResponseBody
	public Result listMemberRecentSearchH5(){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		return Result.ok(manageService.listMemberRecentSearchH5(doctorSessionBO.getDoctorId(), doctorSessionBO.getHospitalId()));
	}

	/**
	 * ????????????
	 *
	 * @param updateMemberDTO
	 * @return
	 */
	@RequestMapping("updateMember")
	@ResponseBody
	public Result updateMember(@Validated UpdateMemberDTO updateMemberDTO) {
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		MemberLock.UPDATE_MEMBER_LOCK.lock();
		try {
			this.memberService.updateMember(updateMemberDTO);
			this.memberService.updateMemberVisitNo(doctorSessionBO.getHospitalId(), updateMemberDTO.getMemberId(), updateMemberDTO.getVisitNo());
		} finally {
			MemberLock.UPDATE_MEMBER_LOCK.unlock();
		}
		return new Result("");
	}
}


