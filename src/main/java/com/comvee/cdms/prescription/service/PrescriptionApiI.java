package com.comvee.cdms.prescription.service;

import com.comvee.cdms.member.constant.ControlTargetConstant;
import com.comvee.cdms.prescription.bo.*;
import com.comvee.cdms.prescription.dto.DoctorDTO;
import com.comvee.cdms.prescription.dto.GetMemberPerDTO;
import com.comvee.cdms.prescription.dto.GetTargetRecommendDTO;
import com.comvee.cdms.prescription.dto.PrescriptionEduDTO;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.vo.MemberLatestSignVO;
import com.comvee.cdms.prescription.vo.PrescriptionVO;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;

import java.util.List;

/**
 * @author 李左河
 * @date 2018/8/3 16:21.
 */
public interface PrescriptionApiI {

    /**
     * 获取患者血糖控制目标
     * @author 李左河
     * @date 2018/8/3 16:25
     * @param memberId
     * @return com.comvee.cdms.member.model.RangeBO
     *
     */
    ApiRangeBO getRangeBO(String memberId);

    /**
     * 新增控制目标默认值
     * @author 李左河
     * @date 2018/8/3 16:26
     * @param memberId
     * @return com.comvee.cdms.member.model.RangeBO
     *
     */
    ApiRangeBO insertMemberRangeWithLock(String memberId);

    /**
     * 根据患者编号获取患者
     * @author 李左河
     * @date 2018/8/3 16:27
     * @param memberId
     * @return com.comvee.cdms.member.po.MemberPO
     *
     */
    ApiMemberBO getMemberByMemberId(String memberId);




    /**
     * 获取患者信息列表
     * @author 李左河
     * @date 2018/8/3 16:37
     * @param getMemberPerDTO
     * @return java.util.List<com.comvee.cdms.member.po.MemberPO>
     *
     */
    List<ApiMemberBO> listMember(GetMemberPerDTO getMemberPerDTO);

    /**
     * 获取患者档案
     * @author 李左河
     * @date 2018/8/3 16:31
     * @param memberId
     * @return com.comvee.cdms.member.model.MemberArchivesModel
     *
     */
    ApiMemberArchivesBO getMemberArchivesById(String memberId,String doctorId);

    /**
     * 获取知识点层级分类同一层级数据
     * @author 李左河
     * @date 2018/8/13 16:00
     * @param pid
     * @return java.util.List<com.comvee.cdms.knowledge.model.KnowledgeClassifyModel>
     *
     */
    List<ApiKnowledgeClassifyBO> loadKnowledgeClassify(Long pid);

    /**
     * 获取知识点分页信息
     * @author 李左河
     * @date 2018/8/13 16:05
     * @param page
     * @param rows
     * @param bo
     * @return java.util.List<com.comvee.cdms.knowledge.model.KnowledgeModel>
     *
     */
    List<ApiKnowledgeBO> loadKnowledge(int page, int rows, ApiKnowledgeBO bo);

    /**
     * 根据文章id获取文章知识点关联表表信息
     * @author 李左河
     * @date 2018/8/13 16:12
     * @param id
     * @return java.util.List<com.comvee.cdms.knowledge.model.ArticleModel>
     *
     */
    List<ApiArticleBO> loadByKnowledgeId(Long id ,String memberId);

    /**
     * 获取文章的知识点列表
     * @author 李左河
     * @date 2018/8/13 16:31
     * @param articleIds
     * @return java.util.List<com.comvee.cdms.knowledge.model.ArticleKnowledgeModel>
     *
     */
    List<ApiArticleKnowledgeBO> findArticleKnowledgeByIds(List<Long> articleIds);

    /**
     * 获取文章表分页信息
     * @author 李左河
     * @date 2018/8/13 16:54
     * @param prescriptionEduDTO
     * @param pager
     * @return java.util.List<com.comvee.cdms.knowledge.model.ArticleModel>
     *
     */
    List<ApiArticleBO> loadArticle(PrescriptionEduDTO prescriptionEduDTO, PagerBO pager);

    /**
     * 获取患者管理处方检验项目值(近一年最新的)
     * @author 李左河
     * @date 2018/8/3 16:29
     * @param memberId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    CheckoutResultBO getMemberCheckoutResultOfEoh(String memberId);



    /**
     * 根据首席的对应的业务数据权限列表获取可查看的医生编号列表
     * @author 李左河
     * @date 2018/8/3 16:41
     * @param hospitalId
     * @param doctorId
     * @param glcf
     * @return java.util.List<java.lang.String>
     *
     */
    //List<String> listDoctorIdOfHospitalLeader(String hospitalId, String doctorId, String glcf);

    /**
     * 智能推荐 知识学习
     * @param archives
     * @param memberId
     * @return
     */
    KnowledgePlanBO createKnowledgePlan(String archives,String memberId ,Integer eohType);

    /**
     * 同步患者信息
     * @author 李左河
     * @date 2018/8/15 10:52
     * @param memberInfoBO
     * @return void
     *
     */
    void synchronizeMember(MemberInfoBO memberInfoBO);

    /**
     * 同步控制目标
     * @author 李左河
     * @date 2018/8/15 13:34
     * @param prescriptionDetailPO
     * @param doctorModel
     * @return void
     *
     */
    void synchronizeTarget(PrescriptionDetailPO prescriptionDetailPO, DoctorDTO doctorModel);

    /**
     * 同步知识学习
     * @author 李左河
     * @date 2018/8/15 14:04
     * @param prescriptionDetailPO
     * @return void
     *
     */
    void synchronizeEdu(PrescriptionDetailPO prescriptionDetailPO);

