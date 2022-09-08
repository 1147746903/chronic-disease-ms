package com.comvee.cdms.hospital.service.impl;

import com.comvee.cdms.doctor.mapper.DoctorCommitteeRelationMapper;
import com.comvee.cdms.doctor.po.DoctorCommitteeRelationPO;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.mapper.HospitalCommitteeMapper;
import com.comvee.cdms.hospital.service.CommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医院 - 居委会关联表(HospitalCommittee)表服务接口实现
 *
 * @author lr
 * @since 2022-07-12
 */
@Service("hospitalCommitteeService")
public class CommitteeServiceImpl implements CommitteeService {

    @Autowired
    private HospitalCommitteeMapper hospitalCommitteeMapper;

    @Autowired
    private DoctorCommitteeRelationMapper doctorCommitteeRelationMapper;

    @Override
    public List<HospitalCommitteePO> listHospitalCommittee(HospitalCommitteePO hospitalCommitteePO) {
        return this.hospitalCommitteeMapper.getListByModel(hospitalCommitteePO);
    }

    @Override
    public List<HospitalCommitteePO> listCommitteeByDoctorId(String doctorId) {
        return doctorCommitteeRelationMapper.getHospitalCommitteeByDoctorId(doctorId);
    }

    @Override
    public Boolean insertHospitalCommittee(HospitalCommitteePO hospitalCommitteePO) {
        return this.hospitalCommitteeMapper.insert(hospitalCommitteePO) > 0;
    }

    @Override
    public void insertBatchDoctorRelation(List<DoctorCommitteeRelationPO> list) {
        this.doctorCommitteeRelationMapper.insertBatch(list);
    }


    @Override
    public Boolean update(HospitalCommitteePO hospitalCommitteePO) {
        return this.hospitalCommitteeMapper.update(hospitalCommitteePO) > 0;
    }


    @Override
    public boolean deleteRelationByDoctorId(String doctorId) {
        return this.doctorCommitteeRelationMapper.deleteByDoctorId(doctorId) > 0;
    }
}

