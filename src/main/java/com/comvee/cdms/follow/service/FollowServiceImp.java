package com.comvee.cdms.follow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.app.doctorapp.vo.CountFollowReqVO;
import com.comvee.cdms.bloodmonitor.dto.GetMemberMonitorDTO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorTaskCardPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanRecordService;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.cfg.ParamLogConstant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.cfg.DoctorConstant;
import com.comvee.cdms.follow.bo.HypJwFollowBodyBO;
import com.comvee.cdms.follow.cfg.FollowRemindConstant;
import com.comvee.cdms.follow.vo.FollowRemindVO;
import com.comvee.cdms.follow.vo.ListFollowPlatformRecordVO;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.service.CommitteeService;
import com.comvee.cdms.level.dto.CurrentGxyLevelDTO;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.differentlevels.dto.MemberDiffLevelSureDTO;
import com.comvee.cdms.differentlevels.service.DifferentLevelsService;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.follow.bo.DialogueFollowQuestionBO;
import com.comvee.cdms.follow.bo.DialogueFollowReportBO;
import com.comvee.cdms.follow.cfg.FollowConstant;
import com.comvee.cdms.follow.cfg.FollowReportConstant;
import com.comvee.cdms.follow.dto.*;
import com.comvee.cdms.follow.helper.*;
import com.comvee.cdms.follow.mapper.FollowMapper;
import com.comvee.cdms.follow.model.*;
import com.comvee.cdms.follow.po.*;
import com.comvee.cdms.foot.dto.ListFootDTO;
import com.comvee.cdms.foot.po.FootPO;
import com.comvee.cdms.foot.service.FootService;
import com.comvee.cdms.knowledge.model.PagerModel;
import com.comvee.cdms.member.constant.ControlTargetConstant;
import com.comvee.cdms.member.constant.ControlTargetConstant;
import com.comvee.cdms.member.constant.DiabetesConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.mapper.MemberCheckinInfoMapper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.model.MemberArchivesModel;
import com.comvee.cdms.member.po.*;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.tool.MemberRangeHelper;
import com.comvee.cdms.member.tool.MemberArchivesTuoMinHelper;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.dto.UsePackageServiceDTO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.prescription.bo.ApiKnowledgePlanBO;
import com.comvee.cdms.prescription.bo.FoodItemBO;
import com.comvee.cdms.prescription.bo.KnowledgePlanBO;
import com.comvee.cdms.prescription.po.PrescriptionKnowledgePO;
import com.comvee.cdms.prescription.service.PrescriptionApiI;
import com.comvee.cdms.prescription.service.PrescriptionOfDietServiceI;
import com.comvee.cdms.questionnaire.constant.QuestionnaireConstant;
import com.comvee.cdms.prescription.vo.eduplan.Knowledge;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeVO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeWeek;
import com.comvee.cdms.questionnaire.model.dto.AddQuestionnaireDTO;
import com.comvee.cdms.questionnaire.model.po.QuestionnairePO;
import com.comvee.cdms.questionnaire.service.QuestionnaireService;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddHba1cDTO;
import com.comvee.cdms.sign.dto.CountBloodSugarDTO;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.sign.service.Hba1cService;
import com.comvee.cdms.statistics.dto.GetMemberStaticsDTO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import com.github.pagehelper.PageHelper;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 李左河
 */
@Service("followService")
public class FollowServiceImp implements FollowServiceI {

    private final static Logger log = LoggerFactory.getLogger(FollowServiceImp.class);

    @Autowired
    public FollowMapper followMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    @Lazy
    private PrescriptionOfDietServiceI prescriptionOfDietService;

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private PackageService packageService;

    @Autowired
    @Lazy
    private BloodSugarService bloodSugarService;

    @Autowired
    @Lazy
    private FootService footService;

    @Autowired
    @Lazy
    private QuestionnaireService questionnaireService;

    @Autowired
    private Hba1cService hba1cService;

    @Autowired
    private DifferentLevelsService differentLevelsService;

    @Autowired
    private MemberMonitorPlanServiceI memberMonitorPlanService;

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    @Lazy
    private FollowOfEduService followOfEduService;

    @Autowired
    private MemberCheckinInfoMapper memberCheckinInfoMapper;

    @Autowired
    private MemberMonitorPlanRecordService memberMonitorPlanRecordService;

    @Autowired
    private CommitteeService committeeService;

    @Override
    public List<FollowListModel> listFollow(String memberId, String memberName, String doctorId, List<Integer> followType, Boolean deal) {
        return this.followMapper.listFollow(memberId, memberName, doctorId, followType, deal);
    }

    @Override
    public Object getFollowById(String sid, Integer type) {
        if (type != null && type == 1 || type == 10) {
            //获取首诊详情
            FollowPO follow = this.followMapper.getFollowById(sid);
            DoctorPO doctorPO = doctorService.getDoctorById(follow.getDoctorId());
            if (null != doctorPO) {
                follow.setDoctorName(doctorPO.getDoctorName());
            }
            GetMemberDrugItemDTO getMemberDrugItemDTO = new GetMemberDrugItemDTO();
            getMemberDrugItemDTO.setMemberId(follow.getMemberId());
            getMemberDrugItemDTO.setFollowId(follow.getSid());
            getMemberDrugItemDTO.setCacheKey(MD5Util.md5("D"+follow.getSid()+"M"+follow.getMemberId()));
            List<MemberDrugItemPO> list = memberService.getMemberDrugItemList(getMemberDrugItemDTO);
            if (null != list && list.size() > 0) {
                String s = JsonSerializer.objectToJson(list);
                follow.setDrugListJson(s);
                follow.setHasDrug("1");
            }
            if (!StringUtils.isBlank(follow.getArchivesJson())) {
                String jMap = doHistoryFood(follow.getArchivesJson());
                follow.setArchivesJson(jMap);
                String newFollow = MemberArchivesTuoMinHelper.archivesTuoMin(follow.getArchivesJson(), null, follow.getDealStatus());
                follow.setArchivesJson(newFollow);
            }
            if(type == 1){
                //封装知识
                List<PrescriptionKnowledgePO> knowledgeList = this.followOfEduService.listFollowKnowledge(sid);
                List<KnowledgeWeek> knowledgeWeekList = this.handleWeekOfKnowledgeList(knowledgeList);
                follow.setKnowledgeList(knowledgeList);
                follow.setKnowledgeWeekList(knowledgeWeekList);
            }

            return follow;
        } else if (type != null && type == 6) {
            //获取自定义随访详情
            FollowCustomContentPO followCustomContent = this.followMapper.getFollowCustomContentById(sid);
            MainFollowPO follow = this.followMapper.getMainFollowByFidAndType(sid, null);
            if (null != follow){
                followCustomContent.setDealStatus(follow.getStatus());
                followCustomContent.setFillFormBy(follow.getFillFormBy());  //填表人：1 医生 2 患者
                if (!StringUtils.isBlank(followCustomContent.getArchivesJson())) {
                    String historyFood = doHistoryFood(followCustomContent.getArchivesJson());
                    followCustomContent.setArchivesJson(historyFood);
                    String aJson= MemberArchivesTuoMinHelper.archivesTuoMin(followCustomContent.getArchivesJson(), null, follow.getStatus());
                    followCustomContent.setArchivesJson(aJson);
                }
                if (StringUtils.isBlank(followCustomContent.getQuesJSon())){
//                    followCustomContent.setQuesJSon(getQuesJSon(followCustomContent.getSid()));
                }
            }
            return followCustomContent;
        }
        //糖尿病2型随访 或 高血压随访
        else if (type != null && (type == 7 || type == FollowConstant.FOLLOW_TYPE_HYP_JW)){
            FollowDiabetesPO followDiabetes = this.followMapper.getFollowDiabetesById(sid);
            MainFollowPO mainFollowPO = followMapper.getMainFollowByFidAndType(sid, type);
            if (null != mainFollowPO) {
                followDiabetes.setLeaderId(mainFollowPO.getLeaderId());
                followDiabetes.setDealStatus(mainFollowPO.getStatus());
                followDiabetes.setDoctorName(mainFollowPO.getDoctorName());
                followDiabetes.setNextDt(mainFollowPO.getNextDt());
                DoctorPO doctorPO = doctorService.getDoctorById(mainFollowPO.getDoctorId());
                if (null != doctorPO) {
                    followDiabetes.setDoctorName(doctorPO.getDoctorName());
                }
            }
            return followDiabetes;
        }else  if (type != null && (type == 15 || type == 16 || type == 17 )){
            Map<String,FollowMmcVisitPO> map = new HashMap<>();
            List<FollowMmcVisitPO> newfollowMmcVisitLIST = new ArrayList<>();
            FollowMmcVisitPO followMmcVisit = new FollowMmcVisitPO();
            followMmcVisit.setSid(sid);
            followMmcVisit.setFollowType(type.toString());
            newfollowMmcVisitLIST = this.followMapper.listFollowMmcVisit(followMmcVisit);
            if (newfollowMmcVisitLIST != null && newfollowMmcVisitLIST.size() != 0){
                map.put("0",newfollowMmcVisitLIST.get(0));
                List<FollowMmcVisitPO> oldfollowMmcVisitLIST = new ArrayList<>();
                followMmcVisit = new FollowMmcVisitPO();
                followMmcVisit.setMemberId(newfollowMmcVisitLIST.get(0).getMemberId());
                followMmcVisit.setFollowType(newfollowMmcVisitLIST.get(0).getFollowType());
                oldfollowMmcVisitLIST = this.followMapper.listFollowMmcVisit(followMmcVisit);
                if (oldfollowMmcVisitLIST!=null && oldfollowMmcVisitLIST.size()>=2){
                    map.put("1",oldfollowMmcVisitLIST.get(1));
                }
            }
            return map;
        }else {
            //获取日常随访详情
            FollowupPO followup = this.followMapper.getFollowupById(sid);
            MainFollowPO mainFollowPO = followMapper.getMainFollowByFidAndType(sid, type);
            if (null != mainFollowPO) {
                followup.setLeaderId(mainFollowPO.getLeaderId());
                followup.setDealStatus(mainFollowPO.getStatus());
                followup.setDoctorName(mainFollowPO.getDoctorName());
                followup.setNextDt(mainFollowPO.getNextDt());
                DoctorPO doctorPO = doctorService.getDoctorById(mainFollowPO.getDoctorId());
                if (null != doctorPO) {
                    followup.setDoctorName(doctorPO.getDoctorName());
                }
            }
            if(followup!=null){
                if (!StringUtils.isBlank(followup.getFollowInfo())){
                    String re = followup.getFollowInfo();
                    re= MemberArchivesTuoMinHelper.archivesTuoMin(re, null, followup.getDealStatus());
                    followup.setFollowInfo(re);
                }
                if (!StringUtils.isBlank(followup.getMemberInfo()) && followup.getDealStatus()!=null && followup.getDealStatus() == 1){
                    String memberInfo = followup.getMemberInfo();
                    Map<String, Object> oldMemberInfoMap=JsonSerializer.jsonToMap(memberInfo);
                    if (memberInfo.indexOf("mobilePhone") >= 0){
                        if (oldMemberInfoMap.get("mobilePhone") != null){
                            String mobilePhone = oldMemberInfoMap.get("mobilePhone").toString();
                            if (!StringUtils.isBlank(mobilePhone)){
                                mobilePhone = ValidateTool.tuoMin(mobilePhone,3,4,"*");
                                oldMemberInfoMap.put("mobilePhone",mobilePhone);
                                memberInfo = JsonSerializer.objectToJson(oldMemberInfoMap);
                            }
                        }
                    }
                    followup.setMemberInfo(memberInfo);
                }

            }
            return followup;
        }
    }


    @Override
    public String doHistoryFood(String archivesJson) {
        List<FoodItemBO> reFoodItem = new ArrayList<>();
        Map<String, Object> jMap = JsonSerializer.jsonToMap(archivesJson);
        if (null == jMap.get("history")) {
            return archivesJson;
        }
        String hStr = jMap.get("history").toString();
        Map<String, Object> history = JsonSerializer.jsonToMap(hStr);

        // 食用早餐主食
        String bsZczslx = StringUtils.converParamToString(history.get("bs_zczslx"));
        //食用中餐主食
        String bsWuczslx = StringUtils.converParamToString(history.get("bs_wuczslx"));
        //食用晚餐主食
        String bsWanczslx = StringUtils.converParamToString(history.get("bs_wanczslx"));
        //有加餐习惯(早餐加餐)
        String bsJcnr = StringUtils.converParamToString(history.get("bs_jcnr"));
        //午餐加餐
        String bsLunchJc = StringUtils.converParamToString(history.get("bs_lunch_jc"));
        //晚餐加餐
        String bsDinnerJc = StringUtils.converParamToString(history.get("bs_dinner_jc"));
        if (!StringUtils.isBlank(bsZczslx)) {
            reFoodItem.addAll(JsonSerializer.jsonToList(bsZczslx, FoodItemBO.class));
            prescriptionOfDietService.fillEohIngredientsItem(reFoodItem, 2);
            history.put("bs_zczslx", JsonSerializer.objectToJson(reFoodItem));
        }
        if (!StringUtils.isBlank(bsWuczslx)) {
            reFoodItem = new ArrayList<>();
            reFoodItem.addAll(JsonSerializer.jsonToList(bsWuczslx, FoodItemBO.class));
            prescriptionOfDietService.fillEohIngredientsItem(reFoodItem, 2);
            history.put("bs_wuczslx", JsonSerializer.objectToJson(reFoodItem));
        }
        if (!StringUtils.isBlank(bsWanczslx)) {
            reFoodItem = new ArrayList<>();
            reFoodItem.addAll(JsonSerializer.jsonToList(bsWanczslx, FoodItemBO.class));
            prescriptionOfDietService.fillEohIngredientsItem(reFoodItem, 2);
            history.put("bs_wanczslx", JsonSerializer.objectToJson(reFoodItem));
        }

        if (!StringUtils.isBlank(bsJcnr)) {
            reFoodItem = new ArrayList<>();
            reFoodItem.addAll(JsonSerializer.jsonToList(bsJcnr, FoodItemBO.class));
            prescriptionOfDietService.fillEohIngredientsItem(reFoodItem, 2);
            history.put("bs_jcnr", JsonSerializer.objectToJson(reFoodItem));

        }
        if (!StringUtils.isBlank(bsLunchJc)) {
            reFoodItem = new ArrayList<>();
            reFoodItem.addAll(JsonSerializer.jsonToList(bsLunchJc, FoodItemBO.class));
            prescriptionOfDietService.fillEohIngredientsItem(reFoodItem, 2);
            history.put("bs_lunch_jc", JsonSerializer.objectToJson(reFoodItem));

        }
        if (!StringUtils.isBlank(bsDinnerJc)) {
            reFoodItem = new ArrayList<>();
            reFoodItem.addAll(JsonSerializer.jsonToList(bsDinnerJc, FoodItemBO.class));
            prescriptionOfDietService.fillEohIngredientsItem(reFoodItem, 2);
            history.put("bs_dinner_jc", JsonSerializer.objectToJson(reFoodItem));

        }
        jMap.put("history", history);
        return JsonSerializer.objectToJson(jMap);
    }


    @Override
    public String insertFollowWithLock(FollowDTO dto) {
        Map memberInfoMap = !StringUtils.isBlank(dto.getMemberInfo()) ? JSONObject.parseObject(dto.getMemberInfo(),Map.class) : new HashMap<String,Object>();
//        if(!"4".equals(dto.getOrigin())){
//            boolean checkResult = this.memberService.checkDoctorMemberRelationExists(dto.getMemberId() ,dto.getLeaderId());
//            if(!checkResult){
//                log.warn("不存在有效的医患关系，创建随访失败！医生id:{},患者id:{},操作者id:{}" ,dto.getLeaderId() ,dto.getMemberId() ,dto.getDoctorId());
//                throw new BusinessException("不存在有效的医患关系，创建随访失败...");
//            }
//        }
        Integer type = dto.getFollowType();
        String sid = DaoHelper.getSeq();
        //添加随访主表
        MainFollowPO mainFollowPO = new MainFollowPO();
        //江苏南京添加doctorIds  如果是小程序的话需要插入fillFormBy = 2 doctorId = doctorIds[0] 20191107
        if(dto.getFillFormBy().equals("2")) {
        	if(!StringUtils.isBlank(dto.getDoctorId())) {
        		String[] idArr = dto.getDoctorId().split(",");
        		mainFollowPO.setDoctorId(idArr[0]);
        		mainFollowPO.setDoctorIds(dto.getDoctorId());
        	}
        }else {
        	mainFollowPO.setDoctorId(dto.getDoctorId());
        }
//        mainFollowPO.setDoctorId(dto.getDoctorId());

        mainFollowPO.setLeaderId(dto.getLeaderId());
        mainFollowPO.setDoctorName(dto.getDoctorName());
        if ("1".equals(dto.getPushMember())){
            mainFollowPO.setFillFormBy(2);
        }else{
            mainFollowPO.setFillFormBy(dto.getFillFormBy());
        }
        mainFollowPO.setMemberId(dto.getMemberId());
        mainFollowPO.setCreateDt(StringUtils.isBlank(dto.getFollowDt()) ? DateHelper.getToday() : dto.getFollowDt());
        mainFollowPO.setNextDt(StringUtils.isBlank(dto.getNextDt()) ? null : dto.getNextDt());
        mainFollowPO.setForeignId(sid);
        mainFollowPO.setType(dto.getFollowType());
        mainFollowPO.setStatus(0);
        mainFollowPO.setSid(DaoHelper.getSeq());
        mainFollowPO.setOrderId(StringUtils.isBlank(dto.getOrderId()) ? "-1" : dto.getOrderId());
        mainFollowPO.setBuyDt(dto.getBuyDt());
        mainFollowPO.setPackageName(dto.getPackageName());
        mainFollowPO.setHospitalId("-1");
        if (!StringUtils.isBlank(dto.getHospitalId())){
            mainFollowPO.setHospitalId(dto.getHospitalId());
        }
        if (type != null) {
            if (type == 1) {
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            } else if (type == 2) {
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            } else if (type == 3) {
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            } else if (type == 4) {
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            } else if (type == 5) {
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            } else if (type == 6) {
                mainFollowPO.setFollowName(dto.getFollowName());
            }else if (type == 7){
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            }else if (type == 9){
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            }else if (type == 8){//江苏南京
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            }else if (type == 10){
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            }else if (type == 11){
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            }else if (type == 12){
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            }
            else if (type == 15){
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            }else if (type == 16){
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            }else if (type == 17){
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            }if (type == 29){
                mainFollowPO.setFollowName(Constant.FOLLOW_NAME_MAP.get(type));
            }
        }
        if (StringUtils.isBlank(dto.getTemplateId())) {
            mainFollowPO.setTemplateId("-1");
        } else {
            mainFollowPO.setTemplateId(dto.getTemplateId());
        }
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(dto.getMemberId());
        MemberPO member = memberService.getMember(getMemberDTO);
        if (null != member) {
            mainFollowPO.setMemberName(member.getMemberName());
            mainFollowPO.setTelephone(member.getMobilePhone());
        }
        //随访类型 1默认 2华西随访
        if (StringUtils.isBlank(dto.getfType())) {
            mainFollowPO.setFollowType(1);
        } else {
            mainFollowPO.setFollowType(Integer.parseInt(dto.getfType()));
        }

        this.followMapper.insertMainFollow(mainFollowPO);

        /*
         * 添加随访类型 1糖尿病首诊 2日常随访 3行为问卷 6自定义随访 7 2型糖尿病随访表 10 高血压首诊
         * 15糖尿病病人问卷调查；16 三个月随访；17 半年随访
         */

        if (type != null && type == 1 || type == 10) {
            //添加首诊
            FollowPO follow = new FollowPO();
            follow.setNextDt(StringUtils.isBlank(dto.getNextDt()) ? null : dto.getNextDt());
            follow.setDoctorName(dto.getDoctorName());
            follow.setDoctorId(dto.getDoctorId());
            follow.setLeaderId(dto.getLeaderId());
            follow.setFollowDt(dto.getFollowDt());
            follow.setFollowType(dto.getFollowType());
            follow.setMemberId(dto.getMemberId());
            follow.setSid(sid);
            if (StringUtils.isBlank(follow.getNextDt())) {
                follow.setNextDt(null);
            }
            this.followMapper.insertFollow(follow);
        } else if (type != null && type == 6) {
            //添加自定义随访内容
            FollowCustomContentPO contentPO = new FollowCustomContentPO();
            contentPO.setSid(sid);
            contentPO.setMemberId(dto.getMemberId());
            contentPO.setFollowDt(dto.getFollowDt());
            contentPO.setFollowDt(StringUtils.isBlank(dto.getFollowDt()) ? null : dto.getFollowDt());
            contentPO.setNextDt(StringUtils.isBlank(dto.getNextDt()) ? null : dto.getNextDt());
            contentPO.setDoctorId(dto.getDoctorId());
            contentPO.setDoctorName(dto.getDoctorName());
            this.followMapper.insertFollowCustomContent(contentPO);
            //如果自定义随访包含问卷就创建问卷
            if (!StringUtils.isBlank(dto.getQuestionType())) {
                String questionType = dto.getQuestionType();
                String[] split = questionType.split(",");
                for (int i = 0; i < split.length; i++) {
                    AddQuestionnaireDTO questionnaireDTO = new AddQuestionnaireDTO();
                    questionnaireDTO.setQuestionType(Integer.valueOf(split[i]));
                    questionnaireDTO.setCreateType(2);
                    questionnaireDTO.setDoctorId(dto.getDoctorId());
                    questionnaireDTO.setMemberId(dto.getMemberId());
                    questionnaireDTO.setOperatorId(dto.getDoctorId());
                    questionnaireDTO.setFollowContentId(sid);
                    this.questionnaireService.addQuestionnaire(questionnaireDTO);
                }
            }
        } else if(type != null && (type == 7 || type == FollowConstant.FOLLOW_TYPE_HYP_JW)){
            //添加2型糖尿病随访表
            FollowDiabetesPO followDiabetes = new FollowDiabetesPO();
            followDiabetes.setSid(sid);
            followDiabetes.setMemberId(dto.getMemberId());
            followDiabetes.setDoctorId(dto.getLeaderId());
            followDiabetes.setFollowDt(dto.getFollowDt());
            this.followMapper.insertFollowDiabetes(followDiabetes);
        }else {
            //添加日常随访
            FollowupPO followup = new FollowupPO();
            followup.setSid(sid);
            followup.setMemberInfo(dto.getMemberInfo());
            followup.setMemberId(dto.getMemberId());
            this.followMapper.insertFollowup(followup);
            if (type != null && (type == 15 || type == 16 || type == 17 )){
                FollowMmcVisitPO followMmcVisit = new FollowMmcVisitPO();
                followMmcVisit.setSid(sid);
                GetMemberDTO memberDTO = new GetMemberDTO();
                memberDTO.setMemberId(dto.getMemberId());
                memberDTO.setHospitalId(dto.getHospitalId());
                MemberCheckinInfoPO memberId = this.memberCheckinInfoMapper.getMemberCheckinInfoByMemberId(dto.getMemberId(), dto.getHospitalId());
                MemberPO memberPO = memberService.getMember(getMemberDTO);
                if (memberId == null){
                    memberPO.setHospitalNo("");
                }else{
                    memberPO.setHospitalNo(memberId.getHospitalNo());
                }
                followMmcVisit.setMemberInfo(JSON.toJSONString(memberPO));
                followMmcVisit.setMemberId(dto.getMemberId());
                followMmcVisit.setFollowType(type.toString());
                this.followMapper.insertFollowMmcVisit(followMmcVisit);
            }
        }

        //添加 首诊/随访 设置
        MemberFollowSetPO set = this.followMapper.getMemberFollowSetByDoc(dto.getMemberId(), dto.getLeaderId(), dto.getFollowType());
        MemberFollowSetPO docNew = this.followMapper.getMemberFollowSetByDocNew(dto.getMemberId(), dto.getLeaderId());
        String newNextDt = docNew == null ? null : docNew.getNextDt();
        if (set != null) {
            set.setDoctorId(dto.getDoctorId());
            set.setFollowType(dto.getFollowType());
            set.setLastDt(dto.getFollowDt());
            set.setLastFollowId(sid);
            set.setNextDt(StringUtils.isBlank(dto.getNextDt()) ? newNextDt : dto.getNextDt());
            this.followMapper.modifyMemberFollowSet(set);
        } else {
            set = new MemberFollowSetPO();
            set.setLeaderId(dto.getLeaderId());
            set.setMemberId(dto.getMemberId());
            set.setSid(DaoHelper.getSeq());
            set.setDoctorId(dto.getDoctorId());
            set.setFollowType(dto.getFollowType());
            set.setLastDt(dto.getFollowDt());
            set.setLastFollowId(sid);
            set.setNextDt(StringUtils.isBlank(dto.getNextDt()) ? newNextDt : dto.getNextDt());
            set.setCycleDt(1);
            this.followMapper.insertMemberFollowSet(set);
        }

        if ("1".equals(dto.getPushMember())) {
            //随访下发通知
            String name = type == 6 ? dto.getFollowName() : Constant.FOLLOW_NAME_MAP.get(type);
            DialogueFollowQuestionBO dialogueFollowQuestionBO = new DialogueFollowQuestionBO();
            dialogueFollowQuestionBO.setDoctorName(dto.getDoctorName());
            dialogueFollowQuestionBO.setName(name);
            dialogueFollowQuestionBO.setFollowType(type);
            dialogueFollowQuestionBO.setDate(DateHelper.getToday());
            dialogueFollowQuestionBO.setTime(DateHelper.getDate(new Date(), "HH:mm:ss"));
            dialogueFollowQuestionBO.setTextType(DialogueConstant.DIALOGUE_FOLLOW_TEXT_TYPE_QUESTION);
            dialogueFollowQuestionBO.setfType(dto.getfType());
            //15/16/17没与报告,故不生成对话框弹窗.
            if (dto.getFollowType() != 15 && dto.getFollowType() != 16 && dto.getFollowType() != 17){
                sendFollowDialogue(dto.getTemplateId(), dto.getMemberId(), dto.getLeaderId(), sid, JSON.toJSONString(dialogueFollowQuestionBO), DIALOGUE_FOLLOW_QUESTION_MESSAGE, dto.getDoctorId() ,type);

                //添加微信消息
                addFollowQuestionWechatMessage(mainFollowPO);
            }
        }
//        //添加微信公众号消息模版
//        this.wechatService.addFollowTemplate(dto.getMemberId(),DateHelper.getToday(),Constant.FOLLOW_NAME_MAP.get(type),mainFollowPO.getSid());

        return sid;
    }

