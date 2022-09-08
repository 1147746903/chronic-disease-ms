package com.comvee.cdms.bloodmonitor.job;

import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorPlanMapper;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorTaskCardMapper;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorTaskCardPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanDetailServiceI;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.prescription.dto.GetPrescriptionDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理监测方案自动停止或开始
 */
@Component
public class MemberMonitorPlanJob {
	@Autowired
	private MemberMonitorPlanMapper memberMonitorPlanMapper;

	@Autowired
	private PrescriptionMapper prescriptionMapper;

	@Autowired
	@Qualifier("memberMonitorPlanDetailService")
	private MemberMonitorPlanDetailServiceI memberMonitorPlanDetailService;

	@Autowired
	private MemberMonitorTaskCardMapper memberMonitorTaskCardMapper;

	private static final Logger log = LoggerFactory.getLogger(MemberMonitorPlanJob.class);

	@XxlJob("MemberMonitorPlanJob")
	public ReturnT<String> execute(String param) throws Exception{
		//取血糖管理的正在执行的监测方案,判断当执行的方案是否是失效的,失效则停止
		List<MemberMonitorPlanPO> memberMonitorPlanPOS = this.memberMonitorPlanMapper.listBloodMonitorPlan();
		startHandler(memberMonitorPlanPOS);
		XxlJobHelper.handleSuccess();
		return ReturnT.SUCCESS;

	}


	/**
	 * 开始处理数据
	 * @param memberMonitorPlanPOS
	 */
	private void startHandler(List<MemberMonitorPlanPO> memberMonitorPlanPOS){
		if(memberMonitorPlanPOS == null || memberMonitorPlanPOS.size() == 0){
			return;
		}
		memberMonitorPlanPOS.forEach(x -> {
			//监测方案处理
			monitorCardHandler(x);
		});
	}

	/**
	 * 下发检测提醒
	 * @param memberMonitorPlanPO
	 */
	private void monitorCardHandler(MemberMonitorPlanPO memberMonitorPlanPO){
		String date = "";
		if (memberMonitorPlanPO.getStartMonitorDt() == null || "".equals(memberMonitorPlanPO.getStartMonitorDt())
				|| memberMonitorPlanPO.getEndMonitorDt() == null || "".equals(memberMonitorPlanPO.getEndMonitorDt())){
			//通过患者id和插入时间获取t_prescription插入的管理处方的数据
			GetPrescriptionDTO prescriptionDTO = new GetPrescriptionDTO();
			prescriptionDTO.setMemberId(memberMonitorPlanPO.getMemberId());
			prescriptionDTO.setModifyDt(memberMonitorPlanPO.getModifyDt().substring(0,16));
			PrescriptionPO prescriptionPO = prescriptionMapper.getPrescriptionByParamByMemberIdAndInsertDt(prescriptionDTO);
			if (prescriptionPO != null){
				//管理处方来的,通过周期计算结束天数.
				int month = getDay(prescriptionPO.getEduCycle());
				//结束监测时间
				date = DateHelper.cycleMonth(prescriptionPO.getInsertDt(),month)+" 23:59:59";
			}else{
				date = DateHelper.plusDate(memberMonitorPlanPO.getInsertDt(),1).substring(0,10)+" 23:59:59";
			}
		}else{
			date = memberMonitorPlanPO.getEndMonitorDt();
		}
		try{
			if (DateHelper.compareDate(date)) {
				this.memberMonitorPlanMapper.modifyMonitorPlanByPlanId(memberMonitorPlanPO.getPlanId());
				//血糖记录->监测方案
				//2、删除监测方案详细（物理删除）
				this.memberMonitorPlanDetailService.deleteMonitorPlanDetail(memberMonitorPlanPO.getMemberId() ,memberMonitorPlanPO.getIllnessType());
				//停止任务卡
				stopAfterTaskCard(memberMonitorPlanPO);
			}
		}catch(Exception e){
			log.info("一条错误的时间格式数据,时间为:"+date);
		}
	}
	/**
	 * 处理时间点之后的任务卡为失效
	 */
	public void stopAfterTaskCard(MemberMonitorPlanPO memberMonitorPlanPO){
		if (memberMonitorPlanPO != null){
			List<String> taskIds = new ArrayList<>();
			String time = memberMonitorPlanPO.getModifyDt().substring(11,16);
			List<MemberMonitorTaskCardPO> taskCardPOS = null;
			if (memberMonitorPlanPO.getIllnessType() == 1){ //血糖
				taskCardPOS = this.memberMonitorTaskCardMapper.listAfterMemberMonitorTaskCard(memberMonitorPlanPO.getPlanId(),monitorDt(time),DateHelper.getToday());
			}else{ //血压
				taskCardPOS = this.memberMonitorTaskCardMapper.listAfterMemberMonitorTaskCard(memberMonitorPlanPO.getPlanId(),bloodPressure(time),DateHelper.getToday());
			}
			for (MemberMonitorTaskCardPO monitorTaskCardPO : taskCardPOS){
				taskIds.add(monitorTaskCardPO.getSid());
			}
			if (taskIds == null || "".equals(taskIds)){
				return;
			}
			//将对应时间点之后的监测点改为失效
			this.memberMonitorTaskCardMapper.updateMemberMonitorTaskCard(taskIds);
		}
	}

