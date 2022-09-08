package com.comvee.cdms.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.doctor.dto.UpdateDoctorDTO;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.shiro.cfg.AuthenticationType;
import com.comvee.cdms.shiro.exception.MyAuthenticationException;
import com.comvee.cdms.user.cfg.LoginLogConstant;
import com.comvee.cdms.user.cfg.UserConstant;
import com.comvee.cdms.user.cfg.UserJoinConstant;
import com.comvee.cdms.user.dto.*;
import com.comvee.cdms.user.mapper.UserDoubleFactorMapper;
import com.comvee.cdms.user.mapper.UserJoinMapper;
import com.comvee.cdms.user.mapper.UserMapper;
import com.comvee.cdms.user.po.UserPO;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.user.service.LoginLogService;
import com.comvee.cdms.user.service.UserLoginService;
import com.comvee.cdms.user.tool.PasswordTool;
import com.comvee.cdms.wechat.api.WechatTokenApi;
import com.comvee.cdms.wechat.api.WechatUserApi;
import com.comvee.cdms.wechat.config.WechatAppName;
import com.comvee.cdms.wechat.config.WechatConfig;
import com.comvee.cdms.wechat.model.WechatAppConfig;
import com.comvee.cdms.wechat.model.WechatSession;
import com.comvee.cdms.wechat.model.WechatUser;
import com.comvee.cdms.wechat.utils.AES;
import com.comvee.cdms.wechat.utils.WxPKCS7Encoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.comvee.cdms.common.cfg.Constant.DEFAULT_FOREIGN_ID;

