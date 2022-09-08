package com.comvee.cdms.sign.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.bo.AddCheckoutDetailBO;
import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.checkresult.constant.CheckoutDict;
import com.comvee.cdms.checkresult.dto.AddCheckoutDTO;
import com.comvee.cdms.checkresult.dto.AddCheckoutWithDetailDTO;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.checkresult.service.CheckoutServiceI;
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
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.MemberArchivesDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
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
import com.comvee.cdms.sign.bo.Hba1cAbnormalDialogueBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.constant.SignDialogueConstant;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.mapper.Hba1cMapper;
import com.comvee.cdms.sign.po.Hba1cPO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.Hba1cService;
import com.comvee.cdms.sign.service.SignSuggestService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("Hba1cService")
public class Hba1cServiceImpl implements Hba1cService {

    @Autowired
    private Hba1cMapper hba1cMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private MemberRemindService memberRemindService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private DoctorPushService doctorPushService;

    @Autowired
    private SignSuggestService signSuggestService;

    @Autowired
    private CheckoutServiceI checkoutServiceI;

    @Autowired
    private DoctorNoticeService doctorNoticeService;

    @Override
    public String addHba1c(AddHba1cDTO addHba1cDTO) {
        String sid = DaoHelper.getSeq();
        Hba1cPO hba1cPO = new Hba1cPO();
        BeanUtils.copyProperties(hba1cPO ,addHba1cDTO);
        hba1cPO.setSid(sid);
        addHba1cMapper(hba1cPO,addHba1cDTO.getHospitalId(),addHba1cDTO.getHospitalName(),SignConstant.SYN_TO_CHECKOUT_YES);
        return sid;
    }

    @Override
    public void addHba1cMapper(Hba1cPO hba1cPO,String hospitalId,String hospitalName,Integer synToCheckout) {
        sidHandler(hba1cPO);
        resolveRecordDt(hba1cPO);
        hba1cLevelHandler(hba1cPO);
        this.hba1cMapper.addHba1c(hba1cPO);
        abnormalHandler(hba1cPO);
        syncHba1cToMemberArchives(hba1cPO.getMemberId());
        addHealthWarningNotice(hba1cPO);
        if (null != synToCheckout && synToCheckout == SignConstant.SYN_TO_CHECKOUT_YES){
            addHba1cToCheckout(hba1cPO,hospitalId,hospitalName);
        }

    }

    /**
     * 主键处理
     * @param hba1c
     */
    private void sidHandler(Hba1cPO hba1c){
        if(StringUtils.isBlank(hba1c.getSid())){
            hba1c.setSid(DaoHelper.getSeq());
        }
    }

    /**
     * 同步糖化至档案
     * @param memberId
     */
    private void syncHba1cToMemberArchives(String memberId){
        //获取患者最新的糖化记录
        Hba1cPO hba1c = this.hba1cMapper.getNewHba1c(memberId);
        if (null != hba1c){
            //录入糖化同步到患者档案 同步最新
            Map<String,Object> archivesMap=new HashMap<>();
            Map<String,Object> labMap=new HashMap<>();
            labMap.put("lab_hba",hba1c.getRecordValue());
            labMap.put("lab_hba_dt",hba1c.getRecordDt());
            archivesMap.put("lab",labMap);

            MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
            memberArchivesDTO.setMemberId(hba1c.getMemberId());
            memberArchivesDTO.setDoctorId(hba1c.getOperatorId());
            memberArchivesDTO.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
            this.memberService.updateMemberArchive(memberArchivesDTO);
        }
    }

    @Override
    public List<Hba1cPO> listMemberHba1cByParam(ListHbalcDTO listHbalcDTO) {
        doListHbalcDTO(listHbalcDTO);
        return this.hba1cMapper.listMemberHba1c(listHbalcDTO.getMemberId(),listHbalcDTO.getStartDt(),listHbalcDTO.getEndDt());
    }

    @Override
    public Hba1cPO getHba1cById(String sid) {
        return this.hba1cMapper.getHba1cById(sid);
    }

