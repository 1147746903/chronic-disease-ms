package com.comvee.cdms.prescription.service;


import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.member.dto.MemberDataDTO;
import com.comvee.cdms.prescription.bo.FoodItemBO;
import com.comvee.cdms.prescription.bo.FoodNutrientBO;
import com.comvee.cdms.prescription.bo.PagerBO;
import com.comvee.cdms.prescription.bo.PrescriptionBO;
import com.comvee.cdms.prescription.dto.*;
import com.comvee.cdms.prescription.po.*;
import com.comvee.cdms.prescription.vo.*;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeTreeVO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;

import java.util.List;
import java.util.Map;

public interface PrescriptionServiceI {


    /**
     * 创建处方
     * @author 李左河
     * @date 2018/8/6 16:06
     * @param prescriptionDTO
     * @param doctor
     * @return java.lang.String
     *
     */
    String createPrescription(PrescriptionDTO prescriptionDTO, DoctorDTO doctor);

    /**
     * 获取管理处方子模块 智能推荐
     * @author 李左河
     * @date 2018/8/6 10:19
     * @param prescriptionDTO
     * @return com.comvee.cdms.prescription.vo.PrescriptionDetailVO
     *
     */
    PrescriptionDetailVO intelligentRecommendationByModule(PrescriptionDTO prescriptionDTO);

    /**
     * 保存模块信息
     * (建议所有模块都使用这个方法内保存）
     * @param prescriptionBO
     */
    void modifyPrescriptionDetail(PrescriptionBO prescriptionBO);

    /**
     * 根据模块获取处方模块内容
     * (建议所有模块都在这个方法内获取模块信息）
     * @param prescriptionDTO
     * @return
     */
    PrescriptionDetailVO  loadPrescriptionDetailByModule(PrescriptionDTO prescriptionDTO);

    /**
     * 获取处方根据处方编号
     * @param prescriptId
     * @return
     */
    PrescriptionVO getPrescriptionById(String prescriptId);

    /**
     * 删除未完成的处方
     * @param memberId
     * @param currentLeaderId
     */
    void deleteUnFinalPrescriptionByMid(String memberId, String currentLeaderId);

    /**
     * 获取处方列表根据患者和制定者列表
     * @return
     */
    List<PrescriptionVO> listPrescriptionOfMember(GetPrescriptionDTO dto);

    /**
     * 获取管理处方的患者信息
     * @param prescriptionId
     * @return
     */
    PrescriptionMemberVO getPrescriptMemberInfo(String prescriptionId);

    /**
     * 管理处方分页数据
     * @param pageDTO
     * @return
     */
    PageResult<PrescriptionVO> listPrescriptionPage(GetPrescriptionPageDTO pageDTO);

    /**
     * 根据条件，获取管理处方详情模块
     * @author 李左河
     * @date 2018/8/2 15:06
     * @param dto
     * @return com.comvee.cdms.prescription.po.PrescriptionDetailPO
     *
     */
    PrescriptionDetailPO getPrescriptionDetailByParam(GetPrescriptionDetailDTO dto);
    
    /**
     * 获取管理处方的推荐监测方案
     * @param sugarmonitor
     * @return
     */
    List<PrescriptionSugarmonitorVO> loadEohSugarmonitorByItem(GetPrescriptionSugarmonitorDTO sugarmonitor);

    /**
     * 修改管理处方 子模块
     * @author 李左河
     * @date 2018/8/6 17:13
     * @param prescriptionDTO
     * @param doctorModel
     * @return void
     *
     */
    void modifyPrescriptionByModule(PrescriptionDTO prescriptionDTO, DoctorDTO doctorModel);

    /**
     * 获取管理处方，下发数量
     * @author 李左河
     * @date 2018/8/9 16:37
     * @param doctorId
     * @return java.lang.Integer
     *
     */
    Integer getHandDownCount(String doctorId);

    /**
     * 知识点树型结构
     * @author 李左河
     * @date 2018/8/13 15:51
     * @param prescriptionEduDTO
     * @return java.util.List<com.comvee.cdms.prescription.vo.eduplan.KnowledgeTreeVO>
     *
     */
    List<KnowledgeTreeVO> knowledgeTree(PrescriptionEduDTO prescriptionEduDTO);

    /**
     * 获取文章表分页信息
     * @author 李左河
     * @date 2018/8/13 16:50
     * @param prescriptionEduDTO
     * @param pager
     * @return java.util.List<com.comvee.cdms.prescription.vo.eduplan.KnowledgeTreeVO>
     *
     */
    List<KnowledgeTreeVO> loadArticle(PrescriptionEduDTO prescriptionEduDTO, PagerBO pager);

    /**
     * 获取处方的食材库
     * @param dto
     * @return
     */
    List<FoodItemBO> listIngredientsItem(GetFoodItemDTO dto);

