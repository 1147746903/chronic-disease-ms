package com.comvee.cdms.remind.mapper;

import com.comvee.cdms.remind.dto.ListMemberRemindMapperDTO;
import com.comvee.cdms.remind.dto.UpdateMemberRemindLatestDTO;
import com.comvee.cdms.remind.po.MemberRemindLatestPO;
import com.comvee.cdms.remind.po.MemberRemindPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/7
 */
public interface MemberRemindMapper {

    /**
     * 获取患者提醒最新消息
     * @param memberId
     * @return
     */
    MemberRemindLatestPO getMemberRemindLatest(@Param("memberId") String memberId);

    /**
     * 新增患者提醒最新消息记录
     * @param memberRemindLatestPO
     */
    void addMemberRemindLatest(MemberRemindLatestPO memberRemindLatestPO);

    /**
     * 修改患者最新消息
     * @param updateMemberRemindLatestDTO
     */
    void updateMemberRemindLatest(UpdateMemberRemindLatestDTO updateMemberRemindLatestDTO);

    /**
     * 加载患者提醒列表
     * @param listMemberRemindMapperDTO
     * @return
     */
    List<MemberRemindPO> listMemberRemind(ListMemberRemindMapperDTO listMemberRemindMapperDTO);

    /**
     * 新增患者提醒记录
     * @param memberRemindPO
     */
    void addMemberRemind(MemberRemindPO memberRemindPO);
}
