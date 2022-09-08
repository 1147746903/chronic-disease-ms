package com.comvee.cdms.user.service;

import com.comvee.cdms.shiro.cfg.AuthenticationType;
import com.comvee.cdms.user.dto.LoginDTO;
import com.comvee.cdms.user.dto.WechatLoginDTO;
import com.comvee.cdms.user.po.UserPO;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.wechat.model.WechatSession;

public interface UserLoginService {

    /**
     * 账号登录
     * @param loginDTO
     */
    UserPO login(LoginDTO loginDTO, AuthenticationType authenticationType , Integer userType);

    /**
     * 退出登录
     * @param doctorId
     */
    void logout(String doctorId);

    /**
     * 微信公众号授权登陆
     * @param code
     * @return
     */
    String wechatPublicOauthLogin(String code);

    /**
     * 渠道公众号授权登陆
     * @param code
     * @return
     */
    String channelWechatPublicOauthLogin(String code);

    /**
     * 医生登录并绑定openId
     * @param userName
     * @param password
     * @param openId
     */
    void doctorLoginAndBindWx(String userName ,String password ,String openId ,String host);

    /**
     * 登出（微信）
     * @param doctorId
     */
    void logoutFoxWx(String doctorId);


    /**
     * 小程序登录
     * @param wechatLoginDTO
     * @return
     */
    UserWechatJoinPO miniProgramlogin(WechatLoginDTO wechatLoginDTO);

    String loginAndBindWxWithCode(String userName ,String password, String code, String host);

    WechatSession getWechatSession(String code);
}
