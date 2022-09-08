package com.comvee.cdms.insulinpump.service.impl;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.clinicaldiagnosis.constant.YzConstant;
import com.comvee.cdms.clinicaldiagnosis.dto.ListMemberYzDTO;
import com.comvee.cdms.clinicaldiagnosis.mapper.YzMapper;
import com.comvee.cdms.clinicaldiagnosis.service.YzServiceI;
import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzListVO;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.insulinpump.constant.InsulinPumpConstant;
import com.comvee.cdms.insulinpump.model.bo.InsulinPumpDosageDetailBO;
import com.comvee.cdms.insulinpump.model.bo.InsulinPumpTimeSlot;
import com.comvee.cdms.insulinpump.model.vo.InsulinPumpDayUsageVO;
import com.comvee.cdms.insulinpump.model.vo.InsulinPumpRecordVO;
import com.comvee.cdms.insulinpump.model.vo.InsulinPumpUsageVO;
import com.comvee.cdms.insulinpump.service.InsulinPumpService;
import com.comvee.cdms.insulinpump.tool.InsulinPumpTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("insulinPumpService")
public class InsulinPumpServiceImpl implements InsulinPumpService {

    private final static Logger log = LoggerFactory.getLogger(InsulinPumpServiceImpl.class);

    @Autowired
    private YzServiceI yzService;

    @Autowired
    private YzMapper yzMapper;
    @Override
    public InsulinPumpUsageVO getInsulinPumpUsage(String memberId, String hospitalNo) {
        List<MemberYzListVO> yzList = getInsulinPumpYz(memberId ,hospitalNo);

        List<InsulinPumpDayUsageVO> dayUsageList = dayUsageListHandler(yzList);
        if(dayUsageList != null && !dayUsageList.isEmpty()){
            dayUsageList.sort((a ,b) -> b.getDate().compareTo(a.getDate()));
        }
        InsulinPumpUsageVO result = new InsulinPumpUsageVO();
        result.setDayUsageList(dayUsageList);
        return result;
    }

