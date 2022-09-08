package com.comvee.cdms.dybloodsugar.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.ComveeMd5Util;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.dybloodsugar.dto.AddDYYPStatisticsAdviceDTO;
import com.comvee.cdms.dybloodsugar.dto.DYYPStatisticsDTO;
import com.comvee.cdms.dybloodsugar.dto.DyStaticsDTO;
import com.comvee.cdms.dybloodsugar.mapper.DYYPStatisticsAdvicePOMapper;
import com.comvee.cdms.dybloodsugar.mapper.DYYPStatisticsPOMapper;
import com.comvee.cdms.dybloodsugar.po.*;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.dybloodsugar.service.DyStaticsService;
import com.comvee.cdms.dybloodsugar.task.StatisticsOfYPTask;
import com.comvee.cdms.dybloodsugar.vo.ShowSensorVO;
import com.comvee.cdms.dybloodsugar.vo.StatisticsYPParamLogOfGAPPVO;
import com.comvee.cdms.dybloodsugar.vo.StatisticsYPParamLogOfWebVO;
import com.comvee.cdms.dybloodsugar.vo.YPCharDataOfXYVO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author linyudui
 */
@Service("dyStaticsService")
public class DyStaticsServiceImpl implements DyStaticsService {

    private final static Integer DAY_FULL_COUNT = 96;
    private final static Integer HOUR_FULL_COUNT = 4;
    private final static Integer FULL_CREATE_AGF_DAY_COUNT = 5;
    private final static Integer FULL_CREATE_AGF_LOG_COUNT = 5 * 96;


    public final static Integer DAY_YP_1 = 1;
    public final static Integer DAY_YP_3 = 3;
    public final static Integer DAY_YP_14 = 14;
    public final static List<Integer> daysOfYP = new ArrayList<>(3);

    static {
        //今日
        daysOfYP.add(DAY_YP_1);
        //近3天
        daysOfYP.add(DAY_YP_3);
        //近14天
        daysOfYP.add(DAY_YP_14);
    }

    public final static Integer STATISTICS_REPORT_TYPE_1 = 1;
    public final static Integer STATISTICS_REPORT_TYPE_2 = 2;
    public final static Integer STATISTICS_REPORT_TYPE_3 = 3;
    public final static Integer STATISTICS_REPORT_TYPE_4 = 4;
    //数值格式化对象
    private final static DecimalFormat DF = new DecimalFormat("#0.00");

    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    @Autowired
    private DYYPStatisticsPOMapper dyypStatisticsPOMapper;

    @Autowired
    private DYYPStatisticsAdvicePOMapper dyypStatisticsAdvicePOMapper;

    @Autowired
    private DyMemberSensorService dyMemberSensorService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private DoctorServiceI doctorServiceI;

    @Override
    public void statisticsOfYPGAPPHandle(Integer type, List<String> paramOfRecordDts, String sensorNo) {
        if (STATISTICS_REPORT_TYPE_1.equals(type)) {
            for (Integer diffDay : daysOfYP) {
                if (paramOfRecordDts != null && paramOfRecordDts.size() > 0) {
                    for (String endDt : paramOfRecordDts) {
                        this.handleOfStatisticsYPParamLogWithLock(diffDay, type, endDt, sensorNo);
                    }
                }
            }
        } else if (STATISTICS_REPORT_TYPE_2.equals(type) || STATISTICS_REPORT_TYPE_3.equals(type) ||
                STATISTICS_REPORT_TYPE_4.equals(type)) {
            if (paramOfRecordDts != null && paramOfRecordDts.size() > 0) {
                for (String endDt : paramOfRecordDts) {
                    this.handleOfStatisticsYPParamLogWithLock(DAY_YP_14, type, endDt, sensorNo);
                }
            }
        }
    }

    @Override
    public StatisticsYPParamLogOfGAPPVO handleOfStatisticsYPParamLogWithLock(Integer diffDay, Integer type,
                                                                             String endDt, String sensorNo) {
        if (StringUtils.isBlank(endDt)) {
            StatisticsYPParamLogOfGAPPVO model = new StatisticsYPParamLogOfGAPPVO();
            model.setChartShow(0);
            return model;
        }
        /**业务数据计算处理开始**/
        //不是每日血糖总结
        if (!STATISTICS_REPORT_TYPE_4.equals(type)) {
            StatisticsYPParamLogOfGAPPVO resultModel = this.handleOfStatisticsYPForUnType4(diffDay, type, endDt, sensorNo);
            return resultModel;
        } else {
            StatisticsYPParamLogOfGAPPVO resultModel = this.getStatisticsYPParamLogOfGAPPVOOfType4(endDt, diffDay, sensorNo);
            return resultModel;
        }
    }

    @Override
    public DYYPStatisticsPOWithBLOBs getStatisticsYPParamLogOfGAPP(String endDt, Integer diffDay, Integer type,
                                                                   String sensorNo) {
        DYYPStatisticsPOWithBLOBs dyypStatisticsPOWithBLOBs = null;
        DYYPStatisticsDTO dto = new DYYPStatisticsDTO();
        dto.setStartDt(endDt);
        dto.setDay(diffDay);
        dto.setType(type);
        dto.setSensorNo(sensorNo);
        dto.setValid(1);
        List<DYYPStatisticsPOWithBLOBs> list = this.dyypStatisticsPOMapper.listDYYPStatisticsPOWithBLOBs(dto);
        if (list != null && list.size() > 0) {
            dyypStatisticsPOWithBLOBs = list.get(0);
        }
        return dyypStatisticsPOWithBLOBs;
    }

    @Override
    public void modifyDayStatistics(DYYPStatisticsPOWithBLOBs statisticsYPParamLog) {
        int recordCount = this.dyypStatisticsPOMapper.updateByPrimaryKeySelective(statisticsYPParamLog);
    }

    @Override
    public void addDayStatistics(DYYPStatisticsPOWithBLOBs statisticsYPParamLog) {
        Date date = new Date();
        statisticsYPParamLog.setInsertDt(date);
        statisticsYPParamLog.setModifyDt(date);
        int recordCount = this.dyypStatisticsPOMapper.insertSelective(statisticsYPParamLog);
    }

