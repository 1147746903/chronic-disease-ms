package com.comvee.cdms.hospital.controller.web;

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.service.CommitteeService;
import com.comvee.cdms.hospital.service.HospitalRelatedService;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
@RestController
@RequestMapping("/web/hospital")
public class WebHospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalRelatedService hospitalRelatedService;

    @Autowired
    private CommitteeService committeeService;

    /**
     * 加载所有医院
     * @param listHospitalDTO
     * @param pr
     * @return
     */
    @RequestMapping("listAllHospital")
    public Result listAllHospital(){
        List<HospitalPO> result = this.hospitalService.listAllHospital();
        return Result.ok(result);
    }

    /** v5.1.1
     * 查询对应等级的医院列表
     * @param queryType 1查询上级  2查询下级
     * @param hospitalId
     * @return
     */
    @RequestMapping("listRelatedHospital")
    public Result listRelatedHospital(Integer queryType,String hospitalId){
        ValidateTool.checkParamIsNull(queryType, "queryType");
        DoctorSessionBO doctor = SessionTool.getWebSession();
        if (StringUtils.isBlank(hospitalId)){
            hospitalId = doctor.getHospitalId();
        }
        List<HospitalPO> list = this.hospitalRelatedService.listRelatedHospital(queryType, hospitalId);
        return new Result(list);
    }

    /**
     * 获取地区医院列表
     * @param areaId
     * @return
     */
    @RequestMapping("listHospitalByAreaId")
    public Result listHospitalByAreaId(String areaId){
        ValidateTool.checkParamIsNull(areaId, "areaId");
        List<HospitalPO> hospitalPOS = hospitalService.listHospitalByAreaId(areaId);
        return Result.ok(hospitalPOS);
    }


    /**
     * 获取当前居委会列表
     */
    @RequestMapping("listCommittee")
    public Result listCommittee(){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        HospitalCommitteePO getPO = new HospitalCommitteePO();
        getPO.setHospitalId(webSession.getHospitalId());
        getPO.setValid(1);
        List<HospitalCommitteePO> result = committeeService.listHospitalCommittee(getPO);
        return Result.ok(result);
    }
}
