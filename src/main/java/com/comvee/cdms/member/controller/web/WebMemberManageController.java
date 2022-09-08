package com.comvee.cdms.member.controller.web;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.service.impl.MemberManageServiceImpl;
import com.comvee.cdms.other.constant.DictConstant;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/web/memberManage")
@RequiresUser
public class WebMemberManageController {

    @Autowired
    private MemberManageServiceImpl manageService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HospitalService hospitalService;
    /**
     * 添加患者到患者管理
     *
     * @param addMemberDTO
     * @return
     */
    @RequestMapping("/createMemberHospitalRelation")
    public Result createMemberHospitalRelation(AddMemberDTO addMemberDTO) {
        ValidateTool.checkParamIsNull(addMemberDTO.getMemberName(), "memberName");
        ValidateTool.checkParamIsNull(addMemberDTO.getBirthday(), "birthday");
        ValidateTool.checkParamIsNull(addMemberDTO.getSex(), "sex");
        ValidateTool.checkParamIsNull(addMemberDTO.getIdCard(), "idCard");
        ValidateTool.checkParamIsNull(addMemberDTO.getSocialCard(), "socialCard");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        String memberId = manageService.createMemberHospitalRelation(doctorSessionBO, addMemberDTO);
        return Result.ok(memberId);
    }

    /**
     * 获取患者管理列表
     * 返回医生id用于患者详情页跳转
     * @param dto
     * @param pr
     * @return
     */
    @RequestMapping("/listMemberJoinHospital")
    public Result listMemberJoinHospital(ListMemberJoinHospitalDTO dto, PageRequest pr) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setDoctorId(doctorSessionBO.getDoctorId());
        dto.setHospitalId(doctorSessionBO.getHospitalId());
        dto.setMobilePhone(dto.getKeyword());
        if(!StringUtils.isBlank(dto.getEnd())){
            dto.setEnd(dto.getEnd() + " 23:59:59");
        }
        return Result.ok(manageService.listMemberJoinHospital(dto, pr));
    }


    /**
     * 患者管理关注患者
     *
     * @param memberList
     * @return
     */
    @RequestMapping("/attentionMember")
    public Result attentionMember(String memberList) {
        ValidateTool.checkParamIsNull(memberList, "memberList");
        String[] members = memberList.split(",");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        manageService.attentionMember(members, doctorSessionBO.getHospitalId());
        return Result.ok();
    }


    /**
     * 患者管理取消关注
     *
     * @param memberList
     * @return
     */
    @RequestMapping("/cancelAttentionMember")
    public Result cancelAttentionMember(String memberList) {
        ValidateTool.checkParamIsNull(memberList, "memberList");
        String[] members = memberList.split(",");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        manageService.cancelAttentionMember(members, doctorSessionBO.getHospitalId());
        return Result.ok();
    }

    @RequestMapping("/memberJoinStatistics")
    public Result memberJoinStatistics() {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        return Result.ok(manageService.memberJoinStatistics(doctorSessionBO.getHospitalId()));
    }


    /**
     *门诊患者就诊记录(15天内)
     * @param dto
     * @param pr
     * @return
     */
    @RequestMapping("/listMemberVisitRecord")
    public Result listMemberVisitRecord(ListMemberVisitDTO dto, PageRequest pr) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setDoctorId(doctorSessionBO.getDoctorId());
        dto.setJoinHospitalId(doctorSessionBO.getHospitalId());
        //处理就诊医院筛选
        if (!StringUtils.isBlank(dto.getHospitalId())){
            dto.setHospitalIdList(Arrays.asList( dto.getHospitalId().split(",")));
            dto.setHospitalId(null);
        }else {
            List<HospitalPO> hospitalPOS = hospitalService.listHospitalByAreaId(DictConstant.ZHENGHE_AREA_ID);
            List<String> hospitalIdList = hospitalPOS.stream().map(HospitalPO::getHospitalId).collect(Collectors.toList());
            dto.setHospitalIdList(hospitalIdList);
        }
        //处理就诊时间
        String visitStartDt = dto.getVisitStartDt();
        String visitEndDt = dto.getVisitEndDt();
        if (StringUtils.isBlank(dto.getVisitStartDt())){
            visitStartDt = DateHelper.plusDate(DateHelper.getToday(), -15);
        }
        if (StringUtils.isBlank(dto.getVisitEndDt())){
            visitEndDt = DateHelper.getToday();
        }
        dto.setVisitStartDt(visitStartDt+DateHelper.DEFUALT_TIME_START);
        dto.setVisitEndDt(visitEndDt+DateHelper.DEFUALT_TIME_END);
        //处理建档时间
        if(!StringUtils.isBlank(dto.getBegin())){
            dto.setBegin(dto.getBegin() + DateHelper.DEFUALT_TIME_START);
        }
        if(!StringUtils.isBlank(dto.getEnd())){
            dto.setEnd(dto.getEnd() + DateHelper.DEFUALT_TIME_END);
        }
        return Result.ok(manageService.listMemberVisitRecord(dto, pr));
    }

    /**
     * 住院患者加入慢病管理
     *isDiabetes是否有糖尿病 1:有 2:无
     * essentialHyp高血压:1有、2无
     */
    @RequestMapping("/memberInHospitalJoinManage")
    public Result memberInHospitalJoinManage(String memberId,AddMemberDTO addMemberDTO) {
        ValidateTool.checkParamIsNull(memberId, "memberId");
        ValidateTool.checkParamIsNull(addMemberDTO.getIsDiabetes(), "isDiabetes");
        ValidateTool.checkParamIsNull(addMemberDTO.getEssentialHyp(), "essentialHyp");
        String isDiabetes = addMemberDTO.getIsDiabetes();
        String essentialHyp = String.valueOf(addMemberDTO.getEssentialHyp());
        if (!isDiabetes.equals("1") && !essentialHyp.equals("1")){
            //两个选项必须选则至少一个
            throw new BusinessException("慢病类型不能为空");
        }
        DoctorSessionBO webSession = SessionTool.getWebSession();
        AddMemberMapperDTO addMemberMapperDTO = new AddMemberMapperDTO();
        addMemberMapperDTO.setMemberId(memberId);
        addMemberMapperDTO.setIsDiabetes(isDiabetes);
        addMemberMapperDTO.setEssentialHyp(essentialHyp);
        addMemberMapperDTO.setDiabetesType(addMemberDTO.getDiabetesType());
        addMemberMapperDTO.setHypType(addMemberDTO.getHypType());
        memberService.memberInHospitalJoinManage(webSession, addMemberMapperDTO);
        return Result.ok();
    }

    /**
     * 删除慢病患者管理
     */
    @RequestMapping("/deleteMemberJoinHospital")
    public Result memberInHospitalJoinManage(String sid) {
        ValidateTool.checkParamIsNull(sid, "sid");
        String code = "-1";
        if (manageService.deleteMemberJoinHospital(sid)){
            code = "1";
        }
        return Result.ok(code);
    }

}
