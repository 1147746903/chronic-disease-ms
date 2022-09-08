package com.comvee.cdms.dybloodpressure.mapper;



import com.comvee.cdms.dybloodpressure.dto.GetDyBloodPressureDiaryDTO;
import com.comvee.cdms.dybloodpressure.po.DyBloodPressureDiaryPO;
import com.comvee.cdms.dybloodpressure.vo.DyBloodPressureDiaryVO;

import java.util.List;

/**
 * 患者动态血压指标设置(DyBloodPressureDiaryPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-04 10:43:27
 */
public interface DyBloodPressureDiaryMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    DyBloodPressureDiaryPO queryById(String sid);
    
    
    DyBloodPressureDiaryPO queryByMemberId(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO);

    DyBloodPressureDiaryVO queryVOByMemberId(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param dyBloodPressureDiaryPO 实例对象
     * @return 对象列表
     */
    List<DyBloodPressureDiaryPO> queryAll(DyBloodPressureDiaryPO dyBloodPressureDiaryPO);

    /**
     * 新增数据
     *
     * @param dyBloodPressureDiaryPO 实例对象
     * @return 影响行数
     */
    int insert(DyBloodPressureDiaryPO dyBloodPressureDiaryPO);


    /**
     * 修改数据
     *
     * @param dyBloodPressureDiaryPO 实例对象
     * @return 影响行数
     */
    int update(DyBloodPressureDiaryPO dyBloodPressureDiaryPO);

    int realUpdate(DyBloodPressureDiaryPO dyBloodPressureDiaryPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(Long sid);

}