    /**
     * 获取处方的食材库分类
     * @param dto
     * @return
     */
    List<FoodItemBO> listIngredientsTypeOfFood(GetFoodItemDTO dto);

    /**
     * 下发管理处方
     * @author 李左河
     * @date 2018/8/24 14:57
     * @param prescriptionId
     * @param doctorModel
     * @return void
     *
     */
    void handDownPrescription(String prescriptionId, DoctorDTO doctorModel,String name);

    /**
     * 根据管理处方id，删除管理处方
     * @author 李左河
     * @date 2018/8/27 15:00
     * @param prescriptionDTO
     * @return void
     *
     */
    void deletePrescriptionById(PrescriptionDTO prescriptionDTO);

    /**
     * 统计处方数量
     * @param countPrescriptionDTO
     * @return
     */
    long countPrescription(CountPrescriptionDTO countPrescriptionDTO);

    /**
     * 统计每月管理处方
     * @param countMonthPrescriptionDTO
     * @return
     */
    List<CountMonthPrescriptionPO> countMonthPrescription(CountMonthPrescriptionDTO countMonthPrescriptionDTO);

    /**
     * 加载管理处方列表（患者视角）
     * @param pr
     * @param listMemberPrescriptionDTO
     * @return
     */
    PageResult<MemberPrescriptionPO> listMemberPrescription(PageRequest pr, ListMemberPrescriptionDTO listMemberPrescriptionDTO);

    /**
     * 加载管理处方知识
     * @param prescriptionId
     * @return
     */
    List<PrescriptionKnowledgePO> listPrescriptionKnowledge(String prescriptionId);

    /**
     * 获取管理处方知识学习详情
     * @param eohKnowledgeId
     * @return
     */
    ArticleVO loadArticleInfo(String eohKnowledgeId ,String memberId);

    /**
     * 获取患者的管理处方数量
     * @param memberId
     * @return
     */
    Long countPrescriptionForMember(String memberId);

    /**
     * 统计处方新增数量
     * @param synthesizeDataDTO
     * @return
     */
    long countNewPrescription(SynthesizeDataDTO synthesizeDataDTO);

    /**
     * 获取首次制定的处方
     * @param synthesizeDataDTO
     * @return
     */
    PrescriptionPO getEarlyMemberPrescription(SynthesizeDataDTO synthesizeDataDTO);
    /**
     * 获取最新的处方详情
     * @param memberId
     * @return
     */
    PrescriptionDetailPO getNewPrescriptionDetail(MemberDataDTO memberDataDTO);

    /**
     * 获取住院患者的管理处方数据源/门诊&居家患者的管理处方完成数据源（医院/门诊&居家/住院/时间段）
     * @param dataAnalysisDTO
     * @return
     */
    List<PrescriptionPO> listPrescriptionOfStatistics(GetStatisticsDTO dto);

    /**
     * 获取最新妊娠管理处方患者档案详情
     * @param memberId
     * @param hospitalId
     * @return
     */
    PrescriptionDetailPO getNewPrescriptionDetailByType(GetPrescriptionDetailDTO dto);

    /**
     * 统计每月管理处方-医院
     * @param countMonthPrescriptionDTO
     * @return
     */
    List<CountMonthPrescriptionPO> countMonthPrescription(GetStatisticsDTO dto);

    /**
     * 统计处方数量-医院
     * @param countPrescriptionDTO
     * @return
     */
    long countPrescription(GetStatisticsDTO dto);

    /**
     * 添加患者数据（BMI,血压，血糖，血脂）
     * @param type 1:bmi 2:血压 3:血糖 4:血脂
     * @param dataJson 数据JSON
     * @return
     */
    void addMemberDataOfPrescription(String type, String dataJson , String doctorId);

    /**
     * 获取患者最新的体征记录
     * @param memberId
     * @return
     */
    MemberLatestSignVO getMemberLatestSign(String memberId);


    /*
     *   获取食谱模板列表
     *  @param dto
     * @return
     * */
    PageResult<NutritionCateringPO> listRecipetemplates(String doctorId,String mealsModel,String eohType,int rows,int page,String nutritionCateringId);

    /*
     *   增加自定义食谱
     *   @param dto
     *  @return
     * */
    String insertCustomRecipes(String nutritionCateringJson,Integer foodVersion,String doctorId,Integer eohType);

    /*
     *  修改自定义食谱
     * */
    String modifyCustomRecipes(String nutritionCateringJson,String recipesCaloricId,String nutritionCateringId,String mealsModel,String eohType);

    /*
     *  删除自定义食谱
     * */
    void delCustomRecipes(String nutritionCateringId);

    /**
     * 获取营养素
     * @param id
     */
    List<FoodNutrientBO> listFoodNutrient(String id,String name);

    /**
     * 获取食谱
     * @param
     */
    Map<String,Object> getFoodcookbook(String labourIntensity, String bodyType, String mealsModel, Double height,String prescriptionId,String sex,String isjejunitas,Integer type);
}
