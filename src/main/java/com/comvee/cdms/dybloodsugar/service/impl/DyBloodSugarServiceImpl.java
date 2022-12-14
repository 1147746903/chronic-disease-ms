package com.comvee.cdms.dybloodsugar.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.DyBloodSugarDTO;
import com.comvee.cdms.dybloodsugar.dto.DyBloodSugarRemarkDTO;
import com.comvee.cdms.dybloodsugar.dto.DyStaticsDTO;
import com.comvee.cdms.dybloodsugar.mapper.DYYPBloodSugarPOMapper;
import com.comvee.cdms.dybloodsugar.mapper.DyBloodSugarInformMapper;
import com.comvee.cdms.dybloodsugar.mapper.DyBloodSugarRemarkMapper;
import com.comvee.cdms.dybloodsugar.mapper.DyRecordSettingMapper;
import com.comvee.cdms.dybloodsugar.po.*;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSettingService;
import com.comvee.cdms.dybloodsugar.task.DynamicBloodSugarPushHandler;
import com.comvee.cdms.dybloodsugar.vo.ShowSensorVO;
import com.comvee.cdms.hospital.model.bo.CheckinInfoBO;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.member.dto.ListDoctorMemberDTO;
import com.comvee.cdms.member.po.DoctorMemberPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service("dyBloodSugarService")
public class DyBloodSugarServiceImpl implements DyBloodSugarService {

    private final static Logger log = LoggerFactory.getLogger(DyBloodSugarServiceImpl.class);

    @Autowired
    private DYYPBloodSugarPOMapper dyypBloodSugarPOMapper;

    @Autowired
    @Lazy
    private DyMemberSensorService dyMemberSensorService;

    @Autowired
    private DyBloodSugarRemarkMapper dyBloodSugarRemarkMapper;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private MemberService memberService;

    @Autowired
    @Lazy
    private DynamicBloodSugarPushHandler dynamicBloodSugarPushHandler;

    @Autowired
    private DyBloodSugarInformMapper dyBloodSugarInformMapper;

    @Autowired
    @Lazy
    private DyBloodSugarService dyBloodSugarService;

    @Autowired
    @Lazy
    private DyMemberSettingService dyMemberSettingService;

    @Autowired
    private DyRecordSettingMapper dyRecordSettingMapper;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private HospitalService hospitalService;

