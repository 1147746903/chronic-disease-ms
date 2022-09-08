package com.comvee.cdms.sign.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.po.BmiPO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public interface BmiService {

    /**
     * 加载bmi列表
     * @param listBmiDTO
     * @return
     */
    List<BmiPO> listBmi(ListBmiDTO listBmiDTO);

    /**
     * 加载bmi列表
     * @param listBmiDTO
     * @return
     */
    PageResult<BmiPO> listBmiPage(ListBmiDTO listBmiDTO,PageRequest page);


    /**
     * 获取bmi
     * @param sid
     * @return
     */
    BmiPO getBmi(String sid);

    /**
     * 获取bmi建议
     * @param signId
     * @return
     */
    SignSuggestPO getBmiSuggest(String signId);

    /**
     * 新增bmi建议
     * @param addSuggestDTO
     * @return
     */
    String addBmiSuggest(AddSuggestDTO addSuggestDTO);

    /**
     * 新增bmi记录
     * @param addBmiServiceDTO
     * @return
     */
    String addBmi(AddBmiServiceDTO addBmiServiceDTO);

    /**
     * 加载bmi列表分布-医院统计
     * @param dto
     * @return
     */
    List<Map<String, Object>> listBmiRang(GetStatisticsDTO dto);

    /**
     * 获取最新的bmi
     * @param memberId
     * @return
     */
    BmiPO getLatestBmi(String memberId);

    /**
     * 添加bmi记录入库
     * @param addBmiMapperDTO
     */
    void addBmiMapper(AddBmiMapperDTO addBmiMapperDTO);
}
