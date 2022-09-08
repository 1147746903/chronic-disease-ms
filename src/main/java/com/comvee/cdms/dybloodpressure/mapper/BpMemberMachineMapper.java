package com.comvee.cdms.dybloodpressure.mapper;


import com.comvee.cdms.dybloodpressure.dto.GetMemberMachineDTO;
import com.comvee.cdms.dybloodpressure.po.BpMemberMachinePO;
import com.comvee.cdms.dybloodpressure.vo.ListMemberMachineBindVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 动态血压患者设备绑定表(TBpMemberMachine)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-26 10:02:03
 */
public interface BpMemberMachineMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    BpMemberMachinePO queryById(@Param("sid") String sid);


    BpMemberMachinePO queryByMachineNo(@Param("machineNo") String machineNo);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<BpMemberMachinePO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tBpMemberMachine 实例对象
     * @return 对象列表
     */

    List<BpMemberMachinePO> queryAll(BpMemberMachinePO BpMemberMachinePO);

    List<ListMemberMachineBindVO> listVO(GetMemberMachineDTO getMemberMachineDTO);

    List<ListMemberMachineBindVO> listAllActiveBindList(GetMemberMachineDTO getMemberMachineDTO);


    /**
     * 新增数据
     *
     * @param tBpMemberMachine 实例对象
     * @return 影响行数
     */
    int insert(BpMemberMachinePO tBpMemberMachine);

    /**
     * 修改数据
     *
     * @param tBpMemberMachine 实例对象
     * @return 影响行数
     */
    int update(BpMemberMachinePO tBpMemberMachine);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(Long sid);


    //获取解绑佩戴计划到期设备
    List<BpMemberMachinePO> unBindPlanEndBindList();

    int unBindPlanEnd(@Param("list") List<String> list);

}

