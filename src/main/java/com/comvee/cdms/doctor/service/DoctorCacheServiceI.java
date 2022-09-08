package com.comvee.cdms.doctor.service;

import com.comvee.cdms.department.model.dto.AddDepartmentDTO;
import com.comvee.cdms.department.model.dto.UpdateDepartmentDTO;
import com.comvee.cdms.doctor.dto.UpdateDoctorDTO;
import com.comvee.cdms.doctor.po.DoctorDepartmentPO;
import com.comvee.cdms.doctor.po.DoctorGroupPO;
import com.comvee.cdms.doctor.po.DoctorPO;

import java.util.List;

public interface DoctorCacheServiceI {

    DoctorPO getDoctorById(String doctorId);

    List<DoctorPO> listMyDoctor(String doctorId);

    List<String> listTeamId(String doctorId);

    void addDoctorRelation(String doctorId, String foreignId);

    void deleteDoctorRelation(String doctorId, String foreignId);

    void updateDoctor(UpdateDoctorDTO updateDoctorDTO);

    List<DoctorPO> listMyTeamDoctor(String doctorId);

    List<DoctorGroupPO> listDepartmentByDid(String doctorId);

    void addDoctorDepartment(DoctorDepartmentPO departmentPO);

    List<DoctorGroupPO> listDepartmentByHid(String hospitalId, String doctorId);

    String addDepartment(AddDepartmentDTO addDepartmentDTO);

    void updateDepartment(UpdateDepartmentDTO updateDepartmentDTO);

    void deleteDepartment(String departmentId);

    void deleteDoctorDepartmentByDoctorId(String doctorId);
}