    /**
     * 添加微信消息 - 随访问卷
     *
     * @param mainFollowPO
     */
    private void addFollowQuestionWechatMessage(MainFollowPO mainFollowPO) {
        //部分随访不需要下发至患者的
        if(FollowConstant.FOLLOW_TYPE_NJ_DIABETES == mainFollowPO.getType()){
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("followId", mainFollowPO.getForeignId());
        jsonObject.put("memberId", mainFollowPO.getMemberId());
        jsonObject.put("doctorId", mainFollowPO.getDoctorId());
        jsonObject.put("type", mainFollowPO.getType());
        jsonObject.put("date", DateHelper.getNowDate());
        jsonObject.put("followName", mainFollowPO.getFollowName());
        jsonObject.put("templateId", mainFollowPO.getTemplateId());
        jsonObject.put("fType", mainFollowPO.getFollowType());
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        addWechatMessageDTO.setMemberId(mainFollowPO.getMemberId());
        addWechatMessageDTO.setForeignId(mainFollowPO.getSid());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_FOLLOW_QUESTION);
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    @Override
    public void modifyFollow(FollowDTO dto) {
        Integer type = dto.getFollowType();
        //更新随访主表信息
        MainFollowPO mainFollowPO = this.followMapper.getMainFollowByFidAndType(dto.getSid(), type);
        String operatorDoctorId = mainFollowPO.getDoctorId();
        DoctorPO doctorPO = this.doctorService.getDoctorById(mainFollowPO.getLeaderId());
        String followDt = DateHelper.getDate(new Date(), DateHelper.DATETIME_FORMAT);
        if (mainFollowPO != null) {
            mainFollowPO.setIsValid(dto.getIsValid());
            mainFollowPO.setFillDt(followDt);
            mainFollowPO.setFillFormBy(dto.getFillFormBy());
            mainFollowPO.setFinalFillFormBy(dto.getFinalFillFormBy());
            mainFollowPO.setStatus(dto.getDealStatus());
            mainFollowPO.setNextDt(StringUtils.isBlank(dto.getNextDt()) ? null : dto.getNextDt());
            mainFollowPO.setDoctorId(dto.getDoctorId());
            mainFollowPO.setDoctorName(dto.getDoctorName());
            mainFollowPO.setFollowName(dto.getFollowName());
            this.followMapper.modifyMainFollow(mainFollowPO);
                if (null != dto.getDealStatus() && 2 == dto.getDealStatus()) {
                    FollowRemindPO followRemindPO = new FollowRemindPO();
                    followRemindPO.setDoctorId(mainFollowPO.getLeaderId());
                    followRemindPO.setMemberId(mainFollowPO.getMemberId());
                    followRemindPO.setMemberName(mainFollowPO.getMemberName());
                    followRemindPO.setFollowId(dto.getSid());
                    followRemindPO.setIsDo("0");
                    followRemindPO.setType(type + "");
                    if (dto.getBeforeDay()==null){
                        followRemindPO.setBeforeDay("0");
                    }else {
                        followRemindPO.setBeforeDay(dto.getBeforeDay()+"");
                    }
                    String tStr = "糖尿病首诊";
                    if (FollowConstant.FOLLOW_TYPE_DAY == type) {
                        tStr = "日常随访";
                    }
                    if (FollowConstant.FOLLOW_TYPE_CUSTOM == type) {
                        followRemindPO.setType(FollowRemindConstant.REMIND_ZDYWC); //13自定义随访完成提醒
                        tStr = dto.getFollowName();
                    }
                    if ( FollowConstant.FOLLOW_TYPE_TWO_DIABETES == type){
                        followRemindPO.setType(FollowRemindConstant.REMIND_TNBWC); //2型糖尿病随访表完成提醒
                        tStr = "2型糖尿病随访表";
                    }
                    if (FollowConstant.FOLLOW_TYPE_FIRST_GXY == type){
                        followRemindPO.setType(FollowRemindConstant.REMIND_GXYSZWC);  //高血压首诊随访完成提醒
                        tStr = "高血压首诊";
                    }
                    if (FollowConstant.FOLLOW_TYPE_QUES_FOLLOW == type){
                        followRemindPO.setType(FollowRemindConstant.REMIND_TNBZWC); //糖尿病足随访表完成提醒
                        tStr = "糖尿病足随访表";
                    }
                    if (FollowConstant.FOLLOW_TYPE_FOLLOW == type){
                        followRemindPO.setType(FollowRemindConstant.REMIND_TNBSFWC); //糖尿病随访表完成提醒
                        tStr = "糖尿病随访表";
                    }
                    else if(FollowConstant.FOLLOW_TYPE_HYP_JW == type){
                        followRemindPO.setType(FollowRemindConstant.REMIND_GXY_FOLLOW);  //高血压随访(基位随访)完成提醒
                        tStr = "高血压随访";
                    }

                    String titStr = mainFollowPO.getMemberName() + "患者完成了" + tStr + "问卷，请及时为患者填写评估建议";
                    followRemindPO.setTitle(titStr);
                    followRemindPO.setBeforeDay("0");
                    insertFollowRemind(followRemindPO);
                }
        }

        //首诊
        if (type != null && type == 1 || type == 10) {
            FollowPO follow = new FollowPO();
            follow.setSid(dto.getSid());
            follow.setIsValid(dto.getIsValid());
            follow.setFollowType(dto.getFollowType());
            follow.setFollowDt(followDt);
            follow.setNextDt(StringUtils.isBlank(dto.getNextDt()) ? null : dto.getNextDt());
            follow.setArchivesJson(dto.getArchivesJson());
            follow.setRightToUseInsulin(dto.getRightToUseInsulin());
            follow.setHasDrug(dto.getHasDrug());
            follow.setHbalcDate(StringUtils.isBlank(dto.getHbalcDate()) ? null : dto.getHbalcDate());
            follow.setDealStatus(dto.getDealStatus());
            follow.setMqzywt(dto.getMqzywt());
            follow.setZygjcs(dto.getZygjcs());
            follow.setMemberName(dto.getMemberName());
            follow.setYqddmb(dto.getYqddmb());
            follow.setDoctorName(dto.getDoctorName());
            follow.setDoctorId(dto.getDoctorId());
            follow.setLevelJson(dto.getLevelJson());
            follow.setDrugListJson(dto.getDrugListJson());
            this.followMapper.modifyFollow(follow);
            if (type == FollowConstant.FOLLOW_TYPE_FIRST){
                //保存首诊学习计划
                this.followOfEduService.saveFollowEdu(dto);
            }
            //修改任务卡为已检测状态
            //修改添加血压时,任务卡为已检测
            MemberMonitorTaskCardPO taskCardPO = new MemberMonitorTaskCardPO();
            taskCardPO.setParamCode(bloodPressure(DateHelper.getNowDate().substring(11,18)));
            taskCardPO.setMemberId(mainFollowPO.getMemberId());
            taskCardPO.setMonitorDt(DateHelper.getToday());
            Map objectToMap = JSONObject.parseObject(dto.getArchivesJson());
            String sbp = "";
            String dbp = "";
            if(objectToMap != null) {
                Object sign = objectToMap.get("sign");
                if (sign != null && !"".equals(sign)) {
                    JSONObject json = (JSONObject) JSON.toJSON(sign);
                    if (json.get("sbp") != null && !"".equals(json.get("sbp"))) {
                        sbp = String.valueOf(json.get("sbp"));
                    }
                    if (json.get("dbp") != null && !"".equals(json.get("dbp"))) {
                        dbp = String.valueOf(json.get("dbp"));
                    }
                }
            }
            JSONObject json = new JSONObject();
            json.put("dbp",dbp);
            json.put("sbp",sbp);
            taskCardPO.setParamValue(json.toString());
            taskCardPO.setInsertDt(DateHelper.getToday());
            taskCardPO.setIsMonitor("1");
            taskCardPO.setMonitorType(2);
            this.memberMonitorPlanRecordService.updateMonitorTaskCard(taskCardPO);
        } else if (type != null && type == 6) {
            //修改自定义随访内容
            FollowCustomContentPO contentPO = new FollowCustomContentPO();
            contentPO.setSid(dto.getSid());
            contentPO.setIsValid(dto.getIsValid());
            contentPO.setMemberId(dto.getMemberId());
            contentPO.setArchivesJson(dto.getArchivesJson());
            contentPO.setAddJson(dto.getAddJson());
            contentPO.setDrugListJson(dto.getDrugListJson());
            contentPO.setFootJson(dto.getFootJson());
            contentPO.setMqzywt(dto.getMqzywt());
            contentPO.setZygjcs(dto.getZygjcs());
            contentPO.setYqddmb(dto.getYqddmb());
            contentPO.setFollowDt(followDt);
            contentPO.setNextDt(StringUtils.isBlank(dto.getNextDt()) ? null : dto.getNextDt());

            this.followMapper.modifyFollowCustomContent(contentPO);
            if (null != dto.getDealStatus() && 1 == dto.getDealStatus()) {
                this.questionnaireService.updateQuestionnaireByFollowId(dto.getSid());
            }
        } else if(type != null && (type == 7 || type == FollowConstant.FOLLOW_TYPE_HYP_JW)){
            //修改2型糖尿病随访表
            FollowDiabetesPO diabetesPO = new FollowDiabetesPO();
            diabetesPO.setSid(dto.getSid());
            diabetesPO.setFollowType(dto.getFollowWay());
            diabetesPO.setIsValid(dto.getIsValid());
            diabetesPO.setFollowDt(dto.getFollowDt());
            diabetesPO.setFollowInfo(dto.getFollowInfo());
            diabetesPO.setMqzywt(dto.getMqzywt());
            diabetesPO.setZygjcs(dto.getZygjcs());
            diabetesPO.setYqddmb(dto.getYqddmb());
            if (!StringUtils.isBlank(dto.getFollowInfo())){
                Map<String, Object> map = JsonSerializer.jsonToMap(dto.getFollowInfo());
                String xltz ;//心理调整
                String zyxw ;//遵医行为
                if (type == 7){
                    //糖尿病
                    xltz  = null == map.get("xltz")?"": map.get("xltz").toString();
                    zyxw = null == map.get("zyxw")?"": map.get("zyxw").toString();
                }else {
                    //高血压
                    xltz  = null == map.get("heartAdjust")?"": map.get("heartAdjust").toString();
                    zyxw = null == map.get("complianceAction")?"": map.get("complianceAction").toString();
                }
                diabetesPO.setXltz(xltz);
                diabetesPO.setZyxw(zyxw);
            }
            this.followMapper.modifyFollowDiabetes(diabetesPO);
        }else {  //随访
            FollowupPO followupPO = new FollowupPO();
            followupPO.setSid(dto.getSid());
            followupPO.setIsValid(dto.getIsValid());
            followupPO.setMemberInfo(dto.getMemberInfo());
            followupPO.setFollowInfo(dto.getFollowInfo());
            followupPO.setDrugListJson(dto.getDrugListJson());
            this.followMapper.modifyFollowup(followupPO);

            if (type != null && (type == 15 || type == 16 || type == 17 )){
                FollowMmcVisitPO followMmcVisit = new FollowMmcVisitPO();
                followMmcVisit.setSid(dto.getSid());
                followMmcVisit.setMemberInfo(dto.getMemberInfo());
                followMmcVisit.setFollowInfo(dto.getFollowInfo2());
                followMmcVisit.setDrugInfo(dto.getDrugInfo());
                followMmcVisit.setDiseasesInfo(dto.getDiseasesInfo());
                followMmcVisit.setOperationInfo(dto.getOperationInfo());
                followMmcVisit.setOtherDrug(dto.getOtherDrug());
                followMmcVisit.setDrinkingInfo(dto.getDrinkingInfo());
                followMmcVisit.setIsValid(dto.getIsValid().toString());
                followMmcVisit.setMemberId(dto.getMemberId());
                followMmcVisit.setFollowType(type.toString());
                followMmcVisit.setDealStatus(dto.getDealStatus());
                this.followMapper.modifyFollowMmcVisit(followMmcVisit);
                Map<String,String> memberinfoMap= JsonSerializer.jsonToStringMap(dto.getMemberInfo());
                MemberArchivesPO memberArchivesPO= this.memberMapper.getMemberArchives(dto.getMemberId(), null);
                MemberPO memberPO = this.memberMapper.getMemberByMemberId(dto.getMemberId());
                if (memberArchivesPO!=null && memberArchivesPO.getArchivesJson()!=null){
                    String archivesJson = memberArchivesPO.getArchivesJson();
                    JSONObject jsonObject = JSONObject.parseObject(archivesJson);
                    JSONObject basic = jsonObject.getJSONObject("basic");
                    if (basic != null){
                        basic.put("member_real_name",memberinfoMap.get("memberRealName"));
                        memberPO.setMemberName(memberinfoMap.get("memberRealName"));
//                        basic.put("sex",memberinfoMap.get("sex"));
//                        if ("男".equals(memberinfoMap.get("sex"))){
//                            memberPO.setSex(1);
//                        }else{
//                            memberPO.setSex(2);
//                        }
                        basic.put("birthday",memberinfoMap.get("birthday"));
                        memberPO.setBirthday(memberinfoMap.get("birthday"));

                    }

                    JSONObject sign = jsonObject.getJSONObject("sign");
                    if (sign != null){
                        sign.put("height",memberinfoMap.get("height"));
                        memberPO.setHeight(memberinfoMap.get("height"));
                        sign.put("weight",memberinfoMap.get("weight"));

                    }
                    memberPO.setWeight(memberinfoMap.get("weight"));
                    memberPO.setMobilePhone(memberinfoMap.get("mobilePhone"));
                    memberPO.setIdCard(memberinfoMap.get("identity"));
                    jsonObject.put("basic",basic);
                    jsonObject.put("sign",sign);
                    archivesJson = JSONObject.toJSONString(jsonObject);
                    memberArchivesPO.setArchivesJson(archivesJson);
                    memberPO.setSbpPressure(memberinfoMap.get("sbpPressure"));
                    memberPO.setDbpPressure(memberinfoMap.get("dbpPressure"));
                }
                if (memberArchivesPO != null){
                    MemberArchivesDTO memberArchivesDTO = new MemberArchivesDTO();
                    BeanUtils.copyProperties(memberArchivesDTO,memberArchivesPO);
                    this.memberService.updateMemberArchive(memberArchivesDTO);
                }
                if (memberPO != null){
                    UpdateMemberDTO memberDTO = new UpdateMemberDTO();
                    BeanUtils.copyProperties(memberDTO,memberPO);
                    this.memberMapper.updateMember(memberDTO);
                }

            }
        }
        //如果有修改下一次随访时间，同步随访配置的下次随访时间
        if (null != mainFollowPO && !StringUtils.isBlank(dto.getNextDt())) {
            MemberFollowSetPO set = this.followMapper.getMemberFollowSetByDoc(dto.getMemberId(), mainFollowPO.getLeaderId(), dto.getFollowType());
            if (null != set) {
                set.setNextDt(dto.getNextDt());
                this.followMapper.modifyMemberFollowSet(set);
            }
        }
        //随访完成
        if (null != dto.getDealStatus() && 1 == dto.getDealStatus()) {
            MemberPO memberPO = this.memberService.getMemberById(dto.getMemberId());
            if (null != memberPO  ){
                if (null != type){
                    if (!"SUGAR_TYPE_003".equals( memberPO.getDiabetesType())){
                        if (type == 1){ //糖尿病首诊报告
                            //参数: 随访id  报告类型  随访类型(华西/普通) 医生id 患者id
                            createReport(dto.getSid(),FollowReportConstant.REPORT_TYPE_FIRST,mainFollowPO.getFollowType(),mainFollowPO.getLeaderId(),mainFollowPO.getMemberId());
                        }else if(type == 2){ //日常随访报告
                            createReport(dto.getSid(),FollowReportConstant.REPORT_TYPE_FOLLOWUP,mainFollowPO.getFollowType(),mainFollowPO.getLeaderId(),mainFollowPO.getMemberId());
                        }else if (null != type && type == 4){ //糖尿病足随访表报告
                            createReport(dto.getSid(),FollowReportConstant.REPORT_TYPE_TNBZ,mainFollowPO.getFollowType(),mainFollowPO.getLeaderId(),mainFollowPO.getMemberId());
                        }else if (null != type && type == 5){ //糖尿病随访表报告
                            createReport(dto.getSid(),FollowReportConstant.REPORT_TYPE_TNB,mainFollowPO.getFollowType(),mainFollowPO.getLeaderId(),mainFollowPO.getMemberId());
                        }else if (type == 7){  //2型糖尿病随访报告
                            //参数: 随访id  报告类型  随访类型(华西/普通) 医生id 患者id
                            createReport(dto.getSid(),FollowReportConstant.REPORT_TYPE_TWO_DIABETES,mainFollowPO.getFollowType(),mainFollowPO.getLeaderId(),mainFollowPO.getMemberId());
                        }
                    }
                    if (type == 10){  //高血压首诊报告
                        //参数: 随访id  报告类型  随访类型(华西/普通) 医生id 患者id
                        createReport(dto.getSid(),FollowReportConstant.REPORT_TYPE_FIRST_GXY,mainFollowPO.getFollowType(),mainFollowPO.getLeaderId(),mainFollowPO.getMemberId());
                    }

                }

            }
            //华西生成随访总结报告
            if (null != mainFollowPO && null != mainFollowPO.getFollowType() && mainFollowPO.getFollowType() == 2){
                createHxFollowZjReport(mainFollowPO);
            }

            //如果是患者小程序创建的随访没有提交医生对象是不进行通知的
            if(doctorPO != null) {
                DoctorPO operatorDoctor = this.doctorService.getDoctorById(operatorDoctorId);
                String doctorName = operatorDoctor == null ? "" : operatorDoctor.getDoctorName();
                //下发随访完成通知
                String name = type == 6 ? dto.getFollowName() : Constant.FOLLOW_NAME_MAP.get(type);
                DialogueFollowReportBO dialogueFollowReportBO = new DialogueFollowReportBO();
                dialogueFollowReportBO.setDoctorName(doctorName);
                dialogueFollowReportBO.setName(name);
                dialogueFollowReportBO.setFollowType(dto.getFollowType());
                dialogueFollowReportBO.setMainProblem(mainProblemHandler(dto));
                dialogueFollowReportBO.setDate(DateHelper.getToday());
                dialogueFollowReportBO.setTime(DateHelper.getDate(new Date(), "HH:mm:ss"));
                dialogueFollowReportBO.setTextType(DialogueConstant.DIALOGUE_FOLLOW_TEXT_TYPE_REPORT);
                dialogueFollowReportBO.setfType(mainFollowPO.getFollowType());
                //15/16/17没与报告,故不生成对话框弹窗.
                if (type != 15 && type != 16 && type != 17) {
                    sendFollowDialogue(dto.getTemplateId(), dto.getMemberId(), doctorPO.getDoctorId(), dto.getSid(), JSON.toJSONString(dialogueFollowReportBO), DIALOGUE_FOLLOW_REPORT_MESSAGE, operatorDoctor.getDoctorId(), type);
                    //添加微信消息
                    addFollowReportWechatMessage(mainFollowPO);
                }
                //修改
                FollowRemindPO followRemindPO = new FollowRemindPO();
                followRemindPO.setFollowId(dto.getSid());
                followRemindPO.setTitle(type + "");
                FollowRemindPO followRemind = getFollowRemind(followRemindPO);
                if (null != followRemind) {
                    followRemind.setIsDo("1");
                    modifyFollowRemind(followRemind);
                }
                //修改问卷状态
                if (type == 6) {
                    this.questionnaireService.updateQuestionnaireByFollowId(dto.getSid());
                }

                //服务扣次
                useFollowService(mainFollowPO.getMemberId(), mainFollowPO.getLeaderId(), ServiceCode.JIAO_YU_ZHE_HUI_FANG);

                //判断是否有用药数据  要保存更新
                if (dto.getSaveDrug() && !StringUtils.isBlank(dto.getDrugListJson()) && !StringUtils.isBlank(mainFollowPO.getLeaderId())) {
                    this.memberService.doDrugItem(mainFollowPO.getLeaderId(),mainFollowPO.getLeaderId(), dto.getMemberId(), dto.getSid(), dto.getDrugListJson(),dto.getOrigin());
                }
                dto.setDoctorId(mainFollowPO.getLeaderId());
                //同步糖化
                //syncHba1c(dto);

                //更新保存患者分层分级评估
                JSONObject jsonObject = JSONObject.parseObject(dto.getArchivesJson());
                String memberId = dto.getMemberId();
                List<Map<String,Object>> list = this.memberService.listMemberNearlyYearArchivesByHid(memberId,doctorPO.getHospitalId());
                MemberDiffLevelSureDTO memberDiffLevelSureDTO = null;
                if(jsonObject!=null && !StringUtils.isBlank(jsonObject.getString("memberDiffLevelSureDTO"))){
                    memberDiffLevelSureDTO = jsonObject.getObject("memberDiffLevelSureDTO",MemberDiffLevelSureDTO.class);
                }
                if(memberDiffLevelSureDTO!=null){
                    for(Map<String,Object> map:list){
                        map.put("memberDiffLevelSureDTO",memberDiffLevelSureDTO);
                    }
                }
                this.differentLevelsService.differentLevelsHandle(list);
            }

            //高血压首诊保存分层分级结果
            if (null != type && 10 == type && !StringUtils.isBlank(dto.getGxyLevelDataJson())){
                addGxyLevelResult(dto.getGxyLevelDataJson(),dto.getMemberId(),dto.getDoctorId(),dto.getHospitalId());
            }
        }
    }

    /**
     * 处理血压的时间点
     * @param time
     * @return
     */
    public String bloodPressure(String time){
        String dt = null;
        String nowTime = DateHelper.getToday()+" "+time;
        Date nowDt = DateHelper.dateTime(nowTime);
        String startTime = DateHelper.getToday()+" 04:01";
        Date startDt = DateHelper.dateTime(startTime);
        String endTime = DateHelper.getToday()+" 09:00";
        Date endDt = DateHelper.dateTime(endTime);
        if (DateHelper.isEffectiveDate(nowDt,startDt,endDt)){
            dt = "9";
        }
        String startTime2 = DateHelper.getToday()+" 09:01";
        String endTime2 = DateHelper.getToday()+" 12:00";
        Date nowDt1 = DateHelper.dateTime(nowTime);
        Date startDt1 = DateHelper.dateTime(startTime2);
        Date endDt1 = DateHelper.dateTime(endTime2);
        if (DateHelper.isEffectiveDate(nowDt1,startDt1,endDt1)){
            dt = "10";
        }

        String startTime3 = DateHelper.getToday()+" 12:01";
        String endTime3 = DateHelper.getToday()+" 18:00";
        Date nowDt2 = DateHelper.dateTime(nowTime);
        Date startDt2 = DateHelper.dateTime(startTime3);
        Date endDt2 = DateHelper.dateTime(endTime3);
        if (DateHelper.isEffectiveDate(nowDt2,startDt2,endDt2)){
            dt = "11";
        }
        String startTime4 = DateHelper.getToday()+" 18:01";
        String today = DateHelper.getToday();
        String endTime4 = DateHelper.plusDate(today,1)+" 04:00";
        Date nowDt3 = DateHelper.dateTime(nowTime);
        Date startDt3 = DateHelper.dateTime(startTime4);
        Date endDt3 = DateHelper.dateTime(endTime4);
        if (DateHelper.isEffectiveDate(nowDt3,startDt3,endDt3)){
            dt = "12";
        }
        return dt;
    }

    /**
     * 添加高血压首诊分层分级数据
     * @param gxyLevelDataJson
     * @param memberId
     * @param doctorId
     * @param hospitalId
     */
    private void addGxyLevelResult(String gxyLevelDataJson,String memberId,String doctorId,String hospitalId){
        LevelFollowDTO dto = JSONObject.parseObject(gxyLevelDataJson, LevelFollowDTO.class);
        MemberLevelPO levelPO = new MemberLevelPO();
        BeanUtils.copyProperties(levelPO,dto);
        levelPO.setHospitalId(hospitalId);
        levelPO.setDoctorId(doctorId);
        levelPO.setMemberId(memberId);
        levelPO.setChangeDt(DateHelper.getNowDate());
        levelPO.setChangeDate(DateHelper.getToday());
        this.memberLevelService.addHypertensionLevel(levelPO);
    }
    private List<Map<String, Object>> getMemberQuesScoreByLevelStr(String levelStr) {
        if(StringUtils.isBlank(levelStr)){
            return null;
        }
        JSONObject levelMap = JSONObject.parseObject(levelStr);
        //患者问卷得分
        List<Map<String, Object>> memberQuesScore = new ArrayList<>();
        String sid1 = levelMap.getString("q1_sid");
        if(!StringUtils.isBlank(sid1)){
            String level1 = levelMap.getString("q1_level");
            String num1 = levelMap.getString("q1_num");
            Map<String,Object> zwglxwpg = new HashMap<>(4);
            zwglxwpg.put("sid",sid1);
            zwglxwpg.put("level",level1);
            zwglxwpg.put("score",num1);
            zwglxwpg.put("type",2);
            memberQuesScore.add(zwglxwpg);
        }
        Map<String,Object> tnbzfxfj = new HashMap<>(4);
        String sid2 = levelMap.getString("q3_sid");
        if(!StringUtils.isBlank(sid1)){
            String level2 = levelMap.getString("q3_level");
            String num2 = levelMap.getString("q3_num");
            tnbzfxfj.put("sid",sid2);
            tnbzfxfj.put("level",level2);
            tnbzfxfj.put("score",num2);
            tnbzfxfj.put("type",4);
        }
        memberQuesScore.add(tnbzfxfj);
        return memberQuesScore;
    }

    /**
     * 生成华西随访总结报告
     */
    private void createHxFollowZjReport(MainFollowPO mainFollowPO){
        FollowReportPO followReportPO = this.followMapper.getFollowReportByFollowId(mainFollowPO.getForeignId(),
        		true,FollowReportConstant.REPORT_HX_FOLLOW_ZJ);
        if (followReportPO != null){
            return;
        }
        String memberId = mainFollowPO.getMemberId();
        String doctorId = mainFollowPO.getLeaderId();
        String startDt = "";
        String nowDt = DateHelper.getNowDate();
        //获取上次随访主表信息
        List<MainFollowPO> mainFollowList = this.followMapper.listMainFollow(memberId, doctorId);
        //第一次随访不生成总结,从第二次随访开始生成总结
        if (null != mainFollowList && mainFollowList.size() > 1){
        	startDt = mainFollowList.get(1).getModifyDt();
            int day = DateHelper.dateCompareGetDay(nowDt, startDt);
            //两次随访间隔日期大于等于7天才生成随访总结报告
            if (day >= 7){

                List<FollowListModel> modelList = this.followMapper.listFollowAndQuestion(memberId, "", doctorId, true, null, null ,null,null,null);
                //第一次随访不生成随访总结报告
                if (null != modelList && modelList.size() > 0){

                    HashMap<String, Object> reportMap = new HashMap<>();
                    MemberPO member = this.memberService.getMemberById(memberId);
                    reportMap.put("member_name",member.getMemberName());
                    reportMap.put("sex",member.getSex());
                    //获取上一次随访总结报告
                    ListFollowReportDTO reportDTO = new ListFollowReportDTO();
                    reportDTO.setMemberId(memberId);
                    reportDTO.setDoctorId(doctorId);
                    reportDTO.setReportType(FollowReportConstant.REPORT_HX_FOLLOW_ZJ);
                    List<FollowReportDTO> reportList = this.followMapper.listFollowReport(reportDTO);
                    Integer reportNum = reportList == null ? 1 : reportList.size() + 1;
                    String lastSumBloodSugar = "";   //上次报告血糖总次数
                    String lastNormalBloodSugar = ""; //上次报告血糖正常次数
                    String lastHighBloodSugar = ""; //上次血糖偏高次数
                    String lastLowBloodSugar = ""; //上次血糖偏低次数
                    if (null != reportList && reportList.size() > 0){
                        FollowReportDTO lastFollowReport = reportList.get(0); //上一次随访总结报告
                        if (!StringUtils.isBlank(lastFollowReport.getReportJson())){
                            Map<String, Object> map = JsonSerializer.jsonToMap(lastFollowReport.getReportJson());
                            lastSumBloodSugar = map.get("sumBloodSugar") == null ? "" : map.get("sumBloodSugar").toString();
                            lastNormalBloodSugar = map.get("normalBloodSugar") == null ? "" : map.get("normalBloodSugar").toString();
                            lastHighBloodSugar = map.get("highBloodSugar") == null ? "" : map.get("highBloodSugar").toString();
                            lastLowBloodSugar = map.get("lowBloodSugar") == null ? "" : map.get("lowBloodSugar").toString();
                        }
                    }
                    reportMap.put("lastSumBloodSugar",lastSumBloodSugar);      //上次报告血糖总次数
                    reportMap.put("lastNormalBloodSugar",lastNormalBloodSugar);  //上次报告血糖正常次数
                    reportMap.put("lastHighBloodSugar",lastHighBloodSugar);  //上次血糖偏高次数
                    reportMap.put("lastLowBloodSugar",lastLowBloodSugar);   //上次血糖偏低次数

                    CountBloodSugarDTO countBloodSugarDTO = new CountBloodSugarDTO();
                    countBloodSugarDTO.setMemberId(memberId);
                    countBloodSugarDTO.setStartDt(startDt);
                    countBloodSugarDTO.setEndDt(nowDt);

                    //获取上次随访到本次随访血糖测量总次数、偏高、正常、偏低
                    Map<String, Object> bloodMap = this.bloodSugarService.loadBloodNumHigLow2(countBloodSugarDTO);
                    reportMap.put("lastFollowDt",startDt.substring(0,10));        //上次随访完成时间
                    reportMap.put("nowFollowDt",nowDt.substring(0,10));           //本次随访完成时间
                    if (null != bloodMap && bloodMap.size() > 0){
                        reportMap.put("sumBloodSugar",bloodMap.get("totalNums"));   //血糖测量总次数
                        reportMap.put("normalBloodSugar",bloodMap.get("nomal")); //血糖测量正常总次数
                        reportMap.put("highBloodSugar",bloodMap.get("high"));   //血糖测量偏高总次数
                        reportMap.put("lowBloodSugar",bloodMap.get("low"));   //血糖测量偏低总次数
                    }

                    // 获取上次随访到本次随访血糖的最高值和最低值
                    Map<String, Object> maxMinMap = this.bloodSugarService.loadBloodAvgMaxMin(countBloodSugarDTO);
                    Integer gwBloodSugar = 0;
                    Integer dwBloodSugar = 0;
                    if (null != maxMinMap && maxMinMap.size() > 0){
                        double max = Double.parseDouble(maxMinMap.get("maxValue1").toString());
                        double min = Double.parseDouble(maxMinMap.get("minValue1").toString());
                        gwBloodSugar = max > 16.7 ? 1 : 0;
                        dwBloodSugar = min <= 3.9 ? 1 : 0;
                    }
                    reportMap.put("gwBloodSugar",gwBloodSugar);  //是否有高危血糖 0 否 1 是
                    reportMap.put("dwBloodSugar",dwBloodSugar);  //是否出现低血糖 0 否 1 是

                    //获取患者的监测方案
                    GetMemberMonitorDTO monitorDTO = new GetMemberMonitorDTO();
                    monitorDTO.setMemberId(memberId);
                    monitorDTO.setInProgress(1);  //是否进行中 1 是 0 不是
                    MemberMonitorPlanPO monitorPlan = this.memberMonitorPlanService.getMemberMonitorPlan(monitorDTO);
                    String planDetail = monitorPlan == null ? "" : monitorPlan.getPlanDetail();
                    reportMap.put("monitorPlan",planDetail);   //患者血糖监测方案

                    //获取患者近一周的血糖监测情况
                    ListBloodSugarDTO bloodSugarDTO = new ListBloodSugarDTO();
                    bloodSugarDTO.setMemberId(memberId);
                    bloodSugarDTO.setCodeDt("3");
                    List<Map<String, Object>> sugarList = this.bloodSugarService.listBloodSugarPage(bloodSugarDTO, null);
                    //患者每天血糖的偏高次数
                    List<Map<String, Object>> evertyDay = this.bloodSugarService.countBloodSugarMaxMin(memberId,startDt,nowDt,null);
                    boolean checkEveryDay = FollowHxZjReportHelper.checkEveryDay(evertyDay);
                    reportMap.put("threeHighDay",checkEveryDay ? 1 : 0); //您有过一天累计出现三次血糖偏高 0 否 1 是
                    List<Map<String, Object>> beforeBreakfast = this.bloodSugarService.countBloodSugarMaxMin(memberId, startDt, nowDt, "beforeBreakfast");
                    reportMap.put("beforeBreakfastHigh",FollowHxZjReportHelper.checkBloodSugar(beforeBreakfast) ? 1 : 0);   //连续三天早餐前血糖偏高 0 否 1是

                    List<Map<String, Object>> beforeLunch = this.bloodSugarService.countBloodSugarMaxMin(memberId, startDt, nowDt, "beforeLunch");
                    reportMap.put("beforeLunchHigh",FollowHxZjReportHelper.checkBloodSugar(beforeLunch) ? 1 : 0);   //连续三天午餐前血糖偏高 0 否 1 是

                    List<Map<String, Object>> beforeDinner = this.bloodSugarService.countBloodSugarMaxMin(memberId, startDt, nowDt, "beforeDinner");
                    reportMap.put("beforeDinnerHigh",FollowHxZjReportHelper.checkBloodSugar(beforeDinner) ? 1 : 0);   //连续三天晚餐前血糖偏高 0 否 1 是

                    List<Map<String, Object>> afterBreakfast = this.bloodSugarService.countBloodSugarMaxMin(memberId, startDt, nowDt, "afterBreakfast");
                    reportMap.put("afterBreakfastHigh",FollowHxZjReportHelper.checkBloodSugar(afterBreakfast) ? 1 : 0);   //连续三天早餐后血糖偏高 0 否 1 是

                    List<Map<String, Object>> afterLunch = this.bloodSugarService.countBloodSugarMaxMin(memberId, startDt, nowDt, "afterLunch");
                    reportMap.put("afterLunchHigh",FollowHxZjReportHelper.checkBloodSugar(afterLunch) ? 1 : 0);   //连续三天午餐后血糖偏高 0 否 1 是

                    List<Map<String, Object>> afterDinner = this.bloodSugarService.countBloodSugarMaxMin(memberId, startDt, nowDt, "afterDinner");
                    reportMap.put("afterDinnerHigh",FollowHxZjReportHelper.checkBloodSugar(afterDinner) ? 1 : 0);   //连续三天晚餐后血糖偏高 0 否 1 是

                    List<Map<String, Object>> beforeSleep = this.bloodSugarService.countBloodSugarMaxMin(memberId, startDt, nowDt, "beforeSleep");
                    reportMap.put("beforeSleepHigh",FollowHxZjReportHelper.checkBloodSugar(beforeSleep) ? 1 : 0);   //连续三天睡前血糖偏高 0 否 1 是
                    FollowReportPO reportPO = new FollowReportPO();
                    String reportId = DaoHelper.getSeq();
                    reportPO.setSid(reportId);
                    reportPO.setFollowId(mainFollowPO.getForeignId());
                    reportPO.setReportType(FollowReportConstant.REPORT_HX_FOLLOW_ZJ);
                    reportPO.setReportJson(JsonSerializer.objectToJson(reportMap));
                    reportPO.setWeekBloodData(JsonSerializer.objectToJson(FollowHxZjReportHelper.dealBloodSugarData(sugarList)));
                    reportPO.setDoctorId(doctorId);
                    reportPO.setMemberId(memberId);
                    reportPO.setReportNum(reportNum);
                    this.followMapper.insertFollowReport(reportPO);
                    //下发随访总结报告
                    DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
                    String doctorName = doctorPO == null ? "" :doctorPO.getDoctorName();
                    DialogueFollowQuestionBO dialogueBO = new DialogueFollowQuestionBO();
                    dialogueBO.setFollowType(0);
                    dialogueBO.setDoctorName(doctorName);
                    dialogueBO.setName("随访总结报告");
                    dialogueBO.setDate(DateHelper.getToday());
                    dialogueBO.setTime(DateHelper.getDate(new Date(), "HH:mm:ss"));
                    dialogueBO.setTextType(DialogueConstant.DIALOGUE_FOLLOW_TEXT_TYPE_ZJ);
                    sendFollowDialogue("", memberId, doctorId, reportId, JSON.toJSONString(dialogueBO), DIALOGUE_FOLLOW_ZJ_REPORT_MESSAGE, doctorId,0);
                    FollowReportPO report = this.followMapper.getFollowReportById(reportId);
                    //添加微信消息
                    addFollowZjReportWechatMessage(report);
                }
            }
        }
    }

    /**
     * 同步糖化血红蛋白
     * @param follow
     */
    private void syncHba1c(FollowDTO follow){
        if(follow == null){
            return;
        }
        String hba = null;
        String dataBody = null;
        Integer followType = follow.getFollowType();
        switch (followType){
            case FollowConstant.FOLLOW_TYPE_TWO_DIABETES:
                dataBody = follow.getFollowInfo();
                if(!StringUtils.isBlank(dataBody)){
                    JSONObject dataJson = JSON.parseObject(dataBody);
                    hba = dataJson.getString("lab_hba");
                }
                break;
            case FollowConstant.FOLLOW_TYPE_FIRST:
            case FollowConstant.FOLLOW_TYPE_FIRST_GXY:
            case FollowConstant.FOLLOW_TYPE_CUSTOM:
                hba = getHbaValue(follow.getArchivesJson());
                break;
            default:
                hba = getHbaValue(follow.getFollowInfo());
                break;
        }
        if(StringUtils.isBlank(hba)){
            return;
        }
        Float hbaValue = null;
        try{
            hbaValue = Float.parseFloat(hba);
        }catch (Exception e){
            return;
        }
        AddHba1cDTO addHba1cDTO = new AddHba1cDTO();
        addHba1cDTO.setMemberId(follow.getMemberId());
        addHba1cDTO.setOperatorId(follow.getDoctorId());
        addHba1cDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
        addHba1cDTO.setOrigin(SignConstant.ORIGIN_FOLLOW);
        addHba1cDTO.setRecordDt(DateHelper.getNowDate());
        addHba1cDTO.setRecordValue(hbaValue);
        this.hba1cService.addHba1c(addHba1cDTO);
    }

    /**
     * 获取糖化值
     * @param dataBody
     * @return
     */
    private String getHbaValue(String dataBody){
        if(StringUtils.isBlank(dataBody)){
            return null;
        }
        JSONObject dataJson = JSON.parseObject(dataBody);
        JSONObject lab = dataJson.getJSONObject("lab");
        if(lab == null){
            return null;
        }
        return lab.getString("lab_hba");
    }


    /**
     *
     * @param baseJson
     * @param reportType 报告类型 1 首诊报告 2 2型糖尿病随访报告
     * @param followType  随访类型 1默认 2华西随访
     */
    private void createReport(String followId,Integer reportType,Integer followType,String doctorId,String memberId){
        FollowReportPO followReportPO = this.followMapper.getFollowReportByFollowId(followId,false,null);
        if(followReportPO != null){
            return;
        }
        String reportJson = ""; //报告json
        //获取患者的控制目标
        RangeBO range = this.memberService.getMemberRange(memberId);
        range.setLowBmi(ControlTargetConstant.BMI_LOW + "");
        range.setHighBmi(ControlTargetConstant.BMI_HIGH + "");
        if (reportType == FollowReportConstant.REPORT_TYPE_FIRST){ //首诊报告
            FollowPO followPO = this.followMapper.getFollowById(followId);
            reportJson = FollowReportHelper.getFirstReportJson(followPO, followType,range);
        }else if (reportType == FollowReportConstant.REPORT_TYPE_TWO_DIABETES){ //2 型糖尿病随访报告
            FollowDiabetesPO diabetesPO = this.followMapper.getFollowDiabetesById(followId);
            reportJson = FollowJwReportHelper.getTwoDiabetesReportJson(diabetesPO,range);
        }else if (reportType == FollowReportConstant.REPORT_TYPE_FOLLOWUP){  //日常随访报告
            FollowupPO followupPO = this.followMapper.getFollowupById(followId);
            reportJson = FollowupReportHelper.getFollowupReportJson(followupPO,range);
        }else if (reportType == FollowReportConstant.REPORT_TYPE_TNB){   //糖尿病随访表报告
            FollowupPO followupPO = this.followMapper.getFollowupById(followId);
            reportJson = FollowTnbReportHelper.getFollowTnbReportReportJson(followupPO,range);
        }else if (reportType == FollowReportConstant.REPORT_TYPE_TNBZ){  //糖尿病足随访表报告
            FollowupPO followupPO = this.followMapper.getFollowupById(followId);
            reportJson = FollowTnbzReportHelper.getFollowTnbReportReportJson(followupPO);
        }else if (reportType == FollowReportConstant.REPORT_TYPE_FIRST_GXY){  //高血压首诊报告
            FollowPO followGxy = this.followMapper.getFollowById(followId);
            MemberArchivesPO archives = this.memberService.getMemberArchives(memberId, doctorId);
            MemberPO memberPO = this.memberService.getMemberById(memberId);
            //判断患者是否有糖尿病并发症
            boolean tnbBfz = FollowGxyReportHelper.checkTnbBfz(archives,memberPO);
            reportJson = FollowGxyReportHelper.getFirstGxyReportJson(followGxy,range);
        }
        FollowReportPO reportPO = new FollowReportPO();
        reportPO.setSid(DaoHelper.getSeq());
        reportPO.setFollowId(followId);
        reportPO.setReportType(reportType);
        reportPO.setReportJson(reportJson);
        reportPO.setDoctorId(doctorId);
        reportPO.setMemberId(memberId);
        reportPO.setReportNum(0);
        this.followMapper.insertFollowReport(reportPO);
    }
    /**
     * 随访服务扣次
     *
     * @param memberId
     * @param doctorId
     */
    private void useFollowService(String memberId, String doctorId, ServiceCode serviceCode) {
        UsePackageServiceDTO usePackageServiceDTO = new UsePackageServiceDTO();
        usePackageServiceDTO.setMemberId(memberId);
        usePackageServiceDTO.setDoctorId(doctorId);
        usePackageServiceDTO.setServiceCode(serviceCode);
        this.packageService.usePackageServiceWithLock(usePackageServiceDTO);
    }

    /**
     * 添加微信消息 - 随访报告下发
     *
     * @param mainFollowPO
     */
    private void addFollowReportWechatMessage(MainFollowPO mainFollowPO) {
        //部分随访不需要下发至患者的
        if(FollowConstant.FOLLOW_TYPE_NJ_DIABETES == mainFollowPO.getType()){
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("followId", mainFollowPO.getForeignId());
        jsonObject.put("memberId", mainFollowPO.getMemberId());
        jsonObject.put("doctorId", mainFollowPO.getLeaderId());
        jsonObject.put("type", mainFollowPO.getType());
        jsonObject.put("date", DateHelper.getNowDate());
        jsonObject.put("followName", mainFollowPO.getFollowName());
        jsonObject.put("templateId", mainFollowPO.getTemplateId());
        jsonObject.put("fType",mainFollowPO.getFollowType());
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_FOLLOW_REPORT);
        addWechatMessageDTO.setMemberId(mainFollowPO.getMemberId());
        addWechatMessageDTO.setForeignId(mainFollowPO.getSid());
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 添加微信消息 - 随访总结报告下发(华西)
     * @param reportPO
     */
    private void addFollowZjReportWechatMessage(FollowReportPO reportPO){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reportId",reportPO.getSid());
        jsonObject.put("date",reportPO.getInsertDt().substring(0,10));
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_FOLLOW_ZJ_REPORT);
        addWechatMessageDTO.setMemberId(reportPO.getMemberId());
        addWechatMessageDTO.setForeignId(reportPO.getSid());
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 主要问题
     *
     * @param dto
     * @return
     */
    private String mainProblemHandler(FollowDTO dto) {
        Integer type = dto.getFollowType();
        String mainProblem = "";
        //首诊
        if (type == 1) {
            mainProblem = subMainProblem(dto.getMqzywt());
        } else if (type == 2) {
            String followInfo = dto.getFollowInfo();
            JSONObject assess = JSON.parseObject(followInfo).getJSONObject("assess");
            if (assess != null) {
                mainProblem = subMainProblem(assess.getString("pg_hzmqczzywt"));
            }
        } else if (type == 3) {
            String followInfo = dto.getFollowInfo();
            JSONObject followObj = JSON.parseObject(followInfo);
            if (followObj != null) {
                mainProblem = subMainProblem(followObj.getString("text"));
            }
        }
        return mainProblem;
    }

    /**
     * 截取主要问题文案
     *
     * @param text
     * @return
     */
    private String subMainProblem(String text) {
        String mainProblem = "";
        if (StringUtils.isBlank(text)) {
            return mainProblem;
        }
        int length = text.length();
        if (length > MAIN_PROBLEM_MAX) {
            mainProblem = text.substring(0, MAIN_PROBLEM_MAX);
        } else {
            mainProblem = text;
        }
        return mainProblem;
    }

    private final static int MAIN_PROBLEM_MAX = 16;

    /**
     * 下发随访对话
     *
     * @param memberId
     * @param doctorId
     * @param followId
     * @param dataStr
     * @param msg
     */
    private void sendFollowDialogue(String templateId, String memberId, String doctorId, String followId, String dataStr, String msg, String senderId ,Integer followType) {
        //南京鼓楼医院糖尿病随访不下发随访对话
        if (FollowConstant.FOLLOW_TYPE_NJ_DIABETES == followType){
            return;
        }
        if(StringUtils.isBlank(senderId)){
            senderId = doctorId;
        }
        CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
        countDoctorMemberDTO.setMemberId(memberId);
        countDoctorMemberDTO.setDoctorId(doctorId);
        long count = this.memberService.countMemberDoctor(countDoctorMemberDTO);
        if(count == 0){
            return;
        }
        DialoguePO dialogueModel = new DialoguePO();
        dialogueModel.setDoctorId(doctorId);
        dialogueModel.setMemberId(memberId);
        dialogueModel.setSenderId(senderId);
        dialogueModel.setDoctorMsg(msg);
        dialogueModel.setPatientMsg(msg);
        dialogueModel.setMsgType(DialogueConstant.DIALOGUE_FOLLOW_MSG_TYPE);
        dialogueModel.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        //部分类型只在医生端展示
        if(FollowConstant.FOLLOW_TYPE_NJ_DIABETES == followType){
            dialogueModel.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_DOCTOR);
        }else{
            dialogueModel.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        }
        dialogueModel.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        dialogueModel.setForeignId(followId);
        dialogueModel.setDataStr(dataStr);
        dialogueModel.setTemplateId(templateId);
        this.dialogueService.addDialogue(dialogueModel);
    }

    @Override
    public PageResult<FollowListModel> listFollowOfPager(String memberId, String leaderId, String doctorId, List<Integer> followType, PageRequest pager, Boolean deal) {
        PageHelper.startPage(pager.getPage(), pager.getRows());
        List<FollowListModel> list = this.listFollow(memberId, leaderId, doctorId, followType, deal);
        if (null != list && list.size() > 0){
            list.forEach(x->{
                FollowReportPO reportPO = this.followMapper.getFollowReportByFollowId(x.getSid(),false,null);
                x.setStatusFlag(reportPO == null ? "0" : "1");
            });
        }
        return new PageResult<FollowListModel>(list);
    }

    @Override
    public PageResult<FollowMemberModel> listFollowMemberOfPage(String leaderId, String doctorId, Integer followType, PageRequest pager) {
        PageHelper.startPage(pager.getPage(), pager.getRows());
        List<String> listMemberId = this.followMapper.listFollowMemberId(leaderId);
        List<FollowMemberModel> list = new ArrayList<FollowMemberModel>(listMemberId.size());
        PageResult<FollowMemberModel> pageResult = new PageResult<FollowMemberModel>(list);
        return pageResult;
    }

    @Override
    public String insertFollowSetWithLock(MemberFollowSetPO set) {
        MemberFollowSetPO oldSet = this.followMapper.getMemberFollowSetByDoc(set.getMemberId(), set.getLeaderId(), set.getFollowType());
        if (oldSet == null) {
            set.setSid(DaoHelper.getSeq());
            if (set.getCycleDt() == null) {
                set.setCycleDt(1);
            }
            this.followMapper.insertMemberFollowSet(set);
        } else {
            set.setSid(oldSet.getSid());
            this.followMapper.modifyMemberFollowSet(set);
        }
        return set.getSid();
    }

    @Override
    public MemberFollowSetPO getMemberFollowSetByDoc(DoctorSessionBO doctor, String memberId, Integer followType) {
        return this.followMapper.getMemberFollowSetByDoc(memberId, doctor.getDoctorId(), followType);
    }

    @Override
    public MemberFollowSetPO getMemberFollowSetByDocNew(String doctorId, String memberId) {
        return this.followMapper.getMemberFollowSetByDocNew(memberId, doctorId);
    }

    @Override
    public PageResult<FollowMemberModel> listOutHosFollowMemberPageByDays(DoctorSessionBO doctor, Integer outDays, Integer endDays, PagerModel pager) {
        String today = DateHelper.getToday();
        String startDt = DateHelper.plusDate(today, (0 - outDays)) + DateHelper.DEFUALT_TIME_START;
        String endDt = DateHelper.plusDate(today, (0 - endDays)) + DateHelper.DEFUALT_TIME_END;
        PageHelper.startPage(pager.getPage(), pager.getRows());
        List<FollowMemberModel> list = null;//this.memberService.listMemberByOutHospitalYM(doctor.getDoctorId(),startDt,endDt);
        PageResult<FollowMemberModel> pageResult = new PageResult<FollowMemberModel>(list);
        return pageResult;
    }

    @Override
    public long countFollow(CountFollowDTO countFollowDTO) {
        return this.followMapper.countFollow(countFollowDTO);
    }

    @Override
    public List<CountMonthFollowPO> countMonthFollow(CountMonthFollowDTO countMonthFollowDTO) {
        return this.followMapper.countMonthFollow(countMonthFollowDTO);
    }

    @Override
    public void modifyFollowSet(MemberFollowSetPO set) {
        this.followMapper.modifyMemberFollowSet(set);
        //如果有修改下一次随访时间，同步最近随访记录的下次随访时间
        if (!StringUtils.isBlank(set.getNextDt()) && !StringUtils.isBlank(set.getLastFollowId())) {
            //修改内容表
            FollowPO po = new FollowPO();
            po.setSid(set.getLastFollowId());
            po.setNextDt(set.getNextDt());
            this.followMapper.modifyFollow(po);
            //修改主表
            MainFollowPO mpo = new MainFollowPO();
            mpo.setNextDt(set.getNextDt());
            mpo.setForeignId(set.getLastFollowId());
            this.followMapper.modifyMainFollowByFid(mpo);
        }
    }

    /**
     * 生成首诊建议
     *
     * @param
     * @return
     */
    @Override
    public Map<String, Object> outFirstFollow(OutFollowDTO followDTO) {
        Map<String, Object> followBodyMap = JsonSerializer.jsonToMap(followDTO.getFollowBody());
        //获取患者的控制目标
        RangeBO range = getMemberRange(followDTO);
        range.setLowBmi("18.5");
        range.setHighBmi("23.9");
        Map<String, Object> reMap = new HashMap<>();
        if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_TWO_DIABETES){  //生成2型糖尿病随访建议
            reMap = FollowJwHelper.outFollowDiabetes(followBodyMap,range);
        }else if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_FIRST){  //生成首诊建议
            reMap = FollowHelper.outFirstFollow(followBodyMap,range);
            Integer eohType = 0; //非妊娠糖尿病
            if ("SUGAR_TYPE_003".equals(followDTO.getDiabetesType())){
                eohType = 1; //妊娠糖尿病
            }
            //首诊下发课程
            KnowledgeVO knowledgeVO = this.followOfEduService.intelligentFollowOfEdu(followDTO.getFollowBody(), followDTO.getMemberId(), range,eohType);
            reMap.put("followFirstKnowledge",knowledgeVO);
        }else if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_DAY){  //生成日常随访建议
            Integer sex = null;
            if (null != followDTO.getComeType() && followDTO.getComeType() == 2){  //华西日常随访
                MemberPO member = this.memberService.getMemberById(followDTO.getMemberId());
                if (null != member){
                    sex = member.getSex();
                }
            }
            reMap = FollowupHelper.outFollowup(followBodyMap,range,sex);
        }else if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_FOLLOW){  //生成糖尿病随访建议
            reMap = FollowTnbHelper.outFollowTnb(followBodyMap,range);
        }else if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_QUES_FOLLOW){  //生成糖尿病足随访建议
            reMap = FollowTnbzHelper.outFollowTnbz(followBodyMap);
        }else if(followDTO.getType() == FollowConstant.FOLLOW_TYPE_HYP_JW){
            HypJwFollowBodyBO hypJwFollowBodyBO = JSON.parseObject(followDTO.getFollowBody() , HypJwFollowBodyBO.class);
            //获取患者控制目标
            RangeBO rangeBO = this.memberService.getMemberRange(hypJwFollowBodyBO.getMemberId());
            reMap = FollowJwHelper.outHypJwFollow(hypJwFollowBodyBO ,rangeBO);
        }else if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_TNB_ASSESS){  //糖尿病风险评估
            reMap = FollowTnbAssessHelper.outFollowTnbAssess(followBodyMap);
        }
        return reMap;
    }

    /**
     * 根据随访信息获取患者控制目标
     * @param followDTO
     * @return
     */
    private RangeBO getMemberRange(OutFollowDTO followDTO){
//        MemberRangeDTO rangeDTO = new MemberRangeDTO();
        RangeBO rangeBO = null;
        MemberPO member = this.memberService.getMemberById(followDTO.getMemberId());
        if (member == null){
            throw new BusinessException("患者不存在");
        }
        if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_QUES_FOLLOW || followDTO.getType() == FollowConstant.FOLLOW_TYPE_FOLLOW){  //糖尿病足随访表,糖尿病随访表
            rangeBO = this.memberService.getMemberRange(followDTO.getMemberId());
        }else{
            MemberRangeDTO rangeDTO = new MemberRangeDTO();
            rangeDTO.setMemberId(followDTO.getMemberId());

            //获取高血压分级
            CurrentGxyLevelDTO c = new CurrentGxyLevelDTO();
            c.setMemberId(followDTO.getMemberId());
            c.setLevelType(1);
            MemberLevelPO memberLevelPO = this.memberLevelService.getCurrentGxyLevel(c);
            if (memberLevelPO != null){
                rangeDTO.setHypLayer(memberLevelPO.getMemberLayer());
            }

            MemberArchivesPO memberArchivesPO = this.memberService.getMemberArchives(followDTO.getMemberId(),followDTO.getDoctorId());
            if(memberArchivesPO != null){
                String archivesJson = memberArchivesPO.getArchivesJson();
                rangeDTO.setCkd(MemberRangeHelper.getCkd(archivesJson));
                rangeDTO.setHypBfz(MemberRangeHelper.getHypBfz(archivesJson));
                rangeDTO.setDiabetesBfz(MemberRangeHelper.getDiabetesBfz(archivesJson,member));
            }

            if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_TWO_DIABETES || followDTO.getType() == FollowConstant.FOLLOW_TYPE_DAY){  //2型糖尿病随访
                rangeDTO.setSex(member.getSex());
                rangeDTO.setChd(member.getChd());
                rangeDTO.setDiabetesType(member.getDiabetesType());
                rangeDTO.setEssentialHyp(member.getEssentialHyp());
                rangeDTO.setBirthday(followDTO.getBirthday());
                rangeDTO.setHeight(followDTO.getHeight());
                if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_TWO_DIABETES){
                    rangeDTO.setIsDiabetes(followDTO.getIsDiabetes());
                }else {
                    rangeDTO.setIsDiabetes(member.getIsDiabetes());
                }

            }else if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_FIRST){  //糖尿病首诊
                rangeDTO.setSex(followDTO.getSex());
                rangeDTO.setChd(followDTO.getChd());
                rangeDTO.setDiabetesType(followDTO.getDiabetesType());
                rangeDTO.setEssentialHyp(followDTO.getEssentialHyp());
                rangeDTO.setBirthday(followDTO.getBirthday());
                rangeDTO.setIsDiabetes(followDTO.getIsDiabetes());
                rangeDTO.setHeight(followDTO.getHeight());

                if (!StringUtils.isBlank(followDTO.getFollowBody())){
                    member.setIsDiabetes(DiabetesConstant.DIABETES_YES);
                    rangeDTO.setDiabetesBfz(MemberRangeHelper.getDiabetesBfz(followDTO.getFollowBody(),member));
                }

            }else if (followDTO.getType() == FollowConstant.FOLLOW_TYPE_QUES_FOLLOW ){ //糖尿病随访表
                rangeDTO.setSex(member.getSex());
                rangeDTO.setChd(member.getChd());
                rangeDTO.setDiabetesType(member.getDiabetesType());
                rangeDTO.setEssentialHyp(member.getEssentialHyp());
                rangeDTO.setBirthday(member.getBirthday());
                rangeDTO.setIsDiabetes(member.getIsDiabetes());
                rangeDTO.setHeight(followDTO.getHeight());
            } else {
                BeanUtils.copyProperties(rangeDTO,followDTO);
                BeanUtils.copyProperties(rangeDTO,member);
            }
            rangeBO = this.memberService.getMemberRangeForFollow(rangeDTO);
        }

        rangeBO.setLowBmi(ControlTargetConstant.BMI_LOW + ""); //BMI下限
        rangeBO.setHighBmi(ControlTargetConstant.BMI_HIGH + ""); //BMI上限
        return rangeBO;
    }

    /**
     * 华西医院患者 分层分级规则
     * @param followBody
     * @return
     */
    @Override
    public Map<String, Object> outHxFollow(String followBody, String levelStr, String memberId, String hospitalId) {
        Map<String, Object> reMap = new HashMap<>(1);
        //患者问卷得分
        if(!StringUtils.isBlank(followBody)){
            List<Map<String, Object>> memberQuesScore = this.getMemberQuesScoreByLevelStr(levelStr);
            JSONObject followBodyMap = JSONObject.parseObject(followBody);
            JSONObject reason = this.differentLevelsService.outHxDifferentLevels(followBodyMap, memberQuesScore);
            //level 1平稳层、2中危层、3高危层
            reMap.put("level",reason.getInteger("layer"));
        }
        return reMap;
    }


    /**
     * 生成行为问卷建议
     * @param followBody
     * @return
     */
    @Override
    public Map<String, Object> outQuesFollow(String followBody, String memberId, String doctorId) {
        String type = "0";
        Map<String, Object> followInfoMap = null;

        List<FollowListModel> fList = this.followMapper.listFollow(memberId, "", doctorId, CollectionUtil.singletonList(3), true);
        if (null != fList && fList.size() > 0) {
            type = "1";//有上一次问卷数据
            FollowListModel followListModel = fList.get(0);
            FollowupPO followup = this.followMapper.getFollowupById(followListModel.getSid());
            if (null != followup) {
                String followInfo = followup.getFollowInfo();
                followInfoMap = JsonSerializer.jsonToMap(followInfo);

            }
        }
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(memberId);
        MemberPO member = memberService.getMember(getMemberDTO);
        List<Map<String, Object>> followBodyMap = JsonSerializer.jsonToMapList(followBody);
        Map<String, Object> reMap = FollowQuesHelper.outQuesFollow(followBodyMap, member, type, followInfoMap);
        return reMap;
    }

    @Override
    public PageResult<FollowRemindVO> listFollowRemindPage(ListFollowRemindDTO listFollowRemindDTO, PageRequest pager) {
        List<String> doctorIdList = this.doctorService.listTeamId(listFollowRemindDTO.getDoctorId());
        listFollowRemindDTO.setDoctorIdList(doctorIdList);
        List<String> typeList = new ArrayList<>();
        if (!StringUtils.isBlank(listFollowRemindDTO.getType())){
            //1 定期随访提醒 2 电话随访提醒 3 随访完成提醒 4 其他提醒
            if (listFollowRemindDTO.getType().equals("1")){ //定期随访提醒
                typeList.add(FollowRemindConstant.REMIND_DQ);
            }else if (listFollowRemindDTO.getType().equals("2")){ //电话随访提醒
                typeList.add(FollowRemindConstant.REMIND_DH);
            }else if (listFollowRemindDTO.getType().equals("3")){ //随访完成提醒
                String[] split = FollowRemindConstant.REMIND_SFWC.split(",");
                for (String s : split) {
                    String [] str = listFollowRemindDTO.getAuthority().split(",");
                    List<String> list = Arrays.asList(str);
                    for (String a : list){
                        if (s.equals(a)){
                            typeList.add(s);
                        }
                    }

                }
            }else if (listFollowRemindDTO.getType().equals("4")){ //其他提醒
                String[] split = FollowRemindConstant.REMIND_OTHER.split(",");
                String [] str = listFollowRemindDTO.getAuthority().split(",");
                List<String> list = Arrays.asList(str);
                for (String s : split) {
                    for (String a : list){
                        if (s.equals(a)){
                            typeList.add(s);
                        }
                    }
                }
            }
            listFollowRemindDTO.setTypeList(typeList);
        }
//        if (listFollowRemindDTO.getType() == null || "".equals(listFollowRemindDTO.getType())){
//            //获取这个医生的权限
//            String [] str = listFollowRemindDTO.getAuthority().split(",");
//            List<String> list = Arrays.asList(str);
//            listFollowRemindDTO.setFollowTypeList(list);
//        }
        PageResult<FollowRemindVO> result = pageFollowRemindHandler(listFollowRemindDTO,pager);
        return result;
    }

    @Override
    public PageResult<FollowRemindVO> pageFollowRemindList(ListFollowRemindDTO listFollowRemindDTO, PageRequest page) {
        PageResult<FollowRemindVO> result = pageFollowRemindHandler(listFollowRemindDTO,page);
        return result;

    }


    private PageResult<FollowRemindVO> pageFollowRemindHandler(ListFollowRemindDTO listFollowRemindDTO, PageRequest pager){
        PageHelper.startPage(pager.getPage(), pager.getRows());
        List<FollowRemindVO> list = this.followMapper.listFollowRemindByParam(listFollowRemindDTO);
        //删除list集合中不是今日的提前提醒
        Iterator<FollowRemindVO> it =list.iterator();
        while (it.hasNext()) {
            FollowRemindVO vo = it.next();
            String insertDt = vo.getInsertDt().substring(0,10)+ " 23:59:59";
            //不为空且类型为12(y天后随访提醒),且当前时间大于提醒时间.
            if (!StringUtils.isBlank(vo.getType()) && "12".equals(vo.getType()) && DateHelper.compareNow(insertDt)) {
                it.remove();
            }
        }
        PageResult<FollowRemindVO> rePage = new PageResult<FollowRemindVO>(list);
        List<FollowRemindVO> rows = rePage.getRows();
        if (null != rows && rows.size() > 0) {
            for (int i = 0; i < rows.size(); i++) {
                FollowRemindVO followRemindVO = rows.get(i);
                DoctorPO doctor = doctorService.getDoctorById(followRemindVO.getDoctorId());
                if (null != doctor) {
                    followRemindVO.setDoctorName(doctor.getDoctorName());
                }
                //获取当前患者所在医院的科室
                MemberCheckinInfoPO po = this.memberCheckinInfoMapper.getMemberCheckinInfoByMemberId(followRemindVO.getMemberId(),listFollowRemindDTO.getHospitalId());
                if (po != null && !"".equals(po)){
                    followRemindVO.setDepartmentId(po.getDepartmentId());
                    followRemindVO.setInHos(1);
                }else {
                    followRemindVO.setInHos(0);
                }

            }
        }
        return rePage;
    }

    @Override
    public void insertFollowRemind(FollowRemindPO followRemindPO) {
        if (!StringUtils.isBlank(followRemindPO.getFollowId()) && !"-1".equals(followRemindPO.getFollowId()) && !"6".equals(followRemindPO.getType())){
            FollowRemindPO po = this.followMapper.getFollowRemind(followRemindPO);
            if(po==null){
                followRemindPO.setId(DaoHelper.getSeq());
                this.followMapper.insertFollowRemind(followRemindPO);
            }
        }else{
            followRemindPO.setId(DaoHelper.getSeq());
            this.followMapper.insertFollowRemind(followRemindPO);
        }

    }

    @Override
    public void modifyFollowRemind(FollowRemindPO followRemindPO) {
        this.followMapper.modifyFollowRemind(followRemindPO);
    }

    @Override
    public long countFollowRemind(ListFollowRemindDTO listFollowRemindDTO) {

        List<String> doctorIdList = this.doctorService.listTeamId(listFollowRemindDTO.getDoctorId());
        listFollowRemindDTO.setDoctorIdList(doctorIdList);
        return this.followMapper.countFollowRemind(listFollowRemindDTO);
    }

    @Override
    public FollowRemindPO getFollowRemind(FollowRemindPO followRemindPO) {
        return this.followMapper.getFollowRemind(followRemindPO);
    }

    @Override
    public List<String> listMemberRevisitDoctor(String memberId, Integer advanceDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, advanceDays);
        String nextDt = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()).concat(" 00:00:00");
        return this.followMapper.listMemberRevisitDoctor(memberId, nextDt);
    }

    @Override
    public List<String> listHasFirstFollowMember(List<String> memberList) {
        return this.followMapper.listHasFirstFollowMember(memberList, FollowConstant.FOLLOW_TYPE_FIRST);
    }

    @Override
    public Object getFollowQuesNewByType(String memberId, String doctorId, Integer followType) {
        Object follow = null;
        List<FollowListModel> list = listFollow(memberId, null, doctorId, CollectionUtil.singletonList(followType), true);
        if (null != list && list.size() > 0) {
            FollowListModel followListModel = list.get(0);
            follow = getFollowById(followListModel.getSid(), followType);
        }
        return follow;
    }

    @Override
    public long countNewFollow(SynthesizeDataDTO synthesizeDataDTO) {
        return this.followMapper.countNewFollow(synthesizeDataDTO);
    }

    @Override
    public String saveFollowCustomerTemplate(AddFollowCustomTemplateDTO templateDTO){
        if (StringUtils.isBlank(templateDTO.getSid())) {  //保存
            String followCustomerTemplateName = templateDTO.getFollowName();
            FollowCustomerTemplatePO po = this.followMapper.getTemplateByName(followCustomerTemplateName);
            if(po!=null){
                return "1001";
            }
            FollowCustomerTemplatePO templatePO = new FollowCustomerTemplatePO();
            String sid = DaoHelper.getSeq();
            BeanUtils.copyProperties(templatePO, templateDTO);
            templatePO.setSid(sid);
            this.followMapper.addFollowCustomerTemplate(templatePO);
            return sid;
        } else {   //修改
            FollowCustomerTemplatePO templatePO = new FollowCustomerTemplatePO();
            BeanUtils.copyProperties(templatePO, templateDTO);
            this.followMapper.modifyFollowCustomerTemplate(templatePO);
            return templateDTO.getSid();
        }

    }

    @Override
    public FollowCustomerTemplatePO getTemplateById(String sid) {
        return this.followMapper.getTemplateById(sid);
    }

    @Override
    public List<FollowCustomerTemplatePO> listFollowCustomerTemplate(String doctorId, String hospitalId) {
        List<FollowCustomerTemplatePO> result = new ArrayList<>();
        //加载权限为个人的模板
        List<FollowCustomerTemplatePO> list1 = this.followMapper.listFollowCustomerTemplate(doctorId, null, "2");
        //加载权限为全院的模板
        List<FollowCustomerTemplatePO> list2 = this.followMapper.listFollowCustomerTemplate(null, hospitalId, "1");
        result.addAll(list1);
        result.addAll(list2);
        return result;
    }

    @Override
    public Map<String, Object> getCustomerEchoInfo(String doctorId, String memberId, String followId) {
        //获取档案信息
        Map<String, Object> map = this.memberService.getMemberArchivesByMemberId(memberId, doctorId,"",true);
        //获取足相关信息
        ListFootDTO dto = new ListFootDTO();
        dto.setDoctorId(doctorId);
        dto.setMemberId(memberId);
        dto.setSaveType("1");
        FootPO foot = this.footService.getFootNew(dto);
        if (foot != null) {
            Map<String, Object> footJson = new HashMap<>();
            footJson.put("assessItem", foot.getAssessItem());
            String check = foot.getAssistCheck();
            if (!StringUtils.isBlank(check)) {
                Map<String, Object> map1 = JsonSerializer.jsonToMap(check);
                if (map1 != null && map1.size() > 0) {
                    if (map1.get("yz_ct") != null) {
                        footJson.put("yz_ct", map1.get("yz_ct"));   //降钙素原
                    }
                    if (map1.get("urine_alb") != null) {
                        footJson.put("urine_alb", map1.get("urine_alb"));   //尿ACR
                    }
                    if (map1.get("yz_hs_crp") != null) {
                        footJson.put("yz_hs_crp", map1.get("yz_hs_crp"));   //CRP（C反应蛋白
                    }
                    if (map1.get("byx_zbfmw") != null) {
                        footJson.put("byx_zbfmw", map1.get("byx_zbfmw"));   //足部分泌物
                    }
                    if (map1.get("byx_xpy") != null) {
                        footJson.put("byx_xpy", map1.get("byx_xpy"));   //血培养
                    }
                    if (map1.get("byx_ym") != null) {
                        footJson.put("byx_ym", map1.get("byx_ym"));   //药敏
                    }
                }
            }
            map.put("footJson", footJson);
        }
        //获取最新糖尿病足数据
        FollowupPO followFoot = (FollowupPO) this.getFollowQuesNewByType(memberId, doctorId, 4);
        if (followFoot != null && !StringUtils.isBlank(followFoot.getFollowInfo())) {
            map.put("followFoot", followFoot.getFollowInfo());
        }
        //获取最新日常随访表数据
        FollowupPO followDayModel = (FollowupPO) this.getFollowQuesNewByType(memberId, doctorId, 2);
        if (followDayModel != null && !StringUtils.isBlank(followDayModel.getFollowInfo())) {
            map.put("followUp", followDayModel.getFollowInfo());
        }
        //获取最新首诊信息
        FollowPO follow = (FollowPO) this.getFollowQuesNewByType(memberId, doctorId, 1);
        if (follow != null && !StringUtils.isBlank(follow.getArchivesJson())) {
            map.put("follow", follow.getArchivesJson());
        }
        return map;
    }

    @Override
    public MainFollowPO getMainFollowByFidAndType(String foreignId, Integer type) {
        return this.followMapper.getMainFollowByFidAndType(foreignId,type);
    }

    @Override
    public PageResult<FollowMemberModel> listMemberFollowSet(int page,int size) {
        PageHelper.startPage(page,size);
        List<FollowMemberModel> list = this.followMapper.listMemberFollowSet();
        return new PageResult<FollowMemberModel>(list);
    }

    @Override
    public void ignoreFollowRemind(String doctorId) {
        List<String> doctorIdList = this.doctorService.listTeamId(doctorId);
        ListFollowRemindDTO dto = new ListFollowRemindDTO();
        dto.setDoctorIdList(doctorIdList);
        dto.setType("12");
        List<FollowRemindPO> list = this.followMapper.listFollowRemind(dto);
        if (list != null && list.size()>0){
            for (FollowRemindPO followRemindPO : list) {
                String insertDt = followRemindPO.getInsertDt();
                String today = DateHelper.getToday();
                int day = DateHelper.dateCompareGetDay(today,insertDt);
                String beforeDayStr = followRemindPO.getBeforeDay();
                int beforeDay = Integer.valueOf(beforeDayStr);
                if (day >= beforeDay){
                    FollowRemindPO po = new FollowRemindPO();
                    po.setId(followRemindPO.getId());
                    po.setIsDo("1");
                    this.followMapper.modifyFollowRemind(po);
                }
            }
        }
    }

    @Override
    public List<CountMonthFollowPO> countMonthFollow(GetStatisticsDTO dto) {
        return this.followMapper.countMonthFollowForHos(dto);
    }

    @Override
    public long countFollow(GetStatisticsDTO getStatisticsDTO) {
        return this.followMapper.countFollowForHos(getStatisticsDTO);
    }

    @Override
    public PageResult<FollowListModel> listFollowAndQuestion(String flag, String memberId, String memberName, String doctorId, Integer type, PageRequest pager, Boolean deal ,String operatorId,String hospitalId,String authority) {
        String [] tag = authority.split(",");
        List<String> authorityList = Arrays.asList(tag);
        Integer followType = null;
        Integer questionType = null;
        if(!StringUtils.isBlank(flag)){
            if("follow".equals(flag)){
                followType = type;
            } else if("question".equals(flag)){
                questionType = type;
            }
        }
        PageHelper.startPage(pager.getPage(),pager.getRows());
        List<FollowListModel> list = null;
        //if (!StringUtils.isBlank(hospitalId)){  //有切换医院权限
            list = this.followMapper.listFollowAndQuestion(memberId, memberName, null, deal, followType, questionType ,operatorId,hospitalId,authorityList);
        /*}else {
            list = this.followMapper.listFollowAndQuestion(memberId, memberName, doctorId, deal, followType, questionType ,operatorId,null);
        }*/
        if (null != list && list.size() > 0){
            list.forEach(x->{
                FollowReportPO reportPO = this.followMapper.getFollowReportByFollowId(x.getSid(),false,null);
                x.setStatusFlag(reportPO == null ? "0" : "1");
            });
        }
        return new PageResult<>(list);
    }

    @Override
    public PageResult<FollowCustomerTemplatePO> listOperateFollowTemplate(PageRequest page,String doctorId,String keyword) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<String> doctorList = new ArrayList<>();
        doctorList.add(doctorId);
        List<FollowCustomerTemplatePO> pos = this.followMapper.listFollowTemplateByPerson(doctorList,keyword);
        PageResult<FollowCustomerTemplatePO> rePage = new PageResult<FollowCustomerTemplatePO>(pos);
        List<FollowCustomerTemplatePO> rows = rePage.getRows();
        if (null != rows && rows.size() > 0) {
            for (int i = 0; i < rows.size(); i++) {
                FollowCustomerTemplatePO po = rows.get(i);
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
    public void deleteFollowTemplate(String ids) {
        String[] split = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            list.add(s);
        }
        DeleteFollowCustomDTO dto = new DeleteFollowCustomDTO();
        dto.setIds(list);
        this.followMapper.deleteFollowTemplate(dto);
    }

    ///////

    @Override
    public FollowDiabetesPO getNewFollowDiabetes(String memberId, String doctorId ,Integer followType) {
        return this.followMapper.getNewFollowDiabetes(memberId ,doctorId ,followType);
    }

    @Override
    public FollowReportPO getFollowReportByFollowId(String followId) {
        return this.followMapper.getFollowReportByFollowId(followId,false,null);
    }

    @Override
    public PageResult<FollowReportDTO> listFollowReport(ListFollowReportDTO followReportDTO,PageRequest page) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<FollowReportDTO> poList = this.followMapper.listFollowReport(followReportDTO);
        return new PageResult<FollowReportDTO>(poList);
    }

    @Override
    public Map<String, Object> outFirstGxyFollow(String dataJson) {
        Map<String, Object> map = JsonSerializer.jsonToMap(dataJson);
        Map<String, Object> resultMap = FollowFirstGxyHelper.outFollowFirstGxy(map);
        return resultMap;
    }

    @Override
    public FollowReportPO getFollowReportById(String sid) {
        return this.followMapper.getFollowReportById(sid);
    }

    private static final String DIALOGUE_FOLLOW_REPORT_MESSAGE = "评估报告生成提醒";
    private static final String DIALOGUE_FOLLOW_QUESTION_MESSAGE = "随访问卷下发通知";
    private static final String DIALOGUE_FOLLOW_ZJ_REPORT_MESSAGE = "随访总结报告生成提醒";


    /**
     * 风险评估数值
     * @param memberInfo
     * @param followInfo
     */

	public Map getHealthAccessData(String memberInfo , String followInfo) {
		int fx_score = 0 ;
		int zz_score = 0 ;

		if(!StringUtils.isBlank(followInfo)) {
			Map<String,Object> member = JSON.parseObject(followInfo);

			String height = member.get("height") == null ? "0":member.get("height").toString();
			String weight = member.get("weight") == null ? "0":member.get("weight").toString();
			String birthday = member.get("birthday") == null ? "":member.get("birthday").toString();
			String ques9 = member.get("ques9") == null ? "":member.get("ques9").toString();//近亲是否糖尿病
			String ques3 = member.get("ques3") == null ? "":member.get("ques3").toString();//您多久运动一次
			String ques4 = member.get("ques4") == null ? "":member.get("ques4").toString();//每次运动多长时间
			String ques7 = member.get("ques7") == null ? "":member.get("ques7").toString();//您平常是否吸烟
			String ques6 = member.get("ques6") == null ? "":member.get("ques6").toString();//饮酒量
			String ques1 = member.get("ques1") == null ? "":member.get("ques1").toString();//高血压
			String ques28 = member.get("ques28") == null ? "":member.get("ques28").toString();//胆固醇

			//zz
			String ques14 = member.get("ques14") == null ? "":member.get("ques14").toString();//口渴情况
			String ques16 = member.get("ques16") == null ? "":member.get("ques16").toString();//疲劳
			String ques15 = member.get("ques15") == null ? "":member.get("ques15").toString();//排尿
			String ques21 = member.get("ques21") == null ? "":member.get("ques21").toString();//眼部症状
			String ques13 = member.get("ques13") == null ? "":member.get("ques13").toString();//口渴情况
			String ques17 = member.get("ques17") == null ? "":member.get("ques17").toString();//手足
			String ques18 = member.get("ques18") == null ? "":member.get("ques18").toString();//皮肤、牙龈

			String bmi = "";
			if(height.equals("0")) {
				bmi = "0";
			}else {
				bmi = ParamLogConstant.getMemberBmi(Double.parseDouble(height), Double.parseDouble(weight));
			}

			if(Double.parseDouble(bmi)>24 && Double.parseDouble(bmi)<28) {
				fx_score += 1;
			}
			if(Double.parseDouble(bmi)>28) {
				fx_score += 2;
			}

			if(!StringUtils.isBlank(birthday)) {
				int age = DateHelper.getAge(birthday);
				if(age<50 && age>39) {
					fx_score += 1;
				}
				if(age>=50 && age<60) {
					fx_score += 2;
				}
				if(age>=60) {
					fx_score += 3;
				}
			}
			if(ques9.equals("1")) {
				fx_score += 1;
			}
			if(ques3.equals("1")) {
				fx_score += 1;
			}
			if((ques3.equals("2")||ques3.equals("3"))&&(ques4.equals("1")||ques4.equals("2")||ques4.equals("4"))) {
				fx_score += 1;
			}
			if(ques7.equals("4")) {
				fx_score += 1;
			}
			if(ques7.equals("5")||ques7.equals("6")) {
				fx_score += 2;
			}
			if(ques6.equals("4")) {
				fx_score += 1;
			}
			if(ques6.equals("5")) {
				fx_score += 2;
			}
			if(ques1.equals("1")) {
				fx_score += 1;
			}
			if(ques28.equals("1")) {
				fx_score += 1;
			}


			if(ques14.equals("2")) {
				zz_score += 1;
			}
			if(ques14.equals("3")) {
				zz_score += 2;
			}
			if(ques16.equals("2")) {
				zz_score += 1;
			}
			if(ques16.equals("3")) {
				zz_score += 2;
			}
			if(ques15.equals("2")) {
				zz_score += 1;
			}
			if(ques15.equals("3")) {
				zz_score += 2;
			}
			if(ques21.equals("1")) {
				zz_score += 2;
			}
			if(ques13.equals("5")) {
				zz_score += 1;
			}
			if(ques17.equals("2")) {
				zz_score += 1;
			}

			if(ques21.equals("1")) {
				zz_score += 2;
			}

		}

		if(!StringUtils.isBlank(memberInfo)) {
			Map<String,Object> member = JSON.parseObject(memberInfo);
			String diabetesType = "0";
			if(!StringUtils.isBlank(member.get("diabetesType").toString())) {
				diabetesType = member.get("diabetesType").toString();
			}

			String kf = "0";
			if(!StringUtils.isBlank(member.get("kf").toString())) {
				kf = member.get("kf").toString();
			}

			if(diabetesType.equals("3")) {
				fx_score += 1;
			}
			if(Float.parseFloat(kf)>6.11) {
				zz_score += 3;
			}
		}

		int level = 0;
		String suggest = "";
		if(fx_score<1 && zz_score <1) {
			level = 1;
			suggest = "您患糖尿病的风险很低。评估结果显示您没有糖尿病相关的症状。请继续保持健康的生活方式;";
		}
		if(fx_score<1 && (zz_score >=1 && zz_score <4)) {
			level = 2;
			suggest = "您患糖尿病的风险较低。评估结果显示您有少量相关的糖尿病症状。建议您带上您的评估结果去咨询您的医生并做糖尿病筛查。跟您的医生预约做空腹血糖测试，空腹血糖测试是诊断糖尿病的最佳方法";
		}
		if(fx_score<1 && zz_score >=4) {
			level = 3;
			suggest = "您患糖尿病的风险很低，但评估结果显示您仍然有发展成为糖尿病患者的可能。请立即带上您的评估结果去咨询您的医生。跟您的医生预约做空腹血糖测试，空腹血糖测试是诊断糖尿病的最佳方法";
		}
		if((fx_score>=1&&fx_score<5) && zz_score <1) {
			level = 4;
			suggest = "您存在一些患糖尿病的风险，但评估结果表明您未患糖尿病。请带上您的评估结果向医生咨询并做糖尿病筛查。跟您的医生预约做空腹血糖测试，空腹血糖测试是诊断糖尿病的最佳方法";
		}
		if((fx_score>=1&&fx_score<5) && (zz_score >=1 && zz_score <4)) {
			level = 5;
			suggest = "您存在一些患糖尿病的风险，评估结果显示您有有一些糖尿病相关症状。请带上您的评估结果去向医生咨询并做糖尿病筛查。跟您的医生预约做空腹血糖测试，空腹血糖测试是诊断糖尿病的最佳方法";
		}
		if((fx_score>=1&&fx_score<5) && zz_score >=4) {
			level = 6;
			suggest = "您存在一些患糖尿病的风险，评估结果显示您发展成为糖尿病患者的可能性很大。请立即带上您的评估结果去向医生咨询。跟您的医生预约做空腹血糖测试，空腹血糖测试是诊断糖尿病的最佳方法";
		}
		if(fx_score>=5 && zz_score <1) {
			level = 7;
			suggest = "您存在一些患糖尿病的风险，评估结果显示您有有一些糖尿病相关症状。请带上您的评估结果去向医生咨询并做糖尿病筛查。跟您的医生预约做空腹血糖测试，空腹血糖测试是诊断糖尿病的最佳方法";
		}
		if(fx_score>=5 && (zz_score >=1 && zz_score <4)) {
			level = 8;
			suggest = "您存在一些患糖尿病的风险，评估结果显示您有有一些糖尿病相关症状。请带上您的评估结果去向医生咨询并做糖尿病筛查。跟您的医生预约做空腹血糖测试，空腹血糖测试是诊断糖尿病的最佳方法";
		}
		if(fx_score>=5 && zz_score >=4) {
			level = 9;
			suggest = "您存在一些患糖尿病的风险，评估结果显示您发展成为糖尿病患者的可能性很大。请立即带上您的评估结果去向医生咨询。跟您的医生预约做空腹血糖测试，空腹血糖测试是诊断糖尿病的最佳方法";
		}
		Map result = new HashMap();
		result.put("level", level);
		result.put("suggest", suggest);
		return result;
	}

	@Override
	public Map getHealthAccessResult(String memberInfo , String followInfo) {
		int fx_score = 0 ;
		int zz_score = 0 ;
		int mqqk_i = 1;
		int suges_i = 1;
		int target_i = 1;
		String now_ques = "";
		String gs_suggest = "";
		String yq_target = "";

		String diabetesTime = "";
		String diabetesType = "";
		String kf = "";
		String hemoglobin = "";
		String sbp = "";
		String dbp = "";
		String cholesterol = "";
		String highCholesterol = "";
		String lowCholesterol = "";
		String sex = "";
		String xjg = "";
		String nwlbdb = "";
		if(!StringUtils.isBlank(memberInfo)) {
			Map<String,Object> member = JSON.parseObject(memberInfo);

			diabetesTime = member.get("diabetesTime") == null ? "":member.get("diabetesTime").toString();
		    diabetesType = member.get("diabetesType") == null ? "":member.get("diabetesType").toString();
			String height = member.get("height") == null ? "0":member.get("height").toString();
			String weight = member.get("weight") == null ? "0":member.get("weight").toString();
			String birthday = member.get("birthday") == null ? "":member.get("birthday").toString();
			kf = member.get("kf") == null ? "":member.get("kf").toString();
			hemoglobin = member.get("hba1c") == null ? "":member.get("hba1c").toString();
			sbp = member.get("sbpPressure") == null ? "":member.get("sbpPressure").toString();
			dbp = member.get("dbpPressure") == null ? "":member.get("dbpPressure").toString();
			cholesterol = member.get("dgc") == null ? "":member.get("dgc").toString();
			highCholesterol = member.get("highCholesterol") == null ? "":member.get("highCholesterol").toString();
			lowCholesterol = member.get("lowCholesterol") == null ? "":member.get("lowCholesterol").toString();
			sex = member.get("sex") == null ? "":member.get("sex").toString();
			xjg = member.get("xjg") == null ? "":member.get("xjg").toString();
			nwlbdb = member.get("nwlbdb") == null ? "":member.get("nwlbdb").toString();
		}

		if(!StringUtils.isBlank(followInfo)) {
			Map<String,Object> follow = JSON.parseObject(followInfo);

			String ques13 = follow.get("ques13") == null ? "":follow.get("ques13").toString();//尿病症状

			String[] temp = ques13.split("-");
			String quesStr = "";
			for (String s : temp) {
				if(s.equals("1")) {
					quesStr += "口舌干燥,";
				}
				if(s.equals("2")) {
					quesStr += "口渴,";
				}
				if(s.equals("3")) {
					quesStr += "多尿 ,";
				}
				if(s.equals("4")) {
					quesStr += "体重减轻 ,";
				}
				if(s.equals("5")) {
					quesStr += "经常性感染,";
				}
				if(s.equals("6")) {
					quesStr += "性功能障碍 ,";
				}
				if(s.equals("7")) {
					quesStr += "伤口愈合缓慢,";
				}
				if(s.equals("8")) {
					quesStr += "生殖器瘙痒或感染,";
				}
				if(s.equals("9")) {
					quesStr += "周身瘙痒,";
				}
				if(s.equals("10")) {

					quesStr += "神经源性疼痛,";
				}
			}
			if(!quesStr.equals("")) {
				quesStr = quesStr.substring(0,quesStr.length()-1);
			}
			if(!ques13.equals("11")) {//选的不是*没有上述症状
				yq_target += String.valueOf(target_i)+"." + "“"+quesStr+" ”等症状逐渐改善;\n";//ques13
				if(temp.length<=4) {
					now_ques += String.valueOf(mqqk_i)+"." + "您的测试结果显示您出现了一些糖尿病症状;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "请带上这份测试结果前去向医生咨询该如何加强糖尿病控制;\n";
					mqqk_i++;
					target_i++;
				}else {
					now_ques += String.valueOf(mqqk_i)+"." + "您的测试结果显示病情控制的非常不好，有可能出现糖尿病并发症;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "请尽快带上这份测试结果前去向医生咨询该如何加强糖尿病控制;\n";
					mqqk_i++;
					target_i++;
				}
			}

			if(diabetesTime.equals("")) {
				now_ques += String.valueOf(mqqk_i)+"." + "如果患上糖尿病，容易引发其他相关的急慢性并发症，并发症将大大影响您的生活质量。如果您及时到糖尿病医生处检查治疗，能够有效预防许多并发症的发生;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "知道您是否患糖尿病是很重要的,建议进一步检查，明确诊断;\n";
				yq_target += String.valueOf(target_i)+"." + "“明确诊断，进行个体化治疗;\n";
				mqqk_i++;

				target_i++;
			}

			String ques20 = follow.get("ques20") == null ? "":follow.get("ques20").toString();//近亲是否糖尿病
			if(!diabetesType.equals("")||!diabetesTime.equals("")) {
				if(ques20.equals("3")) {
					now_ques += String.valueOf(mqqk_i)+"." + "您已确诊患有糖尿病，但是您没有得到医生的监管。当您患了糖尿病之后，您很容易患上很多并发症，那会对您生活质量造成极大的影响;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "健康的生活方式和定期到内分泌科医生处做检查能够预防许多并发症，请尽快去看内分泌科医生;\n";
					yq_target += String.valueOf(target_i)+"." + "及时专科诊治;\n";
					mqqk_i++;

					target_i++;
				}
			}

			String ques7 = follow.get("ques7") == null ? "":follow.get("ques7").toString();//您平常是否吸烟
			if(ques7.equals("3")) {
				now_ques += String.valueOf(mqqk_i)+"." + "您戒烟了，这很好，但请确保您不要再次吸烟;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "很多人之所以无法戒烟是因为他们在戒烟后又忍不住再次吸烟。所以如果您很想吸烟，请克制住，不要因为一次吸烟而放弃了您之前为戒烟所做的努力;\n";
				yq_target += String.valueOf(target_i)+"." + "戒烟;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques7.equals("4")||ques7.equals("5")||ques7.equals("6")) {
				now_ques += String.valueOf(mqqk_i)+"." + "戒烟非常重要，尤其对您身体状况而言，因为吸烟将严重影响您的健康状况;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "您应该戒烟，您可以自己戒，但如果获得支持，您戒烟成功的概率会更大。您可以向您的教练或医生请教有效的戒烟方法;\n";
				yq_target += String.valueOf(target_i)+"." + "戒烟;\n";
				mqqk_i++;

				target_i++;
			}

			String ques6 = follow.get("ques6") == null ? "":follow.get("ques6").toString();//饮酒量
			if(ques6.equals("3")) {
				now_ques += String.valueOf(mqqk_i)+"." + "您的饮酒量有点大;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "每天最多饮1杯酒;\n";
				yq_target += String.valueOf(target_i)+"." + "限酒;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques6.equals("4")) {
				now_ques += String.valueOf(mqqk_i)+"." + "您的饮酒量太大了。这对您的健康有害;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "每天饮酒量控制在1杯以内，并慢慢品尝，如果您需要帮助，建议您可以咨询您的糖尿病教育者或医生;\n";
				yq_target += String.valueOf(target_i)+"." + "限酒;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques6.equals("5")) {
				now_ques += String.valueOf(mqqk_i)+"." + "您的饮酒量将直接威胁您的健康;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "您应该立即戒掉过量饮酒的习惯。如果您需要帮助，请向糖尿病教育者或医生咨询;\n";
				yq_target += String.valueOf(target_i)+"." + "限酒;\n";
				mqqk_i++;

				target_i++;
			}

			String ques3 = follow.get("ques3") == null ? "":follow.get("ques3").toString();//您多久运动一次
			String ques4 = follow.get("ques4") == null ? "":follow.get("ques4").toString();//每次运动多长时间
			if(ques3.equals("2")&&(ques4.equals("2"))) {
				now_ques += String.valueOf(mqqk_i)+"." + "您运动量还不够，请根据您自身情况延长运动时间至每周至少5次，每次至少30分钟;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "运动是保持健康的最佳方法之一，而且运动能阻止糖尿病的进一步发展。医生指南建议您每周至少运动5次，每次至少30分钟;\n";
				yq_target += String.valueOf(target_i)+"." + "保持科学规律的运动习惯;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques3.equals("2")&&ques4.equals("3")) {
				now_ques += String.valueOf(mqqk_i)+"." + "您运动不大科学，请根据您自身情况保持每周运动至少5次，每次的运动时长在30-60分钟之间;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "运动是保持健康的最佳方法之一，而且运动能阻止糖尿病的进一步发展。医生指南建议您每周至少运动5次，每次至少30分钟，每周运动总时长至少150分钟;\n";
				yq_target += String.valueOf(target_i)+"." + "保持科学规律的运动习惯;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques3.equals("2")&&ques4.equals("5")) {
				now_ques += String.valueOf(mqqk_i)+"." + "您每次运动时长够了，但是每周的运动频次偏少，请根据您自身情况延长每次的运动次数到至少5次;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "运动是保持健康的最佳方法之一，请坚持保持科学的运动方式。医生指南建议您每周至少运动5次，每次至少30分钟;\n";
				yq_target += String.valueOf(target_i)+"." + "保持科学规律的运动习惯;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques3.equals("3")&&(ques4.equals("2"))) {
				now_ques += String.valueOf(mqqk_i)+"." + "您运动量还不够，请根据您自身情况延长运动至每周至少5次，每次至少30分钟;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "运动是保持健康的最佳方法之一，而且运动能阻止糖尿病的进一步发展。医生指南建议您每周至少运动5次，每次至少30分钟，每周运动总时长至少150分钟;\n";
				yq_target += String.valueOf(target_i)+"." + "保持科学规律的运动习惯;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques3.equals("3")&&ques4.equals("3")) {
				now_ques += String.valueOf(mqqk_i)+"." + "您每周运动频次偏少，每次运动时间偏长，请根据您自身情况增加每周运动次数到至少5次，另外控制每次运动时长在30-60分钟之间，防止出现低血糖;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "运动是保持健康的最佳方法之一，而且运动能阻止糖尿病的进一步发展。医生指南建议您每周至少运动5次，每次至少30分钟;\n";
				yq_target += String.valueOf(target_i)+"." + "保持科学规律的运动习惯;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques3.equals("3")&&ques4.equals("5")) {
				now_ques += String.valueOf(mqqk_i)+"." + "您做的很好，但是如果您根据自身情况能每周运动至少5次就更好了;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "运动是保持健康的最佳方法之一，而且运动能阻止糖尿病的进一步发展。医生指南建议您每周至少运动5次，每次至少30分钟;\n";
				yq_target += String.valueOf(target_i)+"." + "保持科学规律的运动习惯;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques3.equals("4")&&(ques4.equals("2"))) {
				now_ques += String.valueOf(mqqk_i)+"." + "您做的很好，但是如果您根据您自身情况能延长运动时间到每次至少30分钟更好了;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "运动是保持健康的最佳方法之一，而且运动能阻止糖尿病的进一步发展。医生指南建议您每周至少运动5次，每次至少30分钟;\n";
				yq_target += String.valueOf(target_i)+"." + "保持科学规律的运动习惯;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques3.equals("4")&&ques4.equals("5")) {
				now_ques += String.valueOf(mqqk_i)+"." + "您运动量较大，要注意血糖的变化，防止出现低血糖，建议运动中适当控制运动时长在30-60分钟之间最好;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "运动是保持健康的最佳方法之一，而且运动能阻止糖尿病的进一步发展。医生指南建议您每周至少运动5次，每次至少30分钟;\n";
				yq_target += String.valueOf(target_i)+"." + "保持科学规律的运动习惯;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques3.equals("1")||ques4.equals("1")) {
				now_ques += String.valueOf(mqqk_i)+"." + "没有运动习惯。锻炼是保持健康、预防糖尿病的最好预防方式，请咨询医生或专业人士获得锻炼建议;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "运动是保持健康的最佳方法之一，而且运动能阻止糖尿病的进一步发展。医生指南建议您每周至少运动5次，每次至少30分钟;\n";
				yq_target += String.valueOf(target_i)+"." + "保持科学规律的运动习惯;\n";
				mqqk_i++;

				target_i++;
			}

			String ques21 = follow.get("ques21") == null ? "":follow.get("ques21").toString();//眼部症状
//			if(!diabetesType.equals("")||!diabetesTime.equals("")) {
			if((!diabetesType.equals("")&&!"SUGAR_TYPE_005".equals(diabetesType))||!diabetesTime.equals("")) {
				if(!ques21.equals("") && !ques21.equals("11")) {
					now_ques += String.valueOf(mqqk_i)+"." + "您有糖尿病眼病相关的眼睛或视力异常症状表现，需警惕并发症的发生;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "及时联系您的医生，并告诉他您的眼睛或视力情况;\n";

					String[] ques21Arr = ques21.split("-");
					String ques21Str = "";
					for (String s : ques21Arr) {
						if(s.equals("1")) {
							ques21Str += "视力模糊、";
						}
						if(s.equals("2")) {
							ques21Str += "读标志或书籍困难、";
						}
						if(s.equals("3")) {
							ques21Str += "重影、";
						}
						if(s.equals("4")) {
							ques21Str += "一只眼睛或双眼受伤、";
						}
						if(s.equals("5")) {
							ques21Str += "眼部持续发红、";
						}
						if(s.equals("6")) {
							ques21Str += "感觉眼部有压力、";
						}
						if(s.equals("7")) {
							ques21Str += "飞蚊症、";
						}
						if(s.equals("8")) {
							ques21Str += "直线看起来不直、";
						}
						if(s.equals("9")) {
							ques21Str += "不能像以前一样看到旁边的东西、";
						}
						if(s.equals("10")) {
							ques21Str += "其他眼部症状、";
						}
					}
					if(!ques21Str.equals("")) {
						ques21Str = ques21Str.substring(0,ques21Str.length()-1);
					}

					yq_target += String.valueOf(target_i)+"." + "“"+ques21Str+"”等症状逐渐改善;\n";
					mqqk_i++;
					target_i++;
				}
			}

			String ques22 = follow.get("ques22") == null ? "":follow.get("ques22").toString();//眼部症状
			if(ques22.equals("2")||ques22.equals("3")) {
				now_ques += String.valueOf(mqqk_i)+"." + "疏于眼部检查;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "每12个月做一次眼部检查是必须的，这样医生才能以最好的方式避免您的病情加重导致糖尿病视网膜病变的发生和发展。建议尽快去看医生进行眼部检查;\n";
				yq_target += String.valueOf(target_i)+"." + "遵医嘱定期进行眼部检查;\n";
				mqqk_i++;

				target_i++;
			}

			String ques23 = follow.get("ques23") == null ? "":follow.get("ques23").toString();//眼部症状
			if(!diabetesType.equals("")||!diabetesTime.equals("")) {
				if(!ques23.equals("") && !ques23.equals("7")) {
					now_ques += String.valueOf(mqqk_i)+"." + "您已确诊患有糖尿病，有足部不适症状，因此糖尿病病足的风险比较大;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "您的足部症状需要及时同您的医生讨论，咨询并告诉医生您的足部症状，必要时进一步检查排除糖尿病足是非常重要的;\n";

					String[] ques23Arr = ques23.split("-");
					String ques23Str = "";
					for (String s : ques23Arr) {
						if(s.equals("1")) {
							ques23Str += "皮肤出现异常颜色、";
						}
						if(s.equals("2")) {
							ques23Str += "感觉发冷、";
						}
						if(s.equals("3")) {
							ques23Str += "经常发麻、";
						}
						if(s.equals("4")) {
							ques23Str += "经常受伤或溃疡、";
						}
						if(s.equals("5")) {
							ques23Str += "足部皮肤变硬或变厚、";
						}
						if(s.equals("6")) {
							ques23Str += "其它足部症状、";
						}
					}
					if(!ques23Str.equals("")) {
						ques23Str = ques23Str.substring(0 , ques23Str.length()-1);
					}
					yq_target += String.valueOf(target_i)+"." + "“"+ques23Str+"”等症状逐渐改善;\n";
					mqqk_i++;

					target_i++;
				}
			}

			String ques24 = follow.get("ques24") == null ? "":follow.get("ques24").toString();//眼部症状
			if(ques24.equals("2")||ques24.equals("3")) {
				now_ques += String.valueOf(mqqk_i)+"." + "疏于足部检查;\n";
//				gs_suggest += String.valueOf(mqqk_i)+"." + "尽快去看医生进行足部检查。每12个月做一次足部检查是必须的，这样医生才能以最好的方式避免您的病情加重导致截肢，截肢是非常严重的糖尿病并发症之一;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "尽快去看医生进行足部检查。每12个月做一次足部检查是必须的，这样医生才能以最好的方式避免您的患上糖尿病足，糖尿病足是非常严重的糖尿病并发症之一。\n";
				yq_target += String.valueOf(target_i)+"." + "遵医嘱定期进行足部检查;\n";
				mqqk_i++;

				target_i++;
			}

			String ques25 = follow.get("ques25") == null ? "":follow.get("ques25").toString();//眼部症状
			if(ques25.equals("7")) {
				now_ques += String.valueOf(mqqk_i)+"." + "没有相关定期复查医嘱。为了预防糖尿病并发症的发生或发展，定期检查血糖是很重要的;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "请和医生确认您是应该多久进行血糖监测，然后日常遵医嘱定期复查;\n";
				yq_target += String.valueOf(target_i)+"." + "定期监测血糖变化;\n";
				mqqk_i++;

				target_i++;
			}

			String ques26 = follow.get("ques26") == null ? "":follow.get("ques26").toString();//眼部症状
			if(ques26.equals("3")||ques26.equals("4")) {
				now_ques += String.valueOf(mqqk_i)+"." + "空腹血糖检查间隔时间太长。请注意至少三个月内到医院检查一次空腹血糖是必须的，除此之外，请遵从医嘱定期检查;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "请近期就医进行空腹血糖相关检查，并遵医嘱日常监测血糖变化;\n";
				yq_target += String.valueOf(target_i)+"." + "定期监测血糖变化;\n";
				mqqk_i++;

				target_i++;
			}

			if(!kf.equals("")) {
				if(Float.parseFloat(kf)>7) {
					now_ques += String.valueOf(mqqk_i)+"." + "根据空腹血糖检查结果，您的糖尿病最近控制得不佳;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "为了预防糖尿病并发症的发生或发展，定期检查血糖是很重要的;\n";
					yq_target += String.valueOf(target_i)+"." + "控制空腹血糖，达到4.4-7mmol/L;\n";
					mqqk_i++;

					target_i++;
				}
			}

			String ques27 = follow.get("ques27") == null ? "":follow.get("ques27").toString();//眼部症状
			if(ques27.equals("3")||ques26.equals("4")) {
				now_ques += String.valueOf(mqqk_i)+"." + "糖化血红蛋白反应了您近期血糖平均控制情况，所以遵从医嘱进行糖化血红蛋白检查是很重要的;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "您应该每3个月进行一次糖化血红蛋白测试，请去看医生行糖化血红蛋白检查，观察近期血糖平均控制情况;\n";
				yq_target += String.valueOf(target_i)+"." + "定期监测糖化血红蛋白变化;\n";
				mqqk_i++;

				target_i++;
			}

			if(!hemoglobin.equals("")){
				if(Float.parseFloat(hemoglobin)<7) {
					now_ques += String.valueOf(mqqk_i)+"." + "评估结果显示您的糖尿病控制的较好，请继续保持良好的生活习惯并定期进行身体检查;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "评估结果显示您的糖尿病控制的较好，请继续保持良好的生活习惯并定期进行身体检查\n;";
					yq_target += String.valueOf(target_i)+"." + "评估结果显示您的糖尿病控制的较好，请继续保持良好的生活习惯并定期进行身体检查\n;";
					mqqk_i++;

					target_i++;
				}else if(Float.parseFloat(hemoglobin)>=7) {
					now_ques += String.valueOf(mqqk_i)+"." + "根据糖化血红蛋白检查结果，您糖尿病控制的不好。糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "根据糖化血红蛋白检查结果，您糖尿病控制的不好。糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案;\n";
					yq_target += String.valueOf(target_i)+"." + "根据糖化血红蛋白检查结果，您糖尿病控制的不好。糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案;\n";
					mqqk_i++;

					target_i++;
				}
			}

			String ques1 = follow.get("ques1") == null ? "":follow.get("ques1").toString();//高血压
			if(!diabetesType.equals("")||!diabetesTime.equals("")) {
				if(ques1.equals("3")) {
					now_ques += String.valueOf(mqqk_i)+"." + "您已确诊患有糖尿病，因此确认您是否患了高血压是很重要的;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "如果您不懂是否患有高血压，请咨询医生，并定期检测血压变化。这是糖尿病并发症的重要风险因素之一;\n";
					yq_target += String.valueOf(target_i)+"." + "遵医嘱定期监测血压变化;\n";
					mqqk_i++;

					target_i++;
				}
			}
			String ques35 = follow.get("ques35") == null ? "":follow.get("ques35").toString();//高血压
			if(ques1.equals("1")) {
				if(ques35.equals("3")||ques35.equals("4")||ques35.equals("5")){
					now_ques += String.valueOf(mqqk_i)+"." + "如果您患了高血压，按照医嘱定期检测血压是很重要的;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "对高血压患者来说，恰当饮食，适量运动，保持适当体重，定期检查，并遵守医嘱用药是非常重要的。高血压增加了患糖尿病及心脏相关并发症的风险。请遵医嘱用药，并定期检查血压;\n";
					yq_target += String.valueOf(target_i)+"." + "遵医嘱定期监测血压变化;\n";
					mqqk_i++;

					target_i++;
				}
			}

			if(ques35.equals("1")) {
				if(!sbp.equals("") &&!dbp.equals("")) {
					if(Float.parseFloat(sbp)>130 &&Float.parseFloat(dbp)>80) {
						now_ques += String.valueOf(mqqk_i)+"." + "您的血压超过理想范围或医嘱范围;\n";
						gs_suggest += String.valueOf(mqqk_i)+"." + "您患上或加重糖尿病并发症的风险比较高，请和医生讨论，必要时采取一些措施，定期检查血压是非常重要的;\n";
						yq_target += String.valueOf(target_i)+"." + "舒张压：60～79mmHg收缩压： 90～129mmHg或医嘱范围;\n";
						mqqk_i++;

						target_i++;
					}
				}
			}

			String ques28 = follow.get("ques28") == null ? "":follow.get("ques28").toString();//上一次做胆固醇测试
			int distinct28Tar = 0;
			if(!diabetesType.equals("")||!diabetesTime.equals("")) {
				if(ques28.equals("3")) {
					now_ques += String.valueOf(mqqk_i)+"." + "您已确诊患有糖尿病，因此确认您是否患了高胆固醇是很重要的。这是糖尿病相关并发症的重要风险因素之一;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "如果您不知道您是否有高胆固醇，请跟医生预约于本月做一次胆固醇测试;\n";
					yq_target += String.valueOf(target_i)+"." + "遵医嘱定期检测血脂变化;\n";
					mqqk_i++;

					target_i++;
					distinct28Tar = 1;
				}
			}

			String ques29 = follow.get("ques29") == null ? "":follow.get("ques29").toString();//胆固醇
			if(ques29.equals("2")||ques29.equals("3")) {
//				now_ques += String.valueOf(mqqk_i)+"." + "您已患上高胆固醇，每年做一次血液检查是很重要的，此外您还可以咨询医生其他检查项目的频率;\n";
				now_ques += String.valueOf(mqqk_i)+"." + "您已患上糖尿病，定期每年复查一次血脂水平很重要的，此外您还可以咨询医生其他检查项目的频率;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "请同您的医生讨论，在这个月内做一次胆固醇测试;\n";
//				yq_target += String.valueOf(mqqk_i)+"." + "遵医嘱定期检测血脂变化;\n";
				if(distinct28Tar == 0) {
					yq_target += String.valueOf(target_i)+"." + "遵医嘱定期检测血脂变化;\n";
					distinct28Tar = 1;
					target_i++;
				}

				mqqk_i++;


			}


			if(!cholesterol.equals("") && ques29.equals("1")) {
				if(Float.parseFloat(cholesterol)>4.5) {
					now_ques += String.valueOf(mqqk_i)+"." + "您的胆固醇偏高;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "请和医生讨论，采取一些措施。为了预防糖尿病并发症的发生或发展，定期检查血脂变化并咨询医生进行针对性的调控是非常重要的;\n";
					yq_target += String.valueOf(target_i)+"." + "控制总胆固醇＜4.5mmol/L;\n";
					mqqk_i++;

					target_i++;
				}
			}

			if(ques29.equals("1") && !highCholesterol.equals("")) {
				if(sex.equals("男")){
					if(Float.parseFloat(highCholesterol)<1) {
						now_ques += String.valueOf(mqqk_i)+"." + "高密度脂蛋白胆固醇偏低;\n";
						gs_suggest += String.valueOf(mqqk_i)+"." + "请和医生讨论，采取一些措施。为了预防糖尿病并发症的发生或发展，定期检查血脂变化并咨询医生进行针对性的调控是非常重要的;\n";
						yq_target += String.valueOf(target_i)+"." + "男性：＞1.0mmol/L;\n";
						mqqk_i++;

						target_i++;
					}
				}
				if(sex.equals("女")){
					if(Float.parseFloat(highCholesterol)<1.3) {
						now_ques += String.valueOf(mqqk_i)+"." + "高密度脂蛋白胆固醇偏低;\n";
						gs_suggest += String.valueOf(mqqk_i)+"." + "请和医生讨论，采取一些措施。为了预防糖尿病并发症的发生或发展，定期检查血脂变化并咨询医生进行针对性的调控是非常重要的;\n";
						yq_target += String.valueOf(target_i)+"." + "女性：＞1.3mmol/L;\n";
						mqqk_i++;

						target_i++;
					}
				}
			}

			if(ques29.equals("1") && !lowCholesterol.equals("")) {
				if(Float.parseFloat(highCholesterol)>=2.6) {
					now_ques += String.valueOf(mqqk_i)+"." + "低密度脂蛋白胆固醇偏高;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "请和医生讨论，采取一些措施。为了预防糖尿病并发症的发生或发展，定期检查血脂变化并咨询医生进行针对性的调控是非常重要的;\n";
					yq_target += String.valueOf(target_i)+"." + "未合并冠心病 ＜2.6mmol/L;\n";
					mqqk_i++;
					target_i++;
				}

			}

			String ques30 = follow.get("ques30") == null ? "":follow.get("ques30").toString();//血脂测试
			if(!diabetesType.equals("")||!diabetesTime.equals("")) {
				if(ques30.equals("4")||ques30.equals("5")) {
					now_ques += String.valueOf(mqqk_i)+"." + "疏于血脂检查;\n";
					gs_suggest += String.valueOf(mqqk_i)+"." + "您已确诊患有糖尿病，您应该每年检查一次血脂。因此，请到医生那儿检查血脂;\n";
//					yq_target += String.valueOf(mqqk_i)+"." + "遵医嘱定期检测血脂变化;\n";
					if(distinct28Tar == 0) {
						yq_target += String.valueOf(target_i)+"." + "遵医嘱定期检测血脂变化;\n";
						distinct28Tar = 1;
						target_i++;
					}
					mqqk_i++;


				}
			}

			String ques32 = follow.get("ques32") == null ? "":follow.get("ques32").toString();//尿蛋白值
			String ques33 = follow.get("ques33") == null ? "":follow.get("ques33").toString();//尿蛋白
			if(ques33.equals("2")||ques33.equals("3")||ques33.equals("4")||ques33.equals("5")) {
				now_ques += String.valueOf(mqqk_i)+"." + "疏于尿微量白蛋白检查;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "为了估算肾功能受损风险，您需要定期检查尿微量白蛋白;\n";
				yq_target += String.valueOf(target_i)+"." + "遵医嘱定期检测尿微量白蛋白变化;\n";
				mqqk_i++;

				target_i++;
			}
			if(ques33.equals("1")&&ques32.equals("1")) {
				now_ques += String.valueOf(mqqk_i)+"." + "有肾病风险;\n";
				gs_suggest += String.valueOf(mqqk_i)+"." + "根据您的微量尿白蛋白测试结果，尽快同医生讨论您的肾病风险。尿微量白蛋白值能反应肾功能受损的情况，因此控制好糖尿病非常重要的;\n";
				yq_target += String.valueOf(target_i)+"." + "控制尿微量白蛋白＜30mg/g;\n";
				mqqk_i++;

				target_i++;
			}
			//尿微量白蛋白


			if(ques33.equals("1")||ques33.equals("2")||ques33.equals("3")) {
				if(!nwlbdb.equals("")) {
					if(Float.parseFloat(nwlbdb)>30) {
						now_ques += String.valueOf(mqqk_i)+"." + "有肾病风险;\n";
						gs_suggest += String.valueOf(mqqk_i)+"." + "尿微量白蛋白/肌酐比值（uACR）是诊断早期糖尿病肾病的一项敏感而可靠的指标，有异常升高，建议及时到专科进行诊治，并定期复查观察变化;\n";
						yq_target += String.valueOf(target_i)+"." + "男：控制uACR＜22mg/g女：控制uACR＜31mg/g;\n";
						mqqk_i++;

						target_i++;
					}
				}
			}

			String ques34 = follow.get("ques34") == null ? "":follow.get("ques34").toString();//血肌酐
//			if(ques34.equals("4")) {
//				now_ques += String.valueOf(mqqk_i)+"." + "血肌酐疏于检查;\n";
//				gs_suggest += String.valueOf(mqqk_i)+"." + "为了估算肾功能受损的情况，每12个月检查一次肾功能-血肌酐等相关检查是必须的;\n";
//				yq_target += String.valueOf(target_i)+"." + "男：控制uACR＜22mg/g女：控制uACR＜31mg/g;\n";
//				mqqk_i++;
//
//				target_i++;
//			}
			if(ques34.equals("4")) {
				if(!xjg.equals("") && sex.equals("男")) {
					if(Float.parseFloat(nwlbdb)>106) {
						now_ques += String.valueOf(mqqk_i)+"." + "血肌酐值："+nwlbdb+"μmoI/L,增高;\n";
						gs_suggest += String.valueOf(mqqk_i)+"." + "血肌酐偏高，可见于急慢性肾衰。糖尿病患者需注意关注肾功能改变，建议到专科进一步诊治;\n";
						yq_target += String.valueOf(target_i)+"." + "男:控制血肌酐，达到54-106μmoI/L;\n";
						mqqk_i++;

						target_i++;
					}
				}
				if(!xjg.equals("") && sex.equals("女")) {
					if(Float.parseFloat(nwlbdb)>97) {
						now_ques += String.valueOf(mqqk_i)+"." + "血肌酐值："+nwlbdb+"μmoI/L,增高;\n";
						gs_suggest += String.valueOf(mqqk_i)+"." + "血肌酐偏高，可见于急慢性肾衰。糖尿病患者需注意关注肾功能改变，建议到专科进一步诊治;\n";
						yq_target += String.valueOf(target_i)+"." + "女:控制血肌酐，达到44-97μmol/L;\n";
						mqqk_i++;

						target_i++;
					}
				}
			}

		}

		Map map = new HashMap();
		map.put("now_ques", now_ques);
		map.put("gs_suggest", gs_suggest);
		map.put("yq_target", yq_target);

		return map;
	}

	@Override
	public HealthAccessModel getPrintAccessDiaControl(String sid , String memberId) {
		HealthAccessModel resultModel = new HealthAccessModel();
		FollowupPO followup = this.followMapper.getFollowupById(sid);//根据随访id获取随访内容
		String endTime = followup.getModifyDt();
		String currentfollow = followup.getMemberInfo();
		Map<String,Object> currentmap = JSON.parseObject(currentfollow);
		String diabetesType = currentmap.get("diabetesType") == null ? "" :currentmap.get("diabetesType").toString();
		boolean ifdiabetes = false;
		if(diabetesType.equals("SUGAR_TYPE_001") ||diabetesType.equals("SUGAR_TYPE_002") ||diabetesType.equals("SUGAR_TYPE_003")) {
			ifdiabetes = true;
		}

		Map result = new HashMap();

		FollowDayDTO dto = new FollowDayDTO();
		dto.setMemberId(memberId);
		dto.setType("8");
		dto.setStatus("1");
		dto.setEndTime(endTime);
		 List<FollowDayModel> list = this.followMapper.listFollowDay(dto);
		if(list.size()>0) {
			String foreignId = list.get(list.size()-1).getForeignId();

			if(ifdiabetes) {
				resultModel.setFirst(false);
				String memberInfo = followup.getMemberInfo();
				Map<String,Object> follow = JSON.parseObject(memberInfo);
				String hba1c = follow.get("hba1c") == null ? "" :follow.get("hba1c").toString();
				if(!hba1c.equals("")) {
					if(Float.parseFloat(hba1c)<7) {
						resultModel.setLevel("1");
						resultModel.setSuggest("评估结果显示您的糖尿病控制的较好，请继续保持良好的生活习惯并定期进行身体检查");
					}else if(Float.parseFloat(hba1c)-7<1) {
						resultModel.setLevel("2");
						resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
					}else if(Float.parseFloat(hba1c)-7<2) {
						resultModel.setLevel("3");
						resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
					}else if(Float.parseFloat(hba1c)-7<3) {
						resultModel.setLevel("4");
						resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
					}else if(Float.parseFloat(hba1c)-7<4) {
						resultModel.setLevel("5");
						resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
					}else if(Float.parseFloat(hba1c)-7<5) {
						resultModel.setLevel("6");
						resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
					}else if(Float.parseFloat(hba1c)-7<6) {
						resultModel.setLevel("7");
						resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
					}else if(Float.parseFloat(hba1c)-7<7) {
						resultModel.setLevel("8");
						resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
					}else{
						resultModel.setLevel("9");
						resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
					}
				}
			}else {//没有疾病
				if(sid.equals(foreignId) && !ifdiabetes) {//如果没有患病 且 sid就是第一条记录
					String memberInfo = list.get(list.size()-1).getMemberInfo();
					String followInfo = list.get(list.size()-1).getFollowInfo();
					Map healthLevel = getHealthAccessData(memberInfo , followInfo);
					String level = healthLevel.get("level").toString();
					String suggest = healthLevel.get("suggest").toString();
					resultModel.setLevel(level);
					resultModel.setSuggest(suggest);
					resultModel.setFirst(true);
				}else {
					resultModel.setFirst(false);
					String memberInfo = followup.getMemberInfo();
					Map<String,Object> follow = JSON.parseObject(memberInfo);
					String hba1c = follow.get("hba1c") == null ? "" :follow.get("hba1c").toString();
					if(!hba1c.equals("")) {
						if(Float.parseFloat(hba1c)<7) {
							resultModel.setLevel("1");
							resultModel.setSuggest("评估结果显示您的糖尿病控制的较好，请继续保持良好的生活习惯并定期进行身体检查");
						}else if(Float.parseFloat(hba1c)-7<1) {
							resultModel.setLevel("2");
							resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
						}else if(Float.parseFloat(hba1c)-7<2) {
							resultModel.setLevel("3");
							resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
						}else if(Float.parseFloat(hba1c)-7<3) {
							resultModel.setLevel("4");
							resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
						}else if(Float.parseFloat(hba1c)-7<4) {
							resultModel.setLevel("5");
							resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
						}else if(Float.parseFloat(hba1c)-7<5) {
							resultModel.setLevel("6");
							resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
						}else if(Float.parseFloat(hba1c)-7<6) {
							resultModel.setLevel("7");
							resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
						}else if(Float.parseFloat(hba1c)-7<7) {
							resultModel.setLevel("8");
							resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
						}else{
							resultModel.setLevel("9");
							resultModel.setSuggest("糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案");
						}
					}
				}
			}

		}
		//图2
		List hbalist = new ArrayList();
		String containDate = "";
		//获取糖化血红蛋白list
		for (int i=0;i<list.size();i++) {
			String followinfo = list.get(i).getMemberInfo();
			Map<String,Object> follow = JSON.parseObject(followinfo);
			String hba1c = follow.get("hba1c").toString();
			String hba1cdate = follow.get("hba1cdate").toString();
			String modifyDt = list.get(i).getModifyDt();

			if(!StringUtils.isBlank(hba1c)) {
				if(!StringUtils.isBlank(hba1cdate)) {
					if(!containDate.contains(hba1cdate)) {
						containDate += hba1cdate+",";
						Map map = new HashMap();
						map.put("hba1cdate", hba1cdate);
						map.put("hba1c", hba1c);
						hbalist.add(map);
					}
				}else {
					if(!containDate.contains(modifyDt.substring(0, 10))) {
						containDate += modifyDt.substring(0, 10)+",";
						Map map = new HashMap();
						map.put("hba1cdate", modifyDt.substring(0, 10));
						map.put("hba1c", hba1c);
						hbalist.add(map);
					}
				}
			}

		}
		resultModel.setHbalist(hbalist);
		//图3
		List ques13list = new ArrayList();
		String ques13Date = "";
		String lastques = "";
		String quesResult = "";
		//获取糖化血红蛋白list
		for (int i=0;i<list.size();i++) {
			String followinfo = list.get(i).getFollowInfo();
			Map<String,Object> follow = JSON.parseObject(followinfo);


			String ques13 = follow.get("ques13").toString();
			String modifyDt = list.get(i).getModifyDt();
			String quesStr = "";

			String[] qs = ques13.split("-");

			if(i == 0) {
				int q13index = 1;
				for (String s : qs) {
					if(s.equals("1")) {
						lastques += q13index+".口舌干燥 ; ";
						quesStr += "口舌干燥,";
						q13index ++;
					}
					if(s.equals("2")) {
						lastques += q13index+".口渴 ; ";
						quesStr += "口渴  ,";
						q13index ++;
					}
					if(s.equals("3")) {
						lastques += q13index+".多尿 ; ";
						quesStr += "多尿  ,";
						q13index ++;
					}
					if(s.equals("4")) {
						lastques += q13index+".体重减轻; ";
						quesStr += "体重减轻 ,";
						q13index ++;
					}
					if(s.equals("5")) {
						lastques += q13index+".经常性感染 ; ";
						quesStr += "经常性感染,";
						q13index ++;
					}
					if(s.equals("6")) {
						lastques += q13index+".性功能障碍 ; ";
						quesStr += "性功能障碍,";
						q13index ++;
					}
					if(s.equals("7")) {
						lastques += q13index+".伤口愈合缓慢; ";
						quesStr += "伤口愈合缓慢,";
						q13index ++;
					}
					if(s.equals("8")) {
						lastques += q13index+".生殖器瘙痒或感染 ; ";
						quesStr += "生殖器瘙痒或感染,";
						q13index ++;
					}
					if(s.equals("9")) {
						lastques += q13index+".周身瘙痒; ";
						quesStr += "周身瘙痒,";
						q13index ++;
					}
					if(s.equals("10")) {
						lastques += q13index+".神经源性疼痛 ; ";
						quesStr += "神经源性疼痛 ,";
//						q13index ++;
					}
				}
				if(qs.length > 2) {
					quesResult = "您有一些糖尿病症状，请密切关注您这些症状的发展情况，并定期做糖尿病风险评估问卷，留心上方糖尿病症状走向图的发展情况，并及时与您的医生联系，同时调整治疗方案，尽快减少症状，控制病情。";
				}else {
					if(!quesStr.equals("") && !ques13.equals("11")) {
						quesStr = quesStr.substring(0,quesStr.length()-1);
						quesResult = "您有"+quesStr+"的不适症状表现，需要引起注意不可放任其发展，建议到医院请专科医生进一步评估下，必要时做一次详细的检查再次确认病情。";
					}else if(ques13.equals("11")) {
						quesResult = "您目前没有任何糖尿病症状，这很好，请继续保持健康的生活方式。";
					}
				}
			}

			if(!ques13Date.contains(modifyDt.substring(0, 10)) && !ques13.equals("11")) {//
				Map map = new HashMap();
				map.put("ques13" , ques13);
				map.put("ques13Date" , modifyDt.substring(0, 10));
				map.put("lastques", lastques);
				map.put("quesResult", quesResult);
				ques13list.add(map);
			}
			ques13Date += modifyDt.substring(0, 10) + ",";//每天只取最新的一条
		}
		resultModel.setQues13list(ques13list);

		 //图4
		 boolean ifChd = false;
		 MemberArchivesPO memberArchives = this.memberService.getMemberArchives(memberId, "");//需要判断是否存在患者档案
		 if(memberArchives != null) {
			 String archivesJson = memberArchives.getArchivesJson();
			 if(!StringUtils.isBlank(archivesJson)) {
				 Map<String, Object> archivesJsonMap = JsonSerializer.jsonToMap(archivesJson);
			     String chd = "";
		         if (archivesJsonMap.get("complication") != null) {
		        	 String complication = archivesJsonMap.get("complication").toString();
		        	 Map<String, Object> complicationMap = JsonSerializer.jsonToMap(complication);
		        	 if(complicationMap.get("chd") != null) {
		        		 chd = complicationMap.get("chd").toString();
		        		 if(chd.equals("QZ01")) {
		        			 ifChd = true;
		        		 }
		        	 }
		         }
			 }
		 }
         resultModel.setIfChd(ifChd);
		 String memberInfo = followup.getMemberInfo();
		 String followInfo = followup.getFollowInfo();
		 resultModel.setMemberInfo(memberInfo);

		 //建议模块
		 Map accessSuggest = this.getPrintAccessSuggest(memberInfo,followInfo);
		 String now_ques = accessSuggest.get("now_ques").toString();
		 String gs_suggest = accessSuggest.get("gs_suggest").toString();
		 String yq_target = accessSuggest.get("yq_target").toString();
		 resultModel.setNowques(now_ques);
		 resultModel.setGssuggest(gs_suggest);
		 resultModel.setYqtarget(yq_target);

		 //获取患者血糖信息等的控制目标
		 RangeBO range = this.memberService.getMemberRange(memberId);
		 resultModel.setRangebo(range);

		 return resultModel;
	}

	private Map getPrintAccessSuggest(String memberInfo,String followInfo){
	    Map result = new HashMap();
	    if(!StringUtils.isBlank(followInfo)){
            result = this.getHealthAccessResult(memberInfo, followInfo);
	        JSONObject jsonObject = JSONObject.parseObject(followInfo);
	        String now_ques = jsonObject.getString("nowques");
            String gs_suggest = jsonObject.getString("suggest");
            String yq_target = jsonObject.getString("target");
            if(!StringUtils.isBlank(now_ques)){
                result.put("now_ques", now_ques);
            }
            if(!StringUtils.isBlank(gs_suggest)){
                result.put("gs_suggest", gs_suggest);
            }
            if(!StringUtils.isBlank(yq_target)){
                result.put("yq_target", yq_target);
            }
        }
        return result;
    }

	@Override
	public Map<String, Object> getHealthAccessMemberInfo(String doctorId, String memberId, String followId) {
        //获取档案信息
        Map<String, Object> map = this.memberService.getMemberArchivesByMemberId(memberId, doctorId,"",true);
        //获取相关信息
        FollowupPO followup = this.followMapper.getFollowupById(followId);
        map.put("followmember", followup.getMemberInfo());

        return map;
	}

	public String getQuest13Result(String values) {
		String[] valArr = values.split("-");

		return "";
	}

	@Override
	public List<FollowListModel> wechatListFollow(String memberId, String memberName, String doctorId, Integer followType,
			Boolean deal, String fillFormBy) {

		return this.followMapper.listFollowWechat(memberId, null, doctorId, followType, deal, fillFormBy);
	}

    @Override
    public List<FollowListModel> listFollowByParam(ListFollowDTO followDTO) {
        return this.followMapper.listFollowByParam(followDTO);
    }

    @Override
    public List<FollowRemindPO> listFollowRemind(ListFollowRemindDTO followRemindDTO) {
        return this.followMapper.listFollowRemind(followRemindDTO);
    }

    @Override
    public PageResult<FollowListModel> listMemberFollow(PageRequest page, String memberId,List<Integer> typeList) {
        PageHelper.startPage(page.getPage(),page.getRows());
        ListMemberFollowDTO dto = new ListMemberFollowDTO();
        dto.setMemberId(memberId);
        dto.setTypeList(typeList);
        List<FollowListModel> list = this.followMapper.listMemberFollow(dto);
        if (null != list && list.size() > 0){
            list.forEach(x->{
                FollowReportPO reportPO = this.followMapper.getFollowReportByFollowId(x.getSid(),false,null);
                x.setStatusFlag(reportPO == null ? "0" : "1");
            });
        }
        return new PageResult<FollowListModel>(list);
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
                    knowledgeWeek.setWeekName("第" + NumberTool.numToChinese(week) + "周");
                    knowledgeWeek.setWeek(week);
                    knowledgeWeek.setRows(knowledgeList);
                    knowledgeWeekList.add(knowledgeWeek);
                }
                Knowledge knowledge = new Knowledge();
                knowledge.setTitle(prescriptionKnowledgePO.getTitle());
                knowledge.setKnowledge(prescriptionKnowledgePO.getKnowledge());
