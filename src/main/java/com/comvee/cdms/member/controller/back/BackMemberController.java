package com.comvee.cdms.member.controller.back;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.dto.ListMemberByDoctorDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyc
 * @date 2019/4/15 18:02
 */
@RestController
@RequestMapping("/back/member")
public class BackMemberController {

    @Autowired
    private MemberService memberService;
    /**
     * 根据医生id获取患者列表
     * @param doctorId
     * @return
     */
    @RequestMapping("listMemberByDoctorId")
    public Result listMemberByDoctorId(ListMemberByDoctorDTO listMemberByDoctorDTO, PageRequest pr){
        PageResult<MemberPO> pageResult = this.memberService.listMemberByDoctorId(listMemberByDoctorDTO, pr);
        return new Result(pageResult);
    }

    /**
     * 解除医患关系
     * @param memberId
     * @param doctorId
     * @return
     */
    @RequestMapping("cancelRelation")
    public Result cancelRelation(String memberId,String doctorId){
        ValidateTool.checkParameterIsNull(memberId,"memberId");
        ValidateTool.checkParameterIsNull(doctorId,"doctorId");
        this.memberService.cancelRelation(memberId,doctorId);
        return new Result("");
    }


}
