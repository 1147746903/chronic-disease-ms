package com.comvee.cdms.doctor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.doctor.dto.AddMemberHealthWarningNoticeDTO;
import com.comvee.cdms.doctor.dto.ListDoctorDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorNoticeService;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.dto.ListMemberJoinHospitalDTO;
import com.comvee.cdms.member.po.MemberJoinHospitalPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberManageService;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class DoctorNoticeServiceImpl implements DoctorNoticeService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private MemberManageService memberManageService;

    @Autowired
    private DoctorServiceI doctorService;

    @Override
    public void memberHealthWarningNotice(AddMemberHealthWarningNoticeDTO param) {
        List<String> doctorList = listNeedHealthWarningNoticeDoctor(param.getMemberId());
        if(doctorList == null || doctorList.isEmpty()){
            return;
        }
        String memberInfo = getHealthWarningMemberInfo(param.getMemberId());
        JSONObject dataJson = new JSONObject();
        dataJson.put("datetime" ,param.getDatetime());
        dataJson.put("warningTitle" ,param.getWarningTitle());
        dataJson.put("warningContent" ,param.getWarningContent());
        dataJson.put("memberInfo" ,memberInfo);
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_HEALTH_WARN);
        addWechatMessageDTO.setForeignId(param.getForeignId());
        addWechatMessageDTO.setDataJson(dataJson.toJSONString());
        addWechatMessageDTO.setUserType(WechatMessageConstant.USER_TYPE_DOCTOR);
        for(String doctorId : doctorList){
            addWechatMessageDTO.setMemberId(doctorId);
            this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
        }
    }

    private List<String> listNeedHealthWarningNoticeDoctor(String memberId){
        ListMemberJoinHospitalDTO listMemberJoinHospitalDTO = new ListMemberJoinHospitalDTO();
        listMemberJoinHospitalDTO.setMemberId(memberId);
        List<MemberJoinHospitalPO> list = this.memberManageService.listMemberJoinHospital(listMemberJoinHospitalDTO);
        if(list == null || list.isEmpty()){
            return null;
        }
        List<String> hospitalIdList = list.stream().map(MemberJoinHospitalPO::getHospitalId).collect(Collectors.toList());
        ListDoctorDTO listDoctorDTO = new ListDoctorDTO();
        listDoctorDTO.setHospitalIdList(hospitalIdList);
        List<DoctorPO> doctorList = this.doctorService.listDoctorOne(listDoctorDTO);
        return doctorList.stream()
                .filter(x -> !StringUtils.isBlank(x.getUnionId()))
                .map(DoctorPO::getDoctorId)
                .collect(Collectors.toList());
    }

    private String getHealthWarningMemberInfo(String memberId){
        MemberPO member = this.memberService.getMemberById(memberId);
        StringJoiner memberInfo = new StringJoiner(",");
        if(!StringUtils.isBlank(member.getAddress())){
            String address = member.getAddress().replace("福建省南平市政和县" ,"");
            if(!StringUtils.isBlank(address)){
                memberInfo.add(address);
            }
        }
        memberInfo.add(member.getMemberName());
        if(!StringUtils.isBlank(member.getMobilePhone())){
            memberInfo.add("手机号:" + member.getMobilePhone());
        }
        return memberInfo.toString();
    }
}
