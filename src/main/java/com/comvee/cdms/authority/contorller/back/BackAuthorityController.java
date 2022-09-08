package com.comvee.cdms.authority.contorller.back;

import com.comvee.cdms.authority.model.dto.AddRoleDTO;
import com.comvee.cdms.authority.model.dto.ListRoleDTO;
import com.comvee.cdms.authority.model.dto.UpdateRoleDTO;
import com.comvee.cdms.authority.model.po.AuthorityPO;
import com.comvee.cdms.authority.model.po.RolePO;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.authority.service.RoleService;
import com.comvee.cdms.authority.model.vo.RoleVO;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
@RestController
@RequestMapping("/back/authority")
public class BackAuthorityController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityService authorityService;

    /**
     * 加载角色列表
     * @param pr
     * @param listRoleDTO
     * @return
     */
    @RequestMapping("listRole")
    public Result listRole(PageRequest pr,@Valid ListRoleDTO listRoleDTO){
        PageResult<RolePO> pageResult = this.roleService.listRole(pr, listRoleDTO);
        return Result.ok(pageResult);
    }

    /**
     * 添加角色
     * @param addRoleDTO
     * @return
     */
    @RequestMapping("addRole")
    public Result addRole(AddRoleDTO addRoleDTO){
        String roleId = this.roleService.addRole(addRoleDTO);
        return Result.ok(roleId);
    }

    /**
     * 修改角色
     * @param updateRoleDTO
     * @return
     */
    @RequestMapping("updateRole")
    public Result updateRole(@Validated UpdateRoleDTO updateRoleDTO){
        this.roleService.updateRole(updateRoleDTO);
        return Result.ok();
    }

    /**
     * 获取角色
     * @param roleId
     * @return
     */
    @RequestMapping("getRole")
    public Result getRole(String roleId){
        ValidateTool.checkParamIsNull(roleId, "roleId");
        RoleVO roleVO = this.roleService.getRole(roleId);
        return Result.ok(roleVO);
    }

    /**
     * 加载角色的权限列表
     * @param roleId
     * @return
     */
    @RequestMapping("listRoleAuthority")
    public Result listRoleAuthority(String roleId){
        ValidateTool.checkParamIsNull(roleId, "roleId");
        List<AuthorityPO> list = this.authorityService.listRoleAuthority(Arrays.asList(roleId));
        return Result.ok(list);
    }

    /**
     * 加载权限列表
     * @param available
     * @param type
     * @return
     */
    @RequestMapping("listAuthority")
    public Result listAuthority(Integer available, Integer aType){
        ValidateTool.checkParamIsNull(aType, "aType");
        List<AuthorityPO> list = this.authorityService.listAuthority(available, 1, aType);
        return Result.ok(list);
    }

    /**
     * 删除角色
     * @param roleId
     * @param roleType
     * @return
     */
    @RequestMapping("/delRole")
    public Result delRole(String roleId,Integer roleType){
        ValidateTool.checkParamIsNull(roleId, "roleId");
        ValidateTool.checkParamIsNull(roleType, "roleType");
        this.roleService.delRole(roleId,roleType);
        return Result.ok("删除成功");
    }


}
