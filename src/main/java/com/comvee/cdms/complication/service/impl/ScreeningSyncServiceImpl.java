package com.comvee.cdms.complication.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.complication.constant.ScreeningConstant;
import com.comvee.cdms.complication.model.bo.GroupSyncBO;
import com.comvee.cdms.complication.model.bo.PatientSyncBO;
import com.comvee.cdms.complication.model.bo.ScreeningReportSyncBO;
import com.comvee.cdms.complication.model.bo.ScreeningSyncBO;
import com.comvee.cdms.complication.model.po.ScreeningDataPO;
import com.comvee.cdms.complication.model.po.ScreeningListPO;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.complication.model.po.ScreeningStatsPO;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.complication.service.ScreeningStatsService;
import com.comvee.cdms.complication.service.ScreeningSyncService;
import com.comvee.cdms.doctor.dto.AddGroupDTO;
import com.comvee.cdms.doctor.dto.DeleteGroupDTO;
import com.comvee.cdms.doctor.dto.UpdateGroupDTO;
import com.comvee.cdms.doctor.po.DoctorGroupPO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.foot.po.FootResultPO;
import com.comvee.cdms.foot.service.FootService;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.constant.MemberSourceConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.po.DoctorMemberPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatQrCodeConstant;
import com.comvee.cdms.other.dto.CreateStrQrCodeDTO;
import com.comvee.cdms.other.po.WechatQrcodePO;
import com.comvee.cdms.other.service.WechatQrCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
@Service("screeningSyncService")
public class ScreeningSyncServiceImpl implements ScreeningSyncService {

    private final static Logger log = LoggerFactory.getLogger(ScreeningSyncServiceImpl.class);

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private WechatQrCodeService wechatQrCodeService;

    @Autowired
    private FootService footService;

    @Autowired
    private ScreeningStatsService screeningStatsService;

