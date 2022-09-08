package com.comvee.cdms.foot.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.constant.ScreeningConstant;
import com.comvee.cdms.complication.constant.StatsConstant;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.complication.model.po.ScreeningStatsPO;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.complication.service.ScreeningStatsService;
import com.comvee.cdms.foot.bo.FootPrescriptionQrCodeBO;
import com.comvee.cdms.foot.constant.FootConstant;
import com.comvee.cdms.foot.constant.FootPrescriptionConstant;
import com.comvee.cdms.foot.constant.NephropathyConstant;
import com.comvee.cdms.foot.dto.ListFootDTO;
import com.comvee.cdms.foot.helper.EyesAssessTool;
import com.comvee.cdms.foot.helper.FootHelper;
import com.comvee.cdms.foot.helper.NephropathyAssessTool;
import com.comvee.cdms.foot.mapper.FootMapper;
import com.comvee.cdms.foot.mapper.FootResultMapper;
import com.comvee.cdms.foot.po.FootPO;
import com.comvee.cdms.foot.po.FootReportPO;
import com.comvee.cdms.foot.po.FootResultPO;
import com.comvee.cdms.foot.vo.FootVO;
import com.comvee.cdms.foot.vo.ReportRelateStatusVO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.dto.UpdateMemberDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.dto.UsePackageServiceDTO;
import com.comvee.cdms.packages.service.PackageService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @author: wangyx
 * @date: 2018/12/27
 */
@Service("footService")
public class FootServiceImpl implements FootService {

    private final static Logger log = LoggerFactory.getLogger(FootServiceImpl.class);

    @Autowired
    private FootMapper footMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private FootResultMapper footResultMapper;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private ScreeningStatsService screeningStatsService;

    @Autowired
    @Lazy
    private SyncFootService syncFootService;

    @Autowired
    private PackageService packageService;

    @Override
    public List<FootPO> listFoot(ListFootDTO listFootDTO) {
        return this.footMapper.listFoot(listFootDTO);
    }

    @Override
    public PageResult<FootPO> listFootPage(ListFootDTO listFootDTO, PageRequest page) {

        PageHelper.startPage(page.getPage(),page.getRows());
        if (!StringUtils.isBlank(listFootDTO.getHospitalId())){  //有切换医院权限
            listFootDTO.setDoctorId(null);
        }
        List<FootPO> list= this.footMapper.listFoot(listFootDTO);
        PageResult<FootPO> tempPageResult = new PageResult<FootPO>(list);
        if(list!=null&&list.size()>0){

            return tempPageResult;
        }
        return  new PageResult<FootPO>();

    }

    @Override
    public FootPO getFoot(String sid) {
        FootPO foot = this.footMapper.getFoot(sid);
        //患者信息脱敏
        if (null != foot && !StringUtils.isBlank(foot.getAssistCheck())){
            String assistCheck = foot.getAssistCheck();
            Map<String, Object> oldAssistCheckMap= JsonSerializer.jsonToMap(assistCheck);
            if (assistCheck.indexOf("patientInfo")>=0){
                Object patientInfo = oldAssistCheckMap.get("patientInfo");
                if (patientInfo instanceof Map){
                    Map<String, Object> patientInfoMap = (Map<String, Object>) patientInfo;
                    if (assistCheck.indexOf("idCard")>=0){
                        if (null != patientInfoMap.get("idCard")){
                            String idCard = patientInfoMap.get("idCard").toString();
                            if (!StringUtils.isBlank(idCard)){
                                idCard = ValidateTool.tuoMin(idCard,4,4,"*");
                                patientInfoMap.put("idCard",idCard);
                                oldAssistCheckMap.put("patientInfo",patientInfoMap);
                                assistCheck = JsonSerializer.objectToJson(oldAssistCheckMap);
                            }

                        }
                    }
                }
            }
            foot.setAssistCheck(assistCheck);

        }
        return foot;
    }

    @Override
    public String addFoot(FootPO footPO) {
/*        boolean checkResult = this.memberService.checkDoctorMemberRelationExists(footPO.getMemberId() ,footPO.getTeamId());
        if(!checkResult){
            log.warn("不存在有效的医患关系，创建足部管理处方失败！医生id:{},患者id:{},操作者id:{}" ,footPO.getTeamId() ,footPO.getMemberId() ,footPO.getDoctorId());
            throw new BusinessException("不存在有效的医患关系，创建足部管理处方失败...");
        }*/
        String id = footPO.getFollowId();
        if(StringUtils.isBlank(id)){
            id = DaoHelper.getSeq();
        }
        footPO.setFollowId(id);
        if(StringUtils.isBlank(footPO.getFootType())){
            footPO.setFootType("1");
        }
        if(footPO.getHasRelation() == null){
            footPO.setHasRelation(FootConstant.FOOT_HAS_RELATION_NO);
        }
        this.footMapper.addFoot(footPO);
        return id;
    }

