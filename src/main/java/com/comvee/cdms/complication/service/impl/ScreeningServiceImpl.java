package com.comvee.cdms.complication.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.comvee.cdms.checkresult.constant.CheckConstant;
import com.comvee.cdms.checkresult.constant.InspectionConstant;
import com.comvee.cdms.checkresult.constant.InspectionDict;
import com.comvee.cdms.checkresult.dto.AddInspectionDTO;
import com.comvee.cdms.checkresult.service.InspectionServiceI;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.constant.EyesConstant;
import com.comvee.cdms.complication.constant.ScreeningConstant;
import com.comvee.cdms.complication.constant.StatsConstant;
import com.comvee.cdms.complication.mapper.ScreeningDataMapper;
import com.comvee.cdms.complication.mapper.ScreeningMapper;
import com.comvee.cdms.complication.mapper.ScreeningReportMapper;
import com.comvee.cdms.complication.model.dto.*;
import com.comvee.cdms.complication.model.po.*;
import com.comvee.cdms.complication.model.vo.ScreeningListVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatFinishVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatOrderVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatVO;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.complication.service.ScreeningStatsService;
import com.comvee.cdms.complication.tool.AbiTool;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.AddMemberHealthWarningNoticeDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorNoticeService;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import com.comvee.cdms.visit.constant.VisitEventEnum;
import com.comvee.cdms.visit.dto.AddVistiEventDTO;
import com.comvee.cdms.visit.service.VisitEventService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
@Service("screeningService")
public class ScreeningServiceImpl implements ScreeningService {


    private final static Logger logger = LoggerFactory.getLogger(ScreeningServiceImpl.class);

    @Autowired
    private ScreeningMapper screeningMapper;

    @Autowired
    private ScreeningReportMapper screeningReportMapper;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private ScreeningDataMapper screeningDataMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ScreeningStatsService statsService;

    @Autowired
    private InspectionServiceI inspectionService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private VisitEventService visitEventService;

    @Autowired
    private DoctorNoticeService doctorNoticeService;

    @Override
    public void addScreeningList(AddScreeningListDTO addScreeningListDTO) {
        this.screeningMapper.addScreeningList(addScreeningListDTO);
    }

    @Override
    public ScreeningListPO getScreeningById(String screeningId) {
        return this.screeningMapper.getScreeningById(screeningId);
    }

    @Override
    public void updateScreeningList(ScreeningListPO screeningListPO) {
        this.screeningMapper.updateScreeningList(screeningListPO);
    }

    @Override
    public void addScreeningReport(ScreeningReportPO screeningReportPO) {
        this.screeningReportMapper.addScreeningReport(screeningReportPO);
    }

    @Override
    public void updateScreeningReport(ScreeningReportPO screeningReportPO) {
        this.screeningReportMapper.updateScreeningReport(screeningReportPO);
    }

    /**
     * 报告json修改字段处理
     *
     * @param screeningReportPO
     * @param updateScreeningReportParam
     */
    private void reportJsonUpdateHandler(ScreeningReportPO screeningReportPO, UpdateScreeningReportParam updateScreeningReportParam) {
        JSONObject jsonObject = JSON.parseObject(screeningReportPO.getReportJson());

        if (!StringUtils.isBlank(updateScreeningReportParam.getDoctorMsg())) {
            jsonObject.put("doctorMsg", updateScreeningReportParam.getDoctorMsg());
        }
        if (!StringUtils.isBlank(updateScreeningReportParam.getCompareAnalysis())) {
            jsonObject.put("compareAnalysis", updateScreeningReportParam.getCompareAnalysis());
        }
        if (!StringUtils.isBlank(updateScreeningReportParam.getCheckTips())) {
            jsonObject.put("checkTips", updateScreeningReportParam.getCheckTips());
        }
        if (!StringUtils.isBlank(updateScreeningReportParam.getReportJson())) {
            JSONObject updateJsonObject = JSON.parseObject(updateScreeningReportParam.getReportJson());
            if (updateJsonObject != null) {
                if (!StringUtils.isBlank(updateJsonObject.getString("qrCodeData"))) {
                    jsonObject.put("qrCodeData", updateJsonObject.getString("qrCodeData"));
                }
            }
        }
        updateScreeningReportParam.setReportJson(jsonObject.toJSONString());
    }

    @Override
    public ScreeningReportPO getScreeningReportById(String reportId) {
        return getScreeningReportById(reportId, 1);
    }

    @Override
    public ScreeningReportPO getScreeningReportById(String reportId, Integer valid) {
        return this.screeningReportMapper.getScreeningReportById(reportId, null, null, valid);
    }

    @Override
    public PageResult<ScreeningListPO> listScreening(PageRequest pr, ListScreeningDTO listScreeningParam) {
        if (!StringUtils.isBlank(listScreeningParam.getScreeningStatus())) {
            List<String> stringList = Arrays.asList(listScreeningParam.getScreeningStatus().split(","));
            List<Integer> screeningStatusList = new ArrayList<>();
            stringList.forEach(x -> {
                screeningStatusList.add(Integer.parseInt(x));
            });
            listScreeningParam.setScreeningStatusList(screeningStatusList);
        }

        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<ScreeningListPO> list = this.screeningMapper.listScreening(listScreeningParam);
        if (null != list && list.size() > 0) {
            //患者信息脱敏
            for (ScreeningListPO po : list) {
                po.setIdCard(ValidateTool.tuoMin(po.getIdCard(), 4, 4, "*"));
                po.setMobilePhone(ValidateTool.tuoMin(po.getMobilePhone(), 3, 4, "*"));
            }
        }
        return new PageResult<>(list);
    }

    @Override
    public ScreeningStatVO statScreening(DoctorSessionBO doctorSessionBO) {

        List<Integer> screeningStatusList = new ArrayList<>();
        //今日待筛查
        CountScreeningDTO countScreeningDTO = new CountScreeningDTO();
        countScreeningDTO.setOrderStatus(ScreeningConstant.ORDER_STATUS_YES);
        countScreeningDTO.setOrderStartDt(DateHelper.getToday());
        countScreeningDTO.setOrderEndDt(DateHelper.getToday());
        screeningStatusList.add(ScreeningConstant.SCREENING_STATUS_NOT_START);
        screeningStatusList.add(ScreeningConstant.SCREENING_STATUS_STARTING);
        countScreeningDTO.setScreeningStatusList(screeningStatusList);
        countScreeningDTO.setHospitalId(doctorSessionBO.getHospitalId());
        long todayPreNumber = this.screeningMapper.countScreening(countScreeningDTO);

        //已预约
        countScreeningDTO = new CountScreeningDTO();
        countScreeningDTO.setOrderStatus(ScreeningConstant.ORDER_STATUS_YES);
        countScreeningDTO.setOrderStartDt(DateHelper.getToday());
        countScreeningDTO.setOrderEndDt(DateHelper.getToday());
        countScreeningDTO.setScreeningStatusList(screeningStatusList);
        countScreeningDTO.setHospitalId(doctorSessionBO.getHospitalId());
        long orderNumber = this.screeningMapper.countScreening(countScreeningDTO);

        //已筛查
        countScreeningDTO = new CountScreeningDTO();
        countScreeningDTO.setScreeningStatus(ScreeningConstant.SCREENING_STATUS_FINISH);
        countScreeningDTO.setHospitalId(doctorSessionBO.getHospitalId());
        long finishScreeningNumber = this.screeningMapper.countScreening(countScreeningDTO);

        //逾期
        countScreeningDTO = new CountScreeningDTO();
        countScreeningDTO.setScreeningStatus(ScreeningConstant.SCREENING_STATUS_NOT_START);
        countScreeningDTO.setOrderStatus(ScreeningConstant.ORDER_STATUS_YES);
        countScreeningDTO.setOrderEndDt(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_DATE));
        countScreeningDTO.setHospitalId(doctorSessionBO.getHospitalId());
        long overdueNumber = this.screeningMapper.countScreening(countScreeningDTO);


        //全部
        countScreeningDTO = new CountScreeningDTO();
        screeningStatusList.add(ScreeningConstant.SCREENING_STATUS_NOT_START);
        screeningStatusList.add(ScreeningConstant.SCREENING_STATUS_STARTING);
        countScreeningDTO.setScreeningStatusList(screeningStatusList);
        countScreeningDTO.setHospitalId(doctorSessionBO.getHospitalId());
        long allNumber = this.screeningMapper.countScreening(countScreeningDTO);


