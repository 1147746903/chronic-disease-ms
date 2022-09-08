package com.comvee.cdms.statistics.job;

import com.comvee.cdms.statistics.service.MemberExceptionScreenService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author linr
 * @Date 2022/2/23
 */
@Component
public class MemberExceptionScreenInfoJob {

    @Autowired
    private MemberExceptionScreenService memberExceptionScreenService;

    private final static Logger log = LoggerFactory.getLogger(MemberExceptionScreenInfoJob.class);

    @XxlJob("MemberExceptionScreenInfoJob")
    public ReturnT<String> execute(String param) throws Exception {
        log.info("患者异常信息处理任务开始执行...");
        try {
            memberExceptionScreenService.memberExceptionInfoHandler();
        }catch (Exception e){
            log.error("患者异常信息处理任务执行失败！",e);
        }
        log.info("患者异常信息处理任务完成...");
        return ReturnT.SUCCESS;
    }
}
