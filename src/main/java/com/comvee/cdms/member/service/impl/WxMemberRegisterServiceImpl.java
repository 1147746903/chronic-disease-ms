package com.comvee.cdms.member.service.impl;

import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.cfg.SmsVerifyCodeBusinessCode;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.VerificationCodeUtils;
import com.comvee.cdms.member.dto.ListMemberBySmsVerifyMobilePhoneDTO;
import com.comvee.cdms.member.dto.ListMemberByWxMobilePhoneDTO;
import com.comvee.cdms.member.dto.MemberParamsDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.service.WxMemberRegisterService;
import com.comvee.cdms.member.vo.WxMemberRegisterLoadMembersItemVO;
import com.comvee.cdms.member.vo.WxMemberRegisterLoadMembersVO;
import com.comvee.cdms.user.cfg.UserJoinConstant;
import com.comvee.cdms.user.dto.GetWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.UpdateWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.WechatLoginDTO;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.user.service.UserJoinInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("wxMemberRegisterService")
public class WxMemberRegisterServiceImpl implements WxMemberRegisterService {

    @Autowired
    private UserJoinInfoService userJoinInfoService;

    @Autowired
    private MemberService memberService;

    @Override
    public void sendLoadMembersSmsVerifyCode(String mobilePhone ,String ip) {
        VerificationCodeUtils.sendVerificationCode(mobilePhone ,SmsVerifyCodeBusinessCode.WX_LOAD_MEMBERS ,ip);
    }

    @Override
    public WxMemberRegisterLoadMembersVO listMemberByWxMobilePhone(ListMemberByWxMobilePhoneDTO dto) {
        String mobilePhone = this.userJoinInfoService.getWxPhoneNumber(dto.getCode());
        return listMemberByMobliePhoneForWxRegister(mobilePhone);
    }

    @Override
    public WxMemberRegisterLoadMembersVO listMemberBySmsVerifyMobilePhone(ListMemberBySmsVerifyMobilePhoneDTO dto) {
        boolean checkResult = VerificationCodeUtils.checkVerificationCode(dto.getMobilePhone()
                ,SmsVerifyCodeBusinessCode.WX_LOAD_MEMBERS ,dto.getVerifyCode());
        if(!checkResult){
            throw new BusinessException("3666" ,"验证码错误或已过期");
        }
        return listMemberByMobliePhoneForWxRegister(dto.getMobilePhone());
    }

    @Override
    public void unBindMiniProgram(String memberId) {
        GetWechatJoinMapperDTO getWechatJoinDTO = new GetWechatJoinMapperDTO();
        getWechatJoinDTO.setForeignId(memberId);
        getWechatJoinDTO.setJoinType(UserJoinConstant.JOIN_TYPE_MINI_PROGRAM);
        UserWechatJoinPO wechatJoinPO = this.userJoinInfoService.getUserWechatJoin(getWechatJoinDTO);
        if(wechatJoinPO == null){
            throw new BusinessException("患者未绑定小程序，解绑失败");
        }
        UpdateWechatJoinMapperDTO updateWechatJoin = new UpdateWechatJoinMapperDTO();
        updateWechatJoin.setSid(wechatJoinPO.getSid());
        updateWechatJoin.setForeignId(Constant.DEFAULT_FOREIGN_ID);
        updateWechatJoin.setJoinStatus(UserJoinConstant.JOIN_STATUS_NO);
        this.userJoinInfoService.updateUserWechatJoin(updateWechatJoin);
    }

    private WxMemberRegisterLoadMembersVO listMemberByMobliePhoneForWxRegister(String mobilePhone){

        List<WxMemberRegisterLoadMembersItemVO> memberListResult = new ArrayList<>();
        WxMemberRegisterLoadMembersItemVO item;

        MemberParamsDTO params = new MemberParamsDTO();
        params.setMobilePhone(mobilePhone);
        List<MemberPO> memberList = this.memberService.listMemberByWhere(params);
        if(memberList != null && !memberList.isEmpty()){
            for(MemberPO member : memberList){
                GetWechatJoinMapperDTO getWechatJoinDTO = new GetWechatJoinMapperDTO();
                getWechatJoinDTO.setForeignId(member.getMemberId());
                getWechatJoinDTO.setJoinType(UserJoinConstant.JOIN_TYPE_MINI_PROGRAM);
                UserWechatJoinPO wechatJoinPO = this.userJoinInfoService.getUserWechatJoin(getWechatJoinDTO);
                if(wechatJoinPO == null){
                    item = new WxMemberRegisterLoadMembersItemVO();
                    BeanUtils.copyProperties(item ,member);
                    memberListResult.add(item);
                }
            }
        }
        WxMemberRegisterLoadMembersVO result = new WxMemberRegisterLoadMembersVO();
        result.setMobilePhone(mobilePhone);
        result.setMemberList(memberListResult);
        return result;
    }
}
