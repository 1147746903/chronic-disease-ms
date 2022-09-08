package com.comvee.cdms.sign.service;

import com.comvee.cdms.app.doctorapp.model.app.SugarDetailListModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.vo.DepartmentVO;
import com.comvee.cdms.member.dto.ListMemberBloodStaticsDTO;
import com.comvee.cdms.member.dto.ListMemberDTO;
import com.comvee.cdms.member.dto.MemberDataDTO;
import com.comvee.cdms.member.po.MemberSugarDayRecordPO;
import com.comvee.cdms.member.vo.*;
import com.comvee.cdms.sign.bo.BloodSugarInHosBO;
import com.comvee.cdms.sign.bo.BloodSugarOfParamCodeBO;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.po.MemberBloodSugarPO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.vo.*;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public interface BloodSugarService {

    /**
     * 加载血糖记录
     * @param listBloodSugarDTO
     * @return
     */
    List<BloodSugarPO> listBloodSugar(ListBloodSugarDTO listBloodSugarDTO);

    /**
     * 分页加载血糖记录
     * @param listBloodSugarDTO
     * @return
     */
    List<Map<String ,Object>> listBloodSugarPage(ListBloodSugarDTO listBloodSugarDTO, PageRequest page);

    /**
     * 查询一段时间的血糖记录
     * @param listBloodSugarDTO
     * @return
     */
    List<Map<String ,Object>> listBloodSugarByDate(ListBloodSugarDTO listBloodSugarDTO);

    /**
     * 获取血糖
     * @param sid
     * @return
     */
    BloodSugarPO getBloodSugar(String sid);

    /**
     * 获取血糖建议
     * @param signId
     * @return
     */
    SignSuggestPO getBloodSugarSuggest(String signId);

    /**
     * 新增血糖建议
     * @param addSuggestDTO
     * @return
     */
    String addBloodSugarSuggest(AddSuggestDTO addSuggestDTO);

    /**
     * 新增血糖记录
     * @param addBloodSugarServiceDTO
     * @return
     */
    String addBloodSugar(AddBloodSugarServiceDTO addBloodSugarServiceDTO);


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
    Map<String, Object> findBloodNormalByStageDay(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 根据医生id加载他的患者血糖数据
     * @param startDt
     * @param endDt
     * @param doctorId
     * @return
     */
    List<BloodSugarPO> listMemberBloodSugarByDoctorId(String startDt, String endDt, String doctorId);


    /**
     * 获取最新的血糖记录
     * @param memberId
     * @return
     */
    BloodSugarPO getLatestBloodSugar(String memberId ,String paramCode,String recordDt);

    /**
     * 7天内血糖（高、低）
     * @param bloodSugarListMapperDTO
     * @return
     */
    PageResult<SugarMemberVO> loadBloodSugarByDay(ListBloodSugarByDayDTO listBloodSugarByDayDTO, PageRequest page);

    /**
     * 时间段内没有血糖记录的患者(7天、30天)
     * @param listBloodSugarByDayDTO
     * @return
     */
    PageResult<SugarMemberVO> loadNotBloodSugarByDay(ListBloodSugarByDayDTO listBloodSugarByDayDTO, PageRequest page);

    /**
     * 查询某个患者时间段内的血糖记录(1周,2周,3周,1个月)
     * @param listBloodSugarByDayDTO
     * @return
     */
    List<BloodSugarPO> loadBloodSugarByMemberIdAndDay(String memberId,String codeDt);

    /**
     * 查询患者最近一周内的某一天出现三次高血糖的次数
     * @param listBloodSugarByDayDTO
     * @return
     */
    Map<String,Object> countBloodSugarWeek(ListBloodSugarByDayDTO listBloodSugarByDayDTO);
    /**
     * 连续三天高血糖 - 从该list中取
     * @param doctorId
     * @param startDt
     * @param endDt
     * @param payStatus
     * @return
     */
    List<SugarDetailListModel> getBloodThreeDayListByStatus(ListBloodSugarByDayDTO listBloodSugarByDayDTO);

    /**
     * 加载患者的所有血糖记录
     * @param memberId
     * @return
     */
    PageResult<ListAllBloodSugarDTO> listAllMemberBloodSugar(String memberId,PageRequest page);

    /**
     * 删除血糖记录
     * @param memberId
     */
    void deletMemberBloodSugar(String sid);

    /**
     * 根据时间统计血糖监测人数和血糖监测次数
     * @param synthesizeDataDTO
     * @return
     */
    Map<String,Object> countNewBloodAndMember(SynthesizeDataDTO synthesizeDataDTO);

    /**
     * 查询每周上传血糖次数和总上传次数
     * @param memberDataDTO
     * @return
     */
    Map<String,Object> countWeekAndAll(MemberDataDTO memberDataDTO);


    /**
     * 获取患者一段时间内的血糖记录
     * @param listBloodSugarDTO
     * @return
     */
    List<BloodSugarPO> listSugar(ListBloodSugarDTO listBloodSugarDTO);

    /**
     * 分页加载血糖记录（是否住院记录）
     * @param listBloodSugarDTO
     * @param page
     * @return
     */
    PageResult<Map<String, Object>> listBloodSugarPageOfInHos(ListBloodSugarDTO listBloodSugarDTO, PageRequest page);

    /**
     * 分页加载随机血糖列表
     * @param listBloodSugarDTO
     * @param page
     * @return
     */
    PageResult<BloodSugarPO> listRandomBloodSugarPageOfInHos(ListBloodSugarDTO listBloodSugarDTO, PageRequest page);

    /**
     * 血糖分析数据源-医院
     * @param dto
     * @return
     */
    List<BloodSugarOfParamCodeBO> listMemberParamValuesOfStatistics(GetStatisticsDTO dto);

    Map<String,Object> loadBloodNumHigLow2(CountBloodSugarDTO countBloodSugarDTO);
    /**
     * 患者目前在院期间血糖
     * @param memberId
     * @return
     */
    List<BloodSugarPO> listParamLogByMemberIdOfInHos(String memberId);
    /**
     * 获取患者近一周空腹血糖  和所有餐后血糖的平均值
     * @return
     */
    Map<String,Object> getAvgWeekBlood(String memberId);

    /**
     * 查询一段时间内每天的血糖偏高偏低次数
     * @param countBloodSugarDTO
     * @return
     */
    List<Map<String,Object>> countBloodSugarMaxMin(String memberId,String startDt,String endDt,String paramCode);


    /** v6.0.0
     * 加载住院患者每日血糖
     * @param memberBloodSugarDTO
     * @return
     */
    PageResult<MemberBloodSugarVO> listTodayBloodSugarOfMember(MemberBloodSugarDTO memberBloodSugarDTO, PageRequest page);

    /** v6.3.5
     * 加载住院患者每日血糖
     * @param memberBloodSugarDTO
     * @return
     */
    PageResult<MemberBloodSugarVO> listAllBloodSugarOfMember(MemberBloodSugarDTO memberBloodSugarDTO, PageRequest page);

    /** v6.0.0
     * 获取血糖管理次数
     * @return
     */
    TodayBloodNumVO getTodayMemberBloodNum(TodayMemberBloodNumDTO todayMemberBloodNumDTO);

    /** v6.0.0
     * 将获取今日患者血糖列表(check)PO转换成视图model
     * @author 李左河
     * @date 2018年3月22日 上午9:40:31
     * @param po
     * @return
     */
    List<MemberBloodSugarVO> convertBSPoToWorkMemberBSugarModel(List<MemberBloodSugarPO> po,String doctorName,String doctorId);

    /** v6.0.0
     * 获取患者血糖记录(根据条件)
     * @return
     */
    PageResult<TodayHosMemberBloodVO> listMemberBloodByWhere(TodayHosMemberBloodDTO todayHosMemberBloodDTO,PageRequest page);

    /** v6.0.0
     * 获取今日患者血糖列表
     * @param memberParamValueDTO
     * @param page
     * @return
     */
    PageResult<MemberParamValueVO> listHighRiskParamLogOfMember(MemberParamValueDTO memberParamValueDTO,PageRequest page);

    /**
     * v6.0.0
     * 获取患者最近住院血糖
     * @param memberId
     * @param inHospitalDt
     * @return
     */
    BloodSugarInHosBO getLatestBloodSugarInHos(String memberId, String inHospitalDt);

    /** v 5.1.6
     * 获取患者指定时间的血糖
     * @param listBloodSugarDTO
     */
    BloodSugarPO listBloodSugarOneDayOfInHos(ListBloodSugarDTO listBloodSugarDTO);

    /**
     * v6.0.1
     * @param sidStr
     * @param pager
     * @return
     */
    PageResult<BloodSugarPO> pageBloodSugar(String sidStr, PageRequest pager);

    /**
     * 601
     * 删除血糖
     * @param sid
     * @param memberId
     * @param recordDt
     * @return
     */
    boolean deleteBloodSugar(String sid, String memberId, String recordDt);

    void bloodSugarDayRecordHandler(String sid,String memberId,String recordDt,String paramCode);

    /**
     * 获取患者们最新的血糖记录
     * @param memberIdList
     * @return
     */
    List<BloodSugarPO> listMembersLatestBloodSugarRecord(List<String> memberIdList);

    /**
     * 加载患者指定时间内中每个时段最新的血糖
     * @param memberId
     * @param recordDt
     * @return
     */
    List<BloodSugarPO> listOneDayLatestBloodSugarGroupParamCode(String memberId ,String startDt ,String endDt);

    /**
     * 获取血糖打印数据
     * @param dto
     * @param page
     * @return
     */
    PageResult<BloodGlucoseRecordPrintingVO> listMemberBlood(BloodGlucoseRecordPrintingDTO dto, PageRequest page);

    /**
     * 获取血糖打印的患者分类科室
     * @param dto
     * @return
     */
    List<DepartmentVO> listDepartment(BloodGlucoseRecordPrintingDTO dto);

    /**
     * 获取医生所在科室和统计住过院的患者人数
     * @return
     */
    DoctorMemberVo getDoctorDepartName(MemberBloodSugarDTO memberBloodSugarDTO);

    /**
     * 获取高危血糖人数
     * @param paramDTO
     * @return
     */
    Long countHighRiskParamLogOfMember(MemberParamValueDTO paramDTO);

    /** v8.1.0
     * 获取患者血糖记录
     * @return
     */
    PageResult<MemberSugarHospitalListVO>  listMemberBloodHospital(ListMemberBloodSugarHospitalDTO listMemberBloodSugarHospitalDTO, PageRequest page);


    /**
     * v8.1.0住院患者动态血糖记录
     * @param listMemberDTO
     * @param page
     * @return
     */
    PageResult<DySugarDataListVO>  listMemberDySugarData(ListMemberDTO listMemberDTO, PageRequest page);

    /**
     * v8.1.0出院患者动态血糖记录
     * @param listMemberDTO
     * @param page
     * @return
     */
    PageResult<DySugarDataListVO>  listMemberDySugarDataOut(ListMemberDTO listMemberDTO, PageRequest page);


    PageResult<ListMemberBloodStaticsVO>  listMemberStatics(ListMemberBloodStaticsDTO listMemberBloodStaticsDTO , PageRequest page);

    /**
     * 预警列表
     * @param dto
     * @param page
     * @return
     */
    Map<String, Object>  bloodSugarWarnList(SugarMonitorStaticsDTO dto, PageRequest page);

    boolean handleBloodSugarBySid(String sid,String status);

    //血糖任务
    PageResult<MemberBloodSugarVO> listBloodSugarTask(MemberBloodSugarDTO memberBloodSugarDTO, PageRequest page);

    MemberSugarDayRecordPO getSugarDayPOByMemberIdAndDt(String memberId,String recordDt);

    //添加每日血糖
    void dayMemberSugarRecordHandler(AddBloodSugarServiceDTO dto,AddBloodSugarMapperDTO mDto);
}
