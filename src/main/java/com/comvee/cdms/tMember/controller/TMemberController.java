package com.comvee.cdms.tMember.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.dto.ListMemberByDoctorDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.tMember.entity.TMember;
import com.comvee.cdms.tMember.service.ITMemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 患者档案基本信息 前端控制器
 * </p>
 *
 * @author wqe
 * @since 2022-09-08
 */
@RestController
@RequestMapping("/t-member")
@Api("患者档案基本信息 前端控制器")
public class TMemberController {

    @Autowired
    private ITMemberService iTMemberService;

    /**
     * 查询患者列表
     */
    @RequestMapping("list")
    public Result listMemberByDoctorId(){
        Page<TMember> of = Page.of(1, 10);
        List<TMember> listMember = iTMemberService.list();
        return new Result(listMember);
    }

}