//                knowledge.setDay();
                knowledge.setArticleId(prescriptionKnowledgePO.getArticleId());
                knowledgeList.add(knowledge);
            }
        }
        return knowledgeWeekList;
    }

    /**
     * v5.1.6
     * 获取问卷信息(处理数据)
     * @param sid
     * @return
     */
    private String getQuesJSon(String sid){
        String quesJson = "";
        List<QuestionnairePO> quesList = this.questionnaireService.getQuestionnaireByFollowId(sid);
        if (null != quesList && quesList.size() > 0){
            HashMap<String, Object> quesMap = new HashMap<>();
            for (QuestionnairePO po : quesList) {
                if (po.getQuestionType() == QuestionnaireConstant.QUESTIONNAIRE_TYPE_DIABETES_KNOWLEDGE){ //糖尿病知识测试
                    String q1_num = "";
                    String q1_level = "";
                    if (!StringUtils.isBlank(po.getResultJson())){
                        Map<String, Object> rMap = JsonSerializer.jsonToMap(po.getResultJson());
                        if (rMap.get("standardScore") != null){
                            q1_num = rMap.get("standardScore").toString();
                        }
                        if (rMap.get("assessLevel") != null){
                            q1_level = getStringLevel(po.getQuestionType(), rMap.get("assessLevel").toString());
                        }
                    }
                    quesMap.put("q1_sid",po.getSid());
                    quesMap.put("q1_num",q1_num);
                    quesMap.put("q1_level",q1_level);
                }else  if (po.getQuestionType() == QuestionnaireConstant.QUESTIONNAIRE_TYPE_SELF_MANAGEMENT){ //患者自我管理行为评估
                    String q2_num = "";
                    String q2_level = "";
                    if (!StringUtils.isBlank(po.getResultJson())){
                        Map<String, Object> rMap = JsonSerializer.jsonToMap(po.getResultJson());
                        if (rMap.get("avgScore") != null){
                            q2_num = rMap.get("avgScore").toString();
                        }
                        if (rMap.get("assessLevel") != null){
                            q2_level = getStringLevel(po.getQuestionType(), rMap.get("assessLevel").toString());
                        }
                    }
                    quesMap.put("q2_sid",po.getSid());
                    quesMap.put("q2_num",q2_num);
                    quesMap.put("q2_level",q2_level);
                }else  if (po.getQuestionType() == QuestionnaireConstant.QUESTIONNAIRE_TYPE_DIABETES_AUTHORIZATION){ //糖尿病授权评分测试
                    String q3_num = "";
                    String q3_level = "--";
                    if (!StringUtils.isBlank(po.getResultJson())){
                        Map<String, Object> rMap = JsonSerializer.jsonToMap(po.getResultJson());
                        if (rMap.get("totalScore") != null){
                            q3_num = rMap.get("totalScore").toString();
                        }
                    }
                    quesMap.put("q3_sid",po.getSid());
                    quesMap.put("q3_num",q3_num);
                    quesMap.put("q3_level",q3_level);
                }else  if (po.getQuestionType() == QuestionnaireConstant.QUESTIONNAIRE_TYPE_DIABETIC_FOOT_RISK){ //糖尿病足风险评估
                    String q4_num = "";
                    String q4_level = "";
                    if (!StringUtils.isBlank(po.getResultJson())){
                        Map<String, Object> rMap = JsonSerializer.jsonToMap(po.getResultJson());
                        if (rMap.get("followAdvice") != null){
                            q4_num = rMap.get("followAdvice").toString();
                        }
                        if (rMap.get("riskLevel") != null){
                            q4_level =  rMap.get("assessLevel").toString();
                        }
                    }
                    quesMap.put("q4_sid",po.getSid());
                    quesMap.put("q4_num",q4_num);
                    quesMap.put("q4_level",q4_level);
                }else  if (po.getQuestionType() == QuestionnaireConstant.QUESTIONNAIRE_TYPE_FOOT_CARE){ //足部护理评估量表
                    String q5_num = "";
                    String q5_level = "";
                    if (!StringUtils.isBlank(po.getResultJson())){
                        Map<String, Object> rMap = JsonSerializer.jsonToMap(po.getResultJson());
                        if (rMap.get("standardScore") != null){
                            q5_num = rMap.get("standardScore").toString();
                        }
                        if (rMap.get("assessLevel") != null){
                            q5_level = getStringLevel(po.getQuestionType(), rMap.get("assessLevel").toString());
                        }
                    }
                    quesMap.put("q5_sid",po.getSid());
                    quesMap.put("q5_num",q5_num);
                    quesMap.put("q5_level",q5_level);
                }
            }
            quesJson = JsonSerializer.objectToJson(quesMap);
        }
        return quesJson;
    }

    /**
     * 获取等级
     * v 5.1.6
     * @param quesType
     * @param quesLevel
     * @return
     */
    private String getStringLevel(Integer quesType,String quesLevel){
        String stringLevel = "";
        if (quesType != null){
            if (quesType == 1){
                if ("1".equals(quesLevel)){
                    stringLevel = "好";
                }else if ("2".equals(quesLevel)){
                    stringLevel = "一般";
                }else if ("3".equals(quesLevel)){
                    stringLevel = "差";
                }
            }else if (quesType == 2){
                if ("1".equals(quesLevel)){
                    stringLevel = "良好";
                }else if ("2".equals(quesLevel)){
                    stringLevel = "中等";
                }else if ("3".equals(quesLevel)){
                    stringLevel = "差";
                }
            }else if (quesType == 5){
                if ("1".equals(quesLevel)){
                    stringLevel = "良好";
                }else if ("2".equals(quesLevel)){
                    stringLevel = "中等";
                }else if ("3".equals(quesLevel)){
                    stringLevel = "较差";
                }
            }
        }
        return stringLevel;
    }

    @Override
    public List<FollowQuestionPO> getQuestionnaire(String followtype) {
        return followMapper.getQuestiondate(followtype);
    }

    @Override
    public List<FollowDrugPO> getDrugDICT(){
        List<FollowDrugPO>  drugPOList = followMapper.getDrugDICT();
        return drugPOList;
    }


    @Override
    public void createSZRemind(String doctorId, String memberId) {
        DoctorPO doctorPO = doctorService.getDoctorById(doctorId);
        MemberPO memberPO = memberService.getMemberById(memberId);
        FollowDTO dto = new FollowDTO();
        dto.setDoctorId(doctorPO.getDoctorId());
        dto.setLeaderId(doctorPO.getDoctorId());
        dto.setMemberId(memberPO.getMemberId());
        dto.setMemberName(memberPO.getMemberName());
        dto.setDoctorName(doctorPO.getDoctorName());
        dto.setFollowDt(DateHelper.getToday());
        dto.setPushMember("");
        dto.setFillFormBy(1);
        dto.setMemberInfo("{}");
        dto.setFollowType(1);
        dto.setTemplateId("-1");
        dto.setHospitalId("-1");
        String sid = insertFollowWithLock(dto);
        FollowRemindPO remindPO = new FollowRemindPO();
        String title = memberPO.getMemberName() + "患者的随访时间已到，请及时处理";
        remindPO.setTitle(title);  //随访任务详情
        remindPO.setType(FollowRemindConstant.REMIND_SZ);   //提醒类型
        remindPO.setFollowId(sid);  ///外键  随访id
        remindPO.setMemberId(memberPO.getMemberId());
        remindPO.setDoctorId(doctorPO.getDoctorId());
        remindPO.setMemberName(memberPO.getMemberName());
        remindPO.setIsDo("0");  //是否处理 1是 0否
        remindPO.setBeforeDay("0");  //提前多少天提醒默认0
        insertFollowRemind(remindPO);
    }

    @Override
    public PageResult<ListFollowPlatformRecordVO> pagerMemberFollow(DoctorSessionBO doctorSessionBO,ListMemberFollowDTO listMemberFollowDTO, PageRequest page) {
        List<String> committeeIdList = this.getDoctorCommitteeIdList(doctorSessionBO);
        listMemberFollowDTO.setCommitteeList(committeeIdList);
        PageHelper.startPage(page.getPage(), page.getRows());
        List<ListFollowPlatformRecordVO> followList = followMapper.listMemberFollowForPlatform(listMemberFollowDTO);
        if (!followList.isEmpty()){
            followList.forEach(x->{
                if (!StringUtils.isBlank(x.getBirthday())){
                    x.setAge(DateHelper.getAge(x.getBirthday()));
                }
            });
        }
        return new PageResult<>(followList);
    }

    @Override
    public PageResult<ListFollowPlatformRecordVO> pagerMemberUnDoFollow(DoctorSessionBO doctorSessionBO,ListMemberFollowDTO listMemberFollowDTO, PageRequest page) {
        List<String> committeeIdList = this.getDoctorCommitteeIdList(doctorSessionBO);
        listMemberFollowDTO.setCommitteeList(committeeIdList);
        PageHelper.startPage(page.getPage(), page.getRows());
        List<ListFollowPlatformRecordVO> followList = followMapper.listMemberUnDoFollowForPlatform(listMemberFollowDTO);
        if (!followList.isEmpty()){
            followList.forEach(x->{
                if (!StringUtils.isBlank(x.getBirthday())){
                    x.setAge(DateHelper.getAge(x.getBirthday()));
                }
            });
        }
        return new PageResult<>(followList);
    }

    @Override
    public Map<String, Object> committeeFollowStatics(DoctorSessionBO doctorSessionBO) {
        List<String> committeeIdList = this.getDoctorCommitteeIdList(doctorSessionBO);
        ListMemberFollowDTO listFollowDTO = new ListMemberFollowDTO();
        listFollowDTO.setCommitteeList(committeeIdList);
        listFollowDTO.setHospitalId(doctorSessionBO.getHospitalId());
        listFollowDTO.setStatus(FollowConstant.FOLLOW_STATUS_YES);
        listFollowDTO.setTypeList(Arrays.asList(FollowConstant.FOLLOW_TYPE_TWO_DIABETES,FollowConstant.FOLLOW_TYPE_HYP_JW));
        String currentQuarterStartTime = DateHelper.getCurrentQuarterStartTime();//本季度第一天
        String currentQuarterEndTime = DateHelper.getCurrentQuarterEndTime();//本季度最后一天
        String currentWeekStartTime = DateHelper.getCurrentWeekStartTime();
        String currentWeekEndTime = DateHelper.getCurrentWeekEndTime();
        String currentMonthStartTime = DateHelper.getMonthFirst(DateHelper.getToday())+DateHelper.DEFUALT_TIME_START;
        String currentMonthEndTime = DateHelper.getMonthLast(DateHelper.getToday())+ DateHelper.DEFUALT_TIME_END;
        String currentTodayStartTime = DateHelper.getToday()+DateHelper.DEFUALT_TIME_START;
        String currentTodayEndTime = DateHelper.getToday()+DateHelper.DEFUALT_TIME_END;

        //今日
        listFollowDTO.setStartDt(currentTodayStartTime);
        listFollowDTO.setEndDt(currentTodayEndTime);
        Long todayMemberNum = followMapper.countFollowMemberNum(listFollowDTO);
        Long todayFollowNum = followMapper.countFollowNum(listFollowDTO);
        //本周
        listFollowDTO.setStartDt(currentWeekStartTime);
        listFollowDTO.setEndDt(currentWeekEndTime);
        Long weekMemberNum = followMapper.countFollowMemberNum(listFollowDTO);
        Long weekFollowNum = followMapper.countFollowNum(listFollowDTO);
        //本月
        listFollowDTO.setStartDt(currentMonthStartTime);
        listFollowDTO.setEndDt(currentMonthEndTime);
        Long monthMemberNum = followMapper.countFollowMemberNum(listFollowDTO);
        Long monthFollowNum = followMapper.countFollowNum(listFollowDTO);
        //本季度
        listFollowDTO.setStartDt(currentQuarterStartTime);
        listFollowDTO.setEndDt(currentQuarterEndTime);
        Long quarterMemberNum = followMapper.countFollowMemberNum(listFollowDTO);
        Long quarterFollowNum = followMapper.countFollowNum(listFollowDTO);

        //本季度还未随访
        List<ListFollowPlatformRecordVO> quarterRestMemberList = followMapper.listMemberUnDoFollowForPlatform(listFollowDTO);

        Map<String, Object> result = new HashMap<>();
        result.put("todayMemberNum",todayMemberNum);
        result.put("todayFollowNum",todayFollowNum);
        result.put("weekMemberNum",weekMemberNum);
        result.put("weekFollowNum",weekFollowNum);
        result.put("monthMemberNum",monthMemberNum);
        result.put("monthFollowNum",monthFollowNum);
        result.put("quarterMemberNum",quarterMemberNum);
        result.put("quarterFollowNum",quarterFollowNum);
        result.put("quarterRestMemberNum",quarterRestMemberList.size());
        return result;
    }


    private List<String> getDoctorCommitteeIdList(DoctorSessionBO doctorSessionBO){
        String doctorId = doctorSessionBO.getDoctorId();
        Integer doctorLevel = doctorSessionBO.getDoctorLevel();
        if (null != doctorLevel  &&
                !doctorLevel.equals(DoctorConstant.DOCTOR_LEVEL_VILLAGE)){
            throw new BusinessException("当前账号不是村医,请确认");
        }
        List<HospitalCommitteePO> hospitalCommitteePOS = committeeService.listCommitteeByDoctorId(doctorId);
        if (hospitalCommitteePOS.size()==0){
            throw new BusinessException("当前账号未关联社区,请确认");

        }
        //社区id列表
        List<String>  committeeIdList = hospitalCommitteePOS.stream().map(HospitalCommitteePO::getId).collect(Collectors.toList());
        return committeeIdList;
    }
}
