package com.comvee.cdms.prescription.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.defender.service.CourseServiceI;
import com.comvee.cdms.bloodmonitor.constant.MonitorPlanConstant;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.checkresult.dto.AddCheckoutDTO;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.checkresult.service.CheckoutServiceI;
import com.comvee.cdms.checkresult.vo.CheckoutBloodFatVO;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.knowledge.dto.IncrMemberArticleClicksDTO;
import com.comvee.cdms.knowledge.model.ArticleModel;
import com.comvee.cdms.knowledge.model.ArticleTxtModel;
import com.comvee.cdms.knowledge.service.KnowledgeServiceI;
import com.comvee.cdms.level.dto.CurrentGxyLevelDTO;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.member.bo.MemberControlTargetBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.constant.ControlTargetConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.*;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.dto.UsePackageServiceDTO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.prescription.bo.*;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.*;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.tool.PrescriptionMemberRangeHelper;
import com.comvee.cdms.prescription.vo.MemberLatestSignVO;
import com.comvee.cdms.prescription.vo.PrescriptionEduVO;
import com.comvee.cdms.prescription.vo.PrescriptionTargetVO;
import com.comvee.cdms.prescription.vo.PrescriptionVO;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.po.*;
import com.comvee.cdms.sign.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author 李左河
 * @date 2018/8/3 16:21.
 *
 * 此类为使用者实现，以下为河南业务实现
 * 其他项目可以根据自身情况实现
 */
//@Service("prescriptionApi")
public class PrescriptionApiImpl implements PrescriptionApiI {

    @Autowired
    private MemberService memberService;

    @Autowired
    private KnowledgeServiceI knowledgeService;


    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberMonitorPlanServiceI memberMonitorPlanService;

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private PrescriptionServiceI prescriptionService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private CourseServiceI courseService;

    @Autowired
    private BloodSugarService bloodSugarService;

    @Autowired
    private FollowServiceI followService;

    @Autowired
    private Hba1cService hba1cService;

    @Autowired
    private BloodPressureService bloodPressureService;

    @Autowired
    private BmiService bmiService;

    @Autowired
    private WhrService whrService;

    @Autowired
    private CheckoutDetailServiceI checkoutDetailService;

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private CheckoutServiceI checkoutService;

    @Override
    public ApiRangeBO getRangeBO(String memberId) {
        ApiRangeBO apiRangeBO = null;
        RangeBO rangeModel = this.memberService.getMemberRange(memberId);
        if (null != rangeModel) {
            apiRangeBO = new ApiRangeBO();
            BeanUtils.copyProperties(apiRangeBO,rangeModel);
        }
        return apiRangeBO;
    }

    @Override
    public ApiRangeBO insertMemberRangeWithLock(String memberId) {
        ApiRangeBO apiRangeBO = null;
        RangeBO rangeModel = this.memberService.insertMemberRangeWithLock(memberId);
        if (null != rangeModel) {
            apiRangeBO = new ApiRangeBO();
            BeanUtils.copyProperties(apiRangeBO,rangeModel);
        }
        return apiRangeBO;
    }

    @Override
    public ApiMemberBO getMemberByMemberId(String memberId) {
        ApiMemberBO apiMemberBO = null;
        GetMemberDTO getMemberDTO =new GetMemberDTO();
        getMemberDTO.setMemberId(memberId);
        MemberPO memberPO = this.memberService.getMember(getMemberDTO);
        if (null != memberPO) {
            apiMemberBO = new ApiMemberBO();
            BeanUtils.copyProperties(apiMemberBO,memberPO);
        }
        return apiMemberBO;
    }




    @Override
    public CheckoutResultBO getMemberCheckoutResultOfEoh(String memberId) {
        Map<String, Object> memberCheckoutResult = null;
        CheckoutResultBO bo = null;
        if (null != memberCheckoutResult && memberCheckoutResult.size() > 0) {
            bo = new CheckoutResultBO();
            for (Map.Entry<String, Object> entry : memberCheckoutResult.entrySet()) {
                switch (entry.getKey()) {
                    case "0301624":
                        bo.setGlycosylatedVal(entry.getValue().toString());
                        continue;
                    case "0101017":
                        bo.setHemoglobin(entry.getValue().toString());
                        continue;
                    case "0101019":
                        bo.setAvgRedBloodCell(entry.getValue().toString());
                        continue;
                    case "0101020":
                        bo.setAvgHemoglobin(entry.getValue().toString());
                        continue;
                    case "0101021":
                        bo.setAvgHemoglobinConcentration(entry.getValue().toString());
                        continue;
                    case "0101028":
                        bo.setPlatelet(entry.getValue().toString());
                        continue;
                    case "0101512":
                        bo.setCrp(entry.getValue().toString());
                        continue;
                    case "0301013":
                        bo.setAlt(entry.getValue().toString());
                        continue;
                    case "0301014":
                        bo.setAst(entry.getValue().toString());
                        continue;
                    case "0301007":
                        bo.setTbil(entry.getValue().toString());
                        continue;
                    case "0301001":
                        bo.setTp(entry.getValue().toString());
                        continue;
                    case "0301002":
                        bo.setAlb(entry.getValue().toString());
                        continue;
                    case "0301503":
                        bo.setCa(entry.getValue().toString());
                        continue;
                    case "0303004":
                        bo.setCa(entry.getValue().toString());
                        continue;
                    case "0301029":
                        bo.setCr(entry.getValue().toString());
                        continue;
                    case "0301021":
                        bo.setCurrentTCholesterol(entry.getValue().toString());
                        continue;
                    case "0301600":
                        bo.setTriglyceride(entry.getValue().toString());
                        continue;
                    case "0301518":
                        bo.setCurrentHDLCholesterol(entry.getValue().toString());
                        continue;
                    case "0301514":
                        bo.setCurrentLDLCholesterol(entry.getValue().toString());
                        continue;
                    default:
                        ;
                }
            }
        }
        return bo;
    }

