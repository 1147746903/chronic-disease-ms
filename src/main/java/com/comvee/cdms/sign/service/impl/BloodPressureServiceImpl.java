package com.comvee.cdms.sign.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorTaskCardPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanRecordService;
import com.comvee.cdms.checkresult.dto.AddCheckoutDTO;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.doctor.dto.AddMemberHealthWarningNoticeDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorNoticeService;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.bo.MemberControlTargetBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.dto.MemberArchivesDTO;
import com.comvee.cdms.member.dto.UpdateMemberDTO;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.tool.MemberArchivesSyncHelper;
import com.comvee.cdms.other.constant.DoctorPushConstant;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.po.DoctorPushCachePO;
import com.comvee.cdms.other.service.DoctorPushService;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.dto.ListValidMemberPackageDTO;
import com.comvee.cdms.packages.po.MemberPackagePO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.remind.constant.MemberRemindTypeConstant;
import com.comvee.cdms.remind.dto.AddMemberRemindDTO;
import com.comvee.cdms.remind.service.MemberRemindService;
import com.comvee.cdms.sign.bo.BloodPressureAbnormalDataBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.constant.SignDialogueConstant;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.mapper.BloodPressureMapper;
import com.comvee.cdms.sign.po.BloodPressurePO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.BloodPressureService;
import com.comvee.cdms.sign.service.SignSuggestService;
import com.comvee.cdms.sign.service.SignSyncThirdService;
import com.comvee.cdms.sign.tool.BloodPressureTool;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
@Service("bloodPressureService")
public class BloodPressureServiceImpl implements BloodPressureService {

    @Autowired
    private BloodPressureMapper bloodPressureMapper;

    @Autowired
    private SignSuggestService signSuggestService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRemindService memberRemindService;

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private DoctorPushService doctorPushService;

    @Autowired
    private MemberMonitorPlanRecordService memberMonitorPlanRecordService;

    @Autowired
    private DoctorNoticeService doctorNoticeService;

    @Override
    public List<BloodPressurePO> listBloodPressure(ListBloodPressureDTO listBloodPressureDTO) {
        doListBloodPressureDTO(listBloodPressureDTO);
        return this.bloodPressureMapper.listBloodPressure(listBloodPressureDTO);
    }

    @Override
    public PageResult<BloodPressurePO> listBloodPressurePage(ListBloodPressureDTO listBloodPressureDTO, PageRequest page) {
        doListBloodPressureDTO(listBloodPressureDTO);
        PageHelper.startPage(page.getPage(),page.getRows());
        List<BloodPressurePO> list = this.bloodPressureMapper.listBloodPressure(listBloodPressureDTO);
        PageResult<BloodPressurePO> tempPageResult = new PageResult<BloodPressurePO>(list);
        if(list!=null&&list.size()>0){

            return tempPageResult;
        }
        return  new PageResult<BloodPressurePO>();
    }

    /**
     * 低压峰值 舒张压
     * @param countBloodSugarDTO
     */

    @Override
    public Map<String, Object> loadBloodPressureDbpMax(CountBloodSugarDTO countBloodSugarDTO){
        doCountBloodSugarDTO(countBloodSugarDTO);
        return this.bloodPressureMapper.loadBloodPressureDbpMax(countBloodSugarDTO);
    }

    /**
     * 高压峰值 收缩压
     * @param countBloodSugarDTO
     */
    @Override
    public Map<String, Object> loadBloodPressureSbpMax(CountBloodSugarDTO countBloodSugarDTO){
        doCountBloodSugarDTO(countBloodSugarDTO);
        return this.bloodPressureMapper.loadBloodPressureSbpMax(countBloodSugarDTO);
    }

    /**
     * 测量总次数、偏高、正常、偏低
     * @param countBloodSugarDTO
     */
    @Override
    public Map<String, Object> loadBloodPressureCount(CountBloodSugarDTO countBloodSugarDTO){
        doCountBloodSugarDTO(countBloodSugarDTO);
        Map<String, Object> reMap=new HashMap<>();
        Integer n = this.bloodPressureMapper.loadBloodPressureNomal(countBloodSugarDTO);
        Integer h = this.bloodPressureMapper.loadBloodPressureHigh(countBloodSugarDTO);
        Integer l = this.bloodPressureMapper.loadBloodPressureLow(countBloodSugarDTO);
        Integer num=n+h+l;
        reMap.put("totalNums",num);
        reMap.put("high",h);
        reMap.put("low",l);
        reMap.put("nomal",n);
        return reMap;
    }


