package com.comvee.cdms.prescription.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.prescription.bo.*;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.cfg.PrescriptionHelperOfDiet;
import com.comvee.cdms.prescription.dto.*;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.NutritionCateringPO;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import com.comvee.cdms.prescription.vo.*;
import com.google.common.base.Joiner;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("prescriptionOfDietService")
public class PrescriptionOfDietServiceImpl implements PrescriptionOfDietServiceI {

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private PrescriptionServiceI prescriptionService;

    @Autowired
    private PrescriptionApiI prescriptionApi;

    @Autowired
    private DietCacheI dietCache;

    @Override
    public PrescriptionForDietVO intelligentRecommendationOfDiet(String baseJson) {

        //根据baseJson 初始化 BaseParamOfDietDTO 参数
        BaseParamOfDietDTO baseParamOfDietDTO = JSONObject.parseObject(baseJson,BaseParamOfDietDTO.class);
        GetPrescriptionDTO dto = new GetPrescriptionDTO();
        dto.setSid(baseParamOfDietDTO.getPrescriptionId());
        PrescriptionPO prescription = this.prescriptionMapper.getPrescriptionByParam(dto);
        GetPrescriptionDetailDTO detailDTO = new GetPrescriptionDetailDTO();
        detailDTO.setType(PrescriptionConstant.MODULE_TYPE_DIET);
        detailDTO.setPrescriptionId(baseParamOfDietDTO.getPrescriptionId());
        PrescriptionDetailPO prescriptionDetail = this.prescriptionMapper.getPrescriptionDetailByParam(detailDTO);
        baseParamOfDietDTO.setMemberId(prescription.getMemberId());

        PrescriptionForDietVO model = new PrescriptionForDietVO();
        model.setSid(prescriptionDetail.getSid());

        //1.推荐热量范围
        HeatMaxAndMinOfDietBO heatMaxAndMinOfDietBO = null;
        if (prescription.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_FAT){ // 肥胖
            heatMaxAndMinOfDietBO = PrescriptionHelperOfDiet.getHeatMaxAndMinFat(baseParamOfDietDTO.getHeight(),
                    baseParamOfDietDTO.getWeight(),baseParamOfDietDTO.getLabourIntensity());
        }
        else if (prescription.getEohType()== 1){ //妊娠
            heatMaxAndMinOfDietBO = PrescriptionHelperOfDiet.getHeatMaxAndMinRS(baseParamOfDietDTO.getHeight(),
                    baseParamOfDietDTO.getProgestationalWeight(),baseParamOfDietDTO.getGestationalWeeks());
        }else {  //非妊娠
            heatMaxAndMinOfDietBO = PrescriptionHelperOfDiet.getHeatMaxAndMin(baseParamOfDietDTO.getHeight(),
                    baseParamOfDietDTO.getWeight(),baseParamOfDietDTO.getLabourIntensity());
        }


        //2.推荐食谱数据源（下拉框）
        GetNutritionCateringDTO dto1 = new GetNutritionCateringDTO();
        dto1.setEohType(prescription.getEohType()); //糖尿病类型
        List<String> doctorIdList = this.prescriptionApi.getDoctorIdList(prescription.getDoctorId(),1);
        dto1.setDoctorIdList(doctorIdList);
        if (prescription.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_FAT) {  // 肥胖
            dto1.setMealsModel("1");
        }
        List<NutritionCateringPO> nutritionCateringPOS = this.dietCache.loadNutritionCateringList(dto1);
        List<NutritionCateringTreeVO> nutritionCateringTreeVOS = PrescriptionHelperOfDiet.nCMLConvertToEFAML(nutritionCateringPOS);

        this.doPrescriptionNCTreeTot(nutritionCateringTreeVOS,prescription.getVersion());
        //3.当前推荐食谱份数关系样例+推荐食谱详细样例(表格)
        NutritionCateringTreeVO currentNutritionCateringTreeVO = null;
        Map<String, Double>  heatData = heatMaxAndMinOfDietBO.getHeatData();
        if (prescription.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_FAT){  // 肥胖
            if (heatMaxAndMinOfDietBO!=null) {
                Double preTotalHeatMin = Math.abs(StringUtils.converParamToDouble(heatMaxAndMinOfDietBO.getHeatMin()));
                currentNutritionCateringTreeVO = PrescriptionHelperOfDiet.getCurrentNutritionCateringByHeat(nutritionCateringTreeVOS,
                        preTotalHeatMin.toString());
            }
        }else if(heatData!=null){
            Double preTotalHeatMin = Math.abs(StringUtils.converParamToDouble(heatData.get("heatmin")));
            if(preTotalHeatMin!=null){
                currentNutritionCateringTreeVO = PrescriptionHelperOfDiet.getCurrentNutritionCateringByHeat(nutritionCateringTreeVOS,
                        preTotalHeatMin.toString());
            }
        }

        //4.获取推荐的常见主副食推荐（以一份的量为例）
        //20200409 下面两行代码为饮食技巧与贴士里常见食物交换法及互换举例图片展示
        List<String> ingredientsItemId = PrescriptionHelperOfDiet.getDefaultEohIngredientsItemIds();
        List<FoodItemBO> ingredientsItems = this.dietCache.listEohIngredientsItemByIds(ingredientsItemId);

        //5.目前饮食情况
        Map<String, List<FoodItemBO>> itemMap = this.getEohIngredientsForArchives(baseParamOfDietDTO.getCurrentDietInfo(),
                prescription.getVersion());
        List<FoodItemBO> itemList = itemMap.get("itemList");
        List<FoodItemBO> otherItemList = itemMap.get("otherItemList");

        FoodAllocationVO dailyFoodAllocation = PrescriptionHelperOfDiet.getDailyFoodAllocation(itemList, otherItemList);

        List<FoodNutrientBO> bos = this.prescriptionMapper.listFoodNutrient(null,null);
        //非妊娠智能推荐包括目前饮食分析
        List<ProblemSuggestionVO> problemSuggestionList = null;
        if (prescription.getEohType()==0){
            //6.目前饮食分析
            DietOutPutDTO dietOutPutDTO = this.getDietOutPutDTO(baseParamOfDietDTO,currentNutritionCateringTreeVO,
                    dailyFoodAllocation,heatMaxAndMinOfDietBO,prescription.getVersion());
             problemSuggestionList = PrescriptionHelperOfDiet.getDietOutPut(dietOutPutDTO);
        }
        //高血压处方
        else if(prescription.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_HYP){
            //当前饮食分析 （按克）
            CurrentDietAnalyseVO currentDietAnalyseVO = PrescriptionHelperOfDiet.getCurrentDietAnalyse(itemList ,otherItemList,bos,prescription.getEohType());
            model.setCurrentDietAnalyse(currentDietAnalyseVO);
            //高血压饮食问题/建议
            problemSuggestionList = PrescriptionHelperOfDiet.getHypProblemSuggestion(dailyFoodAllocation.getDailyHeat() ,heatMaxAndMinOfDietBO ,baseParamOfDietDTO , itemList ,otherItemList);
        }else if (prescription.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_FAT){
            //当前饮食分析 （按克）
            CurrentDietAnalyseVO currentDietAnalyseVO = PrescriptionHelperOfDiet.getCurrentDietAnalyse(itemList ,otherItemList,bos,prescription.getEohType());
            model.setCurrentDietAnalyse(currentDietAnalyseVO);
            //肥胖饮食问题/建议
            problemSuggestionList = this.getFatProblemSuggestion(baseParamOfDietDTO);
        }

        //封装返回内容
        //目前饮食情况&分析
        model.setDailyFoodAllocation(dailyFoodAllocation);
        model.setProblemSuggestions(problemSuggestionList);

        //推荐食谱
        model.setHeatMaxAndMinOfDietBO(heatMaxAndMinOfDietBO);
        model.setNutritionCateringTreeVOS(nutritionCateringTreeVOS);
        model.setNutritionCateringTreeVO(currentNutritionCateringTreeVO);

        //饮食附录与贴士
        model.setIngredientsItemList(ingredientsItems);

        //饮食技巧
        String prescriptionAppendix = "";
        //如果是肥胖处方
        if (prescription.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_FAT){
            prescriptionAppendix = fatDietSkillHandler(prescription.getMemberId() , prescription.getDoctorId() );
        }else if(prescription.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_HYP){  //如果是高血压处方
            prescriptionAppendix = hypDietSkillHandler(prescription.getMemberId() , prescription.getDoctorId() ,problemSuggestionList);
        }else{
            prescriptionAppendix = "1,2,3";
        }
        model.setPrescriptionAppendix(prescriptionAppendix);

        List<String> dietTips = null;
        if (prescription.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_FAT){ // 肥胖
            dietTips = PrescriptionHelperOfDiet.getFatDietTips();
        }else if(prescription.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_HYP){
            dietTips = PrescriptionHelperOfDiet.getHypDietTips();
        }else{
            dietTips = PrescriptionHelperOfDiet.getDietTips();
        }
        model.setDietTips(dietTips);
        //设置dietJson-model
        PrescriptionDetailOfDietBO bo = new PrescriptionDetailOfDietBO();
        bo.setDailyFoodAllocation(dailyFoodAllocation);
        bo.setHeatMaxAndMinOfDietBO(heatMaxAndMinOfDietBO);
        bo.setIngredientsItemList(ingredientsItems);
        bo.setProblemSuggestions(problemSuggestionList);
        if(currentNutritionCateringTreeVO!=null
                && currentNutritionCateringTreeVO.getNutritionCateringItems()!=null
                && currentNutritionCateringTreeVO.getNutritionCateringItems().size()>0){
            bo.setNutritionCateringId(currentNutritionCateringTreeVO.getNutritionCateringItems().get(0).getNutritionCateringId());
        }
        bo.setPrescriptionAppendix(prescriptionAppendix);
        bo.setDietTips(dietTips);
        model.setDietInfo(JSONObject.toJSONString(bo));

        //返回
        return model;
    }

    /**
     * 高血压饮食技巧处理
     * @param memberId
     * @param problemSuggestionList
     * @return
     */
    private String hypDietSkillHandler(String memberId , String doctorId ,List<ProblemSuggestionVO> problemSuggestionList){
        /**
         * 1 2 3 4 5 分别对应A	常见富含钠的食物 B	常见食物脂肪含量  C	常见高胆固醇食物  D	常见富含钾的食物 E	食物嘌呤含量分类
         */

        TreeSet<Integer> skillList = new TreeSet<>();
        /**
         * 高血压患者必备附录	A，D
         */
        skillList.add(1);
        skillList.add(4);

        ApiMemberBO apiMemberBO = this.prescriptionApi.getMemberByMemberId(memberId);
        ApiMemberArchivesBO apiMemberArchivesBO = this.prescriptionApi.getMemberArchivesById(memberId ,doctorId);
        MemberLatestSignVO memberLatestSignVO = this.prescriptionApi.getMemberLatestSign(memberId);
        CheckoutBloodFatBO checkoutBloodFatBO = memberLatestSignVO.getBloodFat();
        String archiveString = apiMemberArchivesBO.getArchivesJson();
        JSONObject archiveJson = null;
        if(!StringUtils.isBlank(archiveString)){
            archiveJson = JSONObject.parseObject(archiveString);
        }
        if(checkoutBloodFatBO != null){
            /**
             * 总胆固醇（mmol/L）	 ≤4.49	≥4.5
             */
            if(!StringUtils.isBlank(checkoutBloodFatBO.getTc()) && Float.parseFloat(checkoutBloodFatBO.getTc() ) >= 4.5f){
                skillList.add(3);
            }
            /**
             * 甘油三酯（mmol/L）	 ≤1.69	≥1.7
             */
            if(!StringUtils.isBlank(checkoutBloodFatBO.getTriglyceride()) && Float.parseFloat(checkoutBloodFatBO.getTriglyceride() ) >= 1.7f){
                skillList.add(2);
            }
            /**
             * 高密度脂蛋白（mmol/L）	男：≥1.1            女：≥1.31 	男：≤1.10           女：≤1.30
             */
            if("1".equals(apiMemberBO.getSex()) && !StringUtils.isBlank(checkoutBloodFatBO.getHdl()) && Float.parseFloat(checkoutBloodFatBO.getHdl()) < 1.1f){
                skillList.add(3);
            }
            if("2".equals(apiMemberBO.getSex()) && !StringUtils.isBlank(checkoutBloodFatBO.getHdl()) && Float.parseFloat(checkoutBloodFatBO.getHdl()) <= 1.3f){
                skillList.add(3);
            }
            if(archiveJson != null){
                JSONObject complication = archiveJson.getJSONObject("complication");
                if(complication != null){
                    String chd = complication.getString("chd");
                    if("QZ01".equals(chd) && !StringUtils.isBlank(checkoutBloodFatBO.getLdl()) && Float.parseFloat(checkoutBloodFatBO.getLdl()) >= 1.8f){
                        skillList.add(3);
                    }else if(!StringUtils.isBlank(checkoutBloodFatBO.getLdl()) && Float.parseFloat(checkoutBloodFatBO.getLdl()) >= 2.6f){
                        skillList.add(3);
                    }
                }
            }
        }
       /* if(archiveJson != null){
            JSONObject lab = archiveJson.getJSONObject("lab");
            if(lab != null){
                Float lab_xns = lab.getFloat("lab_xns");
                if(lab_xns != null && lab_xns.floatValue() > 420f){
                    skillList.add(5);
                }
            }
        }*/
        /**
         *  尿酸 > 420
         */
        if (!StringUtils.isBlank(checkoutBloodFatBO.getUric()) && Float.parseFloat(checkoutBloodFatBO.getUric() ) > 420f){
            skillList.add(5);
        }
        /**
         * 饮食问题	对应附录
         * 饮食结构不合理，肉蛋类食物摄入不足	B、C
         * 饮食结构不合理，肉蛋类食物摄入过多	B、C
         */
        if(problemSuggestionList != null && problemSuggestionList.size() > 0){
            for(ProblemSuggestionVO problemSuggestionVO : problemSuggestionList){
                if(problemSuggestionVO.getProblem().startsWith("饮食结构不合理，肉蛋类食物")){
                    skillList.add(2);
                    skillList.add(3);
                    break;
                }
            }
        }

        return Joiner.on(",").join(skillList);
    }

