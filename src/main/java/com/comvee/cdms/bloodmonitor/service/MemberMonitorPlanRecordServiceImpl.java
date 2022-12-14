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
                //????????????  1 ????????? 2 ?????????
                memberMonitorPlanVO.setPackageType(po.getIllnessType());
                //???????????????,???????????????????????????????????????????????????
                if (po.getStartMonitorDt() == null || "".equals(po.getStartMonitorDt()) || po.getEndMonitorDt() == null || "".equals(po.getEndMonitorDt())){
                    //????????????id?????????????????????t_prescription??????????????????????????????
                    GetPrescriptionDTO prescriptionDTO = new GetPrescriptionDTO();
                    prescriptionDTO.setMemberId(po.getMemberId());
                    prescriptionDTO.setModifyDt(po.getModifyDt().substring(0,16));
                    PrescriptionPO prescriptionPO = prescriptionMapper.getPrescriptionByParamByMemberIdAndInsertDt(prescriptionDTO);
                    if (prescriptionPO != null && !"".equals(prescriptionPO)){
                        //??????????????????
                        memberMonitorPlanVO.setStartMonitorDt(prescriptionPO.getInsertDt());
                        //??????????????????,??????????????????????????????.
                        int month = getDay(prescriptionPO.getEduCycle());
                        String endDt = DateHelper.cycleMonth(prescriptionPO.getInsertDt(),month);
                        //??????????????????
                        if(po.getEndMonitorDt() != null && !"".equals(po.getEndMonitorDt())){
                            memberMonitorPlanVO.setEndMonitorDt(po.getEndMonitorDt());
                        }else{
                            memberMonitorPlanVO.setEndMonitorDt(endDt+" 23:59");
                        }
                        //??????
                        memberMonitorPlanVO.setOperationType(3);
                    }else{
                        //??????????????????????????????
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
                memberMonitorPlanVO.setWeek(""); //????????????
                //????????????/??????
                DoctorPO doctorPO = this.doctorMapper.getDoctorById(po.getDoctorId());
                memberMonitorPlanVO.setDoctorName(doctorPO.getDoctorName());
                memberMonitorPlanVO.setDepartmentName(doctorPO.getDepartName());
                memberMonitorPlanVO.setInsertDt(po.getInsertDt());
                memberMonitorPlanVO.setModifyDt(po.getModifyDt());
                //??????
                if (po.getInProgress() == 0){ //??????
                    memberMonitorPlanVO.setState(1);
                }else if(po.getInProgress() == 1){ //?????????
                    memberMonitorPlanVO.setState(2);
                }
                planVOList.add(memberMonitorPlanVO);
            }
        }
        ListUtils.sort(planVOList, false, "state", "modifyDt");

        //??????
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
     * ?????????????????????????????????
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
        //????????????->????????????
        MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanMapper.getMemberMonitorPlanBySid(planId);
        //2?????????????????????????????????????????????
        this.memberMonitorPlanDetailService.deleteMonitorPlanDetail(memberMonitorPlanPO.getMemberId() ,memberMonitorPlanPO.getIllnessType());
        //???????????????
        stopAfterTaskCard(memberMonitorPlanPO);
    }

    @Override
    public void deleteMonitorRecord(String planId,Integer operationType) {
        //2:?????????????????????(????????????)
        this.memberMonitorPlanMapper.deleteMonitorByMemberId(planId);
        //??????????????? (????????????)
        this.memberMonitorPlanMapper.updateMonitorPlanDetailByPlanId(planId);
        //?????????????????????
        this.memberMonitorTaskCardMapper.updateMemberMonitorTaskCard(Arrays.asList(planId));
    }

    @Override
    public PrescriptionMonitorPlanVO lookMonitorRecord(String planId,Integer operationType) {
        PrescriptionMonitorPlanVO planVO = new PrescriptionMonitorPlanVO();
        //??????
        GetMemberMonitorDTO getMemberMonitorDTO = new GetMemberMonitorDTO();
        getMemberMonitorDTO.setPlanId(planId);
        MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanMapper.getMemberMonitorPlan(getMemberMonitorDTO);
        planVO.setPackageType(memberMonitorPlanPO.getIllnessType());
        //????????????
        planVO.setPlanName(memberMonitorPlanPO.getPlanName());
        //????????????
        planVO.setApplyExplain(memberMonitorPlanPO.getApplyExplain());
        planVO.setDetailMonitor(memberMonitorPlanPO.getPlanDetail());
        //????????????(?????????????????????xx???????????????xx?????????????????????)
        MemberPO memberPO = this.memberService.getMemberById(memberMonitorPlanPO.getMemberId());
        planVO.setMemberName(memberPO.getMemberName());
        if (memberMonitorPlanPO.getStartMonitorDt() == null || "".equals(memberMonitorPlanPO.getStartMonitorDt())
                || memberMonitorPlanPO.getEndMonitorDt() == null || "".equals(memberMonitorPlanPO.getEndMonitorDt())){
            //????????????id?????????????????????t_prescription??????????????????????????????
            GetPrescriptionDTO prescriptionDTO = new GetPrescriptionDTO();
            prescriptionDTO.setMemberId(memberMonitorPlanPO.getMemberId());
            prescriptionDTO.setModifyDt(memberMonitorPlanPO.getModifyDt().substring(0,16));
            //????????????

            PrescriptionPO prescriptionPO = prescriptionMapper.getPrescriptionByParamByMemberIdAndInsertDt(prescriptionDTO);
            if (prescriptionPO != null){
                //??????????????????
                planVO.setStartMonitorDt(prescriptionPO.getInsertDt());
                //??????????????????,??????????????????????????????.
                int month = getDay(prescriptionPO.getEduCycle());
                String endDt = DateHelper.cycleMonth(prescriptionPO.getInsertDt(),month);
                //??????????????????
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
        //???????????????
        for (MemberMonitorPlanPO planPO : list ) {
            Date now = DateHelper.stringToDate(DateHelper.getToday()); //????????????
            Date start = null;
            Date end = null;
            if (planPO.getStartMonitorDt() == null || "".equals(planPO.getStartMonitorDt()) || planPO.getEndMonitorDt() == null || "".equals(planPO.getEndMonitorDt())){
                //????????????id?????????????????????t_prescription??????????????????????????????
                GetPrescriptionDTO prescriptionDTO = new GetPrescriptionDTO();
                prescriptionDTO.setMemberId(planPO.getMemberId());
                prescriptionDTO.setModifyDt(planPO.getModifyDt().substring(0,16));
                PrescriptionPO prescriptionPO = prescriptionMapper.getPrescriptionByParamByMemberIdAndInsertDt(prescriptionDTO);
                if (prescriptionPO != null){
                    //??????????????????
                    start = DateHelper.stringToDate(prescriptionPO.getInsertDt());
                    //??????????????????,??????????????????????????????
                    int month = getDay(prescriptionPO.getEduCycle());
                    String endDt = DateHelper.cycleMonth(prescriptionPO.getInsertDt(),month);
                    //??????????????????
                    end = DateHelper.stringToDate(endDt);
                }else{
                    //??????????????????
                    start = DateHelper.stringToDate(planPO.getInsertDt());
                    String endTime = DateHelper.plusDate(planPO.getInsertDt(),1);
                    end = DateHelper.stringToDate(endTime);
                }
            }else{

                start = DateHelper.stringToDate(planPO.getStartMonitorDt().substring(0, 10)); //??????????????????
                end = DateHelper.stringToDate(planPO.getEndMonitorDt().substring(0, 10)); //??????????????????
            }
            //?????????????????????????????????
            if (DateHelper.isEffectiveDate(now,start,end)){
                //???????????????????????????(?????????????????????)
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
                        //???????????????????????????????????????
                        if (planPO.getIllnessType() == 1){
                            memberMonitorTaskCardPO.setParamCode(longA.substring(index + 1, longA.length()));
                            //?????????
                            memberMonitorTaskCardPO.setMonitorType(1);
                            memberMonitorTaskCardPO.setIllnessType(1);
                        }else{

                            //??????
                            String type = String.valueOf(longA.substring(index + 1, longA.length()).charAt(2));
                            if (  "1" .equals(type)|| "2".equals(type)){
                                memberMonitorTaskCardPO.setParamCode("9"); //?????????
                            }else if ("3".equals(type) || "4".equals(type)){
                                memberMonitorTaskCardPO.setParamCode("10"); //??????
                            }else if ("5" .equals(type)|| "6".equals(type)){
                                memberMonitorTaskCardPO.setParamCode("11"); //??????
                            }else if ("7".equals(type) || "8".equals(type)){
                                memberMonitorTaskCardPO.setParamCode("12"); //??????
                            }
                            int monitorType = Integer.parseInt(type);
                            if (monitorType%2 == 0){
                                //??????
                                memberMonitorTaskCardPO.setMonitorType(3);
                            }else{
                                //??????
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
     * ?????????????????????
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
     * ????????????????????????
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
     * ????????????????????????????????????
     * @param time
     * @return
     */
    public String getWeekCode(String time){
        String weekCode = "";
        if ("?????????".equals(time)){
            weekCode = "1";
        }else if ("?????????".equals(time)){
            weekCode = "2";
        }else if ("?????????".equals(time)){
            weekCode = "3";
        }else if ("?????????".equals(time)){
            weekCode = "4";
        }else if ("?????????".equals(time)){
            weekCode = "5";
        }else if ("?????????".equals(time)){
            weekCode = "6";
        }else if ("?????????".equals(time)){
            weekCode = "7";
        }
        return weekCode;
    }

    /**
     * ??????????????????????????????????????????
     */
    public void stopAfterTaskCard(MemberMonitorPlanPO memberMonitorPlanPO){
        if (memberMonitorPlanPO != null){
            List<String> taskIds = new ArrayList<>();
            if (memberMonitorPlanPO.getIllnessType() == 1){ //??????
                //1:??????????????????????????????
                String time = memberMonitorPlanPO.getModifyDt().substring(11,16);
                Integer integer = monitorDt(time);
                //??????????????????????????????????????????,?????????????????????????????????????????????
                List<MemberMonitorTaskCardPO> taskCardPOS = this.memberMonitorTaskCardMapper.listAfterMemberMonitorTaskCard(memberMonitorPlanPO.getPlanId(),monitorDt(time),DateHelper.getToday());
                for (MemberMonitorTaskCardPO monitorTaskCardPO : taskCardPOS){
                    taskIds.add(monitorTaskCardPO.getSid());
                }
            }else{ //??????
                //1:???????????????????????????
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
            //????????????????????????????????????????????????
            this.memberMonitorTaskCardMapper.updateMemberMonitorTaskCard(taskIds);
        }
    }


    @Override
    @Transactional
    public void updateMonitorTaskCard(MemberMonitorTaskCardPO memberMonitorTaskCardPO) {
        this.memberMonitorTaskCardMapper.updateMonitorTaskCard(memberMonitorTaskCardPO);
    }
}