    private StatisticsYPParamLogOfGAPPVO getStatisticsYPParamLogOfGAPPVOOfType4(String endDt, Integer diffDay, String sensorNo) {
        Integer chartShow = 1;
        StatisticsYPParamLogOfGAPPVO resultModel = null;
        //每日血糖总结
        List<StatisticsYPParamLogOfGAPPVO> listChart = new ArrayList<>();
        String dateType = "h";
        String endDt1 = endDt;
        for (int day = 0; day < diffDay; day++) {
            StatisticsYPParamLogOfGAPPVO gappModel = new StatisticsYPParamLogOfGAPPVO();
            //开始时间
            String startDt1 = endDt1;
            /**业务数据计算处理开始**/
            //血糖值和
            Double sum = 0D;
            //血糖记录列表
            List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService.listDataSourceOfYPParamLogOfOBDTASC(startDt1, endDt1, sensorNo);
            if (paramLogOfYPPOS.size() > 0) {
                //血糖记录数量
                int logCount = paramLogOfYPPOS.size();
                //低血糖，高血糖，正常血糖血糖事件次数
                Integer lowEventCount = 0, highEventCount = 0, normalEventCount = 0;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始/结束标志 true 开始，false 结束
                Boolean hasLowItem = false, hasHighItem = false, hasNormalItem = false, hasLogItemOf3_9 = false,
                        hasLogItemOf4_0 = false, hasLogItemOf13_9 = false;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始时间标志
                String lowEventLogSDt = null, highEventLogSDt = null, normalEventLogSDt = null, eventLogSDtOf3_9 = null,
                        eventLogSDtOf4_0 = null, eventLogSDtOf13_9 = null;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件结束时间标志
                String lowEventLogEDt = null, highEventLogEDt = null, normalEventLogEDt = null, eventLogEDtOf3_9 = null,
                        eventLogEDtOf4_0 = null, eventLogEDtOf13_9 = null;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件总持续时间
                Long lowEventDtCount = 0L, highEventDtCount = 0L, normalEventDtCount = 0L, eventDtCountOf3_9 = 0L, eventDtCountOf4_0 = 0L, eventDtCountOf13_9 = 0L;
                //血糖记录持续时间
                Long ptDtCount = 0L;
                if (logCount > 0) {
                    ptDtCount = paramLogOfYPPOS.get(logCount - 1).getRecordTime().getTime() -
                            paramLogOfYPPOS.get(0).getRecordTime().getTime();
                }
                //血糖值数组
                Double[] arr = null;
                if (logCount > 0) {
                    arr = new Double[logCount];
                }

                //持续时间&统计&求和-计算
                this.calculationHandle(hasLowItem, lowEventLogSDt, lowEventLogEDt, lowEventCount, lowEventDtCount,
                        hasHighItem, highEventLogSDt, highEventLogEDt, highEventCount, highEventDtCount,
                        hasNormalItem, normalEventLogSDt, normalEventLogEDt, normalEventCount, normalEventDtCount,
                        hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9, eventDtCountOf3_9,
                        hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0, eventDtCountOf4_0,
                        hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9, eventDtCountOf13_9,
                        arr, sum,
                        logCount, paramLogOfYPPOS);

                //处理血糖列表只有低血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                lowEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasLowItem, lowEventLogSDt, lowEventLogEDt);
                //处理血糖列表只有高血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                highEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasHighItem, highEventLogSDt, highEventLogEDt);
                //处理血糖列表只有正常血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                normalEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasNormalItem, normalEventLogSDt, normalEventLogEDt);
                //处理血糖列表只有低3.9的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf3_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9);
                //处理血糖列表只有低4.0的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf4_0 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0);
                //处理血糖列表只有低13.9的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf13_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9);
                //低血糖范围持续时间占比
                String awiTimeRateOfLow = "0.0";
                //高血糖范围持续时间占比
                String awiTimeRateOfHigh = "0.0";
                //正常血糖范围持续时间占比（3.9-10）
                String awiTimeRateOfNormal = "0.0";
                //低3.9,低4.0,低13.9占比
                String awiTimeRateOf3_9 = "0.0";
                String awiTimeRateOf4_0 = "0.0";
                String awiTimeRateOf13_9 = "0.0";

                //达标非达标时间占比计算
                this.calculationRateHandle(awiTimeRateOfLow, awiTimeRateOfHigh, awiTimeRateOfNormal, awiTimeRateOf3_9, awiTimeRateOf4_0, awiTimeRateOf13_9,
                        lowEventDtCount, highEventDtCount, normalEventDtCount, eventDtCountOf3_9, eventDtCountOf4_0, eventDtCountOf13_9, ptDtCount);


                /**业务数据计算处理结束**/
                /**图表处理开始**/
                //血糖日趋势图-每日血糖总结子项
                //x轴
                List<String> xArea = this.getHChartXList(startDt1, endDt1, 4);
                //散点集列表
                List<List<Object>> pointList = new ArrayList<>();
                for (int i = 0; i < logCount; i++) {
                    DYYPBloodSugarPO paramLogOfYPPO = paramLogOfYPPOS.get(i);
                    BigDecimal value = paramLogOfYPPO.getValue();
                    Byte level = paramLogOfYPPO.getLevel();
                    if (value == null) {
                        continue;
                    }
                    sum += value.doubleValue();
                    //散点集[x,y,s,k] s状态 1偏低 3正常 5偏高 k:血糖记录编号
                    List<Object> pointListItem = this.getPointListItem(dateType, xArea, paramLogOfYPPO);
                    pointListItem.add(level);
                    pointListItem.add(paramLogOfYPPO.getSid());
                    pointList.add(pointListItem);
                }

                //标准差
                Double standardValue = 0.0D;
                if (arr != null && arr.length > 0) {
                    standardValue = this.getStandardValue(arr);
                }
                //平均血糖值
                Double avg = 0D;
                if (logCount > 0) {
                    avg = sum / logCount;
                }
                //糖化血红蛋白
                Double ghb = new BigDecimal(avg + 2.59)
                        .divide(new BigDecimal("1.59"), 1, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
                //低血糖事件
                Integer eventCountOfLow = lowEventCount;
                //低血糖平均持续时间
                Double avgAwiTimeOfLow = 0d;
                if (eventCountOfLow > 0) {
                    avgAwiTimeOfLow = Double.parseDouble(lowEventDtCount / 1000 / 60 / eventCountOfLow + "");
                }
                //高血糖事件
                Integer eventCountOfHigh = highEventCount;
                //高血糖平均持续时间
                Double avgAwiTimeOfHigh = 0d;
                if (eventCountOfHigh > 0) {
                    avgAwiTimeOfHigh = Double.parseDouble(highEventDtCount / 1000 / 60 / eventCountOfHigh + "");
                }
                //正常血糖事件
                Integer eventCountOfNormal = normalEventCount;
                //正常血糖平均持续时间
                Double avgAwiTimeOfNormal = 0d;
                if (eventCountOfNormal > 0) {
                    avgAwiTimeOfNormal = Double.parseDouble(normalEventDtCount / 1000 / 60 / eventCountOfNormal + "");
                }
                //血糖变异系数
                String coefficientOfVariation = "0.0";
                if (avg != 0) {
                    coefficientOfVariation = DF.format(standardValue / avg * 100);
                }
                //平均血糖波动幅度（mean amplitude of glycemic excursion，MAGE）
                Double MAGE = 0.0;
                if (arr != null && arr.length > 0) {
                    MAGE = this.getMAGE(standardValue, arr);
                }
                String meanAmplitudeOfGlycemicExcursion = (MAGE > 0) ? DF.format(MAGE) : "0.0";

                //图表数据源
                YPCharDataOfXYVO charDataOfXYModel = new YPCharDataOfXYVO();
                if (pointList != null && pointList.size() > 0) {
                    //处理xArea最后一个点刚好为下一天凌晨0点的
                    xArea = this.handleOfLastXArea(xArea);
                    //图表数据源
                    charDataOfXYModel.setXArea(xArea);
                    charDataOfXYModel.setDataSource(pointList);
                } else {
                    chartShow = 0;
                }
                gappModel.setChartData(charDataOfXYModel);
                gappModel.setAvgNum(DF.format(avg));
                gappModel.setGhb(DF.format(ghb));
                gappModel.setEventCountOfLow(eventCountOfLow);
                gappModel.setAvgAwiTimeOfLow(DF.format(avgAwiTimeOfLow));
                gappModel.setStandardVal(DF.format(standardValue));
                gappModel.setEventCountOfHigh(eventCountOfHigh);
                gappModel.setAvgAwiTimeOfHigh(avgAwiTimeOfHigh);
                gappModel.setEventCountOfNormal(eventCountOfNormal);
                gappModel.setAvgAwiTimeOfNormal(avgAwiTimeOfNormal);
                gappModel.setAwiTimeRateOfLow(awiTimeRateOfLow);
                gappModel.setAwiTimeRateOfHigh(awiTimeRateOfHigh);
                gappModel.setAwiTimeRateOfNormal(awiTimeRateOfNormal);
                gappModel.setCoefficientOfVariation(coefficientOfVariation);
                gappModel.setAwiTimeRateOf3_9(awiTimeRateOf3_9);
                gappModel.setAwiTimeRateOf4_0(awiTimeRateOf4_0);
                gappModel.setAwiTimeRateOf13_9(awiTimeRateOf13_9);
                gappModel.setMeanAmplitudeOfGlycemicExcursion(meanAmplitudeOfGlycemicExcursion);
                gappModel.setLowLineVal(3.9d);
                gappModel.setHighLineVal(10.0d);
                gappModel.setRecordDt(startDt1);
                gappModel.setChartShow(chartShow);
                listChart.add(gappModel);
            }
            endDt1 = DateHelper.plusDate(endDt1, -1);
        }
        //图表数据JSON
        String chartDateJson = JSONObject.toJSONString(listChart);
        /**图表处理结束**/
        //判断-保存/更新
        DYYPStatisticsPOWithBLOBs statisticsYPParamLog = this.getStatisticsYPParamLogOfGAPP(endDt
                , diffDay, STATISTICS_REPORT_TYPE_4, sensorNo);
        if (statisticsYPParamLog != null) {
            //更新
            statisticsYPParamLog.setStartDt(DateHelper.getDate(endDt, DateHelper.DAY_FORMAT));
            statisticsYPParamLog.setType(Byte.parseByte(STATISTICS_REPORT_TYPE_4.toString()));
            statisticsYPParamLog.setChartData(chartDateJson);
            if (chartShow == 1) {
                this.modifyDayStatistics(statisticsYPParamLog);
            }
        } else {
            //保存
            statisticsYPParamLog = new DYYPStatisticsPOWithBLOBs();
            statisticsYPParamLog.setSid(DaoHelper.getSeq());
            statisticsYPParamLog.setType(Byte.parseByte(STATISTICS_REPORT_TYPE_4.toString()));
            statisticsYPParamLog.setStartDt(DateHelper.getDate(endDt, DateHelper.DAY_FORMAT));
            statisticsYPParamLog.setDay(diffDay);
            statisticsYPParamLog.setSensorNo(sensorNo);
            statisticsYPParamLog.setChartData(chartDateJson);
            if (chartShow == 1) {
                this.addDayStatistics(statisticsYPParamLog);
            }
        }
        //返回对象
        if (listChart != null && listChart.size() > 0) {
            resultModel = new StatisticsYPParamLogOfGAPPVO();
            resultModel.setListChartData(listChart);
        } else {
            resultModel = new StatisticsYPParamLogOfGAPPVO();
        }
        resultModel.setChartShow(chartShow);
        resultModel.setSid(statisticsYPParamLog.getSid());

        DYYPStatisticsAdvicePO advicePO = this.dyypStatisticsAdvicePOMapper.getStatisticAdviceByStatisticsId(statisticsYPParamLog.getSid());
        if (advicePO != null) {
            resultModel.setAdContent(advicePO.getContent());
            resultModel.setDoctorId(advicePO.getDoctorId().toString());
        } else {
            String sid = ComveeMd5Util.md5("no=" + sensorNo + ",type=4,dayNum=" + diffDay);
            advicePO = this.dyypStatisticsAdvicePOMapper.getStatisticAdviceByStatisticsId(sid);
            if (advicePO != null) {
                resultModel.setAdContent(advicePO.getContent());
                resultModel.setDoctorId(advicePO.getDoctorId().toString());
            }
        }

        resultModel.setReportType(STATISTICS_REPORT_TYPE_4);
        resultModel.setRecordDt(DateHelper.plusDate(
                DateHelper.getDate(statisticsYPParamLog.getStartDt(), DateHelper.DAY_FORMAT),
                -DAY_YP_14, DateHelper.DAY_FORMAT) + "~" + statisticsYPParamLog.getStartDt());

        return resultModel;
    }

    /**
     * 处理血糖列表只有低血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
     *
     * @param flag
     * @param sDt
     * @param eDt
     * @return
     */
    private Long getDtCountBySingleTypeGlucoseList(Boolean flag, String sDt, String eDt) {
        Long outDtCount = 0L;
        if (StringUtils.isBlank(sDt) || StringUtils.isBlank(eDt)) {
            return outDtCount;
        }
        if (flag) {
            outDtCount += DateHelper.getDate(eDt, DateHelper.DATETIME_FORMAT).getTime() -
                    DateHelper.getDate(sDt, DateHelper.DATETIME_FORMAT).getTime();
        }
        return outDtCount;
    }

    /**
     * 处理type不为4的统计报告
     *
     * @param diffDay
     * @param type
     * @param endDt
     * @param sensorNo
     * @return
     */
    private StatisticsYPParamLogOfGAPPVO handleOfStatisticsYPForUnType4(Integer diffDay, Integer type, String endDt, String sensorNo) {
        StatisticsYPParamLogOfGAPPVO resultModel = null;
        Integer chartShow = 1;
        //小时
        String dateType = "h";
        //开始时间
        String startDt = null;
        if (DAY_YP_1.equals(diffDay)) {
            startDt = endDt;
        } else {
            startDt = DateHelper.plusDate(endDt, -diffDay);
        }
        //血糖值和
        Double sum = 0d;
        //血糖记录列表
        List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService.listDataSourceOfYPParamLogOfOBDTASC(startDt, endDt, sensorNo);
        if (paramLogOfYPPOS != null && !paramLogOfYPPOS.isEmpty()) {
            for (DYYPBloodSugarPO dyypBloodSugarPO : paramLogOfYPPOS) {
                BigDecimal decimal = dyypBloodSugarPO.getValue();
                if (decimal != null) {
                    sum += decimal.doubleValue();
                }
            }
        }
        //血糖记录数量
        int logCount = paramLogOfYPPOS.size();
        //低血糖，高血糖，正常血糖血糖事件次数
        Integer lowEventCount = 0, highEventCount = 0, normalEventCount = 0;
        //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始/结束标志 true 开始，false 结束
        Boolean hasLowItem = false, hasHighItem = false, hasNormalItem = false, hasLogItemOf3_9 = false,
                hasLogItemOf4_0 = false, hasLogItemOf13_9 = false;
        //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始时间标志
        String lowEventLogSDt = null, highEventLogSDt = null, normalEventLogSDt = null, eventLogSDtOf3_9 = null,
                eventLogSDtOf4_0 = null, eventLogSDtOf13_9 = null;
        //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件结束时间标志
        String lowEventLogEDt = null, highEventLogEDt = null, normalEventLogEDt = null, eventLogEDtOf3_9 = null,
                eventLogEDtOf4_0 = null, eventLogEDtOf13_9 = null;
        //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件总持续时间
        Long lowEventDtCount = 0L, highEventDtCount = 0L, normalEventDtCount = 0L, eventDtCountOf3_9 = 0L, eventDtCountOf4_0 = 0L, eventDtCountOf13_9 = 0L;
        //血糖记录持续时间
        Long ptDtCount = 0L;
        if (logCount > 0) {
            ptDtCount = paramLogOfYPPOS.get(logCount - 1).getRecordTime().getTime() -
                    paramLogOfYPPOS.get(0).getRecordTime().getTime();
        }
        //血糖值数组
        Double[] arr = null;
        if (logCount > 0) {
            arr = new Double[logCount];
        }

        //持续时间&统计&求和-计算
        this.calculationHandle(hasLowItem, lowEventLogSDt, lowEventLogEDt, lowEventCount, lowEventDtCount,
                hasHighItem, highEventLogSDt, highEventLogEDt, highEventCount, highEventDtCount,
                hasNormalItem, normalEventLogSDt, normalEventLogEDt, normalEventCount, normalEventDtCount,
                hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9, eventDtCountOf3_9,
                hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0, eventDtCountOf4_0,
                hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9, eventDtCountOf13_9,
                arr, sum,
                logCount, paramLogOfYPPOS);

        //处理血糖列表只有低血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
        lowEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasLowItem, lowEventLogSDt, lowEventLogEDt);
        //处理血糖列表只有高血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
        highEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasHighItem, highEventLogSDt, highEventLogEDt);
        //处理血糖列表只有正常血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
        normalEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasNormalItem, normalEventLogSDt, normalEventLogEDt);
        //处理血糖列表只有低3.9的情况，把低血糖结束时间设置最后一次血糖记录时间
        eventDtCountOf3_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9);
        //处理血糖列表只有低4.0的情况，把低血糖结束时间设置最后一次血糖记录时间
        eventDtCountOf4_0 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0);
        //处理血糖列表只有低13.9的情况，把低血糖结束时间设置最后一次血糖记录时间
        eventDtCountOf13_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9);
        //低血糖范围持续时间占比
        String awiTimeRateOfLow = "0.0";
        //高血糖范围持续时间占比
        String awiTimeRateOfHigh = "0.0";
        //正常血糖范围持续时间占比（3.9-10）
        String awiTimeRateOfNormal = "0.0";
        //低3.9,低4.0,低13.9占比
        String awiTimeRateOf3_9 = "0.0";
        String awiTimeRateOf4_0 = "0.0";
        String awiTimeRateOf13_9 = "0.0";

        //达标非达标时间占比计算
        this.calculationRateHandle(awiTimeRateOfLow, awiTimeRateOfHigh, awiTimeRateOfNormal, awiTimeRateOf3_9, awiTimeRateOf4_0, awiTimeRateOf13_9,
                lowEventDtCount, highEventDtCount, normalEventDtCount, eventDtCountOf3_9, eventDtCountOf4_0, eventDtCountOf13_9, ptDtCount);

        //标准差
        Double standardValue = 0.0D;
        if (arr != null && arr.length > 0) {
            standardValue = this.getStandardValue(arr);
        }
        //平均血糖值
        Double avg = 0D;
        if (logCount > 0) {
            avg = sum / logCount;
        }
        //糖化血红蛋白
        Double ghb = new BigDecimal(avg + 2.59)
                .divide(new BigDecimal("1.59"), 1, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        //低血糖事件
        Integer eventCountOfLow = lowEventCount;
        //低血糖平均持续时间
        Double avgAwiTimeOfLow = 0d;
        if (eventCountOfLow > 0) {
            avgAwiTimeOfLow = Double.parseDouble(lowEventDtCount / 1000 / 60 / eventCountOfLow + "");
        }
        //高血糖事件
        Integer eventCountOfHigh = highEventCount;
        //高血糖平均持续时间
        Double avgAwiTimeOfHigh = 0d;
        if (eventCountOfHigh > 0) {
            avgAwiTimeOfHigh = Double.parseDouble(highEventDtCount / 1000 / 60 / eventCountOfHigh + "");
        }
        //正常血糖事件
        Integer eventCountOfNormal = normalEventCount;
        //正常血糖平均持续时间
        Double avgAwiTimeOfNormal = 0d;
        if (eventCountOfNormal > 0) {
            avgAwiTimeOfNormal = Double.parseDouble(normalEventDtCount / 1000 / 60 / eventCountOfNormal + "");
        }
        //血糖变异系数
        String coefficientOfVariation = "0.0";
        if (avg != 0) {
            coefficientOfVariation = DF.format(standardValue / avg * 100);
        }
        //平均血糖波动幅度（mean amplitude of glycemic excursion，MAGE）
        Double MAGE = 0.0;
        if (arr != null && arr.length > 0) {
            MAGE = this.getMAGE(standardValue, arr);
        }
        String meanAmplitudeOfGlycemicExcursion = (MAGE > 0) ? DF.format(MAGE) : "0.0";
        /**业务数据计算处理结束**/
        /**图表处理开始**/
        //日间平均血糖绝对差
        List<Map<String, Object>> listDaySugarAvgDiffVal = new ArrayList<>();
        // 图表数据源
        YPCharDataOfXYVO charDataOfXYModel = this.getYPCharDataOfXYVOByType(logCount, type, paramLogOfYPPOS, dateType, startDt,
                endDt, sensorNo, diffDay, chartShow, listDaySugarAvgDiffVal);
        //图表数据JSON
        String chartDateJson = JSONObject.toJSONString(charDataOfXYModel);
        //日间平均血糖绝对差JSON
        String daySugarAvgDiffValJson = JSONObject.toJSONString(listDaySugarAvgDiffVal);
        /**图表处理结束**/
        //判断-保存/更新
        DYYPStatisticsPOWithBLOBs statisticsYPParamLog = this.getStatisticsYPParamLogOfGAPP(endDt
                , diffDay, type, sensorNo);
        if (statisticsYPParamLog != null) {
            //更新
            statisticsYPParamLog.setStartDt(DateHelper.getDate(endDt, DateHelper.DAY_FORMAT));
            statisticsYPParamLog.setType(Byte.parseByte(type.toString()));
            statisticsYPParamLog.setAvgNum(Double.parseDouble(DF.format(avg)));
            statisticsYPParamLog.setGhb(Double.parseDouble(DF.format(ghb)));
            statisticsYPParamLog.setAvgAwiTimeOfLow(DF.format(avgAwiTimeOfLow));
            statisticsYPParamLog.setEventCountOfLow(eventCountOfLow);
            statisticsYPParamLog.setStandardVal(DF.format(standardValue));
            statisticsYPParamLog.setEventCountOfHigh(eventCountOfHigh);
            statisticsYPParamLog.setAvgAwiTimeOfHigh(avgAwiTimeOfHigh.toString());
            statisticsYPParamLog.setEventCountOfNormal(eventCountOfNormal);
            statisticsYPParamLog.setAvgAwiTimeOfNormal(avgAwiTimeOfNormal.toString());
            statisticsYPParamLog.setAwiTimeRateOfLow(awiTimeRateOfLow);
            statisticsYPParamLog.setAwiTimeRateOfHigh(awiTimeRateOfHigh);
            statisticsYPParamLog.setAwiTimeRateOfNormal(awiTimeRateOfNormal);
            statisticsYPParamLog.setCoefficientOfVariation(coefficientOfVariation);
            statisticsYPParamLog.setAwiTimeRateOf39(awiTimeRateOf3_9);
            statisticsYPParamLog.setAwiTimeRateOf40(awiTimeRateOf4_0);
            statisticsYPParamLog.setAwiTimeRateOf139(awiTimeRateOf13_9);
            statisticsYPParamLog.setMeanAmplitudeOfGlycemicExcursion(meanAmplitudeOfGlycemicExcursion);
            statisticsYPParamLog.setChartData(chartDateJson);
            statisticsYPParamLog.setDaySugarAvgDiffValJson(daySugarAvgDiffValJson);
            statisticsYPParamLog.setSensorNo(sensorNo);
            this.modifyDayStatistics(statisticsYPParamLog);
        } else {
            //保存
            statisticsYPParamLog = new DYYPStatisticsPOWithBLOBs();
            statisticsYPParamLog.setType(Byte.parseByte(type.toString()));
            statisticsYPParamLog.setStartDt(DateHelper.getDate(endDt, DateHelper.DAY_FORMAT));
            statisticsYPParamLog.setAvgNum(Double.parseDouble(DF.format(avg)));
            statisticsYPParamLog.setGhb(Double.parseDouble(DF.format(ghb)));
            statisticsYPParamLog.setAvgAwiTimeOfLow(DF.format(avgAwiTimeOfLow));
            statisticsYPParamLog.setEventCountOfLow(eventCountOfLow);
            statisticsYPParamLog.setChartData(chartDateJson);
            statisticsYPParamLog.setStandardVal(DF.format(standardValue));
            statisticsYPParamLog.setEventCountOfHigh(eventCountOfHigh);
            statisticsYPParamLog.setAvgAwiTimeOfHigh(avgAwiTimeOfHigh.toString());
            statisticsYPParamLog.setEventCountOfNormal(eventCountOfNormal);
            statisticsYPParamLog.setAvgAwiTimeOfNormal(avgAwiTimeOfNormal.toString());
            statisticsYPParamLog.setAwiTimeRateOfLow(awiTimeRateOfLow);
            statisticsYPParamLog.setAwiTimeRateOfHigh(awiTimeRateOfHigh);
            statisticsYPParamLog.setAwiTimeRateOfNormal(awiTimeRateOfNormal);
            statisticsYPParamLog.setCoefficientOfVariation(coefficientOfVariation);
            statisticsYPParamLog.setAwiTimeRateOf39(awiTimeRateOf3_9);
            statisticsYPParamLog.setAwiTimeRateOf40(awiTimeRateOf4_0);
            statisticsYPParamLog.setAwiTimeRateOf139(awiTimeRateOf13_9);
            statisticsYPParamLog.setMeanAmplitudeOfGlycemicExcursion(meanAmplitudeOfGlycemicExcursion);
            statisticsYPParamLog.setDaySugarAvgDiffValJson(daySugarAvgDiffValJson);
            statisticsYPParamLog.setSid(DaoHelper.getSeq());
            statisticsYPParamLog.setDay(diffDay);
            statisticsYPParamLog.setSensorNo(sensorNo);
            this.addDayStatistics(statisticsYPParamLog);
        }
        if (STATISTICS_REPORT_TYPE_1.equals(type) || STATISTICS_REPORT_TYPE_2.equals(type)) {
            //返回对象
            resultModel = new StatisticsYPParamLogOfGAPPVO();
            resultModel.setAvgNum(statisticsYPParamLog.getAvgNum().toString());
            resultModel.setGhb(DF.format(ghb));
            resultModel.setEventCountOfLow(statisticsYPParamLog.getEventCountOfLow());
            resultModel.setAvgAwiTimeOfLow(statisticsYPParamLog.getAvgAwiTimeOfLow());
            resultModel.setStandardVal(statisticsYPParamLog.getStandardVal());
            resultModel.setEventCountOfHigh(statisticsYPParamLog.getEventCountOfHigh());
            resultModel.setAvgAwiTimeOfHigh(Double.parseDouble(statisticsYPParamLog.getAvgAwiTimeOfHigh()));
            resultModel.setEventCountOfNormal(statisticsYPParamLog.getEventCountOfNormal());
            resultModel.setAvgAwiTimeOfNormal(Double.parseDouble(statisticsYPParamLog.getAvgAwiTimeOfNormal()));
            resultModel.setAwiTimeRateOfLow(statisticsYPParamLog.getAwiTimeRateOfLow());
            resultModel.setAwiTimeRateOfHigh(statisticsYPParamLog.getAwiTimeRateOfHigh());
            resultModel.setAwiTimeRateOfNormal(statisticsYPParamLog.getAwiTimeRateOfNormal());
            resultModel.setCoefficientOfVariation(statisticsYPParamLog.getCoefficientOfVariation());
            resultModel.setAwiTimeRateOf3_9(statisticsYPParamLog.getAwiTimeRateOf39());
            resultModel.setAwiTimeRateOf4_0(statisticsYPParamLog.getAwiTimeRateOf40());
            resultModel.setAwiTimeRateOf13_9(statisticsYPParamLog.getAwiTimeRateOf139());
            resultModel.setMeanAmplitudeOfGlycemicExcursion(statisticsYPParamLog.getMeanAmplitudeOfGlycemicExcursion());
            resultModel.setChartData(charDataOfXYModel);
        } else if (STATISTICS_REPORT_TYPE_3.equals(type)) {
            //返回对象
            resultModel = new StatisticsYPParamLogOfGAPPVO();
            resultModel.setDaySugarAvgDiffValMap(listDaySugarAvgDiffVal);
        } else {
            resultModel = new StatisticsYPParamLogOfGAPPVO();
        }
        resultModel.setChartShow(chartShow);
        resultModel.setSid(statisticsYPParamLog.getSid());

        DYYPStatisticsAdvicePO advicePO = this.dyypStatisticsAdvicePOMapper.getStatisticAdviceByStatisticsId(statisticsYPParamLog.getSid());
        if (advicePO != null) {
            resultModel.setAdContent(advicePO.getContent());
            resultModel.setDoctorId(advicePO.getDoctorId().toString());
        } else {
            String sid = ComveeMd5Util.md5("no=" + sensorNo + ",type=" + type + ",dayNum=" + diffDay);
            advicePO = this.dyypStatisticsAdvicePOMapper.getStatisticAdviceByStatisticsId(sid);
            if (advicePO != null) {
                resultModel.setAdContent(advicePO.getContent());
                resultModel.setDoctorId(advicePO.getDoctorId().toString());
            }
        }
        return resultModel;
    }

    /**
     * 达标非达标时间占比计算
     *
     * @param awiTimeRateOfLow
     * @param awiTimeRateOfHigh
     * @param awiTimeRateOfNormal
     * @param awiTimeRateOf3_9
     * @param awiTimeRateOf4_0
     * @param awiTimeRateOf13_9
     * @param lowEventDtCount
     * @param highEventDtCount
     * @param normalEventDtCount
     * @param eventDtCountOf3_9
     * @param eventDtCountOf4_0
     * @param eventDtCountOf13_9
     * @param ptDtCount
     */
    private void calculationRateHandle(String awiTimeRateOfLow, String awiTimeRateOfHigh, String awiTimeRateOfNormal, String awiTimeRateOf3_9, String awiTimeRateOf4_0, String awiTimeRateOf13_9,
                                       Long lowEventDtCount, Long highEventDtCount, Long normalEventDtCount, Long eventDtCountOf3_9, Long eventDtCountOf4_0, Long eventDtCountOf13_9, Long ptDtCount) {
        if (ptDtCount > 0) {
            awiTimeRateOfLow = DF.format(lowEventDtCount / Double.parseDouble(ptDtCount + "") * 100D);
            awiTimeRateOfHigh = DF.format(highEventDtCount / Double.parseDouble(ptDtCount + "") * 100D);
            awiTimeRateOfNormal = DF.format(normalEventDtCount / Double.parseDouble(ptDtCount + "") * 100D);
            awiTimeRateOf3_9 = DF.format(eventDtCountOf3_9 / Double.parseDouble(ptDtCount + "") * 100D);
            awiTimeRateOf4_0 = DF.format(eventDtCountOf4_0 / Double.parseDouble(ptDtCount + "") * 100D);
            awiTimeRateOf13_9 = DF.format(eventDtCountOf13_9 / Double.parseDouble(ptDtCount + "") * 100D);
        }
    }

    /**
     * 持续时间&求和&数组赋值计算处理
     *
     * @param hasLowItem
     * @param lowEventLogSDt
     * @param lowEventLogEDt
     * @param lowEventCount
     * @param lowEventDtCount
     * @param hasHighItem
     * @param highEventLogSDt
     * @param highEventLogEDt
     * @param highEventCount
     * @param highEventDtCount
     * @param hasNormalItem
     * @param normalEventLogSDt
     * @param normalEventLogEDt
     * @param normalEventCount
     * @param normalEventDtCount
     * @param hasLogItemOf3_9
     * @param eventLogSDtOf3_9
     * @param eventLogEDtOf3_9
     * @param eventDtCountOf3_9
     * @param hasLogItemOf4_0
     * @param eventLogSDtOf4_0
     * @param eventLogEDtOf4_0
     * @param eventDtCountOf4_0
     * @param hasLogItemOf13_9
     * @param eventLogSDtOf13_9
     * @param eventLogEDtOf13_9
     * @param eventDtCountOf13_9
     * @param arr
     * @param sum
     * @param logCount
     * @param paramLogOfYPPOS
     */
    private void calculationHandle(Boolean hasLowItem, String lowEventLogSDt, String lowEventLogEDt, Integer lowEventCount,
                                   Long lowEventDtCount, Boolean hasHighItem, String highEventLogSDt, String highEventLogEDt,
                                   Integer highEventCount, Long highEventDtCount, Boolean hasNormalItem, String normalEventLogSDt,
                                   String normalEventLogEDt, Integer normalEventCount, Long normalEventDtCount, Boolean hasLogItemOf3_9,
                                   String eventLogSDtOf3_9, String eventLogEDtOf3_9, Long eventDtCountOf3_9, Boolean hasLogItemOf4_0,
                                   String eventLogSDtOf4_0, String eventLogEDtOf4_0, Long eventDtCountOf4_0, Boolean hasLogItemOf13_9,
                                   String eventLogSDtOf13_9, String eventLogEDtOf13_9, Long eventDtCountOf13_9, Double[] arr, Double sum,
                                   int logCount, List<DYYPBloodSugarPO> paramLogOfYPPOS) {
        for (int i = 0; i < logCount; i++) {
            DYYPBloodSugarPO paramLogOfYPPO = paramLogOfYPPOS.get(i);
            Double value = paramLogOfYPPO.getValue().doubleValue();
            Byte level = paramLogOfYPPO.getLevel();
            if (value == null) {
                continue;
            }
            arr[i] = value;
            //求和
            sum += value;
            //计算低血糖事件数量&总持续时间
            Byte flagLevel1 = 1;
            Map<String, Object> resultMap1 = this.handleOfEventCountAndDtCount(1, level, flagLevel1, null, null, paramLogOfYPPO, hasLowItem, lowEventLogSDt, lowEventLogEDt, lowEventCount, lowEventDtCount);
            hasLowItem = (Boolean) resultMap1.getOrDefault("outFlag", false);
            lowEventLogSDt = (String) resultMap1.getOrDefault("outSDt", "");
            lowEventLogEDt = (String) resultMap1.getOrDefault("outEDt", "");
            lowEventCount = (Integer) resultMap1.getOrDefault("outEventCount", 0);
            lowEventDtCount = (Long) resultMap1.getOrDefault("outDtCount", 0L);
            //计算高血糖事件数量&总持续时间
            Byte flagLevel5 = 5;
            Map<String, Object> resultMap2 = this.handleOfEventCountAndDtCount(1, level, flagLevel5, null, null, paramLogOfYPPO, hasHighItem, highEventLogSDt, highEventLogEDt, highEventCount, highEventDtCount);
            hasHighItem = (Boolean) resultMap2.getOrDefault("outFlag", false);
            highEventLogSDt = (String) resultMap2.getOrDefault("outSDt", "");
            highEventLogEDt = (String) resultMap2.getOrDefault("outEDt", "");
            highEventCount = (Integer) resultMap2.getOrDefault("outEventCount", 0);
            highEventDtCount = (Long) resultMap2.getOrDefault("outDtCount", 0L);
            //计算正常血糖事件数量&总持续时间
            Byte flagLevel3 = 3;
            Map<String, Object> resultMap3 = this.handleOfEventCountAndDtCount(1, level, flagLevel3, null, null, paramLogOfYPPO, hasNormalItem, normalEventLogSDt, normalEventLogEDt, normalEventCount, normalEventDtCount);
            hasNormalItem = (Boolean) resultMap3.getOrDefault("outFlag", false);
            normalEventLogSDt = (String) resultMap3.getOrDefault("outSDt", "");
            normalEventLogEDt = (String) resultMap3.getOrDefault("outEDt", "");
            normalEventCount = (Integer) resultMap3.getOrDefault("outEventCount", 0);
            normalEventDtCount = (Long) resultMap3.getOrDefault("outDtCount", 0L);
            //计算血糖小于3.9持续时间
            Map<String, Object> resultMap4 = this.handleOfEventCountAndDtCount(2, null, null, value, 3.9, paramLogOfYPPO, hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9, null, eventDtCountOf3_9);
            hasLogItemOf3_9 = (Boolean) resultMap4.getOrDefault("outFlag", false);
            eventLogSDtOf3_9 = (String) resultMap4.getOrDefault("outSDt", "");
            eventLogEDtOf3_9 = (String) resultMap4.getOrDefault("outEDt", "");
            eventDtCountOf3_9 = (Long) resultMap4.getOrDefault("outDtCount", 0L);
            //计算血糖小于4.0持续时间
            Map<String, Object> resultMap5 = this.handleOfEventCountAndDtCount(2, null, null, value, 4.0, paramLogOfYPPO, hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0, null, eventDtCountOf4_0);
            hasLogItemOf4_0 = (Boolean) resultMap5.getOrDefault("outFlag", false);
            eventLogSDtOf4_0 = (String) resultMap5.getOrDefault("outSDt", "");
            eventLogEDtOf4_0 = (String) resultMap5.getOrDefault("outEDt", "");
            eventDtCountOf4_0 = (Long) resultMap5.getOrDefault("outDtCount", 0L);
            //计算血糖小于13.9持续时间
            Map<String, Object> resultMap6 = this.handleOfEventCountAndDtCount(2, null, null, value, 13.9, paramLogOfYPPO, hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9, null, eventDtCountOf13_9);
            hasLogItemOf13_9 = (Boolean) resultMap6.getOrDefault("outFlag", false);
            eventLogSDtOf13_9 = (String) resultMap6.getOrDefault("outSDt", "");
            eventLogEDtOf13_9 = (String) resultMap6.getOrDefault("outEDt", "");
            eventDtCountOf13_9 = (Long) resultMap6.getOrDefault("outDtCount", 0L);
        }
    }

    private YPCharDataOfXYVO getYPCharDataOfXYVOByType(int logCount, Integer type, List<DYYPBloodSugarPO> paramLogOfYPPOS,
                                                       String dateType, String startDt, String endDt, String sensorNo,
                                                       Integer diffDay, Integer chartShow,
                                                       List<Map<String, Object>> listDaySugarAvgDiffVal) {
        YPCharDataOfXYVO charDataOfXYModel = new YPCharDataOfXYVO();
        if (STATISTICS_REPORT_TYPE_1.equals(type)) {
            //血糖日趋势图
            charDataOfXYModel = this.getYPCharDataOfXYVOOfType1(startDt, endDt, paramLogOfYPPOS, logCount, dateType, chartShow);
        } else if (STATISTICS_REPORT_TYPE_2.equals(type)) {
            //动态血糖图谱
            charDataOfXYModel = this.getYPCharDataOfXYVOOfType2(startDt, endDt, sensorNo, dateType, chartShow);
        } else if (STATISTICS_REPORT_TYPE_3.equals(type)) {
            //日间血糖平均绝对差
            charDataOfXYModel = this.getYPCharDataOfXYVOOfType3(startDt, endDt, sensorNo, diffDay, chartShow, listDaySugarAvgDiffVal);
        }
        return charDataOfXYModel;
    }

    /**
     * 日间血糖平均绝对差
     *
     * @param startDt
     * @param endDt
     * @param sensorNo
     * @param diffDay
     * @param chartShow
     * @param listDaySugarAvgDiffVal
     * @return
     */
    private YPCharDataOfXYVO getYPCharDataOfXYVOOfType3(String startDt, String endDt, String sensorNo, Integer diffDay,
                                                        Integer chartShow, List<Map<String, Object>> listDaySugarAvgDiffVal) {
        YPCharDataOfXYVO charDataOfXYModel = new YPCharDataOfXYVO();
        //血糖记录列表
        List<DYYPBloodSugarPO> yppos = this.dyBloodSugarService.listDataSourceOfYPParamLogOfOBDTASC(startDt, endDt, sensorNo);
        String flagDate1 = startDt;
        for (int i = 1; i < diffDay; i++) {
            String flagDate2 = DateHelper.plusDate(flagDate1, 1);
            List<DYYPBloodSugarPO> temps1 = new ArrayList<>();
            List<DYYPBloodSugarPO> temps2 = new ArrayList<>();
            for (DYYPBloodSugarPO yppo : yppos) {
                String recordDt = DateHelper.getDate(yppo.getRecordDt(), DateHelper.DAY_FORMAT);
                if (recordDt.equals(flagDate1)) {
                    temps1.add(yppo);
                } else if (recordDt.equals(flagDate2)) {
                    temps2.add(yppo);
                }
            }
            //判断是否满足全天血糖仪谱
            boolean isFullByDay1 = true, isFullByDay2 = true;
            if (temps1.size() < DAY_FULL_COUNT) {
                isFullByDay1 = false;
            }
            if (temps2.size() < DAY_FULL_COUNT) {
                isFullByDay2 = false;
            }
            //满足全天血糖仪谱
            if (isFullByDay1 && isFullByDay2) {
                int count = 0;
                double diffSum = 0d;
                //List<Double> listD = new ArrayList<>();
                for (int j = 0; j < temps1.size(); j++) {
                    DYYPBloodSugarPO temp1 = temps1.get(j);
                    String hhmm1 = DateHelper.getDate(temp1.getRecordTime(), DateHelper.TIME_FORMAT);
                    Double value1 = temp1.getValue().doubleValue();
                    for (int k = 0; k < temps2.size(); k++) {
                        DYYPBloodSugarPO temp2 = temps2.get(k);
                        String hhmm2 = DateHelper.getDate(temp2.getRecordTime(), DateHelper.TIME_FORMAT);
                        Double value2 = temp2.getValue().doubleValue();
                        if (hhmm2.equals(hhmm1)) {
                            count++;
                            double value = value2 - value1;
                            if (value < 0) {
                                value = -1 * value;
                            }
                            diffSum += value;
                            //listD.add(value);
                            break;
                        }
                    }
                }
                if (count >= DAY_FULL_COUNT) {
                        /*Double[] arr1 = new Double[listD.size()];
                        double zws = percentile(listD.toArray(arr1),0.5);
                        double diffSum1 = 0;
                        for(Double val : listD){
                            double diffVal = val-zws;
                            if(diffVal<0){
                                diffVal *= -1;
                            }
                            diffSum1 += diffVal;
                        }*/
                    String name = DateHelper.getDate(DateHelper.getDate(flagDate1, DateHelper.DAY_FORMAT), "MM/dd") + "~" +
                            DateHelper.getDate(DateHelper.getDate(flagDate2, DateHelper.DAY_FORMAT), "MM/dd");
                    double diffAvg = Double.parseDouble(DF.format((diffSum / Double.parseDouble(count + ""))));
                    //double diffAvg = Double.parseDouble(df.format((diffSum1/Double.parseDouble(listD.size()+""))));
                    Map<String, Object> daySugarAvgDiffVal = new HashMap<>();
                    daySugarAvgDiffVal.put("value", diffAvg);
                    daySugarAvgDiffVal.put("name", name);
                    listDaySugarAvgDiffVal.add(daySugarAvgDiffVal);
                }
            }
            if (isFullByDay2) {
                flagDate1 = flagDate2;
            } else {
                flagDate1 = DateHelper.plusDate(flagDate2, 1);
                i++;
            }
        }
        if (listDaySugarAvgDiffVal == null || listDaySugarAvgDiffVal.size() <= 0) {
            chartShow = 0;
        }
        return charDataOfXYModel;
    }

    /**
     * 动态血糖图谱
     *
     * @param startDt
     * @param endDt
     * @param sensorNo
     * @param dateType
     * @param chartShow
     * @return
     */
    private YPCharDataOfXYVO getYPCharDataOfXYVOOfType2(String startDt, String endDt, String sensorNo, String dateType,
                                                        Integer chartShow) {
        YPCharDataOfXYVO charDataOfXYModel = new YPCharDataOfXYVO();
        //获取时间范围内满数据数量
        Long isFullDataOfDayCount = this.dyBloodSugarService.getFullDataOfDayCount(startDt, endDt, sensorNo);
        //至少5天以上满数据
        if (isFullDataOfDayCount < 5) {
            chartShow = 0;
        } else {
            //动态血糖图
            //10%曲线
            List<List<Object>> curveOfTenPercent = new ArrayList<>();
            //25%曲线
            List<List<Object>> curveOfTwentyFivePercent = new ArrayList<>();
            //50%曲线
            List<List<Object>> curveOfFiftyPercent = new ArrayList<>();
            //75%曲线
            List<List<Object>> curveOfSeventyFivePercent = new ArrayList<>();
            //90%曲线
            List<List<Object>> curveOfNinetyPercent = new ArrayList<>();
            //x轴
            List<String> xArea = this.getHChartXList(endDt, endDt, 4);
            //时间点
            List<String> times = this.dyBloodSugarService.listMemberTimeOfRecordLog(startDt, endDt, sensorNo);
            for (String time : times) {
                //数据源
                List<DYYPBloodSugarPO> yppos = this.dyBloodSugarService.listDataSourceOfYPParamLogOfTimeASC(time, startDt, endDt, sensorNo);
                if (yppos != null && yppos.size() >= FULL_CREATE_AGF_DAY_COUNT) {
                    int size = yppos.size();
                    Double[] dataArray = new Double[size];
                    String recordTime = DateHelper.getDate(yppos.get(0).getRecordTime(), DateHelper.DATETIME_FORMAT);
                    recordTime = endDt + " " +
                            DateHelper.getDate(DateHelper.getDate(recordTime, DateHelper.DATETIME_FORMAT), "HH:mm:ss");
                    for (int i = 0; i < size; i++) {
                        DYYPBloodSugarPO paramLogOfYPPO = yppos.get(i);
                        Double value = paramLogOfYPPO.getValue().doubleValue();
                        if (value == null) {
                            continue;
                        }
                        dataArray[i] = value;
                    }
                    Date recordDate = DateHelper.getDate(recordTime, DateHelper.DATETIME_FORMAT);
                    //0.1
                    double val1 = percentile(dataArray, 0.1);
                    DYYPBloodSugarPO temp1 = new DYYPBloodSugarPO();
                    temp1.setValue(new BigDecimal(val1));
                    temp1.setRecordTime(recordDate);
                    //散点集1[x,y]
                    List<Object> pointListItem1 = this.getPointListItem(dateType, xArea, temp1);
                    curveOfTenPercent.add(pointListItem1);

                    //0.25
                    double val2 = percentile(dataArray, 0.25);
                    DYYPBloodSugarPO temp2 = new DYYPBloodSugarPO();
                    temp2.setValue(new BigDecimal(val2));
                    temp2.setRecordTime(recordDate);
                    //散点集1[x,y]
                    List<Object> pointListItem2 = this.getPointListItem(dateType, xArea, temp2);
                    curveOfTwentyFivePercent.add(pointListItem2);

                    //0.5
                    double val3 = percentile(dataArray, 0.5);
                    DYYPBloodSugarPO temp3 = new DYYPBloodSugarPO();
                    temp3.setValue(new BigDecimal(val3));
                    temp3.setRecordTime(recordDate);
                    //散点集1[x,y]
                    List<Object> pointListItem3 = this.getPointListItem(dateType, xArea, temp3);
                    curveOfFiftyPercent.add(pointListItem3);

                    //0.75
                    double val4 = percentile(dataArray, 0.75);
                    DYYPBloodSugarPO temp4 = new DYYPBloodSugarPO();
                    temp4.setValue(new BigDecimal(val4));
                    temp4.setRecordTime(recordDate);
                    //散点集1[x,y]
                    List<Object> pointListItem4 = this.getPointListItem(dateType, xArea, temp4);
                    curveOfSeventyFivePercent.add(pointListItem4);

                    //0.9
                    double val5 = percentile(dataArray, 0.9);
                    DYYPBloodSugarPO temp5 = new DYYPBloodSugarPO();
                    temp5.setValue(new BigDecimal(val5));
                    temp5.setRecordTime(recordDate);
                    //散点集1[x,y]
                    List<Object> pointListItem5 = this.getPointListItem(dateType, xArea, temp5);
                    curveOfNinetyPercent.add(pointListItem5);
                }
            }
            if (curveOfTenPercent != null
                    && curveOfTwentyFivePercent != null
                    && curveOfFiftyPercent != null
                    && curveOfSeventyFivePercent != null
                    && curveOfNinetyPercent != null) {
                Map<String, List<List<Object>>> dataSourceMap = new HashMap<>(5);
                dataSourceMap.put("curveOfTenPercent", curveOfTenPercent);
                dataSourceMap.put("curveOfTwentyFivePercent", curveOfTwentyFivePercent);
                dataSourceMap.put("curveOfFiftyPercent", curveOfFiftyPercent);
                dataSourceMap.put("curveOfSeventyFivePercent", curveOfSeventyFivePercent);
                dataSourceMap.put("curveOfNinetyPercent", curveOfNinetyPercent);
                //处理xArea最后一个点刚好为下一天凌晨0点的
                xArea = this.handleOfLastXArea(xArea);
                //图表数据源
                charDataOfXYModel.setXArea(xArea);
                charDataOfXYModel.setDataSource(dataSourceMap);
            } else {
                chartShow = 0;
            }
        }
        return charDataOfXYModel;
    }

    /**
     * 血糖日趋势图
     *
     * @param startDt
     * @param endDt
     * @param paramLogOfYPPOS
     * @param logCount
     * @param dateType
     * @param chartShow
     * @return
     */
    private YPCharDataOfXYVO getYPCharDataOfXYVOOfType1(String startDt, String endDt, List<DYYPBloodSugarPO> paramLogOfYPPOS,
                                                        int logCount, String dateType, Integer chartShow) {
        YPCharDataOfXYVO charDataOfXYModel = new YPCharDataOfXYVO();
        //x轴
        List<String> xArea = this.getHChartXList(startDt, endDt, 4);
        //散点集列表
        List<List<Object>> pointList = new ArrayList<>();
        for (int i = 0; i < logCount; i++) {
            DYYPBloodSugarPO paramLogOfYPPO = paramLogOfYPPOS.get(i);
            Double value = paramLogOfYPPO.getValue().doubleValue();
            Byte level = paramLogOfYPPO.getLevel();
            if (value == null) {
                continue;
            }
            //散点集[x,y,s,k] s状态 1偏低 3正常 5偏高 k:血糖记录编号
            List<Object> pointListItem = this.getPointListItem(dateType, xArea, paramLogOfYPPO);
            pointListItem.add(level);
            pointListItem.add(paramLogOfYPPO.getSid());
            pointList.add(pointListItem);
        }
        if (pointList != null && pointList.size() > 0) {
            //处理xArea最后一个点刚好为下一天凌晨0点的
            xArea = this.handleOfLastXArea(xArea);
            //图表数据源
            charDataOfXYModel.setXArea(xArea);
            charDataOfXYModel.setDataSource(pointList);
        } else {
            chartShow = 0;
        }
        return charDataOfXYModel;
    }

    /**
     * 处理xArea最后一个点刚好为下一天凌晨0点的
     *
     * @param xArea
     * @return
     */
    private List<String> handleOfLastXArea(List<String> xArea) {
        //处理xArea最后一个点刚好为下一天凌晨0点的
        if (xArea != null && xArea.size() > 1) {
            if (xArea.get(xArea.size() - 1).contains(DateHelper.DEFUALT_TIME_START)) {
                int flag = DateHelper.dateCompareGetDay(xArea.get(xArea.size() - 1), xArea.get(xArea.size() - 2));
                if (flag > 0) {
                    String tempDt = DateHelper.plusDate(xArea.get(xArea.size() - 1), -1);
                    xArea.set(xArea.size() - 1, tempDt + " 24:00:00");
                } else {
                    xArea.get(xArea.size() - 1).replaceAll(DateHelper.DEFUALT_TIME_START, " 24:00:00");
                }
            }
        }
        return xArea;
    }

    /**
     * 计算血糖事件数量&总持续时间
     */
    private Map<String, Object> handleOfEventCountAndDtCount(Integer type, Byte level, Byte flagLevel, Double value, Double flagValue, DYYPBloodSugarPO paramLogOfYPPO, Boolean outFlag, String outSDt, String outEDt, Integer outEventCount, Long outDtCount) {
        Map<String, Object> resultMap = new HashMap<>(5);
        String recordTimeStr = DateHelper.getDate(paramLogOfYPPO.getRecordTime(), DateHelper.DATETIME_FORMAT);
        if (STATISTICS_REPORT_TYPE_1.equals(type)) {
            //按血糖等级
            if (level == null || flagLevel == null) {
                return resultMap;
            }
            if (flagLevel.equals(level)) {
                if (!outFlag) {
                    outEventCount++;
                    outSDt = recordTimeStr;
                    outFlag = true;
                } else {
                    outEDt = recordTimeStr;
                }
            } else {
                if (outFlag) {
                    outEDt = recordTimeStr;
                    outDtCount += DateHelper.getDate(outEDt, DateHelper.DATETIME_FORMAT).getTime() -
                            DateHelper.getDate(outSDt, DateHelper.DATETIME_FORMAT).getTime();
                    outFlag = false;
                    outSDt = null;
                    outEDt = null;
                }
            }
        } else {
            //按血糖值
            if (value == null || flagValue == null) {
                return resultMap;
            }
            if (value < flagValue) {
                if (!outFlag) {
                    outSDt = recordTimeStr;
                    outFlag = true;
                } else {
                    outEDt = recordTimeStr;
                }
            } else {
                if (outFlag) {
                    outEDt = recordTimeStr;
                    outDtCount += DateHelper.getDate(outEDt, DateHelper.DATETIME_FORMAT).getTime() -
                            DateHelper.getDate(outSDt, DateHelper.DATETIME_FORMAT).getTime();
                    outFlag = false;
                    outSDt = null;
                    outEDt = null;
                }
            }
        }
        resultMap.put("outFlag", outFlag);
        resultMap.put("outSDt", outSDt);
        resultMap.put("outEDt", outEDt);
        resultMap.put("outEventCount", outEventCount);
        resultMap.put("outDtCount", outDtCount);
        return resultMap;
    }

    /**
     * 计算标准差
     *
     * @param arr
     * @return
     * @author 李左河
     * @date 2018年3月21日 上午11:25:45
     */
    private Double getStandardValue(Double... arr) {
        if (arr.length <= 1) {
            return 0D;
        }
        Double avg = getAvg(arr);
        Double sum = 0D;
        for (Double d : arr) {
            sum += Math.pow((d - avg), 2);
        }
        Double standardValue = new BigDecimal(sum.toString())
                .divide(new BigDecimal(arr.length - 1), 2, BigDecimal.ROUND_DOWN)
                .doubleValue();
        return new BigDecimal(Math.sqrt(standardValue))
                .setScale(1, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 计算方差
     *
     * @param arr
     * @return
     * @author 李左河
     * @date 2018年3月21日 上午11:25:52
     */
    private Double getVariance(Double... arr) {
        Double variance = 0D;
        Double sum = 0D;
        Double avg = getAvg(arr);
        Integer n = 0;
        for (Double d : arr) {
            sum += d * d - 2 * d * avg + avg * avg;
            n++;
        }
        if (n > 0) {
            variance = sum / n;
        }
        return variance;
    }

    /**
     * 计算平均值
     *
     * @param arr
     * @return
     * @author 李左河
     * @date 2018年3月21日 上午11:25:59
     */
    private Double getAvg(Double... arr) {
        Double avg = 0D;
        Double sum = getSum(arr);
        Integer n = arr.length;
        if (n > 0) {
            BigDecimal bigDecimal = new BigDecimal(sum.toString());
            avg = bigDecimal
                    .divide(new BigDecimal(n), 2, BigDecimal.ROUND_DOWN)
                    .doubleValue();
        }
        return avg;
    }

    /**
     * 求和
     *
     * @param arr
     * @return
     * @author 李左河
     * @date 2018年3月21日 上午11:26:05
     */
    private Double getSum(Double... arr) {
        Double sum = 0D;
        for (Double d : arr) {
            sum += d;
        }
        return sum;
    }

    /**
     * 获取平均血糖波动幅度
     *
     * @param SDBG
     * @param array
     * @return
     */
    private Double getMAGE(Double SDBG, Double[] array) {
        List<Double> AGES = this.listValidAGE(SDBG, array);
        if (AGES != null && AGES.size() > 0) {
            Double sum = 0D;
            for (Double AGE : AGES) {
                sum += AGE;
            }
            return sum / AGES.size();
        }
        return 0D;
    }

    /**
     * 获取有效波动幅度列表
     *
     * @param SDBG
     * @param array
     * @return
     */
    private List<Double> listValidAGE(Double SDBG, Double[] array) {
        Map<String, List<Double>> map = this.listWaveAndTrough(array);
        List<Double> waves = map.get("waves");
        List<Double> troughs = map.get("troughs");
        List<Double> ages = new ArrayList<>();
        if (waves != null && waves.size() > 0 && troughs != null && troughs.size() > 0) {
            for (int i = 0; i < waves.size(); i++) {
                double wave = waves.get(i);
                if (i < troughs.size()) {
                    double age = wave - troughs.get(i);
                    if (age > SDBG) {
                        ages.add(age);
                    }
                }
            }
        }
        return ages;
    }

    /**
     * 获取数组的波峰和波谷
     *
     * @param array
     * @return
     */
    private Map<String, List<Double>> listWaveAndTrough(Double[] array) {
        Map<String, List<Double>> resultMap = new HashMap<>(2);
        if (array.length < 2) {
            return resultMap;
        }
        // 默认第一个数字大于0的假设为波峰，方向向下（-1）；小于等于0假设为波谷，方向向上（1）；
        int direction = array[0] > 0 ? -1 : 1;
        List<Double> waves = new ArrayList<>();
        List<Double> troughs = new ArrayList<>();
        for (int i = 0; i < array.length - 1; i++) {
            if ((array[i + 1] - array[i]) * direction > 0) {
                direction *= -1;
                if (direction == 1) {
//                    System.out.println("("+i+","+array[i]+")"+"波峰");
                    waves.add(array[i]);
                } else {
//                    System.out.println("("+i+","+array[i]+")"+"波谷");
                    troughs.add(array[i]);
                }
            }
        }
        resultMap.put("waves", waves);
        resultMap.put("troughs", troughs);
        return resultMap;
    }

    /**
     * 说明：百分位数，统计学术语，如果将一组数据从小到大排序，并计算相应的累计百分位，
     * 则某一百分位所对应数据的值就称为这一百分位的百分位数。
     * 可表示为：一组n个观测值按数值大小排列。如，处于p%位置的值称第p百分位数
     *
     * @param data
     * @param p
     * @return
     */
    private double percentile(Double[] data, double p) {
        int n = data.length;
        Arrays.sort(data);
        double px = p * (n - 1);
        int i = (int) Math.floor(px);
        double g = px - i;
        if (g == 0) {
            return data[i];
        } else {
            return (1 - g) * data[i] + g * data[i + 1];
        }
    }

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

    @Override
    public JSONObject loadSensorBloodSugarChart(DyStaticsDTO dto) {
        String sensorNo = dto.getSensorNo();
        Integer chartType = dto.getType();
        String endDt = dto.getEndDt();
        String startDt = dto.getStartDt();
        StatisticsYPParamLogOfGAPPVO model = null;
        // 获取日数
        Integer diffDay = -1;
        if (!StringUtils.isBlank(startDt) && !StringUtils.isBlank(endDt)) {
            // 两个时间相等-实际相差23小时59分59秒约1天
            if (endDt.equals(startDt)) {
                diffDay = DAY_YP_1;
            } else {
                diffDay = DateHelper.dateCompareGetDay(endDt, startDt);
            }
        }
        //时间范围必须选择,且开始时间不能大于结束时间
        if (diffDay < 0) {
            throw new BusinessException("时间范围必须选择,且开始时间不能大于结束时间");
        }

        // 从预存统计表获取
        model = this.getStatisticsYPParamLogOfGAPPModel(endDt, diffDay, chartType, sensorNo);
        boolean isHandle = false;
        // 预存统计表是否存在-有则判断是否需要重新计算生成
        if (model == null) {
            //预存统计表里不存在-需要重新计算
            isHandle = true;
        } else {
            //计算更新，保证统计报表永远是最新的
            List<String> recordDts = new ArrayList<>(1);
            recordDts.add(endDt);
            StatisticsOfYPTask statisticsOfYPTask = new StatisticsOfYPTask();
            statisticsOfYPTask.setStatisticsService(this);
            statisticsOfYPTask.setParamOfRecordDts(recordDts);
            statisticsOfYPTask.setSensorNo(sensorNo);
            statisticsOfYPTask.setChartType(dto.getType());
            Thread thread = new Thread(statisticsOfYPTask);
            thread.start();
        }

        // 是否需要重新计算-true 是 false 否
        if (isHandle) {
            //预存统计表内容非最新或没有，则重新计算，并保存
            model = this.handleOfStatisticsYPParamLogWithLock(diffDay, chartType, endDt, sensorNo);
        }

        // 返回
        String tempJson = JSONObject.toJSONString(model);
        return JSONObject.parseObject(tempJson);
    }

    /**
     * @param endDt
     * @param dayNum
     * @param type
     * @param sensorNo
     * @return
     */
    private StatisticsYPParamLogOfGAPPVO getStatisticsYPParamLogOfGAPPModel(String endDt, int dayNum, int type, String sensorNo) {
        DYYPStatisticsPOWithBLOBs statisticsYPParamLog = this.getStatisticsYPParamLogOfGAPP(endDt, dayNum, type, sensorNo);
        if (statisticsYPParamLog != null) {
            StatisticsYPParamLogOfGAPPVO resultModel = new StatisticsYPParamLogOfGAPPVO();
            if (STATISTICS_REPORT_TYPE_1.equals(type) || STATISTICS_REPORT_TYPE_2.equals(type)) {
                //返回对象
                resultModel = new StatisticsYPParamLogOfGAPPVO();
                resultModel.setAvgNum(statisticsYPParamLog.getAvgNum().toString());
                resultModel.setGhb(statisticsYPParamLog.getGhb().toString());
                resultModel.setEventCountOfLow(statisticsYPParamLog.getEventCountOfLow());
                resultModel.setAvgAwiTimeOfLow(statisticsYPParamLog.getAvgAwiTimeOfLow());
                resultModel.setStandardVal(statisticsYPParamLog.getStandardVal());
                resultModel.setEventCountOfHigh(statisticsYPParamLog.getEventCountOfHigh());
                resultModel.setAvgAwiTimeOfHigh(Double.parseDouble(statisticsYPParamLog.getAvgAwiTimeOfHigh()));
                resultModel.setEventCountOfNormal(statisticsYPParamLog.getEventCountOfNormal());
                resultModel.setAvgAwiTimeOfNormal(Double.parseDouble(statisticsYPParamLog.getAvgAwiTimeOfNormal()));
                resultModel.setAwiTimeRateOfLow(statisticsYPParamLog.getAwiTimeRateOfLow());
                resultModel.setAwiTimeRateOfHigh(statisticsYPParamLog.getAwiTimeRateOfHigh());
                resultModel.setAwiTimeRateOfNormal(statisticsYPParamLog.getAwiTimeRateOfNormal());
                resultModel.setCoefficientOfVariation(statisticsYPParamLog.getCoefficientOfVariation());
                resultModel.setAwiTimeRateOf3_9(statisticsYPParamLog.getAwiTimeRateOf39());
                resultModel.setAwiTimeRateOf4_0(statisticsYPParamLog.getAwiTimeRateOf40());
                resultModel.setAwiTimeRateOf13_9(statisticsYPParamLog.getAwiTimeRateOf139());
                resultModel.setMeanAmplitudeOfGlycemicExcursion(statisticsYPParamLog.getMeanAmplitudeOfGlycemicExcursion());
                resultModel.setChartData(JSONObject.parseObject(statisticsYPParamLog.getChartData(), YPCharDataOfXYVO.class));
                if (resultModel.getChartData() != null && resultModel.getChartData().getXArea() != null && !resultModel.getChartData().getXArea().isEmpty()) {
                    resultModel.setChartShow(1);
                } else {
                    resultModel.setChartShow(0);
                }
                if (STATISTICS_REPORT_TYPE_1.equals(type)) {
                    resultModel.setRecordDt(DateHelper.getDate(statisticsYPParamLog.getStartDt(), DateHelper.DAY_FORMAT));
                    List<String> listDateOf14Day = new ArrayList<>(DAY_YP_14);
                    for (int i = 0; i < DAY_YP_14; i++) {
                        listDateOf14Day.add(DateHelper.plusDate(endDt, i * -1));
                    }
                    resultModel.setDateOfLast14Day(listDateOf14Day);
                } else {
                    resultModel.setRecordDt(DateHelper.plusDate(endDt, -DAY_YP_14, DateHelper.DAY_FORMAT) + "~" + statisticsYPParamLog.getStartDt());
                }
            } else if (STATISTICS_REPORT_TYPE_3.equals(type)) {
                //返回对象
                resultModel = new StatisticsYPParamLogOfGAPPVO();
                resultModel.setDaySugarAvgDiffValMap(JSONArray.parseObject(statisticsYPParamLog.getDaySugarAvgDiffValJson(), List.class));
                if (resultModel.getDaySugarAvgDiffValMap() != null && !resultModel.getDaySugarAvgDiffValMap().isEmpty()) {
                    resultModel.setChartShow(1);
                } else {
                    resultModel.setChartShow(0);
                }
            } else if (STATISTICS_REPORT_TYPE_4.equals(type)) {
                //返回对象
                resultModel = new StatisticsYPParamLogOfGAPPVO();
                resultModel.setListChartData(JSONArray.parseObject(statisticsYPParamLog.getChartData(), List.class));
                if (resultModel.getListChartData() != null && !resultModel.getListChartData().isEmpty()) {
                    resultModel.setChartShow(1);
                } else {
                    resultModel.setChartShow(0);
                }
            }
            resultModel.setSid(statisticsYPParamLog.getSid());

            DYYPStatisticsAdvicePO advicePO = this.dyypStatisticsAdvicePOMapper.getStatisticAdviceByStatisticsId(statisticsYPParamLog.getSid());
            if (advicePO != null) {
                resultModel.setAdContent(advicePO.getContent());
                resultModel.setDoctorId(advicePO.getDoctorId().toString());
            } else {
                String sid = ComveeMd5Util.md5("no=" + sensorNo + ",type=" + type + ",dayNum=" + dayNum);
                advicePO = this.dyypStatisticsAdvicePOMapper.getStatisticAdviceByStatisticsId(sid);
                if (advicePO != null) {
                    resultModel.setAdContent(advicePO.getContent());
                    resultModel.setDoctorId(advicePO.getDoctorId().toString());
                }
            }

            resultModel.setReportType(type);
            return resultModel;
        }
        return null;
    }

    @Override
    public JSONObject loadSensorBloodSugarChartOfWeb(DyStaticsDTO dto) {
        String startDt = dto.getStartDt();
        String endDt = dto.getEndDt();
        String sensorNo = dto.getSensorNo();
        Integer type = dto.getType();
        JSONObject resultMap = new JSONObject();
        resultMap.put("type", type);
        Integer dayNum = -1;
        if (!StringUtils.isBlank(startDt) && !StringUtils.isBlank(endDt)) {
            dayNum = DateHelper.dateCompareGetDay(endDt, startDt);
            // 两个时间相等-实际相差23小时59分59秒约1天
            if (dayNum == 0) {
                dayNum = 1;
            }
        }
        //时间范围必须选择，且开始时间不能大于结束时间
        if (dayNum < 0) {
            throw new BusinessException("时间范围必须选择,且开始时间不能大于结束时间");
        }

        //随机血糖控制目标
        String lowStr = "3.9";
        String highStr = "10.0";

        //日趋势列表
        if (STATISTICS_REPORT_TYPE_1.equals(type)) {
            resultMap.put("name", "日趋势图");
            List<StatisticsYPParamLogOfWebVO> listChart = new ArrayList<>(dayNum);
            String endDt1 = new String(endDt);
            for (int day = 0; day < dayNum; day++) {
                StatisticsYPParamLogOfWebVO webModel = new StatisticsYPParamLogOfWebVO();
                Integer chartShow = 0;
                //开始时间
                String startDt1 = new String(endDt1);
                /**业务数据计算处理开始**/
                //血糖值和
                Double sum = 0D;
                //血糖记录列表
                List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService.listDataSourceOfYPParamLogOfOBDTASC(startDt1, endDt1, sensorNo);

                for (DYYPBloodSugarPO dyypBloodSugarPO : paramLogOfYPPOS) {
                    BigDecimal decimal = dyypBloodSugarPO.getValue();
                    if (decimal != null) {
                        sum += decimal.doubleValue();
                    }
                }
                //血糖记录数量
                int logCount = paramLogOfYPPOS.size();
                //低血糖，高血糖，正常血糖血糖事件次数
                Integer lowEventCount = 0, highEventCount = 0, normalEventCount = 0;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始/结束标志 true 开始，false 结束
                Boolean hasLowItem = false, hasHighItem = false, hasNormalItem = false, hasLogItemOf3_9 = false,
                        hasLogItemOf4_0 = false, hasLogItemOf13_9 = false;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始时间标志
                String lowEventLogSDt = null, highEventLogSDt = null, normalEventLogSDt = null, eventLogSDtOf3_9 = null,
                        eventLogSDtOf4_0 = null, eventLogSDtOf13_9 = null;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件结束时间标志
                String lowEventLogEDt = null, highEventLogEDt = null, normalEventLogEDt = null, eventLogEDtOf3_9 = null,
                        eventLogEDtOf4_0 = null, eventLogEDtOf13_9 = null;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件总持续时间
                Long lowEventDtCount = 0L, highEventDtCount = 0L, normalEventDtCount = 0L, eventDtCountOf3_9 = 0L, eventDtCountOf4_0 = 0L, eventDtCountOf13_9 = 0L;
                //血糖记录持续时间
                Long ptDtCount = 0L;
                if (logCount > 0) {
                    ptDtCount = paramLogOfYPPOS.get(logCount - 1).getRecordTime().getTime() -
                            paramLogOfYPPOS.get(0).getRecordTime().getTime();
                }
                //血糖值数组
                Double[] arr = null;
                if (logCount > 0) {
                    arr = new Double[logCount];
                }

                //持续时间&统计&求和-计算
                this.calculationHandle(hasLowItem, lowEventLogSDt, lowEventLogEDt, lowEventCount, lowEventDtCount,
                        hasHighItem, highEventLogSDt, highEventLogEDt, highEventCount, highEventDtCount,
                        hasNormalItem, normalEventLogSDt, normalEventLogEDt, normalEventCount, normalEventDtCount,
                        hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9, eventDtCountOf3_9,
                        hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0, eventDtCountOf4_0,
                        hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9, eventDtCountOf13_9,
                        arr, sum,
                        logCount, paramLogOfYPPOS);

                //处理血糖列表只有低血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                lowEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasLowItem, lowEventLogSDt, lowEventLogEDt);
                //处理血糖列表只有高血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                highEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasHighItem, highEventLogSDt, highEventLogEDt);
                //处理血糖列表只有正常血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                normalEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasNormalItem, normalEventLogSDt, normalEventLogEDt);
                //处理血糖列表只有低3.9的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf3_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9);
                //处理血糖列表只有低4.0的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf4_0 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0);
                //处理血糖列表只有低13.9的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf13_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9);
                //数值格式化对象
                DecimalFormat df = new DecimalFormat("#0.0");
                //低血糖范围持续时间占比
                String awiTimeRateOfLow = "--";
                //高血糖范围持续时间占比
                String awiTimeRateOfHigh = "--";
                //正常血糖范围持续时间占比（3.9-10）
                String awiTimeRateOfNormal = "--";
                //低3.9,低4.0,低13.9占比
                String awiTimeRateOf3_9 = "--";
                String awiTimeRateOf4_0 = "--";
                String awiTimeRateOf13_9 = "--";
                //达标非达标时间占比计算
                this.calculationRateHandle(awiTimeRateOfLow, awiTimeRateOfHigh, awiTimeRateOfNormal, awiTimeRateOf3_9, awiTimeRateOf4_0, awiTimeRateOf13_9,
                        lowEventDtCount, highEventDtCount, normalEventDtCount, eventDtCountOf3_9, eventDtCountOf4_0, eventDtCountOf13_9, ptDtCount);
                //标准差
                Double standardValue = 0.0D;
                if (arr != null && arr.length > 0) {
                    standardValue = this.getStandardValue(arr);
                }
                //平均血糖值
                Double avg = 0D;
                if (logCount > 0) {
                    avg = sum / logCount;
                }
                //糖化血红蛋白
                Double ghb = new BigDecimal(avg + 2.59)
                        .divide(new BigDecimal("1.59"), 1, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
                //低血糖事件
                Integer eventCountOfLow = lowEventCount;
                //低血糖平均持续时间
                Double avgAwiTimeOfLow = 0d;
                if (eventCountOfLow > 0) {
                    avgAwiTimeOfLow = Double.parseDouble(lowEventDtCount / 1000 / 60 / eventCountOfLow + "");
                }
                //高血糖事件
                Integer eventCountOfHigh = highEventCount;
                //高血糖平均持续时间
                Double avgAwiTimeOfHigh = 0d;
                if (eventCountOfHigh > 0) {
                    avgAwiTimeOfHigh = Double.parseDouble(highEventDtCount / 1000 / 60 / eventCountOfHigh + "");
                }
                //正常血糖事件
                Integer eventCountOfNormal = normalEventCount;
                //正常血糖平均持续时间
                Double avgAwiTimeOfNormal = 0d;
                if (eventCountOfNormal > 0) {
                    avgAwiTimeOfNormal = Double.parseDouble(normalEventDtCount / 1000 / 60 / eventCountOfNormal + "");
                }
                //血糖变异系数
                String coefficientOfVariation = "0.0";
                if (avg != 0) {
                    coefficientOfVariation = df.format(standardValue / avg * 100);
                }
                //平均血糖波动幅度（mean amplitude of glycemic excursion，MAGE）
                Double MAGE = 0.0;
                if (arr != null && arr.length > 0) {
                    MAGE = this.getMAGE(standardValue, arr);
                }
                String meanAmplitudeOfGlycemicExcursion = (MAGE > 0) ? df.format(MAGE) : "--";
                /**业务数据计算处理结束**/
                /**图表处理开始**/
                //血糖日趋势图
                //线性列表
                List<String> pointList = new ArrayList<>(DAY_FULL_COUNT);
                for (int i = 0; i < DAY_FULL_COUNT; i++) {
                    pointList.add("-");
                }
                for (int i = 0; i < logCount; i++) {
                    DYYPBloodSugarPO paramLogOfYPPO = paramLogOfYPPOS.get(i);
                    Double value = paramLogOfYPPO.getValue().doubleValue();
                    Date dateStr = paramLogOfYPPO.getRecordTime();
                    int h = Integer.parseInt(DateHelper.getDate(dateStr, "HH"));
                    int m = Integer.parseInt(DateHelper.getDate(dateStr, "mm"));
                    int index = -1;
                    if (h >= 0) {
                        if (m < 15) {
                            index = 4 * h;
                        } else if (m < 30) {
                            index = 4 * h + 1;
                        } else if (m < 45) {
                            index = 4 * h + 2;
                        } else {
                            index = 4 * h + 3;
                        }
                    }
                    if (index >= 0) {
                        pointList.set(index, value + "");
                        chartShow = 1;
                    }
                    List<DyBloodSugarRemarkPO> remarkList = this.dyBloodSugarService.listBloodSugarRemarkByBid(paramLogOfYPPO.getSid());
                    if (remarkList != null && !remarkList.isEmpty()) {
                        List<String> remarkStringList = remarkList.stream().map(DyBloodSugarRemarkPO::getContent).collect(Collectors.toList());
                        paramLogOfYPPO.setRemark(Joiner.on("、").join(remarkStringList));
                    }
                }
                webModel.setLineData(pointList);
                webModel.setAvgNum(df.format(avg));
                webModel.setEventCountOfLow(eventCountOfLow);
                webModel.setAvgAwiTimeOfLow(df.format(avgAwiTimeOfLow));
                webModel.setStandardVal(df.format(standardValue));
                webModel.setEventCountOfHigh(eventCountOfHigh);
                webModel.setAvgAwiTimeOfHigh(avgAwiTimeOfHigh);
                webModel.setEventCountOfNormal(eventCountOfNormal);
                webModel.setAvgAwiTimeOfNormal(avgAwiTimeOfNormal);
                webModel.setAwiTimeRateOfLow(awiTimeRateOfLow);
                webModel.setAwiTimeRateOfHigh(awiTimeRateOfHigh);
                webModel.setAwiTimeRateOfNormal(awiTimeRateOfNormal);
                webModel.setCoefficientOfVariation(coefficientOfVariation);
                webModel.setAwiTimeRateOf3_9(awiTimeRateOf3_9);
                webModel.setAwiTimeRateOf4_0(awiTimeRateOf4_0);
                webModel.setAwiTimeRateOf13_9(awiTimeRateOf13_9);
                webModel.setMeanAmplitudeOfGlycemicExcursion(meanAmplitudeOfGlycemicExcursion);
                webModel.setLowLineVal(Double.parseDouble(lowStr));
                webModel.setHighLineVal(Double.parseDouble(highStr));
                webModel.setRecordDt(startDt1);
                webModel.setGhb(df.format(ghb));
                webModel.setChartShow(chartShow);
                webModel.setDataSource(paramLogOfYPPOS);
                listChart.add(webModel);
                endDt1 = DateHelper.plusDate(endDt1, -1);
            }
            resultMap.put("list", listChart);
        } else if (STATISTICS_REPORT_TYPE_2.equals(type)) {
            resultMap.put("name", "动态图谱");
            List<StatisticsYPParamLogOfWebVO> listChart = new ArrayList<>(dayNum);
            String endDt1 = endDt;
            for (int day = 0; day < dayNum; day++) {
                int chartShow = 0;
                StatisticsYPParamLogOfWebVO webModel = new StatisticsYPParamLogOfWebVO();
                //开始时间
                String startDt1 = endDt1;
                /**业务数据计算处理开始**/
                //血糖值和
                Double sum = 0D;
                //血糖记录列表
                List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService.listDataSourceOfYPParamLogOfOBDTASC(startDt1, endDt1, sensorNo);
                //血糖记录数量
                int logCount = paramLogOfYPPOS.size();
                //低血糖，高血糖，正常血糖血糖事件次数
                Integer lowEventCount = 0, highEventCount = 0, normalEventCount = 0;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始/结束标志 true 开始，false 结束
                Boolean hasLowItem = false, hasHighItem = false, hasNormalItem = false, hasLogItemOf3_9 = false,
                        hasLogItemOf4_0 = false, hasLogItemOf13_9 = false;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始时间标志
                String lowEventLogSDt = null, highEventLogSDt = null, normalEventLogSDt = null, eventLogSDtOf3_9 = null,
                        eventLogSDtOf4_0 = null, eventLogSDtOf13_9 = null;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件结束时间标志
                String lowEventLogEDt = null, highEventLogEDt = null, normalEventLogEDt = null, eventLogEDtOf3_9 = null,
                        eventLogEDtOf4_0 = null, eventLogEDtOf13_9 = null;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件总持续时间
                Long lowEventDtCount = 0L, highEventDtCount = 0L, normalEventDtCount = 0L, eventDtCountOf3_9 = 0L, eventDtCountOf4_0 = 0L, eventDtCountOf13_9 = 0L;
                //血糖记录持续时间
                Long ptDtCount = 0L;
                if (logCount > 0) {
                    ptDtCount = paramLogOfYPPOS.get(logCount - 1).getRecordTime().getTime() -
                            paramLogOfYPPOS.get(0).getRecordTime().getTime();
                    chartShow = 1;
                }
                //血糖值数组
                Double[] arr = null;
                if (logCount > 0) {
                    arr = new Double[logCount];
                }

                //持续时间&统计&求和-计算
                this.calculationHandle(hasLowItem, lowEventLogSDt, lowEventLogEDt, lowEventCount, lowEventDtCount,
                        hasHighItem, highEventLogSDt, highEventLogEDt, highEventCount, highEventDtCount,
                        hasNormalItem, normalEventLogSDt, normalEventLogEDt, normalEventCount, normalEventDtCount,
                        hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9, eventDtCountOf3_9,
                        hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0, eventDtCountOf4_0,
                        hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9, eventDtCountOf13_9,
                        arr, sum,
                        logCount, paramLogOfYPPOS);

                //处理血糖列表只有低血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                lowEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasLowItem, lowEventLogSDt, lowEventLogEDt);
                //处理血糖列表只有高血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                highEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasHighItem, highEventLogSDt, highEventLogEDt);
                //处理血糖列表只有正常血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                normalEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasNormalItem, normalEventLogSDt, normalEventLogEDt);
                //处理血糖列表只有低3.9的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf3_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9);
                //处理血糖列表只有低4.0的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf4_0 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0);
                //处理血糖列表只有低13.9的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf13_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9);
                //数值格式化对象
                DecimalFormat df = new DecimalFormat("#0.0");
                //低血糖范围持续时间占比
                String awiTimeRateOfLow = "--";
                //高血糖范围持续时间占比
                String awiTimeRateOfHigh = "--";
                //正常血糖范围持续时间占比（3.9-10）
                String awiTimeRateOfNormal = "--";
                //低3.9,低4.0,低13.9占比
                String awiTimeRateOf3_9 = "--";
                String awiTimeRateOf4_0 = "--";
                String awiTimeRateOf13_9 = "--";
                //达标非达标时间占比计算
                this.calculationRateHandle(awiTimeRateOfLow, awiTimeRateOfHigh, awiTimeRateOfNormal, awiTimeRateOf3_9, awiTimeRateOf4_0, awiTimeRateOf13_9,
                        lowEventDtCount, highEventDtCount, normalEventDtCount, eventDtCountOf3_9, eventDtCountOf4_0, eventDtCountOf13_9, ptDtCount);
                //标准差
                Double standardValue = 0.0D;
                if (arr != null && arr.length > 0) {
                    standardValue = this.getStandardValue(arr);
                }
                //平均血糖值
                Double avg = 0D;
                if (logCount > 0) {
                    avg = sum / logCount;
                }
                //糖化血红蛋白
                Double ghb = new BigDecimal(avg + 2.59)
                        .divide(new BigDecimal("1.59"), 1, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
                //低血糖事件
                Integer eventCountOfLow = lowEventCount;
                //低血糖平均持续时间
                Double avgAwiTimeOfLow = 0d;
                if (eventCountOfLow > 0) {
                    avgAwiTimeOfLow = Double.parseDouble(lowEventDtCount / 1000 / 60 / eventCountOfLow + "");
                }
                //高血糖事件
                Integer eventCountOfHigh = highEventCount;
                //高血糖平均持续时间
                Double avgAwiTimeOfHigh = 0d;
                if (eventCountOfHigh > 0) {
                    avgAwiTimeOfHigh = Double.parseDouble(highEventDtCount / 1000 / 60 / eventCountOfHigh + "");
                }
                //正常血糖事件
                Integer eventCountOfNormal = normalEventCount;
                //正常血糖平均持续时间
                Double avgAwiTimeOfNormal = 0d;
                if (eventCountOfNormal > 0) {
                    avgAwiTimeOfNormal = Double.parseDouble(normalEventDtCount / 1000 / 60 / eventCountOfNormal + "");
                }
                //血糖变异系数
                String coefficientOfVariation = "0.0";
                if (avg != 0) {
                    coefficientOfVariation = df.format(standardValue / avg * 100);
                }
                //平均血糖波动幅度（mean amplitude of glycemic excursion，MAGE）
                Double MAGE = 0.0;
                if (arr != null && arr.length > 0) {
                    MAGE = this.getMAGE(standardValue, arr);
                }
                String meanAmplitudeOfGlycemicExcursion = (MAGE > 0) ? df.format(MAGE) : "--";
                /**业务数据计算处理结束**/
                webModel.setAvgNum(df.format(avg));
                webModel.setGhb(df.format(ghb));
                webModel.setEventCountOfLow(eventCountOfLow);
                webModel.setAvgAwiTimeOfLow(df.format(avgAwiTimeOfLow));
                webModel.setStandardVal(df.format(standardValue));
                webModel.setEventCountOfHigh(eventCountOfHigh);
                webModel.setAvgAwiTimeOfHigh(avgAwiTimeOfHigh);
                webModel.setEventCountOfNormal(eventCountOfNormal);
                webModel.setAvgAwiTimeOfNormal(avgAwiTimeOfNormal);
                webModel.setAwiTimeRateOfLow(awiTimeRateOfLow);
                webModel.setAwiTimeRateOfHigh(awiTimeRateOfHigh);
                webModel.setAwiTimeRateOfNormal(awiTimeRateOfNormal);
                webModel.setCoefficientOfVariation(coefficientOfVariation);
                webModel.setAwiTimeRateOf3_9(awiTimeRateOf3_9);
                webModel.setAwiTimeRateOf4_0(awiTimeRateOf4_0);
                webModel.setAwiTimeRateOf13_9(awiTimeRateOf13_9);
                webModel.setMeanAmplitudeOfGlycemicExcursion(meanAmplitudeOfGlycemicExcursion);
                webModel.setLowLineVal(Double.parseDouble(lowStr));
                webModel.setHighLineVal(Double.parseDouble(highStr));
                webModel.setRecordDt(startDt1);
                webModel.setDataSource(paramLogOfYPPOS);
                webModel.setChartShow(chartShow);
                listChart.add(webModel);
                endDt1 = DateHelper.plusDate(endDt1, -1);
            }
            resultMap.put("list", listChart);
            /**图表处理开始**/
            //动态血糖图
            List<List<String>> pointlistmin = new ArrayList<>(2);
            List<List<String>> pointlistmax = new ArrayList<>(2);
            //10%曲线
            List<String> curveOfTenPercent = new ArrayList<>(DAY_FULL_COUNT);
            for (int i = 0; i < DAY_FULL_COUNT; i++) {
                curveOfTenPercent.add("-");
            }
            //25%曲线
            List<String> curveOfTwentyFivePercent = new ArrayList<>(DAY_FULL_COUNT);
            for (int i = 0; i < DAY_FULL_COUNT; i++) {
                curveOfTwentyFivePercent.add("-");
            }
            //50%曲线
            List<String> curveOfFiftyPercent = new ArrayList<>(DAY_FULL_COUNT);
            for (int i = 0; i < DAY_FULL_COUNT; i++) {
                curveOfFiftyPercent.add("-");
            }
            //75%曲线
            List<String> curveOfSeventyFivePercent = new ArrayList<>(DAY_FULL_COUNT);
            for (int i = 0; i < DAY_FULL_COUNT; i++) {
                curveOfSeventyFivePercent.add("-");
            }
            //90%曲线
            List<String> curveOfNinetyPercent = new ArrayList<>(DAY_FULL_COUNT);
            for (int i = 0; i < DAY_FULL_COUNT; i++) {
                curveOfNinetyPercent.add("-");
            }
            Integer chartShow = 0;
            //时间点
            List<String> times = this.dyBloodSugarService.listMemberTimeOfRecordLog(startDt, endDt, sensorNo);
            if (times != null && times.size() > 0) {
                for (String time : times) {
                    //数据源
                    List<DYYPBloodSugarPO> yppos = this.dyBloodSugarService.listDataSourceOfYPParamLogOfTimeASC(time, startDt, endDt, sensorNo);
                    if (yppos != null && yppos.size() > 4) {
                        int size = yppos.size();
                        Double[] dataArray = new Double[size];
                        String recordTime = DateHelper.getDate(yppos.get(0).getRecordTime(), DateHelper.DATETIME_FORMAT);
                        recordTime = endDt + " " +
                                DateHelper.getDate(DateHelper.getDate(recordTime, DateHelper.DATETIME_FORMAT), "HH:mm:ss");
                        for (int i = 0; i < size; i++) {
                            DYYPBloodSugarPO paramLogOfYPPO = yppos.get(i);
                            Double value = paramLogOfYPPO.getValue().doubleValue();
                            if (value == null) {
                                continue;
                            }
                            dataArray[i] = value;
                        }
                        int h = Integer.parseInt(DateHelper.getDate(DateHelper.getDate(recordTime, DateHelper.DATETIME_FORMAT), "HH"));
                        int m = Integer.parseInt(DateHelper.getDate(DateHelper.getDate(recordTime, DateHelper.DATETIME_FORMAT), "mm"));
                        int index = -1;
                        if (h >= 0) {
                            if (m < 15) {
                                index = 4 * h;
                            } else if (m < 30) {
                                index = 4 * h + 1;
                            } else if (m < 45) {
                                index = 4 * h + 2;
                            } else {
                                index = 4 * h + 3;
                            }
                        }
                        if (index >= 0) {
                            //0.1
                            double val1 = percentile(dataArray, 0.1);
                            curveOfTenPercent.set(index, val1 + "");

                            //0.25
                            double val2 = percentile(dataArray, 0.25);
                            curveOfTwentyFivePercent.set(index, val2 + "");

                            //0.5
                            double val3 = percentile(dataArray, 0.5);
                            curveOfFiftyPercent.set(index, val3 + "");

                            //0.75
                            double val4 = percentile(dataArray, 0.75);
                            curveOfSeventyFivePercent.set(index, val4 + "");

                            //0.9
                            double val5 = percentile(dataArray, 0.9);
                            curveOfNinetyPercent.set(index, val5 + "");

                            chartShow = 1;
                        }
                    }
                }
                pointlistmin.add(curveOfTenPercent);
                pointlistmin.add(curveOfTwentyFivePercent);

                pointlistmax.add(curveOfNinetyPercent);
                pointlistmax.add(curveOfSeventyFivePercent);
            }
            resultMap.put("pointlistmax", pointlistmax);
            resultMap.put("zwxlist", curveOfFiftyPercent);
            resultMap.put("pointlistmin", pointlistmin);
            resultMap.put("chartShow", chartShow);
        } else if (STATISTICS_REPORT_TYPE_4.equals(type)) {
            resultMap.put("name", "每日血糖总结");
            List<StatisticsYPParamLogOfWebVO> listChart = new ArrayList<>(dayNum);
            String endDt1 = endDt;
            for (int day = 0; day < dayNum; day++) {
                StatisticsYPParamLogOfWebVO webModel = new StatisticsYPParamLogOfWebVO();
                Integer chartShow = 0;
                //开始时间
                String startDt1 = endDt1;
                /**业务数据计算处理开始**/
                //血糖值和
                Double sum = 0D;
                //血糖记录列表
                List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService.listDataSourceOfYPParamLogOfOBDTASC(startDt1, endDt1, sensorNo);
                //血糖记录数量
                int logCount = paramLogOfYPPOS.size();
                //低血糖，高血糖，正常血糖血糖事件次数
                Integer lowEventCount = 0, highEventCount = 0, normalEventCount = 0;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始/结束标志 true 开始，false 结束
                Boolean hasLowItem = false, hasHighItem = false, hasNormalItem = false, hasLogItemOf3_9 = false,
                        hasLogItemOf4_0 = false, hasLogItemOf13_9 = false;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始时间标志
                String lowEventLogSDt = null, highEventLogSDt = null, normalEventLogSDt = null, eventLogSDtOf3_9 = null,
                        eventLogSDtOf4_0 = null, eventLogSDtOf13_9 = null;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件结束时间标志
                String lowEventLogEDt = null, highEventLogEDt = null, normalEventLogEDt = null, eventLogEDtOf3_9 = null,
                        eventLogEDtOf4_0 = null, eventLogEDtOf13_9 = null;
                //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件总持续时间
                Long lowEventDtCount = 0L, highEventDtCount = 0L, normalEventDtCount = 0L, eventDtCountOf3_9 = 0L, eventDtCountOf4_0 = 0L, eventDtCountOf13_9 = 0L;
                //血糖记录持续时间
                Long ptDtCount = 0L;
                if (logCount > 0) {
                    ptDtCount = paramLogOfYPPOS.get(logCount - 1).getRecordTime().getTime() -
                            paramLogOfYPPOS.get(0).getRecordTime().getTime();
                }
                //血糖值数组
                Double[] arr = null;
                if (logCount > 0) {
                    arr = new Double[logCount];
                }

                //持续时间&统计&求和-计算
                this.calculationHandle(hasLowItem, lowEventLogSDt, lowEventLogEDt, lowEventCount, lowEventDtCount,
                        hasHighItem, highEventLogSDt, highEventLogEDt, highEventCount, highEventDtCount,
                        hasNormalItem, normalEventLogSDt, normalEventLogEDt, normalEventCount, normalEventDtCount,
                        hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9, eventDtCountOf3_9,
                        hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0, eventDtCountOf4_0,
                        hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9, eventDtCountOf13_9,
                        arr, sum,
                        logCount, paramLogOfYPPOS);

                //处理血糖列表只有低血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                lowEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasLowItem, lowEventLogSDt, lowEventLogEDt);
                //处理血糖列表只有高血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                highEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasHighItem, highEventLogSDt, highEventLogEDt);
                //处理血糖列表只有正常血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
                normalEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasNormalItem, normalEventLogSDt, normalEventLogEDt);
                //处理血糖列表只有低3.9的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf3_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9);
                //处理血糖列表只有低4.0的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf4_0 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0);
                //处理血糖列表只有低13.9的情况，把低血糖结束时间设置最后一次血糖记录时间
                eventDtCountOf13_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9);
                //数值格式化对象
                DecimalFormat df = new DecimalFormat("#0.00");
                //低血糖范围持续时间占比
                String awiTimeRateOfLow = "--";
                //高血糖范围持续时间占比
                String awiTimeRateOfHigh = "--";
                //正常血糖范围持续时间占比（3.9-10）
                String awiTimeRateOfNormal = "--";
                //低3.9,低4.0,低13.9占比
                String awiTimeRateOf3_9 = "--";
                String awiTimeRateOf4_0 = "--";
                String awiTimeRateOf13_9 = "--";
                //达标非达标时间占比计算
                this.calculationRateHandle(awiTimeRateOfLow, awiTimeRateOfHigh, awiTimeRateOfNormal, awiTimeRateOf3_9, awiTimeRateOf4_0, awiTimeRateOf13_9,
                        lowEventDtCount, highEventDtCount, normalEventDtCount, eventDtCountOf3_9, eventDtCountOf4_0, eventDtCountOf13_9, ptDtCount);
                //标准差
                Double standardValue = 0.0D;
                if (arr != null && arr.length > 0) {
                    standardValue = this.getStandardValue(arr);
                }
                //平均血糖值
                Double avg = 0D;
                if (logCount > 0) {
                    avg = sum / logCount;
                }
                //糖化血红蛋白
                Double ghb = new BigDecimal(avg + 2.59)
                        .divide(new BigDecimal("1.59"), 1, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
                //低血糖事件
                Integer eventCountOfLow = lowEventCount;
                //低血糖平均持续时间
                Double avgAwiTimeOfLow = 0d;
                if (eventCountOfLow > 0) {
                    avgAwiTimeOfLow = Double.parseDouble(lowEventDtCount / 1000 / 60 / eventCountOfLow + "");
                }
                //高血糖事件
                Integer eventCountOfHigh = highEventCount;
                //高血糖平均持续时间
                Double avgAwiTimeOfHigh = 0d;
                if (eventCountOfHigh > 0) {
                    avgAwiTimeOfHigh = Double.parseDouble(highEventDtCount / 1000 / 60 / eventCountOfHigh + "");
                }
                //正常血糖事件
                Integer eventCountOfNormal = normalEventCount;
                //正常血糖平均持续时间
                Double avgAwiTimeOfNormal = 0d;
                if (eventCountOfNormal > 0) {
                    avgAwiTimeOfNormal = Double.parseDouble(normalEventDtCount / 1000 / 60 / eventCountOfNormal + "");
                }
                //血糖变异系数
                String coefficientOfVariation = "0.0";
                if (avg != 0) {
                    coefficientOfVariation = df.format(standardValue / avg * 100);
                }
                //平均血糖波动幅度（mean amplitude of glycemic excursion，MAGE）
                Double MAGE = 0.0;
                if (arr != null && arr.length > 0) {
                    MAGE = this.getMAGE(standardValue, arr);
                }
                String meanAmplitudeOfGlycemicExcursion = (MAGE > 0) ? df.format(MAGE) : "--";
                /**业务数据计算处理结束**/
                /**图表处理开始**/
                //血糖日趋势图
                //线性列表
                List<List<Object>> pointList = new ArrayList<>(DAY_FULL_COUNT);
                for (int i = 0; i < DAY_FULL_COUNT; i++) {
                    List<Object> pointItem = new ArrayList<>(2);
                    pointItem.add(i);
                    pointItem.add("-");
                    pointList.add(pointItem);
                }

                for (int i = 0; i < logCount; i++) {
                    DYYPBloodSugarPO paramLogOfYPPO = paramLogOfYPPOS.get(i);
                    Double value = paramLogOfYPPO.getValue().doubleValue();
                    Date dateStr = paramLogOfYPPO.getRecordTime();
                    int h = Integer.parseInt(DateHelper.getDate(dateStr, "HH"));
                    int m = Integer.parseInt(DateHelper.getDate(dateStr, "mm"));
                    int index = -1;
                    if (h >= 0) {
                        if (m < 15) {
                            index = 4 * h;
                        } else if (m < 30) {
                            index = 4 * h + 1;
                        } else if (m < 45) {
                            index = 4 * h + 2;
                        } else {
                            index = 4 * h + 3;
                        }
                    }
                    if (index >= 0) {
                        List<Object> pointItem = new ArrayList<>(2);
                        pointItem.add(index);
                        pointItem.add(value);
                        pointList.set(index, pointItem);
                        chartShow = 1;
                    }
                }
                webModel.setLineDataOfDay(pointList);
                webModel.setAvgNum(df.format(avg));
                webModel.setGhb(df.format(ghb));
                webModel.setEventCountOfLow(eventCountOfLow);
                webModel.setAvgAwiTimeOfLow(df.format(avgAwiTimeOfLow));
                webModel.setStandardVal(df.format(standardValue));
                webModel.setEventCountOfHigh(eventCountOfHigh);
                webModel.setAvgAwiTimeOfHigh(avgAwiTimeOfHigh);
                webModel.setEventCountOfNormal(eventCountOfNormal);
                webModel.setAvgAwiTimeOfNormal(avgAwiTimeOfNormal);
                webModel.setAwiTimeRateOfLow(awiTimeRateOfLow);
                webModel.setAwiTimeRateOfHigh(awiTimeRateOfHigh);
                webModel.setAwiTimeRateOfNormal(awiTimeRateOfNormal);
                webModel.setCoefficientOfVariation(coefficientOfVariation);
                webModel.setAwiTimeRateOf3_9(awiTimeRateOf3_9);
                webModel.setAwiTimeRateOf4_0(awiTimeRateOf4_0);
                webModel.setAwiTimeRateOf13_9(awiTimeRateOf13_9);
                webModel.setMeanAmplitudeOfGlycemicExcursion(meanAmplitudeOfGlycemicExcursion);
                webModel.setLowLineVal(Double.parseDouble(lowStr));
                webModel.setHighLineVal(Double.parseDouble(highStr));
                webModel.setRecordDt(startDt1);
                webModel.setChartShow(chartShow);
                webModel.setDataSource(paramLogOfYPPOS);
                listChart.add(webModel);
                endDt1 = DateHelper.plusDate(endDt1, -1);
            }
            resultMap.put("list", listChart);
        }

        //总血糖计算
        if (StringUtils.isBlank(endDt)) {
            endDt = DateHelper.getToday();
            startDt = DateHelper.plusDate(endDt, -DAY_YP_14);
        }
        if (StringUtils.isBlank(startDt)) {
            startDt = endDt;
        }
        StatisticsYPParamLogOfWebVO model = new StatisticsYPParamLogOfWebVO();
        /**业务数据计算处理开始**/
        //血糖值和
        Double sum = 0D;
        //血糖记录列表
        List<DYYPBloodSugarPO> paramLogOfYPPOS = this.dyBloodSugarService.listDataSourceOfYPParamLogOfOBDTASC(startDt, endDt, sensorNo);
        for (DYYPBloodSugarPO dyypBloodSugarPO : paramLogOfYPPOS) {
            BigDecimal decimal = dyypBloodSugarPO.getValue();
            if (decimal != null) {
                sum += decimal.doubleValue();
            }
        }
        //血糖记录数量
        int logCount = paramLogOfYPPOS.size();
        //低血糖，高血糖，正常血糖血糖事件次数
        Integer lowEventCount = 0, highEventCount = 0, normalEventCount = 0;
        //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始/结束标志 true 开始，false 结束
        Boolean hasLowItem = false, hasHighItem = false, hasNormalItem = false, hasLogItemOf3_9 = false,
                hasLogItemOf4_0 = false, hasLogItemOf13_9 = false;
        //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件开始时间标志
        String lowEventLogSDt = null, highEventLogSDt = null, normalEventLogSDt = null, eventLogSDtOf3_9 = null,
                eventLogSDtOf4_0 = null, eventLogSDtOf13_9 = null;
        //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件结束时间标志
        String lowEventLogEDt = null, highEventLogEDt = null, normalEventLogEDt = null, eventLogEDtOf3_9 = null,
                eventLogEDtOf4_0 = null, eventLogEDtOf13_9 = null;
        //低血糖，高血糖，正常血糖，低于3.9血糖,低于4.0血糖，低于13.9血糖事件总持续时间
        Long lowEventDtCount = 0L, highEventDtCount = 0L, normalEventDtCount = 0L, eventDtCountOf3_9 = 0L, eventDtCountOf4_0 = 0L, eventDtCountOf13_9 = 0L;
        //血糖记录持续时间
        Long ptDtCount = 0L;
        if (logCount > 0) {
            ptDtCount = paramLogOfYPPOS.get(logCount - 1).getRecordTime().getTime() -
                    paramLogOfYPPOS.get(0).getRecordTime().getTime();
        }
        //血糖值数组
        Double[] arr = null;
        if (logCount > 0) {
            arr = new Double[logCount];
        }

        //持续时间&统计&求和-计算
        this.calculationHandle(hasLowItem, lowEventLogSDt, lowEventLogEDt, lowEventCount, lowEventDtCount,
                hasHighItem, highEventLogSDt, highEventLogEDt, highEventCount, highEventDtCount,
                hasNormalItem, normalEventLogSDt, normalEventLogEDt, normalEventCount, normalEventDtCount,
                hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9, eventDtCountOf3_9,
                hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0, eventDtCountOf4_0,
                hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9, eventDtCountOf13_9,
                arr, sum,
                logCount, paramLogOfYPPOS);

        //处理血糖列表只有低血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
        lowEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasLowItem, lowEventLogSDt, lowEventLogEDt);
        //处理血糖列表只有高血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
        highEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasHighItem, highEventLogSDt, highEventLogEDt);
        //处理血糖列表只有正常血糖的情况，把低血糖结束时间设置最后一次血糖记录时间
        normalEventDtCount += this.getDtCountBySingleTypeGlucoseList(hasNormalItem, normalEventLogSDt, normalEventLogEDt);
        //处理血糖列表只有低3.9的情况，把低血糖结束时间设置最后一次血糖记录时间
        eventDtCountOf3_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf3_9, eventLogSDtOf3_9, eventLogEDtOf3_9);
        //处理血糖列表只有低4.0的情况，把低血糖结束时间设置最后一次血糖记录时间
        eventDtCountOf4_0 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf4_0, eventLogSDtOf4_0, eventLogEDtOf4_0);
        //处理血糖列表只有低13.9的情况，把低血糖结束时间设置最后一次血糖记录时间
        eventDtCountOf13_9 += this.getDtCountBySingleTypeGlucoseList(hasLogItemOf13_9, eventLogSDtOf13_9, eventLogEDtOf13_9);
        //数值格式化对象
        DecimalFormat df = new DecimalFormat("#0.0");
        //低血糖范围持续时间占比
        String awiTimeRateOfLow = "--";
        //高血糖范围持续时间占比
        String awiTimeRateOfHigh = "--";
        //正常血糖范围持续时间占比（3.9-10）
        String awiTimeRateOfNormal = "--";
        //低3.9,低4.0,低13.9占比
        String awiTimeRateOf3_9 = "--";
        String awiTimeRateOf4_0 = "--";
        String awiTimeRateOf13_9 = "--";
        //达标非达标时间占比计算
        this.calculationRateHandle(awiTimeRateOfLow, awiTimeRateOfHigh, awiTimeRateOfNormal, awiTimeRateOf3_9, awiTimeRateOf4_0, awiTimeRateOf13_9,
                lowEventDtCount, highEventDtCount, normalEventDtCount, eventDtCountOf3_9, eventDtCountOf4_0, eventDtCountOf13_9, ptDtCount);
        //标准差
        Double standardValue = 0.0D;
        if (arr != null && arr.length > 0) {
            standardValue = this.getStandardValue(arr);
        }
        //平均血糖值
        Double avg = 0D;
        if (logCount > 0) {
            avg = sum / logCount;
        }
        //糖化血红蛋白
        Double ghb = new BigDecimal(avg + 2.59)
                .divide(new BigDecimal("1.59"), 1, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        //低血糖事件
        Integer eventCountOfLow = lowEventCount;
        //低血糖平均持续时间
        Double avgAwiTimeOfLow = 0d;
        if (eventCountOfLow > 0) {
            avgAwiTimeOfLow = Double.parseDouble(lowEventDtCount / 1000 / 60 / eventCountOfLow + "");
        }
        //高血糖事件
        Integer eventCountOfHigh = highEventCount;
        //高血糖平均持续时间
        Double avgAwiTimeOfHigh = 0d;
        if (eventCountOfHigh > 0) {
            avgAwiTimeOfHigh = Double.parseDouble(highEventDtCount / 1000 / 60 / eventCountOfHigh + "");
        }
        //正常血糖事件
        Integer eventCountOfNormal = normalEventCount;
        //正常血糖平均持续时间
        Double avgAwiTimeOfNormal = 0d;
        if (eventCountOfNormal > 0) {
            avgAwiTimeOfNormal = Double.parseDouble(normalEventDtCount / 1000 / 60 / eventCountOfNormal + "");
        }
        //血糖变异系数
        String coefficientOfVariation = "0.0";
        if (avg != 0) {
            coefficientOfVariation = df.format(standardValue / avg * 100);
        }
        //平均血糖波动幅度（mean amplitude of glycemic excursion，MAGE）
        Double MAGE = 0.0;
        if (arr != null && arr.length > 0) {
            MAGE = this.getMAGE(standardValue, arr);
        }
        String meanAmplitudeOfGlycemicExcursion = (MAGE > 0) ? df.format(MAGE) : "--";
        /**业务数据计算处理结束**/
        model.setAvgNum(df.format(avg));
        model.setGhb(df.format(ghb));
        model.setEventCountOfLow(eventCountOfLow);
        model.setAvgAwiTimeOfLow(df.format(avgAwiTimeOfLow));
        model.setStandardVal(df.format(standardValue));
        model.setEventCountOfHigh(eventCountOfHigh);
        model.setAvgAwiTimeOfHigh(avgAwiTimeOfHigh);
        model.setEventCountOfNormal(eventCountOfNormal);
        model.setAvgAwiTimeOfNormal(avgAwiTimeOfNormal);
        model.setAwiTimeRateOfLow(awiTimeRateOfLow);
        model.setAwiTimeRateOfHigh(awiTimeRateOfHigh);
        model.setAwiTimeRateOfNormal(awiTimeRateOfNormal);
        model.setCoefficientOfVariation(coefficientOfVariation);
        model.setAwiTimeRateOf3_9(awiTimeRateOf3_9);
        model.setAwiTimeRateOf4_0(awiTimeRateOf4_0);
        model.setAwiTimeRateOf13_9(awiTimeRateOf13_9);
        model.setMeanAmplitudeOfGlycemicExcursion(meanAmplitudeOfGlycemicExcursion);
        model.setLowLineVal(Double.parseDouble(lowStr));
        model.setHighLineVal(Double.parseDouble(highStr));
        model.setDataSource(paramLogOfYPPOS);
        model.setChartShow(resultMap.getInteger("chartShow"));
        resultMap.put("data", model);
        DYYPStatisticsPOWithBLOBs statisticsYPParamLog = this.getStatisticsYPParamLogOfGAPP(endDt
                , dayNum, type, sensorNo);
        if (statisticsYPParamLog != null) {
            DYYPStatisticsAdvicePO advicePO = this.dyypStatisticsAdvicePOMapper.getStatisticAdviceByStatisticsId(statisticsYPParamLog.getSid());
            if (advicePO != null) {
                resultMap.put("adContent", advicePO.getContent());
                resultMap.put("doctorId", advicePO.getDoctorId());
            }
            resultMap.put("sid", statisticsYPParamLog.getSid());
        } else {
            String sid = ComveeMd5Util.md5("no=" + sensorNo + ",type=" + type + ",dayNum=" + dayNum);
            DYYPStatisticsAdvicePO advicePO = this.dyypStatisticsAdvicePOMapper.getStatisticAdviceByStatisticsId(sid);
            if (advicePO != null) {
                resultMap.put("adContent", advicePO.getContent());
                resultMap.put("doctorId", advicePO.getDoctorId());
            }
            resultMap.put("sid", sid);
        }
        return resultMap;
    }

    @Override
    public void addDYYPStatisticsAdvice(AddDYYPStatisticsAdviceDTO dto) {
        DYYPStatisticsAdvicePO record = new DYYPStatisticsAdvicePO();
        record.setContent(dto.getContent());
        record.setDoctorId(dto.getDoctorId());
        record.setStatisticsId(dto.getStatisticsId());
        record.setInsertDt(DateHelper.getDate(new Date(), DateHelper.DATETIME_FORMAT));
        record.setModifyDt(DateHelper.getDate(new Date(), DateHelper.DATETIME_FORMAT));
        record.setIsValid((byte) 1);
        record.setSid(DaoHelper.getSeq());
        this.dyypStatisticsAdvicePOMapper.insert(record);

        doctorAdviceWechatPushHandler(record);
    }

    @Override
    public void updateSensorMonitorTimes(String sensorNo) {
        //更新探头开始时间
        this.dyMemberSensorService.updateSensorMonitorTimes(sensorNo);
    }

    @Override
    public long hasDynamicBloodSugarSensorStatistics(GetStatisticsDTO gto) {
        return this.dyypStatisticsPOMapper.hasDynamicBloodSugarSensorStatistics(gto);
    }

    /**
     * 医生建议微信推送处理
     *
     * @param advicePO
     */
    private void doctorAdviceWechatPushHandler(DYYPStatisticsAdvicePO advicePO) {
        DYYPStatisticsPO statisticsPO = dyypStatisticsPOMapper.getDYYPStatisticsBySid(advicePO.getStatisticsId());
        if(statisticsPO == null){
            throw new BusinessException("统计记录不存在,请确认");
        }

        DYMemberSensorPO sensorPO = this.dyMemberSensorService.getDYMemberSensorPO(statisticsPO.getSensorNo());
        if (sensorPO == null) {
            return;
        }
        MemberPO memberPO = this.memberService.getMemberById(sensorPO.getMemberId());

        DoctorPO doctorPO = this.doctorServiceI.getDoctorById(advicePO.getDoctorId());

        List<ShowSensorVO> sensorVOS = this.dyMemberSensorService.listShowSensorBySensorSid(sensorPO.getSid());
        List<String> memberIdList = sensorVOS.stream().map(ShowSensorVO::getMemberId).collect(Collectors.toList());
        memberIdList.add(sensorPO.getMemberId());

        JSONObject dataJson = new JSONObject();
        dataJson.put("doctorName", doctorPO.getDoctorName());
        dataJson.put("memberName", memberPO.getMemberName());
        dataJson.put("sensorNo", sensorPO.getSensorNo());
        dataJson.put("type", statisticsPO.getType());
        dataJson.put("startDt", DateHelper.getDate(statisticsPO.getStartDt(), "yyyy-MM-dd"));
        dataJson.put("endDt", DateHelper.addDatetime(statisticsPO.getStartDt(), statisticsPO.getDay()));
        dataJson.put("date", DateHelper.getNowDate());
        dataJson.put("monitorTime", DateHelper.getDate(new Date(Long.parseLong(sensorPO.getMonitoringTime())), DateHelper.DATETIME_FORMAT));
        AddWechatMessageDTO addWechatMessageDTO;
        for (String memberId : memberIdList) {
            addWechatMessageDTO = new AddWechatMessageDTO();
            addWechatMessageDTO.setForeignId(advicePO.getSid());
            addWechatMessageDTO.setMemberId(memberId);
            addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_DYNAMIC_BLOOD_SUGAR_DOCTOR_ADVICE);
            addWechatMessageDTO.setDataJson(dataJson.toJSONString());
            this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
        }
    }
}
