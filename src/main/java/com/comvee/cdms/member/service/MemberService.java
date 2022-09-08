package com.comvee.cdms.member.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.complication.model.bo.PatientSyncBO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.member.bo.MemberCheckinBO;
import com.comvee.cdms.member.bo.MemberControlTargetBO;
import com.comvee.cdms.member.bo.MemberDataBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.po.*;
import com.comvee.cdms.member.vo.*;
import com.comvee.cdms.packages.dto.ListValidMemberPackageDTO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/9/30
 */
public interface MemberService {

    /**
     * 添加患者
     * @param addMemberDTO
     * @return
     */
    String addMember(AddMemberDTO addMemberDTO, DoctorSessionBO doctorSessionBO);

    /**
     * 修改患者
     * @param updateMemberDTO
     */
    void updateMember(UpdateMemberDTO updateMemberDTO);

    /**
     * 获取患者档案
     * @param memberId
     * @return
     */
    Map<String,Object> getMemberArchivesByMemberId(String memberId,String doctorId,String hospitalId,Boolean hide);

    /**
     * 获取患者档案
     * @param memberId
     * @return
     */
    MemberArchivesPO getMemberArchives(String memberId, String doctorId);

    /**
     * 修改档案
     * @param memberArchivesDTO
     */
    void updateMemberArchive(MemberArchivesDTO memberArchivesDTO);

    /**
     * 加载患者列表
     * @param listMemberDTO
     * @param pr
     * @return
     */
    PageResult<MemberListPO> listMember(ListMemberDTO listMemberDTO, PageRequest pr);

    /**
     * 加载患者列表
     * @param memberParamsDTO
     * @return
     */
    List<MemberPO> listMemberByWhere(MemberParamsDTO memberParamsDTO);


    /**
     * 获取患者
     * @param getMemberDTO
     * @return
     */
    MemberPO getMember(GetMemberDTO getMemberDTO);


    List<MemberPO> getMemberByInfo(GetMemberDTO getMemberDTO);

    /**
     * 根据就诊号获取患者
     * @param memberVisitCardDTO
     * @return
     */
    MemberPO getMemberByVisitNo(MemberVisitCardDTO memberVisitCardDTO);

    /**
     * 根据身份证或者社保卡号获取 患者
     * @param getMemberDTO
     * @return
     */
    MemberPO getMemberByIdOrSocialCard(GetMemberDTO getMemberDTO);

    /**
     * 添加医患关系
     * @param addMemberDoctorRelateDTO
     */
    boolean addMemberDoctorRelate(AddMemberDoctorRelateDTO addMemberDoctorRelateDTO);
    /**
     * 添加医患关系
     * @param addMemberDoctorRelateDTO
     */
    boolean checkedMemberDoctorRelate(AddMemberDoctorRelateDTO addMemberDoctorRelateDTO);
    /**
     * 修改患者分组
     * @param updateMemberGroupDTO
     */
    void updateMemberGroup(UpdateMemberGroupDTO updateMemberGroupDTO);

    /**
     * 修改医患关系
     * @param updateDoctorMemberDTO
     */
    void updateMemberDoctor(UpdateDoctorMemberDTO updateDoctorMemberDTO);

    /**
     * 获取用户设置目标
     * @param memberId
     * @return
     */
    RangeBO getMemberRange(String memberId);

    /**
     * 添加控制目标
     * @param rangeBO
     */
    void addMemberRange(RangeBO rangeBO);


    /**
     * 修改控制目标
     * @param rangeBO
     */
    void modifyMemberRange(RangeBO rangeBO);

    /**
     * 新增控制目标默认值
     * @param memberId
     */
    RangeBO insertMemberRangeWithLock(String memberId);

    /**
     * 批量获取患者信息
     * @param idList
     * @return
     */
    List<MemberPO> listMemberByIdList(List<String> idList);

    /**
     * 修改关注状态
     * @param updateDoctorMemberConcernDTO
     */
    void updateMemberDoctorConcern(UpdateDoctorMemberConcernDTO updateDoctorMemberConcernDTO);

    /**
     * 计算医患关系数量
     * @param countDoctorMemberDTO
     * @return
     */
    long countMemberDoctor(CountDoctorMemberDTO countDoctorMemberDTO);

