package com.comvee.cdms.complication.mapper;

import com.comvee.cdms.complication.model.dto.ListScreeningDataDTO;
import com.comvee.cdms.complication.model.po.ScreeningDataPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/4/1
 */
public interface ScreeningDataMapper {

    /**
     * 新增筛查数据
     * @param screeningDataPO
     */
    void addScreeningData(ScreeningDataPO screeningDataPO);

    /**
     * 加载筛查数据列表
     * @param listScreeningDataParam
     * @return
     */
    List<ScreeningDataPO> listScreeningData(ListScreeningDataDTO listScreeningDataDTO);

    /**
     * 删除患者筛查数据
     * @param memberId
     * @param doctorId
     */
    void deleteScreeningData(@Param("memberId") String memberId , @Param("doctorId") String doctorId);


    /**
     * 根据id获取筛查统计数据
     * @param screeningId
     * @return
     */
    ScreeningDataPO getScreeningDataById(String screeningId);



    /**
     * 修改筛查统计数据
     * @param screeningDataPO
     */
    void updateScreeningData(ScreeningDataPO screeningDataPO);
}
