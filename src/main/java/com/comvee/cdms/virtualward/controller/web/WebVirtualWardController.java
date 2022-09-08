package com.comvee.cdms.virtualward.controller.web;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import com.comvee.cdms.virtualward.model.param.*;
import com.comvee.cdms.virtualward.model.po.VirtualWardPO;
import com.comvee.cdms.virtualward.model.vo.*;
import com.comvee.cdms.virtualward.service.VirtualWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 虚拟病区接口controller
 */
@RestController
@RequestMapping("/web/virtualWard")
public class WebVirtualWardController {

    @Autowired
    private VirtualWardService virtualWardService;

    /**
     * @api {post}/web/virtualWard/listVirtualWardPatient.do 加载虚拟病区患者列表(含历史记录)
     * @author suyz
     * @time 2020/07/23
     * @apiName listVirtualWardPatient 加载虚拟病区患者列表(含历史记录)
     * @apiGroup web:virtualWard
     * @apiParam {String} keyword 搜索关键字
     * @apiParam {String} departmentId 科室id
     * @apiParam {int} transferStatus 转移状态 1 已转入 2 已转出
     * @apiParam {int} useInsulinPumpStatus 带泵状态 1 有 0 没有
     * @apiParam {int} page 页数
     * @apiParam {int} rows 条数
     * @apiParam {int} applyStatus 申请状态 1 未申请转出 2 已申请转出
     * @apiSuccess {struct} obj 根对象
     * @apiSuccess {int} obj.totalRows 总条数
     * @apiSuccess {int} obj.totalPages 总页数
     * @apiSuccess {List} obj.rows 数据列表
     * @apiSuccess {String} obj.rows.sid 主键
     * @apiSuccess {String} obj.rows.memberId 患者id
     * @apiSuccess {String} obj.rows.hospitalNo 住院号
     * @apiSuccess {String} obj.rows.bedNo 床号
     * @apiSuccess {String} obj.rows.departmentId 科室id
     * @apiSuccess {String} obj.rows.departmentName 科室名称
     * @apiSuccess {String} obj.rows.inHospitalDate 入院时间
     * @apiSuccess {String} obj.rows.intoDate 转入时间
     * @apiSuccess {String} obj.rows.outDate 转出时间
     * @apiSuccess {String} obj.rows.hospitalId 医院id
     * @apiSuccess {int} obj.rows.transferStatus 转入状态 1 已转入  2 已转出
     * @apiSuccess {int} obj.rows.applyStatus 申请状态 1 未申请转出 2 已申请转出
     * @apiSuccess {String} obj.rows.useInsulinPumpDate 首次带泵时间
     * @apiSuccess {int} obj.rows.useInsulinPumpStatus 是否带泵 1 带 0 没带
     * @apiSuccess {String} obj.rows.allowIntoDoctorId 允许进入的医生id
     * @apiSuccess {String} obj.rows.allowOutDoctorId 允许转出的医生id
     * @apiSuccess {String} obj.rows.memberName 患者姓名
     * @apiSuccess {int} obj.rows.sex 患者性别
     * @apiSuccess {String} obj.rows.birthday 生日
     * @apiSuccess {String} obj.rows.allowIntoDoctorName 允许转入的医生姓名
     * @apiSuccess {String} obj.rows.bloodSugarValue 血糖值
     * @apiSuccess {int} obj.rows.bloodSugarLevel 血糖等级 1 偏低 3 正常 5 偏高
     * @apiSuccess {String} obj.rows.bloodSugarRecordTime 血糖录入时间
     * @apiSuccess {String} obj.rows.bloodSugarCode 血糖时段code
     * @apiSuccess {String} obj.rows.useMachineInfo 佩戴设备情况 多个用^隔开  1 动态血糖 2 泵
     * @apiSuccess {String} obj.rows.applyIntoDoctorName 申请转入的医生姓名
     */
    @RequestMapping("listVirtualWardPatient")
    public Result listVirtualWardPatient(ListVirtualWardPatientParam list , PageRequest pr){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        list.setHospitalId(doctorSessionBO.getHospitalId());
        list.setDoctorId(doctorSessionBO.getDoctorId());
        list.setDoctorName(doctorSessionBO.getDoctorName());
        PageResult<VirtualWardListVO> result = virtualWardService.listVirtualWardPatient(list, pr);
        return Result.ok(result);
    }


