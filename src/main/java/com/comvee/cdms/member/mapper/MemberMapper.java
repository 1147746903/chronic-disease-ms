package com.comvee.cdms.member.mapper;

import com.comvee.cdms.app.doctorapp.model.app.MemberListAndGroupRespModel;
import com.comvee.cdms.app.doctorapp.model.app.MemberListByGroupIdRespModel;
import com.comvee.cdms.glu.model.ListMemberDto;
import com.comvee.cdms.glu.model.ListMemberVO;
import com.comvee.cdms.glu.model.MemberEverydaySugarModel;
import com.comvee.cdms.dybloodsugar.po.DYMemberSensorPO;
import com.comvee.cdms.insulinpump.model.po.InsulinPumpNurseRecordPO;
import com.comvee.cdms.member.bo.MemberCheckinBO;
import com.comvee.cdms.member.bo.MemberDataBO;
import com.comvee.cdms.member.bo.MemberDrugItemBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.model.MemberArchivesModel;
import com.comvee.cdms.member.po.*;
import com.comvee.cdms.member.vo.MemberInspectVO;
import com.comvee.cdms.member.vo.MemberJoinHospitalVO;
import com.comvee.cdms.statistics.dto.GetMemberStaticsDTO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.HospitalDataDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: suyz
 * @date: 2018/10/8
 */
public interface MemberMapper {

	/**
	 * 获取患者每日血糖列表
	 * @param map
	 * @return
	 */
	List<MemberEverydaySugarModel> getMemberEverydaySugarList(Map<String, Object> map);

    /**
     * 获取患者
     * @param getMemberDTO
     * @return
     */
    MemberPO getMember(GetMemberDTO getMemberDTO);


    List<MemberPO> getMemberByInfo(GetMemberDTO getMemberDTO);

    /**
     * 新增患者
     * @param addMemberMapperDTO
     */
    void addMember(AddMemberMapperDTO addMemberMapperDTO);

    /**
     * 新增或者更新
     * @param addMemberMapperDTO
     */
    void addOrUpdateMember(AddMemberMapperDTO addMemberMapperDTO);

    /**
     * 修改患者
     * @param updateMemberDTO
     */
    void updateMember(UpdateMemberDTO updateMemberDTO);


    /**
     * 加载患者列表
     * @param listMemberDTO
     * @return
     */
    List<MemberListPO> listMember(ListMemberDTO listMemberDTO);

    /**
     * 添加医患关系
     * @param doctorMemberMapperDTO
     */
    void addDoctorMember(DoctorMemberMapperDTO doctorMemberMapperDTO);

    /**
     * 修改医患关系
     * @param
     */
    void updateDoctorMember(UpdateDoctorMemberDTO updateDoctorMemberDTO);
    /**
     * 根据id获取患者档案数据
     * @param memberId
     * @return
     */
    MemberArchivesPO getMemberArchives(@Param("memberId") String memberId, @Param("hospitalId")String hospitalId);

    /**
     * 修改患者档案
     * @param memberArchivesPO
     */
    void updateMemberArchivesById(@Param("memberArchivesPO") MemberArchivesPO memberArchivesPO);

    /**
     * 新增患者档案
     * @param memberArchivesPO
     */
    void addMemberArchives(@Param("memberArchivesPO") MemberArchivesPO memberArchivesPO);

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
     * 批量获取患者信息
     * @param idList
     * @return
     */
    List<MemberPO> listMemberByIdList(List<String> idList);

    /**
     * 计算医患关系数量
     * @param listMemberDTO
     * @return
     */
    long countMemberDoctor(CountDoctorMemberDTO listMemberDTO);

    /**
     * 计算分组患者数量
     * @param listMemberDTO
     * @return
     */
    long countGroupMember(ListMemberDTO listMemberDTO);

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
     * 获取患者用药记录列表
     * @param getMemberDrugItemDTO
     * @return
     */
    List<MemberDrugRecordPO> getMemberDrugRecordList(GetMemberDrugItemDTO getMemberDrugItemDTO);


    /**
     * 添加患者用药记录
     * @param memberDrugRecordPO
     * @return
     */
    void addMemberDrugRecord(MemberDrugRecordPO memberDrugRecordPO);

    /**
     * 获取患者用药项目
     * @param getMemberDrugItemDTO
     * @return
     */
    List<MemberDrugItemPO> getMemberDrugItemList(GetMemberDrugItemDTO getMemberDrugItemDTO);

    /**
     * 获取患者用药
     * @param getMemberDrugItemDTO
     * @return
     */
    MemberDrugItemPO getMemberDrugItem(GetMemberDrugItemDTO getMemberDrugItemDTO);

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

