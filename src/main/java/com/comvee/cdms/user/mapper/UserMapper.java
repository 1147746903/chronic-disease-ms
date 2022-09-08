package com.comvee.cdms.user.mapper;

import com.comvee.cdms.user.dto.GetUserDTO;
import com.comvee.cdms.user.dto.UpdateUserDTO;
import com.comvee.cdms.user.dto.UserRelationDTO;
import com.comvee.cdms.user.po.UserPO;
import com.comvee.cdms.user.po.UserRelationPO;

/**
 * @author: suyz
 * @date: 2018/9/29
 */
public interface UserMapper {

    /**
     * 获取用户信息
     * @param getUserDTO
     * @return
     */
    UserPO getUser(GetUserDTO getUserDTO);

    /**
     * 修改用户
     * @param updateUserDTO
     */
    void updateUser(UpdateUserDTO updateUserDTO);

    /**
     * 添加用户
     * @param userPO
     */
    void addUser(UserPO userPO);

}