    @Override
    public ApiMemberArchivesBO getMemberArchivesById(String memberId, String doctorId) {
        ApiMemberArchivesBO apiMemberArchivesBO = null;
        MemberArchivesPO model = this.memberService.getMemberArchives(memberId, doctorId);
        if (null != model) {
            if(!StringUtils.isBlank(model.getArchivesJson())){
                String re = this.followService.doHistoryFood(model.getArchivesJson());
                model.setArchivesJson(re);
            }
            apiMemberArchivesBO = new ApiMemberArchivesBO();
            BeanUtils.copyProperties(apiMemberArchivesBO,model);
            String json = apiMemberArchivesBO.getArchivesJson();
            if(!StringUtils.isBlank(json)){
                JSONObject jsonObject = JSONObject.parseObject(json);
                if(StringUtils.isBlank(apiMemberArchivesBO.getBasic())){
                    apiMemberArchivesBO.setBasic(jsonObject.getString("basic"));
                }
                if(StringUtils.isBlank(apiMemberArchivesBO.getDiabetesComplication())){
                    apiMemberArchivesBO.setDiabetesComplication(jsonObject.getString("complication"));
                }
                if(StringUtils.isBlank(apiMemberArchivesBO.getDiabetesStatus())){
                    apiMemberArchivesBO.setDiabetesStatus(jsonObject.getString("basic"));
                }
                if(StringUtils.isBlank(apiMemberArchivesBO.getHypoglycemia())){
                    apiMemberArchivesBO.setHypoglycemia(jsonObject.getString("hypoglycemia"));
                }
                if(StringUtils.isBlank(apiMemberArchivesBO.getAnamnesis())){
                    apiMemberArchivesBO.setAnamnesis(jsonObject.getString("anamnesis"));
                }
                if (StringUtils.isBlank(apiMemberArchivesBO.getSignjson())){
                    apiMemberArchivesBO.setSignjson(jsonObject.getString("sign"));
                }
            }
        }
        return apiMemberArchivesBO;
    }



    @Override
    public List<ApiMemberBO> listMember(GetMemberPerDTO getMemberPerDTO) {
        List<String> idList = getMemberPerDTO.getMemberIds();
        List<MemberPO> list = this.memberService.listMemberByIdList(idList);
        List<ApiMemberBO> apiMemberBOS = null;
        if(list!=null&&list.size()>0){
            apiMemberBOS = new ArrayList<ApiMemberBO>(list.size());
            for(MemberPO memberPO :list){
                ApiMemberBO apiMemberBO = null;
                if (null != memberPO) {
                    apiMemberBO = new ApiMemberBO();
                    BeanUtils.copyProperties(apiMemberBO,memberPO);
                }
                apiMemberBOS.add(apiMemberBO);
            }
        }
        return apiMemberBOS;
    }

//    @Override
//    public List<String> listDoctorIdOfHospitalLeader(String hospitalId, String leaderId, String bizCode) {
//        return this.authorityService.listDoctorIdOfHospitalLeader(hospitalId, leaderId, bizCode);
//    }

    @Override
    public KnowledgePlanBO createKnowledgePlan(String archives, String memberId , Integer eohType) {
        KnowledgePlanBO knowledgePlanBO = new KnowledgePlanBO();
        Map<String, Object> map = this.knowledgeService.createKnowledgePlan(archives, memberId, null);
        if(map!=null){
            List<String> list = (ArrayList<String>)map.get("knowledgeTag");
            knowledgePlanBO.setKnowledgeTag(list);
            List<com.comvee.cdms.knowledge.model.KnowledgePlanModel> knowledgePlanModels = (ArrayList<com.comvee.cdms.knowledge.model.KnowledgePlanModel>)map.get("knowledges");
            if(knowledgePlanModels!=null && knowledgePlanModels.size()>0){
                List<ApiKnowledgePlanBO> results = new ArrayList<ApiKnowledgePlanBO>(knowledgePlanModels.size());
                for(com.comvee.cdms.knowledge.model.KnowledgePlanModel model:knowledgePlanModels){
                    if(model!=null){
                        ApiKnowledgePlanBO apiKnowledgePlanBO = new ApiKnowledgePlanBO();
                        BeanUtils.copyProperties(apiKnowledgePlanBO,model);
                        results.add(apiKnowledgePlanBO);
                    }
                }
                knowledgePlanBO.setKnowledges(results);
            }
        }
        return knowledgePlanBO;
    }

