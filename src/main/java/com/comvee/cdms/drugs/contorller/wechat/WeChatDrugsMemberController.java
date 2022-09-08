package com.comvee.cdms.drugs.contorller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.drugs.dto.ListDrugsDepotDTO;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.drugs.service.DrugsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat/drugsMember")
public class WeChatDrugsMemberController {


    @Autowired
    private DrugsMemberService drugsMemberService;

    @Autowired
    private DoctorServiceI doctorService;

    /**
     * 分页加载药品库-医院（空取系统默认）
     * @param drugType 药品类型
     * @param drugName 药品名称
     * @param doctorId 医生编号（必填）
     * @param page
     * @return
     */
    @RequestMapping("/loadDrugsList")
    @ResponseBody
    public Result loadDrugsList(String drugType, String drugName, String doctorId, PageRequest page){
        ValidateTool.checkParamIsNull(doctorId,"doctorId");
        DoctorPO doctorModel = this.doctorService.getDoctorById(doctorId);
        ListDrugsDepotDTO listDrugsDepotDTO = new ListDrugsDepotDTO();
        listDrugsDepotDTO.setBelongType(4);
        listDrugsDepotDTO.setBelongId(doctorModel.getHospitalId());
        listDrugsDepotDTO.setDrugName(drugName);
        listDrugsDepotDTO.setMemberId(doctorId);
        listDrugsDepotDTO.setType(drugType);
        PageResult<DrugsDepotPO> pageResult = this.drugsMemberService.listDrugsDepotPageByHosForDefault(listDrugsDepotDTO, page);
        return Result.ok(pageResult);
    }
}
