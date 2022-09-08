package com.comvee.cdms.member.controller.third;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.dto.AddMemberDTO;
import com.comvee.cdms.member.service.impl.MemberManageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/third/memberManage")
public class ThirdMemberManageController {

    @Autowired
    private MemberManageServiceImpl manageService;

    @Autowired
    private DoctorServiceI doctorService;

    /**
     * 用于导入卫生院数据
     * @param addMemberDTO
     * @param doctorId
     * @return
     */
    @RequestMapping("/createMemberHospitalRelation")
    public Result createMemberHospitalRelation(AddMemberDTO addMemberDTO ,String doctorId) {
        DoctorPO doctor = this.doctorService.getDoctorById(doctorId);
        DoctorSessionBO doctorSessionBO = new DoctorSessionBO();
        BeanUtils.copyProperties(doctorSessionBO ,doctor);
        String memberId = manageService.createMemberHospitalRelation(doctorSessionBO, addMemberDTO);
        return Result.ok(memberId);
    }
}
