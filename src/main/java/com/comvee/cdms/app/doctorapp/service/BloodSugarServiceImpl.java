package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.app.doctorapp.model.app.*;
import com.comvee.cdms.app.doctorapp.vo.CountMemberVO;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.ListMemberDTO;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberListPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBloodSugarMapperDTO;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.mapper.BloodSugarMapper;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.vo.BloodSugarParamSettingVO;
import com.comvee.cdms.user.tool.SessionTool;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

@Service("bloodSugarAppService")
public class BloodSugarServiceImpl implements BloodSugarServiceI{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BloodSugarMapper bloodSugarMapper;
	
	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MemberService memberService;

//	@Override
//	public Map<String , Object> statisticsMemberAbnormalsNew(String doctorId , String payStatus, String startDt, String endDt) {
//		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
//		
//		Map<String , Object> map = new HashMap();
//		//2??????????????????
//		String paramLevel = "2";
//		List<BloodNumByStatusResqModel> totalList = this.bloodSugarMapper.getBloodNumByStatus(doctorId, startDt, endDt, paramLevel, null);
//		int lowTotalPatientNums_int = 0 ;
//		if(totalList != null) {
//			lowTotalPatientNums_int = totalList.size();
//		}
//		Long lowTotalPatientNums = Long.valueOf(lowTotalPatientNums_int);
//		
//		List<String> memberIdList = new ArrayList();//?????????????????????  ?????????  ????????? ???????????????
//		for (BloodNumByStatusResqModel bloodNumByStatusResqModel : totalList) {
//			memberIdList.add(bloodNumByStatusResqModel.getMemberId());
//		}
//		Long hasPayLowNum = 0L;
//		Long noPayLowNum = 0L;
//		if(memberIdList.size()>0) {
//			 hasPayLowNum = this.packageMapper.countHasValidPayPackageMember(memberIdList, doctorId);
//			 noPayLowNum = lowTotalPatientNums - hasPayLowNum;
//		}
//
//		map.put("hasPayLowNum", hasPayLowNum);
//		map.put("noPayLowNum", noPayLowNum);
//		//2?????????????????? end...
//		
//		//1?????????????????????  beforeBreakfast  ????????????
//		paramLevel = "5";
//		List<BloodNumByStatusResqModel> kfList = this.bloodSugarMapper.getBloodNumByStatus(doctorId, startDt, endDt, paramLevel, "1");
//		int kfTotalPatientNums_int = 0 ;
//		if(kfList != null) {
//			kfTotalPatientNums_int = kfList.size();
//		}
//		Long kfTotalPatientNums = Long.valueOf(kfTotalPatientNums_int);
//		
//		List<String> kfmemberIdList = new ArrayList();
//		for (BloodNumByStatusResqModel bloodNumByStatusResqModel : kfList) {
//			kfmemberIdList.add(bloodNumByStatusResqModel.getMemberId());
//		}
//		
//		Long kf_hasPayNum = 0L;
//		Long kf_noPayNum = 0L;
//		if(kfmemberIdList.size()>0) {
//			kf_hasPayNum = this.packageMapper.countHasValidPayPackageMember(kfmemberIdList, doctorId);
//			kf_noPayNum = kfTotalPatientNums - kf_hasPayNum;
//		}
//
//		map.put("kf_hasPayNum", kf_hasPayNum);//?????? ?????????
//		map.put("kf_noPayNum", kf_noPayNum);  //?????? ??????	
//		//1????????????????????? end...
//		
//		//1????????????????????????    ???????????????
//		paramLevel = "5";
//		List<BloodNumByStatusResqModel> no_kfList = this.bloodSugarMapper.getBloodNumByStatus(doctorId, startDt, endDt, paramLevel, "2");
//		int no_kfTotalPatientNums_int = 0 ;
//		if(no_kfList != null) {
//			no_kfTotalPatientNums_int = no_kfList.size();
//		}
//		Long no_kfTotalPatientNums = Long.valueOf(no_kfTotalPatientNums_int);
//		
//		List<String> no_kfmemberIdList = new ArrayList();
//		for (BloodNumByStatusResqModel bloodNumByStatusResqModel : no_kfList) {
//			no_kfmemberIdList.add(bloodNumByStatusResqModel.getMemberId());
//		}
//		
//		Long nokf_hasPayNum = this.packageMapper.countHasValidPayPackageMember(no_kfmemberIdList, doctorId);
//		Long nokf_noPayNum = no_kfTotalPatientNums - nokf_hasPayNum;
//		map.put("nokf_hasPayNum", nokf_hasPayNum);//?????? ?????????
//		map.put("nokf_noPayNum", nokf_noPayNum);  //?????? ??????	
//		//1????????? end...
//		
//		//4????????????????????????
//		List<BloodNumByStatusResqModel> threeNumList = this.bloodSugarMapper.getBloodThreeDayByStatus(doctorId, startDt, endDt);
//		
//		Long hasPay_Three = 0L;
//		Long noPay_Three = 0L;
//		int threeTotal = 0 ;
//		 
//		String current_memberId = "";
//		int current_flag = 0;
//		List<String> three_memberIdList = new ArrayList();//
//		for(int i=0 ; i<threeNumList.size(); i++) {
//			BloodNumByStatusResqModel bloodValModel = threeNumList.get(i);
//			String level = bloodValModel.getParamLevel();
//			String memberId = bloodValModel.getMemberId();
//			
//			if(!current_memberId.equals(memberId)) {
//				current_memberId = memberId;
//				current_flag=0;//???????????? ??????0
//			}
//			if("4".equals(level)) {
//				current_flag++ ;
//			}else {
//				current_flag=0;//????????? ??????0
//			}
//			if(current_flag>=3) {//????????????
//				if(!current_memberId.equals(memberId)) {//????????????  ???????????????????????????
//					threeTotal++;
//					three_memberIdList.add(current_memberId);//??????????????? memberid ?????????
//				}else if(i == threeNumList.size()-1) {
//					threeTotal++;
//					three_memberIdList.add(current_memberId);//??????????????? memberid ?????????
//				}
//			}				
//		}
//		
//		Long _three = Long.valueOf(threeTotal);
//		if(three_memberIdList.size()>0) {
//			hasPay_Three = this.packageMapper.countHasValidPayPackageMember(three_memberIdList, doctorId);
//			noPay_Three = _three - hasPay_Three;
//		}
//
//		map.put("hasPay_Three", hasPay_Three);//?????? ?????????
//		map.put("noPay_Three", noPay_Three);  //?????? ??????			
//		
//		return map;
//	}

