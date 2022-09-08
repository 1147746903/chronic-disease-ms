package com.comvee.cdms.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.ServerHostConstant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.HttpUtils;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.service.ThirdSyncMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/4/12
 */
@Service("thirdSyncMemberService")
public class ThirdSyncMemberServiceImpl implements ThirdSyncMemberService {

    @Autowired
    private MemberService memberService;

    @Override
    public void syncMemberInfo(String memberId) {
        MemberPO memberPO = this.memberService.getMemberByIdCache(memberId);
        if(memberPO == null){
            throw new BusinessException("需要同步的患者不存在");
        }
        Map<String ,String> paramMap = new HashMap<>();
        paramMap.put("memberId" ,memberPO.getMemberId());
        paramMap.put("memberName" ,memberPO.getMemberName());
        paramMap.put("sex", String.valueOf(memberPO.getSex()));
        paramMap.put("picUrl", memberPO.getPicUrl());
        String url = ServerHostConstant.COMVEE_DEFENDER_HOST + "syndata/synPatient.do";
        String res = HttpUtils.doPost(url ,paramMap);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if(jsonObject == null ||
                !"0".equals(jsonObject.getString("code"))){
            throw new BusinessException("同步患者信息错误");
        }
    }
}
