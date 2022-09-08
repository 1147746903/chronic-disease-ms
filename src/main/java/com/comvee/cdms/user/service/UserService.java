package com.comvee.cdms.user.service;

import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.dto.*;
import com.comvee.cdms.user.po.UserPO;
import com.comvee.cdms.user.po.UserRelationPO;

/**
 * @author: suyz
 * @date: 2018/9/28
 */
public interface UserService {


    /**
     * 获取用户
     * @param getUserDTO
     * @return
     */
    UserPO getUser(GetUserDTO getUserDTO);

    /**
     * 修改密码
     * @param dto
     * @param uid
     */
    void updatePassword(PasswordDTO dto,String uid);


    /**
     * 添加用户
     * @param addUserDTO
     * @return
     */
    String addUser(AddUserDTO addUserDTO);


    /**
     * 修改用户
     * @param updateUserDTO
     */
    void updateUser(UpdateUserDTO updateUserDTO);


    /**
     * 根据第三方唯一标识和第三方用户的工作号获取关联用户信息
     * @param principalId
     * @param thirdId
     * @return
     */
    DoctorSessionBO getUserByWorkNo(String principalId, String thirdId,String lastLoginIPAddress);

    /**
     * 判断账号是否需要短信验证
     * @param userName
     * @return
     */
    boolean checkSmsVerify(String userName);

    void updatePasswordByUserName(PasswordWithUserNameDTO param);


}
