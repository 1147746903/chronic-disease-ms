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
//		//2获取偏低血糖
//		String paramLevel = "2";
//		List<BloodNumByStatusResqModel> totalList = this.bloodSugarMapper.getBloodNumByStatus(doctorId, startDt, endDt, paramLevel, null);
//		int lowTotalPatientNums_int = 0 ;
//		if(totalList != null) {
//			lowTotalPatientNums_int = totalList.size();
//		}
//		Long lowTotalPatientNums = Long.valueOf(lowTotalPatientNums_int);
//		
//		List<String> memberIdList = new ArrayList();//所有的偏低患者  里过滤  获取到 付费患者数
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
//		//2获取偏低血糖 end...
//		
//		//1空腹高危险血糖  beforeBreakfast  表示空腹
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
//		map.put("kf_hasPayNum", kf_hasPayNum);//空腹 已付款
//		map.put("kf_noPayNum", kf_noPayNum);  //空腹 免费	
//		//1空腹高危险血糖 end...
//		
//		//1非空腹高危险血糖    表示非空腹
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
//		map.put("nokf_hasPayNum", nokf_hasPayNum);//空腹 已付款
//		map.put("nokf_noPayNum", nokf_noPayNum);  //空腹 免费	
//		//1非空腹 end...
//		
//		//4连续三次偏高血糖
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
//				current_flag=0;//换个患者 变成0
//			}
//			if("4".equals(level)) {
//				current_flag++ ;
//			}else {
//				current_flag=0;//不连续 变成0
//			}
//			if(current_flag>=3) {//连续三次
//				if(!current_memberId.equals(memberId)) {//连续三次  把这个患者加入进去
//					threeTotal++;
//					three_memberIdList.add(current_memberId);//获取所有的 memberid 来过滤
//				}else if(i == threeNumList.size()-1) {
//					threeTotal++;
//					three_memberIdList.add(current_memberId);//获取所有的 memberid 来过滤
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
//		map.put("hasPay_Three", hasPay_Three);//空腹 已付款
//		map.put("noPay_Three", noPay_Three);  //空腹 免费			
//		
//		return map;
//	}

	@Override
	public List<Map<String,Object>> statisticsMemberAbnormalsNew(String doctorId , String payStatus, String startDt, String endDt,String hospitalId) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		Map<String , Object> map = new HashMap();
		//2获取偏低血糖
		String paramLevel = "2";
		List<BloodNumByStatusResqModel> totalList = this.bloodSugarMapper.getBloodNumByStatus(doctorId, startDt, endDt, paramLevel, null ,payStatus,hospitalId);
		int lowTotalPatientNums_int = 0 ;
		if(totalList != null) {
			lowTotalPatientNums_int = totalList.size();
		}		
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("text", "低血糖");
		map1.put("code", "2");
		map1.put("icon", "http://comveetest.oss-cn-hangzhou.aliyuncs.com/201610/1410/1610141025187873.png");	
		map1.put("number", lowTotalPatientNums_int);
		resultList.add(map1);
		//2获取偏低血糖 end...
		
		//3空腹高危险血糖  beforeBreakfast  表示空腹
		paramLevel = "5";
		List<BloodNumByStatusResqModel> kfList = this.bloodSugarMapper.getBloodNumByStatus(doctorId, startDt, endDt, paramLevel, "1" , payStatus,hospitalId);
		int kfTotalPatientNums_int = 0 ;
		if(kfList != null) {
			kfTotalPatientNums_int = kfList.size();
		}		
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("text", "空腹高危高血糖");
		map2.put("code", "3");		
		map2.put("icon", "http://comveetest.oss-cn-hangzhou.aliyuncs.com/201610/1410/1610141026174227.png");
		map2.put("number", kfTotalPatientNums_int);
		resultList.add(map2);
		//1空腹高危险血糖 end...
		
		//2非空腹高危险血糖    表示非空腹
		paramLevel = "5";
		List<BloodNumByStatusResqModel> no_kfList = this.bloodSugarMapper.getBloodNumByStatus(doctorId, startDt, endDt, paramLevel, "2" , payStatus,hospitalId);
		int no_kfTotalPatientNums_int = 0 ;
		if(no_kfList != null) {
			no_kfTotalPatientNums_int = no_kfList.size();
		}
		
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("text", "非空腹高危高血糖");
		map3.put("code", "4");		
		map3.put("icon", "http://comveetest.oss-cn-hangzhou.aliyuncs.com/201610/1410/1610141025516135.png");
		map3.put("number", no_kfTotalPatientNums_int);
		resultList.add(map3);
		//2非空腹 end...
		
		//4连续三次偏高血糖
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
				current_flag=0;//换个患者 变成0
			}		
			if("4".equals(level)) {//是偏高+1
				current_flag++ ;
			}else if("5".equals(level)){
				current_flag++ ;
			}else{
				current_flag=0;//不连续 变成0
			}
			if(current_flag>=3) {//连续三次
				if(three_memberIdList.indexOf(memberId) < 0){
					threeTotal++;
					three_memberIdList.add(current_memberId);//获取所有的 memberid 来过滤
				}
/*				if(!current_memberId.equals(memberId)) {//连续三次  把这个患者加入进去
					threeTotal++;
					three_memberIdList.add(current_memberId);//获取所有的 memberid 来过滤
				}else if(i == threeNumList.size()-1) {
					threeTotal++;
					three_memberIdList.add(current_memberId);//获取所有的 memberid 来过滤
				}*/
			}			
			
		}
		
		Map<String,Object> map4 = new HashMap<String,Object>();
		map4.put("text", "连续3次高血糖");
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
		if(type.equals("2")) {//2获取偏低血糖
			paramLevel = "2";
			//payStatus 1付款  0 免费
			PageHelper.startPage(page.getPage(), page.getRows());
			List<SugarDetailListModel> list = this.bloodSugarMapper.lowSugerList(doctorId, startDt, endDt, paramLevel, null , payStatus,hospitalId);//低血糖的 - 条件患者
			for (SugarDetailListModel sugarDetailListModel : list) {
				String num = sugarDetailListModel.getNum();
				String paramValue = sugarDetailListModel.getParamValue();
				if(StringUtils.isBlank(paramValue)) {
					sugarDetailListModel.setText("低血糖"+num+"次,血糖值：--mmol/L");
				}else {
					sugarDetailListModel.setText("低血糖"+num+"次,血糖值："+paramValue+"mmol/L");
				}
			}
			PageResult<SugarDetailListModel> pageResult = new PageResult(list);
			return pageResult;
		}else if(type.equals("3")){//3空腹高危
			paramLevel = "5";
			//payStatus 1付款  0 免费
			PageHelper.startPage(page.getPage(), page.getRows());
			List<SugarDetailListModel> list = this.bloodSugarMapper.lowSugerList(doctorId, startDt, endDt, paramLevel, "1" , payStatus,hospitalId);//paramCode=1 空腹
			for (SugarDetailListModel sugarDetailListModel : list) {
				String num = sugarDetailListModel.getNum();
				String paramValue = sugarDetailListModel.getParamValue();
				if(StringUtils.isBlank(paramValue)) {
					sugarDetailListModel.setText("空腹高危血糖"+num+"次,血糖值：--mmol/L");
				}else {
					sugarDetailListModel.setText("空腹高危血糖"+num+"次,血糖值："+paramValue+"mmol/L");
				}
			}
			
			PageResult<SugarDetailListModel> pageResult = new PageResult(list);
			
			return pageResult;				
		}else if(type.equals("4")) {
			paramLevel = "5";
			//payStatus 1付款  0 免费
			PageHelper.startPage(page.getPage(), page.getRows());
			List<SugarDetailListModel> list = this.bloodSugarMapper.lowSugerList(doctorId, startDt, endDt, paramLevel, "2" , payStatus,hospitalId);//paramCode=1 空腹
			for (SugarDetailListModel sugarDetailListModel : list) {
				String num = sugarDetailListModel.getNum();
				String paramValue = sugarDetailListModel.getParamValue();
				if(StringUtils.isBlank(paramValue)) {
					sugarDetailListModel.setText("非空腹高危血糖"+num+"次,血糖值：--mmol/L");
				}else {
					sugarDetailListModel.setText("非空腹高危血糖"+num+"次,血糖值："+paramValue+"mmol/L");
				}
			}
			
			PageResult<SugarDetailListModel> pageResult = new PageResult(list);
			
			return pageResult;
		}
		else if(type.equals("6")) {//连续三次偏高
			paramLevel = "4";
			//payStatus 1付款  0 免费
			List<SugarDetailListModel> list = this.bloodSugarMapper.getBloodThreeDayListByStatus(doctorId, startDt, endDt,  payStatus,hospitalId);//所有大于三条的血糖记录
			
			String front_memberId = "";//前患者
			String textNow = "";//当前文本
			int current_flag = 0;//当前连续数
			int threeTotal = 0 ;
			List<SugarDetailListModel> three_memberList = new ArrayList();//
			List<String> three_memberIdList = new ArrayList();
			for(int i=0; i<list.size(); i++) {
				SugarDetailListModel sdlModel = list.get(i);
				String level = sdlModel.getParamLevel();
				String memberId = sdlModel.getMemberId();
				
				String paramName = returnParamCodeName(sdlModel.getParamCode() == null?"":sdlModel.getParamCode());
				String text = sdlModel.getRecordDt().substring(5,7)+"月"+sdlModel.getRecordDt().substring(8,10)+"日";
				text = text + paramName;
				
				
				if(!front_memberId.equals(memberId)) {
					front_memberId = memberId;
					current_flag=0;//换个患者 变成0
					textNow = text+"、";
				}
				if("4".equals(level)) {
					current_flag++ ;
					textNow += text +"、";
				}else if("5".equals(level)){
					current_flag++ ;
					textNow += text +"、";
				}else{
					current_flag=0;//不连续 变成0
					textNow = text+"、";
				}
				if(current_flag >= 3){

					if(three_memberIdList.indexOf(memberId) < 0){
						sdlModel.setText(textNow);
						three_memberList.add(sdlModel);//获取所有的 memberid 来过滤
						textNow = "";
						//先吧符合条件的存到list中
						three_memberIdList.add(memberId);
//						//在判断这个memberId是否在list中
//						if (!three_memberList.contains(memberId)){
//
//						}
					}
				}
/*				if(current_flag>=3) {//连续三次
					if(!front_memberId.equals(memberId)) {//连续三次  把这个患者加入进去
						sdlModel.setText(textNow);
						textNow = "";
						three_memberList.add(sdlModel);//获取所有的 memberid 来过滤
					}else if(i == list.size()-1) {
						sdlModel.setText(textNow);
						three_memberList.add(sdlModel);//获取所有的 memberid 来过滤
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
        // 凌晨3点
        case SignConstant.PARAM_CODE_3AM:
        	result = "凌晨3点";
            break;
        //早餐后
        case SignConstant.PARAM_CODE_AFTER_BREAKFAST:
        	result = "早餐后";
            break;
        //晚餐后
        case SignConstant.PARAM_CODE_AFTER_DINNER:
        	result = "晚餐后";
            break;
        //午餐后
        case SignConstant.PARAM_CODE_AFTER_LUNCH:
        	result = "午餐后";
            break;
        //早餐前
        case SignConstant.PARAM_CODE_BEFORE_BREAKFAST:
        	result = "早餐前";
            break;
        //凌晨
        case SignConstant.PARAM_CODE_BEFORE_DAWN:
        	result = "凌晨";
            break;
        //晚餐前
        case SignConstant.PARAM_CODE_BEFORE_DINNER:
        	result = "晚餐前";
            break;
        //午餐前
        case SignConstant.PARAM_CODE_BEFORE_LUNCH:
        	result = "午餐前";
            break;
        //睡前
        case SignConstant.PARAM_CODE_BEFORE_SLEEP:
        	result = "睡前";
            break;
        //随机血糖
        case SignConstant.PARAM_CODE_RANDOM_TIME:
        	result = "随机血糖";
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
        //住院
		if(visitType == 1){

			normalSugerList = this.bloodSugarMapper.listInHospitalNormalSugarPatient(departmentId ,startDt ,endDt);
		}
		//居家
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
		//居家
		if(visitType == 2){
			List<Integer> payStatusList = getPayStatusList(payStatus);
			sugerlist = this.bloodSugarMapper.noMeasureList(doctorId, startDt, endDt, payStatusList,hospitalId);
		}
		//住院
		else if(visitType == 1){
			sugerlist = this.bloodSugarMapper.listInHospitalNotMeasuredSugarPatient(departmentId ,startDt ,endDt);
		}
		sugerlist.forEach( x -> {
			BloodSugarPO bloodSugarPO = this.bloodSugarMapper.getLatestBloodSugar(x.getMemberId() ,null,null);
			if(bloodSugarPO != null){
				x.setLastTime(bloodSugarPO.getRecordDt());
			}else{
				x.setLastTime("无测量记录");
			}
		});
		PageResult<NormalSugerListResqModel> noPayResult = new PageResult(sugerlist);
		return noPayResult;
	}

	@Override
	public CountMemberVO countWorkbenchSugarDetail(String startDt, String endDt) {
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		CountMemberVO countMemberVO = new CountMemberVO();
		List<Integer> list = new ArrayList<>();//付费
		list.add(2);
		List<Integer> list2 = new ArrayList<>();//免费
		list2.add(1);
		list2.add(3);
		//未检测血糖付费的患者
		List<NormalSugerListResqModel> sugarList =
				this.bloodSugarMapper.noMeasureList
						(doctorModel.getDoctorId(), startDt, endDt, list,doctorModel.getHospitalId());

		countMemberVO.setPaid(sugarList.size());
		//正常血糖付费的患者
		List<NormalSugerListResqModel> normalSugarList =
				bloodSugarMapper.normalSugerList
						(doctorModel.getDoctorId(), startDt, endDt, list,doctorModel.getHospitalId());
		countMemberVO.setPaidO(normalSugarList.size());
		//未检测免费患者
		List<NormalSugerListResqModel> sugarList1 =
				this.bloodSugarMapper.noMeasureList
						(doctorModel.getDoctorId(), startDt, endDt, list2,doctorModel.getHospitalId());
		countMemberVO.setUndetected(sugarList1.size());
		//正常血糖免费患者
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
		//根据医院取的结果为空，则返回默认值
		if(result == null || result.isEmpty()){
			result = this.bloodSugarMapper.listBloodSugarParamSetting(Constant.DEFAULT_FOREIGN_ID);
		}
		return result;
	}


	@Override
	public Map<String, Object> getGraphsForParametersNew(String memberId, String startDt, String endDt) {
		Map<String , Object> resultMap = new HashMap();
		//个人血糖数据
//		CountBloodSugarDTO countBloodSugarDTO = new CountBloodSugarDTO();
//		countBloodSugarDTO.setMemberId(memberId);
//		countBloodSugarDTO.setStartDt(startDt);
//		countBloodSugarDTO.setEndDt(endDt);
//		
//		Map<String, Object> personRecord = this.bloodSugarMapper.loadBloodNumHigLow(countBloodSugarDTO);
		
		//
		//个人血糖列表
		String firstRecTime = "";
		ListBloodSugarDTO temp = new ListBloodSugarDTO();
		temp.setMemberId(memberId);
		List<BloodSugarModel> firstRecTimeList = this.bloodSugarMapper.listBloodSugarSign(temp);
		if(firstRecTimeList.size()>0) {
			firstRecTime = firstRecTimeList.get(firstRecTimeList.size()-1).getRecordDt();
		}
		resultMap.put("firstRecTime", firstRecTime);//列表
		
		//个人血糖列表
		ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
		listBloodSugarDTO.setMemberId(memberId);
		listBloodSugarDTO.setStartDt(startDt);
		listBloodSugarDTO.setEndDt(endDt);
		
		List<BloodSugarModel> bloodList = this.bloodSugarMapper.listBloodSugarSign(listBloodSugarDTO);	
		
		//修改
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
					currentDayPoList.add(po);//获取当前血糖对象 只有1个值
				}
				bloodSugarGroupByDayModel.setDay(recordDt);
				bloodSugarGroupByDayModel.setBloodSugarList(currentDayPoList);
				totalPoList.add(bloodSugarGroupByDayModel);
				
			}else {//数量超过1  如有三个
				if(i == 0) {
					currentDayPoList.add(po);//获取当前血糖对象 只有1个值
				}
				if(i>0) {
					if(!nowDay.equals(recordDt)) {//最后一个相等
						bloodSugarGroupByDayModel.setDay(nowDay);
						bloodSugarGroupByDayModel.setBloodSugarList(currentDayPoList);
						//根据记录时间倒序
						currentDayPoList.sort((a ,b) ->{ return b.getRecordDt().compareTo(a.getParamCode());});
						totalPoList.add(bloodSugarGroupByDayModel);
						
						bloodSugarGroupByDayModel = new BloodSugarGroupByDayModel();	
						currentDayPoList = new ArrayList();//不等 换个新的list
						currentDayPoList.add(po);//获取当前血糖对象 只有1个值						
						if(i == bloodList.size()-1) {
							bloodSugarGroupByDayModel.setDay(recordDt);
							bloodSugarGroupByDayModel.setBloodSugarList(currentDayPoList);
							totalPoList.add(bloodSugarGroupByDayModel);
						}					
					}else {
						if(!po.getParamCode().equals(currentParamCode)) {
							currentDayPoList.add(po);//获取当前血糖对象 只有1个值
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
		
//		//餐后目标
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
		

		resultMap.put("range", rangeModel);//目标
		resultMap.put("paramLog", totalPoList);//列表
		
		return resultMap;
	}

	@Override
	public boolean addMemberBloodRecord(AddBloodSugarMapperDTO addBloodSugarMapperDTO) {
        //异常消息处理
		//abnormalMessageHandler(addBloodSugarMapperDTO);
        this.bloodSugarMapper.addBloodSugar(addBloodSugarMapperDTO);		
		return true;
	}

	@Override
	public List<Map<String, Object>> listInHospitalPatientAbnormalSugarDetail(String departmentId ,String startDt ,String endDt) {
		//加载住院患者列表
		ListMemberDTO listMemberDTO = new ListMemberDTO();
		listMemberDTO.setGroupId(departmentId);
		List<MemberListPO> list = this.memberService.listInHospitalMember(listMemberDTO);
		Map<String ,MemberListPO> memberMap = list.stream().collect(Collectors.toMap(MemberListPO::getMemberId ,x -> x));

		//加载住院患者血糖
		List<BloodSugarPO> bloodSugarList = this.bloodSugarMapper.listInHospitalBloodSugar(departmentId ,startDt ,endDt);
		//根据患者分组血糖
		MultiValueMap<String ,BloodSugarPO> memberBloodSugarGroup = new LinkedMultiValueMap<>();
		for(BloodSugarPO bloodSugar : bloodSugarList){
			memberBloodSugarGroup.add(bloodSugar.getMemberId() ,bloodSugar);
		}

		//低血糖患者id
		final Set<String> lowLevel = new HashSet<>();
		//高血糖
		final Set<String> highLevel = new HashSet<>();
		//小于3.9的
		final Set<String> veryLowLevel = new HashSet<>();
		//大于16.7的
		final Set<String> veryHighLevel = new HashSet<>();
		//连续3次高血糖
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
			//取最后一条血糖记录的id
			vo.setBloodSugarRecordId(memberBloodSugarList.get(memberBloodSugarList.size() - 1).getSid());
			vo.setHighBloodSugarValue(high.toString());
			vo.setLowBloodSugarValue(low.toString());
			patientVoMap.put(memberId ,vo);
		}

		List<Map<String, Object>> result = new ArrayList<>();
		result.add(getInHospitalPatientAbnormalSugarDetailMap("低血糖" ,lowLevel ,patientVoMap));
		result.add(getInHospitalPatientAbnormalSugarDetailMap("高血糖" ,highLevel ,patientVoMap));
		result.add(getInHospitalPatientAbnormalSugarDetailMap("连续3次高血糖" ,continuityHighLevel ,patientVoMap));
		result.add(getInHospitalPatientAbnormalSugarDetailMap("高血糖>16.7" ,veryHighLevel ,patientVoMap));
		result.add(getInHospitalPatientAbnormalSugarDetailMap("低血糖<3.9" ,veryLowLevel ,patientVoMap));
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