	@Override
	public List<Map<String,Object>> statisticsMemberAbnormalsNew(String doctorId , String payStatus, String startDt, String endDt,String hospitalId) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		Map<String , Object> map = new HashMap();
		//2??????????????????
		String paramLevel = "2";
		List<BloodNumByStatusResqModel> totalList = this.bloodSugarMapper.getBloodNumByStatus(doctorId, startDt, endDt, paramLevel, null ,payStatus,hospitalId);
		int lowTotalPatientNums_int = 0 ;
		if(totalList != null) {
			lowTotalPatientNums_int = totalList.size();
		}		
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("text", "?????????");
		map1.put("code", "2");
		map1.put("icon", "http://comveetest.oss-cn-hangzhou.aliyuncs.com/201610/1410/1610141025187873.png");	
		map1.put("number", lowTotalPatientNums_int);
		resultList.add(map1);
		//2?????????????????? end...
		
		//3?????????????????????  beforeBreakfast  ????????????
		paramLevel = "5";
		List<BloodNumByStatusResqModel> kfList = this.bloodSugarMapper.getBloodNumByStatus(doctorId, startDt, endDt, paramLevel, "1" , payStatus,hospitalId);
		int kfTotalPatientNums_int = 0 ;
		if(kfList != null) {
			kfTotalPatientNums_int = kfList.size();
		}		
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("text", "?????????????????????");
		map2.put("code", "3");		
		map2.put("icon", "http://comveetest.oss-cn-hangzhou.aliyuncs.com/201610/1410/1610141026174227.png");
		map2.put("number", kfTotalPatientNums_int);
		resultList.add(map2);
		//1????????????????????? end...
		
		//2????????????????????????    ???????????????
		paramLevel = "5";
		List<BloodNumByStatusResqModel> no_kfList = this.bloodSugarMapper.getBloodNumByStatus(doctorId, startDt, endDt, paramLevel, "2" , payStatus,hospitalId);
		int no_kfTotalPatientNums_int = 0 ;
		if(no_kfList != null) {
			no_kfTotalPatientNums_int = no_kfList.size();
		}
		
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("text", "????????????????????????");
		map3.put("code", "4");		
		map3.put("icon", "http://comveetest.oss-cn-hangzhou.aliyuncs.com/201610/1410/1610141025516135.png");
		map3.put("number", no_kfTotalPatientNums_int);
		resultList.add(map3);
		//2????????? end...
		
