package com.comvee.cdms.sign.job;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.sign.po.SugarMonthReportPO;
import com.comvee.cdms.sign.service.SugarMonthReportService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SugarMonthReportPushJob{

    private final static Logger log  = LoggerFactory.getLogger(SugarMonthReportPushJob.class);

    @Autowired
    private SugarMonthReportService sugarMonthReportService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @XxlJob("sugarMonthReportPushJob")
    public ReturnT<String> execute(String param) throws Exception {
        List<SugarMonthReportPO> list = null;
        while (true){
           list = this.sugarMonthReportService.listPushSugarMonthReport(100);
           if(list == null || list.isEmpty()){
               break;
           }
           doPush(list);
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 执行推送
     * @param list
     */
    private void doPush(List<SugarMonthReportPO> list){
        List<String> idList = new ArrayList<>();
        list.forEach(x->{
            try{
                pushReport(x);
            }catch (Exception e){
                log.error("推送月度控糖报告失败,失败报告id:{}" , x.getReportId() ,e);
            }
            idList.add(x.getReportId());
        });
        this.sugarMonthReportService.batchUpdateSugarMonthReportPushStatus(idList);
    }

    /**
     * 推送报告
     * @param sugarMonthReportPO
     */
    private void pushReport(SugarMonthReportPO sugarMonthReportPO){
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(sugarMonthReportPO.getMemberId());
        addWechatMessageDTO.setForeignId(sugarMonthReportPO.getReportId());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_SUGAR_MONTH_REPORT);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date" , sugarMonthReportPO.getInsertDt().substring(0 , 10));
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }
}
