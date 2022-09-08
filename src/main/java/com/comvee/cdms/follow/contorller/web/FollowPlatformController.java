package com.comvee.cdms.follow.contorller.web;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.checkresult.dto.BatchAddCheckoutDTO;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.cfg.DoctorConstant;
import com.comvee.cdms.doctor.dto.UpdateDoctorDTO;
import com.comvee.cdms.doctor.dto.WebUpdateDoctorDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.drugs.dto.ListDrugsDepotDTO;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.drugs.service.DrugsMemberService;
import com.comvee.cdms.follow.cfg.FollowLock;
import com.comvee.cdms.follow.dto.ListMemberFollowDTO;
import com.comvee.cdms.follow.model.FollowDTO;
import com.comvee.cdms.follow.po.FollowDiabetesPO;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.follow.vo.ListFollowPlatformRecordVO;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.service.CommitteeService;
import com.comvee.cdms.member.constant.MemberLock;
import com.comvee.cdms.member.constant.MemberSourceConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberManageService;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.vo.ListFollowPlatformMemberVO;
import com.comvee.cdms.prescription.dto.DoctorDTO;
import com.comvee.cdms.shiro.cfg.AuthenticationType;
import com.comvee.cdms.shiro.token.UserNamePasswordToken;
import com.comvee.cdms.user.cfg.LoginLogConstant;
import com.comvee.cdms.user.dto.LoginDTO;
import com.comvee.cdms.user.dto.PasswordDTO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author linr
 * @Date 2022/8/5
 */
@RestController
@RequestMapping("/web/follow/platform/")
public class FollowPlatformController {

    @Autowired
    private MemberManageService memberManageService;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private FollowServiceI followService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private CommitteeService committeeService;

    @Autowired
    private com.comvee.cdms.app.doctorapp.service.DoctorServiceI doctorServiceI;

    @Autowired
    private CheckoutDetailServiceI checkoutDetailService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DrugsMemberService drugsMemberService;

    /**
     * 登录
     * @param loginDTO
     * @return
     */
    @RequestMapping("login")
    public Result login(@Validated LoginDTO loginDTO){
        UserNamePasswordToken userLoginTypeToken = new UserNamePasswordToken(loginDTO.getUserName()
                , loginDTO.getPassword(), AuthenticationType.DOCTOR_WEB);
        userLoginTypeToken.setHost(RequestTool.getIpAddr());
        userLoginTypeToken.setUserType(LoginLogConstant.USER_TYPE_FOLLOW);
        userLoginTypeToken.setVerifyCodeId(loginDTO.getVerifyCodeId());
        userLoginTypeToken.setVerifyCodeValue(loginDTO.getVerifyCodeValue());
        userLoginTypeToken.setSmsVerifyCode(loginDTO.getSmsVerifyCode());
        Subject subject = SecurityUtils.getSubject();
        subject.login(userLoginTypeToken);
        DoctorSessionBO doctorPO = (DoctorSessionBO) subject.getPrincipal();
        Set<String> authoritySet = this.authorityService.listUserAuthority(doctorPO.getDoctorId(), AuthorityConstant.TYPE_FRONT);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctor", subject.getPrincipal());
        jsonObject.put("authorityList", authoritySet);
        jsonObject.put("sessionId" ,subject.getSession().getId());
        return Result.ok(jsonObject);
    }

    /**
     * 搜索本院建档患者
     * @param keyword
     * @return
     */
    @RequestMapping("searchMemberForJoinHospital")
    public Result searchMemberForJoinHospital(String keyword,PageRequest pageRequest){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        ListMemberJoinHospitalDTO listMemberJoinHospitalDTO = new ListMemberJoinHospitalDTO();
        listMemberJoinHospitalDTO.setHospitalId(webSession.getHospitalId());
        listMemberJoinHospitalDTO.setKeyword(keyword);
        PageResult<ListFollowPlatformMemberVO> pageResult = memberManageService.searchJoinHospitalMember(listMemberJoinHospitalDTO, pageRequest);
        return Result.ok(pageResult);
    }


