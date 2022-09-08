package com.comvee.cdms.hospital.service;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.comvee.cdms.doctor.po.DoctorCommitteeRelationPO;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;

import java.util.List;

/**
 *
 *
 * @author lr
 * @since 2022-07-12
 */
public interface CommitteeService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<HospitalCommitteePO> listHospitalCommittee(HospitalCommitteePO hospitalCommitteePO);

    List<HospitalCommitteePO> listCommitteeByDoctorId(String doctorId);

    /**
     * 新增数据
     *
     * @param hospitalCommitteePO 实例对象
     * @return boolean
     */
    Boolean insertHospitalCommittee(HospitalCommitteePO hospitalCommitteePO);

    void insertBatchDoctorRelation(List<DoctorCommitteeRelationPO> list);

    /**
     * 修改数据
     *
     * @param hospitalCommitteePO 实例对象
     * @return boolean
     */
    Boolean update(HospitalCommitteePO hospitalCommitteePO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteRelationByDoctorId(String doctorId);

}