		//4????????????????????????
		List<BloodNumByStatusResqModel> threeNumList = this.bloodSugarMapper.getBloodThreeDayByStatus(doctorId, startDt, endDt , payStatus,hospitalId);
		int threeTotal = 0 ;
		String current_memberId = "";
		int current_flag = 0;
		List<String> three_memberIdList = new ArrayList();
		
		for(int i=0 ; i<threeNumList.size(); i++) {
			BloodNumByStatusResqModel bloodValModel = threeNumList.get(i);
			String level = bloodValModel.getParamLevel();
			String memberId = bloodValModel.getMemberId();
			if(!current_memberId.equals(memberId)) {
				current_memberId = memberId;
				current_flag=0;//???????????? ??????0
			}		
			if("4".equals(level)) {//?????????+1
				current_flag++ ;
			}else if("5".equals(level)){
				current_flag++ ;
			}else{
				current_flag=0;//????????? ??????0
			}
			if(current_flag>=3) {//????????????
				if(three_memberIdList.indexOf(memberId) < 0){
					threeTotal++;
					three_memberIdList.add(current_memberId);//??????????????? memberid ?????????
				}
/*				if(!current_memberId.equals(memberId)) {//????????????  ???????????????????????????
					threeTotal++;
					three_memberIdList.add(current_memberId);//??????????????? memberid ?????????
				}else if(i == threeNumList.size()-1) {
					threeTotal++;
					three_memberIdList.add(current_memberId);//??????????????? memberid ?????????
				}*/
			}			
			
		}
		
		Map<String,Object> map4 = new HashMap<String,Object>();
		map4.put("text", "??????3????????????");
		map4.put("code", "6");		
		map4.put("icon", "http://comveetest.oss-cn-hangzhou.aliyuncs.com/201610/1410/1610141026456659.png");
		map4.put("number", threeTotal);
		resultList.add(map4);
		 
