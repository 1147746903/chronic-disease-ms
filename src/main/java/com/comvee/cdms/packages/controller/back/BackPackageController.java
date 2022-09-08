package com.comvee.cdms.packages.controller.back;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.packages.dto.DeleteMemberPackageDTO;
import com.comvee.cdms.packages.dto.PackageManageDTO;
import com.comvee.cdms.packages.po.PackageManagePO;
import com.comvee.cdms.packages.po.PackagePO;
import com.comvee.cdms.packages.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wyc
 * @date 2019/4/16 9:29
 */
@RestController
@RequestMapping("/back/package")
public class BackPackageController {

    @Autowired
    private PackageService packageService;

    @RequestMapping("listPackageManage")
    public Result listPackageManage(PackageManageDTO packageManageDTO, PageRequest page){
        PageResult<PackageManagePO> pageResult = this.packageService.listPackageManage(packageManageDTO, page);
        return new Result(pageResult);
    }

    @RequestMapping("pullPackage")
    public Result pullPackage(){
        List<PackagePO> list = this.packageService.pullpackage();
        return new Result(list);
    }

    @RequestMapping("deletePackage")
    public Result deletePackage(DeleteMemberPackageDTO packageDTO) {
        ValidateTool.checkParameterIsNull(packageDTO.getSid(),"sid");
        ValidateTool.checkParameterIsNull(packageDTO.getMemberId(),"memberId");
        ValidateTool.checkParameterIsNull(packageDTO.getDoctorId(),"doctorId");
        this.packageService.deletePackage(packageDTO);
        return new Result("");
    }
}