    @Override
    public void syncGroup(String doctorId, String groupListJson) {
        List<GroupSyncBO> syncBOList = JSON.parseArray(groupListJson ,GroupSyncBO.class);
        if(syncBOList == null || syncBOList.size() == 0){
            return;
        }
        syncBOList.forEach(x -> {
            DoctorGroupPO doctorGroupPO = this.doctorService.getGroupById(x.getGroupId());
            if(x.getDataStatus() == 2 && doctorGroupPO == null){
                return;
            }
            //新增
            if(doctorGroupPO == null){
                AddGroupDTO addGroupDTO = new AddGroupDTO();
                addGroupDTO.setDoctorId(doctorId);
                addGroupDTO.setGroupName(x.getGroupName());
                this.doctorService.addGroupWithId(x.getGroupId(), x.getGroupName() ,doctorId);
                //删除
            }else if(x.getDataStatus() == 2){
                DeleteGroupDTO deleteGroupDTO = new DeleteGroupDTO();
                deleteGroupDTO.setGroupId(x.getGroupId());
                this.doctorService.deleteGroup(deleteGroupDTO);
                //修改
            }else{
                UpdateGroupDTO updateGroupDTO = new UpdateGroupDTO();
                updateGroupDTO.setGroupId(x.getGroupId());
                updateGroupDTO.setGroupName(x.getGroupName());
                this.doctorService.updateGroup(updateGroupDTO);
            }
        });
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void syncPatient(String doctorId, String patientListJson) {
        List<PatientSyncBO> patientSyncBOS = JSON.parseArray(patientListJson ,PatientSyncBO.class);
        if(patientSyncBOS == null || patientSyncBOS.size() == 0){
            return;
        }
        patientSyncBOS.forEach(x -> {
            try{
                MemberPO memberPO = this.memberService.getMemberByIdCard(x.getIdCard());
                if(memberPO == null){
                    memberNotExistHandler(x ,doctorId);
                }else{
                    memberExistHandler(x ,doctorId, memberPO);
                    if(x.getValid() == 0){
                        this.memberService.deleteMemberForScreening(memberPO.getMemberId() ,doctorId);;
                    }
                }
            }catch (Exception e){
                log.warn("同步患者失败,待同步的患者信息为:{}" , JSON.toJSONString(x) , e);
            }
        });
    }

    @Override
    public void syncScreeningList(String doctorId, String screeningListJson) {
        List<ScreeningSyncBO> syncBOList = JSON.parseArray(screeningListJson ,ScreeningSyncBO.class);
        if(syncBOList == null || syncBOList.size() == 0){
            return;
        }
        syncBOList.forEach(x -> {
            String memberId = Constant.DEFAULT_FOREIGN_ID;
            MemberPO member = this.memberService.getMemberByIdCard(x.getIdCard());
            if(member != null){
                memberId = member.getMemberId();
            }
            ScreeningListPO add = new ScreeningListPO();
            BeanUtils.copyProperties(add ,x);
            add.setMemberId(memberId);
            add.setDoctorId(doctorId);
            this.screeningService.addOrUpdateScreening(add);
        });
    }

    @Override
    public void syncScreeningReport(String reportJson, String doctorId) {
        ScreeningReportSyncBO screeningReportSyncBO = JSON.parseObject(reportJson ,ScreeningReportSyncBO.class);
        ScreeningReportPO screeningReportPO = this.screeningService.getScreeningReportById(screeningReportSyncBO.getReportId() ,null);
        if(screeningReportPO == null){
            MemberPO memberPO = this.memberService.getMemberByIdCard(screeningReportSyncBO.getIdCard());
            if(memberPO == null){
                return;
            }
            screeningReportPO = new ScreeningReportPO();
            BeanUtils.copyProperties(screeningReportPO ,screeningReportSyncBO);
            screeningReportPO.setDoctorId(doctorId);
            screeningReportPO.setMemberId(memberPO.getMemberId());
            this.screeningService.addScreeningReport(screeningReportPO);
        }else{
            ScreeningReportPO update = new ScreeningReportPO();
            BeanUtils.copyProperties(update ,screeningReportSyncBO);
            update.setReportId(screeningReportPO.getReportId());
            this.screeningService.updateScreeningReport(update);
        }
    }

    @Override
    public String syncScreeningReportWithFile(String reportJson, String doctorId, Map<String ,String> uploadMap) {
        ScreeningReportSyncBO screeningReportSyncBO = JSON.parseObject(reportJson ,ScreeningReportSyncBO.class);
        ScreeningReportPO screeningReportPO = this.screeningService.getScreeningReportById(screeningReportSyncBO.getReportId());
        if(screeningReportPO != null){
            imageUrlHandler(screeningReportPO ,uploadMap);
            String qrCodeData = createReportQrCode(screeningReportSyncBO);
            JSONObject json = JSONObject.parseObject(screeningReportSyncBO.getReportJson());
            json.put("qrCodeData" ,qrCodeData);
            screeningReportPO.setReportJson(json.toJSONString());
            this.screeningService.updateScreeningReport(screeningReportPO);
            return qrCodeData;
        }
        MemberPO memberPO = this.memberService.getMemberByIdCard(screeningReportSyncBO.getIdCard());
        if(memberPO == null){
            throw new BusinessException("该报告的患者不存在");
        }
        screeningReportPO = new ScreeningReportPO();
        BeanUtils.copyProperties(screeningReportPO ,screeningReportSyncBO);
        screeningReportPO.setDoctorId(doctorId);
        screeningReportPO.setMemberId(memberPO.getMemberId());
        imageUrlHandler(screeningReportPO ,uploadMap);
        String qrCodeData = createReportQrCode(screeningReportSyncBO);
        JSONObject json = JSONObject.parseObject(screeningReportSyncBO.getReportJson());
        json.put("qrCodeData" ,qrCodeData);
        screeningReportPO.setReportJson(json.toJSONString());
        this.screeningService.addScreeningReport(screeningReportPO);
        return qrCodeData;
    }

    /**
     * 图片url处理
     * @param screeningReportPO
     * @param uploadMap
     */
    private void imageUrlHandler(ScreeningReportPO screeningReportPO ,Map<String ,String> uploadMap){
        if(ScreeningConstant.SCREENING_TYPE_ABI == screeningReportPO.getScreeningType()
                || ScreeningConstant.SCREENING_TYPE_VPT == screeningReportPO.getScreeningType() ){
            screeningReportPO.setCutImageUrl(uploadMap.get("cutImage"));
            screeningReportPO.setImageUrl(uploadMap.get("image"));
        }else if(ScreeningConstant.SCREENING_TYPE_EYES == screeningReportPO.getScreeningType()){
            screeningReportPO.setImageUrl(JSON.toJSONString(uploadMap));
            screeningReportPO.setCutImageUrl(JSON.toJSONString(uploadMap));
        }else if(ScreeningConstant.SCREENING_TYPE_EMG == screeningReportPO.getScreeningType()){
            screeningReportPO.setImageUrl(JSON.toJSONString(uploadMap));
            screeningReportPO.setCutImageUrl(JSON.toJSONString(uploadMap));
        }
    }

    @Override
    public void syncScreeningData(String screeningDataJson, String doctorId) {
        List<ScreeningDataPO> screeningDataPOS = JSON.parseArray(screeningDataJson ,ScreeningDataPO.class);
        if(screeningDataPOS == null || screeningDataPOS.size() == 0){
            return;
        }
        screeningDataPOS.forEach(x ->{
            MemberPO memberPO = this.memberService.getMemberByIdCard(x.getIdCard());
            String memberId =
                    memberPO == null ? Constant.DEFAULT_FOREIGN_ID : memberPO.getMemberId();
            x.setDoctorId(doctorId);
            x.setMemberId(memberId);
            this.screeningService.addScreeningData(x);
        });
    }

    @Override
    public void syncFootPrescriptionResult(String resultList, String doctorId) {
        List<FootResultPO> list = JSON.parseArray(resultList ,FootResultPO.class);
        if(list == null || list.size() == 0){
            return;
        }
        list.forEach(x -> {
            MemberPO memberPO = this.memberService.getMemberByIdCard(x.getIdCard());
            if(memberPO != null){
                x.setDoctorId(doctorId);
                x.setMemberId(memberPO.getMemberId());
                this.footService.addPrescriptionFootResultPO(x);
            }
        });
    }

    @Override
    public void syncScreeningStats(String jsonList, String doctorId) {
        List<ScreeningStatsPO> statsPOList = JSON.parseArray(jsonList ,ScreeningStatsPO.class);
        statsPOList.forEach(x ->{
             MemberPO memberPO = this.memberService.getMemberByIdCard(x.getIdCard());
             if(memberPO != null){
                x.setMemberId(memberPO.getMemberId());
                x.setDoctorId(doctorId);
                 this.screeningStatsService.addScreeningStats(x);
             }

        });
    }

    /**
     * 创建报告的二维码
     * @param screeningReportSyncBO
     * @return
     */
    private String createReportQrCode(ScreeningReportSyncBO screeningReportSyncBO){
        CreateStrQrCodeDTO createStrQrCodeDTO = new CreateStrQrCodeDTO();
        createStrQrCodeDTO.setBusinessType(WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_SCREENING_REPORT);
        createStrQrCodeDTO.setForeignId(screeningReportSyncBO.getReportId());
        createStrQrCodeDTO.setExpireSeconds(REPORT_QR_CODE_EXPIRE_SECONDS);
        createStrQrCodeDTO.setDescription("筛查报告二维码");
        JSONObject dataJson = new JSONObject();
        dataJson.put("screeningId" ,screeningReportSyncBO.getScreeningId());
        dataJson.put("screeningType" ,screeningReportSyncBO.getScreeningType());
        createStrQrCodeDTO.setDataJson(dataJson.toJSONString());
        WechatQrcodePO wechatQrCode = this.wechatQrCodeService.createTemporaryStrQrCode(createStrQrCodeDTO);
        return wechatQrCode.getDataUrl();
    }

    /**
     * 筛查报告二维码过期时间
     */
    private final static long REPORT_QR_CODE_EXPIRE_SECONDS = 2592000L;

    /**
     * 患者不存在的处理
     * @param patientSyncBO
     * @param doctorId
     */
    private void memberNotExistHandler(PatientSyncBO patientSyncBO ,String doctorId){
        AddMemberDTO addMemberDTO = new AddMemberDTO();
        addMemberDTO.setDiabetesType(patientSyncBO.getDiabetesType());
        addMemberDTO.setIdCard(patientSyncBO.getIdCard());
        addMemberDTO.setBirthday(patientSyncBO.getBirthday());
        addMemberDTO.setHeight(patientSyncBO.getHeight());
        addMemberDTO.setWeight(patientSyncBO.getWeight());
        addMemberDTO.setMemberName(patientSyncBO.getPatientName());
        addMemberDTO.setSex(patientSyncBO.getSex());
        addMemberDTO.setMobilePhone(patientSyncBO.getMobilePhone());
        addMemberDTO.setMemberSource(MemberSourceConstant.MMEMBER_SOURCE_SCREEING_SYSTEM);  //患者来源 筛查系统
        String memberId = this.memberService.addMember(addMemberDTO , null);

        AddMemberDoctorRelateDTO addMemberDoctorRelateDTO = new AddMemberDoctorRelateDTO();
        addMemberDoctorRelateDTO.setGroupId(patientSyncBO.getGroupId());
        addMemberDoctorRelateDTO.setOperatorId(doctorId);
        addMemberDoctorRelateDTO.setDoctorId(doctorId);
        addMemberDoctorRelateDTO.setMemberId(memberId);
        addMemberDoctorRelateDTO.setConcernStatus(patientSyncBO.getConcernStatus());
        addMemberDoctorRelateDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_SCREEING_SYSTEM);  //筛查系统医患关系创建方式
        this.memberService.addMemberDoctorRelate(addMemberDoctorRelateDTO);

        DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
        if(doctorPO == null){
            return;
        }
        this.memberService.updateMemberVisitNo(doctorPO.getHospitalId() ,memberId ,patientSyncBO.getVisitNo());

        this.memberService.memberScreeningArchiveHandler(patientSyncBO ,memberId ,doctorId);
    }

