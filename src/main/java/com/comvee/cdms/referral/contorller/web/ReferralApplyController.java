package com.comvee.cdms.referral.contorller.web;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.referral.dto.AddReferralApplyDTO;
import com.comvee.cdms.referral.dto.ListReferralApplyDTO;
import com.comvee.cdms.referral.dto.ModifyReferralApplyDTO;
import com.comvee.cdms.referral.dto.ModifyReferralSuggestDTO;
import com.comvee.cdms.referral.service.ReferralApplyServiceI;
import com.comvee.cdms.referral.vo.ReferralApplyVO;
import com.comvee.cdms.referral.vo.ReferralSuggestVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 转诊控制层
 * @author: linyd
 * @date: 2019/07/19
 */
@RestController
@RequestMapping("/web/referral/")
@RequiresUser
public class ReferralApplyController {

    @Autowired
    private ReferralApplyServiceI referralApplyService;

    /**
     * @api {post}/web/referral/insertReferralApply.do 新增转诊记录
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName insertReferralApply 新增转诊记录
     * @apiGroup web-referral
     * @apiVersion 4.0.0
     * @apiParam {String} doctorId 转诊医生编号 (必填)
     * @apiParam {String} doctorName 转诊医生名称 (必填)
     * @apiParam {String} memberId 转诊对象编号 (必填)
     * @apiParam {String} memberName 转诊对象名称 (必填)
     * @apiParam {String} hospitalId 转诊医院编号 (必填)
     * @apiParam {String} hospitalName 转诊医院名称 (必填)
     * @apiParam {String} departmentId 转诊科室编号 (必填)
     * @apiParam {String} departmentName 转诊科室名称 (必填)
     * @apiParam {String} applyDt 申请时间 (必填)
     * @apiParam {String} applyReason 申请（转诊）理由 (必填)
     * @apiParam {String} memberInfo 转诊对象基本信息JSON（出生日期，性别） (必填)
     * @apiParam {int} showVisit 是否授权转诊医院查看患者问诊记录 1授权 0未授权 (必填)
     * @apiParam {int} status 是否接收 1已接收 0未接收 (必填)
     * @apiParam {String} isValid 是否有效数据 (必填)
     * @apiParam {int} referralApplyType 转诊类型：1上转，2下转 (必填)
     * @apiParam {String} currentDesc 病情摘要和处理情况 (必填)
     * @apiParam {String} mobileNo 手机号 (必填)
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/referral/insertReferralApply.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("insertReferralApply")
    public Result insertReferralApply(@Validated AddReferralApplyDTO dto){
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        dto.setApplyDoctorId(doctorModel.getDoctorId());
        dto.setApplyDoctorName(doctorModel.getDoctorName());
        dto.setApplyHospitalId(doctorModel.getHospitalId());
        dto.setApplyHospitalName(doctorModel.getHospitalName());
        Map<String,Object> map = this.referralApplyService.insertReferralApplyWithLock(dto);
        Result resultModel = new Result(map);
        return resultModel;
    }

    /**
     * @api {post}/web/referral/listReferral.do 获取转诊列表
     * @author 林雨堆
     * @time 2019/07/19
     * @apiName listReferral 获取转诊列表
     * @apiGroup web-referral
     * @apiVersion 4.0.0
     * @apiParam {int} page 页码（可选）
     * @apiParam {int} rows 行数（可选）
     * @apiParam {int} status 是否接收 1已接收 0未接收（可选）
     * @apiParam {String} memberName 患者名称（可选）
     * @apiParam {String} applyHospitalName 申请医院名称（可选）
     * @apiParam {String} startDt 申请开始时间（可选）
     * @apiParam {String} endDt 申请结束时间（可选）
     * @apiParam {String} doctorId 当前医生的领导编号（工作台，转入列表 必填）
     * @apiParam {String} applyDoctorId 当前医生的领导编号（转出列表 必填）
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/referral/listReferral.do
     *
     * @apiSuccess {Object} data
     * @apiSuccess {Object} data.obj
     * @apiSuccess {Array} data.obj.rows
     * @apiSuccess {String} data.obj.rows.sid 唯一记录编号
     * @apiSuccess {String} data.obj.rows.doctorId 转诊医生编号
     * @apiSuccess {String} data.obj.rows.doctorName 转诊医生名称
     * @apiSuccess {String} data.obj.rows.memberId 转诊对象编号
     * @apiSuccess {String} data.obj.rows.memberName 转诊对象名称
     * @apiSuccess {String} data.obj.rows.hospitalId 转诊医院编号
     * @apiSuccess {String} data.obj.rows.hospitalName 转诊医院名称
     * @apiSuccess {String} data.obj.rows.departmentId 转诊科室编号
     * @apiSuccess {String} data.obj.rows.departmentName 转诊科室名称
     * @apiSuccess {String} data.obj.rows.applyDoctorId 申请医生编号
     * @apiSuccess {String} data.obj.rows.applyDoctorName 申请医生名称
     * @apiSuccess {String} data.obj.rows.applyHospitalId 申请医院编号
     * @apiSuccess {String} data.obj.rows.applyHospitalName 申请医院名称
     * @apiSuccess {String} data.obj.rows.applyDepartmentId 申请科室编号
     * @apiSuccess {String} data.obj.rows.applyDepartmentName 申请科室名称
     * @apiSuccess {String} data.obj.rows.applyDt 申请时间
     * @apiSuccess {String} data.obj.rows.applyReason 申请（转诊）理由
     * @apiSuccess {String} data.obj.rows.memberInfo 转诊对象基本信息JSON（出生日期，性别）
     * @apiSuccess {int} data.obj.rows.showVisit 是否授权转诊医院查看患者问诊记录 1授权 0未授权
     * @apiSuccess {int} data.obj.rows.status 是否接收 1已接收 0未接收
     * @apiSuccess {String} data.obj.rows.isValid 是否有效数据
     * @apiSuccess {String} data.obj.rows.insertDt 入库时间
     * @apiSuccess {String} data.obj.rows.modifyDt 更新时间
     * @apiSuccess {int} data.obj.rows.referralApplyType 转诊类型：1上转，2下转
     * @apiSuccess {String} data.obj.rows.currentDesc 病情摘要和处理情况
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listReferral")
    public Result listReferral(@Validated ListReferralApplyDTO dto, PageRequest pager){
        PageResult<ReferralApplyVO> pageResult = this.referralApplyService.listReferralPage(dto,pager);
        Result resultModel = new Result(pageResult);
        return resultModel;
    }

    /**
     * @api {post}/web/referral/getReferral.do 根据主键获取转诊详情
     * @author 林雨堆
     * @time 2019/07/19
     * @apiName getReferral 根据主键获取转诊详情
     * @apiGroup web-referral
     * @apiVersion 4.0.0
     * @apiParam {String} sid 唯一记录编号（必填）
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/referral/getReferral.do
     *
     * @apiSuccess {Object} data
     * @apiSuccess {Object} data.obj
     * @apiSuccess {Array} data.obj
     * @apiSuccess {String} data.obj.sid 唯一记录编号
     * @apiSuccess {String} data.obj.doctorId 转诊医生编号
     * @apiSuccess {String} data.obj.doctorName 转诊医生名称
     * @apiSuccess {String} data.obj.memberId 转诊对象编号
     * @apiSuccess {String} data.obj.memberName 转诊对象名称
     * @apiSuccess {String} data.obj.hospitalId 转诊医院编号
     * @apiSuccess {String} data.obj.hospitalName 转诊医院名称
     * @apiSuccess {String} data.obj.departmentId 转诊科室编号
     * @apiSuccess {String} data.obj.departmentName 转诊科室名称
     * @apiSuccess {String} data.obj.applyDoctorId 申请医生编号
     * @apiSuccess {String} data.obj.applyDoctorName 申请医生名称
     * @apiSuccess {String} data.obj.applyHospitalId 申请医院编号
     * @apiSuccess {String} data.obj.applyHospitalName 申请医院名称
     * @apiSuccess {String} data.obj.applyDepartmentId 申请科室编号
     * @apiSuccess {String} data.obj.applyDepartmentName 申请科室名称
     * @apiSuccess {String} data.obj.applyDt 申请时间
     * @apiSuccess {String} data.obj.applyReason 申请（转诊）理由
     * @apiSuccess {String} data.obj.memberInfo 转诊对象基本信息JSON（出生日期，性别）
     * @apiSuccess {int} data.obj.showVisit 是否授权转诊医院查看患者问诊记录 1授权 0未授权
     * @apiSuccess {int} data.obj.status 是否接收 1已接收 0未接收
     * @apiSuccess {String} data.obj.isValid 是否有效数据
     * @apiSuccess {String} data.obj.insertDt 入库时间
     * @apiSuccess {String} data.obj.modifyDt 更新时间
     * @apiSuccess {int} data.obj.referralApplyType 转诊类型：1上转，2下转
     * @apiSuccess {String} data.obj.currentDesc 病情摘要和处理情况
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("getReferral")
    public Result getReferral(String sid){
        Result resultModel = new Result(this.referralApplyService.getReferralById(sid));
        return resultModel;
    }

    /**
     * @api {post}/web/referral/modifyReferral.do 修改转诊信息
     * @author 林雨堆
     * @time 2019/07/19
     * @apiName modifyReferral 修改转诊信息
     * @apiGroup web-referral
     * @apiVersion 4.0.0
     * @apiParam {String} sid 唯一记录编号（必填）
     * @apiParam {String} doctorId 转诊医生编号 (可选)
     * @apiParam {String} doctorName 转诊医生名称 (可选)
     * @apiParam {String} memberId 转诊对象编号 (可选)
     * @apiParam {String} memberName 转诊对象名称 (可选)
     * @apiParam {String} hospitalId 转诊医院编号 (可选)
     * @apiParam {String} hospitalName 转诊医院名称 (可选)
     * @apiParam {String} departmentId 转诊科室编号 (可选)
     * @apiParam {String} departmentName 转诊科室名称 (可选)
     * @apiParam {String} applyDoctorId 申请医生编号 (可选)
     * @apiParam {String} applyDoctorName 申请医生名称 (可选)
     * @apiParam {String} applyHospitalId 申请医院编号 (可选)
     * @apiParam {String} applyHospitalName 申请医院名称 (可选)
     * @apiParam {String} applyDepartmentId 申请科室编号 (可选)
     * @apiParam {String} applyDepartmentName 申请科室名称 (可选)
     * @apiParam {String} applyDt 申请时间 (可选)
     * @apiParam {String} applyReason 申请（转诊）理由 (可选)
     * @apiParam {String} memberInfo 转诊对象基本信息JSON（出生日期，性别） (可选)
     * @apiParam {int} showVisit 是否授权转诊医院查看患者问诊记录 1授权 0未授权 (可选)
     * @apiParam {int} status 是否接收 1已接收 0未接收 (可选)
     * @apiParam {String} isValid 是否有效数据 (可选)
     * @apiParam {int} referralApplyType 转诊类型：1上转，2下转 (可选)
     * @apiParam {String} currentDesc 病情摘要和处理情况 (可选)
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/referral/modifyReferral.do
     *
     * @apiSuccess {Object} data.obj
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("modifyReferral")
    public Result modifyReferral(@Validated ModifyReferralApplyDTO dto){
        this.referralApplyService.modifyReferral(dto);
        return new Result("成功");
    }

    /**
     * @api {post}/web/referral/receiveReferral.do 接收转诊
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName receiveReferral 接收转诊
     * @apiGroup web-referral
     * @apiVersion 4.0.0
     * @apiParam {String} sid 转诊记录编号 (必填)
     * @apiParam {String} memberId 转诊患者编号 (必填)
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/referral/receiveReferral.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("receiveReferral")
    public Result receiveReferral(String sid,String memberId){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.referralApplyService.receiveReferralWithLock(sid,memberId,doctor);
        return new Result("接收成功");
    }

    /**
     * @api {post}/web/referral/listSuggestReferral.do 建议转诊列表
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName listSuggestReferral 建议转诊列表
     * @apiGroup web-referral
     * @apiVersion 4.0.0
     * @apiParam {Integer} page 页码 (默认1)
     * @apiParam {Integer} rows 页数 (默认10)
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/referral/listSuggestReferral.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listSuggestReferral")
    public Result listSuggestReferral(PageRequest pager){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        PageResult<ReferralSuggestVO> pageResult = this.referralApplyService.listSuggestReferral(doctor.getHospitalId(),pager);
        Result resultModel = new Result(pageResult);
        return resultModel;
    }

    /**
     * @api {post}/web/referral/hlSuggestReferral.do 忽略建议转诊
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName hlSuggestReferral 忽略建议转诊
     * @apiGroup web-referral
     * @apiVersion 4.0.0
     * @apiParam {String} sid 建议转诊记录编号 (必填)
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/referral/hlSuggestReferral.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("hlSuggestReferral")
    public Result hlSuggestReferral(String sid){
        this.referralApplyService.hlSuggestReferralLog(sid);
        return new Result("已经忽略");
    }

    /**
     * @api {post}/web/referral/modifySuggestReferral.do 修改建议转诊
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName modifySuggestReferral 修改建议转诊
     * @apiGroup web-referral
     * @apiVersion 4.0.0
     * @apiParam {ModifyReferralSuggestDTO} dto 建议转诊信息 (可选)
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/referral/modifySuggestReferral.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("modifySuggestReferral")
    public Result modifySuggestReferral(@Validated ModifyReferralSuggestDTO dto){
        this.referralApplyService.modifySuggestReferralLog(dto);
        return new Result("修改成功");
    }
}
