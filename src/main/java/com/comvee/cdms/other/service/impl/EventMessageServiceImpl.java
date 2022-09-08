package com.comvee.cdms.other.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.cfg.ServerHostConstant;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.complication.tool.ScreeningTool;
import com.comvee.cdms.doctor.cfg.GroupConstant;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.dybloodsugar.dto.BindSensorDTO;
import com.comvee.cdms.dybloodsugar.dto.BindShowSensorDTO;
import com.comvee.cdms.dybloodsugar.po.DYMemberSensorPO;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.foot.po.FootPO;
import com.comvee.cdms.foot.service.FootService;
import com.comvee.cdms.machine.model.ThpMemberMachine;
import com.comvee.cdms.machine.service.MemberMachineServiceI;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.AddMemberDoctorRelateDTO;
import com.comvee.cdms.member.dto.CountDoctorMemberDTO;
import com.comvee.cdms.member.dto.UpdateMemberDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.service.ThirdSyncMemberService;
import com.comvee.cdms.other.constant.ClickEventKeyConstant;
import com.comvee.cdms.other.constant.WechatQrCodeConstant;
import com.comvee.cdms.other.dto.GetWechatQrCodeDTO;
import com.comvee.cdms.other.po.WechatQrcodePO;
import com.comvee.cdms.other.service.EventMessageService;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.other.service.WechatQrCodeService;
import com.comvee.cdms.other.tool.WechatMessageTool;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.dto.AddMemberPackageDTO;
import com.comvee.cdms.packages.dto.ListValidMemberPackageDTO;
import com.comvee.cdms.packages.po.PackagePO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.user.cfg.UserJoinConstant;
import com.comvee.cdms.user.dto.AddWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.GetWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.UpdateWechatJoinMapperDTO;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.user.service.UserJoinInfoService;
import com.comvee.cdms.wechat.api.WechatMessageApi;
import com.comvee.cdms.wechat.api.WechatTokenApi;
import com.comvee.cdms.wechat.api.WechatUserApi;
import com.comvee.cdms.wechat.config.WechatAppName;
import com.comvee.cdms.wechat.config.WechatConfig;
import com.comvee.cdms.wechat.config.WechatMessageTemplate;
import com.comvee.cdms.wechat.message.TemplateData;
import com.comvee.cdms.wechat.message.event.*;
import com.comvee.cdms.wechat.message.output.OutputMessage;
import com.comvee.cdms.wechat.model.WechatAppConfig;
import com.comvee.cdms.wechat.model.WechatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author: suyz
 * @date: 2018/11/30
 */
@Service("eventMessageService")
public class EventMessageServiceImpl implements EventMessageService {

    private final static Logger log = LoggerFactory.getLogger(EventMessageServiceImpl.class);

    @Autowired
    private UserJoinInfoService userJoinInfoService;

    @Autowired
    private WechatQrCodeService wechatQrCodeService;

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private ThirdSyncMemberService thirdSyncMemberService;

    @Autowired
    private FootService footService;

    @Autowired
    private DyMemberSensorService dyMemberSensorService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private MemberMachineServiceI memberMachineServiceNew;

    @Autowired
    private DyMemberSensorService memberSensorService;

