package com.comvee.cdms.dybloodpressure.service.impl;


import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodpressure.bo.DyBloodPressureDataBO;
import com.comvee.cdms.dybloodpressure.constant.DyBloodPressureConstant;
import com.comvee.cdms.dybloodpressure.dto.*;
import com.comvee.cdms.dybloodpressure.mapper.*;
import com.comvee.cdms.dybloodpressure.po.*;
import com.comvee.cdms.dybloodpressure.service.DyBloodPressureService;
import com.comvee.cdms.dybloodpressure.vo.DyBloodPressureDiaryVO;
import com.comvee.cdms.dybloodpressure.vo.ListDayDyBloodPressureVO;
import com.comvee.cdms.dybloodsugar.dto.DyRememberSleepDTO;
import com.comvee.cdms.dybloodsugar.service.DyRememberService;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.records.mapper.DrugsRecordMapper;
import com.comvee.cdms.records.mapper.SportRecordMapper;
import com.comvee.cdms.records.model.dto.DrugsRecordDTO;
import com.comvee.cdms.records.model.dto.SportRecordDTO;
import com.comvee.cdms.records.model.po.DrugsRecordPO;
import com.comvee.cdms.records.model.po.SportRecordPO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author linr
 * @Date 2021/10/19
 */
@Service
public class DyBloodPressureServiceImpl implements DyBloodPressureService {


    @Autowired
    private DyBloodPressureMapper dyBloodPressureMapper;

    @Autowired
    private BpMemberMachineMapper bpMemberMachineMapper;

    @Autowired
    private DyBloodPressureDiaryMapper dyBloodPressureDiaryMapper;

    @Autowired
    private DyBloodPressureDetailMapper dyBloodPressureDetailMapper;

    @Autowired
    private DyBloodPressureReportMapper dyBloodPressureReportMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private DyRememberService dyRememberService;

    @Autowired
    private SportRecordMapper sportRecordMapper;

    @Autowired
    private DrugsRecordMapper drugsRecordMapper;

    private final static Logger log = LoggerFactory.getLogger(DyBloodPressureServiceImpl.class);


    @Override
    @Transactional
    public void addDyBloodPressure(BatchAddDYBloodPressureDTO batchDTO) {
        BpMemberMachinePO machinePO = bpMemberMachineMapper.queryByMachineNo(batchDTO.getMachineNo());
        if (null == machinePO){
            throw new BusinessException("???????????????????????????,?????????");
        }
        List<AddDyBpBaseDataDTO> dataList = batchDTO.getList();
        if (!StringUtils.isBlank(batchDTO.getMachineNo()) && !StringUtils.isBlank(batchDTO.getMachineModel())
            && null != dataList && dataList.size() > 0) {
            for (AddDyBpBaseDataDTO addDyBpBaseDataDTO : dataList) {
                boolean exists = false;
                if(StringUtils.isBlank(addDyBpBaseDataDTO.getSbp()) || StringUtils.isBlank(addDyBpBaseDataDTO.getDbp())
                        || StringUtils.isBlank(addDyBpBaseDataDTO.getRecordTime()) || null == addDyBpBaseDataDTO.getStartType()){
                    continue;
                }
                if(Double.parseDouble(addDyBpBaseDataDTO.getDbp()) < 0){
                    log.warn("???????????????????????????????????????:{},?????????:{},????????????:{}" ,addDyBpBaseDataDTO.getDbp(),batchDTO.getMachineNo() , addDyBpBaseDataDTO.getRecordTime());
                    continue;
                }
                if(Double.parseDouble(addDyBpBaseDataDTO.getSbp()) < 0){
                    log.warn("???????????????????????????????????????:{},?????????:{},????????????:{}" ,addDyBpBaseDataDTO.getSbp(),batchDTO.getMachineNo() , addDyBpBaseDataDTO.getRecordTime());
                    continue;
                }
                //??????????????????
                DyBloodPressurePO addMapperPO = new DyBloodPressurePO();
                BeanUtils.copyProperties(addMapperPO, batchDTO);
                BeanUtils.copyProperties(addMapperPO, addDyBpBaseDataDTO);
                addMapperPO.setSid(DaoHelper.getSeq());
                addMapperPO.setMemberId(machinePO.getMemberId());
                addMapperPO.setMachineType(machinePO.getMachineType());
                int avgMap = (int) ((Double.parseDouble(addDyBpBaseDataDTO.getSbp())+Double.parseDouble(addDyBpBaseDataDTO.getDbp())*2)/3);
                addMapperPO.setMap(String.valueOf(avgMap));

                //??????????????????
                DyBloodPressurePO getPO = new DyBloodPressurePO();
                getPO.setMachineNo(batchDTO.getMachineNo());
                getPO.setMemberId(machinePO.getMemberId());
                getPO.setRecordTime(addDyBpBaseDataDTO.getRecordTime());
                List<DyBloodPressurePO> dyBloodPressurePOS = dyBloodPressureMapper.queryAll(getPO);
                if (null != dyBloodPressurePOS && dyBloodPressurePOS.size() > 0) {
                    exists = true;
                }
                log.info("???????????????????????? -- >????????????:{} | ??????????????????:{} | ?????????:{} | ?????????:{} | ??????:{} | ????????????????????????:{}"
                        , batchDTO.getMachineNo(), addDyBpBaseDataDTO.getRecordTime(), addDyBpBaseDataDTO.getDbp(), addDyBpBaseDataDTO.getSbp(), addDyBpBaseDataDTO.getHeartRate(), exists ? "???" : "???");
                if (exists) {
                    continue;//?????????????????????
                }

                //???????????????
                bloodPressureValidHandler(addMapperPO);
                if (dyBloodPressureMapper.insert(addMapperPO)>0){
                    //??????????????????
                    DyBloodPressureDetailPO detailPO = assertBloodPressureLevel(addMapperPO,null);
                    GetDyBloodPressureDiaryDTO dto = startEndDtHandler(addDyBpBaseDataDTO.getRecordTime());
                    detailPO.setStartDt(dto.getStartDt());
                    detailPO.setEndDt(dto.getEndDt());
                    dyBloodPressureDetailMapper.insert(detailPO);
                }
            }
        }
    }

    /**
     * ?????????????????????????????????
     * @param dyBloodPressurePO
     */
    private void bloodPressureValidHandler(DyBloodPressurePO dyBloodPressurePO){
        dyBloodPressurePO.setIsValid(1);
        String dbp = dyBloodPressurePO.getDbp();
        String sbp = dyBloodPressurePO.getSbp();
        String heartRate = dyBloodPressurePO.getHeartRate();
        if (dbp.equals("0") || sbp.equals("0")||heartRate.equals("0")
            || Double.parseDouble(dbp) > 270 || Double.parseDouble(sbp) > 270){
            dyBloodPressurePO.setIsValid(0);
        }
    }




