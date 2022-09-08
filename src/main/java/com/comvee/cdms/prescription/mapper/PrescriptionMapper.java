package com.comvee.cdms.prescription.mapper;

import com.comvee.cdms.member.dto.MemberDataDTO;
import com.comvee.cdms.prescription.bo.FoodItemBO;
import com.comvee.cdms.prescription.bo.FoodNutrientBO;
import com.comvee.cdms.prescription.dto.*;
import com.comvee.cdms.prescription.po.*;
import com.comvee.cdms.prescription.vo.PrescriptionSugarmonitorVO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PrescriptionMapper {

    /**
     * 添加处方
     * @param po
     */
    void insertPrescription(PrescriptionPO po);

    /**
     * 添加处方模块
     * @param po
     */
    void insertPrescriptionDetail(PrescriptionDetailPO po);

    /**
     * 批量添加处方模块
     * @param pos
     */
    void batchInsertPrescriptionDetail(List<PrescriptionDetailPO> pos);

    /**
     * 添加处方历史轨迹
     * @param po
     */
    void insertPrescriptionLog(PrescriptionLogPO po);

    /**
     * 删除处方
     * @param dto
     */
    void deletePrescription(DelPrescriptionDTO dto);

    /**
     * 删除处方模块
     * @param dto
     */
    void deletePrescriptionDetail(DelPrescriptionDetailDTO dto);

    /**
     * 删除处方历史轨迹
     * @param dto
     */
    void deletePrescriptionLog(DelPrescriptionLogDTO dto);

    /**
     * 修改处方
     * @param po
     */
    void modifyPrescription(PrescriptionPO po);

    /**
     * 修改处方模块
     * @param po
     */
    void modifyPrescriptionDetail(PrescriptionDetailPO po);

    /**
     * 获取处方列表
     * @param dto
     * @return
     */
    List<PrescriptionPO> listPrescriptionByParam(GetPrescriptionDTO dto);

    Long getPrescriptionPeopleNum(GetPrescriptionDTO dto);

    /**
     * 获取处方
     * @param dto
     * @return
     */
    PrescriptionPO getPrescriptionByParam(GetPrescriptionDTO dto);

    /**
     * 通过患者id和插入时间获取处方
     * @param dto
     * @return
     */
    PrescriptionPO getPrescriptionByParamByMemberIdAndInsertDt(GetPrescriptionDTO dto);

    /**
     * 获取处方模块列表
     * @param dto
     * @return
     */
    List<PrescriptionDetailPO> listPrescriptionDetailByParam(GetPrescriptionDetailDTO dto);

    /**
     * 获取处方模块
     * @param dto
     * @return
     */
    PrescriptionDetailPO getPrescriptionDetailByParam(GetPrescriptionDetailDTO dto);

    /**
     * 获取处方制定轨迹列表
     * @param dto
     * @return
     */
    List<PrescriptionLogPO> listPrescriptionLogByParam(GetPrescriptionLogDTO dto);

    /**
     * 获取处方制定轨迹
     * @param dto
     * @return
     */
    PrescriptionLogPO getPrescriptionLogByParam(GetPrescriptionLogDTO dto);
    
    /**
     * 获取处方推荐监测方案
     * @param dto
     * @return
     */
    List<PrescriptionSugarmonitorVO> loadEohSugarmonitorByItem(GetPrescriptionSugarmonitorDTO dto);

    /**
     * 根据管理处方id删除知识学习记录
     * @author 李左河
     * @date 2018/8/7 10:14 
     * @param prescriptionId 
     * @return void
     * 
     */
    void deletePrescriptionKnowledgeByPrescriptId(@Param("prescriptionId") String prescriptionId);

    /**
     * 批量 新增知识学习记录
     * @author 李左河
     * @date 2018/8/7 10:26
     * @param knowledgeList
     * @return void
     *
     */
    void batchInsertPrescriptionKnowledge(List<PrescriptionKnowledgePO> knowledgeList);


    /**
     * 获取推荐食谱列表
     * @param dto
     * @return
     */
    List<NutritionCateringPO> loadNutritionCateringList(GetNutritionCateringDTO dto);

    /**
     * 根据编号获取新版食材
     * @param id
     * @return
     */
    FoodItemBO getEohFoodItemById(String id);
    
    List<FoodItemBO> getEohFoodItemByIds(@Param("ids") List<String> ids);

    /**
     * 根据编号获取旧版食材
     * @param id
     * @return
     */
    FoodItemBO getEohIngredientsItemById(String id);

    /**
     * getEohIngredientsItemListByIds
     * @param ids
     * @return
     * 李左河
     */
    List<FoodItemBO> listEohIngredientsItemByIds(@Param("ids") List<String> ids);

    /**
     * 获取管理处方，下发数量
     * @author 李左河
     * @date 2018/8/9 16:38
     * @param doctorId
     * @return java.lang.Integer
     *
     */
    Integer getHandDownCount(@Param("doctorId") String doctorId);

    /**
     * 根据管理处方id，获取知识文章列表
     * @author 李左河
     * @date 2018/8/10 11:00
     * @param prescriptionId
     * @return java.util.List<com.comvee.cdms.prescription.po.PrescriptionKnowledgePO>
     *
     */
    List<PrescriptionKnowledgePO> listPrescriptionKnowledge(@Param("prescriptionId") String prescriptionId);

    /**
     * 获取食材列表新版
     * @param dto
     * @return
     */
    List<FoodItemBO> listFoodItem(GetFoodItemDTO dto);

    /**
     * 获取食材列表旧版
     * @param dto
     * @return
     */
    List<FoodItemBO> listIngredientsItem(GetFoodItemDTO dto);

    /**
     * 获取食材分类信息新版
     * @return
     */
    List<FoodItemBO> listNewTypeFoodClass();

    /**
     * 获取食材分类信息旧版
     * @return
     */
    List<FoodItemBO> listOldTypeFoodClass();

    /**
     * 根据md5获取食谱
     * @param md5
     * @return
     */
    List<NutritionCateringPO> getNCateringsByMD5(String md5);

    /**
     * 根据md5获取食谱份量关系
     * @param md5
     * @return
     */
    List<NutritionCateringPO> getRecipesCaloricByMD5(@Param("md5") String md5,@Param("eohType") Integer eohType);

    /**
     * 保存食谱份量关系
     * @param jsonToStringMap
     */
    void saveRecipesCaloricRelation(Map<String, String> jsonToStringMap);

    /**
     * 保存食谱
     * @param jsonToStringMap
     */
    void saveNutritionCatering(Map<String, String> jsonToStringMap);

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
     * 加载管理处方列表(患者视角)
     * @param listMemberPrescriptionDTO
     * @return
     */
    List<MemberPrescriptionPO> listMemberPrescription(ListMemberPrescriptionDTO listMemberPrescriptionDTO);

    /**
     * 获取管理处方知识内容
     * @param sid
     * @return
     */
    PrescriptionKnowledgePO loadEohKnowledgeById(@Param("sid") String sid);
    
    //gwx
    /**
     * 统计患者处方数量
     * @param memberId
     * @return
     */
    long countPrescriptionForMember(@Param("memberId") String memberId);

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
    List<PrescriptionPO> getEarlyMemberPrescription(SynthesizeDataDTO synthesizeDataDTO);

    /**根据患者id获取处方详情
     *
     * @param memberId
     * @return
     */
    List<PrescriptionDetailPO> getPrescriptionDetail(MemberDataDTO memberDataDTO);


    /**
     * 修改管理处方知识学习状态
     * @param sid
     * @return
     */
    void updateEohKnowledgeLearn(@Param("sid") String sid);

    /**
     * 获取住院患者的管理处方数据源/门诊&居家患者的管理处方完成数据源（医院/门诊&居家/住院/时间段）
     * @param dataAnalysisDTO
     * @return
     */
    List<PrescriptionPO> listPrescriptionOfStatistics(GetStatisticsDTO dataAnalysisDTO);

    /**
     * 统计每月管理处方-医院
     * @param dto
     * @return
     */
    List<CountMonthPrescriptionPO> countMonthPrescriptionForHos(GetStatisticsDTO dto);

    /**
     * 统计患者处方数量-医院
     * @param dto
     * @return
     */
    long countPrescriptionForHos(GetStatisticsDTO dto);

    /**
     * 获取最新妊娠管理处方患者档案详情
     * @param memberId
     * @param hospitalId
     * @return
     */
    PrescriptionDetailPO getNewPrescriptionDetailByType(GetPrescriptionDetailDTO dto);

    /** 4.2.1
     * 根据首诊id删除知识学习记录
     * @author 李左河
     * @date 2018/8/7 10:14
     * @param prescriptionId
     * @return void
     *
     */
    void deleteFollowKnowledgeByFollowId(@Param("followId") String followId);

    /** 4.2.1
     * 根据首诊id，获取知识文章列表
     *
     */
    List<PrescriptionKnowledgePO> listFollowKnowledge(@Param("followId") String followId);

    /** v4.2.1
     * 获取最新的处方下发课程
     * @param eohType
     * @return
     */
    PrescriptionKnowledgePO listPrescriptionSendKnowledge(@Param("eohType") String eohType,@Param("memberId")String memberId);

    /** v4.2.1
     * 获取最新的处方下发课程
     * @param eohType
     * @return
     */
    PrescriptionKnowledgePO listFollowSendKnowledge(@Param("eohType") String eohType,@Param("memberId")String memberId);

    PrescriptionPO getPrescriptionById(@Param("prescriptionId") String prescriptionId);

    /**  v6.7.0 start
     * 获取自定义食谱列表
     * @param jsonToStringMap
     */
    List<NutritionCateringPO> listCustomRecipetemplates(Map<String, String> jsonToStringMap);

    /**
     * 修改营养配餐表
     * @param jsonToStringMap
     */
    void modifyCustomRecipe(Map<String, String> jsonToStringMap);

    /**
     * 修改营养配餐 模型表
     * @param jsonToStringMap
     */
    void modifyCustomRecipeCatering(Map<String, String> jsonToStringMap);

    /**
     * 删除自定义模板
     * @param id
     */
    void delCustomRecipes(@Param("id")String id);

    /** v6.7.0 end
     * 获取营养素
     * @param id
     */
    List<FoodNutrientBO> listFoodNutrient(@Param("id")String id,@Param("name")String name);
}
