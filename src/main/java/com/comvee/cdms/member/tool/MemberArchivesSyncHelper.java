package com.comvee.cdms.member.tool;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.dto.MemberArchivesDTO;
import com.comvee.cdms.member.dto.UpdateMemberDTO;
import com.comvee.cdms.member.service.MemberService;

import java.util.HashMap;
import java.util.Map;

public class MemberArchivesSyncHelper {

    /**
     * 患者档案信息更新
     *
     * @param valKey
     * @param value
     * @param archiveKey
     */
    public static void memberArchivesSync(String valKey, Object value, String archiveKey,
                                          MemberService memberService,String memberId,String doctorId){
        if(StringUtils.isBlank(archiveKey)||StringUtils.isBlank(valKey)
                ||StringUtils.isBlank(memberId)||StringUtils.isBlank(doctorId)){
            return;
        }
        if(value!=null&&memberService!=null){
            valKey = valKey.toLowerCase().trim();
            JSONObject archiveItem = new JSONObject();
            archiveItem.put(archivesKeyMap.get(valKey),value);
            JSONObject archives= new JSONObject();
            archives.put(archiveKey ,archiveItem);

            String memberArchives = JSONObject.toJSONString(archives);
            //同步档案
            MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
            memberArchivesDTO.setMemberId(memberId);
            memberArchivesDTO.setDoctorId(doctorId);
            memberArchivesDTO.setArchivesJson(memberArchives);
            memberService.updateMemberArchive(memberArchivesDTO);
            //同步基本信息
            syncBasicMemberPO(memberId,valKey,value,memberService);

/*            MemberArchivesPO memberArchivesPO = memberService.getMemberArchives(memberId,doctorId);
            String memberArchives = memberArchivesPO.getArchivesJson();
            if(!StringUtils.isBlank(memberArchives)){
                JSONObject jsonObject = JSONObject.parseObject(memberArchives);
                if(jsonObject!=null){
                    JSONObject archiveItem = jsonObject.getJSONObject(archiveKey);
                    if(archiveItem!=null){
                        archiveItem.put(archivesKeyMap.get(valKey),value);
                        jsonObject.put(archiveKey,archiveItem);
                    }
                }
                memberArchives = JSONObject.toJSONString(jsonObject);
                //同步档案
                MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
                memberArchivesDTO.setMemberId(memberId);
                memberArchivesDTO.setDoctorId(doctorId);
                memberArchivesDTO.setArchivesJson(memberArchives);
                memberService.updateMemberArchive(memberArchivesDTO);
                //同步基本信息
                syncBasicMemberPO(memberId,valKey,value,memberService);
            }*/
        }
    }

    private static void syncBasicMemberPO(String memberId,String valKey,Object value,MemberService memberService){
        UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
        updateMemberDTO.setMemberId(memberId);
        if("height".equals(signKeyMap.get(valKey))){
            updateMemberDTO.setHeight(value.toString());
            memberService.updateMember(updateMemberDTO);
        } else if("weight".equals(signKeyMap.get(valKey))){
            updateMemberDTO.setWeight(value.toString());
            memberService.updateMember(updateMemberDTO);
        } else if("bmi".equals(signKeyMap.get(valKey))){
            updateMemberDTO.setBmi(value.toString());
            memberService.updateMember(updateMemberDTO);
        } else if("sbp".equals(signKeyMap.get(valKey))){
            updateMemberDTO.setSbpPressure(value.toString());
            memberService.updateMember(updateMemberDTO);
        } else if("dbp".equals(signKeyMap.get(valKey))){
            updateMemberDTO.setDbpPressure(value.toString());
            memberService.updateMember(updateMemberDTO);
        }else if("heart_rate".equals(signKeyMap.get(valKey))){
            updateMemberDTO.setHreatRate(value.toString());
            memberService.updateMember(updateMemberDTO);
        }
    }

    /**
     * 基本信息key
     */
    private static final Map<String,String> signKeyMap = new HashMap<>();
    static {
        signKeyMap.put("身高","height");
        signKeyMap.put("体重","weight");
        signKeyMap.put("bmi","bmi");
        signKeyMap.put("舒张压","dbp");
        signKeyMap.put("收缩压","sbp");
        signKeyMap.put("静息心率","heart_rate");
        signKeyMap.put("血压记录时间","bloodPressureDt");
    }

    /**
     * 档案信息key
     */
    private static final Map<String,String> archivesKeyMap = new HashMap<>();
    static {
        archivesKeyMap.putAll(signKeyMap);
        archivesKeyMap.put("腰围","waistline");
        archivesKeyMap.put("臀围","hipline");
        archivesKeyMap.put("腰臀比","whr");
        archivesKeyMap.put("糖化血红蛋白","lab_hba");
    }
}
