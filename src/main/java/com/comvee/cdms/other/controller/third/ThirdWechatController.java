package com.comvee.cdms.other.controller.third;

import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.other.constant.WechatQrCodeConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.dto.CreateStrQrCodeDTO;
import com.comvee.cdms.other.dto.ThirdWechatMessageTemplateDTO;
import com.comvee.cdms.other.po.WechatQrcodePO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.other.service.WechatQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2019/4/10
 */
@RestController
@RequestMapping("/third/wechat")
public class ThirdWechatController {

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private WechatQrCodeService wechatQrCodeService;

    /**
     * 第三发发送模板消息
     * @param thirdWechatMessageTemplateDTO
     * @return
     */
    @RequestMapping("/sendMessageTemplate")
    public Result sendMessageTemplate(@Validated ThirdWechatMessageTemplateDTO thirdWechatMessageTemplateDTO){
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        BeanUtils.copyProperties(addWechatMessageDTO ,thirdWechatMessageTemplateDTO);
        addWechatMessageDTO.setForeignId(Constant.DEFAULT_FOREIGN_ID);
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
        return Result.ok();
    }

    /**
     * 生成s281血糖仪设备的二维码
     * @param dxid
     * @return
     */
    @RequestMapping("/createS281QrCode")
    public Result createS281QrCode(String dxid){
        ValidateTool.checkParameterIsNull("设备号不允许为空" ,dxid);
        CreateStrQrCodeDTO createStrQrCodeDTO = new CreateStrQrCodeDTO();
        createStrQrCodeDTO.setBusinessType(WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_S281);
        createStrQrCodeDTO.setDescription("s281血糖仪二维码");
        createStrQrCodeDTO.setForeignId(dxid);
        createStrQrCodeDTO.setExpireSeconds(WechatQrCodeConstant.MAX_EXPIRE_SECONDS);
        createStrQrCodeDTO.setUploadOSS(true);
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeService.createTemporaryStrQrCode(createStrQrCodeDTO);
        return Result.ok(wechatQrcodePO.getQrcodeUrl());
    }
}
