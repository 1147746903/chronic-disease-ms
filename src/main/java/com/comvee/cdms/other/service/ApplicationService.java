package com.comvee.cdms.other.service;

public interface ApplicationService {

    /**
     *  修改小程序版本号
     * @param version
     */
    void updateWxMiniVersion(String version);

    /**
     * 获取微信小程序版本
     * @return
     */
    String getWxMiniVersion();

    /**
     * 获取微信小程序一键登录展示
     * @return
     */
    String getWxQuickLogin();
}
