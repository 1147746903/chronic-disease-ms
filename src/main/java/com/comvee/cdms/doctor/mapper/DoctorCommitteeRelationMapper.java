package com.comvee.cdms.doctor.mapper;

import com.comvee.cdms.doctor.po.DoctorCommitteeRelationPO;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 医生村所关联表(DoctorCommitteeRelation)表数据库访问层
 *
 * @author lr
 * @since 2022-07-29
 */
public interface DoctorCommitteeRelationMapper {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param doctorCommitteeRelationPO 实例对象
     * @return 对象列表
     */
    List<DoctorCommitteeRelationPO> getListByModel(DoctorCommitteeRelationPO doctorCommitteeRelationPO);

    List<HospitalCommitteePO> getHospitalCommitteeByDoctorId(String doctorId);

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    DoctorCommitteeRelationPO getOneById(String sid);


    /**
     * 新增数据
     *
     * @param doctorCommitteeRelationPO 实例对象
     * @return 影响行数
     */
    int insert(DoctorCommitteeRelationPO doctorCommitteeRelationPO);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<DoctorCommitteeRelationPO> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("list") List<DoctorCommitteeRelationPO> list);


    /**
     * 修改数据
     *
     * @param doctorCommitteeRelationPO 实例对象
     * @return 影响行数
     */
    int update(DoctorCommitteeRelationPO doctorCommitteeRelationPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

    int deleteByDoctorId(String doctorId);

}