    @Override
    public void bloodSugarHandle(JSONObject uploadLogMap) {
        String sensorNo = uploadLogMap.getString("sensorSN");//?????????
        String machineNo = uploadLogMap.getString("machineNO");//?????????
        String machineEq = uploadLogMap.getString("eq");//??????
        JSONArray jsonArray = uploadLogMap.getJSONArray("bloodSugarList");
        int dataSize = Optional.ofNullable(jsonArray).map(JSONArray::size).orElse(0);
        log.info("???????????????????????? -- > ?????????:{} ,??????????????????:{},??????:{},??????????????????:{}" ,sensorNo ,machineNo ,machineEq ,dataSize);
        if(!StringUtils.isBlank(sensorNo)&&jsonArray!=null&&jsonArray.size()>0){
            //??????????????????
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject!=null){
                    String batchId = jsonObject.getString("batchId");//??????id(??????????????????)
                    String value = jsonObject.getString("value");
                    String recordTime = jsonObject.getString("recordTime");
                    if(StringUtils.isBlank(value) || StringUtils.isBlank(recordTime)){
                        continue;
                    }
                    if(Double.parseDouble(value) <= 0){
                        log.warn("??????????????????????????????:{},?????????:{},????????????:{}" ,value ,sensorNo ,recordTime);
                        continue;
                    }

                    boolean exists = false;
                    //????????????????????????????????????
                    DyBloodSugarDTO sugarDTO = new DyBloodSugarDTO();
                    sugarDTO.setRecordTime(recordTime);
                    sugarDTO.setSensorNo(sensorNo);
                    List<DYYPBloodSugarPO> list = this.dyypBloodSugarPOMapper.listBloodSugar(sugarDTO);
                    if(list!=null && list.size()>0){
                        exists = true;
                    }
                    log.info("???????????????????????????????????????????????????:{} | ?????????:{} | ??????????????????:{} | ?????????:{} | ?????????:{} | ????????????????????????:{}"
                            ,sensorNo ,machineNo ,recordTime ,value ,batchId ,exists ? "???" : "???");
                    if(exists){
                        continue;
                    }
                    //??????
                    DYYPBloodSugarPO po = new DYYPBloodSugarPO();
                    po.setBatchId(batchId);
                    po.setMachineNo(machineNo);
                    po.setValue(new BigDecimal(value));
                    po.setSensorNo(sensorNo);
                    po.setRecordTime(DateHelper.getDate(recordTime,DateHelper.DATETIME_FORMAT));
                    po.setRecordDt(DateHelper.getDate(recordTime,DateHelper.DAY_FORMAT));
                    po.setRecordOrigin((byte)6);//1 ?????????his??????2 ????????????????????????  3 ????????????
                    String level = this.getLevelForYP(po);
                    po.setLevel(Byte.parseByte(level));
                    Date date = new Date();
                    po.setInsertDt(date);
                    po.setModifyDt(date);
                    po.setIsValid((byte)1);
                    po.setParamCode("randomtime");
                    po.setSid(DaoHelper.getSeq());
                    po.setImei(uploadLogMap.getString("imei"));//IMEI???
                    this.dyypBloodSugarPOMapper.insert(po);

                }
            }
            //????????????/??????
//            this.startStatisticsTask(sensorNo, recordDts);
            try{
                uploadDynamicBloodSugarInform(sensorNo ,machineNo ,machineEq);
            }catch (Exception e){
                log.error("??????????????????????????????" ,e);
            }
        }

    }

    @Override
    public List<DYYPBloodSugarPO> listDataSourceOfYPParamLogOfOBDTASC(String startDt, String endDt, String sensorNo) {
        String start = dateDeal(startDt);
        String end = dateDeal(endDt);
        DyBloodSugarDTO dto = new DyBloodSugarDTO();
        if(!StringUtils.isBlank(start) && !StringUtils.isBlank(end)){
            if (startDt.equals(endDt)){
                Date offsetDate = DateHelper.getOffsetDate(DateHelper.getDate(end, DateHelper.DATETIME_FORMAT), 1);
                dto.setEndTime(DateHelper.getDate(offsetDate, DateHelper.DATETIME_FORMAT));
            }else{
                dto.setEndTime(end);
            }
            dto.setStartTime(start);
        }
        dto.setSensorNo(sensorNo);
        dto.setValid(1);
        List<DYYPBloodSugarPO> list = this.dyypBloodSugarPOMapper.listBloodSugar(dto);
        return list;
    }

    @Override
    public List<DYYPBloodSugarPO> listDataWechatSourceOfYPParamLogOfOBDTASC(String startDt, String endDt, String sensorNo) {
        startDt=startDt+DateHelper.DEFUALT_TIME_START;
        endDt=endDt+DateHelper.DEFUALT_TIME_END;
        DyBloodSugarDTO dto = new DyBloodSugarDTO();
        dto.setSensorNo(sensorNo);
        dto.setValid(1);
        dto.setStartTime(startDt);
        dto.setEndTime(endDt);
        List<DYYPBloodSugarPO> list = this.dyypBloodSugarPOMapper.listBloodSugar(dto);
        return list;
    }

    /**
     * ???????????????
     * @param date
     * @return
     */
    private static String dateDeal(String date){
        String dt = date + " 03:00:00";
        return dt;
    }



    @Override
    public List<DYYPBloodSugarPO> listDataSourceOfYPParamLogOfOBDTASC(List<String> dateList, String sensorNo) {
        DyBloodSugarDTO dto = new DyBloodSugarDTO();
        dto.setSensorNo(sensorNo);
        dto.setValid(1);
        dto.setRecordDtList(dateList);
        List<DYYPBloodSugarPO> list = this.dyypBloodSugarPOMapper.listBloodSugar(dto);
        return list;
    }

    @Override
    public List<String> listMemberTimeOfRecordLog(String startDt, String endDt, String sensorNo) {
        List<String> stringList = this.dyypBloodSugarPOMapper.listMemberTimeOfRecordLog(startDt,endDt,sensorNo);
        return stringList;
    }

    @Override
    public List<DYYPBloodSugarPO> listDataSourceOfYPParamLogOfTimeASC(String time, String startDt, String endDt, String sensorNo) {
        List<DYYPBloodSugarPO> list = this.dyypBloodSugarPOMapper.listDataSourceOfYPParamLogOfTimeASC(time,startDt,endDt,sensorNo);
        return list;
    }

    @Override
    public Long getFullDataOfDayCount(String startDt, String endDt, String sensorNo) {
        return this.dyypBloodSugarPOMapper.getFullDataOfDayCount(startDt,endDt,sensorNo);
    }

    @Override
    public List<DYYPBloodSugarPO> listDYYPBloodSugarByNoASC(String sensorNo) {
        DyBloodSugarDTO dto = new DyBloodSugarDTO();
        dto.setSensorNo(sensorNo);
        dto.setValid(1);
        List<DYYPBloodSugarPO> list = this.dyypBloodSugarPOMapper.listBloodSugar(dto);
        return list;
    }

    @Override
    public String addBloodSugarRemark(DyBloodSugarRemarkDTO dto) {
        List<DyBloodSugarRemarkPO> list = this.dyBloodSugarRemarkMapper.listDyBloodSugarRemarkByBidList(Collections.singletonList(dto.getBid()) ,dto.getOrigin());
        if(list != null && !list.isEmpty()){
            throw new BusinessException("??????????????????????????????????????????");
        }
        String sid = DaoHelper.getSeq();
        DyBloodSugarRemarkPO add = new DyBloodSugarRemarkPO();
        BeanUtils.copyProperties(add ,dto);
        add.setSid(sid);
        this.dyBloodSugarRemarkMapper.addDyBloodSugarRemark(add);
        return sid;
    }

    @Override
    public String addBloodSugarRememberRemark(DyBloodSugarRemarkDTO dto,String id) {
        DyBloodSugarRemarkPO Remark = this.dyBloodSugarRemarkMapper.getBloodSugarRemarkById(id);
        String sid = DaoHelper.getSeq();
        if (Remark != null){
            this.dyBloodSugarRemarkMapper.updateBloodSugarRemarkById(dto.getContent(),id);
        }else{
            List<DyBloodSugarRemarkPO> list = this.dyBloodSugarRemarkMapper.listDyBloodSugarRemarkByBidList(Collections.singletonList(dto.getBid()) ,dto.getOrigin());
            if(list != null && !list.isEmpty()){
                throw new BusinessException("??????????????????????????????????????????");
            }

            DyBloodSugarRemarkPO add = new DyBloodSugarRemarkPO();
            BeanUtils.copyProperties(add ,dto);
            add.setSid(sid);
            this.dyBloodSugarRemarkMapper.addDyBloodSugarRemark(add);
        }

        return sid;
    }

    @Override
    public DYYPBloodSugarPO getRemarkByBid(String bid) {
        DYYPBloodSugarPO dyypBloodSugarPO = this.dyypBloodSugarPOMapper.getDyBloodSugarBySid(bid);
        return dyypBloodSugarPO;
    }

    @Override
    public void uploadDynamicBloodSugarInform(String sensorNo, String machineNo, String machineEq) {
        if(StringUtils.isBlank(sensorNo) || StringUtils.isBlank(machineNo)){
            return;
        }
        // ??????????????????????????????
        this.dyMemberSensorService.updateSensorMonitorTimes(sensorNo);
        //??????????????????????????????
        this.dynamicBloodSugarPushHandler.refreshSensorLatestTimestamp(sensorNo);

        //??????????????????
        String machineEqRemind = this.dyMemberSensorService.getMachineEqRemind(machineEq);
        //??????????????????
        this.dynamicBloodSugarPushHandler.refreshMachineLastElectricQuantity(sensorNo, machineEqRemind);
        //????????????????????????
//        this.dyMemberSensorService.updateOrInsertMachineInfo(machineNo,machineEq,sensorNo);
    }

    @Override
    public void deleteBloodSugarRemarkById(String id) {
        this.dyBloodSugarRemarkMapper.deleteyBloodSugarRemarkById(id);
    }

    @Override
    public List<DyBloodSugarRemarkPO> listBloodSugarRemarkByBid(String bid) {
        return this.dyBloodSugarRemarkMapper.listDyBloodSugarRemarkByBid(bid);
    }

    @Override
    public DYYPBloodSugarPO getLatestDyBloodSugar(String sensorNo) {
        return this.dyypBloodSugarPOMapper.getLatestDyBloodSugar(sensorNo);
    }

    /**
     * ???????????? 3.9-10.0
     * @param paramLogDTO
     * @return
     */
    private String getLevelForYP(DYYPBloodSugarPO paramLogDTO) {
        Double value = paramLogDTO.getValue().doubleValue();
        if(value<3.9){
            return "1";
        } else if(value<=10.0){
            return "3";
        } else {
            return "5";
        }
    }

    /**
     * ??????????????????????????????
     * @param sensorNo
     */
    @Override
    public void uploadDynamicBloodSugarHandler(String sensorNo ,Long lastUpTimestamp, String machineEqRemind){
        DYMemberSensorPO dyMemberSensor = this.dyMemberSensorService.getMemberSensorBySensorNo(sensorNo);
        if(dyMemberSensor == null){
            log.info("????????????????????????????????????????????????????????????????????????????????????????????????:{}" ,sensorNo);
            return;
        }
        MemberPO member = this.memberService.getMemberById(dyMemberSensor.getMemberId());
        String memberName = member == null ? "--" : member.getMemberName();

        Map<String ,String> analyseResult = uploadDynamicBloodSugarAnalyseResultHandler(sensorNo ,lastUpTimestamp);

        uploadDynamicBloodSugarPatientMessageHandler(dyMemberSensor ,memberName, machineEqRemind);

        uploadDynamicBloodSugarInformHandler(dyMemberSensor ,memberName ,analyseResult.get(ANALYSE_RESULT_MAP_KEY_WEB));

        uploadDynamicBloodSugarSettingHandler(sensorNo);

        uploadDynamicBloodSugarDoctorMessageHandler(dyMemberSensor ,analyseResult.get(ANALYSE_RESULT_MAP_KEY_WX) ,memberName);
    }

    @Override
    public DYYPBloodSugarPO getDyBloodSugar(String sensorNo) {
        DYYPBloodSugarPO dyypBloodSugarPO = this.dyypBloodSugarPOMapper.getDyBloodSugar(sensorNo);
        return dyypBloodSugarPO;
    }

    @Override
    public List<DYYPBloodSugarPO> getDyBloodSugarList(String sensorNo, String startTime, String endTime) {
        List<DYYPBloodSugarPO> dyypBloodSugarPOList = this.dyypBloodSugarPOMapper.getDyBloodSugarList(sensorNo,startTime,endTime);
        return dyypBloodSugarPOList;
    }

    @Override
    public List<DyBloodSugarRemarkPO> listBloodSugarRemarkBySensorNo(String sensorNo, String startTime, String endTime ,Integer origin) {
        List<DYYPBloodSugarPO> list = getDyBloodSugarList(sensorNo ,startTime ,endTime);
        if(list == null || list.isEmpty()){
            return null;
        }
        List<String> idList = list.stream().map(DYYPBloodSugarPO::getSid).collect(Collectors.toList());
        return this.dyBloodSugarRemarkMapper.listDyBloodSugarRemarkByBidList(idList ,origin);
    }


    private DyMemberSettingPO defaultValues(){
        DyMemberSettingPO dyMemberSettingPO = new DyMemberSettingPO();
        dyMemberSettingPO.setBreakfastDt("06:30");
        dyMemberSettingPO.setLunchDt("11:30");
        dyMemberSettingPO.setDinnerDt("18:10");
        dyMemberSettingPO.setSleepDt("22:30");
        dyMemberSettingPO.setBloodSugarMin(3.9D);
        dyMemberSettingPO.setBloodSugarMax(10.0D);
        dyMemberSettingPO.setBloodSugarNorm(70);
        dyMemberSettingPO.setBloodSugarNormLess(4);
        dyMemberSettingPO.setBloodSugarNormThan(25);
        dyMemberSettingPO.setBloodTimeRatioMin(3.0D);
        dyMemberSettingPO.setBloodTimeRatioMax(13.9D);
        dyMemberSettingPO.setBloodSugarMinAfter(7.8D);
        dyMemberSettingPO.setBloodSugarMaxAfter(10.0D);
        dyMemberSettingPO.setBloodSugarNormRatioMax(5);
        dyMemberSettingPO.setBloodSugarNormRatioMin(4);
        dyMemberSettingPO.setMedianTarget(8.0D);
        dyMemberSettingPO.setGlucoseMin("???");
        dyMemberSettingPO.setHbA1cMax(7.0D);
        return dyMemberSettingPO;
    }

    /**
     * ?????????????????? ????????????????????????
     * @param dyMemberSensor
     * @param memberName
     */
    private void uploadDynamicBloodSugarPatientMessageHandler(DYMemberSensorPO dyMemberSensor ,String memberName, String machineEqRemind){
        List<ShowSensorVO> list = this.dyMemberSensorService.listShowSensorBySensorSid(dyMemberSensor.getSid());
        List<String> memberIdList = list.stream().map(ShowSensorVO::getMemberId).collect(Collectors.toList());
        memberIdList.add(dyMemberSensor.getMemberId());
        AddWechatMessageDTO addWechatMessageDTO = null;
        JSONObject dataJson = new JSONObject();
        dataJson.put("sensorNo" ,dyMemberSensor.getSensorNo());
        dataJson.put("date" ,DateHelper.getNowDate());
        dataJson.put("memberName" ,memberName);
        dataJson.put("machineEqRemind", machineEqRemind);
        String dataJsonString = dataJson.toJSONString();
        for(String s : memberIdList){
            addWechatMessageDTO = new AddWechatMessageDTO();
            addWechatMessageDTO.setMemberId(s);
            addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_REPORT);
            addWechatMessageDTO.setForeignId(dyMemberSensor.getSid());
            addWechatMessageDTO.setDataJson(dataJsonString);
            this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
        }
    }

    /**
     * ?????????????????? ????????????
     * @param dyMemberSensor
     * @param memberName
     */
    private void uploadDynamicBloodSugarInformHandler(DYMemberSensorPO dyMemberSensor ,String memberName ,String survey){
        DyBloodSugarInformPO dyBloodSugarInformPO = this.dyBloodSugarInformMapper.getInfoData(dyMemberSensor.getMemberId());

        if (dyBloodSugarInformPO == null){
            dyBloodSugarInformPO = new DyBloodSugarInformPO();
            String sid = DaoHelper.getSeq();
            dyBloodSugarInformPO.setSid(sid);
            dyBloodSugarInformPO.setMemberId(dyMemberSensor.getMemberId());
            dyBloodSugarInformPO.setMemberName(memberName);
            dyBloodSugarInformPO.setSensorNo(dyMemberSensor.getSensorNo());
            dyBloodSugarInformPO.setBindType(dyMemberSensor.getBindType());
            dyBloodSugarInformPO.setSurvey(survey);
            this.dyBloodSugarInformMapper.addWebBloodSugar(dyBloodSugarInformPO);
        }else {
            dyBloodSugarInformPO.setSensorNo(dyMemberSensor.getSensorNo());
            dyBloodSugarInformPO.setBindType(dyMemberSensor.getBindType());
            dyBloodSugarInformPO.setSurvey(survey);
            this.dyBloodSugarInformMapper.updateInfo(dyBloodSugarInformPO);
        }
    }

    /**
     * ??????????????????
     * @param sensorNo
     */
    private void uploadDynamicBloodSugarSettingHandler(String sensorNo){
        //1:?????????????????????????????????
        DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getDyBloodSugar(sensorNo);
        String startTime = DateHelper.getNowDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = "";
        DYMemberSensorPO dyMemberSensorPO = this.dyMemberSensorService.getDYMemberSensorPO(sensorNo);
        if (dyypBloodSugarPO != null) {

            //2:???????????????????????????????????????????????????????????????????????????.
            endTime = sdf.format(dyypBloodSugarPO.getRecordTime());
        } else {

            //2:????????????????????????????????????????????????????????????.
            endTime = sdf.format(DateHelper.getDate(dyMemberSensorPO.getInsertDt(), "yyyy-MM-dd HH:mm:ss"));
        }
        int day = DateHelper.dateCompareGetDay(startTime, endTime);
        //??????dy_setting_record???????????????????????????????????????,?????????????????????.
        DyRecordSettingPO dyRecordSettingPO = this.dyRecordSettingMapper.getSettingValues(sensorNo);
        //????????????id???????????????????????????
        DyMemberSettingPO dyMemberSettingPO = this.dyMemberSettingService.getSystemSetting(dyMemberSensorPO.getMemberId());
        //3:??????????????????14???,??????14??????????????????????????????,??????????????????????????????.
        if (day > 14) {
            if (dyRecordSettingPO == null) {
                if (dyMemberSettingPO != null) {
                    this.dyMemberSettingService.insertSettingRecord(dyMemberSettingPO, sensorNo);
                } else {
                    DyMemberSettingPO dyMemberSetting = defaultValues();
                    this.dyMemberSettingService.insertSettingRecord(dyMemberSetting, sensorNo);
                }
            }
        }
    }

    /**
     * ?????????????????? ???24??????????????????
     * @param sensorNo
     * @param lastUpTimestamp
     * @return
     */
    private Map<String ,String> uploadDynamicBloodSugarAnalyseResultHandler(String sensorNo ,Long lastUpTimestamp){
        Map<String ,String> result = new HashMap<>();
        final DyRecordSettingPO setting = this.dyMemberSettingService.getSettingValues(sensorNo);

        //??????????????????????????????????????????30???????????????????????????????????????
        String startTime = LocalDateTime.ofEpochSecond((lastUpTimestamp - (30 * 1000)) / 1000 ,0 , ZoneOffset.ofHours(8))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String endTime = LocalDateTime.ofEpochSecond(lastUpTimestamp / 1000 ,0 , ZoneOffset.ofHours(8))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        //?????????????????????????????????
        List<DYYPBloodSugarPO> list = this.dyypBloodSugarPOMapper.listBloodSugarByInsertDt(sensorNo ,startTime ,endTime);
        if(list == null || list.isEmpty()){
            log.info("???????????????????????????24??????????????????????????????????????????????????????????????????:{},????????????:{}???????????????:{}" ,sensorNo ,startTime ,endTime);
            return result;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(list.get(0).getRecordTime());
        calendar.add(Calendar.DAY_OF_YEAR ,-1);
        final Date beforeOneDate = calendar.getTime();
        //????????????24?????????
        List<DYYPBloodSugarPO> recentlyList = list.stream().filter(x -> x.getRecordTime().after(beforeOneDate)).collect(Collectors.toList());
        int total = recentlyList.size();
        int normal = 0;
        int low = 0;
        int high = 0;
        for(DYYPBloodSugarPO bloodSugar : recentlyList){
            double value = bloodSugar.getValue().doubleValue();
            if(value > setting.getBloodSugarMax()){
                high ++;
            }else if(value < setting.getBloodSugarMin()){
                low ++;
            }else{
                normal ++;
            }
        }
        Double normalRate = calculationRate(normal ,total);
        Double lowRate = calculationRate(low ,total);
        Double highRate = calculationRate(high ,total);

        String wxText;
        String webText;
        if(lowRate > setting.getBloodSugarNormLess() && highRate > setting.getBloodSugarNormThan()){
            wxText = MessageFormat.format("?????????24???????????????????????????????????????{0}%??????????????????????????????????????????{1}%?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????", highRate ,lowRate);
            webText = "??????????????????";
        }else if(highRate > setting.getBloodSugarNormThan()){
            wxText = MessageFormat.format("?????????24???????????????????????????????????????{0}%??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????", highRate);
            webText = "???????????????";
        }else if(lowRate > setting.getBloodSugarNormLess()){
            wxText = MessageFormat.format("?????????24???????????????????????????????????????{0}%?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????", lowRate);
            webText = "???????????????";
        }else if(normalRate < setting.getBloodSugarNorm()){
            wxText = MessageFormat.format("?????????24??????????????????????????????{0}%????????????????????????????????????????????????????????????????????????????????????????????????????????????" ,normalRate);
            webText = "??????????????????";
        }else{
            wxText = MessageFormat.format("?????????24??????????????????????????????{0}%?????????????????????", normalRate);
            webText = "??????????????????";
        }
        result.put(ANALYSE_RESULT_MAP_KEY_WX ,wxText);
        result.put(ANALYSE_RESULT_MAP_KEY_WEB ,webText);
        return result;
    }

    private static final String ANALYSE_RESULT_MAP_KEY_WX = "wx";
    private static final String ANALYSE_RESULT_MAP_KEY_WEB = "web";

    /**
     * ????????????
     * @param a
     * @param b
     * @return
     */
    private Double calculationRate(int a ,int b){
        return new BigDecimal(a * 100).divide(new BigDecimal(b) ,2 ,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * ?????????????????? ????????????????????????
     * @param dyMemberSensor
     * @param memberName
     */
    private void uploadDynamicBloodSugarDoctorMessageHandler(DYMemberSensorPO dyMemberSensor ,String text ,String memberName){
        if(StringUtils.isBlank(text)){
            log.info("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????:{}" ,dyMemberSensor.getSensorNo());
            return;
        }
        Set<String> doctorSet = new HashSet<>();
        List<DoctorPO> doctorList = null;
        //??????
        if(DynamicBloodSugarConstant.BIND_TYPE_IN_HOME == dyMemberSensor.getBindType()){
            ListDoctorMemberDTO listDoctorMemberDTO = new ListDoctorMemberDTO();
            listDoctorMemberDTO.setMemberId(dyMemberSensor.getMemberId());
            List<DoctorMemberPO>  list = this.memberService.listDoctorMember(listDoctorMemberDTO);
            if(list != null && !list.isEmpty()){
                for(DoctorMemberPO doctorMember : list){
                    doctorList = this.doctorService.listGroupDoctor(doctorMember.getDoctorId());
                }
            }
        }
        //??????
        else if(DynamicBloodSugarConstant.BIND_TYPE_IN_HOSPITAL == dyMemberSensor.getBindType()){
            CheckinInfoBO checkinInfo = this.hospitalService.getCheckinInfoBOByMid(dyMemberSensor.getMemberId() ,null);
            if(checkinInfo != null){
                doctorList = this.doctorService.listDoctorByDepartId(checkinInfo.getDepartmentId());
            }
        }
        if(doctorList == null || doctorList.isEmpty()){
            log.info("???????????????????????????????????????????????????????????????????????????:{},????????????id:{}" ,dyMemberSensor.getSensorNo() ,dyMemberSensor.getSid());
            return;
        }
        for(DoctorPO doctor : doctorList){
            if(!StringUtils.isBlank(doctor.getOpenId())){
                doctorSet.add(doctor.getDoctorId());
            }
        }
        log.info("??????????????????????????????????????????????????????:{},????????????id:{},?????????????????????id:{}" ,dyMemberSensor.getSensorNo() ,dyMemberSensor.getSid()
            , JSON.toJSONString(doctorSet));
        //????????????????????????
        JSONObject dataJson = new JSONObject();
        dataJson.put("text" ,text);
        dataJson.put("memberId" ,dyMemberSensor.getMemberId());
        dataJson.put("sensorNo" ,dyMemberSensor.getSensorNo());
        dataJson.put("memberName" ,memberName);
        dataJson.put("date" ,DateHelper.getNowDate());
        String data = dataJson.toJSONString();
        AddWechatMessageDTO addWechatMessageDTO;
        for(String doctorId : doctorSet){
            addWechatMessageDTO = new AddWechatMessageDTO();
            addWechatMessageDTO.setMemberId(doctorId);
            addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_DYNAMIC_BLOOD_SUGAR_SURVEY_REMIND);
            addWechatMessageDTO.setForeignId(dyMemberSensor.getSid());
            addWechatMessageDTO.setDataJson(data);
            addWechatMessageDTO.setUserType(WechatMessageConstant.USER_TYPE_DOCTOR);
            this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
        }
    }

    @Override
    public List<DYYPBloodSugarPO> getDataSimulationList(DyStaticsDTO dto) {
        List<DYYPBloodSugarPO> dataSimulationList=new ArrayList<>();
        if (!StringUtils.isBlank(dto.getLatestStartDate())&&!StringUtils.isBlank(dto.getLatestEndDate())){
            dataSimulationList= dyypBloodSugarPOMapper.getDataSimulationList(dto.getSensorNo(), dto.getLatestStartDate(), dto.getLatestEndDate());
        }
        return dataSimulationList;
    }

    /**
     * ????????????????????????
     * @param sensorNo
     * @return
     */
    @Override
    public List<String> listBloodSugarRecordDt(String sensorNo) {
        return dyypBloodSugarPOMapper.listBloodSugarRecordDt(sensorNo);
    }
    /**
     * ???????????????????????????
     * @param sensorNo
     * @return
     */
    @Override
    public String getFirstBloodSugarRecordTime(String sensorNo) {
        return dyypBloodSugarPOMapper.getFirstBloodSugarRecordTime(sensorNo);
    }
}
