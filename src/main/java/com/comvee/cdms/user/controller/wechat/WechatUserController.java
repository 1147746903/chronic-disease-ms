package com.comvee.cdms.user.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.shiro.cfg.AuthenticationType;
import com.comvee.cdms.shiro.token.MemberMiniProgramToken;
import com.comvee.cdms.user.dto.WechatLoginDTO;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.user.service.UserJoinInfoService;
import com.comvee.cdms.user.service.UserLoginService;
import com.comvee.cdms.user.service.UserService;
import com.comvee.cdms.user.vo.MiniProgramLoginReturnVO;
import com.comvee.cdms.wechat.api.WechatTokenApi;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
@RestController
@RequestMapping("/wechat/userJoin")
public class WechatUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserJoinInfoService userJoinInfoService;

    /**
     * 小程序登录
     *
     * @param wechatLoginDTO
     * @return
     */
    @RequestMapping("miniProgramlogin")
    public Result miniProgramlogin(@Validated WechatLoginDTO wechatLoginDTO) {
        MemberMiniProgramToken memberMiniProgramToken = new MemberMiniProgramToken();
        BeanUtils.copyProperties(memberMiniProgramToken, wechatLoginDTO);
        memberMiniProgramToken.setAuthenticationType(AuthenticationType.MEMBER_MINI_PROGRAM);
        Subject subject = SecurityUtils.getSubject();
        subject.login(memberMiniProgramToken);
        UserWechatJoinPO userWechatJoinPO = (UserWechatJoinPO) subject.getPrincipal();
        MiniProgramLoginReturnVO miniProgramLoginReturnVO = new MiniProgramLoginReturnVO();
        miniProgramLoginReturnVO.setJoinStatus(userWechatJoinPO.getJoinStatus());
        miniProgramLoginReturnVO.setSessionId(subject.getSession().getId().toString());
        miniProgramLoginReturnVO.setMemberId(userWechatJoinPO.getForeignId());
        return new Result(miniProgramLoginReturnVO);
    }

    /**
     * 获取微信公众号openId
     *
     * @param code
     * @return
     */
    @RequestMapping("getWechatPublicOpenIdByCode")
    public Result getWechatPublicOpenIdByCode(String code) {
        ValidateTool.checkParamIsNull(code, "code");
        JSONObject jsonObject = WechatTokenApi.getWechatPublicAuthorization(code);
        return Result.ok(jsonObject.getString("openid"));
    }

    /**
     * 微信公众号授权登录
     *
     * @param code
     * @return
     */
    @RequestMapping("/wechatPublicOauthLogin")
    public Result wechatPublicOauthLogin(String code) {
        ValidateTool.checkParamIsNull(code, "code");
        String token = this.userLoginService.wechatPublicOauthLogin(code);
        return Result.ok(token);
    }


    /**
     * 获取手机号
     * @param code
     * @return
     */
    @RequestMapping("/getPhoneNumber")
    public Result getPhoneNumber(String code) {
        ValidateTool.checkParamIsNull(code, "code");
        String wxPhoneNumber = userJoinInfoService.getWxPhoneNumber(code);
        return Result.ok(wxPhoneNumber);
    }

}
