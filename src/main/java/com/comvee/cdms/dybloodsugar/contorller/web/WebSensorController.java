package com.comvee.cdms.dybloodsugar.contorller.web;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.po.DyBloodSugarInformPO;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.dybloodsugar.vo.*;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("web/sensor/")
public class WebSensorController {

    @Autowired
    private DyMemberSensorService dyMemberSensorService;

    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    /**
     * @api {post}/web/sensor/bindSensor.do 绑定传感器
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
     * @api {post}/web/sensor/unBindSensor.do 解绑传感器
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
     * @api {post}/web/sensor/pageSensorBySelf.do 分页获取自己在用的传感器
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
     * @api {post}/web/sensor/pageSensorByShow.do 分页获取分享自己的传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName pageSensorByShow 分页获取分享自己的传感器
     * @apiGroup web-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} memberId 患者编号
     * @apiParam {int} shareType 1:分享给我,2:我分享的
     * @apiParam {int} page 页码
     * @apiParam {int} rows 页数
     * @apiSampleRequest  {post}/web/sensor/pageSensorByShow.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("pageSensorByShow")
    public Result pageSensorByShow(@Validated PagerSensorOfShareDTO dto){
        PageResult<MyShowSensorVO> pageResult = this.dyMemberSensorService.pageSensorByShow(dto);
        return new Result(pageResult);
    }

    /**
     * @api {post}/web/sensor/bindShowSensor.do 分享自己的传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName bindShowSensor 分享自己的传感器
     * @apiGroup web-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} memberSensorSid 患者绑定传感器记录编号
     * @apiSampleRequest  {post}/web/sensor/bindShowSensor.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("bindShowSensor")
    public Result bindShowSensor(@Validated BindShowSensorDTO dto){
        ValidateTool.checkParamIsNull(dto.getMemberId(),"memberId不能为空");
        Result result = new Result();
        if(this.dyMemberSensorService.bindShowSensor(dto)>0){
            result.setMessage("绑定成功");
            result.setCode("0");
        }
        return result;
    }

    /**
     * @api {post}/web/sensor/unBindShowSensor.do 删除分享自己的传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName unBindShowSensor 取消分享自己的传感器
     * @apiGroup web-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} memberSensorSid 患者绑定传感器记录编号
     * @apiSampleRequest  {post}/web/sensor/unBindShowSensor.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("unBindShowSensor")
    public Result unBindShowSensor(@Validated UnBindShowSensorDTO dto){
        ValidateTool.checkParamIsNull(dto.getMemberId(),"memberId不能为空");
        this.dyMemberSensorService.unBindShowSensor(dto);
        return Result.ok("取消成功");
    }

    /**
     * @api {post}/web/sensor/createQrCodeForShareSensor.do 生成分享二维码
     * @author 林雨堆
     * @time 2020/06/16
     * @apiName createQrCodeForShareSensor 生成分享二维码
     * @apiGroup web-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} sensorNo 传感器编号
     * @apiSampleRequest  {post}/web/sensor/createQrCodeForShareSensor.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("createQrCodeForShareSensor")
    public Result createQrCodeForShareSensor(@Validated ShareSensorDTO dto){
        MemberSensorInfoQrCodeVO qrCodeForShareSensor = null;
        try {
            qrCodeForShareSensor = this.dyMemberSensorService.createQrCodeForShareSensor(dto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("生成失败");
        }
        return Result.ok(qrCodeForShareSensor);
    }

    /**
     * 测试添加动态血糖
     * @param json
     * @return
     */
    @RequestMapping("testAddDyBloodSugar")
    @RequiresPermissions({"wz-dtxt-tjcs"})
    public Result testAddDyBloodSugar(String json){
        ValidateTool.checkParameterIsNull("json" ,json);
        this.dyBloodSugarService.bloodSugarHandle(JSON.parseObject(json));
        return Result.ok();
    }

    /**
     * @api {post} /web/sensor/getCountDyBloodSugar.do 获取人数
     * @author 林雨堆
     * @time 2020/06/16
     * @apiName getCountDyBloodSugar 获取人数
     * @apiGroup web-monitor
     * @apiVersion 6.3.0
     * @apiParam {String} authority 虚拟病区权限
     * @apiSampleRequest  {post}/web/sensor/getCountDyBloodSugar.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * @apiSuccessExample {json} Success-Response:
     * {
     * 	"code": "0",
     * 	"message": "成功",
     * 	"obj": {
     * 		"countAbnormityAll": 0, //达标时间占比异常总人数
     * 		"countAbnormityHome": 0, //达标时间占比异常门诊/居家人数
     * 		"countAbnormityHospital": 0, //达标时间占比异常住院人数
     * 		"countMonitorAll": 6, //动态血糖监测总人数
     * 		"countMonitorHome": 5, //动态血糖监测门诊/居家人数
     * 		"countMonitorHospital": 1 //动态血糖监测住院人数
     *        },
     * 	"success": true
     * }
     */
    @RequestMapping("getCountDyBloodSugar")
    public Result getCountDyBloodSugar(Integer virtualWardAuthority){
        ValidateTool.checkParameterIsNull("virtualWardAuthority" ,virtualWardAuthority);
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        DynamicBloodSugarMonitorVO count = this.dyMemberSensorService.getCount(doctorSessionBO.getDoctorId(),doctorSessionBO.getHospitalId(),doctorSessionBO.getDepartId(),virtualWardAuthority);
        return Result.ok(count);
    }

    /**
     * @api {post}/web/sensor/listLatestDyBloodSugarInform.do 获取通知
     * @author 林雨堆
     * @time 2020/06/16
     * @apiName listLatestDyBloodSugarInform 获取通知
     * @apiGroup web-monitor
     * @apiParam {String} latestDt 查询的时间
     * @apiSampleRequest  {post}/web/sensor/listLatestDyBloodSugarInform.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listLatestDyBloodSugarInform")
    public Result listLatestDyBloodSugarInform(String latestDt){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<DyBloodSugarInformPO> list = this.dyMemberSensorService.listLatestDyBloodSugarInform(doctorSessionBO.getDoctorId(),doctorSessionBO.getHospitalId(),doctorSessionBO.getDepartId(),latestDt);
        return Result.ok(list);
    }

}
