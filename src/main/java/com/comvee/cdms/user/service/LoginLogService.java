package com.comvee.cdms.user.service;

public interface LoginLogService {

    /**
     * 添加登录日志
     * @param userName
     * @param userType
     * @param loginIp
     * @param loginResult
     */
    void addLoginLog(String userName, Integer userType, String loginIp, Integer loginResult);
}
