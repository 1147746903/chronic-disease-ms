package com.comvee.cdms.complication.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.model.dto.*;
import com.comvee.cdms.complication.model.po.ScreeningDataPO;
import com.comvee.cdms.complication.model.po.ScreeningItemPO;
import com.comvee.cdms.complication.model.po.ScreeningListPO;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.complication.model.vo.ScreeningListVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatFinishVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatOrderVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatVO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
public interface ScreeningService {

    /**
     * 新增筛查单
     * @param addScreeningListDTO
     */
    void addScreeningList(AddScreeningListDTO addScreeningListDTO);

    /**
     * 根据id获取筛查单
     * @param screeningId
     * @return
     */
    ScreeningListPO getScreeningById(String screeningId);

    /**
     * 修改筛查单
     */
    void updateScreeningList(ScreeningListPO screeningListPO);

    /**
     * 新增筛查报告
     * @param screeningReportPO
     */
    void addScreeningReport(ScreeningReportPO screeningReportPO);

    /**
     * 修改筛查报告
     * @param screeningReportPO
     */
    void updateScreeningReport(ScreeningReportPO screeningReportPO);

    /**
     * 根据id获取筛查报告
     * @param reportId
     */
    ScreeningReportPO getScreeningReportById(String reportId);

    /**
     * 根据id获取筛查报告
     * @param reportId
     */
    ScreeningReportPO getScreeningReportById(String reportId ,Integer valid);

    /**
     * 加载筛查列表
     * @param pr
     * @param listScreeningParam
     * @param doctorSessionBO
     * @return
     */
    PageResult<ScreeningListPO> listScreening(PageRequest pr, ListScreeningDTO listScreeningParam);

    /**
     * 统计筛查
     * @param doctorSessionBO
     * @return
     */
    ScreeningStatVO statScreening(DoctorSessionBO doctorSessionBO);

    /**
     * 统计预约筛查
     * @param doctorSessionBO
     * @return
     */
    ScreeningStatOrderVO statOrderScreening(DoctorSessionBO doctorSessionBO);

    /**
     * 统计完成的筛查
     * @param doctorSessionBO
     * @return
     */
    ScreeningStatFinishVO statFinishScreening(DoctorSessionBO doctorSessionBO);

    /**
     * 根据筛查id和类型获取筛查报告
     * @param screeningId
     * @param screeningType
     * @return
     */
    ScreeningReportPO getScreeningReport(String screeningId ,Integer screeningType);



    /**
     * 加载需要复诊提醒的列表
     * @param page
     * @param rows
     * @return
     */
    PageResult<ScreeningListPO> listScreeningReturnVisitRemind(int page ,int rows);

    /**
     * 加载上周的筛查单列表
     * @param page
     * @param rows
     * @return
     */
    PageResult<ScreeningListPO> listLastWeekScreening(int page ,int rows);

    /**
     * 加载筛查的所有报告
     * @param screeningId
     * @return
     */
    List<ScreeningReportPO> listScreeningReport(String screeningId);

    /**
     * 加载患者的筛查列表
     * @param pr
     * @param memberId
     * @return
     */
    PageResult<ScreeningListPO> listMemberScreening(PageRequest pr, String memberId);

    /**
     * 根据时间ABI,VPT新增数量
     * @param synthesizeDataDTO
     * @return
     */
    Map<String,Object> countNewScreening(SynthesizeDataDTO synthesizeDataDTO);

    /**
     *
     * 查询监测ABI,VPT的人数
     * @param synthesizeDataDTO
     * @return
     */
    Map<String,Object> countScreeningPeople(SynthesizeDataDTO synthesizeDataDTO);
    /**
     * 根据条件获取患者最新筛查记录
     * @param synthesizeDataDTO
     * @return
     */
    ScreeningDataPO getScreening(SynthesizeDataDTO synthesizeDataDTO);


    /**
     * 加载筛查数据统计列表
     * @param pr
     * @param listScreeningDataDTO
     * @return
     */
    PageResult<ScreeningDataPO> listScreeningData(PageRequest pr ,ListScreeningDataDTO listScreeningDataDTO);

    /**
     * 新增筛查数据
     * @param screeningDataPO
     */
    void addScreeningData(ScreeningDataPO screeningDataPO);

    /**
     * 加载需要提醒筛查预约提醒的患者
     * @param page
     * @param rows
     * @return
     */
    PageResult<ScreeningListPO> listScreeningOrderRemindPatient(int page ,int rows);

