package com.comvee.cdms.sign.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.po.BloodPressurePO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public interface BloodPressureService {

    /**
     * 加载血压列表
     * @param listBloodPressureDTO
     * @return
     */
    List<BloodPressurePO> listBloodPressure(ListBloodPressureDTO listBloodPressureDTO);

    /**
     * 加载血压列表
     * @param listBloodPressureDTO
     * @return
     */
    PageResult<BloodPressurePO> listBloodPressurePage(ListBloodPressureDTO listBloodPressureDTO, PageRequest page);

    /**
     * 低压峰值 舒张压
     * @param countBloodSugarDTO
     */
    Map<String, Object> loadBloodPressureDbpMax(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 高压峰值 收缩压
     * @param countBloodSugarDTO
     */
    Map<String, Object> loadBloodPressureSbpMax(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 测量总次数、偏高、正常、偏低
     * @param countBloodSugarDTO
     */
    Map<String, Object> loadBloodPressureCount(CountBloodSugarDTO countBloodSugarDTO);

    /**
     * 获取血压记录
     * @param sid
     * @return
     */
    BloodPressurePO getBloodPressure(String sid);

    /**
     * 获取血压建议
     * @param signId
     * @return
     */
    SignSuggestPO getBloodPressureSuggest(String signId);

    /**
     * 新增血压建议
     * @param addSuggestDTO
     * @return
     */
    String addBloodPressureSuggest(AddSuggestDTO addSuggestDTO);

    /**
     * 新增血压记录
     * @param addBloodPressureServiceDTO
     * @return
     */
    String addBloodPressure(AddBloodPressureServiceDTO addBloodPressureServiceDTO);

    /**
     * 血压达标率统计数据源-医院
     * @param dto
     * @return
     */
    List<BloodPressurePO> listBloodPressureOfStatistics(GetStatisticsDTO dto);

    /**
     * 患者目前在院期间血压
     * @param memberId
     * @return
     */
    List<BloodPressurePO> listBloodPressureByMemberIdOfInHos(String memberId);

    /**
     * 获取患者最新的血压记录
     * @param memberId
     * @return
     */
    BloodPressurePO getLatestBloodPressure(String memberId);

    /**
     * v6.0.0
     * 出院患者血压列表
     * @param dto
     * @return
     */
    List<BloodPressurePO> listBloodPressureOfStatisticsOutHos(GetStatisticsDTO dto);

    String addBloodPressureForWechat(AddBloodPressureServiceDTO addBloodPressureServiceDTO);

    /**
     * v6.0.0
     * 添加血压记录入库
     * @param addBloodPressureMapperDTO
     */
    void addBloodPressureMapper(AddBloodPressureMapperDTO addBloodPressureMapperDTO);
}
