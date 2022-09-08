package com.comvee.cdms.checkresult.job;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.constant.CheckRemindCategory;
import com.comvee.cdms.checkresult.dto.ListMemberCheckRemindDTO;
import com.comvee.cdms.checkresult.po.CheckRemindPO;
import com.comvee.cdms.checkresult.service.CheckRemindService;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.google.common.base.Joiner;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckRemindJob{

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private CheckRemindService checkRemindService;

    private final static int ROWS = 100;

    @XxlJob("checkRemindJob")
    public ReturnT<String> execute(String s) throws Exception {
        String reviewDt = getReviewDt();
        int page = 1;
        PageRequest pr = new PageRequest();
        pr.setRows(ROWS);
        PageResult pageResult = null;
        do{
            pr.setPage(page);
            pageResult = this.checkRemindService.listNeedCheckRemindMember(pr ,reviewDt);
            start(pageResult.getRows());
            page ++;
        }while (pageResult.getTotalPages() > (page - 1));
        return ReturnT.SUCCESS;
    }

    private void start(List<String> memberList){
        if(memberList == null || memberList.isEmpty()){
            return;
        }
        for(String id : memberList){
            createRemind(id);
        }
    }

    private void createRemind(String memberId){
        String reviewDt = getReviewDt();
        ListMemberCheckRemindDTO listMemberCheckRemindDTO = new ListMemberCheckRemindDTO();
        listMemberCheckRemindDTO.setMemberId(memberId);
        listMemberCheckRemindDTO.setReviewDt(reviewDt);
        List<CheckRemindPO> remindList = this.checkRemindService.listMemberCheckRemind(listMemberCheckRemindDTO);

        JSONObject dataJson = new JSONObject();
        dataJson.put("checkItem" ,checkRemindItemText(remindList));
        dataJson.put("reviewDt" ,reviewDt);
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(memberId);
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_CHECK_REMIND);
        addWechatMessageDTO.setForeignId(Constant.DEFAULT_FOREIGN_ID);
        addWechatMessageDTO.setDataJson(dataJson.toJSONString());
        addWechatMessageDTO.setUserType(WechatMessageConstant.USER_TYPE_PATIENT);
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    private String getReviewDt(){
        return LocalDateTime.now().plusDays(7L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String checkRemindItemText(List<CheckRemindPO> remindList){
        List<String> result = new ArrayList<>();
        for(CheckRemindPO checkRemind : remindList){
            result.add(CheckRemindCategory.getName(checkRemind.getCheckItem()));
        }
        return Joiner.on("、").join(result);
    }
}
