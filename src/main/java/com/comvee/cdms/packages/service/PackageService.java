package com.comvee.cdms.packages.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.dto.*;
import com.comvee.cdms.packages.po.*;
import com.comvee.cdms.packages.vo.MemberPackageInfoVO;
import com.comvee.cdms.packages.vo.MemberPackageListVO;
import com.comvee.cdms.packages.vo.PackageGroupVO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/10
 */
public interface PackageService {

    /**
     * 加载医院套餐
     * @param hospitalId
     * @return
     */
    List<PackagePO> listHospitalPackage(String hospitalId);

    /**
     * 新增患者套餐
     * @param addMemberPackageDTO
     * @return
     */
    String addMemberPackage(AddMemberPackageDTO addMemberPackageDTO);

    /**
     * 加载患者套餐列表
     * @param listMemberPackageDTO
     * @param pr
     * @return
     */
    PageResult<MemberPackagePO> listMemberPackageById(ListMemberPackageDTO listMemberPackageDTO, PageRequest pr);

    /**
     * 加载套餐列表
     * @param listMemberPackageDTO
     * @param pr
     * @return
     */
    PageResult<MemberPackageListVO> listMemberPackage(ListMemberPackageDTO listMemberPackageDTO, PageRequest pr);

    /**
     * 套餐金额统计
     * @param sumPackagePriceDTO
     * @return
     */
    long sumPackagePrice(SumPackagePriceDTO sumPackagePriceDTO);

    /**
     * 统计每月套餐金额
     * @param sumPackagePriceDTO
     * @return
     */
    List<PackageMonthPricePO> sumPackageMonthPrice(SumPackageMonthPriceDTO sumPackagePriceDTO);

    /**
     * 统计每月套餐金额-医院角度
     * @param sumPackagePriceDTO
     * @return
     */
    List<PackageMonthPricePO> sumPackageMonthPrice(GetStatisticsDTO dto);

    /**
     * 统计每月套餐数量
     * @param countMonthPackageDTO
     * @return
     */
    List<CountMonthPackagePO> countMonthPackage(CountMonthPackageDTO countMonthPackageDTO);

    /**
     * 根据id获取患者套餐信息
     * @param memberPackageId
     * @return
     */
    MemberPackagePO getMemberPackageById(String memberPackageId);

    /**
     * 加载患者有效套餐
     * @param listValidMemberPackageDTO
     * @return
     */
    List<MemberPackagePO> listValidMemberPackage(ListValidMemberPackageDTO listValidMemberPackageDTO);

    /**
     * 过滤拥有有效套餐的患者id
     * @param memberIdList
     * @param doctorId
     * @return
     */
    List<String> listHasValidPackageMember(List<String> memberIdList, String doctorId);

    /**
     * 使用套餐服务
     * @param usePackageServiceDTO
     */
    boolean usePackageServiceWithLock(UsePackageServiceDTO usePackageServiceDTO);

    /**
     * 分页加载拥有某个服务的患者列表
     * @param serviceCode
     * @return
//     */
    PageResult<String> listHasValidServiceMember(ServiceCode serviceCode ,int page,int rows);

    /**
     * 获取套餐及服务内容
     * @param memberPackageId
     * @return
     */
    MemberPackageInfoVO getMemberPackageWithServiceInfo(String memberPackageId);

    /**
     * 加载订单套餐列表
     * @param listMemberPackageDTO
     * @param pr
     * @return
     */
    PageResult<OrderPackagePO> listOrderPackage(ListMemberPackageDTO listMemberPackageDTO, PageRequest pr);

    /**
     * 根据到期时间加载患者套餐列表
     * @param pr
     * @param day
     * @return
     */
    PageResult<MemberPackagePO> listMemberPackageByExpireDay(int page , int rows, int day);

    /**
     * 统计患者套餐数量
     * @return
     */
    long countMemberPackage(CountMemberPackageDTO countMemberPackageDTO);

    /**
     * 加载套餐管理列表
     * @param packageManageDTO
     * @return
     */
    PageResult<PackageManagePO> listPackageManage(PackageManageDTO packageManageDTO,PageRequest page);

    /**
     * 获取患者的套餐
     * @param memberId
     * @return
     */
    MemberPackagePO getEarlyMemberPackage(SynthesizeDataDTO synthesizeDataDTO);

    /**
     * 根据时间统计套餐新增数量
     * @param synthesizeDataDTO
     * @return
     */
    long countNewPackage(SynthesizeDataDTO synthesizeDataDTO);

    /**
     * 获取套餐下拉列表
     * @return
     */
    List<PackagePO> pullpackage();

    /**
     * 删除套餐
     * @param sid
     */
    void deletePackage(DeleteMemberPackageDTO packageDTO);

    /**
     * 根据患者id和医生id查询套餐列表
     * @param memberId
     * @param doctorId
     * @return
     */
    List<MemberPackagePO> listPackageByMemberDoctor(String memberId,String doctorId);

    /**
     * 删除套餐服务
     * @param memberPackageId
     */
    void updateMemberPackageService(String memberPackageId);



    /**
     * 根据id获取套餐信息
     * @param pacakgeId
     * @return
     */
    PackagePO getPackageById(String packageId);

    /**
     * 统计每月套餐数量-医院
     * @param countMonthPackageDTO
     * @return
     */
    List<CountMonthPackagePO> countMonthPackage(GetStatisticsDTO dto);

    /**
     * 套餐金额统计-医院
     * @param sumPackagePriceDTO
     * @return
     */
    long sumPackagePrice(GetStatisticsDTO dto);

    /**
     * 加载套餐分组
     * @param doctorId
     * @return
     */
    List<PackageGroupVO> listPackageGroup(String doctorId);

    /**
     * 获取患者所有的套餐
     * @param dto
     * @return
     */
    List<MemberPackagePO> listMemberAllPackage(ListValidMemberPackageDTO dto);

    /** v5.1.6
     * 加载患者所有有效的套餐
     * @return
     */
    List<MemberPackagePO> listAllMemberValidPackage(String memberId);
}
