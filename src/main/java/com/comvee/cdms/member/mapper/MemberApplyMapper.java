package com.comvee.cdms.member.mapper;

import com.comvee.cdms.member.dto.ListDoctorMemberApplyDTO;
import com.comvee.cdms.member.dto.UpdateDoctorMemberApplyDTO;
import com.comvee.cdms.member.po.DoctorMemberApplyPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/6
 */
public interface MemberApplyMapper {

    /**
     * 获取申请关系
     * @param memberId
     * @param doctorId
     * @return
     */
    DoctorMemberApplyPO getDoctorMemberApply(@Param("memberId") String memberId,@Param("doctorId") String doctorId);

    /**
     * 新增申请关系记录
     * @param doctorMemberApplyPO
     */
    void addDoctorMemberApply(DoctorMemberApplyPO doctorMemberApplyPO);

    /**
     * 修改申请记录
     * @param updateDoctorMemberApplyDTO
     */
    void updateDoctorMemberApply(UpdateDoctorMemberApplyDTO updateDoctorMemberApplyDTO);

    /**
     * 分页申请记录列表
     * @param listDoctorMemberApplyDTO
     */
    List<DoctorMemberApplyPO> listDoctorMemberApply(ListDoctorMemberApplyDTO listDoctorMemberApplyDTO);

    /**
     * 统计申请数
     * @param listDoctorMemberApplyDTO
     */
    long countDoctorMemberApply(ListDoctorMemberApplyDTO listDoctorMemberApplyDTO);
    
       
    /**
     * 获取申请人数
     * @param memberId
     * @param applyStatus
     * @return
     */
    long countUntreatedApply(@Param("applyStatus") String applyStatus,@Param("doctorId") String doctorId);
    /**
     * 获取申请人数
     * @param memberId
     * @param applyStatus
     * @return
     */
    List<DoctorMemberApplyPO> getDoctorMemberApplyList(@Param("doctorId") String doctorId);
    
    
    DoctorMemberApplyPO getLastPatientRequest (@Param("applyStatus") String applyStatus,@Param("doctorId") String doctorId);    
}
