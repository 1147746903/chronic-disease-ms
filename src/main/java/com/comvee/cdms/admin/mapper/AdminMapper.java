package com.comvee.cdms.admin.mapper;

import com.comvee.cdms.admin.model.dto.ListAdminDTO;
import com.comvee.cdms.admin.model.po.AdminPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/16
 */
public interface AdminMapper {

    /**
     * 新增管理员
     * @param adminPO
     */
    void addAdmin(AdminPO adminPO);

    /**
     * 修改管理员
     * @param adminPO
     */
    void updateAdmin(AdminPO adminPO);

    /**
     * 加载管理员列表
     * @param listAdminDTO
     * @return
     */
    List<AdminPO> listAdmin(ListAdminDTO listAdminDTO);

    /**
     * 根据id获取管理员
     * @param adminId
     * @param accountNo
     * @return
     */
    AdminPO getAdmin(@Param("adminId") String adminId,@Param("accountNo") String accountNo);
}