    /**
     * ??????????????????????????????
     * @param dyBloodPressurePO
     * @return
     */
    private DyBloodPressureDetailPO assertBloodPressureLevel(DyBloodPressurePO dyBloodPressurePO, DyBloodPressureDiaryPO diaryPO){
        String dbpDayLevel = "--";//?????????????????????????????????
        String sbpDayLevel = "--";//?????????????????????????????????
        String dbpSleepLevel = "--";//?????????????????????????????????
        String sbpSleepLevel = "--";//?????????????????????????????????
        String dbpAfterBedLevel = "--";//????????????????????????????????????
        String sbpAfterBedLevel = "--";//????????????????????????????????????
        String dbp24hLevel = DyBloodPressureConstant.BP_LEVEL_NORMAL;//?????????24h??????
        String sbp24hLevel = DyBloodPressureConstant.BP_LEVEL_NORMAL;//?????????24h??????

        double sbp = Double.parseDouble(dyBloodPressurePO.getSbp());
        double dbp = Double.parseDouble(dyBloodPressurePO.getDbp());

        Integer timeType = this.bpTimeTypeHandler(dyBloodPressurePO,diaryPO);//??????????????????
        //?????????????????????
        if (timeType == DyBloodPressureConstant.RECORD_TIME_TYPE_AFTER_BED){
            sbpAfterBedLevel = DyBloodPressureConstant.BP_LEVEL_NORMAL;
            dbpAfterBedLevel = DyBloodPressureConstant.BP_LEVEL_NORMAL;
            sbpDayLevel = DyBloodPressureConstant.BP_LEVEL_NORMAL;
            dbpDayLevel = DyBloodPressureConstant.BP_LEVEL_NORMAL;
            //???????????????
            if(sbp >= DyBloodPressureConstant.SBP_AFTER_BED_MAX){
                sbpAfterBedLevel = DyBloodPressureConstant.BP_LEVEL_HIGH;
            }else if(sbp < DyBloodPressureConstant.SBP_AFTER_BED_MIN){
                sbpAfterBedLevel = DyBloodPressureConstant.BP_LEVEL_LOW;
            }
            if(sbp >= DyBloodPressureConstant.SBP_DAY_MAX){
                sbpDayLevel = DyBloodPressureConstant.BP_LEVEL_HIGH;
            }else if(sbp < DyBloodPressureConstant.SBP_DAY_MIN){
                sbpDayLevel = DyBloodPressureConstant.BP_LEVEL_LOW;
            }
            //???????????????
            if(dbp >= DyBloodPressureConstant.DBP_AFTER_BED_MAX){
                dbpAfterBedLevel = DyBloodPressureConstant.BP_LEVEL_HIGH;
            }else if(dbp < DyBloodPressureConstant.DBP_AFTER_BED_MIN){
                dbpAfterBedLevel = DyBloodPressureConstant.BP_LEVEL_LOW;
            }
            if(dbp >= DyBloodPressureConstant.DBP_DAY_MAX){
                dbpDayLevel = DyBloodPressureConstant.BP_LEVEL_HIGH;
            }else if(dbp < DyBloodPressureConstant.DBP_DAY_MIN){
                dbpDayLevel = DyBloodPressureConstant.BP_LEVEL_LOW;
            }
        }
        //??????????????????
        else if (timeType == DyBloodPressureConstant.RECORD_TIME_TYPE_DAY_WAKE){
            sbpDayLevel = DyBloodPressureConstant.BP_LEVEL_NORMAL;
            dbpDayLevel = DyBloodPressureConstant.BP_LEVEL_NORMAL;
            //???????????????
            if(sbp >= DyBloodPressureConstant.SBP_DAY_MAX){
                sbpDayLevel = DyBloodPressureConstant.BP_LEVEL_HIGH;
            }else if(sbp < DyBloodPressureConstant.SBP_DAY_MIN){
                sbpDayLevel = DyBloodPressureConstant.BP_LEVEL_LOW;
            }
            //???????????????
            if(dbp >= DyBloodPressureConstant.DBP_DAY_MAX){
                dbpDayLevel = DyBloodPressureConstant.BP_LEVEL_HIGH;
            }else if(dbp < DyBloodPressureConstant.DBP_DAY_MIN){
                dbpDayLevel = DyBloodPressureConstant.BP_LEVEL_LOW;
            }
        }
        //??????????????????
        else if (timeType == DyBloodPressureConstant.RECORD_TIME_TYPE_NIGHT_SLEEP){
            sbpSleepLevel = DyBloodPressureConstant.BP_LEVEL_NORMAL;
            dbpSleepLevel = DyBloodPressureConstant.BP_LEVEL_NORMAL;
            //???????????????
            if(sbp >= DyBloodPressureConstant.SBP_SLEEP_MAX){
                sbpSleepLevel = DyBloodPressureConstant.BP_LEVEL_HIGH;
            }else if(sbp < DyBloodPressureConstant.SBP_SLEEP_MIN){
                sbpSleepLevel = DyBloodPressureConstant.BP_LEVEL_LOW;
            }
            //???????????????
            if(dbp >= DyBloodPressureConstant.DBP_SLEEP_MAX){
                dbpSleepLevel = DyBloodPressureConstant.BP_LEVEL_HIGH;
            }else if(dbp < DyBloodPressureConstant.DBP_SLEEP_MIN){
                dbpSleepLevel = DyBloodPressureConstant.BP_LEVEL_LOW;
            }
        }
        //24h??????
        //???????????????
        if(sbp >= DyBloodPressureConstant.SBP_24H_MAX){
            sbp24hLevel = DyBloodPressureConstant.BP_LEVEL_HIGH;
        }else if(sbp < DyBloodPressureConstant.SBP_24H_MIN){
            sbp24hLevel = DyBloodPressureConstant.BP_LEVEL_LOW;
        }
        //???????????????
        if(dbp >= DyBloodPressureConstant.DBP_24H_MAX){
            dbp24hLevel = DyBloodPressureConstant.BP_LEVEL_HIGH;
        }else if(dbp < DyBloodPressureConstant.DBP_24H_MIN){
            dbp24hLevel = DyBloodPressureConstant.BP_LEVEL_LOW;
        }

        DyBloodPressureDetailPO detailPO = new DyBloodPressureDetailPO();
        detailPO.setSid(DaoHelper.getSeq());
        detailPO.setForeignId(dyBloodPressurePO.getSid());
        detailPO.setMemberId(dyBloodPressurePO.getMemberId());
        detailPO.setTimeType(timeType);
        detailPO.setDbpDayLevel(dbpDayLevel);
        detailPO.setSbpDayLevel(sbpDayLevel);
        detailPO.setDbpSleepLevel(dbpSleepLevel);
        detailPO.setSbpSleepLevel(sbpSleepLevel);
        detailPO.setDbpAfterBedLevel(dbpAfterBedLevel);
        detailPO.setSbpAfterBedLevel(sbpAfterBedLevel);
        detailPO.setDbp24hLevel(dbp24hLevel);
        detailPO.setSbp24hLevel(sbp24hLevel);
        return detailPO;
    }

