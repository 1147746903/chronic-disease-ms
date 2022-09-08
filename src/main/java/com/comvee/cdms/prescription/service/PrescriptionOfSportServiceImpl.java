package com.comvee.cdms.prescription.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.cfg.ParamLogConstant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.prescription.bo.ApiDoctorBO;
import com.comvee.cdms.prescription.bo.ApiMemberArchivesBO;
import com.comvee.cdms.prescription.bo.BmiRangeSetBO;
import com.comvee.cdms.prescription.bo.PrescriptionBO;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.cfg.PrescriptionHelperOfSport;
import com.comvee.cdms.prescription.dto.GetPrescriptionDetailDTO;
import com.comvee.cdms.prescription.dto.GetPrescriptionSportOutDTO;
import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("prescriptionOfSportService")
public class PrescriptionOfSportServiceImpl implements PrescriptionOfSportServiceI {

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private PrescriptionServiceI prescriptionService;
    
    @Autowired
    private PrescriptionApiI prescriptionApi;


    @Override
    public PrescriptionForSportVO intelligentRecommendationOfSport(String baseJson,Integer eohType) {
        PrescriptionSportOutVO sportOutVO =new PrescriptionSportOutVO();
        PrescriptionForSportVO model=new PrescriptionForSportVO();
        //baseJson  页面参数为 sprot_bmi
        GetPrescriptionSportOutDTO sportOut = JsonSerializer.jsonToObject(baseJson, GetPrescriptionSportOutDTO.class);
        //适宜情况
      	String adaptcondition = sportOut.getSportPlan();
		String birthday = StringUtils.converParamToString(sportOut.getSportBirth()); //出生日期
		String bmi = StringUtils.converParamToString(sportOut.getSprotBmi());  //bmi
		String bsSport = StringUtils.converParamToString(sportOut.getMemberSprotCase());  //是否有运动习惯
		String bsSportRequency = StringUtils.converParamToString(sportOut.getSportFrequency());  //运动频率
		String bsSportLong = StringUtils.converParamToString(sportOut.getSportDuration());  //运动时长
		String bsBgSportTime = StringUtils.converParamToString(sportOut.getSportOpportunity());  //运动时机
		String bsGestationalWeeks = StringUtils.converParamToString(sportOut.getGestationalWeeks());//孕周

		//如果是不适合运动直接返回
		if (eohType != null && eohType == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_DIABETES_GESTATION && Constant.CONST_NUM_05.equals(adaptcondition)){  //妊娠处方
				sportOutVO.setSportSuggestText(PrescriptionHelperOfSport.RSJD_FORBID_SPORT_ADVICE);
		}else if (eohType != null && eohType == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_DIABETES_2 && Constant.CONST_NUM_03.equals(adaptcondition)){  //2型处方
				sportOutVO.setSportSuggestText(PrescriptionHelperOfSport.FORBID_SPORT_ADVICE);
		}
		//高血压处方
		else if(eohType != null && eohType == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_HYP){
			//绝对禁忌症
			if(Constant.CONST_NUM_02.equals(adaptcondition)){
				sportOutVO.setSportRule(PrescriptionHelperOfSport.HYP_FORBID_SPORT_ADVICE);
			}else{
				//问题建议输出
				List<Map<String, Object>> sportOutPut = PrescriptionHelperOfSport.getHypSportOutPut(bsSport ,bsSportRequency ,bsSportLong ,bsBgSportTime);
				String sportRule = PrescriptionHelperOfSport.getHypSportRule(bmi ,sportOut.getSprotWeight());
				sportOutVO.setSportOutList(sportOutPut);
				sportOutVO.setSportRule(sportRule);
			}
		}else{
      		try {
				List<Map<String, Object>> sportOutPut = new ArrayList<>();
				String sportRule = "";
				Integer sportType = null;  //运动强度
				String heartRate = "";  //心率
				if (null != eohType && eohType == 1){ //妊娠
					sportOutPut = PrescriptionHelperOfSport.getSportOutPut(bmi, bsSport, bsSportRequency, bsSportLong, bsBgSportTime, null,eohType);
					sportRule = PrescriptionHelperOfSport.getSportRule(bmi, null, null,bsGestationalWeeks,eohType);
					sportType = PrescriptionHelperOfSport.getSportintensity(bsGestationalWeeks);
					heartRate = PrescriptionHelperOfSport.getHeartRate(bmi, birthday);
				}else{  //非妊娠
					BmiRangeSetBO healthRangeSet = new BmiRangeSetBO();
					healthRangeSet.setLowBmi(ParamLogConstant.RANGE_BMI_STANDARD_LOW.toString());
					healthRangeSet.setHighBmi(ParamLogConstant.RANGE_BMI_STANDARD_HIGH.toString());
					sportOutPut = PrescriptionHelperOfSport.getSportOutPut(bmi, bsSport, bsSportRequency, bsSportLong, bsBgSportTime, healthRangeSet,eohType);
					String weight = StringUtils.converParamToString(sportOut.getSprotWeight());
					sportRule = PrescriptionHelperOfSport.getSportRule(bmi, weight, healthRangeSet,bsGestationalWeeks,eohType);
				}
				sportOutVO.setSportOutList(sportOutPut);
				sportOutVO.setSportRule(sportRule);
				sportOutVO.setSportType(sportType);
				sportOutVO.setHeartRate(heartRate);

      		} catch (Exception e) {
				e.printStackTrace();
			}
      	}
        String objectToJson = JsonSerializer.objectToJson(sportOutVO);
        model.setSportInfo(objectToJson);
        return model;
    }

