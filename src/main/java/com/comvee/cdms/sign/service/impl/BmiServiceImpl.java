package com.comvee.cdms.sign.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.constant.ControlTargetConstant;
import com.comvee.cdms.member.dto.MemberArchivesDTO;
import com.comvee.cdms.member.dto.UpdateMemberDTO;
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
import com.comvee.cdms.sign.bo.BmiAbnormalRemindBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.constant.SignDialogueConstant;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.mapper.BmiMapper;
import com.comvee.cdms.sign.po.BmiPO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.BmiService;
import com.comvee.cdms.sign.service.SignSuggestService;
import com.comvee.cdms.sign.tool.BmiTool;
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
@Service("bmiService")
public class BmiServiceImpl implements BmiService {

    @Autowired
    private BmiMapper bmiMapper;

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
    private CheckoutDetailServiceI checkoutDetailService;

    @Autowired
    private DoctorServiceI doctorService;

    @Override
    public List<BmiPO> listBmi(ListBmiDTO listBmiDTO) {
        doListBmiDTO(listBmiDTO);
        return this.bmiMapper.listBmi(listBmiDTO);
    }

    /**
     * 加载bmi列表
     * @param listBmiDTO
     * @return
     */
    @Override
    public PageResult<BmiPO> listBmiPage(ListBmiDTO listBmiDTO,PageRequest page) {
        doListBmiDTO(listBmiDTO);
        PageHelper.startPage(page.getPage(),page.getRows());
        List<BmiPO> list= this.bmiMapper.listBmi(listBmiDTO);
        PageResult<BmiPO> tempPageResult = new PageResult<BmiPO>(list);
        if(list!=null&&list.size()>0){

            return tempPageResult;
        }
        return  new PageResult<BmiPO>();

    }


    @Override
    public BmiPO getBmi(String sid) {
        GetBmiDTO param = new GetBmiDTO();
        param.setSid(sid);
        return this.bmiMapper.getBmi(param);
    }

    @Override
    public SignSuggestPO getBmiSuggest(String signId) {
        return this.signSuggestService.getSignSuggetBySignId(signId);
    }

    @Override
    public String addBmiSuggest(AddSuggestDTO addSuggestDTO) {
        BmiPO bmiPO = getBmi(addSuggestDTO.getSignId());
        if(bmiPO == null){
            throw new BusinessException("该条BMI记录已被删除~");
        }
        AddSignSuggestDTO addSignSuggestDTO = new AddSignSuggestDTO();
        BeanUtils.copyProperties(addSignSuggestDTO, addSuggestDTO);
        addSignSuggestDTO.setSignType(SignConstant.SIGN_TYPE_BMI);
        addSignSuggestDTO.setMemberId(bmiPO.getMemberId());
        return this.signSuggestService.addSignSuggest(addSignSuggestDTO);
    }

    @Override
    public String addBmi(AddBmiServiceDTO addBmiServiceDTO) {
        String sid = DaoHelper.getSeq();
        AddBmiMapperDTO addBmiMapperDTO = new AddBmiMapperDTO();
        BeanUtils.copyProperties(addBmiMapperDTO ,addBmiServiceDTO);
        addBmiMapperDTO.setSid(sid);
        addBmiMapper(addBmiMapperDTO);
        memberSignInfoHandler(addBmiMapperDTO);
        return sid;
    }