    @Override
    public BloodPressurePO getBloodPressure(String sid) {
        GetBloodPressureDTO getBloodPressureDTO = new GetBloodPressureDTO();
        getBloodPressureDTO.setSid(sid);
        return this.bloodPressureMapper.getBloodPressure(getBloodPressureDTO);
    }

    @Override
    public SignSuggestPO getBloodPressureSuggest(String signId) {
        return this.signSuggestService.getSignSuggetBySignId(signId);
    }

    @Override
    public String addBloodPressureSuggest(AddSuggestDTO addSuggestDTO) {
        BloodPressurePO bloodPressure = getBloodPressure(addSuggestDTO.getSignId());
        if(bloodPressure == null){
            throw new BusinessException("血压记录已被删除~");
        }
        AddSignSuggestDTO addSignSuggestDTO = new AddSignSuggestDTO();
        BeanUtils.copyProperties(addSignSuggestDTO, addSuggestDTO);
        addSignSuggestDTO.setSignType(SignConstant.SIGN_TYPE_BLOOD_PRESSURE);
        addSignSuggestDTO.setMemberId(bloodPressure.getMemberId());
        return this.signSuggestService.addSignSuggest(addSignSuggestDTO);
    }

    @Override
    public String addBloodPressure(AddBloodPressureServiceDTO addBloodPressureServiceDTO) {
        String sid = DaoHelper.getSeq();
        AddBloodPressureMapperDTO addBloodPressureMapperDTO = new AddBloodPressureMapperDTO();
        BeanUtils.copyProperties(addBloodPressureMapperDTO ,addBloodPressureServiceDTO);
        addBloodPressureMapperDTO.setSid(sid);
        addBloodPressureMapper(addBloodPressureMapperDTO);
        return sid;
    }


