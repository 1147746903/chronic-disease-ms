package com.comvee.cdms.follow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.defender.common.cfg.ConstantCourse;
import com.comvee.cdms.defender.constenum.CourseTag;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorPlanMapper;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.common.cfg.ParamLogConstant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.NumberTool;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.follow.cfg.FollowConstant;
import com.comvee.cdms.follow.model.FollowDTO;
import com.comvee.cdms.follow.po.MainFollowPO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.GetMemberDrugItemDTO;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberDrugItemPO;
import com.comvee.cdms.member.po.MemberDrugRecordPO;
import com.comvee.cdms.prescription.bo.*;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.GetPrescriptionDetailDTO;
import com.comvee.cdms.prescription.dto.ListMemberPrescriptionDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.MemberPrescriptionPO;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.po.PrescriptionKnowledgePO;
import com.comvee.cdms.prescription.service.PrescriptionApiI;
import com.comvee.cdms.prescription.tool.KnowledgeTool;
import com.comvee.cdms.prescription.vo.eduplan.Knowledge;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeVO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeWeek;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wyc
 * @date 2019/12/30 11:40
 */

@Service("followOfEduService")
public class FollowOfEduServiceImpl implements FollowOfEduService {

    @Autowired
    private PrescriptionApiI prescriptionApi;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberMonitorPlanMapper memberMonitorPlanMapper;

    @Autowired
    private FollowServiceI followService;

    @Override
    public KnowledgeVO intelligentFollowOfEdu(String archivesJson, String memberId,RangeBO rangeBO,Integer eohType) {
        KnowledgeVO knowledgeVO = new KnowledgeVO();
//        ?????????????????????????????????????????????
        KnowledgePlanBO knowledgePlanData = this.prescriptionApi.createKnowledgePlan(archivesJson,memberId,eohType);
        if (null != knowledgePlanData){
            //???????????????
            List<ApiKnowledgePlanBO> knowledgePlanList = knowledgePlanData.getKnowledges();
            JSONObject archivesObj = null;
            if(!StringUtils.isBlank(archivesJson)){
                archivesObj = JSON.parseObject(archivesJson);
            }
            this.handleKnowledgeWeekOfEdu(knowledgePlanList, archivesObj, knowledgeVO,rangeBO,memberId);
        }
        return knowledgeVO;
    }

    @Override
    public void saveFollowEdu(FollowDTO followDTO) {
        MainFollowPO follow = this.followService.getMainFollowByFidAndType(followDTO.getSid(), FollowConstant.FOLLOW_TYPE_FIRST);
        if (follow == null){
            throw new BusinessException("????????????????????????!");
        }

        //????????????
        String knowledgeListJson = followDTO.getKnowledgeListJson();
        if (!StringUtils.isBlank(knowledgeListJson)){
            //????????????
            this.prescriptionMapper.deleteFollowKnowledgeByFollowId(follow.getForeignId());
            //????????????
            List<PrescriptionKnowledgePO> knowledgeList = KnowledgeTool.knowledgeListHandler(knowledgeListJson);

            if (null != knowledgeList && knowledgeList.size() > 0){
                for (PrescriptionKnowledgePO prescriptionKnowledgePO : knowledgeList) {
                    prescriptionKnowledgePO.setId(DaoHelper.getSeq());
                    prescriptionKnowledgePO.setMemberId(follow.getMemberId());
                    prescriptionKnowledgePO.setPrescriptionId("-1");
                    prescriptionKnowledgePO.setFollowId(follow.getForeignId());
                    prescriptionKnowledgePO.setKnowledgeType(PrescriptionConstant.KNOWLEDGE_TYPE_FIRST);

                    this.prescriptionApi.addMemberCourse(prescriptionKnowledgePO.getArticleId(),follow.getMemberId(), "2", "1", Long.valueOf(prescriptionKnowledgePO.getId()));
                }
                this.prescriptionMapper.batchInsertPrescriptionKnowledge(knowledgeList);

            }
        }
    }

    @Override
    public List<PrescriptionKnowledgePO> listFollowKnowledge(String followId) {
        return this.prescriptionMapper.listFollowKnowledge(followId);
    }

