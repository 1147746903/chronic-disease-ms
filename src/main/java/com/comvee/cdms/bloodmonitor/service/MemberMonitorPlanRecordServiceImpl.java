package com.comvee.cdms.bloodmonitor.service;

import com.comvee.cdms.bloodmonitor.dto.GetMemberMonitorDTO;
import com.comvee.cdms.bloodmonitor.dto.MemberMonitorPlanRecordDTO;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorPlanMapper;
import com.comvee.cdms.bloodmonitor.mapper.MemberMonitorTaskCardMapper;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorTaskCardPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanDetailServiceI;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanRecordService;
import com.comvee.cdms.bloodmonitor.vo.MemberMonitorPlanVO;
import com.comvee.cdms.bloodmonitor.vo.PrescriptionMonitorPlanVO;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.ListUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.prescription.dto.GetPrescriptionDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("MemberMonitorPlanRecordService")
public class MemberMonitorPlanRecordServiceImpl implements MemberMonitorPlanRecordService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberMonitorPlanMapper memberMonitorPlanMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private MemberMonitorTaskCardMapper memberMonitorTaskCardMapper;

    @Autowired
    private PrescriptionMapper prescriptionMapper;
    @Autowired
    @Qualifier("memberMonitorPlanDetailService")
    private MemberMonitorPlanDetailServiceI memberMonitorPlanDetailService;


    @Override
    public PageResult<MemberMonitorPlanVO> listMonitorRecord(PageRequest page, MemberMonitorPlanRecordDTO dto) {
        List<MemberMonitorPlanVO> planVOList = new ArrayList<>();
        List<MemberMonitorPlanPO> planPOS = this.memberMonitorPlanMapper.listMonitorPlan(dto.getMemberId());
        for (MemberMonitorPlanPO po : planPOS){
            if (po != null){
                MemberMonitorPlanVO memberMonitorPlanVO = new MemberMonitorPlanVO();
                memberMonitorPlanVO.setPlanId(po.getPlanId());
                //方案类型  1 糖尿病 2 高血压
                memberMonitorPlanVO.setPackageType(po.getIllnessType());
                //处理旧数据,没有开始时间和结束时间的就是旧数据
                if (po.getStartMonitorDt() == null || "".equals(po.getStartMonitorDt()) || po.getEndMonitorDt() == null || "".equals(po.getEndMonitorDt())){
                    //通过患者id和插入时间获取t_prescription插入的管理处方的数据
                    GetPrescriptionDTO prescriptionDTO = new GetPrescriptionDTO();
                    prescriptionDTO.setMemberId(po.getMemberId());
                    prescriptionDTO.setModifyDt(po.getModifyDt().substring(0,16));
                    PrescriptionPO prescriptionPO = prescriptionMapper.getPrescriptionByParamByMemberIdAndInsertDt(prescriptionDTO);
                    if (prescriptionPO != null && !"".equals(prescriptionPO)){
                        //开始监测时间
                        memberMonitorPlanVO.setStartMonitorDt(prescriptionPO.getInsertDt());
                        //管理处方来的,通过周期计算结束天数.
                        int month = getDay(prescriptionPO.getEduCycle());
                        String endDt = DateHelper.cycleMonth(prescriptionPO.getInsertDt(),month);
                        //结束监测时间
                        if(po.getEndMonitorDt() != null && !"".equals(po.getEndMonitorDt())){
                            memberMonitorPlanVO.setEndMonitorDt(po.getEndMonitorDt());
                        }else{
                            memberMonitorPlanVO.setEndMonitorDt(endDt+" 23:59");
                        }
                        //来源
                        memberMonitorPlanVO.setOperationType(3);
                    }else{
                        //血糖管理添加的旧数据
                        memberMonitorPlanVO.setStartMonitorDt(po.getInsertDt());
                        if(po.getEndMonitorDt() != null && !"".equals(po.getEndMonitorDt())){
                            memberMonitorPlanVO.setEndMonitorDt(po.getEndMonitorDt());
                        }else{
                            memberMonitorPlanVO.setEndMonitorDt(DateHelper.plusDate(po.getInsertDt(),1).substring(0,10)+" 23:59");
                        }
                        memberMonitorPlanVO.setOperationType(1);
                    }
                }else{
                    memberMonitorPlanVO.setStartMonitorDt(po.getStartMonitorDt());
                    memberMonitorPlanVO.setEndMonitorDt(po.getEndMonitorDt());
                    memberMonitorPlanVO.setOperationType(po.getOperationType());
                }
                memberMonitorPlanVO.setWeek(""); //暂时不用
                //指定医生/科室
                DoctorPO doctorPO = this.doctorMapper.getDoctorById(po.getDoctorId());
                memberMonitorPlanVO.setDoctorName(doctorPO.getDoctorName());
                memberMonitorPlanVO.setDepartmentName(doctorPO.getDepartName());
                memberMonitorPlanVO.setInsertDt(po.getInsertDt());
                memberMonitorPlanVO.setModifyDt(po.getModifyDt());
                //状态
                if (po.getInProgress() == 0){ //停止
                    memberMonitorPlanVO.setState(1);
                }else if(po.getInProgress() == 1){ //执行中
                    memberMonitorPlanVO.setState(2);
                }
                planVOList.add(memberMonitorPlanVO);
            }
        }
        ListUtils.sort(planVOList, false, "state", "modifyDt");

        //分页
        PageResult<MemberMonitorPlanVO>  result = new PageResult<>(planVOList);
        result.setPageNum(page.getPage());
        result.setPageSize(page.getRows());
        result.setTotalPages(planVOList.size()%page.getRows() == 0 ? planVOList.size()/page.getRows():planVOList.size()/page.getRows()+1);
        result.setTotalRows(planVOList.size());
        int startRow = (page.getPage()-1)*page.getRows();
        int endRow = page.getPage()*page.getRows();
        if (endRow>planVOList.size()){
            endRow = planVOList.size();
        }
        List<MemberMonitorPlanVO> list = new ArrayList<>();
        for (int i = startRow;i<endRow;i++){
            list.add(planVOList.get(i));
        }
        result.setRows(list);
        return result;
    }

    /**
     * 见周期转化为对应的月数
     * @param eduCycle
     * @return
     */
    public int getDay(int eduCycle) {
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

    @Override
    public void stopMonitorRecord(String planId,Integer operationType) {

        this.memberMonitorPlanMapper.modifyMonitorPlanByPlanId(planId);
        //血糖记录->监测方案
        MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanMapper.getMemberMonitorPlanBySid(planId);
        //2、删除监测方案详细（物理删除）
        this.memberMonitorPlanDetailService.deleteMonitorPlanDetail(memberMonitorPlanPO.getMemberId() ,memberMonitorPlanPO.getIllnessType());
        //停止任务卡
        stopAfterTaskCard(memberMonitorPlanPO);
    }

    @Override
    public void deleteMonitorRecord(String planId,Integer operationType) {
        //2:删除方案表数据(逻辑删除)
        this.memberMonitorPlanMapper.deleteMonitorByMemberId(planId);
        //删除细项表 (物理删除)
        this.memberMonitorPlanMapper.updateMonitorPlanDetailByPlanId(planId);
        //停止监测任务卡
        this.memberMonitorTaskCardMapper.updateMemberMonitorTaskCard(Arrays.asList(planId));
    }

    @Override
    public PrescriptionMonitorPlanVO lookMonitorRecord(String planId,Integer operationType) {
        PrescriptionMonitorPlanVO planVO = new PrescriptionMonitorPlanVO();
        //血糖
        GetMemberMonitorDTO getMemberMonitorDTO = new GetMemberMonitorDTO();
        getMemberMonitorDTO.setPlanId(planId);
        MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanMapper.getMemberMonitorPlan(getMemberMonitorDTO);
        planVO.setPackageType(memberMonitorPlanPO.getIllnessType());
        //方案名称
        planVO.setPlanName(memberMonitorPlanPO.getPlanName());
        //适用说明
        planVO.setApplyExplain(memberMonitorPlanPO.getApplyExplain());
        planVO.setDetailMonitor(memberMonitorPlanPO.getPlanDetail());
        //方案名称(方案名称默认为xx监测方案，xx指当前患者名称)
        MemberPO memberPO = this.memberService.getMemberById(memberMonitorPlanPO.getMemberId());
        planVO.setMemberName(memberPO.getMemberName());
        if (memberMonitorPlanPO.getStartMonitorDt() == null || "".equals(memberMonitorPlanPO.getStartMonitorDt())
                || memberMonitorPlanPO.getEndMonitorDt() == null || "".equals(memberMonitorPlanPO.getEndMonitorDt())){
            //通过患者id和插入时间获取t_prescription插入的管理处方的数据
            GetPrescriptionDTO prescriptionDTO = new GetPrescriptionDTO();
            prescriptionDTO.setMemberId(memberMonitorPlanPO.getMemberId());
            prescriptionDTO.setModifyDt(memberMonitorPlanPO.getModifyDt().substring(0,16));
            //监测细项

            PrescriptionPO prescriptionPO = prescriptionMapper.getPrescriptionByParamByMemberIdAndInsertDt(prescriptionDTO);
            if (prescriptionPO != null){
                //开始监测时间
                planVO.setStartMonitorDt(prescriptionPO.getInsertDt());
                //管理处方来的,通过周期计算结束天数.
                int month = getDay(prescriptionPO.getEduCycle());
                String endDt = DateHelper.cycleMonth(prescriptionPO.getInsertDt(),month);
                //结束监测时间
                if(memberMonitorPlanPO.getEndMonitorDt() != null && !"".equals(memberMonitorPlanPO.getEndMonitorDt())){
                    planVO.setEndMonitorDt(memberMonitorPlanPO.getEndMonitorDt());
                }else{
                    planVO.setEndMonitorDt(endDt+" 23:59");
                }
            }else{
                planVO.setStartMonitorDt(memberMonitorPlanPO.getInsertDt());
                planVO.setEndMonitorDt(DateHelper.plusDate(memberMonitorPlanPO.getInsertDt(),1).substring(0,10)+" 23:59");
            }
        }else{
            planVO.setStartMonitorDt(memberMonitorPlanPO.getStartMonitorDt());
            planVO.setEndMonitorDt(memberMonitorPlanPO.getEndMonitorDt());
        }

        return planVO;
    }

    @Override
    public void addMemberMonitorTaskCard(List<MemberMonitorPlanPO> list){
        //生成任务卡
        for (MemberMonitorPlanPO planPO : list ) {
            Date now = DateHelper.stringToDate(DateHelper.getToday()); //当前时间
            Date start = null;
            Date end = null;
            if (planPO.getStartMonitorDt() == null || "".equals(planPO.getStartMonitorDt()) || planPO.getEndMonitorDt() == null || "".equals(planPO.getEndMonitorDt())){
                //通过患者id和插入时间获取t_prescription插入的管理处方的数据
                GetPrescriptionDTO prescriptionDTO = new GetPrescriptionDTO();
                prescriptionDTO.setMemberId(planPO.getMemberId());
                prescriptionDTO.setModifyDt(planPO.getModifyDt().substring(0,16));
                PrescriptionPO prescriptionPO = prescriptionMapper.getPrescriptionByParamByMemberIdAndInsertDt(prescriptionDTO);
                if (prescriptionPO != null){
                    //开始监测时间
                    start = DateHelper.stringToDate(prescriptionPO.getInsertDt());
                    //管理处方来的,通过周期计算结束月数
                    int month = getDay(prescriptionPO.getEduCycle());
                    String endDt = DateHelper.cycleMonth(prescriptionPO.getInsertDt(),month);
                    //结束监测时间
                    end = DateHelper.stringToDate(endDt);
                }else{
                    //开始监测时间
                    start = DateHelper.stringToDate(planPO.getInsertDt());
                    String endTime = DateHelper.plusDate(planPO.getInsertDt(),1);
                    end = DateHelper.stringToDate(endTime);
                }
            }else{

                start = DateHelper.stringToDate(planPO.getStartMonitorDt().substring(0, 10)); //开始监测时间
                end = DateHelper.stringToDate(planPO.getEndMonitorDt().substring(0, 10)); //结束监测时间
            }
            //取大于或等于开始时间的
            if (DateHelper.isEffectiveDate(now,start,end)){
                //查询当前时间是周几(获取对应的数字)
                String weekCode = getWeekCode(DateHelper.getWeek());
                String detail = planPO.getPlanDetail();
                String [] longArray = detail.split(";");
                for (String longA : longArray){
                    int index = longA.indexOf("-");
                    String dateCode = longA.substring(0,index);
                    if (dateCode.equals(weekCode)){
                        MemberMonitorTaskCardPO memberMonitorTaskCardPO = new MemberMonitorTaskCardPO();
                        memberMonitorTaskCardPO.setSid(DaoHelper.getSeq());
                        memberMonitorTaskCardPO.setPlanId(planPO.getPlanId());
                        memberMonitorTaskCardPO.setMemberId(planPO.getMemberId());
                        memberMonitorTaskCardPO.setDateCode(longA.substring(0,index));
                        //将对应点的数值转化为时间点
                        if (planPO.getIllnessType() == 1){
                            memberMonitorTaskCardPO.setParamCode(longA.substring(index + 1, longA.length()));
                            //糖尿病
                            memberMonitorTaskCardPO.setMonitorType(1);
                            memberMonitorTaskCardPO.setIllnessType(1);
                        }else{

                            //血压
                            String type = String.valueOf(longA.substring(index + 1, longA.length()).charAt(2));
                            if (  "1" .equals(type)|| "2".equals(type)){
                                memberMonitorTaskCardPO.setParamCode("9"); //早起后
                            }else if ("3".equals(type) || "4".equals(type)){
                                memberMonitorTaskCardPO.setParamCode("10"); //上午
                            }else if ("5" .equals(type)|| "6".equals(type)){
                                memberMonitorTaskCardPO.setParamCode("11"); //下午
                            }else if ("7".equals(type) || "8".equals(type)){
                                memberMonitorTaskCardPO.setParamCode("12"); //晚上
                            }
                            int monitorType = Integer.parseInt(type);
                            if (monitorType%2 == 0){
                                //脉搏
                                memberMonitorTaskCardPO.setMonitorType(3);
                            }else{
                                //血压
                                memberMonitorTaskCardPO.setMonitorType(2);
                            }
                            memberMonitorTaskCardPO.setIllnessType(2);
                        }
                        memberMonitorTaskCardPO.setMonitorTime(longA.substring(index + 1, longA.length()));
                        memberMonitorTaskCardPO.setMonitorDt(DateHelper.getToday());
                        List<MemberMonitorTaskCardPO> all = this.memberMonitorTaskCardMapper.findAll(planPO.getMemberId(),DateHelper.getToday(),longA.substring(index + 1, longA.length()));
                        if (all.size() == 0 || all == null){
                            this.memberMonitorTaskCardMapper.addMemberMonitorTaskCard(memberMonitorTaskCardPO);
                        }
                    }

                }

            }
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

    /**
     * 取对应的周范围对应的数字
     * @param time
     * @return
     */
    public String getWeekCode(String time){
        String weekCode = "";
        if ("星期一".equals(time)){
            weekCode = "1";
        }else if ("星期二".equals(time)){
            weekCode = "2";
        }else if ("星期三".equals(time)){
            weekCode = "3";
        }else if ("星期四".equals(time)){
            weekCode = "4";
        }else if ("星期五".equals(time)){
            weekCode = "5";
        }else if ("星期六".equals(time)){
            weekCode = "6";
        }else if ("星期日".equals(time)){
            weekCode = "7";
        }
        return weekCode;
    }

    /**
     * 处理时间点之后的任务卡为失效
     */
    public void stopAfterTaskCard(MemberMonitorPlanPO memberMonitorPlanPO){
        if (memberMonitorPlanPO != null){
            List<String> taskIds = new ArrayList<>();
            if (memberMonitorPlanPO.getIllnessType() == 1){ //血糖
                //1:取该方案停止的时间点
                String time = memberMonitorPlanPO.getModifyDt().substring(11,16);
                Integer integer = monitorDt(time);
                //获取这停止的时间点之后的数据,将后面时间点及以后的修改为无效
                List<MemberMonitorTaskCardPO> taskCardPOS = this.memberMonitorTaskCardMapper.listAfterMemberMonitorTaskCard(memberMonitorPlanPO.getPlanId(),monitorDt(time),DateHelper.getToday());
                for (MemberMonitorTaskCardPO monitorTaskCardPO : taskCardPOS){
                    taskIds.add(monitorTaskCardPO.getSid());
                }
            }else{ //血压
                //1:取该方案的停止时间
                String time = memberMonitorPlanPO.getModifyDt().substring(11,16);
                List<MemberMonitorTaskCardPO> taskCardPOS = this.memberMonitorTaskCardMapper.listAfterMemberMonitorTaskCard(memberMonitorPlanPO.getPlanId(),bloodPressure(time),DateHelper.getToday());
                for (MemberMonitorTaskCardPO monitorTaskCardPO : taskCardPOS){
                    taskIds.add(monitorTaskCardPO.getSid());
                }
            }
            if (taskIds == null || "".equals(taskIds)){
                return;
            }
            System.out.println("id"+taskIds);
            //将对应时间点之后的监测点改为失效
            this.memberMonitorTaskCardMapper.updateMemberMonitorTaskCard(taskIds);
        }
    }


    @Override
    @Transactional
    public void updateMonitorTaskCard(MemberMonitorTaskCardPO memberMonitorTaskCardPO) {
        this.memberMonitorTaskCardMapper.updateMonitorTaskCard(memberMonitorTaskCardPO);
    }
}
