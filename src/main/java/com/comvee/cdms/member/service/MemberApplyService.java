package com.comvee.cdms.member.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.po.*;

/**
 * @author: suyz
 * @date: 2018/9/30
 */
public interface MemberApplyService {

    /**
     * 获取申请关系
     * @param memberId
     * @param doctorId
     * @return
     */
    DoctorMemberApplyPO getDoctorMemberApply(String memberId, String doctorId);

    /**
     * 新增申请关系记录
     * @param doctorMemberApplyDTO
     */
    String addDoctorMemberApply(DoctorMemberApplyDTO doctorMemberApplyDTO);

    /**
     * 修改申请记录
     * @param updateDoctorMemberApplyDTO
     */
    void updateDoctorMemberApply(UpdateDoctorMemberApplyDTO updateDoctorMemberApplyDTO);

    /**
     * 分页申请记录列表
     * @param listDoctorMemberApplyDTO
     */
    PageResult<DoctorMemberApplyPO> listDoctorMemberApply(ListDoctorMemberApplyDTO listDoctorMemberApplyDTO,PageRequest page);

    /**
     * 统计申请数
     * @param listDoctorMemberApplyDTO
     */
    long countDoctorMemberApply(ListDoctorMemberApplyDTO listDoctorMemberApplyDTO);

}
