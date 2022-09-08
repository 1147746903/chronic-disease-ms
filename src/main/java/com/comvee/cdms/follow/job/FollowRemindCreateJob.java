package com.comvee.cdms.follow.job;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.dto.FollowupSetDTO;
import com.comvee.cdms.follow.model.FollowMemberModel;
import com.comvee.cdms.follow.po.FollowRemindPO;
import com.comvee.cdms.follow.po.FollowupSetPO;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.follow.service.FollowSetServiceI;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: wangxy
 * @date: 2018/11/21
 */
@Component
public class FollowRemindCreateJob{
    private final static Logger log = LoggerFactory.getLogger(FollowRemindCreateJob.class);

    @Autowired
    private FollowServiceI followService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private FollowSetServiceI followSetService;

    private static final int PAGE_SIZE = 50;

    /**
     * 生成下次随访提醒
     * @author wangxy
     * @date 2018/11/9 15:38
     * @return void
     *
     */
    @XxlJob("followRemindCreateJob")
    public ReturnT<String> execute(String s) throws Exception {
        int pageNum = 1;
        PageResult<FollowMemberModel> poPageResult = null;
        do {
            poPageResult = this.followService.listMemberFollowSet(pageNum, PAGE_SIZE);
            startHandler(poPageResult.getRows());
            pageNum ++;
        }while(poPageResult.getTotalPages() > pageNum - 1);
        return ReturnT.SUCCESS;
    }
    /**
     * 开始处理
     * @param list
     */
    private void startHandler(List<FollowMemberModel> list){
        if(list == null || list.size() == 0){
            return;
        }
        list.forEach(x -> {
            try{
                startCreateRemind(x);
            }catch (Exception e){
                log.error("创建定期随访失败~id:{}" , x.getFollowSetId() , e);
            }
        });
    }

    /**
     * 生成提醒
     * @param model
     */
    private void startCreateRemind(FollowMemberModel model){
        String today = DateHelper.getDate(new Date(), "yyyy-MM-dd");
        String nextDt = model.getNextFollowDate();
        if (!StringUtils.isBlank(nextDt)){
            nextDt=nextDt.split(" ")[0];
            FollowupSetDTO setDTO = new FollowupSetDTO();
            setDTO.setMemberId(model.getMemberId());
            setDTO.setDoctorId(model.getLeaderId());
            FollowupSetPO followupSet = this.followSetService.getFollowupSet(setDTO);
            String setDate = "";  //随访设置中的随访时间
            //如同个患者随访设置里的和约复查时间是同一天，当天只需提醒一条（按随访设置提醒）
            if (null != followupSet){
                if (!StringUtils.isBlank(followupSet.getSetRule())){
                    List<Map<String, Object>> maps = JsonSerializer.jsonToMapList(followupSet.getSetRule());
                    for (Map<String, Object> map : maps) {
                        if (null != map.get("day") && !StringUtils.isBlank(map.get("day").toString()) && nextDt.equals(map.get("day").toString())){
                            setDate = map.get("day").toString();
                            break;
                        }
                    }
                }
            }
            if (today.equals(nextDt) && (StringUtils.isBlank(setDate) || !nextDt.equals(setDate))){
                addFollowRemind(model,today);
            }
        }


    }

    private void addFollowRemind(FollowMemberModel model,String today){
        String title="";
        String memberName="";
        GetMemberDTO getMemberDTO=new GetMemberDTO();
        getMemberDTO.setMemberId(model.getMemberId());
        MemberPO member = memberService.getMember(getMemberDTO);
        if(null!=member){
            memberName=member.getMemberName();
        }
        FollowRemindPO fpo=new FollowRemindPO();
        title = today+"(今天)是"+memberName+"患者的定期随访时间，请及时处理";
        fpo.setTitle(title); //随访任务详情
        fpo.setType("6"); //类型 6 、定期下次随访提醒
        fpo.setFollowId(model.getFollowSetId()); //外键
        fpo.setMemberId(model.getMemberId()); //患者id
        fpo.setDoctorId(model.getLeaderId()); //医生id
        fpo.setMemberName(memberName); //患者姓名
        fpo.setIsDo("0"); //是否处理 1是 0否
        fpo.setBeforeDay("0"); //提前多少天提醒默认0
        this.followService.insertFollowRemind(fpo);
    }

}
