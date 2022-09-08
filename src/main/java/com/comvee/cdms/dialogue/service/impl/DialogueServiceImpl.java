package com.comvee.cdms.dialogue.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dialogue.bo.DefaultDialogueBO;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.constant.ReplyTextConstant;
import com.comvee.cdms.dialogue.dto.*;
import com.comvee.cdms.dialogue.mapper.DialogueGroupMapper;
import com.comvee.cdms.dialogue.mapper.DialogueMapper;
import com.comvee.cdms.dialogue.mapper.DoctorReplyTextMapper;
import com.comvee.cdms.dialogue.po.*;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.dialogue.tool.DialogueTimeTool;
import com.comvee.cdms.dialogue.vo.AddDialogueReturnVO;
import com.comvee.cdms.dialogue.vo.DialogueLatestDoctorVO;
import com.comvee.cdms.dialogue.vo.DialogueLatestMemberVO;
import com.comvee.cdms.dialogue.vo.ListDialogueLatestVO;
import com.comvee.cdms.differentlevels.service.DifferentLevelsService;
import com.comvee.cdms.differentlevels.vo.MemberCurrentDiffLevelVO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.hospital.model.dto.HosOptionDTO;
import com.comvee.cdms.hospital.model.po.HosOptionPO;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.DoctorPushConstant;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.po.DoctorPushCachePO;
import com.comvee.cdms.other.service.DoctorPushService;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import com.comvee.cdms.user.tool.SessionTool;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZhiGe
 * @description
 * @date 2018/1/30 13:41 create
 */
@Service("dialogueService")
public class DialogueServiceImpl implements DialogueService {

    @Autowired
    private DialogueMapper dialogueMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    @Lazy
    private DoctorServiceI doctorService;

    @Autowired
    private DialogueGroupMapper dialogueGroupMapper;

    @Autowired
    private DoctorPushService doctorPushService;

    @Autowired
    private DoctorReplyTextMapper doctorReplyTextMapper;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DifferentLevelsService differentLevelsService;

    @Autowired
    private DyMemberSensorService dyMemberSensorService;

    @Override
    public AddDialogueReturnVO sendDialogue(AddDialogueServiceDTO addDialogueServiceDTO) {
        DialoguePO dialoguePO = new DialoguePO();
        String msg = "";
        Map<String,Object> dataMap = new HashMap<>(6);
        dataMap.put("textType" , addDialogueServiceDTO.getTextType());
        switch (addDialogueServiceDTO.getTextType()){
            case DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT:
                msg = addDialogueServiceDTO.getMsg();
                dataMap.put("msg", msg);
                dialoguePO.setDoctorMsg(msg);
                break;
            case DialogueConstant.DIALOGUE_TEXT_TYPE_IMAGE:
                msg = "你收到一张图片消息";
                dataMap.put("msg", msg);
                dataMap.put("attachUrl", addDialogueServiceDTO.getAttachUrl());
                dataMap.put("imgW", addDialogueServiceDTO.getImgW());
                dataMap.put("imgH", addDialogueServiceDTO.getImgH());
                dialoguePO.setDoctorMsg("[图片]");
                break;
            case DialogueConstant.DIALOGUE_TEXT_TYPE_VOICE:
                msg = "你收到一条语音消息";
                dataMap.put("msg", msg);
                dataMap.put("attachUrl", addDialogueServiceDTO.getAttachUrl());
                dataMap.put("voiceLength", addDialogueServiceDTO.getVoiceLength());
                dialoguePO.setDoctorMsg("[语音]");
                break;
            default:
                break;
        }
        dialoguePO.setDoctorId(addDialogueServiceDTO.getDoctorId());
        dialoguePO.setDataStr(JSON.toJSONString(dataMap));
        dialoguePO.setMemberId(addDialogueServiceDTO.getMemberId());
        dialoguePO.setMsgType(DialogueConstant.DIALOGUE_MSG_TYPE);
        dialoguePO.setOwnerType(addDialogueServiceDTO.getOwnerType());
        dialoguePO.setPatientMsg(msg);
        dialoguePO.setSenderId(addDialogueServiceDTO.getSenderId());
        dialoguePO.setSendTimestamp(System.currentTimeMillis());
        dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        dialoguePO.setUpdateTimestamp(System.currentTimeMillis());
        dialoguePO.setForeignId(Constant.DEFAULT_FOREIGN_ID);
        AddDialogueReturnVO addDialogueReturnVO = addDialogue(dialoguePO);
        //新增微信模板消息
        addWechatMessage(addDialogueServiceDTO, addDialogueReturnVO.getSid());
        addAppPush(addDialogueServiceDTO);
        return addDialogueReturnVO;
    }

