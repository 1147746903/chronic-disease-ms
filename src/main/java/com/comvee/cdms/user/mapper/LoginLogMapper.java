package com.comvee.cdms.user.mapper;

import com.comvee.cdms.user.po.LoginLogPO;

public interface LoginLogMapper {

    /**
     * 添加登录日志
     * @param loginLogPO
     */
    void addLoginLog(LoginLogPO loginLogPO);
}
