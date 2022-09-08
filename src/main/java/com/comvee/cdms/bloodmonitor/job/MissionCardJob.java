package com.comvee.cdms.bloodmonitor.job;

import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorPlanMapper;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorTaskCardMapper;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorTaskCardPO;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MissionCardJob{

    @Autowired
    private MemberMonitorPlanMapper memberMonitorPlanMapper;

    @Autowired
    private MemberMonitorTaskCardMapper memberMonitorTaskCardMapper;


    @XxlJob("MissionCardJob")
    public ReturnT<String> execute(String param) throws Exception {
        //2:查询监测方案表所有患者正在执行的监测方案细项
        List<MemberMonitorPlanPO> memberMonitorPlanPOS = this.memberMonitorPlanMapper.listBloodMonitorPlan();
        if (memberMonitorPlanPOS != null && memberMonitorPlanPOS.size()>0) {
            //生成血糖管理->监测方案任务卡
            startHandler(memberMonitorPlanPOS);
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 开始处理数据
     * @param memberMonitorPlanPOS
     */
    private void startHandler(List<MemberMonitorPlanPO> memberMonitorPlanPOS){
        if(memberMonitorPlanPOS == null || memberMonitorPlanPOS.size() == 0){
            return;
        }
        memberMonitorPlanPOS.forEach(x -> {
            //监测方案处理
            addMemberMonitorTaskCard(x);
        });
    }


    public void addMemberMonitorTaskCard(MemberMonitorPlanPO planPO){
        if(planPO != null && !"".equals(planPO)) {
            //查询当前时间是周几(获取对应的数字)
            String weekCode = getWeekCode(DateHelper.getWeek());
            String detail = planPO.getPlanDetail();
            String[] longArray = detail.split(";");
            for (String longA : longArray) {
                int index = longA.indexOf("-");
                String dateCode = longA.substring(0, index);
                if (dateCode.equals(weekCode)) {
                    MemberMonitorTaskCardPO memberMonitorTaskCardPO = new MemberMonitorTaskCardPO();
                    memberMonitorTaskCardPO.setSid(DaoHelper.getSeq());
                    memberMonitorTaskCardPO.setPlanId(planPO.getPlanId());
                    memberMonitorTaskCardPO.setMemberId(planPO.getMemberId());
                    memberMonitorTaskCardPO.setDateCode(longA.substring(0, index));
                    //将对应点的数值转化为时间点
                    if (planPO.getIllnessType() == 1) {
                        memberMonitorTaskCardPO.setParamCode(longA.substring(index + 1, longA.length()));
                        //糖尿病
                        memberMonitorTaskCardPO.setMonitorType(1);
                        memberMonitorTaskCardPO.setIllnessType(1);
                    } else {
                        //血压
                        String type = String.valueOf(longA.substring(index + 1, longA.length()).charAt(2));
                        if ("1".equals(type) || "2".equals(type)) {
                            memberMonitorTaskCardPO.setParamCode("9"); //早起后
                        } else if ("3".equals(type) || "4".equals(type)) {
                            memberMonitorTaskCardPO.setParamCode("10"); //上午
                        } else if ("5".equals(type) || "6".equals(type)) {
                            memberMonitorTaskCardPO.setParamCode("11"); //下午
                        } else if ("7".equals(type) || "8".equals(type)) {
                            memberMonitorTaskCardPO.setParamCode("12"); //晚上
                        }
                        int monitorType = Integer.parseInt(type);
                        if (monitorType % 2 == 0) {
                            //脉搏
                            memberMonitorTaskCardPO.setMonitorType(3);
                        } else {
                            //血压
                            memberMonitorTaskCardPO.setMonitorType(2);
                        }
                        memberMonitorTaskCardPO.setIllnessType(2);
                    }
                    memberMonitorTaskCardPO.setMonitorTime(longA.substring(index + 1, longA.length()));
                    memberMonitorTaskCardPO.setMonitorDt(DateHelper.getToday());
                    //当前患者这一天这一个点的值唯一,作为查询条件,有就不添加
                    List<MemberMonitorTaskCardPO> all = this.memberMonitorTaskCardMapper.findAll(planPO.getMemberId(), DateHelper.getToday(), longA.substring(index + 1, longA.length()));
                    if (all.size() == 0 || all == null) {
                        this.memberMonitorTaskCardMapper.addMemberMonitorTaskCard(memberMonitorTaskCardPO);
                    }
                }
            }
        }

    }

    /**
     * 见周期转化为对应的月数
     * @param eduCycle
     * @return
     */
    public int getDay(int eduCycle) {
        int month = 0;
        if (eduCycle == 4){
            month = 1;
        }else if(eduCycle == 12){
            month = 3;
        }else if(eduCycle == 24){
            month = 6;
        }
        return month;
    }

    /**
     * 取对应的周范围对应的数字
     * @param time
     * @return
     */
    public String getWeekCode(String time){
        String weekCode = "";
        if ("星期一".equals(time)){
            weekCode = "1";
        }else if ("星期二".equals(time)){
            weekCode = "2";
        }else if ("星期三".equals(time)){
            weekCode = "3";
        }else if ("星期四".equals(time)){
            weekCode = "4";
        }else if ("星期五".equals(time)){
            weekCode = "5";
        }else if ("星期六".equals(time)){
            weekCode = "6";
        }else if ("星期日".equals(time)){
            weekCode = "7";
        }
        return weekCode;
    }
}
