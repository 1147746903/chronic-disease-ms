package com.comvee.cdms.statistics.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.app.doctorapp.model.app.BloodRecordRangeModel;
import com.comvee.cdms.checkresult.mapper.CheckoutDetailMapper;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.clinicaldiagnosis.constant.YzConstant;
import com.comvee.cdms.clinicaldiagnosis.dto.ListMemberYzDTO;
import com.comvee.cdms.clinicaldiagnosis.service.YzServiceI;
import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzListVO;
import com.comvee.cdms.common.cfg.ParamLogConstant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.complication.constant.ScreeningConstant;
import com.comvee.cdms.complication.mapper.ScreeningReportMapper;
import com.comvee.cdms.complication.model.dto.ListMemberScreeningReportDTO;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.complication.service.ScreeningStatsService;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.cfg.DoctorConstant;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.drugs.service.DrugStatisticsServer;
import com.comvee.cdms.dybloodsugar.service.DyStaticsService;
import com.comvee.cdms.follow.cfg.FollowConstant;
import com.comvee.cdms.follow.dto.CountFollowDTO;
import com.comvee.cdms.follow.dto.CountMonthFollowDTO;
import com.comvee.cdms.follow.mapper.FollowMapper;
import com.comvee.cdms.follow.po.CountMonthFollowPO;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.service.CommitteeService;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.insulinpump.model.vo.InsulinPumpDayUsageVO;
import com.comvee.cdms.insulinpump.service.InsulinPumpService;
import com.comvee.cdms.insulinpump.tool.InsulinPumpTool;
import com.comvee.cdms.level.bo.MemberCurrentLeverBO;
import com.comvee.cdms.level.constant.MemberLevelConstant;
import com.comvee.cdms.level.dto.ListMemberLevelDTO;
import com.comvee.cdms.level.helper.DiabetesLevelAnalyseHelper;
import com.comvee.cdms.level.helper.LevelAnalyzeHelper;
import com.comvee.cdms.level.mapper.MemberLevelMapper;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.constant.MemberConstant;
import com.comvee.cdms.member.dto.CountDoctorMemberDTO;
import com.comvee.cdms.member.dto.CountMonthMemberDTO;
import com.comvee.cdms.member.dto.ListMemberJoinHospitalDTO;
import com.comvee.cdms.member.dto.ListMemberVisitDTO;
import com.comvee.cdms.member.mapper.MemberJoinHospitalMapper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.CountMonthMemberPO;
import com.comvee.cdms.member.po.MemberJoinHospitalPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.packages.cfg.PackageConstant;
import com.comvee.cdms.packages.dto.CountMonthPackageDTO;
import com.comvee.cdms.packages.dto.SumPackageMonthPriceDTO;
import com.comvee.cdms.packages.dto.SumPackagePriceDTO;
import com.comvee.cdms.packages.po.CountMonthPackagePO;
import com.comvee.cdms.packages.po.PackageMonthPricePO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.CountMonthPrescriptionDTO;
import com.comvee.cdms.prescription.dto.GetPrescriptionDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.CountMonthPrescriptionPO;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import com.comvee.cdms.prescription.service.PrescriptionServiceI;
import com.comvee.cdms.referral.dto.ListReferralApplyDTO;
import com.comvee.cdms.referral.mapper.ReferralApplyMapper;
import com.comvee.cdms.referral.po.ReferralApplyPO;
import com.comvee.cdms.sign.bo.BloodSugarOfParamCodeBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.ListBloodPressureDTO;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.dto.ListHbalcDTO;
import com.comvee.cdms.sign.mapper.BloodPressureMapper;
import com.comvee.cdms.sign.mapper.BloodSugarMapper;
import com.comvee.cdms.sign.mapper.Hba1cMapper;
import com.comvee.cdms.sign.po.BloodPressurePO;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.service.BloodPressureService;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.sign.service.BmiService;
import com.comvee.cdms.statistics.cfg.ChartItemCode;
import com.comvee.cdms.statistics.cfg.StatisticsNavigationItemEnum;
import com.comvee.cdms.statistics.dto.*;
import com.comvee.cdms.statistics.mapper.StaticsMapper;
import com.comvee.cdms.statistics.service.StatisticsService;
import com.comvee.cdms.statistics.vo.*;
import com.comvee.cdms.virtualward.model.param.ListVirtualWardPatientParam;
import com.comvee.cdms.virtualward.model.po.VirtualWardListPO;
import com.comvee.cdms.virtualward.service.VirtualWardService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

