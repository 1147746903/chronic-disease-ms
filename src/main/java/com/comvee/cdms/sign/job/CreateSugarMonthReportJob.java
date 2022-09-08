package com.comvee.cdms.sign.job;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.sign.po.SugarMonthReportPO;
import com.comvee.cdms.sign.service.SugarMonthReportService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CreateSugarMonthReportJob{

    private final static Logger log = LoggerFactory.getLogger(CreateSugarMonthReportJob.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private SugarMonthReportService sugarMonthReportService;

    /**
     * 线程数量，当前服务器核心数 * 2
     */
    private final static int THREAD_NUMBER = Runtime.getRuntime().availableProcessors() * 2;

    private CountDownLatch countDownLatch;

    /**
     * true表示数据源加载完成
     */
    private static volatile boolean loadFinish = false;

    private static ConcurrentLinkedQueue<MemberPO> queue = new ConcurrentLinkedQueue();

    private final static int ROWS = 200;

    @XxlJob("createSugarMonthReportJob")
    public ReturnT<String> execute(String param) throws Exception {
        countDownLatch = new CountDownLatch(THREAD_NUMBER);
        long startTime = System.currentTimeMillis();
        log.info("开始执行创建月度控糖报告任务..");
        loadFinish = false;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);
        try{
            for(int i = 0; i < THREAD_NUMBER; i ++){
                executorService.submit(() ->{
                    createMonthReport();
                });
            }
            int page = 1;
            while(true){
                if(!queue.isEmpty()){
                    continue;
                }
                PageResult<MemberPO> poPageResult = this.memberService.listNeedCreateSugarMonthReportMember(page , ROWS);
                List list = poPageResult.getRows();
                if(list != null && list.size() > 0){
                    queue.addAll(list);
                }else{
                    break;
                }
                page ++;
            }
            loadFinish = true;
            countDownLatch.await();
        }finally {
            executorService.shutdown();
        }

        log.info("月度控糖报告生成任务执行完成，耗时:" + (startTime - System.currentTimeMillis()) + "ms");
        return ReturnT.SUCCESS;
    }

    /**
     * 创建月度报告
     */
    private void createMonthReport(){
        while(true){
            if(loadFinish && queue.isEmpty()){
                break;
            }
            MemberPO memberPO = queue.poll();
            if(memberPO == null){
                continue;
            }
            try{
                doCreateMonthReport(memberPO);
            }catch (Exception ex){
                log.error("创建月度控糖报告失败，失败的患者id:{}" , memberPO.getMemberId() ,ex);
            }
        }
        countDownLatch.countDown();
    }

    /**
     * 执行 创建月度报告
     * @param memberPO
     */
    private void doCreateMonthReport(MemberPO memberPO){
        String reportMonth = LocalDateTime.now().minusMonths(1L).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        SugarMonthReportPO report = this.sugarMonthReportService.getSugarMonthReportByMemberId(memberPO.getMemberId() , reportMonth);
        if(report != null){
            return;
        }
        RangeBO rangeBO = this.memberService.getMemberRange(memberPO.getMemberId());

        SugarMonthReportPO sugarMonthReportPO = new SugarMonthReportPO();
        sugarMonthReportPO.setDiabetesType(memberPO.getDiabetesType());
        sugarMonthReportPO.setMemberId(memberPO.getMemberId());
        sugarMonthReportPO.setRangeData(JSON.toJSONString(rangeBO));
        sugarMonthReportPO.setReportDate(reportMonth);
        this.sugarMonthReportService.addSugarMonthReport(sugarMonthReportPO);
    }
}
