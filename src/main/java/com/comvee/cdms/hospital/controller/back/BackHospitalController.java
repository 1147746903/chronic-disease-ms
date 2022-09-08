package com.comvee.cdms.hospital.controller.back;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.hospital.model.dto.AddHospitalDTO;
import com.comvee.cdms.hospital.model.dto.ListHospitalDTO;
import com.comvee.cdms.hospital.model.dto.UpdateHospitalDTO;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.service.CommitteeService;
import com.comvee.cdms.hospital.service.HospitalRelatedService;
import com.comvee.cdms.hospital.service.HospitalService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
@RestController
@RequestMapping("/back/hospital")
@RequiresRoles("ADMIN")
public class BackHospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalRelatedService hospitalRelatedService;

    @Autowired
    private CommitteeService committeeService;

    /**
     * 添加医院
     * @param addHospitalDTO
     * @return
     */
    @RequestMapping("addHospital")
    public Result addHospital(@Validated AddHospitalDTO addHospitalDTO){
        String hospitalId = this.hospitalService.addHospital(addHospitalDTO);
        return Result.ok(hospitalId);
    }

    /**
     * 加载医院列表
     * @param listHospitalDTO
     * @param pr
     * @return
     */
    @RequestMapping("listHospital")
    public Result listHospital(ListHospitalDTO listHospitalDTO, PageRequest pr){
        PageResult<HospitalPO> result = this.hospitalService.listHospital(listHospitalDTO, pr);
        return Result.ok(result);
    }

    /**
     * 获取医院
     * @param hospitalId
     * @return
     */
    @RequestMapping("getHospital")
    public Result getHospital(String hospitalId){
        ValidateTool.checkParamIsNull(hospitalId, "hospitalId");
        HospitalPO hospitalPO = this.hospitalService.getHospital(hospitalId);
        return Result.ok(hospitalPO);
    }

    /**
     * 修改医院
     * @param hospitalDTO
     * @return
     */
    @RequestMapping("updateHospital")
    public Result updateHospital(@Validated UpdateHospitalDTO hospitalDTO){
        this.hospitalService.updateHospital(hospitalDTO);
        return Result.ok();
    }

    /**
     * 根据省市查询医院
     * @param cityId
     * @param areaId
     * @return
     */
    @RequestMapping("listHospitalByProvinceAndCity")
    public Result listHospitalByCityAndArea(String provinceId,String cityId){
        List<HospitalPO> list = this.hospitalService.listHospitalByProvinceAndCity(provinceId, cityId);
        return new Result(list);
    }

    /** v5.1.1
     * 查询对应等级的医院列表
     * @param queryType 1查询上级  0查询下级
     * @param hospitalId
     * @return
     */
    @RequestMapping("listRelatedHospital")
    public Result listRelatedHospital(Integer queryType,String hospitalId){
        List<HospitalPO> list = this.hospitalRelatedService.listRelatedHospital(queryType, hospitalId);
        return new Result(list);
    }

    /**
     * 查询所有的医院
     * @return
     */
    @RequestMapping("listAllHospital")
    public Result listHospital(){
        List<HospitalPO> result = this.hospitalService.listAllHospital();
        return new Result(result);
    }

    /**
     * 查询医院所有社区
     * @param
     * @return
     */
    @RequestMapping("listHospitalCommittee")
    public Result listDoctorCommittee(String hospitalId){
        ValidateTool.checkParamIsNull(hospitalId, "hospitalId");
        HospitalCommitteePO committeePO = new HospitalCommitteePO();
        committeePO.setHospitalId(hospitalId);
        List<HospitalCommitteePO> hospitalCommitteePOS = this.committeeService.listHospitalCommittee(committeePO);
        return Result.ok(hospitalCommitteePOS);
    }

}