    /**
     * 同步患者体征信息
     */
    private void memberSignInfoHandler(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        String memberId = addBloodPressureMapperDTO.getMemberId();
        String operatorId = addBloodPressureMapperDTO.getOperatorId();
        Map<String, Object> map = new HashMap<>();
        map.put("收缩压",addBloodPressureMapperDTO.getSbp());
        map.put("舒张压",addBloodPressureMapperDTO.getDbp());
        map.put("静息心率",addBloodPressureMapperDTO.getHeartRate());
        map.put("血压记录时间",addBloodPressureMapperDTO.getRecordTime());
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (!ObjectUtils.isEmpty(value)){
                MemberArchivesSyncHelper.memberArchivesSync(key, value, "sign",
                        memberService, memberId, operatorId);
            }
        }
    }

    @Override
    public String addBloodPressureForWechat(AddBloodPressureServiceDTO addBloodPressureServiceDTO){
        return addBloodPressure(addBloodPressureServiceDTO);
    }

    /**
     * 处理血压的时间点
     * @param time
     * @return
     */
    public String bloodPressure(String time){
        String dt = null;
        String nowTime = DateHelper.getToday()+" "+time;
        Date nowDt = DateHelper.dateTime(nowTime);
        String startTime = DateHelper.getToday()+" 04:01";
        Date startDt = DateHelper.dateTime(startTime);
        String endTime = DateHelper.getToday()+" 09:00";
        Date endDt = DateHelper.dateTime(endTime);
        if (DateHelper.isEffectiveDate(nowDt,startDt,endDt)){
            dt = "9";
        }
        String startTime2 = DateHelper.getToday()+" 09:01";
        String endTime2 = DateHelper.getToday()+" 12:00";
        Date nowDt1 = DateHelper.dateTime(nowTime);
        Date startDt1 = DateHelper.dateTime(startTime2);
        Date endDt1 = DateHelper.dateTime(endTime2);
        if (DateHelper.isEffectiveDate(nowDt1,startDt1,endDt1)){
            dt = "10";
        }

        String startTime3 = DateHelper.getToday()+" 12:01";
        String endTime3 = DateHelper.getToday()+" 18:00";
        Date nowDt2 = DateHelper.dateTime(nowTime);
        Date startDt2 = DateHelper.dateTime(startTime3);
        Date endDt2 = DateHelper.dateTime(endTime3);
        if (DateHelper.isEffectiveDate(nowDt2,startDt2,endDt2)){
            dt = "11";
        }
        String startTime4 = DateHelper.getToday()+" 18:01";
        String today = DateHelper.getToday();
        String endTime4 = DateHelper.plusDate(today,1)+" 04:00";
        Date nowDt3 = DateHelper.dateTime(nowTime);
        Date startDt3 = DateHelper.dateTime(startTime4);
        Date endDt3 = DateHelper.dateTime(endTime4);
        if (DateHelper.isEffectiveDate(nowDt3,startDt3,endDt3)){
            dt = "12";
        }
        return dt;
    }

    @Override
    public void addBloodPressureMapper(AddBloodPressureMapperDTO addBloodPressureMapperDTO) {
        GetBloodPressureDTO getBloodPressureDTO = new GetBloodPressureDTO();
        getBloodPressureDTO.setMemberId(addBloodPressureMapperDTO.getMemberId());
        getBloodPressureDTO.setRecordTime(addBloodPressureMapperDTO.getRecordTime());
        BloodPressurePO selectResult = this.bloodPressureMapper.getBloodPressure(getBloodPressureDTO);
        if(selectResult != null){
            return;
        }
        //血压情况判断
        assertBloodPressureLevel(addBloodPressureMapperDTO);
        sidHandler(addBloodPressureMapperDTO);
        this.bloodPressureMapper.addBloodPressure(addBloodPressureMapperDTO);
        bloodPressureAbnormalMessageHandler(addBloodPressureMapperDTO);
        updateBloodPressureMonitorCard(addBloodPressureMapperDTO);
        memberSignInfoHandler(addBloodPressureMapperDTO);
        addHealthWarnWechatMessage(addBloodPressureMapperDTO);
    }


    /**
     * 添加健康预警微信消息-血糖
     * @param addBloodPressureMapperDTO
     */
    private void addHealthWarnWechatMessage(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        //指标正常直接返回
        if(addBloodPressureMapperDTO.getDbpLevel() == SignConstant.SIGN_LEVEL_NORMAL
                && addBloodPressureMapperDTO.getSbpLevel() == SignConstant.SIGN_LEVEL_NORMAL){
            return;
        }
        AddMemberHealthWarningNoticeDTO addMemberHealthWarningNoticeDTO = new AddMemberHealthWarningNoticeDTO();
        addMemberHealthWarningNoticeDTO.setMemberId(addBloodPressureMapperDTO.getMemberId());
        addMemberHealthWarningNoticeDTO.setDatetime(addBloodPressureMapperDTO.getRecordTime());
        addMemberHealthWarningNoticeDTO.setWarningTitle("血压");
        addMemberHealthWarningNoticeDTO.setWarningContent(healthWarnValueHandler(addBloodPressureMapperDTO));
        addMemberHealthWarningNoticeDTO.setForeignId(addBloodPressureMapperDTO.getSid());
        this.doctorNoticeService.memberHealthWarningNotice(addMemberHealthWarningNoticeDTO);
    }

    private String healthWarnValueHandler(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        Integer paramLevel = null;
        if(addBloodPressureMapperDTO.getDbpLevel() != SignConstant.SIGN_LEVEL_NORMAL){
            paramLevel = addBloodPressureMapperDTO.getDbpLevel();
        }else{
            paramLevel = addBloodPressureMapperDTO.getSbpLevel();
        }
        String level = paramLevel == SignConstant.SIGN_LEVEL_LOW ? "偏低":"偏高";
        return addBloodPressureMapperDTO.getSbp()+"/"+addBloodPressureMapperDTO.getDbp()+" mmHg(" + level + ")";
    }

    /**
     * 主键处理
     * @param a
     */
    private void sidHandler(AddBloodPressureMapperDTO a){
        if(StringUtils.isBlank(a.getSid())){
            a.setSid(DaoHelper.getSeq());
        }
    }

    /**
     *  录入血压同步到档案  同步最新
     * @param memberId
     */
    private void syncBloodPressureToMemberArchive(String memberId){
        //获取患者最新的血压记录
        BloodPressurePO bloodPressure = this.bloodPressureMapper.getNewBloodPressure(memberId);
        if (null != bloodPressure){
            // 录入血压同步到档案  同步最新
            Map<String,Object> archivesMap=new HashMap<>();
            Map<String,Object> signMap=new HashMap<>();
            signMap.put("sbp",bloodPressure.getSbp());
            signMap.put("dbp",bloodPressure.getDbp());
            archivesMap.put("sign",signMap);

            MemberArchivesDTO memberArchivesDTO = new MemberArchivesDTO();
            memberArchivesDTO.setMemberId(bloodPressure.getMemberId());
            memberArchivesDTO.setDoctorId(bloodPressure.getOperatorId());
            memberArchivesDTO.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
            this.memberService.updateMemberArchive(memberArchivesDTO);

            //同步身血压至患者信息
            UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
            updateMemberDTO.setMemberId(bloodPressure.getMemberId());
            updateMemberDTO.setSbpPressure(bloodPressure.getSbp());
            updateMemberDTO.setDbpPressure(bloodPressure.getDbp());
            this.memberService.updateMember(updateMemberDTO);
        }
    }

    @Override
    public List<BloodPressurePO> listBloodPressureOfStatistics(GetStatisticsDTO dto) {
        return this.bloodPressureMapper.listBloodPressureOfStatistics(dto);
    }

    @Override
    public List<BloodPressurePO> listBloodPressureByMemberIdOfInHos(String memberId) {
        return this.bloodPressureMapper.listBloodPressureByMemberIdOfInHos(memberId);
    }

    @Override
    public BloodPressurePO getLatestBloodPressure(String memberId) {
        return this.bloodPressureMapper.getNewBloodPressure(memberId);
    }

    @Override
    public List<BloodPressurePO> listBloodPressureOfStatisticsOutHos(GetStatisticsDTO dto) {
        return this.bloodPressureMapper.listBloodPressureOfStatisticsOutHos(dto);
    }


    /**
     * 新增血压微信消息
     * @param addBloodPressureMapperDTO
     */
    private void addBloodPressureWechatMessage(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", addBloodPressureMapperDTO.getRecordTime());
        jsonObject.put("dbp", addBloodPressureMapperDTO.getDbp());
        jsonObject.put("dbpLevel", addBloodPressureMapperDTO.getDbpLevel());
        jsonObject.put("sbp", addBloodPressureMapperDTO.getSbp());
        jsonObject.put("sbpLevel", addBloodPressureMapperDTO.getSbpLevel());
        jsonObject.put("suggest", BloodPressureTool.getIntelligentSuggestion(addBloodPressureMapperDTO.getSbpLevel(), addBloodPressureMapperDTO.getDbpLevel()));
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(addBloodPressureMapperDTO.getMemberId());
        addWechatMessageDTO.setForeignId(addBloodPressureMapperDTO.getSid());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_BLOOD_PRESSURE);
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 判断血压级别
     * @param addBloodPressureMapperDTO
     * @return
     */
    private void assertBloodPressureLevel(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        MemberControlTargetBO memberControlTargetBO = null;
        RangeBO rangeBO = this.memberService.getMemberRange(addBloodPressureMapperDTO.getMemberId());
        if(rangeBO == null){
            memberControlTargetBO = this.memberService.getMemberDefaultControlTarget(addBloodPressureMapperDTO.getMemberId());
        }else{
            memberControlTargetBO = new MemberControlTargetBO();
            BeanUtils.copyProperties(memberControlTargetBO, rangeBO);
        }
        //收缩压高低范围
        Float highSystolicPress = getDefaultRange(addBloodPressureMapperDTO.getSbpMax() ,memberControlTargetBO.getHighSystolicPress());
        Float lowSystolicPress = getDefaultRange(addBloodPressureMapperDTO.getSbpMin() ,memberControlTargetBO.getLowSystolicPress());
        //舒张压高低范围
        Float highDiastolicPress = getDefaultRange(addBloodPressureMapperDTO.getDbpMax() ,memberControlTargetBO.getHighDiastolicPress());
        Float lowDiastolicPress = getDefaultRange(addBloodPressureMapperDTO.getDbpMin() ,memberControlTargetBO.getLowDiastolicPress());

        int spbLevel = SignConstant.SIGN_LEVEL_NORMAL;
        int dbpLevel = SignConstant.SIGN_LEVEL_NORMAL;
        //收缩压判断
        Float spb = Float.parseFloat(addBloodPressureMapperDTO.getSbp());
        if(spb > highSystolicPress){
            spbLevel = SignConstant.SIGN_LEVEL_HIGH;
        }else if(spb < lowSystolicPress){
            spbLevel = SignConstant.SIGN_LEVEL_LOW;
        }
        //舒张压判断
        Float dbp = Float.parseFloat(addBloodPressureMapperDTO.getDbp());
        if(dbp > highDiastolicPress){
            dbpLevel = SignConstant.SIGN_LEVEL_HIGH;
        }else if(dbp < lowDiastolicPress){
            dbpLevel = SignConstant.SIGN_LEVEL_LOW;
        }
        addBloodPressureMapperDTO.setDbpLevel(dbpLevel);
        addBloodPressureMapperDTO.setSbpLevel(spbLevel);

        //控制目标范围冗余 v4.3.0新增
        addBloodPressureMapperDTO.setSbpMin(lowSystolicPress.toString());
        addBloodPressureMapperDTO.setSbpMax(highSystolicPress.toString());

        addBloodPressureMapperDTO.setDbpMin(lowDiastolicPress.toString());
        addBloodPressureMapperDTO.setDbpMax(highDiastolicPress.toString());
    }

    /**
     * 获取默认控制目标
     * @param str
     * @param f
     * @return
     */
    private Float getDefaultRange(String str ,Float f){
        if(StringUtils.isBlank(str)){
            return f;
        }
        return Float.parseFloat(str);
    }

    /**
     * 血压异常处理
     * @param addBloodPressureMapperDTO
     */
    private void bloodPressureAbnormalMessageHandler(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        //非用户录入不下发异常消息
        if(SignConstant.SIGN_OPERATOR_TYPE_MEMBER != addBloodPressureMapperDTO.getOperatorType()){
            return;
        }
        //指标正常直接返回
        if(addBloodPressureMapperDTO.getDbpLevel() == SignConstant.SIGN_LEVEL_NORMAL
                && addBloodPressureMapperDTO.getSbpLevel() == SignConstant.SIGN_LEVEL_NORMAL){
            return;
        }
        sendIntelligentTrackRemind(addBloodPressureMapperDTO);
        sendAbnormalDialogue(addBloodPressureMapperDTO);
    }

    /**
     * 下发智能追踪提醒
     */
    private void sendIntelligentTrackRemind(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        AddMemberRemindDTO addMemberRemindDTO = new AddMemberRemindDTO();
        addMemberRemindDTO.setForeignId(addBloodPressureMapperDTO.getSid());
        addMemberRemindDTO.setMemberId(addBloodPressureMapperDTO.getMemberId());
        addMemberRemindDTO.setRemindMessage(BLOOD_PRESSURE_TITLE);
        addMemberRemindDTO.setRemindType(MemberRemindTypeConstant.REMIND_TYPE_ABNORMAL_BLOOD_PRESSURE);
        BloodPressureAbnormalDataBO bloodPressureAbnormalDataBO = new BloodPressureAbnormalDataBO();
        bloodPressureAbnormalDataBO.setDate(addBloodPressureMapperDTO.getRecordTime());
        bloodPressureAbnormalDataBO.setLevel(String.valueOf(getBloodPressureLevel(addBloodPressureMapperDTO)));
        bloodPressureAbnormalDataBO.setLogId(addBloodPressureMapperDTO.getSid());
        bloodPressureAbnormalDataBO.setTime(addBloodPressureMapperDTO.getRecordTime());
        bloodPressureAbnormalDataBO.setTitle(BLOOD_PRESSURE_TITLE);
        bloodPressureAbnormalDataBO.setUnit(BLOOD_PRESSURE_UNIT);
        bloodPressureAbnormalDataBO.setSbp(addBloodPressureMapperDTO.getSbp());
        bloodPressureAbnormalDataBO.setDbp(addBloodPressureMapperDTO.getDbp());
        bloodPressureAbnormalDataBO.setContent(BloodPressureTool.getIntelligentSuggestion(addBloodPressureMapperDTO.getSbpLevel()
                , addBloodPressureMapperDTO.getDbpLevel()));
        addMemberRemindDTO.setDataJsonStr(JSON.toJSONString(bloodPressureAbnormalDataBO));
        this.memberRemindService.addMemberRemind(addMemberRemindDTO);

        addBloodPressureWechatMessage(addBloodPressureMapperDTO);
    }

    private void doListBloodPressureDTO(ListBloodPressureDTO listBloodPressureDTO){
        if(!StringUtils.isBlank(listBloodPressureDTO.getCodeDt())){
            //日期时间工具
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            //1、今日  2、三天  3、七天 4、一个月（30天）
            if("1".equals(listBloodPressureDTO.getCodeDt())){//
                c.add(Calendar.DATE, 0);
            }else if("2".equals(listBloodPressureDTO.getCodeDt())){
                c.add(Calendar.DATE, -2);
            }else if("3".equals(listBloodPressureDTO.getCodeDt())){
                c.add(Calendar.DATE, -6);
            }else if("4".equals(listBloodPressureDTO.getCodeDt())){
                c.add(Calendar.DATE, -29);
            }
            String startDate= DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            String endDate= DateHelper.getDate(new Date(), "yyyy-MM-dd");
            listBloodPressureDTO.setStartDt(startDate +" 00:00:00");
            listBloodPressureDTO.setEndDt(endDate +" 23:59:59");
        }

    }

    /**
     * 发送异常对话
     * @param addBloodPressureMapperDTO
     */
    private void sendAbnormalDialogue(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(addBloodPressureMapperDTO.getMemberId());
        List<ServiceCode> serviceCodes = new ArrayList<>();
        serviceCodes.add(ServiceCode.ZHI_NENG_ZHUI_ZONG);
        listValidMemberPackageDTO.setCodeList(serviceCodes);
        List<MemberPackagePO> list = this.packageService.listValidMemberPackage(listValidMemberPackageDTO);
        if(list == null || list.size() == 0){
            return;
        }
        Set<String> set = list.stream().map(MemberPackagePO::getDoctorId).collect(Collectors.toSet());
        set.forEach(x -> {
            DialoguePO dialoguePO = new DialoguePO();
            dialoguePO.setDoctorId(x);
            dialoguePO.setMemberId(addBloodPressureMapperDTO.getMemberId());
            dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_MEMBER);
            dialoguePO.setPatientMsg(BLOOD_PRESSURE_TITLE);
            dialoguePO.setDoctorMsg(BLOOD_PRESSURE_TITLE);
            dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
            dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
            dialoguePO.setMsgType(DialogueConstant.DIALOGUE_MSG_TYPE_SIGN_ABNORMAL);
            dialoguePO.setSenderId(x);
            dialoguePO.setForeignId(addBloodPressureMapperDTO.getSid());
            BloodPressureAbnormalDataBO bloodPressureAbnormalDataBO = new BloodPressureAbnormalDataBO();
            bloodPressureAbnormalDataBO.setDate(addBloodPressureMapperDTO.getRecordTime());
            bloodPressureAbnormalDataBO.setLevel(String.valueOf(getBloodPressureLevel(addBloodPressureMapperDTO)));
            bloodPressureAbnormalDataBO.setLogId(addBloodPressureMapperDTO.getSid());
            bloodPressureAbnormalDataBO.setTime(addBloodPressureMapperDTO.getRecordTime());
            bloodPressureAbnormalDataBO.setTitle(BLOOD_PRESSURE_TITLE);
            bloodPressureAbnormalDataBO.setUnit(BLOOD_PRESSURE_UNIT);
            bloodPressureAbnormalDataBO.setSbp(addBloodPressureMapperDTO.getSbp());
            bloodPressureAbnormalDataBO.setDbp(addBloodPressureMapperDTO.getDbp());
            bloodPressureAbnormalDataBO.setTextType(SignDialogueConstant.DIALOGUE_TEXT_TYPE_PRESSURE);
            dialoguePO.setDataStr(JSON.toJSONString(bloodPressureAbnormalDataBO));
            this.dialogueService.addDialogue(dialoguePO);

            addBloodPressurePush(addBloodPressureMapperDTO, x);
        });
    }

    /**
     * 添加血压异常推送
     * @param addBloodPressureMapperDTO
     * @param doctorId
     */
    private void addBloodPressurePush(AddBloodPressureMapperDTO addBloodPressureMapperDTO, String doctorId){
        DoctorPushCachePO doctorPushCachePO = new DoctorPushCachePO();
        doctorPushCachePO.setDoctorId(doctorId);
        doctorPushCachePO.setPushType(DoctorPushConstant.PUSH_TYPE_SIGN);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("t", DoctorPushConstant.PUSH_BUSINESS_TYPE_SIGN_BLOOD_PRESSURE);
        doctorPushCachePO.setCustomInfo(jsonObject.toJSONString());
        doctorPushCachePO.setPushMessage(bloodPressurePushMessageHandler(addBloodPressureMapperDTO));
        this.doctorPushService.addDoctorPushCache(doctorPushCachePO);
    }

    /**
     * 血压异常推送消息处理
     * @param addBloodPressureMapperDTO
     * @return
     */
    private String bloodPressurePushMessageHandler(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        String memberName = getMemberNameById(addBloodPressureMapperDTO.getMemberId());
        StringBuilder stringBuilder = new StringBuilder();
        int level = getBloodPressureLevel(addBloodPressureMapperDTO);
        stringBuilder.append(memberName)
                .append("的血压值为:")
                .append(addBloodPressureMapperDTO.getDbp())
                .append("/")
                .append(addBloodPressureMapperDTO.getSbp())
                .append(BLOOD_PRESSURE_UNIT)
                .append(",")
                .append(level == SignConstant.SIGN_LEVEL_HIGH ? "偏高" : "偏低");
        return stringBuilder.toString();
    }



    /**
     * 获取患者姓名
     * @param memberId
     * @return
     */
    private String getMemberNameById(String memberId){
        MemberPO memberPO = this.memberService.getMemberById(memberId);
        if(memberPO == null){
            return "未知患者";
        }
        return memberPO.getMemberName();
    }

    /**
     * 获取血压级别
     * @param addBloodPressureMapperDTO
     * @return
     */
    private int getBloodPressureLevel(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        if(addBloodPressureMapperDTO.getSbpLevel() == SignConstant.SIGN_LEVEL_HIGH
                || addBloodPressureMapperDTO.getDbpLevel() == SignConstant.SIGN_LEVEL_HIGH){
            return SignConstant.SIGN_LEVEL_HIGH;
        }else if(addBloodPressureMapperDTO.getSbpLevel() == SignConstant.SIGN_LEVEL_LOW
                || addBloodPressureMapperDTO.getDbpLevel() == SignConstant.SIGN_LEVEL_LOW){
            return SignConstant.SIGN_LEVEL_LOW;
        }
        return SignConstant.SIGN_LEVEL_NORMAL;
    }

    private void doCountBloodSugarDTO(CountBloodSugarDTO countBloodSugarDTO){
        if(!StringUtils.isBlank(countBloodSugarDTO.getCodeDt())){
            //日期时间工具
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            //1、今日  2、三天  3、七天 4、一个月（30天）
            if("1".equals(countBloodSugarDTO.getCodeDt())){//
                c.add(Calendar.DATE, 0);
            }else if("2".equals(countBloodSugarDTO.getCodeDt())){
                c.add(Calendar.DATE, -2);
            }else if("3".equals(countBloodSugarDTO.getCodeDt())){
                c.add(Calendar.DATE, -6);
            }else if("4".equals(countBloodSugarDTO.getCodeDt())){
                c.add(Calendar.DATE, -29);
            }
            String startDate=DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            String endDate= DateHelper.getDate(new Date(), "yyyy-MM-dd");
            countBloodSugarDTO.setStartDt(startDate +" 00:00:00");
            countBloodSugarDTO.setEndDt(endDate +" 23:59:59");
        }

    }

    /**
     * 修改血压监测卡片
     * @param addBloodPressureMapperDTO
     */
    private void updateBloodPressureMonitorCard(AddBloodPressureMapperDTO addBloodPressureMapperDTO){
        MemberMonitorTaskCardPO taskCardPO = new MemberMonitorTaskCardPO();
        taskCardPO.setParamCode(bloodPressure(addBloodPressureMapperDTO.getRecordTime().substring(11,18)));
        taskCardPO.setMemberId(addBloodPressureMapperDTO.getMemberId());
        taskCardPO.setMonitorDt(addBloodPressureMapperDTO.getRecordTime().substring(0,10));
        JSONObject json = new JSONObject();
        json.put("dbp",addBloodPressureMapperDTO.getDbp());
        json.put("sbp",addBloodPressureMapperDTO.getSbp());
        taskCardPO.setParamValue(json.toString());
        taskCardPO.setInsertDt(addBloodPressureMapperDTO.getRecordTime().substring(0,10));
        taskCardPO.setIsMonitor("1");
        taskCardPO.setMonitorType(2);
        this.memberMonitorPlanRecordService.updateMonitorTaskCard(taskCardPO);
    }

    private static final String  BLOOD_PRESSURE_TITLE = "血压测量结果通知";
    private static final String  BLOOD_PRESSURE_UNIT = "mmHg";
}
