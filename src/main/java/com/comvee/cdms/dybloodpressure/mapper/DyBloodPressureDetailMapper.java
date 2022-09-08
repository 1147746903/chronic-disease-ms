package com.comvee.cdms.dybloodpressure.mapper;

import com.comvee.cdms.dybloodpressure.dto.GetDyBloodPressureDiaryDTO;
import com.comvee.cdms.dybloodpressure.po.DyBloodPressureDetailPO;
import com.comvee.cdms.dybloodpressure.po.DyBloodPressurePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 动态血压拓展表(TDynamicBpDetail)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-04 10:36:43
 */
public interface DyBloodPressureDetailMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    DyBloodPressureDetailPO queryById(Long sid);




    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDynamicBpDetail 实例对象
     * @return 对象列表
     */
    List<DyBloodPressureDetailPO> queryAll(DyBloodPressureDetailPO tDynamicBpDetail);


    List<DyBloodPressureDetailPO> queryDateList(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO);

    /**
     * 新增数据
     *
     * @param tDynamicBpDetail 实例对象
     * @return 影响行数
     */
    int insert(DyBloodPressureDetailPO tDynamicBpDetail);


    int updateBatch(@Param("list") List<DyBloodPressureDetailPO> list);

    /**
     * 修改数据
     *
     * @param tDynamicBpDetail 实例对象
     * @return 影响行数
     */
    int update(DyBloodPressureDetailPO tDynamicBpDetail);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(Long sid);

}