    /**
     * ????????????????????????
     * @param dyBloodPressurePO
     * @return
     *   1; //?????????????????????
     *   2; //??????????????????
     *   3; //??????????????????
     *   4; //??????????????????
     */
    private Integer bpTimeTypeHandler(DyBloodPressurePO dyBloodPressurePO,DyBloodPressureDiaryPO bpSet){
        String recordTime = dyBloodPressurePO.getRecordTime().substring(11,19);
        String bedTime = DyBloodPressureConstant.DEFAULT_BED_TIME;//????????????
        String sleepDt =DyBloodPressureConstant.DEFAULT_SLEEP_TIME;//????????????
        String bedTimePlus2 = "08:00:00";//??????????????????
        String noonSleepStart = null;//??????????????????
        String noonSleepEnd = null;//??????????????????

        GetDyBloodPressureDiaryDTO dto = startEndDtHandler(dyBloodPressurePO.getRecordTime());
        dto.setMemberId(dyBloodPressurePO.getMemberId());
        if (null == bpSet){
            bpSet = dyBloodPressureDiaryMapper.queryByMemberId(dto);
        }
        if (null != bpSet){
            bedTimePlus2 = StringUtils.isBlank(bpSet.getBedDt())?"08:00:00": DateHelper.plusDate(bpSet.getBedDt(), 4, 2, DateHelper.TIME_SECOND_FORMAT);
            bedTime = StringUtils.isBlank(bpSet.getBedDt())?DyBloodPressureConstant.DEFAULT_BED_TIME: bpSet.getBedDt();
            sleepDt = StringUtils.isBlank(bpSet.getNightSleepStart())?DyBloodPressureConstant.DEFAULT_SLEEP_TIME:bpSet.getNightSleepStart();
            noonSleepStart = bpSet.getNoonSleepStart();
            noonSleepEnd = bpSet.getNoonSleepEnd();
        }
        //??????????????????
        if (DateHelper.comparisonTime(sleepDt+"-"+bedTime,recordTime)){
            //????????????????????????????????????????????????????????????
            return DyBloodPressureConstant.RECORD_TIME_TYPE_NIGHT_SLEEP;
        }else {
            //????????????
            if (null != noonSleepStart && null != noonSleepEnd){
                if (DateHelper.comparisonTime(noonSleepStart+"-"+noonSleepEnd,recordTime)){
                    return DyBloodPressureConstant.RECORD_TIME_TYPE_DAY_SLEEP;
                }
            }//??????????????????
            if (DateHelper.comparisonTime(bedTime+"-"+bedTimePlus2,recordTime)){
                return DyBloodPressureConstant.RECORD_TIME_TYPE_AFTER_BED;
            }
            //????????????
            return DyBloodPressureConstant.RECORD_TIME_TYPE_DAY_WAKE;
        }
    }


    @Override
    public Map<String, Object> getDynBloodPressureData(GetDynBloodPressureDataDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();
        List<DyBloodPressureDataBO> allList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(dto);
        if (allList.isEmpty()){
            resultMap.put("code",0);//????????????
            return resultMap;
        }
        boolean flag = bloodPressureListValidJud(allList);
        if (!flag){
            resultMap.put("code","-1");//?????????????????????????????????
            return resultMap;
        }
        //????????????
        dto.setTypeList(Arrays.asList(DyBloodPressureConstant.RECORD_TIME_TYPE_AFTER_BED,DyBloodPressureConstant.RECORD_TIME_TYPE_DAY_WAKE));
        List<DyBloodPressureDataBO> dayList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(dto);
        Map<String, Object> dayMap = dynBloodPressureDataHandler(dayList, dto.getMemberId(),1,dto.getStartDt(),dto.getEndDt());//??????????????????
        //????????????
        dto.setTypeList(Arrays.asList(DyBloodPressureConstant.RECORD_TIME_TYPE_NIGHT_SLEEP));
        List<DyBloodPressureDataBO> nightList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(dto);
        Map<String, Object> nightMap = dynBloodPressureDataHandler(nightList, dto.getMemberId(),2,dto.getStartDt(),dto.getEndDt());//??????????????????
        //?????????????????????
        dto.setTypeList(Arrays.asList(DyBloodPressureConstant.RECORD_TIME_TYPE_AFTER_BED));
        List<DyBloodPressureDataBO> bedList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(dto);
        Map<String, Object> bedMap = dynBloodPressureDataHandler(bedList, dto.getMemberId(),3,dto.getStartDt(),dto.getEndDt());//?????????????????????
        //24h
        Map<String, Object> allMap = dynBloodPressureDataHandler(allList, dto.getMemberId(),4,dto.getStartDt(),dto.getEndDt());//24h
        resultMap.put("code","1");
        resultMap.put("day",dayMap);
        resultMap.put("night",nightMap);
        resultMap.put("bed",bedMap);
        resultMap.put("all",allMap);
        return resultMap;
    }

