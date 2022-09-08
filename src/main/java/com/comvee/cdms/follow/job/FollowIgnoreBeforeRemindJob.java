package com.comvee.cdms.follow.job;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.cfg.FollowRemindConstant;
import com.comvee.cdms.follow.dto.ListFollowRemindDTO;
import com.comvee.cdms.follow.po.FollowRemindPO;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.github.pagehelper.PageHelper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**自动忽略到期的提前提醒
 * @author wyc
 * @date 2019/9/18 9:11
 */
@Component
public class FollowIgnoreBeforeRemindJob{


    private static final Logger logger = LoggerFactory.getLogger(FollowIgnoreBeforeRemindJob.class);

    private static final int PAGE_SIZE = 100;

    @Autowired
    private FollowServiceI followService;

    @XxlJob("followIgnoreBeforeRemindJob")
    public ReturnT<String> execute(String param) throws Exception {
        int pageNum = 1;
        PageResult<FollowRemindPO> pageResult = null;
        do {
            pageResult = listData(pageNum);
            startHandler(pageResult.getRows());
            pageNum ++;
        }while (pageResult.getTotalPages() > pageNum - 1);
        return ReturnT.SUCCESS;
    }

    /**
     * 加载提前提醒数据
     * @param pageNum
     * @return
     */
    private PageResult<FollowRemindPO> listData(int pageNum){
        PageHelper.startPage(pageNum,PAGE_SIZE);
        ListFollowRemindDTO dto = new ListFollowRemindDTO();
        dto.setType(FollowRemindConstant.REMIND_TQTX);
        List<FollowRemindPO> list = this.followService.listFollowRemind(dto);
        return new PageResult<FollowRemindPO>(list);
    }

    /**
     * 开始处理数据
     * @param list
     */
    private void startHandler(List<FollowRemindPO> list){
        if (null == list || list.size() <= 0){
            return;
        }
        list.forEach(x ->{
            try {
                startIgnoreRemind(x);
            } catch (Exception e) {
                logger.error("忽略随访提醒失败`id:{}",x.getId(),e);
            }
        });
    }

    private void startIgnoreRemind(FollowRemindPO followRemindPO){
        String today = DateHelper.getToday();
        String remindDay = followRemindPO.getInsertDt().substring(0,10);
        String beforeDay = followRemindPO.getBeforeDay();
        int day = DateHelper.dateCompareGetDay(today,remindDay);
        if (day >= Integer.parseInt(beforeDay)){
            FollowRemindPO remindPO = new FollowRemindPO();
            remindPO.setId(followRemindPO.getId());
            remindPO.setIsDo("1");
            this.followService.modifyFollowRemind(remindPO);
        }
    }
}
