package com.comvee.cdms.bloodmonitor.mapper;

import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMonitorPlanRecordMapper {
    /**
     * 获取患者的历史监测方案记录
     * @param memberId
     * @param hospitalId
     * @return
     */
    List<MemberMonitorPlanRecordPO> listMonitorRecord(@Param("memberId") String memberId, @Param("hospitalId") String hospitalId);

    /**
     * 修改监测方案状态
     * @param sid
     */
    void updateMonitorRecord(@Param("sid") String sid);

    /**
     * 删除监测方案状态
     * @param sid
     */
    void deleteMonitorRecord(@Param("sid") String sid);

    List<MemberMonitorPlanRecordPO> listMonitorPlan();


    /**
     * 保存患者监测方案的监测记录
     * @param memberMonitorRecordPO
     */
    void insertMemberMonitorPlanRecord(MemberMonitorPlanRecordPO memberMonitorRecordPO);

    List<MemberMonitorPlanRecordPO> listManageMonitorPlan();

    /**
     * 通过患者和监测类型
     * @param memberId
     * @return
     */
    MemberMonitorPlanRecordPO findMemberMonitorPlanRecordByMonitorPlan(@Param("memberId") String memberId,@Param("hospitalId")String hospitalId);

    MemberMonitorPlanRecordPO listMonitorPlanByMemberAndHospital(@Param("memberId") String memberId, @Param("hospitalId")String hospitalId);

    void modifyMonitorRecord(@Param("planId") String planId);

}
