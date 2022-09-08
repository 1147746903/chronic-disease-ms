package com.comvee.cdms.level.job;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.level.constant.MemberLevelConstant;
import com.comvee.cdms.level.dto.AccessDiabetesLevelDTO;
import com.comvee.cdms.level.dto.AddDiabetesLevelDTO;
import com.comvee.cdms.level.dto.LastLevelDTO;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberDiabetesLevelService;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.level.vo.AccessDiabetesLevelResultVO;
import com.comvee.cdms.level.vo.DiabetesLevelAssessDataVO;
import com.comvee.cdms.member.service.MemberManageService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 糖尿病分标job
 */
@Component
public class DiabetesLevelCreateJob{

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberDiabetesLevelService memberDiabetesLevelService;

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private MemberManageService memberManageService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private BlockingQueue<String> queue = new LinkedBlockingQueue(500);

    private CountDownLatch countDownLatch = null;

    private static final int THREAD_NUMBER = 5;

    private final static int ROWS = 100;

    private volatile boolean loadFinish = false;

    @XxlJob("diabetesLevelCreateJob")
    public ReturnT<String> execute(String param) throws Exception {
        log.info("开始进行系统糖尿病分标...");
        countDownLatch = new CountDownLatch(THREAD_NUMBER);
        loadFinish = false;
        for(int i = 0 ; i < THREAD_NUMBER ; i ++){
            taskExecutor.submit(() ->{
                startConsumer();
            });
        }
        PageResult<String> memberList = null;
        int page = 1;
        PageRequest pr = new PageRequest();
        pr.setRows(ROWS);
        do{
            pr.setPage(page);
            memberList = this.memberManageService.listAllManageMemberDistinct(pr);
            for(String memberId : memberList.getRows()){
                queue.put(memberId);
            }
            page ++;
        }while (memberList.getTotalPages() >= page);
        loadFinish = true;
        countDownLatch.await();
        log.info("系统糖尿病分标结束...");
        countDownLatch = null;
        return ReturnT.SUCCESS;
    }

    private void startConsumer(){
        log.info("开启消费线程:{}" ,Thread.currentThread().getName());
        String memberId = null;
        do{
            memberId = queue.poll();
            if(memberId == null){
                continue;
            }
            try{
                doCreate(memberId);
            }catch (Exception e){
                log.error("系统自动生成糖尿病分标异常，患者id:{}" ,memberId ,e);
            }
        }while (memberId != null || !loadFinish);
        countDownLatch.countDown();
    }

    private void doCreate(String memberId){
        log.info("开始进行系统自动糖尿病分标，患者id:{}" ,memberId);
        LastLevelDTO lastLevelDTO = new LastLevelDTO();
        lastLevelDTO.setMemberId(memberId);
//        lastLevelDTO.setConfirm(MemberLevelConstant.YES);
        lastLevelDTO.setLevelType(MemberLevelConstant.TNB_TYPE);
        MemberLevelPO lastRecord = this.memberLevelService.getLastLevel(lastLevelDTO);
        if(lastRecord != null){
            int diff = DateHelper.dateCompareGetDay(DateHelper.getToday() ,lastRecord.getChangeDate());
            if(diff <= 7){
                log.info("系统自动进行糖尿病分标 >> 患者过去7天内存在分标记录，不进行自动分标的操作！ 患者id:{}" ,memberId);
                return;
            }else if(MemberLevelConstant.NO == lastRecord.getConfirm()){
                lastRecord.setConfirm(MemberLevelConstant.IGNORE);
                this.memberLevelService.updateMemberLevel(lastRecord);
            }
        }
        DiabetesLevelAssessDataVO diabetesLevelAssessData = this.memberDiabetesLevelService.getMemberDiabetesLevelAssessData(memberId);
        AccessDiabetesLevelDTO accessDiabetesLevelDTO = new AccessDiabetesLevelDTO();
        BeanUtils.copyProperties(accessDiabetesLevelDTO ,diabetesLevelAssessData);
        accessDiabetesLevelDTO.setMemberId(memberId);
        log.info("系统自动进行糖尿病分标 >> 获取到的患者分标数据:{}" , JSON.toJSONString(accessDiabetesLevelDTO));
        AccessDiabetesLevelResultVO accessDiabetesLevelResult = this.memberDiabetesLevelService.accessDiabetesLevel(accessDiabetesLevelDTO);
        if(accessDiabetesLevelResult == null){
            log.info("系统自动进行糖尿病分标 >> 没有分标结果,患者id:{}" , memberId);
            return;
        }
        log.info("系统自动进行糖尿病分标 >> 患者id:{} ,分标结果:{}" , memberId ,JSON.toJSONString(accessDiabetesLevelResult));
        if(lastRecord != null){
            if(lastRecord.getMemberLevel() == accessDiabetesLevelResult.getLevel()){
                log.info("系统自动进行糖尿病分标 >> 本次分标结果与上次分标结果相同,不进行新增分标的操作，患者id:{}" ,memberId);
                return;
            }
        }
        AddDiabetesLevelDTO addDiabetesLevelDTO = new AddDiabetesLevelDTO();
        BeanUtils.copyProperties(addDiabetesLevelDTO ,diabetesLevelAssessData);
        addDiabetesLevelDTO.setMemberId(memberId);
        addDiabetesLevelDTO.setLevel(accessDiabetesLevelResult.getLevel());
        addDiabetesLevelDTO.setAdjustment(MemberLevelConstant.NO);
        addDiabetesLevelDTO.setAnalyzeText(accessDiabetesLevelResult.getAnalyseText());
        addDiabetesLevelDTO.setDoctorId(Constant.DEFAULT_FOREIGN_ID);
        addDiabetesLevelDTO.setHospitalId(Constant.DEFAULT_FOREIGN_ID);
        addDiabetesLevelDTO.setConfirm(MemberLevelConstant.NO);
        addDiabetesLevelDTO.setChangeReason(accessDiabetesLevelResult.getProblemText());
        this.memberDiabetesLevelService.addDiabetesLevel(addDiabetesLevelDTO);
        log.info("系统自动进行糖尿病分标 >> 新增糖尿病分标结果成功,新增数据对象:{}" ,JSON.toJSONString(addDiabetesLevelDTO));
    }

}
