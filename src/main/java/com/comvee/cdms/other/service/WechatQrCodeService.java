package com.comvee.cdms.other.service;

import com.comvee.cdms.other.dto.CreateMiniStrQrCodeDTO;
import com.comvee.cdms.other.dto.CreateStrQrCodeDTO;
import com.comvee.cdms.other.dto.GetWechatQrCodeDTO;
import com.comvee.cdms.other.po.WechatQrcodePO;

/**
 * @author: suyz
 * @date: 2019/3/5
 */
public interface WechatQrCodeService {

    /**
     * 生成临时的字符串二维码
     * @param createStrQrCodeDTO
     * @return
     */
    WechatQrcodePO createTemporaryStrQrCode(CreateStrQrCodeDTO createStrQrCodeDTO);

    /**
     * 生成渠道临时的字符串二维码
     * @param createStrQrCodeDTO
     * @return
     */
    WechatQrcodePO createChannelTemporaryStrQrCode(CreateStrQrCodeDTO createStrQrCodeDTO);

    /**
     * 生成永久的字符串二维码
     * @param createStrQrCodeDTO
     * @return
     */
    WechatQrcodePO createForeverStrQrCode(CreateStrQrCodeDTO createStrQrCodeDTO);

    /**
     * 获取二维码
     * @param sceneValue
     * @param businessType
     * @param foreignId
     * @return
     */
    WechatQrcodePO getWechatQrCode(GetWechatQrCodeDTO getWechatQrCodeDTO);

    /**
     * 生成小程序二维码
     * @param createStrQrCodeDTO
     * @return
     */
    WechatQrcodePO createForeverStrMiniQrCode(CreateMiniStrQrCodeDTO createStrQrCodeDTO) throws Exception;


    /**
     * 获取未过期二维码
     * @param getWechatQrCodeDTO
     * @return
     */
    WechatQrcodePO getUnexpiredWechatQrCode(GetWechatQrCodeDTO getWechatQrCodeDTO);
}
