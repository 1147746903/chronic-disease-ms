package com.comvee.cdms.authority.service.impl;

import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.mapper.AuthorityMapper;
import com.comvee.cdms.authority.model.dto.AddRoleAuthorityDTO;
import com.comvee.cdms.authority.model.po.AuthorityPO;
import com.comvee.cdms.authority.model.po.RolePO;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.authority.service.RoleService;
import com.comvee.cdms.common.utils.DaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public String addAuthority(AuthorityPO authorityPO) {
        String sid = DaoHelper.getSeq();
        authorityPO.setSid(sid);
        this.authorityMapper.addAuthority(authorityPO);
        return sid;
    }

    @Override
    public List<AuthorityPO> listRoleAuthority(List<String> roleList) {
        return this.authorityMapper.listRoleAuthority(roleList);
    }

    @Override
    public void addRoleAuthority(List<AddRoleAuthorityDTO> list) {
        this.authorityMapper.addRoleAuthority(list);
    }

    @Override
    public void deleteRoleAuthorityByRole(String roleId) {
        this.authorityMapper.deleteRoleAuthority(roleId);
    }

    @Override
    public Set<String> listUserAuthority(String foreignId, int roleType) {
        List<RolePO> roleList = this.roleService.listUserRole(foreignId, roleType, AuthorityConstant.ROLE_STATUS_OK);
        if(roleList == null || roleList.size() == 0){
            return null;
        }
        List<String> roleIdList = roleList.stream().map(RolePO::getRoleId).collect(Collectors.toList());
        List<AuthorityPO> authorityPOList = listRoleAuthority(roleIdList);
        return authorityPOList.stream().map(AuthorityPO::getPermission).collect(Collectors.toSet());
    }

    @Override
    public List<AuthorityPO> listAuthority(Integer available, Integer showStatus, Integer aType) {
        //新版前台权限-TODO
        if(aType==1){
            aType = 10;
        }
        return this.authorityMapper.listAuthority(available, showStatus, aType);
    }
}