    @Override
    public PrescriptionForDietVO getPrescriptionDiet(String prescriptionId) {
        PrescriptionForDietVO model = new PrescriptionForDietVO();
        //处方信息
        GetPrescriptionDTO dto = new GetPrescriptionDTO();
        dto.setSid(prescriptionId);
        PrescriptionPO prescription = this.prescriptionMapper.getPrescriptionByParam(dto);
        //处方状态 1待制定 2草稿 3完成
        Integer schedule = prescription.getSchedule();
        //处方类型 1 妊娠 2 2型
        Integer prescriptionType = prescription.getEohType();
        //处方使用的食材版本
        Integer foodVersion = prescription.getVersion();

        //饮食信息
        GetPrescriptionDetailDTO dto1 = new GetPrescriptionDetailDTO();
        dto1.setPrescriptionId(prescriptionId);
        dto1.setType(PrescriptionConstant.MODULE_TYPE_DIET);
        PrescriptionDetailPO detail = this.prescriptionMapper.getPrescriptionDetailByParam(dto1);
        if(detail!=null){
            //饮食信息模块的唯一标示
            model.setSid(detail.getSid());
            //饮食信息（保存过才有值）
            String dietJson = detail.getDetailJson();

            //非待制定状态 已经制定过且dietJson!=null
            if(!PrescriptionConstant.SCHEDULE_FORMULATED.equals(schedule) && !StringUtils.isBlank(dietJson)){
                PrescriptionDetailOfDietBO bo = JSONObject.parseObject(dietJson,PrescriptionDetailOfDietBO.class);
                if(bo!=null){
                    model.setCurrentDietAnalyse(bo.getCurrentDietAnalyse());
                    model.setInfo(bo.getInfo());
                    //创建处方时的血糖情况
                    CurrentBloodSugarVO bloodSugar = bo.getCurrentBloodSugarVO();

                    //创建处方时的日常生活习惯
                    CurrentLifeDailyVO lifeDaily = bo.getCurrentLifeDailyVO();

                    //创建处方时的饮食内容
                    CurrentDietVO currentDiet = bo.getCurrentDietVO();

                    //1.推荐热量范围
                    HeatMaxAndMinOfDietBO heatMaxAndMinOfDietBO = bo.getHeatMaxAndMinOfDietBO();

                    //2.推荐食谱数据源（下拉框）
                    List<NutritionCateringTreeVO> nutritionCateringTreeVOS = null;
                    if(heatMaxAndMinOfDietBO!=null){
                        GetNutritionCateringDTO getNutritionCateringDTO = new GetNutritionCateringDTO();
                        getNutritionCateringDTO.setEohType(prescriptionType);
                        List<String> doctorIdList = this.prescriptionApi.getDoctorIdList(prescription.getDoctorId(),1);
                        getNutritionCateringDTO.setDoctorIdList(doctorIdList);
                        getNutritionCateringDTO.setCaloricType("1");
                        List<NutritionCateringPO> nutritionCateringPOS = this.dietCache.loadNutritionCateringList(getNutritionCateringDTO);
                        nutritionCateringTreeVOS = PrescriptionHelperOfDiet.nCMLConvertToEFAML(nutritionCateringPOS);
                        this.doPrescriptionNCTreeTot(nutritionCateringTreeVOS, foodVersion);
                    }

                    //3.当前推荐食谱份数关系样例+推荐食谱详细样例(表格)
                    NutritionCateringTreeVO currentNutritionCateringTreeVO = null;
                    NutritionCateringTreeVO currentNutritionCateringTreeFatVO = null;
                    //有推荐食谱
                    if(!StringUtils.isBlank(bo.getNutritionCateringId())){
                        //有推荐食谱数据源
                        if(nutritionCateringTreeVOS!=null && nutritionCateringTreeVOS.size()>0){
                            currentNutritionCateringTreeVO = PrescriptionHelperOfDiet.getCurrentNutritionCateringByNid(
                                    nutritionCateringTreeVOS,bo.getNutritionCateringId());
                        }
                        if (prescriptionType == 3 && !StringUtils.isBlank(bo.getNutritionCateringFatId())){
                            if(nutritionCateringTreeVOS!=null && nutritionCateringTreeVOS.size()>0){
                                currentNutritionCateringTreeFatVO = PrescriptionHelperOfDiet.getCurrentNutritionCateringByNid(
                                        nutritionCateringTreeVOS,bo.getNutritionCateringFatId());
                            }
                            //从推荐食谱数据源取不到当前推荐食谱
                            if(currentNutritionCateringTreeFatVO==null){
                                //从数据库取
                                GetNutritionCateringDTO getNutritionCateringDTO = new GetNutritionCateringDTO();
                                getNutritionCateringDTO.setNid(bo.getNutritionCateringFatId());
                                getNutritionCateringDTO.setEohType(prescriptionType);
                                getNutritionCateringDTO.setMealsModel("4");
                                List<String> doctorIdList = this.prescriptionApi.getDoctorIdList(prescription.getDoctorId(),1);
                                getNutritionCateringDTO.setDoctorIdList(doctorIdList);
                                List<NutritionCateringPO> nutritionCateringPOS = this.dietCache.loadNutritionCateringList(getNutritionCateringDTO);
                                List<NutritionCateringTreeVO> temp = PrescriptionHelperOfDiet.nCMLConvertToEFAML(nutritionCateringPOS);
                                this.doPrescriptionNCTreeTot(temp, foodVersion);
                                if(temp!=null && temp.size()>0){
                                    currentNutritionCateringTreeFatVO = temp.get(0);
                                }
                            }
                        }

                        //从推荐食谱数据源取不到当前推荐食谱
                        if(currentNutritionCateringTreeVO==null){
                            //从数据库取
                            GetNutritionCateringDTO getNutritionCateringDTO = new GetNutritionCateringDTO();
                            getNutritionCateringDTO.setNid(bo.getNutritionCateringId());
                            getNutritionCateringDTO.setEohType(prescriptionType);
                            List<String> doctorIdList = this.prescriptionApi.getDoctorIdList(prescription.getDoctorId(),1);
                            getNutritionCateringDTO.setDoctorIdList(doctorIdList);
                            List<NutritionCateringPO> nutritionCateringPOS = this.dietCache.loadNutritionCateringList(getNutritionCateringDTO);
                            List<NutritionCateringTreeVO> temp = PrescriptionHelperOfDiet.nCMLConvertToEFAML(nutritionCateringPOS);
                            this.doPrescriptionNCTreeTot(temp, foodVersion);
                            if(temp!=null && temp.size()>0){
                                currentNutritionCateringTreeVO = temp.get(0);
                            }
                            //已经完成的处方
                            if(PrescriptionConstant.SCHEDULE_COMPLETE.equals(schedule)){
                                //将推荐食谱数据源更改为当前推荐食谱
                                if (null == nutritionCateringTreeVOS) {
                                    nutritionCateringTreeVOS = new ArrayList<>();
                                }
                                nutritionCateringTreeVOS = temp;
                            }
                            //保存草稿的处方
                            else if(PrescriptionConstant.SCHEDULE_DRAFT.equals(schedule)){
                                //推荐食谱数据源添加当前推荐食谱数据
                                if (null == nutritionCateringTreeVOS) {
                                    nutritionCateringTreeVOS = new ArrayList<>();
                                }
                                nutritionCateringTreeVOS.addAll(temp);
                            }
                        }
                    }
                    // 无推荐食谱
                    else {
                        //有推荐食谱数据源
                        if(nutritionCateringTreeVOS!=null && nutritionCateringTreeVOS.size()>0){
                            //取下限食谱推荐
                            Map<String, Double>  heatData = heatMaxAndMinOfDietBO.getHeatData();
                            if(heatData!=null){
                                Double preTotalHeatMin = Math.abs(StringUtils.converParamToDouble(heatData.get("heatmin")));
                                if(preTotalHeatMin!=null && prescriptionType != 3){
                                    currentNutritionCateringTreeVO = PrescriptionHelperOfDiet.getCurrentNutritionCateringByHeat(nutritionCateringTreeVOS,
                                            preTotalHeatMin.toString());
                                }
                              /*  if (prescriptionType == 3 && !StringUtils.isBlank(bo.getNutritionCateringFatId())){
                                    currentNutritionCateringTreeFatVO = PrescriptionHelperOfDiet.getCurrentNutritionCateringByHeat(nutritionCateringTreeVOS,
                                            preTotalHeatMin.toString());
                                }*/
                            }
                        }
                    }

                    //4.获取推荐的常见主副食推荐（以一份的量为例）
                    List<FoodItemBO> ingredientsItems = bo.getIngredientsItemList();
                    if(ingredientsItems==null || ingredientsItems.size()<=0){
                        List<String> ingredientsItemId = PrescriptionHelperOfDiet.getDefaultEohIngredientsItemIds();
                        ingredientsItems = this.dietCache.listEohIngredientsItemByIds(ingredientsItemId);
                    }

                    //5.目前饮食情况
                    FoodAllocationVO dailyFoodAllocation = bo.getDailyFoodAllocation();

                    //6.目前饮食分析
                    List<ProblemSuggestionVO> problemSuggestionList = bo.getProblemSuggestions();

                    //7.饮食附录与贴士
                    String prescriptionAppendix = bo.getPrescriptionAppendix();
                    List<String> dietTips = bo.getDietTips();

                    //封装返回内容
                    //非妊娠处方返回内容
                    if(prescriptionType!=1){
                        //创建处方时的血糖情况
                        model.setCurrentBloodSugarVO(bloodSugar);

                        //创建处方时的日常生活习惯
                        model.setCurrentLifeDailyVO(lifeDaily);

                        //创建处方时的饮食内容
                        model.setCurrentDietVO(currentDiet);

                        //目前饮食情况&分析
                        model.setDailyFoodAllocation(dailyFoodAllocation);
                        model.setProblemSuggestions(problemSuggestionList);
                        //推荐食谱
                        model.setHeatMaxAndMinOfDietBO(heatMaxAndMinOfDietBO);
                        model.setNutritionCateringTreeVOS(nutritionCateringTreeVOS);
                        model.setNutritionCateringTreeVO(currentNutritionCateringTreeVO);

                        //饮食技巧与贴士
                        model.setIngredientsItemList(ingredientsItems);
                        model.setDietTips(dietTips);
                        model.setPrescriptionAppendix(prescriptionAppendix);
                    }
                    //妊娠处方返回内容
                    else {
                        //创建处方时的饮食内容
                        model.setCurrentDietVO(currentDiet);
                        //目前饮食情况
                        model.setDailyFoodAllocation(dailyFoodAllocation);

                        //目前饮食建议
                        model.setProblemSuggestions(problemSuggestionList);
                        //推荐食谱
                        model.setHeatMaxAndMinOfDietBO(heatMaxAndMinOfDietBO);
                        model.setNutritionCateringTreeVOS(nutritionCateringTreeVOS);
                        model.setNutritionCateringTreeVO(currentNutritionCateringTreeVO);
                        //饮食技巧与贴士
                        model.setIngredientsItemList(ingredientsItems);
                        model.setDietTips(dietTips);
                        model.setPrescriptionAppendix(prescriptionAppendix);
                    }
                    if (prescriptionType == 3){
                        model.setWeightLoss(bo.getWeightLoss());
                        model.setIsTreat(bo.getIsTreat());
                        model.setNutritionCateringTreeFatVO(currentNutritionCateringTreeFatVO);
                    }
                }
            }
            //未制定过，待制定，非妊娠处方返回基本条件
            else if(prescriptionType!=1) {
                /**待制定-基本条件**/
                String memberId = prescription.getMemberId();
                //最近血糖测量值
                CurrentBloodSugarVO bloodSugar = new CurrentBloodSugarVO();
                ApiMemberEverydaySugarBO memberEverydaySugar = this.prescriptionApi.getMemberEverydaySugarNewByMemberId(memberId);
                if (null != memberEverydaySugar) {
                    String paramValueJson = memberEverydaySugar.getParamValueJson();
                    if (!StringUtils.isBlank(paramValueJson)) {
                        JSONArray jsonArray = JSONObject.parseArray(paramValueJson);
                        if (null != jsonArray && jsonArray.size() > 0) {
                            for (int i = 0; i < jsonArray.size(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if ("beforeBreakfast".equals(jsonObject.getString("paramCode"))) {
                                    bloodSugar.setBeforeBreakfastSugar(jsonObject.getDouble("value"));
                                } else if ("afterBreakfast".equals(jsonObject.getString("paramCode"))) {
                                    bloodSugar.setAfterBreakfastSugar(jsonObject.getDouble("value"));
                                } else if ("beforedawn".equals(jsonObject.getString("paramCode"))) {
                                    bloodSugar.setBeforedawnSugar(jsonObject.getDouble("value"));
                                } else if ("beforeLunch".equals(jsonObject.getString("paramCode"))) {
                                    bloodSugar.setBeforeLunchSugar(jsonObject.getDouble("value"));
                                } else if ("afterLunch".equals(jsonObject.getString("paramCode"))) {
                                    bloodSugar.setAfterLunchSugar(jsonObject.getDouble("value"));
                                } else if ("beforeDinner".equals(jsonObject.getString("paramCode"))) {
                                    bloodSugar.setBeforeDinnerSugar(jsonObject.getDouble("value"));
                                } else if ("afterDinner".equals(jsonObject.getString("paramCode"))) {
                                    bloodSugar.setAfterDinnerSugar(jsonObject.getDouble("value"));
                                } else if ("beforeSleep".equals(jsonObject.getString("paramCode"))) {
                                    bloodSugar.setBeforeSleepSugar(jsonObject.getDouble("value"));
                                }
                            }
                        }
                        model.setCurrentBloodSugarVO(bloodSugar);
                    }
                }

                //日常生活习惯
                PrescriptionVO pre = this.prescriptionService.getPrescriptionById(prescriptionId);
                ApiMemberArchivesBO archives = this.prescriptionApi.getMemberArchivesById(memberId, pre.getTeamId());
                if(archives!=null){
                    String diabetesComplication = archives.getDailyHabits();
                    JSONObject complication = StringUtils.isBlank(diabetesComplication)?null:JSONObject.parseObject(diabetesComplication);
                    if(complication!=null){
                        CurrentLifeDailyVO lifeDaily = new CurrentLifeDailyVO();
                        lifeDaily.setHasSmoke(complication.getInteger("hasSmoke"));
                        lifeDaily.setSomkeYear(complication.getDouble("smokeYear"));
                        lifeDaily.setSmokeNum(complication.getInteger("smokeCountByDay"));
                        lifeDaily.setDrinkRate(complication.getString("drinkRate"));
                        lifeDaily.setDrinkTime(complication.getString("drinkTimes"));
                        lifeDaily.setBeerE(complication.getDouble("beer"));
                        lifeDaily.setRedWineE(complication.getDouble("redWine"));
                        lifeDaily.setYellowWineE(complication.getDouble("yellowWine"));
                        lifeDaily.setWhiteSpiritE(complication.getDouble("whiteSpirit"));
                        lifeDaily.setWhiteSpiritHighE(complication.getDouble("whiteSpiritHigh"));
                        model.setCurrentLifeDailyVO(lifeDaily);
                    }
                }
            }

            model.setSaveState(detail.getSaveState());
        }
        return model;
    }

    @Override
    public void modifyPrescriptionDiet(PrescriptionDTO prescriptionDTO){
        //1.判断是否有新食谱 是则添加，否不处理
        String customNutritionCateringItem = prescriptionDTO.getCustomNutritionCateringItem();
        if(!StringUtils.isBlank(customNutritionCateringItem)){
            //保存/更新
            GetPrescriptionDTO getPrescriptionDTO = new GetPrescriptionDTO();
            getPrescriptionDTO.setSid(prescriptionDTO.getPrescriptionId());
            PrescriptionPO prescription = this.prescriptionMapper.getPrescriptionByParam(getPrescriptionDTO);
            String nid = this.saveCustomRecipeHandle(customNutritionCateringItem,prescription.getVersion(),prescriptionDTO.getDoctorId(),prescription.getEohType(),prescriptionDTO.getCaloricType());
            //设置推荐食谱编号
            String dietJson = prescriptionDTO.getDetailJson();
            PrescriptionDetailOfDietBO bo = JSONObject.parseObject(dietJson,PrescriptionDetailOfDietBO.class);
            bo.setNutritionCateringId(nid);
            if (prescriptionDTO.getEohType() == 3 && !StringUtils.isBlank(prescriptionDTO.getCustomNutritionCateringFatItem())) {
                String customNutritionCateringFatItem = prescriptionDTO.getCustomNutritionCateringFatItem();
                String fatnid = this.saveCustomRecipeHandle(customNutritionCateringFatItem, prescription.getVersion(), prescriptionDTO.getDoctorId(), prescription.getEohType(), prescriptionDTO.getCaloricType());
                bo.setNutritionCateringFatId(fatnid);
            }
            dietJson = JSONObject.toJSONString(bo);
            prescriptionDTO.setDetailJson(dietJson);
        }

        //2、修改子表详情  修改主表
        PrescriptionBO prescriptionBO = new PrescriptionBO();
        BeanUtils.copyProperties(prescriptionBO, prescriptionDTO);
        this.prescriptionService.modifyPrescriptionDetail(prescriptionBO);
    }

    @Override
    public List<FoodItemBO> listIngredientsItem(GetFoodItemDTO dto) {

        //新版本食材表
        return this.prescriptionMapper.listFoodItem(dto);
    }

    @Override
    public List<FoodItemBO> listIngredientsTypeOfFood(GetFoodItemDTO dto){
        //新版本食材表
        return this.prescriptionMapper.listNewTypeFoodClass();
    }

    /**
     * 从患者档案中获取主食和副食、加餐情况
     *
     * @author 占铃树
     * @date 2017年2月21日
     */
    @SuppressWarnings("unchecked")
    private Map<String, List<FoodItemBO>> getEohIngredientsForArchives(String currentDietInfo, Integer foodTableVersion) {
        List<FoodItemBO> foodItemBOList = new ArrayList<FoodItemBO>();
        List<FoodItemBO> otherFoodItemBOList = new ArrayList<FoodItemBO>();
        Map<String, List<FoodItemBO>> data = new HashMap<String, List<FoodItemBO>>(2);
        data.put("itemList", foodItemBOList);
        data.put("otherItemList", otherFoodItemBOList);

        Map<String,Object> history = JSONObject.parseObject(currentDietInfo);

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

        if(!StringUtils.isBlank(bsZczslx)) {
            foodItemBOList.addAll(JsonSerializer.jsonToList(bsZczslx, FoodItemBO.class));
        }
        if(!StringUtils.isBlank(bsWuczslx)) {
            foodItemBOList.addAll(JsonSerializer.jsonToList(bsWuczslx, FoodItemBO.class));
        }
        if(!StringUtils.isBlank(bsWanczslx)) {
            foodItemBOList.addAll(JsonSerializer.jsonToList(bsWanczslx, FoodItemBO.class));
        }

        if(!StringUtils.isBlank(bsJcnr)) {
            otherFoodItemBOList.addAll(JsonSerializer.jsonToList(bsJcnr, FoodItemBO.class));
        }
        if(!StringUtils.isBlank(bsLunchJc)) {
            otherFoodItemBOList.addAll(JsonSerializer.jsonToList(bsLunchJc, FoodItemBO.class));
        }
        if(!StringUtils.isBlank(bsDinnerJc)) {
            otherFoodItemBOList.addAll(JsonSerializer.jsonToList(bsDinnerJc, FoodItemBO.class));
        }

        fillEohIngredientsItem(foodItemBOList,foodTableVersion);
        fillEohIngredientsItem(otherFoodItemBOList,foodTableVersion);

        return data;
    }

    /**
     * 填充食材信息
     *
     * @param foodItemBOList
     * @author 林雨堆
     * @date 2018年05月30日
     */
    @Override
    public void fillEohIngredientsItem(List<FoodItemBO> foodItemBOList, Integer foodTableVersion){
        if(foodItemBOList == null) {
            return;
        }

        for (Iterator<FoodItemBO> iterator = foodItemBOList.iterator(); iterator.hasNext();) {
            FoodItemBO foodItemBO = iterator.next();
            String id = foodItemBO.getId();
            FoodItemBO item = null;
            if (id.length()>5) {
                if (foodTableVersion != null && PrescriptionConstant.PRESCRIPTION_VERSION.equals(foodTableVersion)) {
                    item = this.dietCache.getEohFoodItemById(id);
                } else {
                    item = this.dietCache.getEohIngredientsItemById(id);
                }

                if (item == null) {
                    continue;
                }
                String gram = item.getGram();
                String heat = item.getHeat();
                if (StringUtils.isBlank(gram) || Double.parseDouble(gram) <= 0) {
                    gram = (90 / Double.parseDouble(heat)) + "";
                }
                foodItemBO.setHeat(heat);
                foodItemBO.setChangeDelta(item.getChangeDelta());
                foodItemBO.setGram(gram);
                foodItemBO.setIngredientsType(item.getIngredientsType());
                foodItemBO.setPicture(item.getPicture());
                foodItemBO.setSpell(item.getSpell());
                foodItemBO.setUnit(item.getUnit());
                foodItemBO.setName(item.getName());
                foodItemBO.setNa(item.getNa());
                foodItemBO.setCarbohydrates(item.getCarbohydrates());
                foodItemBO.setTotalfats(item.getTotalfats());
                foodItemBO.setProtein(item.getProtein());
            }else {
                List<FoodNutrientBO> foodNutrientBOS = this.prescriptionMapper.listFoodNutrient(id,null);
                if (foodNutrientBOS.size()<=0){
                    continue;
                }
                foodItemBO.setHeat(foodNutrientBOS.get(0).getHeat());
                foodItemBO.setName(foodNutrientBOS.get(0).getName());
                if (!StringUtils.isBlank(foodNutrientBOS.get(0).getNa())) {
                    foodItemBO.setNa(Double.parseDouble(foodNutrientBOS.get(0).getNa()));
                }
                if (!StringUtils.isBlank(foodNutrientBOS.get(0).getCarbohydrates())) {
                    foodItemBO.setCarbohydrates(Double.parseDouble(foodNutrientBOS.get(0).getCarbohydrates()));
                }
                if (!StringUtils.isBlank(foodNutrientBOS.get(0).getTotalfats())) {
                    foodItemBO.setCarbohydrates(Double.parseDouble(foodNutrientBOS.get(0).getTotalfats()));
                }
                if (!StringUtils.isBlank(foodNutrientBOS.get(0).getProtein())) {
                    foodItemBO.setProtein(Double.parseDouble(foodNutrientBOS.get(0).getProtein()));
                }
                foodItemBO.setUnit(foodNutrientBOS.get(0).getUnit());
            }
        }
    }

    /**
     * 封装DietOutPutDTO
     * @param baseParamOfDietDTO
     * @param currentNutritionCateringTreeVO
     * @param dailyFoodAllocation
     * @param bo
     * @return
     */
    private DietOutPutDTO getDietOutPutDTO(BaseParamOfDietDTO baseParamOfDietDTO,
                                           NutritionCateringTreeVO currentNutritionCateringTreeVO,
                                           FoodAllocationVO dailyFoodAllocation,
                                           HeatMaxAndMinOfDietBO bo,Integer foodTableVersion){

        DietOutPutDTO dto = null;
        if(baseParamOfDietDTO!=null && currentNutritionCateringTreeVO!=null && dailyFoodAllocation!=null && bo!=null && foodTableVersion!=null){
            dto = new DietOutPutDTO();
            dto.setDrinkRate(baseParamOfDietDTO.getDrinkRate());
            dto.setHasSmoke(baseParamOfDietDTO.getHasSmoke());
            dto.setSmokeNum(baseParamOfDietDTO.getSmokeNum());
            dto.setDrinkTime(baseParamOfDietDTO.getDrinkTime());
            dto.setSex(baseParamOfDietDTO.getSex());
            dto.setPreCereal(Double.parseDouble(currentNutritionCateringTreeVO.getCerealNum()));
            dto.setPreMeatAndAge(Double.parseDouble(currentNutritionCateringTreeVO.getMeatNum()));
            dto.setPreMilk(Double.parseDouble(currentNutritionCateringTreeVO.getSoymilkNum()));
            dto.setPreProteins(Double.parseDouble(currentNutritionCateringTreeVO.getGreaseNum()));
            dto.setPreTotalHeatMax(Double.parseDouble(bo.getHeatMax()));
            dto.setPreTotalHeatMin(Double.parseDouble(bo.getHeatMin()));
            //一天摄入总热量
            Double totalHeat = Double.parseDouble(dailyFoodAllocation.getDailyHeat());
            dto.setTotalHeat(totalHeat);
            Map<String, Object> foodAllocationMap = dailyFoodAllocation.getFoodAllocationMap();
            //全天碳水化合物类食物摄入份数
            double totalCarbohydrates = StringUtils.converParamToDouble(foodAllocationMap.get("riceNum"));
            double totalCarbohydrates1 = StringUtils.converParamToDouble(foodAllocationMap.get("riceExtraNum"));
            dto.setTotalCarbohydrates(totalCarbohydrates+totalCarbohydrates1);
            //全日蔬菜份数
            double totalVegetables = StringUtils.converParamToDouble(foodAllocationMap.get("vegetableNum"));
            double totalVegetables1 = StringUtils.converParamToDouble(foodAllocationMap.get("vegetableExtraNum"));
            dto.setTotalVegetables(totalVegetables+totalVegetables1);
            //全天蛋白质类食物摄入份数
            double totalProteins = StringUtils.converParamToDouble(foodAllocationMap.get("meatNum"));
            double totalProteins1 = StringUtils.converParamToDouble(foodAllocationMap.get("meatExtraNum"));
            double totalProteins2 = StringUtils.converParamToDouble(foodAllocationMap.get("proteinNum"));
            double totalProteins3 = StringUtils.converParamToDouble(foodAllocationMap.get("proteinExtraNum"));
            dto.setTotalProteins(totalProteins+totalProteins1+totalProteins2+totalProteins3);
            //全天脂肪类食物摄入份数
            double totalFats = StringUtils.converParamToDouble(foodAllocationMap.get("fatsNum"));
            double totalFats1 = StringUtils.converParamToDouble(foodAllocationMap.get("fatsExtraNum"));
            dto.setTotalFats(totalFats+totalFats1);
            //全天水果类食物摄入份数
            double totalFruit = StringUtils.converParamToDouble(foodAllocationMap.get("fruitNum"));
            double totalFruit1 = StringUtils.converParamToDouble(foodAllocationMap.get("fruitExtraNum"));
            dto.setTotalFruit(totalFruit+totalFruit1);

            //全日酒精摄入量
            Double totalDrinks = PrescriptionHelperOfDiet.getTotalDrinks(baseParamOfDietDTO.getDrinkRate(),
                    baseParamOfDietDTO.getWhiteSpiritE(), baseParamOfDietDTO.getRedWineE(), baseParamOfDietDTO.getBeerE(),
                    baseParamOfDietDTO.getYellowWineE(), baseParamOfDietDTO.getWhiteSpiritHighE());;
            dto.setTotalDrinks(totalDrinks);

            //血糖是否异常(餐后血糖或空腹血糖是否不达标即偏高或者偏低)
            ApiRangeBO rangeModel = this.prescriptionApi.getRangeBO(baseParamOfDietDTO.getMemberId());
            Boolean isPathoglycemia = PrescriptionHelperOfDiet.getIsPathoglycemia(baseParamOfDietDTO.getBeforeBreakfastSugar(),
                    baseParamOfDietDTO.getAfterBreakfastSugar(), rangeModel);
            dto.setPathoglycemia(isPathoglycemia);

            //当前饮食信息
            Map<String, Object> conditions = JsonSerializer.jsonToMap(JSON.toJSONString(baseParamOfDietDTO));
            //早餐主食份数
            List<FoodItemBO> breakfastList = PrescriptionHelperOfDiet.getListForArchives(conditions,"currentDietInfo","bs_zczslx", FoodItemBO.class);
            this.fillEohIngredientsItem(breakfastList,foodTableVersion);
            String breakfastCount = PrescriptionHelperOfDiet.getEohIngredientsItemCount(breakfastList);;
            dto.setBreakfastCount(breakfastCount);
            //午餐主食份数
            List<FoodItemBO> lunchList = PrescriptionHelperOfDiet.getListForArchives(conditions, "currentDietInfo", "bs_wuczslx", FoodItemBO.class);
            this.fillEohIngredientsItem(lunchList,foodTableVersion);
            String lunchCount = PrescriptionHelperOfDiet.getEohIngredientsItemCount(lunchList);
            dto.setLunchCount(lunchCount);
            //晚餐主食份数
            List<FoodItemBO> dinnerList = PrescriptionHelperOfDiet.getListForArchives(conditions, "currentDietInfo", "bs_wanczslx", FoodItemBO.class);
            this.fillEohIngredientsItem(dinnerList,foodTableVersion);
            String dinnerCount = PrescriptionHelperOfDiet.getEohIngredientsItemCount(dinnerList);
            dto.setDinnerCount(dinnerCount);
        }
        return dto;
    }

    /**
     * 食谱列表的食谱元素含量计算
     * @param nutritionCateringTrees 食谱列表
     * @param foodVersion
     */
    private void doPrescriptionNCTreeTot(List<NutritionCateringTreeVO> nutritionCateringTrees,Integer foodVersion){
        for(NutritionCateringTreeVO treeVO : nutritionCateringTrees){
            List<NutritionCateringItemVO> items = treeVO.getNutritionCateringItems();
            for(NutritionCateringItemVO item : items){
                String key = item.getNutritionCateringId() + "|" + foodVersion;
                NutritionCateringItemVO cacheItem = cache.getIfPresent(key);
                if(cacheItem != null){
                    BeanUtils.copyProperties(item, cacheItem);
                }else {
                    doPrescriptionNCItemTot(item, foodVersion);
                    cache.put(key, item);
                }
            }
        }
    }

    /**
     * 食谱内容所含元素计算
     * @param nutritionCateringItem 食谱内容
     * @return
     */
    private void doPrescriptionNCItemTot(NutritionCateringItemVO nutritionCateringItem,Integer foodVersion) {

        Integer version = nutritionCateringItem.getVersion();
        List<Map> bjson = new ArrayList<>();
        List<Map> bajson = new ArrayList<>();
        List<Map> ljson = new ArrayList<>();
        List<Map> lajson = new ArrayList<>();
        List<Map> djson = new ArrayList<>();
        List<Map> dajson = new ArrayList<>();

        //封装
        if(version==1){
            String json = nutritionCateringItem.getBreakfastFoodJson();
            if(!StringUtils.isBlank(json)){
                JSONObject jsonObj = JSONObject.parseObject(json);
                String bjsonStrT = jsonObj.getString("breakfastFoodList");
                this.distMeal(bjsonStrT,bjson,bajson);
                String ljsonStrT = jsonObj.getString("lunchFoodList");
                this.distMeal(ljsonStrT,ljson,lajson);
                String djsonStrT = jsonObj.getString("dinnerFoodList");
                this.distMeal(djsonStrT,djson,dajson);
            }
        }
        else {
            String  bjsonT= nutritionCateringItem.getBreakfastFoodJson();
            if(!StringUtils.isBlank(bjsonT)){
                bjson = JSONArray.parseArray(bjsonT,Map.class);
                List<Map<String, Object>> list1 = JsonSerializer.jsonToMapList(bjsonT);
                List<Map<String, Object>> listMap1 = new ArrayList<>();
                //获取碳水化合物,脂肪,蛋白质
                for (Map<String, Object> map : list1) {
                    if (null != map.get("id") && !StringUtils.isBlank(map.get("id").toString())){
                        String id = map.get("id").toString();
                        FoodItemBO itemBO = this.dietCache.getEohFoodItemById(id);
                        if (itemBO!=null) {
                            map.put("carbohydrates", itemBO.getCarbohydrates());
                            map.put("totalfats", itemBO.getTotalfats());
                            map.put("protein", itemBO.getProtein());
                            listMap1.add(map);
                        }
                        List<FoodNutrientBO> bos = this.prescriptionMapper.listFoodNutrient(map.get("id").toString(),null);
                        if (bos.size()>0){
                            map.put("carbohydrates", bos.get(0).getCarbohydrates());
                            map.put("totalfats", bos.get(0).getTotalfats());
                            map.put("protein", bos.get(0).getProtein());
                            listMap1.add(map);
                        }
                    }
                }
                String json1 = JsonSerializer.objectToJson(listMap1);
                nutritionCateringItem.setBreakfastFoodJson(json1);
            }

            String bajsonT  = nutritionCateringItem.getBreakfastFoodAddJson();
            if(!StringUtils.isBlank(bajsonT)){
                bajson = JSONArray.parseArray(bajsonT,Map.class);
                List<Map<String, Object>> list2 = JsonSerializer.jsonToMapList(bajsonT);
                List<Map<String, Object>> listMap2 = new ArrayList<>();
                //获取碳水化合物,脂肪,蛋白质
                for (Map<String, Object> map : list2) {
                    if (null != map.get("id") && !StringUtils.isBlank(map.get("id").toString())){
                        String id = map.get("id").toString();
                        FoodItemBO itemBO = this.dietCache.getEohFoodItemById(id);
                        if (itemBO!=null) {
                            map.put("carbohydrates", itemBO.getCarbohydrates());
                            map.put("totalfats", itemBO.getTotalfats());
                            map.put("protein", itemBO.getProtein());
                            listMap2.add(map);
                        }
                        List<FoodNutrientBO> bos = this.prescriptionMapper.listFoodNutrient(map.get("id").toString(),null);
                        if (bos.size()>0){
                            map.put("carbohydrates", bos.get(0).getCarbohydrates());
                            map.put("totalfats", bos.get(0).getTotalfats());
                            map.put("protein", bos.get(0).getProtein());
                            listMap2.add(map);
                        }
                    }
                }
                String json2 = JsonSerializer.objectToJson(listMap2);
                nutritionCateringItem.setBreakfastFoodAddJson(json2);
            }

            String ljsonT  = nutritionCateringItem.getLunchFoodJson();
            if(!StringUtils.isBlank(ljsonT)){
                ljson = JSONArray.parseArray(ljsonT,Map.class);
                List<Map<String, Object>> list3 = JsonSerializer.jsonToMapList(ljsonT);
                List<Map<String, Object>> listMap3 = new ArrayList<>();
                //获取碳水化合物,脂肪,蛋白质
                for (Map<String, Object> map : list3) {
                    if (null != map.get("id") && !StringUtils.isBlank(map.get("id").toString())){
                        String id = map.get("id").toString();
                        FoodItemBO itemBO = this.dietCache.getEohFoodItemById(id);
                        if (itemBO!=null) {
                            map.put("carbohydrates", itemBO.getCarbohydrates());
                            map.put("totalfats", itemBO.getTotalfats());
                            map.put("protein", itemBO.getProtein());
                            listMap3.add(map);
                        }
                        List<FoodNutrientBO> bos = this.prescriptionMapper.listFoodNutrient(map.get("id").toString(),null);
                        if (bos.size()>0){
                            map.put("carbohydrates", bos.get(0).getCarbohydrates());
                            map.put("totalfats", bos.get(0).getTotalfats());
                            map.put("protein", bos.get(0).getProtein());
                            listMap3.add(map);
                        }
                    }
                }
                String json3 = JsonSerializer.objectToJson(listMap3);
                nutritionCateringItem.setLunchFoodJson(json3);
            }

            String lajsonT  = nutritionCateringItem.getLunchFoodAddJson();
            if(!StringUtils.isBlank(lajsonT)){
                lajson = JSONArray.parseArray(lajsonT,Map.class);
                List<Map<String, Object>> list4 = JsonSerializer.jsonToMapList(lajsonT);
                List<Map<String, Object>> listMap4 = new ArrayList<>();
                //获取碳水化合物,脂肪,蛋白质
                for (Map<String, Object> map : list4) {
                    if (null != map.get("id") && !StringUtils.isBlank(map.get("id").toString())){
                        String id = map.get("id").toString();
                        FoodItemBO itemBO = this.dietCache.getEohFoodItemById(id);
                        if (itemBO!=null) {
                            map.put("carbohydrates", itemBO.getCarbohydrates());
                            map.put("totalfats", itemBO.getTotalfats());
                            map.put("protein", itemBO.getProtein());
                            listMap4.add(map);
                        }
                        List<FoodNutrientBO> bos = this.prescriptionMapper.listFoodNutrient(map.get("id").toString(),null);
                        if (bos.size()>0){
                            map.put("carbohydrates", bos.get(0).getCarbohydrates());
                            map.put("totalfats", bos.get(0).getTotalfats());
                            map.put("protein", bos.get(0).getProtein());
                            listMap4.add(map);
                        }
                    }
                }
                String json4 = JsonSerializer.objectToJson(listMap4);
                nutritionCateringItem.setLunchFoodAddJson(json4);
            }

            String djsonT = nutritionCateringItem.getDinnerFoodJson();
            if(!StringUtils.isBlank(djsonT)){
                djson = JSONArray.parseArray(djsonT,Map.class);
                List<Map<String, Object>> list5 = JsonSerializer.jsonToMapList(djsonT);
                List<Map<String, Object>> listMap5 = new ArrayList<>();
                //获取碳水化合物,脂肪,蛋白质
                for (Map<String, Object> map : list5) {
                    if (null != map.get("id") && !StringUtils.isBlank(map.get("id").toString())){
                        String id = map.get("id").toString();
                        FoodItemBO itemBO = this.dietCache.getEohFoodItemById(id);
                        if (itemBO!=null) {
                            map.put("carbohydrates", itemBO.getCarbohydrates());
                            map.put("totalfats", itemBO.getTotalfats());
                            map.put("protein", itemBO.getProtein());
                            listMap5.add(map);
                        }
                        List<FoodNutrientBO> bos = this.prescriptionMapper.listFoodNutrient(map.get("id").toString(),null);
                        if (bos.size()>0){
                            map.put("carbohydrates", bos.get(0).getCarbohydrates());
                            map.put("totalfats", bos.get(0).getTotalfats());
                            map.put("protein", bos.get(0).getProtein());
                            listMap5.add(map);
                        }
                    }
                }
                String json5 = JsonSerializer.objectToJson(listMap5);
                nutritionCateringItem.setDinnerFoodJson(json5);
            }

            String dajsonT  = nutritionCateringItem.getDinnerFoodAddJson();
            if(!StringUtils.isBlank(dajsonT)){
                dajson = JSONArray.parseArray(dajsonT,Map.class);
                List<Map<String, Object>> list6 = JsonSerializer.jsonToMapList(dajsonT);
                List<Map<String, Object>> listMap6 = new ArrayList<>();
                //获取碳水化合物,脂肪,蛋白质
                for (Map<String, Object> map : list6) {
                    if (null != map.get("id") && !StringUtils.isBlank(map.get("id").toString())){
                        String id = map.get("id").toString();
                        FoodItemBO itemBO = this.dietCache.getEohFoodItemById(id);
                        if (itemBO!=null) {
                            map.put("carbohydrates", itemBO.getCarbohydrates());
                            map.put("totalfats", itemBO.getTotalfats());
                            map.put("protein", itemBO.getProtein());
                            listMap6.add(map);
                        }
                        List<FoodNutrientBO> bos = this.prescriptionMapper.listFoodNutrient(map.get("id").toString(),null);
                        if (bos.size()>0){
                            map.put("carbohydrates", bos.get(0).getCarbohydrates());
                            map.put("totalfats", bos.get(0).getTotalfats());
                            map.put("protein", bos.get(0).getProtein());
                            listMap6.add(map);
                        }
                    }

                }
                String json6 = JsonSerializer.objectToJson(listMap6);
                nutritionCateringItem.setDinnerFoodAddJson(json6);
            }
        }

        // 碳水化物
        double carbohydrates = doNCItemTot(bjson,foodVersion,1);
        carbohydrates += doNCItemTot(bajson,foodVersion,1);
        carbohydrates += doNCItemTot(ljson,foodVersion,1);
        carbohydrates += doNCItemTot(lajson,foodVersion,1);
        carbohydrates += doNCItemTot(djson,foodVersion,1);
        carbohydrates += doNCItemTot(dajson,foodVersion,1);

        // 脂肪
        double totalfats=doNCItemTot(bjson,foodVersion,2);
        totalfats+=doNCItemTot(bajson,foodVersion,2);
        totalfats+=doNCItemTot(ljson,foodVersion,2);
        totalfats+=doNCItemTot(lajson,foodVersion,2);
        totalfats+=doNCItemTot(djson,foodVersion,2);
        totalfats+=doNCItemTot(dajson,foodVersion,2);

        // 蛋白质
        double protein=doNCItemTot(bjson,foodVersion,3);
        protein+=doNCItemTot(bajson,foodVersion,3);
        protein+=doNCItemTot(ljson,foodVersion,3);
        protein+=doNCItemTot(lajson,foodVersion,3);
        protein+=doNCItemTot(djson,foodVersion,3);
        protein+=doNCItemTot(dajson,foodVersion,3);

        //设置
        nutritionCateringItem.setCarbohydrates(carbohydrates);
        nutritionCateringItem.setTotalfats(totalfats);
        nutritionCateringItem.setProtein(protein);
    }

    /**
     * 含量计算
     * @param maps 数据源
     * @param foodVersion 食材库版本
     * @param type 含量类型 1碳水化物 2脂肪 3蛋白质
     * @return
     */
    private Double doNCItemTot(List<Map> maps,Integer foodVersion,Integer type){
        Double result = 0D;
        boolean isNewFood = true;
        if(foodVersion==1){
            isNewFood = false;
        }
        if(isNewFood){
            if(maps!=null&&maps.size()>0){
                for(Map map : maps){
                    Object id = map.get("id");
                    Object w = map.get("weight");
                    if(id!=null && w!=null){
                        FoodItemBO item = this.dietCache.getEohFoodItemById(id.toString());
                        if(item!=null){
                            double weight = Double.parseDouble(w.toString());
                            if(type==1){
                                Double carbohydrates = item.getCarbohydrates();
                                if(carbohydrates!=null){
                                    result += carbohydrates*weight;
                                }
                            } else if(type==2){
                                Double totalfats = item.getTotalfats();
                                if(totalfats!=null){
                                    result += totalfats*weight;
                                }
                            } else if(type==3){
                                Double protein = item.getProtein();
                                if(protein!=null){
                                    result += protein*weight;
                                }
                            }
                        } else {
                            isNewFood = false;
                        }
                    }
                }
            }
        }

        if(!isNewFood){
            if(maps!=null&&maps.size()>0){
                for(Map map : maps){
                    Object id = map.get("id");
                    Object w = map.get("weight");
                    if(id!=null && w!=null){
                        FoodItemBO item = this.dietCache.getEohIngredientsItemById(id.toString());
                        if(item!=null){
                            String ingredientsType = item.getIngredientsType();
                            double gram = Double.parseDouble(item.getGram());
                            double weight = Double.parseDouble(w.toString());
                            //谷薯类
                            if("1001001".equals(ingredientsType)) {
                                double fen= weight/gram;
                                if(type==1){
                                    result  = result + fen*20;
                                } else if(type==3){
                                    result = result + fen*2;
                                }

                            } else if("1001003".equals(ingredientsType)){
                                //蔬菜类
                                double fen= weight/gram;
                                if(type==1){
                                    result  = result + fen*17;
                                } else if(type==3){
                                    result = result + fen*5;
                                }
                            } else if("1001007".equals(ingredientsType)) {
                                //肉蛋类
                                double fen= weight/gram;
                                if(type==2){
                                    result  = result + fen*6;
                                } else if(type==3){
                                    result = result + fen*9;
                                }
                            } else if("1001002".equals(ingredientsType)) {
                                //豆类
                                double fen= weight/gram;
                                if(type==1){
                                    result  = result + fen*4;
                                } else if(type==2){
                                    result  = result + fen*4;
                                } else if(type==3){
                                    result = result + fen*9;
                                }
                            } else if("1001004".equals(ingredientsType)) {
                                //水果类
                                double fen= weight/gram;
                                if(type==1){
                                    result  = result + fen*21;
                                } else if(type==3){
                                    result = result + fen*1;
                                }
                            } else if("1001012".equals(ingredientsType)) {
                                //浆乳类
                                double fen= weight/gram;
                                if(type==1){
                                    result  = result + fen*6;
                                } else if(type==2){
                                    result  = result + fen*5;
                                } else if(type==3){
                                    result = result + fen*5;
                                }
                            } else if("1001013".equals(ingredientsType)){
                                //油脂类（烹饪）
                                double fen= weight/gram;
                                if(type==2){
                                    result  = result + fen*10;
                                }
                            } else if("1001014".equals(ingredientsType)){
                                //油脂类（坚果）
                                double fen= weight/gram;
                                if(type==1){
                                    result  = result + fen*2;
                                } else if(type==2){
                                    result  = result + fen*7;
                                } else if(type==3){
                                    result = result + fen*4;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 区分正餐和加餐
     * @param djsonStrT 餐点信息
     * @param djson 正餐容器
     * @param dajson 加餐容器
     */
    private void distMeal(String djsonStrT,List<Map> djson,List<Map> dajson){
        if(!StringUtils.isBlank(djsonStrT)){
            List<Map> djsonArrT = JSONArray.parseArray(djsonStrT,Map.class);
            for(int i=0;i<djsonArrT.size();i++){
                Map arrItem = djsonArrT.get(i);
                if(arrItem.get("type")!=null && "1".equals(arrItem.get("type").toString())){
                    djson.add(arrItem);
                } else if(arrItem.get("type")!=null && "2".equals(arrItem.get("type").toString())){
                    dajson.add(arrItem);
                }
            }
        }
    }

    /**
     * 保存自定义食谱
     * @param nutritionCateringJson
     * @param foodVersion 食材版本号
     * @param doctorId 工作室首席编号
     * @return
     */
    public String saveCustomRecipeHandle(String nutritionCateringJson,Integer foodVersion,String doctorId,Integer eohType,String caloricType){
        NutritionCateringPO nutritionCateringPO = null;;
        Map<String, Object> recMap = null;
        List<JSONObject> breakfastFoodList1 = new ArrayList<JSONObject>();
        List<JSONObject> lunchFoodList2 = new ArrayList<JSONObject>();
        List<JSONObject> dinnerFoodList3 = new ArrayList<JSONObject>();
        if(!StringUtils.isBlank(nutritionCateringJson)) {
            nutritionCateringPO = new NutritionCateringPO();
            nutritionCateringPO.setVersion(foodVersion);
            NutritionCateringItemVO nutritionCateringItem = JSONObject.parseObject(nutritionCateringJson,NutritionCateringItemVO.class);
            if (StringUtils.isBlank(nutritionCateringItem.getMealsModel())){
                nutritionCateringItem.setMealsModel("0");
            }
            nutritionCateringPO.setMealsModel(nutritionCateringItem.getMealsModel());  //设置膳食模式
            nutritionCateringPO.setRecipeName(nutritionCateringItem.getName());  //设置食谱名称
            recMap = new HashMap<>(3);

            String tempObject = nutritionCateringItem.getBreakfastFoodJson();
            if(!StringUtils.isBlank(tempObject)) {
                breakfastFoodList1 =JSONObject.parseArray(tempObject,JSONObject.class);
                nutritionCateringPO.setBreakfastFoodJson(tempObject.toString());
            }
            tempObject = nutritionCateringItem.getBreakfastFoodAddJson();
            if(!StringUtils.isBlank(tempObject)) {
                List<JSONObject> tempbreakfastFoodList = JSONObject.parseArray(tempObject,JSONObject.class);
                breakfastFoodList1.addAll(tempbreakfastFoodList);
                nutritionCateringPO.setBreakfastFoodAddJson(tempObject.toString());
            }
            if(breakfastFoodList1!=null && breakfastFoodList1.size()>0){
                recMap.put("breakfastFoodList", breakfastFoodList1);
            }

            tempObject = nutritionCateringItem.getLunchFoodJson();
            if(!StringUtils.isBlank(tempObject)) {
                lunchFoodList2 = JSONObject.parseArray(tempObject,JSONObject.class);
                nutritionCateringPO.setLunchFoodJson(tempObject.toString());
            }
            tempObject = nutritionCateringItem.getLunchFoodAddJson();
            if(!StringUtils.isBlank(tempObject)) {
                List<JSONObject> templunchFoodList = JSONObject.parseArray(tempObject,JSONObject.class);
                lunchFoodList2.addAll(templunchFoodList);
                nutritionCateringPO.setLunchFoodAddJson(tempObject.toString());
            }
            if(lunchFoodList2!=null&&lunchFoodList2.size()>0){
                recMap.put("lunchFoodList", lunchFoodList2);
            }

            tempObject = nutritionCateringItem.getDinnerFoodJson();
            if(!StringUtils.isBlank(tempObject)) {
                dinnerFoodList3 = JSONObject.parseArray(tempObject,JSONObject.class);
                nutritionCateringPO.setDinnerFoodJson(tempObject.toString());
            }
            tempObject = nutritionCateringItem.getDinnerFoodAddJson();
            if(!StringUtils.isBlank(tempObject)) {
                List<JSONObject> tempdinnerFoodList = JSONObject.parseArray(tempObject,JSONObject.class);
                dinnerFoodList3.addAll(tempdinnerFoodList);
                nutritionCateringPO.setDinnerFoodAddJson(tempObject.toString());
            }
            if(dinnerFoodList3!=null && dinnerFoodList3.size()>0){
                recMap.put("dinnerFoodList", dinnerFoodList3);
            }
        }

        if(recMap!=null&&nutritionCateringPO!=null){
            String programmeJson = JSON.toJSONString(recMap);
            nutritionCateringPO.setProgrammeJson(programmeJson);
            List<Map<String, Object>> breakfastFoodList = nutritionCateringPO.getBreakfastFoodList();
            List<Map<String, Object>> lunchFoodList = nutritionCateringPO.getLunchFoodList();
            List<Map<String, Object>> dinnerFoodList = nutritionCateringPO.getDinnerFoodList();
            double heat = 0;
            double foodNum=0,greaseNum=0,fruitsNum=0,meatNum=0,vegetablesNum=0,soymilkNum=0,
                    cerealNum=0;
            double eggNum = 0,beansNum=0;
            String egg_String = "180518092801997,180518092801998,180518092801999,180518092802000,180518092802001,180518092802200,180518092802201,180518092802202,";
            egg_String += "180518092802203,180518092802204,180518092802205,180518092802206,180518092802207,180518092802208,180518092802209,180518092802210,180518092802211,";
            egg_String += "180518092802212,180518092802213,180518092802214,180518092802215,180518092802327,";
            Map<String ,Float> ingredientStat = new HashMap<>();
            Integer version = nutritionCateringPO.getVersion();
            if(version!=null && version==2){
                if ( eohType == 3){
                    for(Map<String, Object> map : breakfastFoodList1){
                        Object tempObject = map.get("ftype");
                        if(tempObject!=null){
                            String ftype = tempObject.toString();
                            tempObject = map.get("weight");
                            if(tempObject!=null){
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat += iHeat;
                                // 谷物类
                                if("1001001".equals(ftype)){
                                    cerealNum += num;
                                } else if("1001003".equals(ftype)){
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                    // 肉蛋豆类
                                    if (eohType == 3){
                                        if ("1001002".equals(ftype)) {
                                            beansNum += num;
                                        }else {
                                            String id = map.get("id").toString();
                                            if (egg_String.contains(id)){
                                                eggNum += num;
                                            }else {
                                                meatNum += num;
                                            }
                                        }
                                    }else {
                                        meatNum += num;
                                    }
                                } else if("1001012".equals(ftype)){
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                    // 油脂类
                                    greaseNum += num;
                                } else if("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }
                                Object weight = map.getOrDefault("weight" , 0);
                                ingredientStatHandler(ingredientStat ,ftype ,weight);
                            }
                        }
                    }

                    for(Map<String, Object> map : lunchFoodList2){
                        Object tempObject = map.get("ftype");
                        if(tempObject!=null){
                            String ftype = tempObject.toString();
                            tempObject = map.get("weight");
                            if(tempObject!=null){
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat +=iHeat;
                                if("1001001".equals(ftype)){
                                    // 谷物类
                                    cerealNum += num;
                                } else if("1001003".equals(ftype)){
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                    // 肉蛋豆类
                                    if (eohType == 3){
                                        if ("1001002".equals(ftype)) {
                                            beansNum += num;
                                        }else {
                                            String id = map.get("id").toString();
                                            if (egg_String.contains(id)){
                                                eggNum += num;
                                            }else {
                                                meatNum += num;
                                            }
                                        }
                                    }else {
                                        meatNum += num;
                                    }
                                } else if("1001012".equals(ftype)){
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                    // 油脂类
                                    greaseNum += num;
                                } else if("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }

                                Object weight = map.getOrDefault("weight" , 0);
                                ingredientStatHandler(ingredientStat ,ftype ,weight);
                            }
                        }
                    }

                    for(Map<String, Object> map : dinnerFoodList3){
                        Object tempObject = map.get("ftype");
                        if(tempObject!=null){
                            String ftype = tempObject.toString();
                            tempObject = map.get("weight");
                            if(tempObject!=null){
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat +=iHeat;
                                if("1001001".equals(ftype)){
                                    // 谷物类
                                    cerealNum += num;
                                } else if("1001003".equals(ftype)){
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                    // 肉蛋豆类
                                    if (eohType == 3){
                                        if ("1001002".equals(ftype)) {
                                            beansNum += num;
                                        }else {
                                            String id = map.get("id").toString();
                                            if (egg_String.contains(id)){
                                                eggNum += num;
                                            }else {
                                                meatNum += num;
                                            }
                                        }
                                    }else {
                                        meatNum += num;
                                    }
                                } else if("1001012".equals(ftype)){
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                    // 油脂类
                                    greaseNum += num;
                                } else if("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }

                                Object weight = map.getOrDefault("weight" , 0);
                                ingredientStatHandler(ingredientStat ,ftype ,weight);
                            }
                        }
                    }
                }else {
                    for (Map<String, Object> map : breakfastFoodList) {
                        Object tempObject = map.get("ftype");
                        if (tempObject != null) {
                            String ftype = tempObject.toString();
                            tempObject = map.get("fnum");
                            if (tempObject != null) {
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat += iHeat;
                                // 谷物类
                                if ("1001001".equals(ftype)) {
                                    cerealNum += num;
                                } else if ("1001003".equals(ftype)) {
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if ("1001007".equals(ftype) || "1001002".equals(ftype)) {
                                    // 肉蛋豆类
                                    meatNum += num;
                                } else if ("1001012".equals(ftype)) {
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if ("1001013".equals(ftype) || "1001014".equals(ftype)) {
                                    // 油脂类
                                    greaseNum += num;
                                } else if ("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }
                                Object weight = map.getOrDefault("weight", 0);
                                ingredientStatHandler(ingredientStat, ftype, weight);
                            }
                        }
                    }

                    for (Map<String, Object> map : lunchFoodList) {
                        Object tempObject = map.get("ftype");
                        if (tempObject != null) {
                            String ftype = tempObject.toString();
                            tempObject = map.get("fnum");
                            if (tempObject != null) {
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat += iHeat;
                                if ("1001001".equals(ftype)) {
                                    // 谷物类
                                    cerealNum += num;
                                } else if ("1001003".equals(ftype)) {
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if ("1001007".equals(ftype) || "1001002".equals(ftype)) {
                                    // 肉蛋豆类
                                    meatNum += num;
                                } else if ("1001012".equals(ftype)) {
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if ("1001013".equals(ftype) || "1001014".equals(ftype)) {
                                    // 油脂类
                                    greaseNum += num;
                                } else if ("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }

                                Object weight = map.getOrDefault("weight", 0);
                                ingredientStatHandler(ingredientStat, ftype, weight);
                            }
                        }
                    }

                    for (Map<String, Object> map : dinnerFoodList) {
                        Object tempObject = map.get("ftype");
                        if (tempObject != null) {
                            String ftype = tempObject.toString();
                            tempObject = map.get("fnum");
                            if (tempObject != null) {
                                String numString = tempObject.toString();
                                String allHeat = map.get("allheat").toString();
                                double num = Double.parseDouble(numString);
                                double iHeat = Double.parseDouble(allHeat);
                                foodNum += num;
                                heat += iHeat;
                                if ("1001001".equals(ftype)) {
                                    // 谷物类
                                    cerealNum += num;
                                } else if ("1001003".equals(ftype)) {
                                    // 蔬菜类
                                    vegetablesNum += num;
                                } else if ("1001007".equals(ftype) || "1001002".equals(ftype)) {
                                    // 肉蛋豆类
                                    meatNum += num;
                                } else if ("1001012".equals(ftype)) {
                                    // 浆乳类
                                    soymilkNum += num;
                                } else if ("1001013".equals(ftype) || "1001014".equals(ftype)) {
                                    // 油脂类
                                    greaseNum += num;
                                } else if ("1001004".equals(ftype)) {
                                    //水果
                                    fruitsNum += num;
                                }

                                Object weight = map.getOrDefault("weight", 0);
                                ingredientStatHandler(ingredientStat, ftype, weight);
                            }
                        }
                    }
                }

            } else {
                for(Map<String, Object> map : breakfastFoodList){
                    Object tempObject = map.get("ftype");
                    if(tempObject!=null){
                        String ftype = tempObject.toString();
                        tempObject = map.get("fnum");
                        if(tempObject!=null){
                            String numString = tempObject.toString();
                            double num = Double.parseDouble(numString);
                            foodNum += num;
                            // 谷物类
                            if("1001001".equals(ftype)){
                                cerealNum += num;
                            } else if("1001003".equals(ftype)){
                                // 蔬菜类
                                vegetablesNum += num;
                            } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                // 肉蛋豆类
                                meatNum += num;
                            } else if("1001012".equals(ftype)){
                                // 浆乳类
                                soymilkNum += num;
                            } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                // 油脂类
                                greaseNum += num;
                            } else if("1001004".equals(ftype)) {
                                //水果
                                fruitsNum += num;
                            }
                        }
                    }
                }

                for(Map<String, Object> map : lunchFoodList){
                    Object tempObject = map.get("ftype");
                    if(tempObject!=null){
                        String ftype = tempObject.toString();
                        tempObject = map.get("fnum");
                        if(tempObject!=null){
                            String numString = tempObject.toString();
                            double num = Double.parseDouble(numString);
                            foodNum += num;
                            if("1001001".equals(ftype)){
                                // 谷物类
                                cerealNum += num;
                            } else if("1001003".equals(ftype)){
                                // 蔬菜类
                                vegetablesNum += num;
                            } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                // 肉蛋豆类
                                meatNum += num;
                            } else if("1001012".equals(ftype)){
                                // 浆乳类
                                soymilkNum += num;
                            } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                // 油脂类
                                greaseNum += num;
                            } else if("1001004".equals(ftype)) {
                                //水果
                                fruitsNum += num;
                            }
                        }
                    }
                }

                for(Map<String, Object> map : dinnerFoodList){
                    Object tempObject = map.get("ftype");
                    if(tempObject!=null){
                        String ftype = tempObject.toString();
                        tempObject = map.get("fnum");
                        if(tempObject!=null){
                            String numString = tempObject.toString();
                            double num = Double.parseDouble(numString);
                            foodNum += num;
                            if("1001001".equals(ftype)){
                                // 谷物类
                                cerealNum += num;
                            } else if("1001003".equals(ftype)){
                                // 蔬菜类
                                vegetablesNum += num;
                            } else if("1001007".equals(ftype) || "1001002".equals(ftype)){
                                // 肉蛋豆类
                                meatNum += num;
                            } else if("1001012".equals(ftype)){
                                // 浆乳类
                                soymilkNum += num;
                            } else if("1001013".equals(ftype) || "1001014".equals(ftype)){
                                // 油脂类
                                greaseNum += num;
                            } else if("1001004".equals(ftype)) {
                                //水果
                                fruitsNum += num;
                            }
                        }
                    }
                }

                heat = foodNum*90;
            }

            nutritionCateringPO.setIngredientStat(JSON.toJSONString(ingredientStat));

            String dfStr = String.format("%.1f",heat);
            nutritionCateringPO.setCaloriesLevel(dfStr);
            dfStr = String.format("%.1f",foodNum);
            nutritionCateringPO.setFoodNum(dfStr);

            dfStr = String.format("%.1f",cerealNum);
            nutritionCateringPO.setCerealNum(dfStr);

            dfStr = String.format("%.1f",meatNum);
            nutritionCateringPO.setMeatNum(dfStr);

            dfStr = String.format("%.1f",fruitsNum);
            nutritionCateringPO.setFruitsNum(dfStr);

            dfStr = String.format("%.1f",vegetablesNum);
            nutritionCateringPO.setVegetablesNum(dfStr);

            dfStr = String.format("%.1f",greaseNum);
            nutritionCateringPO.setGreaseNum(dfStr);

            dfStr = String.format("%.1f",soymilkNum);
            nutritionCateringPO.setSoymilkNum(dfStr);

            dfStr = String.format("%.1f",eggNum);
            nutritionCateringPO.setEggNum(dfStr);

            dfStr = String.format("%.1f",beansNum);
            nutritionCateringPO.setBeansNum(dfStr);

            this.saveRecipe(nutritionCateringPO, doctorId,eohType,caloricType);

            return nutritionCateringPO.getNutritionCateringId();
        }
        return null;
    }

    /**
     * 食谱成分统计
     * @param stat
     * @param type
     * @param weight
     */
    private void ingredientStatHandler(Map<String ,Float> stat ,String type ,Object weight){
        Float g = stat.getOrDefault(type ,0f);
        g += Float.parseFloat(weight.toString());
        stat.put(type ,g);
    }

    /**
     * 保存营养配餐计划(更新or添加)
     * @param nutritionCateringModel
     */
    public void saveRecipe(NutritionCateringPO nutritionCateringModel, String doctorId,Integer eohType,String caloricType){
        String md5 = nutritionCateringModel.getNutritionCateringMD5();
        if(!StringUtils.isBlank(md5)){
          /*  if(nutritionCaterings.size() <= 0) {*/
                //检查配餐方案的食品热量份数关系是否是新的
                md5 = nutritionCateringModel.getRecipesCaloricRelationMD5();
                String objectToJson = JsonSerializer.objectToJson(nutritionCateringModel);
                Map<String, String> jsonToStringMap = JsonSerializer.jsonToStringMap(objectToJson);
                String sid = null;
               /* if(recipesCaloricRelations.size()<=0){
                    sid=DaoHelper.getSeq();
                    //保存新的配餐方案的食品热量份数关系
                    jsonToStringMap.put("md5", md5);
                    jsonToStringMap.put("doctorId", doctorId);
                    jsonToStringMap.put("sid", sid);
                    jsonToStringMap.put("eohType",eohType+"");
                    this.prescriptionMapper.saveRecipesCaloricRelation(jsonToStringMap);
                } else {
                    sid = recipesCaloricRelations.get(0).getRecipesCaloricId();
                }*/

                sid=DaoHelper.getSeq();
                //保存新的配餐方案的食品热量份数关系
                jsonToStringMap.put("md5", md5);
                jsonToStringMap.put("doctorId", doctorId);
                jsonToStringMap.put("sid", sid);
                jsonToStringMap.put("eohType",eohType+"");
                jsonToStringMap.put("caloricType",caloricType);
                if (StringUtils.isBlank(nutritionCateringModel.getMealsModel())){
                    nutritionCateringModel.setMealsModel("0");
                }
                jsonToStringMap.put("mealsModel",nutritionCateringModel.getMealsModel());
                jsonToStringMap.put("recipeName",nutritionCateringModel.getRecipeName());//设置食谱名称
                this.prescriptionMapper.saveRecipesCaloricRelation(jsonToStringMap);
                //保存新的配餐方案
                md5 = nutritionCateringModel.getNutritionCateringMD5();
                String nid = DaoHelper.getSeq();
                jsonToStringMap.put("id", nid);
                jsonToStringMap.put("md5", md5);
                jsonToStringMap.put("doctorId", doctorId);
                jsonToStringMap.put("recipesCaloricRelationId", sid);
                this.prescriptionMapper.saveNutritionCatering(jsonToStringMap);

                //nutritionCaterings = this.dietCache.getNCateringsByMD5(md5);
                nutritionCateringModel.setRecipesCaloricId(sid);
                nutritionCateringModel.setNutritionCateringId(nid);
        /*    } else {
                nutritionCateringModel.setNutritionCateringId(nutritionCaterings.get(0).getNutritionCateringId());
            }*/

            //清除食谱缓存
//            this.removeObjectOfCache("prescriptionResources","nutritionCatering:null(null-null)");
        }
    }


    @Override
    public FoodAllocationVO currentDiet(String currentDietInfo,Integer version) {
/*        currentDietInfo="{\n" +
                "\t\"bs_zczslx\": \"[{\\\"id\\\":\\\"180518092800001\\\",\\\"num\\\":\\\"1\\\",\\\"val\\\":\\\"小麦（龙麦） (1g) \\\"},{\\\"id\\\":\\\"180518092800002\\\",\\\"num\\\":\\\"2\\\",\\\"val\\\":\\\"五谷香 (2g) \\\"}]\",\n" +
                "\t\"bs_wuczslx\": \"[{\\\"id\\\":\\\"180518092800001\\\",\\\"num\\\":\\\"2\\\",\\\"val\\\":\\\"小麦（龙麦） (2g) \\\"},{\\\"id\\\":\\\"180518092800002\\\",\\\"num\\\":\\\"1\\\",\\\"val\\\":\\\"五谷香 (1g) \\\"}]\",\n" +
                "\t\"bs_wanczslx\": \"[{\\\"id\\\":\\\"180518092800001\\\",\\\"num\\\":\\\"1\\\",\\\"val\\\":\\\"小麦（龙麦） (1g) \\\"},{\\\"id\\\":\\\"180518092800002\\\",\\\"num\\\":\\\"2\\\",\\\"val\\\":\\\"五谷香 (2g) \\\"}]\",\n" +
                "\t\"bs_jcnr\": \"[{\\\"id\\\":\\\"180518092800001\\\",\\\"num\\\":\\\"2\\\",\\\"val\\\":\\\"小麦（龙麦） (2g) \\\"},{\\\"id\\\":\\\"180518092800002\\\",\\\"num\\\":\\\"2\\\",\\\"val\\\":\\\"五谷香 (2g) \\\"},{\\\"id\\\":\\\"180518092800003\\\",\\\"num\\\":\\\"2\\\",\\\"val\\\":\\\"小麦粉（标准粉） (2g) \\\"}]\",\n" +
                "\t\"bs_lunch_jc\": \"[{\\\"id\\\":\\\"180518092800351\\\",\\\"num\\\":\\\"2\\\",\\\"val\\\":\\\"苹果 (2g) \\\"},{\\\"id\\\":\\\"180518092800352\\\",\\\"num\\\":\\\"2\\\",\\\"val\\\":\\\"苹果（伏苹果） (2g) \\\"},{\\\"id\\\":\\\"180518092800353\\\",\\\"num\\\":\\\"2\\\",\\\"val\\\":\\\"苹果（国光苹果） (2g) \\\"}]\",\n" +
                "\t\"bs_dinner_jc\": \"[{\\\"id\\\":\\\"180518092800093\\\",\\\"num\\\":\\\"3\\\",\\\"val\\\":\\\"黄豆（大豆） (3g) \\\"},{\\\"id\\\":\\\"180518092800094\\\",\\\"num\\\":\\\"3\\\",\\\"val\\\":\\\"黑豆（黑大豆） (3g) \\\"},{\\\"id\\\":\\\"180518092800095\\\",\\\"num\\\":\\\"3\\\",\\\"val\\\":\\\"青豆（青大豆） (3g) \\\"}]\"\n" +
                "}";
//        currentDietInfo="{}";*/
        Map<String, List<FoodItemBO>> itemMap = this.getEohIngredientsForArchives(currentDietInfo,version);
        List<FoodItemBO> itemList = itemMap.get("itemList");
        List<FoodItemBO> otherItemList = itemMap.get("otherItemList");
        FoodAllocationVO dailyFoodAllocation = PrescriptionHelperOfDiet.getDailyFoodAllocation(itemList, otherItemList);
        return dailyFoodAllocation;
    }


    @Override
    public String insertCustomRecipesDiet(String nutritionCateringJson,Integer foodVersion,String doctorId,Integer eohType) {
        String caloricType = "1";
        String nid = this.saveCustomRecipeHandle(nutritionCateringJson,foodVersion,doctorId,eohType,caloricType);
        return nid;
    }

    @Override
    public List<FoodNutrientBO> listFoodNutrient(String id,String name){
         return  this.prescriptionMapper.listFoodNutrient(id,name);
    }

    @Override
    public  Map<String,Object> getFoodcookbook(String labourIntensity, String bodyType, String mealsModel,Double height,String prescriptionId,String sex,String isjejunitas,Integer type) {
        // 1、 计算能量范围
        Map<String, Object> map = new HashMap<>();
        if (type == 0) {
            if (mealsModel.length() > 1) {
                String[] arr = mealsModel.split(",");
                Map<String, Object> map1 = this.computPowerManger(labourIntensity, bodyType, arr[0], height, prescriptionId, sex, "0");
                Map<String, Object> map2 = this.computPowerManger(labourIntensity, bodyType, arr[1], height, prescriptionId, sex, "1");
                map.put("map1", map1);
                map.put("map2", map2);
            } else if (mealsModel.equals("4")) {
                Map<String, Object> map1 = this.computPowerManger(labourIntensity, bodyType, "4", height, prescriptionId, sex, "0");
                Map<String, Object> map2 = this.computPowerManger(labourIntensity, bodyType, "4", height, prescriptionId, sex, "1");
                map.put("map1", map1);
                map.put("map2", map2);
            } else {
                Map<String, Object> map1 = this.computPowerManger(labourIntensity, bodyType, mealsModel, height, prescriptionId, sex, "");
                map.put("map1", map1);
            }
        }else {
            if (isjejunitas.equals("1")){
                mealsModel = "4";
            }
            map = this.computPowerManger(labourIntensity,bodyType,mealsModel,height,prescriptionId,sex,isjejunitas);
        }
        return map;
    }

    /**
     * 肥胖饮食技巧处理
     * @param memberId
     * @param doctorId
     * @return
     */
    private String fatDietSkillHandler(String memberId , String doctorId){
        /**
         * 1 2 3 分别对应A	常见食物脂肪含量  B 常见高胆固醇食物	  C	食物嘌呤含量分类
         */
        TreeSet<Integer> skillList = new TreeSet<>();

        ApiMemberBO apiMemberBO = this.prescriptionApi.getMemberByMemberId(memberId);
        ApiMemberArchivesBO apiMemberArchivesBO = this.prescriptionApi.getMemberArchivesById(memberId ,doctorId);
        MemberLatestSignVO memberLatestSignVO = this.prescriptionApi.getMemberLatestSign(memberId);
        CheckoutBloodFatBO checkoutBloodFatBO = memberLatestSignVO.getBloodFat();
        String archiveString = apiMemberArchivesBO.getArchivesJson();
        JSONObject archiveJson = null;
        if(!StringUtils.isBlank(archiveString)){
            archiveJson = JSONObject.parseObject(archiveString);
        }
        if(checkoutBloodFatBO != null){
            /**
             * 总胆固醇（mmol/L）	 ≤4.49	≥4.5
             */
            if(!StringUtils.isBlank(checkoutBloodFatBO.getTc()) && Float.parseFloat(checkoutBloodFatBO.getTc() ) >= 4.5f){
                skillList.add(3);
            }
            /**
             * 甘油三酯（mmol/L）	 ≤1.69	≥1.7
             */
            if(!StringUtils.isBlank(checkoutBloodFatBO.getTriglyceride()) && Float.parseFloat(checkoutBloodFatBO.getTriglyceride() ) >= 1.7f){
                skillList.add(2);
            }
            /**
             * 高密度脂蛋白（mmol/L）	男：≥1.1            女：≥1.31 	男：≤1.10           女：≤1.30
             */
            if("1".equals(apiMemberBO.getSex()) && !StringUtils.isBlank(checkoutBloodFatBO.getHdl()) && Float.parseFloat(checkoutBloodFatBO.getHdl()) <= 1.1f){
                skillList.add(3);
            }
            if("2".equals(apiMemberBO.getSex()) && !StringUtils.isBlank(checkoutBloodFatBO.getHdl()) && Float.parseFloat(checkoutBloodFatBO.getHdl()) <= 1.3f){
                skillList.add(3);
            }
            if(archiveJson != null){
                JSONObject complication = archiveJson.getJSONObject("complication");
                if(complication != null){
                    String chd = complication.getString("chd");
                    if("QZ01".equals(chd) && !StringUtils.isBlank(checkoutBloodFatBO.getLdl()) && Float.parseFloat(checkoutBloodFatBO.getLdl()) >= 1.8f){
                        skillList.add(3);
                    }else if(!StringUtils.isBlank(checkoutBloodFatBO.getLdl()) && Float.parseFloat(checkoutBloodFatBO.getLdl()) >= 2.6f){
                        skillList.add(3);
                    }
                }
            }
        }
        /*if(archiveJson != null){
            JSONObject lab = archiveJson.getJSONObject("lab");
            if(lab != null){
                Float lab_xns = lab.getFloat("lab_xns");
                if(lab_xns != null && lab_xns.floatValue() > 420f){
                    skillList.add(5);
                }
            }
        }*/
        /**
         *  尿酸 > 420
         */
        if (!StringUtils.isBlank(checkoutBloodFatBO.getUric()) && Float.parseFloat(checkoutBloodFatBO.getUric() ) > 420f){
            skillList.add(5);
        }
        return Joiner.on(",").join(skillList);
    }

    private List<ProblemSuggestionVO>  getFatProblemSuggestion( BaseParamOfDietDTO baseParamOfDietDTO){
        List<ProblemSuggestionVO> problemSuggestionList = new ArrayList<>();
        GetPrescriptionDetailDTO getPrescriptionDetailDTO = new GetPrescriptionDetailDTO();
        getPrescriptionDetailDTO.setMemberId(baseParamOfDietDTO.getMemberId());
        getPrescriptionDetailDTO.setEohType(PrescriptionConstant.PRESCRIPTION_EOH_TYPE_FAT);
        getPrescriptionDetailDTO.setType(3);
        PrescriptionDetailPO prescriptionDetailPO = this.prescriptionMapper.getNewPrescriptionDetailByType(getPrescriptionDetailDTO);
        if (prescriptionDetailPO!=null && !StringUtils.isBlank(prescriptionDetailPO.getDetailJson())) {
            PrescriptionPO prescriptionPO = this.prescriptionMapper.getPrescriptionById(prescriptionDetailPO.getPrescriptionId());
            if (prescriptionPO != null && !StringUtils.isBlank(prescriptionPO.getMemberInfoJson()) ){
                Map<String, String> map = JsonSerializer.jsonToStringMap(prescriptionDetailPO.getDetailJson());
                Map<String, String> Membermap = JsonSerializer.jsonToStringMap(prescriptionPO.getMemberInfoJson());
                if (!StringUtils.isBlank(map.get("weightLoss")) && !StringUtils.isBlank(Membermap.get("weight"))) {
                    Double weightLoss = Double.parseDouble(map.get("weightLoss"));
                    Double lastWeight = Double.parseDouble(Membermap.get("weight"));
                    Double diffValue = lastWeight - baseParamOfDietDTO.getWeight();
                    if (diffValue < weightLoss) {
                        ProblemSuggestionVO vo = new ProblemSuggestionVO();
                        vo.setCode("1001");
                        if (diffValue == 0 || diffValue == 0.0){
                            vo.setDescription("上次体重为" + lastWeight + "kg,饮食治疗目标为体重减轻" + weightLoss + "kg,本次体重为" + baseParamOfDietDTO.getWeight() + "kg，与上次相比无变化.");
                        }else if (diffValue < 0) {
                            vo.setDescription("上次体重为" + lastWeight + "kg,饮食治疗目标为体重减轻" + weightLoss + "kg,本次体重为" + baseParamOfDietDTO.getWeight() + "kg，与上次相比增加" + Math.abs(diffValue) + "kg.");
                        } else {
                            vo.setDescription("上次体重为" + lastWeight + "kg,饮食治疗目标为体重减轻" + weightLoss + "kg,本次体重为" + baseParamOfDietDTO.getWeight() + "kg，与上次相比减轻" + Math.abs(diffValue) + "kg.");
                        }
                        vo.setProblem("未达到饮食治疗目标");
                        vo.setSuggestion("建议严格执行制定的食谱");
                        problemSuggestionList.add(vo);
                    }
                }
            }
        }
        return problemSuggestionList;
    }

    private  Map<String,Object> computPowerManger(String labourIntensity, String bodyType, String mealsModel,Double height,String prescriptionId,String sex,String isjejunitas){
        // 1、标椎体重
        Double  dhw = height - 105;

        // 2 、计算能量系数
        String powerMin = "";
        String powerMax = "";
        String powerS = "";
        if (labourIntensity.equals("RCHDQD01")){
            if (bodyType.equals("0")){
                powerS = "35";
            }else if (bodyType.equals("1")){
                powerS = "30";
            }else {
                powerMin = "20";
                powerMax = "25";
            }
        }else if (labourIntensity.equals("RCHDQD02")){
            if (bodyType.equals("0")){
                powerS = "40";
            }else if (bodyType.equals("1")){
                powerS = "35";
            }else {
                powerS = "30";
            }

        }else if (labourIntensity.equals("RCHDQD03")){
            if (bodyType.equals("0")){
                powerS = "45";
            }else if (bodyType.equals("1")){
                powerS = "40";
            }else {
                powerS = "35";
            }
        }else {
            if (bodyType.equals("0")){
                powerMin = "20";
                powerMax = "25";
            }else if (bodyType.equals("1")){
                powerMin = "15";
                powerMax = "20";
            }else {
                powerS = "15";
            }
        }

        // 3、 根据模式计算范围
        String minKcal = "";
        String maxKcal = "";
        if (mealsModel.equals("1") || mealsModel.equals("3")){
            if (StringUtils.isBlank(powerS)){
                minKcal = Double.parseDouble(powerMin)*dhw + "";
                maxKcal = Double.parseDouble(powerMax) * dhw + "";
            }else {
                minKcal = Double.parseDouble(powerS) *dhw *0.9+ "";
                maxKcal = Double.parseDouble(powerS) *dhw *1.1+ "";
            }
        }else if (mealsModel.equals("2")){
            minKcal = "1000";
            maxKcal = "1500";
        }else if (mealsModel.equals("4") && isjejunitas.equals("0")){
            if (StringUtils.isBlank(powerS)){
                minKcal = Double.parseDouble(powerMin)*dhw + "";
                maxKcal = Double.parseDouble(powerMax) * dhw + "";
            }else {
                minKcal = Double.parseDouble(powerS) *dhw *0.9+ "";
                maxKcal = Double.parseDouble(powerS) *dhw *1.1+ "";
            }
        }else if (mealsModel.equals("4") && isjejunitas.equals("1")){
            if (sex.equals("0")){
                minKcal = "600";
                maxKcal = "600";
            }else {
                minKcal = "500";
                maxKcal = "500";
            }
        }
        //2.推荐食谱数据源（下拉框）
        GetPrescriptionDTO dto = new GetPrescriptionDTO();
        dto.setSid(prescriptionId);
        PrescriptionPO prescription = this.prescriptionMapper.getPrescriptionByParam(dto);
        GetNutritionCateringDTO dto1 = new GetNutritionCateringDTO();
        dto1.setEohType(3); //糖尿病类型
        List<String> doctorIdList = this.prescriptionApi.getDoctorIdList(prescription.getDoctorId(),1);
        if (doctorIdList.size()>0) {
            dto1.setDoctorIdList(doctorIdList);
        }
        dto1.setMealsModel(mealsModel);
        List<NutritionCateringPO> nutritionCateringPOS = this.dietCache.loadNutritionCateringList(dto1);
        List<NutritionCateringTreeVO> nutritionCateringTreeVOS = PrescriptionHelperOfDiet.nCMLConvertToEFAML(nutritionCateringPOS);
        this.doPrescriptionNCTreeTot(nutritionCateringTreeVOS,prescription.getVersion());
        NutritionCateringTreeVO currentNutritionCateringTreeVO = this.getCurrentNutritionCateringByHeat2(nutritionCateringTreeVOS,
                minKcal,maxKcal);
        Map<String,Object> map = new HashMap<>();
        map.put("minKcal",minKcal);
        map.put("maxKcal",maxKcal);
        if (currentNutritionCateringTreeVO == null){
            currentNutritionCateringTreeVO = new NutritionCateringTreeVO();
            NutritionCateringItemVO itemVO = new NutritionCateringItemVO();
            List<NutritionCateringItemVO> itemVOS = new ArrayList<>();
            itemVOS.add(itemVO);
            currentNutritionCateringTreeVO.setNutritionCateringItems(itemVOS);
        }
        map.put("nutritionCateringTreeVO",currentNutritionCateringTreeVO);
        if (nutritionCateringTreeVOS.size() <1){
            nutritionCateringTreeVOS.add(currentNutritionCateringTreeVO);
        }
        map.put("nutritionCateringTreeVOS",nutritionCateringTreeVOS);
        return map;
    }

    /**
     * 获取患者当前推荐食谱
     * @param vos
     * @param heatStr
     * @return
     */
    private NutritionCateringTreeVO getCurrentNutritionCateringByHeat2(List<NutritionCateringTreeVO> vos, String heatStr,String heatStr2){
        NutritionCateringTreeVO treeVO = null;
        if(vos!=null&&vos.size()>0 && !StringUtils.isBlank(heatStr)){
            List<NutritionCateringItemVO> oneItem = null;
            double heat = Double.parseDouble(heatStr);
            double heat2 = Double.parseDouble(heatStr2);
            for(NutritionCateringTreeVO v : vos){
                List<NutritionCateringItemVO> items = v.getNutritionCateringItems();
                if(!StringUtils.isBlank(v.getCaloriesLevel()) && items!=null && items.size()>0){
                    String level = v.getCaloriesLevel();
                    double d = Double.parseDouble(level);
                    if(heat <= d && d <= heat2){
                        oneItem = new ArrayList<NutritionCateringItemVO>(1);
                        oneItem.add(items.get(0));
                        treeVO = new NutritionCateringTreeVO();
                        BeanUtils.copyProperties(treeVO,v);
                        treeVO.setNutritionCateringItems(oneItem);
                        break;
                    }
                }
            }
            if(treeVO==null){
                double flag = 100;
                for(int i = 0;i<vos.size();i++){
                    NutritionCateringTreeVO v = vos.get(i);
                    List<NutritionCateringItemVO> items = v.getNutritionCateringItems();
                    if(!StringUtils.isBlank(v.getCaloriesLevel()) && items!=null && items.size()>0){
                        String level = v.getCaloriesLevel();
                        double d = Double.parseDouble(level);
                        double d1 = Math.abs(d-heat);
                        double d2 = Math.abs(d-heat2);
                        if (heat2< d && (i == 0)){
                            oneItem = new ArrayList<NutritionCateringItemVO>(1);
                            oneItem.add(items.get(0));
                            treeVO = new NutritionCateringTreeVO();
                            BeanUtils.copyProperties(treeVO,v);
                            treeVO.setNutritionCateringItems(oneItem);
                            break;
                        }else if(d < heat && (i== (vos.size() - 1))){
                            oneItem = new ArrayList<NutritionCateringItemVO>(1);
                            oneItem.add(items.get(0));
                            treeVO = new NutritionCateringTreeVO();
                            BeanUtils.copyProperties(treeVO,v);
                            treeVO.setNutritionCateringItems(oneItem);
                            break;
                        }else if (i< (vos.size()-1) && d< heat){
                            double level2 = Math.abs(Double.parseDouble(vos.get(i+1).getCaloriesLevel()));
                            double d3 = Math.abs(level2-heat2);
                            if ( heat2<level2 && d1 < d3) {
                                oneItem = new ArrayList<NutritionCateringItemVO>(1);
                                oneItem.add(items.get(0));
                                treeVO = new NutritionCateringTreeVO();
                                BeanUtils.copyProperties(treeVO,v);
                                treeVO.setNutritionCateringItems(oneItem);
                                break;
                            }else if (heat2 < level2 && d1 > d3){
                                oneItem = new ArrayList<NutritionCateringItemVO>(1);
                                oneItem.add(items.get(0));
                                treeVO = new NutritionCateringTreeVO();
                                BeanUtils.copyProperties(treeVO,vos.get(i+1));
                                treeVO.setNutritionCateringItems(oneItem);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return treeVO;
    }

    private static final Cache<String , NutritionCateringItemVO> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5 , TimeUnit.MINUTES)
            .maximumSize(5000)
            .initialCapacity(20)
            .build();

}
