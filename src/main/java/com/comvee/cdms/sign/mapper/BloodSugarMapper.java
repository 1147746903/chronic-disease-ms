package com.comvee.cdms.sign.mapper;

import com.comvee.cdms.app.doctorapp.model.app.*;
import com.comvee.cdms.machine.model.MachineModel;
import com.comvee.cdms.member.dto.MemberDataDTO;
import com.comvee.cdms.member.po.MemberSugarDayRecordPO;
import com.comvee.cdms.member.vo.ListBloodSugarWarnVO;
import com.comvee.cdms.member.vo.ListMemberBloodStaticsVO;
import com.comvee.cdms.member.vo.SugarMemberVO;
import com.comvee.cdms.sign.bo.BloodSugarInHosBO;
import com.comvee.cdms.sign.bo.BloodSugarOfParamCodeBO;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.po.MemberBloodSugarPO;
import com.comvee.cdms.sign.po.MemberParamValuePO;
import com.comvee.cdms.sign.vo.*;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public interface BloodSugarMapper {

    /**
     * 加载血糖记录
     * @param bloodSugarListMapperDTO
     * @return
     */
    List<BloodSugarPO> listBloodSugar(ListBloodSugarDTO bloodSugarListMapperDTO);

    List<BloodSugarPO> listBloodSugarByHospitalId(ListBloodSugarDTO bloodSugarListMapperDTO);

    //去重患者id
    List<String> listBloodSugarMemberIdList(ListBloodSugarDTO bloodSugarListMapperDTO);


    /**
     * 加载最新的血糖记录
     * @param bloodSugarListMapperDTO
     * @return
     */
    BloodSugarPO getNewBloodSugar(ListBloodSugarDTO bloodSugarListMapperDTO);

    /**
     * 获取血糖
     * @param sid
     * @return
     */    BloodSugarPO getBloodSugar(@Param("sid") String sid);

    /**
     * 添加血糖记录
     * @param addBloodSugarMapperDTO
     */
    void addBloodSugar(AddBloodSugarMapperDTO addBloodSugarMapperDTO);

    /**
     * 血糖平均值、最高值、最低值
     * @param countBloodSugarDTO
     */
    Map<String, Object> loadBloodAvgMaxMin(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 血糖测量总次数、偏高、正常、偏低
     * @param countBloodSugarDTO
     */
    Map<String, Object> loadBloodNumHigLow(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 血糖趋势图
     * @param countBloodSugarDTO
     */
    List<Map<String, Object>> findBloodNormalByStageDay(CountBloodSugarDTO countBloodSugarDTO);


    /**
     * 根据医生id加载他的患者血糖数据
     * @param startDt
     * @param endDt
     * @param doctorId
     * @return
     */
    List<BloodSugarPO> listMemberBloodSugarByDoctorId(@Param("startDt") String startDt, @Param("endDt")String endDt,@Param("doctorId") String doctorId);

    
    //gwx
    /**
     * 获取医生所属患者的血糖正常、异常人数
     * @param doctorId
     * @param startDt
     * @param endDt
     * @return
     */
    List<BloodSugarCountModel> getBloodNumForType(@Param("doctorId") String doctorId, @Param("startDt") String startDt,
                                                  @Param("endDt") String endDt, @Param("memberId") String memberId);
    
    /**
     * 根据条件获取各个状态的血糖人数
     * @param doctorId
     * @param startDt
     * @param endDt
     * @param paramLevel血糖异常程度
     * @param paramCode  是否空腹血糖码 1 是 / 2 否
     * @param payStatus  付费状态 1 是 / 0 否
     * @return
     */
    List<BloodNumByStatusResqModel> getBloodNumByStatus(@Param("doctorId") String doctorId,@Param("startDt") String startDt,
    		@Param("endDt") String endDt,@Param("paramLevel") String paramLevel,@Param("paramCode") String paramCode,
    		@Param("payStatus") String payStatus,@Param("hospitalId") String hospitalId);
    
    /**
     * 获取7日内  血糖数据量大于三的  所有 数据
     * @param doctorId
     * @param startDt
     * @param endDt
     * @param paramLevel
     * @param paramCode
     * @return
     */
    List<BloodNumByStatusResqModel> getBloodThreeDayByStatus(@Param("doctorId") String doctorId,@Param("startDt") String startDt,
    		@Param("endDt") String endDt, @Param("payStatus") String payStatus,@Param("hospitalId") String hospitalId);
    
    /**
     * 连续三天高血糖 - 从该list中取
     * @param doctorId
     * @param startDt
     * @param endDt
     * @param payStatus
     * @return
     */
    List<SugarDetailListModel> getBloodThreeDayListByStatus(@Param("doctorId") String doctorId,@Param("startDt") String startDt,
    		@Param("endDt") String endDt , @Param("payStatus") String payStatus,@Param("hospitalId") String hospitalId);
    
    /**
     * 患者血糖情况分组列表 - 偏低
     * @param doctorId
     * @param startDt
     * @param endDt
     * @param paramLevel
     * @param paramCode
     * @return
     */
    List<SugarDetailListModel> lowSugerList(@Param("doctorId") String doctorId,@Param("startDt") String startDt,
    		@Param("endDt") String endDt,@Param("paramLevel") String paramLevel,@Param("paramCode") String paramCode ,
                                            @Param("payStatus") String payStatus,@Param("hospitalId") String hospitalId);
    
    /**
     * 获取付费和 免费的  正常血糖值患者list
     * @param doctorId
     * @param startDt
     * @param endDt
     * @param payStatus
     * @return
     */
    List<NormalSugerListResqModel> normalSugerList(@Param("doctorId") String doctorId,@Param("startDt") String startDt,@Param("endDt") String endDt,@Param("payStatusList") List<Integer> payStatus,@Param("hospitalId") String hospitalId);
    
    /**
     * 7日内未测量血糖的患者
     * @param doctorId
     * @param startDt
     * @param endDt
     * @param payStatus
     * @return
     */
    List<NormalSugerListResqModel> noMeasureList(@Param("doctorId") String doctorId,@Param("startDt") String startDt
            ,@Param("endDt") String endDt,@Param("payStatusList") List<Integer> payStatusList,@Param("hospitalId")String hospitalId);
 
    /**
     * 加载血糖记录
     * @param bloodSugarListMapperDTO
     * @return
     */
    List<BloodSugarModel> listBloodSugarSign(ListBloodSugarDTO bloodSugarListMapperDTO);

    /**
     * 获取最新的血糖记录
     * @param memberId
     * @return
     */
    BloodSugarPO getLatestBloodSugar(@Param("memberId") String memberId ,@Param("paramCode") String paramCode,@Param("recordDt")String recordDt);


    /**
     * 7天内血糖（高、低）
     * @param bloodSugarListMapperDTO
     * @return
     */
    List<SugarMemberVO> loadBloodSugarByDay(ListBloodSugarByDayDTO listBloodSugarByDayDTO);

    /**
     * 时间段内血糖异常次数
     * @param bloodSugarListMapperDTO
     * @return
     */
    Map loadLowBloodSugarByMemberId(ListBloodSugarByDayDTO listBloodSugarByDayDTO);

    /**
     * 时间段内有血糖记录的患者
     * @param bloodSugarListMapperDTO
     * @return
     */
    List<SugarMemberVO> loadHasBloodSugarByDay(ListBloodSugarByDayDTO listBloodSugarByDayDTO);

    /**
     * 时间段内没有血糖记录的患者(7天、30天)
     * @param bloodSugarListMapperDTO
     * @return
     */
    List<SugarMemberVO> loadNotBloodSugarByDay(ListBloodSugarByDayDTO listBloodSugarByDayDTO);

    /**
     * 统计医生的血糖异常患者数量
     * @param doctorId
     * @param startDt
     * @param endDt
     * @return
     */
    long countDoctorMemberAbnormalNumber(@Param("doctorId") String doctorId,@Param("startDt") String startDt,@Param("endDt") String endDt,@Param("hospitalId") String hospitalId);

    /**
     * 加载患者的所有血糖记录
     * @param memberId
     * @return
     */
    List<ListAllBloodSugarDTO> listAllMemberBloodSugar(@Param("memberId") String memberId);

    /**
     * 删除血糖记录
     * @param sid
     */
    int deleteMemberBloodSugar(@Param("sid") String sid);

/*    *//**
     * 根据患者id统计记录血糖次数
     * @param synthesizeDataDTO
     * @return
     *//*
    long countNewBloodByMemberId(ListMemberBloodNumDTO listMemberBloodNumDTO);

    *//**
     * 根据患者id统计监测人数
     * @param synthesizeDataDTO
     * @return
     *//*
    long countNewBloodMemberByMemberId(ListMemberBloodNumDTO listMemberBloodNumDTO);*/

    /**
     * 获取患者一段时间内记录血糖次数
     * @param memberDataDTO
     * @return
     */
    long countBloodSugar(MemberDataDTO memberDataDTO);

    /**
     * 获取患者一段时间内的血糖记录
     * @param listBloodSugarDTO
     * @return
     */
    List<BloodSugarPO> listSugar(ListBloodSugarDTO listBloodSugarDTO);

    /**
     * 获取血糖次数和人数
     * @param listMemberBloodNumDTO
     * @return
     */
    Map<String,Object> countNewBloodAndMember(ListMemberBloodNumDTO listMemberBloodNumDTO);
    /**
     * 查询某个患者时间段内的血糖记录(1周,2周,3周,1个月)
     * @param listBloodSugarByDayDTO
     * @return
     */
    List<BloodSugarPO> loadBloodSugarByMemberIdAndDay(ListBloodSugarByDayDTO listBloodSugarByDayDTO);

    /**
     * 查询患者最近一周内的某一天出现三次高血糖的次数
     * @param listBloodSugarByDayDTO
     * @return
     */
    Map<String,Object> countBloodSugarWeek(ListBloodSugarByDayDTO listBloodSugarByDayDTO);

    /**
     * 血糖分析数据源
     * @param dto
     * @return
     */
    List<BloodSugarOfParamCodeBO> listMemberParamValuesOfStatistics(GetStatisticsDTO dto);

    /**
     * 统计医生的血糖异常患者数量
     * @param doctorIds
     * @return
     */
    long countDoctorsMemberAbnormalNumber(@Param("list") List<String> doctorIds);

    Map<String,Object> loadBloodNumHigLow2(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 患者目前在院期间血糖
     * @param memberId
     * @return
     */
    List<BloodSugarPO> listParamLogByMemberIdOfInHos(String memberId);

    /**
     * 查询一段时间内每天的血糖偏高偏低次数
     * @param countBloodSugarDTO
     * @return
     */
    List<Map<String,Object>> countBloodSugarMaxMin(CountBloodSugarDTO countBloodSugarDTO);

    /** v6.0.0
     * 加载院内每日血糖患者
     * @param memberBloodSugarDTO
     * @return
     */
    List<MemberBloodSugarPO> listTodayBloodSugarOfMember(MemberBloodSugarDTO memberBloodSugarDTO);

    /** v6.0.0
     * 获取血糖管理次数
     * @return
     */
    Long getTodayMemberBlood(TodayBloodDTO todayBloodDTO);

    /** v6.0.0
     * 获取出现异常的人数
     * @return
     */
    Long getMemberBloodForMemberId(TodayBloodDTO todayBloodDTO);


    /** v6.0.0
     * 获取虚拟病区出现异常的人数
     * @return
     */
    Long getVirtualWardBloodForMemberId(TodayBloodDTO todayBloodDTO);

    /** v6.0.0
     * 加载患者的所有血糖记录
     * @param memberId
     * @return
     */
    List<ListAllBloodSugarDTO> listAllMemberBloodSugarForHos(@Param("memberId") String memberId,@Param("inHos") Integer inHos,
                                                             @Param("startDt") String startDt,@Param("endDt") String endDt);

    /** v6.0.0
     * 获取患者血糖记录(根据条件)
     * @return
     * TODO 低性能sql
     */
    List<TodayHosMemberBloodVO> listMemberBloodByWhere(TodayHosMemberBloodDTO todayHosMemberBloodDTO);

    /** v6.0.0
     * 获取今日患者血糖列表(血糖值)
     * @param po
     * @return
     */
    List<MemberParamValueVO> listWorkParamValueOfMember(MemberParamValueDTO dto);

    /** v6.0.0
     * 获取血糖情况数量(根据患者分组)
     * @param paramLogModel
     * @return
     */
    List<MemberParamValueVO> listMemberParamLogNumberByWhere(TodayBloodDTO todayBloodDTO);

    /** v6.0.0
     * 获取患者今日血糖记录
     * @param memberId
     * @param paramDt
     * @return
     */
    MemberParamValuePO getWorkParamValueOfMember(@Param("memberId") String memberId, @Param("paramDt") String paramDt);

    /** v6.0.0
     * 更新患者每天血糖记录
     * @param po
     * @return
     */
    void updateMemberBloodSugarOfEverDay(MemberParamValuePO po);

    /** v6.0.0
     * 插入患者每天血糖记录
     * @param po
     * @return
     */
    void insertMemberBloodSugarOfEverDay(MemberParamValuePO po);

    /** v6.0.0
     * 获取患者3次偏高血糖记录
     * @param memberId
     * @return
     */
    List<BloodSugarPO> listTreeTimesParamLogsByMid(@Param("memberId") String memberId,@Param("paramDt") String paramDt);
    /**
     * v6.0.0
     * 获取患者最近住院血糖
     * @param memberId
     * @param inHospitalDt
     * @return
     */
    BloodSugarInHosBO getLatestBloodSugarInHos(@Param("memberId")String memberId, @Param("inHospitalDt")String inHospitalDt);

    /** v 5.1.6
     * 获取患者指定时间的血糖
     * @param listBloodSugarDTO
     */
    BloodSugarPO listBloodSugarOneDayOfInHos(ListBloodSugarDTO listBloodSugarDTO);


    /**
     * 获取患者每日血糖记录（全时段）
     * @param sid
     * @param memberId
     * @param recordDt
     * @return
     */
    MemberParamValuePO getMemberEveryDaySugarByParams(@Param("sid") String sid,@Param("memberId") String memberId,
                                                      @Param("recordDt") String recordDt);

    /**
     * 删除每日血糖记录
     * @param sid
     */
    void deleteMemberBloodSugarOfEverDay(@Param("sid") String sid);

    /**
     * 统计住院的血糖异常患者数量
     * @param doctorId
     * @param startDt
     * @param endDt
     * @return
     */
    long countInHospitalPatientAbnormalNumber(@Param("departmentId") String departmentId,@Param("startDt") String startDt,@Param("endDt") String endDt);

    /**
     * 统计住院患者未测量血糖的人数
     * @param departmentId
     * @param startDt
     * @param endDt
     * @return
     */
    long countInHospitalPatientUnMeasureNumber(@Param("departmentId") String departmentId,@Param("startDt") String startDt,@Param("endDt") String endDt);

    /**
     * 加载住院血糖正常的患者
     * @param departmentId
     * @return
     */
    List<NormalSugerListResqModel> listInHospitalNormalSugarPatient(@Param("departmentId") String departmentId ,@Param("startDt") String startDt ,@Param("endDt") String endDt);

    /**
     * 加载未测量血糖住院患者列表
     * @param departmentId
     * @param startDt
     * @param endDt
     * @return
     */
    List<NormalSugerListResqModel> listInHospitalNotMeasuredSugarPatient(@Param("departmentId") String departmentId ,@Param("startDt") String startDt ,@Param("endDt") String endDt);

    /**
     * 加载科室患者血糖
     * @param departmentId
     * @param startDt
     * @param endDt
     * @return
     */
    List<BloodSugarPO> listInHospitalBloodSugar(@Param("departmentId") String departmentId ,@Param("startDt") String startDt ,@Param("endDt") String endDt);

    /**
     * 获取患者们最新的血糖记录
     * @param memberIdList
     * @return
     */
    List<BloodSugarPO> listMembersLatestBloodSugarRecord(@Param("idList") List<String> memberIdList);

    /**
     * 加载患者指定时间内中每个时段最新的血糖
     * @param memberId
     * @param recordDt
     * @return
     */
    List<BloodSugarPO> listOneDayLatestBloodSugarGroupParamCode(@Param("memberId") String memberId ,@Param("startDt") String startDt ,@Param("endDt") String endDt);

    /**
     * 获取所选时间内患者的血糖记录
     * @param startDt
     * @param endDt
     * @param memberId
     * @return
     */
    List<BloodSugarPO> listMembersLatestBloodSugarRecordByRecordDt(ListMembersLatestBloodSugarRecordByRecordDtDTO dto);

    List<BloodSugarPO> listMembersLatestBloodSugarRecordByRecordDtAndMemberId(@Param("startDt") String startDt,@Param("endDt") String endDt,@Param("memberId") String memberId,@Param("paramLevel") Integer paramLevel,@Param("hospitalId")String hospitalId);

    /**
     * 加载血糖时段设置
     * @param hostialId
     * @return
     */
    List<BloodSugarParamSettingVO> listBloodSugarParamSetting(@Param("hostialId") String hostialId);
    /**
     * 获取住院日志表的患者血糖记录
     * @param memberBloodSugarDTO
     * @return
     */
    List<MemberBloodSugarPO> listTodayBloodSugarOfMemberByLog(MemberBloodSugarDTO memberBloodSugarDTO);

    /**
     * 获取血糖记录
     * @param dto
     * @return
     */
    BloodSugarPO searchBloodSugar(GetBloodSugarDTO dto);

    List<BloodSugarPO> getBloodSugarByMemberIdAndRecordDt(@Param("memberId") String memberId, @Param("recordDt")String recordDt);

    BloodSugarPO getBloodSugarByMemberIdAndParamValue(@Param("memberId") String memberId, @Param("recordDt")String recordDt,@Param("paramValue")Double paramValue);

    void updateBloodSugarEveryDayByMemberId(@Param("isLook") Integer isLook,@Param("paramDt") String paramDt);

    /**
     * 查询虚拟病区
     * @param paramDTO
     * @return
     */
    List<MemberParamValueVO> lisVirtualWardOfMember(MemberParamValueDTO paramDTO);

    /**
     * 获取机器信息
     * @param machineModel 机器参数信息
     * @return 机器信息
     * @author nzq
     * @date 2021/01/20
     */
    MachineModel getMachineModel(MachineModel machineModel);

    /**
     * 修改机器信息
     * @param machineModel 机器参数信息
     * @author nzq
     * @date 2021/01/20
     */
    void modifyMachineModel(MachineModel machineModel);

    List<BloodSugarPO> listBloodSugarByMemberIdAndRecordDt(BloodSugarPO bloodSugarPO);

    /** v8.1.0
     * 获取患者血糖记录(根据条件)
     * @return
     *
     */
    List<MemberSugarDayRecordPO> listAllBloodMemberHospital(ListMemberBloodSugarHospitalDTO listMemberBloodSugarHospitalDTO);

    MemberSugarDayRecordPO getSugarDayPOByMemberIdAndDt(@Param("memberId") String memberId, @Param("recordDt") String recordDt);
    //有血糖记录的日期
    List<String> getSugarRecordDayByMemberId(ListBloodSugarDTO listBloodSugarDTO);

    int addMemberSugarDayRecord(MemberSugarDayRecordPO MemberSugarDayRecordPO);

    int updateMemberSugarDayRecord(MemberSugarDayRecordPO MemberSugarDayRecordPO);

    int deleteMemberSugarDayRecord(MemberSugarDayRecordPO MemberSugarDayRecordPO);


    //患者统计
    ListMemberBloodStaticsVO getMemberBloodStaticsList(@Param("memberId") String memberId, @Param("departmentId") String departmentId);

    //血糖预警列表
    List<ListBloodSugarWarnVO> getBloodSugarWarnVOList(SugarMonitorStaticsDTO dto);

    //修改血糖预警处理状态
    int handleBloodSugarBySid(@Param("sid") String sid, @Param("status") String status);

    //未处理的血糖预警
    int getBloodSugarWarnNum(SugarMonitorStaticsDTO dto);

    //获取本年度最近一次空腹血糖 是否异常人数
    Long getLastSugarYear(@Param("hospitalIdList") List<String> hospitalIdList, @Param("year") String year, @Param("paramCode") String paramCode, @Param("abnormal") Integer abnormal);

}