	@Override
    public PrescriptionForSportVO getPrescriptionSport(String prescriptionId,Integer eohType) {
        PrescriptionForSportVO model = new PrescriptionForSportVO();
        PrescriptionSportLoadVO reModel=new PrescriptionSportLoadVO();
        //运动信息
        GetPrescriptionDetailDTO dto = new GetPrescriptionDetailDTO();
        dto.setPrescriptionId(prescriptionId);
        dto.setType(PrescriptionConstant.MODULE_TYPE_SPORT);
        PrescriptionDetailPO detail = this.prescriptionMapper.getPrescriptionDetailByParam(dto);
        model.setSid(detail.getSid());
        model.setSaveState(detail.getSaveState());
        String sportJson = detail.getDetailJson();
        if(!StringUtils.isBlank(sportJson)){
            //TODO 获取推荐运动信息
        	Map<String, Object> sportMap = JsonSerializer.jsonToMap(sportJson);
        	String sportOut = sportMap.get("sportOutJson").toString();
        	
        	List<Map<String, Object>> sportOutList = new ArrayList<Map<String, Object>>();
    		if(!StringUtils.isBlank(sportOut)) {
    			sportOutList = JsonSerializer.jsonToMapList(sportOut);
    		}
    		
    		// 排序
    		//优先链
    		List<Map<String, Object>> sportOutList1 = new ArrayList<Map<String, Object>>();
    		//普通链
    		List<Map<String, Object>> sportOutList2 = new ArrayList<Map<String, Object>>();
    		for(Map<String, Object> map : sportOutList){
    			Object tempHasChecked = map.get("hasChecked");
    			if(tempHasChecked != null && Constant.CONST_NUM_01.equals(tempHasChecked.toString())){
    				sportOutList1.add(map);
    			} else {
    				sportOutList2.add(map);
    			}
    		}
    		sportOutList2.addAll(0, sportOutList1);
    		sportOutList = sportOutList2;
    		// 运动适宜情况
    		String sportPlan = null; 
    		// 禁忌症类型
    		String contraindication = null; 
    		
    		// 获取运动治疗方案
    		List<PrescriptionSportVO> membersportlist = new ArrayList<PrescriptionSportVO>();
    		PrescriptionSportVO eohMemberSportModel = new PrescriptionSportVO();
    		eohMemberSportModel.setSportItemDetailJson(sportMap.get("sportListStr").toString());
    		membersportlist.add(eohMemberSportModel);
    		Map<String, Object> sportItem = new HashMap<String, Object>(1);
    		if(membersportlist != null && membersportlist.size() > 0) {
    			PrescriptionSportVO eohMemberSport = membersportlist.get(0);
    			String json = eohMemberSport.getSportItemDetailJson();
    			if(!StringUtils.isBlank(json)) {
    				List<Map<String, Object>> itemList = JsonSerializer.jsonToMapList(json);
    				if(itemList !=null && itemList.size() > 0) {
    					sportItem = itemList.get(0);
    				}
    			}
    		}
    
    		Map<String, String> pictureMapping = PrescriptionHelperOfSport.getSportMethodMapping();
    		
    		reModel.setSportPlan(sportPlan);
    		reModel.setSportOutList(sportOutList);
    		reModel.setSportRule(sportMap.get("sportRule").toString());
    		reModel.setSportTips(sportMap.get("sportTip").toString());
    		reModel.setSportItem(sportItem);
    		reModel.setPictureMapping(pictureMapping);
    		reModel.setContraindication(contraindication);
    		
    		Map<String, Object> sportInfoStr = JsonSerializer.jsonToMap(sportMap.get("sportInfoStr").toString());
    		reModel.setSportInfoStr(sportInfoStr);
 
        }
		PrescriptionVO pre = this.prescriptionService.getPrescriptionById(prescriptionId);

        if (null != detail && null == detail.getDetailJson() && !StringUtils.isBlank(detail.getMemberId()) && eohType == 1){
					// 获取上次妊娠管理处方运动患者基本信息
					ApiDoctorBO doctor = this.prescriptionApi.getDoctorById(pre.getTeamId());
			if (null != doctor){
						String hospitalId = doctor.getHospitalId();
						GetPrescriptionDetailDTO dto1 = new GetPrescriptionDetailDTO();
						dto1.setMemberId(detail.getMemberId());
						dto1.setHospitalId(hospitalId);
						dto1.setEohType(eohType);
						dto1.setType(4);
						PrescriptionDetailPO detailPO = this.prescriptionService.getNewPrescriptionDetailByType(dto1);
						if(null != detailPO && !StringUtils.isBlank(detailPO.getDetailJson())){
							Map<String, Object> jsonMap = JsonSerializer.jsonToMap(detailPO.getDetailJson());
							if (null != jsonMap && jsonMap.size() >0){
								if (null != jsonMap.get("sportInfoStr") && !StringUtils.isBlank(jsonMap.get("sportInfoStr").toString())){
									Map<String, Object> sportInfoStr = JsonSerializer.jsonToMap(jsonMap.get("sportInfoStr").toString());
									sportInfoStr.put("sport_plan","");
									reModel.setSportInfoStr(sportInfoStr);
								}
							}
						}
					}
		}

			if(null!=detail && !StringUtils.isBlank(detail.getMemberId()) && eohType == 0){
				//1、获取患者档案
					ApiMemberArchivesBO memberArchivesModel = this.prescriptionApi.getMemberArchivesById(detail.getMemberId(), pre.getTeamId());
					if(null!=memberArchivesModel){
						//3、获取运动频率
						String dailyHabits = memberArchivesModel.getDailyHabits();
						if (!StringUtils.isBlank(dailyHabits)) {
							Map<String, String> map = JSONObject.parseObject(dailyHabits, new TypeReference<Map<String, String>>() {});
							if (null != map) {
								//是否有运动习惯
								reModel.setMemberSprotCase(map.get("memberSprotCase"));
								//开始运动时间
								reModel.setSportOpportunity(map.get("sportOpportunity"));
								//运动时长
								reModel.setSportDuration(map.get("sportDuration"));
								//运动频率
								reModel.setSportFrequency(map.get("sportFrequency"));
								//运动方式
								String sMethod="";
								if(!StringUtils.isBlank(map.get("lowSportWay"))){
									sMethod = sMethod+map.get("lowSportWay")+",";
								}
								if(!StringUtils.isBlank(map.get("middleSportWay"))){
									sMethod = sMethod+map.get("middleSportWay")+",";
								}
								if(!StringUtils.isBlank(map.get("highSportWay"))){
									sMethod = sMethod+map.get("highSportWay")+",";
								}
								if(sMethod.length()>0){
									sMethod=sMethod.substring(0, sMethod.length()-1);
								}
								reModel.setSprotMethod(sMethod);
							}
						}
					}
				}
//			}

        String objectToJson = JsonSerializer.objectToJson(reModel);
        model.setSportInfo(objectToJson);
        return model;
    }
    
    @Override
    public void modifySport(PrescriptionDTO prescriptionDTO){
    	
    	Map<String, Object> parMap = JsonSerializer.jsonToMap(prescriptionDTO.getDetailJson());
		Map<String,Object> sportParamMap = new HashMap<String,Object>();
		sportParamMap.put("sportListStr", parMap.get("sportListStr"));
		sportParamMap.put("sportInfoStr", parMap.get("sportInfoStr"));
		sportParamMap.put("sportOutJson", parMap.get("sportOutJson"));
		sportParamMap.put("sportTip", parMap.get("sportTips"));
		sportParamMap.put("sportRule", parMap.get("xdjh"));
		String re = JsonSerializer.objectToJson(sportParamMap);
		prescriptionDTO.setDetailJson(re);

        PrescriptionBO prescriptionBO = new PrescriptionBO();
		BeanUtils.copyProperties(prescriptionBO, prescriptionDTO);
        prescriptionService.modifyPrescriptionDetail(prescriptionBO);
    }
    
    
}
