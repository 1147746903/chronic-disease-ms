package com.comvee.cdms.dybloodsugar.contorller.web;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.po.DyRememberDietPO;
import com.comvee.cdms.dybloodsugar.po.DyRememberPharmacyPO;
import com.comvee.cdms.dybloodsugar.po.DyRememberSleepPO;
import com.comvee.cdms.dybloodsugar.po.DyRememberSportPO;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyRememberService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/dy/bloodSugarRemember")
public class WebDyBloodSugarRememberController {


    @Autowired
    private DyRememberService dyRememberService;
    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    /**
     * @api {post}/web/dy/bloodSugarRemember/setDietRemember.do 点击保存,保存饮食的记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName setDietRemember 点击保存,保存饮食的记录
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} paramCode  餐时（必填）
     * @apiParam {String} paramDt  用餐时间（必填）
     * @apiParam {String} paramIngredientJson  食材(包括其他食材) (必填)
     * @apiParam {Integer} operationType  1:患者  2:医生 (必填)
     * @apiParam {String} recordDt (yyyy-MM-dd HH:mm:ss) 记录日期（必填）
     * @apiParam {String} recordDtStart (2020-07-23 00:00:00) 记录日期（必填）
     * @apiParam {String} recordDtEnd (2020-07-23 23:59:59) 记录日期 必填）
     * @apiParam {String} sid 饮食记录主键id (选填）在点击列表修改时传sid,其他操作不需要传.
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/setDietRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * @apiSuccessExample {json} Success-Response:
     * {
     * 	"dietFoodList": [{
     * 	    "multipleFoodId": "", //多选食材id
     * 		"multipleFood": "", //多选食材
     *        }],
     * 	"otherFood": "12" //其他食材
     * }
     */
    @RequestMapping("/setDietRemember")
    public Result setDietRemember(DyRememberDietDTO dto,String sid){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setOperationType(DynamicBloodSugarConstant.ORIGIN_DOCTOR);
        dto.setOperationId(doctorSessionBO.getDoctorId());
        this.dyRememberService.setDietRemember(dto,sid);
        return Result.ok("");
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/listDietRemember.do 获取患者的饮食列表
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName listDietRemember 获取患者的饮食列表
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} recordDtStart  开始记录时间（必填）
     * @apiParam {String} recordDtEnd  结束记录时间（必填）
     * @apiParam {Integer} operationType  数据来源 1:医生  0:患者（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/listDietRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * @apiSuccessExample {json} Success-Response:
     * {
     * 	"dietFoodList": [{
     * 	    "multipleFoodId": "", //多选食材id
     * 		"multipleFood": "", //多选食材
     *        }],
     * 	"otherFood": "12" //其他食材
     * }
     */
    @RequestMapping("/listDietRemember")
    public Result listDietRemember(@Validated DyRememberDietDTO dyRememberDietDTO){
        List<DyRememberDietPO> dyRememberDietPOList = this.dyRememberService.getDyDietRememberPOList(dyRememberDietDTO);
        return Result.ok(dyRememberDietPOList);
    }