		return resultList;
	}	
	
	@Override
	public PageResult<SugarDetailListModel> workbenchSugarDetailNew(PageRequest page , String doctorId, String payStatus, String startDt, String endDt, String type ,String hospitalId) {
		Map<String , Object> map = new HashMap();
		
		String paramLevel = "";
		if(type.equals("2")) {//2??????????????????
			paramLevel = "2";
			//payStatus 1??????  0 ??????
			PageHelper.startPage(page.getPage(), page.getRows());
			List<SugarDetailListModel> list = this.bloodSugarMapper.lowSugerList(doctorId, startDt, endDt, paramLevel, null , payStatus,hospitalId);//???????????? - ????????????
			for (SugarDetailListModel sugarDetailListModel : list) {
				String num = sugarDetailListModel.getNum();
				String paramValue = sugarDetailListModel.getParamValue();
				if(StringUtils.isBlank(paramValue)) {
					sugarDetailListModel.setText("?????????"+num+"???,????????????--mmol/L");
				}else {
					sugarDetailListModel.setText("?????????"+num+"???,????????????"+paramValue+"mmol/L");
				}
			}
			PageResult<SugarDetailListModel> pageResult = new PageResult(list);
			return pageResult;
		}else if(type.equals("3")){//3????????????
			paramLevel = "5";
			//payStatus 1??????  0 ??????
			PageHelper.startPage(page.getPage(), page.getRows());
			List<SugarDetailListModel> list = this.bloodSugarMapper.lowSugerList(doctorId, startDt, endDt, paramLevel, "1" , payStatus,hospitalId);//paramCode=1 ??????
			for (SugarDetailListModel sugarDetailListModel : list) {
				String num = sugarDetailListModel.getNum();
				String paramValue = sugarDetailListModel.getParamValue();
				if(StringUtils.isBlank(paramValue)) {
					sugarDetailListModel.setText("??????????????????"+num+"???,????????????--mmol/L");
				}else {
					sugarDetailListModel.setText("??????????????????"+num+"???,????????????"+paramValue+"mmol/L");
				}
			}
			
			PageResult<SugarDetailListModel> pageResult = new PageResult(list);
			
			return pageResult;				
		}else if(type.equals("4")) {
			paramLevel = "5";
			//payStatus 1??????  0 ??????
			PageHelper.startPage(page.getPage(), page.getRows());
			List<SugarDetailListModel> list = this.bloodSugarMapper.lowSugerList(doctorId, startDt, endDt, paramLevel, "2" , payStatus,hospitalId);//paramCode=1 ??????
			for (SugarDetailListModel sugarDetailListModel : list) {
				String num = sugarDetailListModel.getNum();
				String paramValue = sugarDetailListModel.getParamValue();
				if(StringUtils.isBlank(paramValue)) {
					sugarDetailListModel.setText("?????????????????????"+num+"???,????????????--mmol/L");
				}else {
					sugarDetailListModel.setText("?????????????????????"+num+"???,????????????"+paramValue+"mmol/L");
				}
			}
			
			PageResult<SugarDetailListModel> pageResult = new PageResult(list);
			
			return pageResult;
		}
		else if(type.equals("6")) {//??????????????????
			paramLevel = "4";
			//payStatus 1??????  0 ??????
			List<SugarDetailListModel> list = this.bloodSugarMapper.getBloodThreeDayListByStatus(doctorId, startDt, endDt,  payStatus,hospitalId);//?????????????????????????????????
			
			String front_memberId = "";//?????????
			String textNow = "";//????????????
			int current_flag = 0;//???????????????
			int threeTotal = 0 ;
			List<SugarDetailListModel> three_memberList = new ArrayList();//
			List<String> three_memberIdList = new ArrayList();
			for(int i=0; i<list.size(); i++) {
				SugarDetailListModel sdlModel = list.get(i);
				String level = sdlModel.getParamLevel();
				String memberId = sdlModel.getMemberId();
				
				String paramName = returnParamCodeName(sdlModel.getParamCode() == null?"":sdlModel.getParamCode());
				String text = sdlModel.getRecordDt().substring(5,7)+"???"+sdlModel.getRecordDt().substring(8,10)+"???";
				text = text + paramName;
				
				
				if(!front_memberId.equals(memberId)) {
					front_memberId = memberId;
					current_flag=0;//???????????? ??????0
					textNow = text+"???";
				}
				if("4".equals(level)) {
					current_flag++ ;
					textNow += text +"???";
				}else if("5".equals(level)){
					current_flag++ ;
					textNow += text +"???";
				}else{
					current_flag=0;//????????? ??????0
					textNow = text+"???";
				}
				if(current_flag >= 3){

					if(three_memberIdList.indexOf(memberId) < 0){
						sdlModel.setText(textNow);
						three_memberList.add(sdlModel);//??????????????? memberid ?????????
						textNow = "";
						//???????????????????????????list???
						three_memberIdList.add(memberId);
//						//???????????????memberId?????????list???
//						if (!three_memberList.contains(memberId)){
//
//						}
					}
				}
/*				if(current_flag>=3) {//????????????
					if(!front_memberId.equals(memberId)) {//????????????  ???????????????????????????
						sdlModel.setText(textNow);
						textNow = "";
						three_memberList.add(sdlModel);//??????????????? memberid ?????????
					}else if(i == list.size()-1) {
						sdlModel.setText(textNow);
						three_memberList.add(sdlModel);//??????????????? memberid ?????????
					}
				}	*/
			}
			PageHelper.startPage(page.getPage(), page.getRows());
			PageResult<SugarDetailListModel> pageResult = new PageResult(three_memberList);
			pageResult.setTotalPages(1);
			pageResult.setPageSize(three_memberList.size());
			pageResult.setPageNum(1);
			pageResult.setTotalRows(three_memberList.size());
			return pageResult;
		}
		
		return null;
	}
	
	private String returnParamCodeName(String paramCode) {
		String result = "";
        switch (paramCode){
        // ??????3???
        case SignConstant.PARAM_CODE_3AM:
        	result = "??????3???";
            break;
        //?????????
        case SignConstant.PARAM_CODE_AFTER_BREAKFAST:
        	result = "?????????";
            break;
        //?????????
        case SignConstant.PARAM_CODE_AFTER_DINNER:
        	result = "?????????";
            break;
        //?????????
        case SignConstant.PARAM_CODE_AFTER_LUNCH:
        	result = "?????????";
            break;
        //?????????
        case SignConstant.PARAM_CODE_BEFORE_BREAKFAST:
        	result = "?????????";
            break;
        //??????
        case SignConstant.PARAM_CODE_BEFORE_DAWN:
        	result = "??????";
            break;
        //?????????
        case SignConstant.PARAM_CODE_BEFORE_DINNER:
        	result = "?????????";
            break;
        //?????????
        case SignConstant.PARAM_CODE_BEFORE_LUNCH:
        	result = "?????????";
            break;
        //??????
        case SignConstant.PARAM_CODE_BEFORE_SLEEP:
        	result = "??????";
            break;
        //????????????
        case SignConstant.PARAM_CODE_RANDOM_TIME:
        	result = "????????????";
            break;
        default:
        	
            break;
    }
		return result;
	}

	@Override
	public PageResult<NormalSugerModel> workbenchSugarDetailByNormal(String doctorId ,String startDt
			, String endDt , PageRequest page, String payStatus ,Integer visitType ,String departmentId,String hospitalId) {
		List<NormalSugerListResqModel> normalSugerList = null;
        PageHelper.startPage(page.getPage(), page.getRows());
        //??????
		if(visitType == 1){

			normalSugerList = this.bloodSugarMapper.listInHospitalNormalSugarPatient(departmentId ,startDt ,endDt);
		}
		//??????
		else if(visitType == 2){
			List<Integer> payStatusList = getPayStatusList(payStatus);
			normalSugerList = this.bloodSugarMapper.normalSugerList(doctorId, startDt, endDt, payStatusList,hospitalId);
		}
		PageResult<NormalSugerListResqModel> normalSugerListResult = new PageResult(normalSugerList);
		List<NormalSugerModel> resList = new ArrayList();
		normalSugerList.forEach( x -> {
			NormalSugerModel resModel = new NormalSugerModel();
			ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
			listBloodSugarDTO.setMemberId(x.getMemberId());
			listBloodSugarDTO.setStartDt(startDt);
			listBloodSugarDTO.setEndDt(endDt);
			List<BloodSugarPO> bloodSugarList = this.bloodSugarMapper.listBloodSugar(listBloodSugarDTO);
			if(bloodSugarList != null && !bloodSugarList.isEmpty()){
				bloodSugarList.sort((a1, a2) -> a1.getParamValue().compareTo(a2.getParamValue()));
				resModel.setMemberId(x.getMemberId());
				resModel.setMinValue(bloodSugarList.get(0).getParamValue());
				resModel.setMaxValue(bloodSugarList.get(bloodSugarList.size() - 1).getParamValue());
				resModel.setAllNum(String.valueOf(bloodSugarList.size()));
			}
			resModel.setNormalNum(resModel.getAllNum());
			resModel.setMemberName(x.getMemberName());
			resModel.setSex(x.getSex());
			resModel.setDiabetesType(x.getDiabetesType());
			resModel.setDepartmentName(x.getDepartmentName());
			resModel.setBedNo(x.getBedNo());
			resModel.setHospitalNo(x.getHospitalNo());
			resList.add(resModel);
		});

		PageResult<NormalSugerModel> result = normalSugerListResult.createEmptyPageResult();
		result.setRows(resList);
		return result;
	}