    /**
     * 患者存在的处理
     * @param patientSyncBO
     * @param doctorId
     * @param memberPO
     */
    private void memberExistHandler(PatientSyncBO patientSyncBO ,String doctorId ,MemberPO memberPO){
        UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
        BeanUtils.copyProperties(updateMemberDTO ,patientSyncBO);
        updateMemberDTO.setMemberName(patientSyncBO.getPatientName());
        updateMemberDTO.setMemberId(memberPO.getMemberId());
        this.memberService.updateMember(updateMemberDTO);


        ListDoctorMemberDTO listDoctorMemberDTO = new ListDoctorMemberDTO();
        listDoctorMemberDTO.setDoctorId(doctorId);
        listDoctorMemberDTO.setMemberId(memberPO.getMemberId());
        DoctorMemberPO doctorMemberPO = this.memberService.getDoctorMember(listDoctorMemberDTO);
        if(doctorMemberPO == null){
            AddMemberDoctorRelateDTO addMemberDoctorRelateDTO = new AddMemberDoctorRelateDTO();
            addMemberDoctorRelateDTO.setGroupId(patientSyncBO.getGroupId());
            addMemberDoctorRelateDTO.setOperatorId(doctorId);
            addMemberDoctorRelateDTO.setDoctorId(doctorId);
            addMemberDoctorRelateDTO.setMemberId(memberPO.getMemberId());
            addMemberDoctorRelateDTO.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_SCREEING_SYSTEM); //筛查系统医患关系创建方式
            this.memberService.addMemberDoctorRelate(addMemberDoctorRelateDTO);
        }else{
            UpdateDoctorMemberDTO updateDoctorMemberDTO = new UpdateDoctorMemberDTO();
            updateDoctorMemberDTO.setGroupId(patientSyncBO.getGroupId());
            updateDoctorMemberDTO.setDoctorId(doctorId);
            updateDoctorMemberDTO.setMemberId(memberPO.getMemberId());
            updateDoctorMemberDTO.setConcernStatus(patientSyncBO.getConcernStatus());
            this.memberService.updateMemberDoctor(updateDoctorMemberDTO);
        }
        visitNoHandler(memberPO.getMemberId() ,patientSyncBO.getVisitNo() ,doctorId);


        this.memberService.memberScreeningArchiveHandler(patientSyncBO ,memberPO.getMemberId() ,doctorId);
    }

    /**
     * 就诊卡号处理
     * @param memberId
     * @param visitNo
     * @param doctorId
     */
    private void visitNoHandler(String memberId , String visitNo ,String doctorId){
        DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
        if(doctorPO == null){
            return;
        }
        this.memberService.updateMemberVisitNo(doctorPO.getHospitalId() ,memberId ,visitNo);
    }

    /**
     * 筛查档案处理
     * @param patientSyncBO
     * @param memberId
     * @param doctorId
     */
    private void memberScreeningArchiveHandler(PatientSyncBO patientSyncBO ,String memberId ,String doctorId){

    }
}