@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {

    private final static Logger log = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private UserJoinMapper userJoinMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private UserDoubleFactorMapper userDoubleFactorMapper;

    @Override
    public UserPO login(LoginDTO loginDTO, AuthenticationType authenticationType, Integer userType) {
        int loginResult = LoginLogConstant.LOGIN_RESULT_OTHER;
        UpdateUserDTO updateUserDTO = null;
        boolean updateFlag = false;
        try {
            if(LoginLogConstant.USER_TYPE_WEB == userType){
                if(!ImageVerifyCodeUtils.doVerifyCode(loginDTO.getVerifyCodeId(), loginDTO.getVerifyCodeValue())){
                    throw new AuthenticationException("验证码错误");
                }
            }
            GetUserDTO getUserDTO = new GetUserDTO();
            getUserDTO.setUserName(loginDTO.getUserName());
            getUserDTO.setUserType(USER_TYPE.get(authenticationType));
            UserPO userPO = this.userMapper.getUser(getUserDTO);
            if (userPO == null) {
                loginResult = LoginLogConstant.LOGIN_RESULT_USER_NOT_EXISTS;
                throw new AuthenticationException("账号/密码错误，请确认");
            }
            if (UserConstant.USER_STATUS_FORBIDDEN == userPO.getUserStatus().intValue()) {
                loginResult = LoginLogConstant.LOGIN_RESULT_FORBIDDEN;
                throw new AuthenticationException("账号已被禁用");
            }
            if (DateHelper.compareNow(userPO.getValidDt())) {
                loginResult = LoginLogConstant.LOGIN_RESULT_OVERDUE;
                throw new AuthenticationException("账号已过期,请联系管理员");
            }
            updateUserDTO = new UpdateUserDTO();
            updateUserDTO.setUid(userPO.getUid());
            boolean needDoubleFactor = checkNeedDoubleFactor(loginDTO.getUserName() ,userType);
            userPO.setNeedDoubleFactor(needDoubleFactor);
            if(needDoubleFactor){
                checkLockStatus(userPO);
            }
            if (!PasswordTool.passwordSaltHandler(loginDTO.getPassword(), userPO.getSalt()).equals(userPO.getPassword())) {
                int newestLoginFailTimes = userPO.getLoginFailTimes() + 1;
                updateUserDTO.setLoginFailTimes(newestLoginFailTimes);
                if(newestLoginFailTimes >= MAX_LOGIN_FAIL_TIMES){
                    updateUserDTO.setUserStatus(UserConstant.USER_STATUS_LOCK);
                    updateUserDTO.setLockDt(DateHelper.getNowDate());
                }
                loginResult = LoginLogConstant.LOGIN_RESULT_PASSWORD_ERROR;
                updateFlag = true;
                throw new AuthenticationException("账号/密码错误，请确认");
            }
            if(needDoubleFactor){
                checkLastUpdatePasswordDt(userPO);
            }
            if(userPO.getLoginFailTimes() > 0){
                updateUserDTO.setLoginFailTimes(0);
            }
            if(UserConstant.USER_STATUS_LOCK == userPO.getUserStatus()){
                updateUserDTO.setUserStatus(UserConstant.USER_STATUS_OK);
            }
            loginResult = LoginLogConstant.LOGIN_RESULT_SUCCESS;
            updateUserDTO.setLastLoginDt(DateHelper.getNowDate());
            updateUserDTO.setLastLoginIPAddress(loginDTO.getLastLoginIPAddress());
            updateFlag = true;
            return userPO;
        } finally {
            if(updateUserDTO != null && updateFlag){
                this.userMapper.updateUser(updateUserDTO);
            }
            //添加登录日志
            this.loginLogService.addLoginLog(loginDTO.getUserName(), userType
                    , loginDTO.getLastLoginIPAddress(), loginResult);
        }
    }

    @Override
    public void logout(String doctorId) {
//        this.doctorPushService.deleteDoctorPushTokenByDoctorId(doctorId);
    }


    @Override
    public String wechatPublicOauthLogin(String code) {
        JSONObject jsonObject = WechatTokenApi.getWechatPublicAuthorization(code);
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setOpenId(jsonObject.getString("openid"));
        UserWechatJoinPO userWechatJoinPO = this.userJoinMapper.getUserWechatJoinInfo(getWechatJoinMapperDTO);
        if (userWechatJoinPO == null || userWechatJoinPO.getJoinStatus() != UserJoinConstant.JOIN_STATUS_YES) {
            throw new BusinessException("该微信用户尚未完成注册绑定");
        }
        String token = UUID.randomUUID().toString();
        CacheUtils.publicWechatToken.put(token, userWechatJoinPO.getForeignId());
        return token;
    }


    @Override
    public String channelWechatPublicOauthLogin(String code) {
        JSONObject jsonObject = WechatTokenApi.getChannelWechatPublicAuthorization(code);
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setOpenId(jsonObject.getString("openid"));
        UserWechatJoinPO userWechatJoinPO = this.userJoinMapper.getUserWechatJoinInfo(getWechatJoinMapperDTO);
        if (userWechatJoinPO == null || userWechatJoinPO.getJoinStatus() != UserJoinConstant.JOIN_STATUS_YES) {
            throw new BusinessException("该微信用户尚未完成注册绑定");
        }
        String token = UUID.randomUUID().toString();
        CacheUtils.publicWechatToken.put(token, userWechatJoinPO.getForeignId());
        return token;
    }


    @Override
    public void doctorLoginAndBindWx(String userName, String password, String openId, String host) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);
        loginDTO.setLastLoginIPAddress(host);
        UserPO user = login(loginDTO, AuthenticationType.DOCTOR_WEB, LoginLogConstant.USER_TYPE_WX);

        DoctorPO doctor = this.doctorMapper.getDoctorById(user.getUid());

        if (openId.equals(doctor.getOpenId())) {
            return;
        }
        updateDoctorOpenId(doctor.getDoctorId(), openId);
    }

    @Override
    public void logoutFoxWx(String doctorId) {
        DoctorPO doctor = this.doctorMapper.getDoctorById(doctorId);

        UpdateDoctorDTO updateDoctorDTO = new UpdateDoctorDTO();
        updateDoctorDTO.setDoctorId(doctor.getDoctorId());
        updateDoctorDTO.setOpenId(DEFAULT_FOREIGN_ID);
        updateDoctorDTO.setUnionId(DEFAULT_FOREIGN_ID);
        this.doctorMapper.updateDoctor(updateDoctorDTO);
    }


    @Override
    public UserWechatJoinPO miniProgramlogin(WechatLoginDTO wechatLoginDTO) {
        WechatAppConfig wechatAppConfig = WechatConfig.getWechatAppConfig(WechatAppName.MINI_PROGRAM);
        if (wechatAppConfig == null) {
            log.warn("微信配置文件有误，appName:{}", WechatAppName.MINI_PROGRAM);
            throw new BusinessException("找不到有效的配置信息!");
        }
        //换取openId
        WechatSession wechatSession = WechatUserApi.wxLogin(wechatAppConfig.getAppId(), wechatAppConfig.getSecret(), wechatLoginDTO.getCode());
        WechatUser wechatUser = null;
        //查找本地数据库
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setOpenId(wechatSession.getOpenId());
        getWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_MINI_PROGRAM);
        getWechatJoinMapperDTO.setAuthorizedSource(Constant.WECHAT_AUTHORIZED_SOURCE_PATIENT);
        getWechatJoinMapperDTO.setAppId(wechatAppConfig.getAppId());
        UserWechatJoinPO wechatJoinPO = this.userJoinMapper.getUserWechatJoinInfo(getWechatJoinMapperDTO);
        //存在记录直接返回
        if (wechatJoinPO != null) {
            if (DEFAULT_FOREIGN_ID.equals(wechatJoinPO.getUnionId())) {
                wechatUser = decodeWechatUser(wechatLoginDTO, wechatSession);
                updateUnionId(wechatSession.getOpenId(), wechatUser.getUnionid());
                wechatJoinPO.setUnionId(wechatUser.getUnionid());
                //同步公众号的memberId
                if (UserJoinConstant.JOIN_STATUS_YES == wechatJoinPO.getJoinStatus()) {
                    UpdateWechatJoinMapperDTO updateWechatJoinMapperDTO = new UpdateWechatJoinMapperDTO();
                    updateWechatJoinMapperDTO.setWhereUnionId(wechatUser.getUnionid());
                    updateWechatJoinMapperDTO.setForeignId(wechatJoinPO.getForeignId());
                    updateWechatJoinMapperDTO.setAuthorizedSource(Constant.WECHAT_AUTHORIZED_SOURCE_PATIENT);
                    this.userJoinMapper.updateUserWechatJoinInfo(updateWechatJoinMapperDTO);
                }
            }
            return wechatJoinPO;
        }
        //新增记录
        wechatUser = decodeWechatUser(wechatLoginDTO, wechatSession);
        AddWechatJoinMapperDTO addWechatJoinMapperDTO = new AddWechatJoinMapperDTO();
        addWechatJoinMapperDTO.setNickName(wechatUser.getNickName());
        addWechatJoinMapperDTO.setAppId(wechatAppConfig.getAppId());
        addWechatJoinMapperDTO.setAuthorizedSource(Constant.WECHAT_AUTHORIZED_SOURCE_PATIENT);
        addWechatJoinMapperDTO.setForeignId(DEFAULT_FOREIGN_ID);
        addWechatJoinMapperDTO.setOpenId(wechatUser.getOpenid());
        addWechatJoinMapperDTO.setPhotoUrl(wechatUser.getAvatarUrl());
        addWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_MINI_PROGRAM);
        addWechatJoinMapperDTO.setUnionId(wechatUser.getUnionid() == null ? "-1" : wechatUser.getUnionid());
        addUserWechatJoinWithLock(addWechatJoinMapperDTO);
        wechatJoinPO = new UserWechatJoinPO();
        BeanUtils.copyProperties(wechatJoinPO, addWechatJoinMapperDTO);
        wechatJoinPO.setJoinStatus(UserJoinConstant.JOIN_STATUS_NO);
        return wechatJoinPO;
    }

    /**
     * 修改开放平台id
     *
     * @param openId
     * @param unionId
     */
    private void updateUnionId(String openId, String unionId) {
        if (StringUtils.isBlank(unionId)) {
            return;
        }
        UpdateWechatJoinMapperDTO updateWechatJoinMapperDTO = new UpdateWechatJoinMapperDTO();
        updateWechatJoinMapperDTO.setOpenId(openId);
        updateWechatJoinMapperDTO.setUnionId(unionId);
        this.userJoinMapper.updateUserWechatJoinInfo(updateWechatJoinMapperDTO);
    }

    /**
     * 解码用户信息
     *
     * @param wechatLoginDTO
     * @param wechatSession
     * @return
     */
    private WechatUser decodeWechatUser(WechatLoginDTO wechatLoginDTO, WechatSession wechatSession) {
        WechatUser wechatUser = null;
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(wechatLoginDTO.getEncryptedData())
                    , Base64.decodeBase64(wechatSession.getSessionKey())
                    , Base64.decodeBase64(wechatLoginDTO.getIv()));
            String userInfo = new String(WxPKCS7Encoder.decode(resultByte), "utf-8");
            log.info("微信加密数据EncryptedData解密结果:{}", userInfo);
            wechatUser = JSON.parseObject(userInfo, WechatUser.class);
            return wechatUser;
        } catch (Exception e) {
            throw new BusinessException("解密微信用户信息失败~", e);
        }
    }

    private String addUserWechatJoinWithLock(AddWechatJoinMapperDTO addWechatJoinMapperDTO) {
        //判断是否存在
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        BeanUtils.copyProperties(getWechatJoinMapperDTO, addWechatJoinMapperDTO);
        UserWechatJoinPO wechatJoinInfoPO = this.userJoinMapper.getUserWechatJoinInfo(getWechatJoinMapperDTO);
        if (wechatJoinInfoPO != null) {
            return wechatJoinInfoPO.getSid();
        }
        //执行新增
        String sid = DaoHelper.getSeq();
        addWechatJoinMapperDTO.setSid(sid);
        if (addWechatJoinMapperDTO.getJoinStatus() == null) {
            addWechatJoinMapperDTO.setJoinStatus(UserJoinConstant.JOIN_STATUS_NO);
        }
        this.userJoinMapper.addUserWechatJoinInfo(addWechatJoinMapperDTO);
        return sid;
    }

    private static final Map<AuthenticationType, Integer> USER_TYPE = new HashMap<>();

    static {
        USER_TYPE.put(AuthenticationType.DOCTOR_WEB, 1);
    }

    private void updateDoctorOpenId(String doctorId, String openId) {
        UpdateDoctorDTO updateDoctorDTO = null;
        DoctorPO oldDoctor = this.doctorMapper.getDoctorByOpenId(openId);
        if (oldDoctor != null) {
            updateDoctorDTO = new UpdateDoctorDTO();
            updateDoctorDTO.setDoctorId(oldDoctor.getDoctorId());
            updateDoctorDTO.setOpenId(DEFAULT_FOREIGN_ID);
            this.doctorMapper.updateDoctor(updateDoctorDTO);
        }
        updateDoctorDTO = new UpdateDoctorDTO();
        updateDoctorDTO.setDoctorId(doctorId);
        updateDoctorDTO.setOpenId(openId);
        this.doctorMapper.updateDoctor(updateDoctorDTO);

    }

    private void updateDoctorOpenUnionId(String doctorId, WechatSession wechatSession) {
        UpdateDoctorDTO updateDoctorDTO = null;
        DoctorPO oldDoctor = this.doctorMapper.getDoctorByOpenId(wechatSession.getOpenId());
        if (oldDoctor != null) {
            updateDoctorDTO = new UpdateDoctorDTO();
            updateDoctorDTO.setDoctorId(oldDoctor.getDoctorId());
            updateDoctorDTO.setOpenId(DEFAULT_FOREIGN_ID);
            updateDoctorDTO.setUnionId(DEFAULT_FOREIGN_ID);
            this.doctorMapper.updateDoctor(updateDoctorDTO);
        }
        updateDoctorDTO = new UpdateDoctorDTO();
        updateDoctorDTO.setDoctorId(doctorId);
        updateDoctorDTO.setOpenId(wechatSession.getOpenId());
        updateDoctorDTO.setUnionId(wechatSession.getUnionId());
        this.doctorMapper.updateDoctor(updateDoctorDTO);
    }

    @Override
    public String loginAndBindWxWithCode(String userName ,String password, String code, String host) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);
        loginDTO.setLastLoginIPAddress(host);
        UserPO user = login(loginDTO, AuthenticationType.DOCTOR_WEB, LoginLogConstant.USER_TYPE_WX);
        DoctorPO doctor = this.doctorMapper.getDoctorById(user.getUid());
        WechatSession wechatSession = getWechatSession(code);
        String openId = wechatSession.getOpenId();
        String unionId = wechatSession.getUnionId();
        if (openId.equals(doctor.getOpenId()) && unionId.equals(doctor.getUnionId())) {
            return openId;
        }
        updateDoctorOpenUnionId(doctor.getDoctorId(), wechatSession);
        return openId;
    }

    @Override
    public WechatSession getWechatSession(String code) {
        WechatAppConfig wechatAppConfig = WechatConfig.getWechatAppConfig(WechatAppName.DOCTOR_H5_MINI_PROGRAM);
        if(wechatAppConfig == null){
            log.warn("微信配置文件有误，appName:{}", WechatAppName.DOCTOR_H5_MINI_PROGRAM);
            throw new BusinessException("找不到有效的配置信息!");
        }
        //换取openId
        WechatSession wechatSession = WechatUserApi.wxLogin(wechatAppConfig.getAppId(), wechatAppConfig.getSecret(), code);
        return wechatSession;
    }
    private boolean checkNeedDoubleFactor(String userName ,Integer userType){
        if(LoginLogConstant.USER_TYPE_WEB != userType){
            return false;
        }
        long count = this.userDoubleFactorMapper.countDoubleFactorUser(userName ,UserConstant.USER_TYPE_DOCTOR);
        return count > 0;
    }

    private void checkLockStatus(UserPO user){
        if(StringUtils.isBlank(user.getLockDt())){
            return;
        }
        LocalDateTime lockDateTime = LocalDateTime.ofInstant(DateHelper.getDate(user.getLockDt() ,"yyyy-MM-dd HH:mm:ss").toInstant()
                , ZoneId.systemDefault());
        if(UserConstant.USER_STATUS_LOCK == user.getUserStatus()
                && lockDateTime.toLocalDate().isEqual(LocalDate.now())){
            throw new AuthenticationException("密码连续输入错误5次，该账号已被锁定，请明天再试");
        }
    }

    private void checkLastUpdatePasswordDt(UserPO user){
        if(StringUtils.isBlank(user.getLastUpdatePasswordDt())){
            return;
        }
        LocalDateTime lastUpdatePasswordDateTime = LocalDateTime.ofInstant(DateHelper.getDate(user.getLastUpdatePasswordDt() ,"yyyy-MM-dd HH:mm:ss").toInstant()
                ,ZoneId.systemDefault());
        LocalDateTime checkDate = LocalDateTime.now().minusDays(45L);
        if(lastUpdatePasswordDateTime.isBefore(checkDate)){
            throw new MyAuthenticationException("10" ,"密码已过期，请及时修改密码");
        }
    }

    private final static int MAX_LOGIN_FAIL_TIMES = 5;
}
