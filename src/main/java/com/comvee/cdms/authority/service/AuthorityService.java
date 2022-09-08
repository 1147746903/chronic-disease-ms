package com.comvee.cdms.authority.service;

import com.comvee.cdms.authority.model.dto.AddRoleAuthorityDTO;
import com.comvee.cdms.authority.model.po.AuthorityPO;

import java.util.List;
import java.util.Set;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
public interface AuthorityService {

    /**
     * 新增权限
     * @param authorityPO
     * @return String
     */
    String addAuthority(AuthorityPO authorityPO);

    /**
     * 根据角色加载权限
     * @param roleList
     * @return
     */
    List<AuthorityPO> listRoleAuthority(List<String> roleList);

    /**
     * 新增角色-权限关联
     * @param list
     */
    void addRoleAuthority(List<AddRoleAuthorityDTO> list);

    /**
     * 根据角色删除角色- 权限关联
     * @param roleId
     */
    void deleteRoleAuthorityByRole(String roleId);

    /**
     * 获取用户权限合集
     * @param foreignId
     * @param roleType
     * @return
     */
    Set<String> listUserAuthority(String foreignId, int roleType);

    /**
     * 加载权限列表
     * @param available
     * @param showStatus
     * @param type
     * @return
     */
    List<AuthorityPO> listAuthority(Integer available, Integer showStatus, Integer aType);
}
