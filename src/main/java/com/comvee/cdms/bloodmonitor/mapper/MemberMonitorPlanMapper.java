package com.comvee.cdms.bloodmonitor.mapper;

import com.comvee.cdms.bloodmonitor.dto.DeleteMonitorTemplateDTO;
import com.comvee.cdms.bloodmonitor.dto.GetMemberMonitorDTO;
import com.comvee.cdms.bloodmonitor.dto.GetMemberMonitorPlanListDTO;
import com.comvee.cdms.bloodmonitor.dto.ListMonitorPlanTemplateMapperDTO;
import com.comvee.cdms.bloodmonitor.vo.MemberMonitorPlanListVO;
import com.comvee.cdms.bloodmonitor.model.SugarMonitorTemplatePO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.po.MemberSugarCodeNumberPO;
import com.comvee.cdms.bloodmonitor.po.SchemaPO;
import com.comvee.cdms.bloodmonitor.vo.TodayTaskDetailListVO;
import com.comvee.cdms.member.bo.MemberCodeBO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

//import com.comvee.cdms.common.exception.DiabetesMsException;
/**
 * @author 李左河
 *
 */
public interface MemberMonitorPlanMapper {

	/**
	 * 获取血糖计划
	 * @author 李左河
	 * @date 2018年3月22日 上午9:36:07
	 * @param getMemberMonitorDTO
	 * @return
	 */
	MemberMonitorPlanPO getMemberMonitorPlan(GetMemberMonitorDTO getMemberMonitorDTO);

	/**
	 * 停止医嘱
	 * @param memberId
	 * 李左河
	 */
	void stopMonitor(@Param("memberId") String memberId ,@Param("illnessType") Integer illnessType);

	/**
	 * 保存监测方案
	 * @param memberMonitorPlan
	 * 李左河
	 */
	void saveMonitor(MemberMonitorPlanPO memberMonitorPlan);

	/**
	 * 根据memberId和监测类型，获取监测方案
	 * @param map
	 * 李左河
	 * @return
	 */
	MemberMonitorPlanPO getMemberMonitorPlanByMap(Map<String, String> map);

	/**
	 * 修改监测方案
	 * @param memberMonitorPlan
	 */
	void modifyMonitor(MemberMonitorPlanPO memberMonitorPlan);
	
	/**
	 * 加载拥有监测方案的患者人数（根据时间段区分）
	 * @author 李左河
	 * @date 2018年3月22日 上午9:36:38
	 * @param map
	 * @return
	 */
	List<MemberSugarCodeNumberPO> listHasMonitorPeopleNumber(Map<String,Object> map);

	/**
	 * 加载拥有监测方案且进行监测的患者人数（根据时间段区分）
	 * @author 李左河
	 * @date 2018年3月22日 上午9:36:27
	 * @param map
	 * @return
	 */
    List<MemberSugarCodeNumberPO> listHasMonitorAndRecordPeopleNumber(Map<String,Object> map);

	/**
	 * 获取患者血糖监测方案
	 * @param po
	 * @return
	 * @throws
	 */
	List<SchemaPO> listSchemaPoOfMember(@Param("po") SchemaPO po);

	/**
	 * 获取血糖监测计划数量
	 * @param po
	 * @return
	 * @throws
	 */
	Long getTestSugarNumberOfSchema(@Param("po") SchemaPO po);

	/**
	 * 获取血糖监测计划人数
	 * @param po
	 * @return
	 * @throws
	 */
	Long getTestSugarNumberOfMember(@Param("po") SchemaPO po);

	/**
	 * 获取有监测计划到memberId串根据条件
	 * @param po
	 * @return
	 */
    List<String> getHasTestSchemaOfMemberId(@Param("po")SchemaPO po);

	/**
	 * 加载监测方案模板
	 * @param monitorType
	 * @return
	 */
	List<SugarMonitorTemplatePO> listMonitorPlanTemplate(ListMonitorPlanTemplateMapperDTO list);

