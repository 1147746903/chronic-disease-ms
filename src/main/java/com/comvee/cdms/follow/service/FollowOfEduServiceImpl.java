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
//        调用知识模块，获取知识智能推荐
        KnowledgePlanBO knowledgePlanData = this.prescriptionApi.createKnowledgePlan(archivesJson,memberId,eohType);
        if (null != knowledgePlanData){
            //封装知识点
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
            throw new BusinessException("糖尿病首诊不存在!");
        }

        //处理知识
        String knowledgeListJson = followDTO.getKnowledgeListJson();
        if (!StringUtils.isBlank(knowledgeListJson)){
            //全部删除
            this.prescriptionMapper.deleteFollowKnowledgeByFollowId(follow.getForeignId());
            //全部添加
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
        //1、根据管理处方id，获取管理处方主表
//        PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptId);

        //处理用户标签
        List<String> memberTags = handleMemberTags(archivesJsonObj,rangeBO,memberId);
        System.out.println("用户属性标签列表为:" + memberTags);

        List<KnowledgeWeek> knowledgeWeekList = new ArrayList<KnowledgeWeek>();
        List<String> knowledgeTagList = new ArrayList<>();

        if(knowledgePlanList != null) {
            //根据用户标签重新排序学习文章
            knowledgePlanList = sortKnowledgePlanList(knowledgePlanList, archivesJsonObj,memberTags);
//            sortKnowledgePlanList(knowledgePlanList, archivesJsonObj,memberTags);

            //2、学习周期 一个月(4周) 三个月(12周)  半年(24周) 一年(48周) 默认 三个月(12周)
//            int eduCycle=4;
            int eduCycle= 4;
//            if(null != prescriptionVO.getEduCycle()){
//                eduCycle = prescriptionVO.getEduCycle();
//            }

            int numOfWeek = 2;//每周2篇文章
            int week = 0;  //第几周
            int max = eduCycle*numOfWeek > knowledgePlanList.size() ? knowledgePlanList.size() :  eduCycle*numOfWeek;
            List<Knowledge> knowledgeList = null;//每周对象里的文章列表
            for (int i = 0; i < max; i++) {
                ApiKnowledgePlanBO knowledgePlanModel = knowledgePlanList.get(i);
                if(i % numOfWeek == 0) {
                    week++;
                    knowledgeList = new ArrayList<>();
                    KnowledgeWeek knowledgeWeek = new KnowledgeWeek();
                    knowledgeWeek.setWeek(week);
                    knowledgeWeek.setWeekName( "第" + NumberTool.numToChinese(week) + "周");
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

//                //处理标签
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
        //构造用户标签 Personas
        Integer sex = null;
        String birthday = "";
        String bmi = "";
        boolean isHyp = false;//是否有高血压
        //目前用药情况
        String currentPharmacy = "";
        if(archivesJsonObj != null) {
            JSONObject basicObj = archivesJsonObj.getJSONObject("basic");
            if(basicObj != null) {
                String diabetes_date = basicObj.getString("diabetes_date"); //确诊时间
                if(!StringUtils.isBlank(diabetes_date)) {
                    //不到一年	首诊：患者档案-糖尿病现状-确诊时间（必填）
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
                    //中心肥胖	首诊-基本信息-腰围男性＞90cm，女性＞85cm（非必填）
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
                    memberTags.add("11-1"); //喝酒
                }

                if("1".equals(bs_sport)) {
                    //有运动习惯
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

            //处理并发症
            JSONObject complicationObj = archivesJsonObj.getJSONObject("complication");
            boolean isChd = false;//是否有冠心病
            if(complicationObj != null) {
                boolean isAllPass = false;
                String nephropathy = complicationObj.getString("nephropathy"); //糖尿病肾病
                String retinal = complicationObj.getString("retinal"); //糖尿病眼底病变  视网膜
                String pad = complicationObj.getString("pad"); //下肢血管病变
                String diabetic_foot = complicationObj.getString("diabetic_foot");  //糖尿病足
                String neuropathy = complicationObj.getString("neuropathy"); //周围神经病变
                String chd = complicationObj.getString("chd"); //冠心病
                //String hyp = complicationObj.getString("hyp"); //高血压

                //自主神经病变-为评估  ZZ03    冠心病未评估 QZ03
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
                    //无并发症	首诊：糖尿病并发症-各项选择未评估、确诊无/勾选无症状
                    memberTags.add(CourseTag.noComplications.code());
                }
                if ("QZ01".equals(complicationObj.getString("chd"))){
                    isChd = true;
                }
            }

            JSONObject memberComplicationObj = archivesJsonObj.getJSONObject("complication");
            if(memberComplicationObj != null) {
                String nephropathy = memberComplicationObj.getString("nephropathy"); //糖尿病肾病
                String retinal = memberComplicationObj.getString("retinal"); //糖尿病眼底病变  视网膜
                String retinal_date = memberComplicationObj.getString("retinal_date");//眼底病变检查日期
                String pad = memberComplicationObj.getString("pad"); //下肢血管病变
                String diabetic_foot = memberComplicationObj.getString("diabetic_foot");  //糖尿病足
                String neuropathy = memberComplicationObj.getString("neuropathy"); //周围神经病变
                String chd = memberComplicationObj.getString("chd"); //冠心病
                String chd_date = memberComplicationObj.getString("chd_date");//冠心病检查日期
                String dan = memberComplicationObj.getString("dan"); //自主神经病变

                //糖尿病并发症-糖尿病眼底病变，选择除确诊有以外选项
                if(!"SWM01".equals(retinal)) {
                    System.out.println("匹配到  眼底病变  12-1  标签");
                    memberTags.add("12-1");
                }

                //糖尿病并发症-糖尿病足，选择除确诊有以外选项
                if(!"TNBZ01".equals(diabetic_foot)) {
                    System.out.println("匹配到  病足预防  6-4  标签");
                    memberTags.add("6-4");
                }

                //下面匹配 少做检查 标签  糖尿病并发症-各类并发症中有选择未评估的，以及眼底病变的检查日期超过一年，冠心病检查日期超过6个月
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
                    System.out.println("匹配到  少做检查  6-5  标签");
                    memberTags.add("6-5");
                }
            }

            //饮食习惯
            ////常吃水果  患者档案-目前饮食方式-每日饮食情况以及加餐情况添加水果及制品一类
            ////加餐习惯 患者档案-目前饮食方式-加餐情况添加任意食品
            if(historyObj != null) {
                //加餐情况
                JSONArray bs_dinner_jc = JSON.parseArray(historyObj.getString("bs_dinner_jc"));
                JSONArray bs_jcnr = JSON.parseArray(historyObj.getString("bs_jcnr"));
                JSONArray bs_lunch_jc = JSON.parseArray(historyObj.getString("bs_lunch_jc"));
                //每日饮食情况
                JSONArray bs_wanczslx = JSON.parseArray(historyObj.getString("bs_wanczslx"));
                JSONArray bs_wuczslx = JSON.parseArray(historyObj.getString("bs_wuczslx"));
                JSONArray bs_zczslx = JSON.parseArray(historyObj.getString("bs_zczslx"));

                if ((bs_dinner_jc != null && bs_dinner_jc.size() > 0) || (bs_jcnr != null && bs_jcnr.size() > 0)
                        || (bs_lunch_jc != null && bs_lunch_jc.size() > 0)|| (bs_wanczslx != null && bs_wanczslx.size() > 0)
                        || (bs_wuczslx != null && bs_wuczslx.size() > 0) || (bs_zczslx != null && bs_zczslx.size() > 0)){
                    if (bs_wanczslx == null || bs_wanczslx.isEmpty()){
                        memberTags.add(CourseTag.notEatDinner.code());
                        System.out.println("匹配到标签[ 不吃晚餐    4-9]");
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
                    /*以下是  常吃水果 标签处理*/
                    boolean isEatFruits = false;
                    List<FoodItemBO> foodItems = prescriptionMapper.getEohFoodItemByIds(foodIds);
                    for (Iterator<FoodItemBO> iterator = foodItems.iterator(); iterator.hasNext();) {
                        FoodItemBO foodItemBO = (FoodItemBO) iterator.next();
                        if("1001004".equals(foodItemBO.getIngredientsType())) {
                            memberTags.add("4-4");
                            isEatFruits = true;
                            System.out.println("匹配到标签[常吃水果   4-4]");
                            break;
                        }
                    }
                    /*食荤 标签处理结束*/

                    /*以下是  少吃水果  标签处理*/
                    if(!isEatFruits) {
                        memberTags.add("4-8");
                        System.out.println("匹配到标签[ 少吃水果    4-8]");
                    }
                    /*少吃水果  标签处理结束*/


                    /*以下是  食荤  标签处理*/
                    boolean isEatMeat = false; //是否是食荤
                    for (Iterator<FoodItemBO> iterator = foodItems.iterator(); iterator.hasNext();) {
                        FoodItemBO foodItemBO = (FoodItemBO) iterator.next();
                        if("1001007".equals(foodItemBO.getIngredientsType())) {
                            memberTags.add("4-6");
                            isEatMeat = true;
                            System.out.println("匹配到标签[食荤   4-6]");
                            break;
                        }
                    }
                    /*食荤 标签处理结束*/

                    /*以下是  食素 标签处理*/
                    if(!isEatMeat) {
                        memberTags.add("4-7");
                        System.out.println("匹配到标签[食素   4-7]");
                    }
                    /*食素 标签处理结束*/


                }



                /*以下是  加餐习惯  标签处理*/
                if( (bs_dinner_jc != null && bs_dinner_jc.size() > 0)
                        || (bs_jcnr != null && bs_jcnr.size() > 0)
                        || (bs_lunch_jc != null && bs_lunch_jc.size() > 0)
                        ) {
                    memberTags.add("4-5");

                    System.out.println("匹配到标签[加餐习惯   4-5]");
                }
                /*加餐习惯 标签处理结束*/
            }

            //判断糖化
            JSONObject labObj = archivesJsonObj.getJSONObject("lab");
            if(labObj != null) {
                String lab_hba = labObj.getString("lab_hba");
                if(!StringUtils.isBlank(lab_hba)) {
                    float flab_hba = Float.valueOf(lab_hba);
                    if(flab_hba > 9) {
                        //糖化血红蛋白（高危）	控制目标-血糖情况>9.0%
                        memberTags.add(CourseTag.Hba1cHighRisk.code());
                    }

                    //查询控制目标
                    if(rangeBO != null && !StringUtils.isNullOrEmpty(rangeBO.getHighGlycosylatedVal())) {
                        float htr = Float.valueOf(rangeBO.getHighGlycosylatedVal());
                        if(flab_hba > htr) {
                            memberTags.add("5-5");
                        }
                    }
                }else {
                    memberTags.add("5-5");
                }

                //判断是否是高血脂
                boolean isHyperlipidemia = false;
                //判断高密度脂蛋白,总胆固醇
                String tc = labObj.getString("tc");//总胆固醇
                String hdl = labObj.getString("hdl");//高密度脂蛋白
                String ldl = labObj.getString("ldl");//低密度脂蛋白
                if(!StringUtils.isBlank(tc)) {
                    float ftc = Float.valueOf(tc);
                    if(ftc >= 5.4) {
                        isHyperlipidemia = true;
                    }
                }

                //低密度脂蛋白>=2.6mmol/L(未合并冠心病），低密度脂蛋白>=1.8mmol/L(合并冠心病）
                if(!StringUtils.isBlank(ldl)) {
                    float fldl = Float.valueOf(ldl);
                    if(isChd && fldl >= 1.8) {
                        isHyperlipidemia = true;
                    }else if(fldl >= 2.6){
                        isHyperlipidemia = true;
                    }
                }

                //高密度脂蛋白<=1.0mmol/L（男性），高密度脂蛋白<=1.3mmol/L（女性）
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
                    System.out.println("匹配到 高血脂 6-3  标签");
                    memberTags.add("6-3");
                }


            }else {
                memberTags.add("5-5");
            }
            //用药
            JSONObject pharmacyObj = archivesJsonObj.getJSONObject("pharmacy");
            if(pharmacyObj != null){
                currentPharmacy = pharmacyObj.getString("currentPharmacy");
            }
        }

        int age = -1;
        if(!StringUtils.isBlank(birthday)) {
            age = DateHelper.getAge(birthday); //用户年龄
            if(age >= 18 && age <= 45) {
                //青年	18~45（年龄从运动治疗的基本情况中得出）
                memberTags.add(CourseTag.youngPeople.code());
            }else if(age >= 46 && age <= 60) {
                //中年	46~60（年龄从运动治疗的基本情况中得出）
                memberTags.add(CourseTag.middleAgedPerson.code());
            }else if(age > 60) {
                //老年	60岁以上（年龄从运动治疗的基本情况中得出）
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
                //超重	24≤BMI≤26.9（控制目标-患者基本情况获得）
                memberTags.add(CourseTag.overweight.code());
            }else if(fbmi >= 27 && fbmi <= 29.9) {
                //肥胖	27≤BMI≤29.9（控制目标-患者基本情况获得）
                memberTags.add(CourseTag.obesity.code());
            }else if(fbmi >= 30) {
                //重度肥胖	BMI≥30（控制目标-患者基本情况获得）
                memberTags.add(CourseTag.severeObesity.code());
            }
        }

        //判断血糖
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        listBloodSugarDTO.setMemberId(memberId);
        listBloodSugarDTO.setStartDt(DateHelper.getDate(DateHelper.getDateAddDay(new Date(), -7), DateHelper.DATETIME_FORMAT));
        listBloodSugarDTO.setEndDt(DateHelper.getTime());
        List<ApiBloodSugarBO> bloodSugars = this.prescriptionApi.listSugar(listBloodSugarDTO);
        if(bloodSugars != null && bloodSugars.size() > 0) {
            boolean isLowBloodSugar = false; //是否出现过低血糖
            boolean isKfHight = false; //是否空腹高
            boolean isFkfHight = false;//是否非空腹高
            boolean isAbsAfterBreakfast = false; //是否早餐后血糖异常
            boolean isHightAfterMeal = false; //是否餐后高血糖
            boolean isAllNormal = true;
            for (Iterator<ApiBloodSugarBO> iterator = bloodSugars.iterator(); iterator.hasNext();) {
                ApiBloodSugarBO bloodSugarPO = (ApiBloodSugarBO) iterator.next();
                if(!StringUtils.isBlank(bloodSugarPO.getParamValue())) {
                    float fVal = Float.valueOf(bloodSugarPO.getParamValue());

                    if(fVal < 3.9) {
                        isLowBloodSugar = true;
                    }

                    if(ParamLogConstant.PARAM_CODE_BEFOREBREAKFAST.equals(bloodSugarPO.getParamCode())) {
                        //空腹
                        if(fVal >= 10) {
                            isKfHight = true;
                        }

                        if( ( fVal < 4.4 || fVal >7.0 ) ) {
                            isAllNormal = false;
                        }
                    }else {
                        //非空腹
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
                //低血糖	上周触发过任意低血糖逻辑 (血糖值：<3.9)
                memberTags.add(CourseTag.lowBloodSugar.code());

                if(isKfHight || isFkfHight) {
                    //血糖波动	一周内出现过低血糖及高血糖（低血糖<3.9；空腹≥10或非空腹≥16.7）
                    memberTags.add(CourseTag.bloodSugarFluctuation.code());
                }
            }

            if(isAbsAfterBreakfast) {//早餐后血糖异常
                memberTags.add("5-3");
                System.out.println("匹配到  早餐后血糖异常 5-3 标签");
            }
            if(isHightAfterMeal) {//餐后高血糖
                memberTags.add("10-1");
                System.out.println("匹配到  餐后高血糖  10-1 标签");
            }
            if(isAllNormal) {
                memberTags.add("10-2");
                System.out.println("匹配到  血糖正常  10-2 标签");
            }

        }else {
            //监测频率低	患者-血糖记录-7天未监测
            memberTags.add(CourseTag.lowMonitoringFrequency.code());
        }

        //查询30天的血糖记录
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
                System.out.println("匹配到  空腹血糖高  5-4 标签");
            }
        }

        /*
        RangeBO rangeBO = memberService.getMemberRange(prescriptionVO.getMemberId());
        if(rangeBO != null) {
            String highGlycosylatedVal = rangeBO.getHighGlycosylatedVal();
            if(!StringUtils.isBlank(highGlycosylatedVal)) {
                float fhighGlycosylatedVal = Float.valueOf(highGlycosylatedVal);
                if(fhighGlycosylatedVal > 9) {
                    //糖化血红蛋白（高危）	控制目标-血糖情况>9.0%
                    memberTags.add(CourseTag.Hba1cHighRisk.code());
                }
            }
        }
        */


        if(isHyp) {
            memberTags.add("6-2");
            System.out.println("匹配到  高血压  6-2 标签");
        }

        //查询用户的检测方案
        Map<String,Object> queryMonitorPlanMap = new HashMap<>();
        queryMonitorPlanMap.put("memberId", memberId);
        queryMonitorPlanMap.put("planType", "1");
        List<MemberMonitorPlanPO> monitorPlans = memberMonitorPlanMapper.listMonitorPlanHistory(queryMonitorPlanMap);
        if(monitorPlans != null && monitorPlans.size() > 0) {
            memberTags.add("10-3");
            System.out.println("匹配到  正确使用血糖仪  10-3 标签");

            memberTags.add("10-4");
            System.out.println("匹配到  正确监测流程  10-4 标签");
        }

        //判断使用胰岛素
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
            System.out.println("匹配到  胰岛素  13-1  标签");

            //判断女性胰岛素使用者标签:患者档案-用药情况-有勾选胰岛素，且性别为女，年龄大于20岁
            if(sex != null && sex == 2) {
                if(age > 20) {
                    memberTags.add("13-3");
                    System.out.println("匹配到  女性胰岛素使用者 13-3  标签");
                }
            }

            //判断使用胰岛素未达标   患者档案-用药情况-有勾选胰岛素，且在管理处方-血糖监测-治疗阶段选择未达标
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
                        if(jsonObj != null && !StringUtils.isBlank(jsonObj.getString("schemeName"))  && jsonObj.getString("schemeName").contains("未达标") ) {
                            memberTags.add("13-2");
                            System.out.println("匹配到  使用胰岛素未达标 13-2  标签");
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
                    List<ApiKnowledgePlanBO> ysList = new ArrayList<>();  //饮食
                    List<ApiKnowledgePlanBO> ydList = new ArrayList<>();  //运动
                    List<ApiKnowledgePlanBO> yyList = new ArrayList<>();  //用药
                    List<ApiKnowledgePlanBO> bfzList = new ArrayList<>();  //并发症
                    List<ApiKnowledgePlanBO> jcList = new ArrayList<>();  //监测
                    List<ApiKnowledgePlanBO> shfsList = new ArrayList<>();  //生活方式
                    List<ApiKnowledgePlanBO> ydsList = new ArrayList<>();  //胰岛素
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
            knowledgePlanList.removeAll(result);  //移除已经存在的课程
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
     * @param num  随机取的个数
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
//                //取得list 中下标为j 的数据存储到 listRandom 中
//                result.add(po.get(j));
//                //把已取到的数据移除,避免下次再次取到出现重复
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
     * 患者是否使用了胰岛素
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
