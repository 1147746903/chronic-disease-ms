package com.comvee.cdms.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.cfg.ServerHostConstant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.DoctorMemberApplyDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberApplyService;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.service.ThirdSyncMemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.constant.WechatQrCodeConstant;
import com.comvee.cdms.other.dto.GetWechatQrCodeDTO;
import com.comvee.cdms.other.po.WechatQrcodePO;
import com.comvee.cdms.other.service.EventMessageService;
import com.comvee.cdms.other.service.WechatQrCodeService;
import com.comvee.cdms.other.tool.WechatMessageTool;
import com.comvee.cdms.packages.dto.AddMemberPackageDTO;
import com.comvee.cdms.packages.dto.ListValidMemberPackageDTO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.user.cfg.UserJoinConstant;
import com.comvee.cdms.user.dto.AddWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.GetWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.UpdateWechatJoinMapperDTO;
import com.comvee.cdms.user.dto.WechatLoginDTO;
import com.comvee.cdms.user.mapper.UserJoinMapper;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.user.service.UserJoinInfoService;
import com.comvee.cdms.wechat.api.WechatMessageApi;
import com.comvee.cdms.wechat.api.WechatPhoneApi;
import com.comvee.cdms.wechat.api.WechatTokenApi;
import com.comvee.cdms.wechat.api.WechatUserApi;
import com.comvee.cdms.wechat.config.WechatAppName;
import com.comvee.cdms.wechat.config.WechatConfig;
import com.comvee.cdms.wechat.model.WechatAppConfig;
import com.comvee.cdms.wechat.model.WechatSession;
import com.comvee.cdms.wechat.model.WechatUserPhone;
import com.comvee.cdms.wechat.utils.AES;
import com.comvee.cdms.wechat.utils.WxPKCS7Encoder;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: suyz
 * @date: 2018/10/31
 */
@Service("userJoinInfoService")
public class UserJoinInfoServiceImpl implements UserJoinInfoService {

    private final static Logger log = LoggerFactory.getLogger(UserJoinInfoServiceImpl.class);

    @Autowired
    private UserJoinMapper userJoinMapper;

    @Autowired
    private WechatQrCodeService wechatQrCodeService;

    @Autowired
    private MemberApplyService memberApplyService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private ThirdSyncMemberService thirdSyncMemberService;

    @Autowired
    private EventMessageService eventMessageService;

    @Autowired
    private MemberService memberService;

    @Override
    public UserWechatJoinPO getUserWechatJoin(GetWechatJoinMapperDTO getWechatJoinMapperDTO) {
        return this.userJoinMapper.getUserWechatJoinInfo(getWechatJoinMapperDTO);
    }

    @Override
    public String addUserWechatJoinWithLock(AddWechatJoinMapperDTO addWechatJoinMapperDTO) {
        //判断是否存在
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        BeanUtils.copyProperties(getWechatJoinMapperDTO, addWechatJoinMapperDTO);
        UserWechatJoinPO wechatJoinInfoPO = getUserWechatJoin(getWechatJoinMapperDTO);
        if(wechatJoinInfoPO != null){
            return wechatJoinInfoPO.getSid();
        }
        //执行新增
        String sid = DaoHelper.getSeq();
        addWechatJoinMapperDTO.setSid(sid);
        if(addWechatJoinMapperDTO.getJoinStatus() == null){
            addWechatJoinMapperDTO.setJoinStatus(UserJoinConstant.JOIN_STATUS_NO);
        }
        this.userJoinMapper.addUserWechatJoinInfo(addWechatJoinMapperDTO);
        return sid;
    }

    @Override
    public void updateUserWechatJoin(UpdateWechatJoinMapperDTO updateWechatJoinMapperDTO) {
        this.userJoinMapper.updateUserWechatJoinInfo(updateWechatJoinMapperDTO);
    }

    @Override
    public void bindMember(String openId, String memberId ,String qrCodeId ,String idCard) {
        checkIdCardInfo(memberId ,idCard);

        GetWechatJoinMapperDTO count = new GetWechatJoinMapperDTO();
        count.setForeignId(memberId);
        count.setJoinType(UserJoinConstant.JOIN_TYPE_MINI_PROGRAM);
        count.setAuthorizedSource(Constant.WECHAT_AUTHORIZED_SOURCE_PATIENT);
        UserWechatJoinPO userWechatJoinPO = getUserWechatJoin(count);
        if(userWechatJoinPO != null){
            throw new BusinessException("该患者已被绑定，请确认");
        }

        UpdateWechatJoinMapperDTO updateWechatJoinMapperDTO = new UpdateWechatJoinMapperDTO();
        updateWechatJoinMapperDTO.setForeignId(memberId);
        updateWechatJoinMapperDTO.setOpenId(openId);
        updateWechatJoinMapperDTO.setJoinStatus(UserJoinConstant.JOIN_STATUS_YES);
        updateUserWechatJoin(updateWechatJoinMapperDTO);

        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setOpenId(openId);
        userWechatJoinPO = getUserWechatJoin(getWechatJoinMapperDTO);

        if(!userWechatJoinPO.getUnionId().equals(DEFAULT_FOREIGN_ID)){
            updateWechatJoinMapperDTO = new UpdateWechatJoinMapperDTO();
            updateWechatJoinMapperDTO.setForeignId(memberId);
            updateWechatJoinMapperDTO.setWhereUnionId(userWechatJoinPO.getUnionId());
            updateWechatJoinMapperDTO.setJoinStatus(UserJoinConstant.JOIN_STATUS_YES);
            updateWechatJoinMapperDTO.setAuthorizedSource(Constant.WECHAT_AUTHORIZED_SOURCE_PATIENT);
            updateUserWechatJoin(updateWechatJoinMapperDTO);
        }

        qrCodeEventHandler(qrCodeId ,memberId, userWechatJoinPO);
    }

