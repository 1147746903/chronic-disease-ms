package com.comvee.cdms.bloodmonitor.mapper;

import com.comvee.cdms.bloodmonitor.po.MemberMonitorTaskCardPO;
import com.comvee.cdms.sign.dto.MemberBloodSugarDTO;
import com.comvee.cdms.sign.vo.MemberBloodSugarVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMonitorTaskCardMapper {

    /**
     * 创建任务卡
     * @param memberMonitorTaskCardPO
     */
    void addMemberMonitorTaskCard(MemberMonitorTaskCardPO memberMonitorTaskCardPO);

    /**
     * 查询任务卡
     * @param sids
     * @return
     */
    void updateMemberMonitorTaskCard(@Param("sids") List<String> sids);

    /**
     * 获取时间点之前的任务卡
     * @param planId
     * @param dt
     */
    List<MemberMonitorTaskCardPO> listAfterMemberMonitorTaskCard(@Param("planId") String planId, @Param("dt") Integer dt,@Param("insertDt")String insertDt);

    /**
     * 获取时间点之后的任务卡
     * @param planId
     * @param dt
     */
    List<MemberMonitorTaskCardPO> listBeforeMemberMonitorTaskCard(@Param("planId") String planId, @Param("dt") Integer dt);

    /**
     * 查询今日全部的任务卡
     */
    List<MemberMonitorTaskCardPO> findAll(@Param("memberId") String memberId,@Param("insertDt")String insertDt,@Param("monitorTime")String monitorTime);

    List<MemberMonitorTaskCardPO> listTaskPoOfMember(@Param("memberId") String memberId, @Param("insertDt") String insertDt, @Param("illnessType") int illnessType);

    List<MemberMonitorTaskCardPO> listTaskByPlanId(@Param("planId") String planId, @Param("monitorDt") String monitorDt);


    List<MemberBloodSugarVO> listTaskPoMemberList(MemberBloodSugarDTO memberBloodSugarDTO);
    /**
     * 修改任务卡为已检测
     * @return
     */
    void updateMonitorTaskCard(MemberMonitorTaskCardPO memberMonitorTaskCardPO);

}