    /**
     * 下发管理处方卡片
     * @author 李左河
     * @date 2018/8/15 14:57
     * @param prescriptionDialogueBO
     * @param prescriptionHandDownBO
     * @return void
     *
     */
    void sendPrescriptionDialogue(PrescriptionDialogueBO prescriptionDialogueBO, PrescriptionHandDownBO prescriptionHandDownBO);

    /**
     * 管理处方，添加微信公众号消息模版
     * @author 李左河
     * @date 2018/8/15 15:03
     * @param prescriptionTemplateBO
     * @return void
     *
     */
    void addPrescriptionTemplate(PrescriptionTemplateBO prescriptionTemplateBO);
    
    /**
     * 同步管理处方 监测方案
     * @author wangxy
     * @date 2018/8/22 15:03
     * @return void
     *
     */
    void synchronizeMonitor(PrescriptionDetailPO prescriptionDetailPO,DoctorDTO doctorModel);
    
    /**
     * 同步管理处方 运动
     * @author wangxy
     * @date 2018/8/22 15:03
     * @return void
     *
     */
    void synchronizeSport(PrescriptionDetailPO prescriptionDetailPO,DoctorDTO doctorModel);

    /**
     * 同步管理处方 饮食
     * @author wangxy
     * @date 2018/8/22 15:03
     * @return void
     *
     */
    void synchronizeDiet(PrescriptionDetailPO prescriptionDetailPO,DoctorDTO doctorModel);

    /**
     * 根据患者id，获取每日血糖监测的最新记录
     * @author 李左河
     * @date 2018/8/3 16:28
     * @param memberId
     * @return com.comvee.cdms.bloodsugar.po.MemberEverydaySugarPO
     *
     */
    ApiMemberEverydaySugarBO getMemberEverydaySugarNewByMemberId(String memberId);

    /**
     * 获取本月内低血糖次数
     * @author 李左河
     * @date 2018/8/3 16:32
     * @param memberBloodSugarBO
     * @return java.lang.Long
     *
     */
    Long getNumberLowBloodMonthByMemberId(MemberBloodSugarBO memberBloodSugarBO);

    /**
     * 根据记录血糖时间点，获取近三个月最新的血糖记录
     * @author 李左河
     * @date 2018/8/29 9:39
     * @param memberParamBO
     * @return java.lang.String
     *
     */
    String getBloodSugarThreeMonthNew(MemberParamBO memberParamBO);

    /**
     * 通过memberId，获取患者血压的最新记录
     * @author 李左河
     * @date 2018/8/3 16:28
     * @param memberId
     * @return com.comvee.cdms.bloodsugar.po.BloodPressureBO
     *
     */
    ApiBloodPressureBO getBloodPressureNewByMemberId(String memberId);



    /**
     * 点击埋点
     * @param incrMemberArticleClicksDTO
     */
    void incrMemberArticleClick(String memberId);

    /**
     * 根据文章id获取文章正文信息
     * @param articleId
     * @return
     */
    ApiArticleTxtBO loadArticleTxtByArticleId(Long articleId);

    /**
     * 根据医生id获取医生信息
     * @param doctorId
     * @return
     */
    ApiDoctorBO getDoctorById(String doctorId);

    /**
     * 根据医生id获取医生以及助理医生的id
     * @param doctorId
     * @param type 如果type 不为null 在医生id集合中添加"-1"值  用来获取系统制定的一些内容
     * @return
     */
    List<String> getDoctorIdList(String doctorId,Integer type);

    /**
     * 服务扣次
     * @param prescriptionId
     */
    void useService(String prescriptionId);

    /**
     * 添加管理处方微信消息
     * @param prescriptionVO
     */
    void addPrescriptionWechatMessage(PrescriptionVO prescriptionVO);


    /**
     * 添加课程
     * @param sid
     * @param pid  患者id
     * @param origin  课程添加来源
     * @param isPlan 是否是学习计划 0:不是 1:是
     * @param planId  计划id
     */
    void addMemberCourse(String sid, String pid,String origin,String isPlan,Long planId);

    /**
     * 获取患者一段时间内的血糖记录
     * @param listBloodSugarDTO
     * @return
     */
    List<ApiBloodSugarBO> listSugar(ListBloodSugarDTO listBloodSugarDTO);

    /**
     * 根据条件获取患者列表
     * @param getMemberPerDTO
     * @return
     */
    List<ApiMemberBO> listMemberByWhere(GetMemberPerDTO getMemberPerDTO);

    /**
     * 添加BMI
     * @param dataJson
     */
    void addBmi(String dataJson);

    /**
     * 添加血压
     * @param dataJson
     */
    void addBloodPressure(String dataJson);

    /**
     * 添加血糖
     * @param dataJson
     */
    void addBloodSugar(String dataJson);

    /**
     * 添加糖化
     * @param dataJson
     */
    void addHba1c(String dataJson);

    /**
     * 判断是否存在医患关系
     * @param memberId
     * @param doctorId
     * @return
     */
    boolean checkDoctorMemberRelationExists(String memberId ,String doctorId);

    /**
     * 获取患者最新的体征记录
     * @param memberId
     * @return
     */
    MemberLatestSignVO getMemberLatestSign(String memberId);

    /**
     * 添加血脂记录
     * @param dataJson
     */
    void addBloodFat(String dataJson ,String doctorId);

    /**
     * 控制目标推荐
     * @param memberId
     * @param getTargetRecommendDTO
     * @return
     */
    ApiRangeBO rangeRecommend(GetTargetRecommendDTO getTargetRecommendDTO);


    ControlTargetConstant.BaseInfo baseInfoHandler(String memberId, String doctorId);

    /**
     * 统计医患关系
     * @param doctorId
     * @param memberId
     * @return
     */
    Long countDoctorMemberRelation(String doctorId ,String memberId);
}
