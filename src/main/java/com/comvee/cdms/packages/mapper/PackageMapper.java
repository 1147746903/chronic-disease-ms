package com.comvee.cdms.packages.mapper;

import com.comvee.cdms.packages.dto.*;
import com.comvee.cdms.packages.po.*;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/10
 */
public interface PackageMapper {

    /**
     * 加载医院套餐列表
     * @param hospitalId
     * @return
     */
    List<PackagePO> listHospitalPackage(@Param("hospitalId") String hospitalId);

    /**
     * 加载患者套餐列表
     * @param listMemberPackageDTO
     * @return
     */
    List<MemberPackagePO> listMemberPackage(ListMemberPackageMapperDTO listMemberPackageDTO);

    /**
     * 新增患者套餐记录
     * @param addMemberPackageMapperDTO
     */
    void addMemberPackage(AddMemberPackageMapperDTO addMemberPackageMapperDTO);

    /**
     * 根据套餐id加载服务
     * @param packageId
     * @return
     */
    List<PackageServicePO> listPackageService(@Param("packageId") String packageId);

    /**
     * 根据id加载套餐信息
     * @param pacakgeId
     * @return
     */
    PackagePO getPackageById(@Param("packageId") String pacakgeId);

    /**
     * 新增患者套餐服务记录
     * @param addMemberPackageServiceMapperDTO
     */
    void addMemberPackageService(AddMemberPackageServiceMapperDTO addMemberPackageServiceMapperDTO);

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
    MemberPackagePO getMemberPackageById(@Param("memberPackageId") String memberPackageId);

    /**
     * 加载有效的患者套餐
     * @param listValidMemberPackageDTO
     * @return
     */
    List<MemberPackagePO> listValidMemberPackage(ListValidMemberPackageDTO listValidMemberPackageDTO);

    /**
     * 加载有效的患者套餐
     * @param listValidMemberPackageDTO
     * @return
     */
    MemberPackagePO getNewestValidMemberPackage(ListValidMemberPackageDTO listValidMemberPackageDTO);

    /**
     * 过滤拥有有效套餐的患者id
     * @param memberIdList
     * @param doctorId
     * @return
     */
    List<String> listHasValidPackageMember(@Param("memberIdList") List<String> memberIdList, @Param("doctorId") String doctorId);

    /**
     * 加载拥有某个服务的患者列表
     * @param serviceCode
     * @return
     */
    List<String> listHasValidServiceMember(@Param("serviceCode") String serviceCode);
    
    //gwx
    /**
     * 获取付费套餐数
     * @param memberIdList
     * @param doctorId
     * @return
     */    
    Long countHasValidPayPackageMember(@Param("memberIdList") List<String> memberIdList, @Param("doctorId") String doctorId);
   
    /**
     * 过滤得到已经付费的患者id
     * @param memberIdList
     * @param doctorId
     * @return
     */
    List<String> listIfPayPackageMember(@Param("memberIdList") List<String> memberIdList, @Param("doctorId") String doctorId);

    /**
     * 获取所有套餐
     * @return
     */
    List<PackagePO> getAllPackage(@Param("doctorList") List<String> doctorList);

    /**
     * 获取有效套餐的患者列表
     * @param
     * @return
     */
    List<MemberPackagePO> getValidPackageMemberList();

    /**
     * 加载患者套餐服务列表
     * @param memberPackageId
     * @return
     */
    List<MemberPackageServicePO> listMemberPackageServie(@Param("memberPackageId") String memberPackageId);

    /**\
     * 加载订单套餐列表
     * @param listMemberPackageDTO
     * @return
     */
    List<OrderPackagePO> listOrderPackage(ListMemberPackageMapperDTO listMemberPackageDTO);

    /**
     * 获取患者有效的套餐服务
     * @param memberId
     * @param doctorId
     * @param serviceCode
     * @return
     */
    MemberPackageServicePO getValidMemberService(UsePackageServiceDTO usePackageServiceDTO);

    /**
     * 服务扣次
     * @param sid
     */
    void useService(@Param("sid") String sid);

    /**
     * 根据到期时间加载患者套餐列表
     * @param day
     * @return
     */
    List<MemberPackagePO> listMemberPackageByExpireDay(@Param("date") String date);

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
    List<PackageManagePO> listPackageManage(PackageManageDTO packageManageDTO);

    /**
     * 获取患者的套餐
     * @param memberId
     * @return
     */
    List<MemberPackagePO> getEarlyMemberPackage(SynthesizeDataDTO synthesizeDataDTO);

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
    void deletePackage(@Param("sid") String sid);

    /**
     * 根据患者id和医生id查询套餐列表
     * @param memberId
     * @param doctorId
     * @return
     */
    List<MemberPackagePO> listPackageByMemberDoctor(@Param("memberId")String memberId,@Param("doctorId")String doctorId);

    /**
     * 删除套餐服务
     * @param memberPackageId
     */
    void updateMemberPackageService(@Param("memberPackageId") String memberPackageId);

    /**
     * 统计每月套餐金额-医院角度
     * @param dto
     * @return
     */
    List<PackageMonthPricePO> sumPackageMonthPriceForHos(GetStatisticsDTO dto);

    /**
     * 统计每月套餐数量-医院
     * @param countMonthPackageDTO
     * @return
     */
    List<CountMonthPackagePO> countMonthPackageForHos(GetStatisticsDTO dto);

    /**
     * 套餐金额统计-医院
     * @param dto
     * @return
     */
    long sumPackagePriceForHos(GetStatisticsDTO dto);

    /**
     * 统计套餐的患者数量
     * @param packageId
     * @param doctorList
     * @return
     */
    long countPackagePeopleNumber(@Param("packageId") String packageId ,@Param("doctorList") List<String> doctorList);

    /**
     * 加载的患者套餐
     * @param listValidMemberPackageDTO
     * @return
     */
    List<MemberPackagePO> listMemberAllPackage(ListValidMemberPackageDTO listValidMemberPackageDTO);

    /** v5.1.6
     * 加载患者所有有效的套餐
     * @return
     */
    List<MemberPackagePO> listAllMemberValidPackage(@Param("memberId") String memberId);

    /**
     * 设置患者套餐记录为已读
     * @param memberPackageId
     */
    void setMemberPackageIsRead(String memberPackageId);


}
