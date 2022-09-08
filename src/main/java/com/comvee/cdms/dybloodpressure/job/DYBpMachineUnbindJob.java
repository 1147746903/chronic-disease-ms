package com.comvee.cdms.dybloodpressure.job;

import com.comvee.cdms.dybloodpressure.service.DyBpMachineService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author linr
 * @Date 2022/2/24
 * 动态血压设备自动解绑任务
 */
@Component
public class DYBpMachineUnbindJob{

    private final static Logger log = LoggerFactory.getLogger(DYBpMachineUnbindJob.class);

    @Autowired
    private DyBpMachineService dyBpMachineService;

    @XxlJob("DYBpMachineUnbindJob")
    public ReturnT<String> execute(String param) throws Exception {
        dyBpMachineService.autoUnbindMemberMachineHandler();
        return ReturnT.SUCCESS;
    }
}
