package com.comvee.cdms.follow.contorller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.follow.po.FollowupCarePO;
import com.comvee.cdms.follow.service.FollowupCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2019/7/29
 */
@RestController
@RequestMapping("/wechat/followupCare")
public class WechatFollowupCareController {


    @Autowired
    private FollowupCareService followupCareService;

    /**
     * 根据id获取随访关怀
     * @param sid
     * @return
     */
    @RequestMapping("getFollowupCareById")
    public Result getFollowupCareById(String sid){
        ValidateTool.checkParamIsNull(sid, "主键");
        FollowupCarePO followupCarePO = this.followupCareService.getFollowupCareById(sid);
        return Result.ok(followupCarePO);
    }
}
