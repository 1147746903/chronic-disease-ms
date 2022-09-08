package com.comvee.cdms.defender.wechat.job;

import com.comvee.cdms.defender.wechat.mapper.DailyQuestionPushMapper;
import com.comvee.cdms.defender.wechat.service.DailyQuestionServiceI;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author linr
 * @Date 2021/11/30
 */
@Component
public class DailyQuestionPushJob{

    @Autowired
    private DailyQuestionServiceI dailyQuestionServiceI;

    @Autowired
    private DailyQuestionPushMapper dailyQuestionPushMapper;

    private final static Logger log = LoggerFactory.getLogger(DailyQuestionPushJob.class);

    @XxlJob("DailyQuestionPushJob")
    public ReturnT<String> execute(String param) throws Exception {
        log.info("开始处理每日一答推送....");
        List<String> list = dailyQuestionPushMapper.listAllPushMembers();
        for (String  memberId: list) {
            String qid = dailyQuestionServiceI.dailyQuestionGenerate(memberId);
        }
        return ReturnT.SUCCESS;
    }
}
