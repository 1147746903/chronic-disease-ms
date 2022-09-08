package com.comvee.cdms.department.mapper;

import com.comvee.cdms.department.model.dto.UpdateDepartmentDTO;
import com.comvee.cdms.department.model.po.DepartmentPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
public interface DepartmentMapper {

    /**
     * 新增可是
     * @param departmentPO
     */
    void addDepartment(DepartmentPO departmentPO);

    /**
     * 加载医院的科室列表
     * @param hospitalId
     * @return
     */
    List<DepartmentPO> listDepartment(@Param("hospitalId") String hospitalId);

    /**
     * 加载医院的科室列表(包含虚拟病区)
     * @param hospitalId
     * @return
     */
    List<DepartmentPO> listAllDepartmentAndVirtual(@Param("hospitalId") String hospitalId);
    /**
     * 加载医生对应的科室列表
     * @param hospitalId
     * @return
     */
    List<DepartmentPO> listDepartmentByDoctorId(@Param("doctorId") String doctorId);
    /**
     * 修改科室
     * @param departmentPO
     */
    void updateDepartment(UpdateDepartmentDTO updateDepartmentDTO);

    /**
     * 删除科室
     * @param departmentId
     */
    void deleteDepartment(String departmentId);

    /**
     * 根据id加载科室列表
     * @param idList
     * @return
     */
    List<DepartmentPO> listDepartmentById(@Param("idList") List<String> idList);
    /**
     * 根据id加载科室
     * @return
     */
    DepartmentPO listDepartmentByDepartId(@Param("departmentId") String departmentId);


    /**
     * 根据医院id和科室名称获取科室信息
     * @param po
     */
    DepartmentPO getDepartmentByHospitalId(@Param("departmentName")String departmentName,@Param("hospitalId") String hospitalId);

    /**
     * 获取全部科室信息
     * @return
     */
    List<DepartmentPO> listAllDepartment();

    /**
     * 根据医院id和科室名称获取虚拟病区科室信息
     */
    DepartmentPO getDepartmentByIsVirtual(@Param("hospitalId") String hospitalId,@Param("isVirtual") Integer isVirtual);

}