    private void doListHbalcDTO(ListHbalcDTO listHbalcDTO){
        if(!StringUtils.isBlank(listHbalcDTO.getCodeDt())){
            //日期时间工具
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            //1、今日  2、三天  3、七天 4、一个月（30天）5, 三个月 6 一年
            if("1".equals(listHbalcDTO.getCodeDt())){//
                c.add(Calendar.DATE, 0);
            }else if("2".equals(listHbalcDTO.getCodeDt())){
                c.add(Calendar.DATE, -2);
            }else if("3".equals(listHbalcDTO.getCodeDt())){
                c.add(Calendar.DATE, -6);
            }else if("4".equals(listHbalcDTO.getCodeDt())){
                c.add(Calendar.DATE, -29);
            }else if("5".equals(listHbalcDTO.getCodeDt())){
                c.add(Calendar.DATE, -89);
            }else if("6".equals(listHbalcDTO.getCodeDt())){
                c.add(Calendar.DATE, -364);
            }
            String startDate= DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            String endDate= DateHelper.getDate(new Date(), "yyyy-MM-dd");
            listHbalcDTO.setStartDt(startDate +" 00:00:00");
            listHbalcDTO.setEndDt(endDate +" 23:59:59");
        }

    }
    @Override
    public List<Hba1cPO> listMemberHba1c(String memberId, String startDt, String endDt) {
        return this.hba1cMapper.listMemberHba1c(memberId ,startDt ,endDt);
    }

