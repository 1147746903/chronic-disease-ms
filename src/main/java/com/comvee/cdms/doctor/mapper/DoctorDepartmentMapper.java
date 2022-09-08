/*
*
* @author wyc
* @date 2019-12-08
*/
package com.comvee.cdms.doctor.mapper;


import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.doctor.po.DoctorDepartmentPO;
import com.comvee.cdms.doctor.vo.DoctorDepartmentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DoctorDepartmentMapper {

    /**
     * 添加用户部门管理
     */
    void addDoctorDepartment(DoctorDepartmentPO doctorDepartmentPO);

    /**
     * 删除医生部门管理
     * @param doctorId
     */
    void deleteDoctorDepartmentByDoctorId(@Param("doctorId") String doctorId);

    /** v 6.0.0
     * 加载医生的科室权限
     * @param doctorId
     * @return
     */
    List<DoctorDepartmentVO> listDoctorDepartment(@Param("doctorId") String doctorId);

    /** v6.0.0
     * 根据医院id加载医生有账号的科室
     * @param hospitalId
     * @return
     */
    List<DepartmentPO> listDoctorDepartHasAccountByHosId(@Param("hospitalId") String hospitalId);
}