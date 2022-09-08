package com.comvee.cdms.level.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.checkresult.dto.GetNewestCheckoutDetailDTO;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.service.CheckRemindService;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.complication.constant.ScreeningConstant;
import com.comvee.cdms.complication.model.dto.GetLastScreeningReportDTO;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.complication.tool.AbiTool;
import com.comvee.cdms.foot.helper.NephropathyAssessTool;
import com.comvee.cdms.level.bo.DiabetesLevelDataBO;
import com.comvee.cdms.level.constant.MemberLevelConstant;
import com.comvee.cdms.level.dto.AccessDiabetesLevelDTO;
import com.comvee.cdms.level.dto.AddDiabetesLevelDTO;
import com.comvee.cdms.level.dto.LastLevelDTO;
import com.comvee.cdms.level.helper.DiabetesLevelAnalyseHelper;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberDiabetesLevelService;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.level.vo.AccessDiabetesLevelResultVO;
import com.comvee.cdms.level.vo.DiabetesLevelAssessDataBloodSugarItemVO;
import com.comvee.cdms.level.vo.DiabetesLevelAssessDataVO;
import com.comvee.cdms.member.dto.MemberArchivesDTO;
import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.po.Hba1cPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.sign.service.Hba1cService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service("memberDiabetesLevelService")
public class MemberDiabetesLevelServiceImpl implements MemberDiabetesLevelService {

    @Autowired
    private BloodSugarService bloodSugarService;

    @Autowired
    private CheckoutDetailServiceI checkoutDetailService;

    @Autowired
    private Hba1cService hba1cService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CheckRemindService memberCheckRemindService;

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private ScreeningService screeningService;

    @Override
    public DiabetesLevelAssessDataVO getMemberDiabetesLevelAssessData(String memberId) {
        DiabetesLevelAssessDataVO result = new DiabetesLevelAssessDataVO();
        bloodSugarHandler(memberId ,result);
        checkoutHandler(memberId ,result);
        hba1cHandler(memberId ,result);
        diseaseHandler(memberId ,result);
        screeningHandler(memberId ,result);
        return result;
    }