    @Override
    public PageResult<Hba1cPO> listMemberHba1c(PageRequest pr, String memberId) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<Hba1cPO> list = this.listMemberHba1c(memberId ,null ,null);
        return new PageResult<>(list);
    }

    @Override
    public Hba1cPO getLatestHba1c(String memberId) {
        return this.hba1cMapper.getNewHba1c(memberId);
    }

    @Override
    public String addHba1cSugarSuggest(AddSuggestDTO addSuggestDTO) {
        Hba1cPO hba1cPO = getHba1cById(addSuggestDTO.getSignId());
        if (hba1cPO == null){
            throw new BusinessException("糖化血红蛋白记录已被删除");
        }
        AddSignSuggestDTO addSignSuggestDTO = new AddSignSuggestDTO();
        BeanUtils.copyProperties(addSignSuggestDTO, addSuggestDTO);
        addSignSuggestDTO.setSignType(SignConstant.SIGN_TYPE_HBA1C);
        addSignSuggestDTO.setMemberId(hba1cPO.getMemberId());
        return this.signSuggestService.addSignSuggest(addSignSuggestDTO);
    }

    @Override
    public SignSuggestPO getHba1cSuggest(String signId) {
        return this.signSuggestService.getSignSuggetBySignId(signId);
    }

    /**
     * 糖化等级处理
     * @param hba1cPO
     */
    private void hba1cLevelHandler(Hba1cPO hba1cPO){
        RangeBO rangeBO = this.memberService.getMemberRange(hba1cPO.getMemberId());
        Float value = hba1cPO.getRecordValue();
        Float targetMaxValue = null;
        if(StringUtils.isBlank(hba1cPO.getRangeMax())){
            targetMaxValue = Float.parseFloat(rangeBO.getHighGlycosylatedVal());
        }else{
            targetMaxValue = Float.parseFloat(hba1cPO.getRangeMax());
        }
        int level = 0;
        if(value.floatValue() > targetMaxValue){
            level = SignConstant.SIGN_LEVEL_HIGH;
        }else{
            level = SignConstant.SIGN_LEVEL_NORMAL;
        }
        hba1cPO.setRecordLevel(level);
        hba1cPO.setRangeMax(String.valueOf(targetMaxValue));
    }

    /**
     * 异常处理
     * @param hba1cPO
     */
    private void abnormalHandler(Hba1cPO hba1cPO){
        //非患者录入不进行处理
        if(SignConstant.SIGN_OPERATOR_TYPE_MEMBER != hba1cPO.getOperatorType()){
            return;
        }
        //正常的糖化不进行处理
        if(SignConstant.SIGN_LEVEL_NORMAL == hba1cPO.getRecordLevel()){
            return;
        }
        sendIntelligentTrackRemind(hba1cPO);
        sendAbnormalDialogue(hba1cPO);
    }

    /**
     * 下发智能追踪提醒
     */
    private void sendIntelligentTrackRemind(Hba1cPO hba1cPO){
        AddMemberRemindDTO addMemberRemindDTO = new AddMemberRemindDTO();
        addMemberRemindDTO.setForeignId(hba1cPO.getSid());
        addMemberRemindDTO.setMemberId(hba1cPO.getMemberId());
        addMemberRemindDTO.setRemindMessage("糖化测量结果通知");
        addMemberRemindDTO.setRemindType(MemberRemindTypeConstant.REMIND_TYPE_HBA1C_UN_MEASURE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("recordValue" ,hba1cPO.getRecordValue());
        jsonObject.put("recordDt" ,hba1cPO.getRecordDt());
        jsonObject.put("level" ,hba1cPO.getRecordLevel());
        jsonObject.put("sid" ,hba1cPO.getSid());
        jsonObject.put("suggest" , getSuggestContent(hba1cPO.getRecordLevel()));
        addMemberRemindDTO.setDataJsonStr(jsonObject.toJSONString());
        this.memberRemindService.addMemberRemind(addMemberRemindDTO);

        addBloodSugarWechatMessage(hba1cPO);
    }
    /**
     * 发送异常对话
     * v 5.1.6
     * @param
     */
    private void sendAbnormalDialogue(Hba1cPO hba1cPO){
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(hba1cPO.getMemberId());
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
            dialoguePO.setMemberId(hba1cPO.getMemberId());
            dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_MEMBER);
            dialoguePO.setPatientMsg(HBA_TITLE);
            dialoguePO.setDoctorMsg(HBA_TITLE);
            dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
            dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
            dialoguePO.setMsgType(DialogueConstant.DIALOGUE_MSG_TYPE_SIGN_ABNORMAL);
            dialoguePO.setSenderId(x);
            dialoguePO.setForeignId(hba1cPO.getSid());
            Hba1cAbnormalDialogueBO hba1cAbnormalDialogueBO = new Hba1cAbnormalDialogueBO();
            hba1cAbnormalDialogueBO.setDate(hba1cPO.getRecordDt());
            hba1cAbnormalDialogueBO.setLevel(hba1cPO.getRecordLevel().toString());
            hba1cAbnormalDialogueBO.setLogId(hba1cPO.getSid());
            hba1cAbnormalDialogueBO.setTime(hba1cPO.getRecordDt());
            hba1cAbnormalDialogueBO.setTitle(HBA_TITLE);
            hba1cAbnormalDialogueBO.setUnit(HBA_UNIT);
            hba1cAbnormalDialogueBO.setValue(hba1cPO.getRecordValue().toString());
            hba1cAbnormalDialogueBO.setContent(getSuggestContent(hba1cPO.getRecordLevel()));
            hba1cAbnormalDialogueBO.setTextType(SignDialogueConstant.DIALOGUE_TEXT_TYPE_HBALC);
            dialoguePO.setDataStr(JSON.toJSONString(hba1cAbnormalDialogueBO));
            this.dialogueService.addDialogue(dialoguePO);
            addHba1cPush(hba1cPO, x);
        });
    }
    /**
     * 添加糖化血红蛋白异常推送
     *  v 5.1.6
     * @param hba1cPO
     * @param doctorId
     */
    private void addHba1cPush(Hba1cPO hba1cPO, String doctorId){
        DoctorPushCachePO doctorPushCachePO = new DoctorPushCachePO();
        doctorPushCachePO.setDoctorId(doctorId);
        doctorPushCachePO.setPushType(DoctorPushConstant.PUSH_TYPE_SIGN);
        doctorPushCachePO.setPushMessage(HbalccPushMessageHandler(hba1cPO));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("t", DoctorPushConstant.PUSH_BUSINESS_TYPE_SIGN_HBA);
        doctorPushCachePO.setCustomInfo(jsonObject.toJSONString());
        this.doctorPushService.addDoctorPushCache(doctorPushCachePO);
    }
    /**
     * 血压异常推送消息处理
     * v 5.1.6
     * @param hba1cPO
     * @return
     */
    private String HbalccPushMessageHandler(Hba1cPO hba1cPO){
        String memberName = getMemberNameById(hba1cPO.getMemberId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(memberName)
                .append("的糖化血红蛋白值为:")
                .append(hba1cPO.getRecordValue())
                .append(HBA_UNIT)
                .append(",")
                .append(hba1cPO.getRecordLevel() == SignConstant.SIGN_LEVEL_HIGH ? "偏高" : "偏低");
        return stringBuilder.toString();
    }

    /**
     * 获取患者姓名
     * v 5.1.6
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
     * 新增血糖微信消息
     * @param hba1cPO
     */
    private void addBloodSugarWechatMessage(Hba1cPO hba1cPO){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("recordValue" ,hba1cPO.getRecordValue());
        jsonObject.put("recordDt" ,hba1cPO.getRecordDt());
        jsonObject.put("level" ,hba1cPO.getRecordLevel());
        jsonObject.put("sid" ,hba1cPO.getSid());
        jsonObject.put("suggest" , getSuggestContent(hba1cPO.getRecordLevel()));
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(hba1cPO.getMemberId());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_HBA1C);
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        addWechatMessageDTO.setForeignId(hba1cPO.getSid());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 获取健康建议
     * @param level
     * @return
     */
    private String getSuggestContent(int level){
        String text = "";
        if(SignConstant.SIGN_LEVEL_HIGH == level){
            text = "糖化血红蛋白可评估您近2-3月内血糖控制的平均水平，您的糖化血红蛋白偏高，说明您这段时间的血糖控制不佳,请及时咨询医生，调整治疗方案";
        }else{
            text = "糖化血红蛋白可评估您近2-3月内血糖控制的平均水平,您的糖化血红蛋白偏低，说明有低血糖的风险，请及时咨询医生";
        }
        return text;
    }

    private void addHba1cToCheckout(Hba1cPO hba1c,String hospitalId,String hospitalName){
        AddCheckoutDetailBO addCheckoutDetailBO = new AddCheckoutDetailBO();
        addCheckoutDetailBO.setCode(CheckoutDict.HBA.getCode());
        addCheckoutDetailBO.setValue(hba1c.getRecordValue().toString());
        addCheckoutDetailBO.setName(CheckoutDict.HBA.getName());
        addCheckoutDetailBO.setUnit("%");

        AddCheckoutWithDetailDTO addCheckoutWithDetailDTO = new AddCheckoutWithDetailDTO();
        addCheckoutWithDetailDTO.setCheckoutTitle("糖化检查");
        addCheckoutWithDetailDTO.setCheckoutId(hba1c.getSid());
        addCheckoutWithDetailDTO.setCheckoutDoctorId(Constant.DEFAULT_FOREIGN_ID);
        addCheckoutWithDetailDTO.setYzId(hba1c.getSid());
        addCheckoutWithDetailDTO.setDepartCode(Constant.DEFAULT_FOREIGN_ID);
        addCheckoutWithDetailDTO.setMemberId(hba1c.getMemberId());
        addCheckoutWithDetailDTO.setVisitNo(Constant.DEFAULT_FOREIGN_ID);
        addCheckoutWithDetailDTO.setReportDate(hba1c.getRecordDt());
        addCheckoutWithDetailDTO.setDepartmentId(Constant.DEFAULT_FOREIGN_ID);
        addCheckoutWithDetailDTO.setDepartmentName("");
        addCheckoutWithDetailDTO.setSubmitCheckoutDate(hba1c.getRecordDt());
        //addCheckoutWithDetailDTO.setRecordOrigin(convertCheckoutOrigin(hba1c.getOrigin()));
        addCheckoutWithDetailDTO.setRecordOrigin(hba1c.getOrigin());
        addCheckoutWithDetailDTO.setHospitalId(hospitalId);
        addCheckoutWithDetailDTO.setHospitalName(StringUtils.isBlank(hospitalName)?"":hospitalName);
        addCheckoutWithDetailDTO.setDetailList(Collections.singletonList(addCheckoutDetailBO));
        addCheckoutWithDetailDTO.setSyncSign(false);
        addCheckoutWithDetailDTO.setVisitType(CheckoutConstant.VISIT_TYPE_OTHER);
        this.checkoutServiceI.addCheckout(addCheckoutWithDetailDTO);
    }

    private Integer convertCheckoutOrigin(Integer origin){
        Integer result = CheckoutConstant.RECORD_ORIGIN_SYS;
        if(SignConstant.ORIGIN_APP_PROGRAM == origin){
            result = CheckoutConstant.RECORD_ORIGIN_PATIENT_WECHAT;
        }
        return result;
    }

    private void resolveRecordDt(Hba1cPO hba1c){
        if(hba1c.getRecordDt().length() == 16){
            hba1c.setRecordDt(hba1c.getRecordDt() + ":00");
        }
    }

    /**
     * 添加医生通知消息（健康预警）
     * @param hba1c
     */
    private void addHealthWarningNotice(Hba1cPO hba1c){
        //正常的糖化不进行处理
        if(SignConstant.SIGN_LEVEL_NORMAL == hba1c.getRecordLevel()){
            return;
        }
        String levelText = SignConstant.SIGN_LEVEL_HIGH == hba1c.getRecordLevel() ? "偏高" : "偏低";
        String warningContent = hba1c.getRecordValue() + HBA_UNIT + "(" + levelText + ")";
        AddMemberHealthWarningNoticeDTO addMemberHealthWarningNoticeDTO = new AddMemberHealthWarningNoticeDTO();
        addMemberHealthWarningNoticeDTO.setMemberId(hba1c.getMemberId());
        addMemberHealthWarningNoticeDTO.setDatetime(hba1c.getRecordDt());
        addMemberHealthWarningNoticeDTO.setWarningTitle("糖化血红蛋白异常");
        addMemberHealthWarningNoticeDTO.setWarningContent(warningContent);
        addMemberHealthWarningNoticeDTO.setForeignId(hba1c.getSid());
        this.doctorNoticeService.memberHealthWarningNotice(addMemberHealthWarningNoticeDTO);
    }

    private final static String HBA_TITLE = "糖化血红蛋白测量结果通知";
    private final static String HBA_UNIT = "%";

}
