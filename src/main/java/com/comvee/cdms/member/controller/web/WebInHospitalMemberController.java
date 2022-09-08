package com.comvee.cdms.member.controller.web;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.member.dto.AddInHospitalMemberDTO;
import com.comvee.cdms.member.dto.ChangeHospitalBedDTO;
import com.comvee.cdms.member.dto.ListMemberDTO;
import com.comvee.cdms.member.dto.ReInHospitalMemberDTO;
import com.comvee.cdms.member.service.InHospitalMemberService;
import com.comvee.cdms.member.vo.ListInHospitalLogVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/inHospital/member")
public class WebInHospitalMemberController {

    @Autowired
    private InHospitalMemberService inHospitalMemberService;

    /**
     * 加载病床列表(带患者信息)
     * @param listMemberDTO
     * @param pager
     * @return
     */
    @RequestMapping("/listHospitalBedWithMember")
    public Result listHospitalBedWithMember(@Validated ListMemberDTO listMemberDTO, PageRequest pager){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        listMemberDTO.setDoctorId(webSession.getDoctorId());
        listMemberDTO.setHospitalId(webSession.getHospitalId());
        PageResult result = this.inHospitalMemberService.listHospitalBedWithMember(listMemberDTO ,pager);
        return Result.ok(result);
    }

    /**
     * 添加住院患者
     * @param addInHospitalMemberDTO
     * @return
     */
    @RequestMapping("/addInHospitalMember")
    public Result addInHospitalMember(@Validated AddInHospitalMemberDTO addInHospitalMemberDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        DoctorPO doctorPO = new DoctorPO();
        BeanUtils.copyProperties(doctorPO ,doctorSessionBO);
        this.inHospitalMemberService.addInHospitalMember(addInHospitalMemberDTO ,doctorPO);
        return Result.ok();
    }

    /**
     * 更换床位
     * @param changeHospitalBedDTO
     * @return
     */
    @RequestMapping("/changeHospitalBed")
    public Result changeHospitalBed(@Validated ChangeHospitalBedDTO changeHospitalBedDTO){
        this.inHospitalMemberService.changeHospitalBed(changeHospitalBedDTO);
        return Result.ok();
    }

    /**
     * 出院办理
     * @param sid
     * @param outHospitalDate
     * @return
     */
    @RequestMapping("/outHospital")
    public Result outHospital(String sid ,String outHospitalDate){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        ValidateTool.checkParameterIsNull("outHospitalDate" ,outHospitalDate);
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        this.inHospitalMemberService.outHospital(sid ,outHospitalDate,doctorSessionBO.getDoctorId());
        return Result.ok();
    }

    /**
     * 根据科室加载病床列表(仅床位)
     * @param departmentId
     * @param checkinStatus
     * @return
     */
    @RequestMapping("/listHospitalBed")
    public Result listHospitalBed(String departmentId ,Integer checkinStatus){
        ValidateTool.checkParameterIsNull("departmentId" ,departmentId);
        List result = this.inHospitalMemberService.listHospitalBed(departmentId ,checkinStatus);
        return Result.ok(result);
    }


    /**
     * 出院患者列表
     * @param listMemberDTO
     * @param pager
     * @return
     */
    @RequestMapping("/listHospitalOutMember")
    public Result listHospitalOutMember(@Validated ListMemberDTO listMemberDTO, PageRequest pager){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        listMemberDTO.setDoctorId(webSession.getDoctorId());
        listMemberDTO.setHospitalId(webSession.getHospitalId());
        PageResult result = this.inHospitalMemberService.listHospitalOutMember(listMemberDTO ,pager);
        return Result.ok(result);
    }


    /**
     * 出院患者重新入院
     * @param reInHospitalMemberDTO
     * @return
     */
    @RequestMapping("/reInHospital")
    public Result reInHospital(@Validated ReInHospitalMemberDTO reInHospitalMemberDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        DoctorPO doctorPO = new DoctorPO();
        BeanUtils.copyProperties(doctorPO ,doctorSessionBO);
        this.inHospitalMemberService.reInHospital(reInHospitalMemberDTO,doctorPO);
        return Result.ok();
    }

    /**
     * 获取患者住院记录
     * @param memberId
     * @return
     */
    @RequestMapping("/inHospitalLogList")
    public Result getMemberHospitalLog(String memberId){
        ValidateTool.checkParamIsNull(memberId,"memberId");
        List<ListInHospitalLogVO> logs = this.inHospitalMemberService.getMemberHospitalLog(memberId);
        return Result.ok(logs);
    }
}
