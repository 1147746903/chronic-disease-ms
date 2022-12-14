package com.comvee.cdms.prescription.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.comvee.cdms.defender.common.cfg.ConstantCourse;
import com.comvee.cdms.defender.constenum.CourseTag;
import com.comvee.cdms.defender.model.CourseModel;
import com.comvee.cdms.defender.service.CourseServiceI;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorPlanMapper;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.common.cfg.ParamLogConstant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.GetMemberDrugItemDTO;
import com.comvee.cdms.member.dto.ListDoctorMemberDTO;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.DoctorMemberPO;
import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.po.MemberDrugItemPO;
import com.comvee.cdms.member.po.MemberDrugRecordPO;
import com.comvee.cdms.member.service.MemberCacheServiceI;
import com.comvee.cdms.prescription.bo.*;
import com.comvee.cdms.prescription.cfg.InfoManager;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.*;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.MemberPrescriptionPO;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.po.PrescriptionKnowledgePO;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import com.comvee.cdms.prescription.tool.KnowledgeTool;
import com.comvee.cdms.prescription.vo.PrescriptionComplicationVO;
import com.comvee.cdms.prescription.vo.PrescriptionDetailVO;
import com.comvee.cdms.prescription.vo.PrescriptionEduVO;
import com.comvee.cdms.prescription.vo.PrescriptionVO;
import com.comvee.cdms.prescription.vo.eduplan.Knowledge;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeTreeVO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeVO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeWeek;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ?????????
 * @date 2018/8/3 10:11.
 */
@Service("prescriptionOfEduService")
public class PrescriptionOfEduServiceImpl implements PrescriptionOfEduServiceI {

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private PrescriptionApiI prescriptionApi;

    @Autowired
    private PrescriptionServiceI prescriptionService;
    
    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private MemberMonitorPlanMapper memberMonitorPlanMapper;

    @Autowired
    private CourseServiceI courseService;

	@Autowired
	private MemberCacheServiceI memberCacheService;

    @Override
    public PrescriptionDetailVO<PrescriptionEduVO> getPrescriptionEdu(PrescriptionDTO prescriptionDTO) {
        PrescriptionDetailVO<PrescriptionEduVO> model = new PrescriptionDetailVO();
        model.setModuleType(prescriptionDTO.getType());
        model.setModuleName(PrescriptionConstant.MODULE_NAME_EDU);


        //1?????????????????????
        GetPrescriptionDetailDTO dto = new GetPrescriptionDetailDTO();
        dto.setPrescriptionId(prescriptionDTO.getPrescriptionId());
        dto.setType(prescriptionDTO.getType());
        PrescriptionDetailPO detail = this.prescriptionService.getPrescriptionDetailByParam(dto);

        //2???????????????????????????json????????????
        if (null != detail) {
            PrescriptionEduVO prescriptionEduVO = new PrescriptionEduVO();
            if (0 == detail.getSaveState() || StringUtils.isBlank(detail.getDetailJson())) {
                String memberId = detail.getMemberId();
                this.fullPrescriptionEdu(prescriptionEduVO, memberId, prescriptionDTO.getPrescriptionId());
            }else {
                prescriptionEduVO = JSONObject.parseObject(detail.getDetailJson(), PrescriptionEduVO.class);
                //????????????
                List<PrescriptionKnowledgePO> knowledgeList = this.prescriptionMapper.listPrescriptionKnowledge(prescriptionDTO.getPrescriptionId());
                List<KnowledgeWeek> knowledgeWeekList = this.handleWeekOfKnowledgeList(knowledgeList);
                prescriptionEduVO.setKnowledgeWeekList(knowledgeWeekList);
                prescriptionEduVO.setKnowledgeList(knowledgeList);
            }
            model.setModuleSid(detail.getSid());
            model.setModule(prescriptionEduVO);
            model.setSaveState(detail.getSaveState());
        }
        return model;
    }

