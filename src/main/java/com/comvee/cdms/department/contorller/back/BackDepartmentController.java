package com.comvee.cdms.department.contorller.back;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.department.model.dto.AddDepartmentDTO;
import com.comvee.cdms.department.model.dto.UpdateDepartmentDTO;
import com.comvee.cdms.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
@RestController
@RequestMapping("/back/department")
//@RequiresRoles("ADMIN")
public class BackDepartmentController {

    @Autowired
    private DepartmentService departmentService;


    /**
     * 添加科室
     * @param addDepartmentDTO
     * @return
     */
    @RequestMapping("addDepartment")
    public Result addDepartment(@Validated AddDepartmentDTO addDepartmentDTO){
        String departmentId = this.departmentService.addDepartment(addDepartmentDTO);
        return Result.ok(departmentId);
    }

    /**
     * 加载医院所有的科室列表(包含默认添加的虚拟病区科室)
     * @param hospitalId
     * @param pr
     * @return
     */
    @RequestMapping("listDepartmentByHospital")
    public Result listDepartmentByHospital(String hospitalId, PageRequest pr){
        ValidateTool.checkParamIsNull(hospitalId, "hospitalId");
        PageResult pageResult = this.departmentService.listAllDepartmentAndVirtual(hospitalId, pr);
        return Result.ok(pageResult);
    }

    @RequestMapping("listDepartment")
    public Result listDepartment(String hospitalId, PageRequest pr){
        PageResult pageResult = this.departmentService.listDeparmentByHospitalId(hospitalId, pr);
        return Result.ok(pageResult);
    }

    /**
     * 修改科室
     * @param departmentId
     * @param departmentName
     * @return
     */
    @RequestMapping("updateDepartment")
    public Result updateDepartment(String departmentId, String departmentName){
        ValidateTool.checkParamIsNull(departmentId, "departmentId");
        ValidateTool.checkParamIsNull(departmentName, "departmentName");
        UpdateDepartmentDTO updateDepartmentDTO = new UpdateDepartmentDTO();
        updateDepartmentDTO.setDepartmentId(departmentId);
        updateDepartmentDTO.setDepartmentName(departmentName);
        this.departmentService.updateDepartment(updateDepartmentDTO);
        return Result.ok();
    }

    /**
     * 删除科室
     * @param departmentId
     * @return
     */
    @RequestMapping("deleteDepartment")
    public Result deleteDepartment(String departmentId){
        ValidateTool.checkParamIsNull(departmentId, "departmentId");
        this.departmentService.deleteDepartment(departmentId);
        return Result.ok();
    }
}
