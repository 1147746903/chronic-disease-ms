package com.comvee.cdms.bloodmonitor.controller.web;

import com.comvee.cdms.bloodmonitor.dto.MemberMonitorPlanRecordDTO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanRecordService;
import com.comvee.cdms.bloodmonitor.vo.MemberMonitorPlanVO;
import com.comvee.cdms.bloodmonitor.vo.PrescriptionMonitorPlanVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author 李左河
 *
 */
@RestController
@RequestMapping("/web/monitor/record/")
public class MemberMonitorPlanRecordController {

    @Autowired
    private MemberMonitorPlanRecordService memberMonitorPlanRecordService;

    /**
     * @api {post}/web/monitor/record/listMonitorRecord.do 获取患者的监测方案记录列表
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName createLicense 获取患者的监测方案记录列表
     * @apiGroup web-record-plan
     * @apiParam {String} memberId  患者id（必填）
     * @apiSampleRequest  {post}/web/monitor/record/listMonitorRecord.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("listMonitorRecord")
    public Result listMonitorRecord(PageRequest page, MemberMonitorPlanRecordDTO dto){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setDoctorName(doctorSessionBO.getDoctorName());
        dto.setDepartName(doctorSessionBO.getDepartName());
        dto.setHospitalId(doctorSessionBO.getHospitalId());
        PageResult<MemberMonitorPlanVO> memberMonitorPlanPOS = this.memberMonitorPlanRecordService.listMonitorRecord(page,dto);
        return Result.ok(memberMonitorPlanPOS);
    }

    /**
     * @api {post}/web/monitor/record/lookMonitorRecord.do 获取患者的监测方案(查看)
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName createLicense 获取患者的监测方案
     * @apiGroup web-record-plan
     * @apiParam {String} planId 方案主键
     * @apiParam {String} operationType 方案来源  1和2:血糖管理->监测方案 3:管理处方->监测方案
     * @apiSampleRequest  {post}/web/monitor/record/lookMonitorRecord.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("lookMonitorRecord")
    public Result lookMonitorRecord(String planId,Integer operationType) {
        PrescriptionMonitorPlanVO planVO = this.memberMonitorPlanRecordService.lookMonitorRecord(planId,operationType);
        return Result.ok(planVO);
    }

    /**
     * @api {post}/web/monitor/record/stopMonitorRecord.do 停止监测方案
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName createLicense 停止监测方案
     * @apiGroup web-record-plan
     * @apiParam {String} planId 方案主键
     * @apiParam {String} operationType 方案来源   1和2(:血糖管理->监测方案) 3:管理处方->监测方案
     * @apiSampleRequest  {post}/web/monitor/record/stopMonitorRecord.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("stopMonitorRecord")
    public Result stopMonitorRecord(String planId,Integer operationType) {
        this.memberMonitorPlanRecordService.stopMonitorRecord(planId,operationType);
        return Result.ok("");
    }

    /**
     * @api {post}/web/monitor/record/deleteMonitorRecord.do 删除监测方案(暂时没用)
     * @author wangt
     * @time 2020/10/22 14:00
     * @apName createLicense 删除监测方案
     * @apiGroup web-record-plan
     * @apiParam {String} planId 方案主键
     * @apiParam {String} operationType 方案来源  1和2:血糖管理->监测方案 3:管理处方->监测方案
     * @apiSampleRequest  {post}/web/monitor/record/deleteMonitorRecord.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * */
    @RequestMapping("deleteMonitorRecord")
    public Result deleteMonitorRecord(String planId,Integer operationType) {
        this.memberMonitorPlanRecordService.deleteMonitorRecord(planId,operationType);
        return Result.ok("");
    }


}