	/**
	 * 加载系统检测方案
	 * @param list
	 * @return
	 */
	List<SugarMonitorTemplatePO> listSystemMonitorPlanTemplate(ListMonitorPlanTemplateMapperDTO list);

//
	/**
	 * 加载患者监测方案历史
	 * @param map
	 * @return
	 */
    List<MemberMonitorPlanPO> listMonitorPlanHistory(Map<String,Object> map);

    /**
     * 获取血糖监测计划对象
     * @author 李左河
     * @date 2018/5/29 12:59
     * @param schemaPO
     * @return java.util.List<com.comvee.cdms.workbench.model.MemberCodeModel>
     *
     */
    List<MemberCodeBO> getMemberCodeModelBySchema(@Param("po") SchemaPO schemaPO);

	/**
	 * 获取血糖计划
	 * @param synthesizeDataDTO
	 * @return
	 */
	List<MemberMonitorPlanPO> getBloodMonitorPlan(SynthesizeDataDTO synthesizeDataDTO);


	/**
	 * 添加监测方案模板
	 * @param sugarMonitorTemplatePO
	 * @return
	 */
	void addMonitorPlanTemplate(SugarMonitorTemplatePO sugarMonitorTemplatePO);

	/**
	 * 修改自定义监测方案模板
	 * @param sugarMonitorTemplatePO
	 */
	void updateCustomMonitorPlanTemplate(SugarMonitorTemplatePO sugarMonitorTemplatePO);

	/**
	 * 删除自定义监测方案模板
	 */
	void deleteMonitorTemplate(DeleteMonitorTemplateDTO deleteMonitorTemplateDTO);


	/**
	 * 加载创建的自定义监测方案模板
	 * @param doctorList
	 * @return
	 */
	List<SugarMonitorTemplatePO> listMonitorTemplateByPerson(@Param("doctorList") List<String> doctorList,@Param("keyword") String keyword);

	/**
	 * 根据自定义血糖监测模板id获取模板详情
	 * @param sid
	 * @return
	 */
	SugarMonitorTemplatePO getMonitorTemplateById(@Param("sid") String sid);

	/**
	 * 根据监测方案的planId修改该方案的状态为停止
	 * @param planId
	 */
    void modifyMonitorPlanByPlanId(String planId);

	List<MemberMonitorPlanPO> getBloodMonitorPlanAndRecord(@Param("illnessType") Integer illnessType,@Param("memberId") String memberId);

	MemberMonitorPlanPO getMemberMonitorPlanBySid(@Param("planId") String planId);


	/**
	 * 查询患者全部监测方案
	 * @param memberId
	 * @param doctorId
	 * @return
	 */
	List<MemberMonitorPlanPO> listMonitorPlan(@Param("memberId") String memberId);

	//修改监测方案为执行中
	void updateMonitorPlanByPlanId(@Param("planId") String planId);

	//修改监测方案为停止
	void stopMonitorPlanByPlanId(@Param("planId") String planId);
	/**
	 * 物理删除细项表数据
	 * @param planId
	 */
	void deleteMonitorByMemberId(@Param("planId") String planId);

	/**
	 * 修改监测方案表失效
	 * @param planId
	 */
	void updateMonitorPlanDetailByPlanId(@Param("planId") String planId);

	/**
	 * 获取正在执行中的监测方案(H5)
	 * @param getMemberMonitorDTO
	 * @return
	 */
	MemberMonitorPlanPO getExecutionMemberMonitorPlan(GetMemberMonitorDTO getMemberMonitorDTO);

	/**
	 * 查询再执行的监测方案
	 * @return
	 */
	List<MemberMonitorPlanPO>listBloodMonitorPlan();

	/**
	 * 加载住院患者中今日需要测量血糖的患者
	 * @param departmentId
	 * @param date
	 * @param illnessType
	 * @return
	 */
	List<TodayTaskDetailListVO> listInHospitalTodayTask(@Param("departmentId") String departmentId
			,@Param("date") String date ,@Param("illnessType") Integer illnessType ,@Param("paramCode") String paramCode);

	//获取所有检测任务
	List<MemberMonitorPlanListVO> listMemberMonitorPlanList(GetMemberMonitorPlanListDTO dto);

}
