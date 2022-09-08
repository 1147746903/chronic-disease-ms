package com.comvee.cdms.complication.controller.back;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.complication.model.dto.AddScreeningKnowledgeDTO;
import com.comvee.cdms.complication.model.dto.ScreeningMachineDTO;
import com.comvee.cdms.complication.model.po.ScreeningKnowledgePO;
import com.comvee.cdms.complication.model.vo.ScreeningMachineVO;
import com.comvee.cdms.complication.service.ScreeningKnowledgeService;
import com.comvee.cdms.complication.service.ScreeningMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2019/4/1
 */
@RestController
@RequestMapping("/back/screening")
//@RequiresRoles("ADMIN")
public class BackScreeningController {

    @Autowired
    private ScreeningKnowledgeService screeningKnowledgeService;
    @Autowired
    private ScreeningMachineService screeningMachineService;

    /**
     * 加载筛查知识列表
     * @param pr
     * @param keyword
     * @return
     */
    @RequestMapping("/listScreeningKnowledge")
    public Result listScreeningKnowledge(PageRequest pr ,String keyword){
        PageResult pageResult = this.screeningKnowledgeService.listScreeningKnowledge(pr ,keyword);
        return Result.ok(pageResult);
    }

    /**
     * 根据id获取筛查知识
     * @param sid
     * @return
     */
    @RequestMapping("/getScreeningKnowledgeById")
    public Result getScreeningKnowledgeById(String sid){
        ValidateTool.checkParamIsNull(sid ,"sid");
        ScreeningKnowledgePO screeningKnowledgePO = this.screeningKnowledgeService.getScreeningKnowledge(sid ,null);
        return Result.ok(screeningKnowledgePO);
    }

    /**
     * 新增筛查知识
     * @param AddScreeningKnowledgeDTO
     * @return
     */
    @RequestMapping("/addScreeningKnowledge")
    public Result addScreeningKnowledge(@Validated AddScreeningKnowledgeDTO AddScreeningKnowledgeDTO){
        String sid = this.screeningKnowledgeService.addScreeningKnowledge(AddScreeningKnowledgeDTO);
        return Result.ok(sid);
    }

    /**
     * 修改筛查知识
     * @param screeningKnowledgePO
     * @return
     */
    @RequestMapping("/updateScreeningKnowLedge")
    public Result updateScreeningKnowLedge(ScreeningKnowledgePO screeningKnowledgePO){
        ValidateTool.checkParamIsNull(screeningKnowledgePO.getSid() , "sid");
        this.screeningKnowledgeService.updateScreeningKnowledge(screeningKnowledgePO);
        return Result.ok();
    }


    /**
     * @api {post}/back/screening/addHospitalAndMachineNo.do 保存或修改授权码管理
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName addHospitalAndMachineNo 添加或修改授权码管理
     * @apiGroup back-machine
     * @apiVersion 0.0.1
     * @apiParam {String} hospitalId  医院id（必填）
     * @apiParam {String} doctorId 医生id（必填）
     * @apiParam {String} hospitalName  医院名称（必填）
     * @apiParam {String} doctorName  医生名称（必填）
     * @apiParam {String} machineSn  设备号（必填）
     * @apiParam {String} description  备注（选填）
     * @apiParam {String} sid  备注（选填）修改的时候传sid
     * @apiSampleRequest  {post}/back/screening/addHospitalAndMachineNo.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("/addHospitalAndMachineNo")
    public Result addHospitalAndMachineNo(ScreeningMachineDTO dto,String sid) {
        this.screeningMachineService.addScreeningMachine(dto,sid);
        return Result.ok("");
    }

    /**
     * @api {post}/back/screening/listHospitalAndMachineNo.do 根据医院名称和医生和机器码查询全部医院(模糊搜索)
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName listHospitalAndMachineNo 根据医院名称和医生和机器码查询全部医院(模糊搜索)
     * @apiGroup back-machine
     * @apiVersion 0.0.1
     * @apiParam {String} hospitalName  医院名称（选填）搜索条件
     * @apiParam {String} doctorName  医生名称（选填）搜索条件
     * @apiParam {String} machineSn  设备号（选填）搜索条件
     * @apiParam {int} page 页码
     * @apiParam {int} rows 页数
     * @apiSampleRequest  {post}/back/screening/listHospitalAndMachineNo.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("/listHospitalAndMachineNo")
    public Result listHospitalAndMachineNo(PageRequest pr , ScreeningMachineDTO dto){
        PageResult<ScreeningMachineVO> dyBloodManagementPOS =  this.screeningMachineService.listHospitalNameOrDoctorIdOrEquipmentNo(pr,dto);
        return Result.ok(dyBloodManagementPOS);
    }
}