    /**
     * 统计每月患者
     * @param countMonthMemberDTO
     * @return
     */
    List<CountMonthMemberPO> countMonthMember(CountMonthMemberDTO countMonthMemberDTO);

    /**
     * 新增就诊卡
     * @param addMemberVisitCardDTO
     */
    void addMemberVisitCard(AddMemberVisitCardDTO addMemberVisitCardDTO);

    /**
     * 删除用药项
     * @param recordId
     */
    void delMemberDrugItem(String recordId);

    /**
     * 删除用药记录
     * @param id
     */
    void delMemberDrugRecord(String id);

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
    
    //gwx添加201811051605
    /**
     * 患者分组
     * @param doctorId
     * @param lastDate
     * @param groupType
     * @return
     */
    List<MemberListAndGroupRespModel> loadMemberListAndGroup(@Param("doctorList") List<String> doctorIdList , @Param("lastDate")String lastDate,
    		@Param("groupType") String groupType);
    
    /**
     * 每组的患者
     * @param doctorId
     * @param groupId
     * @return
     */
    List<MemberListByGroupIdRespModel> loadMemberListByGroupId(@Param("doctorList") List<String> doctorIdList,@Param("groupId") String groupId,
    		@Param("lastDate")String lastDate,@Param("groupType") String groupType);

    /**
     * 修改付费状态 - 根据套餐开始时间
     */
    void updatePayStatusByPackageStartDate(@Param("payStatus") Integer payStatus, @Param("todayDate") String todayDate);

    /**
     * 添加医生备注信息
     * @param doctorMemberRemarkPO
     */
    void addDoctorMemberRemark(DoctorMemberRemarkPO doctorMemberRemarkPO);

    /**
     * 删除医生备注信息
     * @param remarkId
     */
    void delDoctorMemberRemark(@Param("remarkId") String remarkId);

    /**
     * 获取医生对应的患者的备注信息
     * @param memberId
     * @param doctorId
     * @return
     */
    List<DoctorMemberRemarkPO> listDoctorMemberRemark(@Param("memberId")String memberId,@Param("doctorId")String doctorId,@Param("hospitalId")String hospitalId);

    /**
     * 加载患者的档案列表
     * @param memberId
     * @return
     */
    List<MemberArchivesPO> listMemberArchives(@Param("memberId") String memberId, @Param("hospitalId")String hospitalId);

    /**
     * 根据医生id加载患者列表
     * @param doctorId
     * @return
     */
    List<MemberPO> listMemberByDoctorId(ListMemberByDoctorDTO listMemberByDoctorDTO);

    /**
     * 解除医患关系
     * @param memberId
     * @param doctorId
     */
    void cancelRelation(@Param("memberId") String memberId,@Param("doctorId") String doctorId);

    /**
     * 展示患者信息
     * @param memberDataDTO
     * @return
     */
    List<MemberDataBO> listMemberData(MemberDataDTO memberDataDTO);

    /**
     * 根据时间统计新增患者总数量
     * @param synthesizeDataDTO
     * @return
     */
    long countNewMember(SynthesizeDataDTO synthesizeDataDTO);

    /**
     * 根据医生信息获取患者
     * @param getMemberByDoctorDTO
     * @return
     */
    List<String> getMemberByDoctor(GetMemberByDoctorDTO getMemberByDoctorDTO);


    /**
     * 根据患者id和时间查询患者档案
     * @param synthesizeDataDTO
     * @return
     */
    List<MemberArchivesPO> getMemberArchivesById(SynthesizeDataDTO synthesizeDataDTO);

    /**
     * 修改患者就诊卡号
     */
    void updateMemberVisitNo(MemberVisitCardPO memberVisitCardPO);

    /**
     * 分组患者数量 version4.0.0
     * @param listMemberDTO
     * @return
     */
    long countGroupMemberForV4(ListMemberDTO listMemberDTO);
    /**
     * 分组患者数量 5.1.1
     * @param listMemberDTO
     * @return
     */
    long countGroupMemberForHospital(ListMemberDTO listMemberDTO);
    /**
     * 科室在院患者数量
     * @param listMemberDTO
     * @return
     */
    long countDepartMember(ListMemberDTO listMemberDTO);

    /**
     * 获取院外患者列表
     * @param pagerMemberDTO
     * @return
     */
    List<MemberListPO> listMemberForV4(PagerMemberDTO pagerMemberDTO);
    /** v5.1.1
     * 获取院外患者列表
     * @param pagerMemberDTO
     * @return
     */
    List<MemberListPO> listMemberForHospital(PagerMemberDTO pagerMemberDTO);

    /**
     * 获取院内患者列表
     * @param pagerMemberDTO
     * @return
     */
    List<MemberListPO> listMemberByDepart(PagerMemberDTO pagerMemberDTO);

