package com.comvee.cdms.consultation.controller.web;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.consultation.model.param.AddRemoteConsultationParam;
import com.comvee.cdms.consultation.model.param.FinishRemoteConsultationParam;
import com.comvee.cdms.consultation.model.param.ListCurrentRemoteConsultationParam;
import com.comvee.cdms.consultation.model.param.ListHistoryRemoteConsultationParam;
import com.comvee.cdms.consultation.model.po.RemoteConsultationPO;
import com.comvee.cdms.consultation.model.vo.RemoteConsultationVO;
import com.comvee.cdms.consultation.service.RemoteConsultationService;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/consultation/remote")
public class WebRemoteConsultationController {

    @Autowired
    private RemoteConsultationService remoteConsultationService;

    /**
     * @api {post}/web/consultation/remote/addRemoteConsultation.do 新增远程会诊申请
     * @author suyz
     * @time 2020/12/28
     * @apiName addRemoteConsultation 新增远程会诊申请
     * @apiGroup web:remoteConsultation
     * @apiParam {String} memberId 患者id
     * @apiParam {String} consultationWay 会诊方式  1 向上 2 向下
     * @apiParam {String} fromContact 发起人联系方式
     * @apiParam {String} toHospitalId 接收医院id
     * @apiParam {String} toDepartId 接收科室id
     * @apiParam {String} consultationReason 会诊原因
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/addRemoteConsultation")
    public Result addRemoteConsultation(@Validated AddRemoteConsultationParam param){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        String result = this.remoteConsultationService.addRemoteConsultation(addRemoteConsultationParamHandle(param ,doctor));
        return Result.ok(result);
    }

    /**
     * @api {post}/web/consultation/remote/getRemoteConsultation.do 获取远程会诊详情
     * @author suyz
     * @time 2020/12/28
     * @apiName getRemoteConsultation 获取远程会诊详情
     * @apiGroup web:remoteConsultation
     * @apiParam {String} sid 主键
     * @apiSuccess {struct} obj 根对象
     * @apiSuccess {String} obj.sid 根对象
     * @apiSuccess {String} obj.memberId 患者id
     * @apiSuccess {String} obj.consultationWay 会诊方式  1 向上 2 向下
     * @apiSuccess {String} obj.fromHospitalId 发起医院id
     * @apiSuccess {String} obj.fromDepartId 发起科室id
     * @apiSuccess {String} obj.fromDoctorId 发起医生id
     * @apiSuccess {String} obj.fromContact 发起人联系方式
     * @apiSuccess {String} obj.toHospitalId 接收医院id
     * @apiSuccess {String} obj.toDepartId 接收科室id
     * @apiSuccess {String} obj.toDoctorId 接收医生id
     * @apiSuccess {String} obj.insertDt 申请时间
     * @apiSuccess {String} obj.consultationStatus 会诊状态 1  未开始 2 会诊中  3 已结束 4 过期
     * @apiSuccess {String} obj.fromUnreadNumber 发起方未读消息数
     * @apiSuccess {String} obj.toUnreadNumber 接收方未读消息数
     * @apiSuccess {String} obj.consultationReason 会诊原因
     * @apiSuccess {String} obj.consultationResult 会诊结果
     * @apiSuccess {String} obj.confirmDt 确认接收时间
     * @apiSuccess {String} obj.finishDt 会诊完成时间
     * @apiSuccess {String} obj.finishOperatorId 会诊完成操作者id
     * @apiSuccess {String} obj.fromRemindStatus 发起方 提醒状态 1 需要提醒  0 不需要提醒
     * @apiSuccess {String} obj.fromRemindDt 发起方 提醒时间
     * @apiSuccess {String} obj.toRemindStatus 接收方 提醒状态 1 需要提醒  0 不需要提醒
     * @apiSuccess {String} obj.toRemindDt 接收方 提醒时间
     * @apiSuccess {String} obj.fromHospitalName 发起医院名称
     * @apiSuccess {String} obj.fromDepartName 发起科室名称
     * @apiSuccess {String} obj.fromDoctorName 发起医生名称
     * @apiSuccess {String} obj.toHospitalName 接收医院名称
     * @apiSuccess {String} obj.toDepartName 接收科室名称
     * @apiSuccess {String} obj.toDoctorName 接收医生名称
     * @apiSuccess {String} obj.memberName 患者姓名
     * @apiSuccess {String} obj.sex 性别
     * @apiSuccess {String} obj.birthday 生日
     */
    @RequestMapping("/getRemoteConsultation")
    public Result getRemoteConsultation(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        RemoteConsultationVO result = this.remoteConsultationService.getRemoteConsultationFullInfo(sid);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/consultation/remote/listCurrentRemoteConsultation.do 加载当前正在进行的会诊列表（分页）
     * @author suyz
     * @time 2020/12/28
     * @apiName listCurrentRemoteConsultation  加载当前正在进行的会诊列表（分页）
     * @apiGroup web:remoteConsultation
     * @apiParam {String} searchMemberName 患者搜索关键字
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/listCurrentRemoteConsultation")
    public Result listCurrentRemoteConsultation(ListCurrentRemoteConsultationParam param , PageRequest pr){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        param.setDepartId(doctor.getDepartId());
        PageResult result = this.remoteConsultationService.listCurrentRemoteConsultation(param ,pr);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/consultation/remote/finishRemoteConsultation.do 完成会诊
     * @author suyz
     * @time 2020/12/28
     * @apiName finishRemoteConsultation  完成会诊
     * @apiGroup web:remoteConsultation
     * @apiParam {String} consultationId 会诊记录主键（sid）
     * @apiParam {String} consultationResult 会诊结果
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/finishRemoteConsultation")
    public Result finishRemoteConsultation(@Validated FinishRemoteConsultationParam param){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        param.setDoctorId(doctor.getDoctorId());
        this.remoteConsultationService.finishRemoteConsultation(param);
        return Result.ok();
    }

    /**
     * @api {post}/web/consultation/remote/listApplyRemoteConsultation.do 加载申请列表（分页）
     * @author suyz
     * @time 2020/12/28
     * @apiName listApplyRemoteConsultation 加载申请列表（分页）
     * @apiGroup web:remoteConsultation
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/listApplyRemoteConsultation")
    public Result listApplyRemoteConsultation(PageRequest pr){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        PageResult result = this.remoteConsultationService.listApplyRemoteConsultation(doctor.getDepartId() ,pr);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/consultation/remote/listHistoryRemoteConsultation.do 加载历史
     * @author suyz
     * @time 2020/12/28
     * @apiName listHistoryRemoteConsultation 加载历史（分页）
     * @apiGroup web:remoteConsultation
     * @apiParam {String} consultationStatus 会诊状态
     * @apiParam {String} searchFromHospitalName 发起方医院名称
     * @apiParam {String} searchToHospitalName 接收方医院名
     * @apiParam {String} searchMemberName 患者姓名
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/listHistoryRemoteConsultation")
    public Result listHistoryRemoteConsultation(ListHistoryRemoteConsultationParam param ,PageRequest pr){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        param.setDepartId(doctor.getDepartId());
        PageResult result = this.remoteConsultationService.listHistoryRemoteConsultation(param ,pr);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/consultation/remote/confirmRemoteConsultation.do 确认申请
     * @author suyz
     * @time 2020/12/28
     * @apiName confirmRemoteConsultation 确认申请
     * @apiGroup web:remoteConsultation
     * @apiParam {String} consultationId 会诊记录id
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/confirmRemoteConsultation")
    public Result confirmRemoteConsultation(String consultationId){
        ValidateTool.checkParameterIsNull("consultationId" ,consultationId);
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.remoteConsultationService.confirmRemoteConsultation(consultationId ,doctor.getDoctorId());
        return Result.ok();
    }

    /**
     * @api {post}/web/consultation/remote/getApplyRemindNumber.do 获取申请提醒数量
     * @author suyz
     * @time 2020/12/28
     * @apiName getApplyRemindNumber 获取申请提醒数量
     * @apiGroup web:remoteConsultation
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/getApplyRemindNumber")
    public Result getApplyRemindNumber(){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        Long result = this.remoteConsultationService.getApplyRemindNumber(doctor.getDepartId());
        return Result.ok(result);
    }

    /**
     * @api {post}/web/consultation/remote/getFinishRemindNumber.do 获取完成提醒数量
     * @author suyz
     * @time 2020/12/28
     * @apiName getFinishRemindNumber 获取完成提醒数量
     * @apiGroup web:remoteConsultation
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/getFinishRemindNumber")
    public Result getFinishRemindNumber(){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        Long result = this.remoteConsultationService.getFinishRemindNumber(doctor.getDepartId());
        return Result.ok(result);
    }

    /**
     * @api {post}/web/consultation/remote/delayApplyRemind.do 推迟申请提醒
     * @author suyz
     * @time 2020/12/28
     * @apiName delayApplyRemind 推迟申请提醒
     * @apiGroup web:remoteConsultation
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/delayApplyRemind")
    public Result delayApplyRemind(){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.remoteConsultationService.delayApplyRemind(doctor.getDepartId());
        return Result.ok();
    }

    /**
     * @api {post}/web/consultation/remote/readApplyRemind.do 阅读申请提醒（点击处理时调用）
     * @author suyz
     * @time 2020/12/28
     * @apiName readApplyRemind 阅读申请提醒（点击处理时调用）
     * @apiGroup web:remoteConsultation
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/readApplyRemind")
    public Result readApplyRemind(){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.remoteConsultationService.readApplyRemind(doctor.getDepartId());
        return Result.ok();
    }


    /**
     * @api {post}/web/consultation/remote/delayFinishRemind.do 推迟完成提醒
     * @author suyz
     * @time 2020/12/28
     * @apiName delayFinishRemind 推迟完成提醒
     * @apiGroup web:remoteConsultation
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/delayFinishRemind")
    public Result delayFinishRemind(){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.remoteConsultationService.delayFinishRemind(doctor.getDepartId());
        return Result.ok();
    }

    /**
     * @api {post}/web/consultation/remote/readFinishRemind.do 阅读完成提醒
     * @author suyz
     * @time 2020/12/28
     * @apiName readFinishRemind 阅读完成提醒
     * @apiGroup web:remoteConsultation
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/readFinishRemind")
    public Result readFinishRemind(){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.remoteConsultationService.readFinishRemind(doctor.getDepartId());
        return Result.ok();
    }

    /**
     * @api {post}/web/consultation/remote/checkOperatorMember.do 判断是否能操作患者
     * @author suyz
     * @time 2020/12/28
     * @apiName checkOperatorMember 判断是否能操作患者
     * @apiGroup web:remoteConsultation
     * @apiParam {String} memberId 患者id
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/checkOperatorMember")
    public Result checkOperatorMember(String memberId){
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        DoctorSessionBO doctor = SessionTool.getWebSession();
        Boolean result = this.remoteConsultationService.checkOperatorMember(memberId ,doctor.getDoctorId());
        return Result.ok(result);
    }

    /**
     * 新增会诊记录参数处理
     * @param param
     * @param doctor
     * @return
     */
    private RemoteConsultationPO addRemoteConsultationParamHandle(AddRemoteConsultationParam param ,DoctorSessionBO doctor){
        RemoteConsultationPO result = new RemoteConsultationPO();
        BeanUtils.copyProperties(result ,param);
        result.setFromHospitalId(doctor.getHospitalId());
        result.setFromDepartId(doctor.getDepartId());
        result.setFromDoctorId(doctor.getDoctorId());
        return result;
    }
}
