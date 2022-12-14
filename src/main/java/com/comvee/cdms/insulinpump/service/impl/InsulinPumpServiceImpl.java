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
        ????????????

        ???????????????:
        ??????:{
            ????????????:[
               {
                ????????????: ?????????
               }
            ]
        }
         */
        Map<String ,Map<String ,List<Map<String ,Double>>>> baseMap = new HashMap<>();

        /*
         ??????

         ??????:{
           ??????: ?????????
         }

         */
        Map<String ,Map<String ,Double>> addMap = new HashMap<>();

        /**
         * ????????????
         * ??????:{
         *     ??????:??????
         * }
         */
        Map<String ,Map<String ,Double>> temporaryAddMap = new HashMap<>();

        /**
         * ???????????????
         * ??????:{
         *     ?????????:??????
         * }
         */
        Map<String ,Map<String ,Double>> estimateBaseMap = new HashMap<>();

        /**
         * ???????????????
         * ??????:{
         *     ?????????:??????
         * }
         */
        Map<String ,Map<String ,Double>> estimateAddMap = new HashMap<>();

        /**
         * ??????????????????????????????
         * ??????:{
         *     ???????????????????????????
         * }
         */
        Map<String ,String> checkYzMap = new HashMap<>();
        /**
         * ??????????????????????????????
         * ??????:{
         *     ???????????????????????????
         * }
         */
        Map<String ,String> stopYzMap = new HashMap<>();

        /**
         * ????????????????????????????????????
         */
        Map<String ,String> insulinNameMap = new HashMap<>();

        /**
         * ????????????????????????????????????
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
                //??????????????????
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
            //?????????????????????
            estimateBaseDosage = estimateBaseMap.get(date);
            Double estimateBaseTotal = getMapValueTotal(estimateBaseDosage);
            vo.setEstimateBaseDosage(estimateBaseDosage);
            vo.setEstimateBaseTotal(estimateBaseTotal);
            //??????????????????
            estimateAddDosage = estimateAddMap.get(date);
            Double estimateAddTotal = getMapValueTotal(estimateAddDosage);
            vo.setEstimateTotal(estimateBaseTotal + estimateAddTotal);

            vo.setEstimateAddDosage(estimateAddDosage);

            //??????????????????
            temporaryAddDosage = temporaryAddMap.get(date);
            Double temporaryAddTotal = getMapValueTotal(temporaryAddDosage);
            vo.setTemporaryAddDosage(temporaryAddDosage);
            vo.setTemporaryAddTotal(temporaryAddTotal);

            int dosageChange = dosageChangeHandler(date ,temporaryAddMap ,checkYzMap ,stopYzMap);
            vo.setDosageChange(dosageChange);

            String insulinName = insulinNameMap.get(date);
            vo.setInsulinName(insulinName);

            //?????????????????????
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
     * ????????????????????????????????????????????????
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
        //??????
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
     * ?????????????????????????????????
     */
    private final static String ADD_TIME_POINT_1 = "10:00";
    private final static String ADD_TIME_POINT_2 = "13:00";
    private final static String ADD_TIME_POINT_3 = "19:00";

    /**
     * ???????????????????????????????????????
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
        //????????????????????????
        dosageMapNullValueHandler(base);
        dosageMapNullValueHandler(add);
        /**
         * ???????????????????????????????????????  true ??????????????? false ????????????
         * ?????????????????????????????????????????????????????????
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

        //??????????????????
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
     * ??????????????????????????????map???
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
     * ??????????????????????????????
     * @param time
     * @param range
     * @return
     */
    private boolean checkTimeStringBetweenRange(String time ,String range){
        String[] strings = range.split(InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT);
        if(strings.length != 2){
            throw new BusinessException("????????????????????????:"  + range);
        }
        return (strings[0].compareTo(time) <=0) && (strings[1].compareTo(time) >= 0);
    }

    /**
     * ????????????????????????
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
     * ??????????????????????????????
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
     * ???????????????????????????????????????
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
     * ?????????????????????????????????????????????
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
     * ????????????????????????
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
            //????????????????????????????????????????????????????????????????????????
            if(standTimeSlot.getStartTime().compareTo(checkTime) <= 0
                    && standTimeSlot.getEndTime().compareTo(stopTime) > 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,checkTime + InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT + stopTime
                        ,entry.getValue() ,baseMap);
            }
            //????????????????????????????????????????????????????????????
            else if(standTimeSlot.getStartTime().compareTo(checkTime) <= 0
                    && checkTime.compareTo(standTimeSlot.getEndTime()) < 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,checkTime + InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT + standTimeSlot.getEndTime()
                        ,entry.getValue() ,baseMap);
            }
            //??????????????????????????????????????????????????????????????????
            else if(standTimeSlot.getEndTime().compareTo(stopTime) > 0
                    && standTimeSlot.getStartTime().compareTo(stopTime) < 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,standTimeSlot.getStartTime() + InsulinPumpConstant.INSULIN_PUMP_TIME_RANGE_SPILT + stopTime
                        ,entry.getValue() ,baseMap);
            }
            //?????????????????????????????????????????????????????????????????????
            else if(checkTime.compareTo(standTimeSlot.getStartTime()) < 0
                    && stopTime.compareTo(standTimeSlot.getEndTime()) > 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,entry.getKey()
                        ,entry.getValue() ,baseMap);
            }
            //?????????????????????????????????????????????????????????????????????
            else if(stopTime.compareTo(standTimeSlot.getEndTime()) > 0
                    && checkTime.compareTo(standTimeSlot.getStartTime()) < 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,entry.getKey()
                        ,entry.getValue() ,baseMap);
            }
            //???????????????????????????????????????????????????????????????????????????
            else if(standTimeSlot.getStartTime().compareTo(checkTime) > 0
                    && standTimeSlot.getEndTime().compareTo(stopTime) <= 0){
                addDataToBaseMap(checkDate ,entry.getKey()
                        ,entry.getKey()
                        ,entry.getValue() ,baseMap);
            }
            //??????????????????
            if(!isStop){
                if(estimateStartTime.compareTo(standTimeSlot.getStartTime()) <= 0){
                    addDataToEstimateBaseMap(checkDate ,entry.getKey() ,entry.getValue() ,estimateBaseMap);
                }
            }
        }
        //????????????
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
        //??????????????????
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
     * ????????????????????????
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
        //??????????????????
        insulinNameMap.putIfAbsent(checkDate ,insulinName);
        insulinNameMap.putIfAbsent(stopDate ,insulinName);

        //?????????????????????????????????
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

        //????????????
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
        //?????????????????????????????????
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
        //????????????
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
        //??????????????????
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

        //??????????????????????????????????????????????????????
        int diff = DateHelper.dateCompareGetDay(stopDt.substring(0 ,10) ,checkDt.substring(0 ,10));
        for(int i = 1; i <= diff - 1 ; i ++){
            String date = getAddDate(checkDt ,i);
            for(Map.Entry<String ,Double> entry : base.entrySet()){
                addDataToBaseMap(date ,entry.getKey() ,entry.getKey() ,entry.getValue() ,baseMap);
            }
            //????????????
            addDataToAddMap(date ,InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_BREAKFAST) ,addMap);
            addDataToAddMap(date ,InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_LUNCH) ,addMap);
            addDataToAddMap(date ,InsulinPumpConstant.ADD_TIME_BEFORE_DINNER ,add.get(InsulinPumpConstant.ADD_TIME_BEFORE_DINNER) ,addMap);
            insulinNameMap.putIfAbsent(date ,insulinName);
            dateSet.add(date);
        }
    }

    /**
     * ??????map???value????????????
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
     * ?????????????????????????????????????????????
     * @param yz
     * @param checkYzMap
     * @param stopYzMap
     */
    private void checkOrStopYzMapHandler(MemberYzListVO yz ,Map<String ,String> checkYzMap ,Map<String ,String> stopYzMap){
        //??????????????????
        String checkDate = yz.getCheckDt().substring(0 ,10);
        String mapCheckDt = checkYzMap.get(checkDate);
        if(StringUtils.isBlank(mapCheckDt)){
            checkYzMap.put(checkDate ,yz.getCheckDt());
        }else if(mapCheckDt.compareTo(yz.getCheckDt()) > 0){
            checkYzMap.put(checkDate ,yz.getCheckDt());
        }

        //??????????????????
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
     * ????????????????????????
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
     * ??????map????????????
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
     * ???????????? 1 ????????? 0 ????????????
     */
    private final static int DOSAGE_CHANGE_YES = 1;
    private final static int DOSAGE_CHANGE_NO = 0;
}
