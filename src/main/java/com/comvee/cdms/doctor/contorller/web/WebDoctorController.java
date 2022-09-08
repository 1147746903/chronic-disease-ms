package com.comvee.cdms.doctor.contorller.web;

import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorHospitalBO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.*;
import com.comvee.cdms.doctor.model.KeyNoteModel;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.po.DoctorServiceRemindPO;
import com.comvee.cdms.doctor.po.DoctorServiceTimePO;
import com.comvee.cdms.doctor.service.DoctorPopupRemindIgnoreTimeService;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.doctor.vo.*;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.ListMemberDTO;
import com.comvee.cdms.other.po.DoctorPushSetPO;
import com.comvee.cdms.other.service.DoctorPushService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/9/30
 */
@RestController
@RequestMapping("/web/doctor")
@RequiresUser
public class WebDoctorController {

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private CheckoutDetailServiceI checkoutDetailService;

    @Autowired
    private DoctorPushService doctorPushService;

    @Autowired
    private DoctorPopupRemindIgnoreTimeService doctorPopupRemindIgnoreTimeService;

    /**
     * @api {post}/web/doctor/listGroup.do 加载门诊居家分组
     * @author 林雨堆
     * @time 2020/03/20
     * @apName 加载门诊居家分组
     * @apiGroup WEB-V6.0.0-C
     * @apiVersion 6.0.0
     * @apiParam {String} doctorId  医生编号（必填）
     * @apiParam {String} keyWord  关键字（可选）
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 OK
     * {
     *     "obj":[
     *     {
     *     "doctorId":"1",
     *     "groupId":"0",
     *     "groupName":"默认分组",
     *     "peopleNumber":197
     *     },
     *     {
     *     "doctorId":"1",
     *     "groupId":"123",
     *     "groupName":"vip组。",
     *     "peopleNumber":18
     *     },...],
     *     "code":"0",
     *     "msg":"",
     *     "success":true
     * }
     */
    @RequestMapping("listGroup")
    public Result listGroup(String doctorId,String keyWord ,Boolean isPeopleNumber){
        ValidateTool.checkParamIsNull(doctorId,"doctorId");
        List<DoctorGroupVO> list = this.doctorService.listGroup(doctorId, keyWord ,isPeopleNumber);
        return new Result(list);
    }

    /**
     * 加载我的医生列表
     * @return
     */
    @RequestMapping("listMyDoctor")
    public Result listMyDoctor(String hospitalId){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<DoctorPO> doctorPOList = new ArrayList<DoctorPO>();
        if (!StringUtils.isBlank(hospitalId)){
            doctorPOList = this.doctorService.listDoctorByHospitalId(hospitalId);
        }else {
            doctorPOList = this.doctorService.listMyDoctor(doctorSessionBO.getDoctorId());
        }
        return new Result(doctorPOList);
    }

    /**
     * 新增分组
     * @param addGroupDTO
     * @return
     */
    @RequestMapping("addGroup")
    public Result addGroup(@Validated AddGroupDTO addGroupDTO){
        String id = this.doctorService.addGroup(addGroupDTO);
        return new Result(id);
    }

    /**
     * 修改分组
     * @param updateGroupDTO
     * @return
     */
    @RequestMapping("updateGroup")
    public Result updateGroup(@Validated UpdateGroupDTO updateGroupDTO){
        this.doctorService.updateGroup(updateGroupDTO);
        return new Result("");
    }

    /**
     * 删除分组
     * @param deleteGroupDTO
     * @return
     */
    @RequestMapping("deleteGroup")
    public Result deleteGroup(@Validated DeleteGroupDTO deleteGroupDTO){
        this.doctorService.deleteGroup(deleteGroupDTO);
        return new Result("");
    }

    /**
     * 获取医生待办任务
     * @return
     */
    @RequestMapping("getDoctorTask")
    public Result getDoctorTask(String authority){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        DoctorTaskVO taskVO = this.doctorService.getDoctorTask(doctorSessionBO.getDoctorId(), doctorSessionBO.getHospitalId(),authority);
        return new Result(taskVO);
    }

    /**
     * 修改医生信息
     * @param webUpdateDoctorDTO
     * @return
     */
    @RequestMapping("updateDoctorInfo")
    public Result updateDoctorInfo(WebUpdateDoctorDTO webUpdateDoctorDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        UpdateDoctorDTO updateDoctorDTO = new UpdateDoctorDTO();
        BeanUtils.copyProperties(updateDoctorDTO, webUpdateDoctorDTO);
        updateDoctorDTO.setDoctorId(doctorSessionBO.getDoctorId());
        this.doctorService.updateDoctor(updateDoctorDTO);
        return new Result("");
    }

