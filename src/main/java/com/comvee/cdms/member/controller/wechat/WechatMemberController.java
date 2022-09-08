package com.comvee.cdms.member.controller.wechat;

import com.comvee.cdms.common.cfg.SmsVerifyCodeBusinessCode;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.constant.MemberSourceConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberApplyService;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.service.WxMemberRegisterService;
import com.comvee.cdms.member.vo.MemberCenterVO;
import com.comvee.cdms.member.vo.WxMemberRegisterLoadMembersVO;
import com.comvee.cdms.shiro.session.MySessionManager;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.user.service.UserJoinInfoService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
@RestController
@RequestMapping("/wechat/member")
public class WechatMemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserJoinInfoService userJoinInfoService;

    @Autowired
    private MemberApplyService memberApplyService;

    @Autowired
    private WxMemberRegisterService wxMemberRegisterService;

    /**
     * 根据身份证获取患者信息
     * @param idCard
     * @return
     */
    @RequestMapping("getMemberByIdCard")
    public Result getMemberByIdCard(String idCard){
        ValidateTool.checkParamIsNull(idCard, "idCard");
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setIdCard(idCard);
        MemberPO memberPO = this.memberService.getMember(getMemberDTO);
        return new Result(memberPO);
    }

    /**
     * 根据电话获取患者信息
     * @param mobilePhone
     * @return
     */
    @RequestMapping("getMemberByMobilePhone")
    public Result getMemberByMobilePhone(String mobilePhone){
        ValidateTool.checkParamIsNull(mobilePhone, "mobilePhone");
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMobilePhone(mobilePhone);
        MemberPO memberPO = this.memberService.getMember(getMemberDTO);
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
     * 新增患者
     * @param addMemberDTO
     * @return
     */
    @RequestMapping("addMember")
    public Result addMember(@Validated AddMemberDTO addMemberDTO){
        if(StringUtils.isBlank(addMemberDTO.getDiabetesDate())){
            addMemberDTO.setDiabetesDate(null);
        }
        addMemberDTO.setMemberSource(MemberSourceConstant.MEMBER_SOURCE_WECHAT);  //患者来源 小程序
        String memberId;
        synchronized (memberService){
            memberId = this.memberService.addMember(addMemberDTO, null);
        }
        return new Result(memberId);
    }

    /**
     * 修改患者
     * @param updateMemberDTO
     * @return
     */
    @RequestMapping("updateMember")
    public Result updateMember(@Validated UpdateMemberDTO updateMemberDTO){
        MemberPO memberPO = SessionTool.getWechatSession();
        updateMemberDTO.setMemberId(memberPO.getMemberId());
        synchronized (memberService){
            this.memberService.updateMember(updateMemberDTO);
        }
        return new Result("");
    }

    /**
     * 修改患者
     * @param updateMemberDTO
     * @return
     */
    @RequestMapping("updateMemberById")
    public Result updateMemberById(@Validated UpdateMemberDTO updateMemberDTO){
        ValidateTool.checkParamIsNull(updateMemberDTO.getMemberId(), "memberId");
        synchronized (memberService){
            this.memberService.updateMember(updateMemberDTO);
        }
        return new Result("");
    }

    /**
     * 绑定患者
     * @param memberId
     * @return
     */
    @RequestMapping("bindMember")
    public Result bindMember(String memberId ,String qrCodeId ,String idCard){
        ValidateTool.checkParamIsNull(memberId, "memberId");
        ValidateTool.checkParamIsNull(idCard, "idCard");
        UserWechatJoinPO joinPO = (UserWechatJoinPO) SecurityUtils.getSubject().getPrincipal();
        if(joinPO == null){
            throw new BusinessException("当前用户未进行授权登录，无法绑定");
        }
        joinPO.setForeignId(memberId);
        joinPO.setJoinStatus(USER_JOIN_STATUS_SUCCESS);
        this.userJoinInfoService.bindMember(joinPO.getOpenId(), memberId ,qrCodeId ,idCard);
        shiroSessionHandler();
        return Result.ok();
    }

    /**
     * 添加控制目标
     * @param rangeBO
     * @return
     */
    @RequestMapping("addMemberRange")
    public Result addMemberRange(RangeBO rangeBO){
        MemberPO memberPO = SessionTool.getWechatSession();
        rangeBO.setMemberId(memberPO.getMemberId());
        this.memberService.addMemberRange(rangeBO);
        return new Result("");
    }

    /**
     * 修改控制目标
     * @param rangeBO
     * @return
     */
    @RequestMapping("modifyMemberRange")
    public Result modifyMemberRange(RangeBO rangeBO){
        MemberPO memberPO = SessionTool.getWechatSession();
        rangeBO.setMemberId(memberPO.getMemberId());
        this.memberService.modifyMemberRange(rangeBO);
        return new Result("");
    }

    /**
     * 获取用户设置目标
     * @return memberRange
     */
    @RequestMapping("getMemberRange")
    public Result getMemberRange(){
        MemberPO memberPO = SessionTool.getWechatSession();
        RangeBO memberRange = this.memberService.getMemberRange(memberPO.getMemberId());
        if (memberRange == null) {
        	return new Result(this.memberService.getMemberDefaultControlTarget(memberPO.getMemberId()));
		}
        return new Result(memberRange);
    }

    /**
     * 提交申请
     * @param doctorId
     * @return
     */
    @RequestMapping("applyDoctor")
    public Result applyDoctor(String doctorId){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        MemberPO memberPO = SessionTool.getWechatSession();
        DoctorMemberApplyDTO doctorMemberApplyDTO = new DoctorMemberApplyDTO();
        doctorMemberApplyDTO.setDoctorId(doctorId);
        doctorMemberApplyDTO.setMemberId(memberPO.getMemberId());
        //医患关系创建方式
        doctorMemberApplyDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_WECHAT);
        this.memberApplyService.addDoctorMemberApply(doctorMemberApplyDTO);
        return new Result("");
    }

    /**
     * 加载患者个人中心
     * @return
     */
    @RequestMapping("getMemberCenter")
    public Result getMemberCenter(){
        MemberPO memberPO = SessionTool.getWechatSession();
        MemberCenterVO centerVO = this.memberService.getMemberCenter(memberPO.getMemberId());
        return new Result(centerVO);
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping("getMemberInfo")
    public Result getMemberInfo(){
        MemberPO memberPO = SessionTool.getWechatSession();
        MemberPO member = this.memberService.getMemberById(memberPO.getMemberId());
        MemberPO copyMember = member;
        if(member!=null){
            //患者信息脱敏 防止缓存脱敏数据
            copyMember = new MemberPO();
            BeanUtils.copyProperties(copyMember, member);
            copyMember.setIdCard(ValidateTool.tuoMin(copyMember.getIdCard(),4,4,"*"));
            copyMember.setMobilePhone(ValidateTool.tuoMin(copyMember.getMobilePhone(),3,4,"*"));
        }
        return new Result(copyMember);
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping("getMember")
    public Result getMember(){
        MemberPO memberPO = SessionTool.getWechatSession();
        MemberPO member = this.memberService.getMemberById(memberPO.getMemberId());
        return new Result(member);
    }

    /**
     * 格局id获取用户信息
     * @return
     */
    @RequestMapping("getMemberInfoById")
    public Result getMemberInfoById(String memberId){
        ValidateTool.checkParamIsNull(memberId ,"memberId");
        MemberPO member = this.memberService.getMemberById(memberId);
        return new Result(member);
    }

    /** hide为true 手机号才进行信息脱敏
     * 获取患者个人信息和 患者档案
     * @param memberId
     * @return memberArchivesPO
     */
    @RequestMapping("getMemberArchivesByMemberId")
    public Result getMemberArchivesByMemberId(String memberId,String doctorId,Boolean hide){
        ValidateTool.checkParamIsNull(memberId, "memberId");
        //患者档案信息
        Map<String, Object> reMap = this.memberService.getMemberArchivesByMemberId(memberId,doctorId,"",hide);
        return new Result(reMap);
    }

    /**
     * 修改档案
     * @param memberArchivesDTO
     * @return
     */
    @RequestMapping("updateMemberArchives")
    public Result updateMemberArchives(@Validated MemberArchivesDTO memberArchivesDTO){
        memberArchivesDTO.setOrigin("4");
        this.memberService.updateMemberArchive(memberArchivesDTO);
        return new Result("");
    }

    /**
     * 获取qrCode 跳转地址
     * @param qrCodeId
     * @return
     */
    @RequestMapping("getQrcodeEventJumpUrl")
    public Result getQrcodeEventJumpUrl(String qrCodeId){
        ValidateTool.checkParamIsNull(qrCodeId, "qrCodeId");
        return Result.ok(userJoinInfoService.getQrcodeEventJumpUrl(qrCodeId));
    }

    /**
     * 发送加载患者短信验证码
     * @param mobile
     * @return
     */
    @RequestMapping("sendLoadMembersSmsVerifyCode")
    public Result sendLoadMembersSmsVerifyCode(String mobilePhone){
        ValidateTool.checkParamIsNull(mobilePhone, "mobilePhone");
        this.wxMemberRegisterService.sendLoadMembersSmsVerifyCode(mobilePhone ,RequestTool.getIpAddr());
        return Result.ok();
    }

    /**
     * 通过微信的手机号码查找匹配的患者列表
     * @param dto
     * @return
     */
    @RequestMapping("listMemberByWxMobilePhone")
    public Result listMemberByWxMobilePhone(@Validated ListMemberByWxMobilePhoneDTO dto){
        WxMemberRegisterLoadMembersVO result = this.wxMemberRegisterService.listMemberByWxMobilePhone(dto);
        return Result.ok(result);
    }

    /**
     * 通过短信验证码的手机号码查找匹配的患者列表
     * @param dto
     * @return
     */
    @RequestMapping("listMemberBySmsVerifyMobilePhone")
    public Result listMemberBySmsVerifyMobilePhone(@Validated ListMemberBySmsVerifyMobilePhoneDTO dto){
        WxMemberRegisterLoadMembersVO result = this.wxMemberRegisterService.listMemberBySmsVerifyMobilePhone(dto);
        return Result.ok(result);
    }

    /**
     * 解绑小程序
     * @return
     */
    @RequestMapping("unBindMiniProgram")
    public Result unBindMiniProgram(){
        MemberPO member = SessionTool.getWechatSession();
        this.wxMemberRegisterService.unBindMiniProgram(member.getMemberId());
        return Result.ok();
    }

    /**
     * shiro session处理,把session信息马上更新到数据库
     */
    private void shiroSessionHandler(){
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(MySessionManager.CHANGE_SESSION_IMMEDIATELY ,true);
        session.touch();
    }

    private final static int USER_JOIN_STATUS_SUCCESS = 1;
}
