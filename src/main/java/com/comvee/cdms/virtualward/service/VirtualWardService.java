package com.comvee.cdms.virtualward.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.virtualward.model.param.*;
import com.comvee.cdms.virtualward.model.po.VirtualWardDepartmentPO;
import com.comvee.cdms.virtualward.model.po.VirtualWardListPO;
import com.comvee.cdms.virtualward.model.po.VirtualWardPO;
import com.comvee.cdms.virtualward.model.vo.*;

import java.util.List;

public interface VirtualWardService {

    /**
     * 加载虚拟病区病人列表
     * @param listVirtualWardPatientParam
     * @param pr
     * @return
     */
    PageResult<VirtualWardListVO> listVirtualWardPatient(ListVirtualWardPatientParam param ,PageRequest pr);

    /**
     * 加载历史记录虚拟病区记录列表
     * @param param
     * @param pr
     * @return
     */
    PageResult<VirtualWardVO> listVirtualWardPatientRecord(ListVirtualWardPatientParam param , PageRequest pr);

    /**
     * 加载科室住院患者列表（给虚拟病区内嵌页使用）
     * @param param
     * @param pr
     * @return
     */
    PageResult<InHospitalPatientListVO> listInHospitalPatientForVirtualWard(QueryInHospitalPatientListParam param , PageRequest pr);

    /**
     * 申请转入虚拟病区
     * @param id
     * @param operatorId
     */
    void applyTransferInto(String id ,String operatorId,String hospitalId);

    /**
     * 加载转入申请(提醒)列表
     * @param param
     * @param pr
     * @return
     */
    PageResult<TransferApplyListVO> listTransferIntoVirtualWardApply(QueryTransferIntoApplyListParam param, PageRequest pr);

    /**
     * 转出通知(提醒)列表
     * @param param
     * @param pr
     * @return
     */
    PageResult<TransferApplyListVO> listTransferOutVirtualWardApply(QueryTransferIntoApplyListParam param, PageRequest pr);

    /**
     * 允许转入
     * @param id
     * @param operatorId
     */
    void allowTransferInto(String id ,String operatorId,String hospitalId);

    /**
     * 推迟转入申请提醒
     * @param id
     * @param operatorId
     */
    void delayTransferIntoApplyRemind(String id ,String operatorId);

    /**
     * 获取转移申请详情
     * @param param
     * @return
     */
    TransferApplyDetailVO getTransferApplyDetail(GetVirtualWardTransferApplyParam param);

    /**
     * 申请转出虚拟病区
     * @param id
     * @param applyText
     * @param operatorId
     */
    void applyTransferOut(String id ,String applyText ,String operatorId);

    /**
     * 同意转出
     * @param id
     * @param operatorId
     */
    void allowTransferOut(String id ,String operatorId,String departId);

    /**
     * 加载虚拟病区患者列表
     * @param listVirtualWardPatientParam
     * @return
     */
    List<VirtualWardListPO> listVirtualWardPatient(ListVirtualWardPatientParam listVirtualWardPatientParam);

    /**
     * 统计申请
     * @param param
     * @return
     */
    long countVirtualWardApply(QueryTransferApplyListParam param);

    /**
     * 加载虚拟病区里已转入所有的科室列表
     * @return
     */
    List<VirtualWardDepartmentPO> listVirtualWardDepartment(String hospitalId);

    /**
     * 获取目前有效的虚拟病区记录
     * @param memberId
     * @return
     */
    VirtualWardPO getCurrentVirtualWard(String memberId ,String hospitalId);

    /**
     * 加载有护理记录的虚拟病区历史记录
     * @param pr
     * @param keyword
     * @param hospitalId
     * @return
     */
    PageResult<VirtualWardListPO> listHistoryVirtualWardWithNurseRecord(PageRequest pr ,ListHistoryVirtualWardWithNurseRecordParam param);

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
     * 根据今日血糖测量情况加载虚拟病区患者
     * @param param
     * @return
     */
    List<String> listVirtualWardMemberByMonitor(ListVirtualWardMemberByMonitorParam param);
}