    //???????????????????????? type1?????????????????? type2?????????????????? type3????????????????????? type4 24h
    private Map<String, Object> dynBloodPressureDataHandler(List<DyBloodPressureDataBO> allList,String memberId,Integer type,String startDt,String endDt) {

        //??????????????????(valid=1 && ????????????)
        List<DyBloodPressureDataBO> list = allList.stream().filter(e -> !e.getTimeType().equals(3) && e.getIsValid().equals(1)).collect(Collectors.toList());
        //??????????????????(valid=1 && ????????????)
        List<DyBloodPressureDataBO> noonSleeplist = allList.stream().filter(e -> e.getIsValid().equals(1)).collect(Collectors.toList());

        Integer avgDbp =null;//??????????????????
        Integer maxDbp =null;//??????????????????
        Integer minDbp = null;//??????????????????
        Integer midDbp = null;//??????????????????
        Integer standardDbp = null;//??????????????????
        String dbpLoad = "";//??????????????????
        String dbpVarCoefficient = "";//?????????????????????
        String dbpDayLowNight = "";//??????????????????????????????
        String dbpMorningPeak = "";//???????????????

        Integer avgSbp =null;//??????????????????
        Integer maxSbp =null;//??????????????????
        Integer minSbp = null;//??????????????????
        Integer midSbp = null;//??????????????????
        Integer standardSbp = null;//??????????????????
        String sbpLoad = "";//?????????????????????
        String sbpVarCoefficient = "";//?????????????????????
        String sbpDayLowNight = "";//??????????????????????????????
        String sbpMorningPeak = "";//???????????????

        Integer avgHrate = null;//???????????????
        Integer maxHrate= null;//???????????????
        Integer minHrate= null;//???????????????
        Integer midHrate= null;//???????????????
        Integer standardHrate= null;//???????????????
        String heartVarCoefficient= "";//??????????????????

        String arteriosclerosis = "";//??????????????????

        NumberFormat numberFormat = NumberFormat.getInstance();
        if (list.size() > 0){
            GetDyBloodPressureDiaryDTO dto = new GetDyBloodPressureDiaryDTO();
            dto.setMemberId(memberId);
            dto.setStartDt(startDt.substring(0,10));
            dto.setEndDt(endDt.substring(0,10));
            DyBloodPressureDiaryPO bpSet = dyBloodPressureDiaryMapper.queryByMemberId(dto);

            //?????????
            IntSummaryStatistics dpbResult = list.stream().mapToInt(DyBloodPressureDataBO::getDbp).summaryStatistics();
            avgDbp =getInt(dpbResult.getAverage());
            maxDbp = dpbResult.getMax();
            minDbp = dpbResult.getMin();
            midDbp = getMidNum(list.stream().map(DyBloodPressureDataBO::getDbp).collect(Collectors.toList()));

            //?????????
            IntSummaryStatistics sbpResult = list.stream().mapToInt(DyBloodPressureDataBO::getSbp).summaryStatistics();
            avgSbp = getInt(sbpResult.getAverage());
            maxSbp = sbpResult.getMax();
            minSbp = sbpResult.getMin();
            midSbp = getMidNum(list.stream().map(DyBloodPressureDataBO::getSbp).collect(Collectors.toList()));

            //??????
            IntSummaryStatistics heartResult = list.stream().mapToInt(DyBloodPressureDataBO::getHeartRate).summaryStatistics();
            avgHrate = getInt(heartResult.getAverage());//???????????????
            maxHrate = heartResult.getMax();//???????????????
            minHrate = heartResult.getMin();//???????????????
            midHrate = getMidNum(list.stream().map(DyBloodPressureDataBO::getHeartRate).collect(Collectors.toList()));//?????????

            double standardDbpSum = 0; //????????????????????????
            double standardSbpSum = 0; //????????????????????????
            double standardHrateSum = 0; //????????????????????????
            int overDbpNum = 0; //?????????????????????????????????
            int overSbpNum = 0; //?????????????????????????????????

            for (DyBloodPressureDataBO e : list) {
                if (type == 1){
                    if(e.getDbpDayLevel().equals(DyBloodPressureConstant.BP_LEVEL_HIGH)){
                        overDbpNum += 1;
                    }
                    if(e.getSbpDayLevel().equals(DyBloodPressureConstant.BP_LEVEL_HIGH)){
                        overSbpNum += 1;
                    }
                }else if (type == 2){
                    if(e.getDbpSleepLevel().equals(DyBloodPressureConstant.BP_LEVEL_HIGH)){
                        overDbpNum += 1;
                    }
                    if(e.getSbpSleepLevel().equals(DyBloodPressureConstant.BP_LEVEL_HIGH)){
                        overSbpNum += 1;
                    }
                }else if (type == 4){
                    if(e.getDbp24hLevel().equals(DyBloodPressureConstant.BP_LEVEL_HIGH)){
                        overDbpNum += 1;
                    }
                    if(e.getSbp24hLevel().equals(DyBloodPressureConstant.BP_LEVEL_HIGH)){
                        overSbpNum += 1;
                    }
                }
                //?????????????????????
                standardDbpSum += (e.getDbp() - avgDbp) * (e.getDbp() - avgDbp);
                standardSbpSum += (e.getSbp() - avgSbp) * (e.getSbp() - avgSbp);
                standardHrateSum += (e.getHeartRate() - avgHrate) * (e.getHeartRate() - avgHrate);
            }

            /*?????????????????????*/
            standardDbp = list.size()  == 1?0:getInt(Math.sqrt(standardDbpSum/list.size() - 1));
            standardSbp = list.size()  == 1?0:getInt(Math.sqrt(standardSbpSum/list.size() -1));
            standardHrate = list.size()  == 1?0:getInt(Math.sqrt(standardHrateSum/list.size() -1));

            numberFormat.setMaximumFractionDigits(1);//????????????1???
            if (type !=3){
                /*??????????????????*/
                String overDbpLoad = numberFormat.format((float)overDbpNum/list.size()*100);
                String overSbpLoad = numberFormat.format((float)overSbpNum/list.size()*100);
                if (type==4){
                     overDbpLoad = numberFormat.format((float)overDbpNum/noonSleeplist.size()*100);
                     overSbpLoad = numberFormat.format((float)overSbpNum/noonSleeplist.size()*100);
                }
                dbpLoad = overDbpLoad.equals("0")?"0":overDbpLoad+"%";
                sbpLoad = overSbpLoad.equals("0")?"0":overSbpLoad+"%";
                /*????????????????????????*/
                dbpVarCoefficient = numberFormat.format((double)standardDbp/avgDbp*100)+"%";
                sbpVarCoefficient = numberFormat.format((double)standardSbp/avgSbp*100)+"%";
                heartVarCoefficient = numberFormat.format((double)standardHrate/avgHrate*100)+"%";
            }


            //???24h?????????
            IntSummaryStatistics sbpNightResult;
            List<DyBloodPressureDataBO> nightList;
            if (type == 4){
                /*--------???????????????????????????----------------*/
                String bedTime =DyBloodPressureConstant.DEFAULT_BED_TIME;//????????????
                String sleepTime =DyBloodPressureConstant.DEFAULT_SLEEP_TIME;//????????????
                if (null != bpSet){
                    bedTime = StringUtils.isBlank(bpSet.getBedDt())?DyBloodPressureConstant.DEFAULT_BED_TIME:bpSet.getBedDt();
                    sleepTime = StringUtils.isBlank(bpSet.getNightSleepStart())?DyBloodPressureConstant.DEFAULT_SLEEP_TIME:bpSet.getNightSleepStart();
                }
                String finalBedTime = bedTime;
                String finalSleepTime = sleepTime;
                List<DyBloodPressureDataBO> dayList = list.stream().filter(e->
                        DateHelper.comparisonTime(finalBedTime+"-"+ finalSleepTime,e.getRecordTime().substring(11,19)))
                        .collect(Collectors.toList());

                nightList = list.stream().filter(e->
                        DateHelper.comparisonTime(finalSleepTime+"-"+finalBedTime,e.getRecordTime().substring(11,19)))
                        .collect(Collectors.toList());

                IntSummaryStatistics dbpDayResult = dayList.stream().mapToInt(DyBloodPressureDataBO::getDbp).summaryStatistics();
                IntSummaryStatistics sbpDayResult = dayList.stream().mapToInt(DyBloodPressureDataBO::getSbp).summaryStatistics();
                IntSummaryStatistics dbpNightResult = nightList.stream().mapToInt(DyBloodPressureDataBO::getDbp).summaryStatistics();
                sbpNightResult = nightList.stream().mapToInt(DyBloodPressureDataBO::getSbp).summaryStatistics();
                int dayDbpAvg = getInt(dbpDayResult.getAverage());//?????????????????????
                int daySbpAvg = getInt(sbpDayResult.getAverage());//?????????????????????
                int nightDbpAvg = getInt(dbpNightResult.getAverage());//?????????????????????
                int nightSbpAvg = getInt(sbpNightResult.getAverage());//?????????????????????

                double dbpDLNight = (double) (dayDbpAvg - nightDbpAvg)/dayDbpAvg*100;
                double sbpDLNight = (double) (daySbpAvg - nightSbpAvg)/daySbpAvg*100;
                dbpDayLowNight = dayDbpAvg == 0?"":numberFormat.format(dbpDLNight) + "%"+dayLowNightHandler(dbpDLNight);
                sbpDayLowNight = daySbpAvg == 0?"":numberFormat.format(sbpDLNight) + "%"+dayLowNightHandler(sbpDLNight);

                /*-------------??????????????????------------*/
                String bedTimePlus2 = DyBloodPressureConstant.DEFAULT_AFTER_BED_TIME;//??????????????????

                if (null != bpSet){
                    bedTimePlus2 = StringUtils.isBlank(bpSet.getBedDt())?"08:00:00":DateHelper.plusDate(bpSet.getBedDt(), 4, 2, DateHelper.TIME_SECOND_FORMAT);
                }
                int nightMinSbp = sbpNightResult.getMin();//????????????????????????????????????
                int nightMinDbp = dbpNightResult.getMin();//????????????????????????????????????
                List<Integer> morningBaseSbpList = new ArrayList<>();//??????????????????????????????
                List<Integer> morningBaseDbpList = new ArrayList<>();
                //???????????????????????????????????????
                List<Integer> sbpCollect = nightList.stream().map(DyBloodPressureDataBO::getSbp).collect(Collectors.toList());
                List<Integer> dbpCollect = nightList.stream().map(DyBloodPressureDataBO::getDbp).collect(Collectors.toList());
                int i = sbpCollect.indexOf(nightMinSbp);
                int j = dbpCollect.indexOf(nightMinDbp);
                for (int x =i-1;x<=i+1;x++){//??????????????????1???????????????3???
                    if (x<0 || x>=sbpCollect.size()){
                        continue;
                    }
                    morningBaseSbpList.add(sbpCollect.get(x));
                }
                for (int x =j-1;x<=j+1;x++){//??????????????????1???????????????3???
                    if (x<0 || x>=dbpCollect.size()){
                        continue;
                    }
                    morningBaseDbpList.add(dbpCollect.get(x));
                }
                String finalBedTimePlus = bedTimePlus2;
                //?????????????????????????????????
                List<DyBloodPressureDataBO> bedList = list.stream().filter(e->
                        DateHelper.comparisonTime(finalBedTime+"-"+finalBedTimePlus,e.getRecordTime().substring(11,19)))
                        .collect(Collectors.toList());
                int bedAvgSbp = 0;
                int bedAvgDbp = 0;
                double nigntAvgSbp = 0;
                double nigntAvgDbp = 0;
                //???????????????????????????
                if (!bedList.isEmpty()){
                    bedAvgSbp  = (int) bedList.stream().mapToDouble(DyBloodPressureDataBO::getSbp).average().getAsDouble();
                    bedAvgDbp  = (int) bedList.stream().mapToDouble(DyBloodPressureDataBO::getDbp).average().getAsDouble();
                }
                //????????????????????????????????????
                if (!morningBaseSbpList.isEmpty()){
                    nigntAvgSbp = morningBaseSbpList.stream().mapToDouble(Integer::intValue).average().getAsDouble();
                    nigntAvgDbp = morningBaseDbpList.stream().mapToDouble(Integer::intValue).average().getAsDouble();
                }
                numberFormat.setMaximumFractionDigits(0);
                sbpMorningPeak = bedList.size() == 0?"":numberFormat.format(bedAvgSbp - nigntAvgSbp);
                dbpMorningPeak = bedList.size() == 0?"":numberFormat.format(bedAvgDbp - nigntAvgDbp);

                //-------------------??????????????????-----------
                Map<String,String> map = equationHandler(list);
                BigDecimal b = new BigDecimal(map.get("b"));
                arteriosclerosis =String.valueOf( new BigDecimal(1).subtract(b));
            }
        }
        numberFormat.setMaximumFractionDigits(0);
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> dbpMap = new HashMap<>();
        dbpMap.put("avgDbp",avgDbp == null?"":avgDbp);
        dbpMap.put("standardDbp",standardDbp == null?"":standardDbp);
        dbpMap.put("maxDbp",maxDbp == null?"":maxDbp);
        dbpMap.put("midDbp",midDbp == null?"":midDbp);
        dbpMap.put("minDbp",minDbp == null?"":minDbp);
        dbpMap.put("dbpLoad",dbpLoad);
        dbpMap.put("dbpDayLowNight",dbpDayLowNight);
        dbpMap.put("dbpMorningPeak",dbpMorningPeak);
        dbpMap.put("dbpVarCoefficient",dbpVarCoefficient);

        Map<String, Object> sbpMap = new HashMap<>();
        sbpMap.put("avgSbp",avgSbp == null?"":avgSbp);
        sbpMap.put("standardSbp",standardSbp== null?"":standardSbp);
        sbpMap.put("maxSbp",maxSbp == null?"":maxSbp);
        sbpMap.put("midSbp",midSbp == null?"":midSbp);
        sbpMap.put("minSbp",minSbp == null?"":minSbp);
        sbpMap.put("sbpLoad",sbpLoad);
        sbpMap.put("sbpDayLowNight",sbpDayLowNight);
        sbpMap.put("sbpMorningPeak",sbpMorningPeak);
        sbpMap.put("sbpVarCoefficient",sbpVarCoefficient);

        Map<String, Object> heartMap = new HashMap<>();
        heartMap.put("avgHrate",avgHrate == null?"":avgHrate);
        heartMap.put("maxHrate",maxHrate == null?"":maxHrate);
        heartMap.put("minHrate",minHrate == null?"":minHrate);
        heartMap.put("standardHrate",standardHrate == null?"":standardHrate);
        heartMap.put("midHrate",midHrate == null?"":midHrate);
        heartMap.put("heartVarCoefficient",heartVarCoefficient);

        resultMap.put("dbp",dbpMap);
        resultMap.put("sbp",sbpMap);
        resultMap.put("heart",heartMap);
        resultMap.put("validNum",noonSleeplist.size());//24h??????????????????
        resultMap.put("validRate",list.size() == 0?0:numberFormat.format((float)list.size()/allList.size()*100) + "%");//???????????????????????????/??????
        resultMap.put("arteriosclerosis",arteriosclerosis);
        return resultMap;
    }


