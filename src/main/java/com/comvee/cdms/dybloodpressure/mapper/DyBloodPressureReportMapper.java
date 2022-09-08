package com.comvee.cdms.dybloodpressure.mapper;

import com.comvee.cdms.dybloodpressure.dto.GetDyBloodPressureDiaryDTO;
import com.comvee.cdms.dybloodpressure.po.DyBloodPressureReportPO;

import java.util.List;

/**
 * 动态血压报告(DyBloodPressureReportPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-08 16:44:57
 */
public interface DyBloodPressureReportMapper {


    DyBloodPressureReportPO queryByMemberId(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO);

    List<DyBloodPressureReportPO> listByMemberId(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dyBloodPressureReportPO 实例对象
     * @return 对象列表
     */
    List<DyBloodPressureReportPO> queryAll(DyBloodPressureReportPO dyBloodPressureReportPO);

    /**
     * 新增数据
     *
     * @param dyBloodPressureReportPO 实例对象
     * @return 影响行数
     */
    int insert(DyBloodPressureReportPO dyBloodPressureReportPO);


    /**
     * 修改数据
     *
     * @param dyBloodPressureReportPO 实例对象
     * @return 影响行数
     */
    int update(DyBloodPressureReportPO dyBloodPressureReportPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(Long sid);

}

