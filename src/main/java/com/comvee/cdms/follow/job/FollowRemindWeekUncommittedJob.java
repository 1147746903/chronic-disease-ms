package com.comvee.cdms.follow.job;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.cfg.FollowConstant;
import com.comvee.cdms.follow.cfg.FollowRemindConstant;
import com.comvee.cdms.follow.dto.ListFollowDTO;
import com.comvee.cdms.follow.model.FollowListModel;
import com.comvee.cdms.follow.po.FollowRemindPO;
import com.comvee.cdms.follow.service.FollowServiceI;
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

/** 随访下发一周患者未提交提醒
 * @author wyc
 * @date 2019/9/11 11:38
 */
@Component
public class FollowRemindWeekUncommittedJob{


    private final static Logger logger = LoggerFactory.getLogger(FollowRemindWeekUncommittedJob.class);

    private final static int ROWS = 50;

    @Autowired
    private FollowServiceI followService;
    @Autowired
    private MemberService memberService;

    @XxlJob("followRemindWeekUncommittedJob")
    public ReturnT<String> execute(String param) throws Exception {
        int page = 1;
        PageResult<FollowListModel> pageResult = null;
        do{
            pageResult = listData(page);
            startHandler(pageResult.getRows());
            page ++;
        }while (page <= pageResult.getTotalPages());
        return ReturnT.SUCCESS;
    }

    /**
     * 加载数据
     * @param pageNum
     * @return
     */
    private PageResult<FollowListModel> listData(int pageNum){
        PageHelper.startPage(pageNum, ROWS);
        ListFollowDTO dto = new ListFollowDTO();
        dto.setFillFormBy(FollowConstant.FOLLOW_FILL_BY_MEMBER); //4 随访状态 : 下发患者
        dto.setDealStatus(FollowConstant.FOLLOW_STATUS_NO);
        List<FollowListModel> models = this.followService.listFollowByParam(dto);
        return new PageResult<>(models);
    }

    /**
     * 开始处理
     * @param list
     */
    private void startHandler(List<FollowListModel> list){
        if(list == null || list.size() == 0){
            return;
        }
        list.forEach(x -> {
            try{
                startCreatePlan(x);
            }catch (Exception e){
                logger.error("生成随访提醒失败id:{}" , x.getSid() , e);
            }
        });
    }

    /**
     *
     * 生成提醒
     * @param model
     */
    private void startCreatePlan(FollowListModel model){
        //获取随访下发时间和当前时间相隔的天数
        int day = DateHelper.dateCompareGetDay(DateHelper.getToday(), model.getCreateDt());
        // 如果随访七天没提交.生成随访提醒
        if (day == 7){
            //查询随访提醒列表中是否有改随访的提醒信息,有就忽略
            FollowRemindPO remindPO = new FollowRemindPO();
            remindPO.setFollowId(model.getSid());
            FollowRemindPO followRemind = this.followService.getFollowRemind(remindPO);
            if (null != followRemind){
                FollowRemindPO po = new FollowRemindPO();
                po.setId(followRemind.getId());
                po.setIsDo("1");  //1 已处理
                po.setIsValid("0");
                this.followService.modifyFollowRemind(po);
            }
            //1首诊 2日常随访 3自我行为问卷 4糖尿病足随访表(深圳) 5糖尿病随访表(深圳)6自定义随访 7 2型糖尿病随访表
            String type = "";  //提醒类型
            if (FollowConstant.FOLLOW_TYPE_FIRST == model.getFollowType()){  //首诊
                type = FollowRemindConstant.REMIND_SZ;
            }else if (FollowConstant.FOLLOW_TYPE_DAY == model.getFollowType()){  //日常
                type = FollowRemindConstant.REMIND_RC;
            }else if (FollowConstant.FOLLOW_TYPE_QUES == model.getFollowType()){  //行为问卷
                type = FollowRemindConstant.REMIND_ZWXW;
            }else if (FollowConstant.FOLLOW_TYPE_QUES_FOLLOW == model.getFollowType()){ //糖尿病足随访表
                type = FollowRemindConstant.REMIND_TNBZ;
            }else if (FollowConstant.FOLLOW_TYPE_FOLLOW == model.getFollowType()){  //糖尿病随访表
                type = FollowRemindConstant.REMIND_TNBSF;
            }else if (FollowConstant.FOLLOW_TYPE_CUSTOM == model.getFollowType()){  //自定义随访表
                type = FollowRemindConstant.REMIND_ZDY;
            }else if (FollowConstant.FOLLOW_TYPE_TWO_DIABETES == model.getFollowType()){ //2型糖尿病随访表
                type = FollowRemindConstant.REMIND_TNB;
            }else if (FollowConstant.FOLLOW_TYPE_HEALTH_ACCESS == model.getFollowType()){ //健康评估随访
                type = FollowRemindConstant.REMIND_JKPG;
            }else if (FollowConstant.FOLLOW_TYPE_NJ_DIABETES == model.getFollowType()){ //南京鼓楼医院糖尿病随访随访
                type = FollowRemindConstant.REMIND_NJTNBSF;
            }else if (FollowConstant.FOLLOW_TYPE_FIRST_GXY == model.getFollowType()){ //高血压首诊随访
                type = FollowRemindConstant.REMIND_GXYSZ;
            }else if (FollowConstant.FOLLOW_TYPE_TNB_ASSESS == model.getFollowType()){ //糖尿病风险评估
                type = FollowRemindConstant.REMIND_TNB_ASSESS;
            }else if (FollowConstant.FOLLOW_TYPE_HYP_JW == model.getFollowType()){  //高血压随访(基卫)
                type = FollowRemindConstant.REMIND_GXY_FOLLOW_JW;
            }else if (FollowConstant.FOLLOW_TYPE_QUES_FOLLOW == model.getFollowType()){  //糖尿病足随访表
                type = FollowRemindConstant.REMIND_TNBZ;
            }else if (FollowConstant.FOLLOW_TYPE_FOLLOW == model.getFollowType()){  //糖尿病随访表
                type = FollowRemindConstant.REMIND_TNBSF;
            }
            MemberPO member = this.memberService.getMemberById(model.getMemberId());
            String memberName = "";
            if (null != member){
                memberName = member.getMemberName();
            }

            //生成随访提醒
            String title="";
            FollowRemindPO fpo=new FollowRemindPO();
            title = memberName + "患者已经7天未反馈您下发的随访，请及时对患者进行电话随访";
            fpo.setTitle(title); //随访任务详情
            fpo.setType(type); //类型   随访提醒
            fpo.setFollowId(model.getSid()); //外键
            fpo.setMemberId(model.getMemberId()); //患者id
            fpo.setDoctorId(model.getDoctorId()); //医生id
            fpo.setMemberName(model.getMemberName()); //患者姓名
            fpo.setIsDo("0"); //是否处理 1是 0否
            fpo.setBeforeDay("0"); //提前多少天提醒默认0
            this.followService.insertFollowRemind(fpo);
        }
    }
}
