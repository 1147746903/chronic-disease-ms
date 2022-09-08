package com.comvee.cdms.admin.service;

import com.comvee.cdms.admin.model.dto.AddAdminDTO;
import com.comvee.cdms.admin.model.dto.AdminLoginDTO;
import com.comvee.cdms.admin.model.dto.ListAdminDTO;
import com.comvee.cdms.admin.model.dto.UpdateAdminDTO;
import com.comvee.cdms.admin.model.po.AdminPO;
import com.comvee.cdms.admin.model.vo.AdminVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

/**
 * @author: suyz
 * @date: 2019/1/15
 */
public interface AdminService {

    /**
     * 加载管理员列表
     * @param listAdminDTO
     * @param pr
     * @return
     */
    PageResult<AdminVO> listAdmin(ListAdminDTO listAdminDTO, PageRequest pr);

    /**
     * 添加管理员
     * @param adminPO
     * @return
     */
    String addAdmin(AddAdminDTO addAdminDTO);

    /**
     * 修改管理员
     * @param adminPO
     */
    void updateAdmin(UpdateAdminDTO updateAdminDTO);

    /**
     * 获取admin
     * @param adminId
     * @return
     */
    AdminPO getAdmin(String adminId);

    /**
     * 管理员登录
     * @param accountNo
     * @param password
     * @return
     */
    AdminPO login(AdminLoginDTO loginDTO);

    /**
     * 修改套餐
     * @param adminId
     * @param oldPassword
     * @param newPassword
     */
    void updatePassword(String adminId, String oldPassword, String newPassword);

    /**
     * 获取管理员
     * @param adminId
     * @return
     */
    AdminPO getAdminById(String adminId);

    boolean checkSmsVerify(String userName);

    void updatePasswordWithAccount(String accountNo, String oldPassword, String newPassword);
}
