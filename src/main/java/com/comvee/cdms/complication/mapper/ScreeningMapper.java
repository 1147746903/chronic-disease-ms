package com.comvee.cdms.complication.mapper;

import com.comvee.cdms.complication.model.dto.AddScreeningListDTO;
import com.comvee.cdms.complication.model.dto.CountScreeningDTO;
import com.comvee.cdms.complication.model.dto.ListScreeningDTO;
import com.comvee.cdms.complication.model.po.ScreeningDataPO;
import com.comvee.cdms.complication.model.po.ScreeningListPO;
import com.comvee.cdms.complication.model.po.ScreeningItemPO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
public interface ScreeningMapper {

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
    ScreeningListPO getScreeningById(@Param("screeningId") String screeningId);

    /**
     * 修改筛查单
     */
    void updateScreeningList(ScreeningListPO screeningListPO);

    /**
     * 统计筛查
     * @param countScreeningDTO
     * @return
     */
    long countScreening(CountScreeningDTO countScreeningDTO);

    /**
     * 加载筛查列表
     * @param listScreeningDTO
     * @return
     */
    List<ScreeningListPO> listScreening(ListScreeningDTO listScreeningDTO);

    /**
     * 加载需要复诊提醒的列表
     * @param finishDt
     * @return
     */
    List<ScreeningListPO> listScreeningReturnVisitRemind(@Param("finishDt") String finishDt);

    /**
     * 加载上周完成的筛查单
     * @param startDt
     * @param endDt
     * @return
     */
    List<ScreeningListPO> listLastWeekScreening(@Param("startDt") String startDt ,@Param("endDt") String endDt);

    /**
     * 加载患者的筛查列表
     * @param memberId
     * @return
     */
    List<ScreeningListPO> listMemberScreening(@Param("memberId") String memberId);


    /**
     * 根据时间查询新增ABI,VPT总数量
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
     * 根据条件获取患者筛查ABI记录
     * @param synthesizeDataDTO
     * @return
     */
    List<ScreeningDataPO> getABI(SynthesizeDataDTO synthesizeDataDTO);
    /**
     * 根据条件获取患者筛查VPT记录
     * @param synthesizeDataDTO
     * @return
     */
    List<ScreeningDataPO> getVPT(SynthesizeDataDTO synthesizeDataDTO);


    /**
     * 加载需要筛查预约提醒的患者
     * @param orderDate
     * @return
     */
    List<ScreeningListPO> listScreeningOrderRemindPatient(@Param("orderDate") String orderDate);

    /**
     * 删除筛查
     * @param screeningId
     */
    void deleteScreening(@Param("screeningId") String screeningId);

    /**
     * 删除患者筛查列表
     * @param memberId
     * @param doctorId
     */
    void deleteScreeningList(@Param("memberId") String memberId ,@Param("doctorId") String doctorId);

    /**
     * 新增开始筛查单
     * @param screeningPrePO
     */
    void addScreeningPre(ScreeningItemPO screeningPrePO);

    /**
     *
     * @param update
     */
    void updateScreenPre(ScreeningItemPO update);

    /**
     *
     * @param screeningPrePO
     * @return
     */
    ScreeningItemPO getScreeningPre(ScreeningItemPO screeningPrePO);

    /**
     * 新增或修改筛查单
     * @param addScreeningListDTO
     */
    void addOrUpdateScreening(ScreeningListPO add);

}
