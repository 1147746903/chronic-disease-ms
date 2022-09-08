package com.comvee.cdms.complication.job;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.model.po.ScreeningListPO;
import com.comvee.cdms.complication.service.ScreeningService;
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
 * 筛查复诊提醒
 */
@Component
public class ScreeningReturnVisitRemindJob {

    private static final Logger log = LoggerFactory.getLogger(ScreeningReturnVisitRemindJob.class);

    private static final int ROWS = 20;

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @XxlJob("screeningReturnVisitRemind")
    public ReturnT<String> execute(String param) throws Exception {
        int page = 1;
        PageResult<ScreeningListPO> pageResult = null;
        do{
            pageResult = this.screeningService.listScreeningReturnVisitRemind(page , ROWS);
            startHandler(pageResult.getRows());
            page ++;
        }while (page <= pageResult.getTotalPages());
        return ReturnT.SUCCESS;
    }

    /**
     * 开始处理
     * @param list
     */
    private void startHandler(List<ScreeningListPO> list){
        if(list == null || list.size() == 0){
           return;
        }
        list.forEach(x ->{
            try{
                doHandler(x);
            }catch (Exception e){
                log.error("筛查复诊提醒失败" ,e);
            }

        });
    }

    /**
     * 执行处理
     * @param screeningListPO
     */
    private void doHandler(ScreeningListPO screeningListPO){
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(screeningListPO.getMemberId());
        addWechatMessageDTO.setForeignId(screeningListPO.getScreeningId());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_SCREENING_RETURN_VISIT_REMIND);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctorId", screeningListPO.getDoctorId());
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }
}
