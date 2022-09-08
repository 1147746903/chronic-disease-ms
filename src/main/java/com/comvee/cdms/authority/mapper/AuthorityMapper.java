package com.comvee.cdms.authority.mapper;

import com.comvee.cdms.authority.model.dto.AddRoleAuthorityDTO;
import com.comvee.cdms.authority.model.po.AuthorityPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
public interface AuthorityMapper {

    /**
     * 新增权限
     * @param authorityPO
     */
    void addAuthority(AuthorityPO authorityPO);

    /**
     * 根据角色加载权限
     * @param roleList
     * @return
     */
    List<AuthorityPO> listRoleAuthority(@Param("roleList") List<String> roleList);

    /**
     * 新增角色-权限关联
     * @param list
     */
    void addRoleAuthority(@Param("list") List<AddRoleAuthorityDTO> list);

    /**
     * 删除角色-权限关联
     * @param roleId
     */
    void deleteRoleAuthority(@Param("roleId") String roleId);

    /**
     * 加载权限列表
     * @param available
     * @param showStatus
     * @return
     */
    List<AuthorityPO> listAuthority(@Param("available") Integer available,@Param("showStatus") Integer showStatus,@Param("aType") Integer aType);
}
