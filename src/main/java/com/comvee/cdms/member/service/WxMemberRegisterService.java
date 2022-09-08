package com.comvee.cdms.member.service;

import com.comvee.cdms.member.dto.ListMemberBySmsVerifyMobilePhoneDTO;
import com.comvee.cdms.member.dto.ListMemberByWxMobilePhoneDTO;
import com.comvee.cdms.member.vo.WxMemberRegisterLoadMembersVO;

public interface WxMemberRegisterService {

    void sendLoadMembersSmsVerifyCode(String mobilePhone ,String ip);

    WxMemberRegisterLoadMembersVO listMemberByWxMobilePhone(ListMemberByWxMobilePhoneDTO dto);

    WxMemberRegisterLoadMembersVO listMemberBySmsVerifyMobilePhone(ListMemberBySmsVerifyMobilePhoneDTO dto);

    void unBindMiniProgram(String memberId);
}