    @Override
    public AccessDiabetesLevelResultVO accessDiabetesLevel(AccessDiabetesLevelDTO dto) {
        MemberPO member = this.memberService.getMemberById(dto.getMemberId());
        if(member == null){
            throw new BusinessException("患者不存在，请确认");
        }
        AccessDiabetesLevelResultVO result = null;
        DiabetesLevelAnalyseHelper.LevelAnalyseParam analyseParam = new DiabetesLevelAnalyseHelper.LevelAnalyseParam();
        BeanUtils.copyProperties(analyseParam ,dto);
        analyseParam.setAge(DateHelper.getAge(member.getBirthday()));
        Integer level = DiabetesLevelAnalyseHelper.analyseLevel(analyseParam);
        String problemText = DiabetesLevelAnalyseHelper.analyseProblem(analyseParam);
        if(level != null){
            result = new AccessDiabetesLevelResultVO();
            result.setLevel(level);
            result.setProblemText(problemText);
            LastLevelDTO lastLevelDTO = new LastLevelDTO();
            lastLevelDTO.setMemberId(dto.getMemberId());
            lastLevelDTO.setConfirm(MemberLevelConstant.YES);
            lastLevelDTO.setLevelType(MemberLevelConstant.TNB_TYPE);
            MemberLevelPO lastRecord = this.memberLevelService.getLastLevel(lastLevelDTO);
            if(lastRecord != null){
                DiabetesLevelAnalyseHelper.LevelAnalyseParam currentParam = new DiabetesLevelAnalyseHelper.LevelAnalyseParam();
                BeanUtils.copyProperties(currentParam ,dto);
                DiabetesLevelAnalyseHelper.LevelAnalyseParam lastParam = new DiabetesLevelAnalyseHelper.LevelAnalyseParam();
                DiabetesLevelDataBO levelData = JSON.parseObject(lastRecord.getDataJson() ,DiabetesLevelDataBO.class);
                BeanUtils.copyProperties(lastParam ,levelData);
                String text = DiabetesLevelAnalyseHelper.getAnalyseText(level ,currentParam ,lastRecord.getMemberLevel() ,lastParam);
                if(!StringUtils.isBlank(text)){
                    result.setAnalyseText(text);
                }
            }
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addDiabetesLevel(AddDiabetesLevelDTO add) {
        sameDayLevelHandler(add.getMemberId());

        DiabetesLevelDataBO data = new DiabetesLevelDataBO();
        BeanUtils.copyProperties(data ,add);
        MemberLevelPO addLevel = new MemberLevelPO();
        addLevel.setMemberId(add.getMemberId());
        addLevel.setChangeDate(DateHelper.getToday());
        addLevel.setChangeDt(DateHelper.getNowDate());
        addLevel.setMemberLevel(add.getLevel());
        addLevel.setMemberLayer(0);
        addLevel.setOrigin(MemberLevelConstant.YES == add.getAdjustment() ? MemberLevelConstant.ORIGIN_SYS : MemberLevelConstant.ORIGIN_HAND);
        addLevel.setDoctorId(add.getDoctorId());
        addLevel.setHospitalId(add.getHospitalId());
        addLevel.setConfirm(add.getConfirm());
        addLevel.setLevelType(MemberLevelConstant.TNB_TYPE);
        addLevel.setDataJson(JSON.toJSONString(data));
        addLevel.setContrastAnalyze(add.getAnalyzeText());
        addLevel.setAdjustment(add.getAdjustment());
        addLevel.setChangeReason(add.getChangeReason());
        syncMemberArchives(add.getMemberId() ,data);
        return this.memberLevelService.addMemberLevel(addLevel);
    }

    private void bloodSugarHandler(String memberId ,DiabetesLevelAssessDataVO data){
        LocalDateTime weekAgoDay = LocalDateTime.now().minusDays(7L);
        ListBloodSugarDTO bloodSugarDTO = new ListBloodSugarDTO();
        bloodSugarDTO.setMemberId(memberId);
        bloodSugarDTO.setStartDt(weekAgoDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        bloodSugarDTO.setParamLevel(String.valueOf(SignConstant.SIGN_LEVEL_LOW));
        List<BloodSugarPO> bloodSugarList = this.bloodSugarService.listBloodSugar(bloodSugarDTO);
        int lowTimes = Optional.ofNullable(bloodSugarList).map(list -> list.size()).orElse(0);
        data.setLowBloodSugarTimes(lowTimes);
        if(lowTimes > 0){
            List list = BeanUtils.copyListProperties(DiabetesLevelAssessDataBloodSugarItemVO.class ,bloodSugarList);
            data.setBloodSugarList(list);
        }

    }

    private void checkoutHandler(String memberId ,DiabetesLevelAssessDataVO data){
        GetNewestCheckoutDetailDTO getNewestCheckoutDetailDTO = new GetNewestCheckoutDetailDTO();
        getNewestCheckoutDetailDTO.setMemberId(memberId);
        //gfr
        getNewestCheckoutDetailDTO.setCheckoutCode("add_check_egfr");
        CheckoutDetailPO grfCheckoutDetail = this.checkoutDetailService.getNewestCheckoutDetail(getNewestCheckoutDetailDTO);
        if(grfCheckoutDetail != null){
            Double gfr = Double.parseDouble(grfCheckoutDetail.getCheckoutResult());
            data.setGfr(gfr);
            data.setGfrStages(NephropathyAssessTool.eGFRStagesHandler(gfr.floatValue()));
            data.setGfrCheckDt(grfCheckoutDetail.getCheckoutDatetime());
        }
        //acr
        getNewestCheckoutDetailDTO.setCheckoutCode("add_check_neph_acr");
        CheckoutDetailPO acrCheckoutDetail = this.checkoutDetailService.getNewestCheckoutDetail(getNewestCheckoutDetailDTO);
        if(acrCheckoutDetail != null){
            Double acr = Double.parseDouble(acrCheckoutDetail.getCheckoutResult());
            data.setAcr(acr);
            data.setAcrStages(NephropathyAssessTool.uACRStagesHandler(acr.floatValue()));
            data.setAcrCheckDt(acrCheckoutDetail.getCheckoutDatetime());
        }
    }

    private void hba1cHandler(String memberId ,DiabetesLevelAssessDataVO data){
        Hba1cPO hba1c = this.hba1cService.getLatestHba1c(memberId);
        if(hba1c == null){
            return;
        }
        String threeMonthAgoDate = LocalDateTime.now().minusMonths(3L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(threeMonthAgoDate.compareTo(hba1c.getRecordDt()) > 0){
            return;
        }
        data.setHba1cCheckDt(hba1c.getRecordDt());
        data.setHba1c(new BigDecimal(hba1c.getRecordValue()).setScale(1 ,BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    private void diseaseHandler(String memberId ,DiabetesLevelAssessDataVO data){
        MemberArchivesPO memberArchives = this.memberService.getMemberArchives(memberId ,null);
        if(memberArchives == null || StringUtils.isBlank(memberArchives.getArchivesJson())){
            return;
        }
        JSONObject archivesJson = JSONObject.parseObject(memberArchives.getArchivesJson());
        JSONObject complication = archivesJson.getJSONObject("complication");
        if(complication == null){
            return;
        }
        data.setDiabeticNephropathy(complication.getString("nephropathy"));
        data.setDiabeticNephropathyCheckDt(complication.getString("neph_date"));
        data.setDiabeticNephropathyType(complication.getString("neph_type_cms"));
        data.setDiabeticNephropathyTypeOther(complication.getString("neph_type_remark"));


        data.setDiabeticFoot(complication.getString("diabetic_foot"));
        data.setDiabeticFootCheckDt(complication.getString("df_date"));
        data.setDiabeticFootSymptom(complication.getString("df_level"));
        data.setDiabeticFootSymptomOther(complication.getString("df_level_remark"));
    }

    private void screeningHandler(String memberId ,DiabetesLevelAssessDataVO data){
        GetLastScreeningReportDTO getLastScreeningReportDTO = new GetLastScreeningReportDTO();
        getLastScreeningReportDTO.setMemberId(memberId);
        getLastScreeningReportDTO.setDealStatus(ScreeningConstant.PRE_DEAL_STATUS_REPORT_CONFIRM);
        //abi
        getLastScreeningReportDTO.setScreeningType(ScreeningConstant.SCREENING_TYPE_ABI);
        ScreeningReportPO abi = this.screeningService.getLastScreeningReport(getLastScreeningReportDTO);
        if(abi != null){
            JSONObject abiJson = JSONObject.parseObject(abi.getReportJson());
            data.setAbiCheckDt(abi.getReportDt());
            data.setLeftAbi(abiJson.getDouble("leftAbi"));
            data.setRightAbi(abiJson.getDouble("rightAbi"));
            data.setAbiLevel(AbiTool.getAbiLevel(abiJson.getDouble("leftAbi") ,abiJson.getDouble("rightAbi")));
        }
        //vpt
        getLastScreeningReportDTO.setScreeningType(ScreeningConstant.SCREENING_TYPE_VPT);
        ScreeningReportPO vpt = this.screeningService.getLastScreeningReport(getLastScreeningReportDTO);
        if(vpt != null){
            JSONObject vptJson = JSONObject.parseObject(vpt.getReportJson());
            data.setVptCheckDt(vpt.getReportDt());
            data.setLeftVpt(vptJson.getDouble("leftVpt"));
            data.setRightVpt(vptJson.getDouble("rightVpt"));
        }
        //眼底
        getLastScreeningReportDTO.setScreeningType(ScreeningConstant.SCREENING_TYPE_EYES);
        getLastScreeningReportDTO.setEditStatus(ScreeningConstant.REPORT_EDIT_YES);
        ScreeningReportPO eyes = this.screeningService.getLastScreeningReport(getLastScreeningReportDTO);
        if(eyes != null){
            JSONObject eyesJson = JSONObject.parseObject(eyes.getReportJson());
            data.setEyesCheckDt(eyes.getReportDt());
            data.setLeftEyePathology(eyesJson.getInteger("leftEyesResult"));
            data.setRightEyePathology(eyesJson.getInteger("rightEyesResult"));
        }

    }

    /**
     * 同一天的分标结果处理（删掉已经生成的）
     * @param memberId
     */
    private void sameDayLevelHandler(String memberId){
        LastLevelDTO lastLevelDTO = new LastLevelDTO();
        lastLevelDTO.setMemberId(memberId);
        lastLevelDTO.setLevelType(MemberLevelConstant.TNB_TYPE);
        MemberLevelPO lastLevelResult = this.memberLevelService.getLastLevel(lastLevelDTO);
        if(lastLevelResult != null){
            if(lastLevelResult.getChangeDate().equals(DateHelper.getToday())){
                MemberLevelPO update = new MemberLevelPO();
                update.setIsValid(0);
                update.setSid(lastLevelResult.getSid());
                this.memberLevelService.updateMemberLevel(update);
            }
        }
    }

    /**
     * 同步到档案
     * @param memberId
     * @param data
     */
    private void syncMemberArchives(String memberId ,DiabetesLevelDataBO data){
        JSONObject complication = new JSONObject();
        if(!StringUtils.isBlank(data.getDiabeticNephropathy())){
            complication.put("nephropathy" ,data.getDiabeticNephropathy());
            complication.put("neph_date" ,data.getDiabeticNephropathyCheckDt());
            complication.put("neph_type_cms" ,data.getDiabeticNephropathyType());
            complication.put("neph_type_remark" ,data.getDiabeticNephropathyTypeOther());
        }
        if(!StringUtils.isBlank(data.getDiabeticFoot())){
            complication.put("diabetic_foot" ,data.getDiabeticFoot());
            complication.put("df_date" ,data.getDiabeticFootCheckDt());
            complication.put("df_level" ,data.getDiabeticFootSymptom());
            complication.put("df_level_remark" ,data.getDiabeticFootSymptomOther());
        }
        if(complication.isEmpty()){
            return;
        }
        JSONObject archivesJson = new JSONObject();
        archivesJson.put("complication" ,complication);
        MemberArchivesDTO update = new MemberArchivesDTO();
        update.setMemberId(memberId);
        update.setArchivesJson(archivesJson.toJSONString());
        this.memberService.updateMemberArchive(update);
    }
}
