package com.comvee.cdms.user.service;

import com.comvee.cdms.user.dto.AddWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.GetWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.UpdateWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.WechatLoginDTO;
import com.comvee.cdms.user.po.UserWechatJoinPO;

/**
 * @author: suyz
 * @date: 2018/10/31
 */
public interface UserJoinInfoService {

    /**
     * 获取微信绑定信息
     * @param getWechatJoinMapperDTO
     * @return
     */
    UserWechatJoinPO getUserWechatJoin(GetWechatJoinMapperDTO getWechatJoinMapperDTO);

    /**
     * 新增微信平台绑定数据
     * @param addWechatJoinMapperDTO
     */
    String addUserWechatJoinWithLock(AddWechatJoinMapperDTO addWechatJoinMapperDTO);

    /**
     * 修改微信平台绑定信息
     * @param updateWechatJoinMapperDTO
     */
    void updateUserWechatJoin(UpdateWechatJoinMapperDTO updateWechatJoinMapperDTO);


    /**
     * 绑定患者
     * @param openId
     * @param memberId
     */
    void bindMember(String openId, String memberId ,String qrCodeId ,String idCard);

    String getWxPhoneNumber(String code);

    String getQrcodeEventJumpUrl(String qrCodeId);
}
