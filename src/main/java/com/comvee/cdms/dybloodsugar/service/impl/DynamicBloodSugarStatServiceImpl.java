package com.comvee.cdms.dybloodsugar.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodsugar.bo.*;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.constant.GreenStarPlanConstant;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.mapper.DYYPStatisticsAdvicePOMapper;
import com.comvee.cdms.dybloodsugar.mapper.DyBloodSugarReportMapper;
import com.comvee.cdms.dybloodsugar.po.*;
import com.comvee.cdms.dybloodsugar.service.*;
import com.comvee.cdms.dybloodsugar.support.*;
import com.comvee.cdms.dybloodsugar.tool.AnalysisTool;
import com.comvee.cdms.dybloodsugar.tool.DyBloodSugarInsertValTool;
import com.comvee.cdms.dybloodsugar.tool.DynamicBloodSugarTool;
import com.comvee.cdms.dybloodsugar.vo.*;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.records.model.dto.DietRecordDTO;
import com.comvee.cdms.records.model.dto.DrugsRecordDTO;
import com.comvee.cdms.records.model.dto.SportRecordDTO;
import com.comvee.cdms.records.model.po.DrugsRecordPO;
import com.comvee.cdms.records.model.vo.DrugsRecordVO;
import com.comvee.cdms.records.service.DietRecordService;
import com.comvee.cdms.records.service.DrugsRecordService;
import com.comvee.cdms.records.service.SportRecordService;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service("dynamicBloodSugarStatService")
public class DynamicBloodSugarStatServiceImpl implements DynamicBloodSugarStatService {

    private final static Logger log = LoggerFactory.getLogger(DynamicBloodSugarStatServiceImpl.class);

    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    @Autowired
    private DyStaticsService dyStaticsService;

    @Autowired
    private DYYPStatisticsAdvicePOMapper dyypStatisticsAdvicePOMapper;

    @Autowired
    private DyMemberSettingService dyMemberSettingService;

    @Autowired
    private DyMemberSensorService dyMemberSensorService;

    @Autowired
    private DyRememberService dyRememberService;

    @Autowired
    private DyBloodSugarReportMapper dyBloodSugarReportMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DietRecordService dietService;

    @Autowired
    private SportRecordService sportService;

    @Autowired
    private DrugsRecordService drugService;


    @Override
    public DynamicBloodSugarDailyTrendVO getDynamicBloodSugarDailyTrend(DyStaticsDTO dto) {
        //????????????
        List<DYYPBloodSugarPO> paramLogOfYPPOS = loadWechatDYYPBloodSugarData(dto.getSensorNo(), dto.getStartDt(), dto.getEndDt());
        //????????????
        dynamicBloodSugarRemarkHandler(paramLogOfYPPOS);

        DynamicBloodSugarDailyTrendVO dailyTrendVO = new DynamicBloodSugarDailyTrendVO();

        //????????????
        DyRecordSettingPO settingRecordPO = this.dyMemberSettingService.getSettingValues(dto.getSensorNo());
        DynamicBloodSugarSettingsVO settingsVO = castDynamicBloodSugarSettings(settingRecordPO);


        DynamicBloodSugarIndexBaseVO base = getDynamicBloodSugarIndexBase(paramLogOfYPPOS, settingsVO);
        BeanUtils.copyProperties(dailyTrendVO, base);

        dailyTrendVO.setHighLineVal(settingsVO.getBloodSugarMaxAfter());
        dailyTrendVO.setLowLineVal(settingsVO.getBloodSugarMin());

        dailyTrendVO.setSettings(settingsVO);
        if (DynamicBloodSugarConstant.ORIGIN_WEB == dto.getOrigin()) {
            dailyTrendVO.setLineData(getDailyTrendLineData(paramLogOfYPPOS));
        } else if (DynamicBloodSugarConstant.ORIGIN_WECHAT == dto.getOrigin()) {
            dailyTrendVO.setWechatChartData(getWechatChartData(dto.getStartDt(), dto.getEndDt(), paramLogOfYPPOS));
        }
        DynamicBloodSugarDescComparator.sort(paramLogOfYPPOS);
        dailyTrendVO.setDataList(paramLogOfYPPOS);
        dailyTrendVO.setDate(dto.getStartDt().substring(0, 10));

        DYMemberSensorPO dyMemberSensorPO = this.dyMemberSensorService.getDYMemberSensorPO(dto.getSensorNo());
        String memberId = Optional.ofNullable(dyMemberSensorPO).map(DYMemberSensorPO::getMemberId).orElse(null);

        dailyTrendChartDataWorkRestHandler(dailyTrendVO, settingRecordPO, memberId);
        statBaseHandler(dailyTrendVO, dto.getStartDt(), dto.getEndDt(), dto.getSensorNo(), DynamicBloodSugarConstant.STATISTICS_REPORT_TYPE_1);
        //???????????????????????????
        dailyTrendVO.setDataSimulationList(getDataSimulationList(dto));
        return dailyTrendVO;
    }

    @Override
    public DynamicBloodSugarDailySummaryListVO listDynamicBloodSugarDailySummary(DyStaticsDTO dto) {

        //????????????
        DyRecordSettingPO settingRecordPO = this.dyMemberSettingService.getSettingValues(dto.getSensorNo());
        DynamicBloodSugarSettingsVO settingsVO = castDynamicBloodSugarSettings(settingRecordPO);

        //????????????
        List<DYYPBloodSugarPO> paramLogOfYPPOS = loadWechatDYYPBloodSugarData(dto.getSensorNo(), dto.getStartDt(), dto.getEndDt());
        //????????????????????????
        MultiValueMap<String, DYYPBloodSugarPO> multiValueMap = getMultiValueMapByRecordDt(paramLogOfYPPOS);
        List<DynamicBloodSugarDailySummaryVO> dailySummaryList = new ArrayList<>();
        for (Map.Entry<String, List<DYYPBloodSugarPO>> entry : multiValueMap.entrySet()) {
            dailySummaryList.add(dynamicBloodSugarDailySummaryHandler(entry.getValue(), entry.getKey()
                    , dto.getOrigin(), settingsVO));
        }
        DynamicBloodSugarDailySummaryListVO result = new DynamicBloodSugarDailySummaryListVO();
        DynamicBloodSugarDailySummaryDescComparator.sort(dailySummaryList);
        result.setDataList(dailySummaryList);
        statBaseHandler(result, dto.getStartDt(), dto.getEndDt(), dto.getSensorNo(), DynamicBloodSugarConstant.STATISTICS_REPORT_TYPE_4);

        result.setSettings(settingsVO);
        return result;
    }

    @Override
    public DynamicBloodSugarDynamicVO getDynamicBloodSugarDynamic(DyStaticsDTO dto) {
        //??????????????????????????????
        String endDt = DateHelper.plusDate(dto.getEndDt(), 1);
        //????????????
        List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService
                .listDataWechatSourceOfYPParamLogOfOBDTASC(dto.getStartDt(), endDt, dto.getSensorNo());
        List<DYYPBloodSugarPO> threeAmToThreeAmDataList = null;
        List<DYYPBloodSugarPO> zeroAmToZeroAmDataList = null;
        if (paramLogOfYPPOS != null) {
            //??????????????????3??????????????????????????????3?????????????????????????????????
            Date threeAmStartDate = DateHelper.getDate(dto.getStartDt() + " 03:00:00", DateHelper.DATETIME_FORMAT);
            Date threeAmEndDate = DateHelper.getDate(endDt + " 03:00:00", DateHelper.DATETIME_FORMAT);
            threeAmToThreeAmDataList = paramLogOfYPPOS.stream()
                    .filter(x -> x.getRecordTime().after(threeAmStartDate) && x.getRecordTime().before(threeAmEndDate))
                    .collect(Collectors.toList());
            //?????????????????????????????????????????????????????????????????????
            Date zeroAmStartDate = DateHelper.getDate(dto.getStartDt() + " 00:00:00", DateHelper.DATETIME_FORMAT);
            Date zeroAmEndDate = DateHelper.getDate(endDt + " 00:00:00", DateHelper.DATETIME_FORMAT);
            zeroAmToZeroAmDataList = paramLogOfYPPOS.stream()
                    .filter(x -> x.getRecordTime().after(zeroAmStartDate) && x.getRecordTime().before(zeroAmEndDate))
                    .collect(Collectors.toList());
        }
        //????????????????????????List
//        Map<String, List<Double>> paramLogOfYPPOSMap = listGroup(paramLogOfYPPOS);
        //????????????
        DyRecordSettingPO settingRecordPO = this.dyMemberSettingService.getSettingValues(dto.getSensorNo());
        DynamicBloodSugarSettingsVO settingsVO = castDynamicBloodSugarSettings(settingRecordPO);

        DynamicBloodSugarDynamicVO result = new DynamicBloodSugarDynamicVO();
        List<DyBloodSugarValueDTO> dtoList = timeDeal(threeAmToThreeAmDataList);
        result.setValueList(dtoList);
        DynamicBloodSugarIndexBaseVO base = getDynamicBloodSugarIndexBase(zeroAmToZeroAmDataList, settingsVO);
        BeanUtils.copyProperties(result, base);

        result.setHighLineVal(settingRecordPO.getBloodSugarMax());
        result.setLowLineVal(settingRecordPO.getBloodSugarMin());
        //???????????????????????????
        result.setTimeIntervalData(getTimeIntervalData(paramLogOfYPPOS));
        result.setSettings(settingsVO);
        //????????????????????????
        MultiValueMap<String ,DYYPBloodSugarPO> multiValueMap = getMultiValueMapByRecordDt(zeroAmToZeroAmDataList);
        Map<String, DynamicBloodSugarDynamicItemVO> dynamicItemMap = new HashMap<>();
        List<DynamicBloodSugarDynamicItemVO> dataList = new ArrayList<>();
        for (Map.Entry<String, List<DYYPBloodSugarPO>> entry : multiValueMap.entrySet()) {
            DynamicBloodSugarDynamicItemVO dynamicBloodSugarDynamicItemVO = dynamicBloodSugarDynamicItemHandler(entry.getValue(), entry.getKey(), settingsVO);
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                dynamicBloodSugarDynamicItemVO.setRecordStartTime(DateHelper.getDate(entry.getValue().get(0).getRecordTime(), "yyyy-MM-dd HH:mm:ss"));
                dynamicBloodSugarDynamicItemVO.setRecordEndTime(DateHelper.getDate(entry.getValue().get(entry.getValue().size() - 1).getRecordTime(), "yyyy-MM-dd HH:mm:ss"));
            }
            dynamicItemMap.put(entry.getKey(), dynamicBloodSugarDynamicItemVO);
        }
        DynamicBloodSugarDynamicItemVO itemVO = null;
        DynamicBloodSugarDynamicItemVO beforeItemVO = null;
        for (Map.Entry<String, DynamicBloodSugarDynamicItemVO> entry : dynamicItemMap.entrySet()) {
            itemVO = entry.getValue();
            if (checkFullRecord(itemVO.getTotalEvent())) {
                String theDayBefore = getTheDayBefore(entry.getKey());
                beforeItemVO = dynamicItemMap.get(theDayBefore);
                //?????????????????????????????????
                if (beforeItemVO != null && checkFullRecord(beforeItemVO.getTotalEvent())) {
                    itemVO.setBloodSugarMeanAbsoluteDeviation(meanAbsoluteDeviationHandler(itemVO.getAvgNum(), beforeItemVO.getAvgNum()));
                }
            }
            //dataList ????????????????????????
            if (!itemVO.getRecordDt().equals(endDt))
                dataList.add(itemVO);
        }
        DynamicBloodSugarDynamicDescComparator.sort(dataList);
        result.setDataList(dataList);
        //agp??????,?????????????????????????????????
        if (multiValueMap.size() >= DynamicBloodSugarConstant.DYNAMIC_CHART_MINIMUM_DAY) {
            if (DynamicBloodSugarConstant.ORIGIN_WEB == dto.getOrigin()) {
                agpHandler(result, dto.getStartDt(), dto.getEndDt(), threeAmToThreeAmDataList);
                //????????????????????????,??????????????????,???????????????????????????
/*                Map<String, List> resultMap = lowOrHignDeal(dtoList, settingsVO, result, paramLogOfYPPOSMap);
                result.setResultMap(resultMap);*/
            } else if (DynamicBloodSugarConstant.ORIGIN_WECHAT == dto.getOrigin()) {
                agpWechatChartHandler(result, dto.getStartDt(), dto.getEndDt(), threeAmToThreeAmDataList);
            }
        } else {
            result.setShowChart(false);
        }

