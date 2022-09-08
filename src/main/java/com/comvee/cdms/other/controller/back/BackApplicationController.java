package com.comvee.cdms.other.controller.back;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.other.service.ApplicationService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/back/application")
@RequiresUser
public class BackApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping("/updateWxMiniVersion")
    public Result updateWxMiniVersion(String version){
        ValidateTool.checkParameterIsNull("version",version);
        this.applicationService.updateWxMiniVersion(version);
        return Result.ok();
    }
}
