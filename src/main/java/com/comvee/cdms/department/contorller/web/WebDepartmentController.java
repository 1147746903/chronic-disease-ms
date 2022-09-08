package com.comvee.cdms.department.contorller.web;


import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.department.constant.DepartmentConstant;
import com.comvee.cdms.department.model.MemberBedVO;
import com.comvee.cdms.department.model.dto.AddDepartmentDTO;
import com.comvee.cdms.department.model.dto.UpdateDepartmentAndBedDTO;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.department.service.DepartmentService;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/department")
public class WebDepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 加载医院的科室列表
     * @param hospitalId
     * @param pr
     * @return
     */
    @RequestMapping("listDepartmentByHospitalId")
    public Result listDepartmentByHospitalId(String hospitalId){
        ValidateTool.checkParamIsNull(hospitalId, "hospitalId");
        List<DepartmentPO> list = this.departmentService.listDepartmentByHospitalId(hospitalId);
        return Result.ok(list);
    }

    /**
     * 加载医生可管理的科室列表
     * @return
     */
    @RequestMapping("listDoctorManageDepartment")
    public Result listDoctorManageDepartment(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List result = this.departmentService.listDoctorManageDepartment(doctorSessionBO.getDoctorId());
        return Result.ok(result);
    }

    /**
     * 添加虚拟病区
     * @return
     */
    @RequestMapping("addVirtualWard")
    public Result addVirtualWard(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        AddDepartmentDTO addDepartmentDTO = new AddDepartmentDTO();
        addDepartmentDTO.setHospitalId(doctorSessionBO.getHospitalId());
        addDepartmentDTO.setDepartmentName(DepartmentConstant.KS_DEPARTMENT_NAME);
        addDepartmentDTO.setIsVirtual(2);
        this.departmentService.addVirtualWard(addDepartmentDTO);
        return Result.ok();
    }

    /**
     * 新增科室
     * @param addDepartmentDTO
     * @return
     */
    @RequestMapping("addDepartment")
    public Result addDepartment(@Validated AddDepartmentDTO addDepartmentDTO){
        String departmentId = this.departmentService.addDepartment(addDepartmentDTO);
        return Result.ok(departmentId);
    }

    /**
     * @api {post}/web/department/updateDepartmentAndBedDTO.do 修改科室及病床（之前传参再加上以下两个参数：machineSn和bindFlag）
     * @author wangt
     * @time 2020/10/29 14:00
     * @apName updateDepartmentAndBedDTO 返回饮食记录数据
     * @apiGroup web-update-Bed
     * @apiVersion 0.0.1
     * @apiParam {String} machineSn  住院的主键id（选填）  在床位绑定血糖仪设备的时候传值，可以为空
     * @apiParam {String} bindFlag 住院的主键id（选填，value：02） 在床位绑定血糖仪设备的时候传值（值为：02），可以为空
     * @apiSampleRequest  {post}/web/department/updateDepartmentAndBedDTO.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("updateDepartmentAndBed")
    public Result updateDepartmentAndBed(@Validated UpdateDepartmentAndBedDTO updateDepartmentAndBedDTO,String bindFlag){
        this.departmentService.updateDepartmentAndBed(updateDepartmentAndBedDTO,bindFlag);
        return Result.ok();
    }

    /**
     * @api {post}/web/department/selectDepartmentAndBed.do 双击返回床号和设备号
     * @author wangt
     * @time 2020/10/29 14:00
     * @apName selectDepartmentAndBed 双击返回床号和设备号
     * @apiGroup web-update-Bed
     * @apiVersion 0.0.1
     * @apiParam {String} sid  住院的主键id（必填）
     * @apiSampleRequest  {post}/web/department/selectDepartmentAndBed.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */

    @RequestMapping("selectDepartmentAndBed")
    public Result selectDepartmentAndBed( String sid){
        MemberBedVO memberBedVO =  this.departmentService.selectDepartmentAndBed(sid);
        return Result.ok(memberBedVO);
    }



    /**
     * 删除科室
     * @param departmentId
     * @return
     */
    @RequestMapping("deleteDepartment")
    public Result deleteDepartment(String departmentId){
        ValidateTool.checkParameterIsNull("departmentId" ,departmentId);
        this.departmentService.deleteDepartment(departmentId);
        return Result.ok();
    }
}
