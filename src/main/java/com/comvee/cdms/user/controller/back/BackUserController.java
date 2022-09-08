package com.comvee.cdms.user.controller.back;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.user.dto.GetUserDTO;
import com.comvee.cdms.user.dto.UpdateUserDTO;
import com.comvee.cdms.user.dto.UserRelationDTO;
import com.comvee.cdms.user.po.UserPO;
import com.comvee.cdms.user.po.UserRelationPO;
import com.comvee.cdms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2019/1/21
 */
@RestController
@RequestMapping("/back/user")
//@RequiresRoles("ADMIN")
public class BackUserController {

    @Autowired
    private UserService userService;

    /**
     * 修改用户
     * @param updateUserDTO
     * @return
     */
    @RequestMapping("updateUser")
    public Result updateUser(@Validated UpdateUserDTO updateUserDTO){
        this.userService.updateUser(updateUserDTO);
        return Result.ok();
    }

    /**
     * 获取用户
     * @param getUserDTO
     * @return
     */
    @RequestMapping("getUser")
    public Result getUser(String doctorId){
        ValidateTool.checkParameterIsNull("doctorId", doctorId);
        GetUserDTO getUserDTO=new GetUserDTO();
        getUserDTO.setUid(doctorId);
        UserPO user = this.userService.getUser(getUserDTO);
        return Result.ok(user);
    }

}
