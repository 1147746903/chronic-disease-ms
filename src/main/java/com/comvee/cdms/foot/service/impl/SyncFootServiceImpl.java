package com.comvee.cdms.foot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.foot.bo.FootPrescriptionQrCodeBO;
import com.comvee.cdms.foot.constant.FootConstant;
import com.comvee.cdms.foot.mapper.FootMapper;
import com.comvee.cdms.foot.po.FootPO;
import com.comvee.cdms.foot.po.FootReportPO;
import com.comvee.cdms.foot.service.FootService;
import com.comvee.cdms.foot.service.SyncFootService;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatQrCodeConstant;
import com.comvee.cdms.other.dto.CreateStrQrCodeDTO;
import com.comvee.cdms.other.dto.GetWechatQrCodeDTO;
import com.comvee.cdms.other.po.WechatQrcodePO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.other.service.WechatQrCodeService;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2019/4/23
 */
@Service("SyncFootService")
public class SyncFootServiceImpl implements SyncFootService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private FootMapper footMapper;

    @Autowired
    private WechatQrCodeService wechatQrCodeService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private FootService footService;

    /**
     * 已下发的足部处方
     */
    private final static String FOOT_PRESCRIPTION_SAVE_TYPE_SUCCESS = "3";

    @Override
    public String syncFootPrescription(String doctorId, String prescriptionFootJson) {
        List<FootPO> list = assembleList(prescriptionFootJson);
        list.forEach(x ->{
            FootPO footPO = this.footMapper.getFoot(x.getFollowId());
            if(footPO == null){
                x.setDoctorId(doctorId);
                x.setTeamId(doctorId);
                x.setFootType(String.valueOf(FootConstant.FOOT_TYPE_ZCJ));
                this.footMapper.addFoot(x);
                if(FOOT_PRESCRIPTION_SAVE_TYPE_SUCCESS.equals(x.getSaveType())){
                    this.footService.addFootPrescriptionWechatMessage(x,"sys_foot");
                }
            }else{
                this.footMapper.modifyFoot(x);
                if(FOOT_PRESCRIPTION_SAVE_TYPE_SUCCESS.equals(x.getSaveType())
                        && !FOOT_PRESCRIPTION_SAVE_TYPE_SUCCESS.equals(footPO.getSaveType())){
                    this.footService.addFootPrescriptionWechatMessage(x,"sys_foot");
                }
            }
        });
        List<String> idList = list.stream().map(FootPO::getFollowId).collect(Collectors.toList());
        return Joiner.on(",").join(idList);
    }

    @Override
    public FootPrescriptionQrCodeBO createFootPrescriptionQrCode(String prescriptId) {
        GetWechatQrCodeDTO getWechatQrCodeDTO = new GetWechatQrCodeDTO();
        getWechatQrCodeDTO.setForeignId(prescriptId);
        getWechatQrCodeDTO.setBusinessType(WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_FOOT_PRESCRIPTION);
        WechatQrcodePO wechatQrcodePO = this.wechatQrCodeService.getWechatQrCode(getWechatQrCodeDTO);
        if(wechatQrcodePO == null){
            CreateStrQrCodeDTO createStrQrCodeDTO = new CreateStrQrCodeDTO();
            createStrQrCodeDTO.setDescription("足部筛查报告二维码");
            createStrQrCodeDTO.setBusinessType(WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_FOOT_PRESCRIPTION);
            createStrQrCodeDTO.setForeignId(prescriptId);
            createStrQrCodeDTO.setExpireSeconds(WechatQrCodeConstant.MAX_EXPIRE_SECONDS);
            wechatQrcodePO = this.wechatQrCodeService.createTemporaryStrQrCode(createStrQrCodeDTO);
        }
        FootPrescriptionQrCodeBO footPrescriptionQrCodeBO = new FootPrescriptionQrCodeBO();
        footPrescriptionQrCodeBO.setQrCodeData(wechatQrcodePO.getDataUrl());
        footPrescriptionQrCodeBO.setQrCodeInvalidDt(wechatQrcodePO.getInvalidDt());
        return footPrescriptionQrCodeBO;
    }

    @Override
    public String syncFootReportRelate(String footRelateJson) {
        List<FootReportPO> reportList = JSON.parseArray(footRelateJson ,FootReportPO.class);
        reportList.forEach(x ->{
            FootReportPO f = this.footMapper.getFootReportById(x.getSid());
            if(f == null){
                this.footMapper.addFootReportRelate(x);
            }else{
                this.footMapper.updateFootReportRelate(x);
            }
        });
        List<String> idList = reportList.stream().map(FootReportPO::getSid).collect(Collectors.toList());
        return Joiner.on(",").join(idList);
    }


    /**
     * 组装数据
     * @param prescriptionFootJson
     * @return
     */
    private List<FootPO> assembleList(String prescriptionFootJson){
        List<FootPO> list = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(prescriptionFootJson);
        Iterator<Object> iterator = jsonArray.iterator();
        FootPO footPO;
        while(iterator.hasNext()){
            JSONObject jsonObject = (JSONObject) iterator.next();
            footPO = new FootPO();
            footPO.setAssessItem(jsonObject.getString("assessItem"));
            footPO.setAssessResult(jsonObject.getString("assessResult"));
            footPO.setAssistCheck(jsonObject.getString("assistCheck"));
            footPO.setFollowId(jsonObject.getString("prescriptId"));
            footPO.setHasRelation(jsonObject.getInteger("hasRelation"));
            footPO.setInsertDt(jsonObject.getString("insertDt"));
            footPO.setSaveType(jsonObject.getString("saveType"));
            footPO.setModifyDt(jsonObject.getString("modifyDt"));
            footPO.setRecordDt(jsonObject.getString("recordDt"));
            footPO.setIsValid(jsonObject.getString("valid"));
            footPO.setDoctorName(jsonObject.getString("doctorName"));
            footPO.setQrCodeData(jsonObject.getString("qrCodeData"));
            footPO.setQrCodeInvalidDt(jsonObject.getString("qrCodeInvalidDt"));
            MemberPO memberPO = this.memberService.getMemberByIdCard(jsonObject.getString("idCard"));
            if(memberPO == null){
                continue;
            }
            footPO.setMemberId(memberPO.getMemberId());
            list.add(footPO);
        }
        return list;
    }


}
