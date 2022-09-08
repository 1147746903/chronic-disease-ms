package com.comvee.cdms.hospital.mapper;

import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 医院 - 居委会关联表(HospitalCommittee)表数据库访问层
 *
 * @author lr
 * @since 2022-07-12
 */
public interface HospitalCommitteeMapper {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param hospitalCommitteePO 实例对象
     * @return 对象列表
     */
    List<HospitalCommitteePO> getListByModel(HospitalCommitteePO hospitalCommitteePO);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    HospitalCommitteePO getOneById(String id);


    /**
     * 新增数据
     *
     * @param hospitalCommitteePO 实例对象
     * @return 影响行数
     */
    int insert(HospitalCommitteePO hospitalCommitteePO);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<HospitalCommitteePO> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("list") List<HospitalCommitteePO> list);


    /**
     * 修改数据
     *
     * @param hospitalCommitteePO 实例对象
     * @return 影响行数
     */
    int update(HospitalCommitteePO hospitalCommitteePO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);



}


