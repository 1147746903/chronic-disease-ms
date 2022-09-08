package com.comvee.cdms.level.job;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.helper.FollowFirstGxyHelper;
import com.comvee.cdms.level.constant.MemberLevelConstant;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**高血压分层分级
 * @author wyc
 * @date 2019/11/23 13:26
 */
@Component
public class GenerateLevelJob {

    private final static Logger log = LoggerFactory.getLogger(GenerateLevelJob.class);

    private static final int PAGE_SIZE = 100;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private BlockingQueue<MemberArchivesPO> queue = new LinkedBlockingQueue(500);

    private CountDownLatch countDownLatch = null;

    private static final int THREAD_NUMBER = 5;

    private volatile boolean loadFinish = false;

    @XxlJob("generateLevelJob")
    public ReturnT<String> execute(String param) throws Exception {
        log.info("开始系统自动高血压分层...");
        countDownLatch = new CountDownLatch(THREAD_NUMBER);
        loadFinish = false;
        for(int i = 0 ; i < THREAD_NUMBER ; i ++){
            taskExecutor.submit(() -> startConsumer());
        }
        PageResult<MemberArchivesPO> memberList = null;
        int page = 1;
        do{
            memberList = this.memberService.listAllMemberArchives(page, PAGE_SIZE);
            for(MemberArchivesPO member : memberList.getRows()){
                queue.put(member);
            }
            page ++;
        }while (memberList.getTotalPages() >= page);
        loadFinish = true;
        countDownLatch.await();
        log.info("系统自动高血压分层结束...");
        countDownLatch = null;
        return ReturnT.SUCCESS;
    }

    private void startConsumer(){
        log.info("开启消费线程:{}" ,Thread.currentThread().getName());
        MemberArchivesPO member = null;
        do{
            member = queue.poll();
            if(member == null){
                continue;
            }
            try{
                startGenerateLevel(member);
            }catch (Exception e){
                log.error("系统自动生成高血压分层异常，患者id:{}" ,member.getMemberId() ,e);
            }
        }while (member != null || !loadFinish);
        countDownLatch.countDown();
    }

    /**
     * 生成分层分级
     * @param archivesPO
     */
    public void startGenerateLevel(MemberArchivesPO archivesPO){
        MemberPO member = this.memberService.getMemberById(archivesPO.getMemberId());
        //患者患有高血压才生成分级
        if (null != member && null != member.getEssentialHyp() && "1".equals(member.getEssentialHyp())){
            String birthday = member.getBirthday() == null ? "" : member.getBirthday();  //生日
            String sex = member.getSex() == null ? "" : String.valueOf(member.getSex());  //性别 1:男 2:女
            String bmi = "";  //BMI
            String sbp = "";  //收缩压
            String dbp = "";  //舒张压
            String smoke = "";  //是否吸烟 1:是
            String tc = "";  //总胆固醇
            String diabetes = member.getIsDiabetes() == null ? "" : member.getIsDiabetes();  //是否有糖尿病 1:是 2:否
            String ldl = "";   //低密度脂蛋白
            String height = member.getHeight();

            if (!StringUtils.isBlank(archivesPO.getArchivesJson())){
                Map<String, Object> map = JsonSerializer.jsonToMap(archivesPO.getArchivesJson());

                if (null != map.get("sign") && !StringUtils.isBlank(map.get("sign").toString())){
                    String sign = map.get("sign").toString();
                    Map<String, Object> signMap = JsonSerializer.jsonToMap(sign);
                    if (null != signMap.get("bmi") && !StringUtils.isBlank(signMap.get("bmi").toString())){
                        bmi = signMap.get("bmi").toString();
                    }
                    if (null != signMap.get("sbp") && !StringUtils.isBlank(signMap.get("sbp").toString())){
                        sbp = signMap.get("sbp").toString();
                    }
                    if (null != signMap.get("dbp") && !StringUtils.isBlank(signMap.get("dbp").toString())){
                        dbp = signMap.get("dbp").toString();
                    }
                }

                if (null != map.get("lab") && !StringUtils.isBlank(map.get("lab").toString())){
                    String lab = map.get("lab").toString();
                    Map<String, Object> labMap = JsonSerializer.jsonToMap(lab);
                    if (null != labMap.get("ldl") && !StringUtils.isBlank(labMap.get("ldl").toString())){
                        ldl = labMap.get("ldl").toString();
                    }
                    if (null != labMap.get("tc") && !StringUtils.isBlank(labMap.get("tc").toString())){
                        tc = labMap.get("tc").toString();
                    }
                }

                if (null != map.get("history") && !StringUtils.isBlank(map.get("history").toString())){
                    String history = map.get("history").toString();
                    Map<String, Object> historyMap = JsonSerializer.jsonToMap(history);
                    if (null != historyMap.get("bs_smoke") && !StringUtils.isBlank(historyMap.get("bs_smoke").toString())){
                        smoke = historyMap.get("bs_smoke").toString();
                    }
                }
            }

            //如果判断分层分级的参数都不为空再生成分级
            if (!StringUtils.isBlank(sex) && !StringUtils.isBlank(birthday) && !StringUtils.isBlank(sbp)
                    && !StringUtils.isBlank(dbp) && !StringUtils.isBlank(bmi) && !StringUtils.isBlank(tc)
                    && !StringUtils.isBlank(smoke) && !StringUtils.isBlank(diabetes)){
                //封装参数
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("birthday", birthday);
                paramMap.put("sex", sex);
                paramMap.put("bmi", bmi);
                paramMap.put("sbp", sbp);
                paramMap.put("dbp", dbp);
                paramMap.put("smoke", smoke);
                paramMap.put("tc", tc);
                paramMap.put("diabetes", diabetes);
                paramMap.put("ldl", ldl);
                paramMap.put("height",height);
                //获取本次分层分级
                Map<String, Object> map = FollowFirstGxyHelper.outFollowFirstGxy(paramMap);
                Integer level = null;
                Integer layer = null;
                if (null != map.get("level") && !StringUtils.isBlank(map.get("level").toString())){
                    level = Integer.parseInt(map.get("level").toString());
                }
                if (null != map.get("layer") && !StringUtils.isBlank(map.get("layer").toString())){
                    layer = Integer.parseInt(map.get("layer").toString());
                }
                String changeDt = DateHelper.getNowDate();
                String changeDate = DateHelper.getToday();

                MemberLevelPO nowLevel = new MemberLevelPO();
                nowLevel.setMemberName(member.getMemberName());
                nowLevel.setMemberNamePy(member.getMemberNamePy());
                nowLevel.setAdjustment(MemberLevelConstant.NO);
                nowLevel.setAge(DateHelper.getAge(member.getBirthday()));
                nowLevel.setChangeDate(changeDate);
                nowLevel.setChangeDt(changeDt);
                nowLevel.setConfirm(MemberLevelConstant.NO);
                nowLevel.setDataJson(JsonSerializer.objectToJson(paramMap));
                nowLevel.setDoctorId("-1");
                nowLevel.setLevelType(MemberLevelConstant.GXY_TYPE);
                nowLevel.setMemberLevel(level);
                nowLevel.setMemberLayer(layer);
                nowLevel.setOrigin(MemberLevelConstant.ORIGIN_SYS);
                nowLevel.setSex(member.getSex());
                nowLevel.setMemberId(member.getMemberId());
                nowLevel.setHospitalId(archivesPO.getHospitalId());
                nowLevel.setChangeReason(Optional.ofNullable(map.get("factorText")).map(x -> x.toString()).orElse(null));
                this.memberLevelService.addHypertensionLevel(nowLevel);
            }
        }
    }


}
