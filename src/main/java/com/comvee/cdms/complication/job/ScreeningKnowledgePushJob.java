package com.comvee.cdms.complication.job;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.model.po.ScreeningKnowledgePO;
import com.comvee.cdms.complication.model.po.ScreeningMemberKnowledgeDetailPO;
import com.comvee.cdms.complication.model.po.ScreeningMemberKnowledgePO;
import com.comvee.cdms.complication.service.ScreeningKnowledgeService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/3/7
 */
@Component
public class ScreeningKnowledgePushJob{

    private final static Logger log = LoggerFactory.getLogger(ScreeningKnowledgePushJob.class);

    private final static int ROWS = 20;

    @Autowired
    private ScreeningKnowledgeService screeningKnowledgeService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @XxlJob("screeningKnowledgePushJob")
    public ReturnT<String> execute(String param) throws Exception {
        int page = 1;
        PageResult<ScreeningMemberKnowledgePO> pageResult = null;
        do{
            pageResult = this.screeningKnowledgeService.listScreeningKnowledgePushMember(page , ROWS);
            startHandler(pageResult.getRows());
            page ++;
        }while (page <= pageResult.getTotalPages());
        return ReturnT.SUCCESS;
    }

    /**
     * 开始处理
     * @param list
     */
    private void startHandler(List<ScreeningMemberKnowledgePO> list){
        if(list == null || list.size() == 0){
            return;
        }
        list.forEach(x -> {
            try{
                doHandler(x);
            }catch (Exception e){
                log.error("筛查知识学习推送失败~" ,e);
            }

        });
    }

    /**
     * 执行处理
     * @param screeningMemberKnowledgePO
     */
    private void doHandler(ScreeningMemberKnowledgePO screeningMemberKnowledgePO){
        ScreeningKnowledgePO screeningKnowledgePO = this.screeningKnowledgeService.getNextScreeningKnowledge(screeningMemberKnowledgePO.getLastSerialNumber());
        if(screeningKnowledgePO == null){
            //TODO 结束学习周期
            return;
        }
        //添加详情
        ScreeningMemberKnowledgeDetailPO screeningMemberKnowledgeDetailPO = new ScreeningMemberKnowledgeDetailPO();
        screeningMemberKnowledgeDetailPO.setMemberId(screeningMemberKnowledgePO.getMemberId());
        screeningMemberKnowledgeDetailPO.setKnowledgeId(screeningKnowledgePO.getSid());
        screeningMemberKnowledgeDetailPO.setSerialNumber(screeningKnowledgePO.getSerialNumber());
        this.screeningKnowledgeService.addScreeningMemberKnowledgeDetail(screeningMemberKnowledgeDetailPO);

        //添加微信模板消息
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setForeignId(screeningKnowledgePO.getSid());
        addWechatMessageDTO.setMemberId(screeningMemberKnowledgePO.getMemberId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serialNumber" ,screeningKnowledgePO.getSerialNumber());
        jsonObject.put("summaryText" ,screeningKnowledgePO.getSummaryText());
        jsonObject.put("titleName" ,screeningKnowledgePO.getTitleName());
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_SCREENING_KNOWLEDGE_PUSH);
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);

        //修改最后学习知识序号
        screeningMemberKnowledgePO.setLastSerialNumber(screeningKnowledgePO.getSerialNumber());
        this.screeningKnowledgeService.updateScreeningMemberKnowledge(screeningMemberKnowledgePO);
    }
}
