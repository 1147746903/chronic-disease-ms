package com.comvee.cdms.foot.contorller;

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.foot.dto.ListFootDTO;
import com.comvee.cdms.foot.helper.EyesAssessTool;
import com.comvee.cdms.foot.helper.NephropathyAssessTool;
import com.comvee.cdms.foot.po.FootPO;
import com.comvee.cdms.foot.service.FootService;
import com.comvee.cdms.foot.vo.EyesAssessResultVO;
import com.comvee.cdms.foot.vo.FootVO;
import com.comvee.cdms.foot.vo.NephropathyAssessResultVO;
import com.comvee.cdms.foot.vo.ReportRelateStatusVO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: wangxy
 * @date: 2018/12/28
 */
@RestController
@RequestMapping("/web/foot")
@RequiresUser
public class WebFootController {

    @Autowired
    private FootService footService;

    @Autowired
    private MemberService memberService;

    /**
     * 分页加载足部管理处方列表
     * @param listFootDTO
     * @return
     */
    @RequestMapping("listFootPage")
    public Result listFootPage(@Validated ListFootDTO listFootDTO, PageRequest page){
        //ValidateTool.checkParamIsNull(listFootDTO.getDoctorId(), "医生id");
//        listFootDTO.setDoctorId(null);
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        PageResult<FootPO> list = this.footService.listFootPage(listFootDTO,page);
        return new Result(list);
    }

    /**
     * 根据主键id获取足部管理处方
     * @param followId
     * @return
     */
    @RequestMapping("getFoot")
    public Result getFoot(String followId){
        ValidateTool.checkParamIsNull(followId, "主键id");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        FootPO foot = this.footService.getFoot(followId);
        return new Result(foot);
    }


    /**
     * 新增足部管理处方记录
     * @param footPO
     * @return
     */
    @RequestMapping("addFoot")
    public Result addFoot(@Validated FootPO footPO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if(StringUtils.isBlank(footPO.getDoctorId())){
            footPO.setTeamId(doctorSessionBO.getDoctorId());
        }else{
            footPO.setTeamId(footPO.getDoctorId());
        }
        footPO.setDoctorId(doctorSessionBO.getDoctorId());
        String id = this.footService.addFoot(footPO);
        return new Result(id);
    }

    /**
     * 更新足部管理处方记录
     * @param footPO
     * @return
     */
    @RequestMapping("modifyFoot")
    public Result modifyFoot(@Validated FootPO footPO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(footPO.getFollowId(), "主键id");
        this.footService.modifyFoot(footPO);
        return new Result(null);
    }

    /**
     * 足部管理处方评估逻辑（危险等级，治疗建议）
     * @param footVO
     * @return
     */
    @RequestMapping("outFootSuggest")
    public Result outFootSuggest(String memberId, FootVO footVO){

        ValidateTool.checkParamIsNull(memberId, "患者id");
        Map<String, Object> re = null;
        try {
            re = this.footService.outFootSuggest(memberId, footVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(re);
    }

    /**
     * 获取患者最新的足部管理处方
     * @param footVO
     * @return
     */
    @RequestMapping("getFootNew")
    public Result getFootNew(ListFootDTO listFootDTO){
        ValidateTool.checkParamIsNull(listFootDTO.getMemberId(), "患者id");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if(StringUtils.isBlank(listFootDTO.getDoctorId())){
            listFootDTO.setDoctorId(doctorSessionBO.getDoctorId());
        }
        FootPO footNew = this.footService.getFootNew(listFootDTO);
        return new Result(footNew);
    }

    /**
     * 删除足部处方
     * @param prescriptId
     * @return
     */
    @RequestMapping("/deleteFootPrescription")
    public Result deleteFootPrescription(String prescriptId){
        ValidateTool.checkParamIsNull(prescriptId , "prescriptId");
        FootPO footPO = new FootPO();
        footPO.setFollowId(prescriptId);
        footPO.setIsValid("0");
        this.footService.modifyFoot(footPO);
        return Result.ok();
    }

    /**
     * 加载关联的报告
     * @param prescriptId
     * @return
     */
    @RequestMapping("/listRelateReport")
    public Result listRelateReport(String prescriptId){
        ValidateTool.checkParamIsNull(prescriptId , "prescriptId");
        List<ScreeningReportPO> list = this.footService.listPrescriptionScreeningReport(prescriptId);
        return Result.ok(list);
    }

    /**
     * 判断筛查报告可关联情况
     * @param idCard
     * @return
     */
    @RequestMapping("/checkScreeningReportRelate")
    public Result checkScreeningReportRelate(String idCard){
        ValidateTool.checkParamIsNull(idCard , "idCard");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        ReportRelateStatusVO reportRelateStatusVO = this.footService.checkScreeningReportRelate(idCard ,doctorSessionBO.getDoctorId());
        return Result.ok(reportRelateStatusVO);
    }


    /**
     * 足部处方逻辑输出
     * @param footVO
     * @param idCard
     * @return
     */
    @RequestMapping("/outScreeningFootSuggest")
    public Result outScreeningFootSuggest(FootVO footVO ,String memberId){
        ValidateTool.checkParamIsNull(memberId , "memberId");
        Map<String,Object> result = this.footService.outScreeningFootSuggest(footVO , memberId);
        return Result.ok(result);
    }

    /**
     * 肾病护理评估
     * @param egfrStages
     * @param uacrStages
     * @return
     */
    @RequestMapping("assessNephropathyResult")
    public Result assessNephropathyResult(Integer egfrStages , Integer uacrStages){
        NephropathyAssessResultVO nephropathyAssessResultVO = new NephropathyAssessResultVO();
        nephropathyAssessResultVO.setEgfrStages(egfrStages);
        nephropathyAssessResultVO.setUacrStages(uacrStages);
        nephropathyAssessResultVO.setReferralAdvice(NephropathyAssessTool.referralAdviceHandler(egfrStages ,uacrStages));
        nephropathyAssessResultVO.setRenalInjuryDegree(NephropathyAssessTool.renalInjuryDegreeHandler(egfrStages ,uacrStages));
        nephropathyAssessResultVO.setAdviceList(NephropathyAssessTool.adviceHandler(egfrStages ,uacrStages));
        return Result.ok(nephropathyAssessResultVO);
    }

    /**
     * 眼底护理评估
     * @param eyeLevel
     * @return
     */
    @RequestMapping("assessEyesResult")
    public Result assessEyesResult(Integer eyeLevel){
        ValidateTool.checkParamIsNull(eyeLevel ,"eyeLevel");
        EyesAssessResultVO resultVO = EyesAssessTool.assessEyesResult(eyeLevel ,eyeLevel);
        return Result.ok(resultVO);
    }

    /**
     * 新增足部管理处方记录
     * @param footPO
     * @return
     */
    @RequestMapping("addScreeningFoot")
    public Result addScreeningFoot(FootPO footPO){
        ValidateTool.checkParameterIsNull("teamId" , footPO.getTeamId());
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        footPO.setDoctorId(doctorSessionBO.getDoctorId());
        String id = this.footService.addScreeningFoot(footPO);
        return new Result(id);
    }

    /**
     * 更新足部管理处方记录
     * @param footPO
     * @return
     */
    @RequestMapping("modifyScreeningFoot")
    public Result modifyScreeningFoot(@Validated FootPO footPO){
        ValidateTool.checkParamIsNull(footPO.getFollowId(), "主键id");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        footPO.setDoctorId(doctorSessionBO.getDoctorId());
        this.footService.modifyScreeningFoot(footPO);
        return Result.ok();
    }
}
