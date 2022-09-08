package com.comvee.cdms.dybloodpressure.mapper;

import com.comvee.cdms.dybloodpressure.bo.DyBloodPressureDataBO;
import com.comvee.cdms.dybloodpressure.dto.GetDynBloodPressureDataDTO;
import com.comvee.cdms.dybloodpressure.po.DyBloodPressureDetailPO;
import com.comvee.cdms.dybloodpressure.po.DyBloodPressurePO;
import com.comvee.cdms.dybloodpressure.vo.ListDayDyBloodPressureVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 动态血压表(TDynamicBloodPressure)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-26 10:08:54
 */
public interface DyBloodPressureMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    DyBloodPressurePO queryById(Long sid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DyBloodPressurePO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDynamicBloodPressure 实例对象
     * @return 对象列表
     */
    List<DyBloodPressurePO> queryAll(DyBloodPressurePO tDynamicBloodPressure);

    DyBloodPressurePO queryOne(DyBloodPressureDetailPO getDetailPO);



    List<ListDayDyBloodPressureVO> listDyBloodPressureVO(GetDynBloodPressureDataDTO getDynBloodPressureDataDTO);

    /**
     * 获取患者指定时间范围内的动态血压列表
     * @param getDynBloodPressureDataDTO
     * @return
     */
    List<DyBloodPressureDataBO> listDyBloodPressureByMemberDay(GetDynBloodPressureDataDTO getDynBloodPressureDataDTO);

    /**
     * 新增数据
     *
     * @param tDynamicBloodPressure 实例对象
     * @return 影响行数
     */
    int insert(DyBloodPressurePO dyBloodPressurePO);


    /**
     * 修改数据
     *
     * @param tDynamicBloodPressure 实例对象
     * @return 影响行数
     */
    int update(DyBloodPressurePO tDynamicBloodPressure);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(Long sid);

}