    @Override
    public void modifyFoot(FootPO footPO) {
        this.footMapper.modifyFoot(footPO);
        if("1".equals(footPO.getSaveType())){
            UsePackageServiceDTO usePackageServiceDTO = new UsePackageServiceDTO();
            usePackageServiceDTO.setDoctorId(footPO.getDoctorId());
            usePackageServiceDTO.setMemberId(footPO.getMemberId());
            usePackageServiceDTO.setServiceCode(ServiceCode.CHU_FANG);
            this.packageService.usePackageServiceWithLock(usePackageServiceDTO);
        }
    }

    /**
     * 评估结果统计处理
     * @param assessResult
     */
    private void assessResultStatHandler(String assessResult ,FootPO footPO ,String assessCheck){
        if(StringUtils.isBlank(assessResult)){
            return;
        }
        if(!String.valueOf(FootConstant.FOOT_TYPE_ZCJ).equals(footPO.getFootType())){
            return;
        }
        MemberPO memberPO = this.memberService.getMemberById(footPO.getMemberId());
        JSONObject jsonObject = JSONObject.parseObject(assessResult);
        String wagnerLevel = jsonObject.getString("wagner_level");
        if(!StringUtils.isBlank(wagnerLevel)){
            addPrescriptionResult(StatsConstant.STATS_ITEM_CODE_WAGNER ,wagnerLevel ,memberPO.getIdCard()
                    ,footPO.getMemberId() ,footPO.getTeamId());
            //糖尿病足统计
            String itemValue = "";
            if(Integer.parseInt(wagnerLevel) >= 1){
                itemValue = "1";
            }else{
                itemValue = "0";
            }
            ScreeningStatsPO screeningStatsPO = new ScreeningStatsPO();
            screeningStatsPO.setValid(1);
            screeningStatsPO.setItemCode(StatsConstant.STATS_ITEM_CODE_DM_FOOT);
            screeningStatsPO.setItemValue(itemValue);
            screeningStatsPO.setIdCard(memberPO.getIdCard());
            screeningStatsPO.setDoctorId(footPO.getDoctorId());
            screeningStatsPO.setMemberId(footPO.getMemberId());
            screeningStatsPO.setInsertDt(DateHelper.getNowDate());
            screeningStatsPO.setUpdateDt(DateHelper.getNowDate());
            this.screeningStatsService.addScreeningStats(screeningStatsPO);
        }
        JSONObject nephropathyAssess = jsonObject.getJSONObject("nephropathyAssess");
        if(nephropathyAssess == null){
            return;
        }
        String egfr = nephropathyAssess.getString("egfrStages");
        if(!StringUtils.isBlank(egfr)){
            addPrescriptionResult(StatsConstant.STATS_ITEM_CODE_EGFR ,egfr ,memberPO.getIdCard()
                    ,footPO.getMemberId() ,footPO.getTeamId());
        }

        String uacr = nephropathyAssess.getString("uacrStages");
        if(!StringUtils.isBlank(egfr) || !StringUtils.isBlank(uacr)){
            String itemValue = "";
            boolean hasNephroma = (!StringUtils.isBlank(egfr) && Integer.parseInt(egfr) >= NephropathyConstant.eGFR_STAGES_G3a)
                    || (!StringUtils.isBlank(uacr) && Integer.parseInt(uacr) >= NephropathyConstant.uACR_STAGES_A2);
            if(hasNephroma){
                itemValue = "1";
            }else{
                itemValue = "0";
            }
            ScreeningStatsPO screeningStatsPO = new ScreeningStatsPO();
            screeningStatsPO.setValid(1);
            screeningStatsPO.setItemCode(StatsConstant.STATS_ITEM_CODE_NEPHROMA);
            screeningStatsPO.setItemValue(itemValue);
            screeningStatsPO.setIdCard(memberPO.getIdCard());
            screeningStatsPO.setDoctorId(footPO.getDoctorId());
            screeningStatsPO.setMemberId(footPO.getMemberId());
            screeningStatsPO.setInsertDt(DateHelper.getNowDate());
            screeningStatsPO.setUpdateDt(DateHelper.getNowDate());
            this.screeningStatsService.addScreeningStats(screeningStatsPO);

        }

        if(!StringUtils.isBlank(uacr)){
            ScreeningStatsPO screeningStatsPO = new ScreeningStatsPO();
            screeningStatsPO.setValid(1);
            screeningStatsPO.setItemCode(StatsConstant.STATS_ITEM_CODE_UACR);
            screeningStatsPO.setItemValue(uacr);
            screeningStatsPO.setIdCard(memberPO.getIdCard());
            screeningStatsPO.setDoctorId(footPO.getDoctorId());
            screeningStatsPO.setMemberId(footPO.getMemberId());
            screeningStatsPO.setInsertDt(DateHelper.getNowDate());
            screeningStatsPO.setUpdateDt(DateHelper.getNowDate());
            this.screeningStatsService.addScreeningStats(screeningStatsPO);
        }

    }

