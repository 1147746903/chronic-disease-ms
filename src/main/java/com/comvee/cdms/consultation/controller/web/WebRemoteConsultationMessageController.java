package com.comvee.cdms.consultation.controller.web;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.consultation.model.param.ListNewRemoteConsultationMessageParam;
import com.comvee.cdms.consultation.model.param.ListOldRemoteConsultationMessageParam;
import com.comvee.cdms.consultation.model.param.SendRemoteConsultationMessageParam;
import com.comvee.cdms.consultation.model.vo.SendRemoteConsultationMessageResultVO;
import com.comvee.cdms.consultation.service.RemoteConsultationMessageService;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/consultation/remote/message")
public class WebRemoteConsultationMessageController {

    @Autowired
    private RemoteConsultationMessageService remoteConsultationMessageService;


    /**
     * @api {post}/web/consultation/remote/message/listOldMessage.do 加载历史消息
     * @author suyz
     * @time 2020/12/28
     * @apiName listOldMessage 加载历史消息
     * @apiGroup web:remoteConsultation:message
     * @apiParam {String} consultationId 会诊id
     * @apiParam {String} timeStamp 时间戳（首次加载不用传，后续如果需要加载历史，则传本地最久一条数据的时间戳）
     * @apiParam {String} size 条数
     * @apiSuccess {struct} obj 根对象
     * @apiSuccess {String} obj.sid 主键
     * @apiSuccess {String} obj.consultationId 会诊id
     * @apiSuccess {String} obj.doctorId 医生id
     * @apiSuccess {String} obj.hospitalId 医院id
     * @apiSuccess {String} obj.departId 科室id
     * @apiSuccess {String} obj.sendDt 发送时间
     * @apiSuccess {String} obj.sendTimestamp 发送时间戳
     * @apiSuccess {String} obj.contentType 内容类型
     * @apiSuccess {String} obj.contentData 内容对象json
     * @apiSuccess {String} obj.contentData.text 文本内容（当contentType == 1）
     * @apiSuccess {String} obj.doctorName 医生姓名
     */
    @RequestMapping("/listOldMessage")
    public Result listOldMessage(@Validated ListOldRemoteConsultationMessageParam param){
        List result = this.remoteConsultationMessageService.listOldRemoteConsultationMessage(param);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/consultation/remote/message/listNewMessage.do 加载新消息
     * @author suyz
     * @time 2020/12/28
     * @apiName listNewMessage 加载新消息（刷新）
     * @apiGroup web:remoteConsultation:message
     * @apiParam {String} consultationId 会诊id
     * @apiParam {String} timeStamp 时间戳，本地最新一条消失的时间戳
     * @apiParam {String} size 条数
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/listNewMessage")
    public Result listNewMessage(@Validated ListNewRemoteConsultationMessageParam param){
        List result = this.remoteConsultationMessageService.listNewRemoteConsultationMessage(param);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/consultation/remote/message/sendMessage.do 发送消息
     * @author suyz
     * @time 2020/12/28
     * @apiName sendMessage 发送消息
     * @apiGroup web:remoteConsultation:message
     * @apiParam {String} consultationId 会诊id
     * @apiParam {String} contentType 内容类型 1 文本
     * @apiParam {String} text 文本内容
     * @apiSuccess {struct} obj 根对象
     * @apiSuccess {struct} obj.id 主键
     * @apiSuccess {struct} obj.timestamp 时间戳
     * @apiSuccess {struct} obj.sendDt 发送时间
     */
    @RequestMapping("/sendMessage")
    public Result sendMessage(@Validated SendRemoteConsultationMessageParam param){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        param.setDoctorId(doctor.getDoctorId());
        param.setDepartId(doctor.getDepartId());
        param.setHospitalId(doctor.getHospitalId());
        SendRemoteConsultationMessageResultVO result = this.remoteConsultationMessageService.sendMessage(param);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/consultation/remote/message/readMessage.do 阅读消息（消红点）
     * @author suyz
     * @time 2020/12/28
     * @apiName readMessage 阅读消息（消红点）
     * @apiGroup web:remoteConsultation:message
     * @apiParam {String} consultationId 会诊id
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("/readMessage")
    public Result readMessage(String consultationId){
        ValidateTool.checkParameterIsNull("consultationId" ,consultationId);
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.remoteConsultationMessageService.readMessage(consultationId ,doctor.getDepartId());
        return Result.ok();
    }

}
