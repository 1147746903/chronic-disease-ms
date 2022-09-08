package com.comvee.cdms.user.service.impl;


import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.cfg.SmsVerifyCodeBusinessCode;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.user.cfg.UserConstant;
import com.comvee.cdms.user.dto.*;
import com.comvee.cdms.user.mapper.UserDoubleFactorMapper;
import com.comvee.cdms.user.mapper.UserMapper;
import com.comvee.cdms.user.po.UserPO;
import com.comvee.cdms.user.po.UserRelationPO;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.user.service.UserJoinInfoService;
import com.comvee.cdms.user.service.UserLoginService;
import com.comvee.cdms.user.service.UserService;
import com.comvee.cdms.user.tool.PasswordTool;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2018/9/28
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DoctorServiceI doctorServiceI;

    @Autowired
    private UserDoubleFactorMapper userDoubleFactorMapper;

    @Override
    public UserPO getUser(GetUserDTO getUserDTO) {
        return this.userMapper.getUser(getUserDTO);
    }

    @Override
    public void updatePassword(PasswordDTO dto, String uid) {
        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUid(uid);
        UserPO userPO = getUser(getUserDTO);
        if (userPO == null) {
            throw new BusinessException("用户不存在");
        }
        if (!userPO.getPassword().equals(PasswordTool.passwordSaltHandler(dto.getOldPassword(), userPO.getSalt()))) {
            throw new BusinessException("旧密码错误");
        }
        String password = PasswordTool.passwordSaltHandler(dto.getNewPassword(), userPO.getSalt());
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setPassword(password);
        updateUserDTO.setUid(uid);
        updateUserDTO.setLastUpdatePasswordDt(DateHelper.getNowDate());
        this.userMapper.updateUser(updateUserDTO);
    }


    @Override
    public String addUser(AddUserDTO addUserDTO) {
        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUserName(addUserDTO.getUserName());
        getUserDTO.setUserType(addUserDTO.getUserType());
        UserPO userPO = getUser(getUserDTO);
        if (userPO != null) {
            throw new BusinessException("用户名已存在，请重新输入");
        }
        String pwdSalt = UUID.randomUUID().toString();
        String password = PasswordTool.passwordSaltHandler(addUserDTO.getPassword(), pwdSalt);
        userPO = new UserPO();
        BeanUtils.copyProperties(userPO, addUserDTO);
        userPO.setUid(addUserDTO.getUserId());
        if(StringUtils.isBlank(userPO.getUid())) {
            userPO.setUid(DaoHelper.getSeq());
        }
        userPO.setPassword(password);
        userPO.setSalt(pwdSalt);
        userPO.setValidDt("2099-12-31 23:59:59");
        this.userMapper.addUser(userPO);
        return userPO.getUid();
    }

    @Override
    public void updateUser(UpdateUserDTO updateUserDTO) {
        if (!StringUtils.isBlank(updateUserDTO.getPassword())) {
            GetUserDTO getUserDTO = new GetUserDTO();
            getUserDTO.setUid(updateUserDTO.getUid());
            UserPO userPO = getUser(getUserDTO);
            if (userPO == null) {
                throw new BusinessException("账号不存在，请确认");
            }
            String password = PasswordTool.passwordSaltHandler(updateUserDTO.getPassword(), userPO.getSalt());
            updateUserDTO.setPassword(password);
        }
        this.userMapper.updateUser(updateUserDTO);
    }

    @Override
    public DoctorSessionBO getUserByWorkNo(String principalId, String thirdId, String lastLoginIPAddress) {
        DoctorSessionBO doctorBO = null;
        DoctorPO doctorPO = this.doctorServiceI.getDoctorByWordNo(principalId, thirdId);
        if (doctorPO != null) {
            GetUserDTO getUserDTO = new GetUserDTO();
            getUserDTO.setUid(doctorPO.getDoctorId());
            UserPO user = this.userMapper.getUser(getUserDTO);
            if (user == null) {
                throw new AuthenticationException("找不到该医生的注册用户");
            }
            //修改最后登录时间
            UpdateUserDTO updateUserDTO = new UpdateUserDTO();
            updateUserDTO.setUid(user.getUid());
            updateUserDTO.setLastLoginDt(DateHelper.getNowDate());
            updateUserDTO.setLastLoginIPAddress(lastLoginIPAddress);
            this.userMapper.updateUser(updateUserDTO);
            if (DateHelper.compareNow(user.getValidDt())) {
                throw new AuthenticationException("注册用户已过期,请联系管理员");
            }
            if (UserConstant.USER_STATUS_LOCK == user.getUserStatus().intValue()) {
                throw new AuthenticationException("注册用户已被禁用，请联系管理员");
            }
            doctorBO = new DoctorSessionBO();
            BeanUtils.copyProperties(doctorBO, doctorPO);
            doctorBO.setUid(user.getUid());
            //获取关联的医生id
            List<DoctorPO> doctorPOList = this.doctorServiceI.listMyDoctor(doctorPO.getDoctorId());
            List<String> doctorIdList = null;
            if (doctorPOList != null && doctorPOList.size() > 0) {
                doctorIdList = doctorPOList.stream().map(DoctorPO::getDoctorId).collect(Collectors.toList());
            } else {
                doctorIdList = new ArrayList<>();
            }
            doctorBO.setRelateDoctorList(doctorIdList);
            return doctorBO;
        } else {
            return null;
        }
    }

    @Override
    public boolean checkSmsVerify(String userName) {
        long num = this.userDoubleFactorMapper.countDoubleFactorUser(userName ,UserConstant.USER_TYPE_DOCTOR);
        if(num == 0){
            return false;
        }
        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUserName(userName);
        UserPO user = getUser(getUserDTO);
        if(user == null){
            throw new BusinessException("账号/密码信息不正确");
        }
        DoctorPO doctor = this.doctorServiceI.getDoctorById(user.getUid());
        if(doctor == null){
            throw new BusinessException("账号错误，请确认");
        }
        VerificationCodeUtils.sendVerificationCode(doctor.getPhoneNo() ,SmsVerifyCodeBusinessCode.DOCTOR_LOGIN_DOUBLE_FACTOR ,"");
        return true;
    }

    @Override
    public void updatePasswordByUserName(PasswordWithUserNameDTO param) {
        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUserName(param.getUserName());
        getUserDTO.setUserType(UserConstant.USER_TYPE_DOCTOR);
        UserPO user = getUser(getUserDTO);
        if(user == null){
            throw new BusinessException("账号信息错误");
        }
        PasswordDTO passwordDTO = new PasswordDTO();
        BeanUtils.copyProperties(passwordDTO ,param);
        updatePassword(passwordDTO ,user.getUid());
    }


}