    /**
     * 虚拟病区-历史记录
     * @param list
     * @param pr
     * @return
     */
    @RequestMapping("listVirtualWardPatientRecord")
    public Result listVirtualWardPatientRecord(ListVirtualWardPatientParam list , PageRequest pr){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        list.setHospitalId(doctorSessionBO.getHospitalId());
        list.setDoctorId(doctorSessionBO.getDoctorId());
        list.setHospitalId(doctorSessionBO.getHospitalId());
        PageResult<VirtualWardVO> result = virtualWardService.listVirtualWardPatientRecord(list, pr);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/virtualWard/applyTransferInto.do 转入申请虚拟病区
     * @author suyz
     * @time 2020/07/23
     * @apiName applyTransferInto 转入申请虚拟病区
     * @apiGroup web:virtualWard
     * @apiParam {String} id 住院患者列表主键
     * @apiParam {String} doctorId 当前医生id
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("applyTransferInto")
    public Result applyTransferInto(String id ,String doctorId){
        ValidateTool.checkParameterIsNull("id" ,id);
        ValidateTool.checkParameterIsNull("doctorId" ,doctorId);
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        this.virtualWardService.applyTransferInto(id ,doctorId,doctorSessionBO.getHospitalId());
        return Result.ok();
    }

    /**
     * @api {post}/web/virtualWard/listTransferIntoVirtualWardApply.do 加载转入/转出申请(提醒)
     * @author suyz
     * @time 2020/07/23
     * @apiName listTransferIntoVirtualWardApply 加载转入申请(提醒)
     * @apiGroup web:virtualWard
     * @apiParam {String} moreDate 加载弹窗的时候可以传这个时间，只返回这个时间点之后的数据
     * @apiParam {String} departmentId 科室id
     * @apiParam {String} keyword 搜索关键字
     * @apiParam {int} origin 来源 1  弹窗   2 列表
     * @apiParam {int} page 页数
     * @apiParam {int} rows 条数
     * @apiSuccess {struct} obj 根对象
     * @apiSuccess {int} obj.totalRows 总条数
     * @apiSuccess {int} obj.totalPages 总页数
     * @apiSuccess {List} obj.rows 数据列表
     * @apiSuccess {String} obj.rows.sid 主键
     * @apiSuccess {String} obj.rows.memberId 患者id
     * @apiSuccess {String} obj.rows.hospitalNo 住院号
     * @apiSuccess {String} obj.rows.bedNo 床号
     * @apiSuccess {String} obj.rows.departmentId 科室id
     * @apiSuccess {String} obj.rows.departmentName 科室名称
     * @apiSuccess {String} obj.rows.inHospitalDate 入院时间
     * @apiSuccess {String} obj.rows.applyType 申请类型  1 转入 2 转出
     * @apiSuccess {String} obj.rows.applyStatus 申请状态 1 待处理 2 同意
     * @apiSuccess {String} obj.rows.memberName 患者姓名
     * @apiSuccess {int} obj.rows.sex 患者性别
     * @apiSuccess {String} obj.rows.birthday 生日
     * @apiSuccess {String} obj.rows.applyDoctorName 申请医生姓名
     */
    @RequestMapping("listTransferIntoVirtualWardApply")
    public Result listTransferIntoVirtualWardApply(@Validated QueryTransferIntoApplyListParam param, PageRequest pr){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        param.setHospitalId(doctorSessionBO.getHospitalId());
        param.setDoctorId(doctorSessionBO.getDoctorId());
        param.setDepartId(doctorSessionBO.getDepartId());
        PageResult<TransferApplyListVO> result = this.virtualWardService.listTransferIntoVirtualWardApply(param ,pr);
        return Result.ok(result);
    }

    /**
     * 转出通知(提醒)列表
     * @param param
     * @param pr
     * @return
     */
    @RequestMapping("listTransferOutVirtualWardApply")
    public Result listTransferOutVirtualWardApply(@Validated QueryTransferIntoApplyListParam param, PageRequest pr){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        param.setHospitalId(doctorSessionBO.getHospitalId());
        param.setDoctorId(doctorSessionBO.getDoctorId());
        param.setDepartId(doctorSessionBO.getDepartId());
        PageResult<TransferApplyListVO> result = this.virtualWardService.listTransferOutVirtualWardApply(param ,pr);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/virtualWard/allowTransferInto.do 允许转入
     * @author suyz
     * @time 2020/07/23
     * @apiName allowTransferInto 转入申请虚拟病区
     * @apiGroup web:virtualWard
     * @apiParam {String} id 申请记录主键
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("allowTransferInto")
    public Result allowTransferInto(String id){
        ValidateTool.checkParameterIsNull("id" ,id);
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        this.virtualWardService.allowTransferInto(id ,doctorSessionBO.getDoctorId(),doctorSessionBO.getHospitalId());
        return Result.ok();
    }

    /**
     * @api {post}/web/virtualWard/delayTransferIntoApplyRemind.do 推迟转入申请提醒
     * @author suyz
     * @time 2020/07/23
     * @apiName delayTransferIntoApplyRemind 推迟转入申请提醒
     * @apiGroup web:virtualWard
     * @apiParam {String} id 申请记录主键
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("delayTransferIntoApplyRemind")
    public Result delayTransferIntoApplyRemind(String id){
        ValidateTool.checkParameterIsNull("id" ,id);
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        this.virtualWardService.delayTransferIntoApplyRemind(id ,doctorSessionBO.getDoctorId());
        return Result.ok();
    }

    /**
     * @api {post}/web/virtualWard/getTransferApplyDetail.do 获取申请详情
     * @author suyz
     * @time 2020/07/23
     * @apiName getTransferApplyDetail 获取申请详情
     * @apiGroup web:virtualWard
     * @apiParam {String} memberId 住院患者列表主键[必传]
     * @apiParam {String} departmentId 科室id
     * @apiParam {String} applyType 申请类型 1 转入 2 转出
     * @apiParam {String} id 申请记录主键
     * @apiParam {String} applyStatus 状态 1 未处理  2 已处理
     * @apiSuccess {struct} obj 根对象
     * @apiSuccess {String} obj.sid 主键
     * @apiSuccess {String} obj.memberId 患者id
     * @apiSuccess {String} obj.bedNo 床号
     * @apiSuccess {String} obj.hospitalNo 住院号
     * @apiSuccess {String} obj.departmentId 科室id
     * @apiSuccess {String} obj.departmentName 科室名称
     * @apiSuccess {String} obj.inHospitalDate 入院日期
     * @apiSuccess {String} obj.applyText 申请说明
     * @apiSuccess {int} obj.applyStatus 申请状态
     * @apiSuccess {String} obj.memberName 姓名
     * @apiSuccess {int} obj.sex 性别
     * @apiSuccess {String} obj.birthday 生日
     * @apiSuccess {String} obj.applyDoctorName 申请医生姓名
     */
    @RequestMapping("getTransferApplyDetail")
    public Result getTransferApplyDetail(@Validated GetVirtualWardTransferApplyParam param){
        TransferApplyDetailVO vo = this.virtualWardService.getTransferApplyDetail(param);
        return Result.ok(vo);
    }

    /**
     * @api {post}/web/virtualWard/applyTransferOut.do 申请转出
     * @author suyz
     * @time 2020/07/23
     * @apiName applyTransferOut 申请转出
     * @apiGroup web:virtualWard
     * @apiParam {String} id 虚拟病区列表主键
     * @apiParam {String} applyText 申请说明
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("applyTransferOut")
    public Result applyTransferOut(String id ,String applyText){
        ValidateTool.checkParameterIsNull("id" ,id);
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.virtualWardService.applyTransferOut(id ,applyText ,doctor.getDoctorId());
        return Result.ok();
    }

    /**
     * @api {post}/web/virtualWard/allowTransferOut.do 允许转出
     * @author suyz
     * @time 2020/07/23
     * @apiName allowTransferOut 允许转出
     * @apiGroup web:virtualWard
     * @apiParam {String} id 申请记录主键
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("allowTransferOut")
    public Result allowTransferOut(String id){
        ValidateTool.checkParameterIsNull("id", id);
        this.virtualWardService.allowTransferOut(id, SessionTool.getWebSession().getDoctorId(), SessionTool.getWebSession().getDepartId());
        return Result.ok();
    }


    /**
     * @api {post}/web/virtualWard/listInHospitalPatientForVirtualWard.do 加载住院列表（虚拟病区内嵌页使用）
     * @author suyz
     * @time 2020/07/23
     * @apiName listInHospitalPatientForVirtualWard 加载住院列表（虚拟病区内嵌页使用）
     * @apiGroup web:virtualWard
     * @apiParam {String} keyword 搜索关键字
     * @apiParam {String} departmentId 科室id [必传]
     * @apiParam {int} applyStatus 申请状态 1 未申请 2 已申请转出
     * @apiParam {int} page 页数
     * @apiParam {int} rows 条数
     * @apiSuccess {struct} obj 根对象
     * @apiSuccess {int} obj.totalRows 总条数
     * @apiSuccess {int} obj.totalPages 总页数
     * @apiSuccess {List} obj.rows 数据列表
     * @apiSuccess {String} obj.rows.sid 住院记录主键
     * @apiSuccess {String} obj.rows.memberId 患者id
     * @apiSuccess {String} obj.rows.hospitalNo 住院号
     * @apiSuccess {String} obj.rows.bedNo 床号
     * @apiSuccess {String} obj.rows.departmentId 科室id
     * @apiSuccess {String} obj.rows.departmentName 科室名称
     * @apiSuccess {String} obj.rows.inHospitalDate 入院日期
     * @apiSuccess {String} obj.rows.memberName 姓名
     * @apiSuccess {int} obj.rows.sex 性别
     * @apiSuccess {String} obj.rows.birthday 生日
     * @apiSuccess {int} obj.rows.applyStatus 申请转出状态 1 未申请 2 已申请
     * @apiSuccess {String} obj.rows.bloodSugarValue 血糖值
     * @apiSuccess {int} obj.rows.bloodSugarLevel 血糖等级
     * @apiSuccess {String} obj.rows.bloodSugarRecordTime 记录时间
     * @apiSuccess {String} obj.rows.bloodSugarCode 血糖时段code
     */
    @RequestMapping("/listInHospitalPatientForVirtualWard")
    public Result listInHospitalPatientForVirtualWard(@Validated QueryInHospitalPatientListParam param, PageRequest pr) {
        DoctorSessionBO doctor = SessionTool.getWebSession();
        param.setHospitalId(doctor.getHospitalId());
        param.setDepartmentId(doctor.getDepartId());
        param.setDoctorName(doctor.getDoctorName());
        param.setDoctorId(doctor.getDoctorId());
        PageResult<InHospitalPatientListVO> result = this.virtualWardService.listInHospitalPatientForVirtualWard(param, pr);
        return Result.ok(result);
    }


    /**
     * @api {post}/web/virtualWard/listVirtualWardDepartment.do 加载虚拟病区所有科室列表
     * @author suyz
     * @time 2020/07/23
     * @apiName listVirtualWardDepartment 加载虚拟病区所有科室列表
     * @apiGroup web:virtualWard
     * @apiSuccess {List} obj 根对象
     * @apiSuccess {String} obj.departmentId 科室id
     * @apiSuccess {String} obj.departmentName 科室名称
     */
    @RequestMapping("/listVirtualWardDepartment")
    public Result listVirtualWardDepartment(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List result = this.virtualWardService.listVirtualWardDepartment(doctorSessionBO.getHospitalId());
        return Result.ok(result);
    }

    /**
     * @api {post}/web/virtualWard/getCurrentVirtualWard.do 获取患者目前有效的虚拟病区信息
     * @author suyz
     * @time 2020/07/23
     * @apiName getCurrentVirtualWard 获取患者目前有效的虚拟病区信息
     * @apiGroup web:virtualWard
     * @apiParam {String} memberId 患者id
     * @apiSuccess {struct} obj 根对象
     * @apiSuccess {String} obj.sid 虚拟病区主键
     */
    @RequestMapping("/getCurrentVirtualWard")
    public Result getCurrentVirtualWard(String memberId){
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        VirtualWardPO result = this.virtualWardService.getCurrentVirtualWard(memberId ,doctorSessionBO.getHospitalId());
        return Result.ok(result);
    }


    /**
     * @api {post}/web/virtualWard/listHistoryVirtualWardWithNurseRecord.do 加载有护理记录的虚拟病区历史记录
     * @author suyz
     * @time 2020/07/23
     * @apiName listHistoryVirtualWardWithNurseRecord 加载有护理记录的虚拟病区历史记录
     * @apiGroup web:virtualWard
     * @apiParam {String} keyword 搜索关键字
     * @apiSuccess {struct} obj 根对象
     * @apiSuccess {int} obj.totalRows 总条数
     * @apiSuccess {int} obj.totalPages 总页数
     * @apiSuccess {List} obj.rows 数据列表
     * @apiSuccess {String} obj.rows.sid 主键
     * @apiSuccess {String} obj.rows.memberId 患者id
     * @apiSuccess {String} obj.rows.hospitalNo 住院号
     * @apiSuccess {String} obj.rows.bedNo 床号
     * @apiSuccess {String} obj.rows.departmentId 科室id
     * @apiSuccess {String} obj.rows.departmentName 科室名称
     * @apiSuccess {String} obj.rows.inHospitalDate 入院时间
     * @apiSuccess {String} obj.rows.intoDate 转入时间
     * @apiSuccess {String} obj.rows.outDate 转出时间
     * @apiSuccess {String} obj.rows.hospitalId 医院id
     * @apiSuccess {int} obj.rows.transferStatus 转入状态 1 已转入  2 已转出
     * @apiSuccess {int} obj.rows.applyStatus 申请状态 1 未申请转出 2 已申请转出
     * @apiSuccess {String} obj.rows.useInsulinPumpDate 首次带泵时间
     * @apiSuccess {int} obj.rows.useInsulinPumpStatus 是否带泵 1 带 0 没带
     * @apiSuccess {String} obj.rows.allowIntoDoctorId 允许进入的医生id
     * @apiSuccess {String} obj.rows.allowOutDoctorId 允许转出的医生id
     * @apiSuccess {String} obj.rows.memberName 患者姓名
     * @apiSuccess {int} obj.rows.sex 患者性别
     * @apiSuccess {String} obj.rows.birthday 生日
     */
    @RequestMapping("listHistoryVirtualWardWithNurseRecord")
    public Result listHistoryVirtualWardWithNurseRecord(PageRequest pr ,ListHistoryVirtualWardWithNurseRecordParam param){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        param.setHospitalId(doctorSessionBO.getHospitalId());
        PageResult result = this.virtualWardService.listHistoryVirtualWardWithNurseRecord(pr ,param);
        return Result.ok(result);
    }
}
