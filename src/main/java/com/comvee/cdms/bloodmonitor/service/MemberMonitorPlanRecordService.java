package com.comvee.cdms.bloodmonitor.service;

import com.comvee.cdms.bloodmonitor.dto.MemberMonitorPlanRecordDTO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorTaskCardPO;
import com.comvee.cdms.bloodmonitor.vo.MemberMonitorPlanVO;
import com.comvee.cdms.bloodmonitor.vo.PrescriptionMonitorPlanVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

import java.util.List;


public interface MemberMonitorPlanRecordService {

    /**
     * 获取患者的历史监测记录
     * @param dto
     * @return
     */
    PageResult<MemberMonitorPlanVO> listMonitorRecord(PageRequest page, MemberMonitorPlanRecordDTO dto);

    /**
     *停止监测方案
     * @param planId 方案id
     * @param operationType 方案来源  1:管理处方->监测方案  2:监测方案
     * @return
     */
    void stopMonitorRecord(String planId,Integer operationType);

    /**
     * 删除监测方案
     */
    void deleteMonitorRecord(String planId,Integer operationType);

    /**
     * 查看监测方案
     * @param planId
     */
    PrescriptionMonitorPlanVO lookMonitorRecord(String planId, Integer operationType);

    /**
     * 添加任务卡
     * @param memberMonitorPlanPOS
     */
    void addMemberMonitorTaskCard(List<MemberMonitorPlanPO> memberMonitorPlanPOS);


    /**
     * 修改任务卡为已检测
     */
    void updateMonitorTaskCard(MemberMonitorTaskCardPO memberMonitorTaskCardPO);
}
