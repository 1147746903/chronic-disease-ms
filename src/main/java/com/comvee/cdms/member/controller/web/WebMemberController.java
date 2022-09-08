package com.comvee.cdms.member.controller.web;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.complication.model.bo.PatientSyncBO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.constant.MemberLock;
import com.comvee.cdms.member.constant.MemberSourceConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.po.*;
import com.comvee.cdms.member.service.MemberApplyService;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.vo.InHospitalMemberVO;
import com.comvee.cdms.member.vo.MemberInspectVO;
import com.comvee.cdms.member.vo.MemberSearchResultVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/9/30
 */
@RestController
@RequestMapping("/web/member")
@RequiresUser
public class WebMemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberApplyService memberApplyService;

    @Autowired
    private HospitalService hospitalService;

    /**
     * 新增患者
     *
     * @param addMemberDTO
     * @return
     */
    @RequestMapping("addMember")
    public Result addMember(@Validated AddMemberDTO addMemberDTO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addMemberDTO.setMemberSource(MemberSourceConstant.MEMBER_SOURCE_DOC_WEB); //患者来源 web端
        String memberId;
        MemberLock.ADD_MEMBER_LOCK.lock();
        try {
            memberId = this.memberService.addMember(addMemberDTO, doctorSessionBO);
        } finally {
            MemberLock.ADD_MEMBER_LOCK.unlock();
        }
        return new Result(memberId);
    }

    /**
     * 修改患者
     *
     * @param updateMemberDTO
     * @return
     */
    @RequestMapping("updateMember")
    public Result updateMember(@Validated UpdateMemberDTO updateMemberDTO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        MemberLock.UPDATE_MEMBER_LOCK.lock();
        try {
            this.memberService.updateMember(updateMemberDTO);
            this.memberService.updateMemberVisitNo(doctorSessionBO.getHospitalId(), updateMemberDTO.getMemberId(), updateMemberDTO.getVisitNo());
        } finally {
            MemberLock.UPDATE_MEMBER_LOCK.unlock();
        }
        return new Result("");
    }


    /**
     * 加载患者列表
     *
     * @param listMemberDTO
     * @param pr
     * @return
     */
    @RequestMapping("listMember")
    public Result listMember(@Validated ListMemberDTO listMemberDTO, PageRequest pr) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        listMemberDTO.setHospitalId(doctorSessionBO.getHospitalId());
        PageResult<MemberListPO> poPageResult = this.memberService.listMember(listMemberDTO, pr);
        return new Result(poPageResult);
    }

    /**
     * 根据身份证获取患者信息
     *
     * @param idCard
     * @return
     */
    @RequestMapping("getMemberByIdCard")
    public Result getMemberByIdCard(String idCard) {
        ValidateTool.checkParamIsNull(idCard, "idCard");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setIdCard(idCard);
        getMemberDTO.setHospitalId(doctorSessionBO.getHospitalId());
        MemberPO memberPO = this.memberService.getMember(getMemberDTO);
        return new Result(memberPO);
    }

    /**
     * 根据手机号获取患者信息
     *
     * @param idCard
     * @return
     */
    @RequestMapping("getMemberByMobilePhone")
    public Result getMemberByMobilePhone(String mobilePhone) {
        ValidateTool.checkParamIsNull(mobilePhone, "mobilePhone");
        DoctorSessionBO webSession = SessionTool.getWebSession();
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMobilePhone(mobilePhone);
        MemberPO memberPO = this.memberService.getMember(getMemberDTO);
        return new Result(memberPO);
    }


    /**
     * 根据手机号获取患者信息（仅能获取到被管理的患者）
     *
     * @param idCard
     * @return
     */
    @RequestMapping("getMyMemberByMobilePhone")
    public Result getMyMemberByMobilePhone(String mobilePhone) {
        ValidateTool.checkParamIsNull(mobilePhone, "mobilePhone");
        DoctorSessionBO webSession = SessionTool.getWebSession();
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMobilePhone(mobilePhone);
        MemberPO memberPO = this.memberService.getMember(getMemberDTO);
        boolean checkResult = this.memberService.checkDoctorMemberRelationExists(memberPO.getMemberId(), webSession.getDoctorId());
        if (!checkResult) {
            throw new BusinessException("您与该患者不存在有效的医患关系，请确认");
        }
        return new Result(memberPO);
    }


    /**
     * 根据就诊号获取患者信息
     *
     * @param visitNo
     * @return
     */
    @RequestMapping("getMemberByVisitNo")
    public Result getMemberByVisitNo(String visitNo) {
        DoctorSessionBO doctor = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(visitNo, "visitNo");
        MemberVisitCardDTO memberVisitCardDTO = new MemberVisitCardDTO();
        memberVisitCardDTO.setVisitNo(visitNo);
        memberVisitCardDTO.setHospitalId(doctor.getHospitalId());
        MemberPO memberPO = this.memberService.getMemberByVisitNo(memberVisitCardDTO);
        return new Result(memberPO);
    }


    /**
     * 根据社保卡/身份证查患者
     * @param idCard
     * @param socialCard
     * @return
     */
    @RequestMapping("getMemberByIdOrSocialCard")
    public Result getMemberByIdOrSocialCard(String idCard,String socialCard) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setIdCard(idCard);
        getMemberDTO.setSocialCard(socialCard);
        MemberPO memberPO = this.memberService.getMemberByIdOrSocialCard(getMemberDTO);
        return new Result(memberPO);
    }

    /**
     * 根据患者ID获取患者信息
     *
     * @param memberId
     * @return
     */
    @RequestMapping("getMemberByMemberId")
    public Result getMemberByMemberId(String memberId) {
        ValidateTool.checkParamIsNull(memberId, "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(memberId);
        getMemberDTO.setHospitalId(doctorSessionBO.getHospitalId());
        MemberPO memberPO = this.memberService.getMember(getMemberDTO);
        if (memberPO != null) {
            //患者信息脱敏
            memberPO.setIdCard(ValidateTool.tuoMin(memberPO.getIdCard(), 4, 4, "*"));
            memberPO.setMobilePhone(ValidateTool.tuoMin(memberPO.getMobilePhone(), 3, 4, "*"));
        }
        return new Result(memberPO);
    }

    /**
     * 添加医患关系
     *
     * @param addMemberDoctorRelateDTO
     * @return
     */
    @RequestMapping("addMemberDoctorRelate")
    public Result addMemberDoctorRelate(@Validated AddMemberDoctorRelateDTO addMemberDoctorRelateDTO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addMemberDoctorRelateDTO.setOperatorId(doctorSessionBO.getDoctorId());
        addMemberDoctorRelateDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_WEB); //创建医患关系方式 web
        MemberLock.DOCTOR_MEMBER_RELATION_LOCK.lock();
        Result result = new Result();
        try {
            if (this.memberService.addMemberDoctorRelate(addMemberDoctorRelateDTO)) {
                result.setCode("0");
                result.setSuccess(true);
                result.setMessage("添加成功");
            } else {
                result.setCode("1002");
                result.setSuccess(false);
                result.setMessage("该患者已经存在，请勿重复添加！");
            }
        } finally {
            MemberLock.DOCTOR_MEMBER_RELATION_LOCK.unlock();
        }
        return result;
    }

    /**
     * 修改患者分组
     *
     * @param updateMemberGroupDTO
     * @return
     */
    @RequestMapping("updateMemberGroup")
    public Result updateMemberGroup(@Validated UpdateMemberGroupDTO updateMemberGroupDTO) {
        this.memberService.updateMemberGroup(updateMemberGroupDTO);
        return new Result("");
    }

    /**
     * 修改关注状态
     *
     * @param updateDoctorMemberConcernDTO
     * @return
     */
    @RequestMapping("updateMemberDoctorConcern")
    public Result updateMemberDoctorConcern(@Validated UpdateDoctorMemberConcernDTO updateDoctorMemberConcernDTO) {
        this.memberService.updateMemberDoctorConcern(updateDoctorMemberConcernDTO);
        return new Result("");
    }

    /**
     * @api {post}/web/member/getMemberArchivesByMemberId.do 获取患者个人信息和患者档案
     * @author 王宇晨-林雨堆改
     * @time 2020/03/13
     * @apiName getMemberArchivesByMemberId 获取患者个人信息和患者档案
     * @apiGroup WEB-V6.0.0-C
     * @apiVersion 6.0.0
     * @apiParam {String} hospitalId 医院编号 可选
     * @apiParam {String} doctorId 医护人员编号 默认当前登录医护人员编号
     * @apiParam {String} memberId 患者编号 必填
     * @apiParam {Boolean} hide 是否脱敏 默认false 否 true 是
     * @apiSampleRequest http://192.168.2.12:9080/intelligent-prescription/web/member/getMemberArchivesByMemberId.do
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("getMemberArchivesByMemberId")
    public Result getMemberArchivesByMemberId(String memberId, String doctorId, String hospitalId, Boolean hide) {
        ValidateTool.checkParamIsNull(memberId, "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if (StringUtils.isBlank(doctorId)) {
            doctorId = doctorSessionBO.getDoctorId();
        }
        //患者档案信息
        Map<String, Object> reMap = this.memberService.getMemberArchivesByMemberId(memberId, doctorId, hospitalId, hide);
        return new Result(reMap);
    }

    /**
     * 获取患者档案
     *
     * @param memberId
     * @return memberArchivesPO
     */
    @RequestMapping("getMemberArchives")
    public Result getMemberArchives(String memberId, String doctorId) {
        ValidateTool.checkParamIsNull(memberId, "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if (StringUtils.isBlank(doctorId)) {
            doctorId = doctorSessionBO.getDoctorId();
        }
        //患者档案信息
        MemberArchivesPO reMap = this.memberService.getMemberArchives(memberId, doctorId);
        return new Result(reMap);
    }

    /**
     * 修改档案
     *
     * @param memberArchivesDTO
     * @return
     */
    @RequestMapping("updateMemberArchives")
    public Result updateMemberArchives(@Validated MemberArchivesDTO memberArchivesDTO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if (StringUtils.isBlank(memberArchivesDTO.getDoctorId())) {
            memberArchivesDTO.setDoctorId(doctorSessionBO.getDoctorId());
        }
        memberArchivesDTO.setOrigin("1");
        this.memberService.updateMemberArchive(memberArchivesDTO);
        return new Result("");
    }

    /**
     * 添加档案
     *
     * @param memberArchivesDTO
     * @return
     */
    @RequestMapping("saveMemberArchives")
    public Result saveMemberArchives(@Validated MemberArchivesDTO memberArchivesDTO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if (StringUtils.isBlank(memberArchivesDTO.getDoctorId())) {
            memberArchivesDTO.setDoctorId(doctorSessionBO.getDoctorId());
        }
        memberArchivesDTO.setOrigin("1");
        this.memberService.updateMemberArchive(memberArchivesDTO);
        return new Result("");
    }

    /**
     * 添加控制目标
     *
     * @param rangeBO
     * @return
     */
    @RequestMapping("addMemberRange")
    public Result addMemberRange(@Validated RangeBO rangeBO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        rangeBO.setDoctorId(doctorSessionBO.getDoctorId());
        this.memberService.addMemberRange(rangeBO);
        return new Result("");
    }

    /**
     * 修改控制目标
     *
     * @param rangeBO
     * @return
     */
    @RequestMapping("modifyMemberRange")
    public Result modifyMemberRange(@Validated RangeBO rangeBO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if (StringUtils.isBlank(rangeBO.getDoctorId())) {
            rangeBO.setDoctorId(doctorSessionBO.getDoctorId());
        }
        this.memberService.modifyMemberRange(rangeBO);
        return new Result("");
    }

    /**
     * 获取用户设置目标
     *
     * @param memberId
     * @return memberRange
     */
    @RequestMapping("getMemberRange")
    public Result getMemberRange(String memberId) {
        ValidateTool.checkParameterIsNull("memberId", memberId);
        RangeBO memberRange = this.memberService.getMemberRange(memberId);
        return new Result(memberRange);
    }

    /**
     * 添加患者绑定医患关系
     *
     * @param addMemberWebDTO
     * @return memberId
     */
    @RequestMapping("addMemberWeb")
    public Result addMemberWeb(AddMemberWebDTO addMemberWebDTO) {
        addMemberWebDTO.setMemberSource(MemberSourceConstant.MEMBER_SOURCE_DOC_WEB); //患者来源 web
        addMemberWebDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_WEB);  //医患关系创建方式 web端
        String memberId = this.memberService.addMemberWeb(addMemberWebDTO);
        return new Result(memberId);
    }


    /**
     * 获取患者就诊卡号
     *
     * @param memberId
     * @return
     */
    @RequestMapping("getMemberVisitCard")
    public Result getMemberVisitCard(String memberId, String hospitalId) {
        ValidateTool.checkParamIsNull(memberId, "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        MemberVisitCardDTO memberVisitCardDTO = new MemberVisitCardDTO();
        memberVisitCardDTO.setMemberId(memberId);
        if (!StringUtils.isBlank(hospitalId)) {
            memberVisitCardDTO.setHospitalId(hospitalId);
        } else {
            memberVisitCardDTO.setHospitalId(doctorSessionBO.getHospitalId());
        }
        MemberVisitCardPO memberVisitCardPO = this.memberService.getMemberVisitCard(memberVisitCardDTO);
        return new Result(memberVisitCardPO);
    }


    /**
     * 分页申请记录列表
     *
     * @param listDoctorMemberApplyDTO
     */
    @RequestMapping("listDoctorMemberApply")
    public Result listDoctorMemberApply(ListDoctorMemberApplyDTO listDoctorMemberApplyDTO, PageRequest page) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        listDoctorMemberApplyDTO.setDoctorId(doctorSessionBO.getDoctorId());
        PageResult<DoctorMemberApplyPO> re = this.memberApplyService.listDoctorMemberApply(listDoctorMemberApplyDTO, page);
        return new Result(re);
    }

    /**
     * 获取医患关系
     *
     * @param listDoctorMemberDTO
     */
    @RequestMapping("getDoctorMember")
    public Result getDoctorMember(ListDoctorMemberDTO listDoctorMemberDTO) {
        ValidateTool.checkParamIsNull(listDoctorMemberDTO.getDoctorId(), "doctorId");
        ValidateTool.checkParamIsNull(listDoctorMemberDTO.getMemberId(), "memberId");
        DoctorMemberPO re = this.memberService.getDoctorMember(listDoctorMemberDTO);
        return new Result(re);
    }

    /**
     * 患者概要-风险情况
     *
     * @param memberId 患者id
     * @param doctorId 医生id
     * @return
     */
    @RequestMapping("getRisk")
    public Result getRisk(String memberId, String doctorId, String hospitalId, PageRequest page) {
        ValidateTool.checkParameterIsNull(memberId, "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if (StringUtils.isBlank(doctorId)) {
            doctorId = doctorSessionBO.getDoctorId();
        }
        List<Map<String, Object>> result = this.memberService.getRisk(memberId, doctorId, hospitalId, page);
        return new Result(result);
    }

    /**
     * 患者概要-检测数据-（来源：随访）
     *
     * @param memberId 患者id
     * @param doctorId 医生id
     * @return
     */
    @RequestMapping("getDetectionDate")
    public Result getDetectionDate(String memberId, String doctorId, String hospitalId) {
        ValidateTool.checkParameterIsNull(memberId, "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if (StringUtils.isBlank(doctorId)) {
            doctorId = doctorSessionBO.getDoctorId();
        }
        List<Map<String, Object>> result = this.memberService.getDetectionDate(memberId, doctorId, hospitalId);
        return new Result(result);
    }


    /**
     * 添加医生对患者的备注信息
     *
     * @param doctorMemberRemarkPO
     * @return
     */
    @RequestMapping("addDoctorMemberRemark")
    public Result addDoctorMemberRemark(@Validated DoctorMemberRemarkPO doctorMemberRemarkPO) {
        this.memberService.addDoctorMemberRemark(doctorMemberRemarkPO);
        return new Result("");
    }

    /**
     * 删除医生对患者的备注信息
     *
     * @param remarkId
     * @return
     * @since 20200819弃用
     */
    @RequestMapping("delDoctorMemberRemark")
    public Result delDoctorMemberRemark(String remarkId) {
        ValidateTool.checkParameterIsNull(remarkId, "remarkId");
        this.memberService.delDoctorMemberRemark(remarkId);
        return new Result("");
    }

    /**
     * 获取医生对患者的备注信息
     *
     * @param memberId
     * @param doctorId
     * @return
     * @since 20200819弃用
     */
    @RequestMapping("listDoctorMemberRemark")
    public Result listDoctorMemberRemark(String memberId, String doctorId, String hospitalId) {
        ValidateTool.checkParameterIsNull(memberId, "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if (StringUtils.isBlank(doctorId)) {
            doctorId = doctorSessionBO.getDoctorId();
        }
        List<DoctorMemberRemarkPO> list = this.memberService.listDoctorMemberRemark(memberId, doctorId, hospitalId);
        return new Result(list);
    }

    /**
     * 删除患者 - 并发症筛查
     *
     * @param memberId
     * @return
     */
    @RequestMapping("deleteMemberForScreening")
    public Result deleteMemberForScreening(String memberId) {
        ValidateTool.checkParameterIsNull(memberId, "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        this.memberService.deleteMemberForScreening(memberId, doctorSessionBO.getDoctorId());
        return new Result("");
    }

    /**
     * 根据身份证搜索患者
     *
     * @param idCard
     * @param secretKey
     * @return
     */
    @RequestMapping("searchMemberByIdCard")
    public Result searchMemberByIdCard(String idCard) {
        ValidateTool.checkParamIsNull(idCard, "idCard");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        MemberSearchResultVO memberSearchResultVO = this.memberService.searchMemberByIdCard(idCard, doctorSessionBO);
        return Result.ok(memberSearchResultVO);
    }

    /**
     * 新增患者 (给筛查系统使用)
     *
     * @param addMemberDTO
     * @return
     */
    @RequestMapping("addMemberForScreening")
    public Result addMemberForScreening(PatientSyncBO patientSyncBO, Integer resultStatus) {
        ValidateTool.checkParamIsNull(resultStatus, "resultStatus");
        ValidateTool.checkParamIsNull(patientSyncBO.getIdCard(), "idCard");
        ValidateTool.checkParamIsNull(patientSyncBO.getPatientName(), "patientName");
        ValidateTool.checkParamIsNull(patientSyncBO.getSex(), "sex");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        String memberId = null;
        MemberLock.ADD_MEMBER_LOCK.lock();
        try {
            memberId = this.memberService.addScreeningMember(patientSyncBO, resultStatus, doctorSessionBO);
        } finally {
            MemberLock.ADD_MEMBER_LOCK.unlock();
        }
        return new Result(memberId);
    }

    /**
     * 修改患者(给筛查系统使用)
     *
     * @param updateMemberDTO
     * @return
     */
    @RequestMapping("updateMemberForScreening")
    public Result updateMemberForScreening(PatientSyncBO patientSyncBO, String memberId) {
        ValidateTool.checkParamIsNull(memberId, "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        MemberPO memberPO = this.memberService.getMemberById(memberId);
        MemberLock.UPDATE_MEMBER_LOCK.lock();
        try {
            UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
            BeanUtils.copyProperties(updateMemberDTO, patientSyncBO);
            updateMemberDTO.setMemberName(patientSyncBO.getPatientName());
            updateMemberDTO.setMemberId(memberPO.getMemberId());
            this.memberService.updateMember(updateMemberDTO);
            this.memberService.memberScreeningArchiveHandler(patientSyncBO, memberPO.getMemberId(), doctorSessionBO.getDoctorId());
            this.memberService.updateMemberVisitNo(doctorSessionBO.getHospitalId(), updateMemberDTO.getMemberId(), updateMemberDTO.getVisitNo());
        } finally {
            MemberLock.UPDATE_MEMBER_LOCK.unlock();
        }
        return new Result("");
    }

    /**
     * 根据id获取患者信息（给足筛查用）
     *
     * @param idCard
     * @return
     */
    @RequestMapping("getMemberByIdForScreening")
    public Result getMemberByIdForScreening(String memberId) {
        ValidateTool.checkParamIsNull(memberId, "memberId");
        MemberPO member = this.memberService.getMemberById(memberId);
        PatientSyncBO result = null;
        if (member != null) {
            result = this.memberService.getScreeningPatientByIdCard(member.getIdCard(), SessionTool.getWebSession());
        }
        return new Result(result);
    }


    /**
     * 根据患者ID获取患者信息
     *
     * @param memberId
     * @return
     */
    @RequestMapping("getMemberByMemberIdForScreening")
    public Result getMemberByMemberIdForScreening(String memberId) {
        ValidateTool.checkParamIsNull(memberId, "memberId");
        PatientSyncBO memberPO = this.memberService.getScreeningPatientByMemberId(memberId, SessionTool.getWebSession());
        return new Result(memberPO);
    }

    /**
     * @api {post}/web/member/listMemberForPager.do 分页加载患者列表
     * @author 林雨堆
     * @time 2019/07/18
     * @apName 分页加载患者列表
     * @apiGroup web-member
     * @apiVersion 4.0.0
     * @apiParam {Integer} page 页码（默认1）
     * @apiParam {Integer} rows 页数（默认10）
     * @apiParam {String} groupId  组号（必填）
     * @apiParam {String} type  类型（必填 1院内 2院外）
     * @apiParam {String} keyWord  关键字（可选）
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 OK
     * {
     * "obj":{
     * "pageNum":1,
     * "pageSize":10,
     * "rows":[
     * {
     * // 患者信息
     * },
     * Object{...}
     * ],
     * "totalPages":20,
     * "totalRows":197
     * },
     * "code":"0",
     * "msg":"",
     * "success":true
     * }
     */
    @RequestMapping("listMemberForPager")
    public Result listMemberForPager(@Validated PagerMemberDTO pagerMemberDTO, PageRequest pr) {
        DoctorSessionBO bo = SessionTool.getWebSession();
        pagerMemberDTO.setEntityType(bo.getEntityType());
        if (StringUtils.isBlank(pagerMemberDTO.getHospitalId())) {
            pagerMemberDTO.setHospitalId(bo.getHospitalId());
        } else {
            pagerMemberDTO.setSwitchFlag(true);
        }
        PageResult<MemberListPO> poPageResult = this.memberService.listMemberForPager(pagerMemberDTO, pr);
        return new Result(poPageResult);
    }

    /**
     * @api {post}/web/member/listMemberInHospitalLog.do 获取患者的出入院记录列表
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName listMemberInHospitalLog 获取患者的出入院记录列表
     * @apiGroup web-member
     * @apiVersion 4.0.0
     * @apiParam {String} memberId 转诊患者编号 (必填)
     * @apiSampleRequest http://192.168.7.25:9080/intelligent-prescription/web/member/listMemberInHospitalLog.do
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listMemberInHospitalLog")
    public Result listMemberInHospitalLog(String memberId) {
        return new Result(this.hospitalService.listMemberInHospitalLogByMid(memberId));
    }

    /**
     * @api {post}/web/member/saveMemberWarm.do 保存温馨关怀
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName saveMemberWarm 保存温馨关怀
     * @apiGroup web-member
     * @apiVersion 4.0.0
     * @apiParam {String} memberIds 转诊患者编号串 (必填，多个用","隔开)
     * @apiParam {String} pushDtType 下发时间类型：1立即下发，2定时下发
     * @apiParam {String} pushDt 下发时间 (下发时间类型定时下发必填)
     * @apiParam {String} warmContent 关怀内容 (必填)
     * @apiParam {String} doctorId 创建者编号 (必填)
     * @apiSampleRequest http://192.168.7.25:9080/intelligent-prescription/web/member/saveMemberWarm.do
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("saveMemberWarm")
    public Result saveMemberWarm(MemberWarmDTO memberDTO) {
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        String hospitalId = doctorSession.getHospitalId();
        Map<String, Object> map = this.memberService.saveMemberWarm(memberDTO, hospitalId);
        Result result = new Result(map);
        return result;
    }

    /**
     * @api {post}/web/member/updateMemberWarm.do 修改温馨关怀
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName updateMemberWarm 修改温馨关怀
     * @apiGroup web-member
     * @apiVersion 4.0.0
     * @apiParam {String} sid 记录唯一编号(必填)
     * @apiParam {String} pushDtType 下发时间类型：1立即下发，2定时下发
     * @apiParam {String} pushDt 下发时间 (下发时间类型定时下发必填)
     * @apiParam {String} warmContent 关怀内容 (可选)
     * @apiSampleRequest http://192.168.7.25:9080/intelligent-prescription/web/member/updateMemberWarm.do
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("updateMemberWarm")
    public Result updateMemberWarm(MemberWarmDTO memberDTO) {
        this.memberService.updateMemberWarm(memberDTO);
        Result result = new Result("修改成功");
        return result;
    }

    /**
     * @api {post}/web/member/deleteMemberWarmById.do 删除温馨关怀
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName deleteMemberWarmById 删除温馨关怀
     * @apiGroup web-member
     * @apiVersion 4.0.0
     * @apiParam {String} sid 记录唯一编号(必填)
     * @apiSampleRequest http://192.168.7.25:9080/intelligent-prescription/web/member/deleteMemberWarmById.do
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("deleteMemberWarmById")
    public Result deleteMemberWarmById(String sid) {
        this.memberService.deleteMemberWarmById(sid);
        Result result = new Result("删除成功");
        return result;
    }

    /**
     * @api {post}/web/member/listMemberWarmByDoctorIds.do 获取关怀消息列表-根据医生编号串
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName listMemberWarmByDoctorIds 获取关怀消息列表-根据医生编号串
     * @apiGroup WEB-6.0.0-C
     * @apiVersion 6.0.0
     * @apiParam {String} doctorIds 创建者编号串(必填，多个","隔开)
     * @apiParam {Integer} pushType 下发类型：1立即下发、2定时下发
     * @apiParam {Integer} pushStatus 推送状态: 1 未推送 2 已推送
     * @apiParam {String} startDt 开始时间
     * @apiParam {String} endDt 结束时间
     * @apiSampleRequest http://192.168.7.25:9080/intelligent-prescription/web/member/listMemberWarmByParams.do
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listMemberWarmByParams")
    public Result listMemberWarmByParams(ListMemberWarmDTO listMemberWarmDTO, PageRequest pr) {
        if (StringUtils.isBlank(listMemberWarmDTO.getHospitalId())) {
            listMemberWarmDTO.setHospitalId(SessionTool.getWebSession().getHospitalId());
        }
        PageResult<MemberWarmPO> poPageResult = this.memberService.listMemberWarmByParams(listMemberWarmDTO, pr);
        Result result = new Result(poPageResult);
        return result;
    }

    /**
     * @api {post}/web/member/cancelRelation.do 解除医患关系
     * @author 林雨堆
     * @time 2018/08/21
     * @apiName cancelRelation 解除医患关系
     * @apiGroup web-member
     * @apiVersion 4.0.0
     * @apiParam {String} memberId 患者编号
     * @apiParam {String} doctorId 医生编号
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("cancelRelation")
    public Result cancelRelation(String memberId, String doctorId) {
        this.memberService.cancelRelation(memberId, doctorId);
        Result result = new Result();
        result.setMessage("删除成功");
        return result;
    }

    /**
     * 删除患者用药方案
     *
     * @param id
     * @return
     */
    @RequestMapping("deleteMemberUseDrugPlan")
    public Result deleteMemberUseDrugPlan(String id) {
        ValidateTool.checkParameterIsNull(id, "方案编号（id）");
        MemberDrugItemPO memberDrugItemPO = new MemberDrugItemPO();
        memberDrugItemPO.setId(id);
        memberDrugItemPO.setIsValid("0");
        this.memberService.updateMemberDrugItem(memberDrugItemPO);
        return Result.ok("删除成功");
    }

    /**
     * 添加患者用药方案
     *
     * @param teamId       团队编号
     * @param doctorId     医生编号
     * @param memberId     患者编号
     * @param drugListJson 用药计划
     * @return
     */
    @RequestMapping("addMemberUseDrugPlan")
    public Result addMemberUseDrugPlan(String teamId, String doctorId, String memberId, String drugListJson, Integer dType) {
        ValidateTool.checkParamIsNull(drugListJson, "drugListJson");
        ValidateTool.checkParamIsNull(memberId, "memberId");
        if (StringUtils.isBlank(teamId) && !StringUtils.isBlank(doctorId)) {
            teamId = doctorId;
        } else if (!StringUtils.isBlank(teamId) && StringUtils.isBlank(doctorId)) {
            doctorId = teamId;
        } else {
            DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
            teamId = doctorSessionBO.getDoctorId();
            doctorId = teamId;
        }
        this.memberService.doDrugItem(teamId, doctorId, memberId, null, drugListJson, "1");
        return Result.ok("添加成功");
    }

    /**
     * 修改患者用药方案
     *
     * @param id           记录编号
     * @param remark       备注
     * @param drugListJson 用药计划
     * @return
     */
    @RequestMapping("modifyMemberUseDrugPlan")
    public Result modifyMemberUseDrugPlan(String id, String remark, String drugListJson) {
        ValidateTool.checkParamIsNull(id, "id");
        this.memberService.modifyDrugItem(id, remark, drugListJson);
        return Result.ok("添加成功");
    }

    /**
     * @api {post}/web/member/pagerMemberInspect.do 分页获取患者检查（并发症和其他检查）
     * @author 林雨堆
     * @time 2019/10/10
     * @apiName pagerMemberInspect
     * @apiGroup web-member
     * @apiVersion 4.0.0
     * @apiParam {String} memberId 患者编号
     * @apiParam {String} page 页码
     * @apiParam {String} rows 页数
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("pagerMemberInspect")
    public Result pagerMemberInspect(String memberId, PageRequest pager) {
        PageResult<MemberInspectVO> voPageResult = this.memberService.pagerMemberInspect(memberId, pager);
        return new Result(voPageResult);
    }

    /**
     * 获取当前医生团队下和患者有医关系的医生列表
     *
     * @param memberId
     * @return
     */
    @RequestMapping("listDoctorMemberByDoctorId")
    public Result listDoctorMemberByDoctorId(String memberId) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<DoctorMemberPO> result = this.memberService.listDoctorMemberByDoctorId(memberId, doctorSessionBO.getDoctorId());
        return new Result(result);
    }

    /*************************************************邪恶的分割线******************************************************
     * @version v6.0.0
     * @author: linyd
     * @date: 2020/03/03
     *****************************************************************************************************************/
    /**
     * @api {post}/web/member/listInHospitalMember.do 住院患者列表&卡片
     * @author 林雨堆
     * @time 2020/03/09
     * @apiName listInHospitalMember 住院患者列表&卡片
     * @apiGroup WEB-V6.0.0
     * @apiVersion 6.0.0
     * @apiParam {String} hospitalId 医院编号
     * @apiParam {String} doctorId 医护人员编号
     * @apiParam {String} groupId 科室编号
     * @apiParam {String} keyWord 患者名称或患者名称拼音（含）
     * @apiParam {String} concernStatus 是否关注
     * @apiParam {String} type 管理病种 1:糖尿病 2:高血压
     * @apiParam {String} monitor 是否监测 1是 0否
     * @apiParam {String} paramLevel 血糖情况 1低血糖 3正常 5高血糖
     * @apiParam {Int} page 页码
     * @apiParam {Int} rows 页数
     * @apiSampleRequest http://192.168.2.12:9080/intelligent-prescription/web/member/listInHospitalMember.do
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listInHospitalMember")
    public Result listInHospitalMember(@Validated ListMemberDTO listMemberDTO, PageRequest pager) {
        PageResult<InHospitalMemberVO> pageResult = this.memberService.pagerInHospitalMember(listMemberDTO, pager);
        return new Result(pageResult);
    }

    /**
     * 添加就诊卡号
     *
     * @param dto
     * @return
     */
    @RequestMapping("/addMemberVisitNo")
    public Result addMemberVisitNo(AddMemberVisitCardDTO dto) {
        ValidateTool.checkParamIsNull(dto.getMemberId(), "memberId");
        ValidateTool.checkParamIsNull(dto.getVisitNo(), "visitNo");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setHospitalId(doctorSessionBO.getHospitalId());
        this.memberService.addMemberVisitCard(dto);
        return Result.ok("");
    }

    /**
     * 获取医生对患者的备注信息
     *
     * @param doctorMemberRemarkPO
     * @return
     */
    @RequestMapping("getDoctorMemberRemark")
    public Result getDoctorMemberRemark(String memberId, String doctorId) {
        ValidateTool.checkParameterIsNull("memberId", memberId);
        ValidateTool.checkParameterIsNull("doctorId", doctorId);
        DoctorMemberRemarkPO result = this.memberService.getDoctorMemberRemark(memberId, doctorId);
        return new Result(result);
    }

    /**
     * v8.1.0根据姓名生日性别（1男2女）查询患者
     */
    @RequestMapping("getMemberByInfo")
    public Result getMemberByInfo(String memberName, String birthday, String sex) {
        ValidateTool.checkParamIsNull(memberName, "memberName");
//        ValidateTool.checkParamIsNull(birthday, "birthday");
//        ValidateTool.checkParamIsNull(sex, "sex");
        DoctorSessionBO webSession = SessionTool.getWebSession();
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberName(memberName);
        getMemberDTO.setBirthday(birthday);
        getMemberDTO.setSex(sex);
        getMemberDTO.setHospitalId(webSession.getHospitalId());
        List<MemberPO> memberByInfo = this.memberService.getMemberByInfo(getMemberDTO);
        return new Result(memberByInfo);
    }


}