    @Override
    public List<ApiKnowledgeClassifyBO> loadKnowledgeClassify(Long pid) {

        try {
                    List<com.comvee.cdms.knowledge.model.KnowledgeClassifyModel> knowledgeClassifyModels = this.knowledgeService.loadKnowledgeClassify(pid);
                    List<ApiKnowledgeClassifyBO> list = new ArrayList<ApiKnowledgeClassifyBO>();
                    for(com.comvee.cdms.knowledge.model.KnowledgeClassifyModel knowledgeClassifyModel : knowledgeClassifyModels){
                        ApiKnowledgeClassifyBO apiKnowledgeClassifyBO = null;
                        if (null != knowledgeClassifyModel) {
                            apiKnowledgeClassifyBO = new ApiKnowledgeClassifyBO();
                            BeanUtils.copyProperties(apiKnowledgeClassifyBO,knowledgeClassifyModel);
                        }
                        list.add(apiKnowledgeClassifyBO);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ApiKnowledgeBO> loadKnowledge(int page, int rows, ApiKnowledgeBO bo) {
        try {
            com.comvee.cdms.knowledge.model.KnowledgeModel model = new com.comvee.cdms.knowledge.model.KnowledgeModel();
            BeanUtils.copyProperties(model,bo);
            List<com.comvee.cdms.knowledge.model.KnowledgeModel> knowledgeModels = this.knowledgeService.loadKnowledge(page, rows, model);
            List<ApiKnowledgeBO> list = new ArrayList<ApiKnowledgeBO>();
            for(com.comvee.cdms.knowledge.model.KnowledgeModel knowledgeModel : knowledgeModels){
                ApiKnowledgeBO apiKnowledgeBO = null;
                if (null != knowledgeModel) {
                    apiKnowledgeBO = new ApiKnowledgeBO();
                    BeanUtils.copyProperties(apiKnowledgeBO,knowledgeModel);
                }
                list.add(apiKnowledgeBO);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ApiArticleBO> loadByKnowledgeId(Long id ,String memberId) {
        try {
            List<ArticleModel> articleModels = this.knowledgeService.loadByKnowledgeId(id);
            List<ApiArticleBO> list = new ArrayList<ApiArticleBO>();
            for(ArticleModel articleModel : articleModels){
                ApiArticleBO apiArticleBO = null;
                if (null != articleModel) {
                    apiArticleBO = new ApiArticleBO();
                    BeanUtils.copyProperties(apiArticleBO,articleModel);
                }
                list.add(apiArticleBO);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ApiArticleKnowledgeBO> findArticleKnowledgeByIds(List<Long> articleIds) {
        try {
            List<com.comvee.cdms.knowledge.model.ArticleKnowledgeModel> articleKnowledgeModelList = this.knowledgeService.findArticleKnowledgeByIds(articleIds);
            List<ApiArticleKnowledgeBO> list = new ArrayList<ApiArticleKnowledgeBO>();
            for(com.comvee.cdms.knowledge.model.ArticleKnowledgeModel articleKnowledgeModel : articleKnowledgeModelList){
                ApiArticleKnowledgeBO apiArticleKnowledgeBO = null;
                if (null != articleKnowledgeModel) {
                    apiArticleKnowledgeBO = new ApiArticleKnowledgeBO();
                    BeanUtils.copyProperties(apiArticleKnowledgeBO,articleKnowledgeModel);
                }
                list.add(apiArticleKnowledgeBO);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ApiArticleBO> loadArticle(PrescriptionEduDTO prescriptionEduDTO, PagerBO pager) {
        try {
            List<com.comvee.cdms.knowledge.model.ArticleModel> articleModelList = this.knowledgeService.loadArticle(pager.getPage(), pager.getRows(), null, null, null, prescriptionEduDTO.getParam(), null);
            List<ApiArticleBO> list = new ArrayList<ApiArticleBO>();
            for(com.comvee.cdms.knowledge.model.ArticleModel articleModel : articleModelList){
                ApiArticleBO apiArticleBO = null;
                if (null != articleModel) {
                    apiArticleBO = new ApiArticleBO();
                    BeanUtils.copyProperties(apiArticleBO,articleModel);
                }
                list.add(apiArticleBO);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void synchronizeMember(MemberInfoBO memberInfoBO) {
        if (null != memberInfoBO) {
            //同步member表
            UpdateMemberDTO memberPO = new UpdateMemberDTO();
            BeanUtils.copyProperties(memberPO,memberInfoBO);
            memberPO.setMemberNamePy(Pinyin4jUtil.getPinYin(memberInfoBO.getMemberName()));
            memberPO.setEssentialHyp(memberInfoBO.getHyp());
            memberPO.setChd(memberInfoBO.getChd());
            memberPO.setLabourIntensity(memberInfoBO.getLabourIntensity());//劳动强度
            memberPO.setDiabetesType(memberInfoBO.getDiabetesType());
            this.memberService.updateMember(memberPO);

            //同步member_archives表
//            MemberArchivesPO memberArchivesModel = this.memberService.getMemberArchives(memberInfoBO.getMemberId());
//            if (null == memberArchivesModel) {
//                memberArchivesModel = new MemberArchivesPO();
//                memberArchivesModel.setMemberId(memberInfoBO.getMemberId());
//            }
//            Map<String, Object> archivesMap = new HashMap<>();
//
//            //基本信息
//            Map<String, String> basicMap = new HashMap<>();
//            basicMap.put("member_real_name", memberInfoBO.getMemberName());
//            basicMap.put("birthday",memberInfoBO.getBirthday());
//            basicMap.put("height",memberInfoBO.getHeight());
//            basicMap.put("weight",memberInfoBO.getWeight());
//            basicMap.put("sex",memberInfoBO.getSex());
//            basicMap.put("diabetes_type",memberInfoBO.getDiabetesType());
//            archivesMap.put("basic",basicMap);
//
//            //是否有原发性高血压病
//            Map<String, String> anamnesisMap = new HashMap<>();
//            anamnesisMap.put("essential_hyp", memberInfoBO.getHyp());
//            archivesMap.put("anamnesis",anamnesisMap);
//
//            //冠心病
//            Map<String, String> complicationMap = new HashMap<>();
//            complicationMap.put("chd", memberInfoBO.getChd());
//            archivesMap.put("complication",complicationMap);
//
//            memberArchivesModel.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
//            MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
//            BeanUtils.copyProperties(memberArchivesDTO,memberArchivesModel);
//            this.memberService.updateMemberArchive(memberArchivesDTO);
        }

    }

    @Override
    public void synchronizeTarget(PrescriptionDetailPO prescriptionDetailPO,DoctorDTO doctorModel) {
        if (null != prescriptionDetailPO) {
            String detailJson = prescriptionDetailPO.getDetailJson();
            if (!StringUtils.isBlank(detailJson)) {
                PrescriptionTargetVO prescriptionTargetVO = JsonSerializer.jsonToObject(detailJson, PrescriptionTargetVO.class);
                if (null != prescriptionTargetVO) {
                    //ApiRangeBO rangeModel = this.getRangeBO(prescriptionDetailPO.getMemberId());
                    RangeBO rangeModel = this.memberService.getMemberRange(prescriptionDetailPO.getMemberId());
/*                    if (null == rangeModel) {
                        insertMemberRangeWithLock(prescriptionDetailPO.getMemberId());
                        rangeModel = this.memberMapper.getMemberRange(prescriptionDetailPO.getMemberId());
                    }*/
                    PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptionDetailPO.getPrescriptionId());
                    //糖化 血红蛋白
                    rangeModel.setHighGlycosylatedVal(prescriptionTargetVO.getHighGlycosylatedVal());
                    //空腹血糖
                    rangeModel.setLowBeforeBreakfast(prescriptionTargetVO.getLowBeforeBreakfast());
                    rangeModel.setHighBeforeBreakfast(prescriptionTargetVO.getHighBeforeBreakfast());
                    //非空腹血糖
                    rangeModel.setLowAfterMeal(prescriptionTargetVO.getLowAfterMeal());
                    rangeModel.setHighAfterMeal(prescriptionTargetVO.getHighAfterMeal());
                    //餐后一小时血糖
                    rangeModel.setLowAfterMealOneHour(prescriptionTargetVO.getLowAfterMealOneHour());
                    rangeModel.setHighAfterMealOneHour(prescriptionTargetVO.getHighAfterMealOneHour());

                    //收缩压
                    rangeModel.setLowSystolicPress(prescriptionTargetVO.getLowSystolicPress());
                    rangeModel.setHighSystolicPress(prescriptionTargetVO.getHighSystolicPress());
                    //舒张压
                    rangeModel.setLowDiastolicPress(prescriptionTargetVO.getLowDiastolicPress());
                    rangeModel.setHighDiastolicPress(prescriptionTargetVO.getHighDiastolicPress());

                    //总胆固醇
                    rangeModel.setHighTCholesterol(prescriptionTargetVO.getHighTCholesterol());
                    rangeModel.setLowTCholesterol(prescriptionTargetVO.getLowTCholesterol());

                    //甘油三酯
                    rangeModel.setHighTriglyceride(prescriptionTargetVO.getHighTriglyceride());
                    rangeModel.setLowTriglyceride(prescriptionTargetVO.getLowTriglyceride());

                    //高密度
                    rangeModel.setHighHDLCholesterol(prescriptionTargetVO.getHighHDLCholesterol());
                    rangeModel.setLowHDLCholesterol(prescriptionTargetVO.getLowHDLCholesterol());

                    //低密度
                    rangeModel.setHighLDLCholesterol(prescriptionTargetVO.getHighLDLCholesterol());
                    rangeModel.setLowLDLCholesterol(prescriptionTargetVO.getLowLDLCholesterol());


                    rangeModel.setDoctorId(prescriptionVO.getTeamId());

                    RangeBO rangeModel1 = new RangeBO();
                    BeanUtils.copyProperties(rangeModel1,rangeModel);
                    rangeModel1.setSenderId(doctorModel.getDoctorId());
                    this.memberService.modifyMemberRange(rangeModel1);
                }
            }
        }
    }

    @Override
    public void synchronizeMonitor(PrescriptionDetailPO prescriptionDetailPO,DoctorDTO doctorModel) {
        if (null != prescriptionDetailPO) {
            String detailJson = prescriptionDetailPO.getDetailJson();
            if (!StringUtils.isBlank(detailJson)) {
                Map<String, Object> monMap = JsonSerializer.jsonToMap(detailJson);
                if (null != monMap) {
                    String docId=doctorModel.getDoctorId();
                    PrescriptionVO pVO = prescriptionService.getPrescriptionById(prescriptionDetailPO.getPrescriptionId());
                    if(null!=pVO){
                        docId=pVO.getTeamId();
                    }
                    MemberMonitorPlanPO memberMonitorPlan=new MemberMonitorPlanPO();
                    memberMonitorPlan.setMemberId(prescriptionDetailPO.getMemberId());
                    memberMonitorPlan.setPlanType(1);
                    memberMonitorPlan.setPlanName(monMap.get("schemeName").toString());
                    memberMonitorPlan.setApplyExplain(monMap.get("guidDesc").toString());
                    memberMonitorPlan.setPlanDetail(monMap.get("monitorScheme").toString());
                    memberMonitorPlan.setStartMonitorDt(monMap.get("startMonitor").toString());
                    memberMonitorPlan.setEndMonitorDt(monMap.get("endMonitor").toString());
                    memberMonitorPlan.setOperationType(3);
                    memberMonitorPlan.setDoctorId(docId);
                    memberMonitorPlan.setSenderId(doctorModel.getDoctorId());
                    memberMonitorPlan.setInProgress(1);
                    if(pVO.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_HYP){
                        memberMonitorPlan.setIllnessType(MonitorPlanConstant.ILLNESS_TYPE_HYPERTENSION);
                    }else{
                        memberMonitorPlan.setIllnessType(MonitorPlanConstant.ILLNESS_TYPE_DIABETES);
                    }
                    memberMonitorPlan.setEohType(pVO.getEohType());
            		memberMonitorPlanService.saveMonitorPlan(memberMonitorPlan);

                    //ApiRangeBO rangeModel = this.getRangeBO(prescriptionDetailPO.getMemberId());
                    RangeBO rangeModel = this.memberMapper.getMemberRange(prescriptionDetailPO.getMemberId());
                    if (null == rangeModel) {
                        insertMemberRangeWithLock(prescriptionDetailPO.getMemberId());
                        rangeModel = this.memberMapper.getMemberRange(prescriptionDetailPO.getMemberId());
                    }

                    //空腹血糖
                    if(null!=monMap.get("lowBeforeBreakfast") && !StringUtils.isBlank(monMap.get("lowBeforeBreakfast").toString())
                        && null!=monMap.get("highBeforeBreakfast") && !StringUtils.isBlank(monMap.get("highBeforeBreakfast").toString())     ){
                          rangeModel.setLowBeforeBreakfast(monMap.get("lowBeforeBreakfast").toString());// 空腹范围下线
                          rangeModel.setHighBeforeBreakfast(monMap.get("highBeforeBreakfast").toString());// 空腹范围上线
                    }

                    //非空腹血糖
                    if(null!=monMap.get("lowAfterMeal") && !StringUtils.isBlank(monMap.get("lowAfterMeal").toString())
                            && null!=monMap.get("highAfterMeal") && !StringUtils.isBlank(monMap.get("highAfterMeal").toString())     ){
                        rangeModel.setLowAfterMeal(monMap.get("lowAfterMeal").toString());// 餐后范围下线
                        rangeModel.setHighAfterMeal(monMap.get("highAfterMeal").toString());// 餐后范围上线
                    }

                    RangeBO rangeModel1 = new RangeBO();
                    BeanUtils.copyProperties(rangeModel1,rangeModel);
                    this.memberService.modifyMemberRange(rangeModel1);


                }
            }
        }
    }

    @Override
    public void synchronizeDiet(PrescriptionDetailPO prescriptionDetailPO,DoctorDTO doctorModel) {
        if (null != prescriptionDetailPO) {
            String detailJson = prescriptionDetailPO.getDetailJson();
            if (!StringUtils.isBlank(detailJson)) {
//                Map<String, Object> monMap = JsonSerializer.jsonToMap(detailJson);
//                if(null==monMap.get("currentDietVO") || null==monMap.get("currentBloodSugarVO")){
//                    return;
//                }
//                String  currentDietVO = monMap.get("currentDietVO").toString();
//                Map<String, Object> currentDietVOMap = JsonSerializer.jsonToMap(currentDietVO);
//
//                String  currentBloodSugarVO = monMap.get("currentBloodSugarVO").toString();
//                Map<String, Object> currentBloodSugarVOMap = JsonSerializer.jsonToMap(currentBloodSugarVO);
//
//                Map<String,Object> archivesMap = new HashMap<>();
//                Map<String,Object> historyMap = new HashMap<>();
//                historyMap.put("bs_zczslx",currentDietVOMap.get("breakfastIngredients"));//早
//                historyMap.put("bs_jcnr",currentDietVOMap.get("breakfastLunchIngredients"));//早加
//                historyMap.put("bs_wuczslx",currentDietVOMap.get("dinnerIngredients"));//午饭
//                historyMap.put("bs_lunch_jc",currentDietVOMap.get("lunchDinnerIngredients"));//午饭加
//                historyMap.put("bs_wanczslx",currentDietVOMap.get("lunchIngredients"));//晚饭
//                historyMap.put("bs_dinner_jc",currentDietVOMap.get("beforeSleepIngredients"));//晚饭加
//
//                historyMap.put("bs_smoke",currentDietVOMap.get("hasSmoke")); //是否有抽烟
//                historyMap.put("smoke_history",currentDietVOMap.get("somkeYear")); //烟龄
//                historyMap.put("smoke_day_num",currentDietVOMap.get("smokeNum")); //数量
//                historyMap.put("drink_rate",currentDietVOMap.get("drinkRate")); //是否喝酒 on
//                historyMap.put("drink_times",currentDietVOMap.get("drinkTime")); //喝酒频率 on
//                historyMap.put("white_spirit",currentDietVOMap.get("whiteSpiritE")); //白酒(小于40度) on
//                historyMap.put("white_spirit_high",currentDietVOMap.get("whiteSpiritHighE")); //白酒(大于40度) on
//                historyMap.put("red_wine",currentDietVOMap.get("redWineE")); //红酒 on
//                historyMap.put("beer",currentDietVOMap.get("beerE")); //啤酒 on
//                historyMap.put("yellow_wine",currentDietVOMap.get("yellowWineE"));//黄酒 on
//                archivesMap.put("history",historyMap);
//
//                Map<String,Object> treatmentMap = new HashMap<>();
//                treatmentMap.put("mq_fbg",currentBloodSugarVOMap.get("beforeBreakfastSugar"));//空腹血糖
//                treatmentMap.put("blg",currentBloodSugarVOMap.get("afterLunchSugar"));//非空腹血糖
//                treatmentMap.put("bsg",currentBloodSugarVOMap.get("beforeSleepSugar"));//睡觉前血糖
//
//                MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
//                memberArchivesDTO.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
//                memberArchivesDTO.setMemberId(prescriptionDetailPO.getMemberId());
//                this.memberService.updateMemberArchive(memberArchivesDTO);
            }

        }
    }


    @Override
    public void synchronizeSport(PrescriptionDetailPO prescriptionDetailPO,DoctorDTO doctorModel) {
         if (null != prescriptionDetailPO) {
             String detailJson = prescriptionDetailPO.getDetailJson();
             if (!StringUtils.isBlank(detailJson)) {
//                 Map<String, Object> monMap = JsonSerializer.jsonToMap(detailJson);
//                 if (null != monMap) {
//
//                     Map<String,Object> archivesMap = new HashMap<>();
//                     Map<String,Object> historyMap = new HashMap<>();
//                     if(null==monMap.get("sportInfoStr")){
//                         return;
//                     }
//                     Map<String, Object> jsonToMap = JsonSerializer.jsonToMap(monMap.get("sportInfoStr").toString());
//                     if (null != jsonToMap) {
//                         historyMap.put("bs_sport",jsonToMap.get("member_sprot_case"));
//                         historyMap.put("bs_bg_sport_time",jsonToMap.get("sport_opportunity"));
//                         historyMap.put("bs_sport_long",jsonToMap.get("sport_duration"));
//                         historyMap.put("bs_sport_requency",jsonToMap.get("sport_frequency"));
//
//                         historyMap.put("bs_sport_type",jsonToMap.get("sprot_method"));
//                         historyMap.put("sport_type_remark",jsonToMap.get("tb_sprot_method"));
//                         archivesMap.put("history",historyMap);
//
//                         MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
//                         memberArchivesDTO.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
//                         memberArchivesDTO.setMemberId(prescriptionDetailPO.getMemberId());
//                         this.memberService.updateMemberArchive(memberArchivesDTO);
//                     }
//                 }
             }
         }

     }

    @Override
    public ApiMemberEverydaySugarBO getMemberEverydaySugarNewByMemberId(String memberId) {
        return null;
    }

    @Override
    public Long getNumberLowBloodMonthByMemberId(MemberBloodSugarBO memberBloodSugarBO) {
        return null;
    }

    @Override
    public String getBloodSugarThreeMonthNew(MemberParamBO memberParamBO) {
        BloodSugarPO bloodSugarPO = this.bloodSugarService.getLatestBloodSugar(memberParamBO.getMemberId() ,memberParamBO.getParamCode(),null);
        if(bloodSugarPO == null){
            return "";
        }
        return bloodSugarPO.getParamValue();
    }

    @Override
    public ApiBloodPressureBO getBloodPressureNewByMemberId(String memberId) {
        return null;
    }


    @Override
    public void synchronizeEdu(PrescriptionDetailPO prescriptionDetailPO) {
        if (null != prescriptionDetailPO) {
            String detailJson = prescriptionDetailPO.getDetailJson();
            if (!StringUtils.isBlank(detailJson)) {
                PrescriptionEduVO prescriptionEduVO = JsonSerializer.jsonToObject(detailJson, PrescriptionEduVO.class);
                if (null != prescriptionEduVO) {
                    //获取档案信息
//                    MemberArchivesPO memberArchivesModel = this.memberService.getMemberArchives(prescriptionDetailPO.getMemberId());
//                    if (null == memberArchivesModel) {
//                        memberArchivesModel = new MemberArchivesPO();
//                        memberArchivesModel.setMemberId(prescriptionDetailPO.getMemberId());
//                    }

//                    Map<String,Object> archivesMap = new HashMap<>();
//
//                    Map<String,Object> basicMap = new HashMap<>();
//                    basicMap.put("diabetes_date",prescriptionEduVO.getDiabetesDate());
//                    basicMap.put("jbxx_mujcxtpl_week",prescriptionEduVO.getBloodFrequency());
//                    archivesMap.put("basic",basicMap);
//
//                    Map<String,Object> labMap = new HashMap<>();
//                    labMap.put("lab_hba",prescriptionEduVO.getGlycosylatedVal());
//                    archivesMap.put("lab",labMap);
//
//                    Map<String,Object> hypoglycemiaMap = new HashMap<>();
//                    hypoglycemiaMap.put("hyp_frequency",prescriptionEduVO.getLowBloodMonth());
//                    archivesMap.put("hypoglycemia",hypoglycemiaMap);
//
//                    Map<String,Object> complicationMap = new HashMap<>();
//                    complicationMap.put("nephropathy",prescriptionEduVO.getPrescriptionComplication().getHasNephropathy());
//                    complicationMap.put("retinal",prescriptionEduVO.getPrescriptionComplication().getHasRetinopathy());
//                    complicationMap.put("pad",prescriptionEduVO.getPrescriptionComplication().getHaslowerExt());
//                    complicationMap.put("diabetic_foot",prescriptionEduVO.getPrescriptionComplication().getHasDisFoot());
//                    complicationMap.put("neuropathy",prescriptionEduVO.getPrescriptionComplication().getHasPNP());
//                    complicationMap.put("chd",prescriptionEduVO.getPrescriptionComplication().getHasChd());
//                    archivesMap.put("complication",complicationMap);
//
//                    Map<String,Object> anamnesisMap = new HashMap<>();
//                    anamnesisMap.put("essential_hyp",prescriptionEduVO.getPrescriptionComplication().getHasHyp());
//                    archivesMap.put("anamnesis",anamnesisMap);
//
//                    //教育情况
//                    Map<String,String> historyMap = new HashMap<>();
//                    historyMap.put("bs_sport_requency",prescriptionEduVO.getSportFrequency());
//                    historyMap.put("know_knowledge",prescriptionEduVO.getKnowKnowledge());
//                    archivesMap.put("history",historyMap);
//
//                    MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
//                    memberArchivesDTO.setMemberId(prescriptionDetailPO.getMemberId());
//                    memberArchivesDTO.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
//                    this.memberService.updateMemberArchive(memberArchivesDTO);

                    //member同步患病日期
//                    String diabetesDate = prescriptionEduVO.getDiabetesDate();
//                    UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
//                    updateMemberDTO.setDiabetesDate(diabetesDate);
//                    updateMemberDTO.setMemberId(prescriptionDetailPO.getMemberId());
//                    this.memberService.updateMember(updateMemberDTO);
                }
            }
        }
    }

    @Override
    public void sendPrescriptionDialogue(PrescriptionDialogueBO prescriptionDialogueBO, PrescriptionHandDownBO prescriptionHandDownBO) {
        DialoguePO dialogueModel = new DialoguePO();
        dialogueModel.setDoctorId(prescriptionDialogueBO.getDoctorId());
        dialogueModel.setMemberId(prescriptionDialogueBO.getMemberId());
        dialogueModel.setSenderId(prescriptionDialogueBO.getSenderId());
        dialogueModel.setDoctorMsg(prescriptionDialogueBO.getMsg());
        dialogueModel.setPatientMsg(prescriptionDialogueBO.getMsg());

        dialogueModel.setSendTimestamp(System.currentTimeMillis());
        dialogueModel.setUpdateTimestamp(System.currentTimeMillis());

        dialogueModel.setMsgType(DialogueConstant.DIALOGUE_PRESCRIPT_MSG_TYPE);
        dialogueModel.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        dialogueModel.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        dialogueModel.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);

        dialogueModel.setForeignId(prescriptionHandDownBO.getLogId());
        dialogueModel.setDataStr(JSON.toJSONString(prescriptionHandDownBO));
        this.dialogueService.addDialogue(dialogueModel);
    }

    @Override
    public void addPrescriptionTemplate(PrescriptionTemplateBO prescriptionTemplateBO) {

//        DoctorBO doctorModel = this.doctorService.getDoctorById(prescriptionTemplateBO.getDoctorId());
//        if (null != doctorModel) {
//            this.wechatService.addEohTemplate(prescriptionTemplateBO.getMemberId(),doctorModel.getDoctorName(),prescriptionTemplateBO.getPrescriptionId());
//        }

    }

    @Override
    public void incrMemberArticleClick(String memberId) {
        //点击埋点
        IncrMemberArticleClicksDTO incrMemberArticleClicksDTO = new IncrMemberArticleClicksDTO();
        incrMemberArticleClicksDTO.setMemberId(memberId);
        incrMemberArticleClicksDTO.setKnowledgeClicks(1);  //知识学习点击数
        this.knowledgeService.incrMemberArticleClick(incrMemberArticleClicksDTO);
    }

    @Override
    public ApiArticleTxtBO loadArticleTxtByArticleId(Long articleId) {
        ApiArticleTxtBO apiArticleTxtBO = null;
        ArticleTxtModel articleTxtModel = this.knowledgeService.loadArticleTxtByArticleId(articleId);
        if (articleTxtModel != null){
            apiArticleTxtBO = new ApiArticleTxtBO();
            BeanUtils.copyProperties(apiArticleTxtBO,articleTxtModel);
        }
        return apiArticleTxtBO;
    }

    @Override
    public ApiDoctorBO getDoctorById(String doctorId) {
        ApiDoctorBO apiDoctorBO = null;
        DoctorPO doctorPO = doctorService.getDoctorById(doctorId);
        if (doctorPO != null){
            apiDoctorBO = new ApiDoctorBO();
            BeanUtils.copyProperties(apiDoctorBO,doctorPO);
        }
        return apiDoctorBO;
    }


    @Override
    public List<String> getDoctorIdList(String doctorId,Integer type) {
        List<String> doctorIdList = this.doctorService.listTeamId(doctorId);
        if (type != null){
            doctorIdList.add("-1");
        }
        return doctorIdList;
    }

    @Override
    public void useService(String prescriptionId) {
        PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptionId);
        if(prescriptionVO == null){
            return;
        }
        UsePackageServiceDTO usePackageServiceDTO = new UsePackageServiceDTO();
        usePackageServiceDTO.setDoctorId(prescriptionVO.getDoctorId());
        usePackageServiceDTO.setMemberId(prescriptionVO.getMemberId());
        usePackageServiceDTO.setServiceCode(ServiceCode.CHU_FANG);
        this.packageService.usePackageServiceWithLock(usePackageServiceDTO);
    }


    @Override
    public void addPrescriptionWechatMessage(PrescriptionVO prescriptionVO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctorId", prescriptionVO.getDoctorId());
        jsonObject.put("prescriptionId", prescriptionVO.getSid());
        jsonObject.put("date", DateHelper.getToday());

        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(prescriptionVO.getMemberId());
        addWechatMessageDTO.setForeignId(prescriptionVO.getSid());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_PRESCRIPTION);
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    @Override
    public void addMemberCourse(String sid, String pid,String origin,String isPlan,Long planId) {
        this.courseService.addMemberCourse(sid,pid, origin,isPlan,planId);
    }

    @Override
    public List<ApiBloodSugarBO> listSugar(ListBloodSugarDTO listBloodSugarDTO) {
        List<ApiBloodSugarBO> apiBloodSugarBOList = null ;
        List<BloodSugarPO> list = this.bloodSugarService.listSugar(listBloodSugarDTO);
        if (list != null && list.size() >0){
            apiBloodSugarBOList = new ArrayList<>();
            for (BloodSugarPO bloodSugarPO : list) {
                ApiBloodSugarBO apiBloodSugarBO = null;
                if (bloodSugarPO != null){
                    apiBloodSugarBO = new ApiBloodSugarBO();
                    BeanUtils.copyProperties(apiBloodSugarBO,bloodSugarPO);
                }
                apiBloodSugarBOList.add(apiBloodSugarBO);
            }
        }
        return apiBloodSugarBOList;
    }

    @Override
    public List<ApiMemberBO> listMemberByWhere(GetMemberPerDTO getMemberPerDTO) {
        List<ApiMemberBO> list = null;
        MemberParamsDTO dto = new MemberParamsDTO();
        BeanUtils.copyProperties(dto,getMemberPerDTO);
        List<MemberPO> members = this.memberService.listMemberByWhere(dto);
        if(members!=null){
            list = new ArrayList<ApiMemberBO>(members.size());
            for(MemberPO memberPO :members){
                ApiMemberBO apiMemberBO = null;
                if (null != memberPO) {
                    apiMemberBO = new ApiMemberBO();
                    BeanUtils.copyProperties(apiMemberBO,memberPO);
                }
                list.add(apiMemberBO);
            }
        }
        return list;
    }

    @Override
    public void addBmi(String dataJson) {
        AddBmiServiceDTO addBmiServiceDTO = JSONObject.parseObject(dataJson,AddBmiServiceDTO.class);
        if(addBmiServiceDTO!=null){
            ValidateTool.checkParamIsNull(addBmiServiceDTO.getWeight(),"weight");
            ValidateTool.checkParamIsNull(addBmiServiceDTO.getHeight(),"height");
            ValidateTool.checkParamIsNull(addBmiServiceDTO.getBmi(),"bmi");
            ValidateTool.checkParamIsNull(addBmiServiceDTO.getRecordDt(),"recordDt");
            ValidateTool.checkParamIsNull(addBmiServiceDTO.getMemberId(),"memberId");
            ValidateTool.checkParamIsNull(addBmiServiceDTO.getOperatorId(),"operatorId");
            ValidateTool.checkParamIsNull(addBmiServiceDTO.getOperatorType(),"operatorType");
            ValidateTool.checkParamIsNull(addBmiServiceDTO.getOrigin(),"origin");
            String id = this.bmiService.addBmi(addBmiServiceDTO);
        } else {
            throw new BusinessException("参数不能为空");
        }
    }

    @Override
    public void addBloodPressure(String dataJson) {
        AddBloodPressureServiceDTO addBloodPressureServiceDTO = JSONObject.parseObject(dataJson,AddBloodPressureServiceDTO.class);
        if(addBloodPressureServiceDTO!=null){
            ValidateTool.checkParamIsNull(addBloodPressureServiceDTO.getDbp(),"dbp");
            ValidateTool.checkParamIsNull(addBloodPressureServiceDTO.getSbp(),"sbp");
            ValidateTool.checkParamIsNull(addBloodPressureServiceDTO.getRecordTime(),"recordTime");
            ValidateTool.checkParamIsNull(addBloodPressureServiceDTO.getMemberId(),"memberId");
            ValidateTool.checkParamIsNull(addBloodPressureServiceDTO.getOperatorId(),"operatorId");
            ValidateTool.checkParamIsNull(addBloodPressureServiceDTO.getOperatorType(),"operatorType");
            ValidateTool.checkParamIsNull(addBloodPressureServiceDTO.getOrigin(),"origin");
            this.bloodPressureService.addBloodPressure(addBloodPressureServiceDTO);
        } else {
            throw new BusinessException("参数不能为空");
        }
    }

    @Override
    public void addBloodSugar(String dataJson) {
        AddBloodSugarServiceDTO addBloodSugarServiceDTO = JSONObject.parseObject(dataJson,AddBloodSugarServiceDTO.class);
        if(addBloodSugarServiceDTO!=null){
            ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getParamCode(),"paramCode");
            ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getParamValue(),"paramValue");
            ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getInHos(),"inHos");
            ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getRecordDt(),"recordDt");
            ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getMemberId(),"memberId");
            ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getOperatorId(),"operatorId");
            ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getOperatorType(),"operatorType");
            ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getOrigin(),"origin");
            String sid = this.bloodSugarService.addBloodSugar(addBloodSugarServiceDTO);
        } else {
            throw new BusinessException("参数不能为空");
        }
    }

    @Override
    public void addHba1c(String dataJson) {
        AddHba1cDTO addHba1cDTO = JSONObject.parseObject(dataJson,AddHba1cDTO.class);
        if(addHba1cDTO!=null){
            ValidateTool.checkParamIsNull(addHba1cDTO.getRecordValue(),"recordValue");
            ValidateTool.checkParamIsNull(addHba1cDTO.getRecordDt(),"recordDt");
            ValidateTool.checkParamIsNull(addHba1cDTO.getMemberId(),"memberId");
            ValidateTool.checkParamIsNull(addHba1cDTO.getOperatorId(),"operatorId");
            ValidateTool.checkParamIsNull(addHba1cDTO.getOperatorType(),"operatorType");
            ValidateTool.checkParamIsNull(addHba1cDTO.getOrigin(),"origin");
            String sid = this.hba1cService.addHba1c(addHba1cDTO);
        } else {
            throw new BusinessException("参数不能为空");
        }
    }

    @Override
    public boolean checkDoctorMemberRelationExists(String memberId, String doctorId) {
        return this.memberService.checkDoctorMemberRelationExists(memberId ,doctorId);
    }

    @Override
    public MemberLatestSignVO getMemberLatestSign(String memberId) {
        MemberLatestSignVO memberLatestSignVO = new MemberLatestSignVO();
        //血压
        BloodPressurePO bloodPressurePO = this.bloodPressureService.getLatestBloodPressure(memberId);
        BloodPressureBO bloodPressureBO = new BloodPressureBO();
        if(bloodPressurePO != null){
            BeanUtils.copyProperties(bloodPressureBO ,bloodPressurePO);
            memberLatestSignVO.setBloodPressure(bloodPressureBO);
        }
        //bmi
        BmiPO bmiPO = this.bmiService.getLatestBmi(memberId);
        BmiBO bmiBO = new BmiBO();
        if(bmiPO != null){
            BeanUtils.copyProperties(bmiBO ,bmiPO);
            memberLatestSignVO.setBmi(bmiBO);
        }

        //腰臀比
        WhrPO whrPO = this.whrService.getLatestWhr(memberId);
        WhrBO whrBO = new WhrBO();
        if(whrPO != null){
            BeanUtils.copyProperties(whrBO ,whrPO);
            memberLatestSignVO.setWhr(whrBO);
        }
        //糖化
        Hba1cPO hba1cPO = this.hba1cService.getLatestHba1c(memberId);
        Hba1cBO hba1cBO = new Hba1cBO();
        if(hba1cPO != null){
            BeanUtils.copyProperties(hba1cBO ,hba1cPO);
            memberLatestSignVO.setHba1c(hba1cBO);
        }

        //血脂
        CheckoutBloodFatVO checkoutBloodFatVO = this.checkoutService.getMemberLatestBloodFat(memberId);
        CheckoutBloodFatBO checkoutBloodFatBO = new CheckoutBloodFatBO();
        if(checkoutBloodFatVO != null){
            BeanUtils.copyProperties(checkoutBloodFatBO ,checkoutBloodFatVO);
            memberLatestSignVO.setBloodFat(checkoutBloodFatBO);
        }
        return memberLatestSignVO;
    }

    @Override
    public void addBloodFat(String dataJson ,String doctorId) {
        JSONObject jsonObject = JSONObject.parseObject(dataJson);
        AddCheckoutDTO addCheckoutDTO = new AddCheckoutDTO();
        addCheckoutDTO.setCheckoutTitle("血脂记录");
        addCheckoutDTO.setMemberId(jsonObject.getString("memberId"));
        addCheckoutDTO.setVisitNo(Constant.DEFAULT_FOREIGN_ID);
        addCheckoutDTO.setRecordOrigin(4);
        addCheckoutDTO.setCheckoutDate(jsonObject.getString("recordDt"));

        String sid = DaoHelper.getSeq();
        List<JSONObject> list = new ArrayList<>();
        JSONObject ldl = new JSONObject();
        ldl.put("name", "低密度脂蛋白");
        ldl.put("code", "add_check_ldl");
        ldl.put("value", jsonObject.get("ldl"));
        ldl.put("unit", "mmol/L");
        ldl.put("sid", sid);
        list.add(ldl);

        JSONObject hdl = new JSONObject();
        hdl.put("name", "高密度脂蛋白");
        hdl.put("code", "add_check_hdl");
        hdl.put("value", jsonObject.get("hdl"));
        hdl.put("unit", "mmol/L");
        hdl.put("sid", sid);
        list.add(hdl);

        JSONObject triglyceride = new JSONObject();
        triglyceride.put("name", "甘油三酯");
        triglyceride.put("code", "add_check_triglyceride");
        triglyceride.put("value", jsonObject.get("triglyceride"));
        triglyceride.put("unit", "mmol/L");
        triglyceride.put("sid", sid);
        list.add(triglyceride);

        JSONObject tc = new JSONObject();
        tc.put("name", "总胆固醇");
        tc.put("code", "add_check_tc");
        tc.put("value", jsonObject.get("tc"));
        tc.put("unit", "mmol/L");
        hdl.put("sid", sid);
        list.add(tc);

        addCheckoutDTO.setDetailJSON(JSONArray.toJSONString(list));

        DoctorPO doctorPO = null;
        if(StringUtils.isBlank(doctorId)){
            doctorPO = this.doctorService.getDoctorById(doctorId);
        }
        this.checkoutDetailService.addCheckout(addCheckoutDTO,doctorPO);
    }

    @Override
    public ApiRangeBO rangeRecommend(GetTargetRecommendDTO getTargetRecommendDTO) {
        ControlTargetConstant.BaseInfo baseInfo = new ControlTargetConstant().new BaseInfo();
        baseInfo.setBirthday(getTargetRecommendDTO.getBirthday());
        baseInfo.setChd(getTargetRecommendDTO.getChd());
        baseInfo.setCkd(getTargetRecommendDTO.getCkd());
        baseInfo.setDiabetesBfz(getTargetRecommendDTO.getDiabetesBfz());
        baseInfo.setIsDiabetes(getTargetRecommendDTO.getIsDiabetes());
        baseInfo.setDiabetesType(getTargetRecommendDTO.getDiabetesType());
        baseInfo.setEssentialHyp("1");
        baseInfo.setHypBfz(getTargetRecommendDTO.getHypBfz());
        baseInfo.setHypLayer(getTargetRecommendDTO.getHypLayer());
        baseInfo.setSex(getTargetRecommendDTO.getSex());
        baseInfo.setHeight(getTargetRecommendDTO.getHeight());
        MemberControlTargetBO memberControlTargetBO = ControlTargetConstant.getDefaultControlTarget(baseInfo);
        ApiRangeBO apiRangeBO = new ApiRangeBO();
        BeanUtils.copyProperties(apiRangeBO ,memberControlTargetBO);
        return apiRangeBO;
    }

    /**
     * 默认控制目标基础信息处理
     * @param memberId
     * @return
     */
    @Override
    public ControlTargetConstant.BaseInfo baseInfoHandler(String memberId,String doctorId){
        ControlTargetConstant.BaseInfo baseInfo = new ControlTargetConstant().new BaseInfo();

        ApiMemberBO memberPO = getMemberByMemberId(memberId);
        if(memberPO == null){
            throw new BusinessException("患者不存在");
        }

        //填充基础信息
        baseInfo.setSex(Integer.parseInt(memberPO.getSex()));
        baseInfo.setChd(memberPO.getChd());
        baseInfo.setDiabetesType(memberPO.getDiabetesType());
        baseInfo.setEssentialHyp(memberPO.getEssentialHyp());
        baseInfo.setBirthday(memberPO.getBirthday());
        baseInfo.setIsDiabetes(memberPO.getIsDiabetes());
        baseInfo.setHeight(memberPO.getHeight());

        //获取高血压分级
        CurrentGxyLevelDTO c = new CurrentGxyLevelDTO();
        c.setMemberId(memberId);
        c.setLevelType(1);
        MemberLevelPO memberLevelPO = this.memberLevelService.getCurrentGxyLevel(c);
        if(memberLevelPO != null){
            baseInfo.setHypLayer(memberLevelPO.getMemberLayer());
        }

        //处理档案字段
        ApiMemberArchivesBO memberArchivesPO = getMemberArchivesById(memberId ,doctorId);
        if(memberArchivesPO != null){
            String archivesJson = memberArchivesPO.getArchivesJson();
            baseInfo.setCkd(PrescriptionMemberRangeHelper.getCkd(archivesJson));
            baseInfo.setHypBfz(PrescriptionMemberRangeHelper.getHypBfz(archivesJson));
            baseInfo.setDiabetesBfz(PrescriptionMemberRangeHelper.getDiabetesBfz(archivesJson,memberPO));
        }
        return baseInfo;
    }

    @Override
    public Long countDoctorMemberRelation(String doctorId, String memberId) {
        CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
        countDoctorMemberDTO.setMemberId(memberId);
        countDoctorMemberDTO.setDoctorId(doctorId);
        return this.memberService.countMemberDoctor(countDoctorMemberDTO);
    }
}