    /**
     * 计算分组患者数量
     * @param listMemberDTO
     * @return
     */
    long countGroupMember(ListMemberDTO listMemberDTO);

    /**
     * 添加患者绑定医患关系
     * @param addMemberWebDTO
     * @return
     */
    String addMemberWeb(AddMemberWebDTO addMemberWebDTO);

    /**
     * 获取患者就诊卡号
     * @param memberVisitCardDTO
     * @return
     */
    MemberVisitCardPO getMemberVisitCard(MemberVisitCardDTO memberVisitCardDTO);

    /**
     * 获取患者最后一次用药记录
     * @param getMemberDrugItemDTO
     * @return
     */
    MemberDrugRecordPO getMemberDrugRecord(GetMemberDrugItemDTO getMemberDrugItemDTO);

    /**
     * 添加患者用药记录
     * @param memberDrugRecordPO
     * @return
     */
    void addMemberDrugRecord(MemberDrugRecordPO memberDrugRecordPO);

    /**
     * 获取患者用药项目列表
     * @param getMemberDrugItemDTO
     * @return
     */
    List<MemberDrugItemPO> getMemberDrugItemList(GetMemberDrugItemDTO getMemberDrugItemDTO);


    /**
     * 添加患者用药项
     * @param memberDrugItemPO
     * @return
     */
    void addMemberDrugItem(MemberDrugItemPO memberDrugItemPO);

    /**
     * 更新患者用药项
     * @param memberDrugItemPO
     * @return
     */
    void updateMemberDrugItem(MemberDrugItemPO memberDrugItemPO);

    void doDrugItem(String teamId,String doctorId,String memberId ,String followId, String drugListJson,String origin);

    /**
     * 统计每月患者
     * @param countMonthMemberDTO
     * @return
     */
    List<CountMonthMemberPO> countMonthMember(CountMonthMemberDTO countMonthMemberDTO);

    /**
     * 新增就诊可好
     * @param addMemberVisitCardDTO
     * @return
     */
    String addMemberVisitCard(AddMemberVisitCardDTO addMemberVisitCardDTO);

    /**
     * 从缓存获取用户信息
     * @param memberId
     * @return
     */
    MemberPO getMemberByIdCache(String memberId);

    /**
     * 加载患者个人中心
     * @param memberId
     * @return
     */
    MemberCenterVO getMemberCenter(String memberId);

    /**
     * 加载医患关系列表
     * @param listDoctorMemberDTO
     * @return
     */
    List<DoctorMemberPO> listDoctorMember(ListDoctorMemberDTO listDoctorMemberDTO);

    /**
     * 获取医患关系
     * @param listDoctorMemberDTO
     * @return
     */
    DoctorMemberPO getDoctorMember(ListDoctorMemberDTO listDoctorMemberDTO);

    /**
     * 获取患者默认控制目标
     * @param memberId
     * @return
     */
    MemberControlTargetBO getMemberDefaultControlTarget(String memberId);

    /**
     * 根据患者id获取患者
     * @param memberId
     * @return
     */
    MemberPO getMemberById(String memberId);

    /**
     * 修改付费状态 - 根据套餐开始时间
     */
    void updatePayStatusByPackageStartDate();

    /**
     * 获取身份证获取患者
     * @param getMemberDTO
     * @return
     */
    MemberPO getMemberByIdCard(String idCard);

    /**
     * 根据医生id获取该医生对应的患者
     * @param doctorId
     * @return
     */
    PageResult<MemberPO> listMemberByDoctorId(ListMemberByDoctorDTO listMemberByDoctorDTO,PageRequest pr);

    /**
     * 解除医患关系
     * @param memberId
     * @param doctorId
     */
    void cancelRelation(String memberId,String doctorId);


    /**
     * 展示患者数据信息
     * @param memberDataDTO
     * @param page
     * @return
     */
    List<MemberDataBO> listMemberData(MemberDataDTO memberDataDTO);

    /**
     * 根据医生信息获取患者
     * @param getMemberByDoctorDTO
     * @return
     */
    List<String> getMemberByDoctor(GetMemberByDoctorDTO getMemberByDoctorDTO);

