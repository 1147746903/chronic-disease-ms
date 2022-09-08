package com.comvee.cdms.drugs.contorller.web;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.drugs.dto.DrugsDepotDTO;
import com.comvee.cdms.drugs.dto.ListDrugsDepotDTO;
import com.comvee.cdms.drugs.dto.ListDrugsMemberDTO;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.drugs.po.DrugsMemberPO;
import com.comvee.cdms.drugs.service.DrugsMemberService;
import com.comvee.cdms.sign.dto.AddSuggestDTO;
import com.comvee.cdms.sign.dto.ListBmiDTO;
import com.comvee.cdms.sign.dto.ListMemberBmiDTO;
import com.comvee.cdms.sign.po.BmiPO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.BmiService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: wangxy
 * @date: 2018/12/28
 */
@RestController
@RequestMapping("/web/drugsMember")
@RequiresUser
public class WebDrugsMemberController {

    @Autowired
    private DrugsMemberService drugsMemberService;

    /**
     * 加载药品列表
     * @param listDrugsDepotDTO
     * @return
     */
    @RequestMapping("listDrugsDepot")
    public Result listDrugsDepot(@Validated ListDrugsDepotDTO listDrugsDepotDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<DrugsDepotPO> list = this.drugsMemberService.listDrugsDepot(listDrugsDepotDTO);
        return new Result(list);
    }

    /**
     * 分页加载药品列表根据当前医生医院（空返回系统默认）
     * @param listDrugsDepotDTO
     * @return
     */
    @RequestMapping("listDrugsDepotPageByHosForDefault")
    public Result listDrugsDepotPageByHosForDefault(@Validated ListDrugsDepotDTO listDrugsDepotDTO,PageRequest page){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        PageResult<DrugsDepotPO> list = this.drugsMemberService.listDrugsDepotPageByHosForDefault(listDrugsDepotDTO,page);
        return new Result(list);
    }


    /**
     * 新增药品记录-医院
     * @param drugsDepotDTO
     * @return
     */
    @RequestMapping("addDrugsDepotOfHospital")
    public Result addDrugsDepotOfHospital(@Validated DrugsDepotDTO drugsDepotDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        drugsDepotDTO.setBelongType(4);
        drugsDepotDTO.setBelongId(doctorSessionBO.getHospitalId());
        drugsDepotDTO.setMemberId(doctorSessionBO.getDoctorId());
        String id = this.drugsMemberService.addDrugsDepotOfHospital(drugsDepotDTO);
        return new Result(id);
    }

    /**
     * 新增药品(南京)
     * @return
     */
    @RequestMapping("addDrugsDeportNj")
    public Result addDrugsDeportNj(String drugsDrportJson){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        this.drugsMemberService.addDrugsDeportNj(drugsDrportJson,doctorSessionBO.getHospitalId());
        return Result.ok();
    }
}
