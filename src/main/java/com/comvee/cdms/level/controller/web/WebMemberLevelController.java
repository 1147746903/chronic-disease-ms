package com.comvee.cdms.level.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.level.constant.MemberLevelConstant;
import com.comvee.cdms.level.dto.AccessDiabetesLevelDTO;
import com.comvee.cdms.level.dto.AddDiabetesLevelDTO;
import com.comvee.cdms.level.dto.ConfirmLevelDTO;
import com.comvee.cdms.level.dto.ListMemberLevelDTO;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberDiabetesLevelService;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.level.vo.AccessDiabetesLevelResultVO;
import com.comvee.cdms.level.vo.DiabetesLevelAssessDataVO;
import com.comvee.cdms.level.vo.MemberLevelVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wyc
 * @date 2019/11/19 20:56
 */
@RestController
@RequestMapping("/web/level")
@RequiresUser
public class WebMemberLevelController {

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private MemberDiabetesLevelService memberDiabetesLevelService;

    @Autowired
    private Validator validator;

    /**
     * 新增高血压分层分级
     * @param levelPO
     * @return
     */
    @RequestMapping("/addMemberLevel")
    public Result addMemberLevel(MemberLevelPO levelPO){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        levelPO.setHospitalId(doctor.getHospitalId());
        this.memberLevelService.addHypertensionLevel(levelPO);
        return new Result("");
    }

    /**
     * 加载患者的分标、分层分级列表（已确认）
     * @return
     */
    @RequestMapping("/listMemberLevel")
    public Result listMemberLevel(ListMemberLevelDTO levelDTO){
        List<MemberLevelPO> result = this.memberLevelService.listMemberLevel(levelDTO);
        return Result.ok(result);
    }

    /**
     * 院长工作台分标确认记录(个人最新)
     * @param levelDTO
     * @param pageRequest
     * @return
     */
    @RequestMapping("/pagerMemberLevelConfirm")
    public Result pagerMemberLevelConfirm(ListMemberLevelDTO levelDTO,PageRequest pageRequest){
        String hospitalId = levelDTO.getHospitalId();
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        levelDTO.setHospitalIdList(Arrays.asList(hospitalId.split(",")));
        levelDTO.setHospitalId(null);
        levelDTO.setMobilePhone(levelDTO.getKeyword());
        levelDTO.setConfirm(MemberLevelConstant.YES);
        PageResult<MemberLevelVO> result = this.memberLevelService.pagerMemberLevel(levelDTO, pageRequest);
        return Result.ok(result);
    }


    /**
     * 分标、分层分级数据统计
     * @return
     */
    @RequestMapping("/loadMemberLevelStatics")
    public Result loadMemberLevelStatics(String hospitalId){
        ValidateTool.checkParameterIsNull("hospitalId",hospitalId);
        List<String> hospitalIdList = Arrays.asList(hospitalId.split(","));
        Map<String, Object> map = memberLevelService.loadMemberLevelStatics(hospitalIdList);
        return Result.ok(map);
    }


    /**
     * 患者分标 、分层分级历史
     * @param memberId
     * @return
     */
    @RequestMapping("/listMemberLevelHistory")
    public Result listMemberLevelHistory(String memberId,Integer levelType,PageRequest pageRequest){
        ValidateTool.checkParameterIsNull("memberId",memberId);
        ValidateTool.checkParameterIsNull("levelType",levelType);
        DoctorSessionBO webSession = SessionTool.getWebSession();
        Map<String, Object> map = memberLevelService.listMemberLevelHistory(memberId, levelType,webSession.getHospitalId(), pageRequest);
        return Result.ok(map);
    }

    /**
     * 加载医生工作台分层分级提醒未确认列表
     * @param levelDTO
     * @param page
     * @return
     */
    @RequestMapping("/listMemberLevelRemind")
    public Result listMemberLevelRemind(ListMemberLevelDTO levelDTO, PageRequest page){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        levelDTO.setDoctorId(doctor.getDoctorId());
        levelDTO.setHospitalId(doctor.getHospitalId());
        levelDTO.setConfirm(MemberLevelConstant.NO);
        PageResult<MemberLevelVO> poPageResult = this.memberLevelService.listMemberLevelRemind(levelDTO, page);
        return Result.ok(poPageResult);
    }

    /**
     * 根据id查询分层分级详情
     * @param sid
     * @return
     */
    @RequestMapping("/getMemberLevelById")
    public Result getMemberLevelById(String sid){
        ValidateTool.checkParameterIsNull("sid",sid);
        MemberLevelPO level = this.memberLevelService.getMemberLevelById(sid);
        return Result.ok(level);
    }

    /**
     * 确认分层分级
     * @param confirmLevelDTO
     * @return
     */
    @RequestMapping("/confirmMemberLevel")
    public Result confirmMemberLevel(@Validated ConfirmLevelDTO confirmLevelDTO){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        confirmLevelDTO.setDoctorId(doctor.getDoctorId());
        confirmLevelDTO.setConfirmDt(DateHelper.getTime());
        this.memberLevelService.confirmMemberLevel(confirmLevelDTO);
        return Result.ok("");
    }

    /**
     * 获取患者糖尿病分标评估需要的数据
     * @param memberId
     * @return
     */
    @RequestMapping("getMemberDiabetesLevelAssessData")
    public Result getMemberDiabetesLevelAssessData(String memberId){
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        DiabetesLevelAssessDataVO result = this.memberDiabetesLevelService.getMemberDiabetesLevelAssessData(memberId);
        return Result.ok(result);
    }

    /**
     * 评估糖尿病分级
     * @return
     */
    @RequestMapping("accessDiabetesLevel")
    public Result accessDiabetesLevel(String dataJson){
        ValidateTool.checkParameterIsNull("dataJson" ,dataJson);
        AccessDiabetesLevelDTO accessDiabetesLevelDTO = JSON.parseObject(dataJson ,AccessDiabetesLevelDTO.class);
        doValidate(accessDiabetesLevelDTO);
        AccessDiabetesLevelResultVO result = this.memberDiabetesLevelService.accessDiabetesLevel(accessDiabetesLevelDTO);
        return Result.ok(result);
    }

    /**
     * 新增糖尿病分标结果
     * @param dataJson
     * @return
     */
    @RequestMapping("addDiabetesLevel")
    public Result addDiabetesLevel(String dataJson){
        ValidateTool.checkParameterIsNull("dataJson" ,dataJson);
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        AddDiabetesLevelDTO addDiabetesLevelDTO = JSONObject.parseObject(dataJson ,AddDiabetesLevelDTO.class);
        doValidate(addDiabetesLevelDTO);
        addDiabetesLevelDTO.setDoctorId(doctorSession.getDoctorId());
        addDiabetesLevelDTO.setHospitalId(doctorSession.getHospitalId());
        addDiabetesLevelDTO.setConfirm(MemberLevelConstant.YES);
        addDiabetesLevelDTO.setChangeReason(addDiabetesLevelDTO.getChangeReason());
        String result = this.memberDiabetesLevelService.addDiabetesLevel(addDiabetesLevelDTO);
        return Result.ok(result);
    }

    private <T> void doValidate(T t){
        Set<ConstraintViolation<T>> validatorSet = validator.validate(t);
        if(!validatorSet.isEmpty()){
            ConstraintViolation constraintViolation = validatorSet.iterator().next();
            throw new BusinessException(constraintViolation.getMessage());
        }
    }
}