    /**
     * 获取医生信息
     * @return
     */
    @RequestMapping("getDoctorInfo")
    public Result getDoctorInfo(String doctorId){
        if(StringUtils.isBlank(doctorId)){
            DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
            doctorId=doctorSessionBO.getDoctorId();
        }
        DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
        return new Result(doctorPO);
    }

    /**
     * @api {post}/web/doctor/listGroups.do 加载分组
     * @author 林雨堆
     * @time 2019/07/18
     * @apName 加载分组
     * @apiGroup web-doctor
     * @apiVersion 4.0.0
     * @apiParam {String} keyWord  关键字（可选）
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 OK
     * {
     *     "obj":[
     *      {
     *      "items":[
     *          {
     *          "doctorId":"1",
     *          "groupId":"181105185900104",
     *          "groupName":"内分泌科",
     *          "peopleNumber":10
     *          },
     *          {
     *          "doctorId":"1",
     *          "groupId":"517812E861921A665A9324275FDE8FE6",
     *          "groupName":"虚拟病区",
     *          "peopleNumber":9
     *          },...
     *      ],
     *      "name":"住院患者",
     *      "orderIndex":0,
     *      "type":1
     *     },
     *     {
     *      "items":[
     *          {
     *          "doctorId":"1",
     *          "groupId":"0",
     *          "groupName":"默认分组",
     *          "peopleNumber":197
     *          },
     *          {
     *          "doctorId":"1",
     *          "groupId":"123",
     *          "groupName":"vip组。",
     *          "peopleNumber":18
     *          },...
     *      ],
     *      "name":"门诊/居家患者",
     *       "orderIndex":1,
     *      "type":2
     *      }
     *     ],
     *     "code":"0",
     *     "msg":"",
     *     "success":true
     * }
     */
    @RequestMapping("listGroups")
    public Result listGroups(ListGroupsDTO dto){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setDoctorId(doctorSessionBO.getDoctorId());
        dto.setEntityType(doctorSessionBO.getEntityType());
        dto.setCountPeopleNumber(true);
        List<GroupsVO> list = this.doctorService.listGroups(dto);
        return new Result(list);
    }


    /**
     * @api {post}/web/doctor/getDoctorGroupByGroupId.do 获取分组详细信息
     * @author 李左河
     * @time 2018/7/25
     * @apName 获取分组详细信息
     * @apiGroup web-doctor
     * @apiVersion 4.0.0
     * @apiParam {String} groupId  分组编号（必填）
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 OK
     * {
     *     "obj":null,
     *     "code":"0",
     *     "msg":"",
     *     "success":true
     * }
     */
    @RequestMapping("getDoctorGroupByGroupId")
    public Result getDoctorGroupByGroupId(String groupId){
        DoctorGroupVO doctorGroupVO = this.doctorService.getDoctorGroupByGroupId(groupId);
        return new Result(doctorGroupVO);
    }