    /**
     * 加载患者的筛查报告列表
     * @param memberId
     * @param pr
     * @return
     */
    PageResult<ScreeningReportPO> listMemberScreeningReport(String memberId ,PageRequest pr);

    /**
     * 删除筛查
     * @param screeningId
     */
    void deleteScreening(String screeningId);

    /**
     * 获取最后一次报告
     * @param screeningType
     * @return
     */
    ScreeningReportPO getLastScreeningReport(String memberId ,Integer screeningType ,String doctorId);

    /**
     * 加载患者的筛查报告
     * @return
     */
    JSONObject listPatientScreeningReport(String memberId ,String doctorId);

    /**
     * 删除患者筛查
     * @param memberId
     * @param doctorId
     */
    void deleteMemberScreening(String memberId ,String doctorId);

    /**
     * 获取筛查报告
     * @param screeningId
     * @param screeningType
     * @return
     */
    ScreeningReportPO getScreeningReport(String screeningId ,Integer screeningType ,String reportId);

    /**
     * 修改筛查报告 (含逻辑处理)
     * @param screeningReportPO
     */
    void updateScreeningReport(UpdateScreeningReportParam screeningReportPO);

    /**
     * 获取最后一次报告
     * @param screeningType
     * @return
     */
    ScreeningReportPO getLastScreeningReport(String memberId ,Integer screeningType ,String doctorId ,String unScreeningId);

    /**
     * 患者近六个月各种类型最新的并发症筛查报告
     * @param memberId
     * @return
     */
    List<ScreeningReportPO> listScreeningReportOfTypeNearlySixMonth(String memberId,String hospitalId);

    /**
     * 获取患者近6个月所有类型的并发症模块的检查
     * @param memberId
     * @return
     */
    Map<String,Object> listScreeningReportNearlySixMonths(String memberId,String hospitalId);

    /**
     * 糖化血红蛋白分布情况统计数据源-医院
     * @param dto
     * @return
     */
    List<ScreeningReportPO> listIntelligentResultOfThStatistics(GetStatisticsDTO dto);

    /**
     * 并发症患者数据-医院统计
     * @param dto
     * @return
     */
    Long getScreeningOfMemberCount(GetStatisticsDTO dto);

    /**
     * 开始筛查
     * @param screeningId
     */
    void startScreening(String screeningId,String doctorId);

    /**
     * 预约筛查
     * @param screeningId
     * @param orderDate
     * @param orderTime
     * @param orderTimeCode
     * @param mobilePhone
     */
    void orderScreening(String screeningId, String orderDate, String orderTime, Integer orderTimeCode, String mobilePhone);

    /**
     * 取消筛查项
     * @param screeningId
     * @param screeningType
     */
    void cancelScreeningItem(String screeningId ,Integer screeningType ,String stopReason ,String operatorName);

    /**
     * 获取并发症VO视图
     * @param screeningId
     * @return
     */
    ScreeningListVO getScreeningVOById(String screeningId);

    /*************************************************邪恶的分割线******************************************************
     * @version v6.0.0
     * @author: linyd
     * @date: 2020/03/03
     *****************************************************************************************************************/
    /**
     * 获取患者近6个月所有类型的并发症模块的检查列表
     * @param memberId
     * @param hospitalId
     * @return
     */
    List<Map<String,Object>> listReportNearlySixMonths(String memberId,String hospitalId);

    /**
     * 新增或修改筛查单
     * @param addScreeningListDTO
     */
    void addOrUpdateScreening(ScreeningListPO add);

    ScreeningItemPO getScreeningPre(String preId, String screeningId, Integer screeningType);

    void updateScreenPre(ScreeningItemPO screeningPrePO);

    void eyesReadingFilm(EyesReadingFilmParam eyesReadingFilmParam);

    void inputACRReport(ACRDataInputDto dto);

    void inputHba1cReport(HbA1cDataInputDto dto);

    PageResult<ScreeningReportPO> listMemberScreeningReport(ListMemberScreeningReportDTO dto ,PageRequest pr);

    void confirmScreening(String screeningId, int screeningType ,DoctorSessionBO doctorSession);

    /**
     * 获取最后一次报告
     * @param screeningType
     * @return
     */
    ScreeningReportPO getLastScreeningReport(GetLastScreeningReportDTO param);
}