    @Override
    public PrescriptionDetailVO<KnowledgeVO> intelligentRecommendationOfEdu(PrescriptionDTO prescriptionDTO) {
        PrescriptionDetailVO<KnowledgeVO> model = new PrescriptionDetailVO<KnowledgeVO>();
        model.setModuleType(prescriptionDTO.getType());
        model.setModuleName(PrescriptionConstant.MODULE_NAME_EDU);
        if (!StringUtils.isBlank(prescriptionDTO.getBaseJson())) {
            //1??????json????????????
            PrescriptionEduBO prescriptionEduBO = JSONObject.parseObject(prescriptionDTO.getBaseJson(), PrescriptionEduBO.class);
            if (null != prescriptionEduBO) {
                //2?????????????????????id?????????????????????
                PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptionEduBO.getPrescriptId());
                if (null != prescriptionVO) {
                    //3????????????????????????????????????????????????
                    KnowledgePlanBO knowledgePlanData = this.prescriptionApi.createKnowledgePlan(prescriptionEduBO.getArchivesJson(),prescriptionVO.getMemberId() ,prescriptionVO.getEohType());
                    if(knowledgePlanData != null) {
                        //4???????????????????????????
                        KnowledgeVO knowledgeVO = new KnowledgeVO();
                        //4.1??????????????????
                        List<ApiKnowledgePlanBO> knowledgePlanList = knowledgePlanData.getKnowledges();
                        
                        JSONObject archivesObj = null;
                        if(!StringUtils.isBlank(prescriptionEduBO.getArchivesJson())) {
                        	archivesObj = JSON.parseObject(prescriptionEduBO.getArchivesJson());
                        }
                        this.handleKnowledgeWeekOfEdu(knowledgePlanList, archivesObj,prescriptionEduBO.getPrescriptId(), knowledgeVO);
                        //4.2?????????????????????
//                        List<String> knowledgeTagList = knowledgePlanData.getKnowledgeTag();
//                        knowledgeVO.setKnowledgeTagList(knowledgeTagList);
                        model.setModule(knowledgeVO);
                    }
                }
            }
        }
        return model;
    }

    @Override
    public void modifyPrescriptionEdu(PrescriptionDTO prescriptionDTO) {
    	GetPrescriptionDTO getPrescriptionDTO = new GetPrescriptionDTO();
        getPrescriptionDTO.setSid(prescriptionDTO.getPrescriptionId());;
        PrescriptionPO prescription = prescriptionMapper.getPrescriptionByParam(getPrescriptionDTO);
        if(prescription == null) {
        	throw new BusinessException("?????????????????????!");
        }
    	
        //1???????????????
        String knowledgeListJson = prescriptionDTO.getKnowledgeListJson();
        if (!StringUtils.isBlank(knowledgeListJson)) {
            // ????????????
            this.prescriptionMapper.deletePrescriptionKnowledgeByPrescriptId(prescriptionDTO.getPrescriptionId());
            
            // ????????????
            List<PrescriptionKnowledgePO> knowledgeList = KnowledgeTool.knowledgeListHandler(knowledgeListJson);

            PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptionDTO.getPrescriptionId());
            if (null != knowledgeList && knowledgeList.size() > 0 && null != prescriptionVO) {
                for(PrescriptionKnowledgePO prescriptionKnowledgePO : knowledgeList){
                    prescriptionKnowledgePO.setId(DaoHelper.getSeq());
                    prescriptionKnowledgePO.setMemberId(prescriptionVO.getMemberId());
                    prescriptionKnowledgePO.setPrescriptionId(prescriptionDTO.getPrescriptionId());
                    prescriptionKnowledgePO.setFollowId("-1");
                    prescriptionKnowledgePO.setKnowledgeType(PrescriptionConstant.KNOWLEDGE_TYPE_PRE);
                    
//                    courseService.addMemberCourse(prescriptionKnowledgePO.getArticleId(),prescription.getMemberId(), "2", "1", Long.valueOf(prescriptionKnowledgePO.getId()));
                    this.prescriptionApi.addMemberCourse(prescriptionKnowledgePO.getArticleId(),prescription.getMemberId(), "2", "1", Long.valueOf(prescriptionKnowledgePO.getId()));
                }
                this.prescriptionMapper.batchInsertPrescriptionKnowledge(knowledgeList);
                
                
            }
        }

        //2?????????????????????  ????????????
        PrescriptionBO prescriptionBO = new PrescriptionBO();
        BeanUtils.copyProperties(prescriptionBO, prescriptionDTO);
        this.prescriptionService.modifyPrescriptionDetail(prescriptionBO);
    }
    
    private List<PrescriptionKnowledgePO> getKnowledgeListByWeek(int week,List<PrescriptionKnowledgePO> knowledgeList){
    	List<PrescriptionKnowledgePO> subKnowledgeList = new ArrayList<>();
    	for (Iterator<PrescriptionKnowledgePO> iterator = knowledgeList.iterator(); iterator.hasNext();) {
			PrescriptionKnowledgePO prescriptionKnowledgePO = (PrescriptionKnowledgePO) iterator.next();
			
			if(prescriptionKnowledgePO.getWeek() != null && prescriptionKnowledgePO.getWeek() == week) {
				subKnowledgeList.add(prescriptionKnowledgePO);
			}
			
		}
    	return subKnowledgeList;
    }

    @Override
    public List<KnowledgeTreeVO> knowledgeTree(PrescriptionEduDTO prescriptionEduDTO) {
        List<KnowledgeTreeVO> resList = new ArrayList<>();

        ApiKnowledgeBO model = new ApiKnowledgeBO();
        model.setOrderBy("sort");
		model.setEohType(prescriptionEduDTO.getEohType());
        if (!StringUtils.isBlank(prescriptionEduDTO.getPid())) {
            model.setPid(Long.parseLong(prescriptionEduDTO.getPid()));
        }

        int nType = Integer.parseInt(prescriptionEduDTO.getType());
        switch (nType) {
            case 1:
                List<ApiKnowledgeBO> knowledges = this.prescriptionApi.loadKnowledge(1, 500, model);
                if(knowledges != null && knowledges.size() > 0){
                    for (ApiKnowledgeBO knowledgeModel:knowledges) {
                        KnowledgeTreeVO knowledgeTreeVO = new KnowledgeTreeVO();
                        knowledgeTreeVO.setId(knowledgeModel.getId() + "");
                        knowledgeTreeVO.setName(knowledgeModel.getName());
                        knowledgeTreeVO.setKnowledge("");
                        knowledgeTreeVO.setTitle(knowledgeModel.getName());
                        knowledgeTreeVO.setType(2 + "");
                        resList.add(knowledgeTreeVO);
                    }
                }

                break;
            case 2:
                List<ApiArticleBO> articles = this.prescriptionApi.loadByKnowledgeId(Long.parseLong(prescriptionEduDTO.getPid()) ,prescriptionEduDTO.getMemberId());
                List<ApiKnowledgePlanBO>  knowledgePlans = this.getKnowledge(articles);

                if(knowledgePlans != null && knowledgePlans.size() > 0){
                    for (ApiKnowledgePlanBO knowledgePlanModel:knowledgePlans) {
                        KnowledgeTreeVO knowledgeTreeVO = new KnowledgeTreeVO();
                        knowledgeTreeVO.setId(knowledgePlanModel.getId() + "");
                        knowledgeTreeVO.setName(knowledgePlanModel.getTitle());
                        knowledgeTreeVO.setKnowledge(knowledgePlanModel.getKnowledge());
                        knowledgeTreeVO.setTitle(knowledgePlanModel.getTitle());
                        knowledgeTreeVO.setType(3 + "");
                        knowledgeTreeVO.setLearnStatus(knowledgePlanModel.getLearnStatus());
                        knowledgeTreeVO.setFollowStatus(knowledgePlanModel.getFollowStatus());
                        knowledgeTreeVO.setHairDownStatus(knowledgePlanModel.getHairDownStatus());
                        resList.add(knowledgeTreeVO);
                    }
                }
            default:
                break;
        }
        return resList;
    }


    @Override
    public List<KnowledgeTreeVO> loadArticle(PrescriptionEduDTO prescriptionEduDTO, PagerBO pager) {
        List<KnowledgeTreeVO> list = new ArrayList<>();

        List<ApiArticleBO> articleModelList = this.prescriptionApi.loadArticle(prescriptionEduDTO, pager);
        List<ApiKnowledgePlanBO>  resList = this.getKnowledge(articleModelList);

        if (null != resList && resList.size() > 0) {
            for (ApiKnowledgePlanBO article: resList) {
                KnowledgeTreeVO vo = new KnowledgeTreeVO();
                vo.setId(article.getId() + "");
                vo.setKnowledge(article.getKnowledge());
                vo.setTitle(article.getTitle());
				vo.setHairDownStatus(article.getHairDownStatus());
				vo.setFollowStatus(article.getFollowStatus());
				vo.setLearnStatus(article.getLearnStatus());
                list.add(vo);
            }
        }
        return list;
    }

    /**
     * ??????????????????
     * @author ?????????
     * @date 2018/8/13 16:34
     * @param articles
     * @return java.util.List<com.comvee.cdms.knowledge.model.KnowledgePlanModel>
     *
     */
    private List<ApiKnowledgePlanBO> getKnowledge(List<ApiArticleBO> articles) {
        List<Long> articleIds = new ArrayList<Long>();
        for (ApiArticleBO article: articles) {
            articleIds.add(article.getId());
        }

        List<ApiArticleKnowledgeBO>  ls = this.prescriptionApi.findArticleKnowledgeByIds(articleIds);


        List<ApiKnowledgePlanBO> resList =new ArrayList<ApiKnowledgePlanBO>();
        int total = articles.size();
        for (int i = 0; i <total ; i++) {
            ApiArticleBO article = articles.get(i);
            StringBuilder knowledge = new StringBuilder();
            for (ApiArticleKnowledgeBO aKnowledge : ls) {
                if (aKnowledge.getArticleId().longValue() == article.getId().longValue()) {
                    ApiKnowledgeBO knowledgeModel = InfoManager.getInstance().knowledgeMap.get(aKnowledge.getKnowledgeId().toString());
                    if (knowledgeModel != null) {
                        knowledge.append(knowledgeModel.getName()).append(",");
                    }
                }
            }

            String strKnowledge = knowledge.length() > 0 ? knowledge.substring(0,knowledge.length() - 1):knowledge.toString();
            ApiKnowledgePlanBO np = new ApiKnowledgePlanBO();
            np.setId(article.getId().toString());
            np.setKnowledge(strKnowledge);
            np.setTitle(article.getTitle());
            np.setDay(getDay(total, i) + "");
            np.setLearnStatus(article.getLearnStatus());
            np.setFollowStatus(article.getFollowStatus());
            np.setHairDownStatus(article.getHairDownStatus());
            resList.add(np);
        }


        return resList;
    }
    
  //????????????id ????????????id
    public String getHospitalId(String doctorId){
        if(StringUtils.isBlank(doctorId)){
            return null;
        }
        String hospitalId = null;
		ApiDoctorBO doctorP = this.prescriptionApi.getDoctorById(doctorId);
        if(doctorP != null){
            hospitalId = doctorP.getHospitalId();
        }
        return hospitalId;
    }

	@Override
	public List<String> getMemberTags(String memberId){
		ListDoctorMemberDTO listDoctorMemberDTO = new ListDoctorMemberDTO();
		listDoctorMemberDTO.setMemberId(memberId);
		DoctorMemberPO doctorMember = memberMapper.getDoctorMember(listDoctorMemberDTO);
		String doctorId = null;
		if (null != doctorMember){
			doctorId = doctorMember.getDoctorId();
		}
		PrescriptionVO prescriptionVO = new PrescriptionVO();
		prescriptionVO.setMemberId(memberId);
		prescriptionVO.setDoctorId(doctorId);
		String hospitalId = getHospitalId(doctorId);
		MemberArchivesPO po= this.memberCacheService.getMemberArchives(memberId,hospitalId);
		JSONObject archivesObj = null;
		if(po != null && !StringUtils.isBlank(po.getArchivesJson())) {
			archivesObj = JSON.parseObject(po.getArchivesJson());
		}
		List<String> list = this.handleMemberTags(archivesObj, prescriptionVO);
		return list;
	}
    
    private List<String> handleMemberTags(JSONObject archivesJsonObj,PrescriptionVO prescriptionVO) {
    	List<String> memberTags = new ArrayList<>();
		//?????????????????? Personas
		ApiMemberBO memberPO = this.prescriptionApi.getMemberByMemberId(prescriptionVO.getMemberId());
		if(memberPO != null) {
			boolean isChd = false;//??????????????????
			if(archivesJsonObj.getJSONObject("complication") != null && "QZ01".equals(archivesJsonObj.getJSONObject("complication").getString("chd"))) {
				isChd = true;
			}
			boolean isHyp = false;//??????????????????
			JSONObject anamnesisObj = archivesJsonObj.getJSONObject("anamnesis");
			if(anamnesisObj != null && "1".equals(anamnesisObj.getString("essential_hyp"))) {
				isHyp = true;
			}
			
			
			Integer sex = null;
			if ( !StringUtils.isBlank(memberPO.getSex())){
				sex = Integer.parseInt(memberPO.getSex());
			}
			ApiMemberArchivesBO memberArchives = this.prescriptionApi.getMemberArchivesById(prescriptionVO.getMemberId(), prescriptionVO.getDoctorId());

			if(memberArchives != null) {
				JSONObject memberArchivesObj = JSON.parseObject(memberArchives.getArchivesJson());
				
				JSONObject basicObj = memberArchivesObj.getJSONObject("basic");
				if(basicObj != null) {
					String diabetes_date = basicObj.getString("diabetes_date"); //????????????
					if(!StringUtils.isBlank(diabetes_date)) {
						//????????????	?????????????????????-???????????????-????????????????????????
						int cdays = DateHelper.dateCompareGetDay(DateHelper.getDate(new Date(), "yyyy-MM-dd"), diabetes_date);
						if(Math.abs(cdays) < 365) {
							memberTags.add(CourseTag.lessThanOneYear.code());
						}
					}
				}
				
				
				JSONObject signObj = memberArchivesObj.getJSONObject("sign");
				if(signObj != null) {
					String waistline = signObj.getString("waistline");
					if(!StringUtils.isBlank(waistline)) {
						float fwaistline = Float.valueOf(waistline);
						//????????????	??????-????????????-???????????????90cm????????????85cm???????????????
						if(sex != null && sex == 1 && fwaistline >= 90) {
							memberTags.add(CourseTag.centralObesity.code());
						}else if(sex != null && sex == 2 && fwaistline >= 85) {
							memberTags.add(CourseTag.centralObesity.code());
						}
					}
				}
				//"drink_rate": "YJQK01",
				JSONObject historyObj = memberArchivesObj.getJSONObject("history");
				if(historyObj != null) {
					String bs_smoke = historyObj.getString("bs_smoke");
					String drink_rate = historyObj.getString("drink_rate");
					String bs_sport = historyObj.getString("bs_sport");
					
					if("1".equals(bs_smoke)) {
						memberTags.add(CourseTag.smoking.code());
					}
					
					if("YJQK02".equals(drink_rate)) {
						memberTags.add(CourseTag.drink.code());
						memberTags.add("11-1"); //??????
					}
					
					if("1".equals(bs_sport)) {
						//???????????????
						memberTags.add("11-2");
					}else {
						memberTags.add(CourseTag.inadequateUnderstandingOfSports.code());
					}
				}
				
				//???????????????
				JSONObject complicationObj = archivesJsonObj.getJSONObject("complication");
				if(complicationObj != null) {
					boolean isAllPass = false;
					String nephropathy = complicationObj.getString("nephropathy"); //???????????????  
					String retinal = complicationObj.getString("retinal"); //?????????????????????  ?????????
					String pad = complicationObj.getString("pad"); //??????????????????
					String diabetic_foot = complicationObj.getString("diabetic_foot");  //????????????
					String neuropathy = complicationObj.getString("neuropathy"); //??????????????????
					String chd = complicationObj.getString("chd"); //?????????
					//String hyp = complicationObj.getString("hyp"); //?????????
					
					//??????????????????-?????????  ZZ03    ?????????????????? QZ03
					if("SB03".equals(nephropathy) 
							&& "SWM03".equals(retinal)
							&& "XZXG03".equals(pad)
							&& "TNBZ03".equals(diabetic_foot)
							&& "ZWSJ03".equals(neuropathy) 
							&& "QZ03".equals(chd)
							&& !isHyp
							) {
						isAllPass = true;
					}
					
					if(isAllPass) {
						//????????????	???????????????????????????-?????????????????????????????????/???????????????
						memberTags.add(CourseTag.noComplications.code());
					}
				}
				
				JSONObject memberComplicationObj = memberArchivesObj.getJSONObject("complication");
				if(memberComplicationObj != null) {
					String nephropathy = memberComplicationObj.getString("nephropathy"); //???????????????  
					String retinal = memberComplicationObj.getString("retinal"); //?????????????????????  ?????????
					String retinal_date = memberComplicationObj.getString("retinal_date");//????????????????????????
					String pad = memberComplicationObj.getString("pad"); //??????????????????
					String diabetic_foot = memberComplicationObj.getString("diabetic_foot");  //????????????
					String neuropathy = memberComplicationObj.getString("neuropathy"); //??????????????????
					String chd = memberComplicationObj.getString("chd"); //?????????
					String chd_date = memberComplicationObj.getString("chd_date");//?????????????????????
					String dan = memberComplicationObj.getString("dan"); //??????????????????
					
					//??????????????????-??????????????????????????????????????????????????????
					if(!"SWM01".equals(retinal)) {
						System.out.println("?????????  ????????????  12-1  ??????");
						memberTags.add("12-1");
					}
					
					//??????????????????-?????????????????????????????????????????????
					if(!"TNBZ01".equals(diabetic_foot)) {
						System.out.println("?????????  ????????????  6-4  ??????");
						memberTags.add("6-4");
					}
					
					//???????????? ???????????? ??????  ??????????????????-?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????6??????
					boolean isLessInspection = false;
					if("SB03".equals(nephropathy) 
							|| "SWM03".equals(retinal)
							|| "XZXG03".equals(pad)
							|| "TNBZ03".equals(diabetic_foot)
							|| "ZWSJ03".equals(neuropathy) 
							|| "QZ03".equals(chd)
							|| "ZZ03".equals(dan)
							) {
						isLessInspection = true;
					}else {
						String nowStr = DateHelper.getDate(new Date(), "yyyy-MM-dd");
						if(!StringUtils.isBlank(retinal_date)) {
							int compDays = DateHelper.dateCompareGetDay(nowStr, retinal_date);
							if(compDays >= 360) {
								isLessInspection = true;;
							}
						}
						
						if(!StringUtils.isBlank(chd_date)) {
							int compDays = DateHelper.dateCompareGetDay(nowStr, chd_date);
							if(compDays >= 90) {
								isLessInspection = true;;
							}
						}
					}
					
					if(isLessInspection) {
						System.out.println("?????????  ????????????  6-5  ??????");
						memberTags.add("6-5");
					}
				}
				
				//????????????
				////????????????  ????????????-??????????????????-???????????????????????????????????????????????????????????????
				////???????????? ????????????-??????????????????-??????????????????????????????
				if(historyObj != null) {
					//????????????
					JSONArray bs_dinner_jc = JSON.parseArray(historyObj.getString("bs_dinner_jc"));
					JSONArray bs_jcnr = JSON.parseArray(historyObj.getString("bs_jcnr"));
					JSONArray bs_lunch_jc = JSON.parseArray(historyObj.getString("bs_lunch_jc"));
					//??????????????????
					JSONArray bs_wanczslx = JSON.parseArray(historyObj.getString("bs_wanczslx"));
					JSONArray bs_wuczslx = JSON.parseArray(historyObj.getString("bs_wuczslx"));
					JSONArray bs_zczslx = JSON.parseArray(historyObj.getString("bs_zczslx"));

					if ((bs_dinner_jc != null && bs_dinner_jc.size() > 0) || (bs_jcnr != null && bs_jcnr.size() > 0)
							|| (bs_lunch_jc != null && bs_lunch_jc.size() > 0)|| (bs_wanczslx != null && bs_wanczslx.size() > 0)
							|| (bs_wuczslx != null && bs_wuczslx.size() > 0) || (bs_zczslx != null && bs_zczslx.size() > 0)){
						if (StringUtils.isBlank(historyObj.getString("bs_wanczslx"))){
							memberTags.add(CourseTag.notEatDinner.code());
							System.out.println("???????????????[ ????????????    4-9]");
						}
					}
					
					List<String> foodIds = new ArrayList<>();
					fillFoodIds(foodIds,bs_dinner_jc);
					fillFoodIds(foodIds,bs_jcnr);
					fillFoodIds(foodIds,bs_lunch_jc);
					fillFoodIds(foodIds,bs_wanczslx);
					fillFoodIds(foodIds,bs_wuczslx);
					fillFoodIds(foodIds,bs_zczslx);
					
					if(foodIds.size() > 0) {
						/*?????????  ???????????? ????????????*/
						boolean isEatFruits = false;
						List<FoodItemBO> foodItems = prescriptionMapper.getEohFoodItemByIds(foodIds);
						for (Iterator<FoodItemBO> iterator = foodItems.iterator(); iterator.hasNext();) {
							FoodItemBO foodItemBO = (FoodItemBO) iterator.next();
							if("1001004".equals(foodItemBO.getIngredientsType())) {
								memberTags.add("4-4");
								isEatFruits = true;
								System.out.println("???????????????[????????????   4-4]");
								break;
							}
						}
						/*?????? ??????????????????*/
						
						/*?????????  ????????????  ????????????*/
						if(!isEatFruits) {
							memberTags.add("4-8");
							System.out.println("???????????????[ ????????????    4-8]");
						}
						/*????????????  ??????????????????*/
						
						
						/*?????????  ??????  ????????????*/
						boolean isEatMeat = false; //??????????????? 
						for (Iterator<FoodItemBO> iterator = foodItems.iterator(); iterator.hasNext();) {
							FoodItemBO foodItemBO = (FoodItemBO) iterator.next();
							if("1001007".equals(foodItemBO.getIngredientsType())) {
								memberTags.add("4-6");
								isEatMeat = true;
								System.out.println("???????????????[??????   4-6]");
								break;
							}
						}
						/*?????? ??????????????????*/
						
						/*?????????  ?????? ????????????*/
						if(!isEatMeat) {
							memberTags.add("4-7");
							System.out.println("???????????????[??????   4-7]");
						}
						/*?????? ??????????????????*/
						
						
					}
					
					
					
					/*?????????  ????????????  ????????????*/
					if( (bs_dinner_jc != null && bs_dinner_jc.size() > 0) 
							|| (bs_jcnr != null && bs_jcnr.size() > 0) 
							|| (bs_lunch_jc != null && bs_lunch_jc.size() > 0) 
							) {
						memberTags.add("4-5");
						
						System.out.println("???????????????[????????????   4-5]");
					}
					/*???????????? ??????????????????*/
				}
				
				//????????????
				JSONObject labObj = memberArchivesObj.getJSONObject("lab");
				if(labObj != null) {
					String lab_hba = labObj.getString("lab_hba");
					if(!StringUtils.isBlank(lab_hba)) {
						float flab_hba = Float.valueOf(lab_hba);
						if(flab_hba > 9) {
							//??????????????????????????????	????????????-????????????>9.0%
							memberTags.add(CourseTag.Hba1cHighRisk.code());
						}
						
						//??????????????????
						RangeBO memberRange = memberMapper.getMemberRange(prescriptionVO.getMemberId());
						if(memberRange != null && !StringUtils.isNullOrEmpty(memberRange.getHighGlycosylatedVal())) {
							float htr = Float.valueOf(memberRange.getHighGlycosylatedVal());
							if(flab_hba > htr) {
								memberTags.add("5-5");
							}
						}
					}else {
						memberTags.add("5-5");
					}
					
					//????????????????????????
					boolean isHyperlipidemia = false;
					//????????????????????????,????????????
					String tc = labObj.getString("tc");//????????????
					String hdl = labObj.getString("hdl");//??????????????????
					String ldl = labObj.getString("ldl");//??????????????????
					if(!StringUtils.isBlank(tc)) {
						float ftc = Float.valueOf(tc);
						if(ftc >= 5.4) {
							isHyperlipidemia = true;
						}
					}
					
					//??????????????????>=2.6mmol/L(??????????????????????????????????????????>=1.8mmol/L(??????????????????
					if(!StringUtils.isBlank(ldl)) {
						float fldl = Float.valueOf(ldl);
						if(isChd && fldl >= 1.8) {
							isHyperlipidemia = true;
						}else if(fldl >= 2.6){
							isHyperlipidemia = true;
						}
					}
					
					//??????????????????<=1.0mmol/L?????????????????????????????????<=1.3mmol/L????????????
					if(!StringUtils.isBlank(hdl)) {
						float fhdl = Float.valueOf(hdl);
						if(sex != null) {
							if(sex == 1 && fhdl <= 1.0) {
								isHyperlipidemia = true;
							}else if(sex == 2 && fhdl <= 1.3) {
								isHyperlipidemia = true;
							}
						}
					}
					
					if(isHyperlipidemia) {
						System.out.println("????????? ????????? 6-3  ??????");
						memberTags.add("6-3");
					}
					
					
				}else {
					memberTags.add("5-5");
				}
			}
			
			int age = -1;
			String birthday = memberPO.getBirthday();
			if(!StringUtils.isBlank(birthday)) {
				age = DateHelper.getAge(birthday); //????????????
				if(age >= 18 && age <= 45) {
					//??????	18~45???????????????????????????????????????????????????
					memberTags.add(CourseTag.youngPeople.code());
				}else if(age >= 46 && age <= 60) {
					//??????	46~60???????????????????????????????????????????????????
					memberTags.add(CourseTag.middleAgedPerson.code());
				}else if(age > 60) {
					//??????	60????????????????????????????????????????????????????????????
					memberTags.add(CourseTag.oldPeople.code());
				}
			}
			
			if(sex != null) {
				if(sex == 1) {
					memberTags.add(CourseTag.male.code());
				}else if(sex == 2) {
					memberTags.add(CourseTag.female.code());
				}
			}
			
			String bmi = memberPO.getBmi();
			if(!StringUtils.isBlank(bmi) && !"--".equals(bmi)) {
				float fbmi = Float.valueOf(bmi);
				if(fbmi >= 24 && fbmi <= 26.9) {
					//??????	24???BMI???26.9???????????????-???????????????????????????
					memberTags.add(CourseTag.overweight.code());
				}else if(fbmi >= 27 && fbmi <= 29.9) {
					//??????	27???BMI???29.9???????????????-???????????????????????????
					memberTags.add(CourseTag.obesity.code());
				}else if(fbmi >= 30) {
					//????????????	BMI???30???????????????-???????????????????????????
					memberTags.add(CourseTag.severeObesity.code());
				}
			}
			
			//????????????
			ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
			listBloodSugarDTO.setMemberId(prescriptionVO.getMemberId());
			listBloodSugarDTO.setStartDt(DateHelper.getDate(DateHelper.getDateAddDay(new Date(), -7), DateHelper.DATETIME_FORMAT));
			listBloodSugarDTO.setEndDt(DateHelper.getTime());
			List<ApiBloodSugarBO> bloodSugars = this.prescriptionApi.listSugar(listBloodSugarDTO);
			if(bloodSugars != null && bloodSugars.size() > 0) {
				boolean isLowBloodSugar = false; //????????????????????????
				boolean isKfHight = false; //???????????????
				boolean isFkfHight = false;//??????????????????
				boolean isAbsAfterBreakfast = false; //???????????????????????????
				boolean isHightAfterMeal = false; //?????????????????????
				boolean isAllNormal = true;
				for (Iterator<ApiBloodSugarBO> iterator = bloodSugars.iterator(); iterator.hasNext();) {
                    ApiBloodSugarBO bloodSugarPO = (ApiBloodSugarBO) iterator.next();
					if(!StringUtils.isBlank(bloodSugarPO.getParamValue())) {
						float fVal = Float.valueOf(bloodSugarPO.getParamValue());
						
						if(fVal < 3.9) {
							isLowBloodSugar = true;
						}
						
						if(ParamLogConstant.PARAM_CODE_BEFOREBREAKFAST.equals(bloodSugarPO.getParamCode())) {
							//??????
							if(fVal >= 10) {
								isKfHight = true;
							}
							
							if( ( fVal < 4.4 || fVal > 7.0 ) ) {
								isAllNormal = false;
							}
						}else {
							//?????????
							if(fVal >= 16.7) {
								isFkfHight = true;
							}
							
							if( ( fVal < 4.4 || fVal > 10.0 ) ) {
								isAllNormal = false;
							}
						}
						
						if(ParamLogConstant.PARAM_CODE_AFTERBREAKFAST.equals(bloodSugarPO.getParamCode())) {
							if(fVal < 4.4 || fVal > 10) {
								isAbsAfterBreakfast = true;
							}
						}
						
						if(ParamLogConstant.PARAM_CODE_AFTERBREAKFAST.equals(bloodSugarPO.getParamCode())
								|| ParamLogConstant.PARAM_CODE_AFTERLUNCH.equals(bloodSugarPO.getParamCode())
								|| ParamLogConstant.PARAM_CODE_AFTERDINNER.equals(bloodSugarPO.getParamCode())
								) {
							if(fVal > 10) {
								isHightAfterMeal = true;
							}
						}
						
					}
				}
				
				if(isLowBloodSugar) {
					//?????????	???????????????????????????????????? (????????????<3.9)
					memberTags.add(CourseTag.lowBloodSugar.code());
					
					if(isKfHight || isFkfHight) {
						//????????????	???????????????????????????????????????????????????<3.9????????????10???????????????16.7???
						memberTags.add(CourseTag.bloodSugarFluctuation.code());
					}
				}
				
				if(isAbsAfterBreakfast) {//?????????????????????
					memberTags.add("5-3");
					System.out.println("?????????  ????????????????????? 5-3 ??????");
				}
				if(isHightAfterMeal) {//???????????????
					memberTags.add("10-1");
					System.out.println("?????????  ???????????????  10-1 ??????");
				}
				if(isAllNormal) {
					memberTags.add("10-2");
					System.out.println("?????????  ????????????  10-2 ??????");
				}
				
			}else {
				//???????????????	??????-????????????-7????????????
				memberTags.add(CourseTag.lowMonitoringFrequency.code());
			}
			
			//??????30??????????????????
			listBloodSugarDTO.setMemberId(prescriptionVO.getMemberId());
			listBloodSugarDTO.setStartDt(DateHelper.getDate(DateHelper.getDateAddDay(new Date(), -30), DateHelper.DATETIME_FORMAT));
			listBloodSugarDTO.setEndDt(DateHelper.getTime());
			bloodSugars = this.prescriptionApi.listSugar(listBloodSugarDTO);
			if(bloodSugars != null && bloodSugars.size() > 0) {
				boolean isFastingHight = false;
				for (Iterator<ApiBloodSugarBO> iterator = bloodSugars.iterator(); iterator.hasNext();) {
                    ApiBloodSugarBO bloodSugarPO = (ApiBloodSugarBO) iterator.next();
					if(!StringUtils.isBlank(bloodSugarPO.getParamValue())) {
						float fVal = Float.valueOf(bloodSugarPO.getParamValue());
						if(ParamLogConstant.PARAM_CODE_BEFOREBREAKFAST.equals(bloodSugarPO.getParamCode())) {
							if(fVal > 7) {
								isFastingHight = true;
								break;
							}
						}
						
					}
				}
				if(isFastingHight) {
					memberTags.add("5-4");
					System.out.println("?????????  ???????????????  5-4 ??????");
				}
			}
			
			/*
			RangeBO rangeBO = memberService.getMemberRange(prescriptionVO.getMemberId());
			if(rangeBO != null) {
				String highGlycosylatedVal = rangeBO.getHighGlycosylatedVal();
				if(!StringUtils.isBlank(highGlycosylatedVal)) {
					float fhighGlycosylatedVal = Float.valueOf(highGlycosylatedVal);
					if(fhighGlycosylatedVal > 9) {
						//??????????????????????????????	????????????-????????????>9.0%
						memberTags.add(CourseTag.Hba1cHighRisk.code());
					}
				}
			}
			*/
			
			
			if(isHyp) {
				memberTags.add("6-2");
				System.out.println("?????????  ?????????  6-2 ??????");
			}
			
			//???????????????????????????
			Map<String,Object> queryMonitorPlanMap = new HashMap<>();
			queryMonitorPlanMap.put("memberId", prescriptionVO.getMemberId());
			queryMonitorPlanMap.put("planType", "1");
			List<MemberMonitorPlanPO> monitorPlans = memberMonitorPlanMapper.listMonitorPlanHistory(queryMonitorPlanMap);
			if(monitorPlans != null && monitorPlans.size() > 0) {
				memberTags.add("10-3");
				System.out.println("?????????  ?????????????????????  10-3 ??????");
				
				memberTags.add("10-4");
				System.out.println("?????????  ??????????????????  10-4 ??????");
			}
			
			//?????????????????????
			boolean isUseInsulin = isUseInsulin(prescriptionVO.getMemberId());
			if(isUseInsulin) {
				memberTags.add("13-1");
				System.out.println("?????????  ?????????  13-1  ??????");
				
				//????????????????????????????????????:????????????-????????????-???????????????????????????????????????????????????20???
				if(sex != null && sex == 2) {
					if(age > 20) {
						memberTags.add("13-3");
						System.out.println("?????????  ???????????????????????? 13-3  ??????");
					}
				}
				
				//??????????????????????????????   ????????????-????????????-???????????????????????????????????????-????????????-???????????????????????????
				ListMemberPrescriptionDTO listMemberPrescriptionDTO = new ListMemberPrescriptionDTO();
				listMemberPrescriptionDTO.setMemberId(prescriptionVO.getMemberId());
				listMemberPrescriptionDTO.setModuleString("2");
				List<MemberPrescriptionPO> memberPrescriptions = prescriptionMapper.listMemberPrescription(listMemberPrescriptionDTO);
				if(memberPrescriptions != null && memberPrescriptions.size() > 0) {
					for (Iterator<MemberPrescriptionPO> iterator = memberPrescriptions.iterator(); iterator.hasNext();) {
						MemberPrescriptionPO memberPrescriptionPO = (MemberPrescriptionPO) iterator.next();
						
						GetPrescriptionDetailDTO getPrescriptionDetailDTO = new GetPrescriptionDetailDTO();
						getPrescriptionDetailDTO.setPrescriptionId(memberPrescriptionPO.getSid());
						PrescriptionDetailPO prescriptionDetailPO = prescriptionMapper.getPrescriptionDetailByParam(getPrescriptionDetailDTO);
						if(prescriptionDetailPO != null) {
							JSONObject jsonObj = JSON.parseObject(prescriptionDetailPO.getDetailJson());
							if(jsonObj != null && !StringUtils.isBlank(jsonObj.getString("schemeName"))  && jsonObj.getString("schemeName").contains("?????????") ) {
								memberTags.add("13-2");
								System.out.println("?????????  ???????????????????????? 13-2  ??????");
								break;
							}
						}
					}
				}
			}
		}
		
		return memberTags;
    }
    
    /**
     * ??????????????????????????????
     * @param memberId
     * @return
     */
    private boolean isUseInsulin(String memberId) {
    	GetMemberDrugItemDTO getMemberDrugRecordDTO = new GetMemberDrugItemDTO();
		getMemberDrugRecordDTO.setMemberId(memberId);
		List<MemberDrugRecordPO> memberDrugRecordList = memberMapper.getMemberDrugRecordList(getMemberDrugRecordDTO);
		if(memberDrugRecordList != null && memberDrugRecordList.size() > 0) {
			for (Iterator<MemberDrugRecordPO> iterator = memberDrugRecordList.iterator(); iterator.hasNext();) {
				MemberDrugRecordPO memberDrugRecordPO = (MemberDrugRecordPO) iterator.next();
				
				GetMemberDrugItemDTO getMemberDrugItemDTO = new GetMemberDrugItemDTO();
				getMemberDrugItemDTO.setRecordId(memberDrugRecordPO.getId());
				MemberDrugItemPO memberDrugItemPO = memberMapper.getMemberDrugItem(getMemberDrugItemDTO);
				if(memberDrugItemPO != null) {
					JSONArray drugObjArr = JSON.parseArray(memberDrugItemPO.getDrugJson());
					if(drugObjArr != null && drugObjArr.size() > 0) {
						for (int i = 0; i < drugObjArr.size(); i++) {
							JSONObject drugObj = drugObjArr.getJSONObject(i);
							if("4".equals(drugObj.getString("drugType"))) {
								return true;
								
							}
						}
					}
				}
			}
		}
		return false;
    }
    
    private void fillFoodIds(List<String> foodIds,JSONArray jsonArr) {
    	if(jsonArr != null && jsonArr.size() > 0) {
			for (int i = 0; i < jsonArr.size(); i++) {
				foodIds.add(jsonArr.getJSONObject(i).getString("id"));
			}
		}
    }
    
    /**
     * ??????????????????????????????????????????
     */
    private void sortKnowledgePlanList(List<ApiKnowledgePlanBO> knowledgePlanList,JSONObject archivesJsonObj,List<String> memberTags ,Integer eohType) {
    	final StringBuffer archivesKnowKnowledge = new StringBuffer();
    	if(archivesJsonObj != null) {
    		JSONObject historyObj = archivesJsonObj.getJSONObject("history");
    		if(historyObj != null) {
    			archivesKnowKnowledge.append(historyObj.getString("know_knowledge"));
    		}
    	}

    	Collections.sort(knowledgePlanList, new Comparator<ApiKnowledgePlanBO>() {

			@Override
			public int compare(ApiKnowledgePlanBO o1, ApiKnowledgePlanBO o2) {
				int cp1=0,cp2=0;
				Map<String ,String> courseMap = null;
				if(eohType == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_HYP){
					courseMap = ConstantCourse.COURSE_TYPE_LINK_PLAN_TYPE_HYP;
				}else{
					courseMap = ConstantCourse.COURSE_TYPE_LINK_PLAN_TYPE;
				}
				if(!StringUtils.isBlank(o1.getKnowledgeCode()) && !StringUtils.isBlank(courseMap.get(o1.getKnowledgeCode()))) {
					cp1 = archivesKnowKnowledge.toString().contains(courseMap.get(o1.getKnowledgeCode())) ? 1 : 0;
				}
				if(!StringUtils.isBlank(o2.getKnowledgeCode()) && !StringUtils.isBlank(courseMap.get(o2.getKnowledgeCode()))) {
					cp2 =  archivesKnowKnowledge.toString().contains(courseMap.get(o2.getKnowledgeCode())) ? 1 : 0;
				}
				
				int diff2 = cp2-cp1;
				return diff2;
			}});
    	
    	Collections.sort(knowledgePlanList, new Comparator<ApiKnowledgePlanBO>() {

			@Override
			public int compare(ApiKnowledgePlanBO o1, ApiKnowledgePlanBO o2) {
				int diff = containsTagNum(memberTags,o2.getTags()) - containsTagNum(memberTags,o1.getTags());
				return diff;
			}});
    }
    
    private int containsTagNum(List<String> memberTags,String knowledgeTag) {
    	if(knowledgeTag == null) {
    		return 0;
    	}
    	int cnt = 0;
    	for (Iterator<String> iterator = memberTags.iterator(); iterator.hasNext();) {
			String memberTag = (String) iterator.next();
			if (!StringUtils.isBlank(knowledgeTag)){
				String[] split = knowledgeTag.split(",");
				for (String s : split) {
					if (s.equals(memberTag)){
						cnt ++;
					}
				}
			}
//			if(knowledgeTag.contains(memberTag)) {
//				cnt ++;
//			}
		}
    	return cnt;
    }
    
    private void handleKnowledgeWeekOfEdu(List<ApiKnowledgePlanBO> knowledgePlanList, JSONObject archivesJsonObj,String prescriptId, KnowledgeVO knowledgeVO) {
        //1?????????????????????id???????????????????????????
        PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptId);
        
        //??????????????????
        List<String> memberTags = handleMemberTags(archivesJsonObj,prescriptionVO);
		System.out.println("???????????????????????????:" + memberTags);

        List<KnowledgeWeek> knowledgeWeekList = new ArrayList<KnowledgeWeek>();
        List<String> knowledgeTagList = new ArrayList<>();
        
        if(knowledgePlanList != null) {
        	//??????????????????????????????????????????
        	sortKnowledgePlanList(knowledgePlanList, archivesJsonObj,memberTags ,prescriptionVO.getEohType());
        	
        	//2??????????????? ?????????(4???) ?????????(12???)  ??????(24???) ??????(48???) ?????? ?????????(12???)
            int eduCycle=12;
            if(null != prescriptionVO.getEduCycle()){
                eduCycle = prescriptionVO.getEduCycle();
            }
            
        	int numOfWeek = 2;//??????2?????????
        	int week = 0;  //?????????
			int max = eduCycle*numOfWeek > knowledgePlanList.size() ? knowledgePlanList.size() :  eduCycle*numOfWeek;
        	List<Knowledge> knowledgeList = null;//??????????????????????????????
        	for (int i = 0; i < max; i++) {
        		ApiKnowledgePlanBO knowledgePlanModel = knowledgePlanList.get(i);
        		if(i % numOfWeek == 0) {
        			week++;
        			knowledgeList = new ArrayList<>();
                    KnowledgeWeek knowledgeWeek = new KnowledgeWeek();
                    knowledgeWeek.setWeek(week);
                    knowledgeWeek.setWeekName( "???" + NumberTool.numToChinese(week) + "???");
                    knowledgeWeek.setRows(knowledgeList);
                    knowledgeWeekList.add(knowledgeWeek);
        		}
        		
	            Knowledge knowledge = new Knowledge();
                knowledge.setArticleId(knowledgePlanModel.getId());
                knowledge.setDay(knowledgePlanModel.getDay());
                knowledge.setKnowledge(knowledgePlanModel.getKnowledge());
                knowledge.setTitle(knowledgePlanModel.getTitle());
				knowledge.setLearnStatus(knowledgePlanModel.getLearnStatus());
				knowledge.setFollowStatus(knowledgePlanModel.getFollowStatus());
				knowledge.setHairDownStatus(knowledgePlanModel.getHairDownStatus());
                knowledgeList.add(knowledge);
                
                //????????????
                if(!knowledgeTagList.contains(knowledgePlanModel.getKnowledge())) {
                	knowledgeTagList.add(knowledgePlanModel.getKnowledge());
                }
			}
        }
        knowledgeVO.setKnowledgeWeekList(knowledgeWeekList);
        knowledgeVO.setKnowledgeTagList(knowledgeTagList);
    }
    
    /**
     * ???????????????
     * @author ?????????
     * @date 2018/8/6 14:27
     * @param knowledgePlanList
     * @param prescriptId
     * @param knowledgeVO
     * @return void
     *
     
    private void handleKnowledgeWeekOfEdu(List<ApiKnowledgePlanBO> knowledgePlanList, String prescriptId, KnowledgeVO knowledgeVO) {

        //1?????????????????????id???????????????????????????
        PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptId);

        List<KnowledgeWeek> knowledgeWeekList = new ArrayList<KnowledgeWeek>();
        Iterator<ApiKnowledgePlanBO> iterator = knowledgePlanList.iterator();
        if (null != iterator) {
            //2??????????????? ?????????(4???) ?????????(12???)  ??????(24???) ??????(48???) ?????? ?????????(12???)
            int eduCycle=12;
            if(null != prescriptionVO.getEduCycle()){
                eduCycle = prescriptionVO.getEduCycle();
            }
            //??????2???
            while (iterator.hasNext()) {
                ApiKnowledgePlanBO knowledgePlanModel = (ApiKnowledgePlanBO) iterator.next();
                int dayValue = Integer.parseInt(knowledgePlanModel.getDay());
                int week=0;
                if(4==eduCycle){
                    week = (dayValue+8) / 20;
                }else if(12==eduCycle){
                    week = (dayValue- 1) / 7 + 1;
                }else if(24==eduCycle){
                    week = (dayValue) / 3 + 1;
                    if(week>24){
                        continue;
                    }
                }else if(48==eduCycle){
                    week = (dayValue) / 2 ;
                }
                if(week<=0){
                    continue;
                }

                //3??????????????????
                List<Knowledge> knowledgeList = null;
                boolean flag = true;
                if (null != knowledgeWeekList && knowledgeWeekList.size() > 0) {
                    for (KnowledgeWeek knowledgeWeek: knowledgeWeekList) {
                        if (knowledgeWeek.getWeek() == week) {
                            knowledgeList = knowledgeWeek.getRows();
                            flag = false;
                            break;
                        }
                    }
                }

                if (flag) {
                    knowledgeList = new ArrayList<>();
                    KnowledgeWeek knowledgeWeek = new KnowledgeWeek();
                    knowledgeWeek.setWeek(week);
                    knowledgeWeek.setWeekName( "???" + NumberTool.numToChinese(week) + "???");
                    knowledgeWeek.setRows(knowledgeList);
                    knowledgeWeekList.add(knowledgeWeek);
                }

                Knowledge knowledge = new Knowledge();
                knowledge.setArticleId(knowledgePlanModel.getId());
                knowledge.setDay(knowledgePlanModel.getDay());
                knowledge.setKnowledge(knowledgePlanModel.getKnowledge());
                knowledge.setTitle(knowledgePlanModel.getTitle());
                knowledgeList.add(knowledge);
            }
        }
        knowledgeVO.setKnowledgeWeekList(knowledgeWeekList);
    }
*/
    
    /**
     * ?????????????????? ????????????
     * @author ?????????
     * @date 2018/8/3 11:54
     * @param prescriptionEduVO
     * @param memberId
     * @param prescriptionId
     * @return void
     *
     */
    private void fullPrescriptionEdu(PrescriptionEduVO prescriptionEduVO, String memberId, String prescriptionId) {

        //??????????????????
        ApiMemberBO memberPO = this.prescriptionApi.getMemberByMemberId(memberId);
        if (null != memberPO) {
            prescriptionEduVO.setDiabetesDate(memberPO.getDiabetesDate());
        }

        //memberInfoJson
        PrescriptionComplicationVO prescriptionComplicationVO = new PrescriptionComplicationVO();
        PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptionId);
        if (null != prescriptionVO) {
            String memberInfoJson = prescriptionVO.getMemberInfoJson();
            if (!StringUtils.isBlank(memberInfoJson)) {
                Map<String, String> map = JsonSerializer.jsonToStringMap(memberInfoJson);
                //???????????????
                prescriptionComplicationVO.setHasChd(map.get("chd"));
                prescriptionComplicationVO.setHasHyp(map.get("hyp"));
                //?????????????????????
                prescriptionEduVO.setGlycosylatedVal(map.get("glycosylatedVal"));
            }else {
                //6????????????????????????
                CheckoutResultBO resultMap = this.prescriptionApi.getMemberCheckoutResultOfEoh(memberId);
                if (null != resultMap) {
                    prescriptionEduVO.setGlycosylatedVal(resultMap.getGlycosylatedVal());
                }
            }
        }
        //1?????????????????????
        PrescriptionVO pre = this.prescriptionService.getPrescriptionById(prescriptionId);
        ApiMemberArchivesBO memberArchivesModel = this.prescriptionApi.getMemberArchivesById(memberId, pre.getTeamId());
        if (null != memberArchivesModel) {
            //2?????????????????????
            if (StringUtils.isBlank(prescriptionEduVO.getDiabetesDate())) {
                String diabetesStatus = memberArchivesModel.getDiabetesStatus();
                if (!StringUtils.isBlank(diabetesStatus)) {
                    Map<String, String> map = JSONObject.parseObject(diabetesStatus, new TypeReference<Map<String, String>>() {});
                    if (null != map) {
                        prescriptionEduVO.setDiabetesDate(map.get("diabetesDate"));
                    }
                }
            }


            //3?????????????????????
            String dailyHabits = memberArchivesModel.getDailyHabits();
            if (!StringUtils.isBlank(dailyHabits)) {
                Map<String, String> map = JSONObject.parseObject(dailyHabits, new TypeReference<Map<String, String>>() {});
                if (null != map) {
                    prescriptionEduVO.setSportFrequency(map.get("sportFrequency"));
                }
            }

            //4???????????????????????????
            String educationSituation = memberArchivesModel.getEducationSituation();
            if (!StringUtils.isBlank(educationSituation)) {
                Map<String, String> map = JSONObject.parseObject(educationSituation, new TypeReference<Map<String, String>>() {});
                if (null != map) {
                    prescriptionEduVO.setKnowKnowledge(map.get("knowKnowledge"));
                }
            }

            //5????????????????????????
            String diabetesComplication = memberArchivesModel.getDiabetesComplication();
            if (!StringUtils.isBlank(diabetesComplication)) {
                Map<String, String> map = JSONObject.parseObject(diabetesComplication, new TypeReference<Map<String, String>>() {});
                if (null != map) {
                    //????????????
                    prescriptionComplicationVO.setHasAutoNerve(map.get("hasAutoNerve"));
                    prescriptionComplicationVO.setHasDisFoot(map.get("hasDisFoot"));
                    prescriptionComplicationVO.setHaslowerExt(map.get("haslowerExt"));
                    prescriptionComplicationVO.setHasNephropathy(map.get("hasNephropathy"));
                    prescriptionComplicationVO.setHasAutoNerve(map.get("hasAutoNerve"));
                    prescriptionComplicationVO.setHasRetinopathy(map.get("hasRetinopathy"));
                }
            }
        }
        prescriptionEduVO.setPrescriptionComplication(prescriptionComplicationVO);

        //7???????????????????????????
        String today = DateHelper.getToday() + " 59:59:59";
        String monthFirst = DateHelper.getMonthFirst(today) + " 00:00:00";
        MemberBloodSugarBO memberBloodSugarBO = new MemberBloodSugarBO();
        memberBloodSugarBO.setMemberId(memberId);
        memberBloodSugarBO.setStartDt(monthFirst);
        memberBloodSugarBO.setEndDt(today);
        Long lowBloodMonth = this.prescriptionApi.getNumberLowBloodMonthByMemberId(memberBloodSugarBO);
        if (null != lowBloodMonth) {
            prescriptionEduVO.setLowBloodMonth(lowBloodMonth.toString());
        }

        //8????????????????????? ?????????????????????????????????

    }

    private List<KnowledgeWeek> handleWeekOfKnowledgeList(List<PrescriptionKnowledgePO> knowledgePlanList){

        List<KnowledgeWeek> knowledgeWeekList = new ArrayList<>();

        if (null != knowledgePlanList && knowledgePlanList.size() > 0) {
            for (PrescriptionKnowledgePO prescriptionKnowledgePO: knowledgePlanList) {
                List<Knowledge> knowledgeList = null;
                int week = prescriptionKnowledgePO.getWeek();
                boolean flag = true;
                if (null != knowledgeWeekList && knowledgeWeekList.size() > 0) {
                    for (KnowledgeWeek knowledgeWeek: knowledgeWeekList) {
                        if (knowledgeWeek.getWeek() == week) {
                            knowledgeList = knowledgeWeek.getRows();
                            flag = false;
                            break;
                        }
                    }
                }
                if(flag) {
                    knowledgeList = new ArrayList<Knowledge>();
                    KnowledgeWeek knowledgeWeek = new KnowledgeWeek();
                    knowledgeWeek.setWeekName("???" + NumberTool.numToChinese(week) + "???");
                    knowledgeWeek.setWeek(week);
                    knowledgeWeek.setRows(knowledgeList);
                    knowledgeWeekList.add(knowledgeWeek);
                }
                Knowledge knowledge = new Knowledge();
                knowledge.setTitle(prescriptionKnowledgePO.getTitle());
                knowledge.setKnowledge(prescriptionKnowledgePO.getKnowledge());
//                knowledge.setDay();
                knowledge.setArticleId(prescriptionKnowledgePO.getArticleId());
                courseStatusHandler(knowledge ,prescriptionKnowledgePO.getMemberId());
                knowledgeList.add(knowledge);
            }
        }
        return knowledgeWeekList;
    }

    private static int getDay(int total,int index){
        int num = 12;
        if(total > num){
            Float day = (index-1) * ( 84.0f/total) +1;
            return day.intValue();
        } else {
            //index?????????+1???
            return (index -1)*7 + 1;
        }
    }

	@Override
	public List<PrescriptionKnowledgePO> loadNewSendArticle(String eohType,String memberId) {
		List<PrescriptionKnowledgePO> result = new ArrayList<>();
			PrescriptionKnowledgePO pKnow = this.prescriptionMapper.listPrescriptionSendKnowledge(eohType,memberId);
			PrescriptionKnowledgePO fKnow = this.prescriptionMapper.listFollowSendKnowledge(eohType,memberId);
			if (pKnow != null && fKnow != null){
				String pDate = pKnow.getInsertDt();
				String fDate = fKnow.getInsertDt();
				boolean flag = DateHelper.checkBigSmall(pDate, fDate);
				if (flag){
					result = this.prescriptionMapper.listPrescriptionKnowledge(pKnow.getPrescriptionId());
				}else{
					result = this.prescriptionMapper.listFollowKnowledge(fKnow.getFollowId());
				}
			}else {
				if (pKnow == null && fKnow != null){
					result = this.prescriptionMapper.listFollowKnowledge(fKnow.getFollowId());
				}else if (pKnow != null && fKnow == null){
					result = this.prescriptionMapper.listPrescriptionKnowledge(pKnow.getPrescriptionId());
				}
			}
		return result;
	}

    /**
     * ??????????????????
     * @param knowledge
     * @param memberId
     */
	private void courseStatusHandler(Knowledge knowledge ,String memberId){
        /**
         * ???????????? 1 ?????????  0 ?????????
         */
        Integer learnStatus = 0;
        /**
         * ???????????? 1 ????????? 0 ?????????
         */
        Integer followStatus = 0;
        /**
         * ???????????? 1 ????????? 0 ?????????
         */
        Integer hairDownStatus = 0;
        List<CourseModel> list = this.courseService.listMemberCourseById(memberId ,knowledge.getArticleId());
        if(list != null && !list.isEmpty()){
            for(CourseModel courseModel : list){
                //status = 2 ????????????
                if("2".equals(courseModel.getStatus())){
                    learnStatus = 1;
                }
                //??????????????????????????????????????????????????????
                if(1 == courseModel.getIsPlan()){
                    hairDownStatus = 1;
                }
                //????????????????????????????????????????????????????????????
                if(0 == courseModel.getIsPlan()){
                    followStatus = 1;
                }
            }
        }
        knowledge.setFollowStatus(followStatus);
        knowledge.setHairDownStatus(hairDownStatus);
        knowledge.setLearnStatus(learnStatus);
    }
}
