package com.comvee.cdms.other.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.other.constant.WechatQrCodeConstant;
import com.comvee.cdms.other.dto.CreateMiniStrQrCodeDTO;
import com.comvee.cdms.other.dto.CreateStrQrCodeDTO;
import com.comvee.cdms.other.dto.GetWechatQrCodeDTO;
import com.comvee.cdms.other.mapper.WechatQrCodeMapper;
import com.comvee.cdms.other.po.WechatQrcodePO;
import com.comvee.cdms.other.service.WechatQrCodeService;
import com.comvee.cdms.upload.tool.UploadUtils;
import com.comvee.cdms.wechat.api.WechatQrCodeApi;
import com.comvee.cdms.wechat.api.WechatTokenApi;
import com.comvee.cdms.wechat.config.WechatAppName;
import com.comvee.cdms.wechat.model.WechatQrCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author: suyz
 * @date: 2019/3/5
 */
@Service("wechatQrCodeService")
public class WechatQrCodeServiceImpl implements WechatQrCodeService {


    private final static Logger log = LoggerFactory.getLogger(WechatQrCodeServiceImpl.class);

    @Autowired
    private WechatQrCodeMapper wechatQrCodeMapper;


    @Override
    public WechatQrcodePO createTemporaryStrQrCode(CreateStrQrCodeDTO createStrQrCodeDTO) {
        GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
        getWechatQrCodeDTO.setBusinessType(createStrQrCodeDTO.getBusinessType());
        getWechatQrCodeDTO.setForeignId(createStrQrCodeDTO.getForeignId());
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeMapper.getWechatQrCode(getWechatQrCodeDTO);
        if(wechatQrcodePO != null && !checkQrCodeExpire(wechatQrcodePO)){
            return wechatQrcodePO;
        }
        String sceneStr = UUID.randomUUID().toString();
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO);
        WechatQrCode wechatQrCode = WechatQrCodeApi.createTemporaryStrQrCode(accessToken , sceneStr , createStrQrCodeDTO.getExpireSeconds());
        wechatQrcodePO = new WechatQrcodePO();
        wechatQrcodePO.setActionName(WechatQrCodeApi.ACTION_NAME_QR_STR_SCENE);
        wechatQrcodePO.setBusinessType(createStrQrCodeDTO.getBusinessType());
        wechatQrcodePO.setDataUrl(wechatQrCode.getUrl());
        wechatQrcodePO.setDataJson(createStrQrCodeDTO.getDataJson());
        wechatQrcodePO.setDescription(createStrQrCodeDTO.getDescription());
        wechatQrcodePO.setForeignId(createStrQrCodeDTO.getForeignId());
        wechatQrcodePO.setExpireSeconds(new Long(createStrQrCodeDTO.getExpireSeconds()).intValue());
        wechatQrcodePO.setQrType(WechatQrCodeConstant.QR_CODE_TYPE_TEMPORARY);
        wechatQrcodePO.setSid(DaoHelper.getSeq());
        wechatQrcodePO.setInvalidDt(getInvalidDt(createStrQrCodeDTO.getExpireSeconds()));
        wechatQrcodePO.setSceneValue(sceneStr);
        if (createStrQrCodeDTO.getOrigin() == null){
            wechatQrcodePO.setOrigin(WechatQrCodeConstant.QR_CODE_ORIGIN_ONE);
        }else {
            wechatQrcodePO.setOrigin(createStrQrCodeDTO.getOrigin());
        }
        qrCodeUploadOSSHandler(wechatQrCode ,createStrQrCodeDTO ,wechatQrcodePO);
        this.wechatQrCodeMapper.addWechatQrCode(wechatQrcodePO);
        return wechatQrcodePO;
    }

    @Override
    public WechatQrcodePO createChannelTemporaryStrQrCode(CreateStrQrCodeDTO createStrQrCodeDTO) {
        GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
        getWechatQrCodeDTO.setBusinessType(createStrQrCodeDTO.getBusinessType());
        getWechatQrCodeDTO.setForeignId(createStrQrCodeDTO.getForeignId());
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeMapper.getWechatQrCode(getWechatQrCodeDTO);
        if(wechatQrcodePO != null){
            return wechatQrcodePO;
        }
        String sceneStr = UUID.randomUUID().toString();
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.CHANNEL_GONG_ZHONG_HAO);
        WechatQrCode wechatQrCode = WechatQrCodeApi.createTemporaryStrQrCode(accessToken , sceneStr , createStrQrCodeDTO.getExpireSeconds());
        wechatQrcodePO = new WechatQrcodePO();
        wechatQrcodePO.setActionName(WechatQrCodeApi.ACTION_NAME_QR_STR_SCENE);
        wechatQrcodePO.setBusinessType(createStrQrCodeDTO.getBusinessType());
        wechatQrcodePO.setDataUrl(wechatQrCode.getUrl());
        wechatQrcodePO.setDataJson(createStrQrCodeDTO.getDataJson());
        wechatQrcodePO.setDescription(createStrQrCodeDTO.getDescription());
        wechatQrcodePO.setForeignId(createStrQrCodeDTO.getForeignId());
        wechatQrcodePO.setExpireSeconds(new Long(createStrQrCodeDTO.getExpireSeconds()).intValue());
        wechatQrcodePO.setQrType(WechatQrCodeConstant.QR_CODE_TYPE_TEMPORARY);
        wechatQrcodePO.setSid(DaoHelper.getSeq());
        wechatQrcodePO.setInvalidDt(getInvalidDt(createStrQrCodeDTO.getExpireSeconds()));
        wechatQrcodePO.setSceneValue(sceneStr);
        if (createStrQrCodeDTO.getOrigin() == null){
            wechatQrcodePO.setOrigin(WechatQrCodeConstant.QR_CODE_ORIGIN_ONE);
        }else {
            wechatQrcodePO.setOrigin(createStrQrCodeDTO.getOrigin());
        }
        qrCodeUploadOSSHandler(wechatQrCode ,createStrQrCodeDTO ,wechatQrcodePO);
        this.wechatQrCodeMapper.addWechatQrCode(wechatQrcodePO);
        return wechatQrcodePO;
    }

    /**
     * 二维码图片上传oss处理
     * @param wechatQrCode
     * @param createStrQrCodeDTO
     * @param wechatQrcodePO
     */
    private void qrCodeUploadOSSHandler(WechatQrCode wechatQrCode ,CreateStrQrCodeDTO createStrQrCodeDTO ,WechatQrcodePO wechatQrcodePO){
        if(createStrQrCodeDTO.getUploadOSS() == null || createStrQrCodeDTO.getUploadOSS() == false){
            return;
        }
        try {
            byte[] bytes = WechatQrCodeApi.showQrCode(wechatQrCode.getTicket());
            String url = UploadUtils.uploadFile(bytes ,"jpg");
            wechatQrcodePO.setQrcodeUrl(url);
        } catch (Exception e) {
            log.error("图片上传失败" , e);
        }
    }

    /**
     * 计算失效时间
     * @param expireSeconds
     * @return
     */
    private String getInvalidDt(long expireSeconds){
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.plusSeconds(expireSeconds);
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    @Override
    public WechatQrcodePO createForeverStrQrCode(CreateStrQrCodeDTO createStrQrCodeDTO) {
        GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
        getWechatQrCodeDTO.setBusinessType(createStrQrCodeDTO.getBusinessType());
        getWechatQrCodeDTO.setForeignId(createStrQrCodeDTO.getForeignId());
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeMapper.getWechatQrCode(getWechatQrCodeDTO);
        if(wechatQrcodePO != null){
            return wechatQrcodePO;
        }
        String sceneStr = UUID.randomUUID().toString();
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO);
        WechatQrCode wechatQrCode = WechatQrCodeApi.createForeverStrQrCode(accessToken , sceneStr);
        wechatQrcodePO = new WechatQrcodePO();
        wechatQrcodePO.setActionName(WechatQrCodeApi.ACTION_NAME_QR_LIMIT_STR_SCENE);
        wechatQrcodePO.setBusinessType(createStrQrCodeDTO.getBusinessType());
        wechatQrcodePO.setDataUrl(wechatQrCode.getUrl());
        wechatQrcodePO.setDataJson(createStrQrCodeDTO.getDataJson());
        wechatQrcodePO.setDescription(createStrQrCodeDTO.getDescription());
        wechatQrcodePO.setForeignId(createStrQrCodeDTO.getForeignId());
        wechatQrcodePO.setExpireSeconds(new Long(createStrQrCodeDTO.getExpireSeconds()).intValue());
        wechatQrcodePO.setQrType(WechatQrCodeConstant.QR_CODE_TYPE_FOREVER);
        wechatQrcodePO.setSid(DaoHelper.getSeq());
        wechatQrcodePO.setInvalidDt("2099-12-12 23:59:59");
        wechatQrcodePO.setSceneValue(sceneStr);
        if (createStrQrCodeDTO.getOrigin() == null){
            wechatQrcodePO.setOrigin(WechatQrCodeConstant.QR_CODE_ORIGIN_ONE);
        }else {
            wechatQrcodePO.setOrigin(createStrQrCodeDTO.getOrigin());
        }
        qrCodeUploadOSSHandler(wechatQrCode ,createStrQrCodeDTO ,wechatQrcodePO);
        this.wechatQrCodeMapper.addWechatQrCode(wechatQrcodePO);
        return wechatQrcodePO;
    }

    @Override
    public WechatQrcodePO getWechatQrCode(GetWechatQrCodeDTO getWechatQrCodeDTO) {
        return this.wechatQrCodeMapper.getWechatQrCode(getWechatQrCodeDTO);
    }

    @Override
    public WechatQrcodePO createForeverStrMiniQrCode(CreateMiniStrQrCodeDTO createStrQrCodeDTO) throws Exception {
        GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
        getWechatQrCodeDTO.setBusinessType(createStrQrCodeDTO.getBusinessType());
        getWechatQrCodeDTO.setForeignId(createStrQrCodeDTO.getForeignId());
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeMapper.getWechatQrCode(getWechatQrCodeDTO);
        if(wechatQrcodePO != null){
            return wechatQrcodePO;
        }
        String sceneStr = UUID.randomUUID().toString();
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.MINI_PROGRAM);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page",createStrQrCodeDTO.getPagePath());
        jsonObject.put("width",createStrQrCodeDTO.getWidth());
        jsonObject.put("scene",createStrQrCodeDTO.getParams());
        WechatQrCode wechatQrCode = WechatQrCodeApi.createTemporaryStrMiniQrCode(accessToken , jsonObject);

        wechatQrcodePO = new WechatQrcodePO();
        wechatQrcodePO.setActionName(WechatQrCodeApi.ACTION_NAME_QR_STR_SCENE);
        wechatQrcodePO.setBusinessType(createStrQrCodeDTO.getBusinessType());
        wechatQrcodePO.setDataUrl(wechatQrCode.getUrl());
        wechatQrcodePO.setQrcodeUrl(wechatQrCode.getUrl());
        wechatQrcodePO.setDataJson(JSONObject.toJSONString(createStrQrCodeDTO.getParams()));
        wechatQrcodePO.setDescription(createStrQrCodeDTO.getDescription());
        wechatQrcodePO.setForeignId(createStrQrCodeDTO.getForeignId());
        wechatQrcodePO.setExpireSeconds(new Long(createStrQrCodeDTO.getExpireSeconds()).intValue());
        wechatQrcodePO.setQrType(WechatQrCodeConstant.QR_CODE_TYPE_FOREVER);
        wechatQrcodePO.setSid(DaoHelper.getSeq());
        wechatQrcodePO.setInvalidDt("2099-12-12 23:59:59");
        wechatQrcodePO.setSceneValue(sceneStr);
        this.wechatQrCodeMapper.addWechatQrCode(wechatQrcodePO);
        return wechatQrcodePO;
    }

    /**
     *  获取未过期
     * @param getWechatQrCodeDTO
     * @return
     */
    @Override
    public WechatQrcodePO getUnexpiredWechatQrCode(GetWechatQrCodeDTO getWechatQrCodeDTO) {
        return wechatQrCodeMapper.getUnexpiredWechatQrCode(getWechatQrCodeDTO);
    }

    /**
     * 判断二维码是否过期
     * @param wechatQrcode
     * @return 过期返回true
     */
    private boolean checkQrCodeExpire(WechatQrcodePO wechatQrcode){
        return DateHelper.getNowDate().compareTo(wechatQrcode.getInvalidDt()) > 0;
    }
}