    /**
     * @api {post}/web/doctor/updateDoctorGroupByGroupId.do 修改分组信息
     * @author 李左河
     * @time 2018/7/25
     * @apName 修改分组信息
     * @apiGroup web-doctor
     * @apiVersion 4.0.0
     * @apiParam {String} groupId  分组编号（必填）
     * @apiParam {String} groupName  分组名称
     * @apiParam {String} memberIds  患者id字符串(多个"，"隔开)
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 OK
     * {
     *     "obj":null,
     *     "code":"0",
     *     "msg":"",
     *     "success":true
     * }
     */
    @RequestMapping("updateDoctorGroupByGroupId")
    public Result updateDoctorGroupByGroupId(@Validated UpdateDoctorGroupDTO updateDoctorGroupDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        updateDoctorGroupDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_WEB_UPDATEGROUP); //医患关系创建方式 web
        this.doctorService.updateDoctorGroupByGroupId(doctorSessionBO, updateDoctorGroupDTO);
        return new Result("修改成功");
    }

    /**
     * @api {post}/web/doctor/insertDoctorGroup.do 新增分组
     * @author 李左河
     * @time 2018/7/25
     * @apName 新增分组
     * @apiGroup web-doctor
     * @apiVersion 4.0.0
     * @apiParam {String} groupName  分组名称（必填）
     * @apiParam {String} memberIds  患者id字符串(多个"，"隔开)
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 OK
     * {
     *     "obj":null,
     *     "code":"0",
     *     "msg":"",
     *     "success":true
     * }
     */
    @RequestMapping("insertDoctorGroup")
    public Result insertDoctorGroup(@Validated InsertDoctorGroupDTO insertDoctorGroupDTO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        this.doctorService.insertDoctorGroup(doctorSessionBO,insertDoctorGroupDTO);
        return new Result("");
    }

    /**
     * 加载科室医生列表
     * @return
     */
    @RequestMapping("listDoctorByDepartId")
    public Result listDoctorByDepartId(String departmentId){
        List<DoctorPO> doctorPOList = this.doctorService.listDoctorByDepartId(departmentId);
        return new Result(doctorPOList);
    }

    /** v5.1.1
     * 加载医生可切换医院列表
//     * @param doctorId
//     * @return
     */
    @RequestMapping("listDoctorHospitalByDoctorId")
    public Result listDoctorHospitalByDoctorId(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<DoctorHospitalBO> result = this.doctorService.listDoctorHospitalByDoctorId(doctorSessionBO.getDoctorId());
        return Result.ok(result);
    }

    /**
     * 加载医生团队里的医生列表
     * @param doctorId
     * @return
     */
    @RequestMapping("listMyTeamDoctor")
    public Result listMyTeamDoctor(String doctorId){
        ValidateTool.checkParameterIsNull("doctorId" ,doctorId);
        List<DoctorPO> list = this.doctorService.listMyTeamDoctor(doctorId);
        return Result.ok(list);
    }

    /**
     * 加载团队医生列表
//     * @param doctorId
//     * @return
//     */
    @RequestMapping("listTeamDoctor")
    public Result listTeamDoctor(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<DoctorPO> list = this.doctorService.listGroupDoctor(doctorSessionBO.getDoctorId());
        return Result.ok(list);
    }


    /** v4.0.4
     * 分页加载服务时间
     * @return
     */
    @RequestMapping("pageListDoctorServiceTime")
    public Result pageListDoctorServiceTime(PageRequest page){
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        PageResult<DoctorServiceTimePO> list = this.doctorService.pageListDoctorServiceTime(doctorModel.getDoctorId(), null, null,page);
        return Result.ok(list);
    }

    /** v4.0.4
     * 新增服务时间
     * @return
     */
    @RequestMapping("addDoctorServiceTime")
    public Result addDoctorServiceTime(@Validated AddDoctorServiceTimeDTO addDoctorServiceTimeDTO){
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        DoctorServiceTimePO doctorServiceTimePO = new DoctorServiceTimePO();
        BeanUtils.copyProperties(doctorServiceTimePO, addDoctorServiceTimeDTO);
        doctorServiceTimePO.setDoctorId(doctorModel.getDoctorId());
        String sid = this.doctorService.addDoctorServiceTime(doctorServiceTimePO);
        return Result.ok(sid);
    }

    /** v4.0.4
     * 删除服务时间
     * @return
     */
    @RequestMapping("deleteDoctorServiceTime")
    public Result deleteDoctorServiceTime(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        this.doctorService.deleteDoctorServiceTime(sid);
        return Result.ok("删除成功");
    }

    /** v4.0.4
     * 修改服务时间
     * @return
     */
    @RequestMapping("updateDoctorServiceTime")
    public Result updateDoctorServiceTime(@Validated UpdateDoctorServiceTimeDTO updateDoctorServiceTimeDTO){
        DoctorServiceTimePO doctorServiceTimePO = new DoctorServiceTimePO();
        BeanUtils.copyProperties(doctorServiceTimePO, updateDoctorServiceTimeDTO);
        this.doctorService.updateDoctorServiceTime(doctorServiceTimePO);
        return Result.ok();
    }

    /** v4.0.4
     * 开启和禁用医生服务时间
     * @param updateDoctorServiceTimeDTO
     * @return
     */
    @RequestMapping("/openOrCloseDoctorServiceTime")
    public Result openOrCloseDoctorServiceTime(UpdateDoctorServiceTimeDTO updateDoctorServiceTimeDTO){
        DoctorServiceTimePO doctorServiceTimePO = new DoctorServiceTimePO();
        BeanUtils.copyProperties(doctorServiceTimePO, updateDoctorServiceTimeDTO);
        this.doctorService.openOrCloseDoctorServiceTime(doctorServiceTimePO);
        return Result.ok();
    }

    /** v4.0.4
     * 根据id获取医生服务时间详细信息
     * @param sid
     * @return
     */
    @RequestMapping("/getDoctorServiceTimeById")
    public Result getDoctorServiceTimeById(String sid){
        ValidateTool.checkParameterIsNull("sid",sid);
        DoctorServiceTimePO serviceTime = this.doctorService.getDoctorServiceTimeById(sid);
        return Result.ok(serviceTime);
    }

    /** v4.0.4
     * 保存医生服务时间外提示信息
     * @param addDoctorServiceRemindDTO
     * @return
     */
    @RequestMapping("/saveDoctorServiceRemind")
    public Result saveDoctorServiceRemind(@Validated AddDoctorServiceRemindDTO addDoctorServiceRemindDTO){
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        addDoctorServiceRemindDTO.setDoctorId(doctorModel.getDoctorId());
        this.doctorService.saveDoctorServiceRemindWithLock(addDoctorServiceRemindDTO);
        return Result.ok();
    }


    /** v4.0.4
     * 获取医生服务时间外提示信息
     * @param
     * @return
     */
    @RequestMapping("/getDoctorServiceRemind")
    public Result getDoctorServiceRemind(){
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        DoctorServiceRemindPO remind = this.doctorService.getDoctorServiceRemindByDoctorId(doctorModel.getDoctorId(),null);
        return Result.ok(remind);
    }

    /*************************************************邪恶的分割线******************************************************
     * @version v6.0.0
     * @author: linyd
     * @date: 2020/02/27
     *****************************************************************************************************************/
    /**
     * @api {post}/web/doctor/listDoctorDepart.do 获取医护人员可管理的科室
     * @author 林雨堆
     * @time 2020/03/09
     * @apiName listDoctorDepart 获取医护人员可管理的科室
     * @apiGroup WEB-V6.0.0
     * @apiVersion 6.0.0
     * @apiParam {String} hospitalId 医院编号 （医护人员编号和该编号必须有一个填写，两者都有该编号为主）
     * @apiParam {String} doctorId 医护人员编号 （医院编号和该编号必须有一个填写，两者都有医院编号为主）
     * @apiParam {String} keyWord 患者名称或患者名称拼音（含）
     * @apiParam {String} concernStatus 是否关注
     * @apiParam {String} type 管理病种 1:糖尿病 2:高血压
     * @apiParam {String} monitor 是否监测 1是 0否
     * @apiParam {String} paramLevel 血糖情况 1低血糖 3正常 5高血糖
     * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/doctor/listDoctorDepart.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listDoctorDepart")
    public Result listDoctorDepart(ListMemberDTO listMemberDTO){
        List<DoctorDepartVO> doctorDepartVOList = this.doctorService.listDoctorDepart(listMemberDTO);
        return new Result(doctorDepartVOList);
    }

    /**
     * @api {post}/web/doctor/saveDoctorKeyNotes.do 添加医生重点关注患者项目
     * @author 王宇晨
     * @time 2020/03/09
     * @apiName saveDoctorKeyNotes 添加医生重点关注患者项目
     * @apiGroup WEB-V6.0.0
     * @apiVersion 6.0.0
     * @apiParam {String} memberId 患者编号 可选
     * @apiParam {String} doctorId 医护人员编号 必填
     * @apiParam {Integer} inHos 是否是住院关注项目 0 否 1 是 必填
     * @apiParam {String} keyIds 重点关注项目id串 必填
     * @apiParam {String} hospitalId 医院编号 必填
     * @apiParam {Integer} type 设置类型 1 特别关注设置 2 查看数据设置 可选默认1
     * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/doctor/saveDoctorKeyNotes.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("saveDoctorKeyNotes")
    public Result saveDoctorKeyNotes(@Validated DoctorKeyNoteDTO dto) {
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        dto.setOperatorId(doctorModel.getDoctorId());
        dto.setDoctorId(doctorModel.getDoctorId());
        dto.setHospitalId(doctorModel.getHospitalId());
        this.checkoutDetailService.saveDoctorKeyNotes(dto);
        return Result.ok("添加成功");
    }

    /**
     * @api {post}/web/doctor/listSelectedKeyNotes.do 获取医生重点关注项字段
     * @author 王宇晨-林雨堆改
     * @time 2020/03/09
     * @apiName listSelectedKeyNotes 获取医生重点关注项字段
     * @apiGroup WEB-V6.0.0
     * @apiVersion 6.0.0
     * @apiParam {String} hospitalId 医院编号 必填
     * @apiParam {String} doctorId 医护人员编号 必填
     * @apiParam {String} inHos 是否是住院关注项目 0 否 1 是 必填
     * @apiParam {String} type 设置类型 1 特别关注设置 2 查看数据设置 可选默认1
     * @apiParam {String} memberId 患者编号 可选
     * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/doctor/listSelectedKeyNotes.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listSelectedKeyNotes")
    public Result listSelectedKeyNotes(@Validated ListSelectedKeyNoteDTO dto) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setHospitalId(doctorSessionBO.getHospitalId());
        dto.setDoctorId(doctorSessionBO.getDoctorId());
        List<KeyNoteModel> list = this.doctorService.listSelectedKeyNotes(dto);
        return Result.ok(list);
    }

    /**
     * @api {post}/web/doctor/listSelectedKeyNotes.do 获取医院可配置关注项字段
     * @author 林雨堆
     * @time 2020/03/17
     * @apiName listHospitalAllKeyNotes 获取医院可配置关注项字段
     * @apiGroup WEB-V6.0.0
     * @apiVersion 6.0.0
     * @apiParam {String} hospitalId 医院编号 必填
     * @apiParam {String} keyWord 显示名称or匹配名称or代码 可选
     * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/doctor/listHospitalAllKeyNotes.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listHospitalAllKeyNotes")
    public Result listHospitalAllKeyNotes(@Validated ListHospitalAllKeyNoteDTO dto) {
        List<KeyNoteModel> list = this.doctorService.listHospitalAllKeyNotes(dto);
        return Result.ok(list);
    }

    /** v 5.1.6
     * 根据医院id加载当前医院的所有医生
     * @param hospitalId
     * @return
     */
    @RequestMapping("/listDoctorByHospitalId")
    public Result listDoctorByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<DoctorPO> list = this.doctorService.listDoctorByHospitalId(hospitalId);
        return Result.ok(list);
    }

    /**
     * 根据工号获取医生信息
     * @param workNo
     * @return
     */
    @RequestMapping("/getDoctorInfoByWorkNo")
    public Result getDoctorInfoByWorkNo(String workNo){
        ValidateTool.checkParameterIsNull("workNo",workNo);
        DoctorPO doctor = this.doctorService.getDoctorByWordNo(workNo ,null);
        return Result.ok(doctor);
    }

    /**
     * 获取医生推送设置
     * @return
     */
    @RequestMapping("/getDoctorPushSet")
    public Result getDoctorPushSet(){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        DoctorPushSetPO set = this.doctorPushService.getDoctorPushSet(doctorSession.getDoctorId());
        return Result.ok(set);
    }

    /**
     * 修改医生推送设置
     * @param doctorPushSet
     * @return
     */
    @RequestMapping("/updateDoctorPushSet")
    public Result updateDoctorPushSet(DoctorPushSetPO doctorPushSet){
        ValidateTool.checkParameterIsNull("dialoguePush",doctorPushSet.getDialoguePush());
        ValidateTool.checkParameterIsNull("signPush",doctorPushSet.getSignPush());
        ValidateTool.checkParameterIsNull("wxPush",doctorPushSet.getWxPush());
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        doctorPushSet.setDoctorId(doctorSession.getDoctorId());
        this.doctorPushService.updateDoctorPushSet(doctorPushSet);
        return Result.ok();
    }

    /**
     * h5医生端查看血酮记录
     * @param memberId
     * @return
     */
    @RequestMapping("/listBloodKetone")
    public Result listBloodKetone(String memberId){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(memberId, "memberId");
        Map<String, Object> map = doctorService.listBloodKetone(memberId);
        return Result.ok(map);
    }

    /**
     * 加载医生团队及其分组
     * @return
     */
    @RequestMapping("/listTeamWithGroups")
    public Result listTeamWithGroups(ListTeamWithGroupsDTO dto){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        dto.setDoctorId(doctor.getDoctorId());
        List<DoctorTeamWithGroupsVO> result = this.doctorService.listTeamWithGroups(dto);
        return Result.ok(result);
    }

    /**
     * 忽略医生弹窗提醒
     * @param remindType
     * @return
     */
    @RequestMapping("/ignoreDoctorPopupRemind")
    public Result ignoreDoctorPopupRemind(Integer remindType){
        ValidateTool.checkParameterIsNull("remindType" ,remindType);
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        this.doctorPopupRemindIgnoreTimeService.addOrUpdateDoctorPopupRemindIgnoreTime(doctorSession.getDoctorId()
                ,remindType , DateHelper.getNowDate());
        return Result.ok();
    }
}