    private String dayLowNightHandler(double value){
        if (value>10 && value<=20){
            return "(??????)";
        }else if (value>0 && value<=10){
            return "(?????????)";
        }else if ( value<=0){
            return "(?????????)";
        }else if ( value>20){
            return "(?????????)";
        }
        return "(??????)";
    }


    @Override
    public Map<String,Object> listDayBloodPressurePage(GetDynBloodPressureDataDTO getDto) {
        List<DyBloodPressureDataBO> allList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(getDto);
        Map<String, Object> result = new HashMap<>();
        List<ListDayDyBloodPressureVO> voList = this.dyBloodPressureMapper.listDyBloodPressureVO(getDto);
        //????????????
        for (ListDayDyBloodPressureVO vo : voList) {
            vo.setIsSleep(0);
            if (vo.getTimeType().equals(DyBloodPressureConstant.RECORD_TIME_TYPE_DAY_SLEEP) ||
                    vo.getTimeType().equals(DyBloodPressureConstant.RECORD_TIME_TYPE_NIGHT_SLEEP)){
                vo.setIsSleep(1);
            }
        }

        //??????????????????
        getDto.setIsValid(1);//??????????????????
        getDto.setTypeList(Arrays.asList(DyBloodPressureConstant.RECORD_TIME_TYPE_AFTER_BED,
                DyBloodPressureConstant.RECORD_TIME_TYPE_DAY_WAKE,DyBloodPressureConstant.RECORD_TIME_TYPE_NIGHT_SLEEP));//????????????????????????
        List<DyBloodPressureDataBO> validList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(getDto);
        Map map = equationHandler(validList);
        result.put("code",1);
        boolean flag = bloodPressureListValidJud(allList);
        if (!flag){
            result.put("code",-1);//?????????????????????????????????
        }
        result.put("line",map);
        result.put("list",voList);
        return result;
    }