    /**
     * 同步患者体征信息
     */
    private void memberSignInfoHandler(AddBmiMapperDTO addBmiMapperDTO){
        String memberId = addBmiMapperDTO.getMemberId();
        String operatorId = addBmiMapperDTO.getOperatorId();
        Map<String, Object> map = new HashMap<>();
        map.put("身高",addBmiMapperDTO.getHeight());
        map.put("体重",addBmiMapperDTO.getWeight());
        map.put("bmi",addBmiMapperDTO.getBmi());
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (!ObjectUtils.isEmpty(value)){
                MemberArchivesSyncHelper.memberArchivesSync(key, value, "sign",
                        memberService, memberId, operatorId);
            }
        }
    }

    @Override
    public void addBmiMapper(AddBmiMapperDTO addBmiMapperDTO) {
        GetBmiDTO getBmiDTO = new GetBmiDTO();
        getBmiDTO.setMemberId(addBmiMapperDTO.getMemberId());
        getBmiDTO.setRecordDt(addBmiMapperDTO.getRecordDt());
        BmiPO getResult = this.bmiMapper.getBmi(getBmiDTO);
        if(getResult != null){
            return;
        }
        sidHandler(addBmiMapperDTO);
        assertBmiAbnormal(addBmiMapperDTO);
        bmiAbnormalMessageHandler(addBmiMapperDTO);
        this.bmiMapper.addBmi(addBmiMapperDTO);

        syncMemberBmi(addBmiMapperDTO);
    }

    /**
     * 主键处理
     * @param addBmiMapperDTO
     */
    private void sidHandler(AddBmiMapperDTO addBmiMapperDTO){
        if(StringUtils.isBlank(addBmiMapperDTO.getSid())){
            addBmiMapperDTO.setSid(DaoHelper.getSeq());
        }
    }

    @Override
    public List<Map<String, Object>> listBmiRang(GetStatisticsDTO dto) {
        return this.bmiMapper.listBmiRang(dto);
    }

    @Override
    public BmiPO getLatestBmi(String memberId) {
        return this.bmiMapper.getNewBmi(memberId);
    }

    /**
     * 新增bmi微信消息
     * @param addBmiMapperDTO
     */
    private void addBmiWechatMessage(AddBmiMapperDTO addBmiMapperDTO){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bmi", addBmiMapperDTO.getBmi());
        jsonObject.put("height", addBmiMapperDTO.getHeight());
        jsonObject.put("weight", addBmiMapperDTO.getWeight());
        jsonObject.put("date", addBmiMapperDTO.getRecordDt());
        jsonObject.put("level", addBmiMapperDTO.getParamLevel());
        jsonObject.put("suggest", BmiTool.getBMIIntelligentSuggestion(Float.parseFloat(addBmiMapperDTO.getBmi())));

        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_BMI);
        addWechatMessageDTO.setForeignId(addBmiMapperDTO.getSid());
        addWechatMessageDTO.setMemberId(addBmiMapperDTO.getMemberId());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 同步患者bmi数据
     * @param addBmiServiceDTO
     */
    private void syncMemberBmi(AddBmiMapperDTO addBmiServiceDTO){
        //获取患者最新的BMI记录
        BmiPO bmiPO = this.bmiMapper.getNewBmi(addBmiServiceDTO.getMemberId());
        if (null != bmiPO){
            // 录入身高体重同步到档案   同步最新记录
            Map<String,Object> archivesMap=new HashMap<>();
            Map<String,Object> signMap=new HashMap<>();
            signMap.put("height",bmiPO.getHeight());
            signMap.put("weight",bmiPO.getWeight());
            signMap.put("bmi",bmiPO.getBmi());
            signMap.put("bmiDt",bmiPO.getRecordDt());
            archivesMap.put("sign",signMap);
            MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
            memberArchivesDTO.setMemberId(bmiPO.getMemberId());
            memberArchivesDTO.setDoctorId(bmiPO.getOperatorId());
            memberArchivesDTO.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
            this.memberService.updateMemberArchive(memberArchivesDTO);

            //同步身高体重至患者信息
            UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
            updateMemberDTO.setMemberId(bmiPO.getMemberId());
            updateMemberDTO.setHeight(praseFloat(bmiPO.getHeight()));
            updateMemberDTO.setWeight((praseFloat(bmiPO.getWeight())));
            updateMemberDTO.setBmi(bmiPO.getBmi());
            this.memberService.updateMember(updateMemberDTO);
        }
    }

    /**
     * 处理float值
     * @param f
     * @return
     */
    private String praseFloat(Float f){
        return String.valueOf(f.floatValue());
    }

    /**
     * 判断BMI是否异常
     * @param addBmiMapperDTO
     */
    private void assertBmiAbnormal(AddBmiMapperDTO addBmiMapperDTO){
        Float bmi = Float.parseFloat(addBmiMapperDTO.getBmi());
        if(ControlTargetConstant.BMI_HIGH < bmi){
            addBmiMapperDTO.setParamLevel(SignConstant.SIGN_LEVEL_HIGH);
        }else if(ControlTargetConstant.BMI_LOW > bmi){
            addBmiMapperDTO.setParamLevel(SignConstant.SIGN_LEVEL_LOW);
        }else{
            addBmiMapperDTO.setParamLevel(SignConstant.SIGN_LEVEL_NORMAL);
        }
    }

    /**
     * bmi异常消息处理
     * @param addBmiMapperDTO
     */
    private void bmiAbnormalMessageHandler(AddBmiMapperDTO addBmiMapperDTO){
        //非用户录入不下发异常消息
        if(SignConstant.SIGN_OPERATOR_TYPE_MEMBER != addBmiMapperDTO.getOperatorType()){
            return;
        }
        if(SignConstant.SIGN_LEVEL_NORMAL == addBmiMapperDTO.getParamLevel()){
            return;
        }
        sendIntelligentTrackRemind(addBmiMapperDTO);
        sendAbnormalDialogue(addBmiMapperDTO);
    }


    /**
     * 下发智能追踪提醒
     */
    private void sendIntelligentTrackRemind(AddBmiMapperDTO addBmiMapperDTO){
/*        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(addBmiMapperDTO.getMemberId());
        List<ServiceCode> serviceCodes = new ArrayList<>();
        serviceCodes.add(ServiceCode.ZHI_NENG_ZHUI_ZONG);
        listValidMemberPackageDTO.setCodeList(serviceCodes);
        List<MemberPackagePO> list = this.packageService.listValidMemberPackage(listValidMemberPackageDTO);
        if(list == null || list.size() == 0){
            return;
        }*/
        AddMemberRemindDTO addMemberRemindDTO = new AddMemberRemindDTO();
        addMemberRemindDTO.setForeignId(addBmiMapperDTO.getSid());
        addMemberRemindDTO.setMemberId(addBmiMapperDTO.getMemberId());
        addMemberRemindDTO.setRemindMessage(BMI_TITLE);
        addMemberRemindDTO.setRemindType(MemberRemindTypeConstant.REMIND_TYPE_ABNORMAL_BMI);
        BmiAbnormalRemindBO bmiAbnormalRemindBO = new BmiAbnormalRemindBO();
        bmiAbnormalRemindBO.setDate(addBmiMapperDTO.getRecordDt());
        bmiAbnormalRemindBO.setLevel(addBmiMapperDTO.getParamLevel().toString());
        bmiAbnormalRemindBO.setLogId(addBmiMapperDTO.getSid());
        bmiAbnormalRemindBO.setTime(addBmiMapperDTO.getRecordDt());
        bmiAbnormalRemindBO.setTitle(BMI_TITLE);
        bmiAbnormalRemindBO.setUnit(BMI_UNIT);
        bmiAbnormalRemindBO.setBmi(addBmiMapperDTO.getBmi());
        bmiAbnormalRemindBO.setWeight(addBmiMapperDTO.getWeight().toString());
        bmiAbnormalRemindBO.setHeight(addBmiMapperDTO.getHeight().toString());
        bmiAbnormalRemindBO.setContent(BmiTool.getBMIIntelligentSuggestion(Float.parseFloat(addBmiMapperDTO.getBmi())));
        addMemberRemindDTO.setDataJsonStr(JSON.toJSONString(bmiAbnormalRemindBO));
        this.memberRemindService.addMemberRemind(addMemberRemindDTO);

        addBmiWechatMessage(addBmiMapperDTO);
    }

    /**
     * 发送异常对话
     * @param addBmiMapperDTO
     */
    private void sendAbnormalDialogue(AddBmiMapperDTO addBmiMapperDTO){
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(addBmiMapperDTO.getMemberId());
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
            dialoguePO.setMemberId(addBmiMapperDTO.getMemberId());
            dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_MEMBER);
            dialoguePO.setPatientMsg(BMI_TITLE);
            dialoguePO.setDoctorMsg(BMI_TITLE);
            dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
            dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
            dialoguePO.setMsgType(DialogueConstant.DIALOGUE_MSG_TYPE_SIGN_ABNORMAL);
            dialoguePO.setSenderId(x);
            dialoguePO.setForeignId(addBmiMapperDTO.getSid());
            BmiAbnormalRemindBO bmiAbnormalRemindBO = new BmiAbnormalRemindBO();
            bmiAbnormalRemindBO.setDate(addBmiMapperDTO.getRecordDt());
            bmiAbnormalRemindBO.setLevel(addBmiMapperDTO.getParamLevel().toString());
            bmiAbnormalRemindBO.setLogId(addBmiMapperDTO.getSid());
            bmiAbnormalRemindBO.setTime(addBmiMapperDTO.getRecordDt());
            bmiAbnormalRemindBO.setTitle(BMI_TITLE);
            bmiAbnormalRemindBO.setUnit(BMI_UNIT);
            bmiAbnormalRemindBO.setBmi(addBmiMapperDTO.getBmi());
            bmiAbnormalRemindBO.setWeight(addBmiMapperDTO.getWeight().toString());
            bmiAbnormalRemindBO.setHeight(addBmiMapperDTO.getHeight().toString());
            bmiAbnormalRemindBO.setContent(BmiTool.getBMIIntelligentSuggestion(Float.parseFloat(addBmiMapperDTO.getBmi())));
            bmiAbnormalRemindBO.setTextType(SignDialogueConstant.DIALOGUE_TEXT_TYPE_BMI);
            dialoguePO.setDataStr(JSON.toJSONString(bmiAbnormalRemindBO));
            this.dialogueService.addDialogue(dialoguePO);
            addBMIPush(addBmiMapperDTO, x);
        });
    }

    /**
     * 添加BMI异常推送
     * @param addBmiMapperDTO
     * @param doctorId
     */
    private void addBMIPush(AddBmiMapperDTO addBmiMapperDTO, String doctorId){
        DoctorPushCachePO doctorPushCachePO = new DoctorPushCachePO();
        doctorPushCachePO.setDoctorId(doctorId);
        doctorPushCachePO.setPushType(DoctorPushConstant.PUSH_TYPE_SIGN);
        doctorPushCachePO.setPushMessage(bmiPushMessageHandler(addBmiMapperDTO));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("t", DoctorPushConstant.PUSH_BUSINESS_TYPE_SIGN_BMI);
        doctorPushCachePO.setCustomInfo(jsonObject.toJSONString());
        this.doctorPushService.addDoctorPushCache(doctorPushCachePO);
    }

    /**
     * bmi异常推送消息处理
     * @param addBmiMapperDTO
     * @return
     */
    private String bmiPushMessageHandler(AddBmiMapperDTO addBmiMapperDTO){
        String memberName = getMemberNameById(addBmiMapperDTO.getMemberId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(memberName)
                .append("的BMI为:")
                .append(addBmiMapperDTO.getBmi())
                .append(",")
                .append(addBmiMapperDTO.getParamLevel() == SignConstant.SIGN_LEVEL_HIGH ? "偏高":"偏低");
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


    private void doListBmiDTO(ListBmiDTO listBmiDTO){
        if(!StringUtils.isBlank(listBmiDTO.getCodeDt())){
            //日期时间工具
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            //1、今日  2、三天  3、七天 4、一个月（30天） 5.三个月
            if("1".equals(listBmiDTO.getCodeDt())){//
                c.add(Calendar.DATE, 0);
            }else if("2".equals(listBmiDTO.getCodeDt())){
                c.add(Calendar.DATE, -2);
            }else if("3".equals(listBmiDTO.getCodeDt())){
                c.add(Calendar.DATE, -6);
            }else if("4".equals(listBmiDTO.getCodeDt())){
                c.add(Calendar.DATE, -29);
            }else if ("5".equals(listBmiDTO.getCodeDt())){
                c.add(Calendar.DATE, -89);
            }
            String startDate= DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            String endDate= DateHelper.getDate(new Date(), "yyyy-MM-dd");
            listBmiDTO.setStartDt(startDate +" 00:00:00");
            listBmiDTO.setEndDt(endDate +" 23:59:59");
        }
    }

    private final static String BMI_TITLE = "BMI测量结果通知";
    private final static String BMI_UNIT = "kg/m2";
}
