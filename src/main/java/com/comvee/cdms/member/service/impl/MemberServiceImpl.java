package com.comvee.cdms.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.checkresult.dto.GetMemberCheckoutDTO;
import com.comvee.cdms.checkresult.mapper.InspectionMapper;
import com.comvee.cdms.checkresult.po.InspectionPO;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.clinicaldiagnosis.constant.YzConstant;
import com.comvee.cdms.clinicaldiagnosis.dto.ListMemberYzDTO;
import com.comvee.cdms.clinicaldiagnosis.service.YzServiceI;
import com.comvee.cdms.common.cfg.HxErrorCodeConstant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.complication.model.bo.PatientSyncBO;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.complication.service.ScreeningStatsService;
import com.comvee.cdms.consultation.model.po.RemoteConsultationPO;
import com.comvee.cdms.consultation.service.RemoteConsultationService;
import com.comvee.cdms.dialogue.bo.DefaultDialogueBO;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.differentlevels.service.DifferentLevelsService;
import com.comvee.cdms.differentlevels.vo.MemberCurrentDiffLevelVO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.cfg.DoctorConstant;
import com.comvee.cdms.doctor.cfg.GroupConstant;
import com.comvee.cdms.doctor.dto.ListGroupsDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.doctor.vo.DoctorDepartVO;
import com.comvee.cdms.doctor.vo.DoctorGroupVO;
import com.comvee.cdms.doctor.vo.GroupsVO;
import com.comvee.cdms.dybloodpressure.dto.GetDyBloodPressureDiaryDTO;
import com.comvee.cdms.dybloodpressure.mapper.DyBloodPressureDetailMapper;
import com.comvee.cdms.dybloodpressure.mapper.DyBloodPressureMapper;
import com.comvee.cdms.dybloodpressure.po.DyBloodPressureDetailPO;
import com.comvee.cdms.dybloodpressure.po.DyBloodPressurePO;
import com.comvee.cdms.dybloodpressure.service.DyBloodPressureService;
import com.comvee.cdms.dybloodsugar.po.DYMemberSensorPO;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.follow.po.FollowupPO;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.foot.constant.FootConstant;
import com.comvee.cdms.foot.dto.ListFootDTO;
import com.comvee.cdms.foot.po.FootPO;
import com.comvee.cdms.foot.service.FootService;
import com.comvee.cdms.hospital.model.bo.CheckinInfoBO;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.level.constant.MemberLevelConstant;
import com.comvee.cdms.level.dto.CurrentGxyLevelDTO;
import com.comvee.cdms.level.dto.ListMemberLevelDTO;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.member.bo.*;
import com.comvee.cdms.member.constant.*;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.mapper.MemberCheckinInfoMapper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.*;
import com.comvee.cdms.member.service.InHospitalMemberService;
import com.comvee.cdms.member.service.MemberCacheServiceI;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.service.ResearchGroupService;
import com.comvee.cdms.member.tool.MemberArchivesTuoMinHelper;
import com.comvee.cdms.member.tool.MemberHelper;
import com.comvee.cdms.member.tool.MemberRangeHelper;
import com.comvee.cdms.member.vo.*;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.packages.dto.ListValidMemberPackageDTO;
import com.comvee.cdms.packages.po.MemberPackagePO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.questionnaire.model.dto.ListQuestionnaireDTO;
import com.comvee.cdms.questionnaire.service.QuestionnaireService;
import com.comvee.cdms.sign.bo.BloodSugarInHosBO;
import com.comvee.cdms.sign.dto.CountBloodSugarDTO;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import com.comvee.cdms.virtualward.mapper.VirtualWardMapper;
import com.comvee.cdms.virtualward.model.po.VirtualWardPO;
import com.comvee.cdms.visit.constant.VisitEventEnum;
import com.comvee.cdms.visit.dto.AddVistiEventDTO;
import com.comvee.cdms.visit.service.VisitEventService;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2018/9/30
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {

    private final static Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    @Lazy
    private DoctorServiceI doctorService;

    @Autowired
    @Lazy
    private DialogueService dialogueService;

    @Autowired
    @Lazy
    private FollowServiceI followService;

    @Autowired
    @Lazy
    private BloodSugarService bloodSugarService;

    @Autowired
    @Lazy
    private WechatMessageService wechatMessageService;

    @Autowired
    @Lazy
    private PackageService packageService;

    @Autowired
    @Lazy
    private FootService footService;

    @Autowired
    @Lazy
    private ScreeningService screeningService;

    @Autowired
    @Lazy
    private ScreeningStatsService screeningStatsService;

    @Autowired
    @Lazy
    private HospitalService hospitalService;

    @Autowired
    @Lazy
    private YzServiceI yzService;

    @Autowired
    @Lazy
    private AuthorityService authorityService;

    @Autowired
    @Lazy
    private QuestionnaireService questionnaireService;

    @Autowired
    @Lazy
    private DifferentLevelsService differentLevelsService;

    @Autowired
    @Lazy
    private MemberLevelService memberLevelService;

    @Autowired
    private MemberCacheServiceI memberCacheService;

    @Autowired
    @Lazy
    private DyMemberSensorService dyMemberSensorService;

    @Autowired
    private VirtualWardMapper virtualWardMapper;

    @Autowired
    @Lazy
    private DyBloodSugarService dyBloodSugarService;

    @Autowired
    private MemberCheckinInfoMapper memberCheckinInfoMapper;

    @Autowired
    @Lazy
    private RemoteConsultationService remoteConsultationService;

    @Autowired
    @Lazy
    private ResearchGroupService researchGroupService;

    @Autowired
    @Lazy
    private InHospitalMemberService inHospitalMemberService;

    @Autowired
    @Lazy
    private MemberService memberService;

    @Autowired
    private MemberManageServiceImpl manageService;

    @Autowired
    private VisitEventService visitEventService;

    @Autowired
    private CheckoutDetailServiceI checkoutDetailServiceI;

    @Autowired
    private DyBloodPressureDetailMapper dyBloodPressureDetailMapper;

    @Autowired
    private DyBloodPressureService dyBloodPressureService;

    @Autowired
    private DyBloodPressureMapper dyBloodPressureMapper;

    @Autowired
    private InspectionMapper inspectionMapper;


    @Override
    public String addMember(AddMemberDTO addMemberDTO,  DoctorSessionBO doctorSessionBO) {
        existCheck(addMemberDTO);
        String memberId = DaoHelper.getSeq();
        AddMemberMapperDTO addMemberMapperDTO = new AddMemberMapperDTO();
        BeanUtils.copyProperties(addMemberMapperDTO, addMemberDTO);
        addMemberMapperDTO.setMemberId(memberId);
        addMemberMapperDTO.setMemberNamePy(Pinyin4jUtil.getPinYin(addMemberMapperDTO.getMemberName()));
        addMemberMapperDTO.setMemberNamePys(Pinyin4jUtil.getPinYinHeadChar(addMemberMapperDTO.getMemberName()));
        if(StringUtils.isBlank(addMemberMapperDTO.getDiabetesDate())){
            addMemberMapperDTO.setDiabetesDate(null);
        }
        if (StringUtils.isBlank(addMemberDTO.getIsDiabetes()) && !StringUtils.isBlank(addMemberDTO.getDiabetesType())){
            String dType = addMemberDTO.getDiabetesType();
            if (DiabetesConstant.DIABETES_TYPE_ONE.equals(dType) || DiabetesConstant.DIABETES_TYPE_TWO.equals(dType)
                    || DiabetesConstant.DIABETES_TYPE_RS.equals(dType) || DiabetesConstant.DIABETES_TYPE_OTHER.equals(dType)
                    || DiabetesConstant.DIABETES_TYPE_SPECIAL.equals(dType)){
                addMemberMapperDTO.setIsDiabetes(DiabetesConstant.DIABETES_YES);
            }
        }
        //添加患者来源
        Integer memberSource = addMemberDTO.getMemberSource() == null ? MemberSourceConstant.MMEMBER_SOURCE_OTHER : addMemberDTO.getMemberSource();
        addMemberMapperDTO.setMemberSource(memberSource);
        if (!("1".equals(addMemberMapperDTO.getFat()))){
            addMemberMapperDTO.setFat("0");
        }
        this.memberMapper.addMember(addMemberMapperDTO);

        //就诊号不为空则新增就诊卡号记录
        if(!StringUtils.isBlank(addMemberDTO.getVisitNo()) && doctorSessionBO != null){
            AddMemberVisitCardDTO addMemberVisitCardDTO = new AddMemberVisitCardDTO();
            addMemberVisitCardDTO.setHospitalId(doctorSessionBO.getHospitalId());
            addMemberVisitCardDTO.setMemberId(memberId);
            addMemberVisitCardDTO.setVisitNo(addMemberDTO.getVisitNo());
            addMemberVisitCard(addMemberVisitCardDTO);
        }
        memberArchivementHandler(doctorSessionBO,addMemberMapperDTO);
        //患者端不关联医院
        if (null != addMemberDTO.getMemberSource() &&
                !addMemberDTO.getMemberSource().equals(MemberSourceConstant.MEMBER_SOURCE_WECHAT)){
            memberService.memberJoinHospitalHandler(doctorSessionBO, memberId);
            //就诊事件
            this.memberVisitEventHandler(doctorSessionBO, memberId);
        }
        return memberId;
    }

    //添加or更新患者档案
    private void memberArchivementHandler(DoctorSessionBO doctorSessionBO,AddMemberMapperDTO addMemberMapperDTO){
        if(doctorSessionBO!=null){
            Map<String,Object> archivesMap=new HashMap<>();
            Map<String,Object> anamnesis=new HashMap<>();
            anamnesis.put("essential_hyp",addMemberMapperDTO.getEssentialHyp());
            archivesMap.put("anamnesis",anamnesis);
            Map<String,Object> basic = new HashMap<>();
            basic.put("fat",addMemberMapperDTO.getFat());
            archivesMap.put("basic",basic);
            MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
            memberArchivesDTO.setMemberId(addMemberMapperDTO.getMemberId());
            memberArchivesDTO.setDoctorId(doctorSessionBO.getDoctorId());
            memberArchivesDTO.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
            this.updateMemberArchive(memberArchivesDTO);
        }
    }

    private void memberVisitEventHandler(DoctorSessionBO doctorSessionBO, String memberId){
        if (null != doctorSessionBO){
            AddVistiEventDTO addVistiEventDTO = new AddVistiEventDTO();
            addVistiEventDTO.setMemberId(memberId);
            addVistiEventDTO.setHospitalName(doctorSessionBO.getHospitalName());
            addVistiEventDTO.setHospitalId(doctorSessionBO.getHospitalId());
            addVistiEventDTO.setDepartmentName(doctorSessionBO.getDepartName());
            addVistiEventDTO.setOperatorId(doctorSessionBO.getDoctorId());
            addVistiEventDTO.setOperatorName(doctorSessionBO.getDoctorName());
            addVistiEventDTO.setEventType(VisitEventEnum.MEMBER_JOIN_HOSPITAL.getEventType());
            visitEventService.addVisitEvent(addVistiEventDTO);
        }
    }

    /**
     * 患者注册- 唯一数据是否存在判断
     * @param addMemberDTO
     */
    private void existCheck(AddMemberDTO addMemberDTO){
        GetMemberDTO getMemberDTO;
        MemberPO memberPO;
/*        if(!StringUtils.isBlank(addMemberDTO.getMobilePhone())){
            getMemberDTO = new GetMemberDTO();
            getMemberDTO.setMobilePhone(addMemberDTO.getMobilePhone());
            memberPO = getMember(getMemberDTO);
            if(memberPO != null){
                throw new BusinessException("手机号码已被使用，请确认~");
            }
        }*/
        if(!StringUtils.isBlank(addMemberDTO.getIdCard())){
            getMemberDTO = new GetMemberDTO();
            getMemberDTO.setIdCard(addMemberDTO.getIdCard());
            memberPO = getMember(getMemberDTO);
            if(memberPO != null){
                throw new BusinessException("身份证已被使用，请确认~");
            }
        }
    }

    @Override
    @CacheEvict(value = "public", key = "'member' + #updateMemberDTO.memberId")
//    @Transactional(propagation = Propagation.NESTED ,rollbackFor = Exception.class)
    public void updateMember(UpdateMemberDTO updateMemberDTO) {
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(updateMemberDTO.getMemberId());
        MemberPO memberPO = getMember(getMemberDTO);
        if(memberPO == null){
            throw new BusinessException("该患者不存在，请确认");
        }
        updateMemberDesensitizationFieldHandler(updateMemberDTO);
//        mobileExistCheck(memberPO, updateMemberDTO);
        idCardExistCheck(memberPO, updateMemberDTO);
        //姓名拼音
        if(!StringUtils.isBlank(updateMemberDTO.getMemberName())){
            updateMemberDTO.setMemberNamePy(Pinyin4jUtil.getPinYin(updateMemberDTO.getMemberName()));
            updateMemberDTO.setMemberNamePys(Pinyin4jUtil.getPinYinHeadChar(updateMemberDTO.getMemberName()));
        }
        //是否有糖尿病
        if (StringUtils.isBlank(updateMemberDTO.getIsDiabetes()) && !StringUtils.isBlank(updateMemberDTO.getDiabetesType())){
            String dType = updateMemberDTO.getDiabetesType();
            if (DiabetesConstant.DIABETES_TYPE_ONE.equals(dType) || DiabetesConstant.DIABETES_TYPE_TWO.equals(dType)
                    || DiabetesConstant.DIABETES_TYPE_RS.equals(dType) || DiabetesConstant.DIABETES_TYPE_OTHER.equals(dType)
                    || DiabetesConstant.DIABETES_TYPE_SPECIAL.equals(dType)){
                updateMemberDTO.setIsDiabetes(DiabetesConstant.DIABETES_YES);
            }
        }
        this.memberMapper.updateMember(updateMemberDTO);
        if (!StringUtils.isBlank(updateMemberDTO.getFat())){
            List<MemberArchivesPO> memberArchivesPOS = this.memberMapper.listMemberArchives(updateMemberDTO.getMemberId(),"");
            for (MemberArchivesPO memberArchivesPO:memberArchivesPOS) {
                Map<String, Object> archivesJsonmap = new HashMap<>();
                if (memberArchivesPO != null && !StringUtils.isBlank(memberArchivesPO.getArchivesJson())) {
                    archivesJsonmap = JSON.parseObject(memberArchivesPO.getArchivesJson());
                }
                Map<String, Object> basicjson = new HashMap<>();
                if (archivesJsonmap.get("basic") != null) {
                    basicjson = JsonSerializer.jsonToMap(archivesJsonmap.get("basic").toString());
                }
                basicjson.put("fat", updateMemberDTO.getFat());
                archivesJsonmap.put("basic", basicjson);
                MemberArchivesDTO memberArchivesDTO = new MemberArchivesDTO();
                memberArchivesDTO.setMemberId(memberArchivesPO.getMemberId());
                memberArchivesDTO.setArchivesJson(archivesJsonmap.toString());
                memberService.updateMemberArchive(memberArchivesDTO);
            }
        }
        //修改患者 医患关系的修改时间戳
        UpdateDoctorMemberDTO updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
        updateDoctorMemberDTO.setMemberId(memberPO.getMemberId());
        updateDoctorMemberDTO.setModifyTimeStamp(String.valueOf(System.currentTimeMillis()));
        this.memberMapper.updateDoctorMember(updateDoctorMemberDTO);
    }


    /**
     * 判断手机是否存在
     * @param memberPO
     * @param updateMemberDTO
     */
    private void mobileExistCheck(MemberPO memberPO, UpdateMemberDTO updateMemberDTO){
        if(StringUtils.isBlank(updateMemberDTO.getMobilePhone())){
            return;
        }
        if(updateMemberDTO.getMobilePhone().equals(memberPO.getMobilePhone())){
            return;
        }
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMobilePhone(updateMemberDTO.getMobilePhone());
        MemberPO member = getMember(getMemberDTO);
        if(member != null){
            throw new BusinessException("已存在的手机号码，请确认");
        }
    }

    /**
     * 判断身份证是否存在
     * @param memberPO
     * @param updateMemberDTO
     */
    private void idCardExistCheck(MemberPO memberPO, UpdateMemberDTO updateMemberDTO){
        if(StringUtils.isBlank(updateMemberDTO.getIdCard())){
            return;
        }
        if(updateMemberDTO.getIdCard().equals(memberPO.getIdCard())){
            return;
        }
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setIdCard(updateMemberDTO.getIdCard());
        MemberPO member = getMember(getMemberDTO);
        if(member != null){
            throw new BusinessException("已存在的身份证，请确认");
        }
    }

    @Override
    public String addMemberWeb(AddMemberWebDTO addMemberWebDTO) {
        String memberId = "";
        GetMemberDTO getMemberDTO=new GetMemberDTO();
        getMemberDTO.setIdCard(addMemberWebDTO.getIdCard());
        MemberPO member =  this.getMember(getMemberDTO);
        if(null==member){
            //新增患者，绑定医患关系
            memberId = DaoHelper.getSeq();
            AddMemberMapperDTO addMemberMapperDTO = new AddMemberMapperDTO();
            BeanUtils.copyProperties(addMemberMapperDTO, addMemberWebDTO);
            addMemberMapperDTO.setMemberId(memberId);
            addMemberMapperDTO.setMemberNamePy(Pinyin4jUtil.getPinYin(addMemberMapperDTO.getMemberName()));
            addMemberMapperDTO.setMemberNamePys(Pinyin4jUtil.getPinYinHeadChar(addMemberMapperDTO.getMemberName()));
            Integer memberSource = addMemberWebDTO.getMemberSource() == null ? MemberSourceConstant.MMEMBER_SOURCE_OTHER : addMemberWebDTO.getMemberSource();
            addMemberMapperDTO.setMemberSource(memberSource);
            this.memberMapper.addMember(addMemberMapperDTO);

            AddMemberDoctorRelateDTO addMemberDoctorRelateDTO=new AddMemberDoctorRelateDTO();
            addMemberDoctorRelateDTO.setDoctorId(addMemberWebDTO.getDoctorId());
            addMemberDoctorRelateDTO.setGroupId("0");
            addMemberDoctorRelateDTO.setMemberId(memberId);
            addMemberDoctorRelateDTO.setOperatorId(addMemberWebDTO.getOperatorId());
            addMemberDoctorRelateDTO.setRelationWay(addMemberWebDTO.getRelationWay());
            addMemberDoctorRelate(addMemberDoctorRelateDTO);
        }else{
            //绑定医患关系
            AddMemberDoctorRelateDTO addMemberDoctorRelateDTO=new AddMemberDoctorRelateDTO();
            addMemberDoctorRelateDTO.setDoctorId(addMemberWebDTO.getDoctorId());
            addMemberDoctorRelateDTO.setGroupId("0");
            addMemberDoctorRelateDTO.setMemberId(member.getMemberId());
            addMemberDoctorRelateDTO.setOperatorId(addMemberWebDTO.getOperatorId());
            addMemberDoctorRelateDTO.setRelationWay(addMemberWebDTO.getRelationWay());
            this.addMemberDoctorRelate(addMemberDoctorRelateDTO);
            memberId = member.getMemberId();
        }
        return memberId;
    }

    @Override
    public PageResult<MemberListPO> listMember(ListMemberDTO listMemberDTO, PageRequest pr) {
/*        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        listMemberDTO.setHospitalId(doctorSessionBO.getHospitalId());*/
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<MemberListPO> list = this.memberMapper.listMember(listMemberDTO);
        PageResult<MemberListPO> poPageResult = new PageResult<>(list);
        String hospitalId = null;
        if(StringUtils.isBlank(listMemberDTO.getDoctorId()) && listMemberDTO.getDoctorIdList()!=null
                &&listMemberDTO.getDoctorIdList().size()>0){
            hospitalId = getHospitalId(listMemberDTO.getDoctorIdList().get(0));
        } else {
            hospitalId = getHospitalId(listMemberDTO.getDoctorId());
        }
        for(MemberListPO po : poPageResult.getRows()){
            MemberCurrentDiffLevelVO vo = differentLevelsService.getMemberCurrentDiffLevelResult(po.getMemberId(),hospitalId);
            if(vo==null || vo.getLayer()==null){
                po.setLayer(0);
            } else {
                po.setLayer(vo.getLayer());
            }
        }
        return poPageResult;
    }

    @Override
    public List<MemberPO> listMemberByWhere(MemberParamsDTO memberParamsDTO) {
        List<MemberPO> list = this.memberMapper.listMemberByWhere(memberParamsDTO);
        return list;
    }

    @Override
    public MemberPO getMember(GetMemberDTO getMemberDTO) {
        MemberPO memberPO = this.memberMapper.getMember(getMemberDTO);
        if(memberPO == null){
            return null;
        }
        //年龄
        String birthday = memberPO.getBirthday();
        if (null != birthday && !StringUtils.isBlank(birthday)) {
            int age = DateHelper.getAge(birthday);
            memberPO.setAge(age);
        }
        if(!StringUtils.isBlank(getMemberDTO.getHospitalId())){
            MemberVisitCardDTO memberVisitCardDTO = new MemberVisitCardDTO();
            memberVisitCardDTO.setHospitalId(getMemberDTO.getHospitalId());
            memberVisitCardDTO.setMemberId(memberPO.getMemberId());
            MemberVisitCardPO memberVisitCardPO = getMemberVisitCard(memberVisitCardDTO);
            if(memberVisitCardPO != null){
                memberPO.setVisitNo(memberVisitCardPO.getVisitNo());
            }

            MemberCurrentDiffLevelVO memberCurrentDiffLevelVO = this.differentLevelsService.getMemberCurrentDiffLevelResult(memberPO.getMemberId(),getMemberDTO.getHospitalId());
            if(memberCurrentDiffLevelVO!=null){
                memberPO.setLevelHx(memberCurrentDiffLevelVO.getLayer()+"");
                memberPO.setDsmeHx(memberCurrentDiffLevelVO.getLevel()+"");
            } else {
                memberPO.setLevelHx(null);
                memberPO.setDsmeHx(null);
            }
            //住院号、床号
            MemberCheckinInfoPO memberCheckinInfo = memberCheckinInfoMapper.getMemberCheckinInfoByMemberId2(getMemberDTO.getMemberId(), getMemberDTO.getHospitalId());
            if (memberCheckinInfo!=null){
                memberPO.setHospitalNo(memberCheckinInfo.getHospitalNo());
                memberPO.setBedNo(memberCheckinInfo.getBedNo());
            }
        }
        return memberPO;
    }


    @Override
    public List<MemberPO> getMemberByInfo(GetMemberDTO getMemberDTO) {
        List<MemberPO> memberByInfo = this.memberMapper.getMemberByInfo(getMemberDTO);
        if (memberByInfo.size() == 0){
            return null;
        }
        return memberByInfo;
    }

    @Override
    public MemberPO getMemberByVisitNo(MemberVisitCardDTO memberVisitCardDTO) {
        MemberVisitCardPO memberVisitCardPO = this.memberMapper.getMemberVisitCard(memberVisitCardDTO);
        if(memberVisitCardPO == null){
            return null;
        }
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(memberVisitCardPO.getMemberId());
        return this.memberMapper.getMember(getMemberDTO);
    }

    @Override
    public MemberPO getMemberByIdOrSocialCard(GetMemberDTO getMemberDTO) {
        String socialCard = getMemberDTO.getSocialCard();
        String idCard = getMemberDTO.getIdCard();
        MemberPO member = null;
        //用身份证查
        if(!StringUtils.isBlank(idCard)){
            getMemberDTO.setSocialCard(null);
            member = this.memberMapper.getMember(getMemberDTO);
        }
        //身份证为空或者查不到
        if (null == member && !StringUtils.isBlank(socialCard)){
            getMemberDTO.setIdCard(null);
            getMemberDTO.setSocialCard(socialCard);
            member = this.memberMapper.getMember(getMemberDTO);
        }
        return member;
    }

    @Override
    public boolean addMemberDoctorRelate(AddMemberDoctorRelateDTO addMemberDoctorRelateDTO) {
        //TODO 判断该患者是否已经在该医生所在的医院已经添加团队了
/*        if(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_WEB_UPDATEGROUP!=addMemberDoctorRelateDTO.getRelationWay()){
            String hospitalId = this.getHospitalId(addMemberDoctorRelateDTO.getDoctorId());
            DoctorMemberPO doctorMember = this.memberMapper.getDoctorMemberByHospital(addMemberDoctorRelateDTO.getMemberId(),
                    hospitalId);
            if(doctorMember!=null){
                DoctorPO doctor = this.doctorService.getDoctorById(doctorMember.getDoctorId());
                throw new BusinessException("该患者已经存在本院的"+doctor.getDoctorName()+"的团队下了，请先解除。");
            }
        }*/

        //判断是否已经添加过的医患关系
        CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
        countDoctorMemberDTO.setMemberId(addMemberDoctorRelateDTO.getMemberId());
        countDoctorMemberDTO.setDoctorId(addMemberDoctorRelateDTO.getDoctorId());
        long count = this.memberMapper.countMemberDoctor(countDoctorMemberDTO);
        if(count > 0){
            return false;
        }
        //判断患者是否第一次创建医患关系
        countDoctorMemberDTO = new CountDoctorMemberDTO();
        countDoctorMemberDTO.setMemberId(addMemberDoctorRelateDTO.getMemberId());
        count = this.memberMapper.countMemberDoctor(countDoctorMemberDTO);
        int isAttend = DEFAULT_ATTEND;
        if(count > 0){
            isAttend = DEFAULT_NOT_ATTEND;
        }
        DoctorMemberMapperDTO doctorMemberMapperDTO = new DoctorMemberMapperDTO();
        BeanUtils.copyProperties(doctorMemberMapperDTO, addMemberDoctorRelateDTO);
        doctorMemberMapperDTO.setSid(DaoHelper.getSeq());
        doctorMemberMapperDTO.setModifyTimeStamp(String.valueOf(System.currentTimeMillis()));
        doctorMemberMapperDTO.setIsAttend(isAttend);
        doctorMemberMapperDTO.setConcernStatus(Optional.ofNullable(addMemberDoctorRelateDTO.getConcernStatus()).orElse(MemberDoctorConstant.CONCERN_STATUS_NO));
        if(StringUtils.isBlank(addMemberDoctorRelateDTO.getGroupId())){
            addMemberDoctorRelateDTO.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
        }
        doctorMemberMapperDTO.setGroupId(addMemberDoctorRelateDTO.getGroupId());
        doctorMemberMapperDTO.setOperatorId(addMemberDoctorRelateDTO.getOperatorId());
        Integer relationWay = addMemberDoctorRelateDTO.getRelationWay() == null ? MemberDoctorConstant.MEMBER_DOCTOR_RELATION_OTHER : addMemberDoctorRelateDTO.getRelationWay();
        doctorMemberMapperDTO.setRelationWay(relationWay);
        this.memberMapper.addDoctorMember(doctorMemberMapperDTO);

        addDefaultDialogue(addMemberDoctorRelateDTO);
        return true;
    }

    @Override
    public boolean checkedMemberDoctorRelate(AddMemberDoctorRelateDTO addMemberDoctorRelateDTO) {
        if(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_WEB_UPDATEGROUP!=addMemberDoctorRelateDTO.getRelationWay()){
            String hospitalId = this.getHospitalId(addMemberDoctorRelateDTO.getDoctorId());
            DoctorMemberPO doctorMember = this.memberMapper.getDoctorMemberByHospital(addMemberDoctorRelateDTO.getMemberId(),
                    hospitalId);
            if(doctorMember!=null){
                DoctorPO doctor = this.doctorService.getDoctorById(doctorMember.getDoctorId());
                throw new BusinessException("该患者已经存在本院的"+doctor.getDoctorName()+"的团队下了，请先解除。");
            }
        }
        return true;
    }

    /**
     * 新增患者默认消息
     * @param addMemberDoctorRelateDTO
     */
    private void addDefaultDialogue(AddMemberDoctorRelateDTO addMemberDoctorRelateDTO){
        DialoguePO dialoguePO = new DialoguePO();
        dialoguePO.setDoctorId(addMemberDoctorRelateDTO.getDoctorId());
        dialoguePO.setSenderId(addMemberDoctorRelateDTO.getDoctorId());
        dialoguePO.setMemberId(addMemberDoctorRelateDTO.getMemberId());
        dialoguePO.setForeignId(DEFAULT_FOREIGN_ID);
        dialoguePO.setMsgType(DialogueConstant.DIALOGUE_SYSTEM_MSG_TYPE);
        dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        dialoguePO.setDoctorMsg(DEFAULT_DOCTOR_MSG);
        dialoguePO.setPatientMsg(DEFAULT_MEMBER_MSG);
        DefaultDialogueBO defaultDialogueBO = new DefaultDialogueBO();
        defaultDialogueBO.setContent(DEFAULT_DOCTOR_MSG);
        defaultDialogueBO.setTextType(DialogueConstant.DIALOGUE_SYSTEM_MSG_TEXT_TYPE_MEMBER_DOCTOR);
        dialoguePO.setDataStr(JSON.toJSONString(defaultDialogueBO));
        this.dialogueService.addDialogue(dialoguePO);
/*        DoctorPO doctor = this.doctorService.getDoctorById(addMemberDoctorRelateDTO.getDoctorId());
        dialoguePO.setMsgType(DialogueConstant.DIALOGUE_ADD_MEMBER_MSG_TYPE);
        dialoguePO.setDoctorMsg("我是"+doctor.getDoctorName()+"感谢您选择我作为您的糖尿病教育护师");
        dialoguePO.setPatientMsg("我是"+doctor.getDoctorName()+"感谢您选择我作为您的糖尿病教育护师");
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("msg","我是"+doctor.getDoctorName()+"感谢您选择我作为您的糖尿病教育护师");
        dataMap.put("textType",DialogueConstant.DIALOGUE_SYSTEM_MSG_TEXT_TYPE_MEMBER_DOCTOR);
        dialoguePO.setDataStr(JSON.toJSONString(dataMap));
        this.dialogueService.addDialogue(dialoguePO);*/
    }


    @Override
    public void updateMemberGroup(UpdateMemberGroupDTO updateMemberGroupDTO) {
        UpdateDoctorMemberDTO doctorMemberMapperDTO = new UpdateDoctorMemberDTO();
        BeanUtils.copyProperties(doctorMemberMapperDTO, updateMemberGroupDTO);
        updateMemberDoctor(doctorMemberMapperDTO);
    }

    @Override
    public void updateMemberDoctor(UpdateDoctorMemberDTO updateDoctorMemberDTO) {
        updateDoctorMemberDTO.setModifyTimeStamp(String.valueOf(System.currentTimeMillis()));
        this.memberMapper.updateDoctorMember(updateDoctorMemberDTO);
    }

    @Override
    public RangeBO getMemberRange(String memberId) {

        RangeBO rangeBO = this.memberMapper.getMemberRange(memberId);
        if(rangeBO == null){
            rangeBO = new RangeBO();
            MemberControlTargetBO memberControlTargetBO = getMemberDefaultControlTarget(memberId);
            BeanUtils.copyProperties(rangeBO, memberControlTargetBO);
            rangeBO.setMemberId(memberId);
        }
        return rangeBO;
    }

    /**
     * 控制目标血脂处理
     * @param rangeBO
     */
    @Deprecated
    private void memberRangeBloodFatHandler(RangeBO rangeBO){
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(rangeBO.getMemberId());
        MemberPO memberPO = this.memberMapper.getMember(getMemberDTO);
        if(1 == memberPO.getSex()){
            rangeBO.setLowHDLCholesterol(String.valueOf(ControlTargetConstant.LOW_HDL_CHOLESTEROL_MAN));
        }else{
            rangeBO.setLowHDLCholesterol(String.valueOf(ControlTargetConstant.LOW_HDL_CHOLESTEROL_WOMAN));
        }
        if("QZ01".equals(memberPO.getChd())){
            rangeBO.setHighLDLCholesterol(String.valueOf(ControlTargetConstant.HIGH_LDL_CHOLESTEROL_CHD));
        }else{
            rangeBO.setHighLDLCholesterol(String.valueOf(ControlTargetConstant.HIGH_LDL_CHOLESTEROL_NO_CHD));
        }

        rangeBO.setLowTCholesterol(String.valueOf(ControlTargetConstant.LOW_T_CHOLESTEROL));
        rangeBO.setHighTCholesterol(String.valueOf(ControlTargetConstant.HIGH_T_CHOLESTEROL));
        rangeBO.setLowTriglyceride(String.valueOf(ControlTargetConstant.LOW_TRIGLYCERIDE));
        rangeBO.setHighTriglyceride(String.valueOf(ControlTargetConstant.HIGH_TRIGLYCERIDE));
    }

    @Override
    public void addMemberRange(RangeBO rangeBO) {
        RangeBO memberRange = this.memberMapper.getMemberRange(rangeBO.getMemberId());
        if (null == memberRange) {
            this.memberMapper.addMemberRange(rangeBO);
        }else{
            this.memberMapper.modifyMemberRange(rangeBO);
        }
    }

    @Override
    public void modifyMemberRange(RangeBO rangeBO) {
        RangeBO reRangeBO = this.memberMapper.getMemberRange(rangeBO.getMemberId());
        String doctorId = rangeBO.getDoctorId();
        if (null == reRangeBO) {
            insertMemberRangeWithLock(rangeBO.getMemberId());
            reRangeBO = this.memberMapper.getMemberRange(rangeBO.getMemberId());
            rangeBO.setRangeId(reRangeBO.getRangeId());
            this.memberMapper.modifyMemberRange(rangeBO);
        }else{
            this.memberMapper.modifyMemberRange(rangeBO);
        }
        reRangeBO = getMemberRange(rangeBO.getMemberId());
        reRangeBO.setDoctorId(doctorId);
        reRangeBO.setSenderId(rangeBO.getSenderId());
        sendRangeDialogue(reRangeBO);
    }

    /**
     * 发送控制目标对话
     * @param rangeBO
     */
    private void sendRangeDialogue(RangeBO rangeBO){
        if(StringUtils.isBlank(rangeBO.getDoctorId())){
            return;
        }

        //没有医患关系不下发
/*        CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
        countDoctorMemberDTO.setMemberId(rangeBO.getMemberId());
        countDoctorMemberDTO.setDoctorId(rangeBO.getDoctorId());
        long count = this.memberMapper.countMemberDoctor(countDoctorMemberDTO);
        if(count == 0){
            return;
        }*/

        DialoguePO dialoguePO = new DialoguePO();
        dialoguePO.setMemberId(rangeBO.getMemberId());
        dialoguePO.setDoctorId(rangeBO.getDoctorId());
        dialoguePO.setForeignId(rangeBO.getRangeId());
        dialoguePO.setSenderId(rangeBO.getSenderId());
        dialoguePO.setMsgType(DialogueConstant.DIALOGUE_TARGET_MSG_TYPE);
        dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        dialoguePO.setDoctorMsg(DIALOGUE_RANGE_MESSAGE);
        dialoguePO.setPatientMsg(DIALOGUE_RANGE_MESSAGE);
        RangeDialogueBO rangeDialogueBO = new RangeDialogueBO();
        rangeDialogueBO.setContent(DIALOGUE_RANGE_CONTENT);
        rangeDialogueBO.setDate(DateHelper.getToday());
        rangeDialogueBO.setLogId(rangeBO.getRangeId());
        rangeDialogueBO.setName(DIALOGUE_RANGE_TITLE);
        rangeDialogueBO.setTime(DateHelper.getDate(new Date(),"hh:mm:ss"));
        rangeDialogueBO.setTitle(DIALOGUE_RANGE_MESSAGE);
        rangeDialogueBO.setTextType(DialogueConstant.DIALOGUE_TEXT_TYPE_NO);
        RangeDialogueDataBO rangeDialogueDataBO = new RangeDialogueDataBO();
        rangeDialogueDataBO.setHighAfterMeal(rangeBO.getHighAfterMeal());
        rangeDialogueDataBO.setHighBeforeBreakfast(rangeBO.getHighBeforeBreakfast());
        rangeDialogueDataBO.setLowAfterMeal(rangeBO.getLowAfterMeal());
        rangeDialogueDataBO.setLowBeforeBreakfast(rangeBO.getLowBeforeBreakfast());
        rangeDialogueDataBO.setHighDiastolicPress(rangeBO.getHighDiastolicPress());
        rangeDialogueDataBO.setLowDiastolicPress(rangeBO.getLowDiastolicPress());
        rangeDialogueDataBO.setHighSystolicPress(rangeBO.getHighSystolicPress());
        rangeDialogueDataBO.setLowSystolicPress(rangeBO.getLowSystolicPress());
        rangeDialogueBO.setData(rangeDialogueDataBO);
        dialoguePO.setDataStr(JSON.toJSONString(rangeDialogueBO));
        this.dialogueService.addDialogue(dialoguePO);

        addRangeWechatMessage(rangeBO);
    }

    /**
     * 添加微信消息 - 控制目标
     * @param rangeBO
     */
    private void addRangeWechatMessage(RangeBO rangeBO){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctorId", rangeBO.getDoctorId());
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(rangeBO.getMemberId());
        addWechatMessageDTO.setForeignId(rangeBO.getRangeId());
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_CONTROL_TARGET);
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    @Override
    public RangeBO insertMemberRangeWithLock(String memberId) {
        RangeBO rangeModel = this.memberMapper.getMemberRange(memberId);
        if (null == rangeModel) {
            rangeModel = new RangeBO();
            MemberControlTargetBO controlTarget = getMemberDefaultControlTarget(memberId);
            BeanUtils.copyProperties(rangeModel, controlTarget);
            rangeModel.setRangeId(DaoHelper.getSeq());
            rangeModel.setMemberId(memberId);
            this.memberMapper.addMemberRange(rangeModel);
        }
        return rangeModel;
    }

    @Override
    public List<MemberPO> listMemberByIdList(List<String> idList) {
        return this.memberMapper.listMemberByIdList(idList);
    }

    @Override
    public void updateMemberDoctorConcern(UpdateDoctorMemberConcernDTO updateDoctorMemberConcernDTO) {
        UpdateDoctorMemberDTO doctorMemberMapperDTO = new UpdateDoctorMemberDTO();
        BeanUtils.copyProperties(doctorMemberMapperDTO, updateDoctorMemberConcernDTO);
        updateMemberDoctor(doctorMemberMapperDTO);
    }

    @Override
    public long countMemberDoctor(CountDoctorMemberDTO countDoctorMemberDTO) {
        return this.memberMapper.countMemberDoctor(countDoctorMemberDTO);
    }

    @Override
    public long countGroupMember(ListMemberDTO listMemberDTO) {
        return this.memberMapper.countGroupMember(listMemberDTO);
    }

    @Override
    public MemberVisitCardPO getMemberVisitCard(MemberVisitCardDTO memberVisitCardDTO) {
        return this.memberMapper.getMemberVisitCard(memberVisitCardDTO);
    }

    @Override
    public MemberDrugRecordPO getMemberDrugRecord(GetMemberDrugItemDTO getMemberDrugItemDTO) {
        return this.memberMapper.getMemberDrugRecord(getMemberDrugItemDTO);
    }

    @Override
    @CacheEvict(value = "memberDrugPlan",allEntries = true)
    public void addMemberDrugRecord(MemberDrugRecordPO memberDrugRecordPO) {
         this.memberMapper.addMemberDrugRecord(memberDrugRecordPO);
    }

    @Override
    @Cacheable(value = "memberDrugPlan", key = "#getMemberDrugItemDTO.cacheKey" ,unless="#result == null")
    public List<MemberDrugItemPO> getMemberDrugItemList(GetMemberDrugItemDTO getMemberDrugItemDTO) {
        List<MemberDrugItemPO> reList=new ArrayList<>();
        MemberDrugRecordPO memberDrugRecord = this.memberMapper.getMemberDrugRecord(getMemberDrugItemDTO);
        if(null != memberDrugRecord){
            GetMemberDrugItemDTO d=new GetMemberDrugItemDTO();
            d.setRecordId(memberDrugRecord.getId());
            d.setFollowId(memberDrugRecord.getFollowId());
            reList = this.memberMapper.getMemberDrugItemList(d);
        }
        return reList;
    }

    @Override
    @CacheEvict(value = "memberDrugPlan",allEntries = true)
    public void addMemberDrugItem(MemberDrugItemPO memberDrugItemPO) {
        this.memberMapper.addMemberDrugItem(memberDrugItemPO);
    }

    @Override
    @CacheEvict(value = "memberDrugPlan",allEntries = true)
    public void updateMemberDrugItem(MemberDrugItemPO memberDrugItemPO) {
        this.memberMapper.updateMemberDrugItem(memberDrugItemPO);
    }

    @Override
    @CacheEvict(value = "memberDrugPlan",allEntries = true)
    public void doDrugItem(String teamId,String doctorId,String memberId, String followId, String drugListJson,String origin) {
        List<MemberDrugItemPO> memberDrugItemPOS = JsonSerializer.jsonToList(drugListJson, MemberDrugItemPO.class);
        GetMemberDrugItemDTO getMemberDrugItemDTO=new GetMemberDrugItemDTO();
        getMemberDrugItemDTO.setFollowId(followId);
        getMemberDrugItemDTO.setMemberId(memberId);
        getMemberDrugItemDTO.setTeamId(doctorId);
        getMemberDrugItemDTO.setdType(getDrugType(doctorId));
        MemberDrugRecordPO memberDrugRecord = getMemberDrugRecord(getMemberDrugItemDTO);
        // 将原有方案更新为过期方案
        if(null != memberDrugRecord){
            memberMapper.delMemberDrugRecord(memberDrugRecord.getId());
            memberMapper.delMemberDrugItem(memberDrugRecord.getId());
        }
        MemberDrugRecordPO memberDrugRecordPO=new MemberDrugRecordPO();
        String  sid = DaoHelper.getSeq();
        memberDrugRecordPO.setId(sid);
        memberDrugRecordPO.setDoctorId(doctorId);
        memberDrugRecordPO.setTeamId(doctorId);
        memberDrugRecordPO.setFollowId(followId);
        memberDrugRecordPO.setMemberId(memberId);
        memberDrugRecordPO.setdType(getDrugType(doctorId));
        addMemberDrugRecord(memberDrugRecordPO);

        String endDt="";
        String startDt="";
        for (int i = 0; i < memberDrugItemPOS.size(); i++) {
            MemberDrugItemPO memberDrugItemPO = memberDrugItemPOS.get(i);
            String endDtNew = memberDrugItemPO.getEndDt();
            String startDtNew = memberDrugItemPO.getStartDt();
            if("".equals(endDt)){
                endDt=endDtNew;
                startDt=startDtNew;
            }else{
                //表示时间格式有误;返回 true: date1 时间在date2之后;返回 false: date1 时间在date2之前
                Boolean endDtBoolean = DateHelper.dateAfter(endDt, "yyyy-MM-dd", endDtNew, "yyyy-MM-dd");
                if(!endDtBoolean){
                    endDt =endDtNew;
                }
                Boolean startDtBoolean = DateHelper.dateAfter(startDt, "yyyy-MM-dd", startDtNew, "yyyy-MM-dd");
                if(startDtBoolean){
                    startDt =startDtNew;
                }
            }

        }
        if(null!=memberDrugItemPOS && memberDrugItemPOS.size()>0){
/*            if(!StringUtils.isBlank(startDt)
                    && !StringUtils.isBlank(endDt)){*/
                MemberDrugItemPO memberDrugItemPO = new MemberDrugItemPO();
                memberDrugItemPO.setRecordId(sid);
                memberDrugItemPO.setFollowId(followId);
                memberDrugItemPO.setDrugJson(drugListJson);
                memberDrugItemPO.setId(DaoHelper.getSeq());
                memberDrugItemPO.setStartDt(startDt);
                memberDrugItemPO.setEndDt(endDt);
                memberDrugItemPO.setName(schemeNameHandler(memberDrugItemPO));
                memberDrugItemPO.setSchemeStatus("1");
                memberDrugItemPO.setOrigin(origin);
                memberDrugItemPO.setCacheKey("D"+doctorId+"M"+memberId);
                addMemberDrugItem(memberDrugItemPO);
//            }
//        }
    }
    }

    /**
     * 用药方案名称处理
     * @param scheme
     */
    private String schemeNameHandler(MemberDrugItemPO scheme){
        StringBuilder s = new StringBuilder();
        s.append(schemeDateHandler(scheme.getStartDt())).append("至").append(schemeDateHandler(scheme.getEndDt()))
                .append("用药方案");
        return s.toString();
    }

    /**
     * 用药方案时间处理
     * @param date
     * @return
     */
    private static String schemeDateHandler(String date){
        String re="";
        if(!StringUtils.isBlank(date)){
            if(date.length() < 11){
                re= date;
            }else{
                re =date.substring(5,9);
            }
        }
        return re;
    }

    @Override
    public List<CountMonthMemberPO> countMonthMember(CountMonthMemberDTO countMonthMemberDTO) {
        return this.memberMapper.countMonthMember(countMonthMemberDTO);
    }

    @Override
    public String addMemberVisitCard(AddMemberVisitCardDTO addMemberVisitCardDTO) {
        MemberVisitCardDTO memberVisitCardDTO = new MemberVisitCardDTO();
        BeanUtils.copyProperties(memberVisitCardDTO, addMemberVisitCardDTO);
        MemberVisitCardPO memberVisitCardPO = getMemberVisitCard(memberVisitCardDTO);
        if(memberVisitCardPO != null){
            return memberVisitCardPO.getSid();
        }
        //默认门诊卡号
        if(addMemberVisitCardDTO.getCardType() == null){
            addMemberVisitCardDTO.setCardType(MemberConstant.CARD_TYPE_OUT_PATIENT);
        }
        String sid = DaoHelper.getSeq();
        addMemberVisitCardDTO.setSid(sid);
        this.memberMapper.addMemberVisitCard(addMemberVisitCardDTO);
        return sid;
    }

    @Override
    @Cacheable(value = "public", key = "'member'.concat(#memberId)" ,unless="#result == null")
    public MemberPO getMemberByIdCache(String memberId) {
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(memberId);
        return getMember(getMemberDTO);
    }

    @Override
    public MemberCenterVO getMemberCenter(String memberId) {
        MemberCenterVO memberCenterVO = new MemberCenterVO();
        //获取患者信息
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(memberId);
        MemberPO memberPO = getMember(getMemberDTO);
        if(memberPO!=null){
            //患者信息脱敏
            memberPO.setIdCard(ValidateTool.tuoMin(memberPO.getIdCard(),4,4,"*"));
            memberPO.setMobilePhone(ValidateTool.tuoMin(memberPO.getMobilePhone(),3,4,"*"));
        }
        memberCenterVO.setMember(memberPO);
        //获取血糖统计
        CountBloodSugarDTO countBloodSugarDTO = new CountBloodSugarDTO();
        countBloodSugarDTO.setMemberId(memberId);
        countBloodSugarDTO.setCodeDt("3");
        Map<String,Object> map = this.bloodSugarService.loadBloodNumHigLow(countBloodSugarDTO);
        if(map != null){
            memberCenterVO.setBloodSugarHigh(parseBloodSugarCount(map.get("high")));
            memberCenterVO.setBloodSugarLow(parseBloodSugarCount(map.get("low")));
            memberCenterVO.setBloodSugarNormal(parseBloodSugarCount(map.get("nomal")));
        }
        return memberCenterVO;
    }

    @Override
    public List<DoctorMemberPO> listDoctorMember(ListDoctorMemberDTO listDoctorMemberDTO) {
        return this.memberMapper.listDoctorMember(listDoctorMemberDTO);
    }

    @Override
    public DoctorMemberPO getDoctorMember(ListDoctorMemberDTO listDoctorMemberDTO) {
        return this.memberMapper.getDoctorMember(listDoctorMemberDTO);
    }

    @Override
    public MemberControlTargetBO getMemberDefaultControlTarget(String memberId) {
        ControlTargetConstant.BaseInfo baseInfo = baseInfoHandler(memberId);
        return ControlTargetConstant.getDefaultControlTarget(baseInfo);
    }

    /**
     * 默认控制目标基础信息处理
     * @param memberId
     * @return
     */
    private ControlTargetConstant.BaseInfo baseInfoHandler(String memberId){
        ControlTargetConstant.BaseInfo baseInfo = new ControlTargetConstant().new BaseInfo();
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(memberId);
        MemberPO memberPO = getMember(getMemberDTO);
        if(memberPO == null){
            throw new BusinessException("患者不存在");
        }

        //填充基础信息
        baseInfo.setSex(memberPO.getSex());
        baseInfo.setChd(memberPO.getChd());
        baseInfo.setDiabetesType(memberPO.getDiabetesType());
        baseInfo.setEssentialHyp(memberPO.getEssentialHyp());
        baseInfo.setBirthday(memberPO.getBirthday());
        baseInfo.setIsDiabetes(memberPO.getIsDiabetes());
        baseInfo.setHeight(memberPO.getHeight());

        //获取高血压分级
        CurrentGxyLevelDTO c = new CurrentGxyLevelDTO();
        c.setMemberId(memberId);
        c.setLevelType(1);
        MemberLevelPO memberLevelPO = this.memberLevelService.getCurrentGxyLevel(c);
        if(memberLevelPO != null){
            baseInfo.setHypLayer(memberLevelPO.getMemberLayer());
        }

        //处理档案字段
        MemberArchivesPO memberArchivesPO = getMemberArchives(memberId ,null);
        if(memberArchivesPO != null){
            String archivesJson = memberArchivesPO.getArchivesJson();
            baseInfo.setCkd(MemberRangeHelper.getCkd(archivesJson));
            baseInfo.setHypBfz(MemberRangeHelper.getHypBfz(archivesJson));
            baseInfo.setDiabetesBfz(MemberRangeHelper.getDiabetesBfz(archivesJson,memberPO));
        }
        return baseInfo;
    }



    @Override
    @Cacheable(value = "public" , key = "'member' + #memberId" ,unless="#result == null")
    public MemberPO getMemberById(String memberId) {
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(memberId);
        return getMember(getMemberDTO);
    }

    @Override
    public void updatePayStatusByPackageStartDate() {
        String todayDate = DateHelper.getToday();
        this.memberMapper.updatePayStatusByPackageStartDate(MemberDoctorConstant.PAY_STATUS_YES, todayDate);
    }

    @Override
    public MemberPO getMemberByIdCard(String idCard) {
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setIdCard(idCard);
        return getMember(getMemberDTO);
    }

    @Override
    public PageResult<MemberPO> listMemberByDoctorId(ListMemberByDoctorDTO listMemberByDoctorDTO,PageRequest pr) {
        PageHelper.startPage(pr.getPage(),pr.getRows());
        List<MemberPO> member = this.memberMapper.listMemberByDoctorId(listMemberByDoctorDTO);
        return new PageResult<>(member);
    }

    @Override
    public void cancelRelation(String memberId, String doctorId) {
        //判断该医生和患者是否还有绑定的套餐
        ListValidMemberPackageDTO dto = new ListValidMemberPackageDTO();
        dto.setMemberId(memberId);
        dto.setDoctorId(doctorId);
        List<MemberPackagePO> packagePo = this.packageService.listValidMemberPackage(dto);
        if (packagePo != null && packagePo.size() > 0){
            throw new BusinessException("请先解除该患者绑定的该医生套餐");
        }
        //删除医患关系
        this.memberMapper.cancelRelation(memberId,doctorId);
        //删除医患对话消息
        this.dialogueService.deleteDialogueOfDoctorMember(doctorId,memberId);
        //删除医患业务
        this.memberMapper.deleteDoctorMemberBizData(memberId,doctorId);
    }

    /**
     * 患者概要-风险情况
     * @param memberId
     * @param doctorId
     * @return
     */
    @Override
    public List<Map<String, Object>> getRisk(String memberId, String doctorId,String hospitalId,PageRequest page) {
        Map<String, Object> memberArchivesByMemberIdMap = this.getMemberArchivesByMemberId(memberId, doctorId,hospitalId,true);
        List<Map<String, Object>> result = MemberHelper.dealRiskResult(memberArchivesByMemberIdMap);
        Map<String, Object> bloodDayMap = this.dealBloodDayResult(memberId);
        if (bloodDayMap != null && bloodDayMap.size() > 0){
            result.add(bloodDayMap);
        }

        List<Map<String, Object>> list = this.dealContinuousResult(memberId, page);
        if (list != null && list.size() > 0){
            for (Map<String, Object> map : list) {
                result.add(map);
            }
        }
        Map<String, Object> waveMap = this.dealBloodWaveResult(memberId);
        if (waveMap != null && waveMap.size() > 0){
            result.add(waveMap);
        }
        Map<String, Object> rateMap = this.dealHighRateResult(memberId);
        if (rateMap != null && rateMap.size() > 0){
            result.add(rateMap);
        }
        return result;
    }
    //最近一周血糖偏高率
    private Map<String,Object> dealHighRateResult(String memberId){
        CountBloodSugarDTO countBloodSugarDTO = new CountBloodSugarDTO();
        countBloodSugarDTO.setMemberId(memberId);
        countBloodSugarDTO.setCodeDt("3");
        Map<String, Object> numMap = this.bloodSugarService.loadBloodNumHigLow(countBloodSugarDTO);
        HashMap<String, Object> rateMap = new HashMap<>();
        if (numMap!=null && numMap.size()>0){
            String totalNumsStr = numMap.get("totalNums").toString();
            String highStr = numMap.get("high").toString();
            if (!StringUtils.isBlank(totalNumsStr) && !StringUtils.isBlank(highStr)){
                double totalNums = Double.valueOf(totalNumsStr);
                double high = Double.valueOf(highStr);
                double rate = high/totalNums;
                if (rate >= 0.2){
                    rateMap.put("order",18);
                    rateMap.put("text","最近1周血糖偶尔偏高（偏高率>=20%）");
                }
                if (rate >= 0.4){
                    rateMap.put("order",18);
                    rateMap.put("text","最近1周血糖半数偏高（偏高率>=40%）");
                }
                if (rate >= 0.75){
                    rateMap.put("order",18);
                    rateMap.put("text","最近1周血糖全面偏高（偏高率>=75%）");
                }
            }
        }
        return rateMap;
    }

    //最近1周血糖波动（至少出现3次高血糖，2次低血糖）
    private Map<String,Object> dealBloodWaveResult(String memberId){
        CountBloodSugarDTO countBloodSugarDTO = new CountBloodSugarDTO();
        countBloodSugarDTO.setMemberId(memberId);
        countBloodSugarDTO.setCodeDt("3");
        Map<String, Object> map = this.bloodSugarService.loadBloodNumHigLow(countBloodSugarDTO);
        HashMap<String, Object> waveMap = new HashMap<>();
        if (map != null && map.size() > 0){
            if (null != map.get("high") && null != map.get("low") && !StringUtils.isBlank(map.get("high").toString()) && !StringUtils.isBlank(map.get("low").toString())){
                String highCountStr = map.get("high").toString();
                String lowCountStr = map.get("low").toString();
                Integer highCount = Integer.valueOf(highCountStr);
                Integer lowCount = Integer.valueOf(lowCountStr);
                if (highCount >=3 && lowCount>=2){
                    waveMap.put("order",17);
                    waveMap.put("text","最近1周血糖波动（至少出现3次高血糖，2次低血糖）");
                }
            }
        }
        return waveMap;
    }
    //最近一周连续血糖偏高>=3天
    private List<Map<String,Object>> dealContinuousResult(String memberId,PageRequest page) {
        List<Map<String, Object>> list1 = new ArrayList<>();
        HashMap<String, Object> continuoousMap = new HashMap<>();
        ListBloodSugarDTO dto = new ListBloodSugarDTO();
        dto.setMemberId(memberId);
        dto.setCodeDt("3");
        List<Map<String, Object>> list = this.bloodSugarService.listBloodSugarPage(dto, page);
        int count = 0;
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                int temp = 0;
                if (null != map.get("afterBreakfast") && !StringUtils.isBlank(map.get("afterBreakfast").toString())) {
                    String afterBreakfast = JSON.toJSONString(map.get("afterBreakfast"));
                    Map<String, String> map1 = JsonSerializer.jsonToStringMap(afterBreakfast);
                    if (null != map1.get("paramLevel") && !StringUtils.isBlank(map1.get("paramLevel").toString())) {
                        String level = map1.get("paramLevel").toString();
                        if (level.equals("5")) {
                            temp += 1;
                        }
                    }
                }

                if (null != map.get("afterDinner") && !StringUtils.isBlank(map.get("afterDinner").toString())) {
                    String afterDinner = JSON.toJSONString(map.get("afterDinner"));
                    Map<String, String> map2 = JsonSerializer.jsonToStringMap(afterDinner);
                    if (null != map2.get("paramLevel") && !StringUtils.isBlank(map2.get("paramLevel").toString())) {
                        String level2 = map2.get("paramLevel").toString();
                        if (level2.equals("5")) {
                            temp += 1;
                        }
                    }
                }

                if (null != map.get("afterLunch") && !StringUtils.isBlank(map.get("afterLunch").toString())) {
                    String afterLunch = JSON.toJSONString(map.get("afterLunch"));
                    Map<String, String> map3 = JsonSerializer.jsonToStringMap(afterLunch);
                    if (null != map3.get("paramLevel") && !StringUtils.isBlank(map3.get("paramLevel").toString())) {
                        String level3 = map3.get("paramLevel").toString();
                        if (level3.equals("5")) {
                            temp += 1;
                        }
                    }
                }

                if (null != map.get("beforeBreakfast") && !StringUtils.isBlank(map.get("beforeBreakfast").toString())) {
                    String beforeBreakfast = JSON.toJSONString(map.get("beforeBreakfast"));
                    Map<String, String> map4 = JsonSerializer.jsonToStringMap(beforeBreakfast);
                    if (null != map4.get("paramLevel") && !StringUtils.isBlank(map4.get("paramLevel").toString())) {
                        String level4 = map4.get("paramLevel").toString();
                        if (level4.equals("5")) {
                            temp += 1;
                        }
                    }
                }

                if (null != map.get("beforeDinner") && !StringUtils.isBlank(map.get("beforeDinner").toString())) {
                    String beforeDinner = JSON.toJSONString(map.get("beforeDinner"));
                    Map<String, String> map5 = JsonSerializer.jsonToStringMap(beforeDinner);
                    if (null != map5.get("paramLevel") && !StringUtils.isBlank(map5.get("paramLevel").toString())) {
                        String level5 = map5.get("paramLevel").toString();
                        if (level5.equals("5")) {
                            temp += 1;
                        }
                    }
                }

                if (null != map.get("beforeLunch") && !StringUtils.isBlank(map.get("beforeLunch").toString())) {
                    String beforeLunch = JSON.toJSONString(map.get("beforeLunch"));
                    Map<String, String> map6 = JsonSerializer.jsonToStringMap(beforeLunch);
                    if (null != map6.get("paramLevel") && !StringUtils.isBlank(map6.get("paramLevel").toString())) {
                        String level6 = map6.get("paramLevel").toString();
                        if (level6.equals("5")) {
                            temp += 1;
                        }
                    }
                }

                if (null != map.get("beforeSleep") && !StringUtils.isBlank(map.get("beforeSleep").toString())) {
                    String beforeSleep = JSON.toJSONString(map.get("beforeSleep"));
                    Map<String, String> map7 = JsonSerializer.jsonToStringMap(beforeSleep);
                    if (null != map7.get("paramLevel") && !StringUtils.isBlank(map7.get("paramLevel").toString())) {
                        String level7 = map7.get("paramLevel").toString();
                        if (level7.equals("5")) {
                            temp += 1;
                        }
                    }
                }

                if (null != map.get("beforedawn") && !StringUtils.isBlank(map.get("beforedawn").toString())) {
                    String beforedawn = JSON.toJSONString(map.get("beforedawn"));
                    Map<String, String> map8 = JsonSerializer.jsonToStringMap(beforedawn);
                    if (null != map8.get("paramLevel") && !StringUtils.isBlank(map8.get("paramLevel").toString())) {
                        String level8 = map8.get("paramLevel").toString();
                        if (level8.equals("5")) {
                            temp += 1;
                        }
                    }
                }
                if (temp > 0){
                    count += 1;
                }
                if (temp >= 3){
                    HashMap<String, Object> bloodWeekMap = new HashMap<>();
                    bloodWeekMap.put("order",15);
                    if (null != map.get("day") && !StringUtils.isBlank(map.get("day").toString())){
                        bloodWeekMap.put("text",map.get("day").toString()+"累计出现"+temp+"次血糖偏高");
                    }
                    list1.add(bloodWeekMap);
                }

            }
            if (count >= 3){
                continuoousMap.put("order",16);
                continuoousMap.put("text","最近1周连续血糖偏高>=3天");
                list1.add(continuoousMap);
            }
        }

        return list1;
    }

    //1周,2周,3周,1个月没有测血糖
    private Map<String,Object> dealBloodDayResult(String memberId){
        List<BloodSugarPO> bloodSugarPO1 = this.bloodSugarService.loadBloodSugarByMemberIdAndDay(memberId, "1");
        HashMap<String, Object> bloodMap = new HashMap<>();
        if (bloodSugarPO1==null || bloodSugarPO1.size()<=0){
            bloodMap.put("order",14);
            bloodMap.put("text","累积1周没有监测血糖");
        }
        List<BloodSugarPO> bloodSugarPO2= this.bloodSugarService.loadBloodSugarByMemberIdAndDay(memberId, "3");
        if (bloodSugarPO2==null || bloodSugarPO2.size() <=0){
            bloodMap.put("order",14);
            bloodMap.put("text","累积2周没有监测血糖");
        }
        List<BloodSugarPO> bloodSugarPO3= this.bloodSugarService.loadBloodSugarByMemberIdAndDay(memberId, "4");
        if (bloodSugarPO3==null || bloodSugarPO3.size() <=0){
            bloodMap.put("order",14);
            bloodMap.put("text","累积3周没有监测血糖");
        }
        List<BloodSugarPO> bloodSugarPO4= this.bloodSugarService.loadBloodSugarByMemberIdAndDay(memberId, "2");
        if (bloodSugarPO4==null || bloodSugarPO4.size() <=0){
            bloodMap.put("order",14);
            bloodMap.put("text","超过1个月没有监测血糖");
        }
        return bloodMap;
    }

    /**
     * 患者概要-检测数据
     * @param memberId
     * @param doctorId
     * @return
     */
    @Override
    public List<Map<String, Object>> getDetectionDate(String memberId, String doctorId,String hospitalId) {
        //获取患者档案数据
        Map<String, Object> map = this.getMemberArchivesByMemberId(memberId, doctorId,hospitalId,true);
        ListFootDTO listFootDTO = new ListFootDTO();
        listFootDTO.setMemberId(memberId);
        //listFootDTO.setDoctorId(doctorId);
        //获取最新足处方数据
        FootPO footPO = this.footService.getFootNew(listFootDTO);
        //获取最新日常随访表数据
        FollowupPO followDayModel = (FollowupPO)this.followService.getFollowQuesNewByType(memberId, null, 2);
        List<Map<String, Object>> result = MemberHelper.dealDetectionDateResult(map, footPO,followDayModel);
        return result;
    }

    @Override
    public void addDoctorMemberRemark(DoctorMemberRemarkPO doctorMemberRemarkPO) {
        DoctorMemberRemarkPO remarkPO = new DoctorMemberRemarkPO();
        BeanUtils.copyProperties(remarkPO,doctorMemberRemarkPO);
        remarkPO.setRemarkId(DaoHelper.getSeq());
        this.memberMapper.addDoctorMemberRemark(remarkPO);
    }

    @Override
    public void delDoctorMemberRemark(String remarkId) {
        this.memberMapper.delDoctorMemberRemark(remarkId);
    }

    @Override
    public List<DoctorMemberRemarkPO> listDoctorMemberRemark(String memberId, String doctorId,String hospitalId) {
        if (!StringUtils.isBlank(hospitalId)){
            return this.memberMapper.listDoctorMemberRemark(memberId,"",hospitalId);
        }else {
            return this.memberMapper.listDoctorMemberRemark(memberId,doctorId,hospitalId);
        }
    }

    @Override
    public void updateMemberVisitNo(String hospitalId, String memberId, String visitNo) {
        if(StringUtils.isBlank(hospitalId)
                || StringUtils.isBlank(memberId) || StringUtils.isBlank(visitNo)){
            return;
        }
        MemberVisitCardDTO memberVisitCardDTO = new MemberVisitCardDTO();
        memberVisitCardDTO.setHospitalId(hospitalId);
        memberVisitCardDTO.setMemberId(memberId);
        MemberVisitCardPO memberVisitCardPO = this.memberMapper.getMemberVisitCard(memberVisitCardDTO);
        if(memberVisitCardPO == null){
            AddMemberVisitCardDTO addMemberVisitCardDTO = new AddMemberVisitCardDTO();
            addMemberVisitCardDTO.setVisitNo(visitNo);
            addMemberVisitCardDTO.setMemberId(memberId);
            addMemberVisitCardDTO.setHospitalId(hospitalId);
            addMemberVisitCard(addMemberVisitCardDTO);
        }else{
            memberVisitCardPO.setVisitNo(visitNo);
            this.memberMapper.updateMemberVisitNo(memberVisitCardPO);
        }
    }

    @Override
    public void deleteMemberForScreening(String memberId, String doctorId) {
        cancelRelation(memberId ,doctorId);
        this.screeningService.deleteMemberScreening(memberId ,doctorId);
//        this.footService.deleteMemberFootResult(doctorId ,memberId);
        FootPO footPO = new FootPO();
        footPO.setMemberId(memberId);
        footPO.setDoctorId(doctorId);
        footPO.setIsValid("0");
        footPO.setFootType(String.valueOf(FootConstant.FOOT_TYPE_SCREENING));
        this.footService.modifyFoot(footPO);

        this.screeningStatsService.deleteScreeningStats(memberId ,doctorId);
    }

    /**
     * 1 患者不存在 2 患者存在但没有医患关系 3 患者存在且有医患关系
     */
    public final static int PATIENT_SEARCH_RESULT_1 = 1;
    public final static int PATIENT_SEARCH_RESULT_2 = 2;
    public final static int PATIENT_SEARCH_RESULT_3 = 3;

    @Override
    public MemberSearchResultVO searchMemberByIdCard(String idCard, DoctorSessionBO doctorSessionBO) {
        MemberSearchResultVO memberSearchResultVO = new MemberSearchResultVO();
        PatientSyncBO patientSyncBO = getScreeningPatientByIdCard(idCard ,doctorSessionBO);
        if(patientSyncBO == null){
            memberSearchResultVO.setResultStatus(PATIENT_SEARCH_RESULT_1);
        }else{
            memberSearchResultVO.setPatient(patientSyncBO);
            CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
            countDoctorMemberDTO.setMemberId(patientSyncBO.getMemberId());
            countDoctorMemberDTO.setDoctorId(doctorSessionBO.getDoctorId());
            long count = countMemberDoctor(countDoctorMemberDTO);
            if(count > 0){
                memberSearchResultVO.setResultStatus(PATIENT_SEARCH_RESULT_3);
            }else{
                memberSearchResultVO.setResultStatus(PATIENT_SEARCH_RESULT_2);
            }
        }
        return memberSearchResultVO;
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
    public void memberScreeningArchiveHandler(PatientSyncBO patientSyncBO, String memberId, String doctorId) {
        MemberArchivesDTO memberArchivesDTO = new MemberArchivesDTO();
        memberArchivesDTO.setMemberId(memberId);
        memberArchivesDTO.setDoctorId(doctorId);
        memberArchivesDTO.setArchivesJson(getArchiveJson(patientSyncBO).toJSONString());
        updateMemberArchive(memberArchivesDTO);
    }

    @Override
    public PatientSyncBO getScreeningPatientByIdCard(String idCard ,DoctorSessionBO doctorSessionBO) {
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setHospitalId(doctorSessionBO.getHospitalId());
        getMemberDTO.setIdCard(idCard);
        MemberPO memberPO = getMember(getMemberDTO);
        return assembleScreeningPatient(memberPO ,doctorSessionBO.getDoctorId());
    }

    @Override
    public PatientSyncBO getScreeningPatientByMemberId(String memberId ,DoctorSessionBO doctorSessionBO) {
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setHospitalId(doctorSessionBO.getHospitalId());
        getMemberDTO.setMemberId(memberId);
        MemberPO memberPO = getMember(getMemberDTO);
        return assembleScreeningPatient(memberPO ,doctorSessionBO.getDoctorId());
    }

    @Override
    public Long countGroupMemberForV4(ListMemberDTO listMemberDTO) {
        return this.memberMapper.countGroupMemberForV4(listMemberDTO);
    }

    @Override
    public Long countGroupMemberForHospital(ListMemberDTO listMemberDTO) {
        return this.memberMapper.countGroupMemberForHospital(listMemberDTO);
    }

    @Override
    public Long countDepartMember(ListMemberDTO listMemberDTO) {
        return this.memberMapper.countDepartMember(listMemberDTO);
    }

    @Override
    public PageResult<MemberListPO> listMemberForPager(PagerMemberDTO pagerMemberDTO, PageRequest pr) {
        Integer eType = null;  //1:查询糖尿病患者 2:查询高血压患者 null 查询所有患者
        if (DoctorConstant.ENTITY_TYPE_TNB.equals(pagerMemberDTO.getEntityType())){
            eType = 1;
        }else if (DoctorConstant.ENTITY_TYPE_GXY.equals(pagerMemberDTO.getEntityType())){
            eType = 2;
        }else if(DoctorConstant.ENTITY_TYPE_FAT.equals(pagerMemberDTO.getEntityType())){
            eType = 3;
        }
        pagerMemberDTO.setMemberType(eType);
        String groupId = pagerMemberDTO.getGroupId();
        String hospitalId = pagerMemberDTO.getHospitalId();
        Integer type = pagerMemberDTO.getType();
        if(GroupConstant.IN_HOSPITAL.equals(type)){
            PageHelper.startPage(pr.getPage(), pr.getRows());
            List<MemberListPO> list = this.memberMapper.listMemberByDepart(pagerMemberDTO);
//            this.setMemberDifferentLevels(list,hospitalId);
            memberLeverHandler(list);
            PageResult<MemberListPO> poPageResult = new PageResult<>(list);
            return poPageResult;
        } else if(GroupConstant.OUT_HOSPITAL.equals(type)){
            PageHelper.startPage(pr.getPage(), pr.getRows());
            List<MemberListPO> list = null;
            if (null != pagerMemberDTO.getSwitchFlag() && pagerMemberDTO.getSwitchFlag()){
                list = this.memberMapper.listMemberForHospital(pagerMemberDTO);
            }else{
                list = this.memberMapper.listMemberForV4(pagerMemberDTO);
            }
//            this.setMemberDifferentLevels(list,hospitalId);
            memberLeverHandler(list);
            //使用的设备信息处理
            for(MemberListPO memberListPO : list){
                memberListPO.setUseMachine(getUseMachineInfo(memberListPO.getMemberId(),memberListPO.getHospitalId()));
            }
            PageResult<MemberListPO> poPageResult = new PageResult<>(list);
            return poPageResult;
        }
        return null;
    }

    private void memberLeverHandler(List<MemberListPO> list){
        Set<String> memberIds = new HashSet<>();
        list.forEach( x -> memberIds.add(x.getMemberId()));
        ListMemberLevelDTO levelDTO = new ListMemberLevelDTO();
        levelDTO.setMembers(new ArrayList<>(memberIds));
        levelDTO.setLevelType(1);
        levelDTO.setConfirm(1);
        levelDTO.setCode("2");
        Map<String, MemberLevelPO> result = memberLevelService.getMemberLeverMap(levelDTO);
        levelDTO.setLevelType(2);
        Map<String, MemberLevelPO> result2 = memberLevelService.getMemberLeverMap(levelDTO);
        for(MemberListPO po : list){
            if(result.containsKey(po.getMemberId())){
                MemberLevelPO x = result.get(po.getMemberId());
                po.setHypLayer(x.getMemberLayer());
                po.setHypLever(x.getMemberLevel());
            }
            if(result2.containsKey(po.getMemberId())) {
                MemberLevelPO x = result2.get(po.getMemberId());
                po.setDiabetesLever(x.getMemberLevel());
            }
        }
    }

    /**
     * 设置患者分层分级情况
     * @param list
     * @param hospitalId
     */
    @Override
    public void setMemberDifferentLevels(List<MemberListPO> list, String hospitalId) {
        if(list!=null&&list.size()>0 && !StringUtils.isBlank(hospitalId)){
            MemberListPO memberListPO = null;
            for(int i=0;i<list.size();i++){
                memberListPO = list.get(i);
                if(memberListPO.getConcernStatus() == MemberCheckinInfoConstant.CHECKIN_STATUS_NO){
                    continue;
                }
                MemberCurrentDiffLevelVO memberCurrentDiffLevelResult = this.differentLevelsService.getMemberCurrentDiffLevelResult(memberListPO.getMemberId(),hospitalId);
                if(memberCurrentDiffLevelResult!=null){
                    memberListPO.setDsmeHx(memberCurrentDiffLevelResult.getLevel()+"");
                    memberListPO.setLevelHx(memberCurrentDiffLevelResult.getLayer()+"");
                } else {
                    memberListPO.setDsmeHx(null);
                    memberListPO.setLevelHx(null);
                }
            }
        }
    }

    @Override
    public List<InHospitalMemberVO> castInHospitalMemberVO(List<MemberListPO> list) {
        //远程会诊状态处理
        List<String> memberIdList = list.stream().map(MemberListPO::getMemberId).collect(Collectors.toList());
        List<RemoteConsultationPO> consultationList = null;
        if(memberIdList != null && memberIdList.size() > 0){
            consultationList = this.remoteConsultationService.listCurrentRemoteConsultationByMemberIdList(memberIdList);
        }
        Map<String ,RemoteConsultationPO> consultationMap = null;
        if(consultationList != null && consultationList.size() > 0){
            consultationMap = consultationList.stream().collect(Collectors.toMap(x -> x.getMemberId() ,x -> x));
        }
        List<InHospitalMemberVO> vos = new ArrayList<>(list.size());
        for(MemberListPO po : list){
            InHospitalMemberVO vo = new InHospitalMemberVO();
            String memberId = po.getMemberId();
            if(!StringUtils.isBlank(po.getBirthday())){
                vo.setAge(DateHelper.getAge(po.getBirthday()));
            }
            vo.setBedNo(po.getBedNo());
            vo.setSid(po.getSid());
            vo.setDepartmentId(po.getDepartmentId());
            vo.setDepartmentName(po.getDepartmentName());
            if(MemberCheckinInfoConstant.CHECKIN_STATUS_YES == po.getCheckinStatus()){
                vo.setConcernStatus(po.getConcernStatus());
                vo.setHospitalNo(po.getHospitalNo());
                vo.setInHospitalDt(po.getInHospitalDt());
                vo.setMemberId(memberId);
                vo.setMemberName(po.getMemberName());
                vo.setDiseaseType(this.getDiseaseTypeOfMemberInfoCH(po));
                String inHospitalDate = po.getInHospitalDt()+DateHelper.DEFUALT_TIME_START;
                BloodSugarInHosBO bo = this.bloodSugarService.getLatestBloodSugarInHos(memberId,inHospitalDate);
                vo.setParamCode(bo.getParamCode());
                vo.setParamRecordDt(bo.getRecordDt());
                vo.setParamTestNum(bo.getNum());
                vo.setParamValue(bo.getParamValue());
                vo.setParamLevel(bo.getParamLevel());
                vo.setPicUrl(po.getPicUrl());
                if(!StringUtils.isBlank(po.getSex())){
                    vo.setSex(Integer.parseInt(po.getSex()));
                }
                vo.setBirthday(po.getBirthday());
                vo.setMobilePhone(po.getMobilePhone());
                vo.setHeight(po.getHeight());
                vo.setWeight(po.getWeight());
                vo.setUseMachine(getUseMachineInfo(vo.getMemberId(),po.getHospitalId()));
                vo.setDoctorZg(po.getDoctorZg());
                vo.setDoctorZgCode(po.getDoctorZgCode());
                vo.setPositionName(po.getPositionName());
                vo.setInHospitalDay(DateHelper.dateCompareGetDay(DateHelper.getNowDate(),po.getInHospitalDt()));
                VirtualWardPO wardByMemberId = this.virtualWardMapper.getCurrentVirtualWard(vo.getMemberId(),po.getHospitalId());
                if (wardByMemberId != null && !"".equals(wardByMemberId)){
                    vo.setIsVirtualWard(1);
                }else{
                    vo.setIsVirtualWard(0);
                }
            }
            //远程会诊状态 1 在 0 不在
            if(consultationMap != null && consultationMap.containsKey(memberId)){
                vo.setRemoteConsultationStatus(1);
            }else{
                vo.setRemoteConsultationStatus(0);
            }
            vos.add(vo);
        }
        return vos;
    }


    @Override
    public long countMemberWhere(GetStatisticsDTO dto) {
        return this.memberMapper.countMemberWhere(dto);
    }

    @Override
    public long getPrescriptionOfMemberCount(GetStatisticsDTO dataAnalysisDTO) {
        return this.memberMapper.getPrescriptionOfMemberCount(dataAnalysisDTO);
    }

    @Override
    public String addScreeningMember(PatientSyncBO patientSyncBO, Integer resultStatus, DoctorSessionBO doctorSessionBO) {
        String memberId = null;
        if(resultStatus == 1){
            AddMemberDTO addMemberDTO = new AddMemberDTO();
            BeanUtils.copyProperties(addMemberDTO ,patientSyncBO);
            addMemberDTO.setMemberName(patientSyncBO.getPatientName());
            memberId = addMember(addMemberDTO, doctorSessionBO);
            memberScreeningArchiveHandler(patientSyncBO ,memberId ,doctorSessionBO.getDoctorId());
            AddMemberDoctorRelateDTO addMemberDoctorRelateDTO = new AddMemberDoctorRelateDTO();
            addMemberDoctorRelateDTO.setDoctorId(doctorSessionBO.getDoctorId());
            addMemberDoctorRelateDTO.setOperatorId(doctorSessionBO.getDoctorId());
            addMemberDoctorRelateDTO.setMemberId(memberId);
            addMemberDoctorRelateDTO.setGroupId(patientSyncBO.getGroupId());
            //医患关系创建方式
            addMemberDoctorRelateDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_SCREEING_SYSTEM);
            addMemberDoctorRelate(addMemberDoctorRelateDTO);
        }else if(resultStatus == 2){
            MemberPO memberPO = getMemberByIdCard(patientSyncBO.getIdCard());
            if(memberPO == null){
                throw new BusinessException("患者不存在");
            }
            memberId = memberPO.getMemberId();
            AddMemberDoctorRelateDTO addMemberDoctorRelateDTO = new AddMemberDoctorRelateDTO();
            addMemberDoctorRelateDTO.setDoctorId(doctorSessionBO.getDoctorId());
            addMemberDoctorRelateDTO.setOperatorId(doctorSessionBO.getDoctorId());
            addMemberDoctorRelateDTO.setMemberId(memberId);
            addMemberDoctorRelateDTO.setGroupId(patientSyncBO.getGroupId());
            //医患关系创建方式
            addMemberDoctorRelateDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_SCREEING_SYSTEM);
            addMemberDoctorRelate(addMemberDoctorRelateDTO);
        }
        return memberId;
    }

    @Override
    public List<MemberHistoricMedicationPlanDTO> listMemberHistoricMedicationPlan() {
        return this.memberMapper.listMemberHistoricMedicationPlan();
    }

    @Override
    public PageResult<MemberPO> listNeedCreateSugarMonthReportMember(int page, int rows) {
        PageHelper.startPage(page ,rows);
        List list = this.memberMapper.listNeedCreateSugarMonthReportMember();
        return new PageResult<>(list);
    }

    @Override
    @CacheEvict(value = "memberDrugPlan",allEntries = true)
    public void modifyDrugItem(String id,String remark, String drugListJson) {
        MemberDrugItemPO memberDrugItemPO = new MemberDrugItemPO();
        memberDrugItemPO.setId(id);
        memberDrugItemPO.setDrugJson(drugListJson);
        memberDrugItemPO.setRemark(remark);
        this.memberMapper.updateMemberDrugItem(memberDrugItemPO);
    }

    @Override
    public PageResult<MemberCheckinBO> listInHospitalMemberOfLevelLow(int page, int rows) {
        PageHelper.startPage(page,rows);
        List<MemberCheckinBO> boList = this.memberMapper.listInHospitalMemberOfLevelLow();
        return new PageResult<MemberCheckinBO>(boList);
    }

    @Override
    public PageResult<MemberInspectVO> pagerMemberInspect(String memberId, PageRequest pager) {
        PageHelper.startPage(pager.getPage(),pager.getRows());
        List<MemberInspectVO> voPageResult = this.memberMapper.pagerMemberInspect(memberId);
        return new PageResult<MemberInspectVO>(voPageResult);
    }

    @Override
    public List<MemberInspectVO> pagerMemberInspect2(String memberId) {
        List<MemberInspectVO> voPageResult = this.memberMapper.pagerMemberInspect(memberId);
        GetDyBloodPressureDiaryDTO getDyPressureDto = new GetDyBloodPressureDiaryDTO();
        getDyPressureDto.setMemberId(memberId);
        List<DyBloodPressureDetailPO> dateList = dyBloodPressureDetailMapper.queryDateList(getDyPressureDto);
        String startDt = null;
        String endDt = null;
        //获取有效的血压日期
        for (DyBloodPressureDetailPO date : dateList) {
            boolean isShow = this.dyBloodPressureService.showBloodPressureByDate(memberId, date.getStartDt(), date.getEndDt());
            if (isShow){
                startDt = date.getStartDt();
                endDt = date.getEndDt();
                break;
            }
        }
        DyBloodPressureDetailPO getDetailPO = new DyBloodPressureDetailPO();
        getDetailPO.setStartDt(startDt);
        getDetailPO.setEndDt(endDt);
        getDetailPO.setIsValid(1);
        getDetailPO.setMemberId(memberId);
        DyBloodPressurePO dyBloodPressurePO = dyBloodPressureMapper.queryOne(getDetailPO);
        MemberInspectVO memberInspectVO = new MemberInspectVO();
        memberInspectVO.setInspectTitle("动态血压");
        memberInspectVO.setType(3);
        //memberInspectVO.setInspectDate(DateHelper.changeTimeFormat(dyBloodPressurePO.getRecordTime(),DateHelper.DATETIME_FORMAT,DateHelper.DAY_FORMAT));
        memberInspectVO.setInspectTime(dyBloodPressurePO.getRecordTime());
        JSONObject js = new JSONObject();
        js.put("startDt",startDt);
        js.put("endDt",endDt);
        memberInspectVO.setRemark(js.toJSONString());
        voPageResult.add(memberInspectVO);
        if(null != voPageResult && voPageResult.size()>0){
            ListUtils.sort(voPageResult,false,"inspectTime");
        }
        return voPageResult;
    }

    @Override
    public boolean checkDoctorMemberRelationExists(String memberId, String doctorId) {
        //判断医患关系
        CountDoctorMemberDTO countDoctorMemberDTO = new CountDoctorMemberDTO();
        countDoctorMemberDTO.setMemberId(memberId);
        countDoctorMemberDTO.setDoctorId(doctorId);
        long l = countMemberDoctor(countDoctorMemberDTO);
        //判断住院情况
        DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
        if(doctorPO == null){
            return false;
        }
        ListMemberDTO listMemberDTO = new ListMemberDTO();
        listMemberDTO.setDoctorId(doctorId);
        listMemberDTO.setHospitalId(doctorPO.getHospitalId());
        listMemberDTO.setMemberId(memberId);
        PageResult<InHospitalMemberVO> pageResult = pagerInHospitalMember(listMemberDTO ,new PageRequest());


        //判断是否在本医院课题组中
        Boolean inResearchGroup = this.researchGroupService.checkMemberExistsInResearchGroup(memberId ,doctorPO.getHospitalId());

        return l > 0 || pageResult.getTotalRows() > 0 || inResearchGroup;
    }

    /**
     * 筛查的患者信息组装
     * @param memberPO
     * @param doctorId
     * @return
     */
    private PatientSyncBO assembleScreeningPatient(MemberPO memberPO ,String doctorId){
        if(memberPO == null){
            return null;
        }
        PatientSyncBO patientSyncBO = new PatientSyncBO();
        BeanUtils.copyProperties(patientSyncBO ,memberPO);
        patientSyncBO.setPatientName(memberPO.getMemberName());
        patientSyncBO.setMemberId(memberPO.getMemberId());
        DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
        MemberArchivesPO memberArchives = this.memberCacheService.getMemberArchives(memberPO.getMemberId(), DEFAULT_FOREIGN_ID);
        if(memberArchives == null){
            return patientSyncBO;
        }
        String archiveJson = memberArchives.getArchivesJson();
        if(StringUtils.isBlank(archiveJson)){
            return patientSyncBO;
        }
        JSONObject jsonObject = JSON.parseObject(archiveJson);
        JSONObject sign = jsonObject.getJSONObject("sign");
        if(sign != null){
            patientSyncBO.setBmi(sign.getString("bmi"));
            patientSyncBO.setWeight(sign.getString("weight"));
            patientSyncBO.setHeight(sign.getString("height"));
            patientSyncBO.setSbp(sign.getString("sdp"));
            patientSyncBO.setDbp(sign.getString("dbp"));
            patientSyncBO.setWhr(sign.getString("whr"));
            patientSyncBO.setWaistline(sign.getString("waistline"));
            patientSyncBO.setHipline(sign.getString("hipline"));
        }
        JSONObject treatment = jsonObject.getJSONObject("treatment");
        if(treatment != null){
            patientSyncBO.setFbs(treatment.getString("mq_fbg"));
            patientSyncBO.setPbg(treatment.getString("blg"));
        }
        JSONObject complication = jsonObject.getJSONObject("complication");
        if(complication != null){
            patientSyncBO.setChd(complication.getString("chd"));
        }

        JSONObject anamnesis = jsonObject.getJSONObject("anamnesis");
        if(anamnesis != null){
            patientSyncBO.setAnamnesis(anamnesis.getString("essential_hyp"));
        }

        JSONObject lab = jsonObject.getJSONObject("lab");
        if(lab != null){
            patientSyncBO.setHba1c(lab.getString("lab_hba"));
        }
        return patientSyncBO;
    }

    /**
     * 获取档案的json
     * @param patientSyncBO
     * @return
     */
    private JSONObject getArchiveJson(PatientSyncBO patientSyncBO){
        JSONObject jsonObject = new JSONObject();

        JSONObject sign = new JSONObject();
        sign.put("bmi" ,patientSyncBO.getBmi());
        sign.put("weight" ,patientSyncBO.getWeight());
        sign.put("height" ,patientSyncBO.getHeight());
        sign.put("sbp" ,patientSyncBO.getSbp());
        sign.put("dbp" ,patientSyncBO.getDbp());
        sign.put("whr" ,patientSyncBO.getWhr());
        sign.put("waistline" ,patientSyncBO.getWaistline());
        sign.put("hipline" ,patientSyncBO.getHipline());

        JSONObject treatment = new JSONObject();
        treatment.put("mq_fbg" ,patientSyncBO.getFbs());
        treatment.put("blg" ,patientSyncBO.getPbg());

        String chd = patientSyncBO.getChd();

        if(!StringUtils.isBlank(chd)){
            JSONObject complication = new JSONObject();

            complication.put("chd" , chd);
            jsonObject.put("complication" ,complication);
        }

        String anamnesisStr = patientSyncBO.getAnamnesis();
        if(!StringUtils.isBlank(anamnesisStr)){
            JSONObject anamnesis = new JSONObject();

            anamnesis.put("essential_hyp" , anamnesisStr);
            jsonObject.put("anamnesis" ,anamnesis);
        }

        JSONObject lab = new JSONObject();
        lab.put("lab_hba" ,patientSyncBO.getHba1c());


        jsonObject.put("sign" ,sign);
        jsonObject.put("treatment" ,treatment);


        jsonObject.put("lab" ,lab);
        return jsonObject;
    }

    /**
     * 处理血糖统计数值
     * @param obj
     * @return
     */
    private Long parseBloodSugarCount(Object obj){
        if(obj == null){
            return 0L;
        }
        return Long.parseLong(obj.toString());
    }

    @Override
    @Transactional(rollbackFor = Exception.class ,isolation = Isolation.READ_UNCOMMITTED)
    public void updateMemberArchive(MemberArchivesDTO memberArchivesDTO) {
        MemberArchivesPO archivesPO = this.memberMapper.getMemberArchives(memberArchivesDTO.getMemberId(), DEFAULT_FOREIGN_ID);
        if(archivesPO == null){
            synchronized (this){
                archivesPO = this.memberMapper.getMemberArchives(memberArchivesDTO.getMemberId(), DEFAULT_FOREIGN_ID);
                if(archivesPO == null){
                    MemberArchivesPO memberArchivesPO=new MemberArchivesPO();
                    memberArchivesPO.setMemberId(memberArchivesDTO.getMemberId());
                    memberArchivesPO.setArchivesJson(memberArchivesDTO.getArchivesJson());
                    memberArchivesPO.setHospitalId(DEFAULT_FOREIGN_ID);
                    this.memberCacheService.addMemberArchives(memberArchivesPO);
                    //判断是否有用药数据  要更新
                    if(memberArchivesDTO.getSaveDrug() && !StringUtils.isBlank(memberArchivesDTO.getDrugListJson()) && !StringUtils.isBlank(memberArchivesDTO.getDoctorId())){
                        doDrugItem(memberArchivesDTO.getDoctorId(),memberArchivesDTO.getDoctorId(),memberArchivesDTO.getMemberId(),null,memberArchivesDTO.getDrugListJson(),memberArchivesDTO.getOrigin());
                    }
                    //添加患者档案日志
                    MemberArchivesRecordPO memberArchivesRecordPO = new MemberArchivesRecordPO();
                    memberArchivesRecordPO.setSid(DaoHelper.getSeq());
                    memberArchivesRecordPO.setMemberId(memberArchivesDTO.getMemberId());
                    memberArchivesRecordPO.setArchivesJsonBefore(memberArchivesDTO.getArchivesJson());
                    memberArchivesRecordPO.setArchivesJsonAfter(memberArchivesDTO.getArchivesJson());
                    memberArchivesRecordPO.setOperationId(Optional.ofNullable(memberArchivesDTO.getDoctorId()).orElse(DEFAULT_FOREIGN_ID));
                    memberArchivesRecordPO.setHospitalId(DEFAULT_FOREIGN_ID);
                    this.memberCacheService.addMemberArchivesRecord(memberArchivesRecordPO);
                    return;
                }
            }
        }
        MemberArchivesRecordPO memberArchivesRecordPO = new MemberArchivesRecordPO();
        memberArchivesRecordPO.setSid(DaoHelper.getSeq());
        memberArchivesRecordPO.setMemberId(Optional.ofNullable(archivesPO.getMemberId()).orElse(DEFAULT_FOREIGN_ID));
        memberArchivesRecordPO.setArchivesJsonBefore(Optional.ofNullable(archivesPO.getArchivesJson()).orElse(DEFAULT_FOREIGN_ID));
        memberArchivesRecordPO.setArchivesJsonAfter(memberArchivesDTO.getArchivesJson());
        memberArchivesRecordPO.setOperationId(Optional.ofNullable(memberArchivesDTO.getDoctorId()).orElse(DEFAULT_FOREIGN_ID));
        memberArchivesRecordPO.setHospitalId(DEFAULT_FOREIGN_ID);
        this.memberCacheService.addMemberArchivesRecord(memberArchivesRecordPO);

        archivesPO.setArchivesJson(archivesJsonHandler(archivesPO.getArchivesJson(), memberArchivesDTO.getArchivesJson()));
        this.memberCacheService.updateMemberArchivesById(archivesPO);

        //判断是否有用药数据  要更新
        if(memberArchivesDTO.getSaveDrug() && !StringUtils.isBlank(memberArchivesDTO.getDrugListJson()) && !StringUtils.isBlank(memberArchivesDTO.getDoctorId())){
            doDrugItem(memberArchivesDTO.getDoctorId(),memberArchivesDTO.getDoctorId(),memberArchivesDTO.getMemberId(),null,memberArchivesDTO.getDrugListJson(),memberArchivesDTO.getOrigin());
        }
    }

    //根据医生id 获取医院id
    private String getHospitalId(String doctorId){
        if(StringUtils.isBlank(doctorId)){
            return null;
        }
        String hospitalId = null;
        DoctorPO doctorP = this.doctorService.getDoctorById(doctorId);
        if(doctorP != null){
            hospitalId = doctorP.getHospitalId();
        }
        return hospitalId;
    }

    @Override
    public Map<String,Object> getMemberArchivesByMemberId(String memberId,String doctorId,String hospitalId,Boolean hide) {
        Map<String,Object> reMap = new HashMap<>();
        if (StringUtils.isBlank(hospitalId)){
            hospitalId = getHospitalId(doctorId);
        }
        //所属医生信息
        DoctorPO doctor = this.doctorService.getDoctorById(doctorId);
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setHospitalId(hospitalId);
        getMemberDTO.setMemberId(memberId);
        //患者个人信息
        MemberPO memberPO = this.getMember(getMemberDTO);
        if(memberPO == null){
            throw new BusinessException("不存在的患者，请确认");
        }
        if(doctor != null){
            memberPO.setDepartmentName(doctor.getDepartName());
        }
        MemberArchivesPO memberArchives = this.memberCacheService.getMemberArchives(memberId, DEFAULT_FOREIGN_ID);
        if(null!=memberArchives && !StringUtils.isBlank(memberArchives.getArchivesJson())){
            String re = followService.doHistoryFood(memberArchives.getArchivesJson());
            memberArchives.setArchivesJson(re);
            String newArchive = MemberArchivesTuoMinHelper.archivesTuoMin(memberArchives.getArchivesJson(), hide, null);
            memberArchives.setArchivesJson(newArchive);
        }
        GetMemberDrugItemDTO getMemberDrugItemDTO=new GetMemberDrugItemDTO();
        getMemberDrugItemDTO.setMemberId(memberId);
        getMemberDrugItemDTO.setdType(getDrugType(doctorId));
        getMemberDrugItemDTO.setCacheKey(MD5Util.md5("D"+doctorId+"M"+memberId));
        List<MemberDrugItemPO> list = getMemberDrugItemList(getMemberDrugItemDTO);
        String drugListJson ="";
        if(null!=list && list.size()>0){
            drugListJson =  JsonSerializer.objectToJson(list);
        }

        //患者住院信息
        CheckinInfoBO checkinInfoBO = this.hospitalService.getCheckinInfoBOByMid(memberId ,hospitalId);
        Integer inHos = 0;
        if(checkinInfoBO!=null && "1".equals(checkinInfoBO.getCheckinStatus())){
            inHos = 1;
        }

        ListDoctorMemberDTO dto = new ListDoctorMemberDTO();
        dto.setMemberId(memberId);
        dto.setDoctorId(doctorId);
        DoctorMemberPO doctorMember = this.memberMapper.getDoctorMember(dto);
        //付费状态
        String payStatus = doctorMember == null ? "1" : doctorMember.getPriceFlag();

        MemberCurrentDiffLevelVO memberCurrentDiffLevelResult = this.differentLevelsService.getMemberCurrentDiffLevelResult(memberId,hospitalId);
        if(memberCurrentDiffLevelResult!=null){
            memberPO.setDsmeHx(memberCurrentDiffLevelResult.getLevel()+"");
            memberPO.setLevelHx(memberCurrentDiffLevelResult.getLayer()+"");
        } else {
            memberPO.setDsmeHx(null);
            memberPO.setLevelHx(null);
        }

        //高血压分层分级
        if (!StringUtils.isBlank(memberPO.getEssentialHyp()) && memberPO.getEssentialHyp().equals("1")){
            CurrentGxyLevelDTO levelDTO = new CurrentGxyLevelDTO();
            levelDTO.setMemberId(memberId);
            levelDTO.setHospitalId(hospitalId);
            levelDTO.setLevelType(MemberLevelConstant.GXY_TYPE);
            MemberLevelPO gxyLevel = this.memberLevelService.getCurrentGxyLevel(levelDTO);
            if (null != gxyLevel){
                memberPO.setGxyLevel(gxyLevel.getMemberLevel()+"");
                memberPO.setGxyLayer(gxyLevel.getMemberLayer()+"");
            }
        }

        //信息脱敏
        memberPO.setIdCard(ValidateTool.tuoMin(memberPO.getIdCard(),4,4,"*"));
        if (null == hide || hide){
            memberPO.setMobilePhone(ValidateTool.tuoMin(memberPO.getMobilePhone(),3,4,"*"));
        }


        //并发症种类-v6.0.0
        ComplicationVO complication = null;
        if(null!=memberArchives && !StringUtils.isBlank(memberArchives.getArchivesJson())){
            String re = this.setArchivesJsonHasHyp(memberArchives,memberPO);
            complication = this.getComplicationByArchives(re,memberPO);
        }

        //远程会诊状态 1 正在会诊 0 未开始
        int remoteConsultationStatus = 0;
        List currentRemoteConsultationList = this.remoteConsultationService.listCurrentRemoteConsultationByMemberIdList(Collections.singletonList(memberId));
        if(currentRemoteConsultationList != null && currentRemoteConsultationList.size() > 0){
            remoteConsultationStatus = 1;
        }

        reMap.put("member",memberPO);
        reMap.put("memberArchives",memberArchives);
        reMap.put("drugListJson",drugListJson);
        reMap.put("inHospitalInfo",checkinInfoBO);
        reMap.put("inHos",inHos);
        reMap.put("currentDiffLevelResult",memberCurrentDiffLevelResult);
        reMap.put("payStatus",payStatus);
        reMap.put("complication",complication);
        reMap.put("remoteConsultationStatus" ,remoteConsultationStatus);
        return reMap;
    }

    /**
     * 从患者基本信息设置患者是否高血压
     * @param memberPO
     * @return
     */
    private String setArchivesJsonHasHyp(MemberArchivesPO archivesPO, MemberPO memberPO) {
        if("1".equals(memberPO.getEssentialHyp())){
            String archivesJson = archivesPO.getArchivesJson();
            JSONObject jsonObject = JSONObject.parseObject(archivesJson);
            JSONObject anamnesis = jsonObject.getJSONObject("anamnesis");
            if(anamnesis==null){
                anamnesis = new JSONObject();
            }
            if(!"1".equals(anamnesis.getString("essential_hyp"))){
                anamnesis.put("essential_hyp","1");
                jsonObject.put("anamnesis",anamnesis);
                archivesJson = JSONObject.toJSONString(jsonObject);
                archivesPO.setArchivesJson(archivesJson);
                MemberArchivesDTO memberArchivesDTO = new MemberArchivesDTO();
                memberArchivesDTO.setArchivesJson(archivesJson);
                memberArchivesDTO.setMemberId(archivesPO.getMemberId());
                memberService.updateMemberArchive(memberArchivesDTO);
            }
        }
        return archivesPO.getArchivesJson();
    }

    /**
     * 获取并发症种类及信息v6.0.0
     * @param archivesJson
     * @return
     */
    private ComplicationVO getComplicationByArchives(String archivesJson,MemberPO memberPO) {
        if(StringUtils.isBlank(archivesJson)){
            return null;
        }
        // 1 仅患糖尿病 2仅患高血压 3 糖尿病&高血压 0 其它
        int type = 0;
        if(memberPO!=null){
            if("1".equals(memberPO.getEssentialHyp()) && "1".equals(memberPO.getIsDiabetes())){
                type=3;
            } else if("1".equals(memberPO.getIsDiabetes())){
                type=1;
            } else if("1".equals(memberPO.getEssentialHyp())){
                type=2;
            } else {
                type=0;
            }
        }
        ComplicationVO complicationVO = new ComplicationVO();
        complicationVO.setList(new ArrayList<ComplicationVO.ComplicationBO>());
        complicationVO.setCount(0);
        JSONObject jsonObject = JSONObject.parseObject(archivesJson);
        JSONObject complicationMap = jsonObject.getJSONObject("complication");
        JSONObject anamnesis = jsonObject.getJSONObject("anamnesis");
        JSONObject hypertension = jsonObject.getJSONObject("hypertension");
        if(type==1 || type==3){
            //糖尿病眼底病变 retinal SWM01 有
            this.checkHasComplication("retinal","SWM01","糖尿病眼底病变","retinal_date",complicationMap,complicationVO);
            //糖尿病肾病 nephropathy SB01 有
            this.checkHasComplication("nephropathy","SB01","糖尿病肾病","neph_date",complicationMap,complicationVO);
            //周围神经病变 neuropathy ZWSJ01 有
            this.checkHasComplication("neuropathy","ZWSJ01","周围神经病变",null,complicationMap,complicationVO);
            //糖尿病足 diabetic_foot TNBZ01 有
            this.checkHasComplication("diabetic_foot","TNBZ01","糖尿病足",null,complicationMap,complicationVO);
            //下肢血管病变 pad XZXG01 有
            this.checkHasComplication("pad","XZXG01","下肢血管病变",null,complicationMap,complicationVO);
            //自主神经病变 dan ZZ01 有
            this.checkHasComplication("dan","ZZ01","自主神经病变",null,complicationMap,complicationVO);
        }
        if(type==2 || type==3) {
            //心脏疾病 cardiopathy XZJB01 有
            this.checkHasComplication("cardiopathy", "XZJB01", "心脏疾病", "cardiopathyDate", hypertension, complicationVO);
            //脑血管病 cerebral NXGB01 有
            this.checkHasComplication("cerebral", "NXGB01", "脑血管病", "cerebralDate", hypertension, complicationVO);
            //高血压眼底视网膜病变 hypRet GXYYD01 有
            this.checkHasComplication("hypRet", "GXYYD01", "高血压眼底视网膜病变", "hypRetDate", hypertension, complicationVO);
            //慢性肾脏病 slowNep MXSZB01 有
            this.checkHasComplication("slowNep", "MXSZB01", "慢性肾脏病", "slowNepDate", hypertension, complicationVO);
            //外周血管疾病 peripheral WZXG01 有
            this.checkHasComplication("peripheral", "WZXG01", "外周血管疾病", "peripheralDate", hypertension, complicationVO);
        }
        //冠心病 chd QZ01 有
        this.checkHasComplication("chd", "QZ01", "冠心病", "chd_date", complicationMap, complicationVO);
        return complicationVO;
    }

    /**
     * 检验源是否确诊有检验病症
     * @param checkKey 检验key
     * @param hasFlag 确诊有标志
     * @param checkName 病症名称
     * @param sureDateKey 病症确诊日期key
     * @param source 检验源
     * @param motherVO 容器
     */
    private void checkHasComplication(String checkKey,String hasFlag,String checkName,String sureDateKey,JSONObject source,ComplicationVO motherVO){
        if(motherVO==null || source==null || StringUtils.isBlank(hasFlag) || StringUtils.isBlank(checkName)){
            return;
        }
        if(null!=source.get(checkKey) && !StringUtils.isBlank(source.get(checkKey).toString())){
            String keyValue= source.get(checkKey).toString();
            if(hasFlag.equals(keyValue)){
                ComplicationVO.ComplicationBO complicationBO = motherVO.newComplicationBO();
                complicationBO.setName(checkName);
                if(!StringUtils.isBlank(sureDateKey)){
                    complicationBO.setDate(source.getString(sureDateKey));
                }
                motherVO.setCount(motherVO.getCount()+1);
                motherVO.getList().add(complicationBO);
            }
        }
    }

    @Override
    public MemberArchivesPO getMemberArchives(String memberId, String doctorId) {
        String hospitalId = getHospitalId(doctorId);
        MemberArchivesPO po= this.memberCacheService.getMemberArchives(memberId, DEFAULT_FOREIGN_ID);
        if(null!=po && !StringUtils.isBlank(po.getArchivesJson())){
            String re = followService.doHistoryFood(po.getArchivesJson());
            po.setArchivesJson(re);
        } else if (po==null || StringUtils.isBlank(po.getArchivesJson())){
            GetMemberDTO getMemberDTO = new GetMemberDTO();
            getMemberDTO.setMemberId(memberId);
            MemberPO memberPO = this.memberMapper.getMember(getMemberDTO);
            if(memberPO!=null){
                if(po==null){
                    po = new MemberArchivesPO();
                }
                JSONObject jsonObject = new JSONObject();
                JSONObject item = new JSONObject();
                jsonObject.put("sign",item);
                item.put("memberRealName",memberPO.getMemberName());
                item.put("sex",memberPO.getSex());
                item.put("birthday",memberPO.getBirthday());
                po.setMemberId(memberId);
                po.setHospitalId(hospitalId);
                po.setArchivesJson(JSONObject.toJSONString(jsonObject));
                this.memberCacheService.addMemberArchives(po);
            }
        }
        return po;
    }

    /**
     * 合并患者档案
     *
     * @param oldArchives
     * @param newArchives
     * @return
     * @author
     * @date
     */
    private static String  archivesJsonHandler(String oldArchives,String newArchives) {
        Map<String, Object> oldArchivesMap=JsonSerializer.jsonToMap(oldArchives);
        Map<String, Object> newArchivesMap=JsonSerializer.jsonToMap(newArchives);

        // 旧档案不存在
        if(oldArchivesMap == null) {
            return JsonSerializer.objectToJson(newArchivesMap);
        }

        if(oldArchivesMap != null && newArchivesMap != null) {
            Iterator<Map.Entry<String,Object>> iterable = newArchivesMap.entrySet().iterator();
            while(iterable.hasNext()){
                Map.Entry<String,Object> entry = iterable.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                Map<String, Object> newCategoryInfo = null;
                if(value instanceof Map){
                    newCategoryInfo = (Map<String, Object>) value;
                }
                Map<String, Object> oldCategoryInfo = (Map<String, Object>) oldArchivesMap.get(key);
                if(newCategoryInfo == null) {
                    continue;
                }
                // 旧分类不存在
                if(oldCategoryInfo == null) {
                    oldArchivesMap.put(key, newCategoryInfo);
                    continue;
                }
                Iterator<Map.Entry<String,Object>> newIterable = newCategoryInfo.entrySet().iterator();
                while(newIterable.hasNext()){
                    Map.Entry<String,Object> newentry = newIterable.next();
                    String field = newentry.getKey();
                    String newValue = StringUtils.converParamToString(newentry.getValue());
                    oldCategoryInfo.put(field, newValue);
/*                    if(!StringUtils.isBlank(newValue)) {

                    }*/
                }

            }

        }

        return JsonSerializer.objectToJson(oldArchivesMap);
    }


    @Override
    public List<MemberDataBO> listMemberData(MemberDataDTO memberDataDTO) {
        List<MemberDataBO> dataBOS = this.memberMapper.listMemberData(memberDataDTO);
        return dataBOS;
    }

    @Override
    public long countNewMember(SynthesizeDataDTO synthesizeDataDTO) {
        return this.memberMapper.countNewMember(synthesizeDataDTO);
    }

    @Override
    public List<String> getMemberByDoctor(GetMemberByDoctorDTO getMemberByDoctorDTO) {
        return this.memberMapper.getMemberByDoctor(getMemberByDoctorDTO);
    }

    @Override
    public List<CountMonthMemberPO> countMonthMember(GetStatisticsDTO getStatisticsDTO) {
        return this.memberMapper.countMonthMemberForHos(getStatisticsDTO);
    }

    @Override
    public long countMemberDoctor(GetStatisticsDTO dto) {
        return this.memberMapper.countMemberDoctorForHos(dto);
    }

    @Override
    public long getMemberNumber(GetStatisticsDTO dto) {
        return this.memberMapper.getMemberNumber(dto);
    }

    @Override
    public PageResult<MemberWarmPO> listMemberWarmByParams(ListMemberWarmDTO listMemberWarmDTO,PageRequest pr) {
        List<String> doctorIdList = new ArrayList<>();
        if(!StringUtils.isBlank(listMemberWarmDTO.getDoctorIds())){
            String doctorIds = listMemberWarmDTO.getDoctorIds();
            for(String doctorId : doctorIds.split(",")){
                List<String> doctorIdList1 = this.doctorService.listTeamId(doctorId);
                if(doctorIdList1!=null && doctorIdList1.size()>0 && !doctorIdList.containsAll(doctorIdList1)){
                    doctorIdList.addAll(doctorIdList1);
                }
            }
            listMemberWarmDTO.setDoctorIdList(doctorIdList);
            listMemberWarmDTO.setHospitalId(null);
        }
        PageHelper.startPage(pr.getPage(),pr.getRows());
        List<MemberWarmPO> memberWarmList = this.memberMapper.listMemberWarmByParams(listMemberWarmDTO);
        return new PageResult<>(memberWarmList);
    }

    @Override
    public void deleteMemberWarmById(String sid) {
        MemberWarmPO memberWarmPO = new MemberWarmPO();
        memberWarmPO.setSid(sid);
        memberWarmPO.setIsValid(0);
        this.memberMapper.updateMemberWarm(memberWarmPO);
    }

    @Override
    public void updateMemberWarm(MemberWarmDTO memberDTO) {
        String jsonString = JSONObject.toJSONString(memberDTO);
        MemberWarmPO memberWarmPO = JSONObject.parseObject(jsonString, MemberWarmPO.class);
        memberWarmPO.setPushDt(StringUtils.isBlank(memberWarmPO.getPushDt()) ? null : memberWarmPO.getPushDt());
        this.memberMapper.updateMemberWarm(memberWarmPO);
    }

    @Override
    public Map<String, Object> saveMemberWarm(MemberWarmDTO memberDTO,String hospitalId) {
        Map<String,Object> map = new HashMap<String,Object>(1);
        map.put("message", "保存成功");
        //将对象进行转换
        String jsonString = JSONObject.toJSONString(memberDTO);
        MemberWarmPO memberWarmPO = JSONObject.parseObject(jsonString, MemberWarmPO.class);
        memberWarmPO.setPushDt(StringUtils.isBlank(memberWarmPO.getPushDt()) ? null : memberWarmPO.getPushDt());
        memberWarmPO.setSid(DaoHelper.getSeq());
        memberWarmPO.setPushStatus(1);
        memberWarmPO.setHospitalId(hospitalId);
        this.memberMapper.saveMemberWarm(memberWarmPO);
        return map;
    }

    @Override
    public List<Map<String, Object>> getMemberAgeRang(GetStatisticsDTO dto) {
        return this.memberMapper.getMemberAgeRang(dto);
    }

    @Override
    public List<MemberWarmPO> listMemberWarmByUnSendStatus() {
        return this.memberMapper.listMemberWarmByUnSendStatus();
    }

    @Override
    public int getDrugType(String doctorId) {
        int dType = 1;
        if (null != doctorId){
            Set<String> strings = this.authorityService.listUserAuthority(doctorId, AuthorityConstant.TYPE_FRONT);
            if (null != strings && strings.contains("web_drug_nj")){
                dType = 2;
            }
        }
        return dType;
    }

    @Override
    public PageResult<MemberListPO> listSearchMember(String doctorId, String keyword, PageRequest pr) {
        List<DoctorPO> doctorList = this.doctorService.listMyDoctor(doctorId);
        List<String> doctorIdList = doctorList.stream().map(DoctorPO::getDoctorId).collect(Collectors.toList());
        ListMemberDTO memberDTO = new ListMemberDTO();
        memberDTO.setKeyWord(keyword);
        memberDTO.setDoctorIdList(doctorIdList);
        return listMember(memberDTO ,pr);
    }


    @Override
    public PageResult<MemberPO> listMemberByPackageGroup(PageRequest pr, String packageId, String doctorId) {

        List<String> doctorIdList = getMyDoctorIdList(doctorId);
        List<MemberPO> list = null;
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        //无套餐的患者列表
        if(DEFAULT_FOREIGN_ID.equals(packageId)){
            List<Integer> payStatusList = new ArrayList<>();
            payStatusList.add(MemberDoctorConstant.PAY_STATUS_NO);
            payStatusList.add(MemberDoctorConstant.PAY_STATUS_EXPIRE);
            list = this.memberMapper.listNoPackageMember(doctorIdList ,payStatusList);
        }
        //有套餐的患者列表
        else{
            list = this.memberMapper.listMemberByPackageGroup(packageId ,doctorIdList);
        }
        return new PageResult<>(list);
    }

    @Override
    public long countNoPayMember(List<String> doctorList) {
        List<Integer> payStatusList = new ArrayList<>();
        payStatusList.add(MemberDoctorConstant.PAY_STATUS_NO);
        payStatusList.add(MemberDoctorConstant.PAY_STATUS_EXPIRE);
        return this.memberMapper.countNoPayMember(doctorList ,payStatusList);
    }

    @Override
    public MemberPO getMemberByPatientId(String principalId, String thirdId) {
        MemberVisitCardDTO memberVisitCardDTO = new MemberVisitCardDTO();
        memberVisitCardDTO.setVisitNo(principalId);
        memberVisitCardDTO.setHospitalId(thirdId);
        memberVisitCardDTO.setCardType(3);
        MemberVisitCardPO visitCardPO = this.memberMapper.getMemberVisitCard(memberVisitCardDTO);
        if(visitCardPO!=null && !StringUtils.isBlank(visitCardPO.getMemberId())){
            GetMemberDTO getMemberDTO = new GetMemberDTO();
            getMemberDTO.setMemberId(visitCardPO.getMemberId());
            return this.memberMapper.getMember(getMemberDTO);
        }
        return null;
    }

    @Override
    public MemberPO getMemberByVisitId(String principalId, String thirdId) {
        MemberVisitCardDTO memberVisitCardDTO = new MemberVisitCardDTO();
        memberVisitCardDTO.setVisitNo(principalId);
        memberVisitCardDTO.setHospitalId(thirdId);
        memberVisitCardDTO.setCardType(1);
        MemberVisitCardPO visitCardPO = this.memberMapper.getMemberVisitCard(memberVisitCardDTO);
        if(visitCardPO!=null && !StringUtils.isBlank(visitCardPO.getMemberId())){
            GetMemberDTO getMemberDTO = new GetMemberDTO();
            getMemberDTO.setMemberId(visitCardPO.getMemberId());
            return this.memberMapper.getMember(getMemberDTO);
        }
        return null;
    }

    @Override
    public boolean verificationUserPackage(ListValidMemberPackageDTO dto) {
        List<MemberPackagePO> listPackage = this.packageService.listMemberAllPackage(dto);
        if(listPackage==null || listPackage.size()<=0){
            throw new BusinessException(HxErrorCodeConstant.BIZ_NOT_BUY_PACKAGE,"您还未购买该服务，请前往医院开通");
        }
        List<MemberPackagePO> list = this.packageService.listValidMemberPackage(dto);
        if(list==null || list.size()<=0){
            throw new BusinessException(HxErrorCodeConstant.BIZ_PACKAGE_NOT_VALID,"服务已到期，请前往医院续费");
        }
        return true;
    }

    /**
     * 获取我的医生id列表
     * @param doctorId
     * @return
     */
    private List<String> getMyDoctorIdList(String doctorId){
        //加载医生列表
        List<DoctorPO> doctorList = this.doctorService.listMyDoctor(doctorId);
        return doctorList.stream().map(DoctorPO::getDoctorId).collect(Collectors.toList());
    }

    @Override
    public PageResult<MemberArchivesPO> listAllMemberArchives(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<MemberArchivesPO> result = this.memberMapper.listAllMemberArchives();
        return new PageResult<MemberArchivesPO>(result);
    }

    //默认主治状态 0 非主治
    private final static int DEFAULT_NOT_ATTEND = 0;
    private final static int DEFAULT_ATTEND = 1;
    //对话默认外键
    private final static String DEFAULT_FOREIGN_ID = "-1";
    //医患关系默认文案
    private final static String DEFAULT_DOCTOR_MSG = "你已成功添加患者,现在可以开始交流了";
    private final static String DEFAULT_MEMBER_MSG = "你已成功添加医生,现在可以开始交流了";

    private final static String DIALOGUE_RANGE_MESSAGE = "控制目标设置通知";
    private final static String DIALOGUE_RANGE_TITLE = "控制目标";
    private final static String DIALOGUE_RANGE_CONTENT = "为患者制定控制目标";

    @Override
    public PageResult<Map<String,Object>> listAllMemberNearlyYearArchives(Integer page,Integer rows) {
        List<Map<String,Object>> maps = null;
        if(page!=null && rows!=null){
            PageHelper.startPage(page,rows);
        }
        List<MemberArchivesPO> listAllMemberArchives = this.memberMapper.listAllMemberNearlyYearArchives();
        PageResult<MemberArchivesPO> poPageResult = new PageResult<>(listAllMemberArchives);
        List<MemberArchivesPO> list = poPageResult.getRows();
        if(null!=list && list.size()>0){
            maps = new ArrayList<>(list.size());
            for(MemberArchivesPO po:list){
                String archivesJson = po.getArchivesJson();
                if(!StringUtils.isBlank(archivesJson)){
                    Map<String,Object> reMap = new HashMap<>();
                    String re = followService.doHistoryFood(archivesJson);
                    po.setArchivesJson(re);
                    //患者个人信息
                    GetMemberDTO getMemberDTO = new GetMemberDTO();
                    getMemberDTO.setMemberId(po.getMemberId());
                    MemberPO memberPO = this.memberMapper.getMember(getMemberDTO);
                    //获取患者最近一次 问卷得分
                    ListQuestionnaireDTO listQuestionnaireDTO = new ListQuestionnaireDTO();
                    listQuestionnaireDTO.setMemberId(po.getMemberId());
                    listQuestionnaireDTO.setQuestionStatus("3");
                    List<Map<String, Object>> memberQuesScore = this.questionnaireService.getMemberQuesScore(listQuestionnaireDTO);
                    reMap.put("member",memberPO);
                    reMap.put("memberArchives",po);
                    reMap.put("memberQuesScore",memberQuesScore);
                    maps.add(reMap);
                }
            }
        }
        PageResult<Map<String,Object>> pager = new PageResult<>();
        if(page!=null && rows!=null){
            pager.setTotalPages(poPageResult.getTotalPages());
            pager.setTotalRows(poPageResult.getTotalRows());
            pager.setPageNum(poPageResult.getPageNum());
            pager.setPageSize(poPageResult.getPageSize());
        } else {
            pager.setTotalPages(1);
            pager.setTotalRows(maps.size());
            pager.setPageNum(1);
            pager.setPageSize(maps.size());
        }
        pager.setRows(maps);
        return pager;
    }

    @Override
    public List<Map<String,Object>> listMemberNearlyYearArchivesByHid(String memberId, String hospitalId) {
        List<Map<String,Object>> maps = null;
        List<MemberArchivesPO> list = this.memberMapper.listMemberArchives(memberId,hospitalId);
        if(null!=list && list.size()>0){
            maps = new ArrayList<>(list.size());
            for(MemberArchivesPO po:list){
                String archivesJson = po.getArchivesJson();
                if(!StringUtils.isBlank(archivesJson)){
                    Map<String,Object> reMap = new HashMap<>();
                    String re = followService.doHistoryFood(archivesJson);
                    po.setArchivesJson(re);
                    //患者个人信息
                    GetMemberDTO getMemberDTO = new GetMemberDTO();
                    getMemberDTO.setMemberId(po.getMemberId());
                    MemberPO memberPO = this.memberMapper.getMember(getMemberDTO);
                    //获取患者最近一次 问卷得分
                    ListQuestionnaireDTO listQuestionnaireDTO = new ListQuestionnaireDTO();
                    listQuestionnaireDTO.setMemberId(po.getMemberId());
                    listQuestionnaireDTO.setQuestionStatus("3");
                    List<Map<String, Object>> memberQuesScore = this.questionnaireService.getMemberQuesScore(listQuestionnaireDTO);
                    reMap.put("member",memberPO);
                    reMap.put("memberArchives",po);
                    reMap.put("memberQuesScore",memberQuesScore);
                    maps.add(reMap);
                }
            }
        }
        return maps;
    }

    @Override
    public List<DoctorMemberPO> listDoctorMemberByDoctorId(String memberId,String doctorId) {
        List<String> doctorIdList = this.doctorService.listTeamId(doctorId);
        ListDoctorMemberDTO memberDTO = new ListDoctorMemberDTO();
        memberDTO.setDoctorIdList(doctorIdList);
        memberDTO.setMemberId(memberId);
        return this.memberMapper.listDoctorMemberByDoctorId(memberDTO);
    }

    /*************************************************邪恶的分割线******************************************************
     * @version v6.0.0
     * @author: linyd
     * @date: 2020/03/03
     *****************************************************************************************************************/
    @Override
    public PageResult<InHospitalMemberVO> pagerInHospitalMember(ListMemberDTO listMemberDTO, PageRequest pager) {
        String hospitalId = listMemberDTO.getHospitalId();
        List<String> groupIds = null;
        if(StringUtils.isBlank(listMemberDTO.getGroupId())){
            List<DoctorDepartVO> list = this.doctorService.listDoctorDepart(listMemberDTO);
            if(list!=null&&list.size()>0){
                groupIds = new ArrayList<>(list.size());
                for(DoctorDepartVO vo : list){
                    groupIds.add(vo.getDepartId());
                }
            }
            listMemberDTO.setGroupId(null);
        }
        listMemberDTO.setGroupIdList(groupIds);
        PageHelper.startPage(pager.getPage(), pager.getRows());
        List<MemberListPO> list = this.memberMapper.listMemberByDepartment(listMemberDTO);
        this.setMemberDifferentLevels(list,hospitalId);
        PageResult<MemberListPO> poPageResult = new PageResult<>(list);
        if(poPageResult.getRows()!=null && poPageResult.getRows().size()>0){

            PageResult<InHospitalMemberVO> result = new PageResult<InHospitalMemberVO>(castInHospitalMemberVO(list));
            result.setPageSize(poPageResult.getPageSize());
            result.setPageNum(poPageResult.getPageNum());
            result.setTotalRows(poPageResult.getTotalRows());
            result.setTotalPages(poPageResult.getTotalPages());
            return result;
        }
        return new PageResult<InHospitalMemberVO>(null);
    }

    @Override
    public Long getOutOrInHospitalMemberNum(GetStatisticsDTO dto) {
        return this.memberMapper.getOutOrInHospitalMemberNum(dto);
    }

    @Override
    public List<MemberListPO> listInHospitalMember(ListMemberDTO listMemberDTO) {
        List<String> groupIds = null;
        if(StringUtils.isBlank(listMemberDTO.getGroupId())
                &&listMemberDTO.getGroupIdList()!=null
                && listMemberDTO.getGroupIdList().size()>0){
            List<DoctorDepartVO> list = this.doctorService.listDoctorDepart(listMemberDTO);
            if(list!=null&&list.size()>0){
                groupIds = new ArrayList<>(list.size());
                for(DoctorDepartVO vo : list){
                    groupIds.add(vo.getDepartId());
                }
            }
            listMemberDTO.setGroupId(null);
        }
        listMemberDTO.setGroupIdList(groupIds);
        List<MemberListPO> list = this.memberMapper.listMemberByDepartment(listMemberDTO);
        return list;
    }

    @Override
    @CacheEvict(value = "memberDrugPlan",allEntries = true)
    public void delMemberDrugRecord(String id) {
        this.memberMapper.delMemberDrugRecord(id);
    }

    @Override
    @CacheEvict(value = "memberDrugPlan",allEntries = true)
    public void delMemberDrugItem(String id) {
        this.memberMapper.delMemberDrugItem(id);
    }

    @Override
    public MemberCheckinInfoPO getMemberCheckinInfoById(String id,String hospitalId) {
        return this.memberMapper.getMemberCheckinInfoById(id,hospitalId);
    }

    /**
     * 患病类型
     * @param po
     * @return A 1型糖尿病 B 2型糖尿病 C 妊娠糖尿病 D 高血压 AD 1型糖尿病&高血压 BD 2型糖尿病&高血压 CD 妊娠糖尿病&高血压
     *         O 其它
     */
    @Override
    public String getDiseaseTypeOfMemberInfo(MemberListPO po) {
        //是否有糖尿病 1:是 2:否
        if("1".equals(po.getIsDiabetes())){
            //高血压:1有、2无
            if("1".equals(po.getEssentialHyp())){
                //糖尿病类型：1型(SUGAR_TYPE_001)、2型(SUGAR_TYPE_002)、妊娠(SUGAR_TYPE_003)、其他(SUGAR_TYPE_004)
                if("SUGAR_TYPE_001".equals(po.getDiabetesType())){
                    return "AD";
                } else if("SUGAR_TYPE_002".equals(po.getDiabetesType())){
                    return "BD";
                } else if("SUGAR_TYPE_003".equals(po.getDiabetesType())){
                    return "CD";
                }
            } else {
                //糖尿病类型：1型(SUGAR_TYPE_001)、2型(SUGAR_TYPE_002)、妊娠(SUGAR_TYPE_003)、其他(SUGAR_TYPE_004)
                if("SUGAR_TYPE_001".equals(po.getDiabetesType())){
                    return "A";
                } else if("SUGAR_TYPE_002".equals(po.getDiabetesType())){
                    return "B";
                } else if("SUGAR_TYPE_003".equals(po.getDiabetesType())){
                    return "C";
                }
            }
        } else {
            //高血压:1有、2无
            if("1".equals(po.getEssentialHyp())){
                return "D";
            }
        }
        return "O";
    }

    @Override
    public String getDiseaseTypeOfMemberInfoCH(MemberListPO po) {
        StringBuilder sb = new StringBuilder();
        //是否有糖尿病 1:是 2:否
        if("1".equals(po.getIsDiabetes())) {
            sb.append("糖尿病;");
            if (!StringUtils.isBlank(po.getDiabetesType())) {
                sb = new StringBuilder();
                if ("SUGAR_TYPE_001".equals(po.getDiabetesType())) {
                    //1型糖尿病
                    sb.append("1型糖尿病;");
                } else if ("SUGAR_TYPE_002".equals(po.getDiabetesType())) {
                    //2型糖尿病
                    sb.append("2型糖尿病;");
                } else if ("SUGAR_TYPE_003".equals(po.getDiabetesType())) {
                    //妊娠糖尿病
                    sb.append("妊娠糖尿病;");
                } else if ("SUGAR_TYPE_004".equals(po.getDiabetesType())) {
                    //其他糖尿病
                    sb.append("其他糖尿病;");
                }
            }
        }
        //高血压:1有、2无
        if ("1".equals(po.getEssentialHyp())){
            sb.append("高血压;");
        }
        if ("1".equals(po.getFat())){
            sb.append("肥胖");
        }
       //去除最后的;
        if (sb.length()>1){
            if (String.valueOf(sb.charAt(sb.length() - 1)).equals(";") ){
                sb.deleteCharAt(sb.length()-1);
            }
        }
        String result = sb.toString();
        if (StringUtils.isBlank(result)){
            result = "无";
        }
        return result;
    }

    @Override//作废
    public MemberPO memberDoctorIsManage(MemberPO memberPO, DoctorSessionBO doctorSessionBO) {
        if (null == memberPO){
           return null;
        }
        ListMemberDTO listMemberDTO = new ListMemberDTO();
        listMemberDTO.setDoctorId(doctorSessionBO.getDoctorId());
        listMemberDTO.setHospitalId(doctorSessionBO.getHospitalId());
        inHospitalMemberService.listMemberDTOAuthHandler(listMemberDTO);
        List<MemberListPO> memberListPOS = this.memberMapper.listHospitalBedWithMember(listMemberDTO);
        //住院患者
        Set<String> collect = memberListPOS.stream()
                .filter(e -> !StringUtils.isBlank(e.getDoctorZgCode()) && e.getDoctorZgCode().equals(doctorSessionBO.getDoctorId()))
                .map(MemberListPO::getMemberId)
                .collect(Collectors.toSet());
        // 医患关系表
        Set<String> doctorMemberIds = this.memberMapper.getDoctorMemberIds(doctorSessionBO.getDoctorId());
        doctorMemberIds.addAll(collect);
        boolean result = false;
        if (doctorMemberIds.size()>0){
            result = doctorMemberIds.contains(memberPO.getMemberId());
        }
        if (!result){
            memberPO = null;
        }
        return memberPO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String h5AddMemberOrUpdate(AddMemberDTO addMemberDTO,String groupId,String memberId,DoctorSessionBO doctorModel) {
        addMemberDTO.setMemberSource(MemberSourceConstant.MEMBER_SOURCE_DOC_APP); //患者来源 医生端APP
        if(StringUtils.isBlank(memberId)){
            memberId = memberService.addMember(addMemberDTO, doctorModel);
        }else {
            UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
            BeanUtils.copyProperties(updateMemberDTO,addMemberDTO);
            updateMemberDTO.setMemberId(memberId);
            memberService.updateMember(updateMemberDTO);
        }
        AddMemberDoctorRelateDTO dto = new AddMemberDoctorRelateDTO();
        dto.setDoctorId(doctorModel.getDoctorId());
        dto.setMemberId(memberId);
        if(groupId!=null && groupId.trim().length()>0){
            dto.setGroupId(groupId);
        } else {
            dto.setGroupId("0");
        }
        dto.setOperatorId(doctorModel.getDoctorId());
        dto.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_APP); //医患关系创建方式 医生端APP


        String hospitalId = this.getHospitalId(doctorModel.getDoctorId());
        DoctorMemberPO doctorMember = this.memberMapper.getDoctorMemberByHospital(memberId, hospitalId);
        if(doctorMember!=null){
            DoctorPO doctor = this.doctorService.getDoctorById(doctorMember.getDoctorId());
            throw new BusinessException("该患者已经存在本院的"+doctor.getDoctorName()+"的团队下了，请先解除。");
        }
        //添加医患关系
        memberService.addMemberDoctorRelate(dto);
        memberService.memberJoinHospitalHandler(doctorModel, memberId);
        return memberId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String h5AddInHospitalMemberOrUpdate(AddMemberDTO addMemberDTO, DoctorSessionBO doctorModel, AddInHospitalMemberDTO addInHospitalMemberDTO) {
        addMemberDTO.setMemberSource(MemberSourceConstant.MEMBER_SOURCE_DOC_APP); //患者来源 医生端APP
        String memberId = addInHospitalMemberDTO.getMemberId();
        if(StringUtils.isBlank(memberId)){
            memberId = memberService.addMember(addMemberDTO, doctorModel);
        }else {
            MemberPO memberPO = getMemberById(memberId);
            if(memberPO.getIdCard().equalsIgnoreCase(addMemberDTO.getIdCard()))
                addMemberDTO.setIdCard(null);
            UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
            BeanUtils.copyProperties(updateMemberDTO,addMemberDTO);
            updateMemberDTO.setMemberId(memberId);
            memberService.updateMember(updateMemberDTO);
        }
        addInHospitalMemberDTO.setMemberId(memberId);
        DoctorPO doctorPO = new DoctorPO();
        BeanUtils.copyProperties(doctorPO ,doctorModel);
        this.inHospitalMemberService.addInHospitalMember(addInHospitalMemberDTO ,doctorPO);
        memberService.memberJoinHospitalHandler(doctorModel, memberId);
        return memberId;
    }

    @Override
    public RangeBO getMemberRangeForFollow(MemberRangeDTO rangeDTO) {
        RangeBO rangeBO = this.memberMapper.getMemberRange(rangeDTO.getMemberId());
        if (rangeBO == null){  //获取系统默认的控制目标
            rangeBO = new RangeBO();
            ControlTargetConstant.BaseInfo baseInfo = new ControlTargetConstant().new BaseInfo();
            baseInfo.setSex(rangeDTO.getSex());
            baseInfo.setChd(rangeDTO.getChd());
            baseInfo.setDiabetesType(rangeDTO.getDiabetesType());
            baseInfo.setEssentialHyp(rangeDTO.getEssentialHyp());
            baseInfo.setBirthday(rangeDTO.getBirthday());
            baseInfo.setIsDiabetes(rangeDTO.getIsDiabetes());
            baseInfo.setHypLayer(rangeDTO.getHypLayer());
            baseInfo.setCkd(rangeDTO.getCkd());
            baseInfo.setHypBfz(rangeDTO.getHypBfz());
            baseInfo.setDiabetesBfz(rangeDTO.getDiabetesBfz());

            MemberControlTargetBO targetBO = ControlTargetConstant.getDefaultControlTarget(baseInfo);
            BeanUtils.copyProperties(rangeBO, targetBO);
        }
        return rangeBO;
    }

    @Override
    public long countPayMember(GetStatisticsDTO dto) {
        return this.memberMapper.countPayMember(dto);
    }

    @Override
    public List<String> listDoctorMonitorMemberId(DoctorSessionBO doctorSessionBO) {
        List<String> listMemberId = new ArrayList<>();
        String doctorId = doctorSessionBO.getDoctorId();
        String hospitalId = null;
        //有切换医院权限
        if (doctorSessionBO.getSwitchHospital() != null && doctorSessionBO.getSwitchHospital() == 1){
            hospitalId = doctorSessionBO.getHospitalId();
        }
        ListGroupsDTO dto = new ListGroupsDTO();
        dto.setDoctorId(doctorId);
        dto.setHospitalId(hospitalId);
        dto.setCountPeopleNumber(false);
        List<GroupsVO> groupsVOS = this.doctorService.listGroups(dto);
        for(GroupsVO vo : groupsVOS){
            if(vo.getItems()!=null){
                Integer type = vo.getType();
                List<MemberListPO> listMembr = new ArrayList<>();
                PagerMemberDTO pagerMemberDTO = new PagerMemberDTO();
                pagerMemberDTO.setHospitalId(hospitalId);
                if(GroupConstant.IN_HOSPITAL.equals(type)){
                    List<DoctorGroupVO> vos = (ArrayList<DoctorGroupVO>)vo.getItems();
                    for(DoctorGroupVO doctorGroupVO : vos){
//                        if(doctorGroupVO.getPeopleNumber() != null && doctorGroupVO.getPeopleNumber()>0){
                            pagerMemberDTO.setDoctorId(doctorGroupVO.getDoctorId());
                            pagerMemberDTO.setGroupId(doctorGroupVO.getGroupId());
                            listMembr = this.memberMapper.listMemberByDepart(pagerMemberDTO);
//                        }
                        if(listMembr!=null && listMembr.size()>0){
                            for(MemberListPO memberListPO : listMembr){
                                String memberId = memberListPO.getMemberId();
                                if(listMemberId.contains(memberId)){
                                    continue;
                                } else {
                                    listMemberId.add(memberId);
                                }
                            }
                        }
                    }

                } else if(GroupConstant.OUT_HOSPITAL.equals(type)){
                    List<HashMap> maps = (ArrayList<HashMap>)vo.getItems();
                    for(HashMap map : maps){
                        if(map.get("groups")!=null){
                            List<DoctorGroupVO> vos = (ArrayList<DoctorGroupVO>)map.get("groups");
                            for(DoctorGroupVO doctorGroupVO : vos){
//                                if(doctorGroupVO.getPeopleNumber() != null && doctorGroupVO.getPeopleNumber()>0){
                                    pagerMemberDTO.setDoctorId(doctorGroupVO.getDoctorId());
                                    pagerMemberDTO.setGroupId(doctorGroupVO.getGroupId());
                                    if (!StringUtils.isBlank(hospitalId)){
                                        listMembr = this.memberMapper.listMemberForHospital(pagerMemberDTO);
                                    }else{
                                        listMembr = this.memberMapper.listMemberForV4(pagerMemberDTO);
                                    }
                                    if(listMembr!=null && listMembr.size()>0){
                                        for(MemberListPO memberListPO : listMembr){
                                            String memberId = memberListPO.getMemberId();
                                            if(listMemberId.contains(memberId)){
                                                continue;
                                            } else {
                                                listMemberId.add(memberId);
                                            }
                                        }
                                    }
//                                }
                            }
                        }
                    }
                }

            }
        }
        return listMemberId;
    }
    
    /**
     * 搜索患者
     * @author nzq
     * @date 2020-07-28
     * @return ResultModel
     */
    @Override
	public List<Map<String, Object>> listMemberBySearch(String departName, String bedNo, String memberName, String doctorId){
        if(StringUtils.isBlank(memberName)){
            if(!StringUtils.isBlank(bedNo)){
                memberName = bedNo;
            } else if(!StringUtils.isBlank(departName)){
                memberName = departName;
            }
        }
        Map<String,Object> queryMap = new HashMap<String, Object>();
        queryMap.put("memberName", memberName);
        queryMap.put("doctorId", doctorId);
        List<MemberBedDTO> memberBedList =  this.memberMapper.listMemberBySearch(queryMap);
        List<Map<String, Object>> returnList = ReflectTool.getListFromBean(memberBedList, new Object[]{"departmentId","departmentName","roomId","memberId","memberName","bedId","bedNo"});
        return returnList;
	}

    @Override
    public DoctorMemberRemarkPO getDoctorMemberRemark(String memberId, String doctorId) {
        return this.memberMapper.getDoctorMemberRemark(doctorId ,memberId);
    }

    @Override
    public Result initMemberArchesONfood(String name, String password, String memberId, String hospitalId) {
        Result result = new Result();
        if (name.equals("admin") && password.equals("comv")) {
            int total = this.memberMapper.countMemberTotal();
            for (int i = 0; i < total; i = i + 1000) {
                List<MemberArchivesPO> memberArchivesPOS = this.memberMapper.listMemberArchivesTwo(i);
                for (MemberArchivesPO po : memberArchivesPOS) {
                    if (StringUtils.isBlank(po.getArchivesJson())) {
                        continue;
                    }
                    Map<String, Object> archivesJsonmap = JSON.parseObject(po.getArchivesJson());
                    if (archivesJsonmap.get("history")== null || StringUtils.isBlank(archivesJsonmap.get("history").toString())) {
                        continue;
                    }
                    Map<String, Object> historyjson = JsonSerializer.jsonToMap(archivesJsonmap.get("history").toString());
                    historyjson.put("bs_dinner_jc", "");
                    historyjson.put("bs_wanczslx", "");
                    historyjson.put("bs_lunch_jc", "");
                    historyjson.put("bs_wuczslx", "");
                    historyjson.put("bs_jcnr", "");
                    historyjson.put("bs_zczslx", "");
                    archivesJsonmap.put("history", historyjson);
                    MemberArchivesDTO dto = new MemberArchivesDTO();
                    dto.setMemberId(po.getMemberId());
                    dto.setArchivesJson(archivesJsonmap.toString());
                    this.memberService.updateMemberArchive(dto);
                }
            }
            result.setMessage("初始化成功。");
        }
        if (name.equals("1000") && password.equals("1000")){
            MemberArchivesPO memberArchivesPO = this.memberMapper.getMemberArchives(memberId,DEFAULT_FOREIGN_ID);
            if (memberArchivesPO!=null && !StringUtils.isBlank(memberArchivesPO.getArchivesJson())){
                Map<String, Object> archivesJsonmap = JSON.parseObject(memberArchivesPO.getArchivesJson());
                if (!StringUtils.isBlank(archivesJsonmap.get("history").toString())) {
                    Map<String, Object> historyjson = JsonSerializer.jsonToMap(archivesJsonmap.get("history").toString());
                    historyjson.put("bs_dinner_jc", "");
                    historyjson.put("bs_wanczslx", "");
                    historyjson.put("bs_lunch_jc", "");
                    historyjson.put("bs_wuczslx", "");
                    historyjson.put("bs_jcnr", "");
                    historyjson.put("bs_zczslx", "");
                    archivesJsonmap.put("history", historyjson);
                    MemberArchivesDTO memberArchivesDTO = new MemberArchivesDTO();
                    memberArchivesDTO.setMemberId(memberArchivesPO.getMemberId());
                    memberArchivesDTO.setArchivesJson(archivesJsonmap.toString());
                    memberService.updateMemberArchive(memberArchivesDTO);
                    result.setMessage("初始化成功。");
                }
            }
        }
        return result;
    }


    /**
     * 获取使用的设备信息
     * @param memberId
     * @return
     */
    private String getUseMachineInfo(String memberId,String hospitalId){
        List<Integer> machineList = new ArrayList<>();
        //处理动态血糖
        List<DYMemberSensorPO> memberSensorPOS = this.dyMemberSensorService.getDYMemberSensorPOByMemberId(memberId);
        for (DYMemberSensorPO po : memberSensorPOS) {
            //1:查询这个探头绑定的信息
            DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getDyBloodSugar(po.getSensorNo());
            String startTime = DateHelper.getNowDate();
            String endTime = "";
            if (dyypBloodSugarPO != null) {
                //2:计算探头从第一条上传血糖开始时间到现在使用了多少天.
                endTime = DateHelper.dateToString(dyypBloodSugarPO.getRecordTime());
            } else {
                //2:计算探头从绑定开始时间到现在使用了多少天.
                endTime = po.getInsertDt();
            }
            int day = DateHelper.dateCompareGetDay(startTime, endTime);
            if (day <= 14) {
                if (machineList.indexOf(MemberConstant.MEMBER_MACHINE_DYNAMIC_BLOOD_SUGAR)<0){
                    machineList.add(MemberConstant.MEMBER_MACHINE_DYNAMIC_BLOOD_SUGAR);
                }
            }
        }
        MemberCheckinInfoPO byMemberId = this.memberCheckinInfoMapper.getMemberCheckinInfoByMemberId(memberId, hospitalId);
        if (byMemberId != null){
            //胰岛素泵
            ListMemberYzDTO dto = new ListMemberYzDTO();
            dto.setYzItemCodeList(Collections.singletonList(YzConstant.YZ_ITEM_CODE_INSULIN_PUMP));
            dto.setMemberId(memberId);
            dto.setRecordOriginList(Collections.singletonList(YzConstant.RECORD_ORIGIN_VIRTUAL_WARD));
            dto.setYzStatusList(new ArrayList<>(Arrays.asList(
                    String.valueOf(YzConstant.YZ_STATUS_EXECUTED)
                    ,String.valueOf(YzConstant.YZ_STATUS_STOP)
            )));
            dto.setHospitalId(byMemberId.getHospitalId());
            dto.setVisitNo(byMemberId.getAdmNo());
            List list = this.yzService.listMemberYz(dto);
            if(list != null && !list.isEmpty()){
                machineList.add(MemberConstant.MEMBER_MACHINE_INSULIN_PUMP);
            }
        }

        if(machineList.isEmpty()){
            return "";
        }
        return Joiner.on("^").join(machineList);
    }

    //判断是否是住院患者
    @Override
    public Integer judgeMemberType(String memberId, String hospitalId) {
        MemberCheckinInfoPO inHos = memberCheckinInfoMapper.getMemberCheckinInfoByMemberId2(memberId, hospitalId);
        if (null != inHos && inHos.getCheckinStatus().equals("1")){
            return MemberConstant.MEMBER_TYPE_IN_HOSP;
        }else {
            return MemberConstant.MEMBER_TYPE_OUT_DEPART;
        }
    }

    /**
     * 修改患者脱敏字段处理
     * @param member
     * @param updateMember
     */
    private void updateMemberDesensitizationFieldHandler(UpdateMemberDTO updateMember){
        if(!StringUtils.isBlank(updateMember.getMobilePhone()) && updateMember.getMobilePhone().indexOf("*") > 0){
            updateMember.setMobilePhone(null);
        }
        if(!StringUtils.isBlank(updateMember.getIdCard()) && updateMember.getIdCard().indexOf("*") > 0){
            updateMember.setIdCard(null);
        }
    }

    @Override
    public String memberJoinHospitalHandler(DoctorSessionBO doctorSessionBO, String memberId){
        String joinTime = null;
        if (null != doctorSessionBO){
            MemberJoinHospitalPO memberJoinHospital = manageService.getMemberJoinHospital(memberId, doctorSessionBO.getHospitalId());

            if (null == memberJoinHospital){
                MemberPO memberPO = memberService.getMemberById(memberId);
                //有高血压或者糖尿病才会纳入患者管理（慢病管理）
                String isDiabetes = memberPO.getIsDiabetes();
                String essentialHyp = memberPO.getEssentialHyp();
                if (null != memberPO &&
                        (!StringUtils.isBlank(isDiabetes) && memberPO.getIsDiabetes().equals("1")) ||
                        (!StringUtils.isBlank(essentialHyp) && memberPO.getEssentialHyp().equals("1"))){
                    joinTime = addMemberJoinHospitalHandler(doctorSessionBO,memberId);
                }
            }else {
                joinTime = memberJoinHospital.getInsertDt();
            }
        }
        return joinTime;
    }

    //纳入患者管理（慢病管理）
    private String addMemberJoinHospitalHandler(DoctorSessionBO doctorSessionBO, String memberId){
        String nowDate = DateHelper.getNowDate();
        MemberJoinHospitalPO po = new MemberJoinHospitalPO();
        po.setSid(DaoHelper.getSeq());
        po.setHospitalId(doctorSessionBO.getHospitalId());
        po.setMemberId(memberId);
        po.setoperatorId(doctorSessionBO.getDoctorId());
        po.setoperatorType(MemberJoinHospitalConstant.OPERATOR_TYPE_DOCTOR);
        po.setJoinType(MemberJoinHospitalConstant.JOIN_TYPE_MANUAL);
        po.setForeignId("-1");
        po.setInsertDt(nowDate);
        po.setUpdateDt(nowDate);
        manageService.addMemberJoinHospital(po);
        return nowDate;
    }

    @Override
    @Transactional
    public void memberInHospitalJoinManage(DoctorSessionBO doctorSessionBO,AddMemberMapperDTO addMemberMapperDTO) {
        String memberId = addMemberMapperDTO.getMemberId();
        MemberPO memberPO = memberService.getMemberById(memberId);
        if (null == memberPO){
            throw new BusinessException("该患者不存在，请确认");
        }
        if (manageService.hasMemberJoinHospital(memberId, doctorSessionBO.getHospitalId())){
            throw new BusinessException("该患者已纳入慢病管理，请确认");
        }
        //更新患者表疾病信息
        UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
        updateMemberDTO.setMemberId(memberId);
        updateMemberDTO.setIsDiabetes(addMemberMapperDTO.getIsDiabetes());
        updateMemberDTO.setEssentialHyp(addMemberMapperDTO.getEssentialHyp());
        updateMemberDTO.setDiabetesType(addMemberMapperDTO.getDiabetesType());
        updateMemberDTO.setHypType(addMemberMapperDTO.getHypType());
        memberService.updateMember(updateMemberDTO);
        //更新患者档案
        memberArchivementHandler(doctorSessionBO,addMemberMapperDTO);
        //纳入患者管理（慢病管理）
        addMemberJoinHospitalHandler(doctorSessionBO,memberId);
    }

    @Override
    public Map<String, Object> loadMemberHisEmbedded(String memberId,String hospitalId) {
        Map<String, Object> result = new HashMap<>();
        //处理基本信息
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(memberId);
        MemberPO member = memberMapper.getMember(getMemberDTO);
        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("memberId",member.getMemberId());
        memberMap.put("memberName",member.getMemberName());
        memberMap.put("sex",member.getSex());
        memberMap.put("age",DateHelper.getAge(member.getBirthday()));
        memberMap.put("height",member.getHeight());
        memberMap.put("weight",member.getWeight());
        memberMap.put("bmi",member.getBmi());
        memberMap.put("diabetesType",member.getDiabetesType());
        memberMap.put("hypType",member.getHypType());
        memberMap.put("diabetesTime",DateHelper.getAge(member.getDiabetesDate()));
        memberMap.put("hypTime",DateHelper.getAge(member.getHypDate()));
        memberMap.put("createDate",DateHelper.changeTimeFormat(member.getInsertDt(),DateHelper.DATETIME_FORMAT,DateHelper.DAY_FORMAT));
        if (!StringUtils.isBlank(member.getSbpPressure())&&!StringUtils.isBlank(member.getDbpPressure())){
            memberMap.put("bloodPressure",member.getSbpPressure()+'/'+member.getDbpPressure());
        }else{
            memberMap.put("bloodPressure",null);
        }
        result.put("member",memberMap);
        //检验
        GetMemberCheckoutDTO getMemberCheckoutDTO = new GetMemberCheckoutDTO();
        getMemberCheckoutDTO.setMemberId(memberId);
        getMemberCheckoutDTO.setHospitalId(hospitalId);
        Map<String, Object> checkoutMap = this.checkoutDetailServiceI.loadMemberDefaultCheckoutList(getMemberCheckoutDTO);
        result.put("checked",checkoutMap);
        //检查
        Map<String, Object> memberInspectionScreenResult = getMemberInspectionScreenResult(memberId);
        result.put("inspection",memberInspectionScreenResult);
        //血糖记录
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        listBloodSugarDTO.setMemberId(memberId);
        listBloodSugarDTO.setCodeDt("3");//一周
        listBloodSugarDTO.setLoadAll(1);
        List<Map<String ,Object>> sugarList = this.bloodSugarService.listBloodSugarPage(listBloodSugarDTO, new PageRequest());
        result.put("sugarList",sugarList);
        return result;
    }

    @Override
    public MemberPO getMemberByCertificateNo(CertificateGetMemberDTO getMemberDTO) {
        MemberPO member = this.memberMapper.getMemberByCertificateNo(getMemberDTO);
        if (null == member){
            throw new BusinessException("该患者不存在");
        }
        return member;
    }


    private Map<String,Object> getMemberInspectionScreenResult(String memberId){
        Map<String, Object> insResult = new HashMap<>();
        insResult.put("rightVpt","");
        insResult.put("leftVpt","");
        insResult.put("vptDate","");
        insResult.put("rightEyesResult","");
        insResult.put("leftEyesResult","");
        insResult.put("eyeDate","");
        insResult.put("leftPwv","");
        insResult.put("rightPwv","");
        insResult.put("pwvDate","");
        insResult.put("rightAbi","");
        insResult.put("leftAbi","");
        insResult.put("abiDate","");
        Map<String,Object> insParam = new HashMap<>();
        insParam.put("memberId",memberId);
        insParam.put("inspectCode","eyes");
        List<InspectionPO> eyesInsList = inspectionMapper.listInspectionByParams(insParam);
        if (eyesInsList.size()>0){
            InspectionPO inspectionPO = eyesInsList.get(0);
            String inspectDataJson = inspectionPO.getInspectDataJson();
            Map<String, Object> inspectDataMap = JsonSerializer.jsonToMap(inspectDataJson);
            if(null != inspectDataMap){
                inspectDataMap.put("eyeDate",inspectionPO.getReportDt());
                insResult.putAll(inspectDataMap);
            }
        }

        insParam.put("inspectCode","vpt");
        List<InspectionPO> vptInsList = inspectionMapper.listInspectionByParams(insParam);
        if (vptInsList.size()>0){
            InspectionPO inspectionPO = vptInsList.get(0);
            String inspectDataJson = inspectionPO.getInspectDataJson();
            Map<String, Object> inspectDataMap = JsonSerializer.jsonToMap(inspectDataJson);
            if(null != inspectDataMap){
                inspectDataMap.put("vptDate",inspectionPO.getReportDt());
                insResult.putAll(inspectDataMap);
            }
        }
        insParam.put("inspectCode","abi");
        List<InspectionPO> abiInsList = inspectionMapper.listInspectionByParams(insParam);
        if (abiInsList.size()>0){
            InspectionPO inspectionPO = abiInsList.get(0);
            String inspectDataJson = inspectionPO.getInspectDataJson();
            Map<String, Object> inspectDataMap = JsonSerializer.jsonToMap(inspectDataJson);
            if(null != inspectDataMap){
                inspectDataMap.put("abiDate",inspectionPO.getReportDt());
                inspectDataMap.put("pwvDate",inspectionPO.getReportDt());
                insResult.putAll(inspectDataMap);
            }
        }
        return insResult;
    }

}