    /**
     * 根据时间统计患者总数量
     * @param synthesizeDataDTO
     * @return
     */
    long countNewMember(SynthesizeDataDTO synthesizeDataDTO);


    /**
     * 患者概要-风险情况
     * @param memberId
     * @param doctorId
     * @return
     */
    List<Map<String,Object>> getRisk(String memberId, String doctorId,String hospitalId,PageRequest page);

    /**
     * 患者概要-检测数据
     * @param memberId
     * @param doctorId
     * @return
     */
    List<Map<String,Object>> getDetectionDate(String memberId, String doctorId,String hospitalId);

    /**
     * 添加医生备注信息
     * @param doctorMemberRemarkPO
     */
    void addDoctorMemberRemark(DoctorMemberRemarkPO doctorMemberRemarkPO);

    /**
     * 删除医生备注信息
     * @param remarkId
     */
    void delDoctorMemberRemark(String remarkId);

    /**
     * 获取医生对应的患者的备注信息
     * @param memberId
     * @param doctorId
     * @return
     */
    List<DoctorMemberRemarkPO> listDoctorMemberRemark(String memberId,String doctorId,String hospitalId);

    /**
     * 修改患者就诊卡号
     */
    void updateMemberVisitNo(String hospitalId, String memberId ,String visitNo);

    /**
     *  删除患者 - 并发症筛查
     * @param memberId
     * @param doctorId
     */
    void deleteMemberForScreening(String memberId ,String doctorId);

    /**
     * 根据身份证搜索患者
     * @param idCard
     * @param doctorId
     * @return
     */
    MemberSearchResultVO searchMemberByIdCard(String idCard ,DoctorSessionBO doctorSessionBO);

    /**
     * 筛查档案处理
     * @param patientSyncBO
     * @param memberId
     * @param doctorId
     */
    void memberScreeningArchiveHandler(PatientSyncBO patientSyncBO , String memberId , String doctorId);

    /**
     * 根据身份证获取筛查的患者信息
     * @param idCard
     * @return
     */
    PatientSyncBO getScreeningPatientByIdCard(String idCard ,DoctorSessionBO doctorSessionBO);

    /**
     * 根据id获取筛查的患者信息
     * @param memberId
     * @return
     */
    PatientSyncBO getScreeningPatientByMemberId(String memberId ,DoctorSessionBO doctorSessionBO);

    /**
     * 分组患者数量 version 4.0.0
     * @param listMemberDTO
     * @return
     */
    Long countGroupMemberForV4(ListMemberDTO listMemberDTO);

    /**
     * 分组患者数量 5.1.1
     * @param listMemberDTO
     * @return
     */
    Long countGroupMemberForHospital(ListMemberDTO listMemberDTO);

    /**
     * 科室在院患者数量
     * @param listMemberDTO
     * @return
     */
    Long countDepartMember(ListMemberDTO listMemberDTO);

    /**
     * 分页获取患者数据列表
     * @param pagerMemberDTO
     * @param pr
     * @return
     */
    PageResult<MemberListPO> listMemberForPager(PagerMemberDTO pagerMemberDTO, PageRequest pr);

    /**
     * 根据条件统计患者数量
     * @param dto
     * @return
     */
    long countMemberWhere(GetStatisticsDTO dto);

    /**
     * 根据条件统计制定处方患者数量
     * @param dataAnalysisDTO
     * @return
     */
    long getPrescriptionOfMemberCount(GetStatisticsDTO dataAnalysisDTO);

    /**
     * 统计每月患者-医院
     * @param countMonthMemberDTO
     * @return
     */
    List<CountMonthMemberPO> countMonthMember(GetStatisticsDTO getStatisticsDTO);

    /**
     * 计算医患关系数量-医院
     * @param dto
     * @return
     */
    long countMemberDoctor(GetStatisticsDTO dto);

    /**
     * 患者人次
     * @param dto
     * @return
     */
    long getMemberNumber(GetStatisticsDTO dto);

    /**
     * 获取关怀消息列表-根据医生编号串
     * @param listMemberWarmDTO
     * @return
     */
    PageResult<MemberWarmPO> listMemberWarmByParams(ListMemberWarmDTO listMemberWarmDTO,PageRequest pr);

    /**
     * 删除温馨关怀
     * @param sid
     */
    void deleteMemberWarmById(String sid);

