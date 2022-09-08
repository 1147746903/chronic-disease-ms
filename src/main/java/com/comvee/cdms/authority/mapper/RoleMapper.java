package com.comvee.cdms.authority.mapper;

import com.comvee.cdms.authority.model.dto.AddRoleUserDTO;
import com.comvee.cdms.authority.model.dto.ListRoleDTO;
import com.comvee.cdms.authority.model.po.RolePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
public interface RoleMapper {

    /**
     * 加载角色列表
     * @return
     */
    List<RolePO> listRole(ListRoleDTO listRoleDTO);

    /**
     * 新增角色
     * @param rolePO
     */
    void addRole(RolePO rolePO);

    /**
     * 修改角色
     * @param rolePO
     */
    void updateRole(RolePO rolePO);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteRole(@Param("roleId") String roleId);

    /**
     * 根据id获取角色
     * @param roleId
     * @return
     */
    RolePO getRole(@Param("roleId") String roleId);

    /**
     * 添加角色 - 用户关系
     * @param list
     */
    void addRoleUser(@Param("list") List<AddRoleUserDTO> list);

    /**
     * 加载用户角色
     * @param foreignId
     * @param roleType
     * @return
     */
    List<RolePO> listUserRole(@Param("foreignId") String foreignId,@Param("roleType") Integer roleType,@Param("roleStatus") Integer roleStatus);

    /**
     * 删除用户角色
     * @param foreignId
     * @param roleType
     */
    void deleteUserRole(@Param("foreignId") String foreignId,@Param("roleType") Integer roleType);

    /**
     * 删除角色
     * @param roleId
     */
    void delRole(@Param("roleId") String roleId);

}