    @Override
    public String getWxPhoneNumber(String code) {
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.MINI_PROGRAM);
        WechatUserPhone wechatUserPhone = WechatPhoneApi.getUserPhone(accessToken ,code);
        return wechatUserPhone.getPurePhoneNumber();
    }

    /**
     * 二维码事件处理
     * @param qrCodeId
     */
    private void qrCodeEventHandler(String qrCodeId ,String memberId, UserWechatJoinPO userWechatJoinPO){
        if(StringUtils.isBlank(qrCodeId)){
            return;
        }
        GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
        getWechatQrCodeDTO.setSid(qrCodeId);
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeService.getWechatQrCode(getWechatQrCodeDTO);
        if(wechatQrcodePO == null){
            return;
        }
        switch (wechatQrcodePO.getBusinessType()){
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_DOCTOR:
                bindDoctorHandler(wechatQrcodePO ,memberId);
                break;
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_INTELLIGENT_NURSING:
                intelligentNursingHandler(wechatQrcodePO ,memberId);
                break;
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_MEMBER_SENSOR_SID:
                eventMessageService.bindBloodSugarShareHandler(userWechatJoinPO, wechatQrcodePO, false);
                break;
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_PRODUCT:
                eventMessageService.productQrCodeHandler(userWechatJoinPO, wechatQrcodePO, false);
                break;
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_SENSOR:
                eventMessageService.sensorQrCodeHandler(userWechatJoinPO, wechatQrcodePO, false);
                break;
            default:
                break;
        }
    }

    /**
     * 获取qrCode扫码后跳转地址
     * @param qrCodeId
     * @return
     */
    @Override
    public String getQrcodeEventJumpUrl(String qrCodeId){
        if(StringUtils.isBlank(qrCodeId)){
            return null;
        }
        GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
        getWechatQrCodeDTO.setSid(qrCodeId);
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeService.getWechatQrCode(getWechatQrCodeDTO);
        if(wechatQrcodePO == null){
            return null;
        }
        switch (wechatQrcodePO.getBusinessType()){
            case WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_MEMBER_SENSOR_SID:
                if(StringUtils.isBlank(wechatQrcodePO.getForeignId())) return null;
                String path = "/pages/member/dynamic/dynamic?sensorId=" + wechatQrcodePO.getForeignId();
                return path;
        }
        return null;
    }

    /**
     * 绑定医生处理
     * @param wechatQrcodePO
     * @param memberId
     */
    private void bindDoctorHandler(WechatQrcodePO wechatQrcodePO ,String memberId){
        DoctorMemberApplyDTO doctorMemberApplyDTO = new DoctorMemberApplyDTO();
        doctorMemberApplyDTO.setDoctorId(wechatQrcodePO.getForeignId());
        doctorMemberApplyDTO.setMemberId(memberId);
        doctorMemberApplyDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_WECHAT);
        this.memberApplyService.addDoctorMemberApply(doctorMemberApplyDTO);
    }


    /**
     * 智能服务处理（礼来）
     * @param wechatQrcodePO
     * @param memberId
     */
    private void intelligentNursingHandler(WechatQrcodePO wechatQrcodePO ,String memberId){
        sendIntelligentNursingMessage(memberId);

        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(memberId);
        listValidMemberPackageDTO.setPackageId(wechatQrcodePO.getForeignId());
        int count = this.packageService.listValidMemberPackage(listValidMemberPackageDTO).size();
        if(count > 0){
            return;
        }
        AddMemberPackageDTO addMemberPackageDTO = new AddMemberPackageDTO();
        addMemberPackageDTO.setDoctorId(Constant.DEFAULT_FOREIGN_ID);
        addMemberPackageDTO.setMemberId(memberId);
        addMemberPackageDTO.setPackageId(wechatQrcodePO.getForeignId());
        this.packageService.addMemberPackage(addMemberPackageDTO);

        this.thirdSyncMemberService.syncMemberInfo(memberId);

    }

    /**
     * 发送智能服务处理完成消息（礼来）
     * @param userWechatJoinPO
     */
    private void sendIntelligentNursingMessage(String memberId){
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setForeignId(memberId);
        getWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_GONG_ZHONG_HAO);
        UserWechatJoinPO userWechatJoinPO = getUserWechatJoin(getWechatJoinMapperDTO);
        if(userWechatJoinPO == null){
            return;
        }
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
     * 默认外键
     */
    private static final String DEFAULT_FOREIGN_ID = "-1";

    /**
     * 校验身份证信息
     * @param memberId
     * @param inputIdCard
     */
    private void checkIdCardInfo(String memberId ,String inputIdCard){
        MemberPO member = this.memberService.getMemberById(memberId);
        if(member == null){
            throw new BusinessException("患者不存在，请确认");
        }
        if(StringUtils.isBlank(member.getIdCard())){
            throw new BusinessException("患者没有填写身份证，无法进行验证");
        }
        if(member.getIdCard().length() < 15){
            throw new BusinessException("患者当前填写的身份证非法，无法进行验证");
        }
        if(!member.getIdCard().substring(member.getIdCard().length() - 4).toUpperCase()
                .equals(inputIdCard.toUpperCase())){
            throw new BusinessException("身份证验证失败");
        }
    }
}