    @Override
    @Transactional
    public void addUpdateBpDiary(AddDyBloodPressureDiaryDTO addDyBloodPressureDiaryDTO, Integer origin) {
        GetDyBloodPressureDiaryDTO dto = new GetDyBloodPressureDiaryDTO();
        BeanUtils.copyProperties(dto,addDyBloodPressureDiaryDTO);
        DyBloodPressureDiaryPO diaryPO = dyBloodPressureDiaryMapper.queryByMemberId(dto);
        boolean flag = false;
        if (null == diaryPO){
            //??????
            diaryPO = new DyBloodPressureDiaryPO();
            BeanUtils.copyProperties(diaryPO,addDyBloodPressureDiaryDTO);
            diaryPO.setSid(DaoHelper.getSeq());
            flag =  dyBloodPressureDiaryMapper.insert(diaryPO)>0;
        }else {
            //??????
            //????????????????????????
            org.springframework.beans.BeanUtils.copyProperties(addDyBloodPressureDiaryDTO,diaryPO,getNullPropertyNames(addDyBloodPressureDiaryDTO));
            flag = dyBloodPressureDiaryMapper.realUpdate(diaryPO) >0;
        }
        //??????????????????????????????????????????????????????
        if (flag){
            GetDynBloodPressureDataDTO dataDTO = new GetDynBloodPressureDataDTO();
            BeanUtils.copyProperties(dataDTO,addDyBloodPressureDiaryDTO);
            List<DyBloodPressureDataBO> list = dyBloodPressureMapper.listDyBloodPressureByMemberDay(dataDTO);
            List<DyBloodPressureDetailPO> updateList = new ArrayList<>();
            for (DyBloodPressureDataBO bo : list) {
                DyBloodPressurePO dyBloodPressurePO = new DyBloodPressurePO();
                BeanUtils.copyProperties(dyBloodPressurePO,bo);
                DyBloodPressureDetailPO detailPO = assertBloodPressureLevel(dyBloodPressurePO,diaryPO);
                updateList.add(detailPO);
            }
            dyBloodPressureDetailMapper.updateBatch(updateList);
        }

        if (origin == 1 && !StringUtils.isBlank(addDyBloodPressureDiaryDTO.getMemberId()) &&!StringUtils.isBlank(addDyBloodPressureDiaryDTO.getNightSleepStart())){
            //???????????????
            DyRememberSleepDTO dyRememberSleepDTO = new DyRememberSleepDTO();
            dyRememberSleepDTO.setMemberId(addDyBloodPressureDiaryDTO.getMemberId());
            dyRememberSleepDTO.setOperationType(2);
            dyRememberSleepDTO.setSleepDt(addDyBloodPressureDiaryDTO.getNightSleepStart().substring(0,5));
            dyRememberSleepDTO.setRecordDtStart(addDyBloodPressureDiaryDTO.getStartDt()+DateHelper.DEFUALT_TIME_START);
            dyRememberSleepDTO.setRecordDtEnd(addDyBloodPressureDiaryDTO.getStartDt()+DateHelper.DEFUALT_TIME_END);
            dyRememberSleepDTO.setRecordDt(addDyBloodPressureDiaryDTO.getStartDt()+" "+addDyBloodPressureDiaryDTO.getNightSleepStart());
            dyRememberSleepDTO.setOperationId(addDyBloodPressureDiaryDTO.getOperationId());
            dyRememberService.setSleepRemember(dyRememberSleepDTO,2);
        }

    }


    /**
     * ???????????????????????????
     *
     * @param source
     * @return
     */
    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            // ?????????????????????????????????
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    @Override
    public DyBloodPressureDiaryVO getDyBloodPressureDiaryPO(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO) {
        DyBloodPressureDiaryVO dyBloodPressureDiaryVO = dyBloodPressureDiaryMapper.queryVOByMemberId(getDyBloodPressureDiaryDTO);

        if (dyBloodPressureDiaryVO == null){
            dyBloodPressureDiaryVO = new DyBloodPressureDiaryVO();
            GetMemberDTO getMemberDTO = new GetMemberDTO();
            getMemberDTO.setMemberId(getDyBloodPressureDiaryDTO.getMemberId());
            MemberPO member = memberMapper.getMember(getMemberDTO);
            BeanUtils.copyProperties(dyBloodPressureDiaryVO,member);
            dyBloodPressureDiaryVO.setAge(member.getBirthday());
            BpMemberMachinePO machinePO = new BpMemberMachinePO();
            machinePO.setMemberId(getDyBloodPressureDiaryDTO.getMemberId());
            machinePO.setIsValid(1);
            List<BpMemberMachinePO> bpMemberMachinePOS = bpMemberMachineMapper.queryAll(machinePO);
            if (bpMemberMachinePOS != null && bpMemberMachinePOS.size()>0){
                dyBloodPressureDiaryVO.setMachineNo(bpMemberMachinePOS.get(0).getMachineNo());
            }
        }
        //??????
        diarySportHandler(getDyBloodPressureDiaryDTO,dyBloodPressureDiaryVO);

        //??????
        diaryDrugHandler(getDyBloodPressureDiaryDTO,dyBloodPressureDiaryVO);

        dyBloodPressureDiaryVO.setAge(String.valueOf(DateHelper.getAge(dyBloodPressureDiaryVO.getAge())));
        return dyBloodPressureDiaryVO;
    }


    private void diarySportHandler(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO,DyBloodPressureDiaryVO dyBloodPressureDiaryVO){
        SportRecordDTO sportDto = new SportRecordDTO();
        sportDto.setMemberId(getDyBloodPressureDiaryDTO.getMemberId());
        sportDto.setBegin(getDyBloodPressureDiaryDTO.getStartDt()+" "+DyBloodPressureConstant.DEFAULT_DAY_START_TIME);
        sportDto.setEnd(getDyBloodPressureDiaryDTO.getEndDt()+" "+DyBloodPressureConstant.DEFAULT_DAY_END_TIME);
        List<SportRecordPO> sportRecords = sportRecordMapper.listSportRecord(sportDto);//????????????
        dyBloodPressureDiaryVO.setSportList(sportRecords);
    }

    private void diaryDrugHandler(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO,DyBloodPressureDiaryVO dyBloodPressureDiaryVO){
        DrugsRecordDTO drugsRecordDTO = new DrugsRecordDTO();
        drugsRecordDTO.setMemberId(getDyBloodPressureDiaryDTO.getMemberId());
        drugsRecordDTO.setBegin(getDyBloodPressureDiaryDTO.getStartDt()+" "+DyBloodPressureConstant.DEFAULT_DAY_START_TIME);
        drugsRecordDTO.setEnd(getDyBloodPressureDiaryDTO.getEndDt()+" "+DyBloodPressureConstant.DEFAULT_DAY_END_TIME);
        List<DrugsRecordPO> dataList = drugsRecordMapper.listDrugsRecord(drugsRecordDTO);
        Map<String, List<DrugsRecordPO>> drugMap = new HashMap<>();
        List<List<DrugsRecordPO>> druglist = new ArrayList<>();
        if (dataList.size()>0){
            for (DrugsRecordPO drugsRecordPO : dataList) {
                List<DrugsRecordPO> drugList;
                String drugName = drugsRecordPO.getDrugName();
                if (drugMap.containsKey(drugName)){
                    drugList = drugMap.get(drugName);
                }else {
                    drugList = new ArrayList<>();
                }
                drugList.add(drugsRecordPO);
                drugMap.put(drugName,drugList);
            }
            for (Map.Entry<String, List<DrugsRecordPO>> entry : drugMap.entrySet())   {
                druglist.add(entry.getValue());
            }
        }
        dyBloodPressureDiaryVO.setDrugList(druglist);
    }

    @Override
    @Transactional
    public void addUpdateBpReport(AddDyBloodPressureReportDTO addDyBloodPressureReportDTO) {
        GetDyBloodPressureDiaryDTO dto = new GetDyBloodPressureDiaryDTO();
        BeanUtils.copyProperties(dto,addDyBloodPressureReportDTO);
        DyBloodPressureReportPO reportPO = dyBloodPressureReportMapper.queryByMemberId(dto);
        DyBloodPressureReportPO doReportPO = new DyBloodPressureReportPO();
        BeanUtils.copyProperties(doReportPO,addDyBloodPressureReportDTO);
        if (null == reportPO){
            //??????
            doReportPO.setSid(DaoHelper.getSeq());
            dyBloodPressureReportMapper.insert(doReportPO);
        }else {
            //??????
            doReportPO.setSid(reportPO.getSid());
            dyBloodPressureReportMapper.update(doReportPO);
        }

    }