//	@Override
//	public List<NormalSugerListResqModel> countWorkbenchSugar(String startDt, String endDt, String payStatus) {
//
//		DoctorSessionBO doctorModel = SessionTool.getWebSession();
//		List<Integer> payStatusList = getPayStatusList(payStatus);
//		List<NormalSugerListResqModel> normalSugarList = bloodSugarMapper.normalSugerList(doctorModel.getDoctorId(), startDt, endDt, payStatusList,doctorModel.getHospitalId());
//
//		return normalSugarList;
//	}

	@Override
	public PageResult<NormalSugerListResqModel> workbenchSugarDetailByNotMeasured(String doctorId, String startDt, String endDt,
																				  PageRequest page, String payStatus ,Integer visitType ,String departmentId,String hospitalId) {
		List<NormalSugerListResqModel> sugerlist = null;
		PageHelper.startPage(page.getPage(), page.getRows());
		//??????
		if(visitType == 2){
			List<Integer> payStatusList = getPayStatusList(payStatus);
			sugerlist = this.bloodSugarMapper.noMeasureList(doctorId, startDt, endDt, payStatusList,hospitalId);
		}
		//??????
		else if(visitType == 1){
			sugerlist = this.bloodSugarMapper.listInHospitalNotMeasuredSugarPatient(departmentId ,startDt ,endDt);
		}
		sugerlist.forEach( x -> {
			BloodSugarPO bloodSugarPO = this.bloodSugarMapper.getLatestBloodSugar(x.getMemberId() ,null,null);
			if(bloodSugarPO != null){
				x.setLastTime(bloodSugarPO.getRecordDt());
			}else{
				x.setLastTime("???????????????");
			}
		});
		PageResult<NormalSugerListResqModel> noPayResult = new PageResult(sugerlist);
		return noPayResult;
	}

	@Override
	public CountMemberVO countWorkbenchSugarDetail(String startDt, String endDt) {
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		CountMemberVO countMemberVO = new CountMemberVO();
		List<Integer> list = new ArrayList<>();//??????
		list.add(2);
		List<Integer> list2 = new ArrayList<>();//??????
		list2.add(1);
		list2.add(3);
		//??????????????????????????????
		List<NormalSugerListResqModel> sugarList =
				this.bloodSugarMapper.noMeasureList
						(doctorModel.getDoctorId(), startDt, endDt, list,doctorModel.getHospitalId());

		countMemberVO.setPaid(sugarList.size());
		//???????????????????????????
		List<NormalSugerListResqModel> normalSugarList =
				bloodSugarMapper.normalSugerList
						(doctorModel.getDoctorId(), startDt, endDt, list,doctorModel.getHospitalId());
		countMemberVO.setPaidO(normalSugarList.size());
		//?????????????????????
		List<NormalSugerListResqModel> sugarList1 =
				this.bloodSugarMapper.noMeasureList
						(doctorModel.getDoctorId(), startDt, endDt, list2,doctorModel.getHospitalId());
		countMemberVO.setUndetected(sugarList1.size());
		//????????????????????????
		List<NormalSugerListResqModel> normalSugarList1 =
				bloodSugarMapper.normalSugerList
						(doctorModel.getDoctorId(), startDt, endDt, list2,doctorModel.getHospitalId());
		countMemberVO.setUndetectedO(normalSugarList1.size());
		return countMemberVO;
	}

	@Override
	@Cacheable(value = "public" ,key = "'bloodSugarParamSetting' + #hospitalId")
	public List<BloodSugarParamSettingVO> listBloodSugarParamSetting(String hospitalId) {
		List<BloodSugarParamSettingVO> result = this.bloodSugarMapper.listBloodSugarParamSetting(hospitalId);
		//???????????????????????????????????????????????????
		if(result == null || result.isEmpty()){
			result = this.bloodSugarMapper.listBloodSugarParamSetting(Constant.DEFAULT_FOREIGN_ID);
		}
		return result;
	}


	@Override
	public Map<String, Object> getGraphsForParametersNew(String memberId, String startDt, String endDt) {
		Map<String , Object> resultMap = new HashMap();
		//??????????????????
//		CountBloodSugarDTO countBloodSugarDTO = new CountBloodSugarDTO();
//		countBloodSugarDTO.setMemberId(memberId);
//		countBloodSugarDTO.setStartDt(startDt);
//		countBloodSugarDTO.setEndDt(endDt);
//		
//		Map<String, Object> personRecord = this.bloodSugarMapper.loadBloodNumHigLow(countBloodSugarDTO);
		
		//
		//??????????????????
		String firstRecTime = "";
		ListBloodSugarDTO temp = new ListBloodSugarDTO();
		temp.setMemberId(memberId);
		List<BloodSugarModel> firstRecTimeList = this.bloodSugarMapper.listBloodSugarSign(temp);
		if(firstRecTimeList.size()>0) {
			firstRecTime = firstRecTimeList.get(firstRecTimeList.size()-1).getRecordDt();
		}
		resultMap.put("firstRecTime", firstRecTime);//??????
		
		//??????????????????
		ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
		listBloodSugarDTO.setMemberId(memberId);
		listBloodSugarDTO.setStartDt(startDt);
		listBloodSugarDTO.setEndDt(endDt);
		
		List<BloodSugarModel> bloodList = this.bloodSugarMapper.listBloodSugarSign(listBloodSugarDTO);	
		
		//??????
		String nowDay = "";
		List<BloodSugarModel> currentDayPoList = new ArrayList(); 
		
		List<BloodSugarGroupByDayModel> totalPoList = new ArrayList(); 
		BloodSugarGroupByDayModel bloodSugarGroupByDayModel  = new BloodSugarGroupByDayModel ();
		
		String currentParamCode = "";

		for(int i=0 ;i<bloodList.size();i++) {
			BloodSugarModel po = bloodList.get(i);
			if(po.getDealNum() == 0) {
				po.setDealStatus("0");
			}else if(po.getDealNum()>0) {
				po.setDealStatus("1");
			}
			
			String recordDt = po.getRecordDt();
			recordDt = recordDt.substring(0, 10);
			
			if(bloodList.size() == 1) {
				if(!po.getParamCode().equals(currentParamCode)) {
					currentDayPoList.add(po);//???????????????????????? ??????1??????
				}
				bloodSugarGroupByDayModel.setDay(recordDt);
				bloodSugarGroupByDayModel.setBloodSugarList(currentDayPoList);
				totalPoList.add(bloodSugarGroupByDayModel);
				
			}else {//????????????1  ????????????
				if(i == 0) {
					currentDayPoList.add(po);//???????????????????????? ??????1??????
				}
				if(i>0) {
					if(!nowDay.equals(recordDt)) {//??????????????????
						bloodSugarGroupByDayModel.setDay(nowDay);
						bloodSugarGroupByDayModel.setBloodSugarList(currentDayPoList);
						//????????????????????????
						currentDayPoList.sort((a ,b) ->{ return b.getRecordDt().compareTo(a.getParamCode());});
						totalPoList.add(bloodSugarGroupByDayModel);
						
						bloodSugarGroupByDayModel = new BloodSugarGroupByDayModel();	
						currentDayPoList = new ArrayList();//?????? ????????????list
						currentDayPoList.add(po);//???????????????????????? ??????1??????						
						if(i == bloodList.size()-1) {
							bloodSugarGroupByDayModel.setDay(recordDt);
							bloodSugarGroupByDayModel.setBloodSugarList(currentDayPoList);
							totalPoList.add(bloodSugarGroupByDayModel);
						}					
					}else {
						if(!po.getParamCode().equals(currentParamCode)) {
							currentDayPoList.add(po);//???????????????????????? ??????1??????
						}
						if(i == bloodList.size()-1) {
							bloodSugarGroupByDayModel.setDay(nowDay);
							bloodSugarGroupByDayModel.setBloodSugarList(currentDayPoList);
							totalPoList.add(bloodSugarGroupByDayModel);
						}
					}
				}

				
			}
			nowDay = recordDt;
			currentParamCode = po.getParamCode();
		}
		
//		//????????????
		RangeBO range = this.memberMapper.getMemberRange(memberId);
		BloodRecordRangeModel rangeModel = new BloodRecordRangeModel();
		
		if(range != null) {
			BeanUtils.copyProperties(range, rangeModel);
		}else {
			rangeModel.setLowBeforeBreakfast("4.4");
			rangeModel.setHighBeforeBreakfast("7.0");
			rangeModel.setLowAfterMeal("4.4");
			rangeModel.setHighAfterMeal("10.0");
		}
		

		resultMap.put("range", rangeModel);//??????
		resultMap.put("paramLog", totalPoList);//??????
		
		return resultMap;
	}

	@Override
	public boolean addMemberBloodRecord(AddBloodSugarMapperDTO addBloodSugarMapperDTO) {
        //??????????????????
		//abnormalMessageHandler(addBloodSugarMapperDTO);
        this.bloodSugarMapper.addBloodSugar(addBloodSugarMapperDTO);		
		return true;
	}

	@Override
	public List<Map<String, Object>> listInHospitalPatientAbnormalSugarDetail(String departmentId ,String startDt ,String endDt) {
		//????????????????????????
		ListMemberDTO listMemberDTO = new ListMemberDTO();
		listMemberDTO.setGroupId(departmentId);
		List<MemberListPO> list = this.memberService.listInHospitalMember(listMemberDTO);
		Map<String ,MemberListPO> memberMap = list.stream().collect(Collectors.toMap(MemberListPO::getMemberId ,x -> x));

		//????????????????????????
		List<BloodSugarPO> bloodSugarList = this.bloodSugarMapper.listInHospitalBloodSugar(departmentId ,startDt ,endDt);
		//????????????????????????
		MultiValueMap<String ,BloodSugarPO> memberBloodSugarGroup = new LinkedMultiValueMap<>();
		for(BloodSugarPO bloodSugar : bloodSugarList){
			memberBloodSugarGroup.add(bloodSugar.getMemberId() ,bloodSugar);
		}

		//???????????????id
		final Set<String> lowLevel = new HashSet<>();
		//?????????
		final Set<String> highLevel = new HashSet<>();
		//??????3.9???
		final Set<String> veryLowLevel = new HashSet<>();
		//??????16.7???
		final Set<String> veryHighLevel = new HashSet<>();
		//??????3????????????
		final Set<String> continuityHighLevel = new HashSet<>();

		Map<String , InHospitalAbnormalSugarPatientVO> patientVoMap = new HashMap<>();
		BloodSugarPO bloodSugar  = null;
		List<BloodSugarPO> memberBloodSugarList = null;
		for(Map.Entry<String ,List<BloodSugarPO>> entry : memberBloodSugarGroup.entrySet()){
			String memberId = entry.getKey();
			memberBloodSugarList = entry.getValue();
			InHospitalAbnormalSugarPatientVO vo = new InHospitalAbnormalSugarPatientVO();
			com.comvee.cdms.common.utils.BeanUtils.copyProperties(vo ,memberMap.get(memberId));
			int highLevelTime = 0;
			Float low = null;
			Float high = null;
			for(int i = 0 ,max = memberBloodSugarList.size() ; i < max ; i++){
				bloodSugar = memberBloodSugarList.get(i);
				if(1 == bloodSugar.getParamLevel()){
					lowLevel.add(memberId);
					highLevelTime = 0;
				}
				if(5 == bloodSugar.getParamLevel()){
					highLevel.add(memberId);
					highLevelTime ++ ;
				}
				Float paramValue = Float.parseFloat(bloodSugar.getParamValue());
				if(paramValue < 3.9f){
					veryLowLevel.add(memberId);
				}
				if(paramValue > 16.7f){
					veryHighLevel.add(memberId);
				}
				if(highLevelTime >= 3){
					continuityHighLevel.add(memberId);
				}
				if(low == null){
					low = paramValue;
				}
				if(high == null){
					high = paramValue;
				}
				if(paramValue < low){
					low = paramValue;
				}
				if(paramValue > high){
					high = paramValue;
				}
			}
			//??????????????????????????????id
			vo.setBloodSugarRecordId(memberBloodSugarList.get(memberBloodSugarList.size() - 1).getSid());
			vo.setHighBloodSugarValue(high.toString());
			vo.setLowBloodSugarValue(low.toString());
			patientVoMap.put(memberId ,vo);
		}

		List<Map<String, Object>> result = new ArrayList<>();
		result.add(getInHospitalPatientAbnormalSugarDetailMap("?????????" ,lowLevel ,patientVoMap));
		result.add(getInHospitalPatientAbnormalSugarDetailMap("?????????" ,highLevel ,patientVoMap));
		result.add(getInHospitalPatientAbnormalSugarDetailMap("??????3????????????" ,continuityHighLevel ,patientVoMap));
		result.add(getInHospitalPatientAbnormalSugarDetailMap("?????????>16.7" ,veryHighLevel ,patientVoMap));
		result.add(getInHospitalPatientAbnormalSugarDetailMap("?????????<3.9" ,veryLowLevel ,patientVoMap));
		return result;
	}

	private List<Integer> getPayStatusList(String payStatus){
        List<Integer> payStatusList = new ArrayList<>();
        if("1".equals(payStatus)){
            payStatusList.add(MemberDoctorConstant.PAY_STATUS_YES);
        }else{
            payStatusList.add(MemberDoctorConstant.PAY_STATUS_NO);
            payStatusList.add(MemberDoctorConstant.PAY_STATUS_EXPIRE);
        }
        return payStatusList;
    }

    private Map<String, Object> getInHospitalPatientAbnormalSugarDetailMap(String text ,Set<String> memberSet ,Map<String , InHospitalAbnormalSugarPatientVO> patientVoMap){
		Map<String ,Object> result = new HashMap<>();
		result.put("text" ,text);
		result.put("number" ,memberSet.size());
		List<InHospitalAbnormalSugarPatientVO> patientList = new ArrayList<>();
		InHospitalAbnormalSugarPatientVO vo = null;
		for(String s : memberSet){
			vo = patientVoMap.get(s);
			if(vo != null){
				patientList.add(vo);
			}
		}
		result.put("patientList" ,patientList);
		return result;
	}

}
