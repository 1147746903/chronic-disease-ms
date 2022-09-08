package com.comvee.cdms.prescription.contorller.web;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.prescription.bo.FoodItemBO;
import com.comvee.cdms.prescription.bo.FoodNutrientBO;
import com.comvee.cdms.prescription.bo.PagerBO;
import com.comvee.cdms.prescription.cfg.PrescriptionLock;
import com.comvee.cdms.prescription.dto.*;
import com.comvee.cdms.prescription.po.NutritionCateringPO;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.po.PrescriptionKnowledgePO;
import com.comvee.cdms.prescription.service.PrescriptionOfDietServiceI;
import com.comvee.cdms.prescription.service.PrescriptionOfEduServiceI;
import com.comvee.cdms.prescription.service.PrescriptionServiceI;
import com.comvee.cdms.prescription.vo.*;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeTreeVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 李左河
 * @date 2018/8/2 9:31.
 */
@RestController
@RequestMapping("/web/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionServiceI prescriptionService;
    @Autowired
    private PrescriptionOfDietServiceI prescriptionOfDietService;
    @Autowired
    PrescriptionOfEduServiceI prescriptionOfEduService;

    /**
     * 获取管理处方分页列表
     * @param pageDTO
     * @return
     */
    @RequestMapping("listPrescriptionPage")
    public Result listPrescriptionPage(GetPrescriptionPageDTO pageDTO){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        //String hospitalId = doctor.getHospitalId();
        String doctorId = doctor.getDoctorId();
        //pageDTO.setHospitalId(hospitalId);
        if(StringUtils.isBlank(pageDTO.getDoctorId())){
            pageDTO.setDoctorId(doctorId);
        }
        //不知道什么逻辑
/*        if (null == pageDTO.getFindOrigin()){
            pageDTO.setDoctorId(null);
        }*/
        PageResult<PrescriptionVO> result = this.prescriptionService.listPrescriptionPage(pageDTO);
        Result Result = new Result(result);
        return Result;
    }

    /**
     * 获取处方模块
     * @author 李左河
     * @date 2018/8/2 10:00
     * @param prescriptionDTO
     * @return com.comvee.cdms.common.wrapper.Result
     *
     */
    @RequestMapping("loadPrescriptionDetailByModule")
    public Result loadPrescriptionDetailByModule(@Validated({PrescriptionGetGroup.class}) PrescriptionDTO prescriptionDTO) {
        PrescriptionDetailVO prescriptionDetailModel = this.prescriptionService.loadPrescriptionDetailByModule(prescriptionDTO);
        return new Result(prescriptionDetailModel);
    }

    /**
     * 获取处方患者基本信息
     * @param prescriptionId
     * @return
     */
    @RequestMapping("getPrescriptMemberInfo")
    public Result getPrescriptMemberInfo(String prescriptionId){
        PrescriptionMemberVO prescriptionMember = this.prescriptionService.getPrescriptMemberInfo(prescriptionId);
        return new Result(prescriptionMember);
    }

    /**
     * 创建处方
     * @author 李左河
     * @date 2018/8/6 16:06
     * @param prescriptionDTO
     * @return com.comvee.cdms.common.wrapper.Result
     *
     */
    @RequestMapping("createPrescription")
    public Result createPrescription(@Validated(value = {PrescriptionInsertGroup.class}) PrescriptionDTO prescriptionDTO){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        DoctorDTO doctorDTO = new DoctorDTO();
        BeanUtils.copyProperties(doctorDTO,doctor);
        prescriptionDTO.setHospitalId(doctor.getHospitalId());
        String prescriptionId = this.prescriptionService.createPrescription(prescriptionDTO, doctorDTO);
        return new Result(prescriptionId);
    }

    /**
     * 智能推荐
     * @author 李左河
     * @date 2018/8/6 10:14
     * @param prescriptionDTO
     * @return com.comvee.cdms.common.wrapper.Result
     *
     */
    @RequestMapping("intelligentRecommendationByModule")
    public Result intelligentRecommendationByModule(@Validated(value = {PrescriptionGetIntelligentGroup.class}) PrescriptionDTO prescriptionDTO) {
        PrescriptionDetailVO prescriptionDetailVO = this.prescriptionService.intelligentRecommendationByModule(prescriptionDTO);
        return new Result(prescriptionDetailVO);
    }

    /**
     * 修改管理处方 子模块
     * @author 李左河
     * @date 2018/8/6 17:12
     * @param prescriptionDTO
     * @return com.comvee.cdms.common.wrapper.Result
     *
     */
    @RequestMapping("modifyPrescriptionByModule")
    public Result modifyPrescriptionByModule(@Validated(value = {PrescriptionModifyGroup.class}) PrescriptionDTO prescriptionDTO) {
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        prescriptionDTO.setDoctorId(doctorModel.getDoctorId());
        DoctorDTO doctorDTO = new DoctorDTO();
        BeanUtils.copyProperties(doctorDTO,doctorModel);
        if(prescriptionDTO.getSchedule() != null && prescriptionDTO.getSchedule() == 3){
            PrescriptionLock.HAND_DOWN_PRESCRIPTION_LOCK.lock();
        }
        try{
            this.prescriptionService.modifyPrescriptionByModule(prescriptionDTO, doctorDTO);
        }finally {
            if(prescriptionDTO.getSchedule() != null && prescriptionDTO.getSchedule() == 3){
                if(PrescriptionLock.HAND_DOWN_PRESCRIPTION_LOCK.isLocked()){
                    PrescriptionLock.HAND_DOWN_PRESCRIPTION_LOCK.unlock();
                }
            }
        }
        return new Result("");
    }

    /**
     * 获取管理处方，下发数量
     * @author 李左河
     * @date 2018/8/9 16:35
     * @return com.comvee.cdms.common.wrapper.Result
     *
     */
    @RequestMapping("getHandDownCount")
    public Result getHandDownCount(){
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        Integer count = this.prescriptionService.getHandDownCount(doctorModel.getDoctorId());
        return new Result(count);
    }

    /**
     * 获取患者的管理处方数量
     * @param memberId
     * @return
     */
    @RequestMapping("countPrescriptionForMember")
    public Result countPrescriptionForMember(String memberId){
        ValidateTool.checkParameterIsNull("memberId", memberId);
        Long count = this.prescriptionService.countPrescriptionForMember(memberId);
        return new Result(count);
    }


    /**
     * 下发管理处方
     * @author 李左河
     * @date 2018/8/24 14:57
     * @param prescriptionDTO
     * @return com.comvee.cdms.common.wrapper.Result
     *
     */
    @RequestMapping("handDownPrescription")
    public Result handDownPrescription(@Validated(value = {PrescriptionIdGroup.class}) PrescriptionDTO prescriptionDTO) {
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        DoctorDTO doctorDTO = new DoctorDTO();
        BeanUtils.copyProperties(doctorDTO,doctorModel);
        String name = "糖尿病自我管理处方";
        this.prescriptionService.handDownPrescription(prescriptionDTO.getPrescriptionId(), doctorDTO,name);
        return new Result("下发管理处方成功");
    }

    /**
     * 下发管理处方
     * @author wangxy
     * @date 2018/8/24 14:57
     * @param countPrescriptionDTO
     *
     */
    @RequestMapping("countPrescription")
    public Result countPrescription(CountPrescriptionDTO countPrescriptionDTO) {
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        countPrescriptionDTO.setDoctorId(doctorModel.getDoctorId());
        long re = this.prescriptionService.countPrescription(countPrescriptionDTO);
        return new Result(re);
    }


    /**
     * 根据管理处方id，删除管理处方
     * @author 李左河
     * @date 2018/8/27 14:57
     * @param prescriptionDTO
     * @return com.comvee.cdms.common.wrapper.Result
     *
     */
    @RequestMapping("deletePrescriptionById")
    public Result deletePrescriptionById(@Validated(value = {PrescriptionIdGroup.class}) PrescriptionDTO prescriptionDTO) {

        this.prescriptionService.deletePrescriptionById(prescriptionDTO);
        return new Result("删除管理处方成功");
    }

    /**
     * 知识点树型结构
     * @author 李左河
     * @date 2018/8/13 15:50
     * @param prescriptionEduDTO
     * @return com.comvee.cdms.common.wrapper.Result
     *
     */
    @RequestMapping("knowledgeTree")
    public Result knowledgeTree(PrescriptionEduDTO prescriptionEduDTO){
        List<KnowledgeTreeVO> knowledgeTreeVOList = this.prescriptionService.knowledgeTree(prescriptionEduDTO);
        return new Result(knowledgeTreeVOList);
    }

    /**
     * 获取文章表分页信息
     * @author 李左河
     * @date 2018/8/13 16:42
     * @param prescriptionEduDTO
     * @param pager
     * @return com.comvee.cdms.common.wrapper.Result
     *
     */
    @RequestMapping("loadArticle")
    public Result loadArticle(PrescriptionEduDTO prescriptionEduDTO,PagerBO pager) {
        List<KnowledgeTreeVO> list = this.prescriptionService.loadArticle(prescriptionEduDTO, pager);
        PageResult pageResult = new PageResult(list);
        return new Result(pageResult);
    }

    /**
     * 获取食材库
     * @param dto
     * @return
     */
    @RequestMapping("listIngredientsItem")
    public Result listIngredientsItem(GetFoodItemDTO dto){
        List<FoodItemBO> foodItemBOS = this.prescriptionService.listIngredientsItem(dto);
        return new Result(foodItemBOS);
    }

    /**
     * 获取食材库分类
     * @param dto
     * @return
     */
    @RequestMapping("listIngredientsTypeOfFood")
    public Result listIngredientsTypeOfFood(GetFoodItemDTO dto){
        List<FoodItemBO> foodItemBOS = this.prescriptionService.listIngredientsTypeOfFood(dto);
        return new Result(foodItemBOS);
    }

    /**
     * 获取处方主表信息
     */
    @RequestMapping("getPrescriptionById")
    public Result getPrescriptionById(String prescriptionId) {
        ValidateTool.checkParameterIsNull("prescriptionId", prescriptionId);
        PrescriptionVO vo = this.prescriptionService.getPrescriptionById(prescriptionId);
        return new Result(vo);
    }

    /**
     * 目前饮食情况
     * @param currentDietInfo 目前饮食信息
     * @return
     */
    @RequestMapping("currentDiet")
    public Result currentDiet(String currentDietInfo,Integer version) {
        ValidateTool.checkParameterIsNull("currentDietInfo",currentDietInfo);
        ValidateTool.checkParameterIsNull("version",version);
        FoodAllocationVO vo = this.prescriptionOfDietService.currentDiet(currentDietInfo, version);
        return new Result(vo);
    }

    /**
     * 获取最新处方详情根据模块类型
     * @param dto
     * @return
     */
    @RequestMapping("getNewPrescriptionDetailByType")
    public Result getNewPrescriptionDetailByType(GetPrescriptionDetailDTO dto) {
        ValidateTool.checkParameterIsNull("患者id(memberId)",dto.getMemberId());
        ValidateTool.checkParameterIsNull("管理处方模块(type)",dto.getType());
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        dto.setHospitalId(doctorModel.getHospitalId());
        PrescriptionDetailPO po = this.prescriptionService.getNewPrescriptionDetailByType(dto);
        return new Result(po);
    }

    /**
     * 添加患者数据（BMI,血压，血糖，血脂）
     * @param type 1:bmi 2:血压 3:血糖 4:血脂
     * @param dataJson 数据JSON
     *                 type=1:
     *                  dataJson:{
     *                      bmi:"",
     *                      weight:"",
     *                      height:"",
     *                      recordDt:"",//记录时间
     *                      memberId:"",//患者编号
     *                      origin:"",//来源 1小程序 2web 3app
     *                      operatorType:"",//记录用户类型 1患者 2医护
     *                      operatorId:""//记录者编号
     *                  }
     *                 type=2:
     *                  dataJson:{
     *                      dbp:"",
     *                      sbp:"",
     *                      recordTime:"",//记录时间
     *                      memberId:"",//患者编号
     *                      origin:"",//来源 1小程序 2web 3app
     *                      operatorType:"",//记录用户类型 1患者 2医护
     *                      operatorId:""//记录者编号
     *                  }
     *                 type=3:
     *                  dataJson:{
     *                      paramCode:"",
     *                      paramValue:"",//血糖值
     *                      recordValue:"",//糖化值
     *                      inHos:"",
     *                      recordDt:"",//记录时间
     *                      memberId:"",//患者编号
     *                      origin:"",//来源 1小程序 2web 3app
     *                      operatorType:"",//记录用户类型 1患者 2医护
     *                      operatorId:""//记录者编号
     *                  }
     *                 type=4:
     *                  dataJson:{
     *                      recordDt:"",//记录时间
     *                      memberId:"",//患者编号
                            ldl : "",
                            hdl : "",
                            tc : "",
                            triglyceride : ""
     *                  }
     * @return
     */
    @RequestMapping("addMemberDataOfPrescription")
    public Result addMemberDataOfPrescription(String type,String dataJson){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        this.prescriptionService.addMemberDataOfPrescription(type ,dataJson ,doctorSessionBO.getDoctorId());
        return Result.ok("添加成功");
    }

    /**
     * 获取患者最新的体征记录
     * @return
     */
    @RequestMapping("getMemberLatestSign")
    public Result getMemberLatestSign(String memberId){
        ValidateTool.checkParameterIsNull(memberId , "memberId");
        MemberLatestSignVO memberLatestSignVO = this.prescriptionService.getMemberLatestSign(memberId);
        return Result.ok(memberLatestSignVO);
    }

    /** v4.2.1
     * 加载患者最新下发课程
     * @param eohType  疾病类型 1 糖尿病 2 高血压
     * @return
     */
    @RequestMapping("/loadNewSendArticle")
    public Result loadNewSendArticle(String eohType,String memberId){
        ValidateTool.checkParameterIsNull(eohType , "eohType");
        ValidateTool.checkParameterIsNull(memberId , "memberId");
        List<PrescriptionKnowledgePO> pos = this.prescriptionOfEduService.loadNewSendArticle(eohType,memberId);
        return Result.ok(pos);
    }


    /* v6.7.0
     *   加载自定义食谱模板列表
     *  @param dto
     * @return
     * */
    @RequestMapping("listRecipetemplates")
    public Result listRecipetemplates(String mealsModel,String eohType,int rows,int page,String nutritionCateringId){
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        PageResult<NutritionCateringPO> list = this.prescriptionService.listRecipetemplates(doctorModel.getDoctorId(),mealsModel,eohType,rows,page,nutritionCateringId);
        Result result = new Result(list);
        return result;
    }

    /*
     *   增加自定义食谱
     *   @param dto
     *  @return
     * */
    @RequestMapping("insertCustomRecipes")
    public Result insertCustomRecipes(String nutritionCateringJson,Integer foodVersion,Integer eohType){
        Result result = new Result();
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        if (this.prescriptionService.insertCustomRecipes(nutritionCateringJson,foodVersion,doctorModel.getDoctorId(),eohType)!=null){
            result.setCode("0");
            result.setMessage("保存成功");
        }
        return result;
    }

    /*
     *  修改自定义食谱
     * */
    @RequestMapping("modifyCustomRecipes")
    public Result modifyCustomRecipes(String nutritionCateringJson,String recipesCaloricId,String nutritionCateringId,String mealsModel,String eohType){
        Result result = new Result();
        String recipesString = this.prescriptionService.modifyCustomRecipes(nutritionCateringJson,recipesCaloricId,nutritionCateringId,mealsModel,eohType);
        if (recipesString!=null && !recipesString.equals("")){
            result.setCode("0");
            result.setMessage("修改成功");
        }
        return result;
    }

    /*  v6.7.0
     *  删除自定义食谱
     * */
    @RequestMapping("delCustomRecipes")
    public Result delCustomRecipes(String nutritionCateringId){
        this.prescriptionService.delCustomRecipes(nutritionCateringId);
        return new Result("删除模板成功");
    }

    /*  v6.7.0
     *  获取营养素
     * */
    @RequestMapping("listFoodNutrient")
    public Result listFoodNutrient(String id,String name){
        List<FoodNutrientBO> foodNutrientBOS = this.prescriptionService.listFoodNutrient(id,name);
        return new Result(foodNutrientBOS);
    }

    /*
    *   v6.7.0
    *   获取食谱
    * */
    @RequestMapping("getFoodRange")
    public Result getFoodRange(String labourIntensity,String bodyType,String mealsModel,Double height,String prescriptionId,String sex,String isjejunitas){
        Map<String,Object> map = this.prescriptionService.getFoodcookbook(labourIntensity,bodyType,mealsModel,height,prescriptionId,sex,isjejunitas,0);
        return  new Result(map);
    }

    @RequestMapping("getFoodbook")
    public Result getFoodbook(String labourIntensity,String bodyType,String mealsModel,Double height,String prescriptionId,String sex,String isjejunitas){
        if (!ValidateTool.checkIsNull(labourIntensity)){
            return  new Result("1000","参数labourIntensity不能为空",true);
        }
        if (!ValidateTool.checkIsNull(bodyType)){
            return  new Result("1000","参数bodyType不能为空",true);
        }
        if (!ValidateTool.checkIsNull(mealsModel)){
            return  new Result("1000","参数mealsModel不能为空",true);
        }
        if (!ValidateTool.checkIsNull(height)){
            return  new Result("1000","参数height不能为空",true);
        }
        if (!ValidateTool.checkIsNull(prescriptionId)){
            return  new Result("1000","参数prescriptionId不能为空",true);
        }
        if (mealsModel.equals("4")){
            if (!ValidateTool.checkIsNull(sex) || !ValidateTool.checkIsNull(isjejunitas)){
                return  new Result("1000","模式是轻断食，参数sex或isjejunitas不能为空",true);
            }
        }
        Map<String,Object> map = this.prescriptionService.getFoodcookbook(labourIntensity,bodyType,mealsModel,height,prescriptionId,sex,isjejunitas,1);
        return  new Result(map);
    }
}
