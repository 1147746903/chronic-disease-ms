package com.comvee.cdms.authority.service;

import com.comvee.cdms.authority.model.dto.AddRoleDTO;
import com.comvee.cdms.authority.model.dto.ListRoleDTO;
import com.comvee.cdms.authority.model.dto.UpdateRoleDTO;
import com.comvee.cdms.authority.model.po.RolePO;
import com.comvee.cdms.authority.model.vo.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
public interface RoleService {

    /**
     * 加载角色列表
     * @param pr
     * @param listRoleDTO
     * @return
     */
    PageResult<RolePO> listRole(PageRequest pr, ListRoleDTO listRoleDTO);

    /**
     * 添加角色
     * @param addRoleDTO
     * @return
     */
    String addRole(AddRoleDTO addRoleDTO);

    /**
     * 修改角色
     * @param updateRoleDTO
     */
    void updateRole(UpdateRoleDTO updateRoleDTO);

    /**
     * 新增用户-角色关系
     * @param roleIdList
     * @param foreignId
     * @param roleType
     */
    void addUserRole(String roleIdList, String foreignId, Integer roleType);

    /**
     * 获取角色
     * @param roleId
     * @return
     */
    RoleVO getRole(String roleId);

    /**
     * 加载用户角色
     * @param foreignId
     * @param roleType
     * @return
     */
    List<RolePO> listUserRole(String foreignId, Integer roleType ,Integer roleStatus);

    /**
     * 修改用户角色
     * @param foreignId
     * @param roleIdString
     * @param roleType
     */
    void updateUserRole(String foreignId, String roleIdString, Integer roleType);

    /**
     * 删除角色
     * @param roleId
     */
    void delRole(String roleId,Integer roleType);
}
