package com.comvee.cdms.other.job;

import com.comvee.cdms.other.service.WechatMessageService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: suyz
 * @date: 2018/12/5
 */
@Component
public class WechatMessageHistoryJob{

    @Autowired
    private WechatMessageService wechatMessageService;

    @XxlJob("wechatMessageHistoryJob")
    public ReturnT<String> execute(String param) throws Exception {
        this.wechatMessageService.moveMessageToHistory();
        return ReturnT.SUCCESS;
    }
}
