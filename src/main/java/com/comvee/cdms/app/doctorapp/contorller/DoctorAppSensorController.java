package com.comvee.cdms.app.doctorapp.contorller;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodsugar.dto.BindSensorDTO;
import com.comvee.cdms.dybloodsugar.dto.ListMemberSensorDTO;
import com.comvee.cdms.dybloodsugar.dto.PagerSensorDTO;
import com.comvee.cdms.dybloodsugar.dto.UnBindSensorDTO;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.dybloodsugar.vo.DySensorStasticVO;
import com.comvee.cdms.dybloodsugar.vo.MemberSensorVO;
import com.comvee.cdms.dybloodsugar.vo.MySensorVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyc
 * @date 2020/6/17 16:08
 */
@RestController
@RequestMapping("/docapp/sensor")
public class DoctorAppSensorController {

    @Autowired
    private DyMemberSensorService dyMemberSensorService;

    /**
     * 获取动态血糖情况
     * @return
     */
    @RequestMapping("/getSensorStatistics")
    public Result getSensorStatistics(@Validated ListMemberSensorDTO dto){
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        dto.setDoctorId(doctorModel.getDoctorId());
        dto.setHospitalId(doctorModel.getHospitalId());
        DySensorStasticVO statistics = this.dyMemberSensorService.getSensorStatistics(dto);
        return Result.ok(statistics);
    }

    /**
     * 加载动态血糖患者列表
     * @return
     */
    @RequestMapping("/listMemberSensor")
    public Result listMemberSensor(@Validated ListMemberSensorDTO listMemberSensorDTO, PageRequest pageRequest){
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
//        listMemberSensorDTO.setDoctorId(doctorModel.getDoctorId());
        listMemberSensorDTO.setHospitalId(doctorModel.getHospitalId());
        PageResult<MemberSensorVO> pageResult = this.dyMemberSensorService.listMemberSensor(listMemberSensorDTO, pageRequest);
        return Result.ok(pageResult);
    }


    /**
     * @api {post}/docapp/sensor/bindSensor.do 绑定传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName bindSensor 绑定传感器
     * @apiGroup web-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} memberId 患者编号
     * @apiParam {String} sensorNo 传感器编号
     * @apiSampleRequest  {post}/web/sensor/bindSensor.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("bindSensor")
    public Result bindSensor(@Validated BindSensorDTO dto){
        Result result = new Result();
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setOperationId(doctorSessionBO.getDoctorId());
        dto.setOperationType((byte)2);
        if(this.dyMemberSensorService.bindSensor(dto)>0){
            result.setMessage("绑定成功");
            result.setCode("0");
        }
        return result;
    }

    /**
     * @api {post}/docapp/sensor/unBindSensor.do 解绑传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName unBindSensor 解绑传感器
     * @apiGroup web-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} memberId 患者编号
     * @apiParam {String} sensorNo 传感器编号
     * @apiParam {String} sid 主键id
     * @apiSampleRequest  {post}/web/sensor/unBindSensor.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("unBindSensor")
    public Result unBindSensor(@Validated UnBindSensorDTO dto){
        Result result = new Result();
        if(this.dyMemberSensorService.unBindSensor(dto)>0){
            result.setMessage("解绑成功");
            result.setCode("0");
        }
        return result;
    }

    /**
     * @api {post}/docapp/sensor/pageSensorBySelf.do 分页获取自己在用的传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName pageSensorBySelf 分页获取自己在用的传感器
     * @apiGroup web-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} memberId 患者编号
     * @apiParam {int} page 页码
     * @apiParam {int} rows 页数
     * @apiSampleRequest  {post}/web/sensor/pageSensorBySelf.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("pageSensorBySelf")
    public Result pageSensorBySelf(@Validated PagerSensorDTO dto){
        dto.setOrigin(1);
        PageResult<MySensorVO> pageResult = this.dyMemberSensorService.pageSensorBySelf(dto);
        return new Result(pageResult);
    }

    /**
     * 判断探头绑定状态
     * @param sensorNo
     * @return
     */
    @RequestMapping("checkSensorBindStatus")
    public Result checkSensorBindStatus(String sensorNo){
        ValidateTool.checkParameterIsNull("sensorNo" ,sensorNo);
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        JSONObject jsonObject = this.dyMemberSensorService.checkSensorBindStatus(sensorNo ,doctorSessionBO.getHospitalId());
        return Result.ok(jsonObject);
    }

    /**
     * 判断探头和患者的绑定状态
     * @param sensorNo
     * @param memberId
     * @return
     */
    @RequestMapping("checkSensorAndMemberBindStatus")
    public Result checkSensorAndMemberBindStatus(String sensorNo ,String memberId){
        ValidateTool.checkParameterIsNull("sensorNo" ,sensorNo);
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        JSONObject jsonObject = this.dyMemberSensorService.checkSensorAndMemberBindStatus(sensorNo ,memberId);
        return Result.ok(jsonObject);
    }

    /**
     * 修改绑定
     * @param sensorNo
     * @param memberId
     * @return
     */
    @RequestMapping("changeSensorBind")
    public Result changeSensorBind(@Validated BindSensorDTO bindSensorDTO){
        DoctorSessionBO doctorPO = SessionTool.getWebSession();
        bindSensorDTO.setOperationId(doctorPO.getDoctorId());
        bindSensorDTO.setOperationType((byte) 2);
        this.dyMemberSensorService.changeSensorBind(bindSensorDTO);
        return Result.ok();
    }
}
