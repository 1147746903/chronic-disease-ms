package com.comvee.cdms.dybloodsugar.contorller.back;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.dto.DyBloodManagementDTO;
import com.comvee.cdms.dybloodsugar.po.DyBloodManagementPO;
import com.comvee.cdms.dybloodsugar.service.DyBloodManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("back/dyblood/management/")
public class DynamicBloodManagementController {

    @Autowired
    private DyBloodManagementService dyBloodManagementService;

    /**
     * @api {post}back/dyblood/management/addHospitalNameAndEquipmentNo.do 保存设备出库
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName addHospitalNameAndEquipmentNo 保存设备出库
     * @apiGroup back-dy-equipment
     * @apiVersion 0.0.1
     * @apiParam {String} sid  主键id 编辑保存时必填
     * @apiParam {String} hospitalId  医院id（必填）
     * @apiParam {String} hospitalName  医院名称（必填）
     * @apiParam {String} equipmentNo  设备号（必填）
     * @apiParam {String} outBoundDt  出库日期 yyyy-MM-dd（必填）
     * @apiParam {String} lastUseDt  最新使用日期yyyy-MM-dd（必填）
     * @apiParam {String} remark  备注（选填）
     * @apiSampleRequest  {post}back/dyblood/management/addHospitalNameAndEquipmentNo.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("addHospitalNameAndEquipmentNo")
    public Result addHospitalNameAndEquipmentNo(DyBloodManagementDTO dto){
        return Result.ok(this.dyBloodManagementService.addHospitalNameAndEquipmentNo(dto));
    }


    /**
     * @api {post}back/dyblood/management/listHospitalNameAndEquipmentNo.do 根据医院名称和IMEI号查询全部医院(模糊搜索)
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName listHospitalNameAndEquipmentNo 根据医院名称和IMEI号查询全部医院(模糊搜索)
     * @apiGroup back-dy-equipment
     * @apiVersion 0.0.1
     * @apiParam {String} hospitalName  医院名称（必填）
     * @apiParam {String} equipmentNo  设备号（必填）
     * @apiSampleRequest  {post}back/dyblood/management/listHospitalNameAndEquipmentNo.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("listHospitalNameAndEquipmentNo")
    public Result listHospitalNameAndEquipmentNo(PageRequest pr , DyBloodManagementDTO dto){
        PageResult<DyBloodManagementPO> dyBloodManagementPOS =  this.dyBloodManagementService.listHospitalNameAndEquipmentNo(pr,dto);
        return Result.ok(dyBloodManagementPOS);
    }


    /**
     * @api {post}back/dyblood/management/getManagementBySid.do 编辑返回绑定数据
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName getManagementBySid 编辑返回绑定数据
     * @apiGroup back-dy-equipment
     * @apiVersion 0.0.1
     * @apiParam {String} sid  主键id
     * @apiSampleRequest  {post}back/dyblood/management/getManagementBySid.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("getManagementBySid")
    public Result getManagementBySid(String sid){
        DyBloodManagementPO dyBloodManagementPO = this.dyBloodManagementService.
                getManagementBySid(sid);
        return Result.ok(dyBloodManagementPO);
    }

    /**
     * @api {post}back/dyblood/management/deleteManagementBySid.do 删除绑定
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName deleteManagementBySid 删除绑定
     * @apiGroup back-dy-equipment
     * @apiVersion 0.0.1
     * @apiParam {String} sid  主键id
     * @apiSampleRequest  {post}back/dyblood/management/deleteManagementBySid.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("deleteManagementBySid")
    public Result deleteManagementBySid(String sid){
        this.dyBloodManagementService.deleteManagementBySid(sid);
        return Result.ok("");
    }


    /**
     * @api {post}back/dyblood/management/listManagementHospital.do 获取全部医院
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName listManagementHospital 删除绑定
     * @apiGroup back-dy-equipment
     * @apiVersion 0.0.1
     * @apiSampleRequest  {post}back/dyblood/management/listManagementHospital.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("listManagementHospital")
    public Result listManagementHospital(){
        List<DyBloodManagementPO> list = this.dyBloodManagementService.listHospital();
        return Result.ok(list);
    }

}
