package com.comvee.cdms.statistics.mapper;

import com.comvee.cdms.member.dto.ListMemberVisitDTO;
import com.comvee.cdms.statistics.bo.MemberExceptionBaseBO;
import com.comvee.cdms.statistics.po.MemberExceptionRecordPO;
import com.comvee.cdms.statistics.vo.ListMemberExceptionVO;
import com.comvee.cdms.statistics.vo.ListScreenStaticsVO;
import org.apache.ibatis.annotations.Param;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

/**
 * 患者异常信息记录(MemberExceptionRecordPO)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-18 13:59:21
 */
public interface StaticsMapper {

    /**
     * 查询医院对应显示异常项目code
     * @param hospitalId
     * @return
     */
    List<String> loadExceptionItemCodeByHospital(@Param("hospitalId") String hospitalId);

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    MemberExceptionRecordPO queryById(String sid);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tMemberExceptionRecord 实例对象
     * @return 对象列表
     */
    List<MemberExceptionRecordPO> queryAll(MemberExceptionRecordPO tMemberExceptionRecord);

    /**
     * 新增数据
     *
     * @param tMemberExceptionRecord 实例对象
     * @return 影响行数
     */
    int insert(MemberExceptionRecordPO tMemberExceptionRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MemberExceptionRecordPO> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MemberExceptionRecordPO> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MemberExceptionRecordPO> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MemberExceptionRecordPO> entities);

    /**
     * 修改数据
     *
     * @param tMemberExceptionRecord 实例对象
     * @return 影响行数
     */
    int update(MemberExceptionRecordPO tMemberExceptionRecord);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

    List<ListMemberExceptionVO> loadMemberExceptionRecordList(@Param("recordDt") String recordDt, @Param("hospitalId") String hospitalId);

    Long loadMemberExceptionNum(@Param("recordDt") String recordDt, @Param("hospitalIdList") List<String> hospitalIdList);

    //通过memberId来获取codeList
    List<String> loadExceptionItemCodeByMemberId(@Param("memberId") String memberId);


    List<String> getHospitalByMemberId(@Param("memberId") String memberId);

    //获取医院建档人数
    Long loadInHospitalMembersNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    //获取并发症筛查人数
    Long loadDoScreenMembersNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date, @Param("dealStatus") Integer dealStatus);

    //今日血糖监测人数
    Long loadDoSugarMembersNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    Long loadDoSugarMembersBadNum(@Param("hospitalId") String hospitalId, @Param("date") String date);

    Long loadDoSugarMembersNumByHospitalId(@Param("hospitalId") String hospitalId, @Param("committeeId") String committeeId, @Param("startDt") String startDt, @Param("endDt") String endDt);

    //今日糖化检查人数
    Long loadDoHa1cMembersNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    //今日血压监测人数
    Long loadDoBloodPressureMembersNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    Long loadDoBloodPressureMembersBadNum(@Param("hospitalId") String hospitalId, @Param("date") String date);

    Long loadDoBloodPressureMembersNumByHospitalId(@Param("hospitalId") String hospitalId, @Param("committeeId") String committeeId, @Param("startDt") String startDt, @Param("endDt") String endDt);

    //今日分标异常人数
    Long loadSugarLevelMembersNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    //今日分级分层异常人数
    Long loadPressureLevelLayerMembersNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    //待分标/分层分级人数
    Long loadUnConfirmLevelMemberNum(@Param("hospitalId") String hospitalId, @Param("levelType") Integer levelType);

    //所有类型异常整合到一个
    List<MemberExceptionBaseBO> loadAllMemberException(@Param("startDt") String startDt, @Param("endDt") String endDt);

    //今日管理处方人数
    Long loadDayPrescriptionNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);
    //今日随访人数
    Long loadDayFollowNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);
    //管理人数
    Long loadHospitalMemberNum(@Param("hospitalIdList") List<String> hospitalIdList);
    //糖尿病人数
    Long loadHospitalDiabNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);
    //高血压人数
    Long loadHospitalHypNum(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);


    //年度血糖监测人数 村
    Long loadDoSugarMembersNumByCommitteeId(@Param("committeeId") String committeeId, @Param("checkYear") String checkYear);
    //年度血压监测人数 村
    Long loadDoBloodPressureMembersNumByCommitteeId(@Param("committeeId") String committeeId, @Param("checkYear") String checkYear);
    //获取村建档人数
    Long loadInHospitalMembersNumByCommitteeId(@Param("committeeId") String committeeId, @Param("checkYear") String checkYear, @Param("hospitalId") String hospitalId);
    //年度体检 村
    Long countMemberInspectionByCommitteeId(@Param("committeeId") String committeeId, @Param("checkYear") String checkYear, @Param("hospitalId") String hospitalId);


    List<ListScreenStaticsVO> getMemberJoinHospitalList(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    List<ListScreenStaticsVO> getMemberBloodSugarList(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    List<ListScreenStaticsVO> getMemberBloodPressureList(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    List<ListScreenStaticsVO> getMemberDiabetesLevelList(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    List<ListScreenStaticsVO> getMemberHypLevelList(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);

    //建档就诊记录数
    long countMemberJoinVisit(@Param("hospitalIdList") List<String> hospitalIdList, @Param("date") String date);
    //建档外院就诊记录数
    long countMemberJoinOutVisit(@Param("hospitalId") String hospitalId, @Param("date") String date);

    //社区随访统计人数
    Long loadFollowNumByCommitteeId(@Param("committeeId") String committeeId, @Param("startDt") String startDt, @Param("endDt") String endDt, @Param("type") Integer type, @Param("hospitalId") String hospitalId);

    Long loadFollowNumByHospitalId(@Param("startDt") String startDt, @Param("endDt") String endDt, @Param("type") Integer type, @Param("hospitalId") String hospitalId);

    //规范随访人数
    Long getStandardFollowMemberCount(Map param);
    //规范管理人数
    Long getStandardManageMemberCount(Map param);

    //年度随访人数(血糖、血压)
    Long getYearFollowCountHospitalId(@Param("hospitalId") String hospitalId, @Param("num") String num, @Param("year") String year);
    //人数
    Long getYearFollowCountCommitedId(@Param("committeeId") String committeeId, @Param("num") String num, @Param("year") String year, @Param("type") String type, @Param("hospitalId") String hospitalId);
    //次数
    Long getYearFollowTotalCountCommitedId(@Param("committeeId") String committeeId, @Param("year") String year, @Param("type") String type);
}