    /**
     * 添加评估结果
     * @param assessCode
     * @param assessValue
     * @param idCard
     */
    private void addPrescriptionResult(String assessCode ,String assessValue ,String idCard ,String memberId ,String doctorId){
        ScreeningStatsPO screeningStatsPO = new ScreeningStatsPO();
        screeningStatsPO.setValid(1);
        screeningStatsPO.setItemCode(assessCode);
        screeningStatsPO.setItemValue(assessValue);
        screeningStatsPO.setIdCard(idCard);
        screeningStatsPO.setDoctorId(doctorId);
        screeningStatsPO.setMemberId(memberId);
        screeningStatsPO.setInsertDt(DateHelper.getNowDate());
        screeningStatsPO.setUpdateDt(DateHelper.getNowDate());
        this.screeningStatsService.addScreeningStats(screeningStatsPO);
    }


    @Override
    public Map<String, Object> outFootSuggest(String memberId, FootVO followModel) throws Exception {
        GetMemberDTO getMemberDTO=new GetMemberDTO();
        getMemberDTO.setMemberId(memberId);
        MemberPO member = memberService.getMember(getMemberDTO);
        RangeBO memberRange = memberService.getMemberRange(memberId);
        Map<String, Object> reMap = FootHelper.outFootSuggest(member, memberRange, followModel);
        return reMap;
    }