	/**
	 * 处理血糖时间点
	 * @param time
	 * @return
	 */
	public Integer monitorDt(String time){
		Integer dt = null;
		Date date = DateHelper.date(time);
		Date timeStart = DateHelper.date("00:01");
		Date timeEnd = DateHelper.date("04:00");
		if (DateHelper.isEffectiveDate(date,timeStart,timeEnd)){
			dt = 1;
		}
        Date timeStart1 = DateHelper.date("04:01");
        Date timeEnd1 = DateHelper.date("08:00");
		if (DateHelper.isEffectiveDate(date,timeStart1,timeEnd1)){
			dt = 2;
		}
        Date timeStart2 = DateHelper.date("08:01");
        Date timeEnd2 = DateHelper.date("10:00");
		if (DateHelper.isEffectiveDate(date,timeStart2,timeEnd2)){
			dt = 3;
		}
        Date timeStart3 = DateHelper.date("10:01");
        Date timeEnd3 = DateHelper.date("12:00");
		if (DateHelper.isEffectiveDate(date,timeStart3,timeEnd3)){
			dt = 4;
		}
        Date timeStart4 = DateHelper.date("12:01");
        Date timeEnd4 = DateHelper.date("15:00");
		if (DateHelper.isEffectiveDate(date,timeStart4,timeEnd4)){
			dt = 5;
		}
        Date timeStart5 = DateHelper.date("15:01");
        Date timeEnd5 = DateHelper.date("18:00");
		if (DateHelper.isEffectiveDate(date,timeStart5,timeEnd5)){
			dt = 6;
		}
        Date timeStart6 = DateHelper.date("18:01");
        Date timeEnd6 = DateHelper.date("20:00");
		if (DateHelper.isEffectiveDate(date,timeStart6,timeEnd6)){
			dt = 7;
		}
        Date timeStart7 = DateHelper.date("20:01");
        Date timeEnd7 = DateHelper.date("24:00");
		if (DateHelper.isEffectiveDate(date,timeStart7,timeEnd7)){
			dt = 8;
		}
		return dt;
	}

	/**
	 * 处理血压的时间点
	 * @param time
	 * @return
	 */
	public Integer bloodPressure(String time){
		Integer dt = null;
		String nowTime = DateHelper.getToday()+" "+time;
		Date nowDt = DateHelper.dateTime(nowTime);
		String startTime = DateHelper.getToday()+" 04:01";
		Date startDt = DateHelper.dateTime(startTime);
		String endTime = DateHelper.getToday()+" 09:00";
		Date endDt = DateHelper.dateTime(endTime);
		if (DateHelper.isEffectiveDate(nowDt,startDt,endDt)){
			dt = 9;
		}
		String startTime2 = DateHelper.getToday()+" 09:01";
		String endTime2 = DateHelper.getToday()+" 12:00";
		Date nowDt1 = DateHelper.dateTime(nowTime);
		Date startDt1 = DateHelper.dateTime(startTime2);
		Date endDt1 = DateHelper.dateTime(endTime2);
		if (DateHelper.isEffectiveDate(nowDt1,startDt1,endDt1)){
			dt = 10;
		}

		String startTime3 = DateHelper.getToday()+" 12:01";
		String endTime3 = DateHelper.getToday()+" 18:00";
		Date nowDt2 = DateHelper.dateTime(nowTime);
		Date startDt2 = DateHelper.dateTime(startTime3);
		Date endDt2 = DateHelper.dateTime(endTime3);
		if (DateHelper.isEffectiveDate(nowDt2,startDt2,endDt2)){
			dt = 11;
		}
		String startTime4 = DateHelper.getToday()+" 18:01";
		String today = DateHelper.getToday();
		String endTime4 = DateHelper.plusDate(today,1)+" 04:00";
		Date nowDt3 = DateHelper.dateTime(nowTime);
		Date startDt3 = DateHelper.dateTime(startTime4);
		Date endDt3 = DateHelper.dateTime(endTime4);
		if (DateHelper.isEffectiveDate(nowDt3,startDt3,endDt3)){
			dt = 12;
		}
		return dt;
	}
	private int getDay(int eduCycle) {
		int month = 0;
		if (eduCycle == 4){
			month = 1;
		}else if(eduCycle == 12){
			month = 3;
		}else if(eduCycle == 24){
			month = 6;
		}
		return month;
	}
}