package com.comvee.cdms.sign.mapper;

import com.comvee.cdms.sign.dto.AddBmiMapperDTO;
import com.comvee.cdms.sign.dto.GetBmiDTO;
import com.comvee.cdms.sign.dto.ListBmiDTO;
import com.comvee.cdms.sign.po.BmiPO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
public interface BmiMapper {

    /**
     * 加载bmi列表
     * @param listBmiDTO
     * @return
     */
    List<BmiPO> listBmi(ListBmiDTO listBmiDTO);

    /**
     * 获取bmi
     * @param sid
     * @return
     */
    BmiPO getBmi(GetBmiDTO param);

    /**
     * 新增bmi记录
     * @param addBmiMapperDTO
     */
    void addBmi(AddBmiMapperDTO addBmiMapperDTO);

    /**
     * 加载bmi列表分布-医院统计
     * @param dto
     * @return
     */
    List<Map<String,Object>> listBmiRang(GetStatisticsDTO dto);

    /**
     * 获取患者最新的BMI
     * v5.0.0
     * @param memberId
     * @return
     */
    BmiPO getNewBmi(@Param("memberId") String memberId);
}
