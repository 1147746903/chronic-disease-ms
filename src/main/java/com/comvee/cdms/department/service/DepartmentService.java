package com.comvee.cdms.department.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.department.model.MemberBedVO;
import com.comvee.cdms.department.model.dto.AddDepartmentDTO;
import com.comvee.cdms.department.model.dto.UpdateDepartmentAndBedDTO;
import com.comvee.cdms.department.model.dto.UpdateDepartmentDTO;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.member.dto.ListMemberDTO;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
public interface DepartmentService {

    /**
     * 添加科室
     * @param addDepartmentDTO
     * @return
     */
    String addDepartment(AddDepartmentDTO addDepartmentDTO);

    /**
     * 加载医院的科室列表
     * @param hospitalId
     * @param pr
     * @return
     */
    PageResult<DepartmentPO> listDeparmentByHospitalId(String hospitalId, PageRequest pr);

    /**
     * 修改科室
     * @param departmentId
     * @param departmentName
     */
    void updateDepartment(UpdateDepartmentDTO updateDepartmentDTO);

    /**
     * 删除科室
     * @param departmentId
     */
    void deleteDepartment(String departmentId);

    /**
     * 加载医院的科室列表
     * @param hospitalId
     * @param pr
     * @return
     */
    List<DepartmentPO> listDepartmentByHospitalId(String hospitalId);

    /**
     * 加载医生可管理的科室列表
     * @param doctorId
     * @return
     */
    List<DepartmentPO> listDoctorManageDepartment(String doctorId);

    /**
     * 修改科室和病床信息
     * @param updateDepartmentAndBedDTO
     */
    void updateDepartmentAndBed(UpdateDepartmentAndBedDTO updateDepartmentAndBedDTO,String bindFlag);

    /**
     * 双击返回床号和设备编号
     * @param sid
     * @return
     */
    MemberBedVO selectDepartmentAndBed(String sid);

    DepartmentPO getDepartmentById(String departmentId);

    /**
     * 添加虚拟病区
     */
    void addVirtualWard(AddDepartmentDTO addDepartmentDTO);

    PageResult listAllDepartmentAndVirtual(String hospitalId, PageRequest pr);

    /**
     * 获取当前登陆医生的虚拟病区
     */
    DepartmentPO getVirtualWardDepartment(ListMemberDTO listMemberDTO);

}
