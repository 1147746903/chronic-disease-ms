package com.comvee.cdms.authority.service.impl;

import com.comvee.cdms.authority.mapper.RoleMapper;
import com.comvee.cdms.authority.model.dto.*;
import com.comvee.cdms.authority.model.vo.*;
import com.comvee.cdms.authority.model.po.AuthorityPO;
import com.comvee.cdms.authority.model.po.RolePO;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.authority.service.RoleService;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.Pinyin4jUtil;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * @author: suyz
 * @date: 2019/1/17
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public PageResult<RolePO> listRole(PageRequest pr, ListRoleDTO listRoleDTO) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<RolePO> list = this.roleMapper.listRole(listRoleDTO);
        return new PageResult<>(list);
    }

    @Override
    public String addRole(AddRoleDTO addRoleDTO) {
        String roleId = DaoHelper.getSeq();
        RolePO rolePO = new RolePO();
        BeanUtils.copyProperties(rolePO,addRoleDTO);
        rolePO.setRoleId(roleId);
        rolePO.setRoleStatus(1);
        rolePO.setRoleCode(Pinyin4jUtil.getPinYin(addRoleDTO.getRoleName()));
        this.roleMapper.addRole(rolePO);
        addRoleAuthority(roleId, addRoleDTO.getAuthorityIdList());
        return roleId;
    }

    /**
     * 添加角色- 权限关联
     * @param roleId
     * @param authorityIdList
     */
    private void addRoleAuthority(String roleId, String authorityIdList){
        List<String> authorityList = Arrays.asList(authorityIdList.split(","));
        List<AddRoleAuthorityDTO> addRoleAuthorityDTOList = new ArrayList<>();
        authorityList.forEach(x -> {
            AddRoleAuthorityDTO addRoleAuthorityDTO = new AddRoleAuthorityDTO();
            addRoleAuthorityDTO.setAuthorityId(x);
            addRoleAuthorityDTO.setRoleId(roleId);
            addRoleAuthorityDTO.setSid(DaoHelper.getSeq());
            addRoleAuthorityDTOList.add(addRoleAuthorityDTO);
        });
        this.authorityService.addRoleAuthority(addRoleAuthorityDTOList);
    }

    @Override
    public void updateRole(UpdateRoleDTO updateRoleDTO) {
        RolePO rolePO = new RolePO();
        BeanUtils.copyProperties(rolePO, updateRoleDTO);
        this.roleMapper.updateRole(rolePO);
        //权限-角色关联处理
        if(!StringUtils.isBlank(updateRoleDTO.getAuthorityIdList())){
            this.authorityService.deleteRoleAuthorityByRole(updateRoleDTO.getRoleId());
            addRoleAuthority(updateRoleDTO.getRoleId(), updateRoleDTO.getAuthorityIdList());
        }
    }

    @Override
    public void addUserRole(String roleIdList, String foreignId, Integer roleType) {
        List<String> roleList = Arrays.asList(roleIdList.split(","));
        List<AddRoleUserDTO> list = new ArrayList<>();
        roleList.forEach(x ->{
            AddRoleUserDTO addRoleUserDTO = new AddRoleUserDTO();
            addRoleUserDTO.setForeignId(foreignId);
            addRoleUserDTO.setRoleId(x);
            addRoleUserDTO.setRoleType(roleType);
            addRoleUserDTO.setSid(DaoHelper.getSeq());
            list.add(addRoleUserDTO);
        });
        this.roleMapper.addRoleUser(list);
    }

    @Override
    public RoleVO getRole(String roleId) {
        RoleVO roleVO=new RoleVO();
        RolePO role = this.roleMapper.getRole(roleId);
        if(null!=role){
            BeanUtils.copyProperties(roleVO,role);
            List<AuthorityPO> aList = authorityService.listAuthority(1, 1, role.getRoleType());

            List<String> roleList=new ArrayList<>();
            roleList.add(role.getRoleId());
            List<AuthorityPO> rList = authorityService.listRoleAuthority(roleList);

            List<Map<String,Object>> atuList=new ArrayList<>();

            for (int i = 0; i <aList.size() ; i++) {
                Map<String,Object> map=new HashMap<>();
                map.put("checked",false);
                AuthorityPO authorityA = aList.get(i);
                for (int j = 0; j <rList.size() ; j++) {
                    AuthorityPO authorityR = rList.get(j);
                    if(authorityA.getSid().equals(authorityR.getSid())){
                        map.put("checked",true);
                        break;
                    }
                }
                map.put("id",authorityA.getSid());
                map.put("pId",authorityA.getPid());
                map.put("name",authorityA.getDescription());
                atuList.add(map);
            }
            roleVO.setAuthorityList(atuList);
        }
        return roleVO;
    }

    @Override
    public List<RolePO> listUserRole(String foreignId, Integer roleType,Integer roleStatus) {
        return this.roleMapper.listUserRole(foreignId, roleType, roleStatus);
    }

    @Override
    public void updateUserRole(String foreignId, String roleIdString, Integer roleType) {
        this.roleMapper.deleteUserRole(foreignId, roleType);
        addUserRole(roleIdString, foreignId, roleType);
    }

    @Override
    public void delRole(String roleId,Integer roleType) {
//        删除角色表中角色
        this.roleMapper.delRole(roleId);
        //删除用户角色
        this.roleMapper.deleteUserRole(roleId,roleType);
        //删除角色-权限关联
        this.authorityService.deleteRoleAuthorityByRole(roleId);
    }
}