    @Override
    public List<InsulinPumpDayUsageVO> dayUsageListHandler(List<MemberYzListVO> yzList){
        if(yzList == null || yzList.isEmpty()){
            return null;
        }
        /*
        基础时段

        数据结构为:
        日期:{
            标准时段:[
               {
                具体时段: 基础量
               }
            ]
        }
         */
        Map<String ,Map<String ,List<Map<String ,Double>>>> baseMap = new HashMap<>();

        /*
         追加

         日期:{
           时段: 追加值
         }

         */
        Map<String ,Map<String ,Double>> addMap = new HashMap<>();

        /**
         * 临时追加
         * 日期:{
         *     时间:剂量
         * }
         */
        Map<String ,Map<String ,Double>> temporaryAddMap = new HashMap<>();

        /**
         * 预计基础量
         * 日期:{
         *     时间段:剂量
         * }
         */
        Map<String ,Map<String ,Double>> estimateBaseMap = new HashMap<>();

        /**
         * 预计追加量
         * 日期:{
         *     时间段:剂量
         * }
         */
        Map<String ,Map<String ,Double>> estimateAddMap = new HashMap<>();

        /**
         * 医嘱校对日期数据集合
         * 日期:{
         *     当天最晚的校对时间
         * }
         */
        Map<String ,String> checkYzMap = new HashMap<>();
        /**
         * 医嘱停止时间数据集合
         * 日期:{
         *     当天最早的停止时间
         * }
         */
        Map<String ,String> stopYzMap = new HashMap<>();

        /**
         * 每日最新执行完胰岛素名称
         */
        Map<String ,String> insulinNameMap = new HashMap<>();

        /**
         * 有使用胰岛素泵的日期集合
         */
        Set<String> dateSet = new HashSet<>();

        for(MemberYzListVO v : yzList){
            String checkDt = v.getCheckDt();
            if(StringUtils.isBlank(checkDt)){
                continue;
            }
            if(YzConstant.YZ_TYPE_TEMPORARY == v.getYzType()){
                addDataToTemporaryAddMap(v ,temporaryAddMap);
            }else{
                yzInsulinPumpDosageDetailHandler(checkDt ,v.getStopDt()
                        ,JSON.parseObject(v.getExtData() ,InsulinPumpDosageDetailBO.class)
                        ,baseMap ,addMap ,estimateBaseMap ,estimateAddMap ,insulinNameMap ,v.getYzItemName() ,dateSet);
                checkOrStopYzMapHandler(v ,checkYzMap ,stopYzMap);
            }
        }
        List<InsulinPumpDayUsageVO> result = new ArrayList<>();
        InsulinPumpDayUsageVO vo = null;

        Map<String ,Double> baseDosage = null;
        Map<String ,Double> detailDosage = null;
        Map<String ,Double> addDosage = null;
        Map<String ,Double> temporaryAddDosage = null;
        Map<String ,Double> estimateBaseDosage = null;
        Map<String ,Double> estimateAddDosage = null;
        for(String date : dateSet){
            vo = new InsulinPumpDayUsageVO();
            vo.setDate(date);

            baseDosage = new TreeMap<>();
            detailDosage = new TreeMap<>();
            Double basalTotal = 0D;
            Map<String ,List<Map<String ,Double>>> baseValue = baseMap.get(date);
            if(baseValue != null){
                //处理时段部分
                for(Map.Entry<String ,List<Map<String ,Double>>> dayEntry : baseValue.entrySet()){
                    String timeSlot = dayEntry.getKey();
                    double slotSum = 0D;
                    InsulinPumpTimeSlot standTimeSlot = new InsulinPumpTimeSlot(timeSlot);
                    for(Map<String ,Double> map : dayEntry.getValue()){
                        for(Map.Entry<String ,Double> detailEntry : map.entrySet()){
                            InsulinPumpTimeSlot detailTimeSlot = new InsulinPumpTimeSlot(detailEntry.getKey());
                            if(detailTimeSlot.getMinuteDiff() > 0){
                                detailDosage.put(detailEntry.getKey() ,detailEntry.getValue());
                                slotSum += InsulinPumpTool.divide(detailEntry.getValue() ,60 ,5) * detailTimeSlot.getMinuteDiff();
                            }
                        }
                    }
                    if(slotSum > 0){
                        Double Basal = InsulinPumpTool.divide(slotSum , standTimeSlot.getMinuteDiff() / 60);
                        baseDosage.put(timeSlot ,Basal);
                        basalTotal += Basal * (standTimeSlot.getMinuteDiff() / 60);
                    }
                }
            }
            //预计基础量处理
            estimateBaseDosage = estimateBaseMap.get(date);
            Double estimateBaseTotal = getMapValueTotal(estimateBaseDosage);
            vo.setEstimateBaseDosage(estimateBaseDosage);
            vo.setEstimateBaseTotal(estimateBaseTotal);
            //预计追加处理
            estimateAddDosage = estimateAddMap.get(date);
            Double estimateAddTotal = getMapValueTotal(estimateAddDosage);
            vo.setEstimateTotal(estimateBaseTotal + estimateAddTotal);

            vo.setEstimateAddDosage(estimateAddDosage);

            //临时追加处理
            temporaryAddDosage = temporaryAddMap.get(date);
            Double temporaryAddTotal = getMapValueTotal(temporaryAddDosage);
            vo.setTemporaryAddDosage(temporaryAddDosage);
            vo.setTemporaryAddTotal(temporaryAddTotal);

            int dosageChange = dosageChangeHandler(date ,temporaryAddMap ,checkYzMap ,stopYzMap);
            vo.setDosageChange(dosageChange);

            String insulinName = insulinNameMap.get(date);
            vo.setInsulinName(insulinName);

            //处理追加的部分
            Double addTotal = 0D;
            addDosage = addMap.get(date);
            if(addDosage != null){
                for(Map.Entry<String ,Double> addEntry : addDosage.entrySet()){
                    addTotal += addEntry.getValue();
                }
                vo.setAddDosage(addDosage);

            }
            vo.setAddTotal(addTotal + temporaryAddTotal);

            vo.setBasalTotal(new BigDecimal(basalTotal).setScale(5 ,BigDecimal.ROUND_HALF_UP).doubleValue());
            vo.setDetailDosage(detailDosage);
            vo.setBaseDosage(baseDosage);
            vo.setDayTotal(new BigDecimal(basalTotal + addTotal + temporaryAddTotal).setScale(5 ,BigDecimal.ROUND_HALF_UP).doubleValue());
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<InsulinPumpRecordVO> listInsulinPumpRecord(String memberId, String hospitalId) {
        ListMemberYzDTO dto = new ListMemberYzDTO();
        dto.setMemberId(memberId);
        dto.setHospitalId(hospitalId);
        dto.setYzType(YzConstant.YZ_TYPE_LONG_TERM);
        dto.setYzItemCodeList(Collections.singletonList(YzConstant.YZ_ITEM_CODE_INSULIN_PUMP));
        dto.setYzStatusList(new ArrayList<>(Arrays.asList(
                String.valueOf(YzConstant.YZ_STATUS_EXECUTED)
                ,String.valueOf(YzConstant.YZ_STATUS_STOP)
        )));
        List<MemberYzListVO> list = this.yzService.listMemberYz(dto);
        if(list == null || list.isEmpty()){
            return null;
        }
        MultiValueMap<String ,MemberYzListVO> map = new LinkedMultiValueMap<>();
        for(MemberYzListVO yz : list){
            map.add(yz.getVisitNo() ,yz);
        }
        List<InsulinPumpRecordVO> result = new ArrayList<>();
        InsulinPumpRecordVO recordVO;
        for(MultiValueMap.Entry<String ,List<MemberYzListVO>> entry : map.entrySet()){
            recordVO = new InsulinPumpRecordVO();
            recordVO.setHospitalNo(entry.getKey());
            Optional<MemberYzListVO> optional = entry.getValue().stream().filter(x -> x.getCardNo() != null).findAny();
            if(optional.isPresent()){
                String cardNo = optional.get().getCardNo();
                recordVO.setCardNo(cardNo);
            }
            String startDt = entry.getValue().stream().min(Comparator.comparing(MemberYzListVO::getCheckDt)).get().getCheckDt();
            String endDt = entry.getValue().stream().max((a ,b) -> {
                if(a.getStopDt() == null && b.getStopDt() == null){
                    return 0;
                }else if(b.getStopDt() == null){
                    return 1;
                }else if(a.getStopDt() == null){
                    return -1;
                }
                return a.getStopDt().compareTo(b.getStartDt());
            }).get().getStopDt();
            recordVO.setStartDt(startDt);
            recordVO.setEndDt(endDt);
            result.add(recordVO);
        }
        result.sort((a ,b) -> b.getStartDt().compareTo(a.getStartDt()));
        return result;
    }

    /**
     * 获取已执行或已完成的胰岛素泵医嘱
     * @param memberId
     * @param hospitalNo
     * @return
     */
    private List<MemberYzListVO> getInsulinPumpYz(String memberId, String hospitalNo){
        ListMemberYzDTO dto = new ListMemberYzDTO();
        dto.setMemberId(memberId);
        dto.setVisitNo(hospitalNo);
        dto.setYzStatusList(new ArrayList<>(Arrays.asList(
                String.valueOf(YzConstant.YZ_STATUS_EXECUTED)
                ,String.valueOf(YzConstant.YZ_STATUS_STOP)
        )));
        dto.setYzItemCodeList(Collections.singletonList(YzConstant.YZ_ITEM_CODE_INSULIN_PUMP));
        List<MemberYzListVO> listVOS = this.yzService.listMemberYz(dto);
        //排序
        if(listVOS != null){
            listVOS.sort((a ,b) -> {
                if(a.getLastExecuteTime() == null && a.getLastExecuteTime() == null){
                    return 0;
                }
                if(a.getLastExecuteTime() == null){
                    return -1;
                }
                if(b.getLastExecuteTime() == null){
                    return 1;
                }
                return b.getLastExecuteTime().compareTo(a.getLastExecuteTime());
            });
        }
        return listVOS;
    }

    /**
     * 追加的胰岛素判断时间点
     */
    private final static String ADD_TIME_POINT_1 = "10:00";
    private final static String ADD_TIME_POINT_2 = "13:00";
    private final static String ADD_TIME_POINT_3 = "19:00";

    /**
     * 医嘱的胰岛素泵用量详情处理
     * @param checkDt
     * @param stopDt
     * @param dosageDetail
     * @param map
     */
    private void yzInsulinPumpDosageDetailHandler(String checkDt ,String stopDt
            ,InsulinPumpDosageDetailBO dosageDetail ,Map<String ,Map<String ,List<Map<String ,Double>>>> baseMap
            ,Map<String ,Map<String ,Double>> addMap ,Map<String ,Map<String ,Double>> estimateBaseMap
            ,Map<String ,Map<String ,Double>> estimateAddMap ,Map<String ,String> insulinNameMap ,String insulinName
            ,Set<String> dateSet){
        Map<String ,Double> base = dosageDetail.getBase();
        Map<String ,Double> add = dosageDetail.getAdd();
        //移除没有值的设置
        dosageMapNullValueHandler(base);
        dosageMapNullValueHandler(add);
        /**
         * 是否已经执行完的胰岛素医嘱  true 已经执行完 false 未执行完
         * 如果未执行完，则需要进行预计剂量的逻辑
         */
        boolean isStop = false;
        if(!StringUtils.isBlank(stopDt)){
            isStop = true;
        }

        String checkDate = checkDt.substring(0 ,10);
        String checkTime = checkDt.substring(11 ,16);

        String stopDate = null;
        String stopTime = null;

        String estimateStartTime = null;

        if(isStop){
            stopDate = stopDt.substring(0 ,10);
            stopTime = stopDt.substring(11 ,16);
        }else{
            String nowDate = LocalDateTime.now().withMinute(0).withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            stopDt = nowDate;
            stopDate = nowDate.substring(0 ,10);
            stopTime = nowDate.substring(11 ,16);

            estimateStartTime = stopTime;
        }

        //是否是同一天
        boolean sameDay = checkDate.equals(stopDate);

        if(sameDay){
            sameDateDataHandler(base ,add ,checkDate ,checkTime ,stopTime ,baseMap
            ,addMap ,estimateBaseMap ,isStop ,estimateStartTime ,estimateAddMap ,insulinNameMap
                    ,insulinName ,dateSet);
        }else{
            unSameDateDataHandler(base ,add ,checkDate ,checkTime ,stopTime ,baseMap
                    ,addMap ,estimateBaseMap ,isStop ,estimateStartTime ,stopDate ,checkDt ,stopDt
                    ,estimateAddMap ,insulinNameMap ,insulinName ,dateSet);
        }
    }

    /**
     * 添加数据到基础时段的map中
     * @param date
     * @param timeRange
     * @param detailTime
     * @param basal
     * @param map
     */
    private void addDataToBaseMap(String date ,String timeRange ,String detailTime ,Double basal
            ,Map<String ,Map<String ,List<Map<String ,Double>>>> map){
        Map<String ,List<Map<String ,Double>>> dateMap = map.get(date);
        if(dateMap == null){
            dateMap = new HashMap<>();
            map.put(date ,dateMap);
        }
        List<Map<String ,Double>> detailList = dateMap.get(timeRange);
        if(detailList == null){
            detailList = new ArrayList<>();
            dateMap.put(timeRange ,detailList);
        }
        detailList.add(Collections.singletonMap(detailTime ,basal));
    }


    /**
     * 判断时间是否在范围内
     * @param time
     * @param range
     * @return
     */
    private boolean checkTimeStringBetweenRange(String time ,String range){
        String[] strings = range.split(InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT);
        if(strings.length != 2){
            throw new BusinessException("非法的时间范围值:"  + range);
        }
        return (strings[0].compareTo(time) <=0) && (strings[1].compareTo(time) >= 0);
    }

    /**
     * 获取加过后的日期
     * @param startDt
     * @param add
     * @return
     */
    private String getAddDate(String startDt ,int add){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateHelper.getDate(startDt ,"yyyy-MM-dd HH:mm:ss"));
        calendar.add(Calendar.DATE ,add);
        return DateHelper.getDate(calendar.getTime() ,"yyyy-MM-dd");
    }

    /**
     * 把值加到追加的集合中
     * @param date
     * @param addCode
     * @param addValue
     * @param add
     */
    private void addDataToAddMap(String date ,String addCode ,Double addValue ,Map<String ,Map<String ,Double>> add){
        if(addValue == null){
            return;
        }
        Map<String ,Double> valueMap = add.get(date);
        if(valueMap == null){
            valueMap = new HashMap<>();
            add.put(date ,valueMap);
        }
        valueMap.putIfAbsent(addCode ,addValue);
    }

    /**
     * 添加数据到临时追加数据集合
     * @param v
     * @param temporaryAddMap
     */
    private void addDataToTemporaryAddMap(MemberYzListVO v ,Map<String ,Map<String ,Double>> temporaryAddMap){
        String date = v.getStartDt().substring(0 ,10);
        String time = v.getStartDt().substring(11 ,16);
        Map<String ,Double> detailMap = temporaryAddMap.get(date);
        if(detailMap == null){
            detailMap = new HashMap<>();
            temporaryAddMap.put(date ,detailMap);
        }

        detailMap.put(time ,Double.parseDouble(v.getDrugDose()));
    }

    /**
     * 添加数据到预计的基础量数据集合
     * @param date
     * @param timeRange
     * @param value
     * @param estimateBaseMap
     */
    private void addDataToEstimateBaseMap(String date ,String timeRange ,Double value ,Map<String ,Map<String ,Double>> estimateBaseMap){
        Map<String ,Double> dateMap = estimateBaseMap.get(date);
        if(dateMap == null){
            dateMap = new HashMap<>();
            estimateBaseMap.put(date ,dateMap);
        }
        dateMap.put(timeRange ,value);
    }

    /**
     * 同一天的数据处理
     * @param base
     * @param add
     * @param checkData
     * @param checkTime
     * @param stopTime
     * @param baseMap
     * @param addMap
     * @param estimateBaseMap
     * @param isStop
     * @param estimateStartTime
     */
    private void sameDateDataHandler(Map<String ,Double> base ,Map<String ,Double> add ,String checkDate ,String checkTime
        , String stopTime ,Map<String ,Map<String ,List<Map<String ,Double>>>> baseMap
            ,Map<String ,Map<String ,Double>> addMap ,Map<String ,Map<String ,Double>> estimateBaseMap
            ,boolean isStop ,String estimateStartTime ,Map<String ,Map<String ,Double>> estimateAddMap
            ,Map<String ,String> insulinNameMap ,String insulinName ,Set<String> dateSet){

        insulinNameMap.putIfAbsent(checkDate ,insulinName);

        dateSet.add(checkDate);

        for(Map.Entry<String ,Double> entry : base.entrySet()){
            InsulinPumpTimeSlot standTimeSlot = new InsulinPumpTimeSlot(entry.getKey());
            //执行时间大于等于开始时间，且停止时间小于结束时间
            if(standTimeSlot.getStartTime().compareTo(checkTime) <= 0
                    && standTimeSlot.getEndTime().compareTo(stopTime) > 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,checkTime + InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT + stopTime
                        ,entry.getValue() ,baseMap);
            }
            //执行时间小于等于开始时间，且小于结束时间
            else if(standTimeSlot.getStartTime().compareTo(checkTime) <= 0
                    && checkTime.compareTo(standTimeSlot.getEndTime()) < 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,checkTime + InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT + standTimeSlot.getEndTime()
                        ,entry.getValue() ,baseMap);
            }
            //停止时间小于结束时间，且停止时间大于开始时间
            else if(standTimeSlot.getEndTime().compareTo(stopTime) > 0
                    && standTimeSlot.getStartTime().compareTo(stopTime) < 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,standTimeSlot.getStartTime() + InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT + stopTime
                        ,entry.getValue() ,baseMap);
            }
            //执行时间小于开始时间，并且停止时间大于结束时间
            else if(checkTime.compareTo(standTimeSlot.getStartTime()) < 0
                    && stopTime.compareTo(standTimeSlot.getEndTime()) > 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,entry.getKey()
                        ,entry.getValue() ,baseMap);
            }
            //停止时间大于结束时间，并且执行时间小于开始时间
            else if(stopTime.compareTo(standTimeSlot.getEndTime()) > 0
                    && checkTime.compareTo(standTimeSlot.getStartTime()) < 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,entry.getKey()
                        ,entry.getValue() ,baseMap);
            }
            //执行时间小于开始时间，并且停止时间大于等于结束时间
            else if(standTimeSlot.getStartTime().compareTo(checkTime) > 0
                    && standTimeSlot.getEndTime().compareTo(stopTime) <= 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,entry.getKey()
                        ,entry.getValue() ,baseMap);
            }
            //处理预计基础
            if(!isStop){
                if(estimateStartTime.compareTo(standTimeSlot.getStartTime()) <= 0){
                    addDataToEstimateBaseMap(checkDate ,entry.getKey() ,entry.getValue() ,estimateBaseMap);
                }
            }
        }
        //处理追加
        if(checkTime.compareTo(ADD_TIME_POINT_1) <= 0 && stopTime.compareTo(ADD_TIME_POINT_3) >= 0){
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,addMap);
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,addMap);
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,addMap);
        }else if(checkTime.compareTo(ADD_TIME_POINT_1) <= 0 && stopTime.compareTo(ADD_TIME_POINT_2) >= 0){
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,addMap);
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,addMap);
        }else if(checkTime.compareTo(ADD_TIME_POINT_1) <= 0 && stopTime.compareTo(ADD_TIME_POINT_1) >= 0){
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,addMap);
        }else if(stopTime.compareTo(ADD_TIME_POINT_3) >= 0 && checkTime.compareTo(ADD_TIME_POINT_2) <= 0){
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,addMap);
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,addMap);
        }else if(stopTime.compareTo(ADD_TIME_POINT_3) >= 0 && checkTime.compareTo(ADD_TIME_POINT_3) <= 0){
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,addMap);
        }else if(stopTime.compareTo(ADD_TIME_POINT_2) >= 0 && checkTime.compareTo(ADD_TIME_POINT_2) <= 0){
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,addMap);
        }
        //处理预计追加
        if(!isStop){
            if(estimateStartTime.compareTo(ADD_TIME_POINT_1) < 0){
                addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,estimateAddMap);
                addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,estimateAddMap);
                addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,estimateAddMap);
            }else if(estimateStartTime.compareTo(ADD_TIME_POINT_2) < 0){
                addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,estimateAddMap);
                addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,estimateAddMap);
            }else if(estimateStartTime.compareTo(ADD_TIME_POINT_3) < 0){
                addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,estimateAddMap);
            }
        }
    }

    /**
     * 非同一天数据处理
     * @param base
     * @param add
     * @param checkData
     * @param checkTime
     * @param stopTime
     * @param baseMap
     * @param addMap
     * @param estimateBaseMap
     * @param isStop
     * @param estimateStartTime
     * @param stopDate
     * @param checkDt
     * @param stopDt
     */
    private void unSameDateDataHandler(Map<String ,Double> base ,Map<String ,Double> add ,String checkDate ,String checkTime
            , String stopTime ,Map<String ,Map<String ,List<Map<String ,Double>>>> baseMap
            ,Map<String ,Map<String ,Double>> addMap ,Map<String ,Map<String ,Double>> estimateBaseMap
            ,boolean isStop ,String estimateStartTime ,String stopDate ,String checkDt ,String stopDt
            ,Map<String ,Map<String ,Double>> estimateAddMap ,Map<String ,String> insulinNameMap ,String insulinName
            ,Set<String> dateSet){
        //胰岛素名处理
        insulinNameMap.putIfAbsent(checkDate ,insulinName);
        insulinNameMap.putIfAbsent(stopDate ,insulinName);

        //开始处理校对那天的数据
        for(Map.Entry<String ,Double> entry : base.entrySet()){
            InsulinPumpTimeSlot standTimeSlot = new InsulinPumpTimeSlot(entry.getKey());
            if(checkTimeStringBetweenRange(checkTime ,entry.getKey())){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,checkTime + InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT + standTimeSlot.getEndTime()
                        ,entry.getValue() ,baseMap);
            }
            if(checkTime.compareTo(standTimeSlot.getStartTime()) <= 0){
                addDataToBaseMap(checkDate ,entry.getKey() ,entry.getKey() ,entry.getValue() ,baseMap);
            }
        }

        dateSet.add(checkDate);

        //处理追加
        if(ADD_TIME_POINT_1.compareTo(checkTime) >= 0){
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,addMap);
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,addMap);
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,addMap);
        }else if(ADD_TIME_POINT_2.compareTo(checkTime) >= 0){
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,addMap);
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,addMap);
        }else if(ADD_TIME_POINT_3.compareTo(checkTime) >= 0){
            addDataToAddMap(checkDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,addMap);
        }
        //开始处理停止那天的数据
        for(Map.Entry<String ,Double> entry : base.entrySet()){
            InsulinPumpTimeSlot standTimeSlot = new InsulinPumpTimeSlot(entry.getKey());
            if(checkTimeStringBetweenRange(stopTime ,entry.getKey())){
                addDataToBaseMap(stopDate ,entry.getKey()
                        ,standTimeSlot.getStartTime() + InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT +  stopTime
                        ,entry.getValue() ,baseMap);
            }
            if(stopTime.compareTo(standTimeSlot.getEndTime()) > 0){
                addDataToBaseMap(stopDate ,entry.getKey() ,entry.getKey() ,entry.getValue() ,baseMap);
            }
            if(!isStop){
                if(estimateStartTime.compareTo(standTimeSlot.getStartTime()) <= 0){
                    addDataToEstimateBaseMap(stopDate ,entry.getKey() ,entry.getValue() ,estimateBaseMap);
                }
            }
        }
        //处理追加
        if(ADD_TIME_POINT_3.compareTo(stopTime) <= 0){
            addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,addMap);
            addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,addMap);
            addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,addMap);
        }else if(ADD_TIME_POINT_2.compareTo(stopTime) <= 0){
            addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,addMap);
            addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,addMap);
        }else if(ADD_TIME_POINT_1.compareTo(stopTime) <= 0){
            addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,addMap);
        }
        //处理预计追加
        if(!isStop){
            if(estimateStartTime.compareTo(ADD_TIME_POINT_1) <= 0){
                addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,estimateAddMap);
                addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,estimateAddMap);
                addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,estimateAddMap);
            }else if(estimateStartTime.compareTo(ADD_TIME_POINT_2) <= 0){
                addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,estimateAddMap);
                addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,estimateAddMap);
            }else if(estimateStartTime.compareTo(ADD_TIME_POINT_3) <= 0){
                addDataToAddMap(stopDate ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,estimateAddMap);
            }
        }

        dateSet.add(stopDate);

        //开始处理校对那天到停止那天之间的数据
        int diff = DateHelper.dateCompareGetDay(stopDt.substring(0 ,10) ,checkDt.substring(0 ,10));
        for(int i = 1; i <= diff - 1 ; i ++){
            String date = getAddDate(checkDt ,i);
            for(Map.Entry<String ,Double> entry : base.entrySet()){
                addDataToBaseMap(date ,entry.getKey() ,entry.getKey() ,entry.getValue() ,baseMap);
            }
            //处理追加
            addDataToAddMap(date ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,addMap);
            addDataToAddMap(date ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,addMap);
            addDataToAddMap(date ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,addMap);
            insulinNameMap.putIfAbsent(date ,insulinName);
            dateSet.add(date);
        }
    }

    /**
     * 统计map中value的值之和
     * @param map
     * @return
     */
    public Double getMapValueTotal(Map<String ,Double> map){
        Double result = 0D;
        if(map == null || map.isEmpty()){
            return result;
        }
        for(Map.Entry<String ,Double> entry1 : map.entrySet()){
            result += entry1.getValue();
        }
        return new BigDecimal(result).setScale(5 ,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 执行或停止医嘱日期数据集合处理
     * @param yz
     * @param checkYzMap
     * @param stopYzMap
     */
    private void checkOrStopYzMapHandler(MemberYzListVO yz ,Map<String ,String> checkYzMap ,Map<String ,String> stopYzMap){
        //校对时间数据
        String checkDate = yz.getCheckDt().substring(0 ,10);
        String mapCheckDt = checkYzMap.get(checkDate);
        if(StringUtils.isBlank(mapCheckDt)){
            checkYzMap.put(checkDate ,yz.getCheckDt());
        }else if(mapCheckDt.compareTo(yz.getCheckDt()) > 0){
            checkYzMap.put(checkDate ,yz.getCheckDt());
        }

        //停止时间数据
        String stopDt = yz.getStopDt();
        if(!StringUtils.isBlank(stopDt)){
            String stopDate = stopDt.substring(0 ,10);
            String mapStopDt = stopYzMap.get(stopDate);
            if(StringUtils.isBlank(mapStopDt)){
                stopYzMap.put(stopDate ,stopDt);
            }else if(mapStopDt.compareTo(stopDt) < 0){
                stopYzMap.put(stopDate ,stopDt);
            }
        }
    }

    /**
     * 剂量变化情况处理
     * @param date
     * @param temporaryAddMap
     * @param checkYzMap
     * @param stopYzMap
     * @return
     */
    private Integer dosageChangeHandler(String date ,Map<String ,Map<String ,Double>> temporaryAddMap
        , Map<String ,String> checkYzMap ,Map<String ,String> stopYzMap){
        Integer result = DOSAGE_CHANGE_NO;
        if(temporaryAddMap.containsKey(date)){
            result = DOSAGE_CHANGE_YES;
        }else {
            String stopDt = stopYzMap.get(date);
            if(!StringUtils.isBlank(stopDt)){
                String checkDt = checkYzMap.get(date);
                if(!StringUtils.isBlank(checkDt)){
                    if(checkDt.compareTo(stopDt) > 0){
                        result = DOSAGE_CHANGE_YES;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 剂量map空值处理
     * @param map
     */
    private void dosageMapNullValueHandler(Map<String ,Double> map){
        if(map == null || map.isEmpty()){
            return;
        }
        Iterator<Map.Entry<String ,Double>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String ,Double> entry = iterator.next();
            if(entry.getValue() == null){
                iterator.remove();
            }
        }
    }

    /**
     * 剂量变化 1 有变化 0 没有变化
     */
    private final static int DOSAGE_CHANGE_YES = 1;
    private final static int DOSAGE_CHANGE_NO = 0;
}