    @Override
    public DyBloodPressureReportPO getDyBloodPressureReportPO(GetDyBloodPressureDiaryDTO getDiaryDTO) {
        DyBloodPressureReportPO reportPO = dyBloodPressureReportMapper.queryByMemberId(getDiaryDTO);
        //?????????????????????????????????
        if(reportPO == null){
            GetDynBloodPressureDataDTO dataDTO = new GetDynBloodPressureDataDTO();
            BeanUtils.copyProperties(dataDTO,getDiaryDTO);
            List<DyBloodPressureDataBO> allList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(dataDTO);
            boolean flag = bloodPressureListValidJud(allList);
            reportPO = new DyBloodPressureReportPO();
            if (!flag){
                reportPO.setDetails("????????????????????????????????????????????????????????????????????????????????????");
            }else {
                String summary = dyBpReportHandler(getDiaryDTO);
                reportPO.setDetails("?????????"+summary);
            }
        }
        return reportPO;
    }

    @Override
    public PageResult pageDayBloodPressureList(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<DyBloodPressureDetailPO> detailPOList = dyBloodPressureDetailMapper.queryDateList(getDyBloodPressureDiaryDTO);
        List<Map<String, String>> list = new ArrayList<>(detailPOList.size());
        if (detailPOList.size()>0){
            for (DyBloodPressureDetailPO po : detailPOList) {
                Map<String, String> map = new HashMap<>();
                map.put("startDt",po.getStartDt());
                map.put("endDt",po.getEndDt());
                list.add(map);
            }
        }
        PageResult<DyBloodPressureDetailPO> reportPOPageResult = new PageResult<>(detailPOList);
        PageResult emptyPageResult = reportPOPageResult.createEmptyPageResult();
        emptyPageResult.setRows(list);
        return emptyPageResult;
    }


    //??????????????????
    private String dyBpReportHandler(GetDyBloodPressureDiaryDTO getDiaryDTO){
        GetDynBloodPressureDataDTO getDataDTO = new GetDynBloodPressureDataDTO();
        BeanUtils.copyProperties(getDataDTO,getDiaryDTO);
        getDataDTO.setIsValid(1);//??????????????????
        //24h
        List<DyBloodPressureDataBO> allList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(getDataDTO);
        String allSummary = dyBpReportSummaryHandler(allList,1);

        //????????????
        getDataDTO.setTypeList(Arrays.asList(DyBloodPressureConstant.RECORD_TIME_TYPE_AFTER_BED,DyBloodPressureConstant.RECORD_TIME_TYPE_DAY_WAKE));
        List<DyBloodPressureDataBO> dayList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(getDataDTO);
        String daySummary = dyBpReportSummaryHandler(dayList,2);

        //????????????
        getDataDTO.setTypeList(Arrays.asList(DyBloodPressureConstant.RECORD_TIME_TYPE_NIGHT_SLEEP));
        List<DyBloodPressureDataBO> nightList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(getDataDTO);
        String sleepSummary = dyBpReportSummaryHandler(nightList,3);

        //?????????????????????
        getDataDTO.setTypeList(Arrays.asList(DyBloodPressureConstant.RECORD_TIME_TYPE_AFTER_BED));
        List<DyBloodPressureDataBO> bedList = dyBloodPressureMapper.listDyBloodPressureByMemberDay(getDataDTO);
        String afterBedSummary = dyBpReportSummaryHandler(bedList,4);

        return allSummary+daySummary+sleepSummary+afterBedSummary;
    }

    //?????????????????????
    private String dyBpReportSummaryHandler(List<DyBloodPressureDataBO> list , Integer type){
        double dbpAVG = list.stream().mapToDouble(DyBloodPressureDataBO::getDbp).summaryStatistics().getAverage();
        int dbpAVGI = (int)dbpAVG;
        double sbpAVG = list.stream().mapToDouble(DyBloodPressureDataBO::getSbp).summaryStatistics().getAverage();
        int sbpAVGI = (int)sbpAVG;
        int result =1;
        double dbpMax = 0;
        double sbpMax = 0;
        String pre = "";
        //24h
        if (type == 1){
            pre = "24 h ???";
            dbpMax = DyBloodPressureConstant.DBP_24H_MAX;
            sbpMax = DyBloodPressureConstant.SBP_24H_MAX;
            if (dbpAVG > DyBloodPressureConstant.DBP_24H_MAX){
                result = 2;
                if (sbpAVG > DyBloodPressureConstant.SBP_24H_MAX){
                    result =3;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_24H_MIN){
                    result =4;
                }

            }else if (dbpAVG < DyBloodPressureConstant.DBP_24H_MIN){
                result = 7;
                if (sbpAVG > DyBloodPressureConstant.SBP_24H_MAX){
                    result =8;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_24H_MIN){
                    result =9;
                }

            }else {
                if (sbpAVG > DyBloodPressureConstant.SBP_24H_MAX){
                    result = 5;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_24H_MIN){
                    result = 6;
                }
            }
        //????????????
        }else if(type == 2){
            pre = "?????????????????? ???";
            dbpMax = DyBloodPressureConstant.DBP_DAY_MAX;
            sbpMax = DyBloodPressureConstant.SBP_DAY_MAX;
            if (dbpAVG > DyBloodPressureConstant.DBP_DAY_MAX){
                result = 2;
                if (sbpAVG > DyBloodPressureConstant.SBP_DAY_MAX){
                    result =3;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_DAY_MIN){
                    result =4;
                }

            }else if (dbpAVG < DyBloodPressureConstant.DBP_DAY_MIN){
                result = 7;
                if (sbpAVG > DyBloodPressureConstant.SBP_DAY_MAX){
                    result =8;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_DAY_MIN){
                    result =9;
                }

            }else {
                if (sbpAVG > DyBloodPressureConstant.SBP_DAY_MAX){
                    result = 5;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_DAY_MIN){
                    result = 6;
                }
            }
        //????????????
        }else if (type == 3){
            pre = "?????????????????? ???";
            dbpMax = DyBloodPressureConstant.DBP_SLEEP_MAX;
            sbpMax = DyBloodPressureConstant.SBP_SLEEP_MAX;
            if (dbpAVG > DyBloodPressureConstant.DBP_SLEEP_MAX){
                result = 2;
                if (sbpAVG > DyBloodPressureConstant.SBP_SLEEP_MAX){
                    result =3;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_SLEEP_MIN){
                    result =4;
                }

            }else if (dbpAVG < DyBloodPressureConstant.DBP_SLEEP_MIN){
                result = 7;
                if (sbpAVG > DyBloodPressureConstant.SBP_SLEEP_MAX){
                    result =8;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_SLEEP_MIN){
                    result =9;
                }

            }else {
                if (sbpAVG > DyBloodPressureConstant.SBP_SLEEP_MAX){
                    result = 5;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_SLEEP_MIN){
                    result = 6;
                }
            }
        //?????????????????????
        }else if (type == 4){
            pre = "????????????????????? ???";
            sbpMax = DyBloodPressureConstant.DBP_AFTER_BED_MAX;
            sbpMax = DyBloodPressureConstant.SBP_AFTER_BED_MAX;
            if (dbpAVG > DyBloodPressureConstant.DBP_AFTER_BED_MAX){
                result = 2;
                if (sbpAVG > DyBloodPressureConstant.SBP_AFTER_BED_MAX){
                    result =3;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_AFTER_BED_MIN){
                    result =4;
                }

            }else if (dbpAVG < DyBloodPressureConstant.DBP_AFTER_BED_MIN){
                result = 7;
                if (sbpAVG > DyBloodPressureConstant.SBP_AFTER_BED_MAX){
                    result =8;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_AFTER_BED_MIN){
                    result =9;
                }
            }else {
                if (sbpAVG > DyBloodPressureConstant.SBP_AFTER_BED_MAX){
                    result = 5;
                }else if (sbpAVG < DyBloodPressureConstant.SBP_AFTER_BED_MIN){
                    result = 6;
                }
            }
        }
        String summary = "";
        switch (result){
            case 1:
                summary = "??????????????????????????????";
                break;
            case 2:
                summary = "??????????????????????????????";
                break;
            case 3:
                summary = "??????????????????????????????";
                break;
            case 4:
                summary = "?????????????????????????????????";
                break;
            case 5:
                summary = "?????????????????????????????????";
                break;
            case 6:
                summary = "??????????????????????????????";
                break;
            case 7:
                summary = "?????????????????????????????????";
                break;
            case 8:
                summary = "?????????????????????????????????";
                break;
            case 9:
                summary = "??????????????????????????????";
                break;
            default:
                log.error("??????????????????");
                break;
        }
        String resultS = pre + summary+"???"+sbpAVGI+"/"+dbpAVGI+"mmHg????????????????????? "+sbpMax+"/"+dbpMax +"mmHg??????";
        return resultS;
    }