/**
 * @author: suyz
 * @date: 2018/10/19
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger log = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    private DecimalFormat df = new DecimalFormat("#.#");

    @Autowired
    private FollowServiceI followServiceI;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PrescriptionServiceI prescriptionServiceI;

    @Autowired
    private PackageService packageService;

    @Autowired
    private BloodSugarService bloodSugarService;

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private BmiService bmiService;

    @Autowired
    private BloodPressureService bloodPressureService;

    @Autowired
    private CheckoutDetailServiceI checkoutDetailService;

    @Autowired
    private DrugStatisticsServer drugStatisticsServer;

    @Autowired
    private ScreeningStatsService screeningStatsService;

    @Autowired
    private VirtualWardService virtualWardService;

    @Autowired
    private YzServiceI yzService;

    @Autowired
    private InsulinPumpService insulinPumpService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private DyStaticsService dyStaticsService;

    @Autowired
    private StaticsMapper staticsMapper;

    @Autowired
    private MemberLevelMapper memberLevelMapper;

    @Autowired
    private Hba1cMapper hba1cMapper;

    @Autowired
    private CheckoutDetailMapper checkoutDetailMapper;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private ScreeningReportMapper screeningReportMapper;

    @Autowired
    private MemberJoinHospitalMapper memberJoinHospitalMapper;

    @Autowired
    private BloodSugarMapper bloodSugarMapper;

    @Autowired
    private BloodPressureMapper bloodPressureMapper;

    @Autowired
    private ReferralApplyMapper referralApplyMapper;

    @Autowired
    private CommitteeService committeeService;

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DoctorServiceI doctorServiceI;



    @Override
    public NumberStatisticsVO getNumberStatistics(String doctorId) {
        NumberStatisticsVO numberStatisticsVO = new NumberStatisticsVO();
        //随访统计
        CountFollowDTO countFollowDTO = new CountFollowDTO();
        countFollowDTO.setDoctorId(doctorId);
        countFollowDTO.setFollowStatus(1);
        long followNumber = this.followServiceI.countFollow(countFollowDTO);
        numberStatisticsVO.setFollowNumber(followNumber);
        //患者数量统计
        CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
        countDoctorMemberDTO.setDoctorId(doctorId);
        long memberNumber = this.memberService.countMemberDoctor(countDoctorMemberDTO);
        numberStatisticsVO.setMemberTotalNumber(memberNumber);
        //统计订单总额
        SumPackagePriceDTO sumPackagePriceDTO = new SumPackagePriceDTO();
        sumPackagePriceDTO.setDoctorId(doctorId);
        sumPackagePriceDTO.setPackageStatus(PackageConstant.PACKAGE_STATUS_NORMAL);
        long totalPrice = this.packageService.sumPackagePrice(sumPackagePriceDTO);
        numberStatisticsVO.setOrderTotalNumber(totalPrice);
        //统计处方数量
//        CountPrescriptionDTO countPrescriptionDTO = new CountPrescriptionDTO();
//        countPrescriptionDTO.setDoctorId(doctorId);
//        countPrescriptionDTO.setSchedule(PrescriptionConstant.SCHEDULE_COMPLETE);
//        long prescriptionNumber = this.prescriptionServiceI.countPrescription(countPrescriptionDTO);
        SynthesizeDataDTO dto = new SynthesizeDataDTO();
        dto.setDoctorId(doctorId);
        dto.setPrescriptionStatus(PrescriptionConstant.SCHEDULE_COMPLETE);
        long prescriptionNumber = this.prescriptionServiceI.countNewPrescription(dto);
        numberStatisticsVO.setPrescriptionNumber(prescriptionNumber);
        return numberStatisticsVO;
    }

    @Override
    public List<OrderChartVO> listOrderChartData(QueryChartDTO queryChartDTO) {
        SumPackageMonthPriceDTO sumPackageMonthPriceDTO = new SumPackageMonthPriceDTO();
        BeanUtils.copyProperties(sumPackageMonthPriceDTO, queryChartDTO);
        List<PackageMonthPricePO> monthDataList = this.packageService.sumPackageMonthPrice(sumPackageMonthPriceDTO);
        Map<String, Long> priceMap = monthDataList.stream().collect(Collectors.toMap(PackageMonthPricePO::getMonth, PackageMonthPricePO::getPriceTotal));
        List<OrderChartVO> list = new ArrayList<>();
        OrderChartVO vo;
        for (int i = 1; i <= MAX_MONTH; i++) {
            vo = new OrderChartVO();
            vo.setMonth(String.valueOf(i));
            vo.setOrderTotal(priceMap.getOrDefault(String.format("%02d", i), 0L));
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<StatisticsChartVO> listStatisticsChartData(QueryChartDTO queryChartDTO) {
        List<StatisticsChartVO> list = new ArrayList<>();
        StatisticsChartVO statisticsChartVO;
        //处方
        CountMonthPrescriptionDTO countMonthPrescriptionDTO = new CountMonthPrescriptionDTO();
        BeanUtils.copyProperties(countMonthPrescriptionDTO, queryChartDTO);
        List<CountMonthPrescriptionPO> monthPrescriptionPOList = this.prescriptionServiceI.countMonthPrescription(countMonthPrescriptionDTO);
        Map<Integer, Long> prescriptionPOMap = monthPrescriptionPOList
                .stream()
                .collect(Collectors.toMap(CountMonthPrescriptionPO::getMonth, CountMonthPrescriptionPO::getCount));
        statisticsChartVO = new StatisticsChartVO();
        statisticsChartVO.setDataList(chartDataListHandler(prescriptionPOMap));
        statisticsChartVO.setItemCode(ChartItemCode.CHART_ITEM_PRESCRIPTION);
        list.add(statisticsChartVO);

        //随访
        CountMonthFollowDTO countMonthFollowDTO = new CountMonthFollowDTO();
        BeanUtils.copyProperties(countMonthFollowDTO, queryChartDTO);
        List<CountMonthFollowPO> monthFollowPOList = this.followServiceI.countMonthFollow(countMonthFollowDTO);
        Map<Integer, Long> monthFollowPOMap = monthFollowPOList.stream().collect(Collectors.toMap(CountMonthFollowPO::getMonth, CountMonthFollowPO::getCount));
        statisticsChartVO = new StatisticsChartVO();
        statisticsChartVO.setDataList(chartDataListHandler(monthFollowPOMap));
        statisticsChartVO.setItemCode(ChartItemCode.CHART_ITEM_FOLLOW);
        list.add(statisticsChartVO);

        //订单
        CountMonthPackageDTO countMonthPackageDTO = new CountMonthPackageDTO();
        BeanUtils.copyProperties(countMonthPackageDTO, queryChartDTO);
        List<CountMonthPackagePO> monthPackagePOList = this.packageService.countMonthPackage(countMonthPackageDTO);
        Map<Integer, Long> monthPackageMap = monthPackagePOList
                .stream()
                .collect(Collectors.toMap(CountMonthPackagePO::getMonth, CountMonthPackagePO::getCount));
        statisticsChartVO = new StatisticsChartVO();
        statisticsChartVO.setDataList(chartDataListHandler(monthPackageMap));
        statisticsChartVO.setItemCode(ChartItemCode.CHART_ITEM_ORDER);
        list.add(statisticsChartVO);

        //患者
        CountMonthMemberDTO countMonthMemberDTO = new CountMonthMemberDTO();
        BeanUtils.copyProperties(countMonthMemberDTO, queryChartDTO);
        List<CountMonthMemberPO> monthMemberPOList = this.memberService.countMonthMember(countMonthMemberDTO);
        Map<Integer, Long> monthMemberMap = monthMemberPOList.stream()
                .collect(Collectors.toMap(CountMonthMemberPO::getMonth, CountMonthMemberPO::getCount));
        statisticsChartVO = new StatisticsChartVO();
        statisticsChartVO.setDataList(chartDataListHandler(monthMemberMap));
        statisticsChartVO.setItemCode(ChartItemCode.CHART_ITEM_MEMBER);
        list.add(statisticsChartVO);

        return list;
    }

    @Override
    public BloodSugarChartDataVO listBloodSugarChartData(String startDt, String endDt, String doctorId) {
        DecimalFormat df = new DecimalFormat("#");
        long memberNum = 0L, normalMemberNum, abnormalMemberNum, sugarNum = 0L, abnormalNum, normalNum = 0L, highNum = 0L, lowNum = 0L;
        double max = 0D, min = 0D, normlRate = 0D, lowRate = 0D, highRate = 0D, emptyAvgNum = 0D, fullAvgNum = 0D;
        //小时
        String dateType = "h";
        int diffDay = DateHelper.dateCompareGetDay(endDt, startDt);
        List<List<Double>> pointlist = new ArrayList<List<Double>>();
        List<Double> linelist = new ArrayList<Double>();
        Map<String, Object> linelistMap = new HashMap<String, Object>(16);
        Map<String, Object> dataMap = new HashMap<String, Object>(1);
        List<String> xAlist = this.get24ChartXList(startDt, endDt);
        dataMap.put("type", "tian");
        dataMap.put("title", "血糖值");
        dataMap.put("textype", "text");
        dataMap.put("xlist", xAlist);
        dataMap.put("pointlist", pointlist);
        if (diffDay == 0) {
            dateType = "h";
        } else {
            //天数
            dateType = "d";
        }
        dataMap.put("dateType", dateType);
        Set<String> memberSet = new HashSet<>();
        List<BloodSugarPO> list = this.bloodSugarService.listMemberBloodSugarByDoctorId(startDt, endDt, doctorId);
        int size = list.size();
        if (size > 0) {
            Double[] sugarVals = new Double[size];
            List<Double> emptys = new ArrayList<Double>();
            List<Double> fulls = new ArrayList<Double>();

            for (int i = 0; i < size; i++) {
                BloodSugarPO m = list.get(i);
                memberSet.add(m.getMemberId());
                if (StringUtils.isBlank(m.getParamValue())) {
                    sugarVals[i] = 0D;
                } else {
                    sugarVals[i] = Double.parseDouble(m.getParamValue());
                }

                if (ParamLogConstant.PARAM_CODE_BEFOREBREAKFAST.equals(m.getParamCode()) ||
                        ParamLogConstant.PARAM_CODE_BEFORELUNCH.equals(m.getParamCode()) ||
                        ParamLogConstant.PARAM_CODE_BEFOREDINNER.equals(m.getParamCode()) ||
                        ParamLogConstant.PARAM_CODE_BEFORESLEEP.equals(m.getParamCode())) {
                    //空腹
                    if (StringUtils.isBlank(m.getParamValue())) {
                        emptys.add(0D);
                    } else {
                        emptys.add(Double.parseDouble(m.getParamValue()));
                    }
                } else {//非空腹
                    if (StringUtils.isBlank(m.getParamValue())) {
                        fulls.add(0D);
                    } else {
                        fulls.add(Double.parseDouble(m.getParamValue()));
                    }
                }

                if (m.getParamLevel() == SignConstant.SIGN_LEVEL_HIGH) {
                    highNum++;
                } else if (m.getParamLevel() == SignConstant.SIGN_LEVEL_LOW) {
                    lowNum++;
                }

                //散点集
                List<Double> pointlistItem = this.getPointlistItem(dateType, xAlist, m);
                pointlist.add(pointlistItem);

                //平均曲线数据Map
                String recordTime = m.getRecordDt();
                Date date = DateHelper.getDate(recordTime, "yyyy-MM-dd HH");
                if ("d".equals(dateType)) {
                    date = DateHelper.getDate(recordTime, "yyyy-MM-dd");
                }
                Long recordTimes = date.getTime();
                for (String x : xAlist) {
                    Date xd = DateHelper.getDate(x, "yyyy-MM-dd HH");
                    if ("d".equals(dateType)) {
                        xd = DateHelper.getDate(x, "yyyy-MM-dd");
                    }
                    Long xTimes = xd.getTime();
                    if (recordTimes.equals(xTimes)) {
                        Object obj = linelistMap.get(x);
                        if (obj != null) {
                            List<Double> dl = (List<Double>) obj;
                            dl.add(Double.parseDouble(m.getParamValue()));
                        } else {
                            List<Double> dl = new ArrayList<Double>();
                            dl.add(Double.parseDouble(m.getParamValue()));
                            linelistMap.put(x, dl);
                        }
                    }
                }
            }

            //平均曲线
            linelist = this.getLineList(linelistMap, xAlist);
            dataMap.put("linelist", linelist);

            Double[] dEmptys = new Double[emptys.size()];
            Double[] dFulls = new Double[fulls.size()];

            for (int i = 0; i < emptys.size(); i++) {
                dEmptys[i] = emptys.get(i);
            }

            for (int i = 0; i < fulls.size(); i++) {
                dFulls[i] = fulls.get(i);
            }

            //最高值
            max = getMaxValue(sugarVals);
            dataMap.put("max", max);
            //最低值
            min = getMinValue(sugarVals);
            dataMap.put("min", min);
            //空腹平均值
            emptyAvgNum = Double.parseDouble(df.format(getAvg(dEmptys)));
            //非空平均值
            fullAvgNum = Double.parseDouble(df.format(getAvg(dFulls)));

            //人数
            memberNum = memberSet.size();
            //总读数
            sugarNum = size;
/*            //偏高数量
            highNum= this.getHighAbnormalSugarNumber(paramLogModel);
            //偏低数量
            lowNum= this.getLowAbnormalSugarNumber(paramLogModel);*/

            //偏高率
            if (sugarNum > 0) {
                Double dHighSuagrNum = Double.parseDouble(highNum + "");
                highRate = Double.parseDouble(df.format(dHighSuagrNum / sugarNum * 100));
            }

            //偏低率
            if (lowNum > 0) {
                Double dLowSuagrNum = Double.parseDouble(lowNum + "");
                lowRate = Double.parseDouble(df.format(dLowSuagrNum / sugarNum * 100));
            }

            //正常率
            normlRate = 100 - highRate - lowRate;
        }
        BloodSugarChartDataVO statisticsModel = new BloodSugarChartDataVO();
        statisticsModel.setMemberNum(memberNum);
        statisticsModel.setSugarNum(sugarNum);
        statisticsModel.setHighRate(highRate);
        statisticsModel.setNormalRate(normlRate);
        statisticsModel.setLowRate(lowRate);
        statisticsModel.setMax(max);
        statisticsModel.setMin(min);
        statisticsModel.setEmptyAvgNum(emptyAvgNum);
        statisticsModel.setFullAvgNum(fullAvgNum);
        statisticsModel.setDataMap(dataMap);

        return statisticsModel;
    }

    /**
     * 求最高值
     *
     * @param arr
     * @return
     */
    public static Double getMaxValue(Double... arr) {
        Double max = arr[0];
        for (Double d : arr) {
            if (max <= d) {
                max = d;
            }
        }
        return max;
    }

    /**
     * 求最低值
     *
     * @param arr
     * @return
     */
    public static Double getMinValue(Double... arr) {
        Double min = arr[0];
        for (Double d : arr) {
            if (min >= d) {
                min = d;
            }
        }
        return min;
    }

    /**
     * \
     * 获取图表x轴日期
     *
     * @param startDt
     * @param endDt
     * @return
     */
    private List<String> get24ChartXList(String startDt, String endDt) {
        int diffDay = DateHelper.dateCompareGetDay(endDt, startDt);
        Long times = DateHelper.getDate(endDt, "yyyy-MM-dd HH:mm:ss").getTime()
                - DateHelper.getDate(startDt, "yyyy-MM-dd HH:mm:ss").getTime();
        int int24 = 24;
        if (diffDay > 0) {
            if (diffDay < int24) {
                Long interval = times / diffDay;
                return set24ChartXList(startDt, interval, diffDay + 1);
            } else {
                Long interval = times / int24;
                return set24ChartXList(startDt, interval, int24);
            }
        } else {
            return set24ChartXList(startDt, 3600000L, int24);
        }
    }

    /**
     * 获取图表x轴日期
     *
     * @param startDt
     * @param interval
     * @param xCount
     * @return
     */
    private List<String> set24ChartXList(String startDt, Long interval, int xCount) {
        List<String> list = new ArrayList<String>(xCount);
        Date date = DateHelper.getDate(startDt, "yyyy-MM-dd HH:mm:ss");
        Long startTimes = date.getTime();
        for (int i = 0; i < xCount; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(startTimes);
            String dd = DateHelper.getDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
            list.add(dd);
            Long xTimes = startTimes + interval;
            startTimes = xTimes;
        }
        return list;
    }

    private List<Double> getPointlistItem(String dateType, List<String> xlist, BloodSugarPO data) {
        List<Double> item = new ArrayList<Double>(2);
        String recordTime = data.getRecordDt();
        Date date = DateHelper.getDate(recordTime, "yyyy-MM-dd HH:mm:ss");
        Double x = convertDateToXNumber(dateType, xlist, DateHelper.getDate(date, "yyyy-MM-dd HH:mm:ss"));
        item.add(x);
        item.add(Double.parseDouble(data.getParamValue()));
        return item;
    }

    private Double convertDateToXNumber(String dateType, List<String> xlist, String recordDt) {
        DecimalFormat df = new DecimalFormat("#.0");
        String strH = "h";
        if (strH.equals(dateType)) {
            for (int i = 0; i < xlist.size(); i++) {
                String listItem1 = xlist.get(i);
                String listItem2 = (xlist.size() > (i + 1)) ? xlist.get(i + 1) : listItem1;

                Date date1 = DateHelper.getDate(listItem1, "yyyy-MM-dd HH:mm:ss");
                Date date2 = DateHelper.getDate(listItem2, "yyyy-MM-dd HH:mm:ss");
                Date recordDate = DateHelper.getDate(recordDt, "yyyy-MM-dd HH:mm:ss");

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
        } else {
            for (int i = 0; i < xlist.size(); i++) {
                String listItem1 = xlist.get(i);
                String listItem2 = (xlist.size() > (i + 1)) ? xlist.get(i + 1) : listItem1;

                Date date1 = DateHelper.getDate(listItem1, "yyyy-MM-dd");
                Date date2 = DateHelper.getDate(listItem2, "yyyy-MM-dd");
                Date recordDate = DateHelper.getDate(recordDt, "yyyy-MM-dd");

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

    private List<Double> getLineList(Map<String, Object> dataMap, List<String> xlist) {
        DecimalFormat df = new DecimalFormat("#.0");
        List<Double> linelist = new ArrayList<Double>(xlist.size());
        for (String x : xlist) {
            Double d = 0D;
            Object obj = dataMap.get(x);
            if (obj != null) {
                List<Double> list = (List<Double>) obj;
                Double[] doubles = new Double[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    doubles[i] = list.get(i);
                }
                d = Double.parseDouble(df.format(getAvg(doubles)));
            }
            linelist.add(d);
        }
        return linelist;
    }

    /**
     * 计算平均值
     *
     * @param arr
     * @return
     * @author 李左河
     * @date 2018年3月21日 上午11:25:59
     */
    private static Double getAvg(Double... arr) {
        Double avg = 0D;
        Double sum = getSum(arr);
        Integer n = arr.length;
        if (n > 0) {
            avg = sum / n;
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
    private static Double getSum(Double... arr) {
        Double sum = 0D;
        for (Double d : arr) {
            sum += d;
        }
        return sum;
    }

    /**
     * 图标数据处理
     *
     * @param map
     * @return
     */
    private List<StatisticsChartItemVO> chartDataListHandler(Map<Integer, Long> map) {
        List<StatisticsChartItemVO> dataList = new ArrayList<>();
        StatisticsChartItemVO statisticsChartItemVO;
        for (int i = 1; i <= MAX_MONTH; i++) {
            statisticsChartItemVO = new StatisticsChartItemVO();
            statisticsChartItemVO.setMonth(String.valueOf(i));
            statisticsChartItemVO.setTotal(map.getOrDefault(i, 0L));
            dataList.add(statisticsChartItemVO);
        }
        return dataList;
    }

    /**
     * 图标数据处理
     *
     * @param map
     * @return
     */
    private List<StatisticsChartItemVO> chartDataListForYearHandler(String startStr, Map<String, Long> map) {
        List<StatisticsChartItemVO> dataList = new ArrayList<>();
        StatisticsChartItemVO statisticsChartItemVO;
        Date tempDate = DateHelper.getDate(startStr, DateHelper.DAY_FORMAT_MONTH);
        for (int i = 1; i <= MAX_MONTH; i++) {
            statisticsChartItemVO = new StatisticsChartItemVO();
            if (i == 1) {
                statisticsChartItemVO.setMonth(startStr);
                statisticsChartItemVO.setTotal(map.getOrDefault(startStr, 0L));
            } else {
                String oldYearMonth = DateHelper.getDate(tempDate, DateHelper.DAY_FORMAT_MONTH);
                String year = DateHelper.getDate(tempDate, "yyyy");
                String newYearMonth = DateHelper.plusDate(oldYearMonth, 2, 1, DateHelper.DAY_FORMAT_MONTH);
                Date newDate = DateHelper.getDate(newYearMonth, DateHelper.DAY_FORMAT_MONTH);
                String newYear = DateHelper.getDate(newDate, "yyyy");
                if (year.equals(newYear)) {
                    String month = DateHelper.getDate(newDate, "MM");
                    statisticsChartItemVO.setMonth(month);
                } else {
                    statisticsChartItemVO.setMonth(newYearMonth);
                }
                statisticsChartItemVO.setTotal(map.getOrDefault(newYearMonth, 0L));
                tempDate = newDate;
            }
            dataList.add(statisticsChartItemVO);
        }
        return dataList;
    }

    @Override
    public Map<String, Long> queryDat(SynthesizeDataDTO synthesizeDataDTO) {
        HashMap<String, Long> map = new HashMap<>();
        long countPrescription = this.prescriptionServiceI.countNewPrescription(synthesizeDataDTO);
        long countMember = this.memberService.countNewMember(synthesizeDataDTO);
        long countPackage = this.packageService.countNewPackage(synthesizeDataDTO);
        long countFollow = this.followServiceI.countNewFollow(synthesizeDataDTO);

        map.put("countPrescription", countPrescription);
        map.put("countFollow", countFollow);
        map.put("countMember", countMember);
        map.put("countPackage", countPackage);
        return map;
    }

    @Override
    public Result getMemberParamStatistics(String memberId, String startDt, String endDt) {
        //取用户血糖数据
        ListBloodSugarDTO dto = new ListBloodSugarDTO();
        dto.setStartDt(startDt);
        dto.setEndDt(endDt);
        dto.setMemberId(memberId);
        dto.setOrderASC("ASC");
        dto.setRecordType(1);
        List<BloodSugarPO> paramLogModels = this.bloodSugarService.listBloodSugar(dto);

        List<Map<String, Object>> paramLogModelByDate = this.getMemberParamLogDriftance(paramLogModels);
        Map<String, Object> resMap = new HashMap<String, Object>(2);
        resMap.put("paramLogList", paramLogModels);
        resMap.put("paramLogModelByDate", paramLogModelByDate);
        Result result = new Result("0", "获取成功", true);
        result.setObj(resMap);
        return result;
    }

    @Override
    public Map<String, Object> getGraphsForParametersNewBloodSugar(String memberId, String startDt, String endDt) {
        //取用户血糖数据
        ListBloodSugarDTO dto = new ListBloodSugarDTO();
        dto.setStartDt(startDt);
        dto.setEndDt(endDt);
        dto.setMemberId(memberId);
        dto.setOrderASC("ASC");
        List<BloodSugarPO> paramLogModels = this.bloodSugarService.listBloodSugar(dto);

        List<Map<String, Object>> paramLogModelByDate = this.getMemberParamLog(paramLogModels);
        Map<String, Object> resMap = new HashMap<String, Object>(3);
        //		//餐后目标
        RangeBO range = this.memberMapper.getMemberRange(memberId);
        BloodRecordRangeModel rangeModel = new BloodRecordRangeModel();

        if (range != null) {
            org.springframework.beans.BeanUtils.copyProperties(range, rangeModel);
        } else {
            rangeModel.setLowBeforeBreakfast("4.4");
            rangeModel.setHighBeforeBreakfast("7.0");
            rangeModel.setLowAfterMeal("4.4");
            rangeModel.setHighAfterMeal("10.0");
        }
        resMap.put("paramLogList", paramLogModels);
        resMap.put("paramLogModelByDate", paramLogModelByDate);
        resMap.put("range", rangeModel);//目标
        return resMap;
    }

    @Override
    public JSONObject getStatistics(GetStatisticsDTO dto) {
        JSONObject jsonObject = new JSONObject();
        dto = this.initGetStatisticsDTO(dto);
        //boolean statistics = dto.isStatistics();
        boolean statistics = true;
        //患者数量
        long memberCount = 0L;
        if (statistics) {
//            GetStatisticsDTO dto1 = new GetStatisticsDTO();
//            BeanUtils.copyProperties(dto1, dto);
//            dto1.setStartDt(null);
            memberCount = this.memberService.countMemberWhere(dto);
            if (memberCount <= 0 && !"1".equals(dto.getPageType())) {
                statistics = false;
            }
        }
        if (statistics) {
            if ("1".equals(dto.getPageType())) {
                //20220407删除订单模块
//                Map<String, Object> map = new HashMap<>(4);
//                map.put("numberStatistics", this.getNumberStatisticsWhere(dto));
//                map.put("orderChartData", this.listOrderChartDataWhere(dto));
//                map.put("statisticsChartData", this.listStatisticsChartDataWhere(dto));
//                map.put("rangeOfPrescription", this.getRangeOfPrescription(dto));
//                map.put("rateOfPrescription", this.getRateOfPrescription(dto, memberCount));
//                jsonObject.put("orderStatistics", map);

            } else if ("2".equals(dto.getPageType())) {
                Map<String, Object> memberStatistics = new HashMap<>(5);
                //人数总数
                memberStatistics.put("memberCount", memberCount);

                //住院-出入院患者人数
                if ("1".equals(dto.getVisitType())) {
                    dto.setInHos(0);
                    memberStatistics.put("outHospitalMemberNum", this.getOutOrInHospitalMemberNum(dto));
                    dto.setInHos(1);
                    memberStatistics.put("inHospitalMemberNum", this.getOutOrInHospitalMemberNum(dto));
                    //性别分布人数
                    memberStatistics.put("rateOfMemberSex", this.getRateOfMemberSex(dto, memberCount));
                    //年龄分布人数
                    memberStatistics.put("getRangeOfMemberAge", this.getRangeOfMemberAge(dto, memberCount));
                    //人次总数
                    memberStatistics.put("memberNumber", this.getMemberNumber(dto));
                } else if ("2".equals(dto.getVisitType())) {
                    //门诊居家-新增患者数量
                    SynthesizeDataDTO dataDTO = new SynthesizeDataDTO();
                    dataDTO.setStartDt(dto.getStartDt());
                    dataDTO.setEndDt(dto.getEndDt());
                    dataDTO.setHospitalId(dto.getHospitalId());
                    long countNewMember = this.memberService.countNewMember(dataDTO);
                    memberStatistics.put("addNewMemberCount", countNewMember);
                    //性别分布人数
                    memberStatistics.put("rateOfMemberSex", this.getRateOfMemberSex(dto, countNewMember));
                    //年龄分布人数
                    memberStatistics.put("getRangeOfMemberAge", this.getRangeOfMemberAge(dto, countNewMember));
                    //人次总数
                    memberStatistics.put("memberNumber", this.getMemberNumber(dto));
                }
                //科研
                else if ("3".equals(dto.getVisitType())) {
                    //性别分布人数
                    memberStatistics.put("rateOfMemberSex", this.getRateOfMemberSex(dto, memberCount));
                    //年龄分布人数
                    memberStatistics.put("getRangeOfMemberAge", this.getRangeOfMemberAge(dto, memberCount));
                    // 佩戴动态血糖人数统计
                    memberStatistics.put("hasDynamicBloodSugar", this.hasDynamicBloodSugarSensorStatistics(dto));
                }
                jsonObject.put("memberStatistics", memberStatistics);

            } else if ("3".equals(dto.getPageType())) {
                jsonObject.put("bmiStatistics", this.getRangeOfBmi(dto));

            } else if ("4".equals(dto.getPageType())) {
                jsonObject.put("bloodPressureStatistics", this.getRateOfBloodPressure(dto, 1));

            } else if ("5".equals(dto.getPageType())) {
                Map<String, Object> fatStatistics = new HashMap<>(4);
                fatStatistics.put("tgStatistics", this.getRateOfTG(dto));
                fatStatistics.put("tcStatistics", this.getRateOfTC(dto));
                fatStatistics.put("ldlcStatistics", this.getRateOfLDLC(dto));
                fatStatistics.put("hdlcStatistics", this.getRateOfHDLC(dto));
                jsonObject.put("bloodFatStatistics", fatStatistics);

            } else if ("6".equals(dto.getPageType())) {
                jsonObject.put("hba1cStatistics", this.getRangeOfSugarHemoglobin(dto));

            } else if ("7".equals(dto.getPageType())) {
                jsonObject.put("bloodSugarStatistics", this.getRateOfBloodSugar(dto));

            } else if ("8".equals(dto.getPageType())) {
                Map<String, Object> screeningStatistics = new HashMap<>(5);
                screeningStatistics.put("rateOfIntelligent", this.getRateOfIntelligent(dto, memberCount));
                screeningStatistics.put("rangeOfIntelligent", this.getRangeOfIntelligent(dto));
                screeningStatistics.put("screeningPatientInfo", this.screeningStatsService.screeningPatientInfoStatsForHos(dto));
                screeningStatistics.put("itemPieChartData", this.screeningStatsService.screeningPieChartDataForHos(dto));
                screeningStatistics.put("chronicDisease", this.screeningStatsService.chronicDiseaseStatsForHos(dto));
                jsonObject.put("complicationStatistics", screeningStatistics);

            } else if ("9".equals(dto.getPageType())) {
                Map<String, Object> drugStatistics = new HashMap<>(2);
                drugStatistics.put("drugStatic", this.drugStatic(dto));
                drugStatistics.put("drugInsulinStatic", this.drugInsulinStatic(dto));
                jsonObject.put("drugStatistics", drugStatistics);

            } else if ("10".equals(dto.getPageType())) {
                Map<String, Object> signStatistics = new HashMap<>(2);
                signStatistics.put("bmiStatistics", this.getRangeOfBmi(dto));
                signStatistics.put("hba1cStatistics", this.getRangeOfSugarHemoglobin(dto));
                jsonObject.put("signStatistics", signStatistics);

            } else if ("11".equals(dto.getPageType())) {
                Map<String, Object> bloodPressureStatistics = new HashMap<>(2);
                //3 科研
                if ("3".equals(dto.getVisitType())) {
                    jsonObject.put("bloodPressureStatistics", this.getRateOfBloodPressure(dto, 1));
                } else {
                    bloodPressureStatistics.put("bloodPressureStatisticsOfInHos", this.getRateOfBloodPressure(dto, 1));
                    bloodPressureStatistics.put("bloodPressureStatisticsOfOutHos", this.getRateOfBloodPressure(dto, 0));
                    jsonObject.put("bloodPressureStatistics", bloodPressureStatistics);
                }
            }
            //虚拟病区
            else if ("12".equals(dto.getPageType())) {
                jsonObject.put("virtualWardStatistics", getVirtualWardStatistics(dto));
            }
        }
        return jsonObject;
    }

    /**
     * 初始化统计参数
     *
     * @param dto
     * @return
     */
    private GetStatisticsDTO initGetStatisticsDTO(GetStatisticsDTO dto) {
        boolean statistics = true;
        String doctorId = dto.getDoctorId();
        String hospitalId = dto.getHospitalId();
        String today = DateHelper.getToday();
        String startDt = today;
        String endDt = today;
        if (!StringUtils.isBlank(dto.getDtCode())) {
            endDt = today;
            if ("1".equals(dto.getDtCode())) {
                startDt = DateHelper.getToday();
            } else if ("2".equals(dto.getDtCode())) {
                startDt = DateHelper.plusDate(endDt, -6);
            } else if ("3".equals(dto.getDtCode())) {
                startDt = DateHelper.plusDate(endDt, -89);
            } else if ("4".equals(dto.getDtCode())) {
                startDt = DateHelper.plusDate(endDt, -179);
            } else if ("5".equals(dto.getDtCode())) {
                startDt = DateHelper.plusDate(endDt, -364);
            } else {
                if (!StringUtils.isBlank(dto.getStartDt())) {
                    startDt = dto.getStartDt();
                }
                if (!StringUtils.isBlank(dto.getEndDt())) {
                    endDt = dto.getEndDt();
                }
            }
        } else {
            if (!StringUtils.isBlank(dto.getStartDt())) {
                startDt = dto.getStartDt();
            }
            if (!StringUtils.isBlank(dto.getEndDt())) {
                endDt = dto.getEndDt();
            }
        }
        dto.setStartDt(startDt + DateHelper.DEFUALT_TIME_START);
        dto.setEndDt(endDt + DateHelper.DEFUALT_TIME_END);

        //住院统计
//        if ("1".equals(dto.getVisitType())) {
//            //获取医护人员可以管理的科室编号
//            List<DoctorDepartmentVO> doctorDepartVOS = null;
//            List<DepartmentPO> vos = null;
//            if (!StringUtils.isBlank(hospitalId)) {
//                vos = this.doctorService.listDoctorDepartHasAccountByHosId(hospitalId);
//                if (vos == null || vos.size() <= 0) {
//                    statistics = false;
//                }
//                if (statistics) {
//                    List<String> departIds = new ArrayList<>(vos.size());
//                    for (DepartmentPO vo : vos) {
//                        departIds.add(vo.getDepartmentId());
//                    }
//                    dto.setDepartIdList(departIds);
//                }
//            } else {
//                doctorDepartVOS = this.doctorService.listDoctorDepartment(doctorId);
//                if (doctorDepartVOS == null || doctorDepartVOS.size() <= 0) {
//                    statistics = false;
//                }
//                if (statistics) {
//                    List<String> departIds = new ArrayList<>(doctorDepartVOS.size());
//                    for (DoctorDepartmentVO vo : doctorDepartVOS) {
//                        departIds.add(vo.getDepartmentId());
//                    }
//                    dto.setDepartIdList(departIds);
//                }
//            }
//        } else if ("2".equals(dto.getVisitType())) {
//            // 门诊/居家患者
//            List<DoctorPO> listMyDoctor = null;
//            if (!StringUtils.isBlank(hospitalId)) {
//                listMyDoctor = this.doctorService.listDoctorByHospitalId(hospitalId);
//            } else {
//                listMyDoctor = this.doctorService.listMyDoctor(doctorId);
//            }
//            if (listMyDoctor == null || listMyDoctor.size() <= 0) {
//                statistics = false;
//            }
//            if (statistics) {
//                List<String> doctorIdList = new ArrayList<>(listMyDoctor.size());
//                for (DoctorPO vo : listMyDoctor) {
//                    doctorIdList.add(vo.getDoctorId());
//                }
//                dto.setDoctorIdList(doctorIdList);
//                dto.setDoctorId(null);
//            }
//        }
//        dto.setStatistics(statistics);
        return dto;
    }

    /**
     * 住院-患者出入院人数
     *
     * @param dto
     * @return
     */
    private Long getOutOrInHospitalMemberNum(GetStatisticsDTO dto) {
        return this.memberService.getOutOrInHospitalMemberNum(dto);
    }

    @Override
    public Result getMemberParamCount(String memberId, String startDt, String endDt, String paramCode, Integer inHos) {
        //判断paramCode是否存在，如果不存在就取全部
        List<String> paramCodeList = new ArrayList<String>();
        if (!ValidateTool.checkIsNull(paramCode)) {
            paramCodeList = ParamLogConstant.getDiabetesCode();
        } else {
            paramCodeList.add(ParamLogConstant.getParamcodeByParamcodeNumber(paramCode));
        }
        //获取血糖记录
        ListBloodSugarDTO dto = new ListBloodSugarDTO();
        dto.setMemberId(memberId);
        if (!StringUtils.isBlank(startDt) && startDt.length() < 11) {
            startDt += DateHelper.DEFUALT_TIME_START;
        }
        if (!StringUtils.isBlank(endDt) && endDt.length() < 11) {
            endDt += DateHelper.DEFUALT_TIME_END;
        }
        dto.setStartDt(startDt);
        dto.setEndDt(endDt);
        dto.setParamCodeList(paramCodeList);
        if (inHos == null) {
            inHos = 0;
        }
        dto.setInHos(inHos);
        List<BloodSugarPO> memberParamLogModels = this.bloodSugarService.listSugar(dto);

        double minValue = 0;
        double maxValue = 0;
        double avgValue = 0;
        double totalValue = 0;
        int totalNum = 0;
        int lowNum = 0;
        int highNum = 0;
        int norNum = 0;

        if (memberParamLogModels != null && memberParamLogModels.size() > 0) {
            totalNum = memberParamLogModels.size();
            double firstValue = Double.valueOf(memberParamLogModels.get(0).getParamValue());
            minValue = firstValue;
            maxValue = firstValue;
            for (BloodSugarPO paramLogModel : memberParamLogModels) {
                double value = Double.valueOf(paramLogModel.getParamValue());
                totalValue = AlgorithmUtils.add(totalValue, value);
                int level = Integer.valueOf(paramLogModel.getParamLevel());
                if (level > 0 && level < 3) {
                    lowNum++;
                } else if (level > 3 && level <= 5) {
                    highNum++;
                } else {
                    norNum++;
                }
                minValue = value > minValue ? minValue : value;
                maxValue = value > maxValue ? value : maxValue;
            }
        }

        if (totalValue != 0 && totalNum != 0) {
            avgValue = AlgorithmUtils.divide("2", totalValue, totalNum);
        }

        Map<String, Object> resMap = new HashMap<String, Object>(8);
        resMap.put("minValue", minValue);
        resMap.put("maxValue", maxValue);
        resMap.put("avgValue", avgValue);
        resMap.put("totalValue", totalValue);
        resMap.put("totalNum", totalNum);
        resMap.put("lowNum", lowNum);
        resMap.put("highNum", highNum);
        resMap.put("norNum", norNum);
        Result result = new Result("0", "获取成功", true);
        result.setObj(resMap);
        return result;
    }

    @Override
    public Map<String, Object> loadNavigationItemData(List<String> items, List<String> hospitalIdList, String date) {
        Map<String, Object> map = new HashMap<>();
        if (!items.isEmpty()){
            for (String item : items) {
                navigationItemDataHandler(item,map,hospitalIdList,date);
            }
        }
        return map;
    }


    private void navigationItemDataHandler(String item,Map<String, Object> map,List<String> hospitalIdList,String date){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        if (item.equals(StatisticsNavigationItemEnum.BLOOD_SUGAR_EXCEPTION.getCode())){
            //血糖监测人数
            Long num = staticsMapper.loadDoSugarMembersNum(hospitalIdList, date);
            map.put(StatisticsNavigationItemEnum.BLOOD_SUGAR_EXCEPTION.getCode(),num == null?0:num);
        }else if (item.equals(StatisticsNavigationItemEnum.BLOOD_HBA1C_EXCEPTION.getCode())){
            //糖化检查人数
            Long num = staticsMapper.loadDoHa1cMembersNum(hospitalIdList, date);
            map.put(StatisticsNavigationItemEnum.BLOOD_HBA1C_EXCEPTION.getCode(),num == null?0:num);
        }else if (item.equals(StatisticsNavigationItemEnum.BLOOD_PRESSURE_EXCEPTION.getCode())){
            //血压监测人数
            Long num = staticsMapper.loadDoBloodPressureMembersNum(hospitalIdList, date);
            map.put(StatisticsNavigationItemEnum.BLOOD_PRESSURE_EXCEPTION.getCode(),num == null?0:num);
        }else if (item.equals(StatisticsNavigationItemEnum.BLOOD_SUGAR_LEVER_EXCEPTION.getCode())){
            //血糖分标异常人数
            Long num = staticsMapper.loadSugarLevelMembersNum(hospitalIdList, date);
            map.put(StatisticsNavigationItemEnum.BLOOD_SUGAR_LEVER_EXCEPTION.getCode(),num == null?0:num);
        }else if (item.equals(StatisticsNavigationItemEnum.BLOOD_PRESSURE_LEVER_EXCEPTION.getCode())){
            //血压分级分层异常人数
            Long num = staticsMapper.loadPressureLevelLayerMembersNum(hospitalIdList, date);
            map.put(StatisticsNavigationItemEnum.BLOOD_PRESSURE_LEVER_EXCEPTION.getCode(),num == null?0:num);
        }else if (item.equals(StatisticsNavigationItemEnum.TODAY_SCREEN_EXCEPTION.getCode())){
            //今日并发症筛查人数
            Long num = staticsMapper.loadDoScreenMembersNum(hospitalIdList,date,ScreeningConstant.PRE_DEAL_STATUS_REPORT_CONFIRM);
            map.put(StatisticsNavigationItemEnum.ALL_SCREEN_EXCEPTION.getCode(),num == null?0:num);
        }else if (item.equals(StatisticsNavigationItemEnum.ALL_SCREEN_EXCEPTION.getCode())){
            //所有并发症筛查人数
            Long num = staticsMapper.loadDoScreenMembersNum(hospitalIdList,null,ScreeningConstant.PRE_DEAL_STATUS_REPORT_CONFIRM);
            map.put(StatisticsNavigationItemEnum.ALL_SCREEN_EXCEPTION.getCode(),num == null?0:num);
        }
        else if (item.equals(StatisticsNavigationItemEnum.TODAY_IN_HOSPITAL_NUM.getCode())){
            //今日建档人数
            Long num = staticsMapper.loadInHospitalMembersNum(hospitalIdList, date);
            map.put(StatisticsNavigationItemEnum.TODAY_IN_HOSPITAL_NUM.getCode(),num == null?0:num);
        }
        else if (item.equals(StatisticsNavigationItemEnum.TODAY_MANAGE_PRESC_NUM.getCode())){
            //今日管理处方人数
            Long num = staticsMapper.loadDayPrescriptionNum(hospitalIdList, date);
            map.put(StatisticsNavigationItemEnum.TODAY_MANAGE_PRESC_NUM.getCode(),num == null?0:num);
        }
        else if (item.equals(StatisticsNavigationItemEnum.TODAY_FOLLOW_NUM.getCode())){
            //今日随访人数
            Long num = staticsMapper.loadDayFollowNum(hospitalIdList, date);
            map.put(StatisticsNavigationItemEnum.TODAY_FOLLOW_NUM.getCode(),num == null?0:num);
        }
        else if (item.equals(StatisticsNavigationItemEnum.MANAGE_MEMBER_NUM.getCode())){
            //管理人数
            Long num = staticsMapper.loadHospitalMemberNum(hospitalIdList);
            map.put(StatisticsNavigationItemEnum.MANAGE_MEMBER_NUM.getCode(),num == null?0:num);
        }
        else if (item.equals(StatisticsNavigationItemEnum.DIABETES_MEMBER_NUM.getCode())){
            //糖尿病人数
            Long num = staticsMapper.loadHospitalDiabNum(hospitalIdList,null);
            map.put(StatisticsNavigationItemEnum.DIABETES_MEMBER_NUM.getCode(),num == null?0:num);
        }
        else if (item.equals(StatisticsNavigationItemEnum.HYP_MEMBER_NUM.getCode())){
            //高血压人数
            Long num = staticsMapper.loadHospitalHypNum(hospitalIdList,null);
            map.put(StatisticsNavigationItemEnum.HYP_MEMBER_NUM.getCode(),num == null?0:num);
        }
        else if (item.equals(StatisticsNavigationItemEnum.DIABETES_MEMBER_DAY_NUM.getCode())){
            //日糖尿病人数
            Long num = staticsMapper.loadHospitalDiabNum(hospitalIdList,date);
            map.put(StatisticsNavigationItemEnum.DIABETES_MEMBER_DAY_NUM.getCode(),num == null?0:num);
        }
        else if (item.equals(StatisticsNavigationItemEnum.HYP_MEMBER_DAY_NUM.getCode())){
            //日高血压人数
            Long num = staticsMapper.loadHospitalHypNum(hospitalIdList,date);
            map.put(StatisticsNavigationItemEnum.HYP_MEMBER_DAY_NUM.getCode(),num == null?0:num);
        }
        else if (item.equals(StatisticsNavigationItemEnum.DIABETES_RATE.getCode())){
            //糖尿病占比
            diabRateHandler(map,hospitalIdList,numberFormat);
        }
        else if (item.equals(StatisticsNavigationItemEnum.HYP_RATE.getCode())){
            //高血压占比
            hypRateHandler(map,hospitalIdList,numberFormat);
        }
        //今日门诊患者人数（本院建档）
        else if (item.equals(StatisticsNavigationItemEnum.TODAY_VISIT.getCode())){
            long num = staticsMapper.countMemberJoinVisit(hospitalIdList,DateHelper.getToday());
            map.put(StatisticsNavigationItemEnum.TODAY_VISIT.getCode(),num);
        }
        //本年度体检患者
        else if (item.equals(StatisticsNavigationItemEnum.YEAR_CHECK.getCode())){
            String year = DateHelper.getDate(new Date(),"yyyy");
            long num = memberMapper.countMemberInspection(hospitalIdList, year);
            map.put(StatisticsNavigationItemEnum.YEAR_CHECK.getCode(),num);
        }
        //今日异常患者人数
        else if (item.equals(StatisticsNavigationItemEnum.ABNORMAL_NUM.getCode())){
            Long num = staticsMapper.loadMemberExceptionNum(date, hospitalIdList);
            map.put(StatisticsNavigationItemEnum.ABNORMAL_NUM.getCode(),num);
        }
    }


    //糖尿病占比
    private void diabRateHandler(Map<String, Object> map,List<String> hospitalIdList,NumberFormat numberFormat){
        double diabNumd = 0d;
        double memberNumd = 0d;
        Long diabNum = staticsMapper.loadHospitalDiabNum(hospitalIdList,null);
        if (diabNum != null){
            diabNumd = Double.valueOf(Long.toString(diabNum));
        }
        Long memberNum = staticsMapper.loadHospitalMemberNum(hospitalIdList);
        if (memberNum != null){
            memberNumd = Double.valueOf(Long.toString(memberNum));
        }
        map.put(StatisticsNavigationItemEnum.DIABETES_RATE.getCode(),memberNumd == 0d?"0%":numberFormat.format((diabNumd/memberNumd)*100) + "%");
    }

    //高血压占比
    private void hypRateHandler(Map<String, Object> map,List<String> hospitalIdList,NumberFormat numberFormat){
        double hypNumd = 0d;
        double memberNumd = 0d;
        Long hypNum = staticsMapper.loadHospitalHypNum(hospitalIdList,null);
        if (hypNum != null){
            hypNumd = Double.valueOf(Long.toString(hypNum));
        }
        Long memberNum = staticsMapper.loadHospitalMemberNum(hospitalIdList);
        if (memberNum != null){
            memberNumd = Double.valueOf(Long.toString(memberNum));
        }
        map.put(StatisticsNavigationItemEnum.HYP_RATE.getCode(),memberNumd == 0d?"0%":numberFormat.format((hypNumd/memberNumd)*100) + "%");
    }


    @Override
    public Map<String, Object> loadHospitalDiseaseDataForScreen(List<String> hospitalIdList,String date) {
        String startDt = null;
        String endDt = null;
        if (!StringUtils.isBlank(date)){
            startDt = date+ DateHelper.DEFUALT_TIME_START;
            endDt = date + DateHelper.DEFUALT_TIME_END;
        }
        Map<String, Object> level = getHospitalLevelStatics(hospitalIdList, startDt, endDt);
        return level;
    }



    @Override
    public Map<String, Object> loadHospitalDiseaseDataForWork(List<String> hospitalIdList,String date) {
        String startDt = null;
        String endDt = null;
        if (!StringUtils.isBlank(date)){
            startDt = date+ DateHelper.DEFUALT_TIME_START;
            endDt = date + DateHelper.DEFUALT_TIME_END;
        }
        //分层分级（日or年）
        Map<String, Object> level = getHospitalLevelStatics(hospitalIdList, startDt, endDt);
        //慢病效果情况(年)
        String yearStartDt = DateHelper.getDate(new Date(),"yyyy")+ "-01-01 00:00:00";
        String yearEndDt = DateHelper.getTime();
        Map<String, Object> disease = loadImportantIndicByHospitalId(hospitalIdList, yearStartDt, yearEndDt);

        Map<String, Object> result = new HashMap<>();
        result.putAll(level);
        result.put("disease",disease);
        return result;
    }


    //分层分级情况统计
    private Map<String, Object> getHospitalLevelStatics(List<String> hospitalIdList,String startDt,String endDt){
        ListMemberLevelDTO listMemberLevelDTO = new ListMemberLevelDTO();
        listMemberLevelDTO.setLevelType(MemberLevelConstant.TNB_TYPE);
        listMemberLevelDTO.setHospitalIdList(hospitalIdList);
        listMemberLevelDTO.setConfirm(MemberLevelConstant.YES);
        listMemberLevelDTO.setStartDt(startDt);
        listMemberLevelDTO.setEndDt(endDt);
        //糖尿病
        Map<String, Object> tnb = tnbHospitalDiseaseDataHandler(listMemberLevelDTO);
        //高血压
        listMemberLevelDTO.setLevelType(MemberLevelConstant.GXY_TYPE);
        Map<String, Object> gxy = gxyHospitalDiseaseDataHandler(listMemberLevelDTO);
        Map<String, Object> result = new HashMap<>();
        result.put("tnb",tnb);
        result.put("gxy",gxy);
        return result;
    }


    @Override
    public Map<String, Object> loadHospitalHealthManageData(List<String> hospitalIdList,String date) {
        String startDt = null;
        String endDt = null;
        if (!StringUtils.isBlank(date)){
            startDt = date +DateHelper.DEFUALT_TIME_START;
            endDt = date +DateHelper.DEFUALT_TIME_END;
        }

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);//小数点后1位
        GetPrescriptionDTO getPrescriptionDTO = new GetPrescriptionDTO();
        getPrescriptionDTO.setHospitalIdList(hospitalIdList);
        getPrescriptionDTO.setIsValid(1);
        getPrescriptionDTO.setHandDown(1);
        getPrescriptionDTO.setStartDt(startDt);
        getPrescriptionDTO.setEndDt(endDt);

        List<PrescriptionPO> list = this.prescriptionMapper.listPrescriptionByParam(getPrescriptionDTO);//所有管理处方
        double allPresNumd = Double.valueOf(Integer.toString(list.size()));
        double presPeopleNumd = 0d;
        double followNumd = 0d;
        double followMemberNumd = 0d;

        Long presPeopleNum = this.prescriptionMapper.getPrescriptionPeopleNum(getPrescriptionDTO);//人数
        if (presPeopleNum != null){
            presPeopleNumd = Double.valueOf(Long.toString(presPeopleNum));
        }
        Long followNum = this.followMapper.getFollowNumByHospitalId(hospitalIdList,startDt,endDt);
        if (followNum != null){
            followNumd = Double.valueOf(Long.toString(followNum));
        }
        Long followMemberNum = this.followMapper.getFollowMemberNumByHospitalId(hospitalIdList,startDt,endDt);
        if (followMemberNum != null){
            followMemberNumd = Double.valueOf(Long.toString(followMemberNum));
        }
        //管理处方
        Map<String, Object> result = new HashMap<>();
        result.put("presMemberNum",presPeopleNumd);
        result.put("presNum",allPresNumd);
        result.put("presAvg",presPeopleNumd == 0d?"0%":numberFormat.format(allPresNumd/presPeopleNumd));
        //随访
        result.put("followMemberNum",followMemberNumd);
        result.put("followNum",followNumd);
        result.put("avgFollow",followMemberNumd == 0d?"0%":numberFormat.format(followNumd/followMemberNumd));
        return result;
    }

    @Override
    public Map<String, Object> loadHospitalComplicationData(List<String> hospitalIdList, String startDt, String endDt) {
        ListMemberJoinHospitalDTO listMemberJoinHospitalDTO = new ListMemberJoinHospitalDTO();
        listMemberJoinHospitalDTO.setHospitalIdList(hospitalIdList);
        listMemberJoinHospitalDTO.setBegin(startDt);
        listMemberJoinHospitalDTO.setEnd(startDt);
        double allMemberNumd = 0d;
        double allNumd = 0d;
        double eyeTotalNumd = 0d;
        double eyePassNumd = 0d;
        double abiTotalNumd = 0d;
        double abiPassNumd = 0d;
        double vptTotalNumd = 0d;
        double vptPassNumd = 0d;
        List<MemberJoinHospitalPO> memberJoinHospitalPOList = memberJoinHospitalMapper.listMemberJoinHospital(listMemberJoinHospitalDTO);
        allMemberNumd = Double.valueOf(Integer.toString(memberJoinHospitalPOList.size()));
        GetScreeningReportDTO param = new GetScreeningReportDTO();
        param.setHospitalIdList(hospitalIdList);
        param.setStartDt(startDt);
        param.setEndDt(endDt);
        param.setDealStatus(ScreeningConstant.PRE_DEAL_STATUS_REPORT_CONFIRM);
        //所有筛查人数
        Long allNum = screeningReportMapper.getScreeningOfMemberCountByHospitalId(param);
        if (allNum != null){
            allNumd = Double.valueOf(Long.toString(allNum));
        }
        //眼底人数
        param.setScreeningType(3);
        Long eyeTotalNum = screeningReportMapper.getScreeningOfMemberCountByHospitalId(param);
        if (eyeTotalNum != null){
            eyeTotalNumd = Double.valueOf(Long.toString(eyeTotalNum));
        }
        //眼底正常人数
        param.setResultStatus(1);
        Long eyePassNum = screeningReportMapper.getScreeningOfMemberCountByHospitalId(param);
        if (eyePassNum != null){
            eyePassNumd = Double.valueOf(Long.toString(eyePassNum));
        }
        //abi
        param.setScreeningType(1);
        param.setResultStatus(null);
        Long abiTotalNum = screeningReportMapper.getScreeningOfMemberCountByHospitalId(param);
        if (abiTotalNum != null){
            abiTotalNumd = Double.valueOf(Long.toString(abiTotalNum));
        }
        //abi正常
        param.setResultStatus(1);
        Long abiPassNum = screeningReportMapper.getScreeningOfMemberCountByHospitalId(param);
        if (abiPassNum != null){
            abiPassNumd = Double.valueOf(Long.toString(abiPassNum));
        }
        //vpt
        param.setScreeningType(2);
        param.setResultStatus(null);
        Long vptTotalNum = screeningReportMapper.getScreeningOfMemberCountByHospitalId(param);
        if (vptTotalNum != null){
            vptTotalNumd = Double.valueOf(Long.toString(vptTotalNum));
        }
        //vpt正常
        param.setResultStatus(1);
        Long vptPassNum = screeningReportMapper.getScreeningOfMemberCountByHospitalId(param);
        if (vptPassNum != null){
            vptPassNumd = Double.valueOf(Long.toString(vptPassNum));
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);//小数点后1位
        Map<String, Object> result = new HashMap<>();
        result.put("screenMemberNum",allNumd);//筛查人数
        result.put("screenRate",allMemberNumd == 0d?"0%": numberFormat.format((allNum/allMemberNumd)*100) + "%");//筛查率

        result.put("eyeMemberNum",eyeTotalNumd);//眼底筛查
        result.put("eyeNormalRate",eyeTotalNumd == 0d?"0%":numberFormat.format((eyePassNumd/eyeTotalNumd)*100) + "%");//正常筛查率
        result.put("eyeAbnormalRate",eyeTotalNumd == 0d?"0%":numberFormat.format(((eyeTotalNumd-eyePassNumd)/eyeTotalNumd)*100) + "%");//异常筛查率

        result.put("abiMemberNum",abiTotalNumd);
        result.put("abiNormalRate",abiTotalNumd == 0d?"0%":numberFormat.format((abiPassNumd/abiTotalNumd)*100) + "%");//正常筛查率
        result.put("abiAbnormalRate",abiTotalNumd == 0d?"0%":numberFormat.format(((abiTotalNumd-abiPassNumd)/abiTotalNumd)*100) + "%");//异常筛查率

        result.put("vptMemberNum",vptTotalNumd);
        result.put("vptNormalRate",vptTotalNumd == 0d?"0%":numberFormat.format((vptPassNumd/vptTotalNumd)*100) + "%");
        result.put("vptAbnormalRate",vptTotalNumd == 0d?"0%":numberFormat.format(((vptTotalNumd-vptPassNumd)/vptTotalNumd)*100) + "%");

       //处理pwv   abi报告包含pwv报告
        ListMemberScreeningReportDTO listMemberScreeningReportDTO = new ListMemberScreeningReportDTO();
        listMemberScreeningReportDTO.setHospitalIdList(hospitalIdList);
        listMemberScreeningReportDTO.setStartDt(startDt);
        listMemberScreeningReportDTO.setEndDt(endDt);
        listMemberScreeningReportDTO.setScreeningType(1);
        listMemberScreeningReportDTO.setDealStatus(ScreeningConstant.PRE_DEAL_STATUS_REPORT_CONFIRM);
        //所有的abi报告
        List<ScreeningReportPO> screeningReportPOS = screeningReportMapper.listMemberScreeningReportByHospitalId(listMemberScreeningReportDTO);
        Double pwvReportNum = 0d;
        Double pwvAbnormalNum = 0d;
        Double pwvMemberNum = 0d;
        //具有pwv的报告列表
        List<ScreeningReportPO> pwvList = screeningReportPOS.stream().filter(e -> pwvReportExistHandler(e)).collect(Collectors.toList());
        if (null != pwvList ){
            pwvReportNum = (double) pwvList.size();
            Set<String> pwvMemberSet = pwvList.stream().map(e -> e.getMemberId()).collect(Collectors.toSet());
            if (null != pwvMemberSet){
                pwvMemberNum = (double)pwvMemberSet.size();
            }
        }
        //pwv异常的报告
        List<ScreeningReportPO> abnormalPwvList = pwvList.stream().filter(e -> pwvReportAbnormalHandler(e)).collect(Collectors.toList());
        if (null != abnormalPwvList ){
            pwvAbnormalNum = (double)abnormalPwvList.size();
        }
        result.put("pwvMemberNum",pwvMemberNum);
        result.put("pwvNormalRate",pwvReportNum == 0d?"0%":numberFormat.format(((pwvReportNum-pwvAbnormalNum)/pwvReportNum)*100) + "%");
        result.put("pwvAbnormalRate",pwvReportNum == 0d?"0%":numberFormat.format((pwvAbnormalNum/pwvReportNum)*100) + "%");
        return result;
    }

    //abi中存在pwv结果的报告
    private boolean pwvReportExistHandler(ScreeningReportPO reportPO){
        boolean flag = false;
        String dataJson = reportPO.getReportJson();
        Map<String, Object> map = JsonSerializer.jsonToMap(dataJson);
        if (null != map.get("leftPwv") || null != map.get("rightPwv")) {
            flag = true;
        }
        return flag;
    }

    //pwv是否异常
    private boolean pwvReportAbnormalHandler(ScreeningReportPO reportPO){
        boolean flag = false;
        String dataJson = reportPO.getDataJson();
        Map<String, Object> map = JsonSerializer.jsonToMap(dataJson);
        Object leftPwvO = map.get("leftPwv");
        Object rightPwvO = map.get("rightPwv");
        if (null != leftPwvO){
            Double leftPwv = Double.valueOf(String.valueOf(leftPwvO));
            if (leftPwv <1000 || leftPwv >2000){
                flag = true;
                return flag;
            }
        }
        if (null != rightPwvO){
            Double rightPwv = Double.valueOf(String.valueOf(rightPwvO));
            if (rightPwv <1000 || rightPwv >2000){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public Map<String, Object> loadImportantIndicByHospitalId(List<String> hospitalIdList, String startDt, String endDt) {
        Map<String, Object> hbacStatics = getHbacStatics(hospitalIdList, startDt, endDt);
        Map<String, Object> ldlStatics = getLdlStatics(hospitalIdList, startDt, endDt);
        Map<String, Object> bloodPressureStatics = getBloodPressureStatics(hospitalIdList, startDt, endDt);
        Map<String, Object> beforeBreakFastSugarStatics = getBeforeBreakFastSugarStatics(hospitalIdList);
        Map<String, Object> disease = new HashMap<>(4);
        disease.putAll(hbacStatics);
        disease.putAll(ldlStatics);
        disease.putAll(beforeBreakFastSugarStatics);
        disease.putAll(bloodPressureStatics);
        return disease;
    }

    @Override
    public List<Map<String, Object>> listDiseaseEffectByAreaId(String areaId,String startDt, String endDt) {
        List<HospitalPO> hospitalPOS = hospitalService.listHospitalByAreaId(areaId);
        List<Map<String, Object>> list = new ArrayList<>(hospitalPOS.size());
        if (!hospitalPOS.isEmpty()){
            for (HospitalPO hospitalPO : hospitalPOS) {
                List<String> hospitalIdList = Arrays.asList(hospitalPO.getHospitalId());
                Map<String, Object> map = this.loadImportantIndicByHospitalId(hospitalIdList, startDt, endDt);
                map.put("hospitalName",hospitalPO.getHospitalName());
                map.put("hospitalId",hospitalPO.getHospitalId());
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public List<CommitteeRankVO> loadCommitteeRankByHospitalId(String hospitalId) {
        HospitalCommitteePO getHospitalCommitteePO = new HospitalCommitteePO();
        getHospitalCommitteePO.setHospitalId(hospitalId);
        getHospitalCommitteePO.setValid(1);
        //居委会列表
        List<HospitalCommitteePO> hospitalCommitteeList = committeeService.listHospitalCommittee(getHospitalCommitteePO);
        hospitalCommitteeList.add(new HospitalCommitteePO("流动人口",hospitalId));
        return getCommitteeRank(hospitalCommitteeList, hospitalId);

    }

    @Override
    public List<CommitteeRankVO> loadCommitteeRankForH5(DoctorSessionBO doctorSessionBO) {
        String hospitalId = doctorSessionBO.getHospitalId();
        DoctorPO doctor = doctorServiceI.getDoctorById(doctorSessionBO.getDoctorId());
        Integer doctorLevel = doctor.getDoctorLevel();
        List<HospitalCommitteePO> hospitalCommitteeList = new ArrayList<>();
        if (null != doctorLevel){
            if (doctorLevel.equals(DoctorConstant.DOCTOR_LEVEL_TOWN)){
                HospitalCommitteePO getHospitalCommitteePO = new HospitalCommitteePO();
                getHospitalCommitteePO.setHospitalId(hospitalId);
                getHospitalCommitteePO.setValid(1);
                //居委会列表
                hospitalCommitteeList = committeeService.listHospitalCommittee(getHospitalCommitteePO);
                hospitalCommitteeList.add(new HospitalCommitteePO("流动人口",hospitalId));
            }else if (doctorLevel.equals(DoctorConstant.DOCTOR_LEVEL_VILLAGE)){
                hospitalCommitteeList = committeeService.listCommitteeByDoctorId(doctorSessionBO.getDoctorId());
            }else if (doctorLevel.equals(DoctorConstant.DOCTOR_LEVEL_COUNTY)){
                //todo 县级待定
            }
        }
        return getCommitteeRank(hospitalCommitteeList, hospitalId);
    }

    @Override
    public List<Map<String,Object>> loadHospitalLevel3Data(String areaId) {
        String today = DateHelper.getToday();
        String currentQuarterStartTime = DateHelper.getCurrentQuarterStartTime();//本季度第一天
        String currentQuarterEndTime = DateHelper.getCurrentQuarterEndTime();//本季度最后一天
        Map<String, Object> standardManageParam = getStandardManageParam();
        List<Map<String, Object>> result = new ArrayList<>();
        List<HospitalPO> hospitalPOS = hospitalService.listTownHospitalByAreaId(areaId);

        if (!hospitalPOS.isEmpty()){
            for (HospitalPO hospitalPO : hospitalPOS) {
                String hospitalId = hospitalPO.getHospitalId();
                //建档人数
                Long joinMemberNum = staticsMapper.loadInHospitalMembersNum(Arrays.asList(hospitalId), null);
                //本季度糖尿病随访人数
                Long quarterSugarFollowNum = staticsMapper.loadFollowNumByHospitalId(currentQuarterStartTime,currentQuarterEndTime, FollowConstant.FOLLOW_TYPE_TWO_DIABETES,hospitalId);
                //本季度高血压随访人数
                Long quarterHypFollowNum = staticsMapper.loadFollowNumByHospitalId(currentQuarterStartTime,currentQuarterEndTime, FollowConstant.FOLLOW_TYPE_HYP_JW,hospitalId);
                //年度规范随访人数
                Long standardFollowMemberCount = 0l;
                Map<String, String> queryParam = getQuarterParam(today);
                if (!queryParam.get("quarter").equals("0")){
                    queryParam.put("hospitalId",hospitalId);
                    standardFollowMemberCount = staticsMapper.getStandardFollowMemberCount(queryParam);
                }
                //年度规范管理人数
                Long standardManageMemberCount = staticsMapper.getStandardManageMemberCount(standardManageParam);
                //今日血压异常人数
                Long badBloodPressure = staticsMapper.loadDoBloodPressureMembersBadNum(hospitalId, today);
                //今日血糖异常人数
                Long badBloodSugar = staticsMapper.loadDoSugarMembersBadNum(hospitalId, today);
                //待分层分级人数
                Long gxyLevelNum = staticsMapper.loadUnConfirmLevelMemberNum(hospitalId, MemberLevelConstant.GXY_TYPE);
                //待分标人数
                Long tnbLevelNum = staticsMapper.loadUnConfirmLevelMemberNum(hospitalId, MemberLevelConstant.TNB_TYPE);

                Map<String, Object> map = new HashMap<>();
                map.put("hospitalName",hospitalPO.getHospitalName());
                map.put("joinMemberNum",joinMemberNum);
                map.put("sugarFollowNum",quarterSugarFollowNum);
                map.put("hypFollowNum",quarterHypFollowNum);
                map.put("standardFollow",standardFollowMemberCount);
                map.put("standardManage",standardManageMemberCount);
                map.put("badBloodPressure",badBloodPressure);
                map.put("badBloodSugar",badBloodSugar);
                map.put("gxyLevelNum",gxyLevelNum);
                map.put("tnbLevelNum",tnbLevelNum);
                result.add(map);
            }
        }

        return result;
    }


    private Map<String,Object> getStandardManageParam(){
        String year = DateHelper.getDate(new Date(),"yyyy");
        Map<String, Object> param = new HashMap<>();
        param.put("firstStartDt",year+ "-01-01 00:00:00");
        param.put("firstEndDt",year+ "-03-31 23:59:59");
        param.put("secondStartDt",year+ "-04-01 00:00:00");
        param.put("secondEndDt",year+ "-06-30 23:59:59");
        param.put("thirdStartDt",year+ "-07-01 00:00:00");
        param.put("thirdEndDt",year+ "-09-30 23:59:59");
        param.put("fourthStartDt",year+ "-10-01 00:00:00");
        param.put("fourthEndDt",year+ "-12-31 23:59:59");
        param.put("year",year);
        return param;
    }

    //居委会季度检查列表
    private List<CommitteeRankVO> getCommitteeRank(List<HospitalCommitteePO> hospitalCommitteeList,String hospitalId){
        List<CommitteeRankVO> result = new ArrayList<>();
        if (hospitalCommitteeList.isEmpty()){
            return result;
        }
        String year = DateHelper.getDate(new Date(),"yyyy");
        String currentQuarterStartTime = DateHelper.getCurrentQuarterStartTime();//本季度第一天
        String currentQuarterEndTime = DateHelper.getCurrentQuarterEndTime();//本季度最后一天
        for (HospitalCommitteePO committee : hospitalCommitteeList) {
            CommitteeRankVO rankVO = new CommitteeRankVO();
            String committeeName = committee.getCommitteeName();
            String committeeId = committee.getId();
            Long joinMemberNum = staticsMapper.loadInHospitalMembersNumByCommitteeId(committeeId, null,hospitalId);
            Long quarterSugarFollowNum = staticsMapper.loadFollowNumByCommitteeId(committeeId, currentQuarterStartTime,
                    currentQuarterEndTime, FollowConstant.FOLLOW_TYPE_TWO_DIABETES,hospitalId);
            Long quarterHypFollowNum = staticsMapper.loadFollowNumByCommitteeId(committeeId, currentQuarterStartTime,
                    currentQuarterEndTime, FollowConstant.FOLLOW_TYPE_HYP_JW,hospitalId);
            long checkMemberNum = staticsMapper.countMemberInspectionByCommitteeId(committeeId, year,hospitalId);
            //血糖测量人数
            Long quarterSugarNum = staticsMapper.loadDoSugarMembersNumByHospitalId(hospitalId,committeeId, currentQuarterStartTime, currentQuarterEndTime);
            //血压测量人数
            Long quarterHypNum = staticsMapper.loadDoBloodPressureMembersNumByHospitalId(hospitalId,committeeId, currentQuarterStartTime, currentQuarterEndTime);

            rankVO.setCommitteeName(committeeName);
            rankVO.setCheckMemberNum(String.valueOf(checkMemberNum));
            rankVO.setJoinMemberNum(String.valueOf(joinMemberNum == null?0:joinMemberNum));
            rankVO.setQuarterSugarFollowNum(String.valueOf(quarterSugarFollowNum == null?0:quarterSugarFollowNum));
            rankVO.setQuarterHypFollowNum(String.valueOf(quarterHypFollowNum == null?0:quarterHypFollowNum));
            rankVO.setSugarMemberNum(String.valueOf(quarterSugarNum == null?0:quarterSugarNum));
            rankVO.setPressureMemberNum(String.valueOf(quarterHypNum == null?0:quarterHypNum));
            result.add(rankVO);
        }
        return result;
    }

    @Override
    public Map<String, Object> loadFollowDataByHospitalId(String hospitalId) {
        String year = DateHelper.getDate(new Date(),"yyyy");
        Long oneFollow = staticsMapper.getYearFollowCountHospitalId(hospitalId, "1", year);
        Long twoFollow = staticsMapper.getYearFollowCountHospitalId(hospitalId, "2", year);
        Long threeFollow = staticsMapper.getYearFollowCountHospitalId(hospitalId, "3", year);
        Long fourFollow = staticsMapper.getYearFollowCountHospitalId(hospitalId, "4", year);
        Long fiveFollow = staticsMapper.getYearFollowCountHospitalId(hospitalId, "5", year);
        Map<String, Object> map = new HashMap<>();
        map.put("oneFollow",oneFollow);
        map.put("twoFollow",twoFollow);
        map.put("threeFollow",threeFollow);
        map.put("fourFollow",fourFollow);
        map.put("fiveFollow",fiveFollow);
        return map;
    }


    @Override
    public Map<String, Object> loadManagementDataByHospitalId(String hospitalId) {
        String today = DateHelper.getToday();
        String startDt = null;
        String endDt = today +DateHelper.DEFUALT_TIME_END;
        //建档人数
        List<String> hospitalIds = new ArrayList<>();
        hospitalIds.add(hospitalId);
        Long num = staticsMapper.loadInHospitalMembersNum(hospitalIds, startDt);

        //血压
        ListBloodPressureDTO listBloodPressureDTO = new ListBloodPressureDTO();
        listBloodPressureDTO.setStartDt(startDt);
        listBloodPressureDTO.setEndDt(endDt);
        listBloodPressureDTO.setHospitalId(hospitalId);
        List<BloodPressurePO> bloodPressurePOS = bloodPressureMapper.listBloodPressureByHospitalId(listBloodPressureDTO);
        //血糖
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        listBloodSugarDTO.setStartDt(startDt);
        listBloodSugarDTO.setEndDt(endDt);
        listBloodSugarDTO.setHospitalId(hospitalId);
        listBloodSugarDTO.setRecordType(1);
        List<BloodSugarPO> bloodSugarPOS = bloodSugarMapper.listBloodSugarByHospitalId(listBloodSugarDTO);

        //今日处方、今日随访
        List<String> hospitalIdList = new ArrayList<>();
        hospitalIdList.add(hospitalId);
        Map<String, Object> map = this.loadHospitalHealthManageData(hospitalIdList, today);

        //转入
        ListReferralApplyDTO listReferralApplyDTO = new ListReferralApplyDTO();
        listReferralApplyDTO.setStatus(1);
        listReferralApplyDTO.setHospitalId(hospitalId);
        List<ReferralApplyPO> inReferral = referralApplyMapper.listDistinctMemberReferralByWhere(listReferralApplyDTO);
        //转出
        listReferralApplyDTO.setHospitalId(null);
        listReferralApplyDTO.setApplyHospitalId(hospitalId);
        List<ReferralApplyPO> outReferral = referralApplyMapper.listDistinctMemberReferralByWhere(listReferralApplyDTO);

        //年度体检
        String year = DateHelper.getDate(new Date(), "yyyy");
        Set<String> inspectionMemberIds = memberMapper.getInspectionMemberIds(hospitalId, year);
        Integer checkNum = inspectionMemberIds.size();

        //规范随访人数
        Long standardFollowMemberCount = 0l;
        Map<String, String> queryParam = getQuarterParam(today);
        if (!queryParam.get("quarter").equals("0")){
            queryParam.put("hospitalId",hospitalId);
            standardFollowMemberCount = staticsMapper.getStandardFollowMemberCount(queryParam);
        }

        Map<String, Object> result = new HashMap<>();
        result.put(StatisticsNavigationItemEnum.TODAY_IN_HOSPITAL_NUM.getCode(),num == null?0:num); //建档人数
        result.put("sugarNum",bloodSugarPOS.size());//血糖
        result.put("pressureNum",bloodPressurePOS.size());//血压
        result.put(StatisticsNavigationItemEnum.YEAR_CHECK.getCode(),checkNum);//年度体检
        result.put("standardFollowNum",standardFollowMemberCount);//规范化随访人数
        result.put("inReferral",inReferral.size());//转入
        result.put("outReferral",outReferral.size());//转出
        result.put("presNum", map.get("presNum"));//今日处方数量
        result.put("followNum", map.get("followNum"));//今日随访数量

        return result;
    }

    private Map<String,String> getQuarterParam(String date){
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        int intMonth = Integer.parseInt(month);
        int quarter = intMonth == 12 ? 4 : intMonth/3;//只有12月有第四个季度
        Map<String, String> param = new HashMap<>();
        param.put("quarter","0");
        if (quarter == 1){
            param.put("quarter","1");
            param.put("firstStartDt",year+ "-01-01 00:00:00");
            param.put("firstEndDt",year+ "-03-31 23:59:59");
        }else if (quarter == 2){
            param.put("quarter","2");
            param.put("firstStartDt",year+ "-01-01 00:00:00");
            param.put("firstEndDt",year+ "-03-31 23:59:59");
            param.put("secondStartDt",year+ "-04-01 00:00:00");
            param.put("secondEndDt",year+ "-06-30 23:59:59");
        }else if (quarter == 3){
            param.put("quarter","3");
            param.put("firstStartDt",year+ "-01-01 00:00:00");
            param.put("firstEndDt",year+ "-03-31 23:59:59");
            param.put("secondStartDt",year+ "-04-01 00:00:00");
            param.put("secondEndDt",year+ "-06-30 23:59:59");
            param.put("thirdStartDt",year+ "-07-01 00:00:00");
            param.put("thirdEndDt",year+ "-09-30 23:59:59");
        }else if (quarter == 4){
            param.put("quarter","4");
            param.put("firstStartDt",year+ "-01-01 00:00:00");
            param.put("firstEndDt",year+ "-03-31 23:59:59");
            param.put("secondStartDt",year+ "-04-01 00:00:00");
            param.put("secondEndDt",year+ "-06-30 23:59:59");
            param.put("thirdStartDt",year+ "-07-01 00:00:00");
            param.put("thirdEndDt",year+ "-09-30 23:59:59");
            param.put("fourthStartDt",year+ "-10-01 00:00:00");
            param.put("fourthEndDt",year+ "-12-31 23:59:59");
        }
        return param;
    }

    @Override
    public Map<String, Object> loadMemberAgeDataByHospitalId(String hospitalId) {
        String today = DateHelper.getToday();
        String seventyDt = DateHelper.plusDate(today, 1, -71, DateHelper.DAY_FORMAT);
        String fiftyDt = DateHelper.plusDate(today, 1, -51, DateHelper.DAY_FORMAT);
        String thirtyDt = DateHelper.plusDate(today, 1, -31, DateHelper.DAY_FORMAT);

        GetMemberStaticsDTO getMemberStaticsDTO = new GetMemberStaticsDTO();
        getMemberStaticsDTO.setHospitalId(hospitalId);
        getMemberStaticsDTO.setSex("1");//男
        getMemberStaticsDTO.setEndDt(seventyDt);
        Long seventyNumMan = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//>=70岁
        getMemberStaticsDTO.setStartDt(seventyDt);
        getMemberStaticsDTO.setEndDt(fiftyDt);
        Long fiftyNumMan = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//51-70
        getMemberStaticsDTO.setStartDt(fiftyDt);
        getMemberStaticsDTO.setEndDt(thirtyDt);
        Long thirtyNumMan = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//31-50
        getMemberStaticsDTO.setStartDt(thirtyDt);
        getMemberStaticsDTO.setEndDt(null);
        Long nowNumMan = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//<=30
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("seventy",seventyNumMan);
        manMap.put("fifty",fiftyNumMan);
        manMap.put("thirty",thirtyNumMan);
        manMap.put("young",nowNumMan);
        //女
        getMemberStaticsDTO.setSex("2");
        getMemberStaticsDTO.setStartDt(null);
        getMemberStaticsDTO.setEndDt(seventyDt);
        Long seventyNumWoman = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//>=70岁
        getMemberStaticsDTO.setStartDt(seventyDt);
        getMemberStaticsDTO.setEndDt(fiftyDt);
        Long fiftyNumWoman = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//51-70
        getMemberStaticsDTO.setStartDt(fiftyDt);
        getMemberStaticsDTO.setEndDt(thirtyDt);
        Long thirtyNumWoman = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//31-50
        getMemberStaticsDTO.setStartDt(thirtyDt);
        getMemberStaticsDTO.setEndDt(null);
        Long nowNumWoman = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//<=30
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("seventy",seventyNumWoman);
        womanMap.put("fifty",fiftyNumWoman);
        womanMap.put("thirty",thirtyNumWoman);
        womanMap.put("young",nowNumWoman);

        Map<String, Object> result = new HashMap<>();
        result.put("man",manMap);
        result.put("woman",womanMap);

        return result;
    }

    @Override
    public Map<String, Object> loadMemberBmiDataByHospitalId(String hospitalId) {
        String slim = "18.5";
        String normal = "23.9";
        String fat = "27.9";
        GetMemberStaticsDTO getMemberStaticsDTO = new GetMemberStaticsDTO();
        getMemberStaticsDTO.setHospitalId(hospitalId);
        getMemberStaticsDTO.setSex("1");//男
        getMemberStaticsDTO.setHighBmi(slim);
        Long slimNumMan = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//<18.5消瘦
        getMemberStaticsDTO.setLowBmi(slim);
        getMemberStaticsDTO.setHighBmi(normal);
        Long normalNumMan = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//18.5-23.9正常
        getMemberStaticsDTO.setLowBmi(normal);
        getMemberStaticsDTO.setHighBmi(fat);
        Long overWeightNumMan = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//24-27.9超重
        getMemberStaticsDTO.setLowBmi(fat);
        getMemberStaticsDTO.setHighBmi(null);
        Long fatNumMan = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//>=28肥胖
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("slim",slimNumMan);
        manMap.put("normal",normalNumMan);
        manMap.put("overWeight",overWeightNumMan);
        manMap.put("fat",fatNumMan);

        getMemberStaticsDTO.setSex("2");//女
        getMemberStaticsDTO.setLowBmi(null);
        getMemberStaticsDTO.setHighBmi(slim);
        Long slimNumWoman = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//<18.5消瘦
        getMemberStaticsDTO.setLowBmi(slim);
        getMemberStaticsDTO.setHighBmi(normal);
        Long normalNumWoman = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//18.5-23.9正常
        getMemberStaticsDTO.setLowBmi(normal);
        getMemberStaticsDTO.setHighBmi(fat);
        Long overWeightNumWoman = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//24-27.9超重
        getMemberStaticsDTO.setLowBmi(fat);
        getMemberStaticsDTO.setHighBmi(null);
        Long fatNumWoman = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//>=28肥胖
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("slim",slimNumWoman);
        womanMap.put("normal",normalNumWoman);
        womanMap.put("overWeight",overWeightNumWoman);
        womanMap.put("fat",fatNumWoman);

        Map<String, Object> result = new HashMap<>();
        result.put("man",manMap);
        result.put("woman",womanMap);
        return result;
    }

    @Override
    public Map<String, Object> loadMemberDiseaseTypeDataByHospitalId(String hospitalId) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);//小数点后2位

        GetMemberStaticsDTO getMemberStaticsDTO = new GetMemberStaticsDTO();
        getMemberStaticsDTO.setHospitalId(hospitalId);
        getMemberStaticsDTO.setIsDiabetes("1");
        Long diabetesTotalNum = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//糖尿病总人数
        getMemberStaticsDTO.setDiabetesType("SUGAR_TYPE_001");
        Long diabetesOneNum = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//1型糖尿病人数
        getMemberStaticsDTO.setDiabetesType("SUGAR_TYPE_002");
        Long diabetesTwoNum = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//2型糖尿病人数
        getMemberStaticsDTO.setDiabetesType("SUGAR_TYPE_003");
        Long diabetesGestationNum = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//妊娠型糖尿病人数
        Long diabetesOtherNum = diabetesTotalNum - diabetesOneNum - diabetesTwoNum - diabetesGestationNum;//其他糖尿病人数
        Map<String, Object> diaMap = new HashMap<>();
        diaMap.put("one",diabetesOneNum);
        diaMap.put("oneRate",diabetesTotalNum == 0?"0%":numberFormat.format(((double)diabetesOneNum/diabetesTotalNum)*100) + "%" );
        diaMap.put("two",diabetesTwoNum);
        diaMap.put("twoRate",diabetesTotalNum == 0?"0%":numberFormat.format(((double)diabetesTwoNum/diabetesTotalNum)*100) + "%" );
        diaMap.put("gestation",diabetesGestationNum);
        diaMap.put("gestationRate",diabetesTotalNum == 0?"0%":numberFormat.format(((double)diabetesGestationNum/diabetesTotalNum)*100) + "%" );
        diaMap.put("other",diabetesOtherNum);
        diaMap.put("otherRate",diabetesTotalNum == 0?"0%":numberFormat.format(((double)diabetesOtherNum/diabetesTotalNum)*100) + "%" );

        getMemberStaticsDTO.setIsDiabetes(null);
        getMemberStaticsDTO.setDiabetesType(null);
        getMemberStaticsDTO.setEssentialHyp("1");
        Long hypTotalNum = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//高血压总人数
        getMemberStaticsDTO.setHypType("HYP_TYPE_001");
        Long hypEssentialNum = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//原发性高血压人数
        getMemberStaticsDTO.setHypType("HYP_TYPE_002");
        Long hypSecondaryNum = this.memberMapper.countMemberNumByDTO(getMemberStaticsDTO);//继发性高血压人数
        Long hypOtherNum = hypTotalNum - hypEssentialNum -hypSecondaryNum;
        Map<String, Object> hypMap = new HashMap<>();
        hypMap.put("essential",hypEssentialNum);
        hypMap.put("essentialRate",hypTotalNum == 0?"0%":numberFormat.format(((double)hypEssentialNum/hypTotalNum)*100) + "%" );
        hypMap.put("secondary",hypSecondaryNum);
        hypMap.put("secondaryRate",hypTotalNum == 0?"0%":numberFormat.format(((double)hypSecondaryNum/hypTotalNum)*100) + "%" );
        hypMap.put("other",hypOtherNum);
        hypMap.put("otherRate",hypTotalNum == 0?"0%":numberFormat.format(((double)hypOtherNum/hypTotalNum)*100) + "%" );

        Map<String, Object> result = new HashMap<>();
        result.put("diabetes",diaMap);
        result.put("hyp",hypMap);
        return result;
    }

    @Override
    public Map<String, Object> loadMemberRecordSugarPressureDta(String hospitalId) {
        String today = DateHelper.getToday();
        String lastMonthDate = DateHelper.plusDate(today, 2, -1, DateHelper.DAY_FORMAT);

        String nowMonthFirst = DateHelper.getMonthFirst(today);//当月第一天
        String nowmonthLast = DateHelper.getMonthLast(today);//当月最后一天
        String lastMonthFirst = DateHelper.getMonthFirst(lastMonthDate);//上月第一天
        String lastmonthLast = DateHelper.getMonthLast(lastMonthDate);//上月最后一天

        //血糖
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        listBloodSugarDTO.setHospitalId(hospitalId);
        listBloodSugarDTO.setRecordType(1);//仅计算检测类型
        listBloodSugarDTO.setStartDt(nowMonthFirst);
        listBloodSugarDTO.setEndDt(nowmonthLast);
        List<BloodSugarPO> nowMonthSugarList = bloodSugarMapper.listBloodSugarByHospitalId(listBloodSugarDTO);
        int nowMonthSugar = nowMonthSugarList.size();
        listBloodSugarDTO.setStartDt(lastMonthFirst);
        listBloodSugarDTO.setEndDt(lastmonthLast);
        List<BloodSugarPO> lastMonthSugarList = bloodSugarMapper.listBloodSugarByHospitalId(listBloodSugarDTO);
        int lastMonthSugar = lastMonthSugarList.size();

        //血压
        ListBloodPressureDTO listBloodPressureDTO = new ListBloodPressureDTO();
        listBloodPressureDTO.setHospitalId(hospitalId);
        listBloodPressureDTO.setStartDt(nowMonthFirst);
        listBloodPressureDTO.setEndDt(nowmonthLast);
        List<BloodPressurePO> nowPressureList = bloodPressureMapper.listBloodPressureByHospitalId(listBloodPressureDTO);
        int nowMonthPressure = nowPressureList.size();
        listBloodPressureDTO.setStartDt(lastMonthFirst);
        listBloodPressureDTO.setEndDt(lastmonthLast);
        List<BloodPressurePO> lastPressureList = bloodPressureMapper.listBloodPressureByHospitalId(listBloodPressureDTO);
        int lastMonthPressure = lastPressureList.size();

        Map<String, Object> result = new HashMap<>();
        result.put("nowMonthSugar",nowMonthSugar);
        result.put("lastMonthSugar",lastMonthSugar);
        result.put("nowMonthPressure",nowMonthPressure);
        result.put("lastMonthPressure",lastMonthPressure);

        return result;
    }

    @Override
    public PageResult<ListScreenStaticsVO> pageMemberJoinHospital(List<String> hospitalIdList, PageRequest pageRequest) {
        String today = DateHelper.getToday();
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<ListScreenStaticsVO> memberJoinHospitalList = staticsMapper.getMemberJoinHospitalList(hospitalIdList, today);
        if (!memberJoinHospitalList.isEmpty()){
            for (ListScreenStaticsVO listScreenStaticsVO : memberJoinHospitalList) {
                listScreenStaticsVO.setMemberName(StringUtils.protectedName(listScreenStaticsVO.getMemberName()));
                listScreenStaticsVO.setAge(String.valueOf(DateHelper.getAge(listScreenStaticsVO.getBirthday())));
            }
        }
        return new PageResult<>(memberJoinHospitalList);
    }

    @Override
    public PageResult<ListScreenStaticsVO> pageMemberBloodSugar(List<String> hospitalIdList, PageRequest pageRequest) {
        String today = DateHelper.getToday();
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<ListScreenStaticsVO> result = staticsMapper.getMemberBloodSugarList(hospitalIdList, today);
        if (!result.isEmpty()){
            for (ListScreenStaticsVO listScreenStaticsVO : result) {
                listScreenStaticsVO.setMemberName(StringUtils.protectedName(listScreenStaticsVO.getMemberName()));
                listScreenStaticsVO.setAge(String.valueOf(DateHelper.getAge(listScreenStaticsVO.getBirthday())));
            }
        }
        return new PageResult<>(result);
    }

    @Override
    public PageResult<ListScreenStaticsVO> pageMemberBloodPressure(List<String> hospitalIdList, PageRequest pageRequest) {
        String today = DateHelper.getToday();
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<ListScreenStaticsVO> result = staticsMapper.getMemberBloodPressureList(hospitalIdList, today);
        if (!result.isEmpty()){
            for (ListScreenStaticsVO listScreenStaticsVO : result) {
                listScreenStaticsVO.setMemberName(StringUtils.protectedName(listScreenStaticsVO.getMemberName()));
                listScreenStaticsVO.setAge(String.valueOf(DateHelper.getAge(listScreenStaticsVO.getBirthday())));
            }
        }
        return new PageResult<>(result);
    }

    @Override
    public PageResult<ListScreenStaticsVO> pageMemberDiabetesLevel(List<String> hospitalIdList, PageRequest pageRequest) {
        String today = DateHelper.getToday();
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<ListScreenStaticsVO> result = staticsMapper.getMemberDiabetesLevelList(hospitalIdList, today);
        if (!result.isEmpty()){
            for (ListScreenStaticsVO listScreenStaticsVO : result) {
                listScreenStaticsVO.setMemberName(StringUtils.protectedName(listScreenStaticsVO.getMemberName()));
                listScreenStaticsVO.setCurrentDiabetesLevel("暂无");
                listScreenStaticsVO.setSuggestDiabetesLevel("暂无");
                MemberCurrentLeverBO memberCurrentLevel = memberLevelService.getMemberCurrentLevel(listScreenStaticsVO.getMemberId());
                if (null != memberCurrentLevel){
                    listScreenStaticsVO.setCurrentDiabetesLevel(memberCurrentLevel.getSugarLevelDesc());
                    MemberLevelPO param = new MemberLevelPO();
                    param.setMemberId(listScreenStaticsVO.getMemberId());
                    param.setLevelType(MemberLevelConstant.TNB_TYPE);
                    MemberLevelPO suggestLevel = memberLevelMapper.getMemberSuggestLevel(param);
                    if (null != suggestLevel){
                        listScreenStaticsVO.setSuggestDiabetesLevel(DiabetesLevelAnalyseHelper.getLevel(suggestLevel.getMemberLevel()));
                    }
                }
            }
        }
        return new PageResult<>(result);
    }

    @Override
    public PageResult<ListScreenStaticsVO> pageMemberHypLevel(List<String> hospitalIdList, PageRequest pageRequest) {
        String today = DateHelper.getToday();
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<ListScreenStaticsVO> result = staticsMapper.getMemberHypLevelList(hospitalIdList, today);
        if (!result.isEmpty()){
            for (ListScreenStaticsVO listScreenStaticsVO : result) {
                listScreenStaticsVO.setMemberName(StringUtils.protectedName(listScreenStaticsVO.getMemberName()));
                listScreenStaticsVO.setCurrentHypLevel("暂无");
                listScreenStaticsVO.setCurrentHypLayer("暂无");
                listScreenStaticsVO.setSuggestHypLevel("暂无");
                listScreenStaticsVO.setSuggestHypLayer("暂无");
                MemberCurrentLeverBO memberCurrentLevel = memberLevelService.getMemberCurrentLevel(listScreenStaticsVO.getMemberId());
                if (null != memberCurrentLevel){
                    listScreenStaticsVO.setCurrentHypLevel(memberCurrentLevel.getPressureLevelDesc());
                    listScreenStaticsVO.setCurrentHypLayer(memberCurrentLevel.getPressureLayerDesc());
                    MemberLevelPO param = new MemberLevelPO();
                    param.setMemberId(listScreenStaticsVO.getMemberId());
                    param.setLevelType(MemberLevelConstant.GXY_TYPE);
                    MemberLevelPO suggestLevel = memberLevelMapper.getMemberSuggestLevel(param);
                    if (null != suggestLevel){
                        listScreenStaticsVO.setSuggestHypLevel(LevelAnalyzeHelper.getLevelNumber(suggestLevel.getMemberLevel()));
                        listScreenStaticsVO.setSuggestHypLayer(LevelAnalyzeHelper.getLayer(suggestLevel.getMemberLayer()));
                    }
                }
            }
        }
        return new PageResult<>(result);
    }

    @Override
    public Map<String, Object> getTodayVisitDetail(String hospitalId) {
        ListMemberVisitDTO listMemberVisitDTO = new ListMemberVisitDTO();
        listMemberVisitDTO.setHospitalId(hospitalId);
        listMemberVisitDTO.setJoinHospitalId(hospitalId);
        listMemberVisitDTO.setVisitStartDt(DateHelper.getToday()+DateHelper.DEFUALT_TIME_START);
        listMemberVisitDTO.setVisitEndDt(DateHelper.getToday()+DateHelper.DEFUALT_TIME_END);
        long visitNum = memberMapper.countMemberVisit(listMemberVisitDTO);
        long allNum = staticsMapper.countMemberJoinVisit(Arrays.asList(hospitalId),DateHelper.getToday());
        long outVisitNum = allNum - visitNum;
        Map<String, Object> map = new HashMap<>();
        map.put("visitNum",visitNum);
        map.put("outVisitNum",outVisitNum);
        return map;
    }

    @Override
    public Map<String, Object> detailFollowDataByHospitalId(String hospitalId) {
        String year = DateHelper.getDate(new Date(),"yyyy");
        HospitalCommitteePO getHospitalCommitteePO = new HospitalCommitteePO();
        getHospitalCommitteePO.setHospitalId(hospitalId);
        getHospitalCommitteePO.setValid(1);
        Map<String, Object> result = new HashMap<>(2);
        //居委会列表
        List<HospitalCommitteePO> hospitalCommitteeList = committeeService.listHospitalCommittee(getHospitalCommitteePO);
        hospitalCommitteeList.add(new HospitalCommitteePO("流动人口",hospitalId));
        List<Map> sugarList = new ArrayList<>(hospitalCommitteeList.size());
        List<Map> hypList = new ArrayList<>(hospitalCommitteeList.size());
        if (!hospitalCommitteeList.isEmpty()){
            for (HospitalCommitteePO hospitalCommitteePO : hospitalCommitteeList) {
                //血糖
                Long oneSugarNUM = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), "1", year,"7",hospitalId);
                Long twoSugarNUM = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), "2", year,"7",hospitalId);
                Long threeSugarNUM = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), "3", year,"7",hospitalId);
                Long fourSugarNUM = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), "4", year,"7",hospitalId);
                Long fiveSugarNUM = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), "5", year,"7",hospitalId);
                Long totalMemberSugarNum = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), null, year, "7",hospitalId);
                Long totalSugarNum = staticsMapper.getYearFollowTotalCountCommitedId(hospitalCommitteePO.getId(), year, "7");
                Map<String, Object> sugar = new HashMap<>();
                sugar.put("committeeName",hospitalCommitteePO.getCommitteeName());
                sugar.put("oneSugarNum",oneSugarNUM);
                sugar.put("twoSugarNum",twoSugarNUM);
                sugar.put("threeSugarNum",threeSugarNUM);
                sugar.put("fourSugarNum",fourSugarNUM);
                sugar.put("fiveSugarNum",fiveSugarNUM);
                sugar.put("totalMemberSugarNum",totalMemberSugarNum);
                sugar.put("totalSugarNum",totalSugarNum);
                sugarList.add(sugar);
                //血压
                Long oneHypNUM = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), "1", year,"11",hospitalId);
                Long twoHypNUM = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), "2", year,"11",hospitalId);
                Long threeHypNUM = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), "3", year,"11",hospitalId);
                Long fourHypNUM = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), "4", year,"11",hospitalId);
                Long fiveHypNUM = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), "5", year,"11",hospitalId);
                Long totalMemberHypNum = staticsMapper.getYearFollowCountCommitedId(hospitalCommitteePO.getId(), null, year, "11",hospitalId);
                Long totalHypNum = staticsMapper.getYearFollowTotalCountCommitedId(hospitalCommitteePO.getId(), year, "11");
                Map<String, Object> hyp = new HashMap<>();
                hyp.put("committeeName",hospitalCommitteePO.getCommitteeName());
                hyp.put("oneHypNum",oneHypNUM);
                hyp.put("twoHypNum",twoHypNUM);
                hyp.put("threeHypNum",threeHypNUM);
                hyp.put("fourHypNum",fourHypNUM);
                hyp.put("fiveHypNum",fiveHypNUM);
                hyp.put("totalMemberHypNum",totalMemberHypNum);
                hyp.put("totalHypNum",totalHypNum);
                hypList.add(hyp);
            }
        }
        result.put("sugar",sugarList);
        result.put("hyp",hypList);
        return result;
    }

    @Override
    public String getMemberLevelReasonById(String sid) {
        MemberLevelPO level = this.memberLevelMapper.getMemberLevelById(sid);
        if (level == null){
        throw new BusinessException("该分层分级不存在");

        }
        return level.getChangeReason();
    }

    /**
     * 工作台糖尿病统计数据
     * @param hospitalId
     * @return
     */
    private Map<String,Object> tnbHospitalDiseaseDataHandler(ListMemberLevelDTO listMemberLevelDTO){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);//小数点后1位
        Long tnbTotalNum = 0l;//糖尿病总人数
        Long tnbRedNum = 0l;//糖尿病红标人数
        String tnbRedRate = "";//糖尿病红标人数
        Long tnbYellowNum = 0l;//糖尿病黄标人数
        String tnbYellowRate = "";//糖尿病黄标人数
        Long tnbGreenNum = 0l;//糖尿病绿标人数
        String tnbGreenRate = "";//糖尿病绿标人数
        tnbTotalNum = memberLevelMapper.listMemberLevelNum(listMemberLevelDTO);
        listMemberLevelDTO.setMemberLevel(MemberLevelConstant.DIABETES_LEVEL_RED);
        tnbRedNum = memberLevelMapper.listMemberLevelNum(listMemberLevelDTO);
        listMemberLevelDTO.setMemberLevel(MemberLevelConstant.DIABETES_LEVEL_YELLOW);
        tnbYellowNum = memberLevelMapper.listMemberLevelNum(listMemberLevelDTO);
        listMemberLevelDTO.setMemberLevel(MemberLevelConstant.DIABETES_LEVEL_GREEN);
        tnbGreenNum = memberLevelMapper.listMemberLevelNum(listMemberLevelDTO);
        listMemberLevelDTO.setMemberLevel(null);

        tnbRedRate =  tnbTotalNum == 0?"0%":numberFormat.format(((double)tnbRedNum/tnbTotalNum)*100) + "%" ;
        tnbYellowRate =  tnbTotalNum == 0?"0%":numberFormat.format(((double)tnbYellowNum/tnbTotalNum)*100) + "%" ;
        tnbGreenRate =  tnbTotalNum == 0?"0%":numberFormat.format(((double)tnbGreenNum/tnbTotalNum)*100) + "%" ;
        Map<String, Object> result = new HashMap<>();
        result.put("tnbTotalNum",tnbTotalNum);
        result.put("tnbRedNum",tnbRedNum);
        result.put("tnbRedRate",tnbRedRate);
        result.put("tnbYellowNum",tnbYellowNum);
        result.put("tnbYellowRate",tnbYellowRate);
        result.put("tnbGreenNum",tnbGreenNum);
        result.put("tnbGreenRate",tnbGreenRate);
        return result;
    }


    /**
     * 工作台高血压统计数据
     * @param hospitalId
     * @return
     */
    private Map<String,Object> gxyHospitalDiseaseDataHandler(ListMemberLevelDTO listMemberLevelDTO){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);//小数点后1位
        Long gxyTotalNum = 0l;//高血压总人数
        Long gxyThreeNum = 0l;//高血压三级人数
        String gxyThreeRate = "";//高血压三级占比
        Long gxyTwoNum = 0l;//高血压二级人数
        String gxyTwoRate = "";//高血压二级占比
        Long gxyOneNum = 0l;//高血压一级人数
        String gxyOneRate = "";//高血压一级占比
        Long gxyOtherNum = 0l;//高血压其他人数
        String gxyOtherRate = "";//高血压其他 占比

        gxyTotalNum = memberLevelMapper.listMemberLevelNum(listMemberLevelDTO);
        listMemberLevelDTO.setMemberLevel(MemberLevelConstant.GXY_LEVEL_THREE);
        listMemberLevelDTO.setMemberLayer(MemberLevelConstant.GXY_LAYER_HIGH);
        gxyThreeNum = memberLevelMapper.listMemberLevelNum(listMemberLevelDTO);

        listMemberLevelDTO.setMemberLevel(MemberLevelConstant.GXY_LEVEL_TWO);
        listMemberLevelDTO.setMemberLayer(MemberLevelConstant.GXY_LAYER_MID);
        gxyTwoNum = memberLevelMapper.listMemberLevelNum(listMemberLevelDTO);

        listMemberLevelDTO.setMemberLevel(MemberLevelConstant.GXY_LEVEL_ONE);
        listMemberLevelDTO.setMemberLayer(MemberLevelConstant.GXY_LAYER_LOW);
        gxyOneNum = memberLevelMapper.listMemberLevelNum(listMemberLevelDTO);

        gxyOtherNum = gxyTotalNum - gxyThreeNum - gxyTwoNum - gxyOneNum;
        gxyThreeRate =  gxyTotalNum == 0?"0%":numberFormat.format(((double) gxyThreeNum/gxyTotalNum)*100) + "%" ;
        gxyTwoRate =  gxyTotalNum == 0?"0%":numberFormat.format(((double)gxyTwoNum/gxyTotalNum)*100) + "%" ;
        gxyOneRate =  gxyTotalNum == 0?"0%":numberFormat.format(((double)gxyOneNum/gxyTotalNum)*100) + "%" ;
        gxyOtherRate =  gxyTotalNum == 0?"0%":numberFormat.format(((double)gxyOtherNum/gxyTotalNum)*100) + "%" ;
        Map<String, Object> result = new HashMap<>();
        result.put("gxyTotalNum",gxyTotalNum);
        result.put("gxyThreeNum",gxyThreeNum);
        result.put("gxyThreeRate",gxyThreeRate);
        result.put("gxyTwoNum",gxyTwoNum);
        result.put("gxyTwoRate",gxyTwoRate);
        result.put("gxyOneNum",gxyOneNum);
        result.put("gxyOneRate",gxyOneRate);
        result.put("gxyOtherNum",gxyOtherNum);
        result.put("gxyOtherRate",gxyOtherRate);
        return result;
    }


    //糖化达标
    private Map<String,Object> getHbacStatics(List<String> hospitalIdList,String startDt,String endDt){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);//小数点后1位
        int hba1cPassNum = 0;
        String hba1cPassRate = "";
        ListHbalcDTO listHbalcDTO = new ListHbalcDTO();
        listHbalcDTO.setHospitalIdList(hospitalIdList);
        listHbalcDTO.setStartDt(startDt);
        listHbalcDTO.setEndDt(endDt);
        List<String> hba1cTotalList = this.hba1cMapper.listHba1cMemberIdsByDto(listHbalcDTO);
        int totalHba1cNum = hba1cTotalList.size();
        listHbalcDTO.setRecordLevel(3);
        List<String> passHba1cPass = this.hba1cMapper.listHba1cMemberIdsByDto(listHbalcDTO);
        hba1cPassNum = passHba1cPass.size();
        hba1cPassRate =totalHba1cNum == 0?"0%":numberFormat.format(((double)hba1cPassNum/totalHba1cNum)*100) + "%" ;
        Map<String, Object> result = new HashMap<>();
        result.put("hba1cPassNum",hba1cPassNum);
        result.put("hba1cNoPassNum",totalHba1cNum-hba1cPassNum);
        result.put("hba1cTotalNum",totalHba1cNum);
        result.put("hba1cPassRate",hba1cPassRate);
        return result;
    }

    //血脂达标
    private Map<String,Object> getLdlStatics(List<String> hospitalIdList,String startDt,String endDt){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);//小数点后1位
        int ldlPassNum = 0;
        String ldlPassRate = "";
        Map<String, Object> param = new HashMap<>();
        param.put("hospitalIdList",hospitalIdList);
        param.put("checkoutCode","add_check_ldl");
        param.put("startDt",startDt);
        param.put("endDt",endDt);
        List<String> totalLdlList = checkoutDetailMapper.loadCheckoutDetailMemberIdList(param);
        int totalLdlNum= totalLdlList.size();
        param.put("isPass","false");
        List<String> ldlPassList= checkoutDetailMapper.loadCheckoutDetailMemberIdList(param);
        ldlPassNum= ldlPassList.size();
        ldlPassRate = totalLdlNum == 0?"0%":numberFormat.format(((double)ldlPassNum/totalLdlNum)*100) + "%" ;
        Map<String, Object> result = new HashMap<>();
        result.put("ldlPassNum",ldlPassNum);
        result.put("ldlNoPassNum",totalLdlNum-ldlPassNum);
        result.put("ldlTotalNum",totalLdlNum);
        result.put("ldlPassRate",ldlPassRate);
        return result;
    }

    //血压达标
    private Map<String,Object> getBloodPressureStatics(List<String> hospitalIdList,String startDt,String endDt){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);//小数点后1位
        //血压
        int pressurePassNum = 0;
        String pressurePassRate = "";
        ListBloodPressureDTO listBloodPressureDTO = new ListBloodPressureDTO();
        listBloodPressureDTO.setHospitalIdList(hospitalIdList);
        listBloodPressureDTO.setStartDt(startDt);
        listBloodPressureDTO.setEndDt(endDt);
        List<String> pressureTotalList = bloodPressureMapper.listBloodPressureMemberList(listBloodPressureDTO);
        int pressureTotalNum = pressureTotalList.size();
        listBloodPressureDTO.setRecordLevel(1);
        List<String> pressurePassList = bloodPressureMapper.listBloodPressureMemberList(listBloodPressureDTO);
        pressurePassNum = pressurePassList.size();
        pressurePassRate = pressureTotalNum == 0?"0%":numberFormat.format(((double)pressurePassNum/pressureTotalNum)*100) + "%" ;
        Map<String, Object> result = new HashMap<>();
        result.put("pressurePassNum",pressurePassNum);
        result.put("pressureNoPassNum",pressureTotalNum-pressurePassNum);
        result.put("pressureTotalNum",pressureTotalNum);
        result.put("pressurePassRate",pressurePassRate);
        return result;
    }

    //血糖达标（旧）
    private Map<String,Object> getBloodSugarStatics(List<String> hospitalIdList,String startDt,String endDt){
        String today = DateHelper.getToday();
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);//小数点后1位
        //血糖(工作台)
        int sugarPassNum = 0;
        String sugarPassRate = "";
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        listBloodSugarDTO.setHospitalIdList(hospitalIdList);
        listBloodSugarDTO.setRecordType(1);//仅计算检测类型
        listBloodSugarDTO.setStartDt(startDt);
        listBloodSugarDTO.setEndDt(endDt);
        List<String> sugarMemberIdList = bloodSugarMapper.listBloodSugarMemberIdList(listBloodSugarDTO);
        int totalSugarNum = sugarMemberIdList.size();
        List<String> passSugarList = new ArrayList<>();
        //血糖达标：近90天内，所有血糖记录，正常次数大于75%
        for (String sugarMemberId : sugarMemberIdList) {
            listBloodSugarDTO.setMemberId(sugarMemberId);
            //90天内血糖正常
            if (StringUtils.isBlank(startDt)){
                listBloodSugarDTO.setStartDt(DateHelper.plusDate(today,-90) + DateHelper.DEFUALT_TIME_START);
            }
            if (StringUtils.isBlank(endDt)){
                listBloodSugarDTO.setEndDt(today+DateHelper.DEFUALT_TIME_END);
            }
            //所有的测量次数
            List<BloodSugarPO> memberAllSugar = bloodSugarMapper.listBloodSugar(listBloodSugarDTO);
            int allSize = memberAllSugar.size();
            listBloodSugarDTO.setParamLevel("3");
            //正常的测量次数
            List<BloodSugarPO> memberPassSugar = bloodSugarMapper.listBloodSugar(listBloodSugarDTO);
            int passSize = memberPassSugar.size();
            double sugPassRate = 0d;
            if (allSize>0){
                sugPassRate = (double)passSize / (double) allSize;
            }
            if (sugPassRate > 0.75d){
                passSugarList.add(sugarMemberId);
            }
        }
        sugarPassNum = passSugarList.size();
        sugarPassRate = totalSugarNum == 0?"0%":numberFormat.format(((double)sugarPassNum/totalSugarNum)*100) + "%" ;
        Map<String, Object> result = new HashMap<>();
        result.put("sugarPassNum",sugarPassNum);
        result.put("sugarTotalNum",totalSugarNum);
        result.put("sugarPassRate",sugarPassRate);
        return result;

    }


    //血糖达标（本年度近期统计最近一次空腹血糖＞7为异常）
    private Map<String,Object> getBeforeBreakFastSugarStatics(List<String> hospitalIdList){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);//小数点后1位
        Long allYearSugar = bloodSugarMapper.getLastSugarYear(hospitalIdList, DateHelper.getDate(new Date(), "yyyy"), SignConstant.PARAM_CODE_BEFORE_BREAKFAST,null);
        Long passYearSugar = bloodSugarMapper.getLastSugarYear(hospitalIdList, DateHelper.getDate(new Date(), "yyyy"), SignConstant.PARAM_CODE_BEFORE_BREAKFAST,7);
        String yearSugarPassRate = allYearSugar == 0?"0%":numberFormat.format(((double)passYearSugar/allYearSugar)*100) + "%" ;
        Map<String, Object> result = new HashMap<>();
        result.put("passYearSugar",passYearSugar);
        result.put("noPassYearSugar",allYearSugar-passYearSugar);
        result.put("totalYearSugar",allYearSugar);
        result.put("yearSugarPassRate",yearSugarPassRate);
        return result;
    }

    /**
     * 年龄分布
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRangeOfMemberAge(GetStatisticsDTO dto, Long memberCount) {
        //2.年龄分布
        GetStatisticsDTO param = new GetStatisticsDTO();
        BeanUtils.copyProperties(param, dto);
        param.setSex(null);
        Map<String, Object> rangeOfMemberAge = new HashMap<>(8);
        Double rangeLess30Rate = 0d;
        Double range30To50Rate = 0d;
        Double range51To70Rate = 0d;
        Double rangeGreater70Rate = 0d;
        Double rangeLess30Count = 0d;
        Double range30To50Count = 0d;
        Double range51To70Count = 0d;
        Double rangeGreater70Count = 0d;
        if (memberCount > 0) {
            List<Map<String, Object>> rangs = this.memberService.getMemberAgeRang(param);
            if (rangs != null) {
                for (Iterator<Map<String, Object>> iterator = rangs.iterator(); iterator.hasNext(); ) {
                    Map<String, Object> map = (Map<String, Object>) iterator.next();
                    if (map.get("age_rang") != null) {
                        switch (map.get("age_rang").toString()) {
                            case "0-30":
                                rangeLess30Count = Double.valueOf(map.get("num") == null ? "0" : map.get("num").toString());
                                break;
                            case "30-50":
                                range30To50Count = Double.valueOf(map.get("num") == null ? "0" : map.get("num").toString());
                                break;
                            case "51-70":
                                range51To70Count = Double.valueOf(map.get("num") == null ? "0" : map.get("num").toString());
                                break;
                            case "70":
                                rangeGreater70Count = Double.valueOf(map.get("num") == null ? "0" : map.get("num").toString());
                                break;
                            default:
                                break;
                        }
                    }
                }
                //把没有年龄的患者归类到0-30
                rangeLess30Count += memberCount - (rangeLess30Count + range30To50Count + range51To70Count + rangeGreater70Count);

                rangeLess30Rate = Double.parseDouble(df.format(rangeLess30Count / memberCount * 100));
                range30To50Rate = Double.parseDouble(df.format(range30To50Count / memberCount * 100));
                range51To70Rate = Double.parseDouble(df.format(range51To70Count / memberCount * 100));
                rangeGreater70Rate = Double.parseDouble(df.format(100 - rangeLess30Rate - range30To50Rate - range51To70Rate));
            }
        }

        rangeOfMemberAge.put("rangeLess30Rate", rangeLess30Rate);
        rangeOfMemberAge.put("range30To50Rate", range30To50Rate);
        rangeOfMemberAge.put("range51To70Rate", range51To70Rate);
        rangeOfMemberAge.put("rangeGreater70Rate", rangeGreater70Rate);
        rangeOfMemberAge.put("rangeLess30Count", rangeLess30Count);
        rangeOfMemberAge.put("range30To50Count", range30To50Count);
        rangeOfMemberAge.put("range51To70Count", range51To70Count);
        rangeOfMemberAge.put("rangeGreater70Count", rangeGreater70Count);
        return rangeOfMemberAge;
    }

    /**
     * 用药情况分析
     *
     * @param dto
     * @return
     */
    private Map<String, Object> drugStatic(GetStatisticsDTO dto) {
        Map<String, Object> drugFunPatientCount = this.drugStatisticsServer.getPatientStatisticsForDrugClass(dto);
        // 患者用药情况统计
        if (drugFunPatientCount != null && drugFunPatientCount.size() > 0) {
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            Map<String, Object> oneMap = new LinkedHashMap<String, Object>();
            oneMap.put("t0", Double.parseDouble(drugFunPatientCount.get("r1").toString()) * 100);
            oneMap.put("t1", Double.parseDouble(drugFunPatientCount.get("r11").toString()) * 100);
            oneMap.put("t2", Long.parseLong(drugFunPatientCount.get("c1").toString()));
            Map<String, Object> twoMap = new LinkedHashMap<String, Object>();
            twoMap.put("t0", Double.parseDouble(drugFunPatientCount.get("r2").toString()) * 100);
            twoMap.put("t1", Double.parseDouble(drugFunPatientCount.get("r22").toString()) * 100);
            twoMap.put("t2", Long.parseLong(drugFunPatientCount.get("c2").toString()));
            Map<String, Object> thirdMap = new LinkedHashMap<String, Object>();
            thirdMap.put("t0", Double.parseDouble(drugFunPatientCount.get("r3").toString()) * 100);
            thirdMap.put("t1", Double.parseDouble(drugFunPatientCount.get("r33").toString()) * 100);
            thirdMap.put("t2", Long.parseLong(drugFunPatientCount.get("c3").toString()));
            Map<String, Object> fourMap = new LinkedHashMap<String, Object>();
            fourMap.put("t0", Double.parseDouble(drugFunPatientCount.get("r4").toString()) * 100);
            fourMap.put("t1", Double.parseDouble(drugFunPatientCount.get("r44").toString()) * 100);
            fourMap.put("t2", Long.parseLong(drugFunPatientCount.get("c4").toString()));
            Map<String, Object> otherMap = new LinkedHashMap<String, Object>();
            otherMap.put("t0", Double.parseDouble(drugFunPatientCount.get("r5").toString()) * 100);
            otherMap.put("t1", Double.parseDouble(drugFunPatientCount.get("r55").toString()) * 100);
            otherMap.put("t2", Long.parseLong(drugFunPatientCount.get("c5").toString()));
            map.put("onecombinations_rate", oneMap);
            map.put("twocombinations_rate", twoMap);
            map.put("thirdcombinations_rate", thirdMap);
            map.put("fourcombinations_rate", fourMap);
            map.put("drug_other_rate", otherMap);
            return map;
        }
        return null;
    }

    /**
     * 胰岛素使用情况分析
     *
     * @param dto
     * @return
     */
    public List<Map<String, Object>> drugInsulinStatic(GetStatisticsDTO dto) {
        Map<String, Object> drugFunPatientCount = this.drugStatisticsServer.getPatientStatisticsForDrugClass(dto);
        //糖尿病患者用药情况分析
        if (drugFunPatientCount != null && drugFunPatientCount.size() > 0) {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            String drugWay1 = "胰岛素1次注射";                    //胰岛素1次注射
            String drugWay2 = "胰岛素2次注射";                    //胰岛素2次注射
            String drugWay3 = "胰岛素多次注射（+ 1 种口服药）";    //胰岛素多次注射（+ 1 种口服液）
            String drugWay4 = "胰岛素多次注射（+ 2 种口服药）";    //胰岛素多次注射（+ 2 种口服液）
            String drugWay5 = "胰岛素多次注射（+ 3 种口服药）";    //胰岛素多次注射（+ 3 种口服液）
            String drugWay6 = "其他";                            //其他
            Map<String, Object> m1 = new LinkedHashMap<String, Object>();
            m1.put("drugWay", drugWay1);
            m1.put("pesent", Double.parseDouble(drugFunPatientCount.get("pr1").toString()) * 100);
            m1.put("patientNum", Long.parseLong(drugFunPatientCount.get("p1").toString()));
            list.add(m1);
            Map<String, Object> m2 = new LinkedHashMap<String, Object>();
            m2.put("drugWay", drugWay2);
            m2.put("pesent", Double.parseDouble(drugFunPatientCount.get("pr2").toString()) * 100);
            m2.put("patientNum", Long.parseLong(drugFunPatientCount.get("p2").toString()));
            list.add(m2);
            Map<String, Object> m3 = new LinkedHashMap<String, Object>();
            m3.put("drugWay", drugWay3);
            m3.put("pesent", Double.parseDouble(drugFunPatientCount.get("pr3").toString()) * 100);
            m3.put("patientNum", Long.parseLong(drugFunPatientCount.get("p3").toString()));
            list.add(m3);
            Map<String, Object> m4 = new LinkedHashMap<String, Object>();
            m4.put("drugWay", drugWay4);
            m4.put("pesent", Double.parseDouble(drugFunPatientCount.get("pr4").toString()) * 100);
            m4.put("patientNum", Long.parseLong(drugFunPatientCount.get("p4").toString()));
            list.add(m4);
            Map<String, Object> m5 = new LinkedHashMap<String, Object>();
            m5.put("drugWay", drugWay5);
            m5.put("pesent", Double.parseDouble(drugFunPatientCount.get("pr5").toString()) * 100);
            m5.put("patientNum", Long.parseLong(drugFunPatientCount.get("p5").toString()));
            list.add(m5);
            Map<String, Object> m6 = new LinkedHashMap<String, Object>();
            m6.put("drugWay", drugWay6);
            m6.put("pesent", Double.parseDouble(drugFunPatientCount.get("pr6").toString()) * 100);
            m6.put("patientNum", Long.parseLong(drugFunPatientCount.get("p6").toString()));
            list.add(m6);
            return list;
        }
        return null;
    }

    /**
     * 当前并发症筛查情况
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRangeOfIntelligent(GetStatisticsDTO dto) {
        Map<String, Object> rangeOfIntelligent = new HashMap<String, Object>(11);
        Long count = 0L;
        Long falseCount = 0L;
        Long abiCount = 0L;
        Long vptCount = 0L;
        Long eyeCount = 0L;
        Long acrCount = 0L;
        Long hba1cCount = 0L;
        Double normalCount = 0d;
        Double normalRate = 0d;
        Double abiFalseCount = 0d;
        Double abiFalseRate = 0d;
        Double vptFalseCount = 0d;
        Double vptFalseRate = 0d;
        Double eyeFalseCount = 0d;
        Double eyeFalseRate = 0d;
        Double acrFalseCount = 0d;
        Double acrFalseRate = 0d;
        Double hba1cFalseCount = 0d;
        Double hba1cFalseRate = 0d;
        Double intelligentRate = 0d;
        Long memberCount = this.screeningService.getScreeningOfMemberCount(dto);
        if (memberCount != null && memberCount > 0) {
            count = memberCount;
            dto.setModular("1");
            Long abiMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
            dto.setModular("2");
            Long vptMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
            dto.setModular("3");
            Long eyeMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
            dto.setModular("4");
            Long acrMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
            dto.setModular("5");
            Long hba1cMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
            dto.setModular(null);

            dto.setAbnormalSign(2);
            Long falseMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
            dto.setModular("1");
            Long falseAbiMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
            dto.setModular("2");
            Long falseVptMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
            dto.setModular("3");
            Long falseEyeMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
            dto.setModular("4");
            Long falseAcrMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
            dto.setModular("5");
            Long falseHba1cMemberCount = this.screeningService.getScreeningOfMemberCount(dto);

            //发生率
            intelligentRate = Double.parseDouble(df.format(falseMemberCount / Double.parseDouble(memberCount + "") * 100));
            //abi异常人数比例
            if (abiMemberCount != null && abiMemberCount > 0) {
                abiFalseRate = Double.parseDouble(df.format(falseAbiMemberCount / Double.parseDouble(abiMemberCount + "") * 100));
            }
            //vpt异常人数比例
            if (vptMemberCount != null && vptMemberCount > 0) {
                vptFalseRate = Double.parseDouble(df.format(falseVptMemberCount / Double.parseDouble(vptMemberCount + "") * 100));
            }
            //eye异常人数比例
            if (eyeMemberCount != null && eyeMemberCount > 0) {
                eyeFalseRate = Double.parseDouble(df.format(falseEyeMemberCount / Double.parseDouble(eyeMemberCount + "") * 100));
            }
            //acr异常人数比例
            if (acrMemberCount != null && acrMemberCount > 0) {
                acrFalseRate = Double.parseDouble(df.format(falseAcrMemberCount / Double.parseDouble(acrMemberCount + "") * 100));
            }
            //hba1c异常人数比例
            if (hba1cMemberCount != null && hba1cMemberCount > 0) {
                hba1cFalseRate = Double.parseDouble(df.format(falseHba1cMemberCount / Double.parseDouble(hba1cMemberCount + "") * 100));
            }

            normalCount = Double.parseDouble(df.format(Double.parseDouble((memberCount - falseMemberCount) + "")));
            normalRate = 100 - abiFalseRate - vptFalseRate - eyeFalseRate - acrFalseRate - hba1cFalseRate;
            abiCount = abiMemberCount;
            vptCount = vptMemberCount;
            eyeCount = eyeMemberCount;
            acrCount = acrMemberCount;
            hba1cCount = hba1cMemberCount;
            falseCount = falseMemberCount;
        }
        rangeOfIntelligent.put("count", count);
        rangeOfIntelligent.put("falseCount", falseCount);
        rangeOfIntelligent.put("normalCount", normalCount);
        rangeOfIntelligent.put("normalRate", normalRate);
        rangeOfIntelligent.put("abiFalseCount", abiFalseCount);
        rangeOfIntelligent.put("abiFalseRate", abiFalseRate);
        rangeOfIntelligent.put("vptFalseCount", vptFalseCount);
        rangeOfIntelligent.put("vptFalseRate", vptFalseRate);
        rangeOfIntelligent.put("eyeFalseCount", eyeFalseCount);
        rangeOfIntelligent.put("eyeFalseRate", eyeFalseRate);
        rangeOfIntelligent.put("acrFalseCount", acrFalseCount);
        rangeOfIntelligent.put("acrFalseRate", acrFalseRate);
        rangeOfIntelligent.put("hba1cFalseCount", hba1cFalseCount);
        rangeOfIntelligent.put("hba1cFalseRate", hba1cFalseRate);
        rangeOfIntelligent.put("abiCount", abiCount);
        rangeOfIntelligent.put("vptCount", vptCount);
        rangeOfIntelligent.put("eyeCount", eyeCount);
        rangeOfIntelligent.put("acrCount", acrCount);
        rangeOfIntelligent.put("hba1cCount", hba1cCount);
        //并发症发生率
        rangeOfIntelligent.put("intelligentRate", intelligentRate);
        return rangeOfIntelligent;
    }

    /**
     * 患者并发症筛查比例
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRateOfIntelligent(GetStatisticsDTO dto, long memberCount) {
        Map<String, Object> rateOfIntelligent = new HashMap<>(5);
        Long unIntelligentOfMemberCount = 0l;
        Double intelligentOfMemberRate = 0d;
        Double unIntelligentOfMemberRate = 0d;
        Long intelligentOfMemberCount = 0l;
        intelligentOfMemberCount = this.screeningService.getScreeningOfMemberCount(dto);
        unIntelligentOfMemberCount = memberCount - intelligentOfMemberCount;
        intelligentOfMemberRate = Double.parseDouble(df.format(Double.parseDouble(intelligentOfMemberCount + "") / memberCount * 100));
        unIntelligentOfMemberRate = Double.parseDouble(df.format(100 - intelligentOfMemberRate));
        rateOfIntelligent.put("memberCount", memberCount);
        rateOfIntelligent.put("intelligentOfMemberCount", intelligentOfMemberCount);
        rateOfIntelligent.put("unIntelligentOfMemberCount", unIntelligentOfMemberCount);
        rateOfIntelligent.put("intelligentOfMemberRate", intelligentOfMemberRate);
        rateOfIntelligent.put("unIntelligentOfMemberRate", unIntelligentOfMemberRate);
        return rateOfIntelligent;
    }

    /**
     * 血糖分析
     *
     * @param dto
     * @return
     */
    private BloodSugarOfStatisticVO getRateOfBloodSugar(GetStatisticsDTO dto) {
        BloodSugarOfStatisticVO model = new BloodSugarOfStatisticVO();
        Double[] listLevelOfLow = {0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d};
        Double[] listLevelOfHeight = {0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d};
        Double[] listLevelOfNormal = {0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d};
        List<BloodSugarOfParamCodeBO> memberParamLogModels = this.bloodSugarService.listMemberParamValuesOfStatistics(dto);
        Integer count1 = 0;
        Integer count2 = 0;
        Integer count3 = 0;
        Integer count4 = 0;
        Integer count5 = 0;
        Integer count6 = 0;
        Integer count7 = 0;
        Integer count8 = 0;
        Integer normalCount = 0;
        Integer heightCount = 0;
        Integer lowCount = 0;
        Integer testCount = 0;
        Integer kfHeightCount = 0;
        Integer kfCount = 0;
        Integer fkfHeightCount = 0;
        Integer fkfCount = 0;
        if (memberParamLogModels != null && memberParamLogModels.size() > 0) {
            for (BloodSugarOfParamCodeBO paramLog : memberParamLogModels) {
                String paramCode = paramLog.getParamCode();
                Integer num = paramLog.getNum();
                if (paramCode.equals(ParamLogConstant.PARAM_CODE_BEFOREDAWN)
                        || paramCode.equals(ParamLogConstant.PARAM_CODE_3AM)) {
                    count1 += num;
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_BEFOREBREAKFAST)) {
                    count2 += num;
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERBREAKFAST) ||
                        paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERBREAKFAST_ONE_HOUR)) {
                    count3 += num;
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_BEFORELUNCH)) {
                    count4 += num;
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERLUNCH) ||
                        paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERLUNCH_ONE_HOUR)) {
                    count5 += num;
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_BEFOREDINNER)) {
                    count6 += num;
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERDINNER) ||
                        paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERDINNER_ONE_HOUR)) {
                    count7 += num;
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_BEFORESLEEP)) {
                    count8 += num;
                }
            }
            testCount += count1 + count2 + count3 + count4 + count5 + count6 + count7 + count8;
            kfCount = count2;
            fkfCount = testCount - count2;

            for (BloodSugarOfParamCodeBO paramLog : memberParamLogModels) {
                String paramCode = paramLog.getParamCode();
                String level = paramLog.getLevel();
                Integer num = paramLog.getNum();
                //凌晨
                if (paramCode.equals(ParamLogConstant.PARAM_CODE_BEFOREDAWN)
                        || paramCode.equals(ParamLogConstant.PARAM_CODE_3AM)) {
                    if ("1".equals(level)) {
                        listLevelOfLow[0] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count1 * 100));
                        lowCount += num;
                    } else if ("3".equals(level)) {
                        listLevelOfNormal[0] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count1 * 100));
                        normalCount += num;
                    } else if ("5".equals(level)) {
                        listLevelOfHeight[0] = Double.parseDouble(df.format(100 - listLevelOfLow[0] - listLevelOfNormal[0]));
                        fkfHeightCount += num;
                    }
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_BEFOREBREAKFAST)) {
                    if ("1".equals(level)) {
                        listLevelOfLow[1] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count2 * 100));
                        lowCount += num;
                    } else if ("3".equals(level)) {
                        listLevelOfNormal[1] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count2 * 100));
                        normalCount += num;
                    } else if ("5".equals(level)) {
                        listLevelOfHeight[1] = Double.parseDouble(df.format(100 - listLevelOfLow[1] - listLevelOfNormal[1]));
                        kfHeightCount += num;
                    }
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERBREAKFAST) ||
                        paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERBREAKFAST_ONE_HOUR)) {
                    if ("1".equals(level)) {
                        listLevelOfLow[2] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count3 * 100));
                        lowCount += num;
                    } else if ("3".equals(level)) {
                        listLevelOfNormal[2] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count3 * 100));
                        normalCount += num;
                    } else if ("5".equals(level)) {
                        listLevelOfHeight[2] = Double.parseDouble(df.format(100 - listLevelOfLow[2] - listLevelOfNormal[2]));
                        fkfHeightCount += num;
                    }
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_BEFORELUNCH)) {
                    if ("1".equals(level)) {
                        listLevelOfLow[3] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count4 * 100));
                        lowCount += num;
                    } else if ("3".equals(level)) {
                        listLevelOfNormal[3] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count4 * 100));
                        normalCount += num;
                    } else if ("5".equals(level)) {
                        listLevelOfHeight[3] = Double.parseDouble(df.format(100 - listLevelOfLow[3] - listLevelOfNormal[3]));
                        fkfHeightCount += num;
                    }
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERLUNCH) ||
                        paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERLUNCH_ONE_HOUR)) {
                    if ("1".equals(level)) {
                        listLevelOfLow[4] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count5 * 100));
                        lowCount += num;
                    } else if ("3".equals(level)) {
                        listLevelOfNormal[4] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count5 * 100));
                        normalCount += num;
                    } else if ("5".equals(level)) {
                        listLevelOfHeight[4] = Double.parseDouble(df.format(100 - listLevelOfLow[4] - listLevelOfNormal[4]));
                        fkfHeightCount += num;
                    }
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_BEFOREDINNER)) {
                    if ("1".equals(level)) {
                        listLevelOfLow[5] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count6 * 100));
                        lowCount += num;
                    } else if ("3".equals(level)) {
                        listLevelOfNormal[5] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count6 * 100));
                        normalCount += num;
                    } else if ("5".equals(level)) {
                        listLevelOfHeight[5] = Double.parseDouble(df.format(100 - listLevelOfLow[5] - listLevelOfNormal[5]));
                        fkfHeightCount += num;
                    }
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERDINNER) ||
                        paramCode.equals(ParamLogConstant.PARAM_CODE_AFTERDINNER_ONE_HOUR)) {
                    if ("1".equals(level)) {
                        listLevelOfLow[6] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count7 * 100));
                        lowCount += num;
                    } else if ("3".equals(level)) {
                        listLevelOfNormal[6] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count7 * 100));
                        normalCount += num;
                    } else if ("5".equals(level)) {
                        listLevelOfHeight[6] = Double.parseDouble(df.format(100 - listLevelOfLow[6] - listLevelOfNormal[6]));
                        fkfHeightCount += num;
                    }
                } else if (paramCode.equals(ParamLogConstant.PARAM_CODE_BEFORESLEEP)) {
                    if ("1".equals(level)) {
                        listLevelOfLow[7] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count8 * 100));
                        lowCount += num;
                    } else if ("3".equals(level)) {
                        listLevelOfNormal[7] = Double.parseDouble(df.format(Double.parseDouble(num + "") / count8 * 100));
                        normalCount += num;
                    } else if ("5".equals(level)) {
                        listLevelOfHeight[7] = Double.parseDouble(df.format(100 - listLevelOfLow[7] - listLevelOfNormal[7]));
                        fkfHeightCount += num;
                    }
                }
            }
            heightCount = testCount - normalCount - lowCount;
        }

        Double lowRate = 0D;
        Double normalRate = 0D;
        Double kfHeightRate = 0D;
        Double fkfHeightRate = 0D;
        if (testCount > 0) {
            lowRate = Double.parseDouble(df.format(Double.parseDouble(lowCount + "") / testCount * 100));
            normalRate = Double.parseDouble(df.format(Double.parseDouble(normalCount + "") / testCount * 100));
            if (kfCount > 0) {
                kfHeightRate = Double.parseDouble(df.format(Double.parseDouble(kfHeightCount + "") / kfCount * 100));
            } else {
                kfHeightRate = 0D;
            }
            if (fkfCount > 0) {
                fkfHeightRate = Double.parseDouble(df.format(Double.parseDouble(fkfHeightCount + "") / fkfCount * 100));
            } else {
                fkfHeightRate = 0D;
            }

        }

        model.setListLevelOfHeight(listLevelOfHeight);
        model.setListLevelOfLow(listLevelOfLow);
        model.setListLevelOfNormal(listLevelOfNormal);
        model.setTestCount(testCount);
        model.setNormalCount(normalCount);
        model.setHeightCount(heightCount);
        model.setLowCount(lowCount);
        model.setLowRate(lowRate);
        model.setNormalRate(normalRate);
        model.setKfHeightRate(kfHeightRate);
        model.setFkfHeightRate(fkfHeightRate);

        return model;
    }

    /**
     * 糖化血红蛋白分布情况
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRangeOfSugarHemoglobin(GetStatisticsDTO dto) {
        Map<String, Object> rangeOfSugarHemoglobin = new HashMap<String, Object>(10);
        Double rangeLess6Rate = 0d;
        Double range6To7Rate = 0d;
        Double range7To8Rate = 0d;
        Double range8To9Rate = 0d;
        Double rangeGreater9Rate = 0d;
        Double rangeLess6Count = 0d;
        Double range6To7Count = 0d;
        Double range7To8Count = 0d;
        Double range8To9Count = 0d;
        Double rangeGreater9Count = 0d;
        Integer sugarHemoglobinCount = 0;
        dto.setFatName("糖化血红蛋白");
        dto.setFatCode("糖化血红蛋白");
        //2型糖尿病
        dto.setDiabetesType("SUGAR_TYPE_002");
        List<CheckoutDetailPO> checkoutDetails = this.checkoutDetailService.listFatByCodeOfStatistics(dto);
        for (CheckoutDetailPO checkoutDetail : checkoutDetails) {
            String sugarHemoglobinV = checkoutDetail.getCheckoutResult();
            if (!StringUtils.isBlank(sugarHemoglobinV)) {
                sugarHemoglobinCount++;
                Double sugarHemoglobinI = Double.parseDouble(sugarHemoglobinV);
                if (sugarHemoglobinI < 6) {
                    rangeLess6Count++;
                } else if (sugarHemoglobinI < 7) {
                    range6To7Count++;
                } else if (sugarHemoglobinI < 8) {
                    range7To8Count++;
                } else if (sugarHemoglobinI <= 9) {
                    range8To9Count++;
                } else {
                    rangeGreater9Count++;
                }
            }
        }

        List<ScreeningReportPO> intelligentResultModels = this.screeningService.listIntelligentResultOfThStatistics(dto);
        for (ScreeningReportPO model : intelligentResultModels) {
            String resultJson = model.getDataJson();
            if (!StringUtils.isBlank(resultJson)) {
                sugarHemoglobinCount++;
                JSONObject jsonObject = JSONObject.parseObject(resultJson);
                String sugarHemoglobinV = jsonObject.getString("HbA1c");
                if (!StringUtils.isBlank(sugarHemoglobinV)) {
                    if (ValidateTool.isNum(sugarHemoglobinV)) {
                        Double sugarHemoglobinI = Double.parseDouble(sugarHemoglobinV);
                        if (sugarHemoglobinI < 6) {
                            rangeLess6Count++;
                        } else if (sugarHemoglobinI < 7) {
                            range6To7Count++;
                        } else if (sugarHemoglobinI < 8) {
                            range7To8Count++;
                        } else if (sugarHemoglobinI <= 9) {
                            range8To9Count++;
                        } else {
                            rangeGreater9Count++;
                        }
                    }
                }
            }
        }

        if (sugarHemoglobinCount > 0) {
            rangeLess6Rate = Double.parseDouble(df.format(rangeLess6Count / sugarHemoglobinCount * 100));
            range6To7Rate = Double.parseDouble(df.format(range6To7Count / sugarHemoglobinCount * 100));
            range7To8Rate = Double.parseDouble(df.format(range7To8Count / sugarHemoglobinCount * 100));
            range8To9Rate = Double.parseDouble(df.format(range8To9Count / sugarHemoglobinCount * 100));
            rangeGreater9Rate = Double.parseDouble(df.format(100 - rangeLess6Rate - range6To7Rate - range7To8Rate - range8To9Rate));
        }
        rangeOfSugarHemoglobin.put("rangeLess6Count", rangeLess6Count);
        rangeOfSugarHemoglobin.put("range6To7Count", range6To7Count);
        rangeOfSugarHemoglobin.put("range7To8Count", range7To8Count);
        rangeOfSugarHemoglobin.put("range8To9Count", range8To9Count);
        rangeOfSugarHemoglobin.put("rangeGreater9Count", rangeGreater9Count);
        rangeOfSugarHemoglobin.put("rangeLess6Rate", rangeLess6Rate);
        rangeOfSugarHemoglobin.put("range6To7Rate", range6To7Rate);
        rangeOfSugarHemoglobin.put("range7To8Rate", range7To8Rate);
        rangeOfSugarHemoglobin.put("range8To9Rate", range8To9Rate);
        rangeOfSugarHemoglobin.put("rangeGreater9Rate", rangeGreater9Rate);
        return rangeOfSugarHemoglobin;
    }

    /**
     * 甘油三酯
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRateOfTG(GetStatisticsDTO dto) {
        Map<String, Object> rateOfTG = new HashMap<String, Object>(5);
        Double trueRate = 0d;
        Double falseRate = 0d;
        Double falseCount = 0d;
        Double trueCount = 0d;
        Integer count = 0;
        dto.setFatCode("甘油三酯");
        dto.setFatName("甘油三酯");
        List<CheckoutDetailPO> checkoutDetails = this.checkoutDetailService.listFatByCodeOfStatistics(dto);
        if (checkoutDetails != null && checkoutDetails.size() > 0) {
            count = checkoutDetails.size();
            for (CheckoutDetailPO checkoutDetail : checkoutDetails) {
                String value = checkoutDetail.getCheckoutResult();
                /*String rangeStr = checkoutDetail.getReferenceRange();
                if(StringUtils.isBlank(rangeStr)){
                    count--;
                    continue;
                }
                String[] rangeArr = rangeStr.split("～");
                if(rangeArr==null || rangeArr.length<=1){
                    rangeArr = rangeStr.split("--");
                }
                if(rangeArr==null || rangeArr.length<=1){
                    count--;
                    continue;
                }
                if(StringUtils.isBlank(rangeArr[0]) || StringUtils.isBlank(rangeArr[1])){
                    count--;
                    continue;
                }*/

                if (!StringUtils.isBlank(value)) {
                    double aDouble = Double.parseDouble(value);
                    /*double max = Double.parseDouble(rangeArr[1]);
                    double min = Double.parseDouble(rangeArr[0]);
                    if(min>aDouble){
                        falseCount++;
                    } else if(aDouble>max){
                        falseCount++;
                    } else {
                        trueCount++;
                    }*/
                    if (aDouble <= 1.69) {
                        trueCount++;
                    } else {
                        falseCount++;
                    }
                }
            }
            if (count > 0) {
                trueRate = Double.parseDouble(df.format(trueCount / count * 100));
                falseRate = Double.parseDouble(df.format(100 - trueRate));
            }
        }
        rateOfTG.put("trueRate", trueRate);
        rateOfTG.put("falseRate", falseRate);
        rateOfTG.put("trueCount", trueCount);
        rateOfTG.put("falseCount", falseCount);
        rateOfTG.put("count", count);
        return rateOfTG;
    }

    /**
     * 总胆固醇
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRateOfTC(GetStatisticsDTO dto) {
        Map<String, Object> rateOfTC = new HashMap<>();
        Double trueRate = 0d;
        Double falseRate = 0d;
        Double falseCount = 0d;
        Double trueCount = 0d;
        Integer count = 0;
        dto.setFatName("总胆固醇");
        dto.setFatCode("总胆固醇");
        List<CheckoutDetailPO> checkoutDetails = this.checkoutDetailService.listFatByCodeOfStatistics(dto);
        if (checkoutDetails != null && checkoutDetails.size() > 0) {
            count = checkoutDetails.size();
            for (CheckoutDetailPO checkoutDetail : checkoutDetails) {
                String value = checkoutDetail.getCheckoutResult();
                /*String rangeStr = checkoutDetail.getReferenceRange();
                if(StringUtils.isBlank(rangeStr)){
                    count--;
                    continue;
                }
                String[] rangeArr = rangeStr.split("～");
                if(rangeArr==null || rangeArr.length<=1){
                    rangeArr = rangeStr.split("--");
                }
                if(rangeArr==null || rangeArr.length<=1){
                    count--;
                    continue;
                }
                if(StringUtils.isBlank(rangeArr[0]) || StringUtils.isBlank(rangeArr[1])){
                    count--;
                    continue;
                }*/

                if (!StringUtils.isBlank(value)) {
                    double aDouble = Double.parseDouble(value);
                    /*double max = Double.parseDouble(rangeArr[1]);
                    double min = Double.parseDouble(rangeArr[0]);
                    if(min>aDouble){
                        falseCount++;
                    } else if(aDouble>max){
                        falseCount++;
                    } else {
                        trueCount++;
                    }*/
                    if (aDouble <= 4.49) {
                        trueCount++;
                    } else {
                        falseCount++;
                    }
                }
            }
            if (count > 0) {
                trueRate = Double.parseDouble(df.format(trueCount / count * 100));
                falseRate = Double.parseDouble(df.format(100 - trueRate));
            }
        }
        rateOfTC.put("trueRate", trueRate);
        rateOfTC.put("falseRate", falseRate);
        rateOfTC.put("trueCount", trueCount);
        rateOfTC.put("falseCount", falseCount);
        rateOfTC.put("count", count);
        return rateOfTC;
    }

    /**
     * 高密度脂蛋白
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRateOfHDLC(GetStatisticsDTO dto) {
        Map<String, Object> rateOfHDLC = new HashMap<>();
        Double trueRate = 0d;
        Double falseRate = 0d;
        Double falseCount = 0d;
        Double trueCount = 0d;
        Integer count = 0;
        dto.setFatName("高密度脂蛋白");
        dto.setFatCode("高密度脂蛋白");
        List<CheckoutDetailPO> checkoutDetails = this.checkoutDetailService.listFatByCodeOfStatistics(dto);
        if (checkoutDetails != null && checkoutDetails.size() > 0) {
            count = checkoutDetails.size();
            for (CheckoutDetailPO checkoutDetail : checkoutDetails) {
                String value = checkoutDetail.getCheckoutResult();
                /*String rangeStr = checkoutDetail.getReferenceRange();
                if(StringUtils.isBlank(rangeStr)){
                    count--;
                    continue;
                }
                String[] rangeArr = rangeStr.split("～");
                if(rangeArr==null || rangeArr.length<=1){
                    rangeArr = rangeStr.split("--");
                }
                if(rangeArr==null || rangeArr.length<=1){
                    count--;
                    continue;
                }
                if(StringUtils.isBlank(rangeArr[0]) || StringUtils.isBlank(rangeArr[1])){
                    count--;
                    continue;
                }*/

                if (!StringUtils.isBlank(value)) {
                    double aDouble = Double.parseDouble(value);
                    /*double max = Double.parseDouble(rangeArr[1]);
                    double min = Double.parseDouble(rangeArr[0]);
                    if(min>aDouble){
                        falseCount++;
                    } else if(aDouble>max){
                        falseCount++;
                    } else {
                        trueCount++;
                    }*/
                    Integer sex = checkoutDetail.getSex();
                    if (sex != null && sex == 1) {
                        if (aDouble >= 1.01) {
                            trueCount++;
                        } else {
                            falseCount++;
                        }
                    } else {
                        if (aDouble >= 1.31) {
                            trueCount++;
                        } else {
                            falseCount++;
                        }
                    }
                }
            }
            if (count > 0) {
                trueRate = Double.parseDouble(df.format(trueCount / count * 100));
                falseRate = Double.parseDouble(df.format(100 - trueRate));
            }
        }
        rateOfHDLC.put("trueRate", trueRate);
        rateOfHDLC.put("falseRate", falseRate);
        rateOfHDLC.put("trueCount", trueCount);
        rateOfHDLC.put("falseCount", falseCount);
        rateOfHDLC.put("count", count);
        return rateOfHDLC;
    }

    /**
     * 低密度脂蛋白
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRateOfLDLC(GetStatisticsDTO dto) {
        Map<String, Object> rateOfLDLC = new HashMap<String, Object>();
        Double trueRate = 0d;
        Double falseRate = 0d;
        Double falseCount = 0d;
        Double trueCount = 0d;
        Integer count = 0;
        dto.setFatName("低密度脂蛋白");
        dto.setFatCode("低密度脂蛋白");
        List<CheckoutDetailPO> checkoutDetails = this.checkoutDetailService.listFatByCodeOfStatistics(dto);
        if (checkoutDetails != null && checkoutDetails.size() > 0) {
            count = checkoutDetails.size();
            for (CheckoutDetailPO checkoutDetail : checkoutDetails) {
                String value = checkoutDetail.getCheckoutResult();
                /*String rangeStr = checkoutDetail.getReferenceRange();
                if(StringUtils.isBlank(rangeStr)){
                    count--;
                    continue;
                }
                String[] rangeArr = rangeStr.split("～");
                if(rangeArr==null || rangeArr.length<=1){
                    rangeArr = rangeStr.split("--");
                }
                if(rangeArr==null || rangeArr.length<=1){
                    count--;
                    continue;
                }
                if(StringUtils.isBlank(rangeArr[0]) || StringUtils.isBlank(rangeArr[1])){
                    count--;
                    continue;
                }*/

                if (!StringUtils.isBlank(value)) {
                    double aDouble = Double.parseDouble(value);
                    /*double max = Double.parseDouble(rangeArr[1]);
                    double min = Double.parseDouble(rangeArr[0]);
                    if(min>aDouble){
                        falseCount++;
                    } else if(aDouble>max){
                        falseCount++;
                    } else {
                        trueCount++;
                    }*/
                    String chd = checkoutDetail.getChd();
                    if (!StringUtils.isBlank(chd) && "QZ01".equals(chd.trim().toUpperCase())) {
                        if (aDouble <= 1.79) {
                            trueCount++;
                        } else {
                            falseCount++;
                        }
                    } else {
                        if (aDouble <= 2.59) {
                            trueCount++;
                        } else {
                            falseCount++;
                        }
                    }
                }
            }
            if (count > 0) {
                trueRate = Double.parseDouble(df.format(trueCount / count * 100));
                falseRate = Double.parseDouble(df.format(100 - trueRate));
            }
        }
        rateOfLDLC.put("trueRate", trueRate);
        rateOfLDLC.put("falseRate", falseRate);
        rateOfLDLC.put("trueCount", trueCount);
        rateOfLDLC.put("falseCount", falseCount);
        rateOfLDLC.put("count", count);
        return rateOfLDLC;

    }

    /**
     * 血压达标率
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRateOfBloodPressure(GetStatisticsDTO dto, Integer inHos) {
        //血压达标率
        Map<String, Object> rateOfBloodPressure = new HashMap<String, Object>(4);
        Double falseCount = 0d;
        Double trueCount = 0d;
        Double falseRate = 0d;
        Double trueRate = 0d;
        //是否糖尿病患者 1是 0否
        dto.setIsDiabetes(1);
        List<BloodPressurePO> bloodPressures = null;
        Integer in = 1, out = 0;
        if (in.equals(inHos)) {
            bloodPressures = this.bloodPressureService.listBloodPressureOfStatistics(dto);
        } else if (out.equals(inHos)) {
            bloodPressures = this.bloodPressureService.listBloodPressureOfStatisticsOutHos(dto);
        }
        if (bloodPressures != null && bloodPressures.size() > 0) {
            Integer bloodPressureCount = bloodPressures.size();
            for (BloodPressurePO bloodPressure : bloodPressures) {
                String dbp = bloodPressure.getDbp();
                String sbp = bloodPressure.getSbp();
                boolean isTrue = false;
                //舒张压
                if (!StringUtils.isBlank(dbp)) {
                    Double dbpI = Double.parseDouble(dbp);
                    if (dbpI < 60) {
                        falseCount++;
                        continue;
                    } else if (dbpI > 79) {
                        falseCount++;
                        continue;
                    }
                    isTrue = true;
                }
                //收缩压
                if (!StringUtils.isBlank(sbp)) {
                    Double sbpI = Double.parseDouble(sbp);
                    if (sbpI < 90) {
                        falseCount++;
                        continue;
                    } else if (sbpI > 129) {
                        falseCount++;
                        continue;
                    }
                    isTrue = true;
                }
                if (isTrue) {
                    trueCount++;
                }
            }

            falseRate = Double.parseDouble(df.format(falseCount / bloodPressureCount * 100));
            trueRate = Double.parseDouble(df.format(100 - falseRate));
        }
        rateOfBloodPressure.put("falseCount", falseCount);
        rateOfBloodPressure.put("trueCount", trueCount);
        rateOfBloodPressure.put("falseRate", falseRate);
        rateOfBloodPressure.put("trueRate", trueRate);
        return rateOfBloodPressure;
    }

    /**
     * BMI分布情况
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRangeOfBmi(GetStatisticsDTO dto) {
        DecimalFormat df = new DecimalFormat("#.00");
        Map<String, Object> rangeOfBmi = new HashMap<String, Object>(8);

        Double rangeLess18_5Count = 0d;
        Double rangeLess18_5Rate = 0d;
        Double range18_5To24Count = 0d;
        Double range18_5To24Rate = 0d;
        Double range24To28Count = 0d;
        Double range24To28Rate = 0d;
        Double rangeGreater28Count = 0d;
        Double rangeGreater28Rate = 0d;
        //大于等于18岁
        dto.setGtEqAge(18);
        List<Map<String, Object>> rangs = this.bmiService.listBmiRang(dto);
        if (rangs != null && rangs.size() > 0) {
            Double bmiCount = 0d;
            for (Map<String, Object> map : rangs) {
                switch (map.get("bmi_rang").toString()) {
                    case "0-18.5":
                        rangeLess18_5Count = Double.valueOf(map.get("num") == null ? "0" : map.get("num").toString());
                        break;
                    case "18.5-24":
                        range18_5To24Count = Double.valueOf(map.get("num") == null ? "0" : map.get("num").toString());
                        break;
                    case "24-28":
                        range24To28Count = Double.valueOf(map.get("num") == null ? "0" : map.get("num").toString());
                        break;
                    case "28":
                        rangeGreater28Count = Double.valueOf(map.get("num") == null ? "0" : map.get("num").toString());
                        break;
                    default:
                        break;
                }
            }

            bmiCount = rangeLess18_5Count + range18_5To24Count + range24To28Count + rangeGreater28Count;

            rangeLess18_5Rate = Double.parseDouble(df.format(rangeLess18_5Count / bmiCount * 100));
            range18_5To24Rate = Double.parseDouble(df.format(range18_5To24Count / bmiCount * 100));
            range24To28Rate = Double.parseDouble(df.format(range24To28Count / bmiCount * 100));
            rangeGreater28Rate = Double.parseDouble(df.format(100 - rangeLess18_5Rate - range18_5To24Rate - range24To28Rate));
        }
        rangeOfBmi.put("rangeLess18_5Count", rangeLess18_5Count);
        rangeOfBmi.put("range18_5To24Count", range18_5To24Count);
        rangeOfBmi.put("range24To28Count", range24To28Count);
        rangeOfBmi.put("rangeGreater28Count", rangeGreater28Count);
        rangeOfBmi.put("rangeLess18_5Rate", rangeLess18_5Rate);
        rangeOfBmi.put("range18_5To24Rate", range18_5To24Rate);
        rangeOfBmi.put("range24To28Rate", range24To28Rate);
        rangeOfBmi.put("rangeGreater28Rate", rangeGreater28Rate);
        return rangeOfBmi;
    }

    /**
     * 患者人次
     *
     * @param dto
     * @return
     */
    private long getMemberNumber(GetStatisticsDTO dto) {
        long memberNumber = 0l;
        memberNumber = this.memberService.getMemberNumber(dto);
        return memberNumber;
    }

    /**
     * 性别占比
     *
     * @param dto
     * @return
     */
    private Map<String, Object> getRateOfMemberSex(GetStatisticsDTO dto, long memberCount) {
        //2.性别占比
        Map<String, Object> rateOfMemberSex = new HashMap<>(4);
        long boyCount = 0;
        Double boyRate = 0d;
        Double girlRate = 0d;
        long girlCount = 0;

        if (memberCount > 0) {
            dto.setSex(1);
            boyCount = this.memberService.countMemberWhere(dto);
            girlCount = memberCount - boyCount;
            boyRate = Double.parseDouble(df.format(Double.parseDouble(boyCount + "") / memberCount * 100));
            girlRate = Double.parseDouble(df.format(100 - boyRate));
        }

        rateOfMemberSex.put("boyRate", boyRate);
        rateOfMemberSex.put("girlRate", girlRate);
        rateOfMemberSex.put("girlCount", girlCount);
        rateOfMemberSex.put("boyCount", boyCount);
        return rateOfMemberSex;
    }

    private NumberStatisticsVO getNumberStatisticsWhere(GetStatisticsDTO dto) {
        NumberStatisticsVO numberStatisticsVO = new NumberStatisticsVO();
        //统计处方数量
        dto.setSchedule(PrescriptionConstant.SCHEDULE_COMPLETE);
        long prescriptionNumber = this.prescriptionServiceI.countPrescription(dto);
        numberStatisticsVO.setPrescriptionNumber(prescriptionNumber);
        //随访统计
        long followNumber = this.followServiceI.countFollow(dto);
        numberStatisticsVO.setFollowNumber(followNumber);
        //统计订单总额
        dto.setPackageStatus(PackageConstant.PACKAGE_STATUS_NORMAL);
        long totalPrice = this.packageService.sumPackagePrice(dto);
        numberStatisticsVO.setOrderTotalNumber(totalPrice);
        //付费患者数量统计
        dto.setPayStatus(MemberConstant.PACKAGE_STATUS_PAY);
        long memberNumber = this.memberService.countMemberDoctor(dto);
        numberStatisticsVO.setMemberTotalNumber(memberNumber);
        return numberStatisticsVO;
    }

    public List<OrderChartVO> listOrderChartDataWhere(GetStatisticsDTO dto) {
        String endDt = dto.getEndDt();
        String startDt = dto.getStartDt();
        if (StringUtils.isBlank(startDt)) {
            startDt = DateHelper.getToday() + DateHelper.DEFUALT_TIME_START;
        }
        String startStr = DateHelper.getDate(DateHelper.getDate(startDt, DateHelper.DAY_FORMAT),
                DateHelper.DAY_FORMAT_MONTH);
        String tempStartDt = DateHelper.getDate(DateHelper.getDate(startDt, DateHelper.DAY_FORMAT),
                DateHelper.DAY_FORMAT_MONTH) + "-01" + DateHelper.DEFUALT_TIME_START;
        String tempEndDt = DateHelper.plusDate(tempStartDt, 2, 11, DateHelper.DAY_FORMAT)
                + DateHelper.DEFUALT_TIME_END;
        dto.setStartDt(tempStartDt);
        dto.setEndDt(tempEndDt);
        List<PackageMonthPricePO> monthDataList = this.packageService.sumPackageMonthPrice(dto);
        Map<String, Long> priceMap = monthDataList.stream().collect(Collectors.toMap(PackageMonthPricePO::getMonth, PackageMonthPricePO::getPriceTotal));
        List<OrderChartVO> list = new ArrayList<>();
        OrderChartVO vo;
        Date tempDate = DateHelper.getDate(startStr, DateHelper.DAY_FORMAT_MONTH);
        for (int i = 1; i <= MAX_MONTH; i++) {
            vo = new OrderChartVO();
            if (i == 1) {
                vo.setMonth(startStr);
                vo.setOrderTotal(priceMap.getOrDefault(startStr, 0L));
            } else {
                String oldYearMonth = DateHelper.getDate(tempDate, DateHelper.DAY_FORMAT_MONTH);
                String year = DateHelper.getDate(tempDate, "yyyy");
                String newYearMonth = DateHelper.plusDate(oldYearMonth, 2, 1, DateHelper.DAY_FORMAT_MONTH);
                Date newDate = DateHelper.getDate(newYearMonth, DateHelper.DAY_FORMAT_MONTH);
                String newYear = DateHelper.getDate(newDate, "yyyy");
                if (year.equals(newYear)) {
                    String month = DateHelper.getDate(newDate, "MM");
                    vo.setMonth(month);
                } else {
                    vo.setMonth(newYearMonth);
                }
                vo.setOrderTotal(priceMap.getOrDefault(newYearMonth, 0L));
                tempDate = newDate;
            }
            list.add(vo);
        }
        dto.setStartDt(startDt);
        dto.setEndDt(endDt);
        return list;
    }

    private List<StatisticsChartVO> listStatisticsChartDataWhere(GetStatisticsDTO dto) {
        List<StatisticsChartVO> list = new ArrayList<>();
        StatisticsChartVO statisticsChartVO;
        String endDt = dto.getEndDt();
        String startDt = dto.getStartDt();
        if (StringUtils.isBlank(startDt)) {
            startDt = DateHelper.getToday() + DateHelper.DEFUALT_TIME_START;
        }
        String startStr = DateHelper.getDate(DateHelper.getDate(startDt, DateHelper.DAY_FORMAT),
                DateHelper.DAY_FORMAT_MONTH);
        String tempStartDt = DateHelper.getDate(DateHelper.getDate(startDt, DateHelper.DAY_FORMAT),
                DateHelper.DAY_FORMAT_MONTH) + "-01" + DateHelper.DEFUALT_TIME_START;
        String tempEndDt = DateHelper.plusDate(tempStartDt, 2, 11, DateHelper.DAY_FORMAT)
                + DateHelper.DEFUALT_TIME_END;
        dto.setStartDt(tempStartDt);
        dto.setEndDt(tempEndDt);
        //处方
        List<CountMonthPrescriptionPO> monthPrescriptionPOList = this.prescriptionServiceI.countMonthPrescription(dto);
        Map<String, Long> prescriptionPOMap = monthPrescriptionPOList
                .stream()
                .collect(Collectors.toMap(CountMonthPrescriptionPO::getYearMonth, CountMonthPrescriptionPO::getCount));
        statisticsChartVO = new StatisticsChartVO();
        statisticsChartVO.setDataList(chartDataListForYearHandler(startStr, prescriptionPOMap));
        statisticsChartVO.setItemCode(ChartItemCode.CHART_ITEM_PRESCRIPTION);
        list.add(statisticsChartVO);

        //随访
        List<CountMonthFollowPO> monthFollowPOList = this.followServiceI.countMonthFollow(dto);
        Map<String, Long> monthFollowPOMap = monthFollowPOList.stream().collect(Collectors.toMap(CountMonthFollowPO::getYearMonth, CountMonthFollowPO::getCount));
        statisticsChartVO = new StatisticsChartVO();
        statisticsChartVO.setDataList(chartDataListForYearHandler(startStr, monthFollowPOMap));
        statisticsChartVO.setItemCode(ChartItemCode.CHART_ITEM_FOLLOW);
        list.add(statisticsChartVO);

        //订单
        List<CountMonthPackagePO> monthPackagePOList = this.packageService.countMonthPackage(dto);
        Map<String, Long> monthPackageMap = monthPackagePOList
                .stream()
                .collect(Collectors.toMap(CountMonthPackagePO::getYearMonth, CountMonthPackagePO::getCount));
        statisticsChartVO = new StatisticsChartVO();
        statisticsChartVO.setDataList(chartDataListForYearHandler(startStr, monthPackageMap));
        statisticsChartVO.setItemCode(ChartItemCode.CHART_ITEM_ORDER);
        list.add(statisticsChartVO);
        //患者
        List<CountMonthMemberPO> monthMemberPOList = this.memberService.countMonthMember(dto);
        Map<String, Long> monthMemberMap = monthMemberPOList.stream()
                .collect(Collectors.toMap(CountMonthMemberPO::getYearMonth, CountMonthMemberPO::getCount));
        statisticsChartVO = new StatisticsChartVO();
        statisticsChartVO.setDataList(chartDataListForYearHandler(startStr, monthMemberMap));
        statisticsChartVO.setItemCode(ChartItemCode.CHART_ITEM_MEMBER);
        list.add(statisticsChartVO);
        dto.setStartDt(startDt);
        dto.setEndDt(endDt);

        return list;
    }

    /**
     * 患者管理处方制定比例
     *
     * @param dataAnalysisDTO
     * @return
     */
    private Map<String, Object> getRateOfPrescription(GetStatisticsDTO dataAnalysisDTO, long memberCount) {
        Map<String, Object> rateOfIntelligent = new HashMap<>(5);
        long prescriptionOfMemberCount = 0;
        Double unPrescriptionOfMemberCount = 0d;
        Double prescriptionMemberRate = 0d;
        Double unPrescriptionOfMemberRate = 0d;
        long prescriptionNumber = 0;
        if (memberCount > 0) {
            prescriptionOfMemberCount = this.memberService.getPrescriptionOfMemberCount(dataAnalysisDTO);
            unPrescriptionOfMemberCount = Double.parseDouble(df.format(memberCount - Double.parseDouble(prescriptionOfMemberCount + "")));
            prescriptionMemberRate = Double.parseDouble(df.format(Double.parseDouble(prescriptionOfMemberCount + "") / memberCount * 100));
            unPrescriptionOfMemberRate = Double.parseDouble(df.format(100 - prescriptionMemberRate));
            //统计处方数量
            dataAnalysisDTO.setSchedule(PrescriptionConstant.SCHEDULE_COMPLETE);
            prescriptionNumber = this.prescriptionServiceI.countPrescription(dataAnalysisDTO);
        }
        rateOfIntelligent.put("memberCount", memberCount);
        rateOfIntelligent.put("prescriptionOfMemberCount", prescriptionOfMemberCount);
        rateOfIntelligent.put("unPrescriptionOfMemberCount", unPrescriptionOfMemberCount);
        rateOfIntelligent.put("prescriptionMemberRate", prescriptionMemberRate);
        rateOfIntelligent.put("unPrescriptionOfMemberRate", unPrescriptionOfMemberRate);
        //统计处方数量
        rateOfIntelligent.put("prescriptionNumber", prescriptionNumber);
        return rateOfIntelligent;
    }

    /**
     * 管理处方制定内容占比情况
     *
     * @param dataAnalysisDTO
     * @return
     */
    private Map<String, Object> getRangeOfPrescription(GetStatisticsDTO dataAnalysisDTO) {
        Map<String, Object> rangeOfPrescription = new HashMap<String, Object>(10);
        Double tagarRate = 0d;
        Double sugarMonitorRate = 0d;
        Double repiceRate = 0d;
        Double sportRate = 0d;
        Double eduRate = 0d;
        Double tagarCount = 0d;
        Double sugarMonitorCount = 0d;
        Double repiceCount = 0d;
        Double sportCount = 0d;
        Double eduCount = 0d;
        List<PrescriptionPO> prescriptions = this.prescriptionServiceI.listPrescriptionOfStatistics(dataAnalysisDTO);
        if (prescriptions != null && prescriptions.size() > 0) {
            Integer prescriptionCount = 0;
            for (PrescriptionPO prescription : prescriptions) {
                String module = prescription.getModule();
                if (module.indexOf("1") > -1) {
                    tagarCount++;
                    prescriptionCount++;
                }
                if (module.indexOf("2") > -1) {
                    sugarMonitorCount++;
                    prescriptionCount++;
                }
                if (module.indexOf("3") > -1) {
                    repiceCount++;
                    prescriptionCount++;
                }
                if (module.indexOf("4") > -1) {
                    sportCount++;
                    prescriptionCount++;
                }
                if (module.indexOf("5") > -1) {
                    eduCount++;
                    prescriptionCount++;
                }
            }
            tagarRate = Double.parseDouble(df.format(tagarCount / prescriptionCount * 100));
            sugarMonitorRate = Double.parseDouble(df.format(sugarMonitorCount / prescriptionCount * 100));
            repiceRate = Double.parseDouble(df.format(repiceCount / prescriptionCount * 100));
            sportRate = Double.parseDouble(df.format(sportCount / prescriptionCount * 100));
            eduRate = Double.parseDouble(df.format(100 - tagarRate - sugarMonitorRate - repiceRate - sportRate));
        }
        rangeOfPrescription.put("tagarCount", tagarCount);
        rangeOfPrescription.put("sugarMonitorCount", sugarMonitorCount);
        rangeOfPrescription.put("repiceCount", repiceCount);
        rangeOfPrescription.put("sportCount", sportCount);
        rangeOfPrescription.put("eduCount", eduCount);
        rangeOfPrescription.put("tagarRate", tagarRate);
        rangeOfPrescription.put("sugarMonitorRate", sugarMonitorRate);
        rangeOfPrescription.put("repiceRate", repiceRate);
        rangeOfPrescription.put("sportRate", sportRate);
        rangeOfPrescription.put("eduRate", eduRate);
        //统计处方数量
        dataAnalysisDTO.setSchedule(PrescriptionConstant.SCHEDULE_COMPLETE);
        long prescriptionNumber = this.prescriptionServiceI.countPrescription(dataAnalysisDTO);
        rangeOfPrescription.put("prescriptionNumber", prescriptionNumber);
        return rangeOfPrescription;
    }


    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getMemberParamLog(List<BloodSugarPO> paramLogModels) {
        //将用户血糖按每日划分
        List<Map<String, Object>> list = ChartItemCode.resetList(paramLogModels);
        //取最后7天算血糖偏度
        for (Map<String, Object> stringObjectMap : list) {
            List<BloodSugarPO> paramLogList = (List<BloodSugarPO>) stringObjectMap.get("paramLogList");
            List<Float> floats = new ArrayList<Float>();
            for (BloodSugarPO memberParamLogModel : paramLogList) {
                Float value = Float.valueOf(memberParamLogModel.getParamValue());
                floats.add(value);
            }

            //获取当天血糖平均值
            Float valueAverage = ParamLogConstant.getAverage(floats);
            //获取当天血糖漂移度
            Float standarDeviton = ParamLogConstant.getStandardDevition(valueAverage, floats);
            stringObjectMap.put("valueAverage", valueAverage);
            stringObjectMap.put("standarDeviton", standarDeviton);
        }
        return list;
    }


    /**
     * 获取用户血糖图表
     *
     * @param paramLogModels
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getMemberParamLogDriftance(List<BloodSugarPO> paramLogModels) {
        //将用户血糖按每日划分
        List<Map<String, Object>> list = ChartItemCode.resetList(paramLogModels);
        //取最后7天算血糖偏度
        for (Map<String, Object> stringObjectMap : list) {
            List<BloodSugarPO> paramLogList = (List<BloodSugarPO>) stringObjectMap.get("paramLogList");
            List<Float> floats = new ArrayList<Float>();
            for (BloodSugarPO memberParamLogModel : paramLogList) {
                Float value = Float.valueOf(memberParamLogModel.getParamValue());
                floats.add(value);
            }

            //获取当天血糖平均值
            Float valueAverage = ParamLogConstant.getAverage(floats);
            //获取当天血糖漂移度
            Float standarDeviton = ParamLogConstant.getStandardDevition(valueAverage, floats);
            stringObjectMap.put("valueAverage", valueAverage);
            stringObjectMap.put("standarDeviton", standarDeviton);
        }
        return list;
    }

    //计算两个时间之间相差几个月
    private static int differMonth(String startDt, String endDt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        int res = 0;
        try {
            bef.setTime(sdf.parse(endDt));
            aft.setTime(sdf.parse(startDt));
            int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
            int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
            res = Math.abs(month + result);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return res;
    }


    private static String getEndDt(String start) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date parse = null;
        String date1 = "";
        try {
            parse = sdf.parse(start);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            calendar.add(Calendar.MONTH, 1);
            Date time = calendar.getTime();
            date1 = DateHelper.getDate(time, DateHelper.DAY_FORMAT_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    private final static int MAX_MONTH = 12;

    /**
     * 获取虚拟病区统计数据
     *
     * @param gto
     * @return
     */
    private Map<String, Object> getVirtualWardStatistics(GetStatisticsDTO gto) {

        Map<String, Object> result = new HashMap<>();
        //获取数据
        ListVirtualWardPatientParam param = new ListVirtualWardPatientParam();
        param.setStatStartDt(gto.getStartDt());
        param.setStatEndDt(gto.getEndDt());
        param.setHospitalId(gto.getHospitalId());
        List<VirtualWardListPO> list = this.virtualWardService.listVirtualWardPatient(param);

        int totalPeople = list.size(), intoPeople = 0, outPeople = 0, sumDay = 0, manNumber = 0, womanNumber = 0, lessThan30Age = 0, between30And50Age = 0, between51And70Age = 0, gatherThan70Age = 0;

        //转入虚拟病区前一天的血糖情况 以及转出虚拟病区前一天的血糖情况
        Map<String, List<BloodSugarPO>> beforeIntoBloodSugar = new HashMap<>();
        Map<String, List<BloodSugarPO>> beforeOutBloodSugar = new HashMap<>();
        List<String> foreignIdList = new ArrayList<>();
        for (VirtualWardListPO item : list) {
            //是否转入
            if (checkDateBetween(gto.getStartDt(), gto.getEndDt(), item.getIntoDate())) {
                intoPeople++;
            }
            /**
             * 是否转出
             */
            if (checkDateBetween(gto.getStartDt(), gto.getEndDt(), item.getOutDate())) {
                outPeople++;
            }

            //总天数
            sumDay += getDayDiff(item.getIntoDate(), item.getOutDate());

            //性别
            if (item.getSex() != null) {
                if (1 == item.getSex()) {
                    manNumber++;
                } else if (2 == item.getSex()) {
                    womanNumber++;
                }
            }

            //年龄
            int age = DateHelper.getAge(item.getBirthday());
            if (age < 30) {
                lessThan30Age++;
            } else if (age >= 30 && age <= 50) {
                between30And50Age++;
            } else if (age >= 51 && age <= 70) {
                between51And70Age++;
            } else {
                gatherThan70Age++;
            }
            if (!StringUtils.isBlank(item.getIntoDate()) && !StringUtils.isBlank(item.getOutDate())) {
                beforeBloodSugarHandler(item.getIntoDate(), item.getMemberId(), beforeIntoBloodSugar);
                beforeBloodSugarHandler(item.getOutDate(), item.getMemberId(), beforeOutBloodSugar);
            }
            foreignIdList.add(item.getSid());
        }

        result.put("totalPeople", totalPeople);
        result.put("intoPeople", intoPeople);
        result.put("outPeople", outPeople);
        result.put("avgDay", divide(sumDay, totalPeople));
        result.put("manRate", divide(manNumber, totalPeople));
        result.put("womanRate", divide(womanNumber, totalPeople));
        result.put("womanNumber", womanNumber);
        result.put("manNumber", manNumber);
        result.put("<30", divide(lessThan30Age, totalPeople));
        result.put("30~50", divide(between30And50Age, totalPeople));
        result.put("51~70", divide(between51And70Age, totalPeople));
        result.put(">70", divide(gatherThan70Age, totalPeople));
        result.put("<30Number", lessThan30Age);
        result.put("30~50Number", between30And50Age);
        result.put("51~70Number", between51And70Age);
        result.put(">70Number", gatherThan70Age);
        result.put("beforeIntoBloodSugar", virtualWardBloodSugarChartDataHandler(beforeIntoBloodSugar));
        result.put("beforeOutBloodSugar", virtualWardBloodSugarChartDataHandler(beforeOutBloodSugar));
        result.put("insulinPumpStatData", getInsulinPumpStatData(foreignIdList));
        return result;
    }

    /**
     * 判断日期是否在范围内
     *
     * @param startDt
     * @param endDt
     * @param checkDt
     * @return
     */
    private boolean checkDateBetween(String startDt, String endDt, String checkDt) {
        if (StringUtils.isBlank(checkDt)) {
            return false;
        }
        return (startDt.compareTo(checkDt) <= 0) && (endDt.compareTo(checkDt) >= 0);
    }

    /**
     * 获取天数差距
     *
     * @param startDt
     * @param endDt
     * @return
     */
    private int getDayDiff(String startDt, String endDt) {
        if (StringUtils.isBlank(endDt)) {
            endDt = DateHelper.getNowDate();
        }
        return Math.abs(DateHelper.dateCompareGetDay(endDt, startDt)) + 1;
    }

    /**
     * 除
     *
     * @param denominator
     * @param molecule
     * @return
     */
    private double divide(int denominator, int molecule) {
        if (molecule <= 0) {
            return 0D;
        }
        return new BigDecimal(denominator).divide(new BigDecimal(molecule), 1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转入转出虚拟病区前一天血糖数据处理
     *
     * @param date
     * @param memberId
     * @param beforeBloodSugar
     */
    private void beforeBloodSugarHandler(String date, String memberId, Map<String, List<BloodSugarPO>> beforeBloodSugar) {
        if (StringUtils.isBlank(date)) {
            return;
        }
        String queryDate = DateHelper.plusDate(date.substring(0, 10), -1);
        List<BloodSugarPO> list = this.bloodSugarService.listOneDayLatestBloodSugarGroupParamCode(memberId, queryDate + " 00:00:00", date);
        if (list == null || list.isEmpty()) {
            return;
        }
        List<BloodSugarPO> valueList = null;
        for (BloodSugarPO sugarPO : list) {
            //随机血糖先不统计
            if (SignConstant.PARAM_CODE_RANDOM_TIME.equals(sugarPO.getParamCode())) {
                continue;
            }
            valueList = beforeBloodSugar.get(sugarPO.getParamCode());
            if (valueList == null) {
                valueList = new ArrayList<>();
                beforeBloodSugar.put(sugarPO.getParamCode(), valueList);
            }
            valueList.add(sugarPO);
        }
    }

    /**
     * 虚拟病区血糖统计图表数据处理
     *
     * @param data
     * @return
     */
    private Map<String, Object> virtualWardBloodSugarChartDataHandler(Map<String, List<BloodSugarPO>> data) {
        int totalNormal = 0;
        int totalHigh = 0;
        int totalLow = 0;
        int totalNumber = 0;
        List<BloodSugarPO> dataList = null;
        Map<String, Map<String, Double>> rateMap = new HashMap<>();
        for (Map.Entry<String, List<BloodSugarPO>> entry : data.entrySet()) {
            dataList = entry.getValue();
            int low = 0;
            int high = 0;
            for (BloodSugarPO bloodSugarPO : dataList) {
                int level = virtualWardBloodSugarLevelHandler(bloodSugarPO.getParamValue());
                if (SignConstant.SIGN_LEVEL_HIGH == level) {
                    totalHigh++;
                    high++;
                } else if (SignConstant.SIGN_LEVEL_LOW == level) {
                    totalLow++;
                    low++;
                } else {
                    totalNormal++;
                }
                totalNumber++;
            }
            Map<String, Double> paramCodeRate = new HashMap<>();
            paramCodeRate.put("highRate", divide(high * 100, dataList.size()));
            paramCodeRate.put("lowRate", divide(low * 100, dataList.size()));
            rateMap.put(entry.getKey(), paramCodeRate);
        }
        //图表数据处理
        List<Double> lowList = new ArrayList<>();
        List<Double> highList = new ArrayList<>();
        List<String> paramCodeList = new ArrayList<>();
        paramCodeList.add(SignConstant.PARAM_CODE_BEFORE_DAWN);
        paramCodeList.add(SignConstant.PARAM_CODE_BEFORE_BREAKFAST);
        paramCodeList.add(SignConstant.PARAM_CODE_AFTER_BREAKFAST);
        paramCodeList.add(SignConstant.PARAM_CODE_BEFORE_LUNCH);
        paramCodeList.add(SignConstant.PARAM_CODE_AFTER_LUNCH);
        paramCodeList.add(SignConstant.PARAM_CODE_BEFORE_DINNER);
        paramCodeList.add(SignConstant.PARAM_CODE_AFTER_DINNER);
        paramCodeList.add(SignConstant.PARAM_CODE_BEFORE_SLEEP);
        Map<String, Double> paramCodeRate = null;
        for (String code : paramCodeList) {
            paramCodeRate = rateMap.get(code);
            if (paramCodeRate == null) {
                lowList.add(0D);
                highList.add(0D);
            } else {
                lowList.add(paramCodeRate.get("lowRate"));
                highList.add(paramCodeRate.get("highRate"));
            }
        }
        Map<String, List<Double>> chartData = new HashMap<>();
        chartData.put("lowList", lowList);
        chartData.put("highList", highList);
        Map<String, Object> result = new HashMap<>();
        result.put("chartData", chartData);
        result.put("totalNormal", totalNormal);
        result.put("totalHigh", totalHigh);
        result.put("totalLow", totalLow);
        result.put("totalNumber", totalNumber);
        result.put("totalNormalRate", divide(totalNormal * 100, totalNumber));
        result.put("totalHighRate", divide(totalHigh * 100, totalNumber));
        result.put("totalLowRate", divide(totalLow * 100, totalNumber));
        return result;
    }

    /**
     * 虚拟病区血糖等级判断
     *
     * @param paramValue
     * @return
     */
    private int virtualWardBloodSugarLevelHandler(String paramValue) {
        //血糖等级 1 偏低 3 正常 5 偏高
        int level = 0;
        Float value = Float.parseFloat(paramValue);
        if (value >= 16.7f) {
            level = SignConstant.SIGN_LEVEL_HIGH;
        } else if (value <= 3.9f) {
            level = SignConstant.SIGN_LEVEL_LOW;
        } else {
            level = SignConstant.SIGN_LEVEL_NORMAL;
        }
        return level;
    }

    /**
     * 获取胰岛素泵统计数据
     *
     * @param hospitalNoList
     * @return
     */
    private Map<String, Object> getInsulinPumpStatData(List<String> foreignIdList) {
        List<MemberYzListVO> listVOS = null;
        if (foreignIdList != null && !foreignIdList.isEmpty()) {
            ListMemberYzDTO dto = new ListMemberYzDTO();
            dto.setForeignIdList(foreignIdList);
            dto.setYzItemCodeList(Collections.singletonList(YzConstant.YZ_ITEM_CODE_INSULIN_PUMP));
            dto.setRecordOriginList(Collections.singletonList(YzConstant.RECORD_ORIGIN_VIRTUAL_WARD));
            dto.setYzStatusList(new ArrayList<>(Arrays.asList(
                    String.valueOf(YzConstant.YZ_STATUS_EXECUTED)
                    , String.valueOf(YzConstant.YZ_STATUS_STOP)
            )));
            listVOS = this.yzService.listMemberYz(dto);
        } else {
            listVOS = new ArrayList<>();
        }

        MultiValueMap<String, MemberYzListVO> foreignIdGroupMap = new LinkedMultiValueMap<>();
        for (MemberYzListVO item : listVOS) {
            foreignIdGroupMap.add(item.getForeignId(), item);
        }

        int lessThan3Day = 0, between3And5 = 0, between5And7 = 0, greaterThan7Day = 0;
        double lessThan3DaySum = 0d, between3And5Sum = 0d, between5And7Sum = 0d, greaterThan7DaySum = 0d;

        List<InsulinPumpDayUsageVO> list;
        for (Map.Entry<String, List<MemberYzListVO>> entry : foreignIdGroupMap.entrySet()) {
            list = this.insulinPumpService.dayUsageListHandler(entry.getValue());
            double sum = 0;
            for (InsulinPumpDayUsageVO vo : list) {
                sum += vo.getDayTotal();
            }
            int daySum = list.size();
            double dayAvg = InsulinPumpTool.divide(sum, daySum);
            if (daySum <= 3) {
                lessThan3Day++;
                lessThan3DaySum += dayAvg;
            } else if (daySum > 3 && daySum <= 5) {
                between3And5++;
                between3And5Sum += dayAvg;
            } else if (daySum > 5 && daySum <= 7) {
                between5And7++;
                between5And7Sum += dayAvg;
            } else {
                greaterThan7Day++;
                greaterThan7DaySum += dayAvg;
            }
        }
        int hasInsulinPumpNumber = (int) listVOS.stream().filter(x -> YzConstant.YZ_TYPE_LONG_TERM == x.getYzType()).count();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("lessThan3Day", getInsulinPumpStatCalculationResult(lessThan3Day, hasInsulinPumpNumber, lessThan3DaySum));
        result.put("between3And5", getInsulinPumpStatCalculationResult(between3And5, hasInsulinPumpNumber, between3And5Sum));
        result.put("between5And7", getInsulinPumpStatCalculationResult(between5And7, hasInsulinPumpNumber, between5And7Sum));
        result.put("greaterThan7Day", getInsulinPumpStatCalculationResult(greaterThan7Day, hasInsulinPumpNumber, greaterThan7DaySum));
        result.put("hasInsulinPumpNumber", hasInsulinPumpNumber);
        return result;
    }

    /**
     * 获取胰岛素泵统计计算结果
     *
     * @param peopleNumber
     * @param peopleNumberSum
     * @param avgSum
     * @param day
     * @return
     */
    private Map<String, Object> getInsulinPumpStatCalculationResult(int peopleNumber, int peopleNumberSum, Double avgSum) {
        Map<String, Object> result = new HashMap<>();
        result.put("peopleNumber", peopleNumber);
        result.put("peopleNumberRate", divide(peopleNumber * 100, peopleNumberSum));
        result.put("dagAvgTotal", InsulinPumpTool.divide(avgSum, peopleNumber));
        return result;
    }

    /**
     * 佩戴动态血糖人数统计
     *
     * @param gto
     * @return
     */
    private long hasDynamicBloodSugarSensorStatistics(GetStatisticsDTO gto) {
        return this.dyStaticsService.hasDynamicBloodSugarSensorStatistics(gto);
    }



}
