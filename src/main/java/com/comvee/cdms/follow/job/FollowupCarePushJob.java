package com.comvee.cdms.follow.job;

import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.po.FollowupCarePO;
import com.comvee.cdms.follow.service.FollowupCareService;
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
 * @date: 2019/7/24
 */
@Component
public class FollowupCarePushJob{

    private final static Logger log = LoggerFactory.getLogger(FollowupCarePushJob.class);

    private final static int ROWS = 20;

    @Autowired
    private FollowupCareService followupCareService;

    @XxlJob("followupCarePushJob")
    public ReturnT<String> execute(String param) throws Exception {
        PageResult pageResult = null;
        int page = 1;
        do{
            pageResult = this.followupCareService.listToBeSendFollowupCare(page ,ROWS);
            startPush(pageResult.getRows());
            page ++;
        }while (pageResult.getTotalPages() >= page);
        return ReturnT.SUCCESS;
    }

    /**
     * 开始推送
     * @param list
     */
    private void startPush(List<FollowupCarePO> list){
        if(list == null || list.size() == 0){
            return;
        }
        list.forEach(x ->{
            try{
                this.followupCareService.sendFollowupCareToWechat(x);
                FollowupCarePO followupCarePO = new FollowupCarePO();
                followupCarePO.setSendStatus(1);
                followupCarePO.setSid(x.getSid());
                this.followupCareService.updateFollowupCare(followupCarePO);
            }catch (Exception e){
                log.warn("随访关怀推送处理失败,sid:{}" , x.getSid() , e);
            }
        });
    }

}