    private List<PrescriptionKnowledgePO> getKnowledgeListByWeek(int week, List<PrescriptionKnowledgePO> knowledgeList){
        List<PrescriptionKnowledgePO> subKnowledgeList = new ArrayList<>();
        for (Iterator<PrescriptionKnowledgePO> iterator = knowledgeList.iterator(); iterator.hasNext();) {
            PrescriptionKnowledgePO prescriptionKnowledgePO = (PrescriptionKnowledgePO) iterator.next();

            if(prescriptionKnowledgePO.getWeek() != null && prescriptionKnowledgePO.getWeek() == week) {
                subKnowledgeList.add(prescriptionKnowledgePO);
            }

        }
        return subKnowledgeList;
    }


    private void handleKnowledgeWeekOfEdu(List<ApiKnowledgePlanBO> knowledgePlanList, JSONObject archivesJsonObj, KnowledgeVO knowledgeVO, RangeBO rangeBO, String memberId) {
        //1?????????????????????id???????????????????????????
//        PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptId);

        //??????????????????
        List<String> memberTags = handleMemberTags(archivesJsonObj,rangeBO,memberId);
        System.out.println("???????????????????????????:" + memberTags);

        List<KnowledgeWeek> knowledgeWeekList = new ArrayList<KnowledgeWeek>();
        List<String> knowledgeTagList = new ArrayList<>();

        if(knowledgePlanList != null) {
            //??????????????????????????????????????????
            knowledgePlanList = sortKnowledgePlanList(knowledgePlanList, archivesJsonObj,memberTags);
//            sortKnowledgePlanList(knowledgePlanList, archivesJsonObj,memberTags);

            //2??????????????? ?????????(4???) ?????????(12???)  ??????(24???) ??????(48???) ?????? ?????????(12???)
//            int eduCycle=4;
            int eduCycle= 4;
//            if(null != prescriptionVO.getEduCycle()){
//                eduCycle = prescriptionVO.getEduCycle();
//            }

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
                knowledge.setHairDownStatus(knowledgePlanModel.getHairDownStatus());
                knowledge.setFollowStatus(knowledgePlanModel.getFollowStatus());
                knowledge.setLearnStatus(knowledgePlanModel.getLearnStatus());
                knowledgeList.add(knowledge);

//                //????????????
                if(!knowledgeTagList.contains(knowledgePlanModel.getKnowledge())) {
                    knowledgeTagList.add(knowledgePlanModel.getKnowledge());
                }
            }
        }
        knowledgeVO.setKnowledgeWeekList(knowledgeWeekList);
        knowledgeVO.setKnowledgeTagList(knowledgeTagList);
    }



    private List<String> handleMemberTags(JSONObject archivesJsonObj,RangeBO rangeBO,String memberId) {
        List<String> memberTags = new ArrayList<>();
        //?????????????????? Personas
        Integer sex = null;
        String birthday = "";
        String bmi = "";
        boolean isHyp = false;//??????????????????
        //??????????????????
        String currentPharmacy = "";
        if(archivesJsonObj != null) {
            JSONObject basicObj = archivesJsonObj.getJSONObject("basic");
            if(basicObj != null) {
                String diabetes_date = basicObj.getString("diabetes_date"); //????????????
                if(!StringUtils.isBlank(diabetes_date)) {
                    //????????????	?????????????????????-???????????????-????????????????????????
                    int cdays = DateHelper.dateCompareGetDay(DateHelper.getDate(new Date(), "yyyy-MM-dd"), diabetes_date);
                    if(Math.abs(cdays) < 365) {
                        memberTags.add(CourseTag.lessThanOneYear.code());
                    }
                }
                if (!StringUtils.isBlank(basicObj.getString("sex"))){
                    sex = Integer.parseInt(basicObj.getString("sex"));
                }
                birthday = basicObj.getString("birthday");
            }


            JSONObject signObj = archivesJsonObj.getJSONObject("sign");
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
                bmi = signObj.getString("bmi");
            }
            //"drink_rate": "YJQK01",
            JSONObject historyObj = archivesJsonObj.getJSONObject("history");
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

            JSONObject anamnesisObj = archivesJsonObj.getJSONObject("anamnesis");
            if (anamnesisObj !=  null){
                if ("1".equals(anamnesisObj.getString("essential_hyp"))){
                    isHyp = true;
                }
            }

            //???????????????
            JSONObject complicationObj = archivesJsonObj.getJSONObject("complication");
            boolean isChd = false;//??????????????????
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
                if ("QZ01".equals(complicationObj.getString("chd"))){
                    isChd = true;
                }
            }

            JSONObject memberComplicationObj = archivesJsonObj.getJSONObject("complication");
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
                    if (bs_wanczslx == null || bs_wanczslx.isEmpty()){
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
            JSONObject labObj = archivesJsonObj.getJSONObject("lab");
            if(labObj != null) {
                String lab_hba = labObj.getString("lab_hba");
                if(!StringUtils.isBlank(lab_hba)) {
                    float flab_hba = Float.valueOf(lab_hba);
                    if(flab_hba > 9) {
                        //??????????????????????????????	????????????-????????????>9.0%
                        memberTags.add(CourseTag.Hba1cHighRisk.code());
                    }

                    //??????????????????
                    if(rangeBO != null && !StringUtils.isNullOrEmpty(rangeBO.getHighGlycosylatedVal())) {
                        float htr = Float.valueOf(rangeBO.getHighGlycosylatedVal());
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
            //??????
            JSONObject pharmacyObj = archivesJsonObj.getJSONObject("pharmacy");
            if(pharmacyObj != null){
                currentPharmacy = pharmacyObj.getString("currentPharmacy");
            }
        }

        int age = -1;
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

        if(!StringUtils.isBlank(bmi)) {
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
        listBloodSugarDTO.setMemberId(memberId);
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

                        if( ( fVal < 4.4 || fVal >7.0 ) ) {
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
        listBloodSugarDTO.setMemberId(memberId);
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
        queryMonitorPlanMap.put("memberId", memberId);
        queryMonitorPlanMap.put("planType", "1");
        List<MemberMonitorPlanPO> monitorPlans = memberMonitorPlanMapper.listMonitorPlanHistory(queryMonitorPlanMap);
        if(monitorPlans != null && monitorPlans.size() > 0) {
            memberTags.add("10-3");
            System.out.println("?????????  ?????????????????????  10-3 ??????");

            memberTags.add("10-4");
            System.out.println("?????????  ??????????????????  10-4 ??????");
        }

        //?????????????????????
//        boolean isUseInsulin = isUseInsulin(memberId);
        boolean isUseInsulin = false;
        if(!StringUtils.isBlank(currentPharmacy)){
            if(currentPharmacy.indexOf("4") >= 0){
                isUseInsulin = true;
            }
        }
//        boolean isUseInsulin = currentPharmacy.indexOf("4") >= 0 ? true : false;
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
            listMemberPrescriptionDTO.setMemberId(memberId);
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

        return memberTags;
    }

    private List<ApiKnowledgePlanBO> sortKnowledgePlanList(List<ApiKnowledgePlanBO> knowledgePlanList,JSONObject archivesJsonObj,List<String> memberTags){

        List<ApiKnowledgePlanBO> result = new ArrayList<>();
        if(archivesJsonObj != null) {
            JSONObject historyObj = archivesJsonObj.getJSONObject("history");
            if(historyObj != null) {
                String knowledge = historyObj.getString("know_knowledge");
                if (!StringUtils.isBlank(knowledge)){
                    List<ApiKnowledgePlanBO> ysList = new ArrayList<>();  //??????
                    List<ApiKnowledgePlanBO> ydList = new ArrayList<>();  //??????
                    List<ApiKnowledgePlanBO> yyList = new ArrayList<>();  //??????
                    List<ApiKnowledgePlanBO> bfzList = new ArrayList<>();  //?????????
                    List<ApiKnowledgePlanBO> jcList = new ArrayList<>();  //??????
                    List<ApiKnowledgePlanBO> shfsList = new ArrayList<>();  //????????????
                    List<ApiKnowledgePlanBO> ydsList = new ArrayList<>();  //?????????
                    for (ApiKnowledgePlanBO planBO : knowledgePlanList) {
                        if (!StringUtils.isBlank(planBO.getKnowledgeCode())){
                            if (ConstantCourse.COURSE_TYPE_YS.equals(planBO.getKnowledgeCode())){
                                ysList.add(planBO);
                            }else if (ConstantCourse.COURSE_TYPE_YD.equals(planBO.getKnowledgeCode())){
                                ydList.add(planBO);
                            }else if (ConstantCourse.COURSE_TYPE_YY.equals(planBO.getKnowledgeCode())){
                                yyList.add(planBO);
                            }else if (ConstantCourse.COURSE_TYPE_BSZ.equals(planBO.getKnowledgeCode())){
                                bfzList.add(planBO);
                            }else if (ConstantCourse.COURSE_TYPE_JC.equals(planBO.getKnowledgeCode())){
                                jcList.add(planBO);
                            }else if (ConstantCourse.COURSE_TYPE_SHFS.equals(planBO.getKnowledgeCode())){
                                shfsList.add(planBO);
                            }else if (ConstantCourse.COURSE_TYPE_YDS.equals(planBO.getKnowledgeCode())){
                                ydsList.add(planBO);
                            }
                        }
                    }
                    if (knowledge.contains(ConstantCourse.KNOWLEDGE_TYPE_YS)){
                        result.addAll(getRandomList(4,ysList));
                    }
                    if (knowledge.contains(ConstantCourse.KNOWLEDGE_TYPE_YD)){
                        result.addAll(getRandomList(4,ydList));
                    }
                    if (knowledge.contains(ConstantCourse.KNOWLEDGE_TYPE_YY)){
                        result.addAll(getRandomList(4,yyList));
                    }
                    if (knowledge.contains(ConstantCourse.KNOWLEDGE_TYPE_BSZ)){
                        result.addAll(getRandomList(4,bfzList));
                    }
                    if (knowledge.contains(ConstantCourse.KNOWLEDGE_TYPE_JC)){
                        result.addAll(getRandomList(4,jcList));
                    }
                    if (knowledge.contains(ConstantCourse.KNOWLEDGE_TYPE_SHFS)){
                        result.addAll(getRandomList(4,shfsList));
                    }
                    if (knowledge.contains(ConstantCourse.KNOWLEDGE_TYPE_YDS)){
                        result.addAll(getRandomList(4,ydsList));
                    }
                }

            }
        }
        if (null != result && result.size() >0){
            knowledgePlanList.removeAll(result);  //???????????????????????????
        }


        Collections.sort(knowledgePlanList, new Comparator<ApiKnowledgePlanBO>() {

            @Override
            public int compare(ApiKnowledgePlanBO o1, ApiKnowledgePlanBO o2) {
                int diff = containsTagNum(memberTags,o2.getTags()) - containsTagNum(memberTags,o1.getTags());
                return diff;
            }});
        result.addAll(knowledgePlanList);
        return result;
    }

    /**
     *
     * @param num  ??????????????????
     * @param po
     * @return
     */
    private List<ApiKnowledgePlanBO> getRandomList(int num,List<ApiKnowledgePlanBO> po){
        List<ApiKnowledgePlanBO> result = new ArrayList<>();
        if (po.size() > num){
            for (int i = 0; i < num; i++) {
                result.add(po.get(i));
//                Random random = new Random();
//                Math.random();
//                int j = random.nextInt(po.size() - 1);
//                //??????list ????????????j ?????????????????? listRandom ???
//                result.add(po.get(j));
//                //???????????????????????????,????????????????????????????????????
//                po.remove(j);
            }
        }else {
            return po;
        }

        return result;
    }


    public static void main(String[] args) {
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
//            if(knowledgeTag.contains(memberTag)) {
//                cnt ++;
//            }
        }
        return cnt;
    }

    private void fillFoodIds(List<String> foodIds,JSONArray jsonArr) {
        if(jsonArr != null && jsonArr.size() > 0) {
            for (int i = 0; i < jsonArr.size(); i++) {
                foodIds.add(jsonArr.getJSONObject(i).getString("id"));
            }
        }
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

}
