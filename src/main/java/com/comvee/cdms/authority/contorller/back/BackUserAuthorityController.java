package com.comvee.cdms.authority.contorller.back;

import com.comvee.cdms.authority.constant.RoleConstant;
import com.comvee.cdms.authority.model.po.RolePO;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.authority.service.RoleService;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/2/18
 */
@RestController
@RequestMapping("/back/userAuthority")
public class BackUserAuthorityController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityService authorityService;

    /**
     * 加载用户角色
     * @param foreignId 用户id
     * @param roleType 角色类型
     * @return
     */
    @RequestMapping("listUserRole")
    public Result listUserRole(String foreignId, Integer roleType){
        ValidateTool.checkParamIsNull(foreignId, "foreignId");
        ValidateTool.checkParamIsNull(roleType, "roleType");
        List<RolePO> list = this.roleService.listUserRole(foreignId, roleType, RoleConstant.ROLE_STATUS_OK);
        return Result.ok(list);
    }

    /**
     * 修改用户角色
     * @param foreignId
     * @param roleIdString
     * @param roleType
     * @return
     */
    @RequestMapping("updateUserRole")
    public Result updateUserRole(String foreignId, String roleIdString, Integer roleType){
        ValidateTool.checkParamIsNull(foreignId, "foreignId");
        ValidateTool.checkParamIsNull(roleIdString, "roleIdString");
        ValidateTool.checkParamIsNull(roleType, "roleType");
        this.roleService.updateUserRole(foreignId, roleIdString, roleType);
        return Result.ok();
    }
}