    /**
     * 分页加载村医患者列表
     * @param pageRequest
     * @return
     */
    @RequestMapping("pagerCommitteeMember")
    public Result pagerCommitteeMember(PageRequest pageRequest){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        ListMemberJoinHospitalDTO listMemberJoinHospitalDTO = new ListMemberJoinHospitalDTO();
        listMemberJoinHospitalDTO.setHospitalId(webSession.getHospitalId());
        listMemberJoinHospitalDTO.setDoctorId(webSession.getDoctorId());
        PageResult<ListFollowPlatformMemberVO> pageResult = memberManageService.pagerCommitteeMemberForFollowPlatform(webSession,listMemberJoinHospitalDTO, pageRequest);
        return Result.ok(pageResult);
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
     * 随访记录列表
     */
    @RequestMapping("pagerMemberFollow")
    public Result pagerMemberFollow(ListMemberFollowDTO listMemberFollowDTO, PageRequest page){
        listMemberFollowDTO.setMobilePhone(listMemberFollowDTO.getKeyword());
        DoctorSessionBO webSession = SessionTool.getWebSession();
        listMemberFollowDTO.setHospitalId(webSession.getHospitalId());
        PageResult<ListFollowPlatformRecordVO> pageResult = followService.pagerMemberFollow(webSession,listMemberFollowDTO,page);
        return Result.ok(pageResult);
    }

    /**
     * 未完成随访患者列表
     */
    @RequestMapping("pagerUnDoFollowMember")
    public Result pagerUnDoFollowMember(ListMemberFollowDTO listMemberFollowDTO, PageRequest page){
        listMemberFollowDTO.setMobilePhone(listMemberFollowDTO.getKeyword());
        DoctorSessionBO webSession = SessionTool.getWebSession();
        if (StringUtils.isBlank(listMemberFollowDTO.getStartDt())){
            String currentQuarterStartTime = DateHelper.getCurrentQuarterStartTime();//本季度第一天
            listMemberFollowDTO.setStartDt(currentQuarterStartTime);
        }
        listMemberFollowDTO.setHospitalId(webSession.getHospitalId());
        PageResult<ListFollowPlatformRecordVO> pageResult = followService.pagerMemberUnDoFollow(webSession,listMemberFollowDTO,page);
        return Result.ok(pageResult);
    }


    /**
     * 随访信息统计
     */
    @RequestMapping("committeeFollowStatics")
    public Result committeeFollowStatics(){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        Map<String, Object> map = followService.committeeFollowStatics(webSession);
        return Result.ok(map);
    }


    /**
     * 获取当前居委会列表
     */
    @RequestMapping("listCommittee")
    public Result listCommittee(){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        HospitalCommitteePO getPO = new HospitalCommitteePO();
        getPO.setHospitalId(webSession.getHospitalId());
        getPO.setValid(1);
        List<HospitalCommitteePO> result = committeeService.listHospitalCommittee(getPO);
        return Result.ok(result);
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
     * 修改密码
     *
     * @param dto
     * @return
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Result changePwd(@Validated PasswordDTO dto) {
        DoctorSessionBO doctorModel =  SessionTool.getWebSession();
        this.doctorServiceI.changePwd(dto, doctorModel.getUid());
        return Result.ok();
    }


    /**
     * -------------------------------随访内容相关-----------------------------------
     */

    /**
     * 添加随访
     */
    @RequestMapping("insertFollow")
    public Result insertFollow(FollowDTO follow){
        ValidateTool.checkParamIsNull(follow.getDoctorId(), "医生id");
        DoctorSessionBO doctor = SessionTool.getWebSession();
        DoctorDTO doctorDTO = new DoctorDTO();
        BeanUtils.copyProperties(doctorDTO,doctor);

        follow.setLeaderId(follow.getDoctorId());
        follow.setDoctorId(doctor.getDoctorId());
        follow.setDoctorName(doctor.getDoctorName());
        follow.setHospitalId(doctor.getHospitalId());

        Map<String,String> result = new HashMap<String,String>(1);
        result.put("logId",this.followService.insertFollowWithLock(follow));
        return new Result(result);
    }

    /**
     * 修改随访
     */

    @RequestMapping("modifyFollow")
    public Result modifyFollow(FollowDTO follow){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        follow.setDoctorId(doctor.getDoctorId());
        follow.setHospitalId(doctor.getHospitalId());
        follow.setDoctorName(doctor.getDoctorName());
        follow.setOrigin("1");
        follow.setLeaderId(doctor.getDoctorId());
        FollowLock.MODIFY_FOLLOW_LOCK.lock();
        try{
            this.followService.modifyFollow(follow);
        }finally {
            FollowLock.MODIFY_FOLLOW_LOCK.unlock();
        }
        return new Result("修改成功");
    }


    @RequestMapping("batchSaveCheckout")
    public Result batchSaveCheckout(@Validated BatchAddCheckoutDTO batchDto){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        DoctorPO doctorPO = new DoctorPO();
        BeanUtils.copyProperties(doctorPO,doctorSessionBO);
        this.checkoutDetailService.addCheckoutBatch(batchDto,doctorPO);
        return Result.ok("添加成功");
    }


    /**
     * 新增患者
     *
     * @param addMemberDTO
     * @return
     */
    @RequestMapping("addMember")
    public Result addMember(@Validated AddMemberDTO addMemberDTO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        Integer doctorLevel = doctorSessionBO.getDoctorLevel();
        addMemberDTO.setMemberSource(MemberSourceConstant.MEMBER_SOURCE_Follow_PLATFORM);
        if (null != doctorLevel && doctorLevel.equals(DoctorConstant.DOCTOR_LEVEL_VILLAGE )){
            List<HospitalCommitteePO> hospitalCommitteeList = committeeService.listCommitteeByDoctorId(doctorSessionBO.getDoctorId());
            if (hospitalCommitteeList.size()>0){
                HospitalCommitteePO committeePO = hospitalCommitteeList.get(0);
                addMemberDTO.setCommitteeId(committeePO.getId());
                addMemberDTO.setCommitteeName(committeePO.getCommitteeName());
            }
        }
        String memberId = memberManageService.createMemberHospitalRelation(doctorSessionBO, addMemberDTO);
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
     * 获取随访
     * @param followId
     * @param type
     * @return
     */
    @RequestMapping("getFollowByLogId")
    public Result getFollowByLogId(String followId,Integer type){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        return new Result(this.followService.getFollowById(followId,type));
    }

    /**
     * 加载药品列表
     * @param listDrugsDepotDTO
     * @return
     */
    @RequestMapping("listDrugsDepot")
    public Result listDrugsDepot(@Validated ListDrugsDepotDTO listDrugsDepotDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<DrugsDepotPO> list = this.drugsMemberService.listDrugsDepot(listDrugsDepotDTO);
        return new Result(list);
    }

    /**
     * 获取患者上次随访信息
     * @param memberId
     * @param doctorId
     * @return
     */
    @RequestMapping("getNewFollowDiabetes")
    public Result getNewFollowDiabetes(String memberId,String doctorId ,Integer followType){
        ValidateTool.checkParamIsNull(memberId ,"memberId");
        ValidateTool.checkParamIsNull(doctorId ,"doctorId");
        ValidateTool.checkParamIsNull(followType ,"followType");
        FollowDiabetesPO diabetesPO = this.followService.getNewFollowDiabetes(memberId, doctorId ,followType);
        return Result.ok(diabetesPO);
    }

    /**
     * 根据医院id加载当前医院的所有医生
     * @param hospitalId
     * @return
     */
    @RequestMapping("listDoctorByHospitalId")
    public Result listDoctorByHospitalId(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<DoctorPO> list = this.doctorService.listDoctorByHospitalId(hospitalId);
        return Result.ok(list);
    }
}
