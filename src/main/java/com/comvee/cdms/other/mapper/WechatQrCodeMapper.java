package com.comvee.cdms.other.mapper;

import com.comvee.cdms.other.dto.GetWechatQrCodeDTO;
import com.comvee.cdms.other.po.WechatQrcodePO;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
public interface WechatQrCodeMapper {

    /**
     * 新增微信二维码
     * @param wechatQrcodePO
     */
    void addWechatQrCode(WechatQrcodePO wechatQrcodePO);

    /**
     * 获取二维码
     * @param sceneValue
     * @param businessType
     * @param foreignId
     * @return
     */
    WechatQrcodePO getWechatQrCode(GetWechatQrCodeDTO getWechatQrCodeDTO);

    WechatQrcodePO getUnexpiredWechatQrCode(GetWechatQrCodeDTO getWechatQrCodeDTO);
}
