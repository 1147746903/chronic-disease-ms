package com.comvee.cdms.packages.controller.wechat;

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.dto.ListValidMemberPackageDTO;
import com.comvee.cdms.packages.po.MemberPackagePO;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.packages.vo.MemberPackageInfoVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/10
 */
@RestController
@RequestMapping("/wechat/package")
@RequiresUser
public class WechatPackageController {

    @Autowired
    private PackageService packageService;

    @RequestMapping("listMemberValidPackage")
    public Result listMemberValidPackage(String doctorId,String serviceCodeArr){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        MemberPO memberPO = SessionTool.getWechatSession();
        ListValidMemberPackageDTO listValidMemberPackageDTO = new ListValidMemberPackageDTO();
        listValidMemberPackageDTO.setMemberId(memberPO.getMemberId());
        listValidMemberPackageDTO.setDoctorId(doctorId);
        listValidMemberPackageDTO.setCodeList(serviceCodeHandler(serviceCodeArr));
        List<MemberPackagePO> list = this.packageService.listValidMemberPackage(listValidMemberPackageDTO);
        return new Result(list);
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
    /**
     * 服务code处理
     * @param serviceCodeArr
     * @return
     */
    private List<ServiceCode> serviceCodeHandler(String serviceCodeArr){
        List<ServiceCode> list = new ArrayList<>();
        if(StringUtils.isBlank(serviceCodeArr)){
            return list;
        }
        Arrays.asList(serviceCodeArr.split(",")).forEach(x ->{
            list.add(ServiceCode.valueOf(x));
        });
        return list;
    }

    /**
     * 加载患者所有的有效套餐
     * @return
     */
    @RequestMapping("listAllMemberValidPackage")
    public Result listAllMemberValidPackage(){
        MemberPO memberPO = SessionTool.getWechatSession();
        List<MemberPackagePO> list = this.packageService.listAllMemberValidPackage(memberPO.getMemberId());
        return new Result(list);
    }


}
