package com.comvee.cdms.referral.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.app.doctorapp.service.DoctorServiceI;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.dto.SystemDialogueDTO;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.hospital.model.bo.*;
import com.comvee.cdms.hospital.model.dto.CheckinInfoDTO;
import com.comvee.cdms.hospital.model.dto.InHospitalLogDTO;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.AddMemberDoctorRelateDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.referral.dto.AddReferralApplyDTO;
import com.comvee.cdms.referral.dto.ListReferralApplyDTO;
import com.comvee.cdms.referral.dto.ModifyReferralApplyDTO;
import com.comvee.cdms.referral.dto.ModifyReferralSuggestDTO;
import com.comvee.cdms.referral.mapper.ReferralApplyMapper;
import com.comvee.cdms.referral.po.ReferralApplyPO;
import com.comvee.cdms.referral.po.ReferralSuggestPO;
import com.comvee.cdms.referral.service.ReferralApplyServiceI;
import com.comvee.cdms.referral.vo.H5ListReferralApplyVO;
import com.comvee.cdms.referral.vo.ReferralApplyVO;
import com.comvee.cdms.referral.vo.ReferralSuggestVO;
import com.comvee.cdms.visit.constant.VisitEventEnum;
import com.comvee.cdms.visit.dto.AddVistiEventDTO;
import com.comvee.cdms.visit.service.VisitEventService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: 李左河
 * @date: 2018/3/28 9:49.
 */
@Service("referralApply")
public class ReferralApplyServiceImpl implements ReferralApplyServiceI {

    @Autowired
    private ReferralApplyMapper referralApplyMapper;

    @Autowired
    @Qualifier("doctorAppService")
    public DoctorServiceI doctorService;

    @Autowired
    public MemberService memberService;

    @Autowired
    public DialogueService dialogueService;

    @Autowired
    public HospitalService hospitalService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private VisitEventService visitEventService;