    /**
     * 根据条件统计患者数量
     * @param dto
     * @return
     */
    long countMemberWhere(GetStatisticsDTO dto);

    /**
     * 制定管理处方患者数量
     * @param dataAnalysisDTO
     * @return
     */
    long getPrescriptionOfMemberCount(GetStatisticsDTO dataAnalysisDTO);

    /**
     * 统计每月患者-医院
     * @param getStatisticsDTO
     * @return
     */
    List<CountMonthMemberPO> countMonthMemberForHos(GetStatisticsDTO getStatisticsDTO);

    /**
     * 计算医患关系数量-医院
     * @param dto
     * @return
     */
    long countMemberDoctorForHos(GetStatisticsDTO dto);

    /**
     * 患者人次
     * @param dto
     * @return
     */
    long getMemberNumber(GetStatisticsDTO dto);

    /**
     * 获取关怀列表
     * @param listMemberWarmDTO
     * @return
     */
    List<MemberWarmPO> listMemberWarmByParams(ListMemberWarmDTO listMemberWarmDTO);

    /**
     * 更新温馨关怀信息
     * @param memberWarmPO
     */
    void updateMemberWarm(MemberWarmPO memberWarmPO);

    /**
     * 保存温馨关怀信息
     * @param memberWarmPO
     */
    void saveMemberWarm(MemberWarmPO memberWarmPO);

    /**
     * 年龄分布
     * @param dto
     * @return
     */
    List<Map<String,Object>> getMemberAgeRang(GetStatisticsDTO dto);

    /**
     * 未下发的关怀
     * @return
     */
    List<MemberWarmPO> listMemberWarmByUnSendStatus();

    /**
     * 历史用药计划数据
     * @return
     */
    List<MemberHistoricMedicationPlanDTO> listMemberHistoricMedicationPlan();

    /**
     * 加载需要生成控糖报告的患者
     * @param reportMonth
     * @return
     */
    List<MemberPO> listNeedCreateSugarMonthReportMember();
    /**
     * 根据条件获取患者列表
     * @param memberParamsDTO
     * @return
     */
    List<MemberPO> listMemberByWhere(MemberParamsDTO memberParamsDTO);

    /**
     * 分页获取医生患者的用药方案列表
     * @param listDrugsMemberDTO
     * @return
     */
    List<MemberDrugItemBO> listDrugsMemberPage(GetMemberDrugItemDTO listDrugsMemberDTO);

    /**
     * 获取县级以下在院患者
     * @return
     */
    List<MemberCheckinBO> listInHospitalMemberOfLevelLow();

    /**
     * 分页获取患者检查（并发症和其他检查）
     * @param memberId
     * @return
     */
    List<MemberInspectVO> pagerMemberInspect(@Param("memberId") String memberId);

    List<MemberInspectVO> pagerMemberInspect2(@Param("memberId") String memberId);

    /**
     * 获取所有患者的近一年的档案信息
     * @return
     */
    List<MemberArchivesPO> listAllMemberNearlyYearArchives();

    /**
     * 根据套餐分组加载患者列表
     * @param packageId
     * @param doctorList
     * @return
     */
    List<MemberPO> listMemberByPackageGroup(@Param("packageId") String packageId ,@Param("doctorList") List<String> doctorList);

    /**
     * 加载当前没有有效套餐的患者列表
     * @param doctorIdList
     * @param payStatusList
     * @return
     */
    List<MemberPO> listNoPackageMember(@Param("doctorIdList") List<String> doctorIdList ,@Param("payStatusList") List<Integer> payStatusList);

    /**
     * 统计没有购买套餐的患者数量
     * @param doctorList
     * @return
     */
    long countNoPayMember(@Param("doctorList") List<String> doctorList ,@Param("payStatus") List<Integer> payStatus);

    /**
     * 加载患者档案列表
     * @return
     */
    List<MemberArchivesPO> listAllMemberArchives();

    /**
     * 获取当前医生团队下和患者有医关系的医生列表
     * @param listDoctorMemberDTO
     * @return
     */
    List<DoctorMemberPO> listDoctorMemberByDoctorId(ListDoctorMemberDTO listDoctorMemberDTO);

    /**
     * 获取院内患者列表2
     * @param listMemberDTO
     * @return
     */
    List<MemberListPO> listMemberByDepartment(ListMemberDTO listMemberDTO);

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
     * 删除医患业务数据
     * @param memberId
     * @param doctorId
     */
    void deleteDoctorMemberBizData(@Param("memberId") String memberId,@Param("doctorId") String doctorId);

