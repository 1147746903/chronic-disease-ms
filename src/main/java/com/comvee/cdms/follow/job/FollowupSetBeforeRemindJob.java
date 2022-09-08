package com.comvee.cdms.follow.job;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.po.FollowCustomerTemplatePO;
import com.comvee.cdms.follow.po.FollowRemindPO;
import com.comvee.cdms.follow.po.FollowupSetPO;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.follow.service.FollowSetServiceI;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.github.pagehelper.PageHelper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/** 随访设置提前提醒(新)
 * @author wyc
 * @date 2019/9/16 17:24
 */
@Component
public class FollowupSetBeforeRemindJob{


    private static final Logger logger = LoggerFactory.getLogger(FollowupSetBeforeRemindJob.class);

    private static final int PAGE_SIZE = 50;

    @Autowired
    private FollowSetServiceI followSetService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private FollowServiceI followService;

    @XxlJob("followupSetBeforeRemindJob")
    public ReturnT<String> execute(String param) throws Exception {
        int pageNum = 1;
        PageResult<FollowupSetPO> pageResult = null;
        do {
            pageResult = listData(pageNum);
            startHandler(pageResult.getRows());
            pageNum ++;
        }while (pageResult.getTotalPages() > pageNum - 1);
        return ReturnT.SUCCESS;
    }

    /**
     * 加载数据
     * @param pageNum
     * @return
     */
    private PageResult<FollowupSetPO> listData(int pageNum){
        PageHelper.startPage(pageNum,PAGE_SIZE);
        List<FollowupSetPO> list = this.followSetService.listFollowupSet();
        return new PageResult<FollowupSetPO>(list);
    }

    /**
     * 开始处理数据
     * @param list
     */
    private void startHandler(List<FollowupSetPO> list){
        if (null == list || list.size() <= 0){
            return;
        }
        list.forEach(x ->{
            try {
                startCreateRemind(x);
            } catch (Exception e) {
                logger.error("创建随访提前提醒失败`id:{}",x.getSid(),e);
            }
        });
    }

    /**
     * 生成提醒
     * @param followupSetPO
     */
    private void startCreateRemind(FollowupSetPO followupSetPO){
        String today = DateHelper.getToday();
        if (!StringUtils.isBlank(followupSetPO.getSetRule())){
            List<Map<String, Object>> maps = JsonSerializer.jsonToMapList(followupSetPO.getSetRule());
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> map = maps.get(i);
                String day = "";  //随访日期
                String beforeDay = ""  ;//随访提前提醒天数
                String type = "";  //随访类型1首诊 2日常随访 3 自我行为问卷 6 自定义随访 7 2型糖尿病随访
                String templateId="";  //自定义随访模板id
                if (null != map.get("day") && !StringUtils.isBlank(map.get("day").toString())){
                    day = map.get("day").toString();
                }
                if (null != map.get("beforeDay") && !StringUtils.isBlank(map.get("beforeDay").toString())){
                    beforeDay = map.get("beforeDay").toString();
                }
                if (null != map.get("type") && !StringUtils.isBlank(map.get("type").toString())){
                    type = map.get("type").toString();
                }
                if (null != map.get("templateId") && !StringUtils.isBlank(map.get("templateId").toString())){
                    templateId = map.get("templateId").toString();
                }
                if (!StringUtils.isBlank(day) && !StringUtils.isBlank(beforeDay)){
                    int differDay = DateHelper.dateCompareGetDay(day,today);
                    if (differDay == Integer.parseInt(beforeDay)){
                        addFollow(followupSetPO,beforeDay,type,templateId);
                    }
                }
            }

        }

    }




    private void addFollow(FollowupSetPO followupSetPO,String beforeDay,String type,String templateId){
        String memberName = "";
        String followName = "";
        MemberPO member = this.memberService.getMemberById(followupSetPO.getMemberId());
        if (null != member){
            memberName = member.getMemberName();
        }
        if (!StringUtils.isBlank(type)){
            if ("1".equals(type)){
                followName = "糖尿病首诊";
            }else if ("2".equals(type)){
                followName = "日常";
            }else if ("3".equals(type)){
                followName = "行为分析问卷";
            }else if ("6".equals(type)){  //自定义随访
                FollowCustomerTemplatePO templatePO = this.followService.getTemplateById(templateId);
                if (null != templatePO){
                    followName = templatePO.getFollowName();
                }
            }else if ("7".equals(type)){
                followName = "2型糖尿病";
            }else if ("8".equals(type)){
                followName = "健康评估";
            }else if ("9".equals(type)){
                followName = "南京鼓楼医院糖尿病";
            }else if ("10".equals(type)){
                followName = "高血压首诊";
            }else if ("12".equals(type)){
                followName = "糖尿病风险评估";
            }else if ("11".equals(type)){
                followName = "高血压";
            }else if ("4".equals(type)){
                followName = "糖尿病足";
            }else if ("5".equals(type)){
                followName = "糖尿病";
            }
        }
        String title = "";
        FollowRemindPO remindPO = new FollowRemindPO();
        title = memberName + "患者将于"+beforeDay+"天后进行"+followName+"随访，请及时做好随访安排";
        remindPO.setTitle(title);
        remindPO.setType("12");  //12 y天后随访提醒
        remindPO.setMemberId(followupSetPO.getMemberId());
        remindPO.setDoctorId(followupSetPO.getDoctorId());
        remindPO.setMemberName(memberName);
        remindPO.setIsDo("0");  //是否处理 1是 0否
        remindPO.setBeforeDay(beforeDay);  //提前的天数
        remindPO.setFollowId("-1");  //外键
        this.followService.insertFollowRemind(remindPO);


    }

}