    @Override
    public Map<String,Object> insertReferralApplyWithLock(AddReferralApplyDTO dto) {

        //申请转诊状态
        boolean referralStatus = false;
        Map<String,Object> returnMap = new HashMap<>(1);
        String sid = null;
        String doctorId = dto.getApplyDoctorId();
        ReferralApplyPO referralApplyPO = this.referralApplyMapper.getReferralApplyByMemberId(dto.getMemberId());
        if (null == referralApplyPO) {
            //转诊对象为空
            //封装转诊对象
            referralApplyPO = fullReferralApplyPO(dto);
            sid = DaoHelper.getSeq();
            referralApplyPO.setSid(sid);

            //7、新增转诊记录
            this.referralApplyMapper.insertReferralApply(referralApplyPO);
            returnMap.put("msg", "申请转诊成功");
            referralStatus=true;
            doctorId = dto.getApplyDoctorId();
        }else {
            //转诊对象不为空，判断接收状态
            Integer status = referralApplyPO.getStatus();
            if (status == 1) {
                //已接收，继续转诊到下一家医院
                referralApplyPO = fullReferralApplyPO(dto);
                referralApplyPO.setSid(referralApplyPO.getSid());
                //修改转诊记录
                this.referralApplyMapper.modifyReferral(referralApplyPO);
                returnMap.put("msg", "申请转诊成功");
                referralStatus=true;
                sid = referralApplyPO.getSid();
            } else {
                //未接受
                returnMap.put("msg", "申请转诊失败，该患者已经申请过转诊");
                referralStatus=false;
            }
        }

        //申请转诊成功
        if(referralStatus){
            //清除建议转诊
            ReferralSuggestPO po = new ReferralSuggestPO();
            po.setStatus(1);
            po.setMemberId(referralApplyPO.getMemberId());
            po.setHospitalId(referralApplyPO.getApplyHospitalId());
            this.referralApplyMapper.modifySuggestLogByMIdAndHId(po);

            //下发转诊申请成功消息-只患者可看
            String msg = "你好，"+dto.getApplyHospitalName()+"已申请将你转至"+dto.getHospitalName()+"，若"+dto.getHospitalName()+"同意该转诊申请，平台会给你发送相应通知。请耐心等候。";
            Map<String,Object> dataMap = new HashMap<String,Object>(2);
            dataMap.put("msg",msg);
            dataMap.put("textType", DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT);
            DialoguePO dialoguePO = new DialoguePO();
            dialoguePO.setDoctorId(doctorId);
            dialoguePO.setDoctorMsg(dto.getApplyDoctorName()+"为"+dto.getMemberName()+"申请转诊至"+dto.getHospitalName());
            dialoguePO.setDataStr(JSON.toJSONString(dataMap));
            dialoguePO.setMemberId(dto.getMemberId());
            dialoguePO.setMsgType(DialogueConstant.DIALOGUE_MSG_TYPE);
            dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
            dialoguePO.setPatientMsg(msg);
            dialoguePO.setSenderId(doctorId);
            dialoguePO.setSendTimestamp(System.currentTimeMillis());
            dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_PATIENT);
            dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
            dialoguePO.setUpdateTimestamp(System.currentTimeMillis());
            dialoguePO.setForeignId(sid);
            this.dialogueService.addDialogue(dialoguePO);

            //添加微信公众消息模版 20220822不入库
            /*String typeName = "下转";
            if(dto.getReferralApplyType()==1){
                typeName = "上转";
            }
            AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
            addWechatMessageDTO.setMemberId(dto.getMemberId());
            addWechatMessageDTO.setForeignId(referralApplyPO.getSid());
            addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_REFERRAL_APPLY);
            String titleText = MessageFormat.format("{0}你好,{1}已申请将你转至{2}",dto.getMemberName(),dto.getApplyHospitalName(),dto.getHospitalName());
            String team = MessageFormat.format("{0}糖尿病管理团队",dto.getApplyDoctorName());
            String foolText = MessageFormat.format("若{0}同意该转诊申请，平台会给你发送相应通知。请耐心等候。",dto.getHospitalName());
            Map<String,Object> contentMap = this.getReferralNote(titleText,"--",typeName,dto.getApplyHospitalName(),team,foolText);
            addWechatMessageDTO.setDataJson(JSONObject.toJSONString(contentMap));
            this.wechatMessageService.addWechatMessage(addWechatMessageDTO);*/
            //就诊事件
            memberVisitEventHandler(dto);

        }
        return returnMap;
    }

    private void memberVisitEventHandler(AddReferralApplyDTO dto){
        AddVistiEventDTO addVistiEventDTO = new AddVistiEventDTO();
        addVistiEventDTO.setMemberId(dto.getMemberId());
        addVistiEventDTO.setHospitalName(dto.getApplyHospitalName());
        addVistiEventDTO.setHospitalId(dto.getHospitalId());
        addVistiEventDTO.setReferralHospitalName(dto.getHospitalName());
        addVistiEventDTO.setOperatorId(dto.getDoctorId());
        addVistiEventDTO.setOperatorName(dto.getDoctorName());
        addVistiEventDTO.setEventType(VisitEventEnum.MEMBER_REFERRAL_MESSAGE.getEventType());
        visitEventService.addVisitEvent(addVistiEventDTO);
    }


    /**
     * 转诊通知信息
     * @param titleText 开头文本
     * @param heightNo 身高管理编号
     * @param type 类型 上转/下转
     * @param organ 机构
     * @param team 团队
     * @param foolText 结尾文本
     * @return
     */
    private Map<String,Object> getReferralNote(String titleText,
                                               String heightNo,
                                               String type,
                                               String organ,
                                               String team,
                                               String foolText){

        //详细内容
        //{{first.DATA}}
        //身高管理编号：{{keyword1.DATA}}
        //转诊类别：{{keyword2.DATA}}
        //接诊机构：{{keyword3.DATA}}
        //接诊团队：{{keyword4.DATA}}
        //{{remark.DATA}}

        //内容示例
        //尊敬的用户小明妈妈，您好！您有新的转诊通知。
        //身高管理编号：sggl200709041123
        //转诊类别：血常规
        //接诊机构：武侯区妇幼保健院
        //接诊团队：张大明团队
        //点击查看通知详情。
        Map<String, Object> content = new HashMap<String, Object>(2);
        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
        Map<String, String> map = new HashMap<String, String>(3);
        DateFormat df = new SimpleDateFormat(DateHelper.DATETIME_FORMAT_ZH);
        map.put("itemkey", "first");
        map.put("itemvalue", titleText);
        map.put("itemcolor", "#000000");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>(3);
        map1.put("itemkey", "keyword1");
        map1.put("itemvalue", heightNo);
        map1.put("itemcolor", "#000000");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>(3);
        map2.put("itemkey", "keyword2");
        map2.put("itemvalue", type);
        map2.put("itemcolor", "#000000");
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>(3);
        map3.put("itemkey", "keyword3");
        map3.put("itemvalue", organ);
        map3.put("itemcolor", "#000000");
        list.add(map3);
        Map<String, String> map5 = new HashMap<String, String>(3);
        map5.put("itemkey", "keyword4");
        map5.put("itemvalue", team);
        map5.put("itemcolor", "#000000");
        list.add(map5);
        Map<String, String> map4 = new HashMap<String, String>(3);
        map4.put("itemkey", "remark");
        map4.put("itemvalue", foolText);
        map4.put("itemcolor", "#000000");
        list.add(map4);
        content.put("data", list);
        content.put("topcolor", "#000000");
        return content;
    }

    /**
     * 封装转诊对象
     * @author 李左河
     * @date 2018/4/20 13:44
     * @param referralApplyDTO
     * @return com.comvee.cdms.referral.po.ReferralApplyPO
     *
     */
    private ReferralApplyPO fullReferralApplyPO(AddReferralApplyDTO referralApplyDTO) {
        ReferralApplyPO referralApplyPO = new ReferralApplyPO();

        //1、页面申请信息
        referralApplyPO.setReferralApplyType(referralApplyDTO.getReferralApplyType());
        referralApplyPO.setShowVisit(referralApplyDTO.getShowVisit());
        referralApplyPO.setHospitalId(referralApplyDTO.getHospitalId());
        referralApplyPO.setHospitalName(referralApplyDTO.getHospitalName());
        referralApplyPO.setDepartmentId(referralApplyDTO.getDepartmentId());
        referralApplyPO.setDepartmentName(referralApplyDTO.getDepartmentName());
        referralApplyPO.setDoctorId(referralApplyDTO.getDoctorId());
        referralApplyPO.setDoctorName(referralApplyDTO.getDoctorName());
        referralApplyPO.setCurrentDesc(referralApplyDTO.getCurrentDesc());
        referralApplyPO.setApplyReason(referralApplyDTO.getApplyReason());
        referralApplyPO.setMemberId(referralApplyDTO.getMemberId());

        //2、获取医生id
        String doctorId = referralApplyDTO.getApplyDoctorId();
        referralApplyPO.setApplyDoctorId(doctorId);

        //3、通过doctorId获取医生对象
        DoctorPO doctorPO = this.doctorService.loadDoctorInfo(doctorId);
        //封装医生信息
        if (null != doctorPO) {
            referralApplyPO.setApplyDoctorName(doctorPO.getDoctorName());
            referralApplyPO.setApplyDepartmentId(doctorPO.getDepartId());
            referralApplyPO.setApplyDepartmentName(doctorPO.getDepartName());
            //封装医院信息
            referralApplyPO.setApplyHospitalId(doctorPO.getHospitalId());
            referralApplyPO.setApplyHospitalName(doctorPO.getHospitalName());
        }

        //5、通过memberId,获取患者信息
        MemberPO memberPO = this.memberService.getMemberById(referralApplyDTO.getMemberId());
        if (null != memberPO) {
            Map<String,Object> map = new HashMap<String, Object>(4);
            map.put("birthday", memberPO.getBirthday());
            map.put("sex", memberPO.getSex());
            map.put("mobilePhone",memberPO.getMobilePhone());
            map.put("height",memberPO.getHeight());
            referralApplyPO.setMemberInfo(JsonSerializer.objectToJson(map));
            referralApplyPO.setMemberName(memberPO.getMemberName());
            referralApplyPO.setMobileNo(memberPO.getMobilePhone());
        }

        //6、封装默认参数
        referralApplyPO.setApplyDt(DateHelper.getDate(new Date(), DateHelper.DATETIME_FORMAT));
        //是否接收 1已接收 0未接收
        referralApplyPO.setStatus(0);
        return referralApplyPO;
    }

    @Override
    public ReferralApplyVO getReferralById(String sid) {
        ReferralApplyPO po = this.referralApplyMapper.getReferralById(sid);
        ReferralApplyVO vo = new ReferralApplyVO();
        BeanUtils.copyProperties(vo,po);
        return vo;
    }

    @Override
    public void receiveReferralWithLock(String sid,String memberId,DoctorSessionBO doctor) {
        ReferralApplyPO po = new ReferralApplyPO();
        po.setStatus(1);
        po.setSid(sid);
        this.referralApplyMapper.modifyReferral(po);
        //绑定医院关系
        memberService.memberJoinHospitalHandler(doctor, memberId);
        //接收时添加医患关系
        AddMemberDoctorRelateDTO relation = new AddMemberDoctorRelateDTO();
        relation.setDoctorId(doctor.getDoctorId());
        relation.setMemberId(memberId);
        relation.setOperatorId(doctor.getDoctorId());
        //转诊添加医患关系方式
        relation.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_REFERRAL);
        this.memberService.addMemberDoctorRelate(relation);
        MemberPO member = this.memberService.getMemberById(memberId);
        //接收时修改在院信息表
        CheckinInfoBO checkinInfo = this.hospitalService.getCheckinInfoBOByMid(memberId ,doctor.getHospitalId());
        if(checkinInfo!=null){
            checkinInfo.setCheckinStatus("1");
            checkinInfo.setOutHospitalDate(null);
            checkinInfo.setInHospitalDate(DateHelper.getToday());
            checkinInfo.setDepartmentName(doctor.getDepartName());
            checkinInfo.setDepartmentId(doctor.getDepartId());
            checkinInfo.setHospitalId(doctor.getHospitalId());
            CheckinInfoDTO updDTO = new CheckinInfoDTO();
            this.hospitalService.updateCheckinInfo(updDTO);
        }

        //接收时操作出入院记录表
        InHospitalLogBO inHospitalLog = this.hospitalService.getMemberInHospitalLogBOByMid(memberId);
        boolean addInhospitalLog = true;
        if(inHospitalLog!=null){
            if(StringUtils.isBlank(inHospitalLog.getHospitalId())){
                addInhospitalLog = false;
                inHospitalLog.setHospitalId(doctor.getHospitalId());
                inHospitalLog.setInHospitalDate(DateHelper.getToday());
            }
            else if(inHospitalLog.getHospitalId().equals(doctor.getHospitalId())){
                addInhospitalLog = false;
                inHospitalLog.setInHospitalDate(DateHelper.getToday());
            } else {
                inHospitalLog.setOutHospitalDate(DateHelper.getToday());
                inHospitalLog.setInStatus(0);
            }
            InHospitalLogDTO updDTO = new InHospitalLogDTO();
            BeanUtils.copyProperties(updDTO,addInhospitalLog);
            this.hospitalService.updateInHospitalLog(updDTO);
        }
        if(addInhospitalLog){
            inHospitalLog = new InHospitalLogBO();
            inHospitalLog.setSid(DaoHelper.getSeq());
            inHospitalLog.setHospitalId(doctor.getHospitalId());
            inHospitalLog.setMemberId(memberId);
            inHospitalLog.setDepartmentId(doctor.getDepartId());
            inHospitalLog.setInStatus(1);
            inHospitalLog.setDepartmentName(doctor.getDepartName());
            inHospitalLog.setMemberName(member.getMemberName());
            inHospitalLog.setDoctorId(doctor.getDoctorId());
            inHospitalLog.setSex(member.getSex());
            inHospitalLog.setInHospitalDate(DateHelper.getToday());
            InHospitalLogDTO addDTO = new InHospitalLogDTO();
            BeanUtils.copyProperties(addDTO,addInhospitalLog);
            this.hospitalService.addInHospitalLog(addDTO);
        }

        //发送系统消息app对话
        SystemDialogueDTO systemDialogueDTO = new SystemDialogueDTO();
        systemDialogueDTO.setContent("你好，现在可以开始交流了");
        systemDialogueDTO.setTextType(DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT);
        DialoguePO addDialogueDTO = new DialoguePO();
        addDialogueDTO.setDoctorId(doctor.getDoctorId());
        addDialogueDTO.setMemberId(memberId);
        addDialogueDTO.setSenderId(doctor.getDoctorId());
        addDialogueDTO.setDoctorMsg("你好，现在可以开始交流了");
        addDialogueDTO.setPatientMsg("你好，现在可以开始交流了");
        addDialogueDTO.setSendTimestamp(System.currentTimeMillis());
        addDialogueDTO.setUpdateTimestamp(System.currentTimeMillis());
        addDialogueDTO.setMsgType(DialogueConstant.DIALOGUE_SYSTEM_MSG_TYPE);
        addDialogueDTO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        addDialogueDTO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        addDialogueDTO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        addDialogueDTO.setForeignId("-1");
        addDialogueDTO.setDataStr(JSON.toJSONString(systemDialogueDTO));
        addDialogueDTO.setBeDelete(0);
        this.dialogueService.addDialogue(addDialogueDTO);

        //下发转诊被接收通知-只患者可看
        String msg = "你好，"+doctor.getHospitalName()+doctor.getDepartName()+doctor.getDoctorName()+"医生已同意您的转诊请求，请你进去携带相关材料到院就诊。";
        Map<String,Object> dataMap = new HashMap<String,Object>(2);
        dataMap.put("msg",msg);
        dataMap.put("textType",DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT);
        DialoguePO dialogueModel = new DialoguePO();
        dialogueModel.setDoctorId(doctor.getDoctorId());
        dialogueModel.setDataStr(JSON.toJSONString(dataMap));
        dialogueModel.setDoctorMsg(msg);
        dialogueModel.setMemberId(memberId);
        dialogueModel.setMsgType(DialogueConstant.DIALOGUE_MSG_TYPE);
        dialogueModel.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        dialogueModel.setPatientMsg(msg);
        dialogueModel.setSenderId(doctor.getDoctorId());
        dialogueModel.setSendTimestamp(System.currentTimeMillis());
        dialogueModel.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_PATIENT);
        dialogueModel.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        dialogueModel.setUpdateTimestamp(System.currentTimeMillis());
        dialogueModel.setForeignId(sid);
        this.dialogueService.addDialogue(dialogueModel);

        //添加微信公众消息模版 20220822不入库
       /* AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(memberId);
        addWechatMessageDTO.setForeignId(sid);
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_REFERRAL_SUCCESS);
        String titleText = MessageFormat.format("亲爱的{0}你好，{1}{2}{3}医生已同意您的转诊请求",member.getMemberName(),doctor.getHospitalName(),doctor.getDepartName(),doctor.getDoctorName());
        Map<String,Object> contentMap = this.getReferral(titleText,doctor.getDoctorName(),DateHelper.getToday(),"请你进去携带相关材料到院就诊。");
        addWechatMessageDTO.setDataJson(JSONObject.toJSONString(contentMap));
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);*/
    }
    /**
     * 转诊成功信息
     * @param titleText 开头文本
     * @param doctorName 医生名称
     * @param datetime 接收日期
     * @param foolText 结尾文本
     * @return
     */
    private Map<String,Object> getReferral(String titleText,
                                           String doctorName,
                                           String datetime,
                                           String foolText){

        Map<String, Object> content = new HashMap<String, Object>(2);
        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
        Map<String, String> map = new HashMap<String, String>(3);
        DateFormat df = new SimpleDateFormat(DateHelper.DAY_FORMAT_ZH);
        map.put("itemkey", "first");
        map.put("itemvalue", titleText);
        map.put("itemcolor", "#000000");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>(3);
        map1.put("itemkey", "keyword1");
        map1.put("itemvalue", doctorName);
        map1.put("itemcolor", "#000000");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>(3);
        map2.put("itemkey", "keyword2");
        map2.put("itemvalue", df.format(DateHelper.getDate(datetime,DateHelper.DAY_FORMAT)));
        map2.put("itemcolor", "#000000");
        list.add(map2);

        Map<String, String> map4 = new HashMap<String, String>(3);
        map4.put("itemkey", "remark");
        map4.put("itemvalue", foolText);
        map4.put("itemcolor", "#000000");
        list.add(map4);
        content.put("data", list);
        content.put("topcolor", "#000000");
        return content;
    }

    @Override
    public void modifyReferral(ModifyReferralApplyDTO dto) {
        ReferralApplyPO po = new ReferralApplyPO();
        BeanUtils.copyProperties(po,dto);
        this.referralApplyMapper.modifyReferral(po);
    }

    @Override
    public PageResult<ReferralApplyVO> listReferralPage(ListReferralApplyDTO dto, PageRequest pager) {
        if(!StringUtils.isBlank(dto.getStartDt()) && dto.getStartDt().length()<11){
            dto.setStartDt(dto.getStartDt()+DateHelper.DEFUALT_TIME_START);
        }
        if(!StringUtils.isBlank(dto.getEndDt()) && dto.getEndDt().length()<11){
            dto.setEndDt(dto.getEndDt()+DateHelper.DEFUALT_TIME_END);
        }
        PageHelper.startPage(pager.getPage(),pager.getRows());
        List<ReferralApplyPO> list = this.referralApplyMapper.listReferralByWhere(dto);
        PageResult<ReferralApplyPO> poPageResult = new PageResult<>(list);
        List<ReferralApplyVO> vos = new ArrayList<>();
        list.forEach(item->{
            ReferralApplyVO vo = new ReferralApplyVO();
            BeanUtils.copyProperties(vo,item);
            //患者手机号脱敏
            vo.setMobileNo(ValidateTool.tuoMin(item.getMobileNo(),3,4,"*"));
            vos.add(vo);
        });
        PageResult<ReferralApplyVO> pageResult = new PageResult<ReferralApplyVO>(vos);
        pageResult.setTotalRows(poPageResult.getTotalRows());
        pageResult.setPageNum(poPageResult.getPageNum());
        pageResult.setPageSize(poPageResult.getPageSize());
        pageResult.setTotalPages(poPageResult.getTotalPages());
        return pageResult;
    }

    @Override
    public PageResult<ReferralSuggestVO> listSuggestReferral(String hospitalId, PageRequest pager) {
        Integer referralType = 1;//上转标识
        HospitalPO hospital = hospitalService.getHospital(hospitalId);
        //县级以上只查下转，县级以下只查上转
        if (null != hospital && null != hospital.getLevel() && hospital.getLevel() >= 4){
            referralType = 2;
        }
        PageHelper.startPage(pager.getPage(),pager.getRows());
        List<ReferralSuggestPO> list = this.referralApplyMapper.listSuggestReferralByWhere(hospitalId,referralType);
        PageResult<ReferralSuggestPO> poPageResult = new PageResult<ReferralSuggestPO>(list);
        List<ReferralSuggestVO> vos = new ArrayList<>();
        list.forEach(item->{
            ReferralSuggestVO vo = new ReferralSuggestVO();
            BeanUtils.copyProperties(vo,item);
            vos.add(vo);
        });
        if(vos!=null && vos.size()>0){
            List<String> memberIds = new ArrayList<>(vos.size());
            for(ReferralSuggestVO vo : vos){
                memberIds.add(vo.getMemberId());
            }

            List<MemberPO> memberPOS = this.memberService.listMemberByIdList(memberIds);
            if(memberPOS!=null){
                for(ReferralSuggestVO vo : vos){
                    for(MemberPO member : memberPOS){
                        if(member.getMemberId().equals(vo.getMemberId())){
                            vo.setMemberName(member.getMemberName());
                            //患者手机号脱敏
                            vo.setMobileNo(ValidateTool.tuoMin(member.getMobilePhone(),3,4,"*"));
                        }
                    }
                }
            }
        }
        PageResult<ReferralSuggestVO> pageResult = new PageResult<ReferralSuggestVO>(vos);
        pageResult.setTotalRows(poPageResult.getTotalRows());
        pageResult.setPageNum(poPageResult.getPageNum());
        pageResult.setPageSize(poPageResult.getPageSize());
        pageResult.setTotalPages(poPageResult.getTotalPages());
        return pageResult;
    }

    @Override
    public void hlSuggestReferralLog(String sid) {
        ReferralSuggestPO po = new ReferralSuggestPO();
        po.setSid(sid);
        po.setStatus(-1);
        this.referralApplyMapper.modifySuggestReferralLog(po);
    }

    @Override
    public void modifySuggestReferralLog(ModifyReferralSuggestDTO dto) {
        ReferralSuggestPO po = new ReferralSuggestPO();
        BeanUtils.copyProperties(po,dto);
        this.referralApplyMapper.modifySuggestReferralLog(po);
    }

    @Override
    public PageResult<H5ListReferralApplyVO> listReferralPageForH5(ListReferralApplyDTO dto, PageRequest pager) {
        if(!StringUtils.isBlank(dto.getStartDt()) && dto.getStartDt().length()<11){
            dto.setStartDt(dto.getStartDt()+DateHelper.DEFUALT_TIME_START);
        }
        if(!StringUtils.isBlank(dto.getEndDt()) && dto.getEndDt().length()<11){
            dto.setEndDt(dto.getEndDt()+DateHelper.DEFUALT_TIME_END);
        }
        dto.setStatus(1);
        PageHelper.startPage(pager.getPage(),pager.getRows());
        List<H5ListReferralApplyVO> list = this.referralApplyMapper.listReferralByWhereForH5(dto);
        if (!list.isEmpty()){
            list.forEach(e->e.setAge(String.valueOf(DateHelper.getAge(e.getBirthday()))));
        }
        return new PageResult<>(list);
    }

}
