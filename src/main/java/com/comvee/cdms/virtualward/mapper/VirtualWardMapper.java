package com.comvee.cdms.virtualward.mapper;

import com.comvee.cdms.sign.dto.MemberBloodSugarDTO;
import com.comvee.cdms.virtualward.model.param.*;
import com.comvee.cdms.virtualward.model.po.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VirtualWardMapper {

    /**
     * 添加虚拟病区记录
     * @param virtualWardPO
     */
    void addVirtualWard(VirtualWardPO virtualWardPO);

    /**
     * 修改虚拟病区记录
     * @param virtualWard
     */
    void updateVirtualWard(VirtualWardPO update);

    /**
     * 获取虚拟病区记录
     * @param id
     * @return
     */
    VirtualWardPO getVirtualWard(GetVirtualWardParam get);

    /**
     * 加载虚拟病区患者列表
     * @param param
     * @return
     */
    List<VirtualWardListPO> listVirtualWardPatient(ListVirtualWardPatientParam param);

    /**
     * 加载虚拟病区患者列表历史记录
     * @param param
     * @return
     */
    List<VirtualWardListPO> listVirtualWardPatientRecord(ListVirtualWardPatientParam param);

    /**
     * 加载科室住院患者列表（给虚拟病区内嵌页使用）
     * @param param
     * @return
     */
    List<InHospitalPatientListPO> listInHospitalPatientForVirtualWard(QueryInHospitalPatientListParam param);

    /**
     * 加载虚拟病区里所有的科室列表
     * @return
     */
    List<VirtualWardDepartmentPO> listVirtualWardDepartment(@Param("hospitalId") String hospitalId);


    /**
     * 加载虚拟病区里所有的科室列表
     * @return
     */
    List<VirtualWardDepartmentPO> listAllVirtualWardDepartment(@Param("transferStatus") String transferStatus,@Param("hospitalId") String hospitalId);
    /**
     * 获取目前有效的虚拟病区记录
     * @param memberId
     * @return
     */
    VirtualWardPO getCurrentVirtualWard(@Param("memberId") String memberId , @Param("hospitalId") String hospitalId);

    VirtualWardPO getCurrentVirtualWardByInDate(@Param("memberId") String memberId , @Param("hospitalId") String hospitalId,@Param("inHospitalDate") String inHospitalDate);

    List<VirtualWardListPO> listHistoryVirtualWardWithNurseRecord(ListHistoryVirtualWardWithNurseRecordParam param);

    List<VirtualWardPO> listAllVirtualWard(@Param("hospitalId") String hospitalId,@Param("departmentId") String departmentId,@Param("transferStatus") Integer transferStatus,@Param("departmentIdList") List<String> departmentIdList,@Param("startDt") String startDt,@Param("endDt") String endDt);

    /**
     * 获取当前在虚拟病区的患者数据
     * @param memberId
     * @param hospitalId
     * @return
     */
    VirtualWardPO getVirtualWardByMemberId(@Param("memberId") String memberId,@Param("hospitalId") String hospitalId);

    List<VirtualWardPO> listDepartmentByMemberId(@Param("listMemberId")List<String> listMemberId);
    List<VirtualWardPO> listAllVirtualWardByDepartment(@Param("hospitalId") String hospitalId,@Param("departmentId") String departmentId);

    /**
     * 获取当前医院的全部患者
     * @param hospitalId
     * @return
     */
    List<VirtualWardPO> listAllVirtualWardByHospitalId(@Param("hospitalId") String hospitalId);

    /**
     * 获取今日虚拟病区患者的血糖记录
     * @param memberBloodSugarDTO
     * @return
     */
    List<VirtualWardPO> listTodayBloodSugarByVirtualWard(MemberBloodSugarDTO memberBloodSugarDTO);

    /**
     * 根据今日血糖测量情况加载虚拟病区患者
     * @param param
     * @return
     */
    List<String> listVirtualWardMemberByMonitor(ListVirtualWardMemberByMonitorParam param);
}
