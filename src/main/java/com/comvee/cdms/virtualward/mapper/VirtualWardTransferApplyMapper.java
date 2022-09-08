package com.comvee.cdms.virtualward.mapper;

import com.comvee.cdms.virtualward.model.param.GetVirtualWardTransferApplyParam;
import com.comvee.cdms.virtualward.model.param.ListVirtualWardPatientParam;
import com.comvee.cdms.virtualward.model.param.QueryTransferApplyListParam;
import com.comvee.cdms.virtualward.model.po.TransferApplyListPO;
import com.comvee.cdms.virtualward.model.po.VirtualWardTransferApplyPO;

import java.util.List;

public interface VirtualWardTransferApplyMapper {

    /**
     * 添加转移申请
     * @param v
     */
    void addVirtualWardTransferApply(VirtualWardTransferApplyPO v);

    /**
     * 修改转移申请
     * @param v
     */
    void updateVirtualWardTransferApply(VirtualWardTransferApplyPO v);

    /**
     * 根据条件获取申请记录
     * @param get
     * @return
     */
    VirtualWardTransferApplyPO getVirtualWardTransferApplyOne(GetVirtualWardTransferApplyParam get);

    /**
     * 根据条件获取全部的申请记录
     * @param get
     * @return
     */
    List<VirtualWardTransferApplyPO> listVirtualWardTransferApplyOne(GetVirtualWardTransferApplyParam get);

    /**
     * 加载转移申请的列表
     * @param param
     * @return
     */
    List<TransferApplyListPO> listTransferApplyList(QueryTransferApplyListParam param);

    /**
     * 统计申请
     * @param param
     * @return
     */
    long countVirtualWardApply(QueryTransferApplyListParam param);

    /**
     * 加载历史记录虚拟病区患者列表
     * @param param
     * @return
     */
    List<VirtualWardTransferApplyPO> listVirtualWardPatientRecord(ListVirtualWardPatientParam param);

}