        ScreeningStatVO screeningStatVO = new ScreeningStatVO();
        screeningStatVO.setTodayPreNumber(todayPreNumber);
        screeningStatVO.setOrderNumber(orderNumber);
        screeningStatVO.setFinishScreeningNumber(finishScreeningNumber);
        screeningStatVO.setOverdueNumber(overdueNumber);
        screeningStatVO.setAllNumber(allNumber);
        return screeningStatVO;
    }

    @Override
    public ScreeningStatOrderVO statOrderScreening(DoctorSessionBO doctorSessionBO) {
        List<String> memberIdList = this.memberService.listDoctorMonitorMemberId(doctorSessionBO);

        //今日预约
        CountScreeningDTO countScreeningParam = new CountScreeningDTO();
        String today = DateHelper.getToday();
        countScreeningParam.setOrderStatus(ScreeningConstant.ORDER_STATUS_YES);
        countScreeningParam.setOrderStartDt(today);
        countScreeningParam.setOrderEndDt(today);
        List<Integer> screeningStatusList = new ArrayList<>();
        screeningStatusList.add(ScreeningConstant.SCREENING_STATUS_NOT_START);
        screeningStatusList.add(ScreeningConstant.SCREENING_STATUS_STARTING);
        countScreeningParam.setScreeningStatusList(screeningStatusList);
        countScreeningParam.setMemberIdList(memberIdList);
        long todayNumber = this.screeningMapper.countScreening(countScreeningParam);

        //一周内预约
        String orderStartDt = DateHelper.getAddDateFormat(Calendar.DAY_OF_WEEK, -6, "yyyy-MM-dd");
        countScreeningParam.setOrderStartDt(orderStartDt);
        countScreeningParam.setOrderEndDt(today);
        countScreeningParam.setMemberIdList(memberIdList);
        long weekNumber = this.screeningMapper.countScreening(countScreeningParam);

        //更多预约
        orderStartDt = DateHelper.getAddDateFormat(Calendar.DAY_OF_WEEK, 1, "yyyy-MM-dd");
        String orderEndDt = DateHelper.getAddDateFormat(Calendar.DAY_OF_WEEK, -7, "yyyy-MM-dd");
        countScreeningParam.setOrderStartDt(null);
        countScreeningParam.setOrderEndDt(null);
        countScreeningParam.setOrderStartOrDt(orderStartDt);
        countScreeningParam.setOrderEndOrDt(orderEndDt);
        countScreeningParam.setMemberIdList(memberIdList);
        long otherNumber = this.screeningMapper.countScreening(countScreeningParam);

        ScreeningStatOrderVO screeningStatOrderVO = new ScreeningStatOrderVO();
        screeningStatOrderVO.setOtherOrderNumber(otherNumber);
        screeningStatOrderVO.setTodayOrderNumber(todayNumber);
        screeningStatOrderVO.setWeekOrderNumber(weekNumber);
        return screeningStatOrderVO;
    }

    @Override
    public ScreeningStatFinishVO statFinishScreening(DoctorSessionBO doctorSessionBO) {
        //今日预约
        CountScreeningDTO countScreeningParam = new CountScreeningDTO();
        String today = DateHelper.getToday();
        countScreeningParam.setScreeningStatus(ScreeningConstant.SCREENING_STATUS_FINISH);
        countScreeningParam.setFinishStartDt(today + " 00:00:00");
        countScreeningParam.setFinishEndDt(today + " 23:59:59");
        countScreeningParam.setHospitalId(doctorSessionBO.getHospitalId());
        long todayNumber = this.screeningMapper.countScreening(countScreeningParam);


        //一周内预约
        String orderStartDt = DateHelper.getAddDateFormat(Calendar.DAY_OF_WEEK, -6, "yyyy-MM-dd");
        countScreeningParam.setFinishStartDt(orderStartDt + " 00:00:00");
        countScreeningParam.setFinishEndDt(today + " 23:59:59");
        countScreeningParam.setHospitalId(doctorSessionBO.getHospitalId());
        long weekNumber = this.screeningMapper.countScreening(countScreeningParam);

        //更多预约
        orderStartDt = DateHelper.getAddDateFormat(Calendar.DAY_OF_WEEK, 1, "yyyy-MM-dd");
        String orderEndDt = DateHelper.getAddDateFormat(Calendar.DAY_OF_WEEK, -7, "yyyy-MM-dd");

        countScreeningParam = new CountScreeningDTO();
        countScreeningParam.setScreeningStatus(ScreeningConstant.SCREENING_STATUS_FINISH);
        countScreeningParam.setFinishStartOrDt(orderStartDt + " 00:00:00");
        countScreeningParam.setFinishEndOrDt(orderEndDt + " 23:59:59");
        countScreeningParam.setHospitalId(doctorSessionBO.getHospitalId());
        long otherNumber = this.screeningMapper.countScreening(countScreeningParam);

        ScreeningStatFinishVO screeningStatFinishVO = new ScreeningStatFinishVO();
        screeningStatFinishVO.setOtherFinishNumber(otherNumber);
        screeningStatFinishVO.setTodayFinishNumber(todayNumber);
        screeningStatFinishVO.setWeekFinishNumber(weekNumber);
        return screeningStatFinishVO;
    }

    @Override
    public ScreeningReportPO getScreeningReport(String screeningId, Integer screeningType) {
        ScreeningReportPO po = getScreeningReport(screeningId, screeningType, null);
/*        if(po!=null&&!StringUtils.isBlank(Constant.UPLOAD_SERVER_HOST)&&
                !StringUtils.isBlank(po.getCutImageUrl()) && po.getCutImageUrl().indexOf("http")<0&&
                !StringUtils.isBlank(po.getImageUrl())&& po.getImageUrl().indexOf("http")<0){
            po.setCutImageUrl(Constant.UPLOAD_SERVER_HOST+po.getCutImageUrl());
            po.setImageUrl(Constant.UPLOAD_SERVER_HOST+po.getImageUrl());
        }*/
        return po;
    }

    @Override
    public ScreeningReportPO getScreeningReport(String screeningId, Integer screeningType, String reportId) {
        return this.screeningReportMapper.getScreeningReportById(reportId, screeningId, screeningType, 1);
    }

    @Override
    public void updateScreeningReport(UpdateScreeningReportParam updateScreeningReportParam) {
        ScreeningReportPO screeningReportPO = getScreeningReport(updateScreeningReportParam.getScreeningId()
                , updateScreeningReportParam.getScreeningType(), null);
        if (screeningReportPO == null) {
            throw new BusinessException("报告不存在/已删除");
        }
        updateScreeningReportParam.setReportId(screeningReportPO.getReportId());
        reportJsonUpdateHandler(screeningReportPO, updateScreeningReportParam);
        //打印次数处理
        if (updateScreeningReportParam.getIncrPrintTime() != null) {
            screeningReportPO.setPrintTime(screeningReportPO.getPrintTime() + updateScreeningReportParam.getIncrPrintTime());
        }
        this.screeningReportMapper.updateScreeningReport(screeningReportPO);
    }

    @Override
    public List<ScreeningReportPO> listScreeningReportOfTypeNearlySixMonth(String memberId, String hospitalId) {
        String startDt = DateHelper.plusDate(DateHelper.getNowDate(), 2, -6, DateHelper.DAY_FORMAT);
        return this.screeningReportMapper.listScreeningReportOfTypeNearlySixMonth(memberId, hospitalId, startDt);
    }

    @Override
    public ScreeningReportPO getLastScreeningReport(String memberId, Integer screeningType, String doctorId, String unScreeningId) {
        GetLastScreeningReportDTO param = new GetLastScreeningReportDTO();
        param.setMemberId(memberId);
        param.setDoctorId(doctorId);
        param.setScreeningType(screeningType);
        param.setUnScreeningId(unScreeningId);
        return getLastScreeningReport(param);
    }

    @Override
    public PageResult<ScreeningListPO> listScreeningReturnVisitRemind(int page, int rows) {
        //27天前完成的筛查报告需要复诊提醒
        String finishDt = LocalDateTime.now().minusDays(27).format(DateTimeFormatter.ISO_DATE);
        PageHelper.startPage(page, rows);
        List<ScreeningListPO> listPOS = this.screeningMapper.listScreeningReturnVisitRemind(finishDt);
        return new PageResult<>(listPOS);
    }

    @Override
    public PageResult<ScreeningListPO> listLastWeekScreening(int page, int rows) {
        String startDt = LocalDateTime.now().minusWeeks(1).withHour(0).withMinute(0).withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String endDt = LocalDateTime.now().minusDays(1).withHour(23).withMinute(59).withSecond(59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        PageHelper.startPage(page, rows);
        List<ScreeningListPO> listPOS = this.screeningMapper.listLastWeekScreening(startDt, endDt);
        return new PageResult<>(listPOS);
    }

    @Override
    public List<ScreeningReportPO> listScreeningReport(String screeningId) {
        return this.screeningReportMapper.listScreeningReport(screeningId);
    }

    @Override
    public PageResult<ScreeningListPO> listMemberScreening(PageRequest pr, String memberId) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<ScreeningListPO> list = this.screeningMapper.listMemberScreening(memberId);
        return new PageResult<>(list);
    }

    @Override
    public PageResult<ScreeningDataPO> listScreeningData(PageRequest pr, ListScreeningDataDTO listScreeningDataDTO) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<ScreeningDataPO> list = this.screeningDataMapper.listScreeningData(listScreeningDataDTO);
        return new PageResult<>(list);
    }

    @Override
    public void addScreeningData(ScreeningDataPO screeningDataPO) {
        this.screeningDataMapper.addScreeningData(screeningDataPO);
    }

    @Override
    public PageResult<ScreeningListPO> listScreeningOrderRemindPatient(int page, int rows) {
        String date = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_DATE);
        PageHelper.startPage(page, rows);
        List<ScreeningListPO> screeningListPOS = this.screeningMapper.listScreeningOrderRemindPatient(date);
        return new PageResult<>(screeningListPOS);
    }

    @Override
    public PageResult<ScreeningReportPO> listMemberScreeningReport(String memberId, PageRequest pr) {
        ListMemberScreeningReportDTO dto = new ListMemberScreeningReportDTO();
        dto.setMemberId(memberId);
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<ScreeningReportPO> list = this.screeningReportMapper.listMemberScreeningReport(dto);
        return new PageResult<>(list);
    }

    @Override
    public ScreeningReportPO getLastScreeningReport(String memberId, Integer screeningType, String doctorId) {
        return getLastScreeningReport(memberId, screeningType, doctorId, null);
    }

    @Override
    public void deleteScreening(String screeningId) {
        ScreeningListPO screeningPO = getScreeningById(screeningId);
        if (screeningPO == null) {
            throw new BusinessException("该筛查记录不存在，请确认");
        }
        if (ScreeningConstant.SCREENING_STATUS_NOT_START != screeningPO.getScreeningStatus()) {
            throw new BusinessException("筛查已开始，无法删除");
        }
        this.screeningMapper.deleteScreening(screeningId);
    }

    @Override
    public JSONObject listPatientScreeningReport(String memberId, String doctorId) {
        ListMemberScreeningReportDTO dto = new ListMemberScreeningReportDTO();
        dto.setMemberId(memberId);
        dto.setScreeningType(ScreeningConstant.SCREENING_TYPE_ABI);
        List<ScreeningReportPO> abiReport = this.screeningReportMapper.listMemberScreeningReport(dto);
        dto.setScreeningType(ScreeningConstant.SCREENING_TYPE_VPT);
        List<ScreeningReportPO> vptReport = this.screeningReportMapper.listMemberScreeningReport(dto);
        dto.setScreeningType(ScreeningConstant.SCREENING_TYPE_EYES);
        List<ScreeningReportPO> eyesReport = this.screeningReportMapper.listMemberScreeningReport(dto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("abiReport", abiReport);
        jsonObject.put("vptReport", vptReport);
        jsonObject.put("eyesReport", eyesReport);
        return jsonObject;
    }

    @Override
    public void deleteMemberScreening(String memberId, String doctorId) {
        this.screeningMapper.deleteScreeningList(memberId, doctorId);
        this.screeningDataMapper.deleteScreeningData(memberId, doctorId);
    }

    @Override
    public Map<String, Object> countNewScreening(SynthesizeDataDTO synthesizeDataDTO) {
        return this.screeningMapper.countNewScreening(synthesizeDataDTO);
    }

    @Override
    public Map<String, Object> countScreeningPeople(SynthesizeDataDTO synthesizeDataDTO) {
        return this.screeningMapper.countScreeningPeople(synthesizeDataDTO);
    }

    @Override
    public ScreeningDataPO getScreening(SynthesizeDataDTO synthesizeDataDTO) {
        ScreeningDataPO po = new ScreeningDataPO();
        List<ScreeningDataPO> list = this.screeningMapper.getABI(synthesizeDataDTO);
        if (list != null && list.size() > 0) {
            po.setLeftAbi(list.get(0).getLeftAbi());
            po.setRightAbi(list.get(0).getRightAbi());
        }
        List<ScreeningDataPO> list1 = this.screeningMapper.getVPT(synthesizeDataDTO);
        if (list1 != null && list1.size() > 0) {
            po.setLeftVpt(list1.get(0).getLeftVpt());
            po.setRightVpt(list1.get(0).getRightVpt());
        }
        return po;
    }

    @Override
    public Map<String, Object> listScreeningReportNearlySixMonths(String memberId, String hospitalId) {
        String startDt = DateHelper.plusDate(DateHelper.getNowDate(), 2, -6, DateHelper.DAY_FORMAT);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<ScreeningReportPO> intelligentResultModels = this.screeningReportMapper.listScreeningReportOfTypeNearlySixMonth(memberId, hospitalId, startDt);
        for (ScreeningReportPO model : intelligentResultModels) {
            String reslutJson = model.getDataJson();
            if (!StringUtils.isBlank(reslutJson)) {
                JSONObject jsonObject = JSONObject.parseObject(reslutJson);
                if (ScreeningConstant.SCREENING_TYPE_ABI == model.getScreeningType()) {
                    String docMsg = null;
                    String reportJson = model.getReportJson();
                    if (!StringUtils.isBlank(reportJson)) {
                        JSONObject reportObject = JSONObject.parseObject(reportJson);
                        if (!StringUtils.isBlank(reportObject.getString("doctorMsg"))) {
                            docMsg = reportObject.getString("doctorMsg");
                        }
                    }
                    resultMap.put("abi_screeningId", model.getScreeningId());
                    resultMap.put("abi_reportDt", model.getReportDt());
                    String examinationText = jsonObject.getString("examinationText");
                    //abi 左脚
                    String lVal = jsonObject.getString("leftAbi");
                    //jsonObject.getString("tibialPostLeftIndex");
                    if (!StringUtils.isBlank(lVal)) {
                        Double l = Double.parseDouble(lVal);
                        if (l >= 0.00 && l <= 0.40) {
                            resultMap.put("follow_abi_l_M", "1");
                        } else if (l >= 0.41 && l <= 0.70) {
                            resultMap.put("follow_abi_l_M", "2");
                        } else if (l >= 0.71 && l <= 0.90) {
                            resultMap.put("follow_abi_l_M", "3");
                        } else if (l >= 0.91 && l <= 1.31) {
                            resultMap.put("follow_abi_l_M", "4");
                        } else if (l >= 1.31) {
                            resultMap.put("follow_abi_l_M", "5");
                        }
                        if (StringUtils.isBlank(docMsg)) {
                            StringBuffer sb = new StringBuffer();
                            sb.append("左侧下肢ABI指数为：" + lVal).append("，");
                            if (l <= 0.4) {
                                sb.append("左侧下肢动脉足部大血管、微血管严重病变").append("\n");
                            } else if (l > 0.4 && l <= 0.9) {
                                sb.append("左侧下肢动脉轻度到中度供血不足").append("\n");
                            } else if (l > 0.9 && l <= 0.99) {
                                sb.append("左侧足部供血异常").append("\n");
                            } else if (l > 0.99 && l <= 1.29) {
                                sb.append("左侧足部供血正常").append("\n");
                            } else if (l > 1.29) {
                                sb.append("左侧下肢动脉存在中层钙化的情况").append("\n");
                            }
                            resultMap.put("follow_abi_l_S", sb.toString());
                        }
                    }
                    if (!StringUtils.isBlank(docMsg)) {
                        String[] docMsgArr = docMsg.split("\n");
                        if (docMsgArr != null && docMsgArr.length > 0 && !StringUtils.isBlank(docMsgArr[0])) {
                            resultMap.put("follow_abi_l_S", docMsgArr[0].replace("患者", ""));
                        } else {
                            resultMap.put("follow_abi_l_S", docMsg);
                        }
                    }
                    //abi 右脚
                    String rVal = jsonObject.getString("rightAbi");
                    //jsonObject.getString("tibialPostRightIndex");
                    if (!StringUtils.isBlank(rVal)) {
                        Double r = Double.parseDouble(rVal);
                        if (r >= 0.00 && r <= 0.40) {
                            resultMap.put("follow_abi_M", "1");
                        } else if (r >= 0.41 && r <= 0.70) {
                            resultMap.put("follow_abi_M", "2");
                        } else if (r >= 0.71 && r <= 0.90) {
                            resultMap.put("follow_abi_M", "3");
                        } else if (r >= 0.91 && r <= 1.31) {
                            resultMap.put("follow_abi_M", "4");
                        } else if (r >= 1.31) {
                            resultMap.put("follow_abi_M", "5");
                        }
                        if (StringUtils.isBlank(docMsg)) {
                            StringBuffer sb = new StringBuffer();
                            sb.append("右侧下肢ABI指数为：" + rVal).append("，");
                            if (r <= 0.4) {
                                sb.append("右侧下肢动脉足部大血管、微血管严重病变");
                            } else if (r > 0.4 && r <= 0.9) {
                                sb.append("右侧下肢动脉轻度到中度供血不足");
                            } else if (r > 0.9 && r <= 0.99) {
                                sb.append("右侧足部供血异常");
                            } else if (r > 0.99 && r <= 1.29) {
                                sb.append("右侧足部供血正常");
                            } else {
                                sb.append("右侧下肢动脉存在中层钙化的情况");
                            }
                            resultMap.put("follow_abi_S", sb.toString());
                        }
                    }
                    if (!StringUtils.isBlank(docMsg)) {
                        String[] docMsgArr = docMsg.split("\n");
                        if (docMsgArr != null && docMsgArr.length > 1 && !StringUtils.isBlank(docMsgArr[1])) {
                            resultMap.put("follow_abi_S", docMsgArr[1].replace("患者", ""));
                        } else {
                            resultMap.put("follow_abi_S", docMsg);
                        }
                    }
                } else if (ScreeningConstant.SCREENING_TYPE_VPT == model.getScreeningType()) {
                    resultMap.put("vpt_screeningId", model.getScreeningId());
                    resultMap.put("vpt_reportDt", model.getReportDt());
                    JSONArray jsonArray = jsonObject.getJSONArray("listAcpr");
                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object != null) {
                                String name = object.getString("name");
                                if (!StringUtils.isBlank(name)) {
                                    if (name.equals("左脚第一足趾")) {
                                        //感觉阈值 左脚第一足趾
                                        String lVal1 = object.getString("vpt");
                                        if (!StringUtils.isBlank(lVal1)) {
                                            Double l = Double.parseDouble(lVal1);
                                            if (l >= 0.00 && l <= 15.00) {
                                                resultMap.put("follow_vpt_l_M", "1");
                                            } else if (l >= 16.00 && l <= 24.00) {
                                                resultMap.put("follow_vpt_l_M", "2");
                                            } else {
                                                resultMap.put("follow_vpt_l_M", "3");
                                            }
                                            resultMap.put("follow_vpt_l_S", name + "VPT：" + lVal1 + "(volt)");
                                        }
                                    } else if (name.equals("右脚第一足趾")) {
                                        //感觉阈值 右脚第一足趾
                                        String rVal1 = object.getString("vpt");
                                        if (!StringUtils.isBlank(rVal1)) {
                                            Double r = Double.parseDouble(rVal1);
                                            if (r >= 0.00 && r <= 15.00) {
                                                resultMap.put("follow_vpt_M", "1");
                                            } else if (r >= 16.00 && r <= 24.00) {
                                                resultMap.put("follow_vpt_M", "2");
                                            } else {
                                                resultMap.put("follow_vpt_M", "3");
                                            }
                                            resultMap.put("follow_vpt_S", name + "VPT：" + rVal1 + "(volt)");
                                        }
                                    } else if (name.equals("左足背")) {
                                        //感觉阈值 左足背
                                        String lValb = object.getString("vpt");
                                        if (!StringUtils.isBlank(lValb)) {
                                            Double l = Double.parseDouble(lValb);
                                            if (l >= 0.00 && l <= 15.00) {
                                                resultMap.put("follow_vpt_zb_l_E", "1");
                                            } else if (l >= 16.00 && l <= 24.00) {
                                                resultMap.put("follow_vpt_zb_l_E", "2");
                                            } else {
                                                resultMap.put("follow_vpt_zb_l_E", "3");
                                            }
                                        }
                                    } else if (name.equals("右足背")) {
                                        //感觉阈值 右足背
                                        String rValb = object.getString("vpt");
                                        if (!StringUtils.isBlank(rValb)) {
                                            Double r = Double.parseDouble(rValb);
                                            if (r >= 0.00 && r <= 15.00) {
                                                resultMap.put("follow_vpt_zb_r_E", "1");
                                            } else if (r >= 16.00 && r <= 24.00) {
                                                resultMap.put("follow_vpt_zb_r_E", "2");
                                            } else {
                                                resultMap.put("follow_vpt_zb_r_E", "3");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    String nlszjqsL = jsonObject.getString("nlszjqsL");
                    if (!StringUtils.isBlank(nlszjqsL)) {
                        if ("0".equals(nlszjqsL)) {
                            resultMap.put("follow_nylon_pressure_feel_l_M", "1");
                        } else if ("1".equals(nlszjqsL) || "2".equals(nlszjqsL)) {
                            resultMap.put("follow_nylon_pressure_feel_l_M", "2");
                        } else if ("3".equals(nlszjqsL)) {
                            resultMap.put("follow_nylon_pressure_feel_l_M", "3");
                        }
                    }
                    String nlszjqsR = jsonObject.getString("nlszjqsR");
                    if (!StringUtils.isBlank(nlszjqsR)) {
                        if ("0".equals(nlszjqsR)) {
                            resultMap.put("follow_nylon_pressure_feel_M", "1");
                        } else if ("1".equals(nlszjqsR) || "2".equals(nlszjqsR)) {
                            resultMap.put("follow_nylon_pressure_feel_M", "2");
                        } else if ("3".equals(nlszjqsR)) {
                            resultMap.put("follow_nylon_pressure_feel_M", "3");
                        }
                    }
                    String bwgjL = jsonObject.getString("bwgjL");
                    if (!StringUtils.isBlank(bwgjL)) {
                        if ("1".equals(bwgjL)) {
                            resultMap.put("follow_thermesthesia_l_M", "1");
                        } else if ("2".equals(bwgjL)) {
                            resultMap.put("follow_thermesthesia_l_M", "2");
                        } else if ("3".equals(bwgjL)) {
                            resultMap.put("follow_thermesthesia_l_M", "2");
                        }
                    }
                    String bwgjR = jsonObject.getString("bwgjR");
                    if (!StringUtils.isBlank(bwgjR)) {
                        if ("1".equals(bwgjR)) {
                            resultMap.put("follow_thermesthesia_M", "1");
                        } else if ("2".equals(bwgjR)) {
                            resultMap.put("follow_thermesthesia_M", "2");
                        } else if ("3".equals(bwgjR)) {
                            resultMap.put("follow_thermesthesia_M", "2");
                        }
                    }
                    String zcttgjL = jsonObject.getString("zcttgjL");
                    if (!StringUtils.isBlank(zcttgjL)) {
                        if ("1".equals(zcttgjL)) {
                            resultMap.put("follow_needle_position_sense_l_M", "1");
                        } else if ("2".equals(zcttgjL)) {
                            resultMap.put("follow_needle_position_sense_l_M", "2");
                        } else if ("3".equals(zcttgjL)) {
                            resultMap.put("follow_needle_position_sense_l_M", "2");
                        }
                    }
                    String zcttgjR = jsonObject.getString("zcttgjR");
                    if (!StringUtils.isBlank(zcttgjR)) {
                        if ("1".equals(zcttgjR)) {
                            resultMap.put("follow_needle_position_sense_M", "1");
                        } else if ("2".equals(zcttgjR)) {
                            resultMap.put("follow_needle_position_sense_M", "2");
                        } else if ("3".equals(zcttgjR)) {
                            resultMap.put("follow_needle_position_sense_M", "2");
                        }
                    }
                    String gjfsL = jsonObject.getString("gjfsL");
                    if (!StringUtils.isBlank(gjfsL)) {
                        if ("1".equals(gjfsL)) {
                            resultMap.put("follow_ankle_reflex_l_M", "1");
                        } else if ("2".equals(gjfsL)) {
                            resultMap.put("follow_ankle_reflex_l_M", "2");
                        } else if ("3".equals(gjfsL)) {
                            resultMap.put("follow_ankle_reflex_l_M", "2");
                        }
                    }
                    String gjfsR = jsonObject.getString("gjfsR");
                    if (!StringUtils.isBlank(gjfsR)) {
                        if ("1".equals(gjfsR)) {
                            resultMap.put("follow_ankle_reflex_M", "1");
                        } else if ("2".equals(gjfsR)) {
                            resultMap.put("follow_ankle_reflex_M", "2");
                        } else if ("3".equals(gjfsR)) {
                            resultMap.put("follow_ankle_reflex_M", "2");
                        }
                    }
                    String zkyL = jsonObject.getString("zkyL");
                    if (!StringUtils.isBlank(zkyL)) {
                        if ("1".equals(zkyL)) {
                            resultMap.put("follow_foot_ulcer_his_l_M", "1");
                        } else if ("2".equals(zkyL)) {
                            resultMap.put("follow_foot_ulcer_his_l_M", "2");
                        }
                    }
                    String zkyR = jsonObject.getString("zkyR");
                    if (!StringUtils.isBlank(zkyR)) {
                        if ("1".equals(zkyR)) {
                            resultMap.put("follow_foot_ulcer_his_M", "1");
                        } else if ("2".equals(zkyR)) {
                            resultMap.put("follow_foot_ulcer_his_M", "2");
                        }
                    }
                } else if (ScreeningConstant.SCREENING_TYPE_EYES == model.getScreeningType()) {
                    resultMap.put("eye_screeningId", model.getScreeningId());
                    resultMap.put("eye_reportDt", model.getReportDt());
                    JSONObject reportObj = jsonObject.getJSONObject("report");
                    if (reportObj != null) {
                        /*List<Map<String,String>> leyeCheckArr = IntelligentDeviceServiceImpl.getEyeCheckArr(reportObj.getJSONArray("leyeCheck"));
                        List<Map<String,String>> reyeCheckArr = IntelligentDeviceServiceImpl.getEyeCheckArr(reportObj.getJSONArray("reyeCheck"));

                        resultMap.put("leyeCheckArr", leyeCheckArr);
                        resultMap.put("reyeCheckArr", reyeCheckArr);

                        JSONObject simpleResultObj = jsonObject.getJSONObject("simpleResult");
                        if(simpleResultObj != null) {
                            simpleResultObj.put("lDR_result_str", IntelligentDeviceServiceImpl.getDRResult(simpleResultObj.getString("lDR_result")));
                            simpleResultObj.put("rDR_result_str", IntelligentDeviceServiceImpl.getDRResult(simpleResultObj.getString("rDR_result")));
                        }*/

                        if (jsonObject.getJSONObject("report") != null) {
                            //构造初步诊断
                            JSONArray alphaResults = jsonObject.getJSONObject("report").getJSONArray("alphaResults");
                            if (alphaResults != null) {
                                resultMap.put("eye_LAlphaResultStr", this.getLAlphaResultStr(alphaResults));
                                resultMap.put("eye_RAlphaResultStr", this.getRAlphaResultStr(alphaResults));
                            }
                        }
                        resultMap.put("eye_suggest", reportObj.getString("suggest"));
                    }
                } else if (ScreeningConstant.SCREENING_TYPE_ACR == model.getScreeningType()) {
                    resultMap.put("acr_screeningId", model.getScreeningId());
                    resultMap.put("acr_reportDt", model.getReportDt());
                    String acr = jsonObject.getString("ACR");
                    resultMap.put("follow_urinary_albumin_creatinine_ratio_M", acr);
                    String creat = jsonObject.getString("Creat");
                    resultMap.put("follow_urine_creatinine_M", creat);
                    String alb = jsonObject.getString("Alb");
                    resultMap.put("follow_urine_alb_M", alb);
                } else if (ScreeningConstant.SCREENING_TYPE_HBA1C == model.getScreeningType()) {
                    resultMap.put("hba1c_screeningId", model.getScreeningId());
                    resultMap.put("hba1c_reportDt", model.getReportDt());
                    String HbA1c = jsonObject.getString("HbA1c");
                    resultMap.put("follow_hba1c_M", HbA1c);
                } else if (ScreeningConstant.SCREENING_TYPE_SIGN == model.getScreeningType()) {
                    resultMap.put("sign_screeningId", model.getScreeningId());
                    resultMap.put("sign_reportDt", model.getReportDt());
                    JSONObject BP = jsonObject.getJSONObject("BP");
                    if (BP != null) {
                        String dbp = BP.getString("dbp");
                        String sbp = BP.getString("sbp");
                        resultMap.put("blood_pressure", sbp + "\\" + dbp);
                        String hr = BP.getString("hr");
                        resultMap.put("follow_mem_pr_M", hr);
                    }
                }
            }
        }
        return resultMap;
    }

    @Override
    public List<ScreeningReportPO> listIntelligentResultOfThStatistics(GetStatisticsDTO dto) {
        return this.screeningReportMapper.listIntelligentResultOfThStatistics(dto);
    }

    @Override
    public Long getScreeningOfMemberCount(GetStatisticsDTO dto) {
        return this.screeningReportMapper.getScreeningOfMemberCount(dto);
    }

    @Override
    public void startScreening(String screeningId, String doctorId) {
        ScreeningListPO screeningPO = this.screeningMapper.getScreeningById(screeningId);
        if (screeningPO == null) {
            throw new BusinessException("筛查单记录不存在");
        }
        //不是未开始的筛查单，直接返回
        if (ScreeningConstant.SCREENING_STATUS_NOT_START != screeningPO.getScreeningStatus()) {
            throw new BusinessException("本筛查单不可开始");
        }
        ScreeningListPO update = new ScreeningListPO();
        update.setScreeningId(screeningId);
        update.setScreeningStatus(ScreeningConstant.SCREENING_STATUS_STARTING);
        this.screeningMapper.updateScreeningList(update);
        List<String> list = Arrays.asList(screeningPO.getModules().split(","));
        list.forEach(x -> {
            ScreeningItemPO screeningPrePO = new ScreeningItemPO();
            screeningPrePO.setItemId(DaoHelper.getSeq());
            screeningPrePO.setScreeningId(screeningId);
            screeningPrePO.setScreeningType(Integer.parseInt(x));
            screeningPrePO.setMemberId(screeningPO.getMemberId());
            screeningPrePO.setDoctorId(doctorId);
            this.screeningMapper.addScreeningPre(screeningPrePO);
        });
    }

    @Override
    public void orderScreening(String screeningId, String orderDate, String orderTime
            , Integer orderTimeCode, String mobilePhone) {
        ScreeningListPO screeningPO = getScreeningById(screeningId);
        if (screeningPO == null) {
            throw new BusinessException("筛查单记录不存在");
        }
        ScreeningListPO update = new ScreeningListPO();
        update.setScreeningId(screeningId);
        update.setOrderDate(orderDate);
        update.setOrderTime(orderTime);
        update.setOrderTimeCode(orderTimeCode);
        update.setOrderStatus(ScreeningConstant.ORDER_STATUS_YES);
        update.setMobilePhone(mobilePhone);
        this.screeningMapper.updateScreeningList(update);
    }

    @Override
    public void cancelScreeningItem(String screeningId, Integer screeningType, String stopReason, String operatorName) {
        ScreeningItemPO param = new ScreeningItemPO();
        param.setScreeningId(screeningId);
        param.setScreeningType(screeningType);
        ScreeningItemPO screeningPrePO = this.screeningMapper.getScreeningPre(param);
        if (screeningPrePO != null) {
            if (ScreeningConstant.PRE_DEAL_STATUS_REPORT_OK == screeningPrePO.getDealStatus().intValue()) {
                throw new BusinessException("该筛查已经完成，无法结束");
            }
            ScreeningItemPO update = new ScreeningItemPO();
            update.setItemId(screeningPrePO.getItemId());
            update.setDealStatus(ScreeningConstant.PRE_DEAL_STATUS_REPORT_CANCEL);
            this.screeningMapper.updateScreenPre(update);
        }
        cancelScreeningItemScreeningHandler(screeningId, screeningType, stopReason, operatorName);
    }

    @Override
    public ScreeningListVO getScreeningVOById(String screeningId) {
        ScreeningListPO po = this.getScreeningById(screeningId);
        ScreeningListVO vo = new ScreeningListVO();
        BeanUtils.copyProperties(vo, po);
        String memberId = po.getMemberId();
        MemberPO member = this.memberService.getMemberById(memberId);
        if (member != null) {
            vo.setHeight(member.getHeight());
            vo.setWeight(member.getWeight());
        }
        return vo;
    }

    /**
     * 取消筛查项后筛查单处理
     *
     * @param screeningId
     * @param screeningType
     */
    private void cancelScreeningItemScreeningHandler(String screeningId, Integer screeningType, String stopReason, String operatorName) {
        ScreeningListPO screeningPO = getScreeningById(screeningId);
        if (screeningPO == null) {
            return;
        }
        ScreeningListPO update = new ScreeningListPO();
        //筛查单情况快照处理
        JSONObject jsonObject = JSON.parseObject(screeningPO.getModulesStatus());
        jsonObject.put(String.valueOf(screeningType), ScreeningConstant.PRE_DEAL_STATUS_REPORT_CANCEL);
        update.setScreeningId(screeningPO.getScreeningId());
        update.setModulesStatus(jsonObject.toJSONString());
        //手动结束项处理
        JSONObject stopItemJson;
        String stopItemJsonString = screeningPO.getStopItemJson();
        if (StringUtils.isBlank(stopItemJsonString)) {
            stopItemJson = new JSONObject();
        } else {
            stopItemJson = JSON.parseObject(stopItemJsonString);
        }
        stopItemJson.put(String.valueOf(screeningType), stopItemHandler(stopReason, operatorName));
        update.setStopItemJson(stopItemJson.toJSONString());

        if (checkScreeningFinish(jsonObject)) {
            update.setScreeningStatus(ScreeningConstant.SCREENING_STATUS_FINISH);
            update.setFinishDt(DateHelper.getTime());
        }
        if (checkScreeningCancel(jsonObject)) {
            update.setScreeningStatus(ScreeningConstant.SCREENING_STATUS_CANCEL);
            update.setFinishDt(DateHelper.getTime());
        }
        this.screeningMapper.updateScreeningList(update);
    }

    /**
     * 判断是否完成筛查
     *
     * @param jsonObject
     * @return
     */
    private boolean checkScreeningFinish(JSONObject jsonObject) {
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            int status = Integer.parseInt(entry.getValue().toString());
            if(entry.getKey().equals("1") || entry.getKey().equals("2") || entry.getKey().equals("3") ||  entry.getKey().equals("4")){
                if (ScreeningConstant.PRE_DEAL_STATUS_REPORT_CONFIRM != status ){
                    return false;
                }
            }else if (ScreeningConstant.PRE_DEAL_STATUS_REPORT_OK != status) {
                return false;
            }
        }
        return true;
    }
    private boolean checkScreeningCancel(JSONObject jsonObject) {
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            int status = Integer.parseInt(entry.getValue().toString());
            if ( ScreeningConstant.PRE_DEAL_STATUS_REPORT_CANCEL != status) {
                return false;
            }
        }
        return true;
    }
    /**
     * 结束项json处理
     *
     * @param stopReason
     * @param operatorName
     * @return
     */
    private JSONObject stopItemHandler(String stopReason, String operatorName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stopReason", stopReason);
        jsonObject.put("operatorName", operatorName);
        jsonObject.put("operatorDate", DateHelper.getTime());
        return jsonObject;
    }

    /**
     * 左眼初步诊断
     *
     * @param alphaResults
     * @return
     */
    private String getLAlphaResultStr(JSONArray alphaResults) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < alphaResults.size(); i++) {
            JSONObject alphaResultObj = alphaResults.getJSONObject(i);
            String disease = alphaResultObj.getString("disease");
            String consult = alphaResultObj.getString("consult");

            if ("l_r_eye".equals(alphaResultObj.getString("bodyPartEntry"))) {
                if (sb.length() > 0) {
                    sb.append("，");
                }
                if ("正常".equals(disease)) {
                    sb.append("未见明显异常");
                } else if ("待查".equals(disease) || "未见明显异常".equals(disease)) {
                    sb.append(disease);
                } else {
                    sb.append(disease).append("（").append(consult).append("）");
                }
            } else if ("l_eye".equals(alphaResultObj.getString("bodyPartEntry"))) {
                if (sb.length() > 0) {
                    sb.append("，");
                }
                if ("正常".equals(disease)) {
                    sb.append("未见明显异常");
                } else if ("待查".equals(disease) || "未见明显异常".equals(disease)) {
                    sb.append(disease);
                } else {
                    sb.append(disease).append("（").append(consult).append("）");
                }
            }
        }

        if (sb.length() == 0) {
            sb.append("未见明显异常");
        }

        return sb.toString();
    }

    /**
     * 右眼初步诊断
     *
     * @param alphaResults
     * @return
     */
    private String getRAlphaResultStr(JSONArray alphaResults) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < alphaResults.size(); i++) {
            JSONObject alphaResultObj = alphaResults.getJSONObject(i);
            String disease = alphaResultObj.getString("disease");
            String consult = alphaResultObj.getString("consult");

            if ("l_r_eye".equals(alphaResultObj.getString("bodyPartEntry"))) {
                if (sb.length() > 0) {
                    sb.append("，");
                }
                if ("正常".equals(disease)) {
                    sb.append("未见明显异常");
                } else if ("待查".equals(disease) || "未见明显异常".equals(disease)) {
                    sb.append(disease);
                } else {
                    sb.append(disease).append("（").append(consult).append("）");
                }
            } else if ("r_eye".equals(alphaResultObj.getString("bodyPartEntry"))) {
                if (sb.length() > 0) {
                    sb.append("，");
                }
                if ("正常".equals(disease)) {
                    sb.append("未见明显异常");
                } else if ("待查".equals(disease) || "未见明显异常".equals(disease)) {
                    sb.append(disease);
                } else {
                    sb.append(disease).append("（").append(consult).append("）");
                }
            }
        }

        if (sb.length() == 0) {
            sb.append("未见明显异常");
        }

        return sb.toString();
    }


    @Override
    public List<Map<String, Object>> listReportNearlySixMonths(String memberId, String hospitalId) {
        String startDt = DateHelper.plusDate(DateHelper.getNowDate(), 2, -6, DateHelper.DAY_FORMAT);
        List<Map<String, Object>> list = new ArrayList<>();
        List<ScreeningReportPO> intelligentResultModels = this.screeningReportMapper.listScreeningReportOfTypeNearlySixMonth(memberId, hospitalId, startDt);
        for (ScreeningReportPO model : intelligentResultModels) {
            String reslutJson = model.getDataJson();
            if (!StringUtils.isBlank(reslutJson)) {
                JSONObject jsonObject = JSONObject.parseObject(reslutJson);
                if (ScreeningConstant.SCREENING_TYPE_ABI == model.getScreeningType()) {
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("name", "ABI筛查");
                    String docMsg = null;
                    String reportJson = model.getReportJson();
                    if (!StringUtils.isBlank(reportJson)) {
                        JSONObject reportObject = JSONObject.parseObject(reportJson);
                        if (!StringUtils.isBlank(reportObject.getString("doctorMsg"))) {
                            docMsg = reportObject.getString("doctorMsg");
                        }
                    }
                    resultMap.put("abi_screeningId", model.getScreeningId());
                    resultMap.put("abi_reportDt", model.getReportDt());
                    String examinationText = jsonObject.getString("examinationText");
                    //abi 左脚
                    String lVal = jsonObject.getString("leftAbi");
                    //jsonObject.getString("tibialPostLeftIndex");
                    if (!StringUtils.isBlank(lVal)) {
                        Double l = Double.parseDouble(lVal);
                        if (l >= 0.00 && l <= 0.40) {
                            resultMap.put("follow_abi_l_M", "1");
                        } else if (l >= 0.41 && l <= 0.70) {
                            resultMap.put("follow_abi_l_M", "2");
                        } else if (l >= 0.71 && l <= 0.90) {
                            resultMap.put("follow_abi_l_M", "3");
                        } else if (l >= 0.91 && l <= 1.31) {
                            resultMap.put("follow_abi_l_M", "4");
                        } else if (l >= 1.31) {
                            resultMap.put("follow_abi_l_M", "5");
                        }
                        if (StringUtils.isBlank(docMsg)) {
                            StringBuffer sb = new StringBuffer();
                            sb.append("左侧下肢ABI指数为：" + lVal).append("，");
                            if (l <= 0.4) {
                                sb.append("左侧下肢动脉足部大血管、微血管严重病变").append("\n");
                            } else if (l > 0.4 && l <= 0.9) {
                                sb.append("左侧下肢动脉轻度到中度供血不足").append("\n");
                            } else if (l > 0.9 && l <= 0.99) {
                                sb.append("左侧足部供血异常").append("\n");
                            } else if (l > 0.99 && l <= 1.29) {
                                sb.append("左侧足部供血正常").append("\n");
                            } else if (l > 1.29) {
                                sb.append("左侧下肢动脉存在中层钙化的情况").append("\n");
                            }
                            resultMap.put("follow_abi_l_S", sb.toString());
                        }
                    }
                    if (!StringUtils.isBlank(docMsg)) {
                        String[] docMsgArr = docMsg.split("\n");
                        if (docMsgArr != null && docMsgArr.length > 0 && !StringUtils.isBlank(docMsgArr[0])) {
                            resultMap.put("follow_abi_l_S", docMsgArr[0].replace("患者", ""));
                        } else {
                            resultMap.put("follow_abi_l_S", docMsg);
                        }
                    }
                    //abi 右脚
                    String rVal = jsonObject.getString("rightAbi");
                    //jsonObject.getString("tibialPostRightIndex");
                    if (!StringUtils.isBlank(rVal)) {
                        Double r = Double.parseDouble(rVal);
                        if (r >= 0.00 && r <= 0.40) {
                            resultMap.put("follow_abi_M", "1");
                        } else if (r >= 0.41 && r <= 0.70) {
                            resultMap.put("follow_abi_M", "2");
                        } else if (r >= 0.71 && r <= 0.90) {
                            resultMap.put("follow_abi_M", "3");
                        } else if (r >= 0.91 && r <= 1.31) {
                            resultMap.put("follow_abi_M", "4");
                        } else if (r >= 1.31) {
                            resultMap.put("follow_abi_M", "5");
                        }
                        if (StringUtils.isBlank(docMsg)) {
                            StringBuffer sb = new StringBuffer();
                            sb.append("右侧下肢ABI指数为：" + rVal).append("，");
                            if (r <= 0.4) {
                                sb.append("右侧下肢动脉足部大血管、微血管严重病变");
                            } else if (r > 0.4 && r <= 0.9) {
                                sb.append("右侧下肢动脉轻度到中度供血不足");
                            } else if (r > 0.9 && r <= 0.99) {
                                sb.append("右侧足部供血异常");
                            } else if (r > 0.99 && r <= 1.29) {
                                sb.append("右侧足部供血正常");
                            } else {
                                sb.append("右侧下肢动脉存在中层钙化的情况");
                            }
                            resultMap.put("follow_abi_S", sb.toString());
                        }
                    }
                    if (!StringUtils.isBlank(docMsg)) {
                        String[] docMsgArr = docMsg.split("\n");
                        if (docMsgArr != null && docMsgArr.length > 1 && !StringUtils.isBlank(docMsgArr[1])) {
                            resultMap.put("follow_abi_S", docMsgArr[1].replace("患者", ""));
                        } else {
                            resultMap.put("follow_abi_S", docMsg);
                        }
                    }
                    list.add(resultMap);
                } else if (ScreeningConstant.SCREENING_TYPE_VPT == model.getScreeningType()) {
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("name", "感觉阈值筛查");
                    resultMap.put("vpt_screeningId", model.getScreeningId());
                    resultMap.put("vpt_reportDt", model.getReportDt());
                    JSONArray jsonArray = jsonObject.getJSONArray("listAcpr");
                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object != null) {
                                String name = object.getString("name");
                                if (!StringUtils.isBlank(name)) {
                                    if (name.equals("左脚第一足趾")) {
                                        //感觉阈值 左脚第一足趾
                                        String lVal1 = object.getString("vpt");
                                        if (!StringUtils.isBlank(lVal1)) {
                                            Double l = Double.parseDouble(lVal1);
                                            if (l >= 0.00 && l <= 15.00) {
                                                resultMap.put("follow_vpt_l_M", "1");
                                            } else if (l >= 16.00 && l <= 24.00) {
                                                resultMap.put("follow_vpt_l_M", "2");
                                            } else {
                                                resultMap.put("follow_vpt_l_M", "3");
                                            }
                                            resultMap.put("follow_vpt_l_S", name + "VPT：" + lVal1 + "(volt)");
                                        }
                                    } else if (name.equals("右脚第一足趾")) {
                                        //感觉阈值 右脚第一足趾
                                        String rVal1 = object.getString("vpt");
                                        if (!StringUtils.isBlank(rVal1)) {
                                            Double r = Double.parseDouble(rVal1);
                                            if (r >= 0.00 && r <= 15.00) {
                                                resultMap.put("follow_vpt_M", "1");
                                            } else if (r >= 16.00 && r <= 24.00) {
                                                resultMap.put("follow_vpt_M", "2");
                                            } else {
                                                resultMap.put("follow_vpt_M", "3");
                                            }
                                            resultMap.put("follow_vpt_S", name + "VPT：" + rVal1 + "(volt)");
                                        }
                                    } else if (name.equals("左足背")) {
                                        //感觉阈值 左足背
                                        String lValb = object.getString("vpt");
                                        if (!StringUtils.isBlank(lValb)) {
                                            Double l = Double.parseDouble(lValb);
                                            if (l >= 0.00 && l <= 15.00) {
                                                resultMap.put("follow_vpt_zb_l_E", "1");
                                            } else if (l >= 16.00 && l <= 24.00) {
                                                resultMap.put("follow_vpt_zb_l_E", "2");
                                            } else {
                                                resultMap.put("follow_vpt_zb_l_E", "3");
                                            }
                                        }
                                    } else if (name.equals("右足背")) {
                                        //感觉阈值 右足背
                                        String rValb = object.getString("vpt");
                                        if (!StringUtils.isBlank(rValb)) {
                                            Double r = Double.parseDouble(rValb);
                                            if (r >= 0.00 && r <= 15.00) {
                                                resultMap.put("follow_vpt_zb_r_E", "1");
                                            } else if (r >= 16.00 && r <= 24.00) {
                                                resultMap.put("follow_vpt_zb_r_E", "2");
                                            } else {
                                                resultMap.put("follow_vpt_zb_r_E", "3");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    String nlszjqsL = jsonObject.getString("nlszjqsL");
                    if (!StringUtils.isBlank(nlszjqsL)) {
                        if ("0".equals(nlszjqsL)) {
                            resultMap.put("follow_nylon_pressure_feel_l_M", "1");
                        } else if ("1".equals(nlszjqsL) || "2".equals(nlszjqsL)) {
                            resultMap.put("follow_nylon_pressure_feel_l_M", "2");
                        } else if ("3".equals(nlszjqsL)) {
                            resultMap.put("follow_nylon_pressure_feel_l_M", "3");
                        }
                    }
                    String nlszjqsR = jsonObject.getString("nlszjqsR");
                    if (!StringUtils.isBlank(nlszjqsR)) {
                        if ("0".equals(nlszjqsR)) {
                            resultMap.put("follow_nylon_pressure_feel_M", "1");
                        } else if ("1".equals(nlszjqsR) || "2".equals(nlszjqsR)) {
                            resultMap.put("follow_nylon_pressure_feel_M", "2");
                        } else if ("3".equals(nlszjqsR)) {
                            resultMap.put("follow_nylon_pressure_feel_M", "3");
                        }
                    }
                    String bwgjL = jsonObject.getString("bwgjL");
                    if (!StringUtils.isBlank(bwgjL)) {
                        if ("1".equals(bwgjL)) {
                            resultMap.put("follow_thermesthesia_l_M", "1");
                        } else if ("2".equals(bwgjL)) {
                            resultMap.put("follow_thermesthesia_l_M", "2");
                        } else if ("3".equals(bwgjL)) {
                            resultMap.put("follow_thermesthesia_l_M", "2");
                        }
                    }
                    String bwgjR = jsonObject.getString("bwgjR");
                    if (!StringUtils.isBlank(bwgjR)) {
                        if ("1".equals(bwgjR)) {
                            resultMap.put("follow_thermesthesia_M", "1");
                        } else if ("2".equals(bwgjR)) {
                            resultMap.put("follow_thermesthesia_M", "2");
                        } else if ("3".equals(bwgjR)) {
                            resultMap.put("follow_thermesthesia_M", "2");
                        }
                    }
                    String zcttgjL = jsonObject.getString("zcttgjL");
                    if (!StringUtils.isBlank(zcttgjL)) {
                        if ("1".equals(zcttgjL)) {
                            resultMap.put("follow_needle_position_sense_l_M", "1");
                        } else if ("2".equals(zcttgjL)) {
                            resultMap.put("follow_needle_position_sense_l_M", "2");
                        } else if ("3".equals(zcttgjL)) {
                            resultMap.put("follow_needle_position_sense_l_M", "2");
                        }
                    }
                    String zcttgjR = jsonObject.getString("zcttgjR");
                    if (!StringUtils.isBlank(zcttgjR)) {
                        if ("1".equals(zcttgjR)) {
                            resultMap.put("follow_needle_position_sense_M", "1");
                        } else if ("2".equals(zcttgjR)) {
                            resultMap.put("follow_needle_position_sense_M", "2");
                        } else if ("3".equals(zcttgjR)) {
                            resultMap.put("follow_needle_position_sense_M", "2");
                        }
                    }
                    String gjfsL = jsonObject.getString("gjfsL");
                    if (!StringUtils.isBlank(gjfsL)) {
                        if ("1".equals(gjfsL)) {
                            resultMap.put("follow_ankle_reflex_l_M", "1");
                        } else if ("2".equals(gjfsL)) {
                            resultMap.put("follow_ankle_reflex_l_M", "2");
                        } else if ("3".equals(gjfsL)) {
                            resultMap.put("follow_ankle_reflex_l_M", "2");
                        }
                    }
                    String gjfsR = jsonObject.getString("gjfsR");
                    if (!StringUtils.isBlank(gjfsR)) {
                        if ("1".equals(gjfsR)) {
                            resultMap.put("follow_ankle_reflex_M", "1");
                        } else if ("2".equals(gjfsR)) {
                            resultMap.put("follow_ankle_reflex_M", "2");
                        } else if ("3".equals(gjfsR)) {
                            resultMap.put("follow_ankle_reflex_M", "2");
                        }
                    }
                    String zkyL = jsonObject.getString("zkyL");
                    if (!StringUtils.isBlank(zkyL)) {
                        if ("1".equals(zkyL)) {
                            resultMap.put("follow_foot_ulcer_his_l_M", "1");
                        } else if ("2".equals(zkyL)) {
                            resultMap.put("follow_foot_ulcer_his_l_M", "2");
                        }
                    }
                    String zkyR = jsonObject.getString("zkyR");
                    if (!StringUtils.isBlank(zkyR)) {
                        if ("1".equals(zkyR)) {
                            resultMap.put("follow_foot_ulcer_his_M", "1");
                        } else if ("2".equals(zkyR)) {
                            resultMap.put("follow_foot_ulcer_his_M", "2");
                        }
                    }
                    list.add(resultMap);
                } else if (ScreeningConstant.SCREENING_TYPE_EYES == model.getScreeningType()) {
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("name", "眼底筛查");
                    resultMap.put("eye_screeningId", model.getScreeningId());
                    resultMap.put("eye_reportDt", model.getReportDt());
                    JSONObject reportObj = jsonObject.getJSONObject("report");
                    if (reportObj != null) {
                        /*List<Map<String,String>> leyeCheckArr = IntelligentDeviceServiceImpl.getEyeCheckArr(reportObj.getJSONArray("leyeCheck"));
                        List<Map<String,String>> reyeCheckArr = IntelligentDeviceServiceImpl.getEyeCheckArr(reportObj.getJSONArray("reyeCheck"));

                        resultMap.put("leyeCheckArr", leyeCheckArr);
                        resultMap.put("reyeCheckArr", reyeCheckArr);

                        JSONObject simpleResultObj = jsonObject.getJSONObject("simpleResult");
                        if(simpleResultObj != null) {
                            simpleResultObj.put("lDR_result_str", IntelligentDeviceServiceImpl.getDRResult(simpleResultObj.getString("lDR_result")));
                            simpleResultObj.put("rDR_result_str", IntelligentDeviceServiceImpl.getDRResult(simpleResultObj.getString("rDR_result")));
                        }*/

                        if (jsonObject.getJSONObject("report") != null) {
                            //构造初步诊断
                            JSONArray alphaResults = jsonObject.getJSONObject("report").getJSONArray("alphaResults");
                            if (alphaResults != null) {
                                resultMap.put("eye_LAlphaResultStr", this.getLAlphaResultStr(alphaResults));
                                resultMap.put("eye_RAlphaResultStr", this.getRAlphaResultStr(alphaResults));
                            }
                        }
                        resultMap.put("eye_suggest", reportObj.getString("suggest"));
                    }
                    list.add(resultMap);
                } else if (ScreeningConstant.SCREENING_TYPE_ACR == model.getScreeningType()) {
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("name", "ACR筛查");
                    resultMap.put("acr_screeningId", model.getScreeningId());
                    resultMap.put("acr_reportDt", model.getReportDt());
                    String acr = jsonObject.getString("ACR");
                    resultMap.put("follow_urinary_albumin_creatinine_ratio_M", acr);
                    String creat = jsonObject.getString("Creat");
                    resultMap.put("follow_urine_creatinine_M", creat);
                    String alb = jsonObject.getString("Alb");
                    resultMap.put("follow_urine_alb_M", alb);
                    list.add(resultMap);
                } else if (ScreeningConstant.SCREENING_TYPE_HBA1C == model.getScreeningType()) {
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("name", "HbA1c检测");
                    resultMap.put("hba1c_screeningId", model.getScreeningId());
                    resultMap.put("hba1c_reportDt", model.getReportDt());
                    String HbA1c = jsonObject.getString("HbA1c");
                    resultMap.put("follow_hba1c_M", HbA1c);
                    list.add(resultMap);
                } else if (ScreeningConstant.SCREENING_TYPE_SIGN == model.getScreeningType()) {
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("name", "全身脂肪分布检测");
                    resultMap.put("sign_screeningId", model.getScreeningId());
                    resultMap.put("sign_reportDt", model.getReportDt());
                    JSONObject BP = jsonObject.getJSONObject("BP");
                    if (BP != null) {
                        String dbp = BP.getString("dbp");
                        String sbp = BP.getString("sbp");
                        resultMap.put("blood_pressure", sbp + "\\" + dbp);
                        String hr = BP.getString("hr");
                        resultMap.put("follow_mem_pr_M", hr);
                    }
                    list.add(resultMap);
                }
            }
        }
        return list;
    }

    @Override
    public void addOrUpdateScreening(ScreeningListPO add) {
        this.screeningMapper.addOrUpdateScreening(add);
    }


    @Override
    public ScreeningItemPO getScreeningPre(String preId, String screeningId, Integer screeningType) {
        ScreeningItemPO po = new ScreeningItemPO();
        po.setItemId(preId);
        po.setScreeningId(screeningId);
        po.setScreeningType(screeningType);
        return this.screeningMapper.getScreeningPre(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateScreenPre(ScreeningItemPO screeningPrePO) {
        ScreeningItemPO prePO = this.screeningMapper.getScreeningPre(screeningPrePO);
        if (prePO == null) {
            logger.warn("无法根据待筛查申请单号找到对应的筛查申请记录，待筛查申请单号:{}", screeningPrePO.getItemId());
            throw new BusinessException("待筛查单记录不存在或已被删除");
        }
        if (ScreeningConstant.PRE_DEAL_STATUS_REPORT_OK == prePO.getDealStatus()) {
            logger.info("待筛查申请记录不处于需要修改的状态，本次修改操作忽略。待筛查申请单号:{},当前状态:{}", prePO.getItemId(), prePO.getDealStatus());
            return;
        }
        this.screeningMapper.updateScreenPre(screeningPrePO);
        logger.info("执行待筛查申请数据修改，修改数据内容:{}", JSON.toJSONString(screeningPrePO));
        if (screeningPrePO.getDealStatus() != null) {
            ScreeningListPO screeningPO = getScreeningById(prePO.getScreeningId());
            if (screeningPO == null) {
                throw new BusinessException("筛查单记录不存在或已被删除");
            }
            if (ScreeningConstant.SCREENING_STATUS_FINISH == screeningPO.getScreeningStatus()) {
                logger.info("筛查已完成，不做任何修改，筛查单号:{}", screeningPO.getScreeningId());
                return;
            }
            JSONObject jsonObject = JSONObject.parseObject(screeningPO.getModulesStatus());
            jsonObject.put(prePO.getScreeningType().toString(), screeningPrePO.getDealStatus());
            logger.info("筛查单号:{}，准备进行筛查项状态数据修改，筛查项状态数据:{}", screeningPO.getScreeningId(), jsonObject.toJSONString());
            ScreeningListPO update = new ScreeningListPO();
            update.setScreeningId(screeningPO.getScreeningId());
            update.setModulesStatus(jsonObject.toJSONString());
            if (checkScreeningFinish(jsonObject)) {
                logger.info("筛查单中的各项筛查都已完成，本次筛查完成，筛查单号:{},筛查项数据:{}", screeningPO.getScreeningId(), jsonObject.toJSONString());
                update.setScreeningStatus(ScreeningConstant.SCREENING_STATUS_FINISH);
                update.setFinishDt(DateHelper.getTime());
            }
            if (screeningPrePO.getDealStatus() == ScreeningConstant.PRE_DEAL_STATUS_REPORT_OK) {
                addScreeningData(screeningPO, prePO);
            }
            this.screeningMapper.updateScreeningList(update);
        }
    }


    /**
     * 添加筛查结果数据
     *
     * @param screeningPO
     */
    private void addScreeningData(ScreeningListPO screeningPO, ScreeningItemPO prePO) {
        ScreeningDataPO screeningDataPO = this.screeningDataMapper.getScreeningDataById(screeningPO.getScreeningId());
        if (screeningDataPO == null) {
            screeningDataPO = new ScreeningDataPO();
            BeanUtils.copyProperties(screeningDataPO, screeningPO);
            screeningDataPO.setAge(DateHelper.getAge(screeningPO.getBirthday()));
            screeningDataPO.setScreeningDt(DateHelper.getToday());
            screeningDataHandler(screeningDataPO, prePO.getScreeningType(), screeningPO.getScreeningId());
            this.screeningDataMapper.addScreeningData(screeningDataPO);
        } else {
            screeningDataHandler(screeningDataPO, prePO.getScreeningType(), screeningPO.getScreeningId());
            this.screeningDataMapper.updateScreeningData(screeningDataPO);
        }

    }

    /**
     * 统计数据处理
     *
     * @param screeningDataPO
     * @param screeningType
     * @param screeningId
     */
    private void screeningDataHandler(ScreeningDataPO screeningDataPO, Integer screeningType, String screeningId) {
        //abi处理
        if (ScreeningConstant.SCREENING_TYPE_ABI == screeningType) {
            ScreeningReportPO screeningReportPO = this.screeningReportMapper.getScreeningReportById(null, screeningId, ScreeningConstant.SCREENING_TYPE_ABI, 1);
            if (screeningReportPO != null) {
                JSONObject dataJson = JSON.parseObject(screeningReportPO.getReportJson());
                screeningDataPO.setLeftAbi(dataJson.getString("leftAbi"));
                screeningDataPO.setRightAbi(dataJson.getString("rightAbi"));
                screeningDataPO.setAbiDataJson(screeningReportPO.getDataJson());
            }
        }
        //vpt处理
        if (ScreeningConstant.SCREENING_TYPE_VPT == screeningType) {
            ScreeningReportPO screeningReportPO = this.screeningReportMapper.getScreeningReportById(null, screeningId, ScreeningConstant.SCREENING_TYPE_VPT, 1);
            if (screeningReportPO != null) {
                JSONObject dataJson = JSON.parseObject(screeningReportPO.getReportJson());
                screeningDataPO.setLeftVpt(dataJson.getString("leftVpt"));
                screeningDataPO.setRightVpt(dataJson.getString("rightVpt"));
                screeningDataPO.setVptDataJson(screeningReportPO.getDataJson());
            }
        }
    }


    @Override
    public void eyesReadingFilm(EyesReadingFilmParam eyesReadingFilmParam) {
        ScreeningReportPO screeningReportPO = getScreeningReportById(eyesReadingFilmParam.getReportId());
        if (screeningReportPO == null) {
            throw new BusinessException("眼底报告不存在或已被删除");
        }
        ScreeningReportPO po = new ScreeningReportPO();
        po.setReportId(eyesReadingFilmParam.getReportId());
        po.setEditStatus(ScreeningConstant.REPORT_EDIT_YES);
        po.setReportJson(eyesReportJsonHandler(eyesReadingFilmParam));
        if(judgeHasretinopathy(eyesReadingFilmParam.getLeftEyesResult()) || judgeHasretinopathy(eyesReadingFilmParam.getRightEyesResult())){
            po.setResultStatus(ScreeningConstant.REPORT_STATUS_BAD);
        }
        this.screeningReportMapper.updateScreeningReport(po);
        //统计数据
//        eyesScreeningStatsHandler(screeningReportPO.getIdCard() , eyesReadingFilmParam.getDoctorId(),eyesReadingFilmParam.getLeftEyesResult() ,eyesReadingFilmParam.getRightEyesResult());

    }


    /**
     * 眼底报告阅片结果处理
     *
     * @param eyesReadingFilmParam
     * @return
     */
    private String eyesReportJsonHandler(EyesReadingFilmParam eyesReadingFilmParam) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("advices", eyesReadingFilmParam.getAdvices());
        jsonObject.put("describe", eyesReadingFilmParam.getDescribe());
        if (eyesReadingFilmParam.getLeftEyesResult() != null) {
            jsonObject.put("leftEyesResult", eyesReadingFilmParam.getLeftEyesResult());
        }
        if (eyesReadingFilmParam.getRightEyesResult() != null) {
            jsonObject.put("rightEyesResult", eyesReadingFilmParam.getRightEyesResult());
        }
        return jsonObject.toJSONString();
    }

    /**
     * 眼底筛查数据统计处理
     *
     * @param idCard
     * @param leftEyesResult
     * @param rightEyesResult
     */
    private void eyesScreeningStatsHandler(String idCard, String doctorId, Integer leftEyesResult, Integer rightEyesResult) {
        retinopathyStatsHandler(idCard, doctorId, leftEyesResult, rightEyesResult);
        eyeLevelStatsHandler(idCard, doctorId, leftEyesResult, rightEyesResult);
    }

    /**
     * 糖尿病视网膜病变 统计处理
     *
     * @param idCard
     * @param leftEyesResult
     * @param rightEyesResult
     */
    private void retinopathyStatsHandler(String idCard, String doctorId, Integer leftEyesResult, Integer rightEyesResult) {
        String itemValue = "";
        if (judgeHasretinopathy(leftEyesResult) || judgeHasretinopathy(rightEyesResult)) {
            itemValue = "1";
        } else {
            itemValue = "0";
        }
        ScreeningStatsPO po = new ScreeningStatsPO();
        po.setIdCard(idCard);
        po.setItemCode(StatsConstant.STATS_ITEM_CODE_RETINOPATHY);
        po.setItemValue(itemValue);
        po.setInsertDt(DateHelper.getNowDate());
        po.setUpdateDt(DateHelper.getNowDate());
        po.setValid(1);
        MemberPO memberPO = memberService.getMemberByIdCard(po.getIdCard());
        po.setMemberId(memberPO.getMemberId());
        po.setDoctorId(doctorId);
        this.statsService.addScreeningStats(po);
    }

    /**
     * 判断是否有糖尿病视网膜病变
     *
     * @param eyeResult
     * @return
     */
    private boolean judgeHasretinopathy(Integer eyeResult) {
        if (eyeResult == null) {
            return false;
        }
        if (eyeResult > EyesConstant.EYE_LEVEL_1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 糖尿病视网膜病变分级 统计处理
     *
     * @param idCard
     * @param leftEyesResult
     * @param rightEyesResult
     */
    private void eyeLevelStatsHandler(String idCard, String doctorId, Integer leftEyesResult, Integer rightEyesResult) {
        String itemValue = "";
        Integer eyesResult = null;
        if (leftEyesResult != null && rightEyesResult != null) {
            if (leftEyesResult == 0 || rightEyesResult == 0) {
                eyesResult = 0;
            } else if (leftEyesResult > rightEyesResult) {
                eyesResult = leftEyesResult;
            } else {
                eyesResult = rightEyesResult;
            }
        } else if (leftEyesResult != null) {
            eyesResult = leftEyesResult;
        } else if (rightEyesResult != null) {
            eyesResult = rightEyesResult;
        }
        if (eyesResult == null) {
            return;
        }
        if (eyesResult == 0) {
            //无法判断
            itemValue = "4";
        } else if (eyesResult == 1) {
            //无明显视网膜病变
            itemValue = "1";
        } else if (eyesResult == 5) {
            //增殖期视网膜病变
            itemValue = "3";
        } else {
            //非增殖期视网膜病变
            itemValue = "2";
        }
        ScreeningStatsPO po = new ScreeningStatsPO();
        po.setIdCard(idCard);
        po.setItemCode(StatsConstant.STATS_ITEM_CODE_EYE_LEVEL);
        po.setItemValue(itemValue);
        po.setInsertDt(DateHelper.getNowDate());
        po.setUpdateDt(DateHelper.getNowDate());
        po.setValid(1);
        MemberPO memberPO = memberService.getMemberByIdCard(po.getIdCard());
        po.setMemberId(memberPO.getMemberId());
        po.setDoctorId(doctorId);
        this.statsService.addScreeningStats(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void inputACRReport(ACRDataInputDto dto) {
        ScreeningReportPO screeningReportPO = getScreeningReport(dto.getScreeningId()
                , ScreeningConstant.SCREENING_TYPE_ACR, null);
        if (screeningReportPO != null) {
            throw new BusinessException("报告已存在");
        }
        JSONObject data = createAcrData(dto.getAcr(), dto.getAlb(), dto.getCreat());
        insertReportHandler(dto.getScreeningId(), ScreeningConstant.SCREENING_TYPE_ACR, data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void inputHba1cReport(HbA1cDataInputDto dto) {
        ScreeningReportPO screeningReportPO = getScreeningReport(dto.getScreeningId()
                , ScreeningConstant.SCREENING_TYPE_HBA1C, null);
        if (screeningReportPO != null) {
            throw new BusinessException("报告已存在");
        }
        JSONObject data = createHba1cData(dto.getHbA1c());
        insertReportHandler(dto.getScreeningId(), ScreeningConstant.SCREENING_TYPE_HBA1C, data);
    }

    @Override
    public PageResult<ScreeningReportPO> listMemberScreeningReport(ListMemberScreeningReportDTO dto, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<ScreeningReportPO> list = this.screeningReportMapper.listMemberScreeningReport(dto);
        return new PageResult<>(list);
    }

    private JSONObject createAcrData(double arc, double alb, double creat) {
        JSONObject data = new JSONObject();
        data.put("date", DateHelper.getToday());
        data.put("time", DateHelper.getDate(new Date(), "HH:mm"));
        data.put("pid", "");
        data.put("deviceSN", "");
        data.put("acrThen", "");
        data.put("albThen", "");
        data.put("creatThen", "");
        data.put("then", "");
        data.put("acrUnit", "mg/g");
        data.put("albUnit", "mg/L");
        data.put("creatUnit", "mg/dL");
        data.put("ACR", "" + arc);
        data.put("Alb", "" + arc);
        data.put("Creat", "" + arc);
        data.put("type", "4");
        return data;
    }

    private JSONObject createHba1cData(double HbA1c) {
        JSONObject obj = new JSONObject();
        obj.put("pid", "");
        obj.put("type", "5");
        obj.put("deviceSN", "");
        obj.put("HbA1c", HbA1c);
        obj.put("HbA1cUnit", "%");
        return obj;
    }

    private ScreeningReportPO createReport(ScreeningListPO screeningPO, int screeningType) {
        ScreeningReportPO reportPO = new ScreeningReportPO();
        reportPO.setReportId(DaoHelper.getSeq());
        reportPO.setScreeningId(screeningPO.getScreeningId());
        reportPO.setScreeningType(screeningType);
        reportPO.setMemberId(screeningPO.getMemberId());
        reportPO.setDoctorId(screeningPO.getDoctorId());
        reportPO.setValid(1);
        reportPO.setPrintTime(0);
        reportPO.setReportDt(DateHelper.getNowDate());
        reportPO.setEditStatus(0);
        reportPO.setResultStatus(1);
        return reportPO;
    }

    private void insertReportHandler(String screeningId, int screeningType, JSONObject data) {

        ScreeningListPO screeningPO = getScreeningById(screeningId);
        if (screeningPO == null) {
            throw new BusinessException("筛查单记录不存在或已被删除");
        }

        ScreeningReportPO reportPO = createReport(screeningPO, screeningType);
        reportPO.setDataJson(JSON.toJSONString(data));
        reportPO.setReportJson(JSON.toJSONString(data));
        addScreeningReport(reportPO);

        ScreeningItemPO screeningPrePO = getScreeningPre(null, screeningId, screeningType);
        if (screeningPrePO == null) {
            throw new BusinessException("筛查申请不存在或已被删除");
        }
        screeningPrePO.setDealStatus(ScreeningConstant.PRE_DEAL_STATUS_REPORT_OK);
        updateScreenPre(screeningPrePO);

        JSONObject obj = JSON.parseObject(screeningPO.getModulesStatus());
        if (obj.containsKey("" + screeningType)) {
            obj.put("" + screeningType, ScreeningConstant.PRE_DEAL_STATUS_REPORT_OK);
            screeningPO.setModulesStatus(JSON.toJSONString(obj));
            updateScreeningList(screeningPO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmScreening(String screeningId, int screeningType ,DoctorSessionBO doctorSession) {
        String doctorId = doctorSession.getDoctorId();
        ScreeningListPO screeningPO = getScreeningById(screeningId);
        if (screeningPO == null) {
            throw new BusinessException("筛查单记录不存在或已被删除");
        }
        ScreeningItemPO screeningItemPO = getScreeningPre(null, screeningId, screeningType);
        if (screeningItemPO == null) {
            throw new BusinessException("筛查申请不存在或已被删除");
        }
        if (ScreeningConstant.PRE_DEAL_STATUS_REPORT_OK != screeningItemPO.getDealStatus()) {
            return;
        }

        ScreeningReportPO reportPO = getScreeningReport(screeningId, screeningType, null);
        if (reportPO == null) {
            throw new BusinessException("未生成筛查报告");
        }
        screeningItemPO.setDealStatus(ScreeningConstant.PRE_DEAL_STATUS_REPORT_CONFIRM);
        this.screeningMapper.updateScreenPre(screeningItemPO);
        JSONObject obj = JSON.parseObject(screeningPO.getModulesStatus());
        if(obj == null){
            obj = new JSONObject();
        }
        if (obj.containsKey("" + screeningType)) {
            obj.put("" + screeningType, ScreeningConstant.PRE_DEAL_STATUS_REPORT_CONFIRM);
            screeningPO.setModulesStatus(JSON.toJSONString(obj));
            updateScreeningList(screeningPO);
        }
        if (checkScreeningFinish(obj)) {
            screeningPO.setScreeningStatus(ScreeningConstant.SCREENING_STATUS_FINISH);
            screeningPO.setFinishDt(DateHelper.getTime());
        }
        addScreeningReportToInspection(reportPO ,screeningPO ,doctorId);
        updateScreeningList(screeningPO);
        addScreeningReportWechatMessage(reportPO);
        memberVisitEventHandler(doctorSession,screeningPO.getMemberId(),screeningId,screeningType);
        addDoctorHealthWarningNotice(reportPO);
    }

    @Override
    public ScreeningReportPO getLastScreeningReport(GetLastScreeningReportDTO param) {
        return this.screeningReportMapper.getLastScreeningReport(param);
    }

    /**
     * 添加筛查报告微信消息
     * @param screeningReport
     */
    private void addScreeningReportWechatMessage(ScreeningReportPO screeningReport){
        JSONObject dataJson = new JSONObject();
        dataJson.put("screeningId" ,screeningReport.getScreeningId());
        dataJson.put("screeningType" ,screeningReport.getScreeningType());
        dataJson.put("reportDt" ,screeningReport.getReportDt());
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(screeningReport.getMemberId());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_SCREENING_REPORT);
        addWechatMessageDTO.setForeignId(screeningReport.getReportId());
        addWechatMessageDTO.setDataJson(dataJson.toJSONString());
        addWechatMessageDTO.setUserType(WechatMessageConstant.USER_TYPE_PATIENT);
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 添加筛查报告到检查模块
     * @param screeningReport
     * @param screeningList
     */
    private void addScreeningReportToInspection(ScreeningReportPO screeningReport ,ScreeningListPO screeningList ,String doctorId){
        InspectionDict inspectionDict = SCREENING_INSPECTION_MAP.get(screeningReport.getScreeningType());
        if(inspectionDict == null){
            return;
        }
        DoctorPO doctor = this.doctorService.getDoctorById(doctorId);
        AddInspectionDTO addInspectionDTO = new AddInspectionDTO();
        addInspectionDTO.setInspectTitle(inspectionDict.getName());
        addInspectionDTO.setInspectId(screeningReport.getReportId());
        addInspectionDTO.setInspectDoctorId(doctor.getDoctorId());
        addInspectionDTO.setInspectDoctorName(doctor.getDoctorName());
        addInspectionDTO.setInspectDt(screeningReport.getReportDt());
        addInspectionDTO.setReportDt(screeningReport.getReportDt());
        addInspectionDTO.setReviewDoctorId(doctor.getDoctorId());
        addInspectionDTO.setReviewDoctorName(doctor.getDoctorName());
        addInspectionDTO.setMemberId(screeningReport.getMemberId());
        addInspectionDTO.setVisitNo(Constant.DEFAULT_FOREIGN_ID);
        addInspectionDTO.setInspectCode(inspectionDict.getCode());
        addInspectionDTO.setDepartmentId(doctor.getDepartId());
        addInspectionDTO.setDepartmentName(doctor.getDepartName());
        addInspectionDTO.setHospitalId(doctor.getHospitalId());
        addInspectionDTO.setHospitalName(doctor.getHospitalName());
        addInspectionDTO.setInHos(CheckConstant.INSPECTION_NOT_IN_HOS);
        addInspectionDTO.setOrigin(CheckConstant.INSPECTION_ORIGIN_SYS);
        addInspectionDTO.setReviewStatus(InspectionConstant.REVIEW_STATUS_YES);
        fullScreeningDataToInspection(addInspectionDTO ,screeningReport);
        this.inspectionService.addInspection(addInspectionDTO);
    }

    private void fullScreeningDataToInspection(AddInspectionDTO addInspectionDTO ,ScreeningReportPO report){
        switch (report.getScreeningType()){
            case ScreeningConstant.SCREENING_TYPE_ABI:
                fullAbiData(addInspectionDTO ,report);
                break;
            case ScreeningConstant.SCREENING_TYPE_VPT:
                fullVptData(addInspectionDTO ,report);
                break;
            case ScreeningConstant.SCREENING_TYPE_EYES:
                fullEyesData(addInspectionDTO ,report);
                break;
            default:
                break;
        }
    }

    private void fullAbiData(AddInspectionDTO addInspectionDTO ,ScreeningReportPO report){
        JSONObject jsonData = JSON.parseObject(report.getReportJson());
        JSONObject inspectDataJson = new JSONObject();
        StringBuilder inspectFinding = new StringBuilder();
        inspectFinding.append("左侧ABI:").append(jsonData.getOrDefault("leftAbi" ,"-") )
                .append(" ").append("右侧ABI:").append(jsonData.getOrDefault("rightAbi" ,"-"))
                .append("\\s\\r")
                .append("左侧PWV:").append(jsonData.getOrDefault("leftPwv" ,"-") )
                .append(" ").append("右侧PWV:").append(jsonData.getOrDefault("rightPwv" ,"-"));
        inspectDataJson.put("rightAbi" ,jsonData.get("rightAbi"));
        inspectDataJson.put("leftAbi" ,jsonData.get("leftAbi"));
        inspectDataJson.put("leftPwv" ,jsonData.get("leftPwv"));
        inspectDataJson.put("rightPwv" ,jsonData.get("rightPwv"));
        addInspectionDTO.setInspectFinding(inspectFinding.toString());
        addInspectionDTO.setDiagnoseResult(jsonData.getString("doctorMsg"));
        addInspectionDTO.setReportUrl(resolveReportUrl(ScreeningConstant.ABI_REPORT_URL ,report));
        addInspectionDTO.setInspectDataJson(inspectDataJson.toJSONString());
    }

    private void fullVptData(AddInspectionDTO addInspectionDTO ,ScreeningReportPO report){
        JSONObject jsonData = JSON.parseObject(report.getReportJson());
        JSONObject inspectDataJson = new JSONObject();
        inspectDataJson.put("rightVpt" ,jsonData.get("rightVpt"));
        inspectDataJson.put("leftVpt" ,jsonData.get("leftVpt"));
        addInspectionDTO.setInspectFinding(jsonData.getString("doctorMsg"));
        addInspectionDTO.setDiagnoseResult("-");
        addInspectionDTO.setReportUrl(resolveReportUrl(ScreeningConstant.VPT_REPORT_URL ,report));
        addInspectionDTO.setInspectDataJson(inspectDataJson.toJSONString());
    }

    private void fullEyesData(AddInspectionDTO addInspectionDTO ,ScreeningReportPO report){
        JSONObject jsonData = JSON.parseObject(report.getReportJson());
        Map<String ,String> imageMap = JSON.parseObject(report.getImageUrl() ,new TypeReference<Map<String ,String>>(){});
        Integer leftEye = jsonData.getInteger("leftEyesResult");
        Integer rightEye = jsonData.getInteger("rightEyesResult");
        StringBuilder diagnoseResult = new StringBuilder();
        diagnoseResult.append("左眼:");
        if(leftEye != null){
            diagnoseResult.append(EyesConstant.EYE_LEVEL_NAME.get(leftEye));
        }else{
            diagnoseResult.append("-");
        }
        diagnoseResult.append("\\n\\r");
        diagnoseResult.append("右眼:");
        if(rightEye != null){
            diagnoseResult.append(EyesConstant.EYE_LEVEL_NAME.get(rightEye));
        }else{
            diagnoseResult.append("-");
        }
        addInspectionDTO.setInspectFinding("-");
        addInspectionDTO.setDiagnoseResult(diagnoseResult.toString());
        addInspectionDTO.setReportUrl(resolveReportUrl(ScreeningConstant.EYES_REPORT_URL ,report));
        addInspectionDTO.setInspectDataJson(jsonData.toJSONString());
    }


    private final static Map<Integer , InspectionDict> SCREENING_INSPECTION_MAP = new HashMap<>();
    static {
        SCREENING_INSPECTION_MAP.put(ScreeningConstant.SCREENING_TYPE_ABI ,InspectionDict.ABI);
        SCREENING_INSPECTION_MAP.put(ScreeningConstant.SCREENING_TYPE_VPT ,InspectionDict.VPT);
        SCREENING_INSPECTION_MAP.put(ScreeningConstant.SCREENING_TYPE_EYES ,InspectionDict.EYES);
    }

    //就诊事件
    private void memberVisitEventHandler(DoctorSessionBO doctorSessionBO, String memberId,String screeningId,Integer screeningType){
        AddVistiEventDTO addVistiEventDTO = new AddVistiEventDTO();
        addVistiEventDTO.setMemberId(memberId);
        addVistiEventDTO.setHospitalName(doctorSessionBO.getHospitalName());
        addVistiEventDTO.setHospitalId(doctorSessionBO.getHospitalId());
        addVistiEventDTO.setEventType(VisitEventEnum.MEMBER_FINISH_SCREEN.getEventType());
        addVistiEventDTO.setParamName(ScreeningConstant.SCREENING_TYPE_NAME.get(screeningType));
        addVistiEventDTO.setForeignId(screeningId);
        addVistiEventDTO.setParamCode(String.valueOf(screeningType));
        visitEventService.addVisitEvent(addVistiEventDTO);
    }

    private String resolveReportUrl(String reportUrl ,ScreeningReportPO screeningReport){
        return reportUrl + "?screeningId=" + screeningReport.getScreeningId() + "&screeningType=" + screeningReport.getScreeningType();
    }

    private void addDoctorHealthWarningNotice(ScreeningReportPO screeningReport){
        if(ScreeningConstant.REPORT_STATUS_BAD != screeningReport.getResultStatus()){
            return;
        }
        String warningTitle = "";
        StringBuilder warningContent = new StringBuilder();
        JSONObject reportJson = JSONObject.parseObject(screeningReport.getReportJson());
        if(ScreeningConstant.SCREENING_TYPE_ABI == screeningReport.getScreeningType()){
            warningTitle = "外周动脉筛查异常";
            Float leftAbi = reportJson.getFloat("leftAbi");
            Float rightAbi = reportJson.getFloat("rightAbi");
            Integer leftPwv = reportJson.getInteger("leftPwv");
            Integer rightPwv = reportJson.getInteger("rightPwv");
            boolean leftAbiAbnormal = AbiTool.judgeAbiAbnormal(leftAbi);
            boolean rightAbiAbnormal = AbiTool.judgeAbiAbnormal(rightAbi);
            boolean leftPwvAbnormal = AbiTool.judgePwvAbnormal(leftPwv);
            boolean rightPwvAbnormal = AbiTool.judgePwvAbnormal(rightPwv);
            if(leftAbiAbnormal || rightAbiAbnormal){
                warningContent.append("下肢ABI指数:");
            }
            if(leftAbiAbnormal){
                warningContent.append("左").append(leftAbi).append(";");
            }
            if(rightAbiAbnormal){
                warningContent.append("右").append(rightAbi).append(";");
            }
            if(leftPwvAbnormal || rightPwvAbnormal){
                warningContent.append("脉搏波传导速度:");
            }
            if(leftPwvAbnormal){
                warningContent.append("左").append(leftPwv).append("(cm/s)");
            }
            if(rightPwvAbnormal){
                warningContent.append("右").append(rightPwv).append("(cm/s)");
            }
        }else if(ScreeningConstant.SCREENING_TYPE_VPT == screeningReport.getScreeningType()){
            warningTitle = "震动感觉阈值筛查异常";
            Float leftVpt = reportJson.getFloat("leftVpt");
            Float rightVpt = reportJson.getFloat("rightVpt");
            boolean leftVptAbnormal = leftVpt != null && leftVpt > 10F;
            boolean rightVptAbnormal = rightVpt != null && rightVpt > 10F;
            if(leftVptAbnormal){
                warningContent.append("左脚第一足趾，VPT:").append(leftVpt).append("(volt);");
            }
            if(rightVptAbnormal){
                warningContent.append("右脚第一足趾，VPT:").append(rightVpt).append("(volt);");
            }
        }else if(ScreeningConstant.SCREENING_TYPE_EYES == screeningReport.getScreeningType()){
            warningTitle = "眼底筛查异常";
            Integer leftEyesResult = reportJson.getInteger("leftEyesResult");
            Integer rightEyesResult = reportJson.getInteger("rightEyesResult");
            if(judgeHasretinopathy(leftEyesResult)){
                warningContent.append("左眼").append(EyesConstant.EYE_LEVEL_NAME.get(leftEyesResult)).append(";");
            }
            if(judgeHasretinopathy(rightEyesResult)){
                warningContent.append("右眼").append(EyesConstant.EYE_LEVEL_NAME.get(rightEyesResult)).append(";");
            }
        }
        AddMemberHealthWarningNoticeDTO addMemberHealthWarningNoticeDTO = new AddMemberHealthWarningNoticeDTO();
        addMemberHealthWarningNoticeDTO.setMemberId(screeningReport.getMemberId());
        addMemberHealthWarningNoticeDTO.setDatetime(screeningReport.getReportDt());
        addMemberHealthWarningNoticeDTO.setWarningTitle(warningTitle);
        addMemberHealthWarningNoticeDTO.setWarningContent(warningContent.toString());
        addMemberHealthWarningNoticeDTO.setForeignId(screeningReport.getReportId());
        this.doctorNoticeService.memberHealthWarningNotice(addMemberHealthWarningNoticeDTO);
    }
}
