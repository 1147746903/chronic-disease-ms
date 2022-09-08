package com.comvee.cdms.remind.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.remind.dto.AddMemberRemindDTO;
import com.comvee.cdms.remind.dto.ListMemberRemindMapperDTO;
import com.comvee.cdms.remind.po.MemberRemindLatestPO;
import com.comvee.cdms.remind.po.MemberRemindPO;

/**
 * @author: suyz
 * @date: 2018/11/7
 */
public interface MemberRemindService {


    /**
     * 获取患者提醒最新消息
     * @param memberId
     * @return
     */
    MemberRemindLatestPO getMemberRemindLatest(String memberId);

    /**
     * 加载患者提醒列表
     * @param listMemberRemindMapperDTO
     * @return
     */
    PageResult<MemberRemindPO> listMemberRemind(ListMemberRemindMapperDTO listMemberRemindMapperDTO, PageRequest pr);

    /**
     * 新增患者提醒记录
     * @param addMemberRemindDTO
     */
    String addMemberRemind(AddMemberRemindDTO addMemberRemindDTO);

    /**
     * 清除未读数
     * @param memberId
     */
    void clearUnRead(String memberId);
}
