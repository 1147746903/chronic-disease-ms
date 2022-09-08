package com.comvee.cdms.dybloodsugar.contorller.wechat;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.dybloodsugar.vo.MemberSensorInfoQrCodeVO;
import com.comvee.cdms.dybloodsugar.vo.MySensorVO;
import com.comvee.cdms.dybloodsugar.vo.MyShowSensorVO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wechat/sensor/")
public class WechatSensorContorller {

    @Autowired
    private DyMemberSensorService dyMemberSensorService;

    /**
     * @api {post}/wechat/sensor/bindSensor.do 绑定传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName bindSensor 绑定传感器
     * @apiGroup wechat-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} sensorNo 传感器编号
     * @apiSampleRequest  {post}/wechat/sensor/bindSensor.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("bindSensor")
    public Result bindSensor(@Validated BindSensorDTO dto){
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        dto.setOperationId(member.getMemberId());
        dto.setOperationType((byte)1);
        Result result = new Result();
        if(this.dyMemberSensorService.bindSensor(dto)>0){
            result.setMessage("绑定成功");
            result.setCode("0");
        }
        return result;
    }

    /**
     * @api {post}/wechat/sensor/unBindSensor.do 解绑传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName unBindSensor 解绑传感器
     * @apiGroup wechat-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} sensorNo 传感器编号
     * @apiParam {String} sid 主键id
     * @apiSampleRequest  {post}/wechat/sensor/unBindSensor.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("unBindSensor")
    public Result unBindSensor(UnBindSensorDTO dto){
        ValidateTool.checkParamIsNull(dto.getSensorNo(),"sensorNo");
        ValidateTool.checkParamIsNull(dto.getSid(),"sid");
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        Result result = new Result();
        if(this.dyMemberSensorService.unBindSensor(dto)>0){
            result.setMessage("解绑成功");
            result.setCode("0");
        }
        return result;
    }

    /**
     * @api {post}/wechat/sensor/pageSensorBySelf.do 分页获取自己在用的传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName pageSensorBySelf 分页获取自己在用的传感器
     * @apiGroup wechat-sensor
     * @apiVersion 6.1.2
     * @apiParam {int} page 页码
     * @apiParam {int} rows 页数
     * @apiSampleRequest  {post}/wechat/sensor/pageSensorBySelf.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("pageSensorBySelf")
    public Result pageSensorBySelf(PagerSensorDTO dto){
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        dto.setOrigin(2);
        PageResult<MySensorVO> pageResult = this.dyMemberSensorService.pageSensorBySelf(dto);
        return new Result(pageResult);
    }

    /**
     * @api {post}/wechat/sensor/pageSensorByShow.do 分页获取分享自己的传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName pageSensorByShow 分页获取分享自己的传感器
     * @apiGroup wechat-sensor
     * @apiVersion 6.1.2
     * @apiParam {int} page 页码
     * @apiParam {int} rows 页数
     * @apiParam {int} shareType 查询类型（shareType）不可为空(1:分享给我,2:我分享的)
     * @apiSampleRequest  {post}/wechat/sensor/pageSensorByShow.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("pageSensorByShow")
    public Result pageSensorByShow(PagerSensorOfShareDTO dto){
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        PageResult<MyShowSensorVO> pageResult = this.dyMemberSensorService.pageSensorByShow(dto);
        return new Result(pageResult);
    }

    /**
     * @api {post}/wechat/sensor/bindShowSensor.do 绑定分享自己的传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName bindShowSensor 绑定分享自己的传感器
     * @apiGroup wechat-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} memberSensorSid 患者绑定传感器记录编号
     * @apiSampleRequest  {post}/wechat/sensor/bindShowSensor.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("bindShowSensor")
    public Result bindShowSensor(@Validated BindShowSensorDTO dto){
        Result result = new Result();
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        int count = this.dyMemberSensorService.bindShowSensor(dto);
        if(count == 0){
            result.setCode("2");
            result.setMessage("请不要与自己建立分享关系");
            result.setSuccess(false);
        }else if(count == 1){
            result.setCode("1");
            result.setMessage("请勿重复绑定");
            result.setSuccess(false);
        }else{
            result.setCode("0");
            result.setMessage("绑定成功");
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * @api {post}/wechat/sensor/unBindShowSensor.do 解绑分享自己的传感器
     * @author 林雨堆
     * @time 2020/06/11
     * @apiName unBindShowSensor 解绑分享自己的传感器
     * @apiGroup wechat-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} memberSensorSid 患者绑定传感器记录编号
     * @apiSampleRequest  {post}/wechat/sensor/unBindShowSensor.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("unBindShowSensor")
    public Result unBindShowSensor(@Validated UnBindShowSensorDTO dto){
        MemberPO member = SessionTool.getWechatSession();
        dto.setMemberId(member.getMemberId());
        this.dyMemberSensorService.unBindShowSensor(dto);
        return Result.ok("取消成功");
    }

    /**
     * @api {post}/wechat/sensor/createQrCodeForShareSensor.do 生成分享二维码
     * @author 林雨堆
     * @time 2020/06/16
     * @apiName createQrCodeForShareSensor 生成分享二维码
     * @apiGroup web-sensor
     * @apiVersion 6.1.2
     * @apiParam {String} sensorNo 传感器编号
     * @apiSampleRequest  {post}/wechat/sensor/unBindShowSensor.do
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
     *判断探头是否过期
     * wechat/sensor/hasExpired
     * @param sensorNo
     * @return
     */
    @RequestMapping("/hasExpired")
    public Result hasExpired(String sensorNo){
        return Result.ok(dyMemberSensorService.hasExpired(sensorNo));
    }
}