    /**
     * 获取该患者在该院的某个团队下
     * @param memberId
     * @param hospitalId
     * @return
     */
    DoctorMemberPO getDoctorMemberByHospital(@Param("memberId")String memberId, @Param("hospitalId")String hospitalId);

    /**
     * 统计住院患者数量
     * @param departmentId
     * @return
     */
    long countInHospitalPatient(@Param("departmentId") String departmentId);

    /**
	 * 搜索患者列表
	 * @param map
	 * @return
	 */
	List<MemberBedDTO> listMemberBySearch(Map<String,Object> map);


    /**
     * 根据主键获取患者住院信息
     * @param id
     * @return
     */
    MemberCheckinInfoPO getMemberCheckinInfoById(@Param("id") String id,@Param("hospitalId") String hospitalId);

    MemberCheckinInfoPO getMemberCheckinInfoByMemberId(@Param("memberId") String memberId);

    /**
     * 获取医患备注
     * @param doctorId
     * @param memberId
     * @return
     */
    DoctorMemberRemarkPO getDoctorMemberRemark(@Param("doctorId") String doctorId ,@Param("memberId") String memberId);

    /**
     * 加载住院病床列表（带患者信息）
     * @param listMemberDTO
     * @return
     */
    List<MemberListPO> listHospitalBedWithMember(ListMemberDTO listMemberDTO);

    List<MemberListPO> listHospitalBedWithSensor(ListMemberDTO listMemberDTO);

    List<MemberListPO> listHospitalOutMember(ListMemberDTO listMemberDTO);

    List<MemberListPO> listHospitalOutWithSensor(ListMemberDTO listMemberDTO);

    List<MemberListPO> listHospitalAllLogMember(ListMemberDTO listMemberDTO);

    DoctorMemberPO getDoctorByMemberId(@Param("memberId") String memberId);

    MemberPO getMemberByMemberId(@Param("memberId") String memberId);

    /**
     * 根据id获取患者档案数据2
     * @param memberId
     * @return
     */
    MemberArchivesModel getMemberArchivesByMemberID(@Param("memberId")String memberId);

    /**
     * 新增患者档案记录
     * @param memberArchivesRecordPO
     */
    void addMemberArchivesRecord(@Param("memberArchivesRecordPO") MemberArchivesRecordPO memberArchivesRecordPO);

    int countMemberTotal();

    List<MemberArchivesPO> listMemberArchivesTwo(@Param("start")int start);

    /**
     * 获取医院所有患者数
     * @param hospitalDataDTO
     * @return
     */
    long countAllMemberByHos(HospitalDataDTO hospitalDataDTO);

    /**
     * 获取医院所有探头设备数
     * @param hospitalDataDTO
     * @return
     */
    long countAllSensorByHos(HospitalDataDTO hospitalDataDTO);
    /**
     * 获取医院第一个绑定探头
     * @param hospitalDataDTO
     * @return
     */
    String getFirstBindSensorNoByHos(HospitalDataDTO hospitalDataDTO);

    /**
     * 查询患者探头
     */
    List<DYMemberSensorPO> getMemberSensorPOList(@Param("memberId") String memberId);
    /**
     * 查询患者胰岛素泵
     */
    List<InsulinPumpNurseRecordPO> getMemberInsulinPumpList(@Param("memberId") String memberId);

    /**
     * 获取医生绑定的患者id
     */
    Set<String> getDoctorMemberIds(@Param("doctorId") String doctorId);


    List<ListMemberVO> listGluMember(ListMemberDto dto);

    /**
     * 获取患者绑定的医生id
     */
    Set<String> getMemberDoctorIds(@Param("memberId") String memberId);

    /**
     * 统计t_member
     * @param memberId
     * @param startDt
     * @param endDt
     * @return
     */
    Long countMemberNumByDTO(GetMemberStaticsDTO getMemberStaticsDTO);

    //通过证件号获取
    MemberPO getMemberByCertificateNo(CertificateGetMemberDTO getMemberDTO);

    //就诊记录数
    long countMemberVisit(ListMemberVisitDTO listMemberVisitDTO);

    //体检人数
    long countMemberInspection(@Param("hospitalIdList") List<String> hospitalIdList, @Param("checkYear") String checkYear);

    List<MemberJoinHospitalVO> listMemberVisit(ListMemberVisitDTO listMemberVisitDTO);

    //体检memberId列表
    Set<String> getInspectionMemberIds(@Param("hospitalId") String hospitalId, @Param("checkYear") String checkYear);

    //规范化管理人数
   Long getStandardMemberCount(Map param);

    List<ListMemberVO> listGluMemberByKeyword(ListMemberDto dto);

    List<ListMemberVO> listGluHospitalMemberByKeyword(Map param);

    List<ListMemberVO> listGluMemberWithGroup(ListMemberDto dto);
}
