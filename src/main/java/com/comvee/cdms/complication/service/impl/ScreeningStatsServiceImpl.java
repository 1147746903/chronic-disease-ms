package com.comvee.cdms.complication.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.complication.constant.ScreeningConstant;
import com.comvee.cdms.complication.constant.StatsConstant;
import com.comvee.cdms.complication.mapper.ScreeningStatsMapper;
import com.comvee.cdms.complication.model.dto.CountPatientParam;
import com.comvee.cdms.complication.model.dto.ScreeningStatsParam;
import com.comvee.cdms.complication.model.po.AssessDetailStatsPO;
import com.comvee.cdms.complication.model.po.ScreeningStatsDetailPO;
import com.comvee.cdms.complication.model.po.ScreeningStatsPO;
import com.comvee.cdms.complication.model.vo.AssessResultStatsVO;
import com.comvee.cdms.complication.model.vo.ChronicDiseaseStatsVO;
import com.comvee.cdms.complication.model.vo.PatientInfoStatsVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatsVO;
import com.comvee.cdms.complication.service.ScreeningStatsService;
import com.comvee.cdms.foot.constant.FootPrescriptionConstant;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author: suyz
 * @date: 2019/4/16
 */
@Service("statsService")
public class ScreeningStatsServiceImpl implements ScreeningStatsService {

    @Autowired
    private ScreeningStatsMapper statsMapper;

    @Override
    public long abiStats(String doctorId) {
        return this.statsMapper.countScreeningPeople(ScreeningConstant.SCREENING_TYPE_ABI ,doctorId);
    }

    @Override
    public long vptStats(String doctorId) {
        return this.statsMapper.countScreeningPeople(ScreeningConstant.SCREENING_TYPE_VPT ,doctorId);
    }

    @Override
    public PatientInfoStatsVO screeningPatientInfoStats(String doctorId) {
        CountPatientParam countPatientParam;
        //统计男
        countPatientParam = new CountPatientParam();
        countPatientParam.setSex(1);
        countPatientParam.setDoctorId(doctorId);
        long man = this.statsMapper.countPatient(countPatientParam);
        //统计女
        countPatientParam = new CountPatientParam();
        countPatientParam.setSex(2);
        countPatientParam.setDoctorId(doctorId);
        long woman = this.statsMapper.countPatient(countPatientParam);
        //统计20岁以下
        countPatientParam = new CountPatientParam();
        countPatientParam.setBirthdayMin(getDateByAge(21));
        countPatientParam.setDoctorId(doctorId);
        long low_20 = this.statsMapper.countPatient(countPatientParam);
        //20-40岁
        countPatientParam = new CountPatientParam();
        countPatientParam.setBirthdayMin(getDateByAge(41));
        countPatientParam.setBirthdayMax(getDateByAge(21));
        countPatientParam.setDoctorId(doctorId);
        long age20_40 = this.statsMapper.countPatient(countPatientParam);
        //40-60岁
        countPatientParam = new CountPatientParam();
        countPatientParam.setBirthdayMin(getDateByAge(61));
        countPatientParam.setBirthdayMax(getDateByAge(41));
        countPatientParam.setDoctorId(doctorId);
        long age40_60 = this.statsMapper.countPatient(countPatientParam);
        //60-70岁
        countPatientParam = new CountPatientParam();
        countPatientParam.setBirthdayMin(getDateByAge(71));
        countPatientParam.setBirthdayMax(getDateByAge(61));
        countPatientParam.setDoctorId(doctorId);
        long age60_70 = this.statsMapper.countPatient(countPatientParam);
        //70岁以上
        countPatientParam = new CountPatientParam();
        countPatientParam.setBirthdayMax(getDateByAge(71));
        countPatientParam.setDoctorId(doctorId);
        long high_70 = this.statsMapper.countPatient(countPatientParam);

        PatientInfoStatsVO patientInfoStatsVO = new PatientInfoStatsVO();
        patientInfoStatsVO.setManNumber(man);
        patientInfoStatsVO.setWomanNumber(woman);
        patientInfoStatsVO.setLow20(low_20);
        patientInfoStatsVO.setAge20To40(age20_40);
        patientInfoStatsVO.setAge40To60(age40_60);
        patientInfoStatsVO.setAge60To70(age60_70);
        patientInfoStatsVO.setHigh70(high_70);
        return patientInfoStatsVO;
    }

    @Override
    public long countAllPatient(String doctorId) {
        CountPatientParam countPatientParam = new CountPatientParam();
        countPatientParam.setDoctorId(doctorId);
        return this.statsMapper.countPatient(countPatientParam);
    }

