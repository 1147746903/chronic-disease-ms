package com.comvee.cdms.packages.vo;

import com.comvee.cdms.packages.po.MemberPackagePO;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/22
 */
public class MemberPackageInfoVO {

    private MemberPackagePO memberPackage;
    private List<MemberPackageServiceVO> serviceList;

    public MemberPackagePO getMemberPackage() {
        return memberPackage;
    }

    public void setMemberPackage(MemberPackagePO memberPackage) {
        this.memberPackage = memberPackage;
    }

    public List<MemberPackageServiceVO> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<MemberPackageServiceVO> serviceList) {
        this.serviceList = serviceList;
    }
}
