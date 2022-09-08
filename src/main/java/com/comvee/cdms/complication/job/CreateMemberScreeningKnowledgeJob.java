package com.comvee.cdms.complication.job;

import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.model.po.ScreeningListPO;
import com.comvee.cdms.complication.model.po.ScreeningMemberKnowledgePO;
import com.comvee.cdms.complication.service.ScreeningKnowledgeService;
import com.comvee.cdms.complication.service.ScreeningService;
import com.xxl.job.core.biz.model.ReturnT;
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
public class CreateMemberScreeningKnowledgeJob{

    private final static Logger log = LoggerFactory.getLogger(CreateMemberScreeningKnowledgeJob.class);

    private final static int ROWS = 20;

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private ScreeningKnowledgeService screeningKnowledgeService;

    @XxlJob("createMemberScreeningKnowledgeJob")
    public ReturnT<String> execute(String param) throws Exception {
        int page = 1;
        PageResult<ScreeningListPO> pageResult = null;
        do{
            pageResult = this.screeningService.listLastWeekScreening(page , ROWS);
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
        list.forEach(x -> {
            doHandler(x);
        });
    }

    /**
     * 执行处理
     * @param screeningListPO
     */
    private void doHandler(ScreeningListPO screeningListPO){
        ScreeningMemberKnowledgePO screeningMemberKnowledgePO = this.screeningKnowledgeService.getScreeningMemberKnowledgeByMemberId(screeningListPO.getMemberId());
        if(screeningMemberKnowledgePO != null){
            return;
        }
        screeningMemberKnowledgePO = new ScreeningMemberKnowledgePO();
        screeningMemberKnowledgePO.setMemberId(screeningListPO.getMemberId());
        screeningMemberKnowledgePO.setLastSerialNumber(-1);
        this.screeningKnowledgeService.addScreeningMemberKnowledge(screeningMemberKnowledgePO);
    }
}