    @Override
    public ScreeningStatsVO screeningStats(String doctorId) {
        ScreeningStatsParam screeningStatsParam;
        //全部筛查人数
        screeningStatsParam = new ScreeningStatsParam();
        screeningStatsParam.setDoctorId(doctorId);
        long all = this.statsMapper.screeningStats(screeningStatsParam);
        //VPT与ABI均正常
        screeningStatsParam = new ScreeningStatsParam();
        screeningStatsParam.setAllGood(true);
        screeningStatsParam.setDoctorId(doctorId);
        long allGood = this.statsMapper.screeningStats(screeningStatsParam);
        //仅VPT异常
        screeningStatsParam = new ScreeningStatsParam();
        screeningStatsParam.setVptBad(true);
        screeningStatsParam.setDoctorId(doctorId);
        long vpt = this.statsMapper.screeningStats(screeningStatsParam);
        //仅ABI异常
        screeningStatsParam = new ScreeningStatsParam();
        screeningStatsParam.setAbiBad(true);
        screeningStatsParam.setDoctorId(doctorId);
        long abi = this.statsMapper.screeningStats(screeningStatsParam);
        //ABI及VPT均异常
        screeningStatsParam = new ScreeningStatsParam();
        screeningStatsParam.setAllBad(true);
        screeningStatsParam.setDoctorId(doctorId);
        long allBad = this.statsMapper.screeningStats(screeningStatsParam);
        //筛查有异常的
        screeningStatsParam = new ScreeningStatsParam();
        screeningStatsParam.setHasBad(true);
        screeningStatsParam.setDoctorId(doctorId);
        long hasBad = this.statsMapper.screeningStats(screeningStatsParam);

        ScreeningStatsVO screeningStatsVO = new ScreeningStatsVO();
        screeningStatsVO.setAllPeople(all);
        screeningStatsVO.setAbiAndVptBad(allBad);
        screeningStatsVO.setAbiAndVptGood(allGood);
        screeningStatsVO.setAbiBad(abi);
        screeningStatsVO.setVptBad(vpt);
        screeningStatsVO.setHasBadPeople(hasBad);
        return screeningStatsVO;
    }

    @Override
    public AssessResultStatsVO wagnerLevelStats(String doctorId) {
        Long total = this.statsMapper.countAssessResult(FootPrescriptionConstant.ASSESS_CODE_WAGNER ,doctorId);
        List<AssessDetailStatsPO> list = this.statsMapper.countAssessResultDetail(FootPrescriptionConstant.ASSESS_CODE_WAGNER ,doctorId);
        Map<String ,Long> detailMap = new HashMap<>();
        list.forEach(x ->{
            detailMap.put(FootPrescriptionConstant.ASSESS_CODE_WAGNER + "_" + x.getAssessValue() , x.getCount());
        });
        AssessResultStatsVO assessResultStatsVO = new AssessResultStatsVO();
        assessResultStatsVO.setTotal(total);
        assessResultStatsVO.setDetailStats(detailMap);
        return assessResultStatsVO;
    }

    @Override
    public AssessResultStatsVO gfrStagesStats(String doctorId) {
        Long total = this.statsMapper.countAssessResult(FootPrescriptionConstant.ASSESS_CODE_EGFR ,doctorId);
        Map<String ,Long> detailMap = new HashMap<>();
        List<AssessDetailStatsPO> list = this.statsMapper.countAssessResultDetail(FootPrescriptionConstant.ASSESS_CODE_EGFR ,doctorId);
        list.forEach(x ->{
            detailMap.put(FootPrescriptionConstant.ASSESS_CODE_EGFR + "_" + x.getAssessValue() , x.getCount());
        });
        AssessResultStatsVO assessResultStatsVO = new AssessResultStatsVO();
        assessResultStatsVO.setTotal(total);
        assessResultStatsVO.setDetailStats(detailMap);
        return assessResultStatsVO;
    }

    @Override
    public void addScreeningStats(ScreeningStatsPO screeningStatsPO) {
        ScreeningStatsPO get = this.statsMapper.getScreeningStats(screeningStatsPO.getMemberId() ,screeningStatsPO.getDoctorId() ,screeningStatsPO.getItemCode());
        if(screeningStatsPO.getValid() != null && screeningStatsPO.getValid() == 0){
            this.statsMapper.updateScreeningStats(screeningStatsPO);
        }else if(get == null){
            if(StringUtils.isBlank(screeningStatsPO.getSid())){
                screeningStatsPO.setSid(UUID.randomUUID().toString());
            }
            this.statsMapper.addScreeningStats(screeningStatsPO);
        }else{
            get.setItemValue(screeningStatsPO.getItemValue());
            get.setValid(screeningStatsPO.getValid());
            this.statsMapper.updateScreeningStats(get);
        }
    }

