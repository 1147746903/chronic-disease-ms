package com.comvee.cdms.user.mapper;

import com.comvee.cdms.user.dto.AddWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.GetWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.UpdateWechatJoinMapperDTO;
import com.comvee.cdms.user.po.UserWechatJoinPO;

/**
 * @author: suyz
 * @date: 2018/10/31
 */
public interface UserJoinMapper {

    /**
     * 获取微信绑定信息
     * @param getWechatJoinMapperDTO
     * @return
     */
    UserWechatJoinPO getUserWechatJoinInfo(GetWechatJoinMapperDTO getWechatJoinMapperDTO);

    /**
     * 新增微信平台绑定数据
     * @param addWechatJoinMapperDTO
     */
    void addUserWechatJoinInfo(AddWechatJoinMapperDTO addWechatJoinMapperDTO);

    /**
     * 修改微信平台绑定信息
     * @param updateWechatJoinMapperDTO
     */
    void updateUserWechatJoinInfo(UpdateWechatJoinMapperDTO updateWechatJoinMapperDTO);
}
