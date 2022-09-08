package com.comvee.cdms.packages.controller.web;

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.packages.dto.AddMemberPackageDTO;
import com.comvee.cdms.packages.dto.ListMemberPackageDTO;
import com.comvee.cdms.packages.dto.ListValidMemberPackageDTO;
import com.comvee.cdms.packages.po.MemberPackagePO;
import com.comvee.cdms.packages.po.OrderPackagePO;
import com.comvee.cdms.packages.po.PackagePO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.packages.vo.MemberPackageInfoVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2018/10/10
 */
@RestController
@RequestMapping("/web/package")
@RequiresUser
public class WebPackageController {

    @Autowired
    private PackageService packageService;

    /**
     * 加载医生所有患者套餐列表
     * @param pr
     * @param listMemberPackageDTO
     * @return
     */
    @RequestMapping("listMemberPackage")
    public Result listMemberPackage(PageRequest pr, @Validated ListMemberPackageDTO listMemberPackageDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        listMemberPackageDTO.setDoctorList(doctorSessionBO.getRelateDoctorList());
        listMemberPackageDTO.setIsRead(0);
        PageResult<OrderPackagePO> memberPackageListVOPageResult = this.packageService.listOrderPackage(listMemberPackageDTO, pr);
        return new Result(memberPackageListVOPageResult);
    }

    /**
     * 加载患者套餐列表
     * @param pr
     * @param listMemberPackageDTO
     * @return
     */
    @RequestMapping("listMemberPackageByMemberId")
    public Result listMemberPackageByMemberId(PageRequest pr, @Validated ListMemberPackageDTO listMemberPackageDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        listMemberPackageDTO.setHospitalId(doctorSessionBO.getHospitalId());
        if(StringUtils.isBlank(listMemberPackageDTO.getDoctorId())){
            if(doctorSessionBO.getSwitchHospital()==1){
                listMemberPackageDTO.setDoctorId(doctorSessionBO.getDoctorId());
            }
        }
        PageResult<MemberPackagePO> memberPackagePOPageResult = this.packageService.listMemberPackageById(listMemberPackageDTO, pr);
        return new Result(memberPackagePOPageResult);
    }

    /**
     * 加载可添加的套餐列表
     * @return
     */
    @RequestMapping("listPackage")
    public Result listPackage(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<PackagePO> packagePOList = this.packageService.listHospitalPackage(doctorSessionBO.getHospitalId());
        return new Result(packagePOList);
    }

    /**
     * 新增套餐给患者
     * @param addMemberPackageDTO
     * @return
     */
    @RequestMapping("addMemberPackage")
    public Result addMemberPackage(@Validated AddMemberPackageDTO addMemberPackageDTO){
//        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
//        addMemberPackageDTO.setDoctorId(doctorSessionBO.getDoctorId());
        String memberPacakgeId = this.packageService.addMemberPackage(addMemberPackageDTO);
        return new Result(memberPacakgeId);
    }

    /**
     * 根据id获取用户套餐信息
     * @param memberPackageId
     * @return
     */
    @RequestMapping("getMemberPackageById")
    public Result getMemberPackageById(String memberPackageId){
        ValidateTool.checkParamIsNull(memberPackageId, "memberPackageId");
        MemberPackagePO memberPackagePO = this.packageService.getMemberPackageById(memberPackageId);
        return new Result(memberPackagePO);
    }

    /**
     * 加载患者有效套餐
     * @param memberId
     * @return
     */
    @RequestMapping("listValidMemberPackage")
    public Result listValidMemberPackage(String memberId, String doctorId){
        ValidateTool.checkParamIsNull(memberId, "患者id");
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(memberId);
        listValidMemberPackageDTO.setDoctorId(doctorId);
        List<MemberPackagePO> list = this.packageService.listValidMemberPackage(listValidMemberPackageDTO);
        return new Result(list);
    }

    /**
     * 过滤拥有有效套餐的患者id
     * @param memberIdArr
     * @return
     */
    @RequestMapping("listHasValidPackageMember")
    public Result listHasValidPackageMember(String memberIdArr){
        ValidateTool.checkParamIsNull(memberIdArr, "memberIdArr");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<String> memberIdList = Arrays.asList(memberIdArr.split(",")).stream().collect(Collectors.toList());
        List<String> resultList = this.packageService.listHasValidPackageMember(memberIdList, doctorSessionBO.getDoctorId());
        return new Result(resultList);
    }

    /**
     * 获取患者套餐及服务信息
     * @param memberPackageId
     * @return
     */
    @RequestMapping("getMemberPackageWithServiceInfo")
    public Result getMemberPackageWithServiceInfo(String memberPackageId){
        ValidateTool.checkParamIsNull(memberPackageId, "memberPackageId");
        MemberPackageInfoVO memberPackageInfoVO = this.packageService.getMemberPackageWithServiceInfo(memberPackageId);
        return new Result(memberPackageInfoVO);
    }
}