    @Override
    public JSONObject chronicDiseaseStats(String doctorId) {
        JSONObject jsonObject = new JSONObject();
        List<String> itemCodeList = new ArrayList<>();
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_PAD);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_DPN);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_RETINOPATHY);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_NEPHROMA);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_DM_FOOT);

        itemCodeList.forEach(x ->{
            ChronicDiseaseStatsVO chronicDiseaseStatsVO = new ChronicDiseaseStatsVO();
            long total = this.statsMapper.countScreeningStats(x ,null ,doctorId);
            long percent = this.statsMapper.countScreeningStats(x ,"1" ,doctorId);
            chronicDiseaseStatsVO.setTotalNumber(total);
            chronicDiseaseStatsVO.setPercentNumber(percent);
            jsonObject.put(x ,chronicDiseaseStatsVO);
        });

        return jsonObject;
    }

    @Override
    public JSONObject screeningPieChartData(String doctorId) {
        JSONObject result = new JSONObject();
        List<String> itemCodeList = new ArrayList<>();
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_EGFR);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_WAGNER);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_ABI);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_VPT);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_UACR);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_EYE_LEVEL);

        itemCodeList.forEach(x ->{
            Long total = this.statsMapper.countScreeningStats(x , null ,doctorId);
            List<ScreeningStatsDetailPO> list = this.statsMapper.countScreeningStatsDetail(x ,doctorId);
            Map<String ,Long> detailMap = new HashMap<>();
            list.forEach(b ->{
                detailMap.put(x + "_" + b.getItemValue() , b.getCount());
            });
            AssessResultStatsVO assessResultStatsVO = new AssessResultStatsVO();
            assessResultStatsVO.setTotal(total);
            assessResultStatsVO.setDetailStats(detailMap);
            result.put(x ,assessResultStatsVO);
        });
        return result;
    }

    @Override
    public void deleteScreeningStats(String memberId, String doctorId) {
        this.statsMapper.deleteScreeningStats(doctorId ,memberId);
    }

    /**
     * 根据年龄换算日期
     * @param age
     * @return
     */
    private String getDateByAge(int age){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.minusYears(age).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public PatientInfoStatsVO screeningPatientInfoStatsForHos(GetStatisticsDTO dto) {
        //统计男
        dto.setSex(1);
        long man = this.statsMapper.countPatientForHos(dto);
        //统计女
        dto.setSex(2);
        long woman = this.statsMapper.countPatientForHos(dto);
        //统计20岁以下
        dto.setBirthdayMin(getDateByAge(21));
        long low_20 = this.statsMapper.countPatientForHos(dto);
        //20-40岁
        dto.setBirthdayMin(getDateByAge(41));
        dto.setBirthdayMax(getDateByAge(21));
        long age20_40 = this.statsMapper.countPatientForHos(dto);
        //40-60岁
        dto.setBirthdayMin(getDateByAge(61));
        dto.setBirthdayMax(getDateByAge(41));
        long age40_60 = this.statsMapper.countPatientForHos(dto);
        //60-70岁
        dto.setBirthdayMin(getDateByAge(71));
        dto.setBirthdayMax(getDateByAge(61));
        long age60_70 = this.statsMapper.countPatientForHos(dto);
        //70岁以上
        dto.setBirthdayMax(getDateByAge(71));
        long high_70 = this.statsMapper.countPatientForHos(dto);

        PatientInfoStatsVO patientInfoStatsVO = new PatientInfoStatsVO();
        patientInfoStatsVO.setManNumber(man);
        patientInfoStatsVO.setWomanNumber(woman);
        patientInfoStatsVO.setLow20(low_20);
        patientInfoStatsVO.setAge20To40(age20_40);
        patientInfoStatsVO.setAge40To60(age40_60);
        patientInfoStatsVO.setAge60To70(age60_70);
        patientInfoStatsVO.setHigh70(high_70);
        return patientInfoStatsVO;
    }

    @Override
    public JSONObject screeningPieChartDataForHos(GetStatisticsDTO dto) {
        JSONObject result = new JSONObject();
        List<String> itemCodeList = new ArrayList<>();
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_EGFR);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_WAGNER);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_ABI);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_VPT);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_UACR);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_EYE_LEVEL);

        itemCodeList.forEach(x ->{
            Long total = this.statsMapper.countScreeningStatsForHos(x , null ,dto);
            List<ScreeningStatsDetailPO> list = this.statsMapper.countScreeningStatsDetailForHos(x ,dto);
            Map<String ,Long> detailMap = new HashMap<>();
            list.forEach(b ->{
                detailMap.put(x + "_" + b.getItemValue() , b.getCount());
            });
            AssessResultStatsVO assessResultStatsVO = new AssessResultStatsVO();
            assessResultStatsVO.setTotal(total);
            assessResultStatsVO.setDetailStats(detailMap);
            result.put(x ,assessResultStatsVO);
        });
        return result;
    }

    @Override
    public JSONObject chronicDiseaseStatsForHos(GetStatisticsDTO dto) {
        JSONObject jsonObject = new JSONObject();
        List<String> itemCodeList = new ArrayList<>();
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_PAD);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_DPN);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_RETINOPATHY);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_NEPHROMA);
        itemCodeList.add(StatsConstant.STATS_ITEM_CODE_DM_FOOT);

        itemCodeList.forEach(x ->{
            ChronicDiseaseStatsVO chronicDiseaseStatsVO = new ChronicDiseaseStatsVO();
            long total = this.statsMapper.countScreeningStatsForHos(x ,null ,dto);
            long percent = this.statsMapper.countScreeningStatsForHos(x ,"1" ,dto);
            chronicDiseaseStatsVO.setTotalNumber(total);
            chronicDiseaseStatsVO.setPercentNumber(percent);
            jsonObject.put(x ,chronicDiseaseStatsVO);
        });

        return jsonObject;
    }
}
