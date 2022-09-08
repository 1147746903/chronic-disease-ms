package com.comvee.cdms.prescription.job;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.po.FollowRemindPO;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.packages.mapper.PackageMapper;
import com.comvee.cdms.packages.po.MemberPackagePO;
import com.comvee.cdms.prescription.dto.GetPrescriptionDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import com.github.pagehelper.PageHelper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: wangxy
 * @date: 2018/11/21
 */
@Component
public class PrescriptionRemindCreateJob{

    private final static Logger log = LoggerFactory.getLogger(PrescriptionRemindCreateJob.class);

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private PackageMapper packageMapper;

    @Autowired
    private FollowServiceI followService;

    @Autowired
    private MemberService memberService;

    private static final int PAGE_SIZE = 50;

    /**
     * 生成随访提醒
     * @author wangxy
     * @date 2018/11/9 15:38
     * @return void
     *
     */
    @XxlJob("prescriptionRemindCreateJob")
    public ReturnT<String> execute(String s) throws Exception {
        int pageNum = 0;
        PageResult<MemberPackagePO> poPageResult = null;
        do {
            poPageResult = listData(pageNum);
            startHandler(poPageResult.getRows());
            pageNum ++;
        }while(poPageResult.getTotalPages() > pageNum - 1);
        return ReturnT.SUCCESS;
    }

    /**
     * 加载数据
     * @param pageNum
     * @return
     */
    private PageResult<MemberPackagePO> listData(int pageNum){
        PageHelper.startPage(pageNum, PAGE_SIZE);

        List<MemberPackagePO> list = this.packageMapper.getValidPackageMemberList();
        return new PageResult<>(list);
    }

    private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 开始处理
     * @param list
     */
    private void startHandler(List<MemberPackagePO> list){
        if(list == null || list.size() == 0){
            return;
        }
        list.forEach(x -> {
            try{
                startCreatePlan(x);
            }catch (Exception e){
                log.error("创建管理处方随访提醒失败~id:{}" , x.getSid() , e);
            }
        });
    }

    /**
     * 生成提醒
     * @param prescriptionPO
     */
    private void startCreatePlan(MemberPackagePO memberPackagePO){
        GetPrescriptionDTO dto=new GetPrescriptionDTO();
        dto.setMemberId(memberPackagePO.getMemberId());
        dto.setSchedule(3);
        List<PrescriptionPO> list = this.prescriptionMapper.listPrescriptionByParam(dto);
        for (int i = 0; i <list.size() ; i++) {
            PrescriptionPO pre = list.get(i);
            //处方完成时间
            String check_dt = pre.getModifyDt();
            //当前时间
            String today = DateHelper.getDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            //管理处方下发后   第15天，第45天，第75天  分别生成3次随访提醒
            long day = DateHelper.getDistanceTimes(check_dt, today);
            if(day==15 || day==45 || day==75){
                String type="";
                String title="";
                String memberName="";

                FollowRemindPO fpo=new FollowRemindPO();
                GetMemberDTO getMemberDTO=new GetMemberDTO();
                getMemberDTO.setMemberId(pre.getMemberId());
                MemberPO member = memberService.getMember(getMemberDTO);
                if(null!=member){
                    memberName=member.getMemberName();
                }

                if(day==15){
                    type="3";
                    title =memberName+"患者的15天随访时间已到，请及时处理";
                }else if(day==45){
                    type="4";
                    title =memberName+"患者的45天随访时间已到，请及时处理";
                }else if(day==75){
                    type="5";
                    title =memberName+"患者的75天随访时间已到，请及时处理";
                }

                fpo.setTitle(title); //随访任务详情
                fpo.setType(type); //类型 3、15天提醒 4、45天提醒 5、75天提醒
                fpo.setFollowId(pre.getSid()); //外键
                fpo.setMemberId(pre.getMemberId()); //患者id
                fpo.setDoctorId(pre.getTeamId()); //医生id
                fpo.setMemberName(memberName); //患者姓名
                fpo.setIsDo("0"); //是否处理 1是 0否
                fpo.setBeforeDay("0"); //提前多少天提醒默认0
                this.followService.insertFollowRemind(fpo);
            }

        }
    }

}
