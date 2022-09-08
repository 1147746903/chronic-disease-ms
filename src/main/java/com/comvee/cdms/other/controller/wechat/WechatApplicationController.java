package com.comvee.cdms.other.controller.wechat;

import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.other.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat/application")
public class WechatApplicationController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * 获取微信小程序当前版本
     * @return
     */
    @RequestMapping("/getWxMiniVersion")
    public Result getWxMiniVersion(){
        String result = this.applicationService.getWxMiniVersion();
        return Result.ok(result);
    }

    /**
     * 获取微信小程序一键登录
     * @return
     */
    @RequestMapping("/getWxQuickLogin")
    public Result getWxQuickLogin(){
        String result = this.applicationService.getWxQuickLogin();
        return Result.ok(result);
    }

}