    @Override
    public OutputMessage subscribe(SubscribeEventMessage msg) {
        boolean hasRegister = registerHandle(msg);
        sendSubscribeMessage(hasRegister, msg.getFromUserName(), null);
        return null;
    }
    private boolean registerHandle(SubscribeEventMessage msg){
        UserWechatJoinPO userWechatJoinPO = getLocalUserWechatInfo(msg.getFromUserName());
        String unionId = userWechatJoinPO.getUnionId();
        //是否注册
        boolean hasRegister = false;
        //根据unionId 查找小程序用户信息
        if(!StringUtils.isBlank(unionId)){
            GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
            getWechatJoinMapperDTO.setUnionId(unionId);
            getWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_MINI_PROGRAM);
            getWechatJoinMapperDTO.setAuthorizedSource(Constant.WECHAT_AUTHORIZED_SOURCE_PATIENT);
            UserWechatJoinPO xcx = this.userJoinInfoService.getUserWechatJoin(getWechatJoinMapperDTO);
            //如果存在小程序用户信息且已经绑定患者id,则更新同步memberId
            if(xcx != null && xcx.getJoinType().equals(String.valueOf(UserJoinConstant.JOIN_STATUS_YES))){
                hasRegister = true;
                UpdateWechatJoinMapperDTO updateWechatJoinMapperDTO = new UpdateWechatJoinMapperDTO();
                updateWechatJoinMapperDTO.setForeignId(xcx.getForeignId());
                updateWechatJoinMapperDTO.setOpenId(msg.getFromUserName());
                updateWechatJoinMapperDTO.setJoinStatus(UserJoinConstant.JOIN_STATUS_YES);
                this.userJoinInfoService.updateUserWechatJoin(updateWechatJoinMapperDTO);
            }
        }
        return hasRegister;
    }


    @Override
    public OutputMessage qrsceneScan(QrsceneScanEventMessage msg) {
        GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
        getWechatQrCodeDTO.setSceneValue(eventKeyHandler(msg.getEventKey()));
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeService.getWechatQrCode(getWechatQrCodeDTO);
        //找不到二维码，默认关注流程
        if(wechatQrcodePO == null){
            return null;
            //筛查报告
        }
        qrSceneHandler(msg.getFromUserName() ,wechatQrcodePO);
        return null;
    }


    @Override
    public OutputMessage location(LocationEventMessage msg) {
        return null;
    }

    @Override
    public OutputMessage click(ClickEventMessage msg) {
        UserWechatJoinPO userWechatJoinPO = getLocalUserWechatInfo(msg.getFromUserName());
        switch (msg.getEventKey()){
            case ClickEventKeyConstant.KEY_1:
                intelligentSugarControlClickHandler(userWechatJoinPO);
                break;
            default:
                break;
        }

        return null;
    }


    @Override
    public OutputMessage view(ViewEventMessage msg) {
        return null;
    }

    @Override
    public OutputMessage scanCodePush(ScanCodePushEventMessage msg) {
        return null;
    }

    @Override
    public OutputMessage scanCodeWaitMsg(ScanCodeWaitMsgEventMessage msg) {
        return null;
    }

    @Override
    public OutputMessage picSysPhoto(PicSysPhotoEventMessage msg) {
        return null;
    }

    @Override
    public OutputMessage picPhotoOrAlbum(PicPhotoOrAlbumEventMessage msg) {
        return null;
    }

    @Override
    public OutputMessage picWeixin(PicWeixinEventMessage msg) {
        return null;
    }

    @Override
    public OutputMessage locationSelect(LocationSelectEventMessage msg) {
        return null;
    }

    /**
     * 智能控糖自定义菜单点击处理
     * @param userWechatJoinPO
     */
    private void intelligentSugarControlClickHandler(UserWechatJoinPO userWechatJoinPO){
        if(userWechatJoinPO.getJoinStatus() == UserJoinConstant.JOIN_STATUS_NO){
            sendNoHascdmsackageMessage(userWechatJoinPO);
            return;
        }
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(userWechatJoinPO.getForeignId());
        listValidMemberPackageDTO.setCodeList(Collections.singletonList(ServiceCode.KE_TANG_XIAO_ZHI_SHI));
        int count = this.packageService.listValidMemberPackage(listValidMemberPackageDTO).size();
        if(count > 0){
            sendIntelligentNursingMessage(userWechatJoinPO);
        }else{
            sendNoHascdmsackageMessage(userWechatJoinPO);
        }
    }

    /**
     * 发送没有智能套餐文案
     * @param userWechatJoinPO
     */
    private void sendNoHascdmsackageMessage(UserWechatJoinPO userWechatJoinPO){
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO);
        String appId = WechatConfig.getWechatAppConfig(WechatAppName.MINI_PROGRAM).getAppId();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("欢迎加入政和县慢病管理中心微信公众号！恭喜您获得以下服务权限：\n");
        stringBuilder.append("<a data-miniprogram-appid=\"").append(appId).append("\" data-miniprogram-path=\"")
                .append(WechatMessageTool.miniProgramPathHandler("/pages-3/remember/rememberSuagr", WechatMessageTool.JUMP_TYPE_PAGE)).append("\">＞记血糖</a>\n\n");
        stringBuilder.append("<a data-miniprogram-appid=\"").append(appId).append("\" data-miniprogram-path=\"")
                .append(WechatMessageTool.miniProgramPathHandler("/pages-3/remember/rememberBloodPre", WechatMessageTool.JUMP_TYPE_PAGE)).append("\">＞记血压</a>\n\n");
        stringBuilder.append("赶快点击以上菜单，体验糖尿病智能关爱服务，让您轻松控糖~");
        WechatMessageApi.sendText(accessToken, userWechatJoinPO.getOpenId(), stringBuilder.toString());
    }

    /**
     * 发送关注信息
     * @param hasRegister
     * @param openId
     */
    private void sendSubscribeMessage(boolean hasRegister, String openId, String qrCodeId){
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO);
        String appId = WechatConfig.getWechatAppConfig(WechatAppName.MINI_PROGRAM).getAppId();
        String text = "";
        if(hasRegister){
            text = "欢迎加入政和县慢病管理中心微信公众号！在这里，您可以体验最贴心的糖尿病关爱服务；接收为您定制的糖尿病智能管理处方方案。点击进入小程序，开启您的健康之旅吧！\n" +
                    "<a data-miniprogram-appid=\"" + appId + "\" data-miniprogram-path=\"pages/jump/jump\"> >>点此进入</a>";
        }else{
            if(qrCodeId != null){
                text = "欢迎加入政和县慢病管理中心微信公众号！在这里，您可以体验最贴心的糖尿病关爱服务；接收为您定制的糖尿病智能管理处方方案。点击注册，开启您的健康之旅吧！\n" +
                        "<a data-miniprogram-appid=\"" + appId +"\" data-miniprogram-path=\"pages/jump/jump?qrCodeId="+ qrCodeId +"\"> >>点击进行注册</a>";
            }else  {
                text = "欢迎加入政和县慢病管理中心微信公众号！在这里，您可以体验最贴心的糖尿病关爱服务；接收为您定制的糖尿病智能管理处方方案。点击注册，开启您的健康之旅吧！\n" +
                        "<a data-miniprogram-appid=\"" + appId + "\" data-miniprogram-path=\"pages/jump/jump\"> >>点击进行注册</a>";
            }
        }
        WechatMessageApi.sendText(accessToken, openId, text);
    }


    /**
     * 发送渠道关注信息
     * @param hasRegister
     * @param openId
     */
    private void sendChannelSubscribeMessage(boolean hasRegister, String openId){
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.CHANNEL_GONG_ZHONG_HAO);
        String appId = WechatConfig.getWechatAppConfig(WechatAppName.CHANNEL_MINI_PROGRAM).getAppId();
        String text = "";
        if(hasRegister){
            text = "欢迎加入城市合伙人微信公众号！在这里，您可以体验最贴心的合伙服务；接收为您定制的合伙方案。点击进入小程序，开启您的合伙之旅吧！\n" +
                    "<a data-miniprogram-appid=\"" + appId +"\" data-miniprogram-path=\"pages/phoneLogin/phoneLogin\"> >>点此进入</a>";
        }else{
            text = "欢迎加入城市合伙人微信公众号！在这里，您可以体验最贴心的合伙服务；接收为您定制的合伙方案。点击注册，开启您的合伙之旅吧！\n" +
                    "<a data-miniprogram-appid=\"" + appId +"\" data-miniprogram-path=\"pages/phoneLogin/phoneLogin\"> >>点击进行注册</a>";
        }
        WechatMessageApi.sendText(accessToken, openId, text);
    }

    /**
     * 获取微信用户本地关联信息
     * @param openId
     * @return
     */
    private UserWechatJoinPO getLocalUserWechatInfo(String openId){
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setOpenId(openId);
        getWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_GONG_ZHONG_HAO);
        getWechatJoinMapperDTO.setAuthorizedSource(Constant.WECHAT_AUTHORIZED_SOURCE_PATIENT);
        UserWechatJoinPO userWechatJoinPO = this.userJoinInfoService.getUserWechatJoin(getWechatJoinMapperDTO);
        if(userWechatJoinPO == null){
            userWechatJoinPO = saveWechatInfo(openId);
        }
        return userWechatJoinPO;
    }


    /**
     * 保存微信信息
     * @param openId
     * @return
     */
    private UserWechatJoinPO saveWechatInfo(String openId){
        WechatAppConfig wechatAppConfig = WechatConfig.getWechatAppConfig(WechatAppName.GONG_ZHONG_HAO);
        WechatUser wechatUser = getWechatUser(openId);
        //新增记录
        UserWechatJoinPO userWechatJoinPO = addPublicNoRecord(openId, wechatAppConfig, wechatUser, Constant.WECHAT_AUTHORIZED_SOURCE_PATIENT);
        return userWechatJoinPO;
    }


    /**
     * 新增微信公众号用户授权信息
     * @param openId
     * @param wechatAppConfig 配置信息
     * @param wechatUser 获取微信信息
     * @param wechatAuthorizedSourcePatient
     * @return
     */
    private UserWechatJoinPO addPublicNoRecord(String openId, WechatAppConfig wechatAppConfig, WechatUser wechatUser, int wechatAuthorizedSourcePatient) {
        AddWechatJoinMapperDTO addWechatJoinMapperDTO = new AddWechatJoinMapperDTO();
        addWechatJoinMapperDTO.setNickName(wechatUser.getNickName());
        addWechatJoinMapperDTO.setAppId(wechatAppConfig.getAppId());
        addWechatJoinMapperDTO.setForeignId(Constant.DEFAULT_FOREIGN_ID);
        addWechatJoinMapperDTO.setOpenId(openId);
        addWechatJoinMapperDTO.setPhotoUrl(wechatUser.getAvatarUrl());
        addWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_GONG_ZHONG_HAO);
        addWechatJoinMapperDTO.setUnionId(wechatUser.getUnionid() == null ? Constant.DEFAULT_FOREIGN_ID : wechatUser.getUnionid());
        addWechatJoinMapperDTO.setAuthorizedSource(wechatAuthorizedSourcePatient);
        unionHandler(addWechatJoinMapperDTO);
        String sid = this.userJoinInfoService.addUserWechatJoinWithLock(addWechatJoinMapperDTO);
        UserWechatJoinPO userWechatJoinPO = new UserWechatJoinPO();
        BeanUtils.copyProperties(userWechatJoinPO, addWechatJoinMapperDTO);
        userWechatJoinPO.setSid(sid);
        return userWechatJoinPO;
    }


    /**
     * 关联小程序处理
     * @param addWechatJoinMapperDTO
     */
    private void unionHandler(AddWechatJoinMapperDTO addWechatJoinMapperDTO){
        if(Constant.DEFAULT_FOREIGN_ID.equals(addWechatJoinMapperDTO.getUnionId())){
            return;
        }
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setUnionId(addWechatJoinMapperDTO.getUnionId());
        getWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_MINI_PROGRAM);
        UserWechatJoinPO userWechatJoinPO = this.userJoinInfoService.getUserWechatJoin(getWechatJoinMapperDTO);
        if(userWechatJoinPO == null){
            return;
        }
        if(UserJoinConstant.JOIN_STATUS_YES != userWechatJoinPO.getJoinStatus()){
            return;
        }
        addWechatJoinMapperDTO.setForeignId(userWechatJoinPO.getForeignId());
        addWechatJoinMapperDTO.setJoinStatus(UserJoinConstant.JOIN_STATUS_YES);
    }

    /**
     * 获取渠道微信信息
     * @param openId
     * @return
     */
    private WechatUser getChannelWechatUser(String openId){
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.CHANNEL_GONG_ZHONG_HAO);
        return WechatUserApi.getUserInfo(accessToken, openId);
    }

    /**
     * 获取微信信息
     * @param openId
     * @return
     */
    private WechatUser getWechatUser(String openId){
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO);
        return WechatUserApi.getUserInfo(accessToken, openId);
    }

    @Override
    public OutputMessage unSubscribe(UnSubscribeEventMessage msg) {
        return null;
    }

    @Override
    public OutputMessage qrsceneSubscribe(QrsceneSubscribeEventMessage msg) {
        //关注流程
        SubscribeEventMessage subscribeEventMessage = new SubscribeEventMessage();
        BeanUtils.copyProperties(subscribeEventMessage, msg);
        boolean hasRegister = registerHandle(subscribeEventMessage);
        GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
        getWechatQrCodeDTO.setSceneValue(eventKeyHandler(msg.getEventKey()));
//        getWechatQrCodeDTO.setOrigin(WechatQrCodeConstant.QR_CODE_ORIGIN_ONE);
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeService.getWechatQrCode(getWechatQrCodeDTO);
        //扫码流程流程
        if(wechatQrcodePO != null) {
            qrSceneHandler(msg.getFromUserName(), wechatQrcodePO);
        }
        return null;
    }

    /**
     * 二维码场景值处理
     * @param openId
     * @param wechatQrcodePO
     */
    private void qrSceneHandler(String openId , WechatQrcodePO wechatQrcodePO){
        UserWechatJoinPO userWechatJoinPO = getLocalUserWechatInfo(openId);
        switch (wechatQrcodePO.getBusinessType()){
            //医生二维码
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_DOCTOR:
                doctorQrCodeHandler(userWechatJoinPO ,wechatQrcodePO);
                break;
            //筛查报告
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_SCREENING_REPORT:
                screeningReportQrCodeHandler(userWechatJoinPO ,wechatQrcodePO);
                break;
            //智能看护(礼来)
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_INTELLIGENT_NURSING:
                intelligentNursingHandler(userWechatJoinPO ,wechatQrcodePO);
                break;
            //筛查-足部处方
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_FOOT_PRESCRIPTION:
                footPrescriptionQrCodeHandler(userWechatJoinPO ,wechatQrcodePO);
                break;
            //s281血糖仪二维码扫描流程
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_S281:
                s281QrCodeHandler(userWechatJoinPO ,wechatQrcodePO);
                break;
                //患者动态血糖传感器分享二维码扫描处理
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_MEMBER_SENSOR_SID:
                bindBloodSugarShareHandler(userWechatJoinPO, wechatQrcodePO, true);
                break;
            //服务包产品二维码扫描流程
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_PRODUCT:
                productQrCodeHandler(userWechatJoinPO ,wechatQrcodePO, true);
                break;
            //探头二维码扫描流程
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_SENSOR:
                sensorQrCodeHandler(userWechatJoinPO ,wechatQrcodePO, true);
                break;
            //模板文本消息二维码处理
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_WX_TEXT_MESSAGE:
                wxTextMessageQrCodeHandler(userWechatJoinPO ,wechatQrcodePO);
                break;
            default:
                break;
        }
    }

    /**
     * 足部处方二维码处理
     * @param userWechatJoinPO
     * @param wechatQrcodePO
     */
    private void footPrescriptionQrCodeHandler(UserWechatJoinPO userWechatJoinPO ,WechatQrcodePO wechatQrcodePO){
        MemberPO memberPO = getMember(userWechatJoinPO);
        boolean hasRegister = memberPO != null;
        sendSubscribeMessage(hasRegister, userWechatJoinPO.getOpenId(), wechatQrcodePO.getSid());
        FootPO footPO = this.footService.getFoot(wechatQrcodePO.getForeignId());
        if(footPO == null){
            return;
        }
        //未绑定则将微信信息与用户绑定
        wechatBindMember(userWechatJoinPO ,footPO.getMemberId());
        sendFootPrescriptionTemplate(userWechatJoinPO ,footPO);
    }

    /**
     * 微信绑定患者
     * @param userWechatJoinPO
     * @param memberId
     */
    private void wechatBindMember(UserWechatJoinPO userWechatJoinPO ,String memberId){
        if(UserJoinConstant.JOIN_STATUS_YES == userWechatJoinPO.getJoinStatus()){
            return;
        }
        UpdateWechatJoinMapperDTO updateWechatJoinMapperDTO = new UpdateWechatJoinMapperDTO();
        updateWechatJoinMapperDTO.setForeignId(memberId);
        updateWechatJoinMapperDTO.setOpenId(userWechatJoinPO.getOpenId());
        updateWechatJoinMapperDTO.setJoinStatus(UserJoinConstant.JOIN_STATUS_YES);
        this.userJoinInfoService.updateUserWechatJoin(updateWechatJoinMapperDTO);
    }

    /**
     * 发送足部处方模板消息
     */
    private void sendFootPrescriptionTemplate(UserWechatJoinPO userWechatJoinPO ,FootPO footPO){
/*        DoctorPO doctorPO = this.doctorService.getDoctorById(footPO.getDoctorId());
        MemberPO memberPO = this.memberService.getMemberById(footPO.getMemberId());
        TemplateData templateData = new TemplateData();
        templateData.setTouser(userWechatJoinPO.getOpenId());
        templateData.setTemplate_id(WechatMessageTemplate.SCREENING_REPORT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first" , "您好，你收到一份糖尿病健康管理报告，请查看~");
        templateDataItem.addItem("keyword1", doctorPO == null ? "-":doctorPO.getHospitalName());
        templateDataItem.addItem("keyword2", memberPO == null ? "-":memberPO.getMemberName());
        templateDataItem.addItem("keyword3", "健康管理报告");
        templateDataItem.addItem("keyword4", footPO.getRecordDt().substring(0 , 10));
        templateDataItem.addItem("remark", "若有疑问请到院咨询~");
        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path, footPO.getFollowId(), "footPrescription");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
        JSONObject jsonObject = WechatMessageApi.templateSend(WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO), templateData);
        log.info("筛查报告模板消息发送结果:" + JSON.toJSONString(jsonObject));*/
    }

    /**
     * 智能看护二维码处理
     * @param userWechatJoinPO
     * @param wechatQrcodePO
     */
    private void intelligentNursingHandler(UserWechatJoinPO userWechatJoinPO ,WechatQrcodePO wechatQrcodePO){
        MemberPO memberPO = getMember(userWechatJoinPO);
        boolean hasRegister = memberPO != null;
        sendSubscribeMessage(hasRegister, userWechatJoinPO.getOpenId(), wechatQrcodePO.getSid());
        PackagePO packagePO = this.packageService.getPackageById(wechatQrcodePO.getForeignId());
        if(packagePO == null){
            return;
        }
        //公众号号信息没有unionId，去注册
        if(Constant.DEFAULT_FOREIGN_ID.equals(userWechatJoinPO.getUnionId())){
            sendRegisterMessage(userWechatJoinPO ,wechatQrcodePO.getSid());
            return;
        }
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setUnionId(userWechatJoinPO.getUnionId());
        getWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_MINI_PROGRAM);
        UserWechatJoinPO xcx = this.userJoinInfoService.getUserWechatJoin(getWechatJoinMapperDTO);
        //没有小程序的授权信息，去注册
        if(xcx == null){
            sendRegisterMessage(userWechatJoinPO ,wechatQrcodePO.getSid());
            return;
        }
        //小程序没有绑定患者，去注册
        if(UserJoinConstant.JOIN_STATUS_NO == xcx.getJoinStatus()){
            sendRegisterMessage(userWechatJoinPO ,wechatQrcodePO.getSid());
            return;
        }
        packageBindHandler(xcx.getForeignId() ,packagePO);
        this.thirdSyncMemberService.syncMemberInfo(xcx.getForeignId());
        sendIntelligentNursingMessage(userWechatJoinPO);
    }

    /**
     * 套餐绑定处理
     * @param memberId
     * @param packagePO
     */
    private void packageBindHandler(String memberId ,PackagePO packagePO){
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(memberId);
        listValidMemberPackageDTO.setPackageId(packagePO.getPackageId());
        int count = this.packageService.listValidMemberPackage(listValidMemberPackageDTO).size();
        if(count > 0){
            return;
        }
        AddMemberPackageDTO addMemberPackageDTO = new AddMemberPackageDTO();
        addMemberPackageDTO.setDoctorId(Constant.DEFAULT_FOREIGN_ID);
        addMemberPackageDTO.setMemberId(memberId);
        addMemberPackageDTO.setPackageId(packagePO.getPackageId());
        this.packageService.addMemberPackage(addMemberPackageDTO);
    }

    /**
     * 发送智能服务处理完成消息（礼来）
     * @param userWechatJoinPO
     */
    private void sendIntelligentNursingMessage(UserWechatJoinPO userWechatJoinPO){
        String knowledgeClassroomUrl = ServerHostConstant.COMVEE_DEFENDER_HOST + "defender/html/classroom/my-classroom.jsp";
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO);
        String appId = WechatConfig.getWechatAppConfig(WechatAppName.MINI_PROGRAM).getAppId();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("欢迎加入政和县慢病管理中心微信公众号！恭喜您获得以下服务权限：\n");
        stringBuilder.append("<a href=\"").append(knowledgeClassroomUrl).append("\">＞＞知识小课堂</a>\n\n");
        stringBuilder.append("<a data-miniprogram-appid=\"").append(appId).append("\" data-miniprogram-path=\"")
                .append(WechatMessageTool.miniProgramPathHandler("/pages-3/remember/rememberSuagr", WechatMessageTool.JUMP_TYPE_PAGE)).append("\">＞记血糖</a>\n\n");
        stringBuilder.append("<a data-miniprogram-appid=\"").append(appId).append("\" data-miniprogram-path=\"")
                .append(WechatMessageTool.miniProgramPathHandler("/pages-3/remember/rememberBloodPre", WechatMessageTool.JUMP_TYPE_PAGE)).append("\">＞记血压</a>\n\n");
        stringBuilder.append("赶快点击以上菜单，体验糖尿病智能关爱服务，让您轻松控糖~");
        WechatMessageApi.sendText(accessToken, userWechatJoinPO.getOpenId(), stringBuilder.toString());
    }



    /**
     * 医生二维码流程处理
     * @param userWechatJoinPO
     * @param wechatQrcodePO
     */
    private void doctorQrCodeHandler(UserWechatJoinPO userWechatJoinPO ,WechatQrcodePO wechatQrcodePO){
        MemberPO memberPO = getMember(userWechatJoinPO);
        boolean hasRegister = memberPO != null;
        sendSubscribeMessage(hasRegister, userWechatJoinPO.getOpenId(), wechatQrcodePO.getSid());
        if(!hasRegister){
            return;
        }
        DoctorPO doctorPO = this.doctorService.getDoctorById(wechatQrcodePO.getForeignId());
        //医生不存在直接返回
        if(doctorPO == null){
            return;
        }

        CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
        countDoctorMemberDTO.setDoctorId(doctorPO.getDoctorId());
        countDoctorMemberDTO.setMemberId(memberPO.getMemberId());
        long number = this.memberService.countMemberDoctor(countDoctorMemberDTO);
        if(number > 0){
            sendExistRelateMessage(userWechatJoinPO);
        }else {
            AddMemberDoctorRelateDTO addMemberDoctorRelateDTO = new AddMemberDoctorRelateDTO();
            addMemberDoctorRelateDTO.setMemberId(memberPO.getMemberId());
            addMemberDoctorRelateDTO.setDoctorId(doctorPO.getDoctorId());
            addMemberDoctorRelateDTO.setOperatorId(doctorPO.getDoctorId());
            addMemberDoctorRelateDTO.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
            addMemberDoctorRelateDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_QRCODE); //医患关系添加方式 扫描二维码
            this.memberService.addMemberDoctorRelate(addMemberDoctorRelateDTO);
            if (doctorPO.getIsAgentDoctor() == 1) {
                UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
                updateMemberDTO.setMemberId(memberPO.getMemberId());
                updateMemberDTO.setMemberTag("AGENT");
                memberService.updateMember(updateMemberDTO);
            }
            sendApplySuccessTemplate(userWechatJoinPO ,doctorPO);
        }
    }

    /**
     * 发送已存在医患关系消息
     * @param userWechatJoinPO
     */
    private void sendExistRelateMessage(UserWechatJoinPO userWechatJoinPO){
        String text = "你已经添加过该医生～";
        sendTextMessage(text ,userWechatJoinPO.getOpenId());
    }

    /**
     * 发送注册的图文消息
     * @param userWechatJoinPO
     */
    private void sendRegisterMessage(UserWechatJoinPO userWechatJoinPO ,String qrCodeId){
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO);
        String appId = WechatConfig.getWechatAppConfig(WechatAppName.MINI_PROGRAM).getAppId();
        String text = "欢迎加入政和县慢病管理中心微信公众号！请先完善您的个人信息；以便为您提供个性化的糖尿病管理服务\n" +
                "<a data-miniprogram-appid=\"" + appId +"\" data-miniprogram-path=\"pages/jump/jump?qrCodeId="+ qrCodeId +"\"> >>点击进行注册</a>";
        WechatMessageApi.sendText(accessToken, userWechatJoinPO.getOpenId(), text);
    }

    /**
     * 添加成功模板消息
     * @param userWechatJoinPO
     * @param doctorPO
     */
    private void sendApplySuccessTemplate(UserWechatJoinPO userWechatJoinPO ,DoctorPO doctorPO){
/*        TemplateData templateData = new TemplateData();
        templateData.setTouser(userWechatJoinPO.getOpenId());
        templateData.setTemplate_id(WechatMessageTemplate.ADD_DOCTOR_SUCCESS_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first" , "您已成功扫码添加医生，医生详情如下");
        templateDataItem.addItem("keyword1",  doctorPO.getDoctorName());
        templateDataItem.addItem("keyword2", DateHelper.getNowDate());
        templateDataItem.addItem("remark", "点击查看医生详情");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/doctor/my_doctor?doctorId={0}&memberId={1}";
        path = MessageTool.format(path, doctorPO.getDoctorId(), userWechatJoinPO.getForeignId());
        path = WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE);
        miniProgramData.setPagepath(path);
        templateData.setMiniprogram(miniProgramData);
        JSONObject jsonObject = WechatMessageApi.templateSend(WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO), templateData);*/

    }


    /**
     * 筛查报告二维码流程处理
     * @param userWechatJoinPO
     * @param wechatQrcodePO
     */
    private void screeningReportQrCodeHandler(UserWechatJoinPO userWechatJoinPO ,WechatQrcodePO wechatQrcodePO){
        MemberPO memberPO = getMember(userWechatJoinPO);
        boolean hasRegister = memberPO != null;
        sendSubscribeMessage(hasRegister, userWechatJoinPO.getOpenId(), wechatQrcodePO.getSid());
        ScreeningReportPO screeningReportPO = this.screeningService.getScreeningReportById(wechatQrcodePO.getForeignId());
        if(screeningReportPO == null){
            return;
        }
        //未绑定则将微信信息与用户绑定
        wechatBindMember(userWechatJoinPO ,screeningReportPO.getMemberId());
        sendScreeningReportTemplate(userWechatJoinPO ,screeningReportPO);
    }

    /**
     * 发送筛查报告模板消息
     */
    private void sendScreeningReportTemplate(UserWechatJoinPO userWechatJoinPO ,ScreeningReportPO screeningReportPO){
/*        DoctorPO doctorPO = this.doctorService.getDoctorById(screeningReportPO.getDoctorId());
        MemberPO memberPO = this.memberService.getMemberById(screeningReportPO.getMemberId());
        TemplateData templateData = new TemplateData();
        templateData.setTouser(userWechatJoinPO.getOpenId());
        templateData.setTemplate_id(WechatMessageTemplate.SCREENING_REPORT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first" , "您有新的检查报告，请及时查看~");
        templateDataItem.addItem("keyword1", doctorPO == null ? "-":doctorPO.getHospitalName());
        templateDataItem.addItem("keyword2", memberPO == null ? "-":memberPO.getMemberName());
        templateDataItem.addItem("keyword3", ScreeningTool.getReportNameByType(screeningReportPO.getScreeningType()));
        templateDataItem.addItem("keyword4", screeningReportPO.getReportDt().substring(0 , 10));
        templateDataItem.addItem("remark", "若有疑问请到院咨询~");
        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path, screeningReportPO.getReportId(), "screeningReport");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
        JSONObject jsonObject = WechatMessageApi.templateSend(WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO), templateData);
        log.info("筛查报告模板消息发送结果:" + JSON.toJSONString(jsonObject));*/
    }



    /**
     * 处理eventKey,获取场景值
     * @param eventKey
     * @return
     */
    private String eventKeyHandler(String eventKey){
        return eventKey.replaceFirst("qrscene_" , "");
    }

    /**
     * s281二维码扫描处理
     * @param userWechatJoinPO
     * @param wechatQrcodePO
     */
    private void s281QrCodeHandler(UserWechatJoinPO userWechatJoinPO ,WechatQrcodePO wechatQrcodePO){
        MemberPO memberPO = getMember(userWechatJoinPO);
        boolean hasRegister = memberPO != null;
        sendSubscribeMessage(hasRegister, userWechatJoinPO.getOpenId(), wechatQrcodePO.getSid());
        if(!hasRegister){
            return;
        }
        if(userWechatJoinPO.getJoinStatus() == null
                || UserJoinConstant.JOIN_STATUS_NO == userWechatJoinPO.getJoinStatus()
                || Constant.DEFAULT_FOREIGN_ID.equals(userWechatJoinPO.getForeignId())){
            String appId = WechatConfig.getWechatAppConfig(WechatAppName.MINI_PROGRAM).getAppId();
            String text = "绑定失败，请先进行注册。\n"
                    + "<a data-miniprogram-appid=\"" + appId +"\" data-miniprogram-path=\"pages/jump/jump\"> >>点击进行注册</a>";
            sendTextMessage(text ,userWechatJoinPO.getOpenId());
            return;
        }
        ThpMemberMachine memberMachine = new ThpMemberMachine();
        memberMachine.setMemberId(userWechatJoinPO.getForeignId());
        memberMachine.setDoctorId(Constant.DEFAULT_FOREIGN_ID);
        memberMachine.setMachineId(MD5Util.md5(wechatQrcodePO.getForeignId()));
        memberMachine.setMachineSn(wechatQrcodePO.getForeignId());
        memberMachine.setMachineType("02");
        Result result = this.memberMachineServiceNew.bindOrUnbindEquipment(memberMachine ,"1");
        if(!result.getSuccess()){
            sendTextMessage(result.getMessage() ,userWechatJoinPO.getOpenId());
        }
    }


    /**
     * 获取member
     * @param userWechatJoinPO
     * @return
     */
    private MemberPO getMember(UserWechatJoinPO userWechatJoinPO){
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setUnionId(userWechatJoinPO.getUnionId());
        getWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_MINI_PROGRAM);
        UserWechatJoinPO xcx = this.userJoinInfoService.getUserWechatJoin(getWechatJoinMapperDTO);
        //没有小程序的授权信息，去注册

        if(xcx == null){
            return null;
        }
        //小程序没有绑定患者，去注册
        if(UserJoinConstant.JOIN_STATUS_NO == xcx.getJoinStatus()){
            return null;
        }
        MemberPO memberPO = this.memberService.getMemberById(userWechatJoinPO.getForeignId());
        //找不到患者，重新注册
        if(memberPO == null){
            return null;
        }
        return memberPO;
    }
    /**
     * 患者动态血糖传感器分享二维码扫描处理 绑定血糖分享,并添加微信消息
     * @param userWechatJoinPO
     * @param wechatQrcodePO
     */
    @Override
    public void bindBloodSugarShareHandler(UserWechatJoinPO userWechatJoinPO, WechatQrcodePO wechatQrcodePO, boolean sendSubscribeMsg){
        MemberPO memberPO = getMember(userWechatJoinPO);
        boolean hasRegister = memberPO != null;
        if(sendSubscribeMsg) {
            sendSubscribeMessage(hasRegister, userWechatJoinPO.getOpenId(), wechatQrcodePO.getSid());
        }
        if(!hasRegister){
            return;
        }
        BindShowSensorDTO dto = new BindShowSensorDTO();
        dto.setMemberSensorSid(wechatQrcodePO.getForeignId());
        dto.setMemberId(memberPO.getMemberId());
        this.dyMemberSensorService.bindShowSensor(dto);
    }

    /**
     * 产品服务包二维码扫描处理
     * @param userWechatJoinPO
     * @param wechatQrcodePO
     */
    @Override
    public void productQrCodeHandler(UserWechatJoinPO userWechatJoinPO, WechatQrcodePO wechatQrcodePO, boolean sendSubscribeMsg){
        MemberPO memberPO = getMember(userWechatJoinPO);
        boolean hasRegister = memberPO != null;
        if(sendSubscribeMsg) {
            sendSubscribeMessage(hasRegister, userWechatJoinPO.getOpenId(), wechatQrcodePO.getSid());
        }
        JSONObject jsonObject = JSONObject.parseObject(wechatQrcodePO.getDataJson());
        String productId = jsonObject.getString("productId");
        String saleId = jsonObject.getString("saleId");
        String appId = WechatConfig.getWechatAppConfig(WechatAppName.MINI_PROGRAM).getAppId();
        String path = "/pages-1/orderServer/newOrder?productId="+ productId +"&saleId="+ saleId;
        String text = "欢迎加入政和县慢病管理中心微信公众号！在这里，您可以体验最贴心的糖尿病健康管理服务；接收定制化糖尿病智能管理处方方案。点击链接购买医生服务，开启您的健康管理之旅吧！\n"
                + "<a data-miniprogram-appid=\"" + appId +"\" data-miniprogram-path=\"" + WechatMessageTool.miniProgramPathHandler(path ,WechatMessageTool.JUMP_TYPE_PAGE) + "\"> >>立刻购买</a>";
        log.info("服务包二维码扫描文本:{}", text);
        sendTextMessage(text ,userWechatJoinPO.getOpenId());
    }

    /**
     * 探头二维码扫描处理
     * @param userWechatJoinPO
     * @param wechatQrcodePO
     */
    @Override
    public void sensorQrCodeHandler(UserWechatJoinPO userWechatJoinPO, WechatQrcodePO wechatQrcodePO, boolean sendSubscribeMsg){
        MemberPO memberPO = getMember(userWechatJoinPO);
        boolean hasRegister = memberPO != null;
        if(sendSubscribeMsg) {
            sendSubscribeMessage(hasRegister, userWechatJoinPO.getOpenId(), wechatQrcodePO.getSid());
        }
        if(!hasRegister){
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(wechatQrcodePO.getDataJson());
        String sensorNo = jsonObject.getString("sensorNo");
        String doctorId = jsonObject.getString("doctorId");
        DYMemberSensorPO memberSensorPO = memberSensorService.getMemberSensorBySensorNo(sensorNo);
        if(memberSensorPO != null){
            sendTextMessage("探头已被绑定" ,userWechatJoinPO.getOpenId());
            return;
        }
        DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
        // 获取医生成功则绑定医生关系
        if(doctorPO != null) {
            CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
            countDoctorMemberDTO.setDoctorId(doctorPO.getDoctorId());
            countDoctorMemberDTO.setMemberId(memberPO.getMemberId());
            long number = this.memberService.countMemberDoctor(countDoctorMemberDTO);
            if (number > 0) {
                UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
                updateMemberDTO.setMemberId(memberPO.getMemberId());
                updateMemberDTO.setMemberTag("AGENT");
                memberService.updateMember(updateMemberDTO);
                sendExistRelateMessage(userWechatJoinPO);
            } else {
                try {
                    AddMemberDoctorRelateDTO addMemberDoctorRelateDTO = new AddMemberDoctorRelateDTO();
                    addMemberDoctorRelateDTO.setMemberId(memberPO.getMemberId());
                    addMemberDoctorRelateDTO.setDoctorId(doctorPO.getDoctorId());
                    addMemberDoctorRelateDTO.setOperatorId(doctorPO.getDoctorId());
                    addMemberDoctorRelateDTO.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
                    addMemberDoctorRelateDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_SENSOR); //医患关系添加方式 扫描二维码
                    this.memberService.addMemberDoctorRelate(addMemberDoctorRelateDTO);
                    UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
                    updateMemberDTO.setMemberId(memberPO.getMemberId());
                    updateMemberDTO.setMemberTag("AGENT");
                    memberService.updateMember(updateMemberDTO);
                    sendApplySuccessTemplate(userWechatJoinPO, doctorPO);
                } catch (Exception ex) {
                    log.error("扫描探头二维码绑定医生失败，doctorId:{}, memberId:{}", doctorId, memberPO.getMemberId());
                }
            }
        }
        BindSensorDTO bindSensorDTO = new BindSensorDTO();
        bindSensorDTO.setSensorNo(sensorNo);
        bindSensorDTO.setMemberId(memberPO.getMemberId());
        bindSensorDTO.setOperationId(memberPO.getMemberId());
        bindSensorDTO.setOperationId("1");
        bindSensorDTO.setBindType(1);
        memberSensorService.bindSensor(bindSensorDTO);
    }

    /**
     * 渠道邀请二维码扫描处理
     * @param userWechatJoinPO
     * @param wechatQrcodePO
     */
    private void inviteQrCodeHandler(UserWechatJoinPO userWechatJoinPO ,WechatQrcodePO wechatQrcodePO){
        JSONObject jsonObject = JSONObject.parseObject(wechatQrcodePO.getDataJson());
        String inviteCode = jsonObject.getString("inviteCode");
        String appId = WechatConfig.getWechatAppConfig(WechatAppName.CHANNEL_MINI_PROGRAM).getAppId();
        String text = "欢迎加入掌控合伙人微信公众号！在这里，您可以体验最贴心的合伙服务；接收定制化合伙方案。点击链接开启您的合伙之旅吧！\n"
                + "<a data-miniprogram-appid=\"" + appId +"\" data-miniprogram-path=\"pages/phoneLogin/phoneLogin?inviteCode="+ inviteCode+"\"> >>立刻加入</a>";
        log.info("邀请二维码扫描文本:{}, 文件路径:{}", text, "pages/phoneLogin/phoneLogin?inviteCode="+ inviteCode);
        sendChannelTextMessage(text ,userWechatJoinPO.getOpenId());
    }

    /**
     * 发送文本消息
     * @param text
     * @param openId
     */
    private void sendTextMessage(String text ,String openId){
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO);
        WechatMessageApi.sendText(accessToken, openId, text);
    }

    /**
     * 发送渠道文本消息
     * @param text
     * @param openId
     */
    private void sendChannelTextMessage(String text ,String openId){
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.CHANNEL_GONG_ZHONG_HAO);
        WechatMessageApi.sendText(accessToken, openId, text);
    }

    /**
     * 微信公众号普通文本消息扫码处理
     * @param user
     * @param qrCode
     */
    private void wxTextMessageQrCodeHandler(UserWechatJoinPO user ,WechatQrcodePO qrCode){
        JSONObject data = JSON.parseObject(qrCode.getDataJson());
        String text = data.getString("text");
        if(StringUtils.isBlank(text)){
            log.warn("模板文本消息扫码处理异常，下发的文本为空！二维码id:{}" ,qrCode.getSid());
            return;
        }
        sendTextMessage(text ,user.getOpenId());
    }
}
