package com.comvee.cdms.remind.controller.wechat;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.remind.dto.ListMemberRemindMapperDTO;
import com.comvee.cdms.remind.po.MemberRemindLatestPO;
import com.comvee.cdms.remind.po.MemberRemindPO;
import com.comvee.cdms.remind.service.MemberRemindService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2018/11/7
 */
@RestController
@RequestMapping("/wechat/remind")
@RequiresUser
public class WechatRemindController {

    @Autowired
    private MemberRemindService memberRemindService;

    /**
     * 加载提醒最新消息
     * @return
     */
    @RequestMapping("getMemberRemindLatest")
    public Result getMemberRemindLatest(){
        MemberPO memberPO = SessionTool.getWechatSession();
        MemberRemindLatestPO memberRemindLatestPO = this.memberRemindService.getMemberRemindLatest(memberPO.getMemberId());
        return new Result(memberRemindLatestPO);
    }

    /**
     * 加载患者提醒
     * @param pr
     * @return
     */
    @RequestMapping("listMemberRemind")
    public Result listMemberRemind(PageRequest pr){
        MemberPO memberPO = SessionTool.getWechatSession();
        ListMemberRemindMapperDTO listMemberRemindMapperDTO = new ListMemberRemindMapperDTO();
        listMemberRemindMapperDTO.setMemberId(memberPO.getMemberId());
        PageResult<MemberRemindPO> pageResult = this.memberRemindService.listMemberRemind(listMemberRemindMapperDTO, pr);
        this.memberRemindService.clearUnRead(memberPO.getMemberId());
        return new Result(pageResult);
    }
}