    /**
     * @api {post}/web/dy/bloodSugarRemember/getDietRemember.do 返回饮食记录数据
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName getDietRemember 返回饮食记录数据
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  饮食记录的主键id（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/getDietRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/getDietRemember")
    public Result getDietRemember(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        DyRememberDietPO dyRememberDietPO = this.dyRememberService.getDyDietRememberValues(sid);
        return  Result.ok(dyRememberDietPO);
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/updateDyDietRemember.do 删除饮食记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName updateDyDietRemember 删除饮食记录
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  饮食记录的主键id（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/updateDyDietRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/updateDyDietRemember")
    public Result updateDyDietRemember(String sid, String origin){
        ValidateTool.checkParameterIsNull("sid" ,sid);

        this.dyRememberService.updateDyDietRemember(sid);

        return Result.ok("");
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/setSportRemember.do 添加运动记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName setSportRemember 添加运动记录
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} sportStartDt  开始运动时间（必填）
     * @apiParam {String} sportEndDt  结束运动时间（必填）
     * @apiParam {String} sportMethodJson  运动方式（必填）
     * @apiParam {Integer} operationType  1:患者  2:医生 (必填)
     * @apiParam {String} recordDt (yyyy-MM-dd HH:mm:ss) 记录日期（必填）
     * @apiParam {String} recordDtStart (2020-07-23 00:00:00) 记录日期（必填）
     * @apiParam {String} recordDtEnd (2020-07-23 23:59:59) 记录日期 (必填）
     * @apiParam {String} sid 运动记录主键id (选填）在点击列表修改时传sid,其他操作不需要传.
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/setSportRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/setSportRemember")
    public Result setSportRemember(DyRememberSportDTO dto,String sid){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setOperationType(DynamicBloodSugarConstant.ORIGIN_DOCTOR);
        dto.setOperationId(doctorSessionBO.getDoctorId());
        this.dyRememberService.setSportRemember(dto,sid);
        return Result.ok("");
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/listSportRemember.do 获取医生记录的患者运动列表
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName listSportRemember 获取医生记录的患者运动列表
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} recordDtStart  开始记录时间（必填）
     * @apiParam {String} recordDtEnd  结束记录时间（必填）
     * @apiParam {Integer} operationType  数据来源 1:医生  0:患者（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/listSportRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/listSportRemember")
    public Result listSportRemember(@Validated DyRememberSportDTO dyRememberSportDTO){
        List<DyRememberSportPO> dyRememberSportPOList = this.dyRememberService.getDySportRememberPOList(dyRememberSportDTO);
        return Result.ok(dyRememberSportPOList);
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/getSportRememberBySid.do 处理运动记录数据回填
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName getSportRememberBySid 处理运动记录数据回填
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  运动记录的主键id（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/getSportRememberBySid.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/getSportRememberBySid")
    public Result getSportRememberBySid(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        DyRememberSportPO dyRememberSportPO = this.dyRememberService.getDySportRememberValues(sid);
        return Result.ok(dyRememberSportPO);
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/deleteSportRemember.do 删除运动记录数据回填
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName deleteSportRemember 删除运动记录数据回填
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  运动记录的主键id（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/deleteSportRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/deleteSportRemember")
    public Result deleteSportRemember(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        this.dyRememberService.deleteSportRemember(sid);
        return Result.ok("");
    }

    /**
     * 添加用药记录
     * @return
     */
    /**
     * @api {post}/web/dy/bloodSugarRemember/setPharmacyRemember.do 添加用药记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName setPharmacyRemember 添加用药记录
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} pharmacyDt  用药时间（必填）
     * @apiParam {String} pharmacyDetailsJson  药名和剂量（必填）
     * @apiParam {String} recordDt  记录日期 yyyy-MM-dd HH:mm:ss（必填）
     * @apiParam {Integer} operationType  1:患者  2:医生 (必填)
     * @apiParam {String} recordDtStart (2020-07-23 00:00:00) 记录日期（必填）
     * @apiParam {String} recordDtEnd (2020-07-23 23:59:59) 记录日期 (必填）
     * @apiParam {String} sid 用药记录主键id (选填）在点击列表修改时传sid,其他操作不需要传.
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/setPharmacyRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/setPharmacyRemember")
    public Result setPharmacyRemember(DyRememberPharmacyDTO dto,String sid){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setOperationType(DynamicBloodSugarConstant.ORIGIN_DOCTOR);
        dto.setOperationId(doctorSessionBO.getDoctorId());
        this.dyRememberService.setPharmacyRemember(dto,sid);
        return Result.ok("");
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/listPharmacyRemember.do 获取医生记录患者的用药列表
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName listPharmacyRemember 获取医生记录患者的用药列表
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} recordDtStart  开始记录时间（必填）
     * @apiParam {String} recordDtEnd  结束记录时间（必填）
     * @apiParam {Integer} operationType  数据来源 1:医生  0:患者（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/listPharmacyRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/listPharmacyRemember")
    public Result listPharmacyRemember(@Validated DyRememberPharmacyDTO dyRememberPharmacyDTO){
        List<DyRememberPharmacyPO> dyRememberPharmacyPOList = this.dyRememberService.getDyPharmacyRememberPOList(dyRememberPharmacyDTO);
        return Result.ok(dyRememberPharmacyPOList);
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/getPharmacyRememberBySid.do 处理用药记录数据回填
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName getPharmacyRememberBySid 处理用药记录数据回填
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  用药记录的主键id（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/getPharmacyRememberBySid.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/getPharmacyRememberBySid")
    public Result getPharmacyRememberBySid(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        DyRememberPharmacyPO dyRememberPharmacyPO = this.dyRememberService.getDyPharmacyRememberValues(sid);
        return Result.ok(dyRememberPharmacyPO);
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/deletePharmacyRemember.do 删除用药记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName deletePharmacyRemember 删除用药记录
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  用药记录的主键id（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/deletePharmacyRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/deletePharmacyRemember")
    public Result deletePharmacyRemember(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        this.dyRememberService.deletePharmacyRemember(sid);
        return Result.ok("");
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/setSleepRemember.do 添加睡眠记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName setSleepRemember 添加运动记录
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} sleepDt  睡眠时间（必填）
     * @apiParam {String} sleepRemark  睡眠备注（选填）
     * @apiParam {String} recordDt  记录日期 yyyy-MM-dd HH:mm:ss（必填）
     * @apiParam {Integer} operationType  1:患者  2:医生 (必填)
     * @apiParam {String} recordDtStart (2020-07-23 00:00:00) 记录日期（必填）
     * @apiParam {String} recordDtEnd (2020-07-23 23:59:59) 记录日期 (必填）
     * @apiParam {String} sid 睡眠记录主键id (选填）在点击列表修改时传sid,其他操作不需要传.
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/setSleepRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/setSleepRemember")
    public Result setSleepRemember(DyRememberSleepDTO dto){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setOperationType(DynamicBloodSugarConstant.ORIGIN_DOCTOR);
        dto.setOperationId(doctorSessionBO.getDoctorId());
        this.dyRememberService.setSleepRemember(dto,1);
        return Result.ok("");
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/listSleepRemember.do 获取睡眠记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName listSleepRemember 获取睡眠记录
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} memberId  患者id（必填）
     * @apiParam {String} recordDtStart  开始记录时间（必填）
     * @apiParam {String} recordDtEnd  结束记录时间（必填）
     * @apiParam {Integer} operationType  数据来源 1:医生  0:患者（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/listSleepRemember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/listSleepRemember")
    public Result listSleepRemember(@Validated DyRememberSleepDTO dyRememberSleepDTO){
        List<DyRememberSleepPO> dyRememberSleepPO = this.dyRememberService.getSleepRemember(dyRememberSleepDTO);
        return Result.ok(dyRememberSleepPO);
    }

    /**
     * @api {post}/web/dy/bloodSugarRemember/addBloodSugarRememberRemark.do 添加血糖备注信息
     * @author 林雨堆
     * @time 2019/04/19 17:00
     * @apName 添加血糖备注信息
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {GlucometerRequest} mr 默认参数
     * @apiParam {String} bid  血糖记录编号（必填）
     * @apiParam {String} content  备注内容（必填）
     * @apiParam {String} sid  点击列表的做修改的时候传值,其他操作不传值
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 OK
     * {
     *     "obj":null,
     *     "code":"0",
     *     "msg":"添加成功",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"登录超时，请重新登录",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"系统错误",
     *     "success":false
     * }
     */
    @RequestMapping("addBloodSugarRememberRemark")
    public Result addBloodSugarRememberRemark(@Validated DyBloodSugarRemarkDTO dto,String sid){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_DOCTOR);
        dto.setOperatorId(doctorSessionBO.getDoctorId());
        return Result.ok(this.dyBloodSugarService.addBloodSugarRememberRemark(dto,sid));
    }



    /**
     * @api {post}/web/dy/bloodSugarRemember/deleteBloodSugarRemark.do 删除备注记录
     * @author 林雨堆
     * @time 2020/09/16 14:00
     * @apName deleteBloodSugarRemark 删除备注记录
     * @apiGroup web-dy-remember
     * @apiVersion 0.0.1
     * @apiParam {String} sid  备注信息id（必填）
     * @apiSampleRequest  {post}/web/dy/bloodSugarRemember/deleteBloodSugarRemark.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("deleteBloodSugarRemark")
    public Result deleteBloodSugarRemark(String sid){
        this.dyRememberService.deleteBloodSugarRemark(sid);
        return Result.ok("");
    }


}