    @Override
    public FootPO getFootNew(ListFootDTO listFootDTO) {
        List<FootPO> list = this.footMapper.listFoot(listFootDTO);
        if(null!=list && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<ScreeningReportPO> listPrescriptionScreeningReport(String prescriptId) {
        List<ScreeningReportPO> reportList = new ArrayList<>();
        List<FootReportPO> list = this.footMapper.listFootRelateReport(prescriptId);
        if(list == null || list.size() == 0){
            return reportList;
        }
        list.forEach(x ->{
            ScreeningReportPO screeningReportPO = this.screeningService.getScreeningReportById(x.getReportId());
            reportList.add(screeningReportPO);
        });
        return reportList;
    }

    @Override
    public void addPrescriptionFootResultPO(FootResultPO footResultPO) {
        FootResultPO getResult = this.footResultMapper.getFootResult(footResultPO.getDoctorId(), footResultPO.getMemberId() ,footResultPO.getAssessCode());
        if(getResult == null){
            this.footResultMapper.addFootResult(footResultPO);
        }else{
            footResultPO.setSid(getResult.getSid());
            this.footResultMapper.updateFootResult(footResultPO);
        }

    }

    @Override
    public ReportRelateStatusVO checkScreeningReportRelate(String idCard ,String doctorId) {
        MemberPO memberPO = this.memberService.getMemberById(idCard);
        if(memberPO == null){
            throw new BusinessException("患者不存在或已被删除~");
        }
        ReportRelateStatusVO reportRelateStatusVO = new ReportRelateStatusVO();
        //abi报告判断
        ScreeningReportPO abiReport = this.screeningService.getLastScreeningReport(memberPO.getMemberId() , ScreeningConstant.SCREENING_TYPE_ABI ,doctorId);
        if(abiReport != null && checkReportDistanceThreeMonthBefore(abiReport.getReportDt())){
            reportRelateStatusVO.setAbiRelate(1);
        }else{
            reportRelateStatusVO.setAbiRelate(0);
        }
        //vpt报告判断
        ScreeningReportPO vptReport = this.screeningService.getLastScreeningReport(memberPO.getMemberId() , ScreeningConstant.SCREENING_TYPE_VPT ,doctorId);
        if(vptReport != null && checkReportDistanceThreeMonthBefore(vptReport.getReportDt())){
            reportRelateStatusVO.setVptRelate(1);
        }else{
            reportRelateStatusVO.setVptRelate(0);
        }
        //眼底报告判断
        ScreeningReportPO eyesReport = this.screeningService.getLastScreeningReport(memberPO.getMemberId() , ScreeningConstant.SCREENING_TYPE_EYES ,doctorId);
        if(eyesReport != null && checkReportDistanceThreeMonthBefore(eyesReport.getReportDt())){
            reportRelateStatusVO.setEyesRelate(1);
        }else{
            reportRelateStatusVO.setEyesRelate(0);
        }
        //肌电图报告判断
        ScreeningReportPO emgReport = this.screeningService.getLastScreeningReport(memberPO.getMemberId() , ScreeningConstant.SCREENING_TYPE_EMG ,doctorId);
        if(emgReport != null && checkReportDistanceThreeMonthBefore(emgReport.getReportDt())){
            reportRelateStatusVO.setEmgRelate(1);
        }else{
            reportRelateStatusVO.setEmgRelate(0);
        }
        return reportRelateStatusVO;
    }

    @Override
    public Map<String, Object> outScreeningFootSuggest(FootVO footVO, String memberId) {
        MemberPO memberPO = this.memberService.getMemberById(memberId);
        if(memberPO == null){
            throw new BusinessException("该患者不存在系统中（或已被删除）");
        }
        RangeBO memberRange = memberService.getMemberRange(memberPO.getMemberId());
        Map<String,Object> result = FootHelper.outFootSuggest(memberPO , memberRange ,footVO);
        result.put("nephropathyAssess" , NephropathyAssessTool.assessNephropathyResult(parseFloat(footVO.getEgfr()) ,parseFloat(footVO.getUrinary_albumin_creatinine_ratio())));
        result.put("eyeAssess" , EyesAssessTool.assessEyesResult(footVO.getLeftEyesLevel() ,footVO.getRightEyesLevel()));
        return result;
    }

    @Override
    public void deleteMemberFootResult(String doctorId, String memberId) {
        this.footResultMapper.deleteMemberFootResult(doctorId ,memberId);
    }

    @Override
    public void addFootReportRelate(String listJson) {
        List<FootReportPO> reportList = JSON.parseArray(listJson ,FootReportPO.class);
        reportList.forEach(x ->{
            FootReportPO report = this.footMapper.getFootReportById(x.getSid());
            if(report == null){
                this.footMapper.addFootReportRelate(x);
            }else{
                this.footMapper.updateFootReportRelate(x);
            }
        });
    }

    @Override
    public String addScreeningFoot(FootPO footPO) {
        String id = DaoHelper.getSeq();
        footPO.setHasRelation(relateReportHandler(footPO ,id));
        footPO.setFollowId(id);
        this.footMapper.addFoot(footPO);
        return id;
    }

    @Override
    public void modifyScreeningFoot(FootPO footPO) {
        FootPO prescriptionFootPO = getFoot(footPO.getFollowId());
        if(prescriptionFootPO == null){
            throw new BusinessException("该记录不存在");
        }
/*        int hasRelation = updateReportRelateHandler(footPO.getRelateReportIdStr() ,footPO.getFollowId());
        footPO.setHasRelation(hasRelation);*/
        if("3".equals(footPO.getSaveType())){
            assessResultStatHandler(footPO.getAssessResult() ,prescriptionFootPO ,footPO.getAssistCheck());
            addFootPrescriptionWechatMessage(prescriptionFootPO,"sys_comvee");
            syncMemberInfo(footPO ,prescriptionFootPO.getMemberId());
//            qrCodeHandler(footPO);
        }
        modifyFoot(footPO);
    }

    /**
     * 同步患者信息
     * @param footPO
     * @param memberId
     */
    private void syncMemberInfo(FootPO footPO ,String memberId){
        if(StringUtils.isBlank(footPO.getAssistCheck())){
            return;
        }
        JSONObject assistCheck = JSON.parseObject(footPO.getAssistCheck());
        if(assistCheck == null){
            return;
        }
        UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
        updateMemberDTO.setMemberId(memberId);
        updateMemberDTO.setHeight(assistCheck.getString("height"));
        updateMemberDTO.setWeight(assistCheck.getString("weight"));
        this.memberService.updateMember(updateMemberDTO);
    }

    /**
     * 报告二维码处理
     * @param footPO
     */
    private void qrCodeHandler(FootPO footPO){
        FootPrescriptionQrCodeBO footPrescriptionQrCodeBO = this.syncFootService.createFootPrescriptionQrCode(footPO.getFollowId());
        footPO.setQrCodeData(footPrescriptionQrCodeBO.getQrCodeData());
        footPO.setQrCodeInvalidDt(footPrescriptionQrCodeBO.getQrCodeInvalidDt());
    }

    @Override
    public void addFootPrescriptionWechatMessage(FootPO footPO,String sysOrigin) {
        if(StringUtils.isBlank(footPO.getMemberId())
                || StringUtils.isBlank(footPO.getFollowId())){
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("footType", footPO.getFootType());
        jsonObject.put("sysOrigin", sysOrigin);
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(footPO.getMemberId());
        addWechatMessageDTO.setForeignId(footPO.getFollowId());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_SCREENING_FOOT_PRESCRIPTION);
//        addWechatMessageDTO.setDataJson("{}");
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    @Override
    public void deleteFootReportRelate(String prescriptId) {
        this.footMapper.deleteFootReportRelate(prescriptId);
    }

    @Override
    public FootPO listFootByMemberOfInHos(String memberId) {
        return this.footMapper.listFootByMemberOfInHos(memberId);
    }

    /**
     * 获取报告类型
     * @param reportId
     * @return
     */
    private Integer getReportType(String reportId){
        ScreeningReportPO screeningReportPO = this.screeningService.getScreeningReportById(reportId);
        if(screeningReportPO == null){
            throw new BusinessException("关联的报告不存在，请确认~");
        }
        return screeningReportPO.getScreeningType();
    }

    /**
     * 关联报告处理
     * @return
     */
    private int relateReportHandler(FootPO addFootPrescriptionParam ,String id){
        if(addFootPrescriptionParam.getAbiRelate() == FootPrescriptionConstant.HAS_RELATE_REPORT_NO
                && addFootPrescriptionParam.getVptRelate() == FootPrescriptionConstant.HAS_RELATE_REPORT_NO
                && addFootPrescriptionParam.getEyesRelate() == FootPrescriptionConstant.HAS_RELATE_REPORT_NO){
            return FootPrescriptionConstant.HAS_RELATE_REPORT_NO;
        }
        List<ScreeningReportPO> reportList = new ArrayList<>();
        if(addFootPrescriptionParam.getAbiRelate() == FootPrescriptionConstant.HAS_RELATE_REPORT_YES ){
            ScreeningReportPO abiReport = this.screeningService.getLastScreeningReport(addFootPrescriptionParam.getMemberId() , ScreeningConstant.SCREENING_TYPE_ABI ,addFootPrescriptionParam.getDoctorId());
            if(abiReport != null){
                reportList.add(abiReport);
            }
        }
        if(addFootPrescriptionParam.getVptRelate() == FootPrescriptionConstant.HAS_RELATE_REPORT_YES ){
            ScreeningReportPO vptReport = this.screeningService.getLastScreeningReport(addFootPrescriptionParam.getMemberId() , ScreeningConstant.SCREENING_TYPE_VPT ,addFootPrescriptionParam.getDoctorId());
            if(vptReport != null){
                reportList.add(vptReport);
            }
        }
        if(addFootPrescriptionParam.getEyesRelate() == FootPrescriptionConstant.HAS_RELATE_REPORT_YES ){
            ScreeningReportPO eyesReport = this.screeningService.getLastScreeningReport(addFootPrescriptionParam.getMemberId() , ScreeningConstant.SCREENING_TYPE_EYES ,addFootPrescriptionParam.getDoctorId());
            if(eyesReport != null){
                reportList.add(eyesReport);
            }
        }
        if(reportList.size() == 0){
            return FootPrescriptionConstant.HAS_RELATE_REPORT_NO;
        }
        reportList.forEach(x -> {
            FootReportPO prescriptionFootReportPO = new FootReportPO();
            prescriptionFootReportPO.setPrescriptId(id);
            prescriptionFootReportPO.setReportId(x.getReportId());
            prescriptionFootReportPO.setReportType(x.getScreeningType());
            prescriptionFootReportPO.setSid(UUID.randomUUID().toString());
            prescriptionFootReportPO.setInsertDt(DateHelper.getNowDate());
            prescriptionFootReportPO.setUpdateDt(DateHelper.getNowDate());
            prescriptionFootReportPO.setValid(1);
            this.footMapper.addFootReportRelate(prescriptionFootReportPO);
        });
        return FootPrescriptionConstant.HAS_RELATE_REPORT_YES;
    }

    /**
     * 判断报告是否在3个月内
     * @param reportDt
     * @return
     */
    private static boolean checkReportDistanceThreeMonthBefore(String reportDt){
        LocalDateTime threeMonth = LocalDateTime.now().minusMonths(3L);
        LocalDateTime localDateTime = LocalDateTime.parse(reportDt , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return threeMonth.isBefore(localDateTime);
    }

    /**
     * 转化体征值
     * @param value
     * @return
     */
    private Float parseFloat(String value){
        if(StringUtils.isBlank(value)){
            return null;
        }
        return Float.parseFloat(value);
    }
}
