package com.comvee.cdms.bloodmonitor.service;

import com.comvee.cdms.bloodmonitor.dto.GetMemberMonitorDTO;
import com.comvee.cdms.bloodmonitor.model.SugarMonitorTemplatePO;
import com.comvee.cdms.bloodmonitor.model.TodaySchemaModel;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.po.MemberSugarCodeNumberPO;
import com.comvee.cdms.bloodmonitor.po.SchemaPO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.member.bo.MemberCodeBO;
import com.comvee.cdms.prescription.vo.MemberMonitorVO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;

import java.util.List;
import java.util.Map;

//import com.comvee.cdms.common.exception.DiabetesMsException;

/**
 * 
 * @author 李左河
 *
 */
public interface MemberMonitorPlanServiceI {

	/**
	 * 获取血糖计划
	 * @author 李左河
	 * @date 2018年3月22日 上午9:36:54
	 * @param getMemberMonitorDTO
	 * @return
	 */
	MemberMonitorPlanPO getMemberMonitorPlan(GetMemberMonitorDTO getMemberMonitorDTO);

	/**
	 * 停止医嘱
	 * @param memberId
	 * 李左河
	 */
	void stopMonitor(String memberId);

	/**
	 * 保存监测方案
	 * @param memberMonitorPlan
	 */
	MemberMonitorVO saveMonitor(MemberMonitorPlanPO memberMonitorPlan);

	/**
	 * 点击是保存监测方案
	 * @param memberMonitorPlan
	 */
	void saveMonitorPlan(MemberMonitorPlanPO memberMonitorPlan);
	/**
	 * 加载拥有监测方案的患者人数（根据时间段区分）
	 * @author 李左河
	 * @date 2018年3月22日 上午9:37:11
	 * @param map
	 * @return
	 */
    List<MemberSugarCodeNumberPO> listHasMonitorPeopleNumber(Map<String,Object> map);

    /**
     * 加载拥有监测方案且进行监测的患者人数（根据时间段区分）
     * @author 李左河
     * @date 2018年3月22日 上午9:37:24
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
	List<SchemaPO> listSchemaPoOfMember(SchemaPO po);

	/**
	 * 获取患者今日血糖监测方案
	 * @param midList
	 * @param illnessType 1 糖尿病 2 高血压
	 * @return
	 * @throws
	 */
	List<TodaySchemaModel> listTodaySchemaOfMember(List<String> midList,Integer illnessType);

	/**
	 * 获取血糖监测计划数量（时间条件）
	 * @param po
	 * @return
	 * @throws
	 */
	Long getTestSugarNumberOfSchema(SchemaPO po);

	/**
	 * 获取计划测试血糖人数（根据memberId分组 时间条件）
	 * @param po
	 * @return
	 */
	Long getTestSugarNumberOfMember(SchemaPO po);

	/**
	 * 获取有测试计划到memberId串
	 * @param po
	 * @return
	 */
	List<String> getHasTestSchemaOfMemberId(SchemaPO po);

	/**
	 * 加载监测方案模板
	 * @author 李左河
	 * @date 2018年3月22日 上午9:37:38
	 * @param monitorType
	 * @param memberId
	 * @return
	 */
	Map<String,Object> listMonitorPlanTemplate(Integer monitorType,Integer eohType,String memberId ,String doctorId,String hospitalId,boolean switchFlag);

	/**
	 * 加载患者历史监测方案
	 * @param memberId
	 * @param pageRequestModel
	 * @return
	 */
	PageResult<MemberMonitorPlanPO> listMonitorPlanHistory(String memberId, PageRequest pageRequestModel);

	/**
	 * 获取血糖监测计划对象
	 * @param schemaPO
	 * @return
	 */
    List<MemberCodeBO> getMemberCodeModelBySchema(SchemaPO schemaPO);

	/**
	 * 添加监测方案模板
	 * @param sugarMonitorTemplatePO
	 * @return
	 */
	String addMonitorPlanTemplate(SugarMonitorTemplatePO sugarMonitorTemplatePO);

	/**
	 * 加载自定义监测方案模板
	 * @param doctorId
	 * @return
	 */
	List<SugarMonitorTemplatePO> listCustomMonitorPlanTemplate(String doctorId,String hospitalId,Integer eohType);

	/**
	 * 修改自定义监测方案模板
	 * @param sugarMonitorTemplatePO
	 */
	void updateCustomMonitorPlanTemplate(SugarMonitorTemplatePO sugarMonitorTemplatePO);

	/**
	 * 获取最新血糖计划
	 * @param synthesizeDataDTO
	 * @return
	 */
	MemberMonitorPlanPO getNewBloodMonitorPlan(SynthesizeDataDTO synthesizeDataDTO);

	/**
	 * 加载模板管理血糖监测模板列表
	 * @param doctorId
	 * @return
	 */
	PageResult<SugarMonitorTemplatePO> listOperateMonitorTemplate(PageRequest page,String doctorId,String keyword);

	/**
	 * 删除自定义监测方案模板
	 */
	void deleteMonitorTemplate(String ids);

	/**
	 * 根据自定义血糖监测模板id获取模板详情
	 * @param sid
	 * @return
	 */
	SugarMonitorTemplatePO getMonitorTemplateById(String sid);

	/**
	 * 加载有今日测量任务列表
	 * @param doctorId
	 * @return
	 */
	Map<String ,Object> listTodayTask(String doctorId ,String paramCode ,String departmentId);
}