        statBaseHandler(result, dto.getStartDt(), dto.getEndDt(), dto.getSensorNo(), DynamicBloodSugarConstant.STATISTICS_REPORT_TYPE_2);
        return result;
    }

    /**
     * ????????????????????????List
     *
     * @param dataList
     * @return
     */
    public static Map<String, List<Double>> listGroup(List<DYYPBloodSugarPO> dataList) {
        Map<String, List<Double>> resultMap = new HashMap<String, List<Double>>();
        for (int i = 0; i < dataList.size(); i++) {

            // ??????RecordDt??????????????????List
            if (resultMap.containsKey(dataList.get(i).getRecordDt().toString())) {
                //?????????key?????????value??????add??????
                resultMap.get(dataList.get(i).getRecordDt().toString()).add(dataList.get(i).getValue().doubleValue());
            } else {
                List<Double> list = new ArrayList<>();
                list.add(dataList.get(i).getValue().doubleValue());
                resultMap.put(dataList.get(i).getRecordDt().toString(), list);
            }
        }
        return resultMap;
    }


    /**
     * ????????????
     *
     * @param list
     * @return
     */
    private List<DyBloodSugarValueDTO> timeDeal(List<DYYPBloodSugarPO> list) {
        List<DyBloodSugarValueDTO> dtoList = new ArrayList<>();
        list.forEach(x -> {
            DyBloodSugarValueDTO dyBloodSugarValueDTO = new DyBloodSugarValueDTO();
            String recordTime = DateHelper.dateToString(x.getRecordTime()).substring(11, 16);
            dyBloodSugarValueDTO.setRecordTime(recordTime);
            dyBloodSugarValueDTO.setValue(x.getValue());
            dtoList.add(dyBloodSugarValueDTO);
        });

        return dtoList;
    }

    /**
     * ????????????????????????,??????????????????,?????????????????????????????????
     *
     * @return
     */
    private Map<String, List> lowOrHignDeal(List<DyBloodSugarValueDTO> dtoList, DynamicBloodSugarSettingsVO settingsVO,
                                            DynamicBloodSugarDynamicVO vo, Map<String, List<Double>> paramLogOfYPPOSMap) {

        Map<String, List> map = new HashMap<>();
        //???????????????????????? 1??? 2?????? 3???
        List<Integer> possibilityList = possibilityDeal(dtoList, settingsVO);
        map.put("possibilityList", possibilityList);
        //???????????????????????????????????? 1??? 2?????? 3???
        Boolean boo = judgeBloodSugarRise(paramLogOfYPPOSMap);
        List<Integer> medianList = medianTarget(boo, vo, settingsVO);
        map.put("medianList", medianList);
        //??????????????????????????? 1??? 2?????? 3???
        List<Integer> volatilityList = volatilityDeal(settingsVO, vo);
        map.put("volatilityList", volatilityList);
        return map;
    }

    private static final BigDecimal BELOW_VALUE = new BigDecimal(3.3);
    private static final BigDecimal UNDER_VALUE = new BigDecimal(2.8);
    private static final String START_BY_DATE = "1970-01-01 03:00";
    private static final String EIGHT_BY_DATE = "1970-01-01 08:00";
    private static final String TWELVE_BY_DATE = "1970-01-01 12:00";
    private static final String EIGHTEEN_BY_DATE = "1970-01-01 18:00";
    private static final String TWENTY_TWO_BY_DATE = "1970-01-01 22:00";
    private static final String DATE_FORMAT = "1970-01-01 ";
    //1??? 2?????? 3???
    private static final Integer LOW = 1;
    private static final Integer MEDIUM = 2;
    private static final Integer HIGH = 3;


    /**
     * ??????????????????????????????????????????2.2 mmol/L??????
     * true ???  fales ???
     *
     * @param paramLogOfYPPOSMap
     * @return
     */
    private Boolean judgeBloodSugarRise(Map<String, List<Double>> paramLogOfYPPOSMap) {

        List<Double> list = new ArrayList();
        for (Map.Entry<String, List<Double>> entry : paramLogOfYPPOSMap.entrySet()) {
            Collections.sort(entry.getValue());
            Double res = DynamicBloodSugarTool.getQuantile(entry.getValue(), 2, 4);
            list.add(res);
        }
        Collections.sort(list);
        if (list.get(list.size() - 1) - list.get(0) > 2.2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ??????????????????????????????
     *
     * @return
     */
    private List<Integer> possibilityDeal(List<DyBloodSugarValueDTO> dtoList, DynamicBloodSugarSettingsVO settingsVO) {

        //?????????
        Double oneByCount = 0D;
        Double twoByCount = 0D;
        Double threeByCount = 0D;
        Double fourByCount = 0D;
        Double fiveByCount = 0D;
        //??????3.3mmol/L?????????
        Double oneBelowCount = 0D;
        Double twoBelowCount = 0D;
        Double threeBelowCount = 0D;
        Double fourBelowCount = 0D;
        Double fiveBelowCount = 0D;
        //??????2.8mmol/L?????????
        Double oneUnderCount = 0D;
        Double twoUnderCount = 0D;
        Double threeUnderCount = 0D;
        Double fourUnderCount = 0D;
        Double fiveUnderCount = 0D;
        for (DyBloodSugarValueDTO dto : dtoList) {
//            String recordTime = dto.getRecordTime();
            String recordTime = DATE_FORMAT + dto.getRecordTime();
            Date recordDate = DateHelper.getDate(recordTime, DateHelper.DATETIME);
            Date startByDate = DateHelper.getDate(START_BY_DATE, DateHelper.DATETIME);
            Date eightByDate = DateHelper.getDate(EIGHT_BY_DATE, DateHelper.DATETIME);
            Date twelveByDate = DateHelper.getDate(TWELVE_BY_DATE, DateHelper.DATETIME);
            Date eighteenByDate = DateHelper.getDate(EIGHTEEN_BY_DATE, DateHelper.DATETIME);
            Date twentyTwoByDate = DateHelper.getDate(TWENTY_TWO_BY_DATE, DateHelper.DATETIME);
            if (startByDate.getTime() < recordDate.getTime() && recordDate.getTime() < eightByDate.getTime()) {
                oneByCount++;
                if (BELOW_VALUE.compareTo(dto.getValue()) == 1) {
                    oneBelowCount++;
                }
                if (UNDER_VALUE.compareTo(dto.getValue()) == 1) {
                    oneUnderCount++;
                }
            } else if (eightByDate.getTime() < recordDate.getTime() && recordDate.getTime() < twelveByDate.getTime()) {
                twoByCount++;
                if (BELOW_VALUE.compareTo(dto.getValue()) == 1) {
                    twoBelowCount++;
                }
                if (UNDER_VALUE.compareTo(dto.getValue()) == 1) {
                    twoUnderCount++;
                }
            } else if (twelveByDate.getTime() < recordDate.getTime() && recordDate.getTime() < eighteenByDate.getTime()) {
                threeByCount++;
                if (BELOW_VALUE.compareTo(dto.getValue()) == 1) {
                    threeBelowCount++;
                }
                if (UNDER_VALUE.compareTo(dto.getValue()) == 1) {
                    threeUnderCount++;
                }
            } else if (eighteenByDate.getTime() < recordDate.getTime() && recordDate.getTime() < twentyTwoByDate.getTime()) {
                fourByCount++;
                if (BELOW_VALUE.compareTo(dto.getValue()) == 1) {
                    fourBelowCount++;
                }
                if (UNDER_VALUE.compareTo(dto.getValue()) == 1) {
                    fourUnderCount++;
                }
            } else if (twentyTwoByDate.getTime() < recordDate.getTime() || recordDate.getTime() < startByDate.getTime()) {
                fiveByCount++;
                if (BELOW_VALUE.compareTo(dto.getValue()) == 1) {
                    fiveBelowCount++;
                }
                if (UNDER_VALUE.compareTo(dto.getValue()) == 1) {
                    fiveUnderCount++;
                }
            }
        }
        List<Integer> glucose = new ArrayList<>();
        //???????????????????????????????????? 1??? 2?????? 3???
        if ("???".equals(settingsVO.getGlucoseMin())) {
            //????????????
            if (((oneBelowCount * 100) / oneByCount) / 4D > 0.5 || ((oneUnderCount * 100) / oneByCount) / 2D > 0.5) {
                glucose.add(HIGH);
            } else if (((oneBelowCount * 100) / oneByCount) / 4D < 0.1 || ((oneUnderCount * 100) / oneByCount) / 2D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((twoBelowCount * 100) / twoByCount) / 4D > 0.5 || ((twoUnderCount * 100) / twoByCount) / 2D > 0.5) {
                glucose.add(HIGH);
            } else if (((twoBelowCount * 100) / twoByCount) / 4D < 0.1 || ((twoUnderCount * 100) / twoByCount) / 2D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((threeBelowCount * 100) / threeByCount) / 4D > 0.5 || ((threeUnderCount * 100) / threeByCount) / 2D > 0.5) {
                glucose.add(HIGH);
            } else if (((threeBelowCount * 100) / threeByCount) / 4D < 0.1 || ((threeUnderCount * 100) / threeByCount) / 2D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((fourBelowCount * 100) / fourByCount) / 4D > 0.5 || ((fourUnderCount * 100) / fourByCount) / 2D > 0.5) {
                glucose.add(HIGH);
            } else if (((fourBelowCount * 100) / fourByCount) / 4D < 0.1 || ((fourUnderCount * 100) / fourByCount) / 2D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((fiveBelowCount * 100) / fiveByCount) / 4D > 0.5 || ((fiveUnderCount * 100) / fiveByCount) / 2D > 0.5) {
                glucose.add(HIGH);
            } else if (((fiveBelowCount * 100) / fiveByCount) / 4D < 0.1 || ((fiveUnderCount * 100) / fiveByCount) / 2D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
        } else if ("???".equals(settingsVO.getGlucoseMin())) {
            //????????????
            if (((oneBelowCount * 100) / oneByCount) / 8D > 0.5 || ((oneUnderCount * 100) / oneByCount) / 4D > 0.5) {
                glucose.add(HIGH);
            } else if (((oneBelowCount * 100) / oneByCount) / 8D < 0.1 || ((oneUnderCount * 100) / oneByCount) / 4D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((twoBelowCount * 100) / twoByCount) / 8D > 0.5 || ((twoUnderCount * 100) / twoByCount) / 4D > 0.5) {
                glucose.add(HIGH);
            } else if (((twoBelowCount * 100) / twoByCount) / 8D < 0.1 || ((twoUnderCount * 100) / twoByCount) / 4D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((threeBelowCount * 100) / threeByCount) / 8D > 0.5 || ((threeUnderCount * 100) / threeByCount) / 4D > 0.5) {
                glucose.add(HIGH);
            } else if (((threeBelowCount * 100) / threeByCount) / 8D < 0.1 || ((threeUnderCount * 100) / threeByCount) / 4D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((fourBelowCount * 100) / fourByCount) / 8D > 0.5 || ((fourUnderCount * 100) / fourByCount) / 4D > 0.5) {
                glucose.add(HIGH);
            } else if (((fourBelowCount * 100) / fourByCount) / 8D < 0.1 || ((fourUnderCount * 100) / fourByCount) / 4D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((fiveBelowCount * 100) / fiveByCount) / 8D > 0.5 || ((fiveUnderCount * 100) / fiveByCount) / 4D > 0.5) {
                glucose.add(HIGH);
            } else if (((fiveBelowCount * 100) / fiveByCount) / 8D < 0.1 || ((fiveUnderCount * 100) / fiveByCount) / 4D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
        } else if ("???".equals(settingsVO.getGlucoseMin())) {
            //????????????
            if (((oneBelowCount * 100) / oneByCount) / 20D > 0.5 || ((oneUnderCount * 100) / oneByCount) / 10D > 0.5) {
                glucose.add(HIGH);
            } else if (((oneBelowCount * 100) / oneByCount) / 20D < 0.1 || ((oneUnderCount * 100) / oneByCount) / 10D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((twoBelowCount * 100) / twoByCount) / 20D > 0.5 || ((twoUnderCount * 100) / twoByCount) / 10D > 0.5) {
                glucose.add(HIGH);
            } else if (((twoBelowCount * 100) / twoByCount) / 20D < 0.1 || ((twoUnderCount * 100) / twoByCount) / 10D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((threeBelowCount * 100) / threeByCount) / 20D > 0.5 || ((threeUnderCount * 100) / threeByCount) / 10D > 0.5) {
                glucose.add(HIGH);
            } else if (((threeBelowCount * 100) / threeByCount) / 20D < 0.1 || ((threeUnderCount * 100) / threeByCount) / 10D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((fourBelowCount * 100) / fourByCount) / 20D > 0.5 || ((fourUnderCount * 100) / fourByCount) / 10D > 0.5) {
                glucose.add(HIGH);
            } else if (((fourBelowCount * 100) / fourByCount) / 20D < 0.1 || ((fourUnderCount * 100) / fourByCount) / 10D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
            //????????????
            if (((fiveBelowCount * 100) / fiveByCount) / 20D > 0.5 || ((fiveUnderCount * 100) / fiveByCount) / 10D > 0.5) {
                glucose.add(HIGH);
            } else if (((fiveBelowCount * 100) / fiveByCount) / 20D < 0.1 || ((fiveUnderCount * 100) / fiveByCount) / 10D < 0.1) {
                glucose.add(LOW);
            } else {
                glucose.add(MEDIUM);
            }
        } else {
            log.info("???????????????????????????????????????");
        }
        return glucose;
    }


    /**
     * ???????????????????????????????????? 1??? 2?????? 3???
     *
     * @return
     */
    private List<Integer> medianTarget(Boolean boo, DynamicBloodSugarDynamicVO vo, DynamicBloodSugarSettingsVO settingsVO) {
        List<Integer> medianList = new ArrayList<>();
        //?????????
        List<String> oneMedianList = vo.getMedianPointList().subList(0, 20);
        List<String> twoMedianList = vo.getMedianPointList().subList(20, 36);
        List<String> threeMedianList = vo.getMedianPointList().subList(36, 60);
        List<String> fourMedianList = vo.getMedianPointList().subList(60, 76);
        List<String> fiveMedianList = vo.getMedianPointList().subList(76, 96);
        //????????????
        for (String medianStr : oneMedianList) {
            Double medianPoint = Double.parseDouble(medianStr);
            if (medianPoint > settingsVO.getMedianTarget() && medianPoint >= settingsVO.getMedianTarget() * 1.2 && boo) {
                medianList.add(HIGH);
                break;
            }
            if (medianPoint > settingsVO.getMedianTarget()) {
                medianList.add(MEDIUM);
                break;
            }
        }
        if (medianList.size() != 1) {
            medianList.add(LOW);
        }
        //????????????
        for (String medianStr : twoMedianList) {
            Double medianPoint = Double.parseDouble(medianStr);
            if (medianPoint > settingsVO.getMedianTarget() && medianPoint >= settingsVO.getMedianTarget() * 1.2 && boo) {
                medianList.add(HIGH);
                break;
            }
            if (medianPoint > settingsVO.getMedianTarget()) {
                medianList.add(MEDIUM);
                break;
            }
        }
        if (medianList.size() != 2) {
            medianList.add(LOW);
        }
        //????????????
        for (String medianStr : threeMedianList) {
            Double medianPoint = Double.parseDouble(medianStr);
            if (medianPoint > settingsVO.getMedianTarget() && medianPoint >= settingsVO.getMedianTarget() * 1.2 && boo) {
                medianList.add(HIGH);
                break;
            }
            if (medianPoint > settingsVO.getMedianTarget()) {
                medianList.add(MEDIUM);
                break;
            }
        }
        if (medianList.size() != 3) {
            medianList.add(LOW);
        }
        //????????????
        for (String medianStr : fourMedianList) {
            Double medianPoint = Double.parseDouble(medianStr);
            if (medianPoint > settingsVO.getMedianTarget() && medianPoint >= settingsVO.getMedianTarget() * 1.2 && boo) {
                medianList.add(HIGH);
                break;
            }
            if (medianPoint > settingsVO.getMedianTarget()) {
                medianList.add(MEDIUM);
                break;
            }
        }
        if (medianList.size() != 4) {
            medianList.add(LOW);
        }
        //????????????
        for (String medianStr : fiveMedianList) {
            Double medianPoint = Double.parseDouble(medianStr);
            if (medianPoint > settingsVO.getMedianTarget() && medianPoint >= settingsVO.getMedianTarget() * 1.2 && boo) {
                medianList.add(HIGH);
                break;
            }
            if (medianPoint > settingsVO.getMedianTarget()) {
                medianList.add(MEDIUM);
                break;
            }
        }
        if (medianList.size() != 5) {
            medianList.add(LOW);
        }

        return medianList;
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    private List<Integer> volatilityDeal(DynamicBloodSugarSettingsVO settingsVO, DynamicBloodSugarDynamicVO vo) {
        List<Integer> volatilityList = new ArrayList<>();
        //??????????????????
        List<String> oneMedianList = vo.getMedianPointList().subList(0, 20);
        List<String> twoMedianList = vo.getMedianPointList().subList(20, 36);
        List<String> threeMedianList = vo.getMedianPointList().subList(36, 60);
        List<String> fourMedianList = vo.getMedianPointList().subList(60, 76);
        List<String> fiveMedianList = vo.getMedianPointList().subList(76, 96);
        //10???????????????
        List<String> onePer10PointList = vo.getLowerLimitPointList().get(0).subList(0, 20);
        List<String> twoPer10PointList = vo.getLowerLimitPointList().get(0).subList(20, 36);
        List<String> threePer10PointList = vo.getLowerLimitPointList().get(0).subList(36, 60);
        List<String> fourPer10PointList = vo.getLowerLimitPointList().get(0).subList(60, 76);
        List<String> fivePer10PointList = vo.getLowerLimitPointList().get(0).subList(76, 96);

        //????????????
        for (int i = 0; i < oneMedianList.size(); i++) {
            if (Double.parseDouble(oneMedianList.get(i)) - Double.parseDouble(onePer10PointList.get(i)) < 1.9D) {
                volatilityList.add(LOW);
                break;
            }
            if (Double.parseDouble(oneMedianList.get(i)) > settingsVO.getMedianTarget() && Double.parseDouble(onePer10PointList.get(i)) < 3.9D) {
                volatilityList.add(HIGH);
                break;
            }
        }
        if (volatilityList.size() != 1) {
            volatilityList.add(MEDIUM);
        }
        //????????????
        for (int i = 0; i < twoMedianList.size(); i++) {
            if (Double.parseDouble(twoMedianList.get(i)) - Double.parseDouble(twoPer10PointList.get(i)) < 1.9D) {
                volatilityList.add(LOW);
                break;
            }
            if (Double.parseDouble(twoMedianList.get(i)) > settingsVO.getMedianTarget() && Double.parseDouble(twoPer10PointList.get(i)) < 3.9D) {
                volatilityList.add(HIGH);
                break;
            }
        }
        if (volatilityList.size() != 2) {
            volatilityList.add(MEDIUM);
        }
        //????????????
        for (int i = 0; i < threeMedianList.size(); i++) {
            if (Double.parseDouble(threeMedianList.get(i)) - Double.parseDouble(threePer10PointList.get(i)) < 1.9D) {
                volatilityList.add(LOW);
                break;
            }
            if (Double.parseDouble(threeMedianList.get(i)) > settingsVO.getMedianTarget() && Double.parseDouble(threePer10PointList.get(i)) < 3.9D) {
                volatilityList.add(HIGH);
                break;
            }
        }
        if (volatilityList.size() != 3) {
            volatilityList.add(MEDIUM);
        }
        //????????????
        for (int i = 0; i < fourMedianList.size(); i++) {
            if (Double.parseDouble(fourMedianList.get(i)) - Double.parseDouble(fourPer10PointList.get(i)) < 1.9D) {
                volatilityList.add(LOW);
                break;
            } else if (Double.parseDouble(fourMedianList.get(i)) > settingsVO.getMedianTarget() && Double.parseDouble(fourPer10PointList.get(i)) < 3.9D) {
                volatilityList.add(HIGH);
                break;
            }
        }
        if (volatilityList.size() != 4) {
            volatilityList.add(MEDIUM);
        }
        //????????????
        for (int i = 0; i < fiveMedianList.size(); i++) {
            if (Double.parseDouble(fiveMedianList.get(i)) - Double.parseDouble(fivePer10PointList.get(i)) < 1.9D) {
                volatilityList.add(LOW);
                break;
            }
            if (Double.parseDouble(fiveMedianList.get(i)) > settingsVO.getMedianTarget() && Double.parseDouble(fivePer10PointList.get(i)) < 3.9D) {
                volatilityList.add(HIGH);
                break;
            }
        }
        if (volatilityList.size() != 5) {
            volatilityList.add(MEDIUM);
        }
        return volatilityList;
    }

    @Override
    public DynamicBloodSugarMeanAbsoluteDeviationVO getDynamicBloodSugarMeanAbsoluteDeviation(DyStaticsDTO dto) {
        //????????????
        List<DYYPBloodSugarPO> paramLogOfYPPOS = loadDYYPBloodSugarData(dto.getSensorNo(), dto.getStartDt(), dto.getEndDt());
        //????????????????????????
        MultiValueMap<String, DYYPBloodSugarPO> multiValueMap = getMultiValueMapByRecordDt(paramLogOfYPPOS);

        List<DynamicBloodSugarMeanAbsoluteDeviationItemVO> dataList = new ArrayList<>();
        String recordDtTemp = "";
        for (Map.Entry<String, List<DYYPBloodSugarPO>> entry : multiValueMap.entrySet()) {
            if (StringUtils.isBlank(recordDtTemp)) {
                recordDtTemp = entry.getKey();
                continue;
            }
            int diff = DateHelper.dateCompareGetDay(entry.getKey(), recordDtTemp);
            if (diff == 1) {
                dataList.add(dynamicBloodSugarMeanAbsoluteDeviationItemHandler(recordDtTemp, entry.getKey(), multiValueMap));
            }
            recordDtTemp = entry.getKey();
        }
        DynamicBloodSugarMeanAbsoluteDeviationVO result = new DynamicBloodSugarMeanAbsoluteDeviationVO();
        if (dataList != null && !dataList.isEmpty()) {
            //??????
            Collections.sort(dataList, (a, b) -> b.getYesterday().compareTo(a.getYesterday()));
        }
        result.setDataList(dataList);
        statBaseHandler(result, dto.getStartDt(), dto.getEndDt(), dto.getSensorNo(), DynamicBloodSugarConstant.STATISTICS_REPORT_TYPE_3);
        return result;
    }

    @Override
    public void deleteStatAdvice(String sid) {
        this.dyypStatisticsAdvicePOMapper.deleteBySid(sid);
    }

    @Override
    public DynamicBloodSugarDailyTrendV2VO getDynamicBloodSugarDailyTrendV2(DyStaticsDTO dto) {
        DynamicBloodSugarDailyTrendV2VO result = new DynamicBloodSugarDailyTrendV2VO();
        List<String> dateList;
        if (!StringUtils.isBlank(dto.getDateList())) {
            dateList = Arrays.asList(dto.getDateList().split(","));
        } else {
            return result;
        }
        //????????????
        List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService.listDataSourceOfYPParamLogOfOBDTASC(dateList, dto.getSensorNo());
        //????????????
        dynamicBloodSugarRemarkHandler(paramLogOfYPPOS);
        //????????????
        DyRecordSettingPO settingRecordPO = this.dyMemberSettingService.getSettingValues(dto.getSensorNo());
        DynamicBloodSugarSettingsVO settingsVO = castDynamicBloodSugarSettings(settingRecordPO);

        DynamicBloodSugarIndexBaseVO base = getDynamicBloodSugarIndexBase(paramLogOfYPPOS, settingsVO);

        BeanUtils.copyProperties(result, base);
        result.setSettings(settingsVO);
        boolean sameDay = checkSameDay(dto.getStartDt(), dto.getEndDt());
        //???????????????
        MultiValueMap<String, DYYPBloodSugarPO> dayDataMap = getMultiValueMapByRecordDt(paramLogOfYPPOS);
        //??????????????????
        List<DynamicBloodChartDayItemVO> chartData = getDailyTrendChartData(dayDataMap);
        //???????????????????????????
        result.setTimeIntervalData(getTimeIntervalData(paramLogOfYPPOS));
        //???????????????????????????
        result.setDataSimulationList(getDataSimulationList(dto));
        if (sameDay && chartData.size() > 0) {

            DynamicBloodChartDayItemVO dayItemVO = chartData.get(0);
            //?????????????????????????????????
            if (checkFullRecord(paramLogOfYPPOS.size())) {
                Double meanAbsoluteDifference = getMeanAbsoluteDifference(dto.getStartDt().substring(0, 10), dto.getSensorNo(), base.getAvgNum());
                result.setMeanAbsoluteDifference(meanAbsoluteDifference);
            }

            //????????????
            dailyTrendChartDataRemarkHandler(dayItemVO, dto.getSensorNo());

            DYMemberSensorPO dyMemberSensorPO = this.dyMemberSensorService.getDYMemberSensorPO(dto.getSensorNo());
            String memberId = Optional.ofNullable(dyMemberSensorPO).map(DYMemberSensorPO::getMemberId).orElse(null);

            //??????????????????
            dailyTrendChartDataWorkRestHandler(dayItemVO, settingRecordPO, memberId);

            //????????????????????????
            dailyTrendChartDataDietHandler(dayItemVO, memberId);

            //????????????
            dailyTrendChartDataSportHandler(dayItemVO, memberId);

            //????????????
            dailyTrendChartDataDrugHandler(dayItemVO, memberId);


        }
        result.setChartData(chartData);
        statBaseHandler(result, dto.getStartDt(), dto.getEndDt(), dto.getSensorNo(), DynamicBloodSugarConstant.STATISTICS_REPORT_TYPE_1);
        return result;
    }

    @Override
    public DynamicBloodSugarDailySummaryListV2VO listDynamicBloodSugarDailySummaryV2(DyStaticsDTO dto) {

        //????????????
        List<DYYPBloodSugarPO> paramLogOfYPPOS = loadWechatDYYPBloodSugarData(dto.getSensorNo(), dto.getStartDt(), dto.getEndDt());
        //????????????????????????
        MultiValueMap<String, DYYPBloodSugarPO> multiValueMap = getMultiValueMapByRecordDt(paramLogOfYPPOS);

        //????????????
        DyRecordSettingPO settingRecordPO = this.dyMemberSettingService.getSettingValues(dto.getSensorNo());
        DynamicBloodSugarSettingsVO settingsVO = castDynamicBloodSugarSettings(settingRecordPO);

        DYMemberSensorPO dyMemberSensorPO = this.dyMemberSensorService.getDYMemberSensorPO(dto.getSensorNo());
        String memberId = Optional.ofNullable(dyMemberSensorPO).map(DYMemberSensorPO::getMemberId).orElse(null);

        List<DynamicBloodSugarDailySummaryV2VO> dailySummaryList = new ArrayList<>();

        for (Map.Entry<String, List<DYYPBloodSugarPO>> entry : multiValueMap.entrySet()) {
            dailySummaryList.add(dynamicBloodSugarDailySummaryHandlerV2(entry.getValue(), entry.getKey()
                    , settingRecordPO, memberId));
        }
        DynamicBloodSugarDailySummaryListV2VO result = new DynamicBloodSugarDailySummaryListV2VO();

        DynamicBloodSugarIndexBaseVO base = getDynamicBloodSugarIndexBase(paramLogOfYPPOS, settingsVO);
        BeanUtils.copyProperties(result, base);
        result.setDataList(dailySummaryList);
        result.setSettings(settingsVO);
        statBaseHandler(result, dto.getStartDt(), dto.getEndDt(), dto.getSensorNo(), DynamicBloodSugarConstant.STATISTICS_REPORT_TYPE_4);
        return result;
    }

    /**
     * ????????????????????? ????????????
     *
     * @param list
     * @return
     */
    private List<String> getDailyTrendLineData(List<DYYPBloodSugarPO> list) {
        List<String> result = getChartDefaultIndexList();
        for (DYYPBloodSugarPO item : list) {
            result.set(DynamicBloodSugarTool.getLineDataIndex(item.getRecordTime()), item.getValue().toString());
        }
        return result;
    }

    /**
     * ????????????????????????????????????
     *
     * @param list
     * @return
     */
    @Override
    public DynamicBloodSugarIndexBaseVO getDynamicBloodSugarIndexBase(List<DYYPBloodSugarPO> list ,DynamicBloodSugarSettingsVO settings){
        //???????????????????????????
        if (list == null || list.isEmpty()) {
            return getDefaultDynamicBloodSugarIndexBase();
        }
        Integer totalEvent = 0, lowEvent = 0, highEvent = 0, normalEvent = 0, customLessThan = 0, customGreaterThan = 0;

        Double sum = 0D;
        List<Double> valueList = new ArrayList<>();
        //??????mage???????????????
        List<DynamicBloodSugarMAGEItemBO> itemList = new ArrayList<>();
        //???????????????????????????????????????15???????????????????????????????????????15???????????????????????????????????????
        for (DYYPBloodSugarPO item : list) {
            sum += item.getValue().doubleValue();
            valueList.add(item.getValue().doubleValue());
            addMAGEItem(itemList, item);
            if (!checkRecordDateIsDayFirst15Minutes(item.getRecordTime())) {
                totalEvent++;
                //??????
                if (item.getValue().doubleValue() > settings.getBloodSugarMaxAfter()) {
                    highEvent++;
                }
                //??????
                else if (item.getValue().doubleValue() < settings.getBloodSugarMin()) {
                    lowEvent++;
                }
                //??????
                else {
                    normalEvent++;
                }

                if (settings.getBloodTimeRatioMin() != null && item.getValue().doubleValue() < settings.getBloodTimeRatioMin()) {
                    customLessThan++;
                }
                if (settings.getBloodTimeRatioMax() != null && item.getValue().doubleValue() > settings.getBloodTimeRatioMax()) {
                    customGreaterThan++;
                }
            }
        }
        Double avg = DynamicBloodSugarTool.avg(sum, list.size(), 2);
        Double sdbg = DynamicBloodSugarTool.sdbg(valueList, avg);
        DynamicBloodSugarIndexBaseVO result = new DynamicBloodSugarIndexBaseVO();
        result.setAvgNum(avg);
        result.setStandardVal(sdbg);
        result.setEventCountOfHigh(highEvent - customGreaterThan);
        result.setEventCountOfLow(lowEvent - customLessThan);
        result.setEventCountOfNormal(normalEvent);
        result.setAwiTimeRateOfHigh(DynamicBloodSugarTool.tir(highEvent - customGreaterThan, totalEvent));
        result.setAwiTimeRateOfNormal(DynamicBloodSugarTool.tir(normalEvent, totalEvent));
        result.setAwiTimeRateOfLow(DynamicBloodSugarTool.tir(lowEvent - customLessThan, totalEvent));
        result.setCoefficientOfVariation(DynamicBloodSugarTool.cv(sdbg, avg));
        result.setGhb(DynamicBloodSugarTool.ghb(avg));
        result.setMeanAmplitudeOfGlycemicExcursion(DynamicBloodSugarTool.getMAGE(sdbg, itemList));
        result.setCustomLessThanRatio(DynamicBloodSugarTool.tir(customLessThan, totalEvent));
        result.setCustomGreaterThanRatio(DynamicBloodSugarTool.tir(customGreaterThan, totalEvent));
        result.setCustomGreaterThan(customGreaterThan);
        result.setCustomLessThan(customLessThan);
        result.setTotalEvent(list.size());
        return result;
    }

    /**
     * ?????????????????????????????????????????????15??????
     *
     * @param date
     * @return true ??? false ??????
     */
    private boolean checkRecordDateIsDayFirst15Minutes(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) < 15) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    private DynamicBloodSugarIndexBaseVO getDefaultDynamicBloodSugarIndexBase() {
        DynamicBloodSugarIndexBaseVO dynamicBloodSugarIndexBaseVO = new DynamicBloodSugarIndexBaseVO();
        dynamicBloodSugarIndexBaseVO.setEventCountOfLow(0);
        dynamicBloodSugarIndexBaseVO.setEventCountOfHigh(0);
        dynamicBloodSugarIndexBaseVO.setEventCountOfNormal(0);
        dynamicBloodSugarIndexBaseVO.setAwiTimeRateOfLow(0.0D);
        dynamicBloodSugarIndexBaseVO.setAwiTimeRateOfHigh(0.0D);
        dynamicBloodSugarIndexBaseVO.setAwiTimeRateOfNormal(0.0D);
        dynamicBloodSugarIndexBaseVO.setAvgNum(0.0D);
        dynamicBloodSugarIndexBaseVO.setStandardVal(0.0D);
        dynamicBloodSugarIndexBaseVO.setCoefficientOfVariation(0.0D);
        dynamicBloodSugarIndexBaseVO.setMeanAmplitudeOfGlycemicExcursion(0.0D);
        dynamicBloodSugarIndexBaseVO.setGhb(0.0D);
        return dynamicBloodSugarIndexBaseVO;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param startDt
     * @param endDt
     */
    private void checkStartAndEndDate(String startDt, String endDt) {
        if (StringUtils.isBlank(startDt)
                || StringUtils.isBlank(endDt)) {
            throw new BusinessException("???????????????????????????????????????~");
        }
        if (endDt.compareTo(startDt) < 0) {
            throw new BusinessException("????????????????????????????????????~");
        }
    }

    /**
     * ???????????????????????? ??????
     *
     * @param list
     * @param recordDt
     * @return
     */
    private DynamicBloodSugarDailySummaryVO dynamicBloodSugarDailySummaryHandler(List<DYYPBloodSugarPO> list
            , String recordDt, Integer origin, DynamicBloodSugarSettingsVO settingsVO) {
        DynamicBloodSugarDailySummaryVO result = new DynamicBloodSugarDailySummaryVO();
        DynamicBloodSugarIndexBaseVO baseVO = getDynamicBloodSugarIndexBase(list, settingsVO);
        BeanUtils.copyProperties(result, baseVO);
        result.setRecordDt(recordDt);
        result.setHighLineVal(DynamicBloodSugarConstant.HIGH_VALUE);
        result.setLowLineVal(DynamicBloodSugarConstant.LOW_VALUE);
        if (DynamicBloodSugarConstant.ORIGIN_WEB == origin) {
            result.setLineData(getDailyTrendLineData(list));
        } else if (DynamicBloodSugarConstant.ORIGIN_WECHAT == origin) {
            result.setWechatChartData(getWechatChartData(recordDt, recordDt, list));
        }

        return result;
    }

    /**
     * ???????????????sid??????
     *
     * @param base
     * @param startDt
     * @param endDt
     * @param sensorNo
     * @param type
     */
    private void statBaseHandler(DynamicBloodSugarStatBase base, String startDt, String endDt, String sensorNo, Integer type) {
        boolean exists = true;
        Integer diff = getDayDiff(startDt, endDt);
        DYYPStatisticsPOWithBLOBs dyypStatisticsPOWithBLOBs = this.dyStaticsService.getStatisticsYPParamLogOfGAPP(startDt, diff, type, sensorNo);
        if (dyypStatisticsPOWithBLOBs == null) {
            dyypStatisticsPOWithBLOBs = new DYYPStatisticsPOWithBLOBs();
            dyypStatisticsPOWithBLOBs.setSid(DaoHelper.getSeq());
            dyypStatisticsPOWithBLOBs.setSensorNo(sensorNo);
            dyypStatisticsPOWithBLOBs.setType(type.byteValue());
            dyypStatisticsPOWithBLOBs.setStartDt(DateHelper.getDate(startDt, DateHelper.DAY_FORMAT));
            dyypStatisticsPOWithBLOBs.setDay(diff);
            this.dyStaticsService.addDayStatistics(dyypStatisticsPOWithBLOBs);
            exists = false;
        }
        base.setSid(dyypStatisticsPOWithBLOBs.getSid());
        if (exists) {
            DYYPStatisticsAdvicePO advice = getStatisticAdvice(dyypStatisticsPOWithBLOBs.getSid());
            if (advice != null) {
                base.setAdContent(advice.getContent());
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param sid
     * @return
     */
    private DYYPStatisticsAdvicePO getStatisticAdvice(String statisticsId) {
        return dyypStatisticsAdvicePOMapper.getStatisticAdviceByStatisticsId(statisticsId);
    }

    /**
     * ??????????????????????????????
     *
     * @param paramLogOfYPPOS
     * @return
     */
    private MultiValueMap<String, DYYPBloodSugarPO> getMultiValueMapByRecordDt(List<DYYPBloodSugarPO> paramLogOfYPPOS) {
        MultiValueMap<String, DYYPBloodSugarPO> multiValueMap = new LinkedMultiValueMap<>();
        for (DYYPBloodSugarPO item : paramLogOfYPPOS) {
            multiValueMap.add(DateHelper.getDate(item.getRecordDt(), "yyyy-MM-dd"), item);
        }
        return multiValueMap;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param list
     * @param recordDt
     * @return
     */
    private DynamicBloodSugarDynamicItemVO dynamicBloodSugarDynamicItemHandler(List<DYYPBloodSugarPO> list, String recordDt, DynamicBloodSugarSettingsVO settingsVO) {
        DynamicBloodSugarDynamicItemVO result = new DynamicBloodSugarDynamicItemVO();
        DynamicBloodSugarIndexBaseVO baseVO = getDynamicBloodSugarIndexBase(list, settingsVO);
        BeanUtils.copyProperties(result, baseVO);
        result.setRecordDt(recordDt);
        return result;
    }

    /**
     * ???????????????
     *
     * @param startDt
     * @param endDt
     * @return
     */
    private int getDayDiff(String startDt, String endDt) {
        //????????????????????????????????????????????????+1
        return DateHelper.dateCompareGetDay(endDt, startDt) + 1;
    }

    /**
     * apg ????????????????????? ??????
     *
     * @param dynamicVO
     * @param startDt
     * @param endDt
     * @param list
     */
    private void agpHandler(DynamicBloodSugarDynamicVO dynamicVO, String startDt, String endDt, List<DYYPBloodSugarPO> list) {
        int diff = getDayDiff(startDt, endDt);
        //??????5??? ???????????????
        if (diff < DynamicBloodSugarConstant.DYNAMIC_CHART_MINIMUM_DAY
                || list.size() < (DynamicBloodSugarConstant.DYNAMIC_CHART_MINIMUM_DAY * DynamicBloodSugarConstant.DAY_MAX_RECORD)) {
            dynamicVO.setShowChart(false);
            return;
        }
        //??????????????????????????????
        MultiValueMap<Integer, Double> multiValueMap = new LinkedMultiValueMap<>();
        for (DYYPBloodSugarPO item : list) {
            multiValueMap.add(DynamicBloodSugarTool.getLineDataIndexFor3(item.getRecordTime()), item.getValue().doubleValue());
        }
        MultiValueMap<Integer, String> multiValueMapDt = new LinkedMultiValueMap<>();
        for (DYYPBloodSugarPO item : list) {
            multiValueMapDt.add(DynamicBloodSugarTool.getLineDataIndexFor3(item.getRecordTime()), DateHelper.dateToString(item.getRecordTime()));
        }

        List<String> time = getChartDefaultIndexList();
        for (Map.Entry<Integer, List<String>> entry : multiValueMapDt.entrySet()) {
            Collections.sort(entry.getValue());
            time.set(entry.getKey(), time(entry.getValue()));
        }
        //agp???5??????
        List<String> per10 = getChartDefaultIndexList();
        List<String> per25 = getChartDefaultIndexList();
        List<String> per50 = getChartDefaultIndexList();
        List<String> per75 = getChartDefaultIndexList();
        List<String> per90 = getChartDefaultIndexList();

        for (Map.Entry<Integer, List<Double>> entry : multiValueMap.entrySet()) {
            Collections.sort(entry.getValue());
            per10.set(entry.getKey(), DynamicBloodSugarTool.getQuantile(entry.getValue(), 1, 10) + "");
            per25.set(entry.getKey(), DynamicBloodSugarTool.getQuantile(entry.getValue(), 1, 4) + "");
            per50.set(entry.getKey(), DynamicBloodSugarTool.getQuantile(entry.getValue(), 2, 4) + "");
            per75.set(entry.getKey(), DynamicBloodSugarTool.getQuantile(entry.getValue(), 3, 4) + "");
            per90.set(entry.getKey(), DynamicBloodSugarTool.getQuantile(entry.getValue(), 9, 10) + "");

        }
        //????????????
        List<List<String>> lowerLimitPointList = new ArrayList<>();
        //????????????
        List<List<String>> upperLimitPointList = new ArrayList<>();

        lowerLimitPointList.add(per10);
        lowerLimitPointList.add(per25);


        upperLimitPointList.add(per90);
        upperLimitPointList.add(per75);
        dynamicVO.setLowerLimitPointListDt(time);
        dynamicVO.setLowerLimitPointList(lowerLimitPointList);
        dynamicVO.setUpperLimitPointList(upperLimitPointList);
        dynamicVO.setMedianPointList(per50);
    }

    public String time(List<String> time) {
        String date = "";
        for (int i = 0; i < time.size(); i++) {
            date = time.get(0);
        }
        return date.substring(11, 16);
    }

    /**
     * ??????????????????????????????list ??????????????????96????????????
     *
     * @return
     */
    private List<String> getChartDefaultIndexList() {
        List<String> result = new LinkedList<>();
        for (int i = 0; i < DynamicBloodSugarConstant.DAY_MAX_RECORD; i++) {
            result.add("--");
        }
        return result;
    }

    /**
     * ??????????????????????????????
     *
     * @param list
     */
    private void dynamicBloodSugarRemarkHandler(List<DYYPBloodSugarPO> list) {
        for (DYYPBloodSugarPO item : list) {
            List<DyBloodSugarRemarkPO> remarkList = this.dyBloodSugarService.listBloodSugarRemarkByBid(item.getSid());
            if (remarkList != null && !remarkList.isEmpty()) {
                List<String> remarkStringList = remarkList.stream().map(DyBloodSugarRemarkPO::getContent).collect(Collectors.toList());
                item.setRemark(Joiner.on("???").join(remarkStringList));
            }
        }
    }

    /**
     * ??????????????????????????? ?????????
     *
     * @param startDt
     * @param endDt
     * @param multiValueMap
     * @return
     */
    private DynamicBloodSugarMeanAbsoluteDeviationItemVO dynamicBloodSugarMeanAbsoluteDeviationItemHandler(String yesterday
            , String today, MultiValueMap<String, DYYPBloodSugarPO> multiValueMap) {
        Double yesterdayAvg = getDayBloodSugarAvg(multiValueMap.get(yesterday));
        Double todayAvg = getDayBloodSugarAvg(multiValueMap.get(today));
        DynamicBloodSugarMeanAbsoluteDeviationItemVO result = new DynamicBloodSugarMeanAbsoluteDeviationItemVO();
        result.setToday(today);
        result.setYesterday(yesterday);
        result.setValue(new BigDecimal(Math.abs(todayAvg - yesterdayAvg)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        return result;
    }

    /**
     * ??????????????????????????????
     *
     * @param list
     * @return
     */
    private Double getDayBloodSugarAvg(List<DYYPBloodSugarPO> list) {
        double sum = 0D;
        for (DYYPBloodSugarPO item : list) {
            sum += item.getValue().doubleValue();
        }
        return DynamicBloodSugarTool.avg(sum, list.size(), 2);
    }

    /**
     * ???????????????????????????x?????????
     *
     * @param startDt
     * @param endDt
     * @param defaultCount
     * @return
     */
    private List<String> getHChartXList(String startDt, String endDt, int defaultCount) {
        int diffDay = DateHelper.dateCompareGetDay(endDt, startDt) + 1;
        Long times = diffDay * 24 * 60 * 60 * 1000L;
        Long interval = times / defaultCount;
        return setHChartXList(startDt, interval, defaultCount + 1);
    }

    private List<String> setHChartXList(String startDt, Long interval, int xCount) {
        List<String> list = new ArrayList<String>(xCount);
        Date date = DateHelper.getDate(startDt, DateHelper.DAY_FORMAT);
        Long startTimes = date.getTime();
        for (int i = 0; i < xCount; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(startTimes);
            String dd = DateHelper.getDate(calendar.getTime(), DateHelper.DATETIME_FORMAT);
            list.add(dd);
            Long xTimes = startTimes + interval;
            startTimes = xTimes;
        }
        return list;
    }

    private List<Object> getPointListItem(String dateType, List<String> xlist, DYYPBloodSugarPO data) {
        DecimalFormat df = new DecimalFormat("#0.00");
        List<Object> item = new ArrayList<Object>(2);
        Date date = data.getRecordTime();
        Double x = convertDateToXNumber(dateType, xlist, DateHelper.getDate(date, DateHelper.DATETIME_FORMAT));
        item.add(Double.parseDouble(df.format(x)));
        item.add(Double.parseDouble(df.format(data.getValue())));
        return item;
    }

    private Double convertDateToXNumber(String dateType, List<String> xlist, String recordDt) {
        DecimalFormat df = new DecimalFormat("#.00");
        String strH = "h";
        if (strH.equals(dateType)) {

            for (int i = 0; i < xlist.size(); i++) {
                String listItem1 = xlist.get(i);
                String listItem2 = (xlist.size() > (i + 1)) ? xlist.get(i + 1) : listItem1;

                Date date1 = DateHelper.getDate(listItem1, DateHelper.DATETIME_FORMAT);
                Date date2 = DateHelper.getDate(listItem2, DateHelper.DATETIME_FORMAT);
                Date recordDate = DateHelper.getDate(recordDt, DateHelper.DATETIME_FORMAT);

                Long times1 = date1.getTime();
                Long times2 = date2.getTime();
                Long recordTimes = recordDate.getTime();

                if (times1 < recordTimes && recordTimes < times2) {
                    Long xx = times2 - times1;
                    Long x = recordTimes - times1;
                    Double dX = Double.parseDouble(x + "");
                    Double dd2 = Double.parseDouble(df.format(dX / xx));
                    return i + dd2;
                } else if (recordTimes.equals(times1)) {
                    return Double.parseDouble(i + "");
                } else if (recordTimes.equals(times2)) {
                    return Double.parseDouble(i + 1 + "");
                }
            }
        } else {
            for (int i = 0; i < xlist.size(); i++) {
                String listItem1 = xlist.get(i);
                String listItem2 = (xlist.size() > (i + 1)) ? xlist.get(i + 1) : listItem1;

                Date date1 = DateHelper.getDate(listItem1, DateHelper.DAY_FORMAT);
                Date date2 = DateHelper.getDate(listItem2, DateHelper.DAY_FORMAT);
                Date recordDate = DateHelper.getDate(recordDt, DateHelper.DAY_FORMAT);

                Long times1 = date1.getTime();
                Long times2 = date2.getTime();
                Long recordTimes = recordDate.getTime();

                if (times1 < recordTimes && recordTimes < times2) {
                    Long xx = times2 - times1;
                    Long x = recordTimes - times1;
                    Double dX = Double.parseDouble(x + "");
                    Double dd2 = Double.parseDouble(df.format(dX / xx));
                    return i + dd2;
                } else if (recordTimes.equals(times1)) {
                    return Double.parseDouble(i + "");
                }
            }
        }
        return null;
    }

    /**
     * ????????????????????????
     *
     * @param startDt
     * @param endDt
     * @param list
     * @return
     */
    private List<List<Object>> getWechatChartData(String startDt, String endDt, List<DYYPBloodSugarPO> list) {
        List<String> xArea = this.getHChartXList(startDt, endDt, 4);
        //???????????????
        List<List<Object>> pointList = new ArrayList<>();
        for (DYYPBloodSugarPO item : list) {
            BigDecimal value = item.getValue();
            Byte level = item.getLevel();
            if (value == null) {
                continue;
            }
            //?????????[x,y,s,k] s?????? 1?????? 3?????? 5?????? k:??????????????????
            List<Object> pointListItem = this.getPointListItem("h", xArea, item);
            pointListItem.add(level);
            pointListItem.add(item.getSid());
            pointList.add(pointListItem);
        }
        return pointList;
    }

    /**
     * apg ????????????????????? ?????? (??????????????????)
     *
     * @param dynamicVO
     * @param startDt
     * @param endDt
     * @param list
     */
    private void agpWechatChartHandler(DynamicBloodSugarDynamicVO dynamicVO, String startDt, String endDt, List<DYYPBloodSugarPO> list) {
        int diff = getDayDiff(startDt, endDt);
        //??????5??? ???????????????
        if (diff < DynamicBloodSugarConstant.DYNAMIC_CHART_MINIMUM_DAY
                || list.size() < (DynamicBloodSugarConstant.DYNAMIC_CHART_MINIMUM_DAY * DynamicBloodSugarConstant.DAY_MAX_RECORD)) {
            dynamicVO.setShowChart(false);
            return;
        }
        //??????????????????????????????
        MultiValueMap<Integer, DYYPBloodSugarPO> multiValueMap = new LinkedMultiValueMap<>();
        for (DYYPBloodSugarPO item : list) {
            multiValueMap.add(DynamicBloodSugarTool.getLineDataIndexFor3(item.getRecordTime()), item);
        }
        multiValueMap.get(0);
        //agp???5??????
        List<List<Object>> per10 = new ArrayList<>();
        List<List<Object>> per25 = new ArrayList<>();
        List<List<Object>> per50 = new ArrayList<>();
        List<List<Object>> per75 = new ArrayList<>();
        List<List<Object>> per90 = new ArrayList<>();

        List<String> xArea = this.getHChartXList(endDt, endDt, 4);
        List<DYYPBloodSugarPO> mapList = null;
        for (int i = 0; i < DynamicBloodSugarConstant.DAY_MAX_RECORD - 1; i++) {
            mapList = multiValueMap.get(i);
            if (mapList != null) {
                List<Double> valueList = covertDoubleList(mapList);
                Collections.sort(valueList);
                Date recordTime = mapList.get(0).getRecordTime();

                double per10Value = DynamicBloodSugarTool.getQuantile(valueList, 1, 10);
                double per25Value = DynamicBloodSugarTool.getQuantile(valueList, 1, 4);
                double per50Value = DynamicBloodSugarTool.getQuantile(valueList, 2, 4);
                double per75Value = DynamicBloodSugarTool.getQuantile(valueList, 3, 4);
                double per90Value = DynamicBloodSugarTool.getQuantile(valueList, 9, 10);

                addValueToAgpList(per10, recordTime, per10Value, xArea, endDt);
                addValueToAgpList(per25, recordTime, per25Value, xArea, endDt);
                addValueToAgpList(per50, recordTime, per50Value, xArea, endDt);
                addValueToAgpList(per75, recordTime, per75Value, xArea, endDt);
                addValueToAgpList(per90, recordTime, per90Value, xArea, endDt);
            }
        }

        dynamicVO.setPer5Point(per10);
        dynamicVO.setPer25Point(per25);
        dynamicVO.setPer50Point(per50);
        dynamicVO.setPer75Point(per75);
        dynamicVO.setPer90Point(per90);
    }

    /**
     * ????????????????????????????????????double???????????????
     *
     * @param list
     * @return
     */
    private List<Double> covertDoubleList(List<DYYPBloodSugarPO> list) {
        List<Double> result = new ArrayList<>();
        for (DYYPBloodSugarPO item : list) {
            result.add(item.getValue().doubleValue());
        }
        return result;
    }

    /**
     * ??????????????????
     *
     * @param recordTime
     * @param value
     * @return
     */
    private DYYPBloodSugarPO assembleDYYPBloodSugar(Date recordTime, double value, String endDt) {
        DYYPBloodSugarPO result = new DYYPBloodSugarPO();
        //??????x???????????? + ???????????????????????????????????????????????????????????????????????????
        String[] arr = endDt.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(recordTime);
        calendar.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[2]));
        result.setValue(new BigDecimal(value));
        result.setRecordTime(calendar.getTime());
        return result;
    }

    /**
     * ???agp??????????????????
     *
     * @param list
     * @param recordTime
     * @param value
     * @param xArea
     */
    private void addValueToAgpList(List<List<Object>> list, Date recordTime, double value, List<String> xArea, String endDt) {
        List<Object> pointListItem1 = this.getPointListItem("h", xArea, assembleDYYPBloodSugar(recordTime, value, endDt));
        list.add(pointListItem1);
    }

    /**
     * ??????mage?????????????????????
     *
     * @param itemList
     * @param po
     */
    private void addMAGEItem(List<DynamicBloodSugarMAGEItemBO> itemList, DYYPBloodSugarPO po) {
        DynamicBloodSugarMAGEItemBO item = new DynamicBloodSugarMAGEItemBO();
        item.setTimestamp(po.getRecordTime().getTime());
        item.setValue(po.getValue().doubleValue());
        itemList.add(item);
    }

    /**
     * ????????????????????????
     *
     * @param sensorNo
     * @param startDt
     * @param endDt
     * @return
     */
    private List<DYYPBloodSugarPO> loadDYYPBloodSugarData(String sensorNo, String startDt, String endDt) {
        checkStartAndEndDate(startDt, endDt);
        return this.dyBloodSugarService.listDataSourceOfYPParamLogOfOBDTASC(startDt
                , endDt, sensorNo);
    }

    /**
     * ????????????????????????
     *
     * @param sensorNo
     * @param startDt
     * @param endDt
     * @return
     */
    private List<DYYPBloodSugarPO> loadWechatDYYPBloodSugarData(String sensorNo, String startDt, String endDt) {
        checkStartAndEndDate(startDt, endDt);
        return this.dyBloodSugarService.listDataWechatSourceOfYPParamLogOfOBDTASC(startDt
                , endDt, sensorNo);
    }

    /**
     * ????????????????????????
     *
     * @param startDt
     * @param endDt
     * @return
     */
    private boolean checkSameDay(String startDt, String endDt) {
        return startDt.substring(0, 10).equals(endDt.substring(0, 10));
    }

    /**
     * ?????? ???????????? ????????????
     *
     * @param dayDataMap
     * @param sameDay
     * @return
     */
    private List<DynamicBloodChartDayItemVO> getDailyTrendChartData(MultiValueMap<String, DYYPBloodSugarPO> dayDataMap) {
        List<DynamicBloodChartDayItemVO> result = new ArrayList<>();
        DynamicBloodChartDayItemVO item = null;
        for (Map.Entry<String, List<DYYPBloodSugarPO>> entry : dayDataMap.entrySet()) {
            item = new DynamicBloodChartDayItemVO();
            item.setDate(entry.getKey());
            item.setItemList(chartDataItemHandler(entry.getValue()));
            result.add(item);
        }
        //??????
        result.sort(Comparator.comparing(DynamicBloodChartDayItemVO::getDate));
        return result;
    }

    /**
     * ??????????????????????????????
     *
     * @param list
     * @param withRemark
     * @return
     */
    private List<DynamicBloodChartDataItemVO> chartDataItemHandler(List<DYYPBloodSugarPO> list) {
        List<DynamicBloodChartDataItemVO> result = new ArrayList<>();
        DynamicBloodChartDataItemVO item = null;
        for (DYYPBloodSugarPO po : list) {
            item = new DynamicBloodChartDataItemVO();
            item.setId(po.getSid());
            item.setTime(chartDataItemTimeHandler(po.getRecordTime()));
            item.setValue(po.getValue().doubleValue());
            result.add(item);
        }
        return result;
    }

    private String chartDataItemTimeHandler(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    /**
     * ?????????????????????????????????
     *
     * @param date
     * @param sensorNo
     * @return
     */
    private Double getMeanAbsoluteDifference(String date, String sensorNo, Double avgNum) {
        date = getTheDayBefore(date);
        List<DYYPBloodSugarPO> dataList = loadDYYPBloodSugarData(sensorNo, date + " 00:00:00", date + " 23:59:59");
        if (dataList == null || dataList.isEmpty() || dataList.size() < DynamicBloodSugarConstant.DAY_MAX_RECORD) {
            return null;
        }
        BigDecimal sum = dataList.stream().map(DYYPBloodSugarPO::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        return Math.abs(avgNum - DynamicBloodSugarTool.avg(sum.doubleValue(), dataList.size(), 2));
    }

    /**
     * ??????????????????????????????
     *
     * @param recordPO
     * @return
     */
    private DynamicBloodSugarSettingsVO castDynamicBloodSugarSettings(DyRecordSettingPO recordPO) {
        DynamicBloodSugarSettingsVO settingsVO = new DynamicBloodSugarSettingsVO();
        BeanUtils.copyProperties(settingsVO, recordPO);
        return settingsVO;
    }

    /**
     * ?????????????????????????????????
     *
     * @param chartData
     * @param dto
     */
    private void dailyTrendChartDataRemarkHandler(DynamicBloodChartDayItemVO chartData, String sensorNo) {
        List<DyBloodSugarRemarkPO> remarkList = null;
        Map<String, DyBloodSugarRemarkPO> memberRemarkMap = null;
        Map<String, DyBloodSugarRemarkPO> doctorRemarkMap = null;
        DyBloodSugarRemarkPO remarkPO = null;
        String startDt = chartData.getDate() + " 00:00:00";
        String endDt = chartData.getDate() + " 23:59:59";
        remarkList = this.dyBloodSugarService.listBloodSugarRemarkBySensorNo(sensorNo, startDt, endDt, null);
        if (remarkList == null || remarkList.isEmpty()) {
            return;
        }
        memberRemarkMap = new HashMap<>();
        doctorRemarkMap = new HashMap<>();
        for (DyBloodSugarRemarkPO remark : remarkList) {
            if (DynamicBloodSugarConstant.ORIGIN_MEMBER == remark.getOrigin()) {
                memberRemarkMap.put(remark.getBid(), remark);
            } else {
                doctorRemarkMap.put(remark.getBid(), remark);
            }
        }
        for (DynamicBloodChartDataItemVO itemVO : chartData.getItemList()) {
            remarkPO = memberRemarkMap.get(itemVO.getId());
            if (remarkPO != null) {
                itemVO.setMemberRemark(remarkPO.getContent());
            }
            remarkPO = doctorRemarkMap.get(itemVO.getId());
            if (remarkPO != null) {
                itemVO.setDoctorRemark(remarkPO.getContent());
                itemVO.setDoctorRemarkId(remarkPO.getSid());
            }
        }
    }

    /**
     * ????????????-????????????-??????????????????
     *
     * @param chartData
     * @param setting
     * @param sensorNo
     */
    private void dailyTrendChartDataDietHandler(DynamicBloodSugarChartDataDiet chartData, String memberId) {
        if (StringUtils.isBlank(memberId)) {
            return;
        }

        DietRecordDTO dietDTO = new DietRecordDTO();
        dietDTO.setMemberId(memberId);
        dietDTO.setBegin(chartData.getDate() + " 00:00:00");
        dietDTO.setEnd(chartData.getDate() + " 23:59:59");
        List<DyRememberDietPO> dietPOList = dietService.listDietRecordByDayForMiniProgram(dietDTO);

        List<DynamicBloodChartDataDietVO> dietList = castDynamicBloodChartDataDietList(dietPOList);
        chartData.setDietList(dietList);

        List<DyRememberDietPO> otherDietPOList = dietPOList.stream().filter(a -> DynamicBloodSugarConstant.OTHER_MEAL.equals(a.getParamCode())).collect(Collectors.toList());
        List<DynamicBloodChartDataDietVO> otherDietList = castDynamicBloodChartDataDietList(otherDietPOList);
        chartData.setOtherDietList(otherDietList);
    }


    /**
     * ????????????-????????????-??????????????????
     *
     * @param chartData
     * @param memberId
     */
    private void dailyTrendChartDataSportHandler(DynamicBloodSugarChartDataSport chartData, String memberId) {
        if (StringUtils.isBlank(memberId)) {
            return;
        }
        SportRecordDTO sportDTO = new SportRecordDTO();
        sportDTO.setMemberId(memberId);
        sportDTO.setBegin(chartData.getDate() + " 00:00:00");
        sportDTO.setEnd(chartData.getDate() + " 23:59:59");
        List<DyRememberSportPO> sportPOList = sportService.listSportRecordForDyBlood(sportDTO);
        if (sportPOList != null && !sportPOList.isEmpty()) {
            List<DynamicBloodChartDataSportVO> sportList = new ArrayList<>();
            DynamicBloodChartDataSportVO sportVO = null;
            for (DyRememberSportPO s : sportPOList) {
                sportVO = new DynamicBloodChartDataSportVO();
                sportVO.setStartTime(s.getSportStartDt());
                sportVO.setEndTime(s.getSportEndDt());
                sportVO.setOrigin(s.getOperationType());
                sportVO.setSportContent(sportJsonDataHandler(s.getSportMethodJson()));
                sportList.add(sportVO);
            }
            chartData.setSportList(sportList);
        }
    }

    /**
     * ????????????-????????????-??????????????????
     *
     * @param chartData
     * @param memberId
     */
    private void dailyTrendChartDataDrugHandler(DynamicBloodSugarChartDataDrug chartData, String memberId) {
        if (StringUtils.isBlank(memberId)) {
            return;
        }

        DrugsRecordDTO dto = new DrugsRecordDTO();
        dto.setMemberId(memberId);
        dto.setBegin(chartData.getDate() + " 00:00:00");
        dto.setEnd(chartData.getDate() + " 23:59:59");
        List<DrugsRecordVO> drugPOList = drugService.listDrugsRecord(dto);
        if (drugPOList != null && !drugPOList.isEmpty()) {
            List<DynamicBloodChartDataDrugVO> drugList = new ArrayList<>();
            DynamicBloodChartDataDrugVO drugVO = null;
            for (DrugsRecordVO vo : drugPOList) {
                drugVO = new DynamicBloodChartDataDrugVO();
                StringBuilder drugs = new StringBuilder();
                for (DrugsRecordPO po: vo.getDataList()){
                    if (!StringUtils.isBlank(po.getDrugName())) {
                        if(drugs.length() >0){
                            drugs.append("???");
                        }
                        drugs.append(po.getDrugName());
                    }
                }
                drugVO.setDrugContent(drugs.toString());
                drugVO.setTime(vo.getParamTime());
                drugList.add(drugVO);
            }
            chartData.setDrugList(drugList);
        }
    }

    /**
     * ????????????-????????????-??????????????????
     *
     * @param chartData
     * @param setting
     * @param memberId
     */
    private void dailyTrendChartDataWorkRestHandler(DynamicBloodSugarChartDataWorkRest chartData, DyRecordSettingPO setting, String memberId) {
        //??????????????????
        setDefaultWorkRest(chartData, setting);

        //??????

        DietRecordDTO dietDTO = new DietRecordDTO();
        dietDTO.setMemberId(memberId);
        dietDTO.setBegin(chartData.getDate() + " 00:00:00");
        dietDTO.setEnd(chartData.getDate() + " 23:59:59");
        List<DyRememberDietPO> dietPOList = dietService.listDietRecordByDayForMiniProgram(dietDTO);

        if (dietPOList != null && !dietPOList.isEmpty()) {

            //???????????????????????????????????????
            dietPOList.sort(Comparator.comparing(DyRememberDietPO::getOperationType));

            DynamicBloodChartDataDietVO dietVO = null;
            for (DyRememberDietPO dietPO : dietPOList) {
                dietVO = new DynamicBloodChartDataDietVO();
                dietVO.setOrigin(dietPO.getOperationType());
                dietVO.setTime(dietPO.getRecordDt());
                if (StringUtils.isNullOrEmpty(dietPO.getContent())) {
                    dietVO.setDietContent(dietJsonDataHandler(dietPO.getParamIngredientJson()));
                } else {
                    dietVO.setDietContent(dietJsonDataHandlerForWx(dietPO.getContent()));
                }

                if (DynamicBloodSugarConstant.BREAKFAST.equals(dietPO.getParamCode())) {
                    chartData.setBreakfastTime(dietVO);
                } else if (DynamicBloodSugarConstant.LUNCH.equals(dietPO.getParamCode())) {
                    chartData.setLunchTime(dietVO);
                } else if (DynamicBloodSugarConstant.DINNER.equals(dietPO.getParamCode())) {
                    chartData.setDinnerTime(dietVO);
                }
            }
        }

        //??????
        DyRememberSleepDTO sleepDTO = new DyRememberSleepDTO();
        sleepDTO.setMemberId(memberId);
        sleepDTO.setRecordDtStart(chartData.getDate() + " 00:00:00");
        sleepDTO.setRecordDtEnd(chartData.getDate() + " 23:59:59");
        List<DyRememberSleepPO> sleepPO = this.dyRememberService.getSleepRemember(sleepDTO);
        if (sleepPO != null && !sleepPO.isEmpty()) {
            DyRememberSleepPO sleepPO1 = sleepPO.get(0);
            DynamicBloodChartDataDietVO dietVO = new DynamicBloodChartDataDietVO();
            dietVO.setTime(sleepPO1.getSleepDt());
            dietVO.setDietContent(sleepPO1.getSleepRemark());
            chartData.setSleepTime(dietVO);
        }
    }

    /**
     * ???????????????????????? ??????
     *
     * @param list
     * @param recordDt
     * @return
     */
    private DynamicBloodSugarDailySummaryV2VO dynamicBloodSugarDailySummaryHandlerV2(List<DYYPBloodSugarPO> list
            , String recordDt, DyRecordSettingPO recordPO, String memberId) {
        DynamicBloodSugarDailySummaryV2VO result = new DynamicBloodSugarDailySummaryV2VO();
        result.setDate(recordDt);

        DynamicBloodSugarSettingsVO settingsVO = castDynamicBloodSugarSettings(recordPO);

        DynamicBloodSugarIndexBaseVO baseVO = getDynamicBloodSugarIndexBase(list, settingsVO);
        BeanUtils.copyProperties(result, baseVO);

        List<DynamicBloodChartDataItemVO> itemList = chartDataItemHandler(list);
        result.setItemList(itemList);

        dailyTrendChartDataWorkRestHandler(result, recordPO, memberId);

        dailyTrendChartDataDrugHandler(result, memberId);

        dailyTrendChartDataSportHandler(result, memberId);

        dailyTrendChartDataDietHandler(result, memberId);

        dailyTrendChartDataLifeTypetHandler(result, memberId);

        //?????????????????????
        result.setMorningAvgAndArea(DynamicBloodSugarTool.summaryAvgAndAreaHandler(
                result.getBreakfastTime().getTime()
                , recordPO.getBloodSugarMin()
                , recordPO.getBloodSugarMax()
                , list));
        result.setNoonAvgAndArea(DynamicBloodSugarTool.summaryAvgAndAreaHandler(
                result.getLunchTime().getTime()
                , recordPO.getBloodSugarMin()
                , recordPO.getBloodSugarMax()
                , list));
        result.setNightAvgAndArea(DynamicBloodSugarTool.summaryAvgAndAreaHandler(
                result.getDinnerTime().getTime()
                , recordPO.getBloodSugarMin()
                , recordPO.getBloodSugarMax()
                , list));

        //??????
        List<DyBloodSugarRemarkPO> remarkPOList = this.dyBloodSugarService.listBloodSugarRemarkBySensorNo(list.get(0).getSensorNo()
                , recordDt + " 00:00:00", recordDt + " 23:59:59", null);
        if (remarkPOList != null && !remarkPOList.isEmpty()) {
            List<DynamicBloodChartDataRemarkVO> remarkList = new ArrayList<>();
            DynamicBloodChartDataRemarkVO remarkVO = null;
            for (DyBloodSugarRemarkPO remarkPO : remarkPOList) {
                remarkVO = new DynamicBloodChartDataRemarkVO();
                remarkVO.setContent(remarkPO.getContent());
                remarkVO.setOrigin(remarkPO.getOrigin());
                DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getRemarkByBid(remarkPO.getBid());
                remarkVO.setTime(dyypBloodSugarPO.getRecordTime().toString());
                remarkList.add(remarkVO);
            }
            result.setRemarkList(remarkList);
        }
        return result;
    }

    /**
     * ?????????????????????
     *
     * @param workRest
     * @param settingPO
     */
    private void setDefaultWorkRest(DynamicBloodSugarChartDataWorkRest workRest, DyRecordSettingPO settingPO) {
        int defaultOrigin = 0;


        DynamicBloodChartDataDietVO dietVO = new DynamicBloodChartDataDietVO();
        dietVO.setTime(fullTime(workRest.getDate(), settingPO.getBreakfastDt()));
        dietVO.setOrigin(defaultOrigin);
        workRest.setBreakfastTime(dietVO);

        dietVO = new DynamicBloodChartDataDietVO();
        dietVO.setTime(fullTime(workRest.getDate(), settingPO.getLunchDt()));
        dietVO.setOrigin(defaultOrigin);
        workRest.setLunchTime(dietVO);

        dietVO = new DynamicBloodChartDataDietVO();
        dietVO.setTime(fullTime(workRest.getDate(), settingPO.getDinnerDt()));
        dietVO.setOrigin(defaultOrigin);
        workRest.setDinnerTime(dietVO);

        dietVO = new DynamicBloodChartDataDietVO();
        dietVO.setTime(settingPO.getSleepDt());
        dietVO.setOrigin(defaultOrigin);
        workRest.setSleepTime(dietVO);
    }

    /**
     * ?????????????????????????????????
     *
     * @param dietPOList
     * @return
     */
    private List<DynamicBloodChartDataDietVO> castDynamicBloodChartDataDietList(List<DyRememberDietPO> dietPOList) {
        if (dietPOList == null || dietPOList.isEmpty()) {
            return null;
        }
        List<DynamicBloodChartDataDietVO> result = new ArrayList<>();
        DynamicBloodChartDataDietVO dietVO = null;
        for (DyRememberDietPO dietPO : dietPOList) {
            dietVO = new DynamicBloodChartDataDietVO();
            if (StringUtils.isNullOrEmpty(dietPO.getContent())){
                dietVO.setDietContent(dietJsonDataHandler(dietPO.getParamIngredientJson()));
            }else {
                dietVO.setDietContent(dietJsonDataHandlerForWx(dietPO.getContent()));
            }

            dietVO.setTime(dietPO.getParamDt());
            dietVO.setOrigin(dietPO.getOperationType());
            result.add(dietVO);
        }
        return result;
    }

    /**
     * ????????????????????????
     *
     * @param date
     * @return
     */
    private static String getTheDayBefore(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalDate result = localDate.minusDays(1L);
        return result.toString();
    }

    /**
     * ??????json??????
     *
     * @param jsonString
     * @return
     */
    private String dietJsonDataHandler(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return "";
        }
        List<String> textList = new ArrayList<>();
        DyRememberDietBO dyRememberDietBO = null;
        try {
            dyRememberDietBO = JSON.parseObject(jsonString, DyRememberDietBO.class);
        } catch (Exception e) {
            log.warn("?????????????????????????????????????????????", e);
            return "";
        }
        List<DyRememberDietFoodBO> foodList = dyRememberDietBO.getDietFoodList();
        if (foodList != null && !foodList.isEmpty()) {
            for (DyRememberDietFoodBO food : foodList) {
                textList.add(food.getMultipleFood());
            }
        }
        if (!StringUtils.isBlank(dyRememberDietBO.getOtherFood())) {
            textList.add(dyRememberDietBO.getOtherFood());
        }
        if (textList.isEmpty()) {
            return "";
        }
        return Joiner.on("???").join(textList);
    }

    /**
     * ??????json????????????
     *
     * @param jsonString
     * @return
     */
    private String sportJsonDataHandler(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return "";
        }
        List<String> textList = new ArrayList<>();
        DyRememberSportBO bo = null;
        try {
            bo = JSON.parseObject(jsonString, DyRememberSportBO.class);
        } catch (Exception e) {
            log.warn("?????????????????????????????????????????????", e);
            return "";
        }
        List<DyRememberSportMethodBO> sportList = bo.getSportMethodBOList();
        if (sportList != null && !sportList.isEmpty()) {
            for (DyRememberSportMethodBO food : sportList) {
                textList.add(food.getSportMethod());
            }
        }
        if (bo.getOtherSport() != null && !bo.getOtherSport().isEmpty()) {
            textList.addAll(bo.getOtherSport());
        }
        if (textList.isEmpty()) {
            return "";
        }
        return Joiner.on("???").join(textList);
    }

    private String fullTime(String date, String time) {
        return date + " " + time + ":00";
    }

    /**
     * ?????????????????????????????????
     *
     * @param val1
     * @param val2
     * @return
     */
    private double meanAbsoluteDeviationHandler(double val1, double val2) {
        return new BigDecimal(Math.abs(val1 - val2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * ?????????????????????
     *
     * @param size
     * @return
     */
    private boolean checkFullRecord(int size) {
        return size >= DynamicBloodSugarConstant.DAY_MAX_RECORD - 1;
    }

    /**
     * ???????????????????????????
     * V7.2.0
     *
     * @param paramLogOfYPPOS
     * @return
     */
    private List<DynamicBloodSugarDailyTrendV2AvgTimeVO> getTimeIntervalData(List<DYYPBloodSugarPO> paramLogOfYPPOS) {
        List<DynamicBloodSugarDailyTrendV2AvgTimeVO> list = new ArrayList<>();
        MultiValueMap<String, DYYPBloodSugarPO> multiValueMap = new LinkedMultiValueMap<>();
        for (DYYPBloodSugarPO item : paramLogOfYPPOS) {
            multiValueMap.add(conversionIntervalData(item), item);
        }
        for (Map.Entry<String, List<DYYPBloodSugarPO>> entry : multiValueMap.entrySet()) {
            DynamicBloodSugarDailyTrendV2AvgTimeVO vo = new DynamicBloodSugarDailyTrendV2AvgTimeVO();
            vo.setTimeInterval(entry.getKey());
            List<DYYPBloodSugarPO> value = entry.getValue();
            if (!value.isEmpty()) {
                OptionalDouble average = value.stream().mapToDouble(s -> s.getValue().doubleValue()).average();
                vo.setValue(average.getAsDouble());
            } else {
                vo.setValue(0d);
            }
            list.add(vo);
        }
        list.sort(Comparator.comparing(DynamicBloodSugarDailyTrendV2AvgTimeVO::getTimeInterval));
        return list;
    }

    @Override
    public void getExportDynamicBloodSugarDailyData(DyStaticsDTO dto) {
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_WEB);
        List<String> dateList;
        if (!StringUtils.isBlank(dto.getDateList())) {
            dateList = Arrays.asList(dto.getDateList().split(","));
        } else {
            return;
        }
        //????????????
        List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService.listDataSourceOfYPParamLogOfOBDTASC(dateList, dto.getSensorNo());
        //???????????????
        MultiValueMap<String, DYYPBloodSugarPO> dayDataMap = getMultiValueMapByRecordDt(paramLogOfYPPOS);
        //??????????????????
        List<DynamicBloodChartDayItemVO> chartData = getDailyTrendChartData(dayDataMap);
        //???????????????????????????
        MultiValueMap<String, DYYPBloodSugarPO> dataSimulationList = getDataSimulationList(dto);
        if (!chartData.isEmpty()) {
            for (DynamicBloodChartDayItemVO chartDatum : chartData) {
                List<DynamicBloodChartDataItemVO> dynamicBloodChartDataItemVOS = DyBloodSugarInsertValTool.changeHourMinutes(chartDatum, dataSimulationList);
                //??????????????????
            }
        }

    }

    private String conversionIntervalData(DYYPBloodSugarPO item) {
        String time = DateHelper.getDate(item.getRecordTime(), "HH:mm:ss");
        if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL1)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL1;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL2)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL2;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL3)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL3;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL4)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL4;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL5)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL5;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL6)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL6;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL7)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL7;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL8)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL8;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL9)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL9;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL10)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL10;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL11)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL11;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL12)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL12;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL13)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL13;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL14)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL14;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL15)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL15;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL16)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL16;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL17)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL17;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL18)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL18;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL19)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL19;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL20)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL20;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL21)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL21;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL22)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL22;
        } else if (TimeUtil.timeIsInRound(time, DynamicBloodSugarConstant.TIME_INTERVAL23)) {
            return DynamicBloodSugarConstant.TIME_INTERVAL23;
        } else {
            return DynamicBloodSugarConstant.TIME_INTERVAL24;
        }
    }

    /**
     * ????????????????????????????????????????????????
     * V7.2.0
     *
     * @param paramLogOfYPPOS
     * @param settingsVO
     * @return
     */
    private Integer statisticsDynamicsEventCountOfLow(List<DYYPBloodSugarPO> paramLogOfYPPOS, DynamicBloodSugarSettingsVO settingsVO) {
        Integer count = 0;
        List<DYYPBloodSugarPO> eventList = new ArrayList<>();
        for (int i = 0; i < paramLogOfYPPOS.size(); i++) {
            DYYPBloodSugarPO dyypBloodSugarPO1 = paramLogOfYPPOS.get(i);
            if (dyypBloodSugarPO1.getValue().doubleValue() < settingsVO.getBloodSugarMin()) {
                eventList.add(dyypBloodSugarPO1);
                if (i < paramLogOfYPPOS.size() - 1) {
                    DYYPBloodSugarPO dyypBloodSugarPO2 = paramLogOfYPPOS.get(i + 1);
                    if (dyypBloodSugarPO2.getValue().doubleValue() > settingsVO.getBloodSugarMin()
                            && (dyypBloodSugarPO1.getRecordDt().equals(dyypBloodSugarPO2.getRecordDt())
                            || DateHelper.getOffsetDate(dyypBloodSugarPO1.getRecordDt(), 1).equals(dyypBloodSugarPO2.getRecordDt()))) {
                        eventList.add(dyypBloodSugarPO2);
                    }
                }
            }
        }
        if (!eventList.isEmpty()) {
            List<DYYPBloodSugarPO> collect = eventList.stream().filter(s -> s.getValue().doubleValue() > settingsVO.getBloodSugarMin()).collect(Collectors.toList());
            if (eventList.get(eventList.size() - 1).getValue().doubleValue() < settingsVO.getBloodSugarMin()) {
                count = collect.size() + 1;
            } else {
                count = collect.size();
            }
        }
        return count;
    }

    private MultiValueMap<String, DYYPBloodSugarPO> getDataSimulationList(DyStaticsDTO dto) {
        List<DYYPBloodSugarPO> list = dyBloodSugarService.getDataSimulationList(dto);
        MultiValueMap<String, DYYPBloodSugarPO> multiValueMap = new LinkedMultiValueMap<>();
        if (!list.isEmpty()) {
            for (DYYPBloodSugarPO s : list) {
                DYYPBloodSugarPO po = new DYYPBloodSugarPO();
                po.setRecordTime(s.getRecordTime());
                po.setValue(s.getValue());
                multiValueMap.add(DateHelper.getDate(s.getRecordDt(), "yyyy-MM-dd"), po);
            }
        }
        return multiValueMap;
    }

    /**
     * ???????????????????????????json
     *
     * @param dietJsonWx
     * @return java.lang.String
     * @author
     * @date 2021/6/29
     */
    private String dietJsonDataHandlerForWx(String dietJsonWx) {
        if (StringUtils.isNullOrEmpty(dietJsonWx)) return "";
        List<String> textList = new ArrayList<>();
        List<Map<String, String>> foodList = null;
        try {
            foodList = (List) JSON.parse(dietJsonWx);
        } catch (Exception e) {
            log.error("??????????????????????????????", e);
            return "";
        }

        if (foodList != null && !foodList.isEmpty()) {
            for (Map<String, String> food : foodList) {
                textList.add(food.get("name"));
            }
        }

        if (textList.isEmpty()) {
            return "";
        }

        return Joiner.on("???").join(textList);
    }


    public void dailyTrendChartDataLifeTypetHandler(DynamicBloodSugarDailySummaryV2VO result, String memberId){
        if (StringUtils.isNullOrEmpty(memberId)){
            return;
        }

        MemberLifeTypeDTO lifeDTO = new MemberLifeTypeDTO();
        lifeDTO.setMemberId(memberId);
        lifeDTO.setRecordDtStart(result.getDate() + " 00:00:00");
        lifeDTO.setRecordDtEnd(result.getDate() + " 23:59:59");

         List<MemberLifeTypePO> liftTypeList = this.dyRememberService.getLiftTypeList(lifeDTO);

         if (liftTypeList != null && liftTypeList.size() > 0){
             List<DynamicBloodChartDataDietVO> list = new ArrayList<>();
             DynamicBloodChartDataDietVO lifeVO = null;
             for (MemberLifeTypePO lifeTypePO : liftTypeList){
                 lifeVO = new DynamicBloodChartDataDietVO();

                 lifeVO.setDietContent(dietJsonDataHandler(lifeTypePO.getContent()));

                 lifeVO.setTime(lifeTypePO.getParamDt());
                 lifeVO.setOrigin(Integer.valueOf(lifeTypePO.getOperationType()));
                 list.add(lifeVO);
             }

             result.setLifeTypeList(list);
         }
    }

    @Override
    public Map<String, Map<String, Double>> getDynamicBloodSugarValues(DyStaticsDTO dto){
        List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService
                .listDataWechatSourceOfYPParamLogOfOBDTASC(dto.getStartDt(), dto.getEndDt(), dto.getSensorNo());
        List<DYYPBloodSugarPO> zeroAmToZeroAmDataList = null;
        if (paramLogOfYPPOS != null) {
            //?????????????????????????????????????????????????????????????????????
            Date zeroAmStartDate = DateHelper.getDate(dto.getStartDt() + " 00:00:00", DateHelper.DATETIME_FORMAT);
            Date zeroAmEndDate = DateHelper.getDate(dto.getEndDt() + " 23:59:59", DateHelper.DATETIME_FORMAT);
            zeroAmToZeroAmDataList = paramLogOfYPPOS.stream()
                    .filter(x -> x.getRecordTime().after(zeroAmStartDate) && x.getRecordTime().before(zeroAmEndDate))
                    .collect(Collectors.toList());

            Map<String, Map<String, Double>> dtDataMap = new HashMap<>();
            for (DYYPBloodSugarPO item : zeroAmToZeroAmDataList) {
                String recordTime = DateHelper.dateToString(item.getRecordTime());
                String dt = recordTime.substring(0, 10);
                if (!dtDataMap.containsKey(dt)) {
                    dtDataMap.put(dt, new HashMap<>());
                }
                dtDataMap.get(dt).put(recordTime.substring(11, 16), item.getValue().doubleValue());
            }
            return dtDataMap;
        }
        throw new BusinessException("???????????????");
    }
    /**
     * ??????????????????
     * @param dto
     * @return
     */
    @Override
    public String analysisDyBloodSugar(DyReportDTO dto) {

        DYMemberSensorPO po = this.dyMemberSensorService.getDYMemberSensorPO(dto.getSensorNo());
        if (po == null || po.getMonitoringTime() == null)
            throw new BusinessException("????????????????????????");
        Date startDate = new Date(Long.parseLong(po.getMonitoringTime()));
        String startDt = DateHelper.getDate(startDate, DateHelper.DAY_FORMAT);
        if (dto.getStartDt() == null) {
            dto.setStartDt(startDt);
        }
        if (dto.getEndDt() == null) {
            String endDt = DateHelper.plusDate(startDt, 14);
            dto.setEndDt(endDt);
        }
        //??????????????????????????????
        String endDt = DateHelper.plusDate(dto.getEndDt(), 1);
        //????????????
        List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService
                .listDataWechatSourceOfYPParamLogOfOBDTASC(dto.getStartDt(), endDt, dto.getSensorNo());
        //????????????
        List<DYYPBloodSugarPO> threeAmToThreeAmDataList = null;
        List<DYYPBloodSugarPO> zeroAmToZeroAmDataList = null;
        if (paramLogOfYPPOS != null) {
            //??????????????????3??????????????????????????????3?????????????????????????????????
            Date threeAmStartDate = DateHelper.getDate(dto.getStartDt() + " 03:00:00", DateHelper.DATETIME_FORMAT);
            Date threeAmEndDate = DateHelper.getDate(endDt + " 03:00:00", DateHelper.DATETIME_FORMAT);
            threeAmToThreeAmDataList = paramLogOfYPPOS.stream()
                    .filter(x -> x.getRecordTime().after(threeAmStartDate) && x.getRecordTime().before(threeAmEndDate))
                    .collect(Collectors.toList());
            //?????????????????????????????????????????????????????????????????????
            Date zeroAmStartDate = DateHelper.getDate(dto.getStartDt() + " 00:00:00", DateHelper.DATETIME_FORMAT);
            Date zeroAmEndDate = DateHelper.getDate(endDt + " 00:00:00", DateHelper.DATETIME_FORMAT);
            zeroAmToZeroAmDataList = paramLogOfYPPOS.stream()
                    .filter(x -> x.getRecordTime().after(zeroAmStartDate) && x.getRecordTime().before(zeroAmEndDate))
                    .collect(Collectors.toList());
        } else
            throw new BusinessException("???????????????");
        Map<String, Map<Integer, Double>> dtDataMap = new HashMap<>();
        MultiValueMap<Integer, Double> multiValueMap = new LinkedMultiValueMap<>();
        for (DYYPBloodSugarPO item : threeAmToThreeAmDataList) {
            String dt = DateHelper.dateToString(item.getRecordDt()).substring(0, 10);
            multiValueMap.add(DynamicBloodSugarTool.getLineDataIndexFor3(item.getRecordTime()), item.getValue().doubleValue());
            if (!dtDataMap.containsKey(dt)) {
                dtDataMap.put(dt, new HashMap<>());
            }
            dtDataMap.get(dt).put(DynamicBloodSugarTool.getLineDataIndexFor3(item.getRecordTime()), item.getValue().doubleValue());
        }
        if (multiValueMap.size() < DynamicBloodSugarConstant.DYNAMIC_CHART_MINIMUM_DAY) {
            throw new BusinessException("????????????????????????");
        }
        //agp???5??????
        Map<Integer, Double> per10 = new HashMap<>();
        Map<Integer, Double> per25 = new HashMap<>();
        Map<Integer, Double> per50 = new HashMap<>();
        Map<Integer, Double> per75 = new HashMap<>();
        Map<Integer, Double> per90 = new HashMap<>();

        for (Map.Entry<Integer, List<Double>> entry : multiValueMap.entrySet()) {
            Collections.sort(entry.getValue());
            per10.put(entry.getKey(), DynamicBloodSugarTool.getQuantile(entry.getValue(), 1, 10));
            per25.put(entry.getKey(), DynamicBloodSugarTool.getQuantile(entry.getValue(), 1, 4));
            per50.put(entry.getKey(), DynamicBloodSugarTool.getQuantile(entry.getValue(), 2, 4));
            per75.put(entry.getKey(), DynamicBloodSugarTool.getQuantile(entry.getValue(), 3, 4));
            per90.put(entry.getKey(), DynamicBloodSugarTool.getQuantile(entry.getValue(), 9, 10));
        }


        MultiValueMap<Integer, String> dtMap = new LinkedMultiValueMap<>();
        for (DYYPBloodSugarPO item : threeAmToThreeAmDataList) {
            dtMap.add(DynamicBloodSugarTool.getLineDataIndexFor3(item.getRecordTime()), DateHelper.dateToString(item.getRecordTime()));
        }
        Map<Integer, String> times = new HashMap<>();
        for (Map.Entry<Integer, List<String>> entry : dtMap.entrySet()) {
            Collections.sort(entry.getValue());
            times.put(entry.getKey(), time(entry.getValue()));
        }

        //????????????
        DyRecordSettingPO settingRecordPO = this.dyMemberSettingService.getSettingValues(dto.getSensorNo());
        DynamicBloodSugarSettingsVO settingsVO = castDynamicBloodSugarSettings(settingRecordPO);

        DynamicBloodSugarIndexBaseVO base = getDynamicBloodSugarIndexBase(zeroAmToZeroAmDataList, settingsVO);
        Map<String, Map<Integer, String>> eatTime = eatingTimeAnalysis(dtDataMap, times);
        DyBloodSugarReportPO reportPO = new DyBloodSugarReportPO();
        reportPO.setSid(DaoHelper.getSeq());
        reportPO.setDoctorId(dto.getDoctorId());
        reportPO.setSensorNo(dto.getSensorNo());
        reportPO.setStartDt(dto.getStartDt());
        reportPO.setEndDt(dto.getEndDt());
        StringBuilder report = new StringBuilder();
        int count = 1;
        Map<Integer, Double> lowPoints = getHypoglycemiaPoint(per10);
        List<String> result = hypoglycemiaAnalysis(times, lowPoints, eatTime);
        for (String s : result) {
            if (!s.equals("")) {
                if (count != 1)
                    report.append("\n");
                report.append(count++).append("???").append(s);
            }
        }
        if (base.getAwiTimeRateOfNormal() < DynamicBloodSugarConstant.TIR_VALUE
                || base.getAwiTimeRateOfHigh() > DynamicBloodSugarConstant.TBR_VALUE
                || base.getAwiTimeRateOfLow() > DynamicBloodSugarConstant.TDR_VALUE) {
            String hyperglycemia = hyperglycemiaAnalysis(per90, times, eatTime);
            if (!hyperglycemia.equals("")) {
                if (count != 1)
                    report.append("\n");
                report.append(count++).append("???").append(hyperglycemia);
            }
            String per50Result = per50FluctuationAnalysis(per50, times, eatTime);
            if (!per50Result.equals("")) {
                if (count != 1)
                    report.append("\n");
                report.append(count++).append("???").append(per50Result);
            }
            String iqrResult = iqrOrIDRFluctuationAnalysis(1, per25, per75, times, eatTime);
            if (!iqrResult.equals("")) {
                if (count != 1)
                    report.append("\n");
                report.append(count++).append("???").append(iqrResult);
            }
            String idrResult = iqrOrIDRFluctuationAnalysis(2, per10, per90, times, eatTime);
            if (!idrResult.equals("")) {
                if (count != 1)
                    report.append("\n");
                report.append(count++).append("???").append(idrResult);
            }
        }

        String tirResult = tirAnalysis(base, settingsVO, per50, multiValueMap, times);
        if (!tirResult.equals("")) {
            if (count != 1)
                report.append("\n");
            report.append(count).append("???").append(tirResult);
        }
        reportPO.setDetails(report.toString());
        dyBloodSugarReportMapper.addReport(reportPO);
        return reportPO.getSid();
    }


    private Map<String, Map<Integer, String>> eatingTimeAnalysis(Map<String, Map<Integer, Double>> data, Map<Integer, String> times) {
        Map<String, Map<Integer, String>> eatTimes = new HashMap<>();
        for (Map.Entry<String, Map<Integer, Double>> entry : data.entrySet()) {
            Map<Integer, String> value = eatingTimes(entry.getValue(), times);
            eatTimes.put(entry.getKey(), value);
        }
        return eatTimes;
    }

    private String castTimeToChinese(String time){
        try {
            int h = Integer.parseInt(time.substring(0,2));
            int m = Integer.parseInt(time.substring(3, 5));
            if(m >= 45) h++;
            if( 15 <= m && 45 > m){
                return String.format("%d??????", h);
            }else {
                return String.format("%d???", h);
            }
        }catch (Exception ignored){

        }
        return "";
    }


    private String getTimeDesc(String time, Map<String, Map<Integer, String>> eatTime) {
        Map<String, Integer> map = new HashMap<>();
        for (String key : eatTime.keySet()
        ) {
            String desc = getTimeDescString(time, eatTime.get(key).get(1), eatTime.get(key).get(2), eatTime.get(key).get(3));
            if (map.containsKey(desc)) {
                map.put(desc, map.get(desc) + 1);
            } else {
                map.put(desc, 1);
            }
        }
        int max = -1;
        String result = "";
        for (String key : map.keySet()
        ) {
            if (map.get(key) > max) {
                if (!key.equals("")) {
                    result = key;
                    max = map.get(key);
                }
            }
        }
        return result;
    }

    /**
     * ???????????????????????????
     */
    private String getTimeDescString(String time, String brTime, String luTime, String diTime) {

        String beforeBr = null;
        if (brTime != null)
            beforeBr = DateHelper.getHourAddHour(brTime, -1);
        String beforeLu = null;
        if (luTime != null)
            beforeLu = DateHelper.getHourAddHour(luTime, -1);
        String beforeDr = null;
        if (diTime != null)
            beforeDr = DateHelper.getHourAddHour(diTime, -1);
        //?????????????????????????????????
        if (beforeBr != null && DateHelper.hourAfter(beforeBr, time) && DateHelper.hourAfter(time, brTime)) {
            return "?????????";
        }
        //???????????????????????????
        if (brTime != null && beforeLu != null && DateHelper.hourAfter(brTime, time)
                && DateHelper.hourAfter(time, beforeLu)
        ) {
            return "?????????";
        }
        //????????????????????????
        if (luTime != null && DateHelper.hourAfter(beforeLu, time) && DateHelper.hourAfter(time, luTime)) {
            return "?????????";
        }
        //???????????????????????????
        if (luTime != null && beforeDr != null && DateHelper.hourAfter(luTime, time)
                && DateHelper.hourAfter(time, beforeDr)
        ) {
            return "?????????";
        }
        //????????????????????????
        if (diTime != null && DateHelper.hourAfter(beforeDr, time) && DateHelper.hourAfter(time, diTime)) {
            return "?????????";
        }
        //?????????22:00???
        if (diTime != null && DateHelper.hourAfter(diTime, time)
                && DateHelper.hourAfter(time, "22:00")
        ) {
            return "?????????";
        }
        //???22???00-???????????????
        if (DateHelper.hourAfter("22:00", time)
                || (DateHelper.hourAfter(time, beforeBr) && beforeBr != null && DateHelper.hourAfter(time, "06:00"))
                || DateHelper.hourAfter(time, "06:00")) {
            return "??????";
        }
        return "";
    }

    /**
     * @param data  ?????????????????? {????????? ?????????}
     * @param times ????????????
     * @return
     */
    private Map<Integer, String> eatingTimes(Map<Integer, Double> data, Map<Integer, String> times) {
        Set<Integer> keys = new TreeSet<>(Comparator.naturalOrder());
        keys.addAll(data.keySet());
        Integer through = keys.iterator().next();
        Integer last = -1;
        Map<Integer, String> eatTimes = new HashMap<>();
        boolean isUp = false;
        int[] lastEat = new int[]{-1, -1, -1};
        for (Integer key : keys) {
            if (last == -1 || data.get(last) > data.get(key)) {
                if (isUp) {
                    if (data.get(last) - data.get(through) >= DynamicBloodSugarConstant.EATING_FLUCTUATION_VALUE) {
                        String eatTime = times.get(through);
                        int type = AnalysisTool.getEatingTimeType(eatTime);
                        if(type != 4){
                            if (eatTimes.containsKey(type)) {
                                if (lastEat[type - 1] == -1 || data.get(through) > data.get(lastEat[type - 1]) &&
                                        DateHelper.compareTimeHM(eatTime, eatTimes.get(type)) > 0) {
                                    eatTimes.put(type, eatTime);
                                }
                            } else {
                                eatTimes.put(type, eatTime);
                            }
                            lastEat[type - 1] = through;
                        }
                    }
                }
                isUp = false;
                through = key;
            } else {
                isUp = true;
            }
            last = key;
        }
        return eatTimes;
    }


    /**
     * ?????????????????????
     */
    private Map<Integer, Double> getHypoglycemiaPoint(Map<Integer, Double> per10) {
        Map<Integer, Double> lowPoint = new HashMap<>();
        Map<Integer, Double> tmp = new HashMap<>();
        boolean flag = false;
        boolean isSecond = false;
        for (int i = 0; i < 96; i++) {
            if (!per10.containsKey(i))
                continue;
            Double value = per10.get(i);
            if (value < DynamicBloodSugarConstant.FIRST_LOW_VALUE) {
                if (!isSecond)
                    tmp.put(i, value);
                if (!flag) {
                    flag = true;
                }
                if (value < DynamicBloodSugarConstant.SECOND_LOW_VALUE) {
                    if (!isSecond) {
                        tmp.clear();
                        isSecond = true;
                    }
                    tmp.put(i, value);
                }
            } else {
                if (flag) {
                    lowPoint.putAll(tmp);
                    tmp.clear();
                    flag = false;
                    isSecond = false;
                }
            }
        }
        if(flag){
            lowPoint.putAll(tmp);
        }
        return lowPoint;
    }

    /**
     * ???????????????
     */
    private List<String> hypoglycemiaAnalysis(Map<Integer, String> times, Map<Integer, Double> lowPoints, Map<String, Map<Integer, String>> eatTimes) {
        List<String> result = new ArrayList<>();
        int last = -100;
        int start = -1;
        double min = 0;
        List<Integer> keys = new ArrayList<>(lowPoints.keySet());
        Collections.sort(keys);
        Map<String, String> timeStr = new HashMap<>();
        int count = 0;
        for (Integer i : keys) {
            count++;
            if (last + 1 != i) {
                if (start != -1) {
                    String t = getTimeDesc(times.get(start), eatTimes);
                    String tt = "";
                    if (times.get(start).equals(times.get(last))) {
                        tt = times.get(start);
                    } else {
                        tt = times.get(start) + "-" + times.get(last);
                    }
                    if (timeStr.containsKey(t))
                        timeStr.put(t, timeStr.get(t) + "???" + tt);
                    else
                        timeStr.put(t, tt);
                }
                min = lowPoints.get(i);
                start = last = i;
            }
            if (count == keys.size()) {
                if (start != -1) {
                    String t = getTimeDesc(times.get(start), eatTimes);
                    String tt = "";
                    if (times.get(start).equals(times.get(last))) {
                        tt = times.get(start);
                    } else {
                        tt = times.get(start) + "-" + times.get(last);
                    }
                    if (timeStr.containsKey(t))
                        timeStr.put(t, timeStr.get(t) + "???" + tt);
                    else
                        timeStr.put(t, tt);
                }
            }
            if (lowPoints.get(i) < min)
                min = lowPoints.get(i);
            last = i;
        }
        for (String key : timeStr.keySet()
        ) {
            result.add(getHypoglycemiaReport(key, timeStr.get(key), min));
        }

        return result;
    }

    /**
     * ?????????????????????
     */
    private String getHypoglycemiaReport(String timeStr, String t, double min) {
        String firstHint = "??????%s?????????????????????????????????%s;";
        String secondHint = "??????%s???????????????????????????????????????%s???????????????????????????;";
        StringBuilder report = new StringBuilder();
        if (min < DynamicBloodSugarConstant.SECOND_LOW_VALUE) {
            report.append(String.format(secondHint, timeStr + t, String.format("%.2f mmol/l", min)));
        } else {
            report.append(String.format(firstHint, timeStr + t, String.format("%.2f mmol/l", min)));
        }
        if (timeStr.contains("??????"))
            report.append("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        if (timeStr.contains("??????"))
            report.append("?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        return report.toString();
    }

    /**
     * ????????????????????????
     */
    private String tirAnalysis(DynamicBloodSugarIndexBaseVO baseVO, DynamicBloodSugarSettingsVO settingsVO, Map<Integer, Double> per50, MultiValueMap<Integer, Double> values, Map<Integer, String> times) {
        StringBuilder tirStr = new StringBuilder();
        if (baseVO.getAwiTimeRateOfNormal() >= DynamicBloodSugarConstant.TIR_VALUE) {
            if (baseVO.getAwiTimeRateOfHigh() > DynamicBloodSugarConstant.TBR_VALUE) {
                tirStr.append(String.format("????????????????????????????????????????????????10.0mmol/L????????????%.2f%%????????????????????????", baseVO.getAwiTimeRateOfHigh()));
                tirStr.append(tirDetailsAnalysis(1, settingsVO.getBloodSugarMaxAfter(), per50, times));
            } else if (baseVO.getAwiTimeRateOfLow() > DynamicBloodSugarConstant.TDR_VALUE) {
                tirStr.append(String.format("????????????????????????????????????????????????3.9mmol/L????????????%.2f%%????????????????????????", baseVO.getAwiTimeRateOfLow()));
                tirStr.append(tirDetailsAnalysis(2, settingsVO.getBloodSugarMin(), per50, times));
            } else {
                tirStr.append("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
            }
        } else {
            if (baseVO.getAwiTimeRateOfHigh() > DynamicBloodSugarConstant.TBR_VALUE) {
                tirStr.append(String.format("??????????????????????????????????????????10.0mmol/L????????????%.2f%%????????????????????????", baseVO.getAwiTimeRateOfHigh()));
                tirStr.append(tirDetailsAnalysis(1, settingsVO.getBloodSugarMaxAfter(), per50, times));
            }
            if (baseVO.getAwiTimeRateOfLow() > DynamicBloodSugarConstant.TDR_VALUE) {
                tirStr.append(String.format("??????????????????????????????????????????3.9mmol/L????????????%.2f%%????????????????????????", baseVO.getAwiTimeRateOfLow()));
                tirStr.append(tirDetailsAnalysis(2, settingsVO.getBloodSugarMin(), per50, times));
            }

        }
        return tirStr.toString();
    }

    /**
     * tir ???????????????????????????????????????
     *
     * @param type   1??? ??? 2 ???
     * @param target ?????????
     * @param per50  ?????????
     * @param times  ??????
     * @return ????????????
     */
    private String tirDetailsAnalysis(int type, double target, Map<Integer, Double> per50, Map<Integer, String> times) {
        int start = -1;
        int last = -1;
        boolean flag = false;
        List<String> periods = new ArrayList<>();
        int key = -1;
        for (int i = 0; i < times.size(); i++) {
            if (!per50.containsKey(i))
                continue;
            key = i;
            if ((per50.get(key) > target && type == 1) ||
                    (per50.get(key) < target && type == 2)) {
                if (!flag) {
                    start = key;

                    flag = true;
                }
            } else {
                if (flag) {
                    if (last - start >= 3) {
                        periods.add(times.get(start) + "-" + times.get(last));
                    }
                    flag = false;
                }
            }
            last = key;
        }
        String output = "";
        if (periods.size() > 0) {
            StringBuilder periodsStr = new StringBuilder();
            for (int i = 0; i < periods.size(); i++) {
                if (periodsStr.length() > 0) {
                    if (1 + i == periods.size()) {
                        periodsStr.append("???");
                    } else {
                        periodsStr.append("???");
                    }
                }
                periodsStr.append(periods.get(i));
            }
            output = periodsStr.toString();
            if (type == 1) {
                output += "?????????????????????????????????????????????";
            } else {
                output += "?????????????????????????????????????????????";
            }

        }
        return output;
    }

    /**
     * ?????????????????????
     *
     * @param per50
     * @param times
     * @param eatTimes
     * @return
     */
    private String per50FluctuationAnalysis(Map<Integer, Double> per50, Map<Integer, String> times, Map<String, Map<Integer, String>> eatTimes) {
        int start = -1;
        int last = -1;
        boolean isUp = false;
        StringBuilder timeStr = new StringBuilder();
        for (Integer key : per50.keySet()) {
            if (last == -1 || per50.get(key) < per50.get(last)) {
                if (isUp) {
                    double tmp = per50.get(last) - per50.get(start);
                    if (tmp > DynamicBloodSugarConstant.PER_50_FLUCTUATION_VALUE) {
                        String t1 = DateHelper.getHourAddHour(times.get(start), 1);
                        String t2 = times.get(last);
                        if (DateHelper.hourAfter(t1, t2)) {
                            String t = getTimeDesc(times.get(last), eatTimes);
                            if (timeStr.indexOf(t) == -1) {
                                if (t.length() > 0 && timeStr.length() > 0) {
                                    timeStr.append("???");
                                }
                                timeStr.append(t);
                            }
                        }
                    }
                    isUp = false;
                }
                start = key;
            } else {
                isUp = true;
            }
            last = key;
        }
        String result = "";
        if (timeStr.length() > 0)
            result += "?????????????????????????????????????????????" + timeStr
                    + "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????";

        return result;
    }

    /**
     * IQR IDR ??????
     *
     * @param type     1 IQR  2 IDR
     * @param perLow
     * @param perHigh
     * @param times
     * @param eatTimes
     * @return
     */
    private String iqrOrIDRFluctuationAnalysis(int type, Map<Integer, Double> perLow, Map<Integer, Double> perHigh, Map<Integer, String> times, Map<String, Map<Integer, String>> eatTimes) {
        int start = 0;
        int last = -1;
        boolean flag = false;
        StringBuilder timeStr = new StringBuilder();
        for (int i = 0; i < times.size(); i++) {
            if (perLow.containsKey(i) && perHigh.containsKey(i)) {
                double tmp = perHigh.get(i) - perLow.get(i);
                if ((type == 1 && tmp >= DynamicBloodSugarConstant.IQR_VALUE) ||
                        (type == 2 && tmp >= DynamicBloodSugarConstant.IDR_VALUE)
                ) {
                    if (!flag) {
                        start = i;
                        flag = true;
                    }
                } else {
                    if (flag) {
                        String t1 = DateHelper.getHourAddHour(times.get(start), 1);
                        String t2 = times.get(last);
                        if (DateHelper.hourAfter(t1, t2)) {
                            String s = getTimeDesc(times.get(start), eatTimes);
                            if (!timeStr.toString().contains(s)) {
                                if ( timeStr.length() > 0)
                                    timeStr.append("???");
                                timeStr.append(s);
                            }
                        }
                        flag = false;
                    }
                }
                last = i;
            }
        }
        if (flag) {
            String t1 = DateHelper.getHourAddHour(times.get(start), 1);
            String t2 = times.get(last);
            if (DateHelper.hourAfter(t1, t2)) {
                String s = getTimeDesc(times.get(start), eatTimes);
                if (!timeStr.toString().contains(s)) {
                    if ( timeStr.length() > 0)
                        timeStr.append("???");
                    timeStr.append(s);
                }
            }
        }
        String result = "";
        if (flag) {
            result = "???????????????????????????,";
            if (type == 1) {
                result += timeStr + "??????????????????????????????????????????????????????????????????????????????????????????";
            } else {
                result += timeStr + "?????????????????????????????????????????????????????????????????????????????????????????????????????????";
            }
        }
        return result;
    }

    /**
     * ???????????????
     * @param perHigh
     * @param times
     * @param eatTimes
     * @return
     */
    private String hyperglycemiaAnalysis(Map<Integer, Double> perHigh, Map<Integer, String> times, Map<String, Map<Integer, String>> eatTimes) {
        List<Integer> keys = new ArrayList<>(perHigh.keySet());
        Collections.sort(keys);
        boolean flag = false;
        StringBuilder timeStr = new StringBuilder();
        double max = 0;
        for (Integer key : keys
        ) {
            double value = perHigh.get(key);
            if (value > DynamicBloodSugarConstant.HYPERGLYCEMIA_VALUE) {
                flag = true;
                if (max < value)
                    max = value;
                String s = getTimeDesc(times.get(key), eatTimes);
                if (timeStr.indexOf(s) == -1) {
                    if (-1 == timeStr.indexOf(s) && !s.equals("") && !timeStr.toString().equals(""))
                        timeStr.append("???");
                    timeStr.append(s);
                }
            }
        }
        String result = "";
        if (flag) {
            result = "??????%s???????????????????????????????????????%.2fmmol/L???";
            result = String.format(result, timeStr, max);
            if (timeStr.indexOf("??????") > -1) {
                result += "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????";
            }
            if (timeStr.indexOf("??????") > -1) {
                result += "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????";
            }
        }
        return result;
    }

    /**
     * ????????????
     *
     * @param dto
     * @return
     */
    @Override
    public PageResult<DyBloodSugarReportPO> getBloodSugarReportList(DyReportDTO dto, PageRequest pager) {
        PageHelper.startPage(pager.getPage(), pager.getRows());
        List<DyBloodSugarReportPO> list = dyBloodSugarReportMapper.getReportList(dto);
        return new PageResult<>(list);
    }

    /**
     * ????????????
     *
     * @param dto
     * @return
     */
    @Override
    public PageResult<DyBloodSugarReportPO> getBloodSugarReportListByMember(DyReportDTO dto, PageRequest pager) {
        PageHelper.startPage(pager.getPage(), pager.getRows());
        List<DyBloodSugarReportPO> list = dyBloodSugarReportMapper.getReportListByMember(dto);
        return new PageResult<>(list);
    }

    @Override
    public String getBloodSugarReportById(String id) {
        DyBloodSugarReportPO po = dyBloodSugarReportMapper.getReportById(id);
        if (po == null || po.getDetails() == null)
            throw new BusinessException("???????????????");
//        return JSON.parseArray(po.getDetails());
        return po.getDetails();
    }

    @Override
    public void setBloodSugarReportById(String id, String details) {
        DyBloodSugarReportPO po = dyBloodSugarReportMapper.getReportById(id);
        if (po == null)
            throw new BusinessException("???????????????");
        po.setDetails(details);
        dyBloodSugarReportMapper.updateReport(po);
    }

    @Override
    public JSONObject getBloodSugarReportCover(String reportId, String memberId) {
        DyBloodSugarReportPO po = dyBloodSugarReportMapper.getReportById(reportId);
        MemberPO member = memberService.getMemberById(memberId);
        JSONObject obj = new JSONObject();
        obj.put("memberName", member.getMemberName());
        obj.put("sex", member.getSex());
        obj.put("age", member.getAge());
        obj.put("doctor", po.getDoctorName());
        obj.put("reportDt", po.getInsertDt());
        obj.put("sensorNo", po.getSensorNo());
        obj.put("startDt", po.getStartDt());
        obj.put("endDt", po.getEndDt());
        return obj;
    }

    private Map<Integer, Double> getIdrOrIqr(Map<Integer, Double> perL, Map<Integer, Double> perH) {
        Map<Integer, Double> result = new HashMap<>();
        for (int i = 0; i < 96; i++) {
            if (perH.containsKey(i) && perL.containsKey(i)) {
                result.put(i, perH.get(i) - perL.get(i));
            }
        }
        return result;
    }

    /**
     * ????????????
     */
    @Override
    public List<JSONObject> analysisDailyDyBloodSugar(int dateType, DyReportDTO dto, Map<Integer, String> eatTime) {
        //????????????

        List<DYYPBloodSugarPO> dateList = this.dyBloodSugarService
                .listDataWechatSourceOfYPParamLogOfOBDTASC(dto.getStartDt(), dto.getStartDt(), dto.getSensorNo());

        if(dateList == null || dateList.size() == 0)
            return null;

        Map<Integer, String> timeMap = getTimeMap(dateList);
        String lastDt = DateHelper.plusDate(dto.getStartDt(), -1, DateHelper.DAY_FORMAT);

        List<DYYPBloodSugarPO> lastList = this.dyBloodSugarService
                .listDataWechatSourceOfYPParamLogOfOBDTASC(lastDt, lastDt, dto.getSensorNo());
        List<JSONObject> nightHypo = analysisNightlyHypoglycemia( timeMap, dto.getStartDt(),dateList);
        //????????????
        DyRecordSettingPO settingRecordPO = this.dyMemberSettingService.getSettingValues(dto.getSensorNo());
        List<JSONObject> dawn = dawnPhenomenon(dto.getStartDt(), lastDt, settingRecordPO.getBloodSugarMaxAfter(), dateList, lastList);
        dawn.addAll(somogyPhenomenon(dto.getStartDt(), lastDt, dateList, lastList));
        JSONObject hype = analysisNightlyHyperglycemia(dto.getStartDt(), lastDt, dateList, lastList);
        if(hype != null)
            dawn.add(hype);
        Map<Integer, Double> allDate = getDyValueMap(dateList);
        Map<Integer, JSONObject> map = getValueAroundEat(allDate, timeMap, eatTime);
        List<JSONObject>  array = getEatFlu(allDate, timeMap);
        for (int i = 1; i < 4; i++) {
            Integer finalI = i;
            JSONObject obj = array.stream().filter(x -> x.containsKey("type") && Objects.equals(x.getInteger("type"), finalI)).findFirst().orElse(null);
            if(!map.containsKey(i) && obj != null)
                map.put(i, obj);
        }
        List<JSONObject> result = analysisEatFlu(dateType, timeMap, map);
        result.addAll(nightHypo);
        result.addAll(dawn);
        if(result.size() == 0){
            JSONObject val = new JSONObject();
            val.put("show", 0);
            val.put("title","??????????????????");
            val.put("content","??????????????????????????????????????????????????????????????????????????????????????????????????????");
            result.add(val);
        }
        return result;
    }

    /**
     * ?????????????????????
     * @param values
     * @return
     */
    public Map<Integer, Double> getDyValueMap(List<DYYPBloodSugarPO> values){
        Map<Integer, Double> result = new HashMap<>();
        if(values != null){
            values.forEach( v -> result.put(DynamicBloodSugarTool.getLineDataIndex(v.getRecordTime()), v.getValue().doubleValue()));
        }
        return result;
    }

    /**
     * ?????????????????????
     * @param values
     * @return
     */
    public Map<Integer, String> getTimeMap(List<DYYPBloodSugarPO> values){
       Map<Integer, String> result = new HashMap<>();
       if(values != null){
           values.forEach( v -> {
               String time = DateHelper.dateToString(v.getRecordTime()).substring(11, 16);
               result.put(DynamicBloodSugarTool.getLineDataIndex(v.getRecordTime()), time);
           });
       }
       return result;
   }

    /**
     * ?????????????????????
     */
    private List<JSONObject> analysisNightlyHypoglycemia( Map<Integer, String> times, String date, List<DYYPBloodSugarPO> dateList){
        Date startDt = DateHelper.getDate(date + " 22:30:00", DateHelper.DATETIME_FORMAT);
        Date endDt = DateHelper.getDate(date + " 06:00:00", DateHelper.DATETIME_FORMAT);
        List<DYYPBloodSugarPO> nightDate = dateList.stream().filter(x -> x.getRecordTime().after(startDt) || x.getRecordTime().before(endDt))
                .collect(Collectors.toList());
        Map<Integer, Double> nightMap = getDyValueMap(nightDate);
        Map<Integer, Double> lowPoints= getHypoglycemiaPoint(nightMap);
        List<JSONObject> result = new ArrayList<>();
        String out1 = "????????????%s????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????";
        String out2 = "????????????%s???????????????????????????????????????????????????????????????";
        int last = -100;
        int start = -1;
        double min = 0;
        List<Integer> keys = new ArrayList<>(lowPoints.keySet());
        Collections.sort(keys);
        List<String> timeStr = new ArrayList<>();
        List<String> timeStart = new ArrayList<>();
        List<String> timeEnd = new ArrayList<>();
        List<Double> minVals = new ArrayList<>();
        int count = 0;
        for (Integer i : keys) {
            count++;
            if (last + 1 != i) {
                if (start != -1) {
                    String tt = "";
                    String s1 = castTimeToChinese(times.get(start));
                    String s2 = castTimeToChinese(times.get(last));
                    timeStart.add(times.get(start));
                    timeEnd.add(times.get(last));
                    if (Objects.equals(s1, s2)) {
                        tt = s1;
                    } else {
                        tt =  s1 + "-" + s2;
                    }
                    timeStr.add(tt);
                    minVals.add(min);
                }
                min = lowPoints.get(i);
                start = last = i;
            }
            if (count == keys.size()) {
                if (start != -1) {
                    String tt = "";
                    String s1 = castTimeToChinese(times.get(start));
                    String s2 = castTimeToChinese(times.get(last));
                    timeStart.add(times.get(start));
                    timeEnd.add(times.get(last));
                    if (Objects.equals(s1, s2)) {
                        tt = s1;
                    } else {
                        tt =  s1 + "-" + s2;
                    }
                    timeStr.add(tt);
                    minVals.add(min);
                }
            }
            if (lowPoints.get(i) < min)
                min = lowPoints.get(i);
            last = i;
        }
        for (int i = 0;i < timeStr.size();i ++){
            JSONObject val = new JSONObject();
            val.put("start" , timeStart.get(i));
            val.put("end", timeEnd.get(i));
            val.put("time", times.get(start));
            val.put("value", nightMap.get(start));
            val.put("title", "???????????????");
            val.put("type", 0);
            val.put("show", 1);
            val.put("createError", 1);
            val.put("errorTime", "????????????");
            val.put("errorDesc", "?????????????????????");
            val.put("errorType", GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_DINNER);
            if(minVals.get(i) < DynamicBloodSugarConstant.SECOND_LOW_VALUE) {
                val.put("content", String.format(out2, timeStr.get(i)));
            }else {
                val.put("content",String.format(out1, timeStr.get(i)));
            }
            result.add(val);
        }
        return result;
    }


    private JSONObject analysisNightlyHyperglycemia(String date1, String date2, List<DYYPBloodSugarPO> dateList, List<DYYPBloodSugarPO> lastList){
        String out = "???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????";
        Date t1 = DateHelper.getDate(date2 + " 22:30:00", DateHelper.DATETIME_FORMAT);
        Date t3 = DateHelper.getDate(date1 + " 03:00:00", DateHelper.DATETIME_FORMAT);
        if(lastList.stream().anyMatch(x -> x.getRecordTime().after(t1) && x.getValue().doubleValue() > 10)
                && dateList.stream().anyMatch(x -> x.getRecordTime().before(t3) &&  x.getValue().doubleValue() > 10)){
            JSONObject val = new JSONObject();
            val.put("title", "???????????????");
            val.put("type", 1);
            val.put("show", 0);
            val.put("createError", 1);
            val.put("errorTime", "????????????");
            val.put("errorDesc", "??????????????????");
            val.put("errorType", GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_DINNER);
            val.put("content", out);
            return val;
        }
        return null;
    }

    /**
     * ????????????
     * @return
     */
    private List<JSONObject> dawnPhenomenon(String date1, String date2, double target, List<DYYPBloodSugarPO> dateList, List<DYYPBloodSugarPO> lastList){
        List<JSONObject> result = new ArrayList<>();
        String out = "????????????%s?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????";
        Date t1 = DateHelper.getDate(date2 + " 22:30:00", DateHelper.DATETIME_FORMAT);
        Date t2 = DateHelper.getDate(date1 + " 06:00:00", DateHelper.DATETIME_FORMAT);
        Date t3 = DateHelper.getDate(date1 + " 03:00:00", DateHelper.DATETIME_FORMAT);
        Date t4 = DateHelper.getDate(date1 + " 05:00:00", DateHelper.DATETIME_FORMAT);
        if(lastList.stream().noneMatch(x -> x.getRecordTime().after(t1) && (x.getValue().doubleValue() < DynamicBloodSugarConstant.FIRST_LOW_VALUE || x.getValue().doubleValue() > target))
        && dateList.stream().noneMatch(x -> x.getRecordTime().before(t2) && x.getValue().doubleValue() < DynamicBloodSugarConstant.FIRST_LOW_VALUE )
        && dateList.stream().noneMatch(x -> x.getRecordTime().before(t3) &&  x.getValue().doubleValue() > target)){
            List<DYYPBloodSugarPO> high= dateList.stream().filter(x-> x.getRecordTime().after(t3) && x.getRecordTime().before(t4) && x.getValue().doubleValue()  > target).collect(Collectors.toList());
            Map<Integer,Double> values = getDyValueMap(high);
            Map<Integer, String> time = getTimeMap(dateList);
            List<Integer> keys = new ArrayList<>(values.keySet());
            Collections.sort(keys);
            int start = -1;
            int last = -1;
            int count = 0;
            for (int key:keys) {
                count ++;
                if(start == -1 || key - last != 1){
                    if(start != -1) {
                        String s1 = castTimeToChinese(time.get(start));
                        String s2 = castTimeToChinese(time.get(last));
                        JSONObject val = new JSONObject();
                        val.put("start", time.get(start));
                        val.put("end", time.get(last));
                        val.put("time", time.get(start));
                        val.put("value", values.get(start));
                        val.put("title", "?????????????????????????????????");
                        val.put("type", 1);
                        val.put("show", 1);
                        val.put("createError", 1);
                        val.put("errorTime", "????????????");
                        val.put("errorDesc", "??????????????????");
                        val.put("errorType", GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_DINNER);
                        if (s1.equals(s2))
                            val.put("content", String.format(out, s1));
                        else
                            val.put("content", String.format(out, s1 + "-" + s2));
                        result.add(val);
                    }
                    start = key;
                }
                last = key;
                if(count == keys.size()){
                    JSONObject val = new JSONObject();
                    val.put("start", time.get(start));
                    val.put("end", time.get(last));
                    val.put("time", time.get(start));
                    val.put("value", values.get(start));
                    val.put("title", "???????????????");
                    val.put("type", 1);
                    val.put("show", 1);
                    val.put("createError", 1);
                    val.put("errorTime", "????????????");
                    val.put("errorDesc", "??????????????????");
                    val.put("errorType", GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_DINNER);
                    String s1 = castTimeToChinese(time.get(start));
                    String s2 = castTimeToChinese(time.get(last));
                    if (s1.equals(s2))
                        val.put("content", String.format(out, s1));
                    else
                        val.put("content", String.format(out, s1 + "-" + s2));
                    result.add(val);
                }
            }
        }
        return result;
    }

    public List<JSONObject> somogyPhenomenon(String date1, String date2,  List<DYYPBloodSugarPO> dateList, List<DYYPBloodSugarPO> lastList) {
        List<JSONObject> result = new ArrayList<>();
        String out = "????????????%s?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????";
        Date t1 = DateHelper.getDate(date2 + " 02:00:00", DateHelper.DATETIME_FORMAT);
        Date t2 = DateHelper.getDate(date1 + " 03:00:00", DateHelper.DATETIME_FORMAT);
        Date t3 = DateHelper.getDate(date1 + " 06:00:00", DateHelper.DATETIME_FORMAT);
        if(lastList.stream().anyMatch(x -> x.getRecordTime().after(t1) && x.getValue().doubleValue() < DynamicBloodSugarConstant.FIRST_LOW_VALUE)
                || dateList.stream().anyMatch(x -> x.getRecordTime().before(t2) && x.getValue().doubleValue() < DynamicBloodSugarConstant.FIRST_LOW_VALUE)){
            List<DYYPBloodSugarPO> high= dateList.stream().filter(x-> x.getRecordTime().after(t2) && x.getRecordTime().before(t3) && x.getValue().doubleValue()  > 10).collect(Collectors.toList());
            Map<Integer,Double> values = getDyValueMap(high);
            Map<Integer, String> time = getTimeMap(dateList);
            List<Integer> keys = new ArrayList<>(values.keySet());
            Collections.sort(keys);
            int start = -1;
            int last = -1;
            int count = 0;
            for (int key:keys) {
                count ++;
                if(start == -1 || key - last != 1){
                    if(start != -1) {
                        JSONObject val = new JSONObject();
                        val.put("start", time.get(start));
                        val.put("end", time.get(last));
                        String s1 = castTimeToChinese(time.get(start));
                        String s2 = castTimeToChinese(time.get(last));
                        if (s1.equals(s2))
                            val.put("content", String.format(out, s1));
                        else
                            val.put("content", String.format(out, s1 + "-" + s2));
                        result.add(val);
                    }
                    start = key;
                }
                last = key;
                if(count == keys.size()){
                    JSONObject val = new JSONObject();
                    val.put("start", time.get(start));
                    val.put("end", time.get(last));
                    val.put("time", time.get(start));
                    val.put("value", values.get(start));
                    val.put("title", "????????????????????????????????????");
                    val.put("type", 1);
                    val.put("show", 1);
                    val.put("createError", 1);
                    val.put("errorTime", "????????????");
                    val.put("errorDesc", "????????????????????????");
                    val.put("errorType", GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_DINNER);
                    String s1 = castTimeToChinese(time.get(start));
                    String s2 = castTimeToChinese(time.get(last));
                    if (s1.equals(s2))
                        val.put("content", String.format(out, s1));
                    else
                        val.put("content", String.format(out, s1 + "-" + s2));
                    result.add(val);
                }
            }
        }
        return result;
    }


    private Map<Integer, JSONObject> getValueAroundEat(Map<Integer, Double> values, Map<Integer, String> timeMap, Map<Integer, String> eatTime){
        List<Integer> keys = new ArrayList<>(values.keySet());
        Collections.sort(keys);
        double [] min = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
        double [] max = new double[]{9, 0 ,9, 0, 9, 0};
        Integer [] minTime = new Integer[] {-1, -1,-1, -1, -1, -1};
        Integer [] maxTime = new Integer[] {-1, -1,-1, -1, -1, -1};
        String [] times = new String[]{ eatTime.get(1), eatTime.get(2),eatTime.get(3)};
        for (Integer key : keys) {
            String t = timeMap.get(key);
            double value = values.get(key);
            for (int i = 0; i < 3; i++) {
                String time = times[i];
                if(time == null) continue;
                if ( DateHelper.hourAfter(DateHelper.getHourAddHour(time, -1), t)
                        && DateHelper.hourAfter(t, time)) {
                    if (min[2 * i] > value) {
                        min[2 * i] = value;
                        minTime[2 * i] = key;
                    }
                    if (max[2 * i] < value) {
                        max[2 * i] = value;
                        maxTime[2 * i] = key;
                    }
                    break;
                }
                if ( DateHelper.hourAfter(t, DateHelper.getHourAddHour(time, 2))
                        && DateHelper.hourAfter(time, t)) {
                    if (min[1 + 2 * i] > value) {
                        min[1 + 2 * i] = value;
                        minTime[1 + 2 * i] = key;
                    }
                    if (max[1 + 2 * i] < value) {
                        max[1 + 2 * i] = value;
                        maxTime[1 + 2 * i] = key;
                    }
                    break;
                }
            }
        }
        Map<Integer, JSONObject> map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            if(times[i] == null) continue;
            JSONObject obj = new JSONObject();
            if(min[2 * i] < 3.9){
                obj.put("start", minTime[2 * i]);
                obj.put("startTime", timeMap.get(minTime[2 * i]));
                obj.put("startValue", min[2 *i]);
            }else {
                obj.put("start", maxTime[2 * i]);
                obj.put("startTime", timeMap.get(maxTime[2 * i]));
                obj.put("startValue", max[2 *i]);
            }
            obj.put("end", maxTime[2 * i + 1]);
            obj.put("endTime", timeMap.get(maxTime[2 * i + 1]));
            obj.put("endValue", max[2 *i + 1]);
            obj.put("type", i + 1);
            map.put(obj.getInteger("type"), obj);
        }

        return map;
    }


    /**
     * ??????????????????
     * @param values
     * @return
     */
    private List<JSONObject>  getEatFlu(Map<Integer, Double> values, Map<Integer, String> timeMap){
        List<Integer> keys = new ArrayList<>(values.keySet());
        Collections.sort(keys);
        Integer start = -1;
        Integer last = -1;
        double startValue = 0;
        double lastValue = 0;
        boolean isUp = false;
        List<JSONObject>  array = new ArrayList<>();
        for (Integer key : keys) {
            if (last == -1 || lastValue > values.get(key)) {
                if (isUp) {
                    if (lastValue - startValue >= DynamicBloodSugarConstant.EATING_FLUCTUATION_VALUE) {
                        JSONObject obj = new JSONObject();
                        obj.put("type", AnalysisTool.getEatingTimeType(timeMap.get(start)));
                        obj.put("start", start);
                        obj.put("end", last);
                        obj.put("startTime", timeMap.get(start));
                        obj.put("endTime", timeMap.get(last));
                        obj.put("startValue", startValue);
                        obj.put("endValue", lastValue);
                        array.add(obj);
                    }
                }
                isUp = false;
                start = key;
                startValue = values.get(key);
            } else {
                isUp = true;
            }
            last = key;
            lastValue = values.get(key);
        }
        if (isUp) {
            if (lastValue - startValue >= DynamicBloodSugarConstant.EATING_FLUCTUATION_VALUE) {
                JSONObject obj = new JSONObject();
                obj.put("type", AnalysisTool.getEatingTimeType(timeMap.get(start)));
                obj.put("start", start);
                obj.put("end", last);
                obj.put("startTime", timeMap.get(start));
                obj.put("endTime", timeMap.get(last));
                obj.put("startValue", startValue);
                obj.put("endValue", lastValue);
                array.add(obj);
            }
        }
        return array;
    }

    /**
     * ??????????????????
     * @param dateType 0 ?????? 1 ??????
     * @param times
     * @param map
     */
    private List<JSONObject> analysisEatFlu(int dateType, Map<Integer, String> times, Map<Integer, JSONObject> map){
        String str1 = "??????%s???????????????????????????????????????????????????????????????????????????????????????????????????????????????%s????????????????????????????????????????????????????????????????????????";
        String str2 = "???%s??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????%s?????????????????????????????????????????????????????????????????????";
        String str3 = "???%s??????????????????????????????????????????%s????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????%s????????????????????????????????????????????????????????????????????????";
        String str4 = "????????????%s???????????????,????????????????????????????????????????????????";
        List<JSONObject> result = new ArrayList<>();
        int[] checkEat = new int[]{36, 48, 72};
        for (int i = 1; i < 4; i++) {
            JSONObject obj = map.get(i);
            if(obj == null){
                if(times.containsKey(checkEat[i - 1])) {
                    JSONObject val = new JSONObject();
                    val.put("show", 0);
                    val.put("title", "????????????????????????");
                    val.put("content", String.format(str4, i == 1 ? "??????" : i == 2 ? "??????" : "??????"));
                    result.add(val);
                }
                continue;
            }
            double startValue = (double) obj.get("startValue");
            double endValue = (double) obj.get("endValue");
            int start = obj.getInteger("start");
            int end = obj.getInteger("end");
            int type = obj.getInteger("type");
            if (type == 4) continue;
            String timeStr = times.get(start);
            String tmp = "";
            String errorTypeB = GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_BREAKFAST;
            String errorTypeA = GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_BREAKFAST;
            switch (type) {
                case 1:
                    timeStr = dateType == 0 ? "??????" : "????????????";
                    tmp = dateType == 0 ? "????????????" : "????????????";
                    errorTypeB = GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_BREAKFAST;
                    errorTypeA = GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_DINNER;
                    break;
                case 2:
                    timeStr = dateType == 0 ? "??????" : "????????????";
                    tmp = dateType == 0 ? "??????" : "????????????";
                    errorTypeB = GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_LUNCH;
                    errorTypeA = GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_BREAKFAST;
                    break;
                case 3:
                    timeStr = dateType == 0 ? "??????" : "????????????";
                    tmp = dateType == 0 ? "??????" : "????????????";
                    errorTypeB = GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_DINNER;
                    errorTypeA = GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_LUNCH;
                    break;
            }
            if (startValue < 3.9) {
                JSONObject val = new JSONObject();
                val.put("show", 1);
                val.put("createError", 1);
                val.put("time", times.get(start));
                val.put("value", startValue);
                val.put("type", 0);
                val.put("errorTime", tmp);
                val.put("errorDesc", timeStr + "???????????????");
                val.put("errorType", errorTypeA);
                val.put("content", String.format(str1, timeStr, tmp));
                val.put("title", "??????????????????");
                result.add(val);
            }
            if (startValue > 10) {
                JSONObject val = new JSONObject();
                val.put("show", 1);
                val.put("createError", 1);
                val.put("time", times.get(start));
                val.put("value", startValue);
                val.put("type", 1);
                val.put("errorTime", tmp);
                val.put("errorDesc", timeStr + "???????????????");
                val.put("errorType", errorTypeA);
                val.put("content", String.format(str2, timeStr, tmp));
                val.put("title", "??????????????????");
                result.add(val);
            }
            if (endValue > 13.9) {
                JSONObject val = new JSONObject();
                val.put("show", 1);
                val.put("createError", 1);
                val.put("time", times.get(end));
                val.put("value", endValue);
                val.put("type", 1);
                val.put("errorTime", timeStr);
                val.put("errorDesc", timeStr + "???????????????");
                val.put("errorType", errorTypeB);
                val.put("content", String.format(str3, timeStr, timeStr, timeStr));
                val.put("title", "??????????????????");
                result.add(val);
            }
        }
        return result;
    }


    /**
     *  ????????????????????????
     * @param sensorNo
     * @param date
     * @return
     */
    @Override
    public Double getMaxEatFlu(String sensorNo, String date){
        List<DYYPBloodSugarPO> dateList = this.dyBloodSugarService
                .listDataWechatSourceOfYPParamLogOfOBDTASC(date, date, sensorNo);
        Map<Integer, Double> allDate = getDyValueMap(dateList);
        Map<Integer, String> timeMap = getTimeMap(dateList);
        List<JSONObject> array= getEatFlu(allDate, timeMap);
        double max = 0;
        for(Object item: array) {
            JSONObject obj = (JSONObject) item;
            double startValue = (double) obj.get("startValue");
            double endValue = (double) obj.get("endValue");
            if(endValue -startValue > max)
                max = endValue - startValue;
        }
        return max;
    }
}
