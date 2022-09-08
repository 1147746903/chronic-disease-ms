package com.comvee.cdms.user.service.impl;

import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.user.mapper.LoginLogMapper;
import com.comvee.cdms.user.po.LoginLogPO;
import com.comvee.cdms.user.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("LoginLogService")
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addLoginLog(String userName, Integer userType, String loginIp, Integer loginResult) {
        LoginLogPO loginLogPO = new LoginLogPO();
        loginLogPO.setLogId(DaoHelper.getSeq());
        loginLogPO.setUserName(userName);
        loginLogPO.setUserType(userType);
        loginLogPO.setLoginIp(loginIp);
        loginLogPO.setLoginResult(loginResult);
        this.loginLogMapper.addLoginLog(loginLogPO);
    }
}