    /**
     * 修改温馨关怀
     * @param memberDTO
     */
    void updateMemberWarm(MemberWarmDTO memberDTO);

    /**
     * 保存温馨关怀信息
     * @param memberDTO
     * @return
     */
    Map<String,Object> saveMemberWarm(MemberWarmDTO memberDTO,String hospitalId);

    /**
     * 年龄分布
     * @param dto
     * @return
     */
    List<Map<String,Object>> getMemberAgeRang(GetStatisticsDTO dto);

    /**
     * 获取未下发温馨关怀列表
     * @return
     */
    List<MemberWarmPO> listMemberWarmByUnSendStatus();

    /**
     * 添加筛查患者
     * @param patientSyncBO
     * @param resultStatus
     * @param doctorSessionBO
     * @return
     */
    String addScreeningMember(PatientSyncBO patientSyncBO ,Integer resultStatus ,DoctorSessionBO doctorSessionBO);

    /**
     * 历史用药计划数据
     * @return
     */
    List<MemberHistoricMedicationPlanDTO> listMemberHistoricMedicationPlan();

    /**
     * 加载需要生成控糖报告的患者列表
     * @param page
     * @param rows
     * @return
     */
    PageResult<MemberPO> listNeedCreateSugarMonthReportMember(int page ,int rows);

    /**
     * 修改用药计划
     * @param id
     * @param drugListJson
     */
    void modifyDrugItem(String id,String remark, String drugListJson);

    /**
     * 获取县级以下在院患者
     * @return
     */
    PageResult<MemberCheckinBO> listInHospitalMemberOfLevelLow(int page, int rows);

    /**
     * 判断是否存在医患关系
     * @param memberId
     * @param doctorId
     * @return
     */
    boolean checkDoctorMemberRelationExists(String memberId ,String doctorId);

    /**
     * 分页获取患者检查（并发症和其他检查）
     * @param memberId
     * @param pager
     * @return
     */
    PageResult<MemberInspectVO> pagerMemberInspect(String memberId, PageRequest pager);
    //包含动态血压pad
    List<MemberInspectVO> pagerMemberInspect2(String memberId);

    /**
     * 判断用药类型
     * v 4.0.3 南京
     * @param doctorId
     * @return
     */
    int getDrugType(String doctorId);

    /**
     * 获取所有患者的近一年的档案信息
     * @return
     */
    PageResult<Map<String,Object>> listAllMemberNearlyYearArchives(Integer page,Integer rows);

    /**
     * 获取患者的近一年的档案信息
     * @return
     */
    List<Map<String,Object>> listMemberNearlyYearArchivesByHid(String memberId, String hospitalId);

    /**
     * 搜索患者
     * @param doctorId
     * @param keyword
     * @param pr
     * @return
     */
    PageResult<MemberListPO> listSearchMember(String doctorId ,String keyword ,PageRequest pr);

    /**
     * 根据套餐分组加载患者列表
     * @param pr
     * @param packageId
     * @param doctorId
     * @return
     */
    PageResult<MemberPO> listMemberByPackageGroup(PageRequest pr ,String packageId ,String doctorId) ;

    /**
     * 统计没有购买套餐的患者数量
     * @param doctorList
     * @return
     */
    long countNoPayMember(List<String> doctorList);

    /**
     * 根据第三方唯一标识和第三方用户的标识获取关联用户信息
     * @param principalId
     * @param thirdId
     * @return
     */
    MemberPO getMemberByPatientId(String principalId, String thirdId);
    /**
     * 根据第三方唯一标识和第三方用户的就诊卡号获取关联用户信息
     * @param principalId
     * @param thirdId
     * @return
     */
    MemberPO getMemberByVisitId(String principalId, String thirdId);

    /**
     *
     * @param dto
     */
    boolean verificationUserPackage(ListValidMemberPackageDTO dto);

    /**
     * 加载患者档案列表
     * @return
     */
    PageResult<MemberArchivesPO> listAllMemberArchives(int pageNum,int pageSize);

    /** v 5.0.0
     * 获取当前医生团队下和患者有医关系的医生列表
     * @param memberId
     * @return
     */
    List<DoctorMemberPO> listDoctorMemberByDoctorId(String memberId,String doctorId);

