package com.comvee.cdms.bloodmonitor.mapper;

import com.comvee.cdms.bloodmonitor.dto.MemberMonitorPlanDetailDTO;
import org.apache.ibatis.annotations.Param;

import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanDetailPO;

import java.util.List;

/**
 * @author 李左河
 *
 */
public interface MemberMonitorPlanDetailMapper {

	/**
	 * 删除监测方案详细（物理删除）
	 * @param memberId
	 * 李左河
	 */
	void deleteMonitorPlanDetail(@Param("memberId") String memberId ,@Param("illnessType") Integer illnessType);

	/**
	 * 保存监测方案详细
	 * @param memberMonitorPlanDetail
	 * 李左河
	 */
	void saveMonitorPlanDetail(MemberMonitorPlanDetailPO memberMonitorPlanDetail);

	/**
	 * 加载患者检测方案详情列表
	 * @param memberMonitorPlanDetailDTO
	 * @return
	 */
	List<MemberMonitorPlanDetailPO> listMemberMonitorPlanDetail(MemberMonitorPlanDetailDTO memberMonitorPlanDetailDTO);
}