    /**
     * ??????????????????r ???????????????ab
     * @param list
     * @return
     */
    private Map<String,String> equationHandler(List<DyBloodPressureDataBO> list) {
        Map<String, String> map = new HashMap<>();
        Double r = null;
        Double a = null;
        Double b = null;
        double lxy = 0;
        double lxx = 0;
        double lyy = 0;
        double xsum = 0;
        //y??????
        double ysum =0;
        //x?????????
        double xsquaressum = 0;
        //x???????????????
        double xsumsquares = 0;
        //y?????????
        double ysquaressum = 0;
        //y???????????????
        double ysumsquares = 0;
        //x?????????
        double xaverage = 0;
        //y?????????
        double yaverage = 0;
        //X??????????????????
        double xsqua = 0;
        double num = list.size();
        if (list.size() != 0){
            //??????
            double xyproduct = list.stream().mapToDouble(e -> { return e.getDbp() * e.getSbp(); }).summaryStatistics().getSum();

            DoubleSummaryStatistics XsummaryStatistics = list.stream().mapToDouble(DyBloodPressureDataBO::getSbp).summaryStatistics();
            DoubleSummaryStatistics YsummaryStatistics = list.stream().mapToDouble(DyBloodPressureDataBO::getDbp).summaryStatistics();
            //x??????
             xsum = XsummaryStatistics.getSum();
            //y??????
             ysum =YsummaryStatistics.getSum();
            //x?????????
             xsquaressum = list.stream().mapToDouble(e -> { return e.getSbp() * e.getSbp(); }).summaryStatistics().getSum();
            //x???????????????
             xsumsquares = xsum * xsum;
            //y?????????
             ysquaressum = list.stream().mapToDouble(e -> { return e.getDbp() * e.getDbp(); }).summaryStatistics().getSum();
            //y???????????????
             ysumsquares = ysum * ysum;
            //x?????????
             xaverage = XsummaryStatistics.getAverage();
            //y?????????
             yaverage = YsummaryStatistics.getAverage();
            //X??????????????????
             xsqua = xaverage * xaverage;

            lxy = xyproduct - (xsum * ysum)/num;
            lxx = xsquaressum - xsumsquares/num;
            lyy = ysquaressum - ysumsquares/num;
            r = lxy/Math.sqrt(lxx * lyy);//r=lxy/?????????lxx*lyy???
            b = (xyproduct - num * xaverage * yaverage)/(xsquaressum - num * xsqua);//b=?????????-?????????*X?????????*Y????????????/???X?????????-?????????*X?????????????????????
            a = yaverage - b *xaverage;//a=Y?????????-b*X?????????=??????????????????-b*??????????????????
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        map.put("r",(lxx * lyy) == 0?"--":numberFormat.format(r));
        map.put("a",(xsquaressum - num * xsqua) == 0?"--":numberFormat.format(a));
        map.put("b",(xsquaressum - num * xsqua) == 0?"--":numberFormat.format(b));
        return map;
    }

    //??????list?????????
    private int getMidNum(List<Integer> list){
        Collections.sort(list);
        // ???????????????
        int midNum;
        if (list.size() % 2 == 0) {
            midNum = (list.get(list.size() / 2 - 1) + list.get(list.size() / 2)) / 2;
        } else {
            midNum = list.get(list.size() / 2);
        }
        return midNum;
    }

    //??????recordTime?????????????????????????????????
    private GetDyBloodPressureDiaryDTO startEndDtHandler(String recordTime){
        Boolean after = DateHelper.dateAfter(recordTime.substring(11, 19), DateHelper.TIME_SECOND_FORMAT,
                DyBloodPressureConstant.DEFAULT_DAY_START_TIME, DateHelper.TIME_SECOND_FORMAT);
        String now = recordTime.substring(0, 10);
        String front = DateHelper.plusDate(now, -1);
        String back = DateHelper.plusDate(now, 1);

        GetDyBloodPressureDiaryDTO dto = new GetDyBloodPressureDiaryDTO();
        if (after){
            dto.setStartDt(now);
            dto.setEndDt(back);
        }else {
            dto.setStartDt(front);
            dto.setEndDt(now);
        }
        return dto;
    }


    //??????????????????????????????????????????
    @Override
    public boolean showBloodPressureByDate(String memberId,String startDt,String endDt){
        GetDynBloodPressureDataDTO getDto = new GetDynBloodPressureDataDTO();
        getDto.setMemberId(memberId);
        getDto.setStartDt(startDt);
        getDto.setEndDt(endDt);
        List<DyBloodPressureDataBO> list = dyBloodPressureMapper.listDyBloodPressureByMemberDay(getDto);
        boolean flag = bloodPressureListValidJud(list);
        return flag;
    }


    //?????????????????????????????????????????? true?????? false??????
    private boolean bloodPressureListValidJud(List<DyBloodPressureDataBO> list){
        boolean flag = false;
        if (list != null && list.size()>0){
            //????????????
            List<DyBloodPressureDataBO> validList = list.stream().filter(e -> e.getIsValid().equals(1)).collect(Collectors.toList());
            //??????????????????
            List<DyBloodPressureDataBO> nightCollect = validList.stream().filter(e -> e.getTimeType().equals(DyBloodPressureConstant.RECORD_TIME_TYPE_NIGHT_SLEEP)).collect(Collectors.toList());
            //??????????????????
            List<DyBloodPressureDataBO> dayCollect = validList.stream().filter(e -> e.getTimeType().equals(DyBloodPressureConstant.RECORD_TIME_TYPE_DAY_WAKE) || e.getTimeType().equals(DyBloodPressureConstant.RECORD_TIME_TYPE_AFTER_BED)).collect(Collectors.toList());

            //??????????????????
            float validRate = (float) validList.size()/list.size();
            if (validRate >= 0.7 && dayCollect.size() >= 20 && nightCollect.size() >= 7){
                flag = true;
            }
        }
        return flag;
    }

    //double???int
    public int getInt(double number){
        BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }
}