    /**
     * 添加app推送
     * @param dialoguePO
     */
    private void addAppPush(AddDialogueServiceDTO addDialogueServiceDTO){
        //非患者消息，不处理
        if(addDialogueServiceDTO.getOwnerType() != DialogueConstant.DIALOGUE_OWNER_TYPE_MEMBER){
            return;
        }
        DoctorPushCachePO doctorPushCachePO = new DoctorPushCachePO();
        doctorPushCachePO.setDoctorId(addDialogueServiceDTO.getDoctorId());
        doctorPushCachePO.setPushMessage(dialoguePushMessageHandler(addDialogueServiceDTO));
        doctorPushCachePO.setPushType(DoctorPushConstant.PUSH_TYPE_DIALOGUE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("t", DoctorPushConstant.PUSH_BUSINESS_TYPE_DIALOGUE);
        doctorPushCachePO.setCustomInfo(jsonObject.toJSONString());
        this.doctorPushService.addDoctorPushCache(doctorPushCachePO);
    }

    /**
     * 对话消息推送 内容处理
     * @param addDialogueServiceDTO
     * @return
     */
    private String dialoguePushMessageHandler(AddDialogueServiceDTO addDialogueServiceDTO){
        String memberName = getMemberNameById(addDialogueServiceDTO.getMemberId());
        StringBuilder pushMessage = new StringBuilder();
        switch (addDialogueServiceDTO.getTextType()){
            case DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT:
                pushMessage.append(memberName).append(":").append(addDialogueServiceDTO.getMsg());
                break;
            case DialogueConstant.DIALOGUE_TEXT_TYPE_IMAGE:
                pushMessage.append(memberName).append("给你发送了一张图片");
                break;
            case DialogueConstant.DIALOGUE_TEXT_TYPE_VOICE:
                pushMessage.append(memberName).append("给你发送了一条语音");
                break;
            default:
                break;
        }
        return pushMessage.toString();
    }

    /**
     * 获取患者姓名
     * @param memberId
     * @return
     */
    private String getMemberNameById(String memberId){
        MemberPO memberPO = this.memberService.getMemberById(memberId);
        if(memberPO == null){
            return "患者";
        }
        return memberPO.getMemberName();
    }

    /**
     * 添加微信消息
     * @param memberId
     * @param foreignId
     * @param dataMap
     */
    private void addWechatMessage(AddDialogueServiceDTO addDialogueServiceDTO, String sid){
        //不是医生消息不通知
        if(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR != addDialogueServiceDTO.getOwnerType()){
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textType", addDialogueServiceDTO.getTextType());
        jsonObject.put("doctorId", addDialogueServiceDTO.getDoctorId());
        jsonObject.put("date", DateHelper.getNowDate());
        String content = "";
        if(DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT == addDialogueServiceDTO.getTextType()){
            content = addDialogueServiceDTO.getMsg();
        }else if(DialogueConstant.DIALOGUE_TEXT_TYPE_IMAGE == addDialogueServiceDTO.getTextType()){
            content = "[图片]";
        }else if(DialogueConstant.DIALOGUE_TEXT_TYPE_VOICE == addDialogueServiceDTO.getTextType()){
            content = "[语音]";
        }
        jsonObject.put("content", content);

        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        addWechatMessageDTO.setMemberId(addDialogueServiceDTO.getMemberId());
        addWechatMessageDTO.setForeignId(sid);
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_CONSULT);
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    @Override
    public Map<String,Object> listDialogueLatest(DoctorSessionBO DoctorSessionBO
            , PageRequest PageRequest, String refreshTimeStamp, Integer beDelete, String param) {

        //封装返回
        Map<String,Object> map = new HashMap<>(2);
//        map.put("pageResult", pageResult);
        map.put("timestamp", System.currentTimeMillis());
        return map;
    }

    @Override
    public ListDialogueLatestVO<DialogueLatestDoctorVO> listDoctorDialogueLatestWithSearch(DoctorDialogueLatestSearchDTO doctorDialogueLatestSearchDTO, PageRequest pr) {
        List<DialogueLatestDoctorVO> list = null;
        if(DialogueTimeTool.doctorListNewDialogueCompare(doctorDialogueLatestSearchDTO.getDoctorList()
                , doctorDialogueLatestSearchDTO.getRefreshTimeStamp())){
            String doctorId = doctorDialogueLatestSearchDTO.getDoctorId();
            if(StringUtils.isBlank(doctorId)){
                if(doctorDialogueLatestSearchDTO.getDoctorList()!=null && doctorDialogueLatestSearchDTO.getDoctorList().size()>0){
                    doctorId = doctorDialogueLatestSearchDTO.getDoctorList().get(0);
                }
            }
            DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
            String hospitalId = null;
            if(doctorPO!=null){
                hospitalId=doctorPO.getHospitalId();
            }
            PageHelper.startPage(pr.getPage(), pr.getRows());
            list = this.dialogueMapper.listDoctorDialogueLatestWithSearch(doctorDialogueLatestSearchDTO);
            if(!StringUtils.isBlank(hospitalId)){
                for(DialogueLatestDoctorVO vo : list){
                    MemberCurrentDiffLevelVO memberCurrentDiffLevelResult = this.differentLevelsService.getMemberCurrentDiffLevelResult(vo.getMemberId(),hospitalId);
                    if(memberCurrentDiffLevelResult!=null){
                        vo.setLevelHx(memberCurrentDiffLevelResult.getLayer()+"");
                    } else {
                        vo.setLevelHx(null);
                    }
                }
            }
            for(DialogueLatestDoctorVO vo : list){
                vo.setUseMachine(getUseMachineInfo(vo.getMemberId()));
            }

        }else{
            list = new ArrayList<>();
        }
        PageResult<DialogueLatestDoctorVO> pageResult = new PageResult(list);
        ListDialogueLatestVO<DialogueLatestDoctorVO> listDialogueLatestVO = new ListDialogueLatestVO();
        listDialogueLatestVO.setPageResult(pageResult);
        listDialogueLatestVO.setTimestamp(System.currentTimeMillis());
        return listDialogueLatestVO;
    }

    @Override
    public Map<String,Object> listDialogue(PageRequest PageRequest , ListDialogueServiceDTO listDialogueServiceDTO) {
        List<TeamDialoguePO> list = null;
        if(DialogueTimeTool.newDialogueCompare(listDialogueServiceDTO.getDoctorId(),
                listDialogueServiceDTO.getMemberId(), listDialogueServiceDTO.getDownTimeStamp())){
            Map<String,Object> queryMap = new HashMap<>(6);
            queryMap.put("doctorId", listDialogueServiceDTO.getDoctorId());
            queryMap.put("memberId", listDialogueServiceDTO.getMemberId());
            queryMap.put("upTimeStamp", listDialogueServiceDTO.getUpTimeStamp());
            queryMap.put("downTimeStamp", listDialogueServiceDTO.getDownTimeStamp());
            queryMap.put("showClient",listDialogueServiceDTO.getShowClientList());
            queryMap.put("beDelete", listDialogueServiceDTO.getBeDelete());
            PageHelper.startPage(PageRequest.getPage(), PageRequest.getRows());
            list = this.dialogueMapper.listTeamDialogue(queryMap);
        }else{
            list = new ArrayList<>();
        }
        PageResult<TeamDialoguePO> pageResult = new PageResult<>(list);
        Map<String,Object> map = new HashMap<>(2);
        map.put("pageResult", pageResult);
        map.put("timestamp", System.currentTimeMillis());
        return map;
    }


    /**
     * 清除红点数
     * @param memberId
     * @param doctorId
     */
    @Override
    public void  clearUnread(String memberId,String doctorId, Integer visitOwner){
        List<DoctorPO> list = this.doctorService.listMyTeamDoctor(doctorId);
        if(list!=null && list.size()>0){
            for(DoctorPO doctor : list){
                UpdateDialogueLastestMapperDTO updateDialogueLastestMapperDTO = new UpdateDialogueLastestMapperDTO();
                updateDialogueLastestMapperDTO.setMemberId(memberId);
                updateDialogueLastestMapperDTO.setDoctorId(doctor.getDoctorId());
                if(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR == visitOwner){
                    updateDialogueLastestMapperDTO.setClearDoctorUnRead(0L);
                }else{
                    updateDialogueLastestMapperDTO.setClearMemberUnRead(0L);
                }
                this.dialogueMapper.updateDialogueLatest(updateDialogueLastestMapperDTO);
            }
        }
    }

    @Override
    public AddDialogueReturnVO addDialogue(DialoguePO dialoguePO) {
        String sid = DaoHelper.getSeq();
        long currentTimestamp = System.currentTimeMillis();
        dialoguePO.setSid(sid);
        dialoguePO.setSendTimestamp(currentTimestamp);
        dialoguePO.setUpdateTimestamp(currentTimestamp);
        if (StringUtils.isBlank(dialoguePO.getTemplateId())){
            dialoguePO.setTemplateId("-1");
        }
        DialogueTimeTool.newDialogueTimeCache(dialoguePO.getDoctorId(), dialoguePO.getMemberId(), dialoguePO.getOwnerType(), currentTimestamp);
        this.dialogueMapper.addDialogue(dialoguePO);
        refreshDialogueLatest(dialoguePO);
        AddDialogueReturnVO addDialogueReturnVO = new AddDialogueReturnVO();
        addDialogueReturnVO.setReturnTimeStamp(currentTimestamp);
        addDialogueReturnVO.setSid(sid);
        return addDialogueReturnVO;
    }

    /**
     * 刷新最新消息
     * @param DialoguePO
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public void refreshDialogueLatest(DialoguePO DialoguePO){
        DialogueLatestPO DialogueLatestPO = new DialogueLatestPO();
        DialogueLatestPO.setSid(DaoHelper.getSeq());
        DialogueLatestPO.setDoctorId(DialoguePO.getDoctorId());
        DialogueLatestPO.setDoctorMsg(DialoguePO.getDoctorMsg());
        DialogueLatestPO.setDoctorTimestamp(System.currentTimeMillis());
        DialogueLatestPO.setMemberId(DialoguePO.getMemberId());
        DialogueLatestPO.setPatientMsg(DialoguePO.getPatientMsg());
        DialogueLatestPO.setPatientTimestamp(System.currentTimeMillis());
        DialogueLatestPO.setLatestDt(DateHelper.getDateFormatter());
        if(DialoguePO.getBeDelete()!=null){
            DialogueLatestPO.setBeDelete(DialoguePO.getBeDelete()+"");
        }
        //是否是需要提醒患者的消息
        boolean patientMsg = DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR == DialoguePO.getOwnerType()
                && (DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL == DialoguePO.getShowClient()
                || DialogueConstant.DIALOGUE_SHOW_CLIENT_PATIENT == DialoguePO.getShowClient());
        //是否是需要提醒医生的消息
        boolean doctorMsg = DialogueConstant.DIALOGUE_OWNER_TYPE_MEMBER == DialoguePO.getOwnerType()
                && (DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL == DialoguePO.getShowClient()
                || DialogueConstant.DIALOGUE_SHOW_CLIENT_DOCTOR == DialoguePO.getShowClient());
        if(patientMsg){
            DialogueLatestPO.setPatientUnread(1L);
        }else if(doctorMsg){
            DialogueLatestPO.setDoctorUnread(1L);
        }
        Map<String,Object> queryMap = new HashMap<>(2);
        queryMap.put("memberId", DialoguePO.getMemberId());
        queryMap.put("doctorId", DialoguePO.getDoctorId());
        if(DialoguePO.getShowClient().equals(DialogueConstant.DIALOGUE_SHOW_CLIENT_PATIENT)){
            DialogueLatestPO.setDoctorMsg(null);
            DialogueLatestPO.setDoctorTimestamp(null);
        }
        long count = countDialogueLatest(queryMap);
        if(count == 0){
            this.dialogueMapper.addDialogueLatest(DialogueLatestPO);
        }else{
            boolean updateFlag = updateDialogueLatestHandler(DialogueLatestPO, DialoguePO);
            if(updateFlag){
                UpdateDialogueLastestMapperDTO updateDialogueLastestMapperDTO = new UpdateDialogueLastestMapperDTO();
                BeanUtils.copyProperties(updateDialogueLastestMapperDTO, DialogueLatestPO);
                updateDialogueLastestMapperDTO.setLatestDt(DateHelper.getDateFormatter());
                if(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR == DialoguePO.getOwnerType()){
                    updateDialogueLastestMapperDTO.setClearDoctorUnRead(0L);
                }
                updateDialogueLastestMapperDTO.setBeDelete("0");
                this.dialogueMapper.updateDialogueLatest(updateDialogueLastestMapperDTO);
            }
        }
    }

    @Override
    public void deleteDialogueOfDoctorMember(String doctorId, String memberId) {
        ValidateTool.checkParameterIsNull(memberId,"memberId");
        ValidateTool.checkParameterIsNull(doctorId,"doctorId");
        this.deleteDialoguelatest(doctorId,memberId);
        this.deleteDialogue(doctorId,memberId);
    }

    @Override
    public ListDialogueLatestVO<DialogueLatestMemberVO> listMemberDialogueLatest(PageRequest pr, MemberDialogueLatestDTO memberDialogueLatestDTO) {
        ListDialogueLatestVO<DialogueLatestMemberVO> listDialogueLatestVO = new ListDialogueLatestVO<DialogueLatestMemberVO>();
        ListDialogueLatestMapperDTO listDialogueLatestMapperDTO = new ListDialogueLatestMapperDTO();
        BeanUtils.copyProperties(listDialogueLatestMapperDTO, memberDialogueLatestDTO);
        listDialogueLatestMapperDTO.setMemberRefreshTime(memberDialogueLatestDTO.getRefreshTimeStamp());
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<DialogueLatestPO> list = this.dialogueMapper.listDialogueLatest(listDialogueLatestMapperDTO);
        PageResult<DialogueLatestPO> poPageResult = new PageResult(list);
        List<DialogueLatestPO> rows = poPageResult.getRows();
        if(rows == null || rows.size() == 0){
            PageResult<DialogueLatestMemberVO> pageResult = poPageResult.createEmptyPageResult();
            listDialogueLatestVO.setPageResult(pageResult);
            listDialogueLatestVO.setTimestamp(System.currentTimeMillis());
            return listDialogueLatestVO;
        }
        List<String> doctorIdList = rows.stream().map(DialogueLatestPO::getDoctorId).collect(Collectors.toList());
        List<DoctorPO> doctorPOList = this.doctorService.listDoctorInId(doctorIdList);
        Map<String, DoctorPO> doctorPOMap = doctorPOList.stream().collect(Collectors.toMap(DoctorPO::getDoctorId, x -> x));
        List<DialogueLatestMemberVO> dialogueLatestMemberVOS = new ArrayList<>();
        rows.forEach(x -> {
            DialogueLatestMemberVO dialogueLatestMemberVO = new DialogueLatestMemberVO();
            BeanUtils.copyProperties(dialogueLatestMemberVO, x);
            DoctorPO doctorPO = doctorPOMap.get(x.getDoctorId());
            if(doctorPO != null){
                dialogueLatestMemberVO.setDoctorImageHead(doctorPO.getPhotoUrl());
                dialogueLatestMemberVO.setDoctorName(doctorPO.getDoctorName());
                dialogueLatestMemberVO.setSex(doctorPO.getSex());
            }
            dialogueLatestMemberVOS.add(dialogueLatestMemberVO);
        });
        PageResult<DialogueLatestMemberVO> pageResult = poPageResult.createEmptyPageResult();
        pageResult.setRows(dialogueLatestMemberVOS);
        listDialogueLatestVO.setPageResult(pageResult);
        listDialogueLatestVO.setTimestamp(System.currentTimeMillis());
        return listDialogueLatestVO;
    }

    @Override
    public UnReadDialoguePO countUnRead(UnReadDialogueDTO unReadDialogueDTO) {
        return this.dialogueMapper.countUnRead(unReadDialogueDTO);
    }

    @Override
    public void addDialogueGroup(AddDialogueGroupDTO addDialogueGroupDTO, String senderId) {
        Map<String,String> map = JSON.parseObject(addDialogueGroupDTO.getSendGroupJson(), new TypeReference<Map<String,String>>(){});
        map.forEach((k, v) -> {
            DialogueGroupPO dialogueGroupPO = new DialogueGroupPO();
            dialogueGroupPO.setSid(DaoHelper.getSeq());
            dialogueGroupPO.setDoctorId(k);
            dialogueGroupPO.setMsgType(addDialogueGroupDTO.getMsgType());
            dialogueGroupPO.setSenderId(senderId);
            dialogueGroupPO.setMemberIdData(v);
            groupMemberHandler(v, dialogueGroupPO);
            dataJsonHandler(dialogueGroupPO, addDialogueGroupDTO);
            this.dialogueGroupMapper.addDialogueGroup(dialogueGroupPO);
        });
    }

    @Override
    public PageResult<DialogueGroupPO> listDialogueGroup(PageRequest pr, ListDialogueGroupDTO listDialogueGroupDTO) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<DialogueGroupPO> list = this.dialogueGroupMapper.listDialogueGroup(listDialogueGroupDTO);
        return new PageResult<>(list);
    }

    @Override
    public void updateDialogueGroup(UpdateDialogueGroupDTO updateDialogueGroupDTO) {
        this.dialogueGroupMapper.updateDialogueGroup(updateDialogueGroupDTO);
    }

    /**
     * 姓名处理
     * @param memberIdList
     * @return
     */
    private void groupMemberHandler(String memberIdList, DialogueGroupPO dialogueGroupPO){
        List<String> list = Arrays.asList(memberIdList.split(","));
        if(list.size() == 0){
            throw new BusinessException("群发消息的患者不能为空");
        }
        List<MemberPO> memberPOList = this.memberService.listMemberByIdList(list);
        List<String> memberNameList = memberPOList.stream().map(MemberPO::getMemberName).collect(Collectors.toList());
        dialogueGroupPO.setMemberNameJson(JSON.toJSONString(memberNameList));
        dialogueGroupPO.setPeopleNumber(list.size());
    }

    /**
     * 数据json处理
     * @param dialogueGroupPO
     * @param addDialogueGroupDTO
     */
    private void dataJsonHandler(DialogueGroupPO dialogueGroupPO, AddDialogueGroupDTO addDialogueGroupDTO){
        JSONObject jsonObject = new JSONObject();
        String content = "";
        if(addDialogueGroupDTO.getMsgType() == DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT){
            content = addDialogueGroupDTO.getTextContent();
        }else if(addDialogueGroupDTO.getMsgType() == DialogueConstant.DIALOGUE_TEXT_TYPE_IMAGE){
            content = "[图片]";
            jsonObject.put("attachUrl", addDialogueGroupDTO.getAttachUrl());
            jsonObject.put("imgH", addDialogueGroupDTO.getImgH());
            jsonObject.put("imgW", addDialogueGroupDTO.getImgW());
        }else if(addDialogueGroupDTO.getMsgType() == DialogueConstant.DIALOGUE_TEXT_TYPE_VOICE){
            content = "[语音]";
            jsonObject.put("attachUrl", addDialogueGroupDTO.getAttachUrl());
            jsonObject.put("voiceLength", addDialogueGroupDTO.getVoiceLength());
        }
        dialogueGroupPO.setDataJson(jsonObject.toJSONString());
        dialogueGroupPO.setMsgContent(content);
    }

    /**
     * 删除医患消息列表
     * @param doctorId
     * @param memberId
     */
    private void deleteDialoguelatest(String doctorId,String memberId){
        UpdateDialogueLastestMapperDTO updateDialogueLastestMapperDTO = new UpdateDialogueLastestMapperDTO();
        updateDialogueLastestMapperDTO.setDoctorId(doctorId);
        updateDialogueLastestMapperDTO.setMemberId(memberId);
        updateDialogueLastestMapperDTO.setBeDelete("1");
        updateDialogueLastestMapperDTO.setDoctorTimestamp(System.currentTimeMillis());
        updateDialogueLastestMapperDTO.setPatientTimestamp(System.currentTimeMillis());
        this.dialogueMapper.updateDialogueLatest(updateDialogueLastestMapperDTO);
        updateDialogueLastestMapperDTO = null;
    }

    /**
     * 删除医患消息信息
     * @param doctorId
     * @param memberId
     */
    private void deleteDialogue(String doctorId,String memberId){
        DialoguePO DialoguePO = new DialoguePO();
        DialoguePO.setDoctorId(doctorId);
        DialoguePO.setMemberId(memberId);
        DialoguePO.setBeDelete(1);
        this.dialogueMapper.updateDialogue(DialoguePO);
    }

    /**
     * 计算最新消息记录条数
     * @param queryMap
     * @return
     */
    public Long countDialogueLatest(Map<String,Object> queryMap){
        return this.dialogueMapper.countDialogueLatest(queryMap);
    }

    /**
     * 刷新最新消息对象处理
     * @param DialogueLatestPO
     * @param DialoguePO
     * @return
     */
    private boolean updateDialogueLatestHandler(DialogueLatestPO DialogueLatestPO,DialoguePO DialoguePO){
        if(DialoguePO.getShowType() == DialogueConstant.DIALOGUE_SHOW_TYPE_INSIDE){
            return false;
        }
        switch (DialoguePO.getShowClient()){
            case DialogueConstant.DIALOGUE_SHOW_CLIENT_PATIENT:
                DialogueLatestPO.setDoctorTimestamp(null);
                DialogueLatestPO.setDoctorMsg(null);
                break;
            case DialogueConstant.DIALOGUE_SHOW_CLIENT_DOCTOR:
                DialogueLatestPO.setPatientTimestamp(null);
                DialogueLatestPO.setPatientMsg(null);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 医生撤回消息
     * @param sid
     * @param doctorId
     * @param memberId
     */
    @Override
    public AddDialogueReturnVO recallDocDialogue(String sid, String memberId, String doctorId){
        AddDialogueReturnVO addDialogueReturnVO = new AddDialogueReturnVO();
        DialoguePO dialogue = dialogueMapper.getDialogue(sid);
        if(null!=dialogue){
            String insertDt = dialogue.getInsertDt();
            String nowDate = DateHelper.getNowDate();
            long min = DateHelper.getDistanceTimesMin(nowDate, insertDt);
            //消息发出2分钟内，可以撤回
            if(min <= 2){
                DialoguePO upDialogue=new DialoguePO();
                upDialogue.setSid(sid);
                upDialogue.setBeDelete(1);
                upDialogue.setUpdateTimestamp(System.currentTimeMillis());
                dialogueMapper.updateDialogue(upDialogue);
                //发送消息撤回系统消息
                addDialogueReturnVO = addRecallDialogue(sid,memberId,doctorId);
            }
        }
        return  addDialogueReturnVO;
    }

    //发送消息撤回系统消息
    public AddDialogueReturnVO addRecallDialogue(String sid,String memberId, String doctorId){
        DialoguePO dialoguePO = new DialoguePO();
        dialoguePO.setDoctorId(doctorId);
        DoctorSessionBO doctorModel = SessionTool.getWebSession();
        dialoguePO.setSenderId(doctorModel.getDoctorId());
        dialoguePO.setMemberId(memberId);
        dialoguePO.setForeignId(DEFAULT_FOREIGN_ID);
        dialoguePO.setMsgType(DialogueConstant.DIALOGUE_SYSTEM_MSG_TYPE);
        dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        dialoguePO.setDoctorMsg(DEFAULT_RECLAA_DOCTOR_MSG);
        dialoguePO.setPatientMsg(doctorModel.getDoctorName()+ DEFAULT_RECLAA_MEMBER_MSG);
        DefaultDialogueBO defaultDialogueBO = new DefaultDialogueBO();
        defaultDialogueBO.setContent(DEFAULT_RECLAA_DOCTOR_MSG);
        defaultDialogueBO.setTextType(DialogueConstant.DIALOGUE_SYSTEM_MSG_TEXT_TYPE_RECALL_DOCTOR);
        defaultDialogueBO.setSid(sid);
        dialoguePO.setDataStr(JSON.toJSONString(defaultDialogueBO));
        return addDialogue(dialoguePO);
    }


    @Override
    public long countAllOnlineMember(SynthesizeDataDTO synthesizeDataDTO) {
        return this.dialogueMapper.countAllOnlineMember(synthesizeDataDTO);
    }

    @Override
    public Map<String, Object> countExchange(SynthesizeDataDTO synthesizeDataDTO) {
        return this.dialogueMapper.countExchange(synthesizeDataDTO);
    }


    //

    @Override
    public List<ReplyTextPO> listReplyText(ListReplyTextDTO listReplyTextDTO) {
        HosOptionDTO dto = new HosOptionDTO();
        dto.setHospitalId(listReplyTextDTO.getHospitalId());
        dto.setPjoType(1);
        HosOptionPO optionPO = this.hospitalService.getHosOption(dto);
        JSONObject option = null;
        if(optionPO!=null && !StringUtils.isBlank(optionPO.getOptionJson())){
            option = JSONObject.parseObject(optionPO.getOptionJson());
        }
        List<ReplyTextPO> result = new ArrayList<>();
        if(option==null){
            if (null != listReplyTextDTO.getSwitchFlag() && listReplyTextDTO.getSwitchFlag()){
                //加载系统预设文案
                ListReplyTextDTO dto2 = new ListReplyTextDTO();
                dto2.setTextType(ReplyTextConstant.REPLY_TEXT_SYSTEM);
                List<ReplyTextPO> list2 = this.doctorReplyTextMapper.listReplyText(dto2);
                result.addAll(list2);
            }else{
                //加载权限为个人文案
                ListReplyTextDTO dto1 = new ListReplyTextDTO();
                dto1.setDoctorId(listReplyTextDTO.getDoctorId());
                dto1.setTextType(ReplyTextConstant.REPLY_TEXT_PERSON);
                List<ReplyTextPO> list1 = this.doctorReplyTextMapper.listReplyText(dto1);
                result.addAll(list1);

                //加载系统预设文案
                ListReplyTextDTO dto2 = new ListReplyTextDTO();
                dto2.setTextType(ReplyTextConstant.REPLY_TEXT_SYSTEM);
                List<ReplyTextPO> list2 = this.doctorReplyTextMapper.listReplyText(dto2);
                result.addAll(list2);

                //加载权限为科室文案
                ListReplyTextDTO dto3 = new ListReplyTextDTO();
                dto3.setDepartmentId(listReplyTextDTO.getDepartmentId());
                dto3.setTextType(ReplyTextConstant.REPLY_TEXT_DEPART);
                List<ReplyTextPO> list3 = this.doctorReplyTextMapper.listReplyText(dto3);
                result.addAll(list3);

                //加载权限为全院文案
                ListReplyTextDTO dto4 = new ListReplyTextDTO();
                dto4.setHospitalId(listReplyTextDTO.getHospitalId());
                dto4.setTextType(ReplyTextConstant.REPLY_TEXT_HOSPITAL);
                List<ReplyTextPO> list4 = this.doctorReplyTextMapper.listReplyText(dto4);
                result.addAll(list4);
            }
        } else {
            if("1".equals(option.getString("isLoadPersonRTxt"))){
                //加载权限为个人文案
                ListReplyTextDTO dto1 = new ListReplyTextDTO();
                dto1.setDoctorId(listReplyTextDTO.getDoctorId());
                dto1.setTextType(ReplyTextConstant.REPLY_TEXT_PERSON);
                List<ReplyTextPO> list1 = this.doctorReplyTextMapper.listReplyText(dto1);
                result.addAll(list1);
            }

            if("1".equals(option.getString("isLoadSystemRTxt"))){
                //加载系统预设文案
                ListReplyTextDTO dto2 = new ListReplyTextDTO();
                dto2.setTextType(ReplyTextConstant.REPLY_TEXT_SYSTEM);
                List<ReplyTextPO> list2 = this.doctorReplyTextMapper.listReplyText(dto2);
                result.addAll(list2);
            }

            if("1".equals(option.getString("isLoadDepartRTxt"))){
                //加载权限为科室文案
                ListReplyTextDTO dto3 = new ListReplyTextDTO();
                dto3.setDepartmentId(listReplyTextDTO.getDepartmentId());
                dto3.setTextType(ReplyTextConstant.REPLY_TEXT_DEPART);
                List<ReplyTextPO> list3 = this.doctorReplyTextMapper.listReplyText(dto3);
                result.addAll(list3);
            }

            if("1".equals(option.getString("isLoadHospitalRTxt"))){
                //加载权限为全院文案
                ListReplyTextDTO dto4 = new ListReplyTextDTO();
                dto4.setHospitalId(listReplyTextDTO.getHospitalId());
                dto4.setTextType(ReplyTextConstant.REPLY_TEXT_HOSPITAL);
                List<ReplyTextPO> list4 = this.doctorReplyTextMapper.listReplyText(dto4);
                result.addAll(list4);
            }
        }
        return result;
    }

    @Override
    public String addReplyText(ReplyTextDTO dto) {
        ReplyTextPO po = new ReplyTextPO();
        BeanUtils.copyProperties(po,dto);
        String sid = DaoHelper.getSeq();
        po.setSid(sid);
        this.doctorReplyTextMapper.addReplyText(po);
        return sid;
    }

    @Override
    public void modifyReplyText(ReplyTextDTO dto) {
        ReplyTextPO po = new ReplyTextPO();
        BeanUtils.copyProperties(po,dto);
        this.doctorReplyTextMapper.modifyReplyText(po);
    }

    @Override
    public void delReplayText(String sid) {
        this.doctorReplyTextMapper.delReplayText(sid);
    }

    @Override
    public ReplyTextPO getReplyTextById(String sid) {
        return this.doctorReplyTextMapper.getReplyTextById(sid);
    }

    /**
     * 获取患者设备信息
     * @param memberId
     * @return
     */
    private String getUseMachineInfo(String memberId){
        List<String> machineList = new ArrayList<>();
        //处理动态血糖
        long sensorCount = this.dyMemberSensorService.countMemberSensor(memberId);
        if(sensorCount > 0){
            machineList.add("1");
        }
        return Joiner.on("^").join(machineList);
    }

    //对话默认外键
    private final static String DEFAULT_FOREIGN_ID = "-1";
    //医患撤回消息，给医生的文案
    private final static String DEFAULT_RECLAA_DOCTOR_MSG = "你撤回了一条消息";
    //医患撤回消息，给患者的文案
    private final static String DEFAULT_RECLAA_MEMBER_MSG = "医生撤回了一条消息";

}
