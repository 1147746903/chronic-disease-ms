package com.comvee.cdms.member.job;

import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.UpdateDoctorMemberDTO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.packages.dto.ListValidMemberPackageDTO;
import com.comvee.cdms.packages.po.MemberPackagePO;
import com.comvee.cdms.packages.service.PackageService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: suyz
 * @date: 2019/1/9
 */
@Component
public class MemberDoctorPayStatusJob{

    private final static Logger log = LoggerFactory.getLogger(MemberDoctorPayStatusJob.class);

    @Autowired
    private PackageService packageService;

    @Autowired
    private MemberService memberService;

    private final static int PAGE_ROWS = 50;

    private final static int EXPIRE_DAY = -1;

    private final static Set<String> DOCTOR_MEMBER_SET = new HashSet<>();

    @XxlJob("memberDoctorPayStatusJob")
    public ReturnT<String> execute(String param) throws Exception {
        int pageNum = 1;
        PageResult<MemberPackagePO> poPageResult = null;
        do {
            poPageResult = this.packageService.listMemberPackageByExpireDay(pageNum, PAGE_ROWS, EXPIRE_DAY);
            startHandler(poPageResult.getRows());
            pageNum ++;
        }while(poPageResult.getTotalPages() > pageNum - 1);
        DOCTOR_MEMBER_SET.clear();
        this.memberService.updatePayStatusByPackageStartDate();
        return ReturnT.SUCCESS;
    }

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
                String setKey = x.getDoctorId().concat(x.getMemberId());
                if(DOCTOR_MEMBER_SET.contains(setKey)){
                    return;
                }
                payStatusHandler(x);
                DOCTOR_MEMBER_SET.add(setKey);
            }catch (Exception e){
                log.error("患者付费状态处理失败，主键id:{}", x.getSid());
            }
        });
    }

    /**
     * 付费状态处理
     * @param memberPackagePO
     */
    private void payStatusHandler(MemberPackagePO memberPackagePO){
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(memberPackagePO.getMemberId());
        listValidMemberPackageDTO.setDoctorId(memberPackagePO.getDoctorId());
        List<MemberPackagePO> list = this.packageService.listValidMemberPackage(listValidMemberPackageDTO);
        if(list == null || list.size() == 0){
            UpdateDoctorMemberDTO updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
            updateDoctorMemberDTO.setMemberId(memberPackagePO.getMemberId());
            updateDoctorMemberDTO.setDoctorId(memberPackagePO.getDoctorId());
            updateDoctorMemberDTO.setPayStatus(MemberDoctorConstant.PAY_STATUS_EXPIRE);
            this.memberService.updateMemberDoctor(updateDoctorMemberDTO);
        }
    }
}