    @Transactional(rollbackFor = Exception.class)
    String h5AddInHospitalMemberOrUpdate(AddMemberDTO addMemberDTO,  DoctorSessionBO doctorModel, AddInHospitalMemberDTO addInHospitalMemberDTO);

    /** v4.2.1
     * 获取用户设置目标(随访智能推荐中用)
     * @param rangeDTO
     * @return
     */
    RangeBO getMemberRangeForFollow(MemberRangeDTO rangeDTO);

    /*************************************************邪恶的分割线******************************************************
     * @version v6.0.0
     * @author: linyd
     * @date: 2020/03/03
     *****************************************************************************************************************/
    /**
     * 住院患者列表&卡片
     * @param listMemberDTO
     * @param pager
     * @return
     */
    PageResult<InHospitalMemberVO> pagerInHospitalMember(ListMemberDTO listMemberDTO, PageRequest pager);

    /**
     * 患者出入院人数
     * @param dto
     * @return
     */
    Long getOutOrInHospitalMemberNum(GetStatisticsDTO dto);

    /** v5.1.6
     * 统计付费患者人数
     * @param dto
     * @return
     */
    long countPayMember(GetStatisticsDTO dto);

    /**
     * v6.0.0
     * 医生可管理的患者编号列表
     * @param doctorSessionBO
     * @return
     */
    List<String> listDoctorMonitorMemberId(DoctorSessionBO doctorSessionBO);

    /**
     * 住院患者列表
     * @param listMemberDTO
     * @return
     */
    List<MemberListPO> listInHospitalMember(ListMemberDTO listMemberDTO);

    void delMemberDrugRecord(String id);

    void delMemberDrugItem(String id);

    /**
     * 搜索患者
     * @author nzq
     * @date 2020-07-28
     * @return ResultModel
     */
    List<Map<String, Object>> listMemberBySearch(String departName, String bedNo, String memberName, String doctorId);

    /**
     * 根据主键获取住院信息
     * @param id
     * @return
     */
    MemberCheckinInfoPO getMemberCheckinInfoById(String id,String hospitalId);

    /**
     * 获取医患备注
     * @param memberId
     * @param doctorId
     * @return
     */
    DoctorMemberRemarkPO getDoctorMemberRemark(String memberId ,String doctorId);

    /**
     * 设置患者分层分级情况
     * @param list
     * @param hospitalId
     */
    void setMemberDifferentLevels(List<MemberListPO> list, String hospitalId);

    /**
     * 填充住院列表需要的数据
     * @param list
     * @return
     */
    List<InHospitalMemberVO> castInHospitalMemberVO(List<MemberListPO> list);



    /**
     * 填充住院列表需要的数据
     * @param list
     * @return
     */
    Result initMemberArchesONfood(String name, String password, String memberId, String hospitalId);

    /**
     * 诊断病类型
     * @param po
     * @return
     */
     String getDiseaseTypeOfMemberInfo(MemberListPO po);

    /**
     * V8.1.0诊断病类型
     * @param po
     * @return
     */
     String getDiseaseTypeOfMemberInfoCH(MemberListPO po);

    //判断该患者是否被医生管理
    MemberPO memberDoctorIsManage(MemberPO memberPO, DoctorSessionBO doctorSessionBO);

    String h5AddMemberOrUpdate(AddMemberDTO addMemberDTO,String groupId,String memberId,DoctorSessionBO doctorModel);

    /**
     * 判断患者类型 1门诊/2住院 0其他
     * @param memberId
     * @param hospitalId
     * @return
     */
    Integer judgeMemberType(String memberId,String hospitalId);



    String memberJoinHospitalHandler(DoctorSessionBO doctorSessionBO, String memberId);

    void memberInHospitalJoinManage(DoctorSessionBO doctorSessionBO,AddMemberMapperDTO addMemberMapperDTO);

    /**
     * 患者嵌入页数据展示
     * @param memberId
     * @return
     */
    Map<String,Object> loadMemberHisEmbedded(String memberId,String hospitalId);

    //根据 身份证号、就诊号、住院号获取患者
    MemberPO getMemberByCertificateNo(CertificateGetMemberDTO getMemberDTO);

}
