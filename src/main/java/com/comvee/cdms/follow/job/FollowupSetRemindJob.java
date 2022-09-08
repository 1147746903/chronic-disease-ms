package com.comvee.cdms.follow.job;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.follow.cfg.FollowRemindConstant;
import com.comvee.cdms.follow.model.FollowDTO;
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

/** 随访设置提醒(新)
 * @author wyc
 * @date 2019/9/16 17:23
 */
@Component
public class FollowupSetRemindJob{

    private static final Logger logger = LoggerFactory.getLogger(FollowupSetRemindJob.class);
    private static final int PAGE_SIZE = 50;

    @Autowired
    private FollowSetServiceI followSetService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private FollowServiceI followService;
    /**
     * 随访设置生成
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("followupSetRemindJob")
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
     */
    private void startHandler(List<FollowupSetPO> list){
        if (null == list || list.size() <= 0){
            return;
        }
        list.forEach(x ->{
            try {
                startCreateRemind(x);
            } catch (Exception e) {
                logger.error("创建随访提醒失败`id:{}",x.getSid(),e);
            }
        });
    }

    /**
     * 生成提醒
     */
    private void startCreateRemind(FollowupSetPO followupSetPO){
        String today = DateHelper.getToday();
        if (!StringUtils.isBlank(followupSetPO.getSetRule())){
            List<Map<String, Object>> maps = JsonSerializer.jsonToMapList(followupSetPO.getSetRule());
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> map = maps.get(i);
                String day = "";  //随访日期
                String type = "";  //随访类型1首诊 2日常随访 3 自我行为问卷 6 自定义随访 7 2型糖尿病随访
                String templateId="";  //自定义随访模板id
                if (null != map.get("day") && !StringUtils.isBlank(map.get("day").toString())){
                    day = map.get("day").toString();
                }
                if (null != map.get("type") && !StringUtils.isBlank(map.get("type").toString())){
                    type = map.get("type").toString();
                }
                if (null != map.get("templateId") && !StringUtils.isBlank(map.get("templateId").toString())){
                    templateId = map.get("templateId").toString();
                }
                //如果提醒时间等于当前时间就添加提醒
                if (day.equals(today)){
                    addFollowRemind(followupSetPO,type,templateId);
                }
            }
        }

    }

    /**
     *
     * @param followupSetPO
     * @param type  1首诊 2日常随访 3 自我行为问卷 6 自定义随访 7 2型糖尿病随访
     * @param templateId
     */
    private void addFollowRemind(FollowupSetPO followupSetPO,String type,String templateId){
        String memberName = "";
        String doctorName="";
        MemberPO member = this.memberService.getMemberById(followupSetPO.getMemberId());
        if (null != member){
            memberName = member.getMemberName();
        }
        DoctorPO doctor = this.doctorService.getDoctorById(followupSetPO.getDoctorId());
        if (null != doctor){
            doctorName = doctor.getDoctorName();
        }
        //followType 随访方式 1提醒医生随访 2自动下发患者填写
        Integer followType = followupSetPO.getFollowType();
        if (null != followType && followType == 2){
            //自动下发患者填写
            FollowDTO dto = new FollowDTO();
            dto.setDoctorId(followupSetPO.getDoctorId());
            dto.setLeaderId(followupSetPO.getDoctorId());
            dto.setMemberId(followupSetPO.getMemberId());
            dto.setDoctorName(doctorName);
            dto.setMemberName(memberName);
            dto.setFollowDt(DateHelper.getToday());
            dto.setPushMember("1");
            dto.setFillFormBy(2);
            dto.setMemberInfo("{}");
            dto.setFollowType(Integer.parseInt(type));
            dto.setHospitalId(followupSetPO.getHospitalId());
            //自定义随访需要模板id和随访名称
            if ("6".equals(type)){
                dto.setTemplateId(templateId);
                FollowCustomerTemplatePO templatePO = this.followService.getTemplateById(templateId);
                if (null != templatePO){
                    dto.setFollowName(templatePO.getFollowName());
                    dto.setQuestionType(templatePO.getQuestionType());
                }else{
                    dto.setTemplateId("-1");
                }
            }
            this.followService.insertFollowWithLock(dto);
        }else if(null != followType && followType == 1){
            //提醒医生
            FollowDTO dto = new FollowDTO();
            dto.setDoctorId(followupSetPO.getDoctorId());
            dto.setLeaderId(followupSetPO.getDoctorId());
            dto.setMemberId(followupSetPO.getMemberId());
            dto.setMemberName(memberName);
            dto.setDoctorName(doctorName);
            dto.setFollowDt(DateHelper.getToday());
            dto.setPushMember("");
            dto.setFillFormBy(1);
            dto.setMemberInfo("{}");
            dto.setFollowType(Integer.parseInt(type));
            dto.setHospitalId("-1");
            //自定义随访需要模板id和随访名称
            if ("6".equals(type)){
                dto.setTemplateId(templateId);
                FollowCustomerTemplatePO templatePO = this.followService.getTemplateById(templateId);
                if (null != templatePO){
                    dto.setFollowName(templatePO.getFollowName());
                    dto.setQuestionType(templatePO.getQuestionType());
                }else{
                    dto.setTemplateId("-1");
                }
            }
            String sid = this.followService.insertFollowWithLock(dto);
            String msgType = "";
            if("1".equals(type)){  //首诊
                msgType= FollowRemindConstant.REMIND_SZ;   //首诊随访提醒
            }
            if("2".equals(type)){  //日常随访
                msgType=FollowRemindConstant.REMIND_RC;  //日常随访提醒
            }
            if("3".equals(type)){  //自我行为问卷
                msgType=FollowRemindConstant.REMIND_ZWXW;  //自我行为随访提醒
            }
            if ("6".equals(type)){  //自定义随访
                msgType = FollowRemindConstant.REMIND_ZDY;  //自定义随访提醒
            }
            if ("7".equals(type)){  //2型糖尿病随访
                msgType = FollowRemindConstant.REMIND_TNB;   //2型糖尿病随访提醒
            }
            if ("8".equals(type)){  //健康评估
                msgType = FollowRemindConstant.REMIND_JKPG;  //健康评估随访提醒
            }
            if ("9".equals(type)){  //南京鼓楼医院糖尿病随访
                msgType = FollowRemindConstant.REMIND_NJTNBSF;   //南京鼓楼医院糖尿病随访提醒
            }
            if ("10".equals(type)){  //高血压首诊
                msgType = FollowRemindConstant.REMIND_GXYSZ;  //高血压首诊随访提醒
            }
            if ("12".equals(type)){  //糖尿病风险评估
                msgType = FollowRemindConstant.REMIND_TNB_ASSESS;  //糖尿病风险评估随访提醒
            }
            if ("11".equals(type)){  //高血压随访
                msgType = FollowRemindConstant.REMIND_GXY_FOLLOW_JW;  //高血压随访随访提醒
            }
            if ("4".equals(type)){  //糖尿病足随访表
                msgType = FollowRemindConstant.REMIND_TNBZ;  //糖尿病足随访表随访提醒
            }
            if ("5".equals(type)){  //糖尿病随访表
                msgType = FollowRemindConstant.REMIND_TNBSF;  //糖尿病随访表随访提醒
            }
            if (!StringUtils.isBlank(msgType)){
                String title = "";
                FollowRemindPO remindPO = new FollowRemindPO();
                title = memberName + "患者的随访时间已到，请及时处理";
                remindPO.setTitle(title);  //随访任务详情
                remindPO.setType(msgType);   //提醒类型
                remindPO.setFollowId(sid);  ///外键  随访id
                remindPO.setMemberId(followupSetPO.getMemberId());
                remindPO.setDoctorId(followupSetPO.getDoctorId());
                remindPO.setMemberName(memberName);
                remindPO.setIsDo("0");  //是否处理 1是 0否
                remindPO.setBeforeDay("0");  //提前多少天提醒默认0
                this.followService.insertFollowRemind(remindPO);
            }

        }

    }
}